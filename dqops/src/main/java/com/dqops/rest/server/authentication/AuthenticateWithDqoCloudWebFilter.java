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

import com.dqops.core.configuration.DqoCloudConfigurationProperties;
import com.dqops.core.configuration.DqoInstanceConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.domains.LocalDataDomainRegistry;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.login.DqoUserRole;
import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import com.dqops.core.dqocloud.login.InstanceCloudLoginService;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.secrets.signature.SignedObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * Web filter that redirects unauthenticated users to DQOps Cloud login page.
 */
@Component
@Slf4j
public class AuthenticateWithDqoCloudWebFilter implements WebFilter {
    /**
     * Special url that receives a post with the authentication token received from DQOps Cloud.
     */
    public static final String ISSUE_TOKEN_REQUEST_PATH = "/tokenissuer";

    /**
     * Health check url.
     */
    public static final String HEALTHCHECK_REQUEST_PATH = "/api/ishealthy";

    /**
     * /manifest.json path
     */
    public static final String MANIFEST_JSON_REQUEST_PATH = "/manifest.json";

    /**
     * Cookie name used to send the authentication token.
     */
    public static final String AUTHENTICATION_TOKEN_COOKIE = "DQOAccessToken";

    /**
     * Cookie name used to receive the name of the selected data domain.
     */
    public static final String DATA_DOMAIN_COOKIE = "DQODataDomain";


    private final DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private final DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties;
    private final InstanceCloudLoginService instanceCloudLoginService;
    private final DqoAuthenticationTokenFactory dqoAuthenticationTokenFactory;
    private final DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private final DqoUserPrincipalProvider dqoUserPrincipalProvider;
    private final DqoUserConfigurationProperties dqoUserConfigurationProperties;
    private final LocalDataDomainRegistry localDataDomainRegistry;

    @Autowired
    public AuthenticateWithDqoCloudWebFilter(DqoCloudConfigurationProperties dqoCloudConfigurationProperties,
                                             DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties,
                                             InstanceCloudLoginService instanceCloudLoginService,
                                             DqoAuthenticationTokenFactory dqoAuthenticationTokenFactory,
                                             DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                             DqoUserPrincipalProvider dqoUserPrincipalProvider,
                                             DqoUserConfigurationProperties dqoUserConfigurationProperties,
                                             LocalDataDomainRegistry localDataDomainRegistry) {
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
        this.dqoInstanceConfigurationProperties = dqoInstanceConfigurationProperties;
        this.instanceCloudLoginService = instanceCloudLoginService;
        this.dqoAuthenticationTokenFactory = dqoAuthenticationTokenFactory;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.dqoUserPrincipalProvider = dqoUserPrincipalProvider;
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
        this.localDataDomainRegistry = localDataDomainRegistry;
    }

    /**
     * Extracts the token from the Authorization header, after the "Bearer" value or returns null when the Authorization header as not present or the format was not accepted.
     * @param request Http request to use for retrieving the header.
     * @return Token extracted from the Authorization header or null.
     */
    protected String extractAuthenticationBearerToken(ServerHttpRequest request) {
        List<String> authorizationHeaderValues = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authorizationHeaderValues == null || authorizationHeaderValues.size() != 1) {
            return null;
        }

        String headerValue = authorizationHeaderValues.get(0);
        String[] headerTokens = StringUtils.split(headerValue, ' ');
        if (headerTokens == null || headerTokens.length != 2) {
            return null;
        }

        if (!Objects.equals("Bearer", headerTokens[0])) {
            return null;
        }

        String token = headerTokens[1];
        if (token.length() > 3 && token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"') {
            token = token.substring(1, token.length() - 1);
        }

