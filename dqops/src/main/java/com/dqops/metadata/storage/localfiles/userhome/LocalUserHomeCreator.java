/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.userhome;

/**
 * Component that ensures that a DQOps local user home was created and the default files were written.
 */
public interface LocalUserHomeCreator {
    /**
     * Initializes the DQOps user home at the default location.
     * @return True when the user home was initialized, false when it cannot be initialized.
     */
    boolean initializeDefaultDqoUserHome();

    /**
     * Checks the default DQO_USER_HOME path if it points to a valid and initialized DQOps user home.
     * @return True when the path points to a valid DQOps User home, false otherwise (the user home must be initialized before first use).
     */
    boolean isDefaultDqoUserHomeInitialized();

    /**
     * Checks the given path if it points to a valid and initialized DQOps user home.
     * @param userHomePathString Path to a potential DQOps user home.
     * @return True when the path points to a valid DQOps User home, false otherwise.
     */
    boolean isDqoUserHomeInitialized(String userHomePathString);

    /**
     * Initializes a DQOps user home at a given location.
     * @param userHomePathString Path to the DQOps user home.
     */
    void initializeDqoUserHome(String userHomePathString);

    /**
     * Ensures that the DQOps User home is initialized at the default location. Prompts the user before creating the user home to confirm.
     * NOTE: this method may forcibly stop the program execution if the user did not agree to create the DQOps User home.
     * @param isHeadless Is headless mode - when true, then the dqo user home is created silently, when false (interactive execution) then the user is asked to confirm.
     * @return True when a new user home was initialized. False when the existing user home was used.
     */
    boolean ensureDefaultUserHomeIsInitialized(boolean isHeadless);

    /**
     * Verifies if the user home configuration (and the local settings) are valid and are not missing configuration.
     * Applies missing default observability check configuration when it is not configured.
     * @param dataDomain Data Domain name.
     */
    void upgradeUserHomeConfigurationWhenMissing(String dataDomain);
}
