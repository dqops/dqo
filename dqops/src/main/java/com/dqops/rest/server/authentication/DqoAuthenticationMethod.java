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

/**
 * Authentication method type.
 */
public enum DqoAuthenticationMethod {
    /**
     * Disable authentication. The instance works for the local user.
     */
    none,

    /**
     * Use the DQOps Cloud as an identity provider (requires a paid version of DQOps).
     */
    dqops_cloud,

    /**
     * Use OAuth2 for authentication to support on-premise authentication. Requires a paid version of DQOps.
     */
    oauth2
}
