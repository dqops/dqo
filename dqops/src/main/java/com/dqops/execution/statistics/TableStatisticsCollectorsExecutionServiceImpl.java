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
package com.dqops.execution.statistics;

import com.dqops.connectors.*;
import com.dqops.core.configuration.DqoSensorLimitsConfigurationProperties;
import com.dqops.core.configuration.DqoStatisticsCollectorConfigurationProperties;
import com.dqops.core.jobqueue.*;
import com.dqops.core.jobqueue.exceptions.DqoQueueJobCancelledException;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.data.statistics.normalization.StatisticsResultsNormalizationService;
import com.dqops.data.statistics.normalization.StatisticsResultsNormalizedResult;
import com.dqops.data.statistics.snapshot.StatisticsSnapshot;
import com.dqops.data.statistics.snapshot.StatisticsSnapshotFactory;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.CheckExecutionFailedException;
import com.dqops.execution.sensors.*;
import com.dqops.execution.sensors.grouping.GroupedSensorExecutionResult;
import com.dqops.execution.sensors.grouping.GroupedSensorsCollection;
import com.dqops.execution.sensors.grouping.PreparedSensorsGroup;
import com.dqops.execution.sensors.progress.ExecutingSensorEvent;
import com.dqops.execution.sensors.progress.PreparingSensorEvent;
import com.dqops.execution.sensors.progress.SensorExecutedEvent;
import com.dqops.execution.sensors.progress.SensorFailedEvent;
import com.dqops.execution.statistics.progress.ExecuteStatisticsCollectorsOnTableFinishedEvent;
import com.dqops.execution.statistics.progress.ExecuteStatisticsCollectorsOnTableStartEvent;
import com.dqops.execution.statistics.progress.SavingStatisticsResultsEvent;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import com.dqops.metadata.dqohome.DqoHome;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;
import com.dqops.utils.logging.UserErrorLogger;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Statistics collectors execution service that collects basic profiling statistics on a single table.
 */
@Service
@Slf4j
public class TableStatisticsCollectorsExecutionServiceImpl implements TableStatisticsCollectorsExecutionService {
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory;
    private DataQualitySensorRunner dataQualitySensorRunner;
    private ConnectionProviderRegistry connectionProviderRegistry;
    private StatisticsResultsNormalizationService statisticsResultsNormalizationService;
    private StatisticsSnapshotFactory statisticsSnapshotFactory;
    private DqoSensorLimitsConfigurationProperties dqoSensorLimitsConfigurationProperties;
    private DqoStatisticsCollectorConfigurationProperties statisticsCollectorConfigurationProperties;
    private final UserErrorLogger userErrorLogger;

