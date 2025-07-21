/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli;

/**
 * Initializes the local instance, configures a DQOps user home, logs the user to the cloud dqo instance.
 * Component called by the CLI command runner just before the first command is executed.
 */
public interface CliInitializer {
    /**
     * Initializes the local folder, creates a dqo user home, configures some properties.
     * @param args Command line parameters used to start dqo. Initializer will look for the --headless parameter to perform silent initialization.
     */
    void initializeApp(String[] args);
}
