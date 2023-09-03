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

package com.dqops.core.principal;

/**
 * Service that returns the user principal of the user identified by the DQO Cloud API Key.
 * This provider should be called by operations executed from the DQO command line to obtain the principal or when DQO is running in a single user mode.
 */
public interface DqoCloudApiKeyPrincipalProvider {
    /**
     * Creates a DQO user principal for the user who has direct access to DQO instance, running operations from CLI
     * or using the DQO shell directly.
     *
     * @return User principal that has full admin rights when the instance is not authenticated to DQO Cloud or limited to the role in the DQO Cloud Api key.
     */
    DqoUserPrincipal createUserPrincipal();
}
