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
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Object mother for the user principal.
 */
public class DqoUserPrincipalObjectMother {
    /**
     * Creates a DQO user principal for the default user that is not authenticated to DQO Cloud, but is a local admin.
     * @return DQO user principal.
     */
    public static DqoUserPrincipal createStandaloneAdmin() {
        List<GrantedAuthority> privileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(DqoUserRole.ADMIN);
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal("test", DqoUserRole.ADMIN, privileges,
                UserDomainIdentity.ROOT_DATA_DOMAIN, UserDomainIdentity.ROOT_DATA_DOMAIN, null, null, null, null);
        return dqoUserPrincipal;
    }
}
