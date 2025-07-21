/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.userhome;

import com.dqops.core.principal.UserDomainIdentity;

/**
 * User home object mother.
 */
public class UserHomeObjectMother {
    /**
     * Creates a bare user home without any file system backend.
     * @return Bare, empty user home.
     */
    public static UserHomeImpl createBareUserHome() {
        return new UserHomeImpl(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY, false);
    }
}
