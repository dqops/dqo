package ai.dqo.data.checkresults.services;

import ai.dqo.checks.AbstractRootChecksContainerSpec;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.data.checkresults.factory.CheckResultsColumnNames;
import ai.dqo.data.checkresults.services.models.CheckResultDetailedSingleModel;
import ai.dqo.data.checkresults.services.models.CheckResultStatus;
import ai.dqo.data.checkresults.services.models.CheckResultsDetailedDataModel;
import ai.dqo.data.checkresults.services.models.CheckResultsOverviewDataModel;
import ai.dqo.data.checkresults.snapshot.CheckResultsSnapshot;
import ai.dqo.data.checkresults.snapshot.CheckResultsSnapshotFactory;
import ai.dqo.data.errors.factory.ErrorsColumnNames;
import ai.dqo.data.errors.snapshot.ErrorsSnapshot;
import ai.dqo.data.errors.snapshot.ErrorsSnapshotFactory;
import ai.dqo.data.normalization.CommonTableNormalizationService;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import ai.dqo.utils.tables.TableRowUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;
import tech.tablesaw.selection.Selection;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service that returns data from the check results.
 */
@Service
public class CheckResultsDataServiceImpl implements CheckResultsDataService {
    private CheckResultsSnapshotFactory checkResultsSnapshotFactory;
    private ErrorsSnapshotFactory errorsSnapshotFactory;
    private DefaultTimeZoneProvider defaultTimeZoneProvider;

    @Autowired
    public CheckResultsDataServiceImpl(CheckResultsSnapshotFactory checkResultsSnapshotFactory,
                                       ErrorsSnapshotFactory errorsSnapshotFactory,
                                       DefaultTimeZoneProvider defaultTimeZoneProvider) {
        this.checkResultsSnapshotFactory = checkResultsSnapshotFactory;
        this.errorsSnapshotFactory = errorsSnapshotFactory;
        this.defaultTimeZoneProvider = defaultTimeZoneProvider;
    }

