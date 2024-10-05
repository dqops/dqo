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
