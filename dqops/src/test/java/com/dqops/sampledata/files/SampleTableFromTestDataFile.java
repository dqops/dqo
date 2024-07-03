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

import java.util.HashMap;
import java.util.Map;

/**
 * Sample table holder. Stores a sample data for a single table, with a hash of the data.
 */
public class SampleTableFromTestDataFile {
    private final Table table;
    private final String hashedTableName;
    private final long hash;
    private final Map<String, String> physicalColumnTypes;

    /**
     * Creates a sample table holder.
     * @param table Table content.
     * @param hashedTableName Table name with a hash suffix, this is the name of the file (without the file extension), followed by a hashcode of the table to make the tables unique.
     * @param hash Hash of the table content.
     * @param physicalColumnTypes Map of column names to physical colum names when a column has a hint to use a specified physical data type that is database specific.
     */
    public SampleTableFromTestDataFile(Table table, String hashedTableName, long hash, Map<String, String> physicalColumnTypes) {
        this.table = table;
        this.hashedTableName = hashedTableName;
        this.hash = hash;
        this.physicalColumnTypes = physicalColumnTypes != null ? physicalColumnTypes : new HashMap<>();
    }

    /**
     * Sample table content.
     * @return Sample table.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Table name with a hash suffix. This is the name used to create the table in the target database for integration tests.
     * @return Hashed name.
     */
    public String getHashedTableName() {
        return hashedTableName;
    }

    /**
     * Table hash calculated from the content of the table.
     * @return Table hash.
     */
    public long getHash() {
        return hash;
    }

    /**
     * Returns a dictionary of mapping of column names to their physical data types that are expected.
     * The mapping is optional and may return null if the file has no proposal for a physical data type name that is a database specific.
     * @return Mapping of column names to their physical data types, if a physical data type is requested.
     */
    public Map<String, String> getPhysicalColumnTypes() {
        return physicalColumnTypes;
    }
}
