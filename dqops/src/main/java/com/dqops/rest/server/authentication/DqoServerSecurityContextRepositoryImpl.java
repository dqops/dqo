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
     * Stores the principal for later use. Not used by DQOps.
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
    }
}
