/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.rest.server.authentication;

/**
 * Authentication method type.
 */
public enum DqoAuthenticationMethod {
    /**
     * Disable authentication. The instance works for the local user.
     */
    none,

    /**
     * Use the DQOps Cloud as an identity provider (requires a paid version of DQOps).
     */
    dqops_cloud,

    /**
     * Use OAuth2 for authentication to support on-premise authentication. Requires a paid version of DQOps.
     */
    oauth2
}
