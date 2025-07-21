/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.bigquery;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Authentication mode to Google BigQuery.
 */
public enum BigQueryAuthenticationMode {
    /**
     * Default application credentials retrieved from the Service Account Key when running on a GCP VM,
     * the environment variable GOOGLE_APPLICATION_CREDENTIALS or credentials obtained by gcloud auth application-default login
     */
    @JsonProperty("google_application_credentials")
    google_application_credentials,

    /**
     * JSON key content provided directly, it should be a name of an environment variable that holds the key
     * or a GCP Secret Manager's secret: ${sm://secret_name}
     */
    @JsonProperty("json_key_content")
    json_key_content,

    /**
     * Path to a JSON key file.
     */
    @JsonProperty("json_key_path")
    json_key_path
}
