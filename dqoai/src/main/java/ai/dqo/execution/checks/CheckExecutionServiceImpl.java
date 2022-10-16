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
package ai.dqo.execution.checks;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.connectors.ConnectionProvider;
import ai.dqo.connectors.ConnectionProviderRegistry;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import ai.dqo.data.alerts.snapshot.RuleResultsSnapshot;
import ai.dqo.data.alerts.snapshot.RuleResultsSnapshotFactory;
import ai.dqo.data.readings.normalization.SensorNormalizedResult;
import ai.dqo.data.readings.normalization.SensorResultNormalizeService;
import ai.dqo.data.readings.snapshot.SensorReadingsSnapshot;
import ai.dqo.data.readings.snapshot.SensorReadingsSnapshotFactory;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.checks.progress.*;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationResult;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationService;
import ai.dqo.execution.checks.scheduled.ScheduledChecksCollection;
import ai.dqo.execution.checks.scheduled.ScheduledTableChecksCollection;
import ai.dqo.execution.checks.scheduled.ScheduledTargetChecksFindService;
import ai.dqo.execution.sensors.DataQualitySensorRunner;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersFactory;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Service that executes data quality checks.
 */
@Service
public class CheckExecutionServiceImpl implements CheckExecutionService {
    private final HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private final SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory;
    private final DataQualitySensorRunner dataQualitySensorRunner;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final SensorResultNormalizeService sensorResultNormalizeService;
    private final RuleEvaluationService ruleEvaluationService;
    private final SensorReadingsSnapshotFactory sensorReadingsSnapshotFactory;
    private final RuleResultsSnapshotFactory ruleResultsSnapshotFactory;
    private ScheduledTargetChecksFindService scheduledTargetChecksFindService;

