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
