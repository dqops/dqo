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

import com.dqops.utils.conversion.NumericTypeConverter;
import com.dqops.utils.string.UniqueStringSet;
import org.apache.commons.lang3.StringUtils;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.Column;

/**
 * Helper class used to retrieve column from a table.
 */
public final class TableColumnUtility {
    /**
     * Finds a named column in the table. Performs a case-insensitive search, so the columns may be named in upper or lower case.
     * @param resultsTable Table to analyze.
     * @param columnName Expected column name.
     * @return Column that was found or null.
     */
    public static Column<?> findColumn(Table resultsTable, String columnName) {
        if (resultsTable.containsColumn(columnName)) {
            return resultsTable.column(columnName);
        }

        for (String existingColumnName: resultsTable.columnNames()) {
            if (StringUtils.equalsIgnoreCase(columnName, existingColumnName)) {
                return resultsTable.column(existingColumnName);
            }
        }

        return null;
    }

    /**
     * Finds a named column in the table. Performs a case-insensitive search, so the columns may be named in upper or lower case.
     * Accepts a list of column names, finds the first name that matches. This method is designed for a case when we renamed a column.
     * @param resultsTable Table to analyze.
     * @param columnNames Expected column names.
     * @return Column that was found or null.
     */
    public static Column<?> findColumn(Table resultsTable, String... columnNames) {
        for (String columnName : columnNames) {
            Column<?> column = findColumn(resultsTable, columnName);
            if (column != null) {
                return column;
            }
        }

        return null;
    }

