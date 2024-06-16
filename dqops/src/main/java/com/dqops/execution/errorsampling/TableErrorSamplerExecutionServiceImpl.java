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
package com.dqops.execution.errorsampling;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckType;
import com.dqops.checks.custom.CustomCheckSpec;
import com.dqops.checks.defaults.DefaultObservabilityConfigurationService;
import com.dqops.connectors.*;
import com.dqops.core.configuration.DqoSensorLimitsConfigurationProperties;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.jobqueue.exceptions.DqoQueueJobCancelledException;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.data.errorsamples.normalization.ErrorSamplesNormalizationService;
import com.dqops.data.errorsamples.normalization.ErrorSamplesNormalizedResult;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshot;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshotFactory;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.CheckExecutionFailedException;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.errorsampling.progress.ExecuteErrorSamplerOnTableFinishedEvent;
import com.dqops.execution.errorsampling.progress.ExecuteErrorSamplerOnTableStartEvent;
import com.dqops.execution.errorsampling.progress.SavingErrorSamplesResultsEvent;
import com.dqops.execution.sensors.*;
import com.dqops.execution.sensors.grouping.GroupedSensorExecutionResult;
import com.dqops.execution.sensors.grouping.GroupedSensorsCollection;
import com.dqops.execution.sensors.grouping.PreparedSensorsGroup;
import com.dqops.execution.sensors.progress.ExecutingSensorEvent;
import com.dqops.execution.sensors.progress.PreparingSensorEvent;
import com.dqops.execution.sensors.progress.SensorExecutedEvent;
import com.dqops.execution.sensors.progress.SensorFailedEvent;
import com.dqops.metadata.definitions.checks.CheckDefinitionList;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.dqops.utils.logging.UserErrorLogger;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Error sampler execution service that collects error samples on a single table.
 */
@Service
@Slf4j
public class TableErrorSamplerExecutionServiceImpl implements TableErrorSamplerExecutionService {
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory;
    private DataQualitySensorRunner dataQualitySensorRunner;
    private ConnectionProviderRegistry connectionProviderRegistry;
    private ErrorSamplesNormalizationService errorSamplesNormalizationService;
    private ErrorSamplesSnapshotFactory errorSamplesSnapshotFactory;
    private DqoSensorLimitsConfigurationProperties dqoSensorLimitsConfigurationProperties;
    private final UserErrorLogger userErrorLogger;
    private final DefaultObservabilityConfigurationService defaultObservabilityConfigurationService;

