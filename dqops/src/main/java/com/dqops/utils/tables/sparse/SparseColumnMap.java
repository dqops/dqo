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

package com.dqops.utils.tables.sparse;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A dictionary of column name to column index map. Provides also information about sparse columns that contain only one value.
 */
public class SparseColumnMap {
    private final Map<String, SparseColumnMetadata> columnsMap = new LinkedHashMap<>();

    public SparseColumnMap() {
    }

    /**
     * Creates a sparse column map from a table.
     * @param table Source table.
     * @return Sparse column map.
     */
    public static SparseColumnMap fromTable(Table table) {
        SparseColumnMap sparseColumnMap = new SparseColumnMap();

        List<Column<?>> columns = table.columns();
        for (int i = 0; i < columns.size(); i++) {
            Column<?> column = columns.get(i);
            SparseColumnMetadata columnMetadata = new SparseColumnMetadata(column.name(), i, column.type(), SparseColumnType.regular, null);
            sparseColumnMap.columnsMap.put(column.name(), columnMetadata);
        }

        return sparseColumnMap;
    }

    /**
     * Returns the column metadata map.
     * @return Column metadata map.
     */
    public final Map<String, SparseColumnMetadata> getColumnsMap() {
        return columnsMap;
    }

    /**
     * Adds an empty column.
     * @param columnName Column name.
     * @param columnType Column type.
     */
    public void addEmptyColumn(String columnName, ColumnType columnType) {
        this.columnsMap.put(columnName, new SparseColumnMetadata(columnName, -1, columnType, SparseColumnType.empty, null));
    }

    /**
     * Adds a column that has the same value for all rows.
     * @param columnName Column name.
     * @param columnType Column type.
     * @param value The value that is on all columns.
     */
    public void addSingleValueColumn(String columnName, ColumnType columnType, Object value) {
        this.columnsMap.put(columnName, new SparseColumnMetadata(columnName, -1, columnType, SparseColumnType.single_value, value));
    }

    /**
     * Adds a regular column to the map.
     * @param columnName Column name.
     * @param columnIndex Column index.
     * @param columnType Column type.
     */
    public void addRegularColumn(String columnName, int columnIndex, ColumnType columnType) {
        this.columnsMap.put(columnName, new SparseColumnMetadata(columnName, columnIndex, columnType, SparseColumnType.regular, null));
    }

    /**
     * Creates a sparse column.
     * @param table Source table.
     * @param rowIndex Row index.
     * @return Sparse column.
     */
    public SparseRow getRow(Table table, int rowIndex) {
        return new SparseRow(table, rowIndex, this);
    }

    /**
     * Finds the column by name and returns its metadata.
     * @param columnName Column name.
     * @return Column metadata.
     */
    public SparseColumnMetadata getColumn(String columnName) {
        return this.columnsMap.get(columnName);
    }
}