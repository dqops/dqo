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

import org.springframework.security.config.web.server.ServerHttpSecurity;

/**
 * Service that configures an authentication chain.
 */
public interface SecurityWebFilterChainBuilder {
    /**
     * Configures a http filter chain for managing security of the web interface.
     *
     * @param http Http security object.
     * @return The same http security object that was passed, but configured and ready to build.
     */
    ServerHttpSecurity configureSecurityFilterChain(ServerHttpSecurity http);
}
