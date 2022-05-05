/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.connectors.bigquery;

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
