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
import ai.dqo.execution.checks.progress.*;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationResult;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationService;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResult;
import ai.dqo.execution.rules.finder.RuleDefinitionFindService;
import ai.dqo.execution.sensors.*;
import ai.dqo.execution.sensors.grouping.GroupedSensorExecutionResult;
import ai.dqo.execution.sensors.grouping.GroupedSensorsCollection;
import ai.dqo.execution.sensors.grouping.PreparedSensorsGroup;
import ai.dqo.execution.sensors.progress.ExecutingSensorEvent;
import ai.dqo.execution.sensors.progress.PreparingSensorEvent;
import ai.dqo.execution.sensors.progress.SensorExecutedEvent;
import ai.dqo.execution.sensors.progress.SensorFailedEvent;
import ai.dqo.metadata.definitions.checks.CheckDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.timeseries.TimePeriodGradient;
import ai.dqo.metadata.timeseries.TimeSeriesConfigurationProvider;
import ai.dqo.metadata.timeseries.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.timeseries.TimeSeriesMode;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.id.HierarchyNode;
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
import tech.tablesaw.api.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service that executes data quality checks on a single table.
 */
@Service
@Slf4j
public class TableCheckExecutionServiceImpl implements TableCheckExecutionService {
    private final HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private final SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory;
    private final DataQualitySensorRunner dataQualitySensorRunner;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final SensorReadoutsNormalizationService sensorReadoutsNormalizationService;
    private final RuleEvaluationService ruleEvaluationService;
    private final SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory;
    private final CheckResultsSnapshotFactory checkResultsSnapshotFactory;
    private final ErrorsNormalizationService errorsNormalizationService;
    private final ErrorsSnapshotFactory errorsSnapshotFactory;
    private final RuleDefinitionFindService ruleDefinitionFindService;
    private final IncidentImportQueueService incidentImportQueueService;

