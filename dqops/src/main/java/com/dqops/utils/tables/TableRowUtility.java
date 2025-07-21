/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.utils.tables;

import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

/**
 * Helper class used to operate on rows (eg. retrieve sanitized values).
 */
public class TableRowUtility {
    /**
     * Retrieves a String from a string-type column on a provided row. If string is missing, returns null.
     * @param row Row.
     * @param columnName Column name (it must be a string-type column).
     * @return String with the value from the row, or null if value is missing.
     */
    public static String getSanitizedStringValue(Row row, String columnName) {
        if (row.isMissing(columnName)) {
            return null;
        }
        return row.getString(columnName);
    }

    /**
     * Retrieves a Double from a double-type column on a provided row. If double is missing, returns null.
     * @param row Row.
     * @param columnName Column name (it must be a double-type column).
     * @return Double with the value from the row, or null if value is missing.
     */
    public static Double getSanitizedDoubleValue(Row row, String columnName) {
        if (row.isMissing(columnName)) {
            return null;
        }
        return row.getDouble(columnName);
    }

    /**
     * Appends a row to a table without constructing a {@link Row} object.
     * @param table Table to append a record.
     * @return Index of the new record.
     */
    public static int appendRow(Table table) {
        for (final Column<?> column : table.columns()) {
            column.appendMissing();
        }

        return table.rowCount() - 1;
    }
}
