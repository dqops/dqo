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

package com.dqops.core.dqocloud.login;

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.secrets.signature.SignedObject;

/**
 * Service that manages communication with DQO Cloud for authenticating local users using their DQO Cloud credentials.
 */
public interface InstanceCloudLoginService {
    /**
     * Finds out or derives the base url of the web server of this DQO instance.
     *
     * @return Base url of this DQO instance.
     */
    String getReturnBaseUrl();

    /**
     * Returns the ticket granting ticket that should be added as a query parameter to the "/login" page on the DQO Cloud
     * for performing an identity provider login.
     *
     * @return Ticket granting ticket.
     */
    String getTicketGrantingTicket();

    /**
     * Builds a url to the DQO Cloud's login page with the ticket granting ticket and the return url.
     *
     * @param returnUrl Return url.
     * @return Url to the DQO Cloud's login page to redirect to.
     */
    String makeDqoLoginUrl(String returnUrl);

    /**
     * Creates a signed authentication token from a refresh token.
     * @param refreshToken Refresh token.
     * @return Signed authentication token.
     */
    SignedObject<DqoUserTokenPayload> issueDqoUserAuthenticationToken(String refreshToken);

    /**
     * Issues an API key token for the calling user.
     * @param sourceUserToken Source user token.
     * @return Signed API Key token.
     */
    SignedObject<DqoUserTokenPayload> issueApiKey(DqoUserTokenPayload sourceUserToken);

    /**
     * Issues an API key token for the calling user, using a principal. Generates a local API Key independent of the authentication method (DQO Cloud federated login or a local login).
     * @param principal User principal
     * @return Signed API Key token.
     */
    SignedObject<DqoUserTokenPayload> issueApiKey(DqoUserPrincipal principal);

    /**
     * Verifies and decodes the authentication token. Throws an exception if the authentication token is invalid or has expired.
     * @param authenticationToken Authentication token.
     * @return Decoded authentication token.
     */
    SignedObject<DqoUserTokenPayload> verifyAuthenticationToken(String authenticationToken);
}
