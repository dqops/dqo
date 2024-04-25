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

import com.dqops.utils.exceptions.DqoRuntimeException;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Helper class for making a copy of a table.
 */
public final class TableCopyUtility {
    /**
     * Creates a new table that contains only the columns specified. The column objects are not cloned,
     * they are copied by reference and should not be modified.
     * @param table Source table.
     * @param columns A list of columns to preserve.
     * @return A new table with a subset of columns.
     */
    public static Table extractColumns(Table table, String... columns) {
        Map<String, ? extends Column<?>> currentColumns = table.columns().stream()
                .collect(Collectors.toMap(c -> c.name(), c -> c));

        List<Column<?>> filteredColumns = Arrays.stream(columns)
                .map(colName -> {
                    Column<?> column = currentColumns.get(colName);
                    if (column == null) {
                        throw new DqoRuntimeException("Column " + colName + " not found");
                    }
                    return column;
                })
                .collect(Collectors.toList());

        Table filteredTable = Table.create(table.name(), filteredColumns);
        return filteredTable;
    }
}
