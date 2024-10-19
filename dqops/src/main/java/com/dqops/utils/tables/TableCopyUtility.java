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
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;
import tech.tablesaw.selection.Selection;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Helper class for making a copy of a table.
 */
public final class TableCopyUtility {
    public static final double FAST_COPY_THRESHOLD = 0.01;

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

    /**
     * Makes a fast copy of a table, by making copies of all columns.
     * The purpose of this function is a slow implementation of the Tablesaw's Table.copy() operation that internally iterates over rows, using a "where" operation.
     * @param sourceTable Source table.
     * @return Cloned table, with full clone of columns and their content.
     */
    public static Table fastTableCopy(Table sourceTable) {
        List<Column<?>> clonedColumns = sourceTable.columns().stream()
                .map(column -> column.copy())
                .collect(Collectors.toList());

        Table clonedTable = Table.create(sourceTable.name(), clonedColumns);
        return clonedTable;
    }

    /**
     * Perform a fast copy of a table, by first capturing the indexes, and then interating over them.
     * @param sourceTable Source table.
     * @param filter Selection filter.
     * @return Copied table.
     */
    public static Table copyTableFiltered(Table sourceTable, Selection filter) {
        int sourceTableRowCount = sourceTable.rowCount();
        int resultRowCount = filter.size();

        if (sourceTableRowCount == resultRowCount) {
            return fastTableCopy(sourceTable);
        }

        if (resultRowCount < sourceTableRowCount * FAST_COPY_THRESHOLD) {
            return sourceTable.where(filter);
        }

        int[] indexes = filter.toArray();

        List<Column<?>> clonedColumns = sourceTable.columns().stream()
                .map(column -> column.emptyCopy(indexes.length))
                .collect(Collectors.toList());

        Table clonedTable = Table.create(sourceTable.name(), clonedColumns);
        for (int i = 0; i < clonedColumns.size(); i++) {
            Column sourceColumn = sourceTable.column(i);
            Column targetColumn = clonedColumns.get(i);

            if (sourceColumn instanceof DoubleColumn) {
                DoubleColumn typedSourceColumn = (DoubleColumn) sourceColumn;
                DoubleColumn typetTargetColumn = (DoubleColumn) targetColumn;

                for (int targetRowIndex = 0; targetRowIndex < indexes.length; targetRowIndex++) {
//                    if (!typedSourceColumn.isMissing(indexes[targetRowIndex])) {
                    typetTargetColumn.set(targetRowIndex, typedSourceColumn.getDouble(indexes[targetRowIndex]));
//                    }
                }
            } else if (sourceColumn instanceof LongColumn) {
                LongColumn typedSourceColumn = (LongColumn) sourceColumn;
                LongColumn typetTargetColumn = (LongColumn) targetColumn;

                for (int targetRowIndex = 0; targetRowIndex < indexes.length; targetRowIndex++) {
                    typetTargetColumn.set(targetRowIndex, typedSourceColumn.getLong(indexes[targetRowIndex]));
                }
            } else if (sourceColumn instanceof IntColumn) {
                IntColumn typedSourceColumn = (IntColumn) sourceColumn;
                IntColumn typetTargetColumn = (IntColumn) targetColumn;

                for (int targetRowIndex = 0; targetRowIndex < indexes.length; targetRowIndex++) {
                    typetTargetColumn.set(targetRowIndex, typedSourceColumn.getInt(indexes[targetRowIndex]));
                }
            } else if (sourceColumn instanceof StringColumn) {
                StringColumn typedSourceColumn = (StringColumn) sourceColumn;
                StringColumn typetTargetColumn = (StringColumn) targetColumn;

                for (int targetRowIndex = 0; targetRowIndex < indexes.length; targetRowIndex++) {
                    typetTargetColumn.set(targetRowIndex, typedSourceColumn.getString(indexes[targetRowIndex]));
                }
            } else if (sourceColumn instanceof DateTimeColumn) {
                DateTimeColumn typedSourceColumn = (DateTimeColumn) sourceColumn;
                DateTimeColumn typetTargetColumn = (DateTimeColumn) targetColumn;

                for (int targetRowIndex = 0; targetRowIndex < indexes.length; targetRowIndex++) {
                    typetTargetColumn.set(targetRowIndex, typedSourceColumn.getLongInternal(indexes[targetRowIndex]));
                }
            } else if (sourceColumn instanceof InstantColumn) {
                InstantColumn typedSourceColumn = (InstantColumn) sourceColumn;
                InstantColumn typetTargetColumn = (InstantColumn) targetColumn;

                for (int targetRowIndex = 0; targetRowIndex < indexes.length; targetRowIndex++) {
                    typetTargetColumn.set(targetRowIndex, typedSourceColumn.getLongInternal(indexes[targetRowIndex]));
                }
            } else if (sourceColumn instanceof TimeColumn) {
                TimeColumn typedSourceColumn = (TimeColumn) sourceColumn;
                TimeColumn typetTargetColumn = (TimeColumn) targetColumn;

                for (int targetRowIndex = 0; targetRowIndex < indexes.length; targetRowIndex++) {
                    typetTargetColumn.set(targetRowIndex, typedSourceColumn.getIntInternal(indexes[targetRowIndex]));
                }
            } else if (sourceColumn instanceof TextColumn) {
                TextColumn typedSourceColumn = (TextColumn) sourceColumn;
                TextColumn typetTargetColumn = (TextColumn) targetColumn;

                for (int targetRowIndex = 0; targetRowIndex < indexes.length; targetRowIndex++) {
                    typetTargetColumn.set(targetRowIndex, typedSourceColumn.getString(indexes[targetRowIndex]));
                }
            } else {
                for (int targetRowIndex = 0; targetRowIndex < indexes.length; targetRowIndex++) {
                    targetColumn.set(targetRowIndex, sourceColumn.get(indexes[targetRowIndex]));
                }
            }
        }

        return clonedTable;
    }
}
