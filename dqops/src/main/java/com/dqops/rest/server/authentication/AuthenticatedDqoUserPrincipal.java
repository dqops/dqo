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

import org.springframework.security.core.AuthenticatedPrincipal;

/**
 * DQO user principal that identifies an unauthenticated user.
 */
public class AuthenticatedDqoUserPrincipal extends DqoUserPrincipal implements AuthenticatedPrincipal {
    public AuthenticatedDqoUserPrincipal() {
    }

    /**
     * Creates a user principal.
     *
     * @param name User's email (principal name).
     */
    public AuthenticatedDqoUserPrincipal(String name) {
        super(name);
    }
}
