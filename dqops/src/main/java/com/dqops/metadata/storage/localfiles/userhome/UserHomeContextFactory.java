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

import com.dqops.core.principal.UserDomainIdentity;

/**
 * Creates a user come context and loads the home model from the file system.
 */
public interface UserHomeContextFactory {
    /**
     * Opens a local home context, loads the files from the local file system.
     * @param userDomainIdentity User identity that identifies the user for whom we are opening the user home and the data domain for which we are opening the DQOps user home.
     * @param readOnly Make the context read-only.
     * @return User home context with an active user home model that is backed by the local home file system.
     */
    UserHomeContext openLocalUserHome(UserDomainIdentity userDomainIdentity, boolean readOnly);
}
