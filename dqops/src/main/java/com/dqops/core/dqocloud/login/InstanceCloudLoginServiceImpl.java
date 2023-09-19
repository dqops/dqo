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

import com.dqops.cloud.rest.api.RefreshTokenIssueApi;
import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.cloud.rest.model.UserLoginTicketGrantingTicketRequest;
import com.dqops.core.configuration.DqoCloudConfigurationProperties;
import com.dqops.core.configuration.DqoInstanceConfigurationProperties;
import com.dqops.core.configuration.ServerConfigurationProperties;
import com.dqops.core.configuration.ServerSslConfigurationProperties;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.client.DqoCloudApiClientFactory;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.secrets.signature.InstanceSignatureKeyProvider;
import com.dqops.core.secrets.signature.SignatureService;
import com.dqops.core.secrets.signature.SignedObject;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

/**
 * Service that manages communication with DQO Cloud for authenticating local users using their DQO Cloud credentials.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InstanceCloudLoginServiceImpl implements InstanceCloudLoginService {
    private InstanceSignatureKeyProvider instanceSignatureKeyProvider;
    private SignatureService signatureService;
    private DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private DqoCloudApiClientFactory dqoCloudApiClientFactory;
    private DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties;
    private ServerConfigurationProperties serverConfigurationProperties;
    private ServerSslConfigurationProperties serverSslConfigurationProperties;
    private DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private SignedObject<UserLoginTicketGrantingTicketPayload> grantingTicketPayloadSignedObject;
    private final Object lock = new Object();

    @Autowired
    public InstanceCloudLoginServiceImpl(InstanceSignatureKeyProvider instanceSignatureKeyProvider,
                                         SignatureService signatureService,
                                         DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                         DqoCloudApiClientFactory dqoCloudApiClientFactory,
                                         DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties,
                                         ServerConfigurationProperties serverConfigurationProperties,
                                         ServerSslConfigurationProperties serverSslConfigurationProperties,
                                         DqoCloudConfigurationProperties dqoCloudConfigurationProperties) {
        this.instanceSignatureKeyProvider = instanceSignatureKeyProvider;
        this.signatureService = signatureService;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
        this.dqoInstanceConfigurationProperties = dqoInstanceConfigurationProperties;
        this.serverConfigurationProperties = serverConfigurationProperties;
        this.serverSslConfigurationProperties = serverSslConfigurationProperties;
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
    }

    /**
     * Finds out or derives the base url of the web server of this DQO instance.
     * @return Base url of this DQO instance.
     */
    @Override
    public String getReturnBaseUrl() {
        if (!Strings.isNullOrEmpty(this.dqoInstanceConfigurationProperties.getReturnBaseUrl())) {
            return this.dqoInstanceConfigurationProperties.getReturnBaseUrl();
        }

        StringBuilder urlBuilder = new StringBuilder();
        boolean isHttps = !Strings.isNullOrEmpty(this.serverSslConfigurationProperties.getKeyStore());
        urlBuilder.append(isHttps ? "https://" : "http://");

        String address = this.serverConfigurationProperties.getAddress();
        if (Strings.isNullOrEmpty(address)) {
            urlBuilder.append("localhost");
        } else {
            urlBuilder.append(address);
        }

        String port = this.serverConfigurationProperties.getPort();
        if (!Strings.isNullOrEmpty(port) &&
                !"0".equals(port) &&
                !"-1".equals(port)) {
            if ((isHttps && !"443".equals(port)) || (!isHttps && !"80".equals(port))) {
                urlBuilder.append(':');
                urlBuilder.append(port);
            }
        }
        urlBuilder.append('/');

        String returnBaseUrl = urlBuilder.toString();
        return returnBaseUrl;
    }

    /**
     * Returns the ticket granting ticket that should be added as a query parameter to the "/login" page on the DQO Cloud
     * for performing an identity provider login.
     * @return Ticket granting ticket.
     */
    @Override
    public String getTicketGrantingTicket() {
        synchronized (this.lock) {
            Instant keyLatestExpiresAt = Instant.now().plus(15, ChronoUnit.MINUTES);
            if (this.grantingTicketPayloadSignedObject == null ||
                    this.grantingTicketPayloadSignedObject.getTarget().getExpiresAt().isAfter(keyLatestExpiresAt)) {
                ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient();
                RefreshTokenIssueApi refreshTokenIssueApi = new RefreshTokenIssueApi(authenticatedClient);

                UserLoginTicketGrantingTicketRequest ticketGrantingTicketRequest = new UserLoginTicketGrantingTicketRequest();
                String clientSecretEncoded = Base64.getEncoder().encodeToString(this.instanceSignatureKeyProvider.getInstanceSignatureKey());
                ticketGrantingTicketRequest.setCs(clientSecretEncoded);

                DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey();
                ticketGrantingTicketRequest.setTid(apiKey.getApiKeyPayload().getTenantId());
                ticketGrantingTicketRequest.setTg(apiKey.getApiKeyPayload().getTenantGroup());

                String returnBaseUrl = this.getReturnBaseUrl();
                ticketGrantingTicketRequest.setUrl(returnBaseUrl);

                String signedTicketGrantingTicket = refreshTokenIssueApi.issueLoginTicketGrantingTicketToken(ticketGrantingTicketRequest);
                this.grantingTicketPayloadSignedObject = this.signatureService.decodeSignedMessageHexNoValidate(
                        UserLoginTicketGrantingTicketPayload.class, signedTicketGrantingTicket);
            }

            return this.grantingTicketPayloadSignedObject.getSignedHex();
        }
    }

    /**
     * Builds a url to the DQO Cloud's login page with the ticket granting ticket and the return url.
     * @param returnUrl Return url.
     * @return Url to the DQO Cloud's login page to redirect to.
     */
    @Override
    public String makeDqoLoginUrl(String returnUrl) {
        String returnBaseUrl = this.getReturnBaseUrl();
        if (this.dqoInstanceConfigurationProperties.isValidateReturnBaseUrl() && !returnUrl.startsWith(returnBaseUrl)) {
            throw new DqoRuntimeException("Invalid return url. The valid return url for this DQO instance must begin with " + returnBaseUrl +
                    ". You can change the configuration by setting the --dqo.instance.return-base-url or setting the environment variable " +
                    "DQO_INSTANCE_RETURN_BASE_URL to the base url of your DQO instance, for example --dqo.instance.return-base-url=https://dqoinstance.yourcompany.com");
        }

        String ticketGrantingTicket = this.getTicketGrantingTicket();
        DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey();
        String dqoCloudUiUrlBase = this.dqoCloudConfigurationProperties.getUiBaseUrl();

        try {
            if (!returnUrl.startsWith(returnBaseUrl)) {
                URI originalReturnUri = new URI(returnUrl);
                URIBuilder returnUrlBuilder = new URIBuilder(returnBaseUrl);
                returnUrlBuilder.setPath(originalReturnUri.getPath());
                if (originalReturnUri.getRawQuery() != null) {
                    returnUrlBuilder.setCustomQuery(originalReturnUri.getRawQuery());
                }
                returnUrl = returnUrlBuilder.build().toString();
            }

            URIBuilder uriBuilder = new URIBuilder(dqoCloudUiUrlBase);
            uriBuilder.setPath("/login");
            uriBuilder.addParameter("tgt", ticketGrantingTicket);
            if (!Strings.isNullOrEmpty(apiKey.getApiKeyPayload().getAccountName())) {
                uriBuilder.addParameter("account", apiKey.getApiKeyPayload().getAccountName());
            }
            uriBuilder.addParameter("returnUrl", returnUrl);

            String dqoLoginUrl = uriBuilder.build().toString();
            return dqoLoginUrl;
        }
        catch (Exception ex) {
            throw new DqoRuntimeException("Invalid DQO cloud base url, error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Creates a signed authentication token from a refresh token.
     * @param refreshToken Refresh token.
     * @return Signed authentication token.
     */
    @Override
    public SignedObject<DqoUserTokenPayload> issueDqoUserAuthenticationToken(String refreshToken) {
        SignedObject<DqoUserTokenPayload> signedRefreshToken = this.signatureService.decodeAndValidateSignedMessageHex(DqoUserTokenPayload.class, refreshToken);
        if (signedRefreshToken.getTarget().getDisposition() != DqoUserAuthenticationTokenDisposition.REFRESH_TOKEN) {
            throw new DqoRuntimeException("The refresh token is invalid, it has a different purpose: " + signedRefreshToken.getTarget().getDisposition());
        }

        if (signedRefreshToken.getTarget().getExpiresAt().isBefore(Instant.now())) {
            throw new DqoRuntimeException("The refresh token has already expired.");
        }

        DqoUserTokenPayload authenticationToken = signedRefreshToken.getTarget().clone();
        authenticationToken.setDisposition(DqoUserAuthenticationTokenDisposition.AUTHENTICATION_TOKEN);
        Instant expiresAt = Instant.now().plus(this.dqoInstanceConfigurationProperties.getAuthenticationTokenExpirationMinutes(), ChronoUnit.MINUTES);
        authenticationToken.setExpiresAt(expiresAt);

        SignedObject<DqoUserTokenPayload> signedAuthenticationToken = this.signatureService.createSigned(authenticationToken);
        return signedAuthenticationToken;
    }

    /**
     * Issues an API key token for the calling user.
     * @param sourceUserToken Source user token.
     * @return Signed API Key token.
     */
    @Override
    public SignedObject<DqoUserTokenPayload> issueApiKey(DqoUserTokenPayload sourceUserToken) {
        DqoUserTokenPayload authenticationToken = sourceUserToken.clone();
        authenticationToken.setDisposition(DqoUserAuthenticationTokenDisposition.API_KEY);
        authenticationToken.setExpiresAt(null); // API Keys do not have an expiration time

        SignedObject<DqoUserTokenPayload> signedApiKeyToken = this.signatureService.createSigned(authenticationToken);
        return signedApiKeyToken;
    }

    /**
     * Issues an API key token for the calling user, using a principal. Generates a local API Key independent of the authentication method (DQO Cloud federated login or a local login).
     * @param principal User principal
     * @return Signed API Key token.
     */
    @Override
    public SignedObject<DqoUserTokenPayload> issueApiKey(DqoUserPrincipal principal) {
        DqoUserTokenPayload userTokenPayload = principal.getUserTokenPayload();
        if (userTokenPayload != null ) {
            return issueApiKey(userTokenPayload); // federated login
        }

        DqoCloudApiKeyPayload apiKeyPayload = principal.getApiKeyPayload();
        if (apiKeyPayload != null) {
            DqoUserTokenPayload userTokenFromApiKey = DqoUserTokenPayload.createFromCloudApiKey(apiKeyPayload);
            return issueApiKey(userTokenFromApiKey); // local user login
        }

        return null;
    }

    /**
     * Verifies and decodes the authentication token. Throws an exception if the authentication token is invalid or has expired.
     * @param authenticationToken Authentication token.
     * @return Decoded authentication token.
     */
    @Override
    public SignedObject<DqoUserTokenPayload> verifyAuthenticationToken(String authenticationToken) {
        SignedObject<DqoUserTokenPayload> signedAuthenticationToken = this.signatureService.decodeAndValidateSignedMessageHex(DqoUserTokenPayload.class, authenticationToken);
        DqoUserAuthenticationTokenDisposition tokenDisposition = signedAuthenticationToken.getTarget().getDisposition();
        if (tokenDisposition != DqoUserAuthenticationTokenDisposition.AUTHENTICATION_TOKEN &&
                tokenDisposition != DqoUserAuthenticationTokenDisposition.API_KEY) {
            throw new DqoRuntimeException("The authentication token is invalid, it has a different purpose: " + tokenDisposition);
        }

        if (signedAuthenticationToken.getTarget().getExpiresAt() != null &&
                signedAuthenticationToken.getTarget().getExpiresAt().isBefore(Instant.now())) {
            throw new DqoRuntimeException("The authentication token has already expired.");
        }

        return signedAuthenticationToken;
    }
}
