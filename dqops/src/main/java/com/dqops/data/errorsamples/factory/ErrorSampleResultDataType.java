/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
