/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.secrets;

import com.dqops.metadata.userhome.UserHome;

/**
 * Context object passed to the secret value. Provides access to the user home, to use shared credentials.
 */
public class SecretValueLookupContext {
    private UserHome userHome;

    /**
     * Creates a secret value lookup context.
     * @param userHome User home.
     */
    public SecretValueLookupContext(UserHome userHome) {
        this.userHome = userHome;
    }

    /**
     * User home - to look up shared credentials.
     * @return User home.
     */
    public UserHome getUserHome() {
        return userHome;
    }
}
