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
import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.custom.CustomCheckSpec;
import ai.dqo.connectors.ConnectionProvider;
import ai.dqo.connectors.ConnectionProviderRegistry;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.core.incidents.IncidentImportQueueService;
import ai.dqo.core.incidents.TableIncidentImportBatch;
import ai.dqo.core.jobqueue.*;
import ai.dqo.core.jobqueue.exceptions.DqoQueueJobCancelledException;
import ai.dqo.data.checkresults.snapshot.CheckResultsSnapshot;
import ai.dqo.data.checkresults.snapshot.CheckResultsSnapshotFactory;
import ai.dqo.data.errors.normalization.ErrorsNormalizationService;
import ai.dqo.data.errors.normalization.ErrorsNormalizedResult;
import ai.dqo.data.errors.snapshot.ErrorsSnapshot;
import ai.dqo.data.errors.snapshot.ErrorsSnapshotFactory;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizationService;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshot;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshotFactory;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.checks.jobs.RunChecksOnTableQueueJob;
import ai.dqo.execution.checks.jobs.RunChecksOnTableQueueJobParameters;
import ai.dqo.execution.checks.progress.*;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationResult;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationService;
import ai.dqo.execution.checks.scheduled.ScheduledChecksCollection;
import ai.dqo.execution.checks.scheduled.ScheduledTableChecksCollection;
import ai.dqo.execution.checks.scheduled.ScheduledTargetChecksFindService;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResult;
import ai.dqo.execution.rules.finder.RuleDefinitionFindService;
import ai.dqo.execution.sensors.*;
import ai.dqo.execution.sensors.progress.ExecutingSensorEvent;
import ai.dqo.execution.sensors.progress.PreparingSensorEvent;
import ai.dqo.execution.sensors.progress.SensorExecutedEvent;
import ai.dqo.execution.sensors.progress.SensorFailedEvent;
import ai.dqo.metadata.definitions.checks.CheckDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationProvider;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.groupings.TimePeriodGradient;
import ai.dqo.metadata.groupings.TimeSeriesMode;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rules.HistoricDataPointsGrouping;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.utils.datetime.LocalDateTimePeriodUtility;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tech.tablesaw.api.Table;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service that executes data quality checks.
 */
@Service
@Slf4j
public class CheckExecutionServiceImpl implements CheckExecutionService {
    private final HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private final SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory;
    private final DataQualitySensorRunner dataQualitySensorRunner;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;
    private final SensorReadoutsNormalizationService sensorReadoutsNormalizationService;
    private final RuleEvaluationService ruleEvaluationService;
    private final SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory;
    private final CheckResultsSnapshotFactory checkResultsSnapshotFactory;
    private ErrorsNormalizationService errorsNormalizationService;
    private ErrorsSnapshotFactory errorsSnapshotFactory;
    private final ScheduledTargetChecksFindService scheduledTargetChecksFindService;
    private final RuleDefinitionFindService ruleDefinitionFindService;
    private IncidentImportQueueService incidentImportQueueService;

