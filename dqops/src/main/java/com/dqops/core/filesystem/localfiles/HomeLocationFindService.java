/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.localfiles;

import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.storage.localfiles.HomeType;

/**
 * Simple service that returns the location of the user home or the DQO_HOME system home.
 */
public interface HomeLocationFindService {
    String DQO_USER_HOME_MARKER_NAME = ".DQO_USER_HOME";

    /**
     * Returns an absolute path to the user home.
     * @param userDomainIdentity User home identity to identify the data domain.
     * @return Absolute path to the user home. May return null if the user home is not enabled.
     */
    String getUserHomePath(UserDomainIdentity userDomainIdentity);

    /**
     * Returns an absolute path to the root user home.
     * @return Absolute path to the user home. May return null if the user home is not enabled.
     */
    String getRootUserHomePath();

    /**
     * Returns an absolute path to the dqo home.
     * @return Absolute path to the DQO_HOME.
     */
    String getDqoHomePath();

    /**
     * Returns the absolute path to a home of choice (user home or DQO_HOME).
     * @param homeType Home type (user home or dqo system home).
     * @param userDomainIdentity User domain identity to identify the data domain when the user home is requested.
     * @return Absolute path to home.
     */
    String getHomePath(HomeType homeType, UserDomainIdentity userDomainIdentity);
}
