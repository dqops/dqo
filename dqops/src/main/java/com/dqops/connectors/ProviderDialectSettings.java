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
package com.dqops.connectors;

import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.utils.string.StringCheckUtility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;

import java.util.Locale;

/**
 * Information about the dialect of the target database, like the beginning and ending quotes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class ProviderDialectSettings {
    private String quoteBegin = "\"";
    private String quoteEnd = "\"";
    private String quoteEscape = "\"\"";
    private boolean tableNameIncludesDatabaseName = false;
    private boolean supportsLimitClause = true;

    public ProviderDialectSettings() {
    }

    /**
     * Creates a dialect settings.
     * @param quoteBegin Begin quote.
     * @param quoteEnd End quote.
     * @param quoteEscape Quote escape sequence to replace the end quote.
     * @param tableNameIncludesDatabaseName The fully qualified table name should include a database name.
     */
    public ProviderDialectSettings(String quoteBegin, String quoteEnd, String quoteEscape, boolean tableNameIncludesDatabaseName) {
        this.quoteBegin = quoteBegin;
        this.quoteEnd = quoteEnd;
        this.quoteEscape = quoteEscape;
        this.tableNameIncludesDatabaseName = tableNameIncludesDatabaseName;
    }

    /**
     * Quote beginning character.
     * @return Quote beginning character.
     */
    public String getQuoteBegin() {
        return quoteBegin;
    }

    /**
     * Sets the quote beginning character.
     * @param quoteBegin Quote beginning.
     */
    public void setQuoteBegin(String quoteBegin) {
        this.quoteBegin = quoteBegin;
    }

    /**
     * Quote ending character.
     * @return Quote ending.
     */
    public String getQuoteEnd() {
        return quoteEnd;
    }

    /**
     * Sets the quote ending character.
     * @param quoteEnd Quote ending.
     */
    public void setQuoteEnd(String quoteEnd) {
        this.quoteEnd = quoteEnd;
    }

    /**
     * End quote escape sequence that is used when the end quote was found in the identifier and the end quote must be replaced.
     * @return Quote escape sequence.
     */
    public String getQuoteEscape() {
        return quoteEscape;
    }

    /**
     * Sets the quote escape sequence.
     * @param quoteEscape Quote escape sequence.
     */
    public void setQuoteEscape(String quoteEscape) {
        this.quoteEscape = quoteEscape;
    }

    /**
     * Returns if the fully qualified table name should be made of three elements: database.schema.table.
     * The database name is taken from the connection configuration.
     * @return The table must include a database name.
     */
    public boolean isTableNameIncludesDatabaseName() {
        return tableNameIncludesDatabaseName;
    }

    /**
     * Configures if a fully qualified table should be made of three elements: database.schema.table or only schema.table.
     * @param tableNameIncludesDatabaseName True when three elements (also a database) must be rendered, false otherwise.
     */
    public void setTableNameIncludesDatabaseName(boolean tableNameIncludesDatabaseName) {
        this.tableNameIncludesDatabaseName = tableNameIncludesDatabaseName;
    }

    /**
     * The driver supports a LIMIT keyword.
     * @return True when the connection supports a LIMIT keyword.
     */
    public boolean isSupportsLimitClause() {
        return supportsLimitClause;
    }

    /**
     * Sets a flag that the query supports a LIMIT keyword.
     * @param supportsLimitClause True when the LIMIT keyword is supported.
     */
    public void setSupportsLimitClause(boolean supportsLimitClause) {
        this.supportsLimitClause = supportsLimitClause;
    }

    /**
     * Quotes a given identifier.
     * @param identifier Identifier to be quoted.
     * @return Quoted identifier.
     */
    public String quoteIdentifier(String identifier) {
        if (Strings.isNullOrEmpty(identifier)) {
            return identifier;
        }

        return this.quoteBegin + identifier.replace(this.quoteEnd, this.quoteEscape) + this.quoteEnd;
    }

    /**
     * Returns the best matching column type for the type snapshot (real column type returned by the database).
     * Provider dialect settings subclasses that are specific to database providers may override this method.
     *
     * @param columnTypeSnapshot Column type snapshot.
     * @return Data type category.
     */
    public DataTypeCategory detectColumnType(ColumnTypeSnapshotSpec columnTypeSnapshot) {
        if (columnTypeSnapshot == null || columnTypeSnapshot.getColumnType() == null) {
            return null;
        }

        String columnType = columnTypeSnapshot.getColumnType().toLowerCase(Locale.ROOT);
        if (StringCheckUtility.containsAny(columnType, "array")) {
            return DataTypeCategory.array;
        }

        if (StringCheckUtility.containsAny(columnType, "struct", "record", "table")) {
            return DataTypeCategory.other;
        }

        if (StringCheckUtility.containsAny(columnType, "int", "integer", "byte", "short", "long", "bigint", "smallint", "tinyint", "byteint")) {
            return DataTypeCategory.numeric_integer;
        }

        if (StringCheckUtility.containsAny(columnType, "numeric", "decimal", "number")) {
            return DataTypeCategory.numeric_decimal;
        }

        if (StringCheckUtility.containsAny(columnType, "float", "double", "real")) {
            return DataTypeCategory.numeric_float;
        }

        if (StringCheckUtility.containsAny(columnType, "bool", "boolean", "bit")) {
            return DataTypeCategory.bool;
        }

        if (StringCheckUtility.containsAny(columnType, "datetime", "timestamp_ntz")) {
            return DataTypeCategory.datetime_datetime;
        }

        if (StringCheckUtility.containsAny(columnType, "date")) {
            return DataTypeCategory.datetime_date;
        }

        if (StringCheckUtility.containsAny(columnType, "timestamp")) {
            return DataTypeCategory.datetime_timestamp;
        }

        if (StringCheckUtility.containsAny(columnType, "varchar", "string", "nvarchar", "char", "nchar", "character")) {
            return DataTypeCategory.text;
        }

        if (StringCheckUtility.containsAny(columnType, "text", "clob", "ntext", "nclob")) {
            return DataTypeCategory.clob;
        }

        if (StringCheckUtility.containsAny(columnType, "varbinary", "binary")) {
            return DataTypeCategory.binary;
        }

        if (StringCheckUtility.containsAny(columnType, "json")) {
            return DataTypeCategory.json;
        }

        return DataTypeCategory.other;
    }
}
