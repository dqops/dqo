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
package com.dqops.connectors.jdbc;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.io.jdbc.SqlResultSetReader;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Initializes the format of non-standard JDBC data types. Performs one-time initialization of data type support. */
public class JdbcTypeColumnMapping {
    private static boolean isInitializedJdbc;
    private static final Object lock = new Object();

    private static final int MIN_TYPE_ID = -5000;
    private static final int MAX_TYPE_ID = 5000;
    private static final boolean[] JDBC_TYPES_WITH_MAPPINGS = new boolean[-MIN_TYPE_ID + MAX_TYPE_ID + 1];

    /**
     * A copy of mappings of JDBC type codes to tablesaw column types from the tech.tablesaw.io.jdbc.SqlResultSetReader class in tablesaw.
     * It is not exposed as a public field.
     */
    private static final Map<Integer, ColumnType> TABLESAW_BUILT_IN_MAPPINGS = new LinkedHashMap<>() {{
        put(Types.BOOLEAN, ColumnType.BOOLEAN);
        put(Types.BIT, ColumnType.BOOLEAN);
        put(Types.DECIMAL, ColumnType.DOUBLE);
        put(Types.DOUBLE, ColumnType.DOUBLE);
        put(Types.FLOAT, ColumnType.DOUBLE);
        put(Types.NUMERIC, ColumnType.DOUBLE);
        put(Types.REAL, ColumnType.FLOAT);
        // Instant, LocalDateTime, OffsetDateTime and ZonedDateTime are often mapped to
        // timestamp
        put(Types.TIMESTAMP, ColumnType.INSTANT);
        put(Types.INTEGER, ColumnType.INTEGER);
        put(Types.DATE, ColumnType.LOCAL_DATE);
        put(Types.TIME, ColumnType.LOCAL_TIME);
        put(Types.BIGINT, ColumnType.LONG);
        put(Types.SMALLINT, ColumnType.SHORT);
        put(Types.TINYINT, ColumnType.SHORT);
        put(Types.BINARY, ColumnType.STRING);
        put(Types.CHAR, ColumnType.STRING);
        put(Types.NCHAR, ColumnType.STRING);
        put(Types.NVARCHAR, ColumnType.STRING);
        put(Types.VARCHAR, ColumnType.STRING);
        put(Types.LONGVARCHAR, ColumnType.TEXT);
        put(Types.LONGNVARCHAR, ColumnType.TEXT);
    }};

    /**
     * Additional dictionary of type mappings to support JDBC drivers that DQOps uses.
     */
    private static final Map<Integer, ColumnType> DQOPS_TYPE_MAPPINGS = new LinkedHashMap<>() {{
        put(-155, ColumnType.INSTANT);
        put(-101, ColumnType.INSTANT);

        // Timestamp with time zone -> LocalDateTime (https://github.com/tlabs-data/tablesaw-parquet#data-type-conversion)
        put(2014, ColumnType.LOCAL_DATE_TIME);

        put(2000, ColumnType.LONG);

        put(-3, ColumnType.STRING); // Oracle RAW(1000) type
        put(101, ColumnType.DOUBLE); // Oracle BINARY DOUBLE type
    }};

    /**
     * Checks if the the JDBC type has a type mapping configured, or should be converted to a text.
     * @param jdbcTypeCode JDBC type code.
     * @return True when it has a type mapping, false when the value must be converted to a string.
     */
    public static boolean hasJdbcTypeMappingConfigured(int jdbcTypeCode) {
        if (jdbcTypeCode < MIN_TYPE_ID || jdbcTypeCode > MAX_TYPE_ID) {
            return false;
        }

        return JDBC_TYPES_WITH_MAPPINGS[jdbcTypeCode + (-MIN_TYPE_ID)];
    }

    /**
     * Sets a flag that this type code has a configuration to use a dedicated data type.
     * @param jdbcTypeCode JDBC type code to test.
     */
    public static void setJdbcTypeMappingConfiguredOn(int jdbcTypeCode) {
        if (jdbcTypeCode < MIN_TYPE_ID || jdbcTypeCode > MAX_TYPE_ID) {
            return;
        }

        JDBC_TYPES_WITH_MAPPINGS[jdbcTypeCode + (-MIN_TYPE_ID)] = true;
    }

    /**
     * Ensures that data type format support for JDB is initialized exactly once.
     * This method must be called in read and write operations of non-standard data types for JDBC.
     */
    public static void ensureInitializedJdbc() {
        synchronized (lock) {
            if (isInitializedJdbc) {
                return;
            }

            for (Integer jdbcTypeCode : TABLESAW_BUILT_IN_MAPPINGS.keySet()) {
                setJdbcTypeMappingConfiguredOn(jdbcTypeCode);
            }

            for (Map.Entry<Integer, ColumnType> dqopsColumnTypeEntry : DQOPS_TYPE_MAPPINGS.entrySet()) {
                setJdbcTypeMappingConfiguredOn(dqopsColumnTypeEntry.getKey());
                SqlResultSetReader.mapJdbcTypeToColumnType(dqopsColumnTypeEntry.getKey(), dqopsColumnTypeEntry.getValue());
            }

            // map all unknown types to collect their samples as strings
            for (int jdbcTypeCode = MIN_TYPE_ID; jdbcTypeCode <= MAX_TYPE_ID; jdbcTypeCode++) {
                if (!hasJdbcTypeMappingConfigured(jdbcTypeCode)) {
                    SqlResultSetReader.mapJdbcTypeToColumnType(jdbcTypeCode, ColumnType.STRING);
                }
            }

            isInitializedJdbc = true;
        }
    }
}
