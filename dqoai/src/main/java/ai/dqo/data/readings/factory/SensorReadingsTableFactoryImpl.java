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
package ai.dqo.data.readings.factory;

import ai.dqo.data.readings.normalization.SensorNormalizedResult;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

/**
 * Factory that creates an empty tablesaw table for storing the sensor readouts. The table schema is configured.
 */
@Component
public class SensorReadingsTableFactoryImpl implements SensorReadingsTableFactory {
    /**
     * Creates an empty normalized sensor readouts table that has the right schema.
     * @param tableName Table name.
     * @return Empty sensor readouts table.
     */
    public Table createEmptySensorReadoutsTable(String tableName) {
        Table table = Table.create(tableName);
        table.addColumns(
                DoubleColumn.create(SensorNormalizedResult.ACTUAL_VALUE_COLUMN_NAME),
                DoubleColumn.create(SensorNormalizedResult.EXPECTED_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorNormalizedResult.TIME_PERIOD_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.TIME_GRADIENT_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "1"),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "2"),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "3"),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "4"),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "5"),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "6"),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "7"),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "8"),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_LEVEL_COLUMN_NAME_PREFIX + "9"),
                LongColumn.create(SensorNormalizedResult.DATA_STREAM_HASH_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.DATA_STREAM_NAME_COLUMN_NAME),
                LongColumn.create(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.CONNECTION_NAME_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.PROVIDER_COLUMN_NAME),
                LongColumn.create(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.SCHEMA_NAME_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.TABLE_NAME_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.TABLE_STAGE_COLUMN_NAME),
                LongColumn.create(SensorNormalizedResult.COLUMN_HASH_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.COLUMN_NAME_COLUMN_NAME),
                LongColumn.create(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.CHECK_NAME_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.CHECK_TYPE_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.CHECK_CATEGORY_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.QUALITY_DIMENSION_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.SENSOR_NAME_COLUMN_NAME),
                StringColumn.create(SensorNormalizedResult.TIME_SERIES_UUID_COLUMN_NAME),
                InstantColumn.create(SensorNormalizedResult.EXECUTED_AT_COLUMN_NAME),
                IntColumn.create(SensorNormalizedResult.DURATION_MS_COLUMN_NAME)
        );

        return table;
    }
}
