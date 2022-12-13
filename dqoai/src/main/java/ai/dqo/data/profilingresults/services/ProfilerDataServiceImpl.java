package ai.dqo.data.profilingresults.services;

import ai.dqo.core.configuration.DqoProfilerConfigurationProperties;
import ai.dqo.data.profilingresults.factory.ProfilingResultDataType;
import ai.dqo.data.profilingresults.factory.ProfilingResultsColumnNames;
import ai.dqo.data.profilingresults.services.models.ProfilerMetricModel;
import ai.dqo.data.profilingresults.services.models.ProfilerResultsForColumnModel;
import ai.dqo.data.profilingresults.services.models.ProfilerResultsForTableModel;
import ai.dqo.data.profilingresults.snapshot.ProfilingResultsSnapshot;
import ai.dqo.data.profilingresults.snapshot.ProfilingResultsSnapshotFactory;
import ai.dqo.metadata.sources.PhysicalTableName;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Service that provides access to read requested data profiling results.
 */
@Service
public class ProfilerDataServiceImpl implements ProfilerDataService {
    private final ProfilingResultsSnapshotFactory profilingResultsSnapshotFactory;
    private final DqoProfilerConfigurationProperties profilerConfigurationProperties;

    /**
     * Creates a profiler data management service.
     * @param profilingResultsSnapshotFactory Profiler results snapshot factory.
     * @param profilerConfigurationProperties Profiler configuration properties.
     */
    @Autowired
    public ProfilerDataServiceImpl(ProfilingResultsSnapshotFactory profilingResultsSnapshotFactory,
                                   DqoProfilerConfigurationProperties profilerConfigurationProperties) {
        this.profilingResultsSnapshotFactory = profilingResultsSnapshotFactory;
        this.profilerConfigurationProperties = profilerConfigurationProperties;
    }

    /**
     * Retrieves the most recent table profiler results for a given table.
     * @param connectionName Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param dataStreamName Data stream name.
     * @return Profiler results for the given table.
     */
    @Override
    public ProfilerResultsForTableModel getMostRecentProfilerMetricsForTable(String connectionName,
                                                                             PhysicalTableName physicalTableName,
                                                                             String dataStreamName) {
        ProfilerResultsForTableModel tableProfilerResults = new ProfilerResultsForTableModel();
        Table allData = loadProfilerResultsForTable(connectionName, physicalTableName);
        if (allData == null) {
            return tableProfilerResults; // no profiling data
        }
        Table selectedDataStreamData = allData.where(allData.stringColumn(ProfilingResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).isEqualTo(dataStreamName));
        Table sortedResults = selectedDataStreamData.sortDescendingOn(ProfilingResultsColumnNames.PROFILED_AT_COLUMN_NAME);

        StringColumn categoryColumn = sortedResults.stringColumn(ProfilingResultsColumnNames.PROFILER_CATEGORY_COLUMN_NAME);
        StringColumn profilerNameColumn = sortedResults.stringColumn(ProfilingResultsColumnNames.PROFILER_NAME_COLUMN_NAME);
        StringColumn columnNameColumn = sortedResults.stringColumn(ProfilingResultsColumnNames.COLUMN_NAME_COLUMN_NAME);

        int rowCount = sortedResults.rowCount();
        for (int i = 0; i < rowCount ; i++) {
            String category = categoryColumn.get(i);
            String profiler = profilerNameColumn.get(i);

            if (columnNameColumn.isMissing(i)) {
                // table level

                if (tableProfilerResults.getMetrics().stream()
                        .noneMatch(m -> Objects.equal(m.getCategory(), category) && Objects.equal(m.getProfiler(), profiler))) {
                    tableProfilerResults.getMetrics().add(createMetricModel(sortedResults.row(i)));
                }
            }
            else {
                String columnName = columnNameColumn.get(i);
                ProfilerResultsForColumnModel columnModel = tableProfilerResults.getColumns().get(columnName);
                if (columnModel == null) {
                    columnModel = new ProfilerResultsForColumnModel(connectionName, physicalTableName, columnName);
                    tableProfilerResults.getColumns().put(columnName, columnModel);
                }

                if (columnModel.getMetrics().stream()
                        .noneMatch(m -> Objects.equal(m.getCategory(), category) && Objects.equal(m.getProfiler(), profiler))) {
                    columnModel.getMetrics().add(createMetricModel(sortedResults.row(i)));
                }
            }
        }

        return tableProfilerResults;
    }

