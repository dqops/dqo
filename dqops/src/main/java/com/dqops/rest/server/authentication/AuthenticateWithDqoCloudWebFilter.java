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
import com.dqops.core.dqocloud.login.DqoUserTokenPayload;
import com.dqops.core.dqocloud.login.InstanceCloudLoginService;
import com.dqops.core.secrets.signature.SignedObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
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

    private final DqoCloudConfigurationProperties dqoCloudConfigurationProperties;
    private final InstanceCloudLoginService instanceCloudLoginService;
    private final DqoAuthenticationTokenFactory dqoAuthenticationTokenFactory;

    @Autowired
    public AuthenticateWithDqoCloudWebFilter(DqoCloudConfigurationProperties dqoCloudConfigurationProperties,
                                             InstanceCloudLoginService instanceCloudLoginService,
                                             DqoAuthenticationTokenFactory dqoAuthenticationTokenFactory) {
        this.dqoCloudConfigurationProperties = dqoCloudConfigurationProperties;
        this.instanceCloudLoginService = instanceCloudLoginService;
        this.dqoAuthenticationTokenFactory = dqoAuthenticationTokenFactory;
    }

    /**
     * Filter web requests that are not authenticated.
     * @param exchange Web server exchange.
     * @param chain The next filter in the chain.
     * @return Mono.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (!this.dqoCloudConfigurationProperties.isAuthenticateWithDqoCloud()) {
            Authentication singleUserAuthenticationToken = this.dqoAuthenticationTokenFactory.createAuthenticatedWithDefaultDqoCloudApiKey();
            SecurityContextHolder.getContext().setAuthentication(singleUserAuthenticationToken);

            ServerWebExchange mutatedExchange = exchange.mutate()
                    .principal(Mono.just(singleUserAuthenticationToken))
                    .build();
            mutatedExchange.getAttributes().put(DqoUserPrincipal.SERVER_WEB_EXCHANGE_ATTRIBUTE_NAME, singleUserAuthenticationToken.getPrincipal()); // probably not required, because repository is triggered before this filter

            return chain
                    .filter(mutatedExchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(new SecurityContextImpl(singleUserAuthenticationToken))))
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(singleUserAuthenticationToken))
                    .then();
        }

        String requestPath = exchange.getRequest().getPath().value();
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
            return exchange.getResponse().writeAndFlushWith(Mono.empty());
        } else {
            HttpCookie dqoAccessTokenCookie = exchange.getRequest().getCookies().getFirst("DQOAccessToken");
            if (dqoAccessTokenCookie == null) {
                if (requestPath.startsWith("/api")) {
                    exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
                    return exchange.getResponse().writeAndFlushWith(Mono.empty());
                }
            } else {
                try {
                    SignedObject<DqoUserTokenPayload> decodedAuthenticationToken = this.instanceCloudLoginService.verifyAuthenticationToken(dqoAccessTokenCookie.getValue());

                    // TODO: create token...

                    return chain.filter(exchange);
                }
                catch (Exception ex) {
                    if (requestPath.startsWith("/api")) {
                        exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(401));
                        return exchange.getResponse().writeAndFlushWith(Mono.empty());
                    }
                }
            }

            try {
                String requestUrl = exchange.getRequest().getURI().toString();
                String dqoCloudLoginUrl = this.instanceCloudLoginService.makeDqoLoginUrl(requestUrl);
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
