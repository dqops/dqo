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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Reactive web security configuration.
 */
@Configuration
@EnableWebFluxSecurity
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
