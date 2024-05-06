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
