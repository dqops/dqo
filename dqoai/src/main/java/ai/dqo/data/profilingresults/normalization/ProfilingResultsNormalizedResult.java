package ai.dqo.data.profilingresults.normalization;

import ai.dqo.data.profilingresults.factory.ProfilerDataScope;
import ai.dqo.data.profilingresults.factory.ProfilingResultsColumnNames;
import ai.dqo.utils.tables.TableColumnUtility;
import tech.tablesaw.api.*;

/**
 * Wrapper over a {@link tech.tablesaw.api.Table} data frame that contains the profiler results. Provides access to typed columns.
 */
public class ProfilingResultsNormalizedResult {
    private final Table table;
    private final StringColumn idColumn;
    private final StringColumn statusColumn;
    private final DateTimeColumn profiledAtColumn;
    private final StringColumn resultTypeColumn;
    private final StringColumn resultStringColumn;
    private final LongColumn resultIntegerColumn;
    private final DoubleColumn resultFloatColumn;
    private final BooleanColumn resultBooleanColumn;
    private final DateColumn resultDateColumn;
    private final DateTimeColumn resultDateTimeColumn;
    private final InstantColumn resultInstantColumn;
    private final TimeColumn resultTimeColumn;
    private final StringColumn scopeColumn;
    private final LongColumn dataStreamHashColumn;
    private final StringColumn dataStreamNameColumn;
    private final LongColumn connectionHashColumn;
    private final StringColumn connectionNameColumn;
    private final StringColumn providerColumn;
    private final LongColumn tableHashColumn;
    private final StringColumn schemaNameColumn;
    private final StringColumn tableNameColumn;
    private final StringColumn tableStageColumn;
    private final LongColumn columnHashColumn;
    private final StringColumn columnNameColumn;
    private final LongColumn profilerHashColumn;
    private final StringColumn profilerNameColumn;
    private final StringColumn profilerTypeColumn;
    private final StringColumn profilerCategoryColumn;
    private final StringColumn sensorNameColumn;
    private final StringColumn timeSeriesIdColumn;
    private final InstantColumn executedAtColumn;
    private final IntColumn durationMsColumn;
    private final StringColumn errorMessageColumn;

