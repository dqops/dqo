/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