    /**
     * Creates a data quality check execution service.
     * @param hierarchyNodeTreeSearcher Hierarchy node searcher.
     * @param sensorExecutionRunParametersFactory Sensor execution run parameters factory that expands parameters.
     * @param dataQualitySensorRunner Data quality sensor runner that executes sensors one by one.
     * @param connectionProviderRegistry Connection provider registry.
     * @param sensorResultNormalizeService Sensor dataset parse service.
     * @param ruleEvaluationService  Rule evaluation service.
     * @param sensorReadingsSnapshotFactory Sensor reading storage service.
     * @param ruleResultsSnapshotFactory Rule evaluation result (alerts) snapshot factory.
     * @param scheduledTargetChecksFindService Service that finds matching checks that are assigned to a given schedule.
     */
    @Autowired
    public CheckExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                     SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory,
                                     DataQualitySensorRunner dataQualitySensorRunner,
                                     ConnectionProviderRegistry connectionProviderRegistry,
                                     SensorResultNormalizeService sensorResultNormalizeService,
                                     RuleEvaluationService ruleEvaluationService,
                                     SensorReadingsSnapshotFactory sensorReadingsSnapshotFactory,
                                     RuleResultsSnapshotFactory ruleResultsSnapshotFactory,
                                     ScheduledTargetChecksFindService scheduledTargetChecksFindService) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.sensorExecutionRunParametersFactory = sensorExecutionRunParametersFactory;
        this.dataQualitySensorRunner = dataQualitySensorRunner;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.sensorResultNormalizeService = sensorResultNormalizeService;
        this.ruleEvaluationService = ruleEvaluationService;
        this.sensorReadingsSnapshotFactory = sensorReadingsSnapshotFactory;
        this.ruleResultsSnapshotFactory = ruleResultsSnapshotFactory;
        this.scheduledTargetChecksFindService = scheduledTargetChecksFindService;
    }

    /**
     * Executes data quality checks. Reports progress and saves the results.
     * @param checkExecutionContext Check execution context with access to the user home and dqo home.
     * @param checkSearchFilters Check search filters to find the right checks.
     * @param progressListener Progress listener that receives progress calls.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    public CheckExecutionSummary executeChecks(CheckExecutionContext checkExecutionContext,
											   CheckSearchFilters checkSearchFilters,
											   CheckExecutionProgressListener progressListener,
											   boolean dummySensorExecution) {
        UserHome userHome = checkExecutionContext.getUserHomeContext().getUserHome();
        Collection<TableWrapper> targetTables = listTargetTables(userHome, checkSearchFilters);
        CheckExecutionSummary checkExecutionSummary = new CheckExecutionSummary();

        for (TableWrapper targetTable :  targetTables) {
            // TODO: we can increase DOP here by turning each call (running sensors on a single table) into a multi step pipeline, we will start up to DOP pipelines, we will start new when a pipeline has finished...
            ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());
			executeChecksOnTable(checkExecutionContext, userHome, connectionWrapper, targetTable, checkSearchFilters, progressListener,
                    dummySensorExecution, checkExecutionSummary);
        }

        progressListener.onCheckExecutionFinished(new CheckExecutionFinishedEvent(checkExecutionSummary));

        return checkExecutionSummary;
    }

    /**
     * Executes scheduled data quality checks. A list of checks divided by tables must be provided.
     *
     * @param checkExecutionContext Check execution context with access to the user home and dqo home.
     * @param targetSchedule        Target schedule to match, when finding checks that should be executed.
     * @param progressListener      Progress listener that receives progress calls.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    @Override
    public CheckExecutionSummary executeChecksForSchedule(CheckExecutionContext checkExecutionContext,
                                               RunChecksSchedule targetSchedule,
                                               CheckExecutionProgressListener progressListener) {
        UserHome userHome = checkExecutionContext.getUserHomeContext().getUserHome();
        ScheduledChecksCollection checksForSchedule = this.scheduledTargetChecksFindService.findChecksForSchedule(userHome, targetSchedule);
        CheckExecutionSummary checkExecutionSummary = new CheckExecutionSummary();

        for(ScheduledTableChecksCollection scheduledChecksForTable : checksForSchedule.getTablesWithChecks()) {
            TableWrapper targetTable = scheduledChecksForTable.getTargetTable();
            ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());

            CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
            checkSearchFilters.setEnabled(true);
            checkSearchFilters.setCheckHierarchyIds(scheduledChecksForTable.getChecks());

            executeChecksOnTable(checkExecutionContext, userHome, connectionWrapper, targetTable, checkSearchFilters, progressListener,
                    false, checkExecutionSummary);
        }

        progressListener.onCheckExecutionFinished(new CheckExecutionFinishedEvent(checkExecutionSummary));

        return checkExecutionSummary;
    }

    /**
     * Execute checks on a single table.
     * @param checkExecutionContext Check execution context with access to the user home and dqo home.
     * @param userHome User home with all metadata and checks.
     * @param connectionWrapper  Target connection.
     * @param targetTable Target table.
     * @param checkSearchFilters Check search filters.
     * @param progressListener Progress listener.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param checkExecutionSummary Target object to gather the check execution summary information for the table.
     */
    public void executeChecksOnTable(CheckExecutionContext checkExecutionContext,
									 UserHome userHome,
									 ConnectionWrapper connectionWrapper,
									 TableWrapper targetTable,
									 CheckSearchFilters checkSearchFilters,
									 CheckExecutionProgressListener progressListener,
									 boolean dummySensorExecution,
									 CheckExecutionSummary checkExecutionSummary) {
        Collection<AbstractCheckSpec> checks = this.hierarchyNodeTreeSearcher.findChecks(targetTable, checkSearchFilters);
        if (checks.size() == 0) {
            checkExecutionSummary.reportTableStats(connectionWrapper, targetTable.getSpec(), 0, 0, 0, 0, 0, 0);
            return; // no checks for this table
        }

        TableSpec tableSpec = targetTable.getSpec();
        progressListener.onExecuteChecksOnTableStart(new ExecuteChecksOnTableStartEvent(connectionWrapper, tableSpec, checks));
        String connectionName = connectionWrapper.getName();
        PhysicalTableName physicalTableName = tableSpec.getTarget().toPhysicalTableName();
        SensorReadingsSnapshot sensorReadingsSnapshot = this.sensorReadingsSnapshotFactory.createSnapshot(connectionName, physicalTableName);
        RuleResultsSnapshot ruleResultsSnapshot = this.ruleResultsSnapshotFactory.createSnapshot(connectionName, physicalTableName);
        Table allNormalizedSensorResultsTable = sensorReadingsSnapshot.getNewResults();
        Table allRuleEvaluationResultsTable = ruleResultsSnapshot.getNewResults();
        int checksCount = 0;
        int sensorResultsCount = 0;
        int passedRules = 0;
        int lowSeverityAlerts = 0;
        int mediumSeverityAlerts = 0;
        int highSeverityAlerts = 0;

        for (AbstractCheckSpec checkSpec : checks) {
            List<AbstractRuleThresholdsSpec<?>> enabledRules = checkSpec.getRuleSet().getEnabledRules();
            if (enabledRules.size() == 0) {
                // no enabled rules, skipping the check

                // TODO: call the progressListener to show a message that a check was skipped because it has no enabled rules
                continue;
            }

            checksCount++;

            try {
                SensorExecutionRunParameters sensorRunParameters = prepareSensorRunParameters(userHome, checkSpec);
                progressListener.onExecutingSensor(new ExecutingSensorEvent(tableSpec, sensorRunParameters));

                SensorExecutionResult sensorResult = this.dataQualitySensorRunner.executeSensor(checkExecutionContext,
                        sensorRunParameters, progressListener, dummySensorExecution);
                progressListener.onSensorExecuted(new SensorExecutedEvent(tableSpec, sensorRunParameters, sensorResult));
                if (sensorResult.getResultTable().rowCount() == 0) {
                    continue; // no results captured, moving to the next sensor, probably an incremental time window too small
                }
                sensorResultsCount += sensorResult.getResultTable().rowCount();

                TimeSeriesConfigurationSpec effectiveTimeSeries = sensorRunParameters.getEffectiveTimeSeries();

                SensorNormalizedResult normalizedSensorResults = this.sensorResultNormalizeService.normalizeResults(
                        sensorResult, effectiveTimeSeries.getTimeGradient(), sensorRunParameters);
                progressListener.onSensorResultsNormalized(new SensorResultsNormalizedEvent(
                        tableSpec, sensorRunParameters, sensorResult, normalizedSensorResults));
                allNormalizedSensorResultsTable.append(normalizedSensorResults.getTable()); // TODO: move to the bottom, we will append an error...

                LocalDateTime maxTimePeriod = normalizedSensorResults.getTimePeriodColumn().max(); // most recent time period that was captured
                LocalDateTime minTimePeriod = normalizedSensorResults.getTimePeriodColumn().min(); // oldest time period tha was captured
                LocalDateTime earliestRequiredReading = checkSpec.findEarliestRequiredHistoricReadingDate(effectiveTimeSeries.getTimeGradient(), minTimePeriod);
                sensorReadingsSnapshot.ensureMonthsAreLoaded(earliestRequiredReading.toLocalDate(), maxTimePeriod.toLocalDate()); // preload required historic results

                RuleEvaluationResult ruleEvaluationResult = this.ruleEvaluationService.evaluateRules(
                        checkExecutionContext, checkSpec, sensorRunParameters, normalizedSensorResults, sensorReadingsSnapshot, progressListener);
                progressListener.onRulesExecuted(new RulesExecutedEvent(tableSpec, sensorRunParameters, normalizedSensorResults, ruleEvaluationResult));

                allRuleEvaluationResultsTable.append(ruleEvaluationResult.getRuleResultsTable());

                passedRules += ruleEvaluationResult.getSeverityColumn().isEqualTo(0).size();
                lowSeverityAlerts += ruleEvaluationResult.getSeverityColumn().isEqualTo(1).size();
                mediumSeverityAlerts += ruleEvaluationResult.getSeverityColumn().isEqualTo(2).size();
                highSeverityAlerts += ruleEvaluationResult.getSeverityColumn().isEqualTo(3).size();
            }
            catch (Exception ex) {
                // TODO: append a special error row instead of appending the reading row...

                throw new RuntimeException("Check failed to execute", ex);
            }

            // TODO: we can consider flushing results here if we run out of memory (too many results)
        }

        progressListener.onSavingSensorResults(new SavingSensorResultsEvent(tableSpec, sensorReadingsSnapshot));
        if (sensorReadingsSnapshot.hasNewReadings() && !dummySensorExecution) {
            sensorReadingsSnapshot.save();
        }

        progressListener.onSavingRuleEvaluationResults(new SavingRuleEvaluationResults(tableSpec, ruleResultsSnapshot));
        if (ruleResultsSnapshot.hasNewAlerts() && !dummySensorExecution) {
            ruleResultsSnapshot.save();
        }
        progressListener.onTableChecksProcessingFinished(new TableChecksProcessingFinished(connectionWrapper, tableSpec, checks,
                checksCount, sensorResultsCount, passedRules, lowSeverityAlerts, mediumSeverityAlerts, highSeverityAlerts));

        checkExecutionSummary.reportTableStats(connectionWrapper, tableSpec, checksCount, sensorResultsCount,
                passedRules, lowSeverityAlerts, mediumSeverityAlerts, highSeverityAlerts);
    }

    /**
     * Lists all target tables that were not excluded from the filter and may have checks to be executed.
     * @param userHome User home.
     * @param checkSearchFilters Check search filter.
     * @return Collection of table wrappers.
     */
    public Collection<TableWrapper> listTargetTables(UserHome userHome, CheckSearchFilters checkSearchFilters) {
        Collection<TableWrapper> tables = this.hierarchyNodeTreeSearcher.findTables(userHome.getConnections(), checkSearchFilters);
        return tables;
    }

    /**
     * Creates a sensor run parameters from the check specification. Retrieves the connection, table, column and sensor parameters.
     * @param userHome User home with the metadata.
     * @param checkSpec Check specification.
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters prepareSensorRunParameters(UserHome userHome, AbstractCheckSpec checkSpec) {
        HierarchyId checkHierarchyId = checkSpec.getHierarchyId();
        ConnectionWrapper connectionWrapper = userHome.findConnectionFor(checkHierarchyId);
        TableWrapper tableWrapper = userHome.findTableFor(checkHierarchyId);
        ColumnSpec columnSpec = userHome.findColumnFor(checkHierarchyId); // may be null
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings dialectSettings = connectionProvider.getDialectSettings(connectionSpec);
        TableSpec tableSpec = tableWrapper.getSpec();

        SensorExecutionRunParameters sensorRunParameters = this.sensorExecutionRunParametersFactory.createSensorParameters(
                connectionSpec, tableSpec, columnSpec, checkSpec, dialectSettings);
        return sensorRunParameters;
    }
}
