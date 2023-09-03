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

import com.dqops.core.principal.DqoPermissionNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Reactive web security configuration.
 */
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {
    private AuthenticateWithDqoCloudWebFilter authenticateWithDqoCloudWebFilter;
    private DqoServerSecurityContextRepository dqoServerSecurityContextRepository;

    @Autowired
    public WebSecurityConfiguration(AuthenticateWithDqoCloudWebFilter authenticateWithDqoCloudWebFilter,
                                    DqoServerSecurityContextRepository dqoServerSecurityContextRepository) {
        this.authenticateWithDqoCloudWebFilter = authenticateWithDqoCloudWebFilter;
        this.dqoServerSecurityContextRepository = dqoServerSecurityContextRepository;
    }

    /**
     * Configure the filter chain to support web security.
     * @param http Http server security.
     * @return Filter chain.
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
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
            customizer.pathMatchers(AuthenticateWithDqoCloudWebFilter.ISSUE_TOKEN_URL).permitAll();
            customizer.pathMatchers(AuthenticateWithDqoCloudWebFilter.HEALTHCHECK_URL).permitAll();
            customizer.pathMatchers("/**").hasAuthority(DqoPermissionNames.VIEW);
        });

        http.addFilterBefore(this.authenticateWithDqoCloudWebFilter, SecurityWebFiltersOrder.REACTOR_CONTEXT);

        return http.build();
    }
}
