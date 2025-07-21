/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sampledata.files;

import tech.tablesaw.api.Table;

import java.util.Map;

/**
 * Internal object that contains the file content and the proposed physical data types.
 */
public class TestDataSampleFileContent {
    private final Table table;
    private final Map<String, String> columnPhysicalDataTypes;

    public TestDataSampleFileContent(Table table, Map<String, String> columnPhysicalDataTypes) {
        this.table = table;
        this.columnPhysicalDataTypes = columnPhysicalDataTypes;
    }

    /**
     * The loaded content of a table.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Mapping of proposed physical column types (may be empty) or miss some columns.
     * @return Mapping of physical data types for selected columns.
     */
    public Map<String, String> getColumnPhysicalDataTypes() {
        return columnPhysicalDataTypes;
    }
}