    /**
     * Creates a data quality check execution service.
     * @param hierarchyNodeTreeSearcher Hierarchy node searcher.
     * @param sensorExecutionRunParametersFactory Sensor execution run parameters factory that expands parameters.
     * @param dataQualitySensorRunner Data quality sensor runner that executes sensors one by one.
     * @param connectionProviderRegistry Connection provider registry.
     * @param dqoQueueJobFactory DQO job factory, used to create child jobs that will run checks per table.
     * @param dqoJobQueue DQO job queue where child jobs (that run checks per table) are scheduled.
     * @param sensorReadoutsNormalizationService Sensor dataset parse service.
     * @param ruleEvaluationService  Rule evaluation service.
     * @param sensorReadoutsSnapshotFactory Sensor readouts storage service.
     * @param checkResultsSnapshotFactory Rule evaluation result (alerts) snapshot factory.
     * @param errorsNormalizationService Error normalization service - creates datasets with the error information.
     * @param errorsSnapshotFactory Error snapshot factory, provides read and write support for errors stored in tabular format.
     * @param scheduledTargetChecksFindService Service that finds matching checks that are assigned to a given schedule.
     * @param ruleDefinitionFindService Rule definition find service - used to find the rule definitions and get their configured time windows.
     * @param incidentImportQueueService New incident import queue service. Identifies new incidents and sends notifications.
     */
    @Autowired
    public CheckExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                     SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory,
                                     DataQualitySensorRunner dataQualitySensorRunner,
                                     ConnectionProviderRegistry connectionProviderRegistry,
                                     DqoQueueJobFactory dqoQueueJobFactory,
                                     DqoJobQueue dqoJobQueue,
                                     SensorReadoutsNormalizationService sensorReadoutsNormalizationService,
                                     RuleEvaluationService ruleEvaluationService,
                                     SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory,
                                     CheckResultsSnapshotFactory checkResultsSnapshotFactory,
                                     ErrorsNormalizationService errorsNormalizationService,
                                     ErrorsSnapshotFactory errorsSnapshotFactory,
                                     ScheduledTargetChecksFindService scheduledTargetChecksFindService,
                                     RuleDefinitionFindService ruleDefinitionFindService,
                                     IncidentImportQueueService incidentImportQueueService) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.sensorExecutionRunParametersFactory = sensorExecutionRunParametersFactory;
        this.dataQualitySensorRunner = dataQualitySensorRunner;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.sensorReadoutsNormalizationService = sensorReadoutsNormalizationService;
        this.ruleEvaluationService = ruleEvaluationService;
        this.sensorReadoutsSnapshotFactory = sensorReadoutsSnapshotFactory;
        this.checkResultsSnapshotFactory = checkResultsSnapshotFactory;
        this.errorsNormalizationService = errorsNormalizationService;
        this.errorsSnapshotFactory = errorsSnapshotFactory;
        this.scheduledTargetChecksFindService = scheduledTargetChecksFindService;
        this.ruleDefinitionFindService = ruleDefinitionFindService;
        this.incidentImportQueueService = incidentImportQueueService;
    }

    /**
     * Executes data quality checks. Reports progress and saves the results.
     * @param executionContext Check execution context with access to the user home and dqo home.
     * @param checkSearchFilters Check search filters to find the right checks.
     * @param userTimeWindowFilters Optional user provided time window filters to restrict the range of dates that are analyzed.
     * @param progressListener Progress listener that receives progress calls.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param startChildJobsPerTable True - starts parallel jobs per table, false - runs all checks without starting additional jobs.
     * @param parentJobId Parent job id for the parent job.
     * @param jobCancellationToken Job cancellation token.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    @Override
    public CheckExecutionSummary executeChecks(ExecutionContext executionContext,
                                               CheckSearchFilters checkSearchFilters,
                                               TimeWindowFilterParameters userTimeWindowFilters,
                                               CheckExecutionProgressListener progressListener,
                                               boolean dummySensorExecution,
                                               boolean startChildJobsPerTable,
                                               DqoQueueJobId parentJobId,
                                               JobCancellationToken jobCancellationToken) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        Collection<TableWrapper> targetTables = listTargetTables(userHome, checkSearchFilters);
        CheckExecutionSummary checkExecutionSummary = new CheckExecutionSummary();
        jobCancellationToken.throwIfCancelled();

        if (startChildJobsPerTable) {
            List<DqoQueueJob<CheckExecutionSummary>> childTableJobs = new ArrayList<>();

            for (TableWrapper targetTable : targetTables) {
                ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());

                RunChecksOnTableQueueJobParameters runChecksOnTableQueueJobParameters = new RunChecksOnTableQueueJobParameters() {{
                   setConnection(connectionWrapper.getName());
                   setMaxJobsPerConnection(connectionWrapper.getSpec().getParallelRunsLimit());
                   setTable(targetTable.getPhysicalTableName());
                   setCheckSearchFilters(checkSearchFilters);
                   setTimeWindowFilter(userTimeWindowFilters);
                   setProgressListener(progressListener);
                   setDummyExecution(dummySensorExecution);
                }};
                RunChecksOnTableQueueJob runChecksOnTableJob = this.dqoQueueJobFactory.createRunChecksOnTableJob();
                runChecksOnTableJob.setParameters(runChecksOnTableQueueJobParameters);
                childTableJobs.add(runChecksOnTableJob);
            }

            ChildDqoQueueJobsContainer<CheckExecutionSummary> childTableJobsContainer = this.dqoJobQueue.pushChildJobs(childTableJobs, parentJobId);
            List<CheckExecutionSummary> checkExecutionSummaries = childTableJobsContainer.waitForChildResults(jobCancellationToken);
            checkExecutionSummaries.forEach(checkExecutionSummary::append);
        }
        else {
            for (TableWrapper targetTable : targetTables) {
                ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());
                executeChecksOnTable(executionContext, userHome, connectionWrapper, targetTable, checkSearchFilters, userTimeWindowFilters, progressListener,
                        dummySensorExecution, checkExecutionSummary, jobCancellationToken);
            }
        }

        progressListener.onCheckExecutionFinished(new CheckExecutionFinishedEvent(checkExecutionSummary));

        return checkExecutionSummary;
    }

    /**
     * Executes scheduled data quality checks. A list of checks divided by tables must be provided.
     *
     * @param executionContext Check execution context with access to the user home and dqo home.
     * @param targetSchedule        Target schedule to match, when finding checks that should be executed.
     * @param progressListener      Progress listener that receives progress calls.
     * @param parentJobId           Parent job id.
     * @param jobCancellationToken  Job cancellation token.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    @Override
    public CheckExecutionSummary executeChecksForSchedule(ExecutionContext executionContext,
                                                          RecurringScheduleSpec targetSchedule,
                                                          CheckExecutionProgressListener progressListener,
                                                          DqoQueueJobId parentJobId,
                                                          JobCancellationToken jobCancellationToken) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        ScheduledChecksCollection checksForSchedule = this.scheduledTargetChecksFindService.findChecksForSchedule(userHome, targetSchedule);
        CheckExecutionSummary checkExecutionSummary = new CheckExecutionSummary();
        List<DqoQueueJob<CheckExecutionSummary>> childTableJobs = new ArrayList<>();

        for(ScheduledTableChecksCollection scheduledChecksForTable : checksForSchedule.getTablesWithChecks()) {
            TableWrapper targetTable = scheduledChecksForTable.getTargetTable();
            ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());

            CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
            checkSearchFilters.setEnabled(true);
            checkSearchFilters.setConnectionName(connectionWrapper.getName());
            checkSearchFilters.setSchemaTableName(targetTable.getPhysicalTableName().toTableSearchFilter());
            checkSearchFilters.setCheckHierarchyIds(scheduledChecksForTable.getChecks());

            RunChecksOnTableQueueJobParameters runChecksOnTableQueueJobParameters = new RunChecksOnTableQueueJobParameters() {{
                setConnection(connectionWrapper.getName());
                setMaxJobsPerConnection(connectionWrapper.getSpec().getParallelRunsLimit());
                setTable(targetTable.getPhysicalTableName());
                setCheckSearchFilters(checkSearchFilters);
                setTimeWindowFilter(null);
                setProgressListener(progressListener);
            }};
            RunChecksOnTableQueueJob runChecksOnTableJob = this.dqoQueueJobFactory.createRunChecksOnTableJob();
            runChecksOnTableJob.setParameters(runChecksOnTableQueueJobParameters);
            childTableJobs.add(runChecksOnTableJob);
        }

        ChildDqoQueueJobsContainer<CheckExecutionSummary> childTableJobsContainer = this.dqoJobQueue.pushChildJobs(childTableJobs, parentJobId);
        List<CheckExecutionSummary> checkExecutionSummaries = childTableJobsContainer.waitForChildResults(jobCancellationToken);
        checkExecutionSummaries.forEach(checkExecutionSummary::append);

        progressListener.onCheckExecutionFinished(new CheckExecutionFinishedEvent(checkExecutionSummary));

        return checkExecutionSummary;
    }

    /**
     * Executes selected checks on a table. This method is called from {@link ai.dqo.execution.checks.jobs.RunChecksOnTableQueueJob}
     * that is a child job which runs checks for each table in parallel.
     * @param executionContext Execution context that provides access to the user home.
     * @param connectionName Connection name on which the checks are executed.
     * @param targetTable Full name of the target table.
     * @param checkSearchFilters Check search filters, may not specify the connection and the table name.
     * @param userTimeWindowFilters Optional user provided time window filters to restrict the range of dates that are analyzed.
     * @param progressListener Progress listener that receives progress calls.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token.
     * @return Check summary table with the count of alerts, checks and rules for each table, but having only one row for the target table. The result could be empty if the table was not found.
     */
    @Override
    public CheckExecutionSummary executeSelectedChecksOnTable(ExecutionContext executionContext,
                                                              String connectionName,
                                                              PhysicalTableName targetTable,
                                                              CheckSearchFilters checkSearchFilters,
                                                              TimeWindowFilterParameters userTimeWindowFilters,
                                                              CheckExecutionProgressListener progressListener,
                                                              boolean dummySensorExecution,
                                                              JobCancellationToken jobCancellationToken) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        CheckExecutionSummary checkExecutionSummary = new CheckExecutionSummary();

        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return checkExecutionSummary; // the connection was probably deleted in the meantime
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(targetTable, true);
        if (tableWrapper == null) {
            return checkExecutionSummary; // the table was probably deleted in the meantime
        }

        executeChecksOnTable(executionContext, userHome, connectionWrapper, tableWrapper, checkSearchFilters, userTimeWindowFilters, progressListener,
                dummySensorExecution, checkExecutionSummary, jobCancellationToken);

        return checkExecutionSummary;
    }

    /**
     * Execute checks on a single table.
     * @param executionContext Check execution context with access to the user home and dqo home.
     * @param userHome User home with all metadata and checks.
     * @param connectionWrapper  Target connection.
     * @param targetTable Target table.
     * @param checkSearchFilters Check search filters.
     * @param userTimeWindowFilters Optional user provided time window filters to restrict the range of dates that are analyzed.
     * @param progressListener Progress listener.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param checkExecutionSummary Target object to gather the check execution summary information for the table.
     * @param jobCancellationToken Job cancellation token.
     */
    public void executeChecksOnTable(ExecutionContext executionContext,
                                     UserHome userHome,
                                     ConnectionWrapper connectionWrapper,
                                     TableWrapper targetTable,
                                     CheckSearchFilters checkSearchFilters,
                                     TimeWindowFilterParameters userTimeWindowFilters,
                                     CheckExecutionProgressListener progressListener,
                                     boolean dummySensorExecution,
                                     CheckExecutionSummary checkExecutionSummary,
                                     JobCancellationToken jobCancellationToken) {
        jobCancellationToken.throwIfCancelled();

        Collection<AbstractCheckSpec<?, ?, ?, ?>> checks = this.hierarchyNodeTreeSearcher.findChecks(targetTable, checkSearchFilters);
        if (checks.size() == 0) {
            // checkExecutionSummary.reportTableStats(connectionWrapper, targetTable.getSpec(), 0, 0, 0, 0, 0, 0, 0);
            return; // no checks for this table
        }
        jobCancellationToken.throwIfCancelled();

        TableSpec tableSpec = targetTable.getSpec();
        progressListener.onExecuteChecksOnTableStart(new ExecuteChecksOnTableStartEvent(connectionWrapper, tableSpec, checks));
        String connectionName = connectionWrapper.getName();
        PhysicalTableName physicalTableName = tableSpec.getPhysicalTableName();

        SensorReadoutsSnapshot sensorReadoutsSnapshot = this.sensorReadoutsSnapshotFactory.createSnapshot(connectionName, physicalTableName);
        Table allNormalizedSensorResultsTable = sensorReadoutsSnapshot.getTableDataChanges().getNewOrChangedRows();
        jobCancellationToken.throwIfCancelled();

        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createSnapshot(connectionName, physicalTableName);
        Table allRuleEvaluationResultsTable = checkResultsSnapshot.getTableDataChanges().getNewOrChangedRows();
        jobCancellationToken.throwIfCancelled();

        ErrorsSnapshot errorsSnapshot = this.errorsSnapshotFactory.createSnapshot(connectionName, physicalTableName);
        Table allErrorsTable = errorsSnapshot.getTableDataChanges().getNewOrChangedRows();
        jobCancellationToken.throwIfCancelled();

        int checksCount = 0;
        int sensorResultsCount = 0;
        int passedRules = 0;
        int warningIssuesCount = 0;
        int errorIssuesCount = 0;
        int fatalIssuesCount = 0;
        int erroredSensors = 0;
        int erroredRules = 0;

        for (AbstractCheckSpec<?, ?, ?, ?> checkSpec : checks) {
            if (jobCancellationToken.isCancelled()) {
                break;
            }

            checksCount++;

            try {
                SensorExecutionRunParameters sensorRunParameters = prepareSensorRunParameters(userHome, checkSpec, userTimeWindowFilters);
                TimeSeriesConfigurationSpec effectiveTimeSeries = sensorRunParameters.getTimeSeries();
                TimePeriodGradient timeGradient = effectiveTimeSeries.getTimeGradient();

                CheckSearchFilters exactCheckSearchFilters = checkSearchFilters.clone();
                exactCheckSearchFilters.setConnectionName(connectionName);
                exactCheckSearchFilters.setSchemaTableName(physicalTableName.toTableSearchFilter());
                exactCheckSearchFilters.setColumnName(sensorRunParameters.getColumn() == null ? null : sensorRunParameters.getColumn().getColumnName());
                exactCheckSearchFilters.setCheckCategory(checkSpec.getCategoryName());
                exactCheckSearchFilters.setCheckName(checkSpec.getCheckName());
                exactCheckSearchFilters.setSensorName(sensorRunParameters.getEffectiveSensorRuleNames().getSensorName());

                if (sensorRunParameters.getTimeSeries().getMode() == TimeSeriesMode.timestamp_column &&
                        Strings.isNullOrEmpty(sensorRunParameters.getTimeSeries().getTimestampColumn())) {
                    // timestamp column not configured, date/time partitioned data quality checks cannot be evaluated
                    CheckExecutionFailedException missingTimestampException = new CheckExecutionFailedException("Data quality check " + checkSpec.getHierarchyId().toString() +
                            " cannot be executed because the timestamp column is not configured for date/time partitioned data quality checks. " +
                            "Configure the name of the columns in the \"timestamp_columns\" node on the table level (.dqotable.yaml file).");

                    erroredSensors++;
                    SensorExecutionResult sensorExecutionResultWithError = new SensorExecutionResult(sensorRunParameters, missingTimestampException);
                    ErrorsNormalizedResult normalizedSensorErrorResults = this.errorsNormalizationService.createNormalizedSensorErrorResults(
                            sensorExecutionResultWithError, timeGradient, sensorRunParameters);
                    allErrorsTable.append(normalizedSensorErrorResults.getTable());
                    progressListener.onSensorFailed(new SensorFailedEvent(tableSpec, sensorRunParameters, sensorExecutionResultWithError, missingTimestampException));
                    checkExecutionSummary.updateCheckExecutionErrorSummary(new CheckExecutionErrorSummary(missingTimestampException, exactCheckSearchFilters));
                    continue;
                }

                jobCancellationToken.throwIfCancelled();
                progressListener.onPreparingSensor(new PreparingSensorEvent(tableSpec, sensorRunParameters));
                SensorPrepareResult sensorPrepareResult = this.dataQualitySensorRunner.prepareSensor(executionContext, sensorRunParameters, progressListener);

                jobCancellationToken.throwIfCancelled();
                progressListener.onExecutingSensor(new ExecutingSensorEvent(tableSpec, sensorPrepareResult));
                SensorExecutionResult sensorResult = this.dataQualitySensorRunner.executeSensor(executionContext,
                        sensorPrepareResult, progressListener, dummySensorExecution, jobCancellationToken);
                jobCancellationToken.throwIfCancelled();

                if (!sensorResult.isSuccess()) {
                    erroredSensors++;
                    ErrorsNormalizedResult normalizedSensorErrorResults = this.errorsNormalizationService.createNormalizedSensorErrorResults(
                            sensorResult, timeGradient, sensorRunParameters);
                    allErrorsTable.append(normalizedSensorErrorResults.getTable());
                    progressListener.onSensorFailed(new SensorFailedEvent(tableSpec, sensorRunParameters, sensorResult, sensorResult.getException()));
                    checkExecutionSummary.updateCheckExecutionErrorSummary(new CheckExecutionErrorSummary(sensorResult.getException(), exactCheckSearchFilters));
                    continue;
                }

                progressListener.onSensorExecuted(new SensorExecutedEvent(tableSpec, sensorRunParameters, sensorResult));
                if (sensorResult.getResultTable().rowCount() == 0) {
                    continue; // no results captured, moving to the next sensor, probably an incremental time window too small or no data in the table
                }
                sensorResultsCount += sensorResult.getResultTable().rowCount();

                SensorReadoutsNormalizedResult normalizedSensorResults = this.sensorReadoutsNormalizationService.normalizeResults(
                        sensorResult, timeGradient, sensorRunParameters);
                progressListener.onSensorResultsNormalized(new SensorResultsNormalizedEvent(
                        tableSpec, sensorRunParameters, sensorResult, normalizedSensorResults));
                allNormalizedSensorResultsTable.append(normalizedSensorResults.getTable());

                LocalDateTime maxTimePeriod = normalizedSensorResults.getTimePeriodColumn().max(); // most recent time period that was captured
                LocalDateTime minTimePeriod = normalizedSensorResults.getTimePeriodColumn().min(); // oldest time period that was captured

                String ruleDefinitionName = sensorRunParameters.getEffectiveSensorRuleNames().getRuleName();

                if (ruleDefinitionName == null) {
                    // no rule to run, just the sensor...
                    sensorReadoutsSnapshot.ensureMonthsAreLoaded(minTimePeriod.toLocalDate(), maxTimePeriod.toLocalDate()); // preload required historic results for merging
                }
                else {
                    RuleDefinitionFindResult ruleDefinitionFindResult = this.ruleDefinitionFindService.findRule(executionContext, ruleDefinitionName);
                    RuleDefinitionSpec ruleDefinitionSpec = ruleDefinitionFindResult.getRuleDefinitionSpec();
                    RuleTimeWindowSettingsSpec ruleTimeWindowSettings = ruleDefinitionSpec.getTimeWindow();
                    TimePeriodGradient timeGradientForRuleScope = ruleTimeWindowSettings != null ?
                            (ruleTimeWindowSettings.getHistoricDataPointGrouping() != HistoricDataPointsGrouping.last_n_readouts ?
                                    ruleTimeWindowSettings.getHistoricDataPointGrouping().toTimePeriodGradient() : TimePeriodGradient.day) : TimePeriodGradient.day;

                    LocalDateTime earliestRequiredReadout = ruleTimeWindowSettings == null ? minTimePeriod :
                            LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(
                                    minTimePeriod, ruleTimeWindowSettings.getPredictionTimeWindow(), timeGradientForRuleScope);

                    sensorReadoutsSnapshot.ensureMonthsAreLoaded(earliestRequiredReadout.toLocalDate(), maxTimePeriod.toLocalDate()); // preload required historic sensor readouts
                    checkResultsSnapshot.ensureMonthsAreLoaded(earliestRequiredReadout.toLocalDate(), maxTimePeriod.toLocalDate()); // will be used for notifications

                    try {
                        RuleEvaluationResult ruleEvaluationResult = this.ruleEvaluationService.evaluateRules(
                                executionContext, checkSpec, sensorRunParameters, normalizedSensorResults, sensorReadoutsSnapshot, progressListener);
                        progressListener.onRuleExecuted(new RuleExecutedEvent(tableSpec, sensorRunParameters, normalizedSensorResults, ruleEvaluationResult));

                        allRuleEvaluationResultsTable.append(ruleEvaluationResult.getRuleResultsTable());

                        passedRules += ruleEvaluationResult.getSeverityColumn().isLessThanOrEqualTo(1).size();  // passed checks are severity 0 (passed) and 1 (warnings)
                        warningIssuesCount += ruleEvaluationResult.getSeverityColumn().isEqualTo(1).size();
                        errorIssuesCount += ruleEvaluationResult.getSeverityColumn().isEqualTo(2).size();
                        fatalIssuesCount += ruleEvaluationResult.getSeverityColumn().isEqualTo(3).size();
                    }
                    catch (Exception ex) {
                        log.error("Rule " + ruleDefinitionName + " failed to execute: " + ex.getMessage(), ex);
                        erroredRules++;
                        ErrorsNormalizedResult normalizedRuleErrorResults = this.errorsNormalizationService.createNormalizedRuleErrorResults(
                                sensorResult, timeGradient, sensorRunParameters, ex);
                        allErrorsTable.append(normalizedRuleErrorResults.getTable());
                        progressListener.onRuleFailed(new RuleFailedEvent(tableSpec, sensorRunParameters, sensorResult, ex, ruleDefinitionName));
                        checkExecutionSummary.updateCheckExecutionErrorSummary(new CheckExecutionErrorSummary(ex, exactCheckSearchFilters));
                    }
                }
            }
            catch (DqoQueueJobCancelledException cex) {
                // ignore the error, just stop running checks
                break;
            }
            catch (Exception ex) {
                log.error("Check runner failed to run checks: " + ex.getMessage(), ex);
                throw new CheckExecutionFailedException("Checks on table failed to execute", ex);
            }
        }

        if (sensorReadoutsSnapshot.getTableDataChanges().hasChanges() && !dummySensorExecution) {
            progressListener.onSavingSensorResults(new SavingSensorResultsEvent(tableSpec, sensorReadoutsSnapshot));
            sensorReadoutsSnapshot.save();
        }

        if (checkResultsSnapshot.getTableDataChanges().hasChanges() && !dummySensorExecution) {
            progressListener.onSavingRuleEvaluationResults(new SavingRuleEvaluationResultsEvent(tableSpec, checkResultsSnapshot));
            checkResultsSnapshot.save();
        }

        if (errorsSnapshot.getTableDataChanges().hasChanges() && !dummySensorExecution) {
            progressListener.onSavingErrors(new SavingErrorsEvent(tableSpec, errorsSnapshot));
            errorsSnapshot.save();
        }

        progressListener.onTableChecksProcessingFinished(new TableChecksProcessingFinishedEvent(connectionWrapper, tableSpec, checks,
                checksCount, sensorResultsCount, passedRules, warningIssuesCount, errorIssuesCount, fatalIssuesCount,
                erroredSensors, erroredRules));

        checkExecutionSummary.reportTableStats(connectionWrapper, tableSpec, checksCount, sensorResultsCount,
                passedRules, warningIssuesCount, errorIssuesCount, fatalIssuesCount, erroredSensors + erroredRules);

        if (this.incidentImportQueueService != null && (warningIssuesCount > 0 || errorIssuesCount > 0 || fatalIssuesCount > 0)) {
            TableIncidentImportBatch tableIncidentImportBatch = new TableIncidentImportBatch(
                    checkResultsSnapshot.getTableDataChanges().getNewOrChangedRows(),
                    connectionWrapper.getSpec(),
                    tableSpec);
            this.incidentImportQueueService.importTableIncidents(tableIncidentImportBatch);
        }
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
     * @param userTimeWindowFilters Optional user time window filters, to run the checks for a given time period.
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters prepareSensorRunParameters(UserHome userHome,
                                                                   AbstractCheckSpec<?,?,?,?> checkSpec,
                                                                   TimeWindowFilterParameters userTimeWindowFilters) {
        HierarchyId checkHierarchyId = checkSpec.getHierarchyId();
        ConnectionWrapper connectionWrapper = userHome.findConnectionFor(checkHierarchyId);
        TableWrapper tableWrapper = userHome.findTableFor(checkHierarchyId);
        ColumnSpec columnSpec = userHome.findColumnFor(checkHierarchyId); // may be null
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings dialectSettings = connectionProvider.getDialectSettings(connectionSpec);
        TableSpec tableSpec = tableWrapper.getSpec();

        List<HierarchyNode> nodesOnPath = List.of(checkHierarchyId.getNodesOnPath(userHome));
        Optional<HierarchyNode> timeSeriesProvider = Lists.reverse(nodesOnPath)
                .stream()
                .filter(n -> n instanceof TimeSeriesConfigurationProvider)
                .findFirst();
        assert timeSeriesProvider.isPresent();

        TimeSeriesConfigurationProvider timeSeriesConfigurationProvider = (TimeSeriesConfigurationProvider) timeSeriesProvider.get();
        TimeSeriesConfigurationSpec timeSeriesConfigurationSpec = timeSeriesConfigurationProvider.getTimeSeriesConfiguration(tableSpec);

        Optional<HierarchyNode> checkCategoryRootProvider = Lists.reverse(nodesOnPath)
                .stream()
                .filter(n -> n instanceof AbstractRootChecksContainerSpec)
                .findFirst();
        assert checkCategoryRootProvider.isPresent();
        AbstractRootChecksContainerSpec rootChecksContainerSpec = (AbstractRootChecksContainerSpec)checkCategoryRootProvider.get();
        CheckType checkType = rootChecksContainerSpec.getCheckType();
        CheckDefinitionSpec customCheckDefinitionSpec = null;

        if (checkSpec instanceof CustomCheckSpec) {
            CustomCheckSpec customCheckSpec = (CustomCheckSpec)checkSpec;
            customCheckDefinitionSpec = userHome.getChecks().getCheckDefinitionSpec(
                    rootChecksContainerSpec.getCheckTarget(),checkType,
                    rootChecksContainerSpec.getCheckTimeScale(), customCheckSpec.getCheckName());
        }

        SensorExecutionRunParameters sensorRunParameters = this.sensorExecutionRunParametersFactory.createSensorParameters(
                connectionSpec, tableSpec, columnSpec, checkSpec, customCheckDefinitionSpec, checkType,
                timeSeriesConfigurationSpec, userTimeWindowFilters, dialectSettings);
        return sensorRunParameters;
    }
}
