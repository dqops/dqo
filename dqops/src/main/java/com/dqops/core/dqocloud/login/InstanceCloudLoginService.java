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

import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.secrets.signature.SignedObject;

/**
 * Service that manages communication with DQOps Cloud for authenticating local users using their DQOps Cloud credentials.
 */
public interface InstanceCloudLoginService {
    /**
     * Finds out or derives the base url of the web server of this DQOps instance.
     *
     * @return Base url of this DQOps instance.
     */
    String getReturnBaseUrl();

    /**
     * Returns the ticket granting ticket that should be added as a query parameter to the "/login" page on the DQOps Cloud
     * for performing an identity provider login.
     *
     * @return Ticket granting ticket.
     */
    String getTicketGrantingTicket();

    /**
     * Builds a url to the DQOps Cloud's login page with the ticket granting ticket and the return url.
     *
     * @param returnUrl Return url.
     * @return Url to the DQOps Cloud's login page to redirect to.
     */
    String makeDqoLoginUrl(String returnUrl);

    /**
     * Builds a url to the DQOps Cloud's logout page with the ticket granting ticket and the return url.
     *
     * @param returnUrl Return url.
     * @return Url to the DQOps Cloud's login page to redirect to.
     */
    String makeDqoLogoutUrl(String returnUrl);

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
     * Issues an API key token for the calling user, using a principal. Generates a local API Key independent of the authentication method (DQOps Cloud federated login or a local login).
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
