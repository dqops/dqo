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

import java.security.Principal;

/**
 * DQO user principal that identifies the user.
 */
public class DqoUserPrincipal implements Principal {
    /**
     * Attribute in the {@link org.springframework.web.server.ServerWebExchange} used to store the principal.
     */
    public static final String SERVER_WEB_EXCHANGE_ATTRIBUTE_NAME = "dqo principal";

    /**
     * Name used for an unauthenticated principal.
     */
    public static final String UNAUTHENTICATED_PRINCIPAL_NAME = "";

    private String name;

    public DqoUserPrincipal() {
        this.name = UNAUTHENTICATED_PRINCIPAL_NAME;
    }

    /**
     * Creates a user principal.
     * @param name User's email (principal name).
     */
    public DqoUserPrincipal(String name) {
        this.name = name;
    }

    /**
     * Returns the email that identifies the user. It is the email used to log in to DQO cloud.
     * @return User email.
     */
    public String getName() {
        return name;
    }
}
