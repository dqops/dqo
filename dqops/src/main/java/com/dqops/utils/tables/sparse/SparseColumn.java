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

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

/**
 * Sparse column wrapper.
 * @param <T>
 */
public class SparseColumn<T extends Column<?>> {
    private SparseColumnMetadata columnMetadata;
    private T column;
    private Table parentTable;

    public SparseColumn(SparseColumnMetadata columnMetadata, T column, Table parentTable) {
        this.columnMetadata = columnMetadata;
        this.column = column;
        this.parentTable = parentTable;
    }

    /**
     * Returns the column metadata.
     * @return Column metadata.
     */
    public SparseColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    /**
     * Returns the column, but only when it is a regular (non-sparse) column.
     * @return Column.
     */
    public T getColumn() {
        return column;
    }

    /**
     * Returns true if this is a sparse column (empty, or a uniform single value).
     * @return True when a sparse colum, false when a regular column.
     */
    public boolean isSparse() {
        return this.column == null;
    }

    /**
     * Returns the column's name.
     *
     * @return name as String
     */
    public String name() {
        return this.columnMetadata.getName();
    }

    /**
     * Returns this column's ColumnType
     *
     * @return {@link ColumnType}
     */
    public ColumnType type() {
        return this.columnMetadata.getColumnType();
    }

    /**
     * Returns a string representation of the value at the given row.
     *
     * @param row The index of the row.
     * @return value as String
     */
    public String getString(int row) {
        if (this.column != null) {
            return this.column.getString(row);
        }

        if (this.columnMetadata.getSparseType() == SparseColumnType.empty) {
            return "";
        }

        return this.columnMetadata.getSingleValue().toString();
    }

    /** Returns true if the value at rowNumber is missing */
    public boolean isMissing(int rowNumber) {
        if (this.column != null) {
            return this.column.isMissing(rowNumber);
        }

        if (this.columnMetadata.getSparseType() == SparseColumnType.empty) {
            return true;
        }

        return false;
    }
}
