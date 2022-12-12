package ai.dqo.data.profilingresults.factory;

import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

/**
 * Tablesaw table factory that creates a tabular object used to store the profiling results.
 */
@Component
public class ProfilingResultsTableFactoryImpl implements ProfilingResultsTableFactory {
    /**
     * Creates an empty normalized profiling results (profiles) table that has the right schema.
     * @param tableName Table name.
     * @return Empty profiling results table.
     */
    @Override
    public Table createEmptyProfilingResultsTable(String tableName) {
        Table table = Table.create(tableName);
        table.addColumns(
                StringColumn.create(ProfilingResultsColumnNames.ID_COLUMN_NAME),
                DateTimeColumn.create(ProfilingResultsColumnNames.PROFILED_AT_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.STATUS_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.RESULT_TYPE_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.RESULT_STRING_COLUMN_NAME),
                LongColumn.create(ProfilingResultsColumnNames.RESULT_INTEGER_COLUMN_NAME),
                DoubleColumn.create(ProfilingResultsColumnNames.RESULT_FLOAT_COLUMN_NAME),
                BooleanColumn.create(ProfilingResultsColumnNames.RESULT_BOOLEAN_COLUMN_NAME),
                DateColumn.create(ProfilingResultsColumnNames.RESULT_DATE_COLUMN_NAME),
                DateTimeColumn.create(ProfilingResultsColumnNames.RESULT_DATE_TIME_COLUMN_NAME),
                InstantColumn.create(ProfilingResultsColumnNames.RESULT_INSTANT_COLUMN_NAME),
                TimeColumn.create(ProfilingResultsColumnNames.RESULT_TIME_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.SCOPE_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "1"),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "2"),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "3"),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "4"),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "5"),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "6"),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "7"),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "8"),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "9"),
                LongColumn.create(ProfilingResultsColumnNames.DATA_STREAM_HASH_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.DATA_STREAM_NAME_COLUMN_NAME),
                LongColumn.create(ProfilingResultsColumnNames.CONNECTION_HASH_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.CONNECTION_NAME_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.PROVIDER_COLUMN_NAME),
                LongColumn.create(ProfilingResultsColumnNames.TABLE_HASH_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.SCHEMA_NAME_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.TABLE_NAME_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.TABLE_STAGE_COLUMN_NAME),
                LongColumn.create(ProfilingResultsColumnNames.COLUMN_HASH_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.COLUMN_NAME_COLUMN_NAME),
                LongColumn.create(ProfilingResultsColumnNames.PROFILER_HASH_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.PROFILER_NAME_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.PROFILER_TYPE_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.PROFILER_CATEGORY_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.SENSOR_NAME_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.TIME_SERIES_ID_COLUMN_NAME),
                InstantColumn.create(ProfilingResultsColumnNames.EXECUTED_AT_COLUMN_NAME),
                IntColumn.create(ProfilingResultsColumnNames.DURATION_MS_COLUMN_NAME),
                StringColumn.create(ProfilingResultsColumnNames.ERROR_MESSAGE_COLUMN_NAME)
        );

        return table;
    }
}
