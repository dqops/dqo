/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.sources.fileformat.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The JSON format type supported by DuckDB.
 */
public enum JsonFormatType {
    @JsonProperty("auto")
    auto,

    @JsonProperty("unstructured")
    unstructured,

    @JsonProperty("newline_delimited")
    newline_delimited,

    @JsonProperty("array")
    array
}
