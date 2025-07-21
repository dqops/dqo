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

import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import org.springframework.security.core.Authentication;

/**
 * DQOps authentication token factory that creates Spring Security {@link org.springframework.security.core.Authentication} token
 * with a user principal.
 */
public interface DqoAuthenticationTokenFactory {
    /**
     * Creates an anonymous user token with no roles assigned and no identity.
     *
     * @return Anonymous user token.
     */
    Authentication createAnonymousToken();

    /**
     * Creates an authenticated DQOps user principal that is identified by the DQOps Cloud API key stored in the local DQOps instance.
     * This type of authentication is used when authentication via DQOps Cloud is not used and a single user is accessing a local DQOps instance, having
     * full (ADMIN) access rights when the DQOps Cloud API key is not present (not working with a DQOps Cloud connection) or limited the role in the DQOps Cloud API Key.
     * @param dataDomain Data domain name.
     *
     * @return Authenticated user principal, based on the identity stored in the DQOps Cloud API Key.
     */
    Authentication createAuthenticatedWithDefaultDqoCloudApiKey(String dataDomain);

    /**
     * Creates an authentication principal from a DQOps Cloud issued user token. User tokens are issued for multi-user accounts.
     * @param userTokenPayload User token payload.
     * @param dataDomain Data domain name.
     * @return Authenticated user principal, based on the user token.
     */
    Authentication createAuthenticatedWithUserToken(DqoUserTokenPayload userTokenPayload, String dataDomain);
}
