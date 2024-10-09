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
