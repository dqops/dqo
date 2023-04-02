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
import ai.dqo.checks.CheckType;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshot;
import ai.dqo.data.readouts.snapshot.SensorReadoutsTimeSeriesData;
import ai.dqo.data.readouts.snapshot.SensorReadoutsTimeSeriesMap;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListener;
import ai.dqo.execution.rules.*;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResult;
import ai.dqo.execution.rules.finder.RuleDefinitionFindService;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.table.TableSlice;
import tech.tablesaw.table.TableSliceGroup;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Service that evaluates rules for each sensor readouts returned by a sensor query.
 */
@Service
public class RuleEvaluationServiceImpl implements RuleEvaluationService {
    private final DataQualityRuleRunner ruleRunner;
    private final RuleDefinitionFindService ruleDefinitionFindService;
    private final DefaultTimeZoneProvider defaultTimeZoneProvider;

    /**
     * Creates an instance of the rule evaluation service, given all dependencies.
     * @param ruleRunner Rule runner dependency.
     * @param ruleDefinitionFindService Rule definition find service.
     */
    @Autowired
    public RuleEvaluationServiceImpl(DataQualityRuleRunner ruleRunner,
                                     RuleDefinitionFindService ruleDefinitionFindService,
                                     DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.ruleRunner = ruleRunner;
        this.ruleDefinitionFindService = ruleDefinitionFindService;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Evaluate rules for data quality checks.
     *
     * @param executionContext   Check execution context.
     * @param checkSpec               Check specification with a list of rules.
     * @param sensorRunParameters     Sensor run parameters (connection, table, check spec, etc).
     * @param normalizedSensorResults Table with the sensor results. Each row is evaluated through rules.
     * @param sensorReadoutsSnapshot  Snapshot of all sensor readouts loaded for the table.
     * @param progressListener        Progress listener that receives events that notify about the rule evaluation.
     * @return Rule evaluation results as a table.
     */
    @Override
    public RuleEvaluationResult evaluateRules(ExecutionContext executionContext,
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
        DoubleColumn expectedValueColumn = normalizedSensorResults.getExpectedValueColumn();
        DateTimeColumn timePeriodColumn = normalizedSensorResults.getTimePeriodColumn();
        RuleEvaluationResult result = RuleEvaluationResult.makeEmptyFromSensorResults(normalizedSensorResults);

        String ruleDefinitionName = sensorRunParameters.getEffectiveSensorRuleNames().getRuleName();
        RuleDefinitionFindResult ruleFindResult = this.ruleDefinitionFindService.findRule(executionContext, ruleDefinitionName);
        RuleTimeWindowSettingsSpec ruleTimeWindowSettings = ruleFindResult.getRuleDefinitionSpec().getTimeWindow();

        for (TableSlice dimensionTableSlice : dimensionTimeSeriesSlices) {
            Table dimensionSensorResults = dimensionTableSlice.asTable();  // results for a single dimension, the rows should be already sorted by the time period, ascending
            LongColumn dimensionColumn = (LongColumn) dimensionSensorResults.column(SensorReadoutsColumnNames.DATA_STREAM_HASH_COLUMN_NAME);
            Long timeSeriesDimensionId = dimensionColumn.get(0);
            SensorReadoutsTimeSeriesData historicTimeSeriesData = historicReadoutsTimeSeries.findTimeSeriesData(checkHashId, timeSeriesDimensionId);
            TimeSeriesGradient timeGradient = sensorRunParameters.getTimeSeries().getTimeGradient();

            ZoneId defaultTimeZoneId = this.defaultTimeZoneProvider.getDefaultTimeZoneId();
            HistoricDataPointTimeSeriesCollector previousDataPointTimeSeriesCollectorCurrent = new HistoricDataPointTimeSeriesCollector(
                    dimensionSensorResults,
                    timeGradient,
                    defaultTimeZoneId);
            HistoricDataPointTimeSeriesCollector previousDataPointTimeSeriesCollectorOld = (historicTimeSeriesData != null) ?
                    new HistoricDataPointTimeSeriesCollector(
                            historicTimeSeriesData.getTable(),
                            timeGradient,
                            defaultTimeZoneId)
                    : null;

            for (int sliceRowIndex = 0; sliceRowIndex < dimensionTableSlice.rowCount() ; sliceRowIndex++) {
                int allSensorResultsRowIndex = dimensionTableSlice.mappedRowNumber(sliceRowIndex);
                Double actualValue = actualValueColumn.get(allSensorResultsRowIndex);
                Double expectedValueFromSensor = expectedValueColumn != null && !expectedValueColumn.isMissing(allSensorResultsRowIndex) ?
                        expectedValueColumn.get(allSensorResultsRowIndex) : null;

                LocalDateTime timePeriodLocal = timePeriodColumn.get(allSensorResultsRowIndex);
                HistoricDataPoint[] previousDataPoints = null; // combined data points from current readouts and historic sensor readouts

                if (sensorRunParameters.getCheckType() == CheckType.PROFILING) {
                    // these checks do not have real time periods, we just take the last data points, also we don't want the current sensor results
                    // because there should be none (only partitioned checks will have previous results from the most recent query), we will find them only in old data

                    if (ruleTimeWindowSettings != null && previousDataPointTimeSeriesCollectorOld != null) {
                        previousDataPoints = previousDataPointTimeSeriesCollectorOld.getHistoricContinuousResultsBefore(
                                timePeriodLocal, ruleTimeWindowSettings.getPredictionTimeWindow());
                    }
                } else {
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
                }

                Integer highestSeverity = null;
                Row targetRuleResultRow = result.appendRow();
                int targetRowIndex = targetRuleResultRow.getRowNumber();
                result.copyRowFrom(targetRowIndex, sensorResultsTable, allSensorResultsRowIndex);
                result.getIncludeInKpiColumn().set(targetRowIndex, !checkSpec.isExcludeFromKpi());
                result.getIncludeInSlaColumn().set(targetRowIndex, checkSpec.isIncludeInSla());

                AbstractRuleParametersSpec fatalRule = checkSpec.getFatal();
                AbstractRuleParametersSpec errorRule = checkSpec.getError();
                AbstractRuleParametersSpec warningRule = checkSpec.getWarning();
                Double expectedValue = null;

                if (fatalRule != null) {
                    RuleExecutionRunParameters ruleRunParametersFatal = new RuleExecutionRunParameters(actualValue, expectedValueFromSensor,
                            fatalRule, timePeriodLocal, previousDataPoints, ruleTimeWindowSettings);
                    RuleExecutionResult ruleExecutionResultFatal = this.ruleRunner.executeRule(executionContext, ruleRunParametersFatal, sensorRunParameters);

                    if (!ruleExecutionResultFatal.isPassed()) {
                        highestSeverity = 3;
                    }

                    if (ruleExecutionResultFatal.getExpectedValue() != null) {
                        expectedValue = ruleExecutionResultFatal.getExpectedValue();
                    }

                    if (ruleExecutionResultFatal.getLowerBound() != null) {
                        result.getFatalLowerBoundColumn().set(targetRowIndex, ruleExecutionResultFatal.getLowerBound());
                    }
                    if (ruleExecutionResultFatal.getUpperBound() != null) {
                        result.getFatalUpperBoundColumn().set(targetRowIndex, ruleExecutionResultFatal.getUpperBound());
                    }
                }

                if (errorRule != null) {
                    RuleExecutionRunParameters ruleRunParametersError = new RuleExecutionRunParameters(actualValue, expectedValueFromSensor,
                            errorRule, timePeriodLocal, previousDataPoints, ruleTimeWindowSettings);
                    RuleExecutionResult ruleExecutionResultError = this.ruleRunner.executeRule(executionContext, ruleRunParametersError, sensorRunParameters);

                    if (highestSeverity == null && !ruleExecutionResultError.isPassed()) {
                        highestSeverity = 2;
                    }

                    if (expectedValue == null && ruleExecutionResultError.getExpectedValue() != null) {
                        expectedValue = ruleExecutionResultError.getExpectedValue();
                    }

                    if (ruleExecutionResultError.getLowerBound() != null) {
                        result.getErrorLowerBoundColumn().set(targetRowIndex, ruleExecutionResultError.getLowerBound());
                    }
                    if (ruleExecutionResultError.getUpperBound() != null) {
                        result.getErrorUpperBoundColumn().set(targetRowIndex, ruleExecutionResultError.getUpperBound());
                    }
                }

                if (warningRule != null) {
                    RuleExecutionRunParameters ruleRunParametersWarning = new RuleExecutionRunParameters(actualValue, expectedValueFromSensor,
                            warningRule, timePeriodLocal, previousDataPoints, ruleTimeWindowSettings);
                    RuleExecutionResult ruleExecutionResultWarning = this.ruleRunner.executeRule(executionContext, ruleRunParametersWarning, sensorRunParameters);

                    if (highestSeverity == null && !ruleExecutionResultWarning.isPassed()) {
                        highestSeverity = 1;
                    }

                    if (expectedValue == null && ruleExecutionResultWarning.getExpectedValue() != null) {
                        expectedValue = ruleExecutionResultWarning.getExpectedValue();
                    }

                    if (ruleExecutionResultWarning.getLowerBound() != null) {
                        result.getWarningLowerBoundColumn().set(targetRowIndex, ruleExecutionResultWarning.getLowerBound());
                    }
                    if (ruleExecutionResultWarning.getUpperBound() != null) {
                        result.getWarningUpperBoundColumn().set(targetRowIndex, ruleExecutionResultWarning.getUpperBound());
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
}
