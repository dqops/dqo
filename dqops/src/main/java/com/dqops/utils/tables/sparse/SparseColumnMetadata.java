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
