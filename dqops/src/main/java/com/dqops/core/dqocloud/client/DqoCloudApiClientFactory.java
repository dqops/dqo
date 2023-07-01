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
package com.dqops.core.dqocloud.client;

import com.dqops.cloud.rest.handler.ApiClient;

/**
 * DQO Cloud API client factory.
 */
public interface DqoCloudApiClientFactory {
    /**
     * Creates an unauthenticated API client for the DQO Cloud.
     * @return Unauthenticated client.
     */
    ApiClient createUnauthenticatedClient();

    /**
     * Creates an authenticated API client for the DQO Cloud. The authentication uses the API Key that must be obtained by running the "login" CLI command.
     * @return Authenticated client.
     */
    ApiClient createAuthenticatedClient();
}