    /**
     * Retrieves the overall status of the recent check executions for the given root checks container (group of checks).
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters Load parameters.
     * @return Overview of the check recent results.
     */
    @Override
    public CheckResultsOverviewDataModel[] readMostRecentCheckStatuses(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                       CheckResultsOverviewParameters loadParameters) {
        Map<Long, CheckResultsOverviewDataModel> resultMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        Table ruleResultsTable = loadRuleResults(loadParameters, connectionName, physicalTableName);
        Table errorsTable = loadErrorsNormalizedToResults(loadParameters, connectionName, physicalTableName);
        Table combinedTable = errorsTable != null ?
                (ruleResultsTable != null ? errorsTable.append(ruleResultsTable) : errorsTable) :
                ruleResultsTable;

        if (combinedTable == null) {
            return new CheckResultsOverviewDataModel[0]; // empty array
        }

        Table filteredTable = filterTableToRootChecksContainer(rootChecksContainerSpec, combinedTable);
        Table sortedTable = filteredTable.sortDescendingOn(
                SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, // most recent execution first
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, // then the most recent reading (for partitioned checks) when many partitions were captured
                CheckResultsColumnNames.SEVERITY_COLUMN_NAME); // second on the highest severity first on that time period

        int rowCount = sortedTable.rowCount();
        DateTimeColumn timePeriodColumn = sortedTable.dateTimeColumn(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);
        InstantColumn timePeriodUtcColumn = sortedTable.instantColumn(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME);
        InstantColumn executedAtColumn = sortedTable.instantColumn(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME);
        IntColumn severityColumn = sortedTable.intColumn(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);
        StringColumn dataStreamColumn = sortedTable.stringColumn(SensorReadoutsColumnNames.DATA_STREAM_NAME_COLUMN_NAME);
        LongColumn checkHashColumn = sortedTable.longColumn(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
        StringColumn checkCategoryColumn = sortedTable.stringColumn(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
        StringColumn checkNameColumn = sortedTable.stringColumn(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
        DoubleColumn actualValueColumn = sortedTable.doubleColumn(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
        for (int i = 0; i < rowCount ; i++) {
            LocalDateTime timePeriod = timePeriodColumn.get(i);
            Instant timePeriodUtc = timePeriodUtcColumn.get(i);
            if (timePeriodUtc == null) {
                timePeriodUtc = timePeriod.atZone(this.defaultTimeZoneProvider.getDefaultTimeZoneId().getRules().getOffset(timePeriod)).toInstant();
            }
            Instant executedAt = executedAtColumn.get(i);
            Integer severity = severityColumn.get(i);
            String dataStreamName = dataStreamColumn.get(i);
            Long checkHash = checkHashColumn.get(i);
            Double actualValue = actualValueColumn.get(i);

            CheckResultsOverviewDataModel checkResultsOverviewDataModel = resultMap.get(checkHash);
            if (checkResultsOverviewDataModel == null) {
                String checkCategory = checkCategoryColumn.get(i);
                String checkName = checkNameColumn.get(i);
                checkResultsOverviewDataModel = new CheckResultsOverviewDataModel() {{
                    setCheckCategory(checkCategory);
                    setCheckName(checkName);
                    setCheckHash(checkHash);
                }};
                resultMap.put(checkHash, checkResultsOverviewDataModel);
            }

            checkResultsOverviewDataModel.appendResult(timePeriod, timePeriodUtc, executedAt, rootChecksContainerSpec.getCheckTimeScale(),
                    severity, actualValue, dataStreamName, loadParameters.getResultsCount());
        }

        resultMap.values().forEach(m -> m.reverseLists());

        return resultMap.values().toArray(CheckResultsOverviewDataModel[]::new);
    }

    /**
     * Retrieves a detailed model of the results of check executions for the given root checks container (group of checks).
     *
     * @param rootChecksContainerSpec Root checks container.
     * @param loadParameters          Load parameters.
     * @return Detailed model of the check results.
     */
    @Override
    public CheckResultsDetailedDataModel[] readCheckStatusesDetailed(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                                     CheckResultsDetailedParameters loadParameters) {
        Map<Long, CheckResultsDetailedDataModel> resultMap = new LinkedHashMap<>();
        HierarchyId checksContainerHierarchyId = rootChecksContainerSpec.getHierarchyId();
        String connectionName = checksContainerHierarchyId.getConnectionName();
        PhysicalTableName physicalTableName = checksContainerHierarchyId.getPhysicalTableName();

        Table ruleResultsTable = loadRecentRuleResults(loadParameters, connectionName, physicalTableName);
        if (ruleResultsTable == null || ruleResultsTable.isEmpty()) {
            return new CheckResultsDetailedDataModel[0]; // empty array
        }

        Table filteredTable = filterTableToRootChecksContainer(rootChecksContainerSpec, ruleResultsTable);
        if (filteredTable.isEmpty()) {
            return new CheckResultsDetailedDataModel[0]; // empty array
        }

        StringColumn dataStreamColumn = filteredTable.stringColumn(SensorReadoutsColumnNames.DATA_STREAM_NAME_COLUMN_NAME);
        List<String> dataStreams = dataStreamColumn.unique().asList().stream().sorted().collect(Collectors.toList());

        if (dataStreams.size() > 1 && dataStreams.contains(CommonTableNormalizationService.ALL_DATA_DATA_STREAM_NAME)) {
            dataStreams.remove(CommonTableNormalizationService.ALL_DATA_DATA_STREAM_NAME);
            dataStreams.add(0, CommonTableNormalizationService.ALL_DATA_DATA_STREAM_NAME);
        }
        
        String selectedDataStream = Objects.requireNonNullElse(loadParameters.getDataStreamName(), dataStreams.get(0));
        Table filteredByDataStream = filteredTable.where(dataStreamColumn.isEqualTo(selectedDataStream));

        if (filteredByDataStream.isEmpty()) {
            return new CheckResultsDetailedDataModel[0]; // empty array
        }

        Table sortedTable = filteredByDataStream.sortDescendingOn(
                SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME, // most recent execution first
                SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME, // then the most recent reading (for partitioned checks) when many partitions were captured
                CheckResultsColumnNames.SEVERITY_COLUMN_NAME); // second on the highest severity first on that time period

        Table workingTable = sortedTable;
        if (loadParameters.getResultsCount() < sortedTable.rowCount()) {
            workingTable = sortedTable.dropRange(loadParameters.getResultsCount(), sortedTable.rowCount());
        }

        for (Row row : workingTable) {
            String id = row.getString(SensorReadoutsColumnNames.ID_COLUMN_NAME);
            Double actualValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME);
            Double expectedValue = TableRowUtility.getSanitizedDoubleValue(row, SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME);
            Double warningLowerBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.WARNING_LOWER_BOUND_COLUMN_NAME);
            Double warningUpperBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.WARNING_UPPER_BOUND_COLUMN_NAME);
            Double errorLowerBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.ERROR_LOWER_BOUND_COLUMN_NAME);
            Double errorUpperBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.ERROR_UPPER_BOUND_COLUMN_NAME);
            Double fatalLowerBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.FATAL_LOWER_BOUND_COLUMN_NAME);
            Double fatalUpperBound = TableRowUtility.getSanitizedDoubleValue(row, CheckResultsColumnNames.FATAL_UPPER_BOUND_COLUMN_NAME);
            Integer severity = row.getInt(CheckResultsColumnNames.SEVERITY_COLUMN_NAME);

