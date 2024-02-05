/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
                UserDomainIdentity.DEFAULT_DATA_DOMAIN, UserDomainIdentity.DEFAULT_DATA_DOMAIN, null, null);
        return dqoUserPrincipal;
    }
}
