/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.local;

import com.dqops.core.principal.UserDomainIdentity;

import java.nio.file.Path;

/**
 * Service that returns an absolute path to the local user home, used to access data files.
 */
public interface LocalDqoUserHomePathProvider {
    /**
     * Returns the absolute path to the DQO_USER_HOME folder.
     * @param userIdentity User identity that identifies the target data domain.
     * @return Absolute path to the DQOps user home folder.
     */
    Path getLocalUserHomePath(UserDomainIdentity userIdentity);
}
