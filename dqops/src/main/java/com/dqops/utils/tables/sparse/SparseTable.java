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

import com.dqops.utils.exceptions.DqoRuntimeException;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

import java.util.Collection;

/**
 * A custom subclass of the {@link tech.tablesaw.api.Table} that also stores sparse columns (empty or uniform single value).
 */
public class SparseTable extends Table {
    private SparseColumnMap sparseColumnMap;

    /**
     * Returns a new Table initialized with the given names and columns
     *
     * @param name    The name of the table
     * @param columns One or more columns, all of which must have either the same length or size 0
     */
    protected SparseTable(String name, Column<?>... columns) {
        super(name, columns);
        this.sparseColumnMap = SparseColumnMap.fromTable(this);
    }

    /**
     * Returns a new Table initialized with the given names and columns
     *
     * @param name    The name of the table
     * @param columns One or more columns, all of which must have either the same length or size 0
     */
    protected SparseTable(String name, Collection<Column<?>> columns) {
        super(name, columns);
        this.sparseColumnMap = SparseColumnMap.fromTable(this);
    }

    /**
     * Creates a sparse table.
     * @param name Table name.
     * @param columns Array of columns.
     * @return Sparse table.
     */
    public static SparseTable create(String name, Column<?>... columns) {
        return new SparseTable(name, columns);
    }

    /**
     * Creates a sparse table.
     * @param name Table name.
     * @param columns Collection of columns.
     * @return Sparse table.
     */
    public static SparseTable create(String name, Collection<Column<?>> columns) {
        return new SparseTable(name, columns);
    }

    /**
     * Creates or extracts a sparse table from a given table. If the <code>table</code> is a regular
     * {@link Table}, then returns a copy of the table as a sparse table.
     * But when the table is already a sparse table, returns it.
     * @param table Source table.
     * @return Sparse table.
     */
    public static SparseTable fromTable(Table table) {
        if (table instanceof SparseTable) {
            return (SparseTable) table;
        }

        return create(table.name(), table.columns());
    }

    /**
     * Creates a sparse table from a non-sparse table that was deserialized from Parquet and may be missing some columns.
     * Finds missing columns that are in the template table (an empty table that is a template of a correct table schema) and registers them as empty columns.
     * @param table Source table.
     * @return Sparse table.
     */
    public static SparseTable makeFromSparseTableAndTemplate(Table table, Table fullTableTemplate) {
        if (table instanceof SparseTable) {
            throw new DqoRuntimeException("This operation can be called only on a fresh table that was just deserialized form Parquet and is missing some columns");
        }

        SparseTable sparseTable = create(table.name(), table.columns());
        for (Column<?> templateColumn : fullTableTemplate.columns()) {
            if (sparseTable.sparseColumnMap.getColumn(templateColumn.name()) == null) {
                sparseTable.sparseColumnMap.addEmptyColumn(templateColumn.name(), templateColumn.type());
            }
        }

        return sparseTable;
    }

    /**
     * Adds the given column to this table. Column must either be empty or have size() == the
     * rowCount() of the table they're being added to. Column names in the table must remain unique.
     *
     * @param cols Columns to be added.
     */
    @Override
    public Table addColumns(Column<?>... cols) {
        int columnCount = this.columnCount();
        super.addColumns(cols);

        for (int i = 0; i < cols.length; i++) {
            Column<?> column = cols[i];
            this.sparseColumnMap.addRegularColumn(column.name(), i + columnCount, column.type());
        }

        return this;
    }

    /**
     * Returns the column with the given columnName, ignoring case
     *
     * @param columnName
     */
    @Override
    public Column<?> column(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.column(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Returns a sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column.
     */
    public SparseColumn<Column<?>> columnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        Column<?> column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.column(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a BooleanColumn with the given name if it is present in this Relation. If the column is
     * of a different type, a ClassCastException is thrown
     *
     * @param columnName
     */
    @Override
    public BooleanColumn booleanColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.booleanColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a boolean sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<BooleanColumn> booleanColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        BooleanColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.booleanColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a NumericColumn with the given name if it is present in this Relation. If the column is
     * not Numeric, a ClassCastException is thrown
     *
     * @param columnName
     */
    @Override
    public NumericColumn<?> numberColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.numberColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a numeric sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<NumericColumn<?>> numberColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        NumericColumn<?> column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.numberColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a DoubleColumn with the given name if it is present in this Relation. If the column is
     * not of type DOUBLE, a ClassCastException is thrown
     *
     * @param columnName
     */
    @Override
    public DoubleColumn doubleColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.doubleColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a double sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<DoubleColumn> doubleColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        DoubleColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.doubleColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns an IntColumn with the given name if it is present in this Relation. If the column has a
     * different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public IntColumn intColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.intColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a int sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<IntColumn> intColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        IntColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.intColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a ShortColumn with the given name if it is present in this Relation. If the column has
     * a different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public ShortColumn shortColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.shortColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a short sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<ShortColumn> shortColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        ShortColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.shortColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a LongColumn with the given name if it is present in this Relation. If the column has a
     * different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public LongColumn longColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.longColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a long sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<LongColumn> longColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        LongColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.longColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a FloatColumn with the given name if it is present in this Relation. If the column has
     * a different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public FloatColumn floatColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.floatColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a float sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<FloatColumn> floatColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        FloatColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.floatColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a DateColumn with the given name if it is present in this Relation. If the column has a
     * different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public DateColumn dateColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.dateColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a date sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<DateColumn> dateColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        DateColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.dateColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a TimeColumn with the given name if it is present in this Relation. If the column has a
     * different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public TimeColumn timeColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.timeColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a time sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<TimeColumn> timeColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        TimeColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.timeColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a StringColumn with the given name if it is present in this Relation. If the column has
     * a different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public StringColumn stringColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.stringColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a string sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<StringColumn> stringColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        StringColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.stringColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a TextColumn with the given name if it is present in this Relation. If the column has a
     * different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public TextColumn textColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.textColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a text sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<TextColumn> textColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        TextColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.textColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns a DateTimeColumn with the given name if it is present in this Relation. If the column
     * has a different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public DateTimeColumn dateTimeColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.dateTimeColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a datetime sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<DateTimeColumn> dateTimeColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        DateTimeColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.dateTimeColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }

    /**
     * Returns an InstantColumn with the given name if it is present in this Relation. If the column
     * has a different type, a ClassCastException is thrown.
     *
     * @param columnName
     */
    @Override
    public InstantColumn instantColumn(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        if (columnMetadata.getSparseType() == SparseColumnType.regular) {
            return this.instantColumn(columnMetadata.getIndex());
        }

        return null;
    }

    /**
     * Extracts a instant sparse column wrapper.
     * @param columnName Column name.
     * @return Sparse column wrapper.
     */
    public SparseColumn<InstantColumn> instantColumnSparse(String columnName) {
        SparseColumnMetadata columnMetadata = this.sparseColumnMap.getColumn(columnName);
        InstantColumn column = columnMetadata.getSparseType() == SparseColumnType.regular ?
                this.instantColumn(columnMetadata.getIndex()) : null;

        return new SparseColumn<>(columnMetadata, column, this);
    }
}
