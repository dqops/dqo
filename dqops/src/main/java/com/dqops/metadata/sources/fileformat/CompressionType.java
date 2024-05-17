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
    zstd;

    public String getFileExtension(){
        switch (this){
            case gzip: return ".gz";
            case zstd: return ".zst";
            default: return null;
        }
    }

}
