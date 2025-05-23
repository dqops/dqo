package com.dqops.connectors.duckdb.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DuckDB supported file types to be read as a table.
 */
public enum DuckdbFilesFormatType {
    @JsonProperty("csv")
    csv,

    @JsonProperty("json")
    json,

    @JsonProperty("parquet")
    parquet,

    @JsonProperty("avro")
    avro,

    @JsonProperty("iceberg")
    iceberg,

    @JsonProperty("delta_lake")
    delta_lake
}
