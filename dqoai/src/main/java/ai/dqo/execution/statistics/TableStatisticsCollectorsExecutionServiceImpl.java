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
package ai.dqo.execution.statistics;

import ai.dqo.connectors.ConnectionProvider;
import ai.dqo.connectors.ConnectionProviderRegistry;
import ai.dqo.connectors.DataTypeCategory;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.core.jobqueue.*;
import ai.dqo.core.jobqueue.exceptions.DqoQueueJobCancelledException;
import ai.dqo.data.statistics.factory.StatisticsDataScope;
import ai.dqo.data.statistics.normalization.StatisticsResultsNormalizationService;
import ai.dqo.data.statistics.normalization.StatisticsResultsNormalizedResult;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshot;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshotFactory;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.sensors.*;
import ai.dqo.execution.sensors.progress.ExecutingSensorEvent;
import ai.dqo.execution.sensors.progress.PreparingSensorEvent;
import ai.dqo.execution.sensors.progress.SensorExecutedEvent;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.StatisticsCollectorSearchFilters;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.statistics.AbstractStatisticsCollectorSpec;
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

    /**
     * Creates a statistics collectors execution service with given dependencies.
     * @param hierarchyNodeTreeSearcher Target node search service.
     * @param sensorExecutionRunParametersFactory Sensor run parameters factory.
     * @param dataQualitySensorRunner Sensor runner.
     * @param connectionProviderRegistry Connection provider.
     * @param statisticsResultsNormalizationService Normalization service that creates profiling results.
     * @param statisticsSnapshotFactory Statistics results snapshot factory. Snapshots support storage of profiler results.
     */
    @Autowired
    public TableStatisticsCollectorsExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                                         SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory,
                                                         DataQualitySensorRunner dataQualitySensorRunner,
                                                         ConnectionProviderRegistry connectionProviderRegistry,
                                                         StatisticsResultsNormalizationService statisticsResultsNormalizationService,
                                                         StatisticsSnapshotFactory statisticsSnapshotFactory) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.sensorExecutionRunParametersFactory = sensorExecutionRunParametersFactory;
        this.dataQualitySensorRunner = dataQualitySensorRunner;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.statisticsResultsNormalizationService = statisticsResultsNormalizationService;
        this.statisticsSnapshotFactory = statisticsSnapshotFactory;
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
//        progressListener.onExecuteProfilerOnTableStart(new ExecuteChecksOnTableStartEvent(connectionWrapper, tableSpec, checks));
        String connectionName = connectionWrapper.getName();
        PhysicalTableName physicalTableName = tableSpec.getPhysicalTableName();
        StatisticsSnapshot statisticsSnapshot = this.statisticsSnapshotFactory.createSnapshot(connectionName, physicalTableName);
        Table allNormalizedStatisticsTable = statisticsSnapshot.getTableDataChanges().getNewOrChangedRows();

        Map<String, Integer> successfulCollectorsPerColumn = new HashMap<>();

        for (AbstractStatisticsCollectorSpec<?> statisticsCollectorSpec : collectors) {
            jobCancellationToken.throwIfCancelled();
            executionStatistics.incrementCollectorsExecutedCount(1);

            try {
                SensorExecutionRunParameters sensorRunParameters = prepareSensorRunParameters(userHome, statisticsCollectorSpec, statisticsDataScope);
                if (!collectorSupportsTarget(statisticsCollectorSpec, sensorRunParameters)) {
                    continue; // the collector does not support that target
                }

                progressListener.onPreparingSensor(new PreparingSensorEvent(tableSpec, sensorRunParameters));
                SensorPrepareResult sensorPrepareResult = this.dataQualitySensorRunner.prepareSensor(executionContext, sensorRunParameters, progressListener);

                progressListener.onExecutingSensor(new ExecutingSensorEvent(tableSpec, sensorPrepareResult));
                SensorExecutionResult sensorResult = this.dataQualitySensorRunner.executeSensor(executionContext,
                        sensorPrepareResult, progressListener, dummySensorExecution, jobCancellationToken);
                progressListener.onSensorExecuted(new SensorExecutedEvent(tableSpec, sensorRunParameters, sensorResult));

                if (!sensorResult.isSuccess()) {
                    executionStatistics.incrementCollectorsFailedCount(1);
                }

                if (sensorRunParameters.getColumn() != null) {
                    String columnName = sensorRunParameters.getColumn().getColumnName();
                    if (successfulCollectorsPerColumn.containsKey(columnName)) {
                        if (sensorResult.isSuccess()) {
                            successfulCollectorsPerColumn.put(columnName, successfulCollectorsPerColumn.get(columnName) + 1);
                        }
                    }
                    else {
                        successfulCollectorsPerColumn.put(columnName, sensorResult.isSuccess() ? 1 : 0);
                    }
                }

                if (statisticsDataScope == StatisticsDataScope.data_stream && sensorResult.isSuccess() && sensorResult.getResultTable().rowCount() == 0) {
                    continue; // no results captured, moving to the next sensor, probably an incremental time window too small or no data in the table
                }
                executionStatistics.incrementCollectorsResultsCount(sensorResult.getResultTable() != null ? sensorResult.getResultTable().rowCount() : 0);

                StatisticsResultsNormalizedResult normalizedStatisticsResults = this.statisticsResultsNormalizationService.normalizeResults(
                        sensorResult, collectionSessionStartAt, sensorRunParameters);
//                progressListener.onSensorResultsNormalized(new SensorResultsNormalizedEvent(
//                        tableSpec, sensorRunParameters, sensorResult, normalizedSensorResults));
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

//        progressListener.onSavingSensorResults(new SavingSensorResultsEvent(tableSpec, profilerResultsSnapshot));
        if (statisticsSnapshot.getTableDataChanges().hasChanges() && !dummySensorExecution) {
            statisticsSnapshot.save();
        }

//        progressListener.onTableProfilerProcessingFinished(new TableProfilerProcessingFinished(connectionWrapper, tableSpec, profilers,
//                profilersExecuted, profiledColumns, profiledColumnsSuccessfully, profilersFailed, profilerResults));

        int profiledColumns = successfulCollectorsPerColumn.size();
        long profiledColumnsSuccessfully = successfulCollectorsPerColumn.values().stream()
                .filter(cnt -> cnt > 0)
                .count();

        executionStatistics.incrementProfiledColumnsCount(profiledColumns);
        executionStatistics.incrementProfiledColumnSuccessfullyCount((int)profiledColumnsSuccessfully);

        statisticsCollectionExecutionSummary.reportTableStats(connectionWrapper, tableSpec, executionStatistics);

        return statisticsCollectionExecutionSummary;
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

        ColumnTypeSnapshotSpec typeSnapshot = sensorRunParameters.getColumn().getTypeSnapshot();
        if (typeSnapshot == null || Strings.isNullOrEmpty(typeSnapshot.getColumnType())) {
            return false; // the data type not known, we cannot risk failing the profiler, skipping
        }

        DataTypeCategory[] supportedDataTypes = statisticsCollectorSpec.getSupportedDataTypes();
        if (supportedDataTypes == null) {
            return true; // all data types supported
        }

        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(sensorRunParameters.getConnection().getProviderType());
        DataTypeCategory targetColumnTypeCategory = connectionProvider.detectColumnType(typeSnapshot);

        for (DataTypeCategory supportedDataType : supportedDataTypes) {
            if (targetColumnTypeCategory == supportedDataType) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a sensor run parameters from the statistics collector specification. Retrieves the connection, table, column and sensor parameters.
     * @param userHome User home with the metadata.
     * @param statisticsCollectorSpec Statistics collector specification.
     * @param statisticsDataScope Statistics collector data scope to analyze - the whole table or each data stream separately.
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters prepareSensorRunParameters(UserHome userHome,
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
                connectionSpec, tableSpec, columnSpec, statisticsCollectorSpec, null, statisticsDataScope, dialectSettings);
        return sensorRunParameters;
    }

}
