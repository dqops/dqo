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
