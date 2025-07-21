/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.duckdb.config;

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