        return token;
    }

    /**
     * Filter web requests that are not authenticated.
     * @param exchange Web server exchange.
     * @param chain The next filter in the chain.
     * @return Mono.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getPath().value();
        String activeDataDomainCloudName = this.dqoUserConfigurationProperties.getDefaultDataDomain();
        HttpCookie selectedDataDomainCookie = exchange.getRequest().getCookies().getFirst(DATA_DOMAIN_COOKIE);
        if (selectedDataDomainCookie != null) {
            activeDataDomainCloudName = selectedDataDomainCookie.getValue();
            if (Strings.isNullOrEmpty(activeDataDomainCloudName) || Objects.equals(activeDataDomainCloudName, UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME)) {
                activeDataDomainCloudName = UserDomainIdentity.ROOT_DATA_DOMAIN; // switching to the default (root) domain
            }
        }

        String authorizationToken = extractAuthenticationBearerToken(request);
        if (authorizationToken != null) {
            DqoUserPrincipal operatorUserPrincipal = dqoUserPrincipalProvider.createLocalInstanceAdminPrincipal();
            DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey(operatorUserPrincipal.getDataDomainIdentity());  // NOTE: this operation will support only the api key of the primary data domain
            if (apiKey != null && Objects.equals(authorizationToken, apiKey.getApiKeyToken())) {
                String apiKeyDataDomain = apiKey.getApiKeyPayload().getDefaultDomain() != null ? apiKey.getApiKeyPayload().getDefaultDomain() :
                        this.dqoUserConfigurationProperties.getDefaultDataDomain();
                Authentication singleUserAuthenticationToken = this.dqoAuthenticationTokenFactory.createAuthenticatedWithDefaultDqoCloudApiKey(apiKeyDataDomain);

                if (log.isDebugEnabled()) {
                    log.debug("Processing request type " + request.getMethod().name() + ", path: " + requestPath + " authenticating with an API Key for the user " + singleUserAuthenticationToken.getName());
                }

                ServerWebExchange mutatedExchange = exchange.mutate()
                        .principal(Mono.just(singleUserAuthenticationToken))
                        .build();

                return chain
                        .filter(mutatedExchange)
                        .then();
            }

            try {
                SignedObject<DqoUserTokenPayload> decodedAuthenticationToken =
                        this.instanceCloudLoginService.verifyAuthenticationToken(authorizationToken);
                DqoUserTokenPayload userTokenPayload = decodedAuthenticationToken.getTarget();
                Authentication userTokenAuthentication = this.dqoAuthenticationTokenFactory.createAuthenticatedWithUserToken(
                        userTokenPayload, userTokenPayload.getActiveDataDomain() != null ? userTokenPayload.getActiveDataDomain() : activeDataDomainCloudName);

                if (log.isDebugEnabled()) {
                    log.debug("Processing request type " + request.getMethod().name() + ", path: " + requestPath + " authenticating with a bearer token for the user " + userTokenAuthentication.getName());
                }

                ServerWebExchange mutatedExchange = exchange.mutate()
                        .principal(Mono.just(userTokenAuthentication))
                        .build();

                return chain
                        .filter(mutatedExchange)
                        .then();
            }
            catch (Exception ex) {
                log.warn("Invalid Authentication token.", ex);
            }

            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
            return exchange.getResponse().writeAndFlushWith(Mono.empty());
        }

        if (!this.dqoCloudConfigurationProperties.isAuthenticateWithDqoCloud()) {
            Authentication singleUserAuthenticationToken = this.dqoAuthenticationTokenFactory.createAuthenticatedWithDefaultDqoCloudApiKey(activeDataDomainCloudName);

            if (log.isDebugEnabled()) {
                log.debug("Processing request type " + request.getMethod().name() + ", path: " + requestPath + " authenticating with the DQOps Cloud Pairing Api Key for the user " +
                        singleUserAuthenticationToken.getName());
            }

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .principal(Mono.just(singleUserAuthenticationToken))
                    .build();

            return chain
                    .filter(mutatedExchange)
                    .then();
        }

        if (Objects.equals(requestPath, ISSUE_TOKEN_REQUEST_PATH)) {
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(303));
            String returnUrl = exchange.getRequest().getQueryParams().getFirst("returnUrl");
            String refreshToken = exchange.getRequest().getQueryParams().getFirst("refreshToken");
            SignedObject<DqoUserTokenPayload> signedAuthenticationToken = this.instanceCloudLoginService.issueDqoUserAuthenticationToken(refreshToken);

            String hostHeader;
            try {
                URI originalReturnUri = new URI(returnUrl);
                hostHeader = originalReturnUri.getHost();
            }
            catch (Exception ex) {
                log.error("Return url is invalid: " + returnUrl + ", exception: " + ex.getMessage(), ex);
                exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(403));
                return exchange.getResponse().writeAndFlushWith(Mono.empty());
            }

            int portPrefixIndex = hostHeader.indexOf(':');
            if (portPrefixIndex > 0) {
                hostHeader = hostHeader.substring(0, portPrefixIndex);
            }

            if (log.isDebugEnabled()) {
                log.debug("Returning a DQOAccessToken cookie for the user " + signedAuthenticationToken.getTarget().getUser() + " in the tenant " +
                        signedAuthenticationToken.getTarget().getTenantId() + ", the cookie is valid for " + hostHeader + " host");
            }

            ResponseCookie dqoAccessTokenCookie = ResponseCookie.from(AUTHENTICATION_TOKEN_COOKIE, signedAuthenticationToken.getSignedHex())
                    .maxAge(Duration.ofMinutes(this.dqoInstanceConfigurationProperties.getAuthenticationTokenExpirationMinutes()))
                    .path("/")
                    .domain(hostHeader)
                    .build();
            exchange.getResponse().getCookies().add(AUTHENTICATION_TOKEN_COOKIE, dqoAccessTokenCookie);

            String defaultDataDomainFromLogin = signedAuthenticationToken.getTarget().getActiveDataDomain();
            if (defaultDataDomainFromLogin != null || selectedDataDomainCookie != null) {
                String effectiveDataDomain = defaultDataDomainFromLogin;

                if (selectedDataDomainCookie != null) {
                    if (Objects.equals(activeDataDomainCloudName, this.dqoUserConfigurationProperties.getDefaultDataDomain()) ||
                        this.localDataDomainRegistry.getNestedDomain(activeDataDomainCloudName) != null) {
                        if (signedAuthenticationToken.getTarget().getAccountRole() != null && signedAuthenticationToken.getTarget().getAccountRole() != DqoUserRole.NONE ||
                            signedAuthenticationToken.getTarget().getDomainRoles() != null && signedAuthenticationToken.getTarget().getDomainRoles().get(activeDataDomainCloudName) != null &&
                            signedAuthenticationToken.getTarget().getDomainRoles().get(activeDataDomainCloudName) != DqoUserRole.NONE) {
                            effectiveDataDomain = activeDataDomainCloudName;
                        }
                    }
                }

                if (Objects.equals(effectiveDataDomain, UserDomainIdentity.ROOT_DATA_DOMAIN)) {
                    effectiveDataDomain = UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME;
                }

                ResponseCookie activeDataDomainTokenCookie = ResponseCookie.from(DATA_DOMAIN_COOKIE, effectiveDataDomain)
                        .maxAge(Duration.ofMinutes(this.dqoInstanceConfigurationProperties.getAuthenticationTokenExpirationMinutes()))
                        .domain(hostHeader)
                        .path("/")
                        .build();
                exchange.getResponse().getCookies().add(DATA_DOMAIN_COOKIE, activeDataDomainTokenCookie);
            }
            exchange.getResponse().getHeaders().add("Location", returnUrl);

            if (log.isDebugEnabled()) {
                log.debug("Processing the refresh token from Cloud DQOps for the user " + signedAuthenticationToken.getTarget().getUser());
            }

            return exchange.getResponse().writeAndFlushWith(Mono.empty());
        } else if (Objects.equals(requestPath, HEALTHCHECK_REQUEST_PATH) || Objects.equals(requestPath, MANIFEST_JSON_REQUEST_PATH)) {
            Authentication singleUserAuthenticationToken = this.dqoAuthenticationTokenFactory.createAnonymousToken();

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .principal(Mono.just(singleUserAuthenticationToken))
                    .build();

            return chain
                    .filter(mutatedExchange)
                    .then();
        } else {
            HttpCookie dqoAccessTokenCookie = exchange.getRequest().getCookies().getFirst(AUTHENTICATION_TOKEN_COOKIE);
            if (dqoAccessTokenCookie == null) {
                if (requestPath.startsWith("/api")) {
                    exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
                    return exchange.getResponse().writeAndFlushWith(Mono.empty());
                }
            } else {
                try {
                    SignedObject<DqoUserTokenPayload> decodedAuthenticationToken = 
                            this.instanceCloudLoginService.verifyAuthenticationToken(dqoAccessTokenCookie.getValue());
                    Authentication userTokenAuthentication = this.dqoAuthenticationTokenFactory.createAuthenticatedWithUserToken(
                            decodedAuthenticationToken.getTarget(), activeDataDomainCloudName);

                    if (log.isDebugEnabled()) {
                        log.debug("Processing request type " + request.getMethod().name() + ", path: " + requestPath + " authenticating with local credentials for the user " + userTokenAuthentication.getName());
                    }

                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .principal(Mono.just(userTokenAuthentication))
                            .build();

                    return chain
                            .filter(mutatedExchange)
                            .then();
                }
                catch (Exception ex) {
                    if (requestPath.startsWith("/api")) {
                        log.error("Verification of a refresh token failed with the message: " + ex.getMessage(), ex);
                        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
                        return exchange.getResponse().writeAndFlushWith(Mono.empty());
                    }
                }
            }

            DqoUserPrincipal operatorUserPrincipal = dqoUserPrincipalProvider.createLocalInstanceAdminPrincipal();
            if (this.dqoCloudApiKeyProvider.getApiKey(operatorUserPrincipal.getDataDomainIdentity()) == null) {
                log.warn("DQOps Cloud pairing API Key missing, cannot use federated authentication");
                exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(403));
                return exchange.getResponse().writeAndFlushWith(Mono.empty());
            }

            try {
                String requestUrl = exchange.getRequest().getURI().toString();
                String dqoCloudLoginUrl = this.instanceCloudLoginService.makeDqoLoginUrl(requestUrl);

                if (log.isDebugEnabled()) {
                    log.debug("Redirecting the user to authenticate with DQOps Cloud federated authentication at " + dqoCloudLoginUrl);
                }

                exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(303));
                exchange.getResponse().getHeaders().add("Location", dqoCloudLoginUrl);
                return exchange.getResponse().writeAndFlushWith(Mono.empty());
            }
            catch (Exception ex) {
                log.error("Cannot create a DQOps Cloud login url: " + ex.getMessage(), ex);
                exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(500));
                return exchange.getResponse().writeAndFlushWith(Mono.empty());
            }
        }
    }
}
