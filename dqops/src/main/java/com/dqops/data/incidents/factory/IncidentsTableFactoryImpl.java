/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