    /**
     * Creates a statistics collectors execution service with given dependencies.
     * @param hierarchyNodeTreeSearcher Target node search service.
     * @param sensorExecutionRunParametersFactory Sensor run parameters factory.
     * @param dataQualitySensorRunner Sensor runner.
     * @param connectionProviderRegistry Connection provider.
     * @param statisticsResultsNormalizationService Normalization service that creates profiling results.
     * @param statisticsSnapshotFactory Statistics results snapshot factory. Snapshots support storage of profiler results.
     * @param dqoSensorLimitsConfigurationProperties DQOps sensor limits configuration.
     * @param statisticsCollectorConfigurationProperties Statistics collector configuration properties.
     * @param userErrorLogger Execution logger.
     */
    @Autowired
    public TableStatisticsCollectorsExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                                         SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory,
                                                         DataQualitySensorRunner dataQualitySensorRunner,
                                                         ConnectionProviderRegistry connectionProviderRegistry,
                                                         StatisticsResultsNormalizationService statisticsResultsNormalizationService,
                                                         StatisticsSnapshotFactory statisticsSnapshotFactory,
                                                         DqoSensorLimitsConfigurationProperties dqoSensorLimitsConfigurationProperties,
                                                         DqoStatisticsCollectorConfigurationProperties statisticsCollectorConfigurationProperties,
                                                         UserErrorLogger userErrorLogger) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.sensorExecutionRunParametersFactory = sensorExecutionRunParametersFactory;
        this.dataQualitySensorRunner = dataQualitySensorRunner;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.statisticsResultsNormalizationService = statisticsResultsNormalizationService;
        this.statisticsSnapshotFactory = statisticsSnapshotFactory;
        this.dqoSensorLimitsConfigurationProperties = dqoSensorLimitsConfigurationProperties;
        this.statisticsCollectorConfigurationProperties = statisticsCollectorConfigurationProperties;
        this.userErrorLogger = userErrorLogger;
    }

    /**
     * Execute statistics collectors on a single table.
     * @param executionContext Execution context with access to the user home and dqo home.
     * @param userHome User home with all metadata and checks.
     * @param connectionWrapper  Target connection.
     * @param targetTable Target table.
     * @param statisticsCollectorSearchFilters Statistics collectors search filters.
     * @param progressListener Progress listener.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param collectionSessionStartAt Timestamp when the statistics collection session started. All statistics results will be saved with the same timestamp.
     * @param statisticsDataScope Collector data scope to analyze - the whole table or each data stream separately.
     * @param jobCancellationToken Job cancellation token, used to detect if the job should be cancelled.
     * @return Table level statistics.
     */
    @Override
    public StatisticsCollectionExecutionSummary executeCollectorsOnTable(ExecutionContext executionContext,
                                                                         UserHome userHome,
                                                                         ConnectionWrapper connectionWrapper,
                                                                         TableWrapper targetTable,
                                                                         StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                                                         StatisticsCollectorExecutionProgressListener progressListener,
                                                                         boolean dummySensorExecution,
                                                                         LocalDateTime collectionSessionStartAt,
                                                                         StatisticsDataScope statisticsDataScope,
                                                                         JobCancellationToken jobCancellationToken) {
        StatisticsCollectionExecutionSummary statisticsCollectionExecutionSummary = new StatisticsCollectionExecutionSummary();
        CollectorExecutionStatistics executionStatistics = new CollectorExecutionStatistics();

        Collection<AbstractStatisticsCollectorSpec<?>> collectors = this.hierarchyNodeTreeSearcher.findStatisticsCollectors(targetTable, statisticsCollectorSearchFilters);
        if (collectors.size() == 0) {
            statisticsCollectionExecutionSummary.reportTableStats(connectionWrapper, targetTable.getSpec(), executionStatistics);
            return statisticsCollectionExecutionSummary; // no checks for this table
        }
        jobCancellationToken.throwIfCancelled();

        TableSpec tableSpec = targetTable.getSpec();
        progressListener.onExecuteStatisticsCollectorsOnTableStart(new ExecuteStatisticsCollectorsOnTableStartEvent(connectionWrapper, tableSpec, collectors));
        String connectionName = connectionWrapper.getName();
        PhysicalTableName physicalTableName = tableSpec.getPhysicalTableName();
        UserDomainIdentity userDomainIdentity = userHome.getUserIdentity();
        StatisticsSnapshot statisticsSnapshot = this.statisticsSnapshotFactory.createSnapshot(connectionName, physicalTableName, userDomainIdentity);
        Table allNormalizedStatisticsTable = statisticsSnapshot.getTableDataChanges().getNewOrChangedRows();

        Map<String, Integer> successfulCollectorsPerColumn = new LinkedHashMap<>();

        List<SensorPrepareResult> allPreparedSensors = this.prepareSensors(collectors, executionContext, userHome, progressListener,
                executionStatistics, statisticsDataScope, jobCancellationToken);

        GroupedSensorsCollection groupedSensorsCollection = new GroupedSensorsCollection(this.dqoSensorLimitsConfigurationProperties.getMaxMergedQueries());
        groupedSensorsCollection.addAllPreparedSensors(allPreparedSensors);

        List<SensorExecutionResult> sensorExecutionResults = this.executeSensors(groupedSensorsCollection, executionContext, progressListener,
                executionStatistics, dummySensorExecution, jobCancellationToken);

        for (SensorExecutionResult sensorExecutionResult : sensorExecutionResults) {
            jobCancellationToken.throwIfCancelled();
            AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec = sensorExecutionResult.getSensorRunParameters().getProfiler();

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

                if (statisticsDataScope == StatisticsDataScope.data_group && sensorExecutionResult.isSuccess() &&
                        sensorExecutionResult.getResultTable().rowCount() == 0) {
                    continue; // no results captured, moving to the next sensor
                }
                executionStatistics.incrementCollectorsResultsCount(sensorExecutionResult.getResultTable() != null ?
                        sensorExecutionResult.getResultTable().rowCount() : 0);

                StatisticsResultsNormalizedResult normalizedStatisticsResults = this.statisticsResultsNormalizationService.normalizeResults(
                        sensorExecutionResult, collectionSessionStartAt, sensorRunParameters);
                allNormalizedStatisticsTable.append(normalizedStatisticsResults.getTable());
            }
            catch (DqoQueueJobCancelledException cex) {
                // ignore
                break;
            }
            catch (Exception ex) {
                log.error("Statistics collector " + statisticsCollectorSpec.getProfilerName() + " failed to execute: " + ex);
            }
        }

        progressListener.onSavingStatisticsResults(new SavingStatisticsResultsEvent(tableSpec, statisticsSnapshot));
        if (statisticsSnapshot.getTableDataChanges().hasChanges() && !dummySensorExecution) {
            statisticsSnapshot.save();
        }

        int profiledColumns = successfulCollectorsPerColumn.size();
        long profiledColumnsSuccessfully = successfulCollectorsPerColumn.values().stream()
                .filter(cnt -> cnt > 0)
                .count();

        executionStatistics.incrementProfiledColumnsCount(profiledColumns);
        executionStatistics.incrementProfiledColumnSuccessfullyCount((int)profiledColumnsSuccessfully);

        progressListener.onTableStatisticsCollectionFinished(new ExecuteStatisticsCollectorsOnTableFinishedEvent(
                connectionWrapper, tableSpec, collectors, executionStatistics));

        statisticsCollectionExecutionSummary.reportTableStats(connectionWrapper, tableSpec, executionStatistics);

        return statisticsCollectionExecutionSummary;
    }

    /**
     * Prepares all sensors for execution.
     * @param collectors Collection of statistics collectors that will be prepared.
     * @param executionContext Execution context - to access sensor definitions.
     * @param userHome User home.
     * @param progressListener Progress listener - to report progress.
     * @param executionStatistics Execution statistics - counts of checks and errors.
     * @param statisticsDataScope Statistics scope (whole table or data streams).
     * @param jobCancellationToken Job cancellation token - to cancel the preparation by the user.
     * @return List of prepared sensors.
     */
    public List<SensorPrepareResult> prepareSensors(Collection<AbstractStatisticsCollectorSpec<?>> collectors,
                                                    ExecutionContext executionContext,
                                                    UserHome userHome,
                                                    StatisticsCollectorExecutionProgressListener progressListener,
                                                    CollectorExecutionStatistics executionStatistics,
                                                    StatisticsDataScope statisticsDataScope,
                                                    JobCancellationToken jobCancellationToken) {
        DqoHome dqoHome = executionContext.getDqoHomeContext().getDqoHome();
        List<SensorPrepareResult> sensorPrepareResults = new ArrayList<>();
        int sensorResultId = 0;

        for (AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec : collectors) {
            if (jobCancellationToken.isCancelled()) {
                break;
            }

            try {
                SensorExecutionRunParameters sensorRunParameters = createSensorRunParameters(dqoHome, userHome, statisticsCollectorSpec, statisticsDataScope);
                if (sensorRunParameters == null || !collectorSupportsTarget(statisticsCollectorSpec, sensorRunParameters)) {
                    continue; // the collector does not support that target
                }
                executionStatistics.incrementCollectorsExecutedCount(1);


                sensorResultId++;
                sensorRunParameters.setActualValueAlias("dqo_actual_value_" + sensorResultId);
                sensorRunParameters.setExpectedValueAlias("dqo_expected_value_" + sensorResultId);

                jobCancellationToken.throwIfCancelled();
                progressListener.onPreparingSensor(new PreparingSensorEvent(sensorRunParameters.getTable(), sensorRunParameters));
                SensorPrepareResult sensorPrepareResult = this.dataQualitySensorRunner.prepareSensor(executionContext, sensorRunParameters, progressListener);

                if (!sensorPrepareResult.isSuccess()) {
                    this.userErrorLogger.logStatistics("Failed to prepare a sensor for statistics collector, error: " +
                                ((sensorPrepareResult.getPrepareException() != null) ? sensorPrepareResult.getPrepareException().getMessage() : ""),
                                sensorPrepareResult.getPrepareException());

                    executionStatistics.incrementCollectorsFailedCount(sensorPrepareResult.getPrepareException());
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
                log.error("Statistics collector failed to run checks: " + ex.getMessage(), ex);
                throw new CheckExecutionFailedException("Statistics collector on table failed to execute", ex);
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
                                                      StatisticsCollectorExecutionProgressListener progressListener,
                                                      CollectorExecutionStatistics executionStatistics,
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

                            executionStatistics.incrementCollectorsFailedCount(sensorExecuteResult.getException());
                            progressListener.onSensorFailed(new SensorFailedEvent(tableSpec, sensorRunParameters,
                                    sensorExecuteResult, sensorExecuteResult.getException()));
                            continue;
                        }

                        progressListener.onSensorExecuted(new SensorExecutedEvent(tableSpec, sensorRunParameters, sensorExecuteResult));
                        executionStatistics.incrementCollectorsResultsCount(sensorExecuteResult.getResultTable().rowCount());
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
     * Verify if the statistics collector supports data types against the data types in the data source.
     * @param statisticsCollectorSpec Statistics collector specification.
     * @param sensorRunParameters Sensor run parameters.
     * @return True - the collector could be executed, false - not supported on this data type.
     */
    public boolean collectorSupportsTarget(AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec, SensorExecutionRunParameters sensorRunParameters) {
        if (sensorRunParameters.getColumn() == null) {
            return true; // this is not a column level profiler, should work
        }

        DataTypeCategory[] supportedDataTypes = statisticsCollectorSpec.getSupportedDataTypes();
        if (supportedDataTypes == null) {
            return true; // all data types supported
        }

        ProviderType providerType = sensorRunParameters.getConnection().getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        ProviderDialectSettings dialectSettings = connectionProvider.getDialectSettings(sensorRunParameters.getConnection());
        DataTypeCategory targetColumnTypeCategory;

        ColumnTypeSnapshotSpec typeSnapshot = sensorRunParameters.getColumn().getTypeSnapshot();
        if (typeSnapshot == null || Strings.isNullOrEmpty(typeSnapshot.getColumnType())) {
            targetColumnTypeCategory = DataTypeCategory.text; // we are assuming that all unknown types are text types, just to allow analyzing calculated columns
        } else {
            targetColumnTypeCategory = dialectSettings.detectColumnType(typeSnapshot);
        }

        for (DataTypeCategory supportedDataType : supportedDataTypes) {
            if (targetColumnTypeCategory == supportedDataType) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a sensor run parameters from the statistics collector specification. Retrieves the connection, table, column and sensor parameters.
     * @param dqoHome DQO home.
     * @param userHome User home with the metadata.
     * @param statisticsCollectorSpec Statistics collector specification.
     * @param statisticsDataScope Statistics collector data scope to analyze - the whole table or each data stream separately.
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters createSensorRunParameters(DqoHome dqoHome,
                                                                  UserHome userHome,
                                                                  AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec,
                                                                  StatisticsDataScope statisticsDataScope) {
        HierarchyId checkHierarchyId = statisticsCollectorSpec.getHierarchyId();
        ConnectionWrapper connectionWrapper = userHome.findConnectionFor(checkHierarchyId);
        TableWrapper tableWrapper = userHome.findTableFor(checkHierarchyId);
        ColumnSpec columnSpec = userHome.findColumnFor(checkHierarchyId); // may be null
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings dialectSettings = connectionProvider.getDialectSettings(connectionSpec);
        TableSpec tableSpec = tableWrapper.getSpec();

        // TODO: statistics collection could support time windows or a time range, the filter that is passed downstream is now null

        SensorExecutionRunParameters sensorRunParameters = this.sensorExecutionRunParametersFactory.createStatisticsSensorParameters(
                dqoHome, userHome, connectionSpec, tableSpec, columnSpec, statisticsCollectorSpec,
                null, statisticsDataScope, dialectSettings);
        return sensorRunParameters; // may return null if the sensor is not supported on the data source
    }

}
