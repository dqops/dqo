package ai.dqo.execution.profiler;

import ai.dqo.connectors.ConnectionProvider;
import ai.dqo.connectors.ConnectionProviderRegistry;
import ai.dqo.connectors.DataTypeCategory;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.data.profilingresults.factory.ProfilerDataScope;
import ai.dqo.data.profilingresults.normalization.ProfilingResultsNormalizationService;
import ai.dqo.data.profilingresults.normalization.ProfilingResultsNormalizedResult;
import ai.dqo.data.profilingresults.snapshot.ProfilingResultsSnapshot;
import ai.dqo.data.profilingresults.snapshot.ProfilingResultsSnapshotFactory;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.profiler.progress.ProfilersExecutionFinishedEvent;
import ai.dqo.execution.profiler.progress.ProfilerExecutionProgressListener;
import ai.dqo.execution.sensors.DataQualitySensorRunner;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersFactory;
import ai.dqo.execution.sensors.progress.ExecutingSensorEvent;
import ai.dqo.execution.sensors.progress.SensorExecutedEvent;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.ProfilerSearchFilters;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.profiling.AbstractProfilerSpec;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Table;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Profiler execution service. Executes profilers on tables and columns.
 */
@Service
@Slf4j
public class ProfilerExecutionServiceImpl implements ProfilerExecutionService {
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory;
    private DataQualitySensorRunner dataQualitySensorRunner;
    private ConnectionProviderRegistry connectionProviderRegistry;
    private ProfilingResultsNormalizationService profilingResultsNormalizationService;
    private ProfilingResultsSnapshotFactory profilingResultsSnapshotFactory;