    /**
     * Retrieves the most recent table profiler results for a given column.
     *
     * @param connectionName    Connection name.
     * @param physicalTableName Full table name (schema and table).
     * @param columName         Column name.
     * @param dataStreamName    Data stream name.
     * @return Profiler results for the given table.
     */
    @Override
    public ProfilerResultsForColumnModel getMostRecentProfilerMetricsForColumn(String connectionName,
                                                                               PhysicalTableName physicalTableName,
                                                                               String columName,
                                                                               String dataStreamName) {
        ProfilerResultsForColumnModel columnProfilerResults = new ProfilerResultsForColumnModel(connectionName, physicalTableName, columName);
        Table allData = loadProfilerResultsForTable(connectionName, physicalTableName);
        if (allData == null) {
            return columnProfilerResults; // no profiling data
        }
        Table selectedDataStreamData = allData.where(allData.stringColumn(ProfilingResultsColumnNames.COLUMN_NAME_COLUMN_NAME).isEqualTo(columName)
                .and(allData.stringColumn(ProfilingResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME).isEqualTo(dataStreamName)));
        Table sortedResults = selectedDataStreamData.sortDescendingOn(ProfilingResultsColumnNames.PROFILED_AT_COLUMN_NAME);

        StringColumn categoryColumn = sortedResults.stringColumn(ProfilingResultsColumnNames.PROFILER_CATEGORY_COLUMN_NAME);
        StringColumn profilerNameColumn = sortedResults.stringColumn(ProfilingResultsColumnNames.PROFILER_NAME_COLUMN_NAME);

        int rowCount = sortedResults.rowCount();
        for (int i = 0; i < rowCount ; i++) {
            String category = categoryColumn.get(i);
            String profiler = profilerNameColumn.get(i);
            if (columnProfilerResults.getMetrics().stream()
                    .noneMatch(m -> Objects.equal(m.getCategory(), category) && Objects.equal(m.getProfiler(), profiler))) {
                columnProfilerResults.getMetrics().add(createMetricModel(sortedResults.row(i)));
            }
        }

        return columnProfilerResults;
    }

    /**
     * Loads profiler results for a given table. Profiler results from the number of months limited by a profiler configuration parameter
     * {@link DqoProfilerConfigurationProperties#getViewedProfileAgeMonths()} are loaded.
     * @param connectionName Connection name.
     * @param physicalTableName Physical table name.
     * @return Table with results or null when no profiler results were found.
     */
    protected Table loadProfilerResultsForTable(String connectionName, PhysicalTableName physicalTableName) {
        ProfilingResultsSnapshot profilingResultsSnapshot = this.profilingResultsSnapshotFactory.createSnapshot(connectionName, physicalTableName);
        LocalDate todayDate = LocalDate.now();
        int monthsToLoad = this.profilerConfigurationProperties.getViewedProfileAgeMonths() - 1;
        if (monthsToLoad < 0 || monthsToLoad > 36) {
            monthsToLoad = 3;
        }
        LocalDate startDate = todayDate.minus(monthsToLoad, ChronoUnit.MONTHS);
        profilingResultsSnapshot.ensureMonthsAreLoaded(startDate, todayDate);

        Table allData = profilingResultsSnapshot.getAllData();
        return allData;
    }

    /**
     * Creates a single metric model.
     * @param row Row with one metric result.
     * @return Metric model.
     */
    public ProfilerMetricModel createMetricModel(Row row) {
        ProfilerMetricModel result = new ProfilerMetricModel();
        result.setCategory(row.getString(ProfilingResultsColumnNames.PROFILER_CATEGORY_COLUMN_NAME));
        result.setProfiler(row.getString(ProfilingResultsColumnNames.PROFILER_NAME_COLUMN_NAME));
        String resultTypeString = row.getString(ProfilingResultsColumnNames.RESULT_TYPE_COLUMN_NAME);
        ProfilingResultDataType profilingResultDataType = ProfilingResultDataType.fromName(resultTypeString);

        switch (profilingResultDataType) {
            case INTEGER:
                result.setResult(row.getLong(ProfilingResultsColumnNames.RESULT_INTEGER_COLUMN_NAME)); // TODO: store also typed values in typed fields
                break;
            case BOOLEAN:
                result.setResult(row.getBoolean(ProfilingResultsColumnNames.RESULT_BOOLEAN_COLUMN_NAME));
                break;
            case FLOAT:
                result.setResult(row.getDouble(ProfilingResultsColumnNames.RESULT_FLOAT_COLUMN_NAME));
                break;
            case STRING:
                result.setResult(row.getString(ProfilingResultsColumnNames.RESULT_STRING_COLUMN_NAME));
                break;
            case INSTANT:
                result.setResult(row.getInstant(ProfilingResultsColumnNames.RESULT_INSTANT_COLUMN_NAME));
                break;
            case DATE:
                result.setResult(row.getDate(ProfilingResultsColumnNames.RESULT_DATE_COLUMN_NAME));
                break;
            case DATETIME:
                result.setResult(row.getDateTime(ProfilingResultsColumnNames.RESULT_DATE_TIME_COLUMN_NAME));
                break;
            case TIME:
                result.setResult(row.getTime(ProfilingResultsColumnNames.RESULT_TIME_COLUMN_NAME));
                break;
            default:
                break;
        }

        return result;
    }
}
