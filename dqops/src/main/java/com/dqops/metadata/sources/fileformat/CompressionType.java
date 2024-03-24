package com.dqops.metadata.sources.fileformat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Compresison type option used in csv and json format of DuckDB
 */
public enum CompressionType {
    @JsonProperty("auto")
    auto,

    @JsonProperty("none")
    none,

    @JsonProperty("gzip")
    gzip,

    @JsonProperty("zstd")
    zstd
}