            String checkCategory = row.getString(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME);
            String checkDisplayName = row.getString(SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME);
            Long checkHash = row.getLong(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME);
            String checkName = row.getString(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME);
            String checkType = row.getString(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME);

            String columnName = TableRowUtility.getSanitizedStringValue(row, SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME);
            String dataStream = row.getString(SensorReadoutsColumnNames.DATA_STREAM_NAME_COLUMN_NAME);

            Integer durationMs = row.getInt(SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME);
            Instant executedAt = row.getInstant(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME);
            String timeGradient = row.getString(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            LocalDateTime timePeriod = row.getDateTime(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME);

            Boolean includeInKpi = row.getBoolean(CheckResultsColumnNames.INCLUDE_IN_KPI_COLUMN_NAME);
            Boolean includeInSla = row.getBoolean(CheckResultsColumnNames.INCLUDE_IN_SLA_COLUMN_NAME);
            String provider = row.getString(SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME);
            String qualityDimension = row.getString(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME);
            String sensorName = row.getString(SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME);

            CheckResultDetailedSingleModel singleModel = new CheckResultDetailedSingleModel() {{
                setId(id);
                setActualValue(actualValue);
                setExpectedValue(expectedValue);
                setWarningLowerBound(warningLowerBound);
                setWarningUpperBound(warningUpperBound);
                setErrorLowerBound(errorLowerBound);
                setErrorUpperBound(errorUpperBound);
                setFatalLowerBound(fatalLowerBound);
                setFatalUpperBound(fatalUpperBound);
                setSeverity(severity);

                setColumnName(columnName);
                setDataStream(dataStream);

                setDurationMs(durationMs);
                setExecutedAt(executedAt);
                setTimeGradient(timeGradient);
                setTimePeriod(timePeriod);

                setIncludeInKpi(includeInKpi);
                setIncludeInSla(includeInSla);
                setProvider(provider);
                setQualityDimension(qualityDimension);
                setSensorName(sensorName);
            }};
            
            CheckResultsDetailedDataModel checkResultsDetailedDataModel = resultMap.get(checkHash);
            if (checkResultsDetailedDataModel == null) {
                checkResultsDetailedDataModel = new CheckResultsDetailedDataModel() {{
                    setCheckCategory(checkCategory);
                    setCheckName(checkName);
                    setCheckHash(checkHash);
                    setCheckType(checkType);
                    setCheckDisplayName(checkDisplayName);
                    setDataStreamNames(dataStreams);
                    setDataStream(selectedDataStream);
                    setSingleCheckResults(new ArrayList<>());
                }};
                resultMap.put(checkHash, checkResultsDetailedDataModel);
            }

            checkResultsDetailedDataModel.getSingleCheckResults().add(singleModel);
        }

