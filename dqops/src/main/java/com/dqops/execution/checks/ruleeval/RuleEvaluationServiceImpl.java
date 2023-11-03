/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.execution.checks.ruleeval;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.data.readouts.snapshot.SensorReadoutsTimeSeriesData;
import com.dqops.data.readouts.snapshot.SensorReadoutsTimeSeriesMap;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.rules.*;
import com.dqops.execution.rules.finder.RuleDefinitionFindResult;
import com.dqops.execution.rules.finder.RuleDefinitionFindService;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.metadata.incidents.EffectiveIncidentGroupingConfiguration;
import com.dqops.metadata.incidents.TableIncidentGroupingSpec;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.HistoricDataPointsGrouping;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.utils.tables.TableColumnUtility;
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
        TableSliceGroup dimensionTimeSeriesSlices = sensorResultsTable.splitOn(normalizedSensorResults.getDataGroupHashColumn());
        SensorReadoutsTimeSeriesMap historicReadoutsTimeSeries = sensorReadoutsSnapshot.getHistoricReadoutsTimeSeries();
        long checkHashId = checkSpec.getHierarchyId().hashCode64();

        DoubleColumn actualValueColumn = normalizedSensorResults.getActualValueColumn();
        DoubleColumn expectedValueColumn = normalizedSensorResults.getExpectedValueColumn();
        DateTimeColumn timePeriodColumn = normalizedSensorResults.getTimePeriodColumn();
        RuleEvaluationResult result = RuleEvaluationResult.makeEmptyFromSensorResults(normalizedSensorResults);

        String ruleDefinitionName = sensorRunParameters.getEffectiveSensorRuleNames() != null ?
                sensorRunParameters.getEffectiveSensorRuleNames().getRuleName() : null;
        RuleDefinitionFindResult ruleFindResult = this.ruleDefinitionFindService.findRule(executionContext, ruleDefinitionName);
        RuleTimeWindowSettingsSpec ruleTimeWindowSettings = ruleFindResult != null && ruleFindResult.getRuleDefinitionSpec() != null ?
                ruleFindResult.getRuleDefinitionSpec().getTimeWindow() : null;
        TableComparisonConfigurationSpec tableComparisonConfiguration = sensorRunParameters.getTableComparisonConfiguration();

        for (TableSlice dimensionTableSlice : dimensionTimeSeriesSlices) {
            Table dimensionSensorResults = dimensionTableSlice.asTable();  // results for a single dimension, the rows should be already sorted by the time period, ascending
            LongColumn dimensionColumn = (LongColumn) TableColumnUtility.findColumn(dimensionSensorResults,
                    SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME, "data_stream_hash");
            Long timeSeriesDimensionId = dimensionColumn.get(0);
            SensorReadoutsTimeSeriesData historicTimeSeriesData = historicReadoutsTimeSeries.findTimeSeriesData(checkHashId, timeSeriesDimensionId);
            HistoricDataPointsGrouping historicDataPointGrouping = ruleTimeWindowSettings != null ? ruleTimeWindowSettings.getHistoricDataPointGrouping() : null;
            TimePeriodGradient timeGradient = historicDataPointGrouping != null ? historicDataPointGrouping.toTimePeriodGradient() : TimePeriodGradient.day;
            if (timeGradient == null) {
                timeGradient = TimePeriodGradient.day; // timeGradient could be null for rules that require a continuous array of any results, we will use days as a fallback to define the time window
            }

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

                if (historicDataPointGrouping == HistoricDataPointsGrouping.last_n_readouts) {
                    // these checks do not have real time periods, we just take the last data points, also we don't want the current sensor results
                    // because there should be none (only partitioned checks will have previous results from the most recent query), we will find them only in old data

                    if (ruleTimeWindowSettings != null && previousDataPointTimeSeriesCollectorOld != null) {
                        previousDataPoints = previousDataPointTimeSeriesCollectorOld.getHistoricContinuousResultsBefore(
                                timePeriodLocal, ruleTimeWindowSettings.getPredictionTimeWindow());
                    }

                    if (previousDataPoints == null) {
                        previousDataPoints = new HistoricDataPoint[ruleTimeWindowSettings != null ? ruleTimeWindowSettings.getPredictionTimeWindow() : 0];
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
                    } else {
                        previousDataPoints = new HistoricDataPoint[ruleTimeWindowSettings != null ? ruleTimeWindowSettings.getPredictionTimeWindow() : 0];
                    }
                }

                Integer highestSeverity = null;
                AbstractRuleParametersSpec fatalRule = checkSpec.getFatal();
                AbstractRuleParametersSpec errorRule = checkSpec.getError();
                AbstractRuleParametersSpec warningRule = checkSpec.getWarning();
                Double expectedValue = null;
                Double newActualValue = null;

                RuleExecutionResult ruleExecutionResultFatal = null;
                RuleExecutionResult ruleExecutionResultError = null;
                RuleExecutionResult ruleExecutionResultWarning = null;

                if (fatalRule != null) {
                    RuleExecutionRunParameters ruleRunParametersFatal = new RuleExecutionRunParameters(actualValue, expectedValueFromSensor,
                            fatalRule, timePeriodLocal, previousDataPoints, ruleTimeWindowSettings);
                    ruleExecutionResultFatal = this.ruleRunner.executeRule(executionContext, ruleRunParametersFatal, sensorRunParameters);

                    if (ruleExecutionResultFatal.getPassed() != null && !ruleExecutionResultFatal.getPassed()) {
                        highestSeverity = 3;
                    }

                    if (ruleExecutionResultFatal.getExpectedValue() != null) {
                        expectedValue = ruleExecutionResultFatal.getExpectedValue();
                    }

                    if (ruleExecutionResultFatal.getNewActualValue() != null) {
                        newActualValue = ruleExecutionResultFatal.getNewActualValue();
                    }
                }

                if (errorRule != null) {
                    RuleExecutionRunParameters ruleRunParametersError = new RuleExecutionRunParameters(actualValue, expectedValueFromSensor,
                            errorRule, timePeriodLocal, previousDataPoints, ruleTimeWindowSettings);
                    ruleExecutionResultError = this.ruleRunner.executeRule(executionContext, ruleRunParametersError, sensorRunParameters);

                    if (ruleExecutionResultError.getPassed() != null && highestSeverity == null && !ruleExecutionResultError.getPassed()) {
                        highestSeverity = 2;
                    }

                    if (ruleExecutionResultError.getExpectedValue() != null) {
                        expectedValue = ruleExecutionResultError.getExpectedValue();
                    }

                    if (newActualValue == null && ruleExecutionResultError.getNewActualValue() != null) {
                        newActualValue = ruleExecutionResultError.getNewActualValue();
                    }
                }

                if (warningRule != null) {
                    RuleExecutionRunParameters ruleRunParametersWarning = new RuleExecutionRunParameters(actualValue, expectedValueFromSensor,
                            warningRule, timePeriodLocal, previousDataPoints, ruleTimeWindowSettings);
                    ruleExecutionResultWarning = this.ruleRunner.executeRule(executionContext, ruleRunParametersWarning, sensorRunParameters);

                    if (ruleExecutionResultWarning.getPassed() != null && highestSeverity == null && !ruleExecutionResultWarning.getPassed()) {
                        highestSeverity = 1;
                    }

                    if (ruleExecutionResultWarning.getExpectedValue() != null) {
                        expectedValue = ruleExecutionResultWarning.getExpectedValue();
                    }

                    if (newActualValue == null && ruleExecutionResultWarning.getNewActualValue() != null) {
                        newActualValue = ruleExecutionResultWarning.getNewActualValue();
                    }
                }

                boolean hasRuleResult;
                if (warningRule == null && errorRule == null && fatalRule == null) {
                    // no rules are enabled, we are appending the check result as passed, but excluding it from KPI
                    hasRuleResult = false;
                } else {
                    hasRuleResult = true;
                    if ((ruleExecutionResultFatal == null || ruleExecutionResultFatal.getPassed() == null) &&
                            (ruleExecutionResultError == null || ruleExecutionResultError.getPassed() == null) &&
                            (ruleExecutionResultWarning == null || ruleExecutionResultWarning.getPassed() == null)) {
                        // no rule managed to evaluate, the result is inconclusive, no rule returned any result,
                        // probably not enough historical data to calculate, we are not adding a check result row
                        continue;
                    }
                }

                if (highestSeverity == null) {
                    // some rules evaluated, not no rule raised an error, setting the passing severity
                    highestSeverity = 0; // pass
                }

                Row targetRuleResultRow = result.appendRow();
                int targetRowIndex = targetRuleResultRow.getRowNumber();
                result.copyRowFrom(targetRowIndex, sensorResultsTable, allSensorResultsRowIndex);
                result.getIncludeInKpiColumn().set(targetRowIndex, hasRuleResult && !checkSpec.isExcludeFromKpi());
                result.getIncludeInSlaColumn().set(targetRowIndex, hasRuleResult && checkSpec.isIncludeInSla());

                if (tableComparisonConfiguration != null) {
                    result.getReferenceConnectionColumn().set(targetRowIndex, tableComparisonConfiguration.getReferenceTableConnectionName());
                    result.getReferenceSchemaColumn().set(targetRowIndex, tableComparisonConfiguration.getReferenceTableSchemaName());
                    result.getReferenceTableColumn().set(targetRowIndex, tableComparisonConfiguration.getReferenceTableName());
                    result.getReferenceColumnColumn().set(targetRowIndex, sensorRunParameters.getReferenceColumnName());
                }

                if (ruleExecutionResultFatal != null && ruleExecutionResultFatal.getLowerBound() != null) {
                    result.getFatalLowerBoundColumn().set(targetRowIndex, ruleExecutionResultFatal.getLowerBound());
                }
                if (ruleExecutionResultFatal != null && ruleExecutionResultFatal.getUpperBound() != null) {
                    result.getFatalUpperBoundColumn().set(targetRowIndex, ruleExecutionResultFatal.getUpperBound());
                }

                if (ruleExecutionResultError != null && ruleExecutionResultError.getLowerBound() != null) {
                    result.getErrorLowerBoundColumn().set(targetRowIndex, ruleExecutionResultError.getLowerBound());
                }
                if (ruleExecutionResultError != null && ruleExecutionResultError.getUpperBound() != null) {
                    result.getErrorUpperBoundColumn().set(targetRowIndex, ruleExecutionResultError.getUpperBound());
                }

                if (ruleExecutionResultWarning != null && ruleExecutionResultWarning.getLowerBound() != null) {
                    result.getWarningLowerBoundColumn().set(targetRowIndex, ruleExecutionResultWarning.getLowerBound());
                }
                if (ruleExecutionResultWarning != null && ruleExecutionResultWarning.getUpperBound() != null) {
                    result.getWarningUpperBoundColumn().set(targetRowIndex, ruleExecutionResultWarning.getUpperBound());
                }

                ConnectionIncidentGroupingSpec connectionIncidentGrouping = sensorRunParameters.getConnection().getIncidentGrouping();
                TableIncidentGroupingSpec tableIncidentGrouping = sensorRunParameters.getTable().getIncidentGrouping();
                EffectiveIncidentGroupingConfiguration effectiveIncidentGrouping = new EffectiveIncidentGroupingConfiguration(connectionIncidentGrouping, tableIncidentGrouping);

                if (!effectiveIncidentGrouping.isDisabled() && highestSeverity >= effectiveIncidentGrouping.getMinimumSeverity().getSeverityLevel()) {
                    String dataStreamName = !normalizedSensorResults.getDataGroupNameColumn().isMissing(allSensorResultsRowIndex) ?
                            normalizedSensorResults.getDataGroupNameColumn().get(allSensorResultsRowIndex) : null;
                    String qualityDimension = !normalizedSensorResults.getQualityDimensionColumn().isMissing(allSensorResultsRowIndex) ?
                            normalizedSensorResults.getQualityDimensionColumn().get(allSensorResultsRowIndex) : null;
                    String checkCategory = !normalizedSensorResults.getCheckCategoryColumn().isMissing(allSensorResultsRowIndex) ?
                            normalizedSensorResults.getCheckCategoryColumn().get(allSensorResultsRowIndex) : null;
                    String checkType = !normalizedSensorResults.getCheckTypeColumn().isMissing(allSensorResultsRowIndex) ?
                            normalizedSensorResults.getCheckTypeColumn().get(allSensorResultsRowIndex) : null;
                    String checkName = checkSpec.getCheckName();
                    long incidentHash = effectiveIncidentGrouping.calculateIncidentHash(sensorRunParameters.getConnection().getConnectionName(),
                            sensorRunParameters.getTable().getPhysicalTableName(),
                            dataStreamName, qualityDimension, checkCategory, checkType, checkName);
                    result.getIncidentHashColumn().set(targetRowIndex, incidentHash);
                }

                result.getSeverityColumn().set(targetRowIndex, highestSeverity);
                result.getExpectedValueColumn().set(targetRowIndex, expectedValue);

                if (newActualValue != null) {
                    result.getActualValueColumn().set(targetRowIndex, newActualValue);
                }
            }
        }

        return result;
    }
}
