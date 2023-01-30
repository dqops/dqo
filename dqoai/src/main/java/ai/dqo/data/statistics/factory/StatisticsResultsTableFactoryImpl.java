package ai.dqo.data.statistics.factory;

import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

/**
 * Tablesaw table factory that creates a tabular object used to store the statistics results.
 */
@Component
public class StatisticsResultsTableFactoryImpl implements StatisticsResultsTableFactory {
    /**
     * Creates an empty normalized statistics results (basic profiles) table that has the right schema.
     * @param tableName Table name.
     * @return Empty statistics results table.
     */
    @Override
    public Table createEmptyStatisticsTable(String tableName) {
        Table table = Table.create(tableName);
        table.addColumns(
                StringColumn.create(StatisticsColumnNames.ID_COLUMN_NAME),
                DateTimeColumn.create(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.STATUS_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.RESULT_TYPE_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.RESULT_STRING_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.RESULT_INTEGER_COLUMN_NAME),
                DoubleColumn.create(StatisticsColumnNames.RESULT_FLOAT_COLUMN_NAME),
                BooleanColumn.create(StatisticsColumnNames.RESULT_BOOLEAN_COLUMN_NAME),
                DateColumn.create(StatisticsColumnNames.RESULT_DATE_COLUMN_NAME),
                DateTimeColumn.create(StatisticsColumnNames.RESULT_DATE_TIME_COLUMN_NAME),
                InstantColumn.create(StatisticsColumnNames.RESULT_INSTANT_COLUMN_NAME),
                TimeColumn.create(StatisticsColumnNames.RESULT_TIME_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.SCOPE_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "1"),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "2"),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "3"),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "4"),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "5"),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "6"),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "7"),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "8"),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "9"),
                LongColumn.create(StatisticsColumnNames.DATA_STREAM_HASH_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.DATA_STREAM_NAME_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.CONNECTION_HASH_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.CONNECTION_NAME_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.PROVIDER_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.TABLE_HASH_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.SCHEMA_NAME_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.TABLE_NAME_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.TABLE_STAGE_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.COLUMN_HASH_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.COLLECTOR_HASH_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.TIME_SERIES_ID_COLUMN_NAME),
                InstantColumn.create(StatisticsColumnNames.EXECUTED_AT_COLUMN_NAME),
                IntColumn.create(StatisticsColumnNames.DURATION_MS_COLUMN_NAME),
                StringColumn.create(StatisticsColumnNames.ERROR_MESSAGE_COLUMN_NAME)
        );

        return table;
    }
}
