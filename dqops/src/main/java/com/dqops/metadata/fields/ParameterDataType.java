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
