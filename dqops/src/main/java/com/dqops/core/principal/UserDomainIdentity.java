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
 * Identifies a single user, by login, role in the domain, and the data domain.
 */
public class UserDomainIdentity {
    /**
     * Empty user to identify the system user.
     */
    private static final String SYSTEM_USER = "";

    /**
     * The name of the default data domain.
     */
    public static final String DEFAULT_DATA_DOMAIN = "";

    /**
     * The default identity of the local instance, a user who manages the root data domain on this DQOps instance.
     */
    public static final UserDomainIdentity LOCAL_INSTANCE_ADMIN_IDENTITY = new UserDomainIdentity(SYSTEM_USER, DqoUserRole.ADMIN, DEFAULT_DATA_DOMAIN);

    private final String userName;
    private final DqoUserRole domainRole;
    private final String dataDomain;

    /**
     * Creates a user identity object.
     * @param userName User name.
     * @param domainRole Domain role.
     * @param dataDomain Data domain. Null for the default data domain.
     */
    public UserDomainIdentity(String userName, DqoUserRole domainRole, String dataDomain) {
        this.userName = userName;
        this.domainRole = domainRole;
        this.dataDomain = !Strings.isNullOrEmpty(dataDomain) ? dataDomain : DEFAULT_DATA_DOMAIN;
    }

    /**
     * Creates a default system user that is the administrator of a given data domain.
     * @param dataDomain Data domain name.
     * @return System user identity for the given data domain.
     */
    public static UserDomainIdentity createDataDomainAdminIdentity(String dataDomain) {
        return  new UserDomainIdentity(SYSTEM_USER, DqoUserRole.ADMIN, dataDomain);
    }

    /**
     * Return the username. Can be null when the user is not authenticated to DQOps Cloud.
     * @return User name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Return the user role in the data domain.
     * @return User role in the data domain.
     */
    public DqoUserRole getDomainRole() {
        return domainRole;
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

        UserDomainIdentity that = (UserDomainIdentity) o;

        if (!Objects.equals(userName, that.userName)) return false;
        if (domainRole != that.domainRole) return false;
        return Objects.equals(dataDomain, that.dataDomain);
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (domainRole != null ? domainRole.hashCode() : 0);
        result = 31 * result + (dataDomain != null ? dataDomain.hashCode() : 0);
        return result;
    }
}
