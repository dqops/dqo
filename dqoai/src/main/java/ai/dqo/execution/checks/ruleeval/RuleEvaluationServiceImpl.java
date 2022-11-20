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

import ai.dqo.checks.AbstractCheckDeprecatedSpec;
import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshot;
import ai.dqo.data.readouts.snapshot.SensorReadoutsTimeSeriesData;
import ai.dqo.data.readouts.snapshot.SensorReadoutsTimeSeriesMap;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.rules.*;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResult;
import ai.dqo.execution.rules.finder.RuleDefinitionFindService;
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
 * Service that evaluates rules for each sensor readouts returned by a sensor query.
 */
@Service
public class RuleEvaluationServiceImpl implements RuleEvaluationService {
    private final DataQualityRuleRunner ruleRunner;
    private final RuleDefinitionFindService ruleDefinitionFindService;

    /**
     * Creates an instance of the rule evaluation service, given all dependencies.
     * @param ruleRunner Rule runner dependency.
     * @param ruleDefinitionFindService Rule definition find service.
     */
    @Autowired
    public RuleEvaluationServiceImpl(DataQualityRuleRunner ruleRunner,
                                     RuleDefinitionFindService ruleDefinitionFindService) {
        this.ruleRunner = ruleRunner;
        this.ruleDefinitionFindService = ruleDefinitionFindService;
    }

