/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
