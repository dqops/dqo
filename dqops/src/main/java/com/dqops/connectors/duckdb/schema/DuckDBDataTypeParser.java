/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.duckdb.schema;

/**
 * Parser that is able to parse data types from DuckDB.
 */
public interface DuckDBDataTypeParser {
    /**
     * Parses a text in the <code>dataType</code> into an object that describes the field. Parses also structures and arrays.
     * @param dataType Data type to parse.
     * @param fieldName Field name to store in the result object (because the root data type has no field name, only nested fields contain a name).
     * @return Data type, also for nested structures.
     */
    DuckDBField parseFieldType(String dataType, String fieldName);
}
