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
 * Storage providers' for DuckDB.
 */
public enum DuckdbStorageType {
    /**
     * Uses the local file system.
     */
    @JsonProperty("local")
    local,

    /**
     * Uses the AWS S3.
     */
    @JsonProperty("s3")
    s3,

    /**
     * Uses the Azure Blob Storage.
     */
    @JsonProperty("azure")
    azure,

    /**
     * Uses the Google Cloud Storage.
     */
    @JsonProperty("gcs")
    gcs,

    // todo: uncomment when implemented
//    /**
//     * Uses the Cloudflare R2.
//     */
//    @JsonProperty("r2")
//    r2,

}
