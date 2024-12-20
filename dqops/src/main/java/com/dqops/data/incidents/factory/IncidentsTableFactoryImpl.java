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
package com.dqops.data.incidents.factory;

import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

/**
 * Tablesaw table factory for incidents. Creates an empty table that will store the incidents.
 */
@Component
public class IncidentsTableFactoryImpl implements IncidentsTableFactory {
    /**
     * Creates an empty incidents table that has the right schema.
     * @param tableName Table name.
     * @return Empty incidents table.
     */
    @Override
    public Table createEmptyIncidentsTable(String tableName) {
        Table table = Table.create(tableName);
        table.addColumns(
                StringColumn.create(IncidentsColumnNames.ID_COLUMN_NAME),
                LongColumn.create(IncidentsColumnNames.INCIDENT_HASH_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.SCHEMA_NAME_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.TABLE_NAME_COLUMN_NAME),
                IntColumn.create(IncidentsColumnNames.TABLE_PRIORITY_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.DATA_GROUP_NAME_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.QUALITY_DIMENSION_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.CHECK_CATEGORY_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.CHECK_TYPE_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.CHECK_NAME_COLUMN_NAME),
                IntColumn.create(IncidentsColumnNames.HIGHEST_SEVERITY_COLUMN_NAME),
                IntColumn.create(IncidentsColumnNames.MINIMUM_SEVERITY_COLUMN_NAME),
                InstantColumn.create(IncidentsColumnNames.FIRST_SEEN_COLUMN_NAME),
                InstantColumn.create(IncidentsColumnNames.LAST_SEEN_COLUMN_NAME),
                InstantColumn.create(IncidentsColumnNames.INCIDENT_UNTIL_COLUMN_NAME),
                IntColumn.create(IncidentsColumnNames.FAILED_CHECKS_COUNT_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.ISSUE_URL_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.RESOLVED_BY_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.STATUS_COLUMN_NAME),
                InstantColumn.create(IncidentsColumnNames.CREATED_AT_COLUMN_NAME),
                InstantColumn.create(IncidentsColumnNames.UPDATED_AT_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.CREATED_BY_COLUMN_NAME),
                StringColumn.create(IncidentsColumnNames.UPDATED_BY_COLUMN_NAME));

        return table;
    }
}