    /**
     * Evaluate rules for data quality checks.
     *
     * @param checkExecutionContext   Check execution context.
     * @param checkSpec               Check specification with a list of rules.
     * @param sensorRunParameters     Sensor run parameters (connection, table, check spec, etc).
     * @param normalizedSensorResults Table with the sensor results. Each row is evaluated through rules.
     * @param sensorReadoutsSnapshot  Snapshot of all sensor readouts loaded for the table.
     * @param progressListener        Progress listener that receives events that notify about the rule evaluation.
     * @return Rule evaluation results as a table.
     */
    @Override
    public RuleEvaluationResult evaluateRules(CheckExecutionContext checkExecutionContext,
                                              AbstractCheckSpec checkSpec,
                                              SensorExecutionRunParameters sensorRunParameters,
                                              SensorReadoutsNormalizedResult normalizedSensorResults,
                                              SensorReadoutsSnapshot sensorReadoutsSnapshot,
                                              CheckExecutionProgressListener progressListener) {
        Table sensorResultsTable = normalizedSensorResults.getTable();
        TableSliceGroup dimensionTimeSeriesSlices = sensorResultsTable.splitOn(normalizedSensorResults.getDataStreamHashColumn());
        SensorReadoutsTimeSeriesMap historicReadoutsTimeSeries = sensorReadoutsSnapshot.getHistoricReadoutsTimeSeries();
        long checkHashId = checkSpec.getHierarchyId().hashCode64();

        DoubleColumn actualValueColumn = normalizedSensorResults.getActualValueColumn();
        DateTimeColumn timePeriodColumn = normalizedSensorResults.getTimePeriodColumn();
        RuleEvaluationResult result = RuleEvaluationResult.makeEmptyFromSensorResults(normalizedSensorResults);

        String ruleDefinitionName = checkSpec.getRuleDefinitionName();
        RuleDefinitionFindResult ruleFindResult = this.ruleDefinitionFindService.findRule(checkExecutionContext, ruleDefinitionName);
        RuleTimeWindowSettingsSpec ruleTimeWindowSettings = ruleFindResult.getRuleDefinitionSpec().getTimeWindow();

        for (TableSlice dimensionTableSlice : dimensionTimeSeriesSlices) {
            Table dimensionSensorResults = dimensionTableSlice.asTable();  // results for a single dimension, the rows should be already sorted by the time period, ascending
            LongColumn dimensionColumn = (LongColumn) dimensionSensorResults.column(SensorReadoutsNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME);
            Long timeSeriesDimensionId = dimensionColumn.get(0);
            SensorReadoutsTimeSeriesData historicTimeSeriesData = historicReadoutsTimeSeries.findTimeSeriesData(checkHashId, timeSeriesDimensionId);
            TimeSeriesGradient timeGradient = sensorRunParameters.getTimeSeries().getTimeGradient();

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

                HistoricDataPoint[] previousDataPoints = null; // combined data points from current readouts and historic sensor readouts
                HistoricDataPoint[] oldDataPoints = null; // old data points retrieved from the last copy (snapshot) of previous readouts
                if (ruleTimeWindowSettings != null) {
                    previousDataPoints = previousDataPointTimeSeriesCollectorCurrent.getHistoricDataPointsBefore(
                            timePeriodLocal, ruleTimeWindowSettings.getPredictionTimeWindow());

                    if (previousDataPointTimeSeriesCollectorOld != null) {
                        oldDataPoints = previousDataPointTimeSeriesCollectorOld.getHistoricDataPointsBefore(
                                timePeriodLocal, ruleTimeWindowSettings.getPredictionTimeWindow());
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

                    if (countNotNull < ruleTimeWindowSettings.getMinPeriodsWithReadouts()) {
                        continue; // we will skip this readout, we cannot calculate a value because there is not enough sensor readouts
                    }
                }

                Integer highestSeverity = null;
                Row targetRuleResultRow = result.appendRow();
                int targetRowIndex = targetRuleResultRow.getRowNumber();
                result.copyRowFrom(targetRowIndex, sensorResultsTable, allSensorResultsRowIndex);
                result.getIncludeInKpiColumn().set(targetRowIndex, !checkSpec.isExcludeFromKpi());

                AbstractRuleParametersSpec fatalRule = checkSpec.getFatal();
                AbstractRuleParametersSpec errorRule = checkSpec.getError();
                AbstractRuleParametersSpec warningRule = checkSpec.getWarning();
                Double expectedValue = null;

                if (fatalRule != null) {
                    RuleExecutionRunParameters ruleRunParametersHigh = new RuleExecutionRunParameters(actualValue, fatalRule, timePeriodLocal, previousDataPoints, ruleTimeWindowSettings);
                    RuleExecutionResult ruleExecutionResultHigh = this.ruleRunner.executeRule(checkExecutionContext, ruleRunParametersHigh);

                    if (!ruleExecutionResultHigh.isPassed()) {
                        highestSeverity = 3;
                    }

                    if (ruleExecutionResultHigh.getExpectedValue() != null) {
                        expectedValue = ruleExecutionResultHigh.getExpectedValue();
                    }

                    if (ruleExecutionResultHigh.getLowerBound() != null) {
                        result.getFatalLowerBoundColumn().set(targetRowIndex, ruleExecutionResultHigh.getLowerBound());
                    }
                    if (ruleExecutionResultHigh.getUpperBound() != null) {
                        result.getFatalUpperBoundColumn().set(targetRowIndex, ruleExecutionResultHigh.getUpperBound());
                    }
                }

                if (errorRule != null) {
                    RuleExecutionRunParameters ruleRunParametersMedium = new RuleExecutionRunParameters(actualValue, errorRule, timePeriodLocal, previousDataPoints, ruleTimeWindowSettings);
                    RuleExecutionResult ruleExecutionResultMedium = this.ruleRunner.executeRule(checkExecutionContext, ruleRunParametersMedium);

                    if (highestSeverity == null && !ruleExecutionResultMedium.isPassed()) {
                        highestSeverity = 2;
                    }

                    if (expectedValue == null && ruleExecutionResultMedium.getExpectedValue() != null) {
                        expectedValue = ruleExecutionResultMedium.getExpectedValue();
                    }

                    if (ruleExecutionResultMedium.getLowerBound() != null) {
                        result.getErrorLowerBoundColumn().set(targetRowIndex, ruleExecutionResultMedium.getLowerBound());
                    }
                    if (ruleExecutionResultMedium.getUpperBound() != null) {
                        result.getErrorUpperBoundColumn().set(targetRowIndex, ruleExecutionResultMedium.getUpperBound());
                    }
                }

                if (warningRule != null) {
                    RuleExecutionRunParameters ruleRunParametersLow = new RuleExecutionRunParameters(actualValue, warningRule, timePeriodLocal, previousDataPoints, ruleTimeWindowSettings);
                    RuleExecutionResult ruleExecutionResultLow = this.ruleRunner.executeRule(checkExecutionContext, ruleRunParametersLow);

                    if (highestSeverity == null && !ruleExecutionResultLow.isPassed()) {
                        highestSeverity = 1;
                    }

                    if (expectedValue == null && ruleExecutionResultLow.getExpectedValue() != null) {
                        expectedValue = ruleExecutionResultLow.getExpectedValue();
                    }

                    if (ruleExecutionResultLow.getLowerBound() != null) {
                        result.getWarningLowerBoundColumn().set(targetRowIndex, ruleExecutionResultLow.getLowerBound());
                    }
                    if (ruleExecutionResultLow.getUpperBound() != null) {
                        result.getWarningUpperBoundColumn().set(targetRowIndex, ruleExecutionResultLow.getUpperBound());
                    }
                }

                if (highestSeverity == null) {
                    highestSeverity = 0; // no alert
                }

                result.getSeverityColumn().set(targetRowIndex, highestSeverity);
                result.getExpectedValueColumn().set(targetRowIndex, expectedValue);
            }
        }

        return result;
    }

