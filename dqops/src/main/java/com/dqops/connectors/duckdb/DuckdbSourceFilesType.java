package com.dqops.connectors.duckdb;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DuckDB supported file types to be read as a table.
 */
public enum DuckdbSourceFilesType {
    @JsonProperty("csv")
    csv,

    @JsonProperty("json")
    json,

    @JsonProperty("parquet")
    parquet
}
