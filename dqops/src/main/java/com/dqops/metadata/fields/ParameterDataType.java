/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.fields;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumeration of supported field types that are used as sensor or rule parameters.
 */
public enum ParameterDataType {
    @JsonProperty("string")
    string_type,

    @JsonProperty("boolean")
    boolean_type,

    @JsonProperty("integer")
    integer_type,

    @JsonProperty("long")
    long_type,

    @JsonProperty("double")
    double_type,

    @JsonProperty("date")
    date_type,

    @JsonProperty("datetime")
    datetime_type,

    @JsonProperty("column_name")
    column_name_type, // this is a string, but should match a known column name from the same table

    @JsonProperty("enum")
    enum_type,

    @JsonProperty("string_list")
    string_list_type,

    @JsonProperty("integer_list")
    integer_list_type,

    @JsonProperty("object")
    object_type
}