    /**
     * Creates a data quality check execution service.
     * @param hierarchyNodeTreeSearcher Hierarchy node searcher.
     * @param sensorExecutionRunParametersFactory Sensor execution run parameters factory that expands parameters.
     * @param dataQualitySensorRunner Data quality sensor runner that executes sensors one by one.
     * @param connectionProviderRegistry Connection provider registry.
     * @param sensorReadoutsNormalizationService Sensor dataset parse service.
     * @param ruleEvaluationService  Rule evaluation service.
     * @param sensorReadoutsSnapshotFactory Sensor readouts storage service.
     * @param checkResultsSnapshotFactory Rule evaluation result (alerts) snapshot factory.
     * @param errorsNormalizationService Error normalization service - creates datasets with the error information.
     * @param errorsSnapshotFactory Error snapshot factory, provides read and write support for errors stored in tabular format.
     * @param ruleDefinitionFindService Rule definition find service - used to find the rule definitions and get their configured time windows.
     * @param incidentImportQueueService New incident import queue service. Identifies new incidents and sends notifications.
     */
    @Autowired
    public TableCheckExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                          SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory,
                                          DataQualitySensorRunner dataQualitySensorRunner,
                                          ConnectionProviderRegistry connectionProviderRegistry,
                                          SensorReadoutsNormalizationService sensorReadoutsNormalizationService,
                                          RuleEvaluationService ruleEvaluationService,
                                          SensorReadoutsSnapshotFactory sensorReadoutsSnapshotFactory,
                                          CheckResultsSnapshotFactory checkResultsSnapshotFactory,
                                          ErrorsNormalizationService errorsNormalizationService,
                                          ErrorsSnapshotFactory errorsSnapshotFactory,
                                          RuleDefinitionFindService ruleDefinitionFindService,
                                          IncidentImportQueueService incidentImportQueueService) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.sensorExecutionRunParametersFactory = sensorExecutionRunParametersFactory;
        this.dataQualitySensorRunner = dataQualitySensorRunner;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.sensorReadoutsNormalizationService = sensorReadoutsNormalizationService;
        this.ruleEvaluationService = ruleEvaluationService;
        this.sensorReadoutsSnapshotFactory = sensorReadoutsSnapshotFactory;
        this.checkResultsSnapshotFactory = checkResultsSnapshotFactory;
        this.errorsNormalizationService = errorsNormalizationService;
        this.errorsSnapshotFactory = errorsSnapshotFactory;
        this.ruleDefinitionFindService = ruleDefinitionFindService;
        this.incidentImportQueueService = incidentImportQueueService;
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
     * @param jobCancellationToken Job cancellation token.
     * @return Check execution summary that has an overview information about executed checks.
     */
    @Override
    public CheckExecutionSummary executeChecksOnTable(ExecutionContext executionContext,
                                                      UserHome userHome,
                                                      ConnectionWrapper connectionWrapper,
                                                      TableWrapper targetTable,
                                                      CheckSearchFilters checkSearchFilters,
                                                      TimeWindowFilterParameters userTimeWindowFilters,
                                                      CheckExecutionProgressListener progressListener,
                                                      boolean dummySensorExecution,
                                                      JobCancellationToken jobCancellationToken) {
        jobCancellationToken.throwIfCancelled();
        CheckExecutionSummary checkExecutionSummary = new CheckExecutionSummary();

        Collection<AbstractCheckSpec<?, ?, ?, ?>> checks = this.hierarchyNodeTreeSearcher.findChecks(targetTable, checkSearchFilters);
        if (checks.size() == 0) {
            return checkExecutionSummary; // no checks for this table
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

        TableChecksExecutionStatistics executionStatistics = new TableChecksExecutionStatistics();

        List<SensorPrepareResult> allPreparedSensors = this.prepareSensors(checks, executionContext, userHome, userTimeWindowFilters, progressListener,
                allErrorsTable, checkExecutionSummary, executionStatistics, jobCancellationToken);

        GroupedSensorsCollection groupedSensorsCollection = new GroupedSensorsCollection();
        groupedSensorsCollection.addAllPreparedSensors(allPreparedSensors);

        List<SensorExecutionResult> sensorExecutionResults = this.executeSensors(groupedSensorsCollection, executionContext, progressListener, allErrorsTable,
                checkExecutionSummary, executionStatistics, dummySensorExecution, jobCancellationToken);

        for (SensorExecutionResult sensorExecutionResult : sensorExecutionResults) {
            if (jobCancellationToken.isCancelled()) {
                break;
            }

            try {
                SensorExecutionRunParameters sensorRunParameters = sensorExecutionResult.getSensorRunParameters();

                SensorReadoutsNormalizedResult normalizedSensorResults = this.sensorReadoutsNormalizationService.normalizeResults(
                        sensorExecutionResult, sensorRunParameters);
                progressListener.onSensorResultsNormalized(new SensorResultsNormalizedEvent(
                        tableSpec, sensorRunParameters, sensorExecutionResult, normalizedSensorResults));
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
                                executionContext, sensorExecutionResult.getSensorRunParameters().getCheck(), sensorRunParameters,
                                normalizedSensorResults, sensorReadoutsSnapshot, progressListener);
                        progressListener.onRuleExecuted(new RuleExecutedEvent(tableSpec, sensorRunParameters, normalizedSensorResults, ruleEvaluationResult));

                        allRuleEvaluationResultsTable.append(ruleEvaluationResult.getRuleResultsTable());
                        executionStatistics.addRuleEvaluationResults(ruleEvaluationResult);
                    }
                    catch (Throwable ex) {
                        log.error("Rule " + ruleDefinitionName + " failed to execute: " + ex.getMessage(), ex);
                        executionStatistics.incrementRuleExecutionErrorsCount(1);
                        ErrorsNormalizedResult normalizedRuleErrorResults = this.errorsNormalizationService.createNormalizedRuleErrorResults(
                                sensorExecutionResult, sensorRunParameters, ex);
                        allErrorsTable.append(normalizedRuleErrorResults.getTable());
                        progressListener.onRuleFailed(new RuleFailedEvent(tableSpec, sensorRunParameters, sensorExecutionResult, ex, ruleDefinitionName));
                        checkExecutionSummary.updateCheckExecutionErrorSummary(new CheckExecutionErrorSummary(ex, sensorRunParameters.getCheckSearchFilter()));
                    }
                }
            }
            catch (DqoQueueJobCancelledException cex) {
                // ignore the error, just stop running checks
                break;
            }
            catch (Throwable ex) {
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

        progressListener.onTableChecksProcessingFinished(new TableChecksProcessingFinishedEvent(
                connectionWrapper, tableSpec, checks, executionStatistics));

        checkExecutionSummary.reportTableStats(connectionWrapper, tableSpec, executionStatistics);

        if (this.incidentImportQueueService != null && executionStatistics.hasAnyFailedRules()) {
            TableIncidentImportBatch tableIncidentImportBatch = new TableIncidentImportBatch(
                    checkResultsSnapshot.getTableDataChanges().getNewOrChangedRows(),
                    connectionWrapper.getSpec(),
                    tableSpec);
            this.incidentImportQueueService.importTableIncidents(tableIncidentImportBatch);
        }

        return checkExecutionSummary;
    }

    /**
     * Prepares all sensors for execution.
     * @param checks Collection of checks that will be prepared.
     * @param executionContext Execution context - to access sensor definitions.
     * @param userHome User home.
     * @param userTimeWindowFilters Optional user provided time window filters.
     * @param progressListener Progress listener - to report progress.
     * @param allErrorsTable Target table where errors are added when parsing fails.
     * @param checkExecutionSummary Check execution summary where results are added.
     * @param executionStatistics Execution statistics - counts of checks and errors.
     * @param jobCancellationToken Job cancellation token - to cancel the preparation by the user.
     * @return List of prepared sensors.
     */
    public List<SensorPrepareResult> prepareSensors(Collection<AbstractCheckSpec<?, ?, ?, ?>> checks,
                                                    ExecutionContext executionContext,
                                                    UserHome userHome,
                                                    TimeWindowFilterParameters userTimeWindowFilters,
                                                    CheckExecutionProgressListener progressListener,
                                                    Table allErrorsTable,
                                                    CheckExecutionSummary checkExecutionSummary,
                                                    TableChecksExecutionStatistics executionStatistics,
                                                    JobCancellationToken jobCancellationToken) {
        List<SensorPrepareResult> sensorPrepareResults = new ArrayList<>();
        int sensorResultId = 0;

        for (AbstractCheckSpec<?, ?, ?, ?> checkSpec : checks) {
            if (jobCancellationToken.isCancelled()) {
                break;
            }

            executionStatistics.incrementExecutedChecksCount(1);

            try {
                SensorExecutionRunParameters sensorRunParameters = createSensorRunParameters(userHome, checkSpec, userTimeWindowFilters);
                sensorResultId++;
                sensorRunParameters.setActualValueAlias("dqo_actual_value_" + sensorResultId);
                sensorRunParameters.setExpectedValueAlias("dqo_expected_value_" + sensorResultId);

                if (sensorRunParameters.getTimeSeries().getMode() == TimeSeriesMode.timestamp_column &&
                        Strings.isNullOrEmpty(sensorRunParameters.getTimeSeries().getTimestampColumn())) {
                    // timestamp column not configured, date/time partitioned data quality checks cannot be evaluated
                    CheckExecutionFailedException missingTimestampException = new CheckExecutionFailedException("Data quality check " + checkSpec.getHierarchyId().toString() +
                            " cannot be executed because the timestamp column is not configured for date/time partitioned data quality checks. " +
                            "Configure the name of the columns in the \"timestamp_columns\" node on the table level (.dqotable.yaml file).");

                    executionStatistics.incrementSensorExecutionErrorsCount(1);
                    SensorExecutionResult sensorExecutionResultWithError = new SensorExecutionResult(sensorRunParameters, missingTimestampException);
                    ErrorsNormalizedResult normalizedSensorErrorResults = this.errorsNormalizationService.createNormalizedSensorErrorResults(
                            sensorExecutionResultWithError, sensorRunParameters);
                    allErrorsTable.append(normalizedSensorErrorResults.getTable());
                    progressListener.onSensorFailed(new SensorFailedEvent(sensorRunParameters.getTable(), sensorRunParameters, sensorExecutionResultWithError, missingTimestampException));
                    checkExecutionSummary.updateCheckExecutionErrorSummary(new CheckExecutionErrorSummary(missingTimestampException, sensorRunParameters.getCheckSearchFilter()));
                    continue;
                }

                jobCancellationToken.throwIfCancelled();
                progressListener.onPreparingSensor(new PreparingSensorEvent(sensorRunParameters.getTable(), sensorRunParameters));
                SensorPrepareResult sensorPrepareResult = this.dataQualitySensorRunner.prepareSensor(executionContext, sensorRunParameters, progressListener);

                if (!sensorPrepareResult.isSuccess()) {
                    executionStatistics.incrementSensorExecutionErrorsCount(1);
                    SensorExecutionResult sensorExecutionResultFailedPrepare = new SensorExecutionResult(sensorRunParameters, sensorPrepareResult.getPrepareException());
                    ErrorsNormalizedResult normalizedSensorErrorResults = this.errorsNormalizationService.createNormalizedSensorErrorResults(
                            sensorExecutionResultFailedPrepare, sensorRunParameters);
                    allErrorsTable.append(normalizedSensorErrorResults.getTable());
                    progressListener.onSensorFailed(new SensorFailedEvent(sensorRunParameters.getTable(), sensorRunParameters,
                            sensorExecutionResultFailedPrepare, sensorPrepareResult.getPrepareException()));
                    checkExecutionSummary.updateCheckExecutionErrorSummary(new CheckExecutionErrorSummary(
                            sensorPrepareResult.getPrepareException(), sensorRunParameters.getCheckSearchFilter()));
                    continue;
                }

                sensorPrepareResults.add(sensorPrepareResult);
            }
            catch (DqoQueueJobCancelledException cex) {
                // ignore the error, just stop running checks
                break;
            }
            catch (Throwable ex) {
                log.error("Check runner failed to run checks: " + ex.getMessage(), ex);
                throw new CheckExecutionFailedException("Checks on table failed to execute", ex);
            }
        }

        return sensorPrepareResults;
    }

    /**
     * Executes prepared sensors.
     * @param groupedSensorsCollection Collection of sensors grouped by executors and similar queries that could be merged together.
     * @param executionContext Execution context - to access sensor definitions.
     * @param progressListener Progress listener - to report progress.
     * @param allErrorsTable Target table where errors are added when parsing fails.
     * @param checkExecutionSummary Check execution summary where results are added.
     * @param executionStatistics Execution statistics - counts of checks and errors.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token - to cancel the preparation by the user.
     * @return List of sensor execution results.
     */
    public List<SensorExecutionResult> executeSensors(GroupedSensorsCollection groupedSensorsCollection,
                                                      ExecutionContext executionContext,
                                                      CheckExecutionProgressListener progressListener,
                                                      Table allErrorsTable,
                                                      CheckExecutionSummary checkExecutionSummary,
                                                      TableChecksExecutionStatistics executionStatistics,
                                                      boolean dummySensorExecution,
                                                      JobCancellationToken jobCancellationToken) {
        List<SensorExecutionResult> sensorExecuteResults = new ArrayList<>();
        Collection<PreparedSensorsGroup> preparedSensorGroups = groupedSensorsCollection.getPreparedSensorGroups();

        for (PreparedSensorsGroup preparedSensorsGroup : preparedSensorGroups) {
            if (jobCancellationToken.isCancelled()) {
                break;
            }

            try {
                SensorPrepareResult firstSensorPrepareResult = preparedSensorsGroup.getFirstSensorPrepareResult();
                SensorExecutionRunParameters firstSensorRunParameters = firstSensorPrepareResult.getSensorRunParameters();
                TableSpec tableSpec = firstSensorRunParameters.getTable();

                progressListener.onExecutingSensor(new ExecutingSensorEvent(tableSpec, firstSensorPrepareResult));
                List<GroupedSensorExecutionResult> groupedSensorExecutionResults = this.dataQualitySensorRunner.executeGroupedSensors(executionContext,
                        preparedSensorsGroup, progressListener, dummySensorExecution, jobCancellationToken);

                for (GroupedSensorExecutionResult groupedSensorExecutionResult : groupedSensorExecutionResults) {
                    PreparedSensorsGroup sensorGroup = groupedSensorExecutionResult.getSensorGroup();
                    List<SensorPrepareResult> preparedSensorsInGroup = sensorGroup.getPreparedSensors();

                    for (SensorPrepareResult sensorPrepareResult : preparedSensorsInGroup) {
                        SensorExecutionRunParameters sensorRunParameters = sensorPrepareResult.getSensorRunParameters();
                        SensorExecutionResult sensorExecuteResult = sensorPrepareResult.getSensorRunner().extractSensorResults(
                                executionContext, groupedSensorExecutionResult,
                                sensorPrepareResult, progressListener, jobCancellationToken);

                        if (!sensorExecuteResult.isSuccess()) {
                            executionStatistics.incrementSensorExecutionErrorsCount(1);
                            ErrorsNormalizedResult normalizedSensorErrorResults = this.errorsNormalizationService.createNormalizedSensorErrorResults(
                                    sensorExecuteResult, sensorRunParameters);
                            allErrorsTable.append(normalizedSensorErrorResults.getTable());
                            progressListener.onSensorFailed(new SensorFailedEvent(tableSpec, sensorRunParameters,
                                    sensorExecuteResult, sensorExecuteResult.getException()));
                            checkExecutionSummary.updateCheckExecutionErrorSummary(new CheckExecutionErrorSummary(
                                    sensorExecuteResult.getException(), sensorRunParameters.getCheckSearchFilter()));
                            continue;
                        }

                        progressListener.onSensorExecuted(new SensorExecutedEvent(tableSpec, sensorRunParameters, sensorExecuteResult));
                        if (sensorExecuteResult.getResultTable().rowCount() == 0) {
                            continue; // no results captured, moving to the next sensor, probably an incremental time window too small or no data in the table
                        }
                        executionStatistics.incrementSensorReadoutsCount(sensorExecuteResult.getResultTable().rowCount());
                        sensorExecuteResults.add(sensorExecuteResult);
                    }
                }
            }
            catch (DqoQueueJobCancelledException cex) {
                // ignore the error, just stop running checks
                break;
            }
            catch (Throwable ex) {
                log.error("Check runner failed to run checks: " + ex.getMessage(), ex);
                throw new CheckExecutionFailedException("Checks on table failed to execute", ex);
            }
        }

        return sensorExecuteResults;
    }

    /**
     * Creates a sensor run parameters from the check specification. Retrieves the connection, table, column and sensor parameters.
     * @param userHome User home with the metadata.
     * @param checkSpec Check specification.
     * @param userTimeWindowFilters Optional user time window filters, to run the checks for a given time period.
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters createSensorRunParameters(UserHome userHome,
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
