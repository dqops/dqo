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

import com.dqops.core.principal.DqoPermissionNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.stereotype.Component;

/**
 * Service that configures an authentication chain.
 */
@Component
public class SecurityWebFilterChainBuilderImpl implements SecurityWebFilterChainBuilder {
    private final AuthenticateWithDqoCloudWebFilter authenticateWithDqoCloudWebFilter;
    private final DqoServerSecurityContextRepository dqoServerSecurityContextRepository;

    /**
     * Dependency injection constructor.
     * @param authenticateWithDqoCloudWebFilter Http chain filter that manages authentication.
     * @param dqoServerSecurityContextRepository Principal storage container.
     */
    @Autowired
    public SecurityWebFilterChainBuilderImpl(
            AuthenticateWithDqoCloudWebFilter authenticateWithDqoCloudWebFilter,
            DqoServerSecurityContextRepository dqoServerSecurityContextRepository) {
        this.authenticateWithDqoCloudWebFilter = authenticateWithDqoCloudWebFilter;
        this.dqoServerSecurityContextRepository = dqoServerSecurityContextRepository;
    }

    /**
     * Configures a http filter chain for managing security of the web interface.
     * @param http Http security object.
     * @return The same http security object that was passed, but configured and ready to build.
     */
    @Override
    public ServerHttpSecurity configureSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(customizer -> {
            customizer.disable();
        });

        http.anonymous(customizer -> {
            customizer.disable();
        });

        http.httpBasic(customizer -> {
            customizer.disable();
        });

        http.formLogin(customizer -> {
            customizer.disable();
        });

        http.securityContextRepository(this.dqoServerSecurityContextRepository);

        http.authorizeExchange(customizer -> {
            customizer.pathMatchers(AuthenticateWithDqoCloudWebFilter.LOGO_PATH).permitAll();
            customizer.pathMatchers(AuthenticateWithDqoCloudWebFilter.ISSUE_TOKEN_REQUEST_PATH).permitAll();
            customizer.pathMatchers(AuthenticateWithDqoCloudWebFilter.HEALTHCHECK_REQUEST_PATH).permitAll();
            customizer.pathMatchers(AuthenticateWithDqoCloudWebFilter.MANIFEST_JSON_REQUEST_PATH).permitAll();
            customizer.pathMatchers("/**").hasAuthority(DqoPermissionNames.VIEW);
        });

        http.addFilterBefore(this.authenticateWithDqoCloudWebFilter, SecurityWebFiltersOrder.REACTOR_CONTEXT);

        return http;
    }
}