    /**
     * Creates (or casts) a given column to a {@link TextColumn}.
     * @param sourceColumn Source column.
     * @return TextColumn. It is a copy of the original column (with casting to a string) or the original column object instance if it is a TextColumn.
     */
    public static TextColumn convertToTextColumn(Column<?> sourceColumn) {
        if (sourceColumn instanceof TextColumn) {
            return (TextColumn) sourceColumn;
        }

        int size = sourceColumn.size();
        TextColumn targetColumn = TextColumn.create(sourceColumn.name(), size);

        if (sourceColumn instanceof StringColumn) {
            StringColumn stringColumn = (StringColumn) sourceColumn;
            for (int i = 0; i < size; i++) {
                if (!stringColumn.isMissing(i)) {
                    targetColumn.set(i, stringColumn.get(i));
                }
            }
        }
        else {
            UniqueStringSet uniqueStringSet = new UniqueStringSet();

            if (sourceColumn instanceof NumberColumn<?,?>) {
                NumberColumn<?,?> numberColumn = (NumberColumn<?,?>) sourceColumn;
                for (int i = 0; i < size; i++) {
                    if (!numberColumn.isMissing(i)) {
                        double doubleValue = numberColumn.getDouble(i);
                        targetColumn.set(i, uniqueStringSet.deduplicate(Double.toString(doubleValue)));
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    if (!sourceColumn.isMissing(i)) {
                        targetColumn.set(i, uniqueStringSet.deduplicate(sourceColumn.get(i).toString()));
                    }
                }
            }
        }

        return targetColumn;
    }

    /**
     * Creates (or casts) a given column to a {@link DoubleColumn}.
     * @param sourceColumn Source column.
     * @return DoubleColumn. It is a copy of the original column (with casting to a double) or the original column object instance if it is a DoubleColumn.
     */
    public static DoubleColumn convertToDoubleColumn(Column<?> sourceColumn) {
        if (sourceColumn instanceof DoubleColumn) {
            return (DoubleColumn) sourceColumn;
        }

        if (sourceColumn instanceof IntColumn) {
            return ((IntColumn) sourceColumn).asDoubleColumn();
        }

        if (sourceColumn instanceof LongColumn) {
            return ((LongColumn) sourceColumn).asDoubleColumn();
        }

        if (sourceColumn instanceof FloatColumn) {
            return ((FloatColumn) sourceColumn).asDoubleColumn();
        }

        if (sourceColumn instanceof ShortColumn) {
            return ((ShortColumn) sourceColumn).asDoubleColumn();
        }

        int size = sourceColumn.size();
        DoubleColumn targetColumn = DoubleColumn.create(sourceColumn.name(), size);

        for (int i = 0; i < size; i++) {
            if (!sourceColumn.isMissing(i)) {
                targetColumn.set(i, NumericTypeConverter.toDouble(sourceColumn.get(i)));
            }
        }

        return targetColumn;
    }

    /**
     * Creates (or casts) a given column to a {@link LongColumn}.
     * @param sourceColumn Source column.
     * @return LongColumn. It is a copy of the original column (with casting to a long) or the original column object instance if it is a LongColumn.
     */
    public static LongColumn convertToLongColumn(Column<?> sourceColumn) {
        if (sourceColumn instanceof LongColumn) {
            return (LongColumn) sourceColumn;
        }

        if (sourceColumn instanceof IntColumn) {
            return ((IntColumn) sourceColumn).asLongColumn();
        }

        if (sourceColumn instanceof DoubleColumn) {
            return ((DoubleColumn) sourceColumn).asLongColumn();
        }

        if (sourceColumn instanceof FloatColumn) {
            return ((FloatColumn) sourceColumn).asLongColumn();
        }

        if (sourceColumn instanceof ShortColumn) {
            return ((ShortColumn) sourceColumn).asLongColumn();
        }

        int size = sourceColumn.size();
        LongColumn targetColumn = LongColumn.create(sourceColumn.name(), size);

        for (int i = 0; i < size; i++) {
            if (!sourceColumn.isMissing(i)) {
                targetColumn.set(i, NumericTypeConverter.toLong(sourceColumn.get(i)));
            }
        }

        return targetColumn;
    }

    /**
     * Creates (or casts) a given column to a {@link IntColumn}.
     * @param sourceColumn Source column.
     * @return IntColumn. It is a copy of the original column (with casting to a int) or the original column object instance if it is an IntColumn.
     */
    public static IntColumn convertToIntColumn(Column<?> sourceColumn) {
        if (sourceColumn instanceof IntColumn) {
            return (IntColumn) sourceColumn;
        }

        if (sourceColumn instanceof LongColumn) {
            return ((LongColumn) sourceColumn).asIntColumn();
        }

        if (sourceColumn instanceof DoubleColumn) {
            return ((DoubleColumn) sourceColumn).asIntColumn();
        }

        if (sourceColumn instanceof FloatColumn) {
            return ((FloatColumn) sourceColumn).asIntColumn();
        }

        if (sourceColumn instanceof ShortColumn) {
            return ((ShortColumn) sourceColumn).asIntColumn();
        }

        int size = sourceColumn.size();
        IntColumn targetColumn = IntColumn.create(sourceColumn.name(), size);

        for (int i = 0; i < size; i++) {
            if (!sourceColumn.isMissing(i)) {
                targetColumn.set(i, NumericTypeConverter.toInt(sourceColumn.get(i)));
            }
        }

        return targetColumn;
    }

    /**
     * Retrieves or adds and returns a text column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static TextColumn getOrAddTextColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (TextColumn) table.column(columnName);
        }

        TextColumn newColumn = TextColumn.create(columnName);
        table.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds and returns a double column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static DoubleColumn getOrAddDoubleColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (DoubleColumn) table.column(columnName);
        }

        DoubleColumn newColumn = DoubleColumn.create(columnName);
        table.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds and returns a long column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static LongColumn getOrAddLongColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (LongColumn) table.column(columnName);
        }

        LongColumn newColumn = LongColumn.create(columnName);
        table.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds and returns an int column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static IntColumn getOrAddIntColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (IntColumn) table.column(columnName);
        }

        IntColumn newColumn = IntColumn.create(columnName);
        table.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds and returns a boolean column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static BooleanColumn getOrAddBooleanColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (BooleanColumn) table.column(columnName);
        }

        BooleanColumn newColumn = BooleanColumn.create(columnName);
        table.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds and returns a datetime column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static DateTimeColumn getOrAddDateTimeColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (DateTimeColumn) table.column(columnName);
        }

        DateTimeColumn newColumn = DateTimeColumn.create(columnName);
        table.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds and returns a date column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static DateColumn getOrAddDateColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (DateColumn) table.column(columnName);
        }

        DateColumn newColumn = DateColumn.create(columnName);
        table.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds and returns a time column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static TimeColumn getOrAddTimeColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (TimeColumn) table.column(columnName);
        }

        TimeColumn newColumn = TimeColumn.create(columnName);
        table.addColumns(newColumn);
        return newColumn;
    }

    /**
     * Retrieves or adds and returns an instant column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static InstantColumn getOrAddInstantColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (InstantColumn) table.column(columnName);
        }

        InstantColumn newColumn = InstantColumn.create(columnName);
        table.addColumns(newColumn);
        return newColumn;
    }
}
