package com.dqops.metadata.sources.fileformat.json;

/**
 * The JSON format type supported by DuckDB.
 */
public enum JsonFormatType {
    auto,
    unstructured,
    newline_delimited,
    array
}
