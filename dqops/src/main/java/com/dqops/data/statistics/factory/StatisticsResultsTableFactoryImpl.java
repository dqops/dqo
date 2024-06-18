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
package com.dqops.data.statistics.factory;

import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
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
                TextColumn.create(StatisticsColumnNames.ID_COLUMN_NAME),
                DateTimeColumn.create(StatisticsColumnNames.COLLECTED_AT_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.STATUS_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.RESULT_TYPE_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.RESULT_STRING_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.RESULT_INTEGER_COLUMN_NAME),
                DoubleColumn.create(StatisticsColumnNames.RESULT_FLOAT_COLUMN_NAME),
                BooleanColumn.create(StatisticsColumnNames.RESULT_BOOLEAN_COLUMN_NAME),
                DateColumn.create(StatisticsColumnNames.RESULT_DATE_COLUMN_NAME),
                DateTimeColumn.create(StatisticsColumnNames.RESULT_DATE_TIME_COLUMN_NAME),
                InstantColumn.create(StatisticsColumnNames.RESULT_INSTANT_COLUMN_NAME),
                TimeColumn.create(StatisticsColumnNames.RESULT_TIME_COLUMN_NAME),
                IntColumn.create(StatisticsColumnNames.SAMPLE_INDEX_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.SAMPLE_COUNT_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.SCOPE_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "1"),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "2"),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "3"),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "4"),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "5"),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "6"),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "7"),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "8"),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "9"),
                LongColumn.create(StatisticsColumnNames.DATA_GROUP_HASH_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.DATA_GROUP_NAME_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.CONNECTION_HASH_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.CONNECTION_NAME_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.PROVIDER_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.TABLE_HASH_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.SCHEMA_NAME_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.TABLE_NAME_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.TABLE_STAGE_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.COLUMN_HASH_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.COLUMN_NAME_COLUMN_NAME),
                LongColumn.create(StatisticsColumnNames.COLLECTOR_HASH_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.COLLECTOR_NAME_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.COLLECTOR_TARGET_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.COLLECTOR_CATEGORY_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.SENSOR_NAME_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.TIME_SERIES_ID_COLUMN_NAME),
                InstantColumn.create(StatisticsColumnNames.EXECUTED_AT_COLUMN_NAME),
                IntColumn.create(StatisticsColumnNames.DURATION_MS_COLUMN_NAME),
                TextColumn.create(StatisticsColumnNames.ERROR_MESSAGE_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.CREATED_AT_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.UPDATED_AT_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.CREATED_BY_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.UPDATED_BY_COLUMN_NAME)
        );

        return table;
    }
}
