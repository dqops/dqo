/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.utils.tables;

import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;
import tech.tablesaw.columns.Column;

import java.util.HashMap;
import java.util.List;

/**
 * Helper classes that deduplicate values, decreasing the memory footprint of {@link tech.tablesaw.api.Table} objects.
 */
public class TableCompressUtility {
    /**
     * Finds all duplicate strings in {@link TextColumn} and replaces them with previous values that are unique.
     * NOTE: uses a local hashmap for deduplication, not the Java String's intern() method, which would cause memory leaks and would be slower.
     * @param table Table to internalize (deduplicate).
     */
    public static void internStrings(Table table) {
        if (table == null) {
            return;
        }

        HashMap<String, String> uniqueStrings = new HashMap<>(); // we can use a regular has map here (not a linked hash map)
        List<Column<?>> columns = table.columns();
        int rowCount = table.rowCount();

        for (Column<?> column : columns) {
            if (column instanceof TextColumn) {
                TextColumn textColumn = (TextColumn)column;
                for (int i = 0; i < rowCount; i++) {
                    String originalString = textColumn.getString(i);
                    if (originalString != null) {
                        String oldUniqueValue = uniqueStrings.putIfAbsent(originalString, originalString);
                        if (oldUniqueValue != null) {
                            textColumn.set(i, oldUniqueValue);
                        }
                    }
                }
            }
        }
    }
}
