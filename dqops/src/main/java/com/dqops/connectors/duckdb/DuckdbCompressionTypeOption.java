package com.dqops.connectors.duckdb;

import com.dqops.metadata.sources.fileformat.CompressionType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Compresison type option used in csv and json format of DuckDB
 */
public enum DuckdbCompressionTypeOption {
    @JsonProperty("auto")
    auto,

    @JsonProperty("none")
    none,

    @JsonProperty("gzip")
    gzip,

    @JsonProperty("zstd")
    zstd;

    public static DuckdbCompressionTypeOption fromCompressionType(CompressionType compressionType){
        if(compressionType == null){
            return DuckdbCompressionTypeOption.auto;
        }
        switch(compressionType){
            case none:
                return DuckdbCompressionTypeOption.none;
            case gzip:
                return DuckdbCompressionTypeOption.gzip;
            case zstd:
                return DuckdbCompressionTypeOption.zstd;
            default: // auto, snappy, lz4
                return DuckdbCompressionTypeOption.auto;
        }
    }

}
