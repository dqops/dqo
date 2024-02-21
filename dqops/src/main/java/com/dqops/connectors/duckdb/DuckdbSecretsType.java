/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.connectors.duckdb;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Storage providers' credentials for DuckDB
 */
public enum DuckdbSecretsType {
    /**
     * Uses the AWS S3
     */
    @JsonProperty("s3")
    s3,

    /**
     * Uses the Google Cloud Storage
     */
    @JsonProperty("gcs")
    gcs,

    /**
     * Uses the Cloudflare R2
     */
    @JsonProperty("r2")
    r2,

    /**
     * Uses the Azure Blob Storage
     */
    @JsonProperty("azure")
    azure

}
