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
                TextColumn.create(ErrorSamplesColumnNames.ID_COLUMN_NAME),
                DateTimeColumn.create(ErrorSamplesColumnNames.COLLECTED_AT_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.SCOPE_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "1"),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "2"),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "3"),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "4"),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "5"),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "6"),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "7"),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "8"),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_LEVEL_COLUMN_NAME_PREFIX + "9"),
                LongColumn.create(ErrorSamplesColumnNames.DATA_GROUP_HASH_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUP_NAME_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.DATA_GROUPING_CONFIGURATION_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.CONNECTION_HASH_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.CONNECTION_NAME_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.PROVIDER_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.TABLE_HASH_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.SCHEMA_NAME_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.TABLE_NAME_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.TABLE_STAGE_COLUMN_NAME),
                IntColumn.create(ErrorSamplesColumnNames.TABLE_PRIORITY_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.COLUMN_HASH_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.COLUMN_NAME_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.CHECK_HASH_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.CHECK_NAME_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.CHECK_DISPLAY_NAME_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.CHECK_TYPE_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.CHECK_CATEGORY_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.QUALITY_DIMENSION_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.SENSOR_NAME_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.TIME_SERIES_ID_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.RESULT_TYPE_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.RESULT_STRING_COLUMN_NAME),
                LongColumn.create(ErrorSamplesColumnNames.RESULT_INTEGER_COLUMN_NAME),
                DoubleColumn.create(ErrorSamplesColumnNames.RESULT_FLOAT_COLUMN_NAME),
                BooleanColumn.create(ErrorSamplesColumnNames.RESULT_BOOLEAN_COLUMN_NAME),
                DateColumn.create(ErrorSamplesColumnNames.RESULT_DATE_COLUMN_NAME),
                DateTimeColumn.create(ErrorSamplesColumnNames.RESULT_DATE_TIME_COLUMN_NAME),
                InstantColumn.create(ErrorSamplesColumnNames.RESULT_INSTANT_COLUMN_NAME),
                TimeColumn.create(ErrorSamplesColumnNames.RESULT_TIME_COLUMN_NAME),
                IntColumn.create(ErrorSamplesColumnNames.SAMPLE_INDEX_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.SAMPLE_FILTER_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.ID_COLUMN_NAME_PREFIX + "1"),
                TextColumn.create(ErrorSamplesColumnNames.ID_COLUMN_NAME_PREFIX + "1"),
                TextColumn.create(ErrorSamplesColumnNames.ID_COLUMN_NAME_PREFIX + "1"),
                TextColumn.create(ErrorSamplesColumnNames.ID_COLUMN_NAME_PREFIX + "1"),
                TextColumn.create(ErrorSamplesColumnNames.ID_COLUMN_NAME_PREFIX + "1"),
                InstantColumn.create(ErrorSamplesColumnNames.EXECUTED_AT_COLUMN_NAME),
                IntColumn.create(ErrorSamplesColumnNames.DURATION_MS_COLUMN_NAME),
                InstantColumn.create(ErrorSamplesColumnNames.CREATED_AT_COLUMN_NAME),
                InstantColumn.create(ErrorSamplesColumnNames.UPDATED_AT_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.CREATED_BY_COLUMN_NAME),
                TextColumn.create(ErrorSamplesColumnNames.UPDATED_BY_COLUMN_NAME)
        );

        return table;
    }
}