    /**
     * Creates an error sampling collectors execution service with given dependencies.
     * @param hierarchyNodeTreeSearcher Target node search service.
     * @param sensorExecutionRunParametersFactory Sensor run parameters factory.
     * @param dataQualitySensorRunner Sensor runner.
     * @param connectionProviderRegistry Connection provider.
     * @param errorSamplesNormalizationService Normalization service that creates error samples results.
     * @param errorSamplesSnapshotFactory Error samples results snapshot factory. Snapshots support storage of error samples.
     * @param dqoSensorLimitsConfigurationProperties DQOps sensor limits configuration.
     * @param userErrorLogger Execution logger.
     * @param defaultObservabilityConfigurationService Default check patterns configuration service.
     */
    @Autowired
    public TableErrorSamplerExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                                 SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory,
                                                 DataQualitySensorRunner dataQualitySensorRunner,
                                                 ConnectionProviderRegistry connectionProviderRegistry,
                                                 ErrorSamplesNormalizationService errorSamplesNormalizationService,
                                                 ErrorSamplesSnapshotFactory errorSamplesSnapshotFactory,
                                                 DqoSensorLimitsConfigurationProperties dqoSensorLimitsConfigurationProperties,
                                                 UserErrorLogger userErrorLogger,
                                                 DefaultObservabilityConfigurationService defaultObservabilityConfigurationService) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.sensorExecutionRunParametersFactory = sensorExecutionRunParametersFactory;
        this.dataQualitySensorRunner = dataQualitySensorRunner;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.errorSamplesNormalizationService = errorSamplesNormalizationService;
        this.errorSamplesSnapshotFactory = errorSamplesSnapshotFactory;
        this.dqoSensorLimitsConfigurationProperties = dqoSensorLimitsConfigurationProperties;
        this.userErrorLogger = userErrorLogger;
        this.defaultObservabilityConfigurationService = defaultObservabilityConfigurationService;
    }

    /**
     * Execute error samplers on a single table.
     * @param executionContext Execution context with access to the user home and dqo home.
     * @param userHome User home with all metadata and checks.
     * @param connectionWrapper  Target connection.
     * @param targetTable Target table.
     * @param checkSearchFilters Check search filters for which we are collecting error samples.
     * @param userTimeWindowFilters Optional user provided time filter to limit the samples time range.
     * @param progressListener Progress listener.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param errorSamplingSessionStartAt Timestamp when the error sampling collection session started. All collected error samples results will be saved with the same timestamp.
     * @param errorSamplesDataScope Error sampler scope to analyze - the whole table or each data stream separately.
     * @param jobCancellationToken Job cancellation token, used to detect if the job should be cancelled.
     * @return Table level error sampling summary.
     */
    @Override
    public ErrorSamplerExecutionSummary captureErrorSamplesOnTable(ExecutionContext executionContext,
                                                                   UserHome userHome,
                                                                   ConnectionWrapper connectionWrapper,
                                                                   TableWrapper targetTable,
                                                                   CheckSearchFilters checkSearchFilters,
                                                                   TimeWindowFilterParameters userTimeWindowFilters,
                                                                   ErrorSamplerExecutionProgressListener progressListener,
                                                                   boolean dummySensorExecution,
                                                                   LocalDateTime errorSamplingSessionStartAt,
                                                                   ErrorSamplesDataScope errorSamplesDataScope,
                                                                   JobCancellationToken jobCancellationToken) {
        ErrorSamplerExecutionSummary errorSamplerExecutionSummary = new ErrorSamplerExecutionSummary();
        ErrorSamplerExecutionStatistics executionStatistics = new ErrorSamplerExecutionStatistics();

        TableSpec originalTableSpec = targetTable.getSpec();
        TableSpec tableSpec = originalTableSpec.deepClone();
        this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableAndColumns(connectionWrapper.getSpec(), tableSpec, userHome); // expand checks configured by check patterns

        Collection<AbstractCheckSpec<?,?,?,?>> checks = this.hierarchyNodeTreeSearcher.findChecks(tableSpec, checkSearchFilters);
        if (checks.size() == 0) {
            errorSamplerExecutionSummary.reportTableStats(connectionWrapper, tableSpec, executionStatistics);
            return errorSamplerExecutionSummary; // no checks for this table
        }
        jobCancellationToken.throwIfCancelled();

        progressListener.onTableErrorSamplersStart(new ExecuteErrorSamplerOnTableStartEvent(connectionWrapper, tableSpec, checks));
        String connectionName = connectionWrapper.getName();
        PhysicalTableName physicalTableName = tableSpec.getPhysicalTableName();
        UserDomainIdentity userDomainIdentity = userHome.getUserIdentity();
        ErrorSamplesSnapshot errorSamplesSnapshot = this.errorSamplesSnapshotFactory.createSnapshot(connectionName, physicalTableName, userDomainIdentity);
        Table allNormalizedStatisticsTable = errorSamplesSnapshot.getTableDataChanges().getNewOrChangedRows();

        Map<String, Integer> successfulCollectorsPerColumn = new LinkedHashMap<>();

        List<SensorPrepareResult> allPreparedSensors = this.prepareSensors(checks, executionContext, userHome, userTimeWindowFilters, progressListener,
                executionStatistics, errorSamplesDataScope, jobCancellationToken);

        GroupedSensorsCollection groupedSensorsCollection = new GroupedSensorsCollection(1); // no merging, just find the same sensors
        groupedSensorsCollection.addAllPreparedSensors(allPreparedSensors);

        List<SensorExecutionResult> sensorExecutionResults = this.executeSensors(groupedSensorsCollection, executionContext, progressListener,
                executionStatistics, dummySensorExecution, jobCancellationToken);

        for (SensorExecutionResult sensorExecutionResult : sensorExecutionResults) {
            jobCancellationToken.throwIfCancelled();
            AbstractCheckSpec<?,?,?,?> checkSpec = sensorExecutionResult.getSensorRunParameters().getCheck();

            try {
                SensorExecutionRunParameters sensorRunParameters = sensorExecutionResult.getSensorRunParameters();

                if (sensorRunParameters.getColumn() != null) {
                    String columnName = sensorRunParameters.getColumn().getColumnName();
                    if (successfulCollectorsPerColumn.containsKey(columnName)) {
                        if (sensorExecutionResult.isSuccess()) {
                            successfulCollectorsPerColumn.put(columnName, successfulCollectorsPerColumn.get(columnName) + 1);
                        }
                    }
                    else {
                        successfulCollectorsPerColumn.put(columnName, sensorExecutionResult.isSuccess() ? 1 : 0);
                    }
                }

                if (errorSamplesDataScope == ErrorSamplesDataScope.data_group && sensorExecutionResult.isSuccess() &&
                        sensorExecutionResult.getResultTable().rowCount() == 0) {
                    continue; // no results captured, moving to the next sensor
                }
                executionStatistics.incrementCollectedErrorSamplesCount(sensorExecutionResult.getResultTable() != null ?
                        sensorExecutionResult.getResultTable().rowCount() : 0);

                ErrorSamplesNormalizedResult normalizedStatisticsResults = this.errorSamplesNormalizationService.normalizeResults(
                        sensorExecutionResult, errorSamplingSessionStartAt, sensorRunParameters);
                allNormalizedStatisticsTable.append(normalizedStatisticsResults.getTable());
            }
            catch (DqoQueueJobCancelledException cex) {
                // ignore
                break;
            }
            catch (Exception ex) {
                log.error("Error samples collector " + checkSpec.getHierarchyId().toString() + " failed to execute: " + ex);
            }
        }

        progressListener.onSavingErrorSamplesResults(new SavingErrorSamplesResultsEvent(tableSpec, errorSamplesSnapshot));
        if (errorSamplesSnapshot.getTableDataChanges().hasChanges() && !dummySensorExecution) {
            errorSamplesSnapshot.save();
        }

        int profiledColumns = successfulCollectorsPerColumn.size();
        long profiledColumnsSuccessfully = successfulCollectorsPerColumn.values().stream()
                .filter(cnt -> cnt > 0)
                .count();

        executionStatistics.incrementAnalyzedColumnsCount(profiledColumns);
        executionStatistics.incrementAnalyzedColumnSuccessfullyCount((int)profiledColumnsSuccessfully);

        progressListener.onTableErrorSamplesFinished(new ExecuteErrorSamplerOnTableFinishedEvent(
                connectionWrapper, tableSpec, checks, executionStatistics));

        errorSamplerExecutionSummary.reportTableStats(connectionWrapper, tableSpec, executionStatistics);

        return errorSamplerExecutionSummary;
    }

    /**
     * Prepares all sensors for execution.
     * @param checks Collection of check specifications for which error samplers that will be prepared.
     * @param executionContext Execution context - to access sensor definitions.
     * @param userHome User home.
     * @param userTimeWindowFilters Optional, user provided time window to apply for filtering.
     * @param progressListener Progress listener - to report progress.
     * @param executionStatistics Execution statistics - counts of checks and errors.
     * @param errorSamplesDataScope Error sampling scope (whole table or data streams).
     * @param jobCancellationToken Job cancellation token - to cancel the preparation by the user.
     * @return List of prepared sensors.
     */
    public List<SensorPrepareResult> prepareSensors(Collection<AbstractCheckSpec<?,?,?,?>> checks,
                                                    ExecutionContext executionContext,
                                                    UserHome userHome,
                                                    TimeWindowFilterParameters userTimeWindowFilters,
                                                    ErrorSamplerExecutionProgressListener progressListener,
                                                    ErrorSamplerExecutionStatistics executionStatistics,
                                                    ErrorSamplesDataScope errorSamplesDataScope,
                                                    JobCancellationToken jobCancellationToken) {
        DqoHome dqoHome = executionContext.getDqoHomeContext().getDqoHome();
        List<SensorPrepareResult> sensorPrepareResults = new ArrayList<>();

        for (AbstractCheckSpec<?,?,?,?> check : checks) {
            if (jobCancellationToken.isCancelled()) {
                break;
            }

            try {
                SensorExecutionRunParameters sensorRunParameters = createSensorRunParameters(dqoHome, userHome, check, errorSamplesDataScope, userTimeWindowFilters);
                if (sensorRunParameters == null) {
                    continue; // the collector does not support that target
                }
                executionStatistics.incrementErrorSamplersExecutedCount(1);

                jobCancellationToken.throwIfCancelled();
                progressListener.onPreparingSensor(new PreparingSensorEvent(sensorRunParameters.getTable(), sensorRunParameters));
                SensorPrepareResult sensorPrepareResult = this.dataQualitySensorRunner.prepareSensor(executionContext, sensorRunParameters, progressListener);

                if (!sensorPrepareResult.isSuccess()) {
                    this.userErrorLogger.logStatistics("Failed to prepare a sensor for statistics collector, error: " +
                                ((sensorPrepareResult.getPrepareException() != null) ? sensorPrepareResult.getPrepareException().getMessage() : ""),
                                sensorPrepareResult.getPrepareException());

                    executionStatistics.incrementErrorSamplersFailedCount(sensorPrepareResult.getPrepareException());
                    SensorExecutionResult sensorExecutionResultFailedPrepare = new SensorExecutionResult(sensorRunParameters, sensorPrepareResult.getPrepareException());
                    progressListener.onSensorFailed(new SensorFailedEvent(sensorRunParameters.getTable(), sensorRunParameters,
                            sensorExecutionResultFailedPrepare, sensorPrepareResult.getPrepareException()));
                    continue;
                }

                sensorPrepareResults.add(sensorPrepareResult);
            }
            catch (DqoQueueJobCancelledException cex) {
                // ignore the error, just stop running checks
                break;
            }
            catch (Throwable ex) {
                log.error("Error sample collector failed to run checks: " + ex.getMessage(), ex);
                throw new CheckExecutionFailedException("Error sample collector on table failed to execute", ex);
            }
        }

        return sensorPrepareResults;
    }

    /**
     * Executes prepared sensors.
     * @param groupedSensorsCollection Collection of sensors grouped by executors and similar queries that could be merged together.
     * @param executionContext Execution context - to access sensor definitions.
     * @param progressListener Progress listener - to report progress.
     * @param executionStatistics Execution statistics - counts of executed statistics collectors and errors.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token - to cancel the preparation by the user.
     * @return List of sensor execution results.
     */
    public List<SensorExecutionResult> executeSensors(GroupedSensorsCollection groupedSensorsCollection,
                                                      ExecutionContext executionContext,
                                                      ErrorSamplerExecutionProgressListener progressListener,
                                                      ErrorSamplerExecutionStatistics executionStatistics,
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
                            this.userErrorLogger.logStatistics("Failed to execute a sensor for statistics collector, error: " +
                                                    ((sensorExecuteResult.getException() != null) ? sensorExecuteResult.getException().getMessage() : ""),
                                            sensorExecuteResult.getException());

                            executionStatistics.incrementErrorSamplersFailedCount(sensorExecuteResult.getException());
                            progressListener.onSensorFailed(new SensorFailedEvent(tableSpec, sensorRunParameters,
                                    sensorExecuteResult, sensorExecuteResult.getException()));
                            continue;
                        }

                        progressListener.onSensorExecuted(new SensorExecutedEvent(tableSpec, sensorRunParameters, sensorExecuteResult));
                        executionStatistics.incrementCollectedErrorSamplesCount(sensorExecuteResult.getResultTable().rowCount());
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
     * Creates a sensor run parameters from the statistics collector specification. Retrieves the connection, table, column and sensor parameters.
     * @param dqoHome DQO home.
     * @param userHome User home with the metadata.
     * @param checkSpec Check specification for the target check.
     * @param errorSamplingDataScope Error sampler collector data scope to analyze - the whole table or each data stream separately.
     * @param userTimeWindowFilters User defined time window.
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters createSensorRunParameters(DqoHome dqoHome,
                                                                  UserHome userHome,
                                                                  AbstractCheckSpec<?,?,?,?> checkSpec,
                                                                  ErrorSamplesDataScope errorSamplingDataScope,
                                                                  TimeWindowFilterParameters userTimeWindowFilters) {
        HierarchyId checkHierarchyId = checkSpec.getHierarchyId();
        ConnectionWrapper connectionWrapper = userHome.findConnectionFor(checkHierarchyId);
        TableWrapper tableWrapper = userHome.findTableFor(checkHierarchyId);
        ColumnSpec columnSpec = userHome.findColumnFor(checkHierarchyId); // may be null
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings dialectSettings = connectionProvider.getDialectSettings(connectionSpec);
        TableSpec tableSpec = tableWrapper.getSpec();

        List<HierarchyNode> nodesOnPath = List.of(checkHierarchyId.getNodesOnPath(tableSpec));
        Optional<HierarchyNode> checkCategoryRootProvider = Lists.reverse(nodesOnPath)
                .stream()
                .filter(n -> n instanceof AbstractRootChecksContainerSpec)
                .findFirst();
        assert checkCategoryRootProvider.isPresent();
        AbstractRootChecksContainerSpec rootChecksContainerSpec = (AbstractRootChecksContainerSpec) checkCategoryRootProvider.get();
        CheckType checkType = rootChecksContainerSpec.getCheckType();

        CheckDefinitionSpec customCheckDefinitionSpec = null;

        if (checkSpec instanceof CustomCheckSpec) {
            CustomCheckSpec customCheckSpec = (CustomCheckSpec) checkSpec;
            customCheckDefinitionSpec = userHome.getChecks().getCheckDefinitionSpec(
                    rootChecksContainerSpec.getCheckTarget(), checkType,
                    rootChecksContainerSpec.getCheckTimeScale(), checkSpec.getCategoryName(), customCheckSpec.getCheckName());
            if (customCheckDefinitionSpec == null) {
                String fullCheckName = CheckDefinitionList.makeCheckName(rootChecksContainerSpec.getCheckTarget(), checkType,
                        rootChecksContainerSpec.getCheckTimeScale(), checkSpec.getCategoryName(), customCheckSpec.getCheckName());
                String errorMessage = "Cannot execute a custom check " + fullCheckName + " on the table " + tableSpec.toString() +
                        " because the custom check is not defined. The configured check that failed to execute is " + checkSpec.getHierarchyId().toString();
                this.userErrorLogger.logCheck(errorMessage, null);
                throw new DqoRuntimeException(errorMessage);
            }
        }

        SensorExecutionRunParameters sensorRunParameters = this.sensorExecutionRunParametersFactory.createErrorSamplerSensorParameters(
                dqoHome, userHome, connectionSpec, tableSpec, columnSpec, checkSpec,
                customCheckDefinitionSpec, userTimeWindowFilters, errorSamplingDataScope, dialectSettings);
        return sensorRunParameters; // may return null if the sensor is not supported on the data source
    }
}
