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
 * Service that returns the user principal of the user identified by the DQOps Cloud API Key.
 * This provider should be called by operations executed from the DQOps command line to obtain the principal or when DQOps is running in a single user mode.
 */
public interface DqoCloudApiKeyPrincipalProvider {
    /**
     * Creates a DQOps user principal for the user who has direct access to DQOps instance, running operations from CLI
     * or using the DQOps shell directly.
     *
     * @return User principal that has full admin rights when the instance is not authenticated to DQOps Cloud or limited to the role in the DQOps Cloud Api key.
     */
    DqoUserPrincipal createUserPrincipal();
}
