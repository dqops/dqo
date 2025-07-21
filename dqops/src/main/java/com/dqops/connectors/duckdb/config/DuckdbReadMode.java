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
 * DuckDB read mode.
 */
public enum DuckdbReadMode {
    @JsonProperty("in_memory")
    in_memory,

    @JsonProperty("files")  //duckdb_file
    files
}
