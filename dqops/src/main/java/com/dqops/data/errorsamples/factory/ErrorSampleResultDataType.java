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
package com.dqops.data.errorsamples.factory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of data types that were detected as the error sample collector result.
 */
public enum ErrorSampleResultDataType {
    @JsonProperty("null")
    NULL("null"),

    @JsonProperty("boolean")
    BOOLEAN("boolean"),

    @JsonProperty("string")
    STRING("string"),

    @JsonProperty("integer")
    INTEGER("integer"),

    @JsonProperty("float")
    FLOAT("float"),

    @JsonProperty("date")
    DATE("date"),

    @JsonProperty("datetime")
    DATETIME("datetime"),

    @JsonProperty("instant")
    INSTANT("instant"),

    @JsonProperty("time")
    TIME("time");

    private String name;

    ErrorSampleResultDataType(String name) {
        this.name = name;
    }

    /**
     * Returns the data type name.
     * @return Data type name name.
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a error sample result data type from the name.
     * @param name result data type name.
     * @return Error sample result type.
     */
    public static ErrorSampleResultDataType fromName(String name) {
        switch (name) {
            case "null":
                return NULL;
            case "boolean":
                return BOOLEAN;
            case "string":
                return STRING;
            case "integer":
                return INTEGER;
            case "float":
                return FLOAT;
            case "date":
                return DATE;
            case "datetime":
                return DATETIME;
            case "instant":
                return INSTANT;
            case "time":
                return TIME;
            default:
                throw new IllegalArgumentException("Unsupported value " + name);
        }
    }
}
