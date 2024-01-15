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
