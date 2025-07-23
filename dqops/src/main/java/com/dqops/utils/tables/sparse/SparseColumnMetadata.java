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

/**
 * Description of a sparse column. Identifies the column type, name, and how sparse it is (empty, single value).
 */
public final class SparseColumnMetadata {
    private final String name;
    private int index;
    private final ColumnType columnType;
    private SparseColumnType sparseType;
    private final Object singleValue;

    public SparseColumnMetadata(String name, int index, ColumnType columnType, SparseColumnType sparseType, Object singleValue) {
        this.name = name;
        this.index = index;
        this.columnType = columnType;
        this.sparseType = sparseType;
        this.singleValue = singleValue;
    }

    /**
     * Returns the column name.
     * @return Column name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the column index.
     * @return Column index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns the column type.
     * @return Column type.
     */
    public ColumnType getColumnType() {
        return columnType;
    }

    /**
     * Returns the type of sparseness.
     * @return How sparse is this column.
     */
    public SparseColumnType getSparseType() {
        return sparseType;
    }

    /**
     * A single value that should be present in all records if this column is sparse.
     * @return Single value.
     */
    public Object getSingleValue() {
        return singleValue;
    }

    /**
     * Changes a column type to a regular column.
     * @param columnIndex Column index.
     */
    public void changeToRegular(int columnIndex) {
        this.index = columnIndex;
        this.sparseType = SparseColumnType.regular;
    }
}
