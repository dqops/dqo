/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.readouts.factory;

import com.dqops.data.checkresults.factory.CheckResultsColumnNames;
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
                StringColumn.create(SensorReadoutsColumnNames.ID_COLUMN_NAME),
                DoubleColumn.create(SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME),
                DoubleColumn.create(SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME),
                DateTimeColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.TIME_PERIOD_UTC_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.TIME_GRADIENT_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "1"),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "2"),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "3"),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "4"),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "5"),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "6"),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "7"),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "8"),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "9"),
                LongColumn.create(SensorReadoutsColumnNames.DATA_GROUP_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUP_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME),
                LongColumn.create(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.CONNECTION_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.PROVIDER_COLUMN_NAME),
                LongColumn.create(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.SCHEMA_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.TABLE_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.TABLE_NAME_PATTERN_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.TABLE_STAGE_COLUMN_NAME),
                IntColumn.create(SensorReadoutsColumnNames.TABLE_PRIORITY_COLUMN_NAME),
                LongColumn.create(SensorReadoutsColumnNames.COLUMN_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.COLUMN_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.COLUMN_NAME_PATTERN_COLUMN_NAME),
                LongColumn.create(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.CHECK_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.CHECK_TYPE_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.CHECK_CATEGORY_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.QUALITY_DIMENSION_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.SENSOR_NAME_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.TIME_SERIES_ID_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.EXECUTED_AT_COLUMN_NAME),
                IntColumn.create(SensorReadoutsColumnNames.DURATION_MS_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.CREATED_AT_COLUMN_NAME),
                InstantColumn.create(SensorReadoutsColumnNames.UPDATED_AT_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.CREATED_BY_COLUMN_NAME),
                StringColumn.create(SensorReadoutsColumnNames.UPDATED_BY_COLUMN_NAME)
        );

        return table;
    }
}
