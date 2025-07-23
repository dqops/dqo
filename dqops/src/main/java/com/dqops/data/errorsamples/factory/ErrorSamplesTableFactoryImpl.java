/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.errorsamples.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

/**
 * Factory that creates an empty tablesaw table for storing the error samples (samples captured form a column). The table schema is configured.
 */
@Component
public class ErrorSamplesTableFactoryImpl implements ErrorSamplesTableFactory {
    @Autowired
    public ErrorSamplesTableFactoryImpl() {
    }

    /**
     * Creates an empty normalized sensor execution error samples table that has the right schema.
     *
     * @param tableName Table name.
     * @return Empty error samples table.
     */
    @Override
    public Table createEmptyErrorSamplesTable(String tableName) {
        Table table = Table.create(tableName);
        table.addColumns(
                StringColumn.create(ErrorSamplesColumnNames.ID_COLUMN_NAME),
                DateTimeColumn.create(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.SCOPE_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "1"),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "2"),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "3"),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "4"),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "5"),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "6"),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "7"),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "8"),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "9"),
                LongColumn.create(ErrorSamplesColumnNames.DATA_GROUP_HASH_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.CONNECTION_HASH_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.CONNECTION_NAME_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.PROVIDER_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.TABLE_HASH_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.SCHEMA_NAME_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.TABLE_NAME_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.TABLE_STAGE_COLUMN_NAME),
                IntColumn.create(ErrorSamplesColumnNames.TABLE_PRIORITY_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.COLUMN_HASH_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.CHECK_HASH_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.TIME_GRADIENT_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.QUALITY_DIMENSION_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.TABLE_COMPARISON_NAME_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.TIME_SERIES_ID_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.RESULT_TYPE_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.RESULT_STRING_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME),
                DoubleColumn.create(ErrorSamplesColumnNames.RESULT_FLOAT_COLUMN_NAME),
                BooleanColumn.create(ErrorSamplesColumnNames.RESULT_BOOLEAN_COLUMN_NAME),
                DateColumn.create(ErrorSamplesColumnNames.RESULT_DATE_COLUMN_NAME),
                DateTimeColumn.create(ErrorSamplesColumnNames.RESULT_DATE_TIME_COLUMN_NAME),
                InstantColumn.create(ErrorSamplesColumnNames.RESULT_INSTANT_COLUMN_NAME),
                TimeColumn.create(ErrorSamplesColumnNames.RESULT_TIME_COLUMN_NAME),
                IntColumn.create(ErrorSamplesColumnNames.SAMPLE_INDEX_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.SAMPLE_FILTER_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "1"),
                StringColumn.create(ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "2"),
                StringColumn.create(ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "3"),
                StringColumn.create(ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "4"),
                StringColumn.create(ErrorSamplesColumnNames.ROW_ID_COLUMN_NAME_PREFIX + "5"),
                InstantColumn.create(ErrorSamplesColumnNames.EXECUTED_AT_COLUMN_NAME),
                IntColumn.create(ErrorSamplesColumnNames.DURATION_MS_COLUMN_NAME),
                InstantColumn.create(ErrorSamplesColumnNames.CREATED_AT_COLUMN_NAME),
                InstantColumn.create(ErrorSamplesColumnNames.UPDATED_AT_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.CREATED_BY_COLUMN_NAME),
                StringColumn.create(ErrorSamplesColumnNames.UPDATED_BY_COLUMN_NAME)
        );

        return table;
    }
}
