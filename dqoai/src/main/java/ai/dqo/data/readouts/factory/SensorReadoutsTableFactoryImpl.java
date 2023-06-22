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
package ai.dqo.data.readouts.factory;

import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

/**
 * Factory that creates an empty tablesaw table for storing the sensor readouts. The table schema is configured.
 */
@Component
public class SensorReadoutsTableFactoryImpl implements SensorReadoutsTableFactory {
    /**
     * Creates an empty normalized sensor readouts table that has the right schema.
     * @param tableName Table name.
     * @return Empty sensor readouts table.
     */
    public Table createEmptySensorReadoutsTable(String tableName) {
        Table table = Table.create(tableName);
        table.addColumns(
                TextColumn.create(SensorReadoutsColumnNames.ID_COLUMN_NAME),
                DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DoubleColumn.create(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "1"),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "2"),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "3"),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "4"),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "5"),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "6"),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "7"),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "8"),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "9"),
                LongColumn.create(SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME),
                LongColumn.create(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.CONNECTION_NAME_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME),
                LongColumn.create(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.SCHEMA_NAME_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.TABLE_NAME_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.TABLE_NAME_PATTERN_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.TABLE_STAGE_COLUMN_NAME),
                IntColumn.create(SensorReadoutsColumnNames.TABLE_PRIORITY_COLUMN_NAME),
                LongColumn.create(SensorReadoutsColumnNames.COLUMN_HASH_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.COLUMN_NAME_PATTERN_COLUMN_NAME),
                LongColumn.create(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.TIME_SERIES_ID_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME),
                IntColumn.create(SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.CREATED_AT_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.UPDATED_AT_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.CREATED_BY_COLUMN_NAME),
                TextColumn.create(SensorReadoutsColumnNames.UPDATED_BY_COLUMN_NAME)
        );

        return table;
    }
}
