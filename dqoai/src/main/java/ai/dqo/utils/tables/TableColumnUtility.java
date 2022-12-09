/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.utils.tables;

import tech.tablesaw.api.*;

/**
 * Helper class used to retrieve column from a table.
 */
public final class TableColumnUtility {
    /**
     * Retrieves or adds and returns a string column from a table.
     * @param table Table.
     * @param columnName Column name.
     * @return Existing column or just added column.
     */
    public static StringColumn getOrAddStringColumn(Table table, String columnName) {
        if (table.containsColumn(columnName)) {
            return (StringColumn) table.column(columnName);
        }

        StringColumn newColumn = StringColumn.create(columnName);
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
