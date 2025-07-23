/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.dqocloud.client;

import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.core.principal.UserDomainIdentity;

/**
 * DQOps Cloud API client factory.
 */
public interface DqoCloudApiClientFactory {
    /**
     * Creates an unauthenticated API client for the DQOps Cloud.
     * @return Unauthenticated client.
     */
    ApiClient createUnauthenticatedClient();

    /**
     * Creates an authenticated API client for the DQOps Cloud. The authentication uses the API Key that must be obtained by running the "login" CLI command.
     * @param userIdentity Calling user identity, required to select the data domain.
     * @return Authenticated client.
     */
    ApiClient createAuthenticatedClient(UserDomainIdentity userIdentity);
}
