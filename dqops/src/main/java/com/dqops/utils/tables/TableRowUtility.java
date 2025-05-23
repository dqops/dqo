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