        return resultMap.values().toArray(CheckResultsDetailedDataModel[]::new);
    }

    /**
     * Filters the results to only the results for the target object.
     * @param rootChecksContainerSpec Root checks container to identify the parent column, check type and time scale.
     * @param sourceTable Source table to be filtered.
     * @return Filtered table.
     */
    protected Table filterTableToRootChecksContainer(AbstractRootChecksContainerSpec rootChecksContainerSpec,
                                                     Table sourceTable) {
        String columnName = rootChecksContainerSpec.getHierarchyId().getColumnName(); // nullable
        String checkType = rootChecksContainerSpec.getCheckType().getDisplayName();
        CheckTimeScale timeScale = rootChecksContainerSpec.getCheckTimeScale();

        Selection rowSelection = sourceTable.stringColumn(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME).isEqualTo(checkType);

        if (timeScale != null) {
            StringColumn timeGradientColumn = sourceTable.stringColumn(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME);
            TimeSeriesGradient timeSeriesGradient = timeScale.toTimeSeriesGradient();
            rowSelection = rowSelection.and(timeGradientColumn.isEqualTo(timeSeriesGradient.name()));
        }

        StringColumn columnNameColumn = sourceTable.stringColumn(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME);
        rowSelection = rowSelection.and((columnName != null) ? columnNameColumn.isEqualTo(columnName) : columnNameColumn.isMissing());

        Table filteredTable = sourceTable.where(rowSelection);
        return filteredTable;
    }

    /**
     * Loads all errors and normalizes them to match the column names loaded from the rule results.
     * Adds a fake "severity" column with a value 4, so it is higher even then a fatal severity data quality error.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Errors table or null when no data found.
     */
    protected Table loadErrorsNormalizedToResults(CheckResultsOverviewParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        ErrorsSnapshot errorsSnapshot = this.errorsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, ErrorsColumnNames.COLUMN_NAMES_FOR_ERRORS_OVERVIEW);
        errorsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        Table allErrors = errorsSnapshot.getAllData();
        if (allErrors == null) {
            return null;
        }

        IntColumn severityColumn = IntColumn.create(CheckResultsColumnNames.SEVERITY_COLUMN_NAME, allErrors.rowCount());
        severityColumn.setMissingTo(CheckResultStatus.execution_error.getSeverity()); // severity 0,1,2,3 are success,warning,error,fatal, so a processing error with severity 4 will sort ahead of other severities (processing errors are more severe for the overview)
        allErrors.addColumns(severityColumn);

        return allErrors;
    }

    /**
     * Loads rule results.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with all rule results or null when no data found.
     */
    protected Table loadRuleResults(CheckResultsOverviewParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.COLUMN_NAMES_FOR_RESULTS_OVERVIEW);
        checkResultsSnapshot.ensureMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth());
        Table ruleResultsData = checkResultsSnapshot.getAllData();
        return ruleResultsData;
    }

    /**
     * Loads rule results for a maximum of two months available in the data, within the date range specified in {@code loadParameters}.
     * If the date range is open-ended, only one or none of the range's boundaries are checked.
     * @param loadParameters Load parameters.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with rule results for the most recent two months inside the specified range or null when no data found.
     */
    protected Table loadRecentRuleResults(CheckResultsDetailedParameters loadParameters, String connectionName, PhysicalTableName physicalTableName) {
        CheckResultsSnapshot checkResultsSnapshot = this.checkResultsSnapshotFactory.createReadOnlySnapshot(connectionName,
                physicalTableName, CheckResultsColumnNames.COLUMN_NAMES_FOR_RESULTS_DETAILED);
        int monthsToLoad = 2;
        checkResultsSnapshot.ensureNRecentMonthsAreLoaded(loadParameters.getStartMonth(), loadParameters.getEndMonth(), monthsToLoad);
        Table ruleResultsData = checkResultsSnapshot.getAllData();
        return ruleResultsData;
    }
}
