package com.dqops.connectors.duckdb.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DuckDB read mode.
 */
public enum DuckdbReadMode {
    @JsonProperty("in_memory")
    in_memory,

    @JsonProperty("files")  //duckdb_file
    files
}
