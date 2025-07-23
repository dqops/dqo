/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.storage.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Authentication mode to AWS.
 */
public enum AwsAuthenticationMode {
    /**
     * Uses the IAM credentials to connection to AWS, as access key id and secret access key
     */
    @JsonProperty("iam")
    iam,

    /**
     * Uses the default credentials set in DQOps.
     */
    @JsonProperty("default_credentials")
    default_credentials,

}
