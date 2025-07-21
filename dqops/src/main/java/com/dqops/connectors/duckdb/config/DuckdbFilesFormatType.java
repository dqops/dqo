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
