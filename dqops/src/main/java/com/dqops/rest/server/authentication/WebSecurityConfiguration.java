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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Reactive web security configuration.
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(useAuthorizationManager=true)
public class WebSecurityConfiguration {
    private final SecurityWebFilterChainBuilder securityWebFilterChainBuilder;

    @Autowired
    public WebSecurityConfiguration(SecurityWebFilterChainBuilder securityWebFilterChainBuilder) {
        this.securityWebFilterChainBuilder = securityWebFilterChainBuilder;
    }

    /**
     * Configure the filter chain to support web security.
     * @param http Http server security.
     * @return Filter chain.
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        this.securityWebFilterChainBuilder.configureSecurityFilterChain(http);

        return http.build();
    }
}
