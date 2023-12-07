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
import org.apache.parquet.Strings;

import java.util.Objects;

/**
 * Identifies a single user, by login, role, data domain.
 */
public class DqoUserIdentity {
    /**
     * The name of the default data domain.
     */
    public static final String DEFAULT_DATA_DOMAIN = "";

    /**
     * The default identity of the local instance, a user who manages the root data domain on this DQOps instance.
     */
    public static final DqoUserIdentity LOCAL_INSTANCE_ADMIN_IDENTITY = new DqoUserIdentity("admin", DqoUserRole.ADMIN, DEFAULT_DATA_DOMAIN);

    private String name;
    private DqoUserRole accountRole;
    private String dataDomain = DEFAULT_DATA_DOMAIN;

    /**
     * Creates a user identity object.
     * @param name User name.
     * @param accountRole Account role.
     * @param dataDomain Data domain. Null for the default data domain.
     */
    public DqoUserIdentity(String name, DqoUserRole accountRole, String dataDomain) {
        this.name = name;
        this.accountRole = accountRole;
        this.dataDomain = !Strings.isNullOrEmpty(dataDomain) ? dataDomain : DEFAULT_DATA_DOMAIN;
    }

    /**
     * Return the username. Can be null when the user is not authenticated to DQOps Cloud.
     * @return User name.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the user role.
     * @return User role.
     */
    public DqoUserRole getAccountRole() {
        return accountRole;
    }

    /**
     * Return the active data domain name.
     * @return Active data domain name.
     */
    public String getDataDomain() {
        return dataDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DqoUserIdentity that = (DqoUserIdentity) o;

        if (!Objects.equals(name, that.name)) return false;
        if (accountRole != that.accountRole) return false;
        return Objects.equals(dataDomain, that.dataDomain);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (accountRole != null ? accountRole.hashCode() : 0);
        result = 31 * result + (dataDomain != null ? dataDomain.hashCode() : 0);
        return result;
    }
}
