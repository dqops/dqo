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
package ai.dqo.connectors;

import ai.dqo.metadata.sources.ColumnTypeSnapshotSpec;
import ai.dqo.utils.string.StringCheckUtility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Base class for connection providers that are using SQL.
 */
public abstract class AbstractSqlConnectionProvider implements ConnectionProvider {
    /**
     * Formats a constant for the target database.
     *
     * @param constant   Constant to be formatted.
     * @param columnType Column type snapshot.
     * @return Formatted constant.
     */
    @Override
    public String formatConstant(Object constant, ColumnTypeSnapshotSpec columnType) {
        if (constant == null) {
            return "NULL";
        }

        if (constant instanceof String) {
            String asString = (String)constant;
            return "'" + asString.replace("'", "''") + "'";
        }

        if (constant instanceof Integer) {
            Integer asInteger = (Integer)constant;
            return asInteger.toString();
        }

        if (constant instanceof Short) {
            Short asShort = (Short)constant;
            return asShort.toString();
        }

        if (constant instanceof Long) {
            Long asLong = (Long)constant;
            return asLong.toString();
        }

        if (constant instanceof Double) {
            Double asDouble = (Double)constant;
            return asDouble.toString();
        }

        if (constant instanceof Float) {
            Float asFloat = (Float)constant;
            return asFloat.toString();
        }

        if (constant instanceof Boolean) {
            Boolean asBoolean = (Boolean)constant;
            return asBoolean ? "1" : "0";
        }

        if (constant instanceof LocalDate) {
            LocalDate asLocalDate = (LocalDate)constant;
            return "'" + asLocalDate.format(DateTimeFormatter.ISO_DATE) + "'";
        }

        if (constant instanceof LocalTime) {
            LocalTime asLocalTime = (LocalTime)constant;
            return "'" + asLocalTime.format(DateTimeFormatter.ISO_TIME) + "'";
        }

        if (constant instanceof LocalDateTime) {
            LocalDateTime asLocalTimeTime = (LocalDateTime)constant;
            return "'" + asLocalTimeTime.format(DateTimeFormatter.ISO_DATE_TIME) + "'";
        }

        if (constant instanceof Instant) {
            Instant asInstant = (Instant)constant;
            return "'" + asInstant + "'";
        }

        return constant.toString();
    }

    /**
     * Returns the best matching column type for the type snapshot (real column type returned by the database).
     *
     * @param columnTypeSnapshot Column type snapshot.
     * @return Data type category.
     */
    @Override
    public DataTypeCategory detectColumnType(ColumnTypeSnapshotSpec columnTypeSnapshot) {
        if (columnTypeSnapshot == null || columnTypeSnapshot.getColumnType() == null) {
            return null;
        }

        String columnType = columnTypeSnapshot.getColumnType().toLowerCase(Locale.ENGLISH);
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
            return DataTypeCategory.datetime_instant;
        }

        if (StringCheckUtility.containsAny(columnType, "varchar", "string", "nvarchar", "char", "nchar", "character")) {
            return DataTypeCategory.string;
        }

        if (StringCheckUtility.containsAny(columnType, "text", "clob")) {
            return DataTypeCategory.text;
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
