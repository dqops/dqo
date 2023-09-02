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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

/**
 * Implementation of the Spring Security context factory that creates a token.
 */
@Component
public class DqoServerSecurityContextRepositoryImpl implements DqoServerSecurityContextRepository {
    private final Authentication anonymousToken;
    private final DqoAuthenticationTokenFactory dqoAuthenticationTokenFactory;

    @Autowired
    public DqoServerSecurityContextRepositoryImpl(DqoAuthenticationTokenFactory dqoAuthenticationTokenFactory) {
        this.dqoAuthenticationTokenFactory = dqoAuthenticationTokenFactory;
        this.anonymousToken = this.dqoAuthenticationTokenFactory.createAnonymousToken();
    }

    /**
     * Stores the principal for later use. Not used by DQO.
     * @param exchange Server exchange.
     * @param context Security context to store.
     * @return Mono.
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    /**
     * Creates an authentication token.
     * This method returns a special anonymous token, because this repository is called by Spring Security before
     * our filter can handle authentication. If this repository does not return any authenticated token, the user receives an error 401.
     * @param exchange Server web exchange.
     * @return Authenticated token.
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        Mono<Principal> principalMono = exchange.getPrincipal();
        Mono<SecurityContext> makeSecurityContextMono = principalMono
                .defaultIfEmpty(this.anonymousToken)
                .map(principal -> {
                    SecurityContextImpl securityContext = new SecurityContextImpl((Authentication) principal);
                    return securityContext;
                });

        return makeSecurityContextMono;
//
//        Authentication authentication = this.dqoAuthenticationTokenFactory.createAnonymousToken();
//        SecurityContextImpl securityContext = new SecurityContextImpl(authentication);
//        return Mono.just(securityContext);
    }
}
