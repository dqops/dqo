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
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import com.dqops.core.dqocloud.login.InstanceCloudLoginService;
import com.dqops.core.secrets.signature.SignedObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * Web filter that redirects unauthenticated users to DQO Cloud login page.
 */
@Component
@Slf4j
public class AuthenticateWithDqoCloudWebFilter implements WebFilter {
    /**
     * Special url that receives a post with the authentication token received from DQO Cloud.
     */
    public static final String ISSUE_TOKEN_URL = "/tokenissuer";

    /**
     * Health check url.
     */
    public static final String HEALTHCHECK_URL = "/api/ishealthy";

    private final DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private final InstanceCloudLoginService instanceCloudLoginService;
    private final DqoAuthenticationTokenFactory dqoAuthenticationTokenFactory;
    private final DqoCloudApiKeyProvider dqoCloudApiKeyProvider;

    @Autowired
    public AuthenticateWithDqoCloudWebFilter(DqoCloudConfigurationProperties dqoCloudConfigurationProperties,
                                             InstanceCloudLoginService instanceCloudLoginService,
                                             DqoAuthenticationTokenFactory dqoAuthenticationTokenFactory,
                                             DqoCloudApiKeyProvider dqoCloudApiKeyProvider) {
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
        this.instanceCloudLoginService = instanceCloudLoginService;
        this.dqoAuthenticationTokenFactory = dqoAuthenticationTokenFactory;
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
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

        return headerTokens[1];
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

        String authorizationToken = extractAuthenticationBearerToken(request);
        if (authorizationToken != null) {
            DqoCloudApiKey apiKey = this.dqoCloudApiKeyProvider.getApiKey();
            if (apiKey != null && Objects.equals(authorizationToken, apiKey.getApiKeyToken())) {
                Authentication singleUserAuthenticationToken = this.dqoAuthenticationTokenFactory.createAuthenticatedWithDefaultDqoCloudApiKey();

                if (log.isInfoEnabled()) {
                    log.info("Processing request type " + request.getMethod().name() + ", path: " + requestPath + " authenticating with an API Key for the user " + singleUserAuthenticationToken.getName());
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
                Authentication userTokenAuthentication = this.dqoAuthenticationTokenFactory.createAuthenticatedWithUserToken(
                        decodedAuthenticationToken.getTarget());

                if (log.isInfoEnabled()) {
                    log.info("Processing request type " + request.getMethod().name() + ", path: " + requestPath + " authenticating with a bearer token for the user " + userTokenAuthentication.getName());
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
            Authentication singleUserAuthenticationToken = this.dqoAuthenticationTokenFactory.createAuthenticatedWithDefaultDqoCloudApiKey();

            if (log.isInfoEnabled()) {
                log.info("Processing request type " + request.getMethod().name() + ", path: " + requestPath + " authenticating with the DQO Cloud Pairing Key for the user " + singleUserAuthenticationToken.getName());
            }

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .principal(Mono.just(singleUserAuthenticationToken))
                    .build();

            return chain
                    .filter(mutatedExchange)
                    .then();
        }

        if (Objects.equals(requestPath, ISSUE_TOKEN_URL)) {
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(303));
            String returnUrl = exchange.getRequest().getQueryParams().getFirst("returnUrl");
            String refreshToken = exchange.getRequest().getQueryParams().getFirst("refreshToken");
            SignedObject<DqoUserTokenPayload> signedAuthenticationToken = this.instanceCloudLoginService.issueDqoUserAuthenticationToken(refreshToken);
            String hostHeader = exchange.getRequest().getHeaders().getFirst("Host");
            int portPrefixIndex = hostHeader.indexOf(':');
            if (portPrefixIndex > 0) {
                hostHeader = hostHeader.substring(0, portPrefixIndex);
            }
            ResponseCookie dqoAccessTokenCookie = ResponseCookie.from("DQOAccessToken", signedAuthenticationToken.getSignedHex())
                    .maxAge(Duration.ofHours(24))
                    .domain(hostHeader)
                    .build();
            exchange.getResponse().getCookies().add("DQOAccessToken", dqoAccessTokenCookie);
            exchange.getResponse().getHeaders().add("Location", returnUrl);

            if (log.isInfoEnabled()) {
                log.info("Processing the refresh token from Cloud DQO for the user " + signedAuthenticationToken.getTarget().getUser());
            }

            return exchange.getResponse().writeAndFlushWith(Mono.empty());
        } else if (Objects.equals(requestPath, HEALTHCHECK_URL)) {
            Authentication singleUserAuthenticationToken = this.dqoAuthenticationTokenFactory.createAnonymousToken();

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .principal(Mono.just(singleUserAuthenticationToken))
                    .build();

            return chain
                    .filter(mutatedExchange)
                    .then();
        } else {
            HttpCookie dqoAccessTokenCookie = exchange.getRequest().getCookies().getFirst("DQOAccessToken");
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
                            decodedAuthenticationToken.getTarget());

                    if (log.isInfoEnabled()) {
                        log.info("Processing request type " + request.getMethod().name() + ", path: " + requestPath + " authenticating with local credentials for the user " + userTokenAuthentication.getName());
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

            if (this.dqoCloudApiKeyProvider.getApiKey() == null) {
                log.warn("DQO Cloud pairing API Key missing, cannot use federated authentication");
                exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(403));
                return exchange.getResponse().writeAndFlushWith(Mono.empty());
            }

            try {
                String requestUrl = exchange.getRequest().getURI().toString();
                String dqoCloudLoginUrl = this.instanceCloudLoginService.makeDqoLoginUrl(requestUrl);

                if (log.isInfoEnabled()) {
                    log.info("Redirecting the user to authenticate with DQO Cloud federated authentication");
                }

                exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(303));
                exchange.getResponse().getHeaders().add("Location", dqoCloudLoginUrl);
                return exchange.getResponse().writeAndFlushWith(Mono.empty());
            }
            catch (Exception ex) {
                log.error("Cannot create a DQO Cloud login url: " + ex.getMessage(), ex);
                exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(500));
                return exchange.getResponse().writeAndFlushWith(Mono.empty());
            }
        }
    }
}
