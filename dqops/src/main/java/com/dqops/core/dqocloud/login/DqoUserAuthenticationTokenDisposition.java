/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.dqocloud.login;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The purpose of the authentication token.
 */
public enum DqoUserAuthenticationTokenDisposition {
    /**
     * This token is a refresh token and can only be used to issue an authentication token.
     */
    @JsonProperty("rt")
    REFRESH_TOKEN,

    /**
     * This token is an authentication token that was issued from a refresh token.
     */
    @JsonProperty("at")
    AUTHENTICATION_TOKEN,

    /**
     * This token is an API Key.
     */
    @JsonProperty("ak")
    API_KEY
}
