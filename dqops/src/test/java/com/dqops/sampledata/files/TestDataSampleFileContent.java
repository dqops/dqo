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
