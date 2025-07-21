/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
