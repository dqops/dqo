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

import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

/**
 * Custom wrapper over tables that is lighter than {@link tech.tablesaw.api.Row} from Tablesaw, and reuses a dictionary of column name to index mapping.
 */
public class SparseRow {
    private Table table;
    private int rowIndex;
    private SparseColumnMap sparseColumnMap;

    public SparseRow(Table table, int rowIndex, SparseColumnMap sparseColumnMap) {
        this.table = table;
        this.rowIndex = rowIndex;
        this.sparseColumnMap = sparseColumnMap;
    }

    /**
     * Returns the original table.
     * @return Original table.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Row index in the original table.
     * @return Row index.
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Returns the column name to column index map.
     * @return Column index map.
     */
    public SparseColumnMap getSparseColumnMap() {
        return sparseColumnMap;
    }

    /**
     * Materializes a sparse column, changing it to a regular column and adding it to the table.
     * @param columnName Colum name.
     */
    public void materializeColumn(String columnName) {
        SparseColumnMetadata sparseColumnMetadata = this.sparseColumnMap.getColumnsMap().get(columnName);
        if (sparseColumnMetadata.getSparseType() == SparseColumnType.regular) {
            return;
        }

        Column<?> column = sparseColumnMetadata.getColumnType().create(sparseColumnMetadata.getName());
        int rowCount = this.table.rowCount();

        if (sparseColumnMetadata.getSparseType() == SparseColumnType.empty) {
            for (int i = 0; i < rowCount; i++) {
                column.appendMissing();
            }
        } else if (sparseColumnMetadata.getSparseType() == SparseColumnType.single_value) {
            Object singleValue = sparseColumnMetadata.getSingleValue();
            for (int i = 0; i < rowCount; i++) {
                column.appendObj(singleValue);
            }
        }

        this.table.addColumns(column);
        sparseColumnMetadata.changeToRegular(this.table.columnCount() - 1);
    }

    /**
     * Returns true if the value at columnName is missing, and false otherwise
     */
    public boolean isMissing(String columnName) {
        SparseColumnMetadata sparseColumnMetadata = this.sparseColumnMap.getColumnsMap().get(columnName);
        if (sparseColumnMetadata.getSparseType() == SparseColumnType.empty) {
            return true;
        }

        if (sparseColumnMetadata.getSparseType() == SparseColumnType.single_value) {
            return false;
        }

        return this.table.column(sparseColumnMetadata.getIndex()).isMissing(this.rowIndex);
    }

    // TODO: add other methods, such as getString, getInt...
}
