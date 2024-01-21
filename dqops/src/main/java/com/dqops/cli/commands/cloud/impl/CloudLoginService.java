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
package com.dqops.cli.commands.cloud.impl;

/**
 * Service that will open a browser and log in to the DQOps cloud.
 */
public interface CloudLoginService {
    /**
     * Requests an API key generation, opens a browser and waits for the API key.
     * @return True when the api key was retrieved, false when there was an error.
     */
    boolean logInToDqoCloud();

    /**
     * Enables synchronization with DQOps Cloud.
     */
    void enableCloudSync();

    /**
     * Disable synchronization with DQOps Cloud.
     */
    void disableCloudSync();
}
