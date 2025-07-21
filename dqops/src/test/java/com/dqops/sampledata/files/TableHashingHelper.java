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
import tech.tablesaw.columns.Column;

/**
 * Tablesaw table hashing helper. Creates a hashcode for a whole table.
 */
public class TableHashingHelper {
    /**
     * Creates a hash value for the content of the table.
     * @param table Table to hash.
     * @return Hashed table.
     */
    public static long hashTable(Table table) {
        long hash = 0;

        int rowCount = table.rowCount();
        for (Column<?> column : table.columnArray()) {
            if (column.name() != null) {
                hash = hash * 29 + column.name().hashCode();
            }

            for( int i = 0; i < rowCount; i++) {
                Object cellValue = column.get(i);
                int cellHash = cellValue != null ? cellValue.hashCode() : -999;
                hash = hash * 29 + cellHash;
            }
        }

        return Math.abs(hash);
    }
}
