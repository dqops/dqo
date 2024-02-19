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