    /**
     * Evaluate rules for sensor rules
     * @param checkExecutionContext Check execution context.
     * @param checkSpec Check specification with a list of rules.
     * @param sensorRunParameters Sensor run parameters (connection, table, check spec, etc).
     * @param normalizedSensorResults Table with the sensor results. Each row is evaluated through rules.
     * @param sensorReadoutsSnapshot Sensor readouts snapshot with historic sensor results.
     * @param progressListener Progress listener that receives events that notify about the rule evaluation.
     * @return Rule evaluation results as a table.
     */
    @Deprecated
    public RuleEvaluationResult evaluateLegacyRules(CheckExecutionContext checkExecutionContext,
                                                    AbstractCheckDeprecatedSpec checkSpec,
                                                    SensorExecutionRunParameters sensorRunParameters,
                                                    SensorReadoutsNormalizedResult normalizedSensorResults,
                                                    SensorReadoutsSnapshot sensorReadoutsSnapshot,
                                                    CheckExecutionProgressListener progressListener) {
        AbstractRuleSetSpec ruleSet = checkSpec.getRuleSet();
        List<AbstractRuleThresholdsSpec<?>> enabledRules = ruleSet.getEnabledRules();
        Table sensorResultsTable = normalizedSensorResults.getTable();
        TableSliceGroup dimensionTimeSeriesSlices = sensorResultsTable.splitOn(normalizedSensorResults.getDataStreamHashColumn());
        SensorReadoutsTimeSeriesMap historicReadoutsTimeSeries = sensorReadoutsSnapshot.getHistoricReadoutsTimeSeries();
        long checkHashId = checkSpec.getHierarchyId().hashCode64();

        DoubleColumn actualValueColumn = normalizedSensorResults.getActualValueColumn();
        DateTimeColumn timePeriodColumn = normalizedSensorResults.getTimePeriodColumn();
        RuleEvaluationResult result = RuleEvaluationResult.makeEmptyFromSensorResults(normalizedSensorResults);

        for (TableSlice dimensionTableSlice : dimensionTimeSeriesSlices) {
            Table dimensionSensorResults = dimensionTableSlice.asTable();  // results for a single dimension, the rows should be already sorted by the time period, ascending
            LongColumn dimensionColumn = (LongColumn) dimensionSensorResults.column(SensorReadoutsNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME);
            Long timeSeriesDimensionId = dimensionColumn.get(0);
            SensorReadoutsTimeSeriesData historicTimeSeriesData = historicReadoutsTimeSeries.findTimeSeriesData(checkHashId, timeSeriesDimensionId);
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
                    HistoricDataPoint[] previousDataPoints = null; // combined data points from current readouts and historic sensor readouts
                    HistoricDataPoint[] oldDataPoints = null; // old data points retrieved from the last copy (snapshot) of previous readouts
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

                        if (countNotNull < ruleTimeWindow.getMinPeriodsWithReadouts()) {
                            continue; // we will skip this readout, we cannot calculate a value because there is not enough sensor readouts
                        }
                    }

                    Integer highestSeverity = null;
                    Row targetRuleResultRow = result.appendRow();
                    int targetRowIndex = targetRuleResultRow.getRowNumber();
                    result.copyRowFrom(targetRowIndex, sensorResultsTable, allSensorResultsRowIndex);
                    long ruleHash = ruleThresholds.getHierarchyId().hashCode64();

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
                            result.getFatalLowerBoundColumn().set(targetRowIndex, ruleExecutionResultHigh.getLowerBound());
                        }
                        if (ruleExecutionResultHigh.getUpperBound() != null) {
                            result.getFatalUpperBoundColumn().set(targetRowIndex, ruleExecutionResultHigh.getUpperBound());
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
                            result.getErrorLowerBoundColumn().set(targetRowIndex, ruleExecutionResultMedium.getLowerBound());
                        }
                        if (ruleExecutionResultMedium.getUpperBound() != null) {
                            result.getErrorUpperBoundColumn().set(targetRowIndex, ruleExecutionResultMedium.getUpperBound());
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
                            result.getWarningLowerBoundColumn().set(targetRowIndex, ruleExecutionResultLow.getLowerBound());
                        }
                        if (ruleExecutionResultLow.getUpperBound() != null) {
                            result.getWarningUpperBoundColumn().set(targetRowIndex, ruleExecutionResultLow.getUpperBound());
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
