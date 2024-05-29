package com.dqops.metadata.sources.fileformat;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CompressionType {

    @JsonProperty("none")
    none,

    @JsonProperty("auto")
    auto,

    @JsonProperty("gzip")
    gzip,

    @JsonProperty("zstd")
    zstd,

    @JsonProperty("snappy")
    snappy,

    @JsonProperty("lz4")
    lz4;

    // todo: add support when brotli is implemented by duckdb
    //  https://github.com/duckdb/duckdb/pull/12103
//    @JsonProperty("brotli")
//    brotli;

    public String getCompressionExtension(){
        switch (this){
            case gzip: return ".gz";
            case zstd: return ".zst";
            case snappy: return ".snappy";
            case lz4: return ".lz4";
            default: return null;
        }
    }

}
