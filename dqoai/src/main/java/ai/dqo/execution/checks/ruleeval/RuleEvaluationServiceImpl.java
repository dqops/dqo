/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.dqo.execution.checks.ruleeval;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.data.readings.snapshot.SensorReadingsSnapshot;
import ai.dqo.data.readings.snapshot.SensorReadingsTimeSeriesData;
import ai.dqo.data.readings.snapshot.SensorReadingsTimeSeriesMap;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.data.readings.normalization.SensorNormalizedResult;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.rules.*;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.table.TableSlice;
import tech.tablesaw.table.TableSliceGroup;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Service that evaluates rules for each sensor reading returned by a sensor query.
 */
@Service
public class RuleEvaluationServiceImpl implements RuleEvaluationService {
    private final DataQualityRuleRunner ruleRunner;

    /**
     * Creates an instance of the rule evaluation service, given all dependencies.
     * @param ruleRunner Rule runner dependency.
     */
    @Autowired
    public RuleEvaluationServiceImpl(DataQualityRuleRunner ruleRunner) {
        this.ruleRunner = ruleRunner;
    }

    /**
     * Evaluate rules for sensor rules
     * @param checkExecutionContext Check execution context.
     * @param checkSpec Check specification with a list of rules.
     * @param sensorRunParameters Sensor run parameters (connection, table, check spec, etc).
     * @param normalizedSensorResults Table with the sensor results. Each row is evaluated through rules.
     * @param sensorReadingsSnapshot Sensor reading snapshot with historic sensor results.
     * @param progressListener Progress listener that receives events that notify about the rule evaluation.
     * @return Rule evaluation results as a table.
     */
    public RuleEvaluationResult evaluateRules(CheckExecutionContext checkExecutionContext,
											  AbstractCheckSpec checkSpec,
											  SensorExecutionRunParameters sensorRunParameters,
											  SensorNormalizedResult normalizedSensorResults,
											  SensorReadingsSnapshot sensorReadingsSnapshot,
											  CheckExecutionProgressListener progressListener) {
        AbstractRuleSetSpec ruleSet = checkSpec.getRuleSet();
        List<AbstractRuleThresholdsSpec<?>> enabledRules = ruleSet.getEnabledRules();
        Table sensorResultsTable = normalizedSensorResults.getTable();
        TableSliceGroup dimensionTimeSeriesSlices = sensorResultsTable.splitOn(normalizedSensorResults.getDimensionIdColumn());
        SensorReadingsTimeSeriesMap historicReadingsTimeSeries = sensorReadingsSnapshot.getHistoricReadingsTimeSeries();
        long checkHashId = checkSpec.getHierarchyId().hashCode64();

        DoubleColumn actualValueColumn = normalizedSensorResults.getActualValueColumn();
        DateTimeColumn timePeriodColumn = normalizedSensorResults.getTimePeriodColumn();
        RuleEvaluationResult result = RuleEvaluationResult.makeEmptyFromSensorResults(normalizedSensorResults);

        for (TableSlice dimensionTableSlice : dimensionTimeSeriesSlices) {
            Table dimensionSensorResults = dimensionTableSlice.asTable();  // results for a single dimension, the rows should be already sorted by the time period, ascending
            LongColumn dimensionColumn = (LongColumn) dimensionSensorResults.column(SensorNormalizedResult.DIMENSION_ID_COLUMN_NAME);
            Long timeSeriesDimensionId = dimensionColumn.get(0);
            SensorReadingsTimeSeriesData historicTimeSeriesData = historicReadingsTimeSeries.findTimeSeriesData(checkHashId, timeSeriesDimensionId);
            TimeSeriesGradient timeGradient = sensorRunParameters.getEffectiveTimeSeries().getTimeGradient();
            ZoneId connectionTimeZoneId = sensorRunParameters.getConnectionTimeZoneId();
            HistoricDataPointTimeSeriesCollector previousDataPointTimeSeriesCollectorCurrent = new HistoricDataPointTimeSeriesCollector(
                    dimensionSensorResults,
                    timeGradient,
                    connectionTimeZoneId);
            HistoricDataPointTimeSeriesCollector previousDataPointTimeSeriesCollectorOld = (historicTimeSeriesData != null) ?
                    new HistoricDataPointTimeSeriesCollector(
                            historicTimeSeriesData.getTable(),
                            timeGradient,
                            connectionTimeZoneId)
                    : null;

            for (int sliceRowIndex = 0; sliceRowIndex < dimensionTableSlice.rowCount() ; sliceRowIndex++) {
                int allSensorResultsRowIndex = dimensionTableSlice.mappedRowNumber(sliceRowIndex);
                Double actualValue = actualValueColumn.get(allSensorResultsRowIndex);
                LocalDateTime timePeriodLocal = timePeriodColumn.get(allSensorResultsRowIndex);

                for (AbstractRuleThresholdsSpec<?> ruleThresholds : enabledRules) {
                    RuleTimeWindowSettingsSpec ruleTimeWindow = ruleThresholds.getTimeWindow();
                    HistoricDataPoint[] previousDataPoints = null; // combined data points from current readings and historic sensor results
                    HistoricDataPoint[] oldDataPoints = null; // old data points retrieved from the last copy (snapshot) of previous readings
                    if (ruleTimeWindow != null) {
                        previousDataPoints = previousDataPointTimeSeriesCollectorCurrent.getHistoricDataPointsBefore(
                                timePeriodLocal, ruleTimeWindow.getPredictionTimeWindow());

                        if (previousDataPointTimeSeriesCollectorOld != null) {
                            oldDataPoints = previousDataPointTimeSeriesCollectorOld.getHistoricDataPointsBefore(
                                    timePeriodLocal, ruleTimeWindow.getPredictionTimeWindow());
                        }
                    }

                    if (previousDataPoints != null) {
                        int countNotNull = 0;
                        for (int hIdx = 0; hIdx < previousDataPoints.length; hIdx++) {
                            if (previousDataPoints[hIdx] == null) {
                                // check if we have historic data from previous sensor runs
                                if (oldDataPoints != null) {
                                    previousDataPoints[hIdx] = oldDataPoints[hIdx];
                                }
                            }

                            if (previousDataPoints[hIdx] != null) {
                                countNotNull++;
                            }
                        }

                        if (countNotNull < ruleTimeWindow.getMinPeriodsWithReading()) {
                            continue; // we will skip this reading, we cannot calculate a value because there is not enough sensor readings
                        }
                    }

                    Integer highestSeverity = null;
                    Row targetRuleResultRow = result.appendRow();
                    int targetRowIndex = targetRuleResultRow.getRowNumber();
                    result.copyRowFrom(targetRowIndex, sensorResultsTable, allSensorResultsRowIndex);
                    long ruleHash = ruleThresholds.getHierarchyId().hashCode64();
                    result.getRuleHashColumn().set(targetRowIndex, ruleHash);
                    result.getRuleNameColumn().set(targetRowIndex, ruleThresholds.getRuleName());

                    AbstractRuleParametersSpec highRule = ruleThresholds.getHigh();
                    AbstractRuleParametersSpec mediumRule = ruleThresholds.getMedium();
                    AbstractRuleParametersSpec lowRule = ruleThresholds.getLow();
                    Double expectedValue = null;

                    if (highRule != null) {
                        RuleExecutionRunParameters ruleRunParametersHigh = new RuleExecutionRunParameters(actualValue, highRule, timePeriodLocal, previousDataPoints, ruleTimeWindow);
                        RuleExecutionResult ruleExecutionResultHigh = this.ruleRunner.executeRule(checkExecutionContext, ruleRunParametersHigh);

                        if (!ruleExecutionResultHigh.isPassed()) {
                            highestSeverity = 3;
                        }

                        if (ruleExecutionResultHigh.getExpectedValue() != null) {
                            expectedValue = ruleExecutionResultHigh.getExpectedValue();
                        }

                        if (ruleExecutionResultHigh.getLowerBound() != null) {
                            result.getHighLowerBoundColumn().set(targetRowIndex, ruleExecutionResultHigh.getLowerBound());
                        }
                        if (ruleExecutionResultHigh.getUpperBound() != null) {
                            result.getHighUpperBoundColumn().set(targetRowIndex, ruleExecutionResultHigh.getUpperBound());
                        }
                    }

                    if (mediumRule != null) {
                        RuleExecutionRunParameters ruleRunParametersMedium = new RuleExecutionRunParameters(actualValue, mediumRule, timePeriodLocal, previousDataPoints, ruleTimeWindow);
                        RuleExecutionResult ruleExecutionResultMedium = this.ruleRunner.executeRule(checkExecutionContext, ruleRunParametersMedium);

                        if (highestSeverity == null && !ruleExecutionResultMedium.isPassed()) {
                            highestSeverity = 2;
                        }

                        if (expectedValue == null && ruleExecutionResultMedium.getExpectedValue() != null) {
                            expectedValue = ruleExecutionResultMedium.getExpectedValue();
                        }

                        if (ruleExecutionResultMedium.getLowerBound() != null) {
                            result.getMediumLowerBoundColumn().set(targetRowIndex, ruleExecutionResultMedium.getLowerBound());
                        }
                        if (ruleExecutionResultMedium.getUpperBound() != null) {
                            result.getMediumUpperBoundColumn().set(targetRowIndex, ruleExecutionResultMedium.getUpperBound());
                        }
                    }

                    if (lowRule != null) {
                        RuleExecutionRunParameters ruleRunParametersLow = new RuleExecutionRunParameters(actualValue, lowRule, timePeriodLocal, previousDataPoints, ruleTimeWindow);
                        RuleExecutionResult ruleExecutionResultLow = this.ruleRunner.executeRule(checkExecutionContext, ruleRunParametersLow);

                        if (highestSeverity == null && !ruleExecutionResultLow.isPassed()) {
                            highestSeverity = 1;
                        }

                        if (expectedValue == null && ruleExecutionResultLow.getExpectedValue() != null) {
                            expectedValue = ruleExecutionResultLow.getExpectedValue();
                        }

                        if (ruleExecutionResultLow.getLowerBound() != null) {
                            result.getLowLowerBoundColumn().set(targetRowIndex, ruleExecutionResultLow.getLowerBound());
                        }
                        if (ruleExecutionResultLow.getUpperBound() != null) {
                            result.getLowUpperBoundColumn().set(targetRowIndex, ruleExecutionResultLow.getUpperBound());
                        }
                    }

                    if (highestSeverity == null) {
                        highestSeverity = 0; // no alert
                    }

                    result.getSeverityColumn().set(targetRowIndex, highestSeverity);
                    result.getExpectedValueColumn().set(targetRowIndex, expectedValue);
                }
            }
        }

        return result;
    }
}
