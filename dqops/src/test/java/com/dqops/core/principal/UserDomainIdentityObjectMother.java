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