    /**
     * Creates a profiler execution service with given dependencies.
     * @param hierarchyNodeTreeSearcher Target node search service.
     * @param sensorExecutionRunParametersFactory Sensor run parameters factory.
     * @param dataQualitySensorRunner Sensor runner.
     * @param connectionProviderRegistry Connection provider.
     * @param profilingResultsNormalizationService Normalization service that creates profiling results.
     * @param profilingResultsSnapshotFactory Profiling results snapshot factory. Snapshots support storage of profiler results.
     */
    @Autowired
    public ProfilerExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                        SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory,
                                        DataQualitySensorRunner dataQualitySensorRunner,
                                        ConnectionProviderRegistry connectionProviderRegistry,
                                        ProfilingResultsNormalizationService profilingResultsNormalizationService,
                                        ProfilingResultsSnapshotFactory profilingResultsSnapshotFactory) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.sensorExecutionRunParametersFactory = sensorExecutionRunParametersFactory;
        this.dataQualitySensorRunner = dataQualitySensorRunner;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.profilingResultsNormalizationService = profilingResultsNormalizationService;
        this.profilingResultsSnapshotFactory = profilingResultsSnapshotFactory;
    }

    /**
     * Executes data profilers on tables and columns. Reports progress and saves the results.
     * @param executionContext Check/profiler execution context with access to the user home and dqo home.
     * @param profilerSearchFilters Profiler search filters to find the right checks.
     * @param progressListener Progress listener that receives progress calls.
     * @param profilerDataScope Profiler data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @return Profiler summary table with the count of executed and successful profile executions for each table.
     */
    @Override
    public ProfilerExecutionSummary executeProfilers(ExecutionContext executionContext,
                                                     ProfilerSearchFilters profilerSearchFilters,
                                                     ProfilerExecutionProgressListener progressListener,
                                                     ProfilerDataScope profilerDataScope,
                                                     boolean dummySensorExecution) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        Collection<TableWrapper> targetTables = listTargetTables(userHome, profilerSearchFilters);
        ProfilerExecutionSummary profilerExecutionSummary = new ProfilerExecutionSummary();
        LocalDateTime profilingSessionStartAt = LocalDateTime.now();

        for (TableWrapper targetTable :  targetTables) {
            ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());
            executeProfilersOnTable(executionContext, userHome, connectionWrapper, targetTable, profilerSearchFilters, progressListener,
                    dummySensorExecution, profilerExecutionSummary, profilingSessionStartAt, profilerDataScope);
        }

        progressListener.onProfilersExecutionFinished(new ProfilersExecutionFinishedEvent(profilerExecutionSummary));

        return profilerExecutionSummary;
    }

    /**
     * Lists all target tables that were not excluded from the filter and may have profilers to be executed.
     * @param userHome User home.
     * @param profilerSearchFilters Profilers search filter.
     * @return Collection of table wrappers.
     */
    public Collection<TableWrapper> listTargetTables(UserHome userHome, ProfilerSearchFilters profilerSearchFilters) {
        Collection<TableWrapper> tables = this.hierarchyNodeTreeSearcher.findTables(userHome.getConnections(), profilerSearchFilters);
        return tables;
    }
    
    /**
     * Execute profilers on a single table.
     * @param executionContext Execution context with access to the user home and dqo home.
     * @param userHome User home with all metadata and checks.
     * @param connectionWrapper  Target connection.
     * @param targetTable Target table.
     * @param profilerSearchFilters Profilers search filters.
     * @param progressListener Progress listener.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param profilerExecutionSummary Target object to gather the profiler execution summary information for the table.
     * @param profilingSessionStartAt Timestamp when the profiling session started. All profiler results will be saved with the same timestamp.
     * @param profilerDataScope Profiler data scope to analyze - the whole table or each data stream separately.
     */
    public void executeProfilersOnTable(ExecutionContext executionContext,
                                        UserHome userHome,
                                        ConnectionWrapper connectionWrapper,
                                        TableWrapper targetTable,
                                        ProfilerSearchFilters profilerSearchFilters,
                                        ProfilerExecutionProgressListener progressListener,
                                        boolean dummySensorExecution,
                                        ProfilerExecutionSummary profilerExecutionSummary,
                                        LocalDateTime profilingSessionStartAt,
                                        ProfilerDataScope profilerDataScope) {
        Collection<AbstractProfilerSpec<?>> profilers = this.hierarchyNodeTreeSearcher.findProfilers(targetTable, profilerSearchFilters);
        if (profilers.size() == 0) {
            profilerExecutionSummary.reportTableStats(connectionWrapper, targetTable.getSpec(), 0, 0, 0, 0, 0);
            return; // no checks for this table
        }

        TableSpec tableSpec = targetTable.getSpec();
//        progressListener.onExecuteProfilerOnTableStart(new ExecuteChecksOnTableStartEvent(connectionWrapper, tableSpec, checks));
        String connectionName = connectionWrapper.getName();
        PhysicalTableName physicalTableName = tableSpec.getTarget().toPhysicalTableName();
        ProfilingResultsSnapshot profilerResultsSnapshot = this.profilingResultsSnapshotFactory.createSnapshot(connectionName, physicalTableName);
        Table allNormalizedProfilerResultsTable = profilerResultsSnapshot.getTableDataChanges().getNewOrChangedRows();
        int profilersExecuted = 0;
        int profilersFailed = 0;
        int profilerResults = 0;
        Map<String, Integer> successfulProfilesPerColumn = new HashMap<>();

        for (AbstractProfilerSpec<?> profilerSpec : profilers) {
            profilersExecuted++;

            try {
                SensorExecutionRunParameters sensorRunParameters = prepareSensorRunParameters(userHome, profilerSpec, profilerDataScope);
                if (!profilerSupportsTarget(profilerSpec, sensorRunParameters)) {
                    continue; // the profiler does not support that target
                }

                progressListener.onExecutingSensor(new ExecutingSensorEvent(tableSpec, sensorRunParameters));

                SensorExecutionResult sensorResult = this.dataQualitySensorRunner.executeSensor(executionContext,
                        sensorRunParameters, progressListener, dummySensorExecution);
                progressListener.onSensorExecuted(new SensorExecutedEvent(tableSpec, sensorRunParameters, sensorResult));

                if (!sensorResult.isSuccess()) {
                    profilersFailed++;
                }

                if (sensorRunParameters.getColumn() != null) {
                    String columnName = sensorRunParameters.getColumn().getColumnName();
                    if (successfulProfilesPerColumn.containsKey(columnName)) {
                        if (sensorResult.isSuccess()) {
                            successfulProfilesPerColumn.put(columnName, successfulProfilesPerColumn.get(columnName) + 1);
                        }
                    }
                    else {
                        successfulProfilesPerColumn.put(columnName, sensorResult.isSuccess() ? 1 : 0);
                    }
                }

                if (profilerDataScope == ProfilerDataScope.data_stream && sensorResult.isSuccess() && sensorResult.getResultTable().rowCount() == 0) {
                    continue; // no results captured, moving to the next sensor, probably an incremental time window too small or no data in the table
                }
                profilerResults += sensorResult.getResultTable() != null ? sensorResult.getResultTable().rowCount() : 0;

                ProfilingResultsNormalizedResult normalizedProfilerResults = this.profilingResultsNormalizationService.normalizeResults(
                        sensorResult, profilingSessionStartAt, sensorRunParameters);
//                progressListener.onSensorResultsNormalized(new SensorResultsNormalizedEvent(
//                        tableSpec, sensorRunParameters, sensorResult, normalizedSensorResults));
                allNormalizedProfilerResultsTable.append(normalizedProfilerResults.getTable());
            }
            catch (Exception ex) {
                log.error("Profiler " + profilerSpec.getProfilerName() + " failed to execute: " + ex);
            }
        }

//        progressListener.onSavingSensorResults(new SavingSensorResultsEvent(tableSpec, profilerResultsSnapshot));
        if (profilerResultsSnapshot.getTableDataChanges().hasChanges() && !dummySensorExecution) {
            profilerResultsSnapshot.save();
        }

//        progressListener.onTableProfilerProcessingFinished(new TableProfilerProcessingFinished(connectionWrapper, tableSpec, profilers,
//                profilersExecuted, profiledColumns, profiledColumnsSuccessfully, profilersFailed, profilerResults));

        int profiledColumns = successfulProfilesPerColumn.size();
        long profiledColumnsSuccessfully = successfulProfilesPerColumn.values().stream()
                .filter(cnt -> cnt > 0)
                .count();

        profilerExecutionSummary.reportTableStats(connectionWrapper, tableSpec, profilersExecuted, profiledColumns,
                (int)profiledColumnsSuccessfully, profilersFailed, profilerResults);
    }

    /**
     * Verify the profiler supported data types against the data types in the data source.
     * @param profilerSpec Profiler specification.
     * @param sensorRunParameters Sensor run parameters.
     * @return True - the profiler could be executed, false - not supported on this data type.
     */
    public boolean profilerSupportsTarget(AbstractProfilerSpec<?> profilerSpec, SensorExecutionRunParameters sensorRunParameters) {
        if (sensorRunParameters.getColumn() == null) {
            return true; // this is not a column level profiler, should work
        }

        ColumnTypeSnapshotSpec typeSnapshot = sensorRunParameters.getColumn().getTypeSnapshot();
        if (typeSnapshot == null || Strings.isNullOrEmpty(typeSnapshot.getColumnType())) {
            return false; // the data type not known, we cannot risk failing the profiler, skipping
        }

        DataTypeCategory[] supportedDataTypes = profilerSpec.getSupportedDataTypes();
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
     * Creates a sensor run parameters from the profiler specification. Retrieves the connection, table, column and sensor parameters.
     * @param userHome User home with the metadata.
     * @param profilerSpec Profiler specification.
     * @param profilerDataScope Profiler data scope to analyze - the whole table or each data stream separately.
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters prepareSensorRunParameters(UserHome userHome,
                                                                   AbstractProfilerSpec<?> profilerSpec,
                                                                   ProfilerDataScope profilerDataScope) {
        HierarchyId checkHierarchyId = profilerSpec.getHierarchyId();
        ConnectionWrapper connectionWrapper = userHome.findConnectionFor(checkHierarchyId);
        TableWrapper tableWrapper = userHome.findTableFor(checkHierarchyId);
        ColumnSpec columnSpec = userHome.findColumnFor(checkHierarchyId); // may be null
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        ProviderDialectSettings dialectSettings = connectionProvider.getDialectSettings(connectionSpec);
        TableSpec tableSpec = tableWrapper.getSpec();

        SensorExecutionRunParameters sensorRunParameters = this.sensorExecutionRunParametersFactory.createSensorParameters(
                connectionSpec, tableSpec, columnSpec, profilerSpec, profilerDataScope, dialectSettings);
        return sensorRunParameters;
    }

}
