/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.principal;

import com.dqops.core.dqocloud.login.DqoUserRole;

/**
 * Object mother for creating the user identity object for the test user, selecting the data domain.
 */
public class UserDomainIdentityObjectMother {
    /**
     * Creates an admin user identity.
     * @return Admin user identity.
     */
    public static UserDomainIdentity createAdminIdentity() {
        return new UserDomainIdentity("test", DqoUserRole.ADMIN, UserDomainIdentity.ROOT_DATA_DOMAIN,
                UserDomainIdentity.ROOT_DATA_DOMAIN, null, null, null);
    }
}