    /**
     * Creates a profiler result dataset, extracting key columns.
     * WARNING: this method has an intended side result - it adds missing columns to the table.
     * @param table Sorted table with profiling results - may be modified by adding missing columns.
     */
    public ProfilingResultsNormalizedResult(Table table) {
        this.table = table;
        this.idColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.ID_COLUMN_NAME);
        this.profiledAtColumn = TableColumnUtility.getOrAddDateTimeColumn(table, ProfilingResultsColumnNames.PROFILED_AT_COLUMN_NAME);
        this.statusColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.STATUS_COLUMN_NAME);
        this.resultTypeColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.RESULT_TYPE_COLUMN_NAME);
        this.resultStringColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.RESULT_STRING_COLUMN_NAME);
        this.resultIntegerColumn = TableColumnUtility.getOrAddLongColumn(table, ProfilingResultsColumnNames.RESULT_INTEGER_COLUMN_NAME);
        this.resultFloatColumn = TableColumnUtility.getOrAddDoubleColumn(table, ProfilingResultsColumnNames.RESULT_FLOAT_COLUMN_NAME);
        this.resultBooleanColumn = TableColumnUtility.getOrAddBooleanColumn(table, ProfilingResultsColumnNames.RESULT_BOOLEAN_COLUMN_NAME);
        this.resultDateColumn = TableColumnUtility.getOrAddDateColumn(table, ProfilingResultsColumnNames.RESULT_DATE_COLUMN_NAME);
        this.resultDateTimeColumn = TableColumnUtility.getOrAddDateTimeColumn(table, ProfilingResultsColumnNames.RESULT_DATE_TIME_COLUMN_NAME);
        this.resultInstantColumn = TableColumnUtility.getOrAddInstantColumn(table, ProfilingResultsColumnNames.RESULT_INSTANT_COLUMN_NAME);
        this.resultTimeColumn = TableColumnUtility.getOrAddTimeColumn(table, ProfilingResultsColumnNames.RESULT_TIME_COLUMN_NAME);
        this.scopeColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.SCOPE_COLUMN_NAME);
        this.dataStreamHashColumn = TableColumnUtility.getOrAddLongColumn(table, ProfilingResultsColumnNames.DATA_STREAM_HASH_COLUMN_NAME);
        this.dataStreamNameColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME);
        this.connectionHashColumn = TableColumnUtility.getOrAddLongColumn(table, ProfilingResultsColumnNames.CONNECTION_HASH_COLUMN_NAME);
        this.connectionNameColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.CONNECTION_NAME_COLUMN_NAME);
        this.providerColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.PROVIDER_COLUMN_NAME);
        this.tableHashColumn = TableColumnUtility.getOrAddLongColumn(table, ProfilingResultsColumnNames.TABLE_HASH_COLUMN_NAME);
        this.schemaNameColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.SCHEMA_NAME_COLUMN_NAME);
        this.tableNameColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.TABLE_NAME_COLUMN_NAME);
        this.tableStageColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.TABLE_STAGE_COLUMN_NAME);
        this.columnHashColumn = TableColumnUtility.getOrAddLongColumn(table, ProfilingResultsColumnNames.COLUMN_HASH_COLUMN_NAME);
        this.columnNameColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.COLUMN_NAME_COLUMN_NAME);
        this.profilerHashColumn = TableColumnUtility.getOrAddLongColumn(table, ProfilingResultsColumnNames.PROFILER_HASH_COLUMN_NAME);
        this.profilerNameColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.PROFILER_NAME_COLUMN_NAME);
        this.profilerTypeColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.PROFILER_TYPE_COLUMN_NAME);
        this.profilerCategoryColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.PROFILER_CATEGORY_COLUMN_NAME);
        this.sensorNameColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.SENSOR_NAME_COLUMN_NAME);
        this.timeSeriesIdColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.TIME_SERIES_ID_COLUMN_NAME);
        this.executedAtColumn = TableColumnUtility.getOrAddInstantColumn(table, ProfilingResultsColumnNames.EXECUTED_AT_COLUMN_NAME);
        this.durationMsColumn = TableColumnUtility.getOrAddIntColumn(table, ProfilingResultsColumnNames.DURATION_MS_COLUMN_NAME);
        this.errorMessageColumn = TableColumnUtility.getOrAddStringColumn(table, ProfilingResultsColumnNames.ERROR_MESSAGE_COLUMN_NAME);
    }


    /**
     * Dataset table that was returned from the sensor query.
     * @return Sensor results as a table with a time dimension column and grouping dimension columns.
     */
    public Table getTable() {
        return table;
    }

    /**
     * id column that is a primary for both the sensor_readouts and rule_results tables.
     * @return Check result id (primary key) column.
     */
    public StringColumn getIdColumn() {
        return idColumn;
    }

    /**
     * Returns the instant column that contains a timestamp (utc) when the profiling session was started. All results from all providers
     * executed during that session will share the same timestamp.
     * @return Profiler started at instant column.
     */
    public DateTimeColumn getProfiledAtColumn() {
        return profiledAtColumn;
    }

    /**
     * Returns the column name that holds the status code. Status codes are defined in {@link ai.dqo.data.profilingresults.factory.ProfilerResultStatus}.
     * @return Status column.
     */
    public StringColumn getStatusColumn() {
        return statusColumn;
    }

    /**
     * Returns the column that identifies the result data type retrieved from the profiler.
     * @return result data type.
     */
    public StringColumn getResultTypeColumn() {
        return resultTypeColumn;
    }

    /**
     * Returns the column that contains the profiler results that are strings.
     * @return Column with the string results.
     */
    public StringColumn getResultStringColumn() {
        return resultStringColumn;
    }

    /**
     * Returns the column that contains the profiler result that are integers, stored as a long integer.
     * @return Column with the integer results.
     */
    public LongColumn getResultIntegerColumn() {
        return resultIntegerColumn;
    }

    /**
     * Returns the column that contains the profiler results that are floats (single, double) or a fixed precision decimal or numeric, stored as a double floating value.
     * @return Column with the float results.
     */
    public DoubleColumn getResultFloatColumn() {
        return resultFloatColumn;
    }

    /**
     * Returns the column that contains the profiler results that are boolean.
     * @return Column with the boolean results.
     */
    public BooleanColumn getResultBooleanColumn() {
        return resultBooleanColumn;
    }

    /**
     * Returns the column that contains the profiler results that are dates.
     * @return Column with the date results.
     */
    public DateColumn getResultDateColumn() {
        return resultDateColumn;
    }

    /**
     * Returns the column that contains the profiler results that are date time.
     * @return Column with the date time results.
     */
    public DateTimeColumn getResultDateTimeColumn() {
        return resultDateTimeColumn;
    }

    /**
     * Returns the column that contains the profiler results that are instants.
     * @return Column with the instant results.
     */
    public InstantColumn getResultInstantColumn() {
        return resultInstantColumn;
    }

    /**
     * Returns the column that contains the profiler results that are time.
     * @return Column with the time results.
     */
    public TimeColumn getResultTimeColumn() {
        return resultTimeColumn;
    }

    /**
     * Returns the column that contains a profiler scope. Values are from the {@link ProfilerDataScope} ("table" or "column").
     * @return Profile scope column (contains "table" or "column").
     */
    public StringColumn getScopeColumn() {
        return scopeColumn;
    }

    /**
     * Data stream hash column.
     * @return Data stream hash column.
     */
    public LongColumn getDataStreamHashColumn() {
        return dataStreamHashColumn;
    }

    /**
     * Data stream name column. The data stream name is concatenated from data stream levels and is a user friendly value.
     * @return Data stream name column.
     */
    public StringColumn getDataStreamNameColumn() {
        return dataStreamNameColumn;
    }

    /**
     * Connection hash column. The column contains a 64-bit hash of the connection's hierarchy id.
     * @return Connection hash column.
     */
    public LongColumn getConnectionHashColumn() {
        return connectionHashColumn;
    }

    /**
     * Returns a connection name column. The column contains the source connection name.
     * @return Connection name column.
     */
    public StringColumn getConnectionNameColumn() {
        return connectionNameColumn;
    }

    /**
     * Returns a provider name column. The column contains the provider name.
     * @return Provider name column.
     */
    public StringColumn getProviderColumn() {
        return providerColumn;
    }

    /**
     * Table hash column. The column contains a 64-bit hash of the table's hierarchy id.
     * @return Table hash column.
     */
    public LongColumn getTableHashColumn() {
        return tableHashColumn;
    }

    /**
     * Returns a tablesaw column with the schema name of a table.
     * @return Schema name column.
     */
    public StringColumn getSchemaNameColumn() {
        return schemaNameColumn;
    }

    /**
     * Returns a tablesaw column with the table name.
     * @return Column with the table name.
     */
    public StringColumn getTableNameColumn() {
        return tableNameColumn;
    }

    /**
     * Returns a tablesaw column with the table's stage.
     * @return Table's stage column.
     */
    public StringColumn getTableStageColumn() {
        return tableStageColumn;
    }

    /**
     * Column hash column. The column contains a 64-bit hash of the column's hierarchy id.
     * @return Column hash column.
     */
    public LongColumn getColumnHashColumn() {
        return columnHashColumn;
    }

    /**
     * Returns a tablesaw column that stores the column name. The column with "column names" may contain nulls when checks are defined on a whole table level.
     * @return Column name tablesaw column.
     */
    public StringColumn getColumnNameColumn() {
        return columnNameColumn;
    }

    /**
     * Profiler hash column. The column contains a 64-bit hash of the profiler's hierarchy id.
     * @return Profiler hash column.
     */
    public LongColumn getProfilerHashColumn() {
        return profilerHashColumn;
    }

    /**
     * Returns a tablesaw column with the profiler name.
     * @return Profiler name column.
     */
    public StringColumn getProfilerNameColumn() {
        return profilerNameColumn;
    }

    /**
     * Returns a column that stores the profiler type (table, column).
     * @return Profiler type column.
     */
    public StringColumn getProfilerTypeColumn() {
        return profilerTypeColumn;
    }

    /**
     * Returns the column that stores the profiler category (the node in YAML that is a parent of the group of profilers).
     * @return Quality profiler category name column.
     */
    public StringColumn getProfilerCategoryColumn() {
        return profilerCategoryColumn;
    }

    /**
     * Returns a column with the sensor name.
     * @return Sensor name column.
     */
    public StringColumn getSensorNameColumn() {
        return sensorNameColumn;
    }

    /**
     * Returns a time series id column.
     * @return Column that stores a time series id.
     */
    public StringColumn getTimeSeriesIdColumn() {
        return timeSeriesIdColumn;
    }

    /**
     * Absolute timestamp when the sensor execution started.
     * @return Sensor started timestamp.
     */
    public InstantColumn getExecutedAtColumn() {
        return executedAtColumn;
    }

    /**
     * Sensor execution duration in milliseconds.
     * @return Sensor duration in millis.
     */
    public IntColumn getDurationMsColumn() {
        return durationMsColumn;
    }

    /**
     * Returns the column that stores an error message when the profiler failed to execute.
     * @return Error message column.
     */
    public StringColumn getErrorMessageColumn() {
        return errorMessageColumn;
    }
}
