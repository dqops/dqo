/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.sources;

import com.dqops.metadata.id.HierarchyId;

/**
 * Object mother for TableSpec.
 */
public class TableSpecObjectMother {
    /**
     * Creates a table schema for a given schema.table name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @return Table spec.
     */
    public static TableSpec create(String schemaName, String tableName) {
        PhysicalTableName physicalTableName = new PhysicalTableName() {{
            setSchemaName(schemaName);
            setTableName(tableName);
        }};

        TableSpec tableSpec = new TableSpec() {{
            setPhysicalTableName(physicalTableName);
            setHierarchyId(HierarchyId.makeHierarchyIdForTable("connection", physicalTableName));
        }};
        return tableSpec;
    }

    /**
     * Adds a column to a table.
     * @param tableSpec Target table spec to add the column.
     * @param columnName Column name.
     * @param columnSpec Column spec.
     * @return Column spec that was passed (for chaining).
     */
    public static ColumnSpec addColumn(TableSpec tableSpec, String columnName, ColumnSpec columnSpec) {
        tableSpec.getColumns().put(columnName, columnSpec);
        return columnSpec;
    }
}
