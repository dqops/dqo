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

import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
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
                StringColumn.create(SensorReadoutsNormalizedResult.ID_COLUMN_NAME),
                DoubleColumn.create(SensorReadoutsNormalizedResult.ACTUAL_VALUE_COLUMN_NAME),
                DoubleColumn.create(SensorReadoutsNormalizedResult.EXPECTED_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsNormalizedResult.TIME_PERIOD_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.TIME_GRADIENT_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "1"),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "2"),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "3"),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "4"),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "5"),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "6"),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "7"),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "8"),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "9"),
                LongColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.DATA_STREAM_NAME_COLUMN_NAME),
                LongColumn.create(SensorReadoutsNormalizedResult.CONNECTION_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.CONNECTION_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.PROVIDER_COLUMN_NAME),
                LongColumn.create(SensorReadoutsNormalizedResult.TABLE_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.SCHEMA_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.TABLE_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.TABLE_STAGE_COLUMN_NAME),
                LongColumn.create(SensorReadoutsNormalizedResult.COLUMN_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.COLUMN_NAME_COLUMN_NAME),
                LongColumn.create(SensorReadoutsNormalizedResult.CHECK_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.CHECK_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.CHECK_DISPLAY_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.CHECK_TYPE_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.CHECK_CATEGORY_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.QUALITY_DIMENSION_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.SENSOR_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsNormalizedResult.TIME_SERIES_ID_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsNormalizedResult.EXECUTED_AT_COLUMN_NAME),
                IntColumn.create(SensorReadoutsNormalizedResult.DURATION_MS_COLUMN_NAME)
        );

        return table;
    }
}
