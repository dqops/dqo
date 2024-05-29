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
package com.dqops.connectors.storage.azure;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Authentication mode to Azure.
 */
public enum AzureAuthenticationMode {
    /**
     * Uses the connection string
     */
    @JsonProperty("connection_string")
    connection_string,

    /**
     * Uses the credential chain
     */
    @JsonProperty("credential_chain")
    credential_chain,

    /**
     * Uses the service principal
     */
    @JsonProperty("service_principal")
    service_principal,

    /**
     * Uses the DQOps default credential from USER_HOME/.credentials/Azure_default_credentials
     * The authentication is acquired from the file.
     */
    @JsonProperty("default_credentials")
    default_credentials

}
