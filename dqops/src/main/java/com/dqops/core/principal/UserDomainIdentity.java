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
    public static final String SYSTEM_USER_NAME = "";

    /**
     * The name of the default data domain.
     */
    public static final String DEFAULT_DATA_DOMAIN = "";

    /**
     * The alternative name for the root domain if this DQOps instance was started with a --dqo.user.default-domain-name=other  parameter, mounting a different domain as the root domain.
     */
    public static final String ROOT_DOMAIN_ALTERNATE_NAME = "(default)";

    /**
     * The default identity of the local instance, a user who manages the root data domain on this DQOps instance.
     */
    public static final UserDomainIdentity LOCAL_INSTANCE_ADMIN_IDENTITY = new UserDomainIdentity(SYSTEM_USER_NAME, DqoUserRole.ADMIN, DEFAULT_DATA_DOMAIN,
            DEFAULT_DATA_DOMAIN, null, null, null);

    private final String userName;
    private final DqoUserRole domainRole;
    private final String dataDomainFolder;
    private final String dataDomainCloud;
    private final String tenantOwner;
    private final String tenantId;
    private final Integer tenantGroupId;

    /**
     * Creates a user identity object.
     * @param userName User name.
     * @param domainRole Domain role.
     * @param dataDomainFolder The data domain folder name.
     * @param dataDomainCloud The real data domain on DQOps cloud that is mounted.
     * @param tenantOwner The email of the tenant owner.
     * @param tenantId The tenant id.
     * @param tenantGroupId Tenant group id.
     */
    public UserDomainIdentity(String userName,
                              DqoUserRole domainRole,
                              String dataDomainFolder,
                              String dataDomainCloud,
                              String tenantOwner,
                              String tenantId,
                              Integer tenantGroupId) {
        this.userName = userName;
        this.domainRole = domainRole;
        this.dataDomainFolder = !Strings.isNullOrEmpty(dataDomainFolder) ? dataDomainFolder : DEFAULT_DATA_DOMAIN;
        this.dataDomainCloud = dataDomainCloud;
        this.tenantOwner = tenantOwner;
        this.tenantId = tenantId;
        this.tenantGroupId = tenantGroupId;
    }

    /**
     * Creates a default system user that is the administrator of a given data domain.
     * @param dataDomainFolder The data domain folder name.
     * @param dataDomainCloud The real data domain on DQOps cloud that is mounted.
     * @return System user identity for the given data domain.
     */
    public static UserDomainIdentity createDataDomainAdminIdentity(String dataDomainFolder, String dataDomainCloud) {
        return  new UserDomainIdentity(SYSTEM_USER_NAME, DqoUserRole.ADMIN, dataDomainFolder, dataDomainCloud, null, null, null);
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
     * Return the active data domain name, which is the local folder. This value could be "", which suggests the default data domain
     * on DQOps cloud, but if the --dqo.user.default-data-domain=somethingelse is provided, the root DQOps user home folder is simply mounted to a different data domain.
     * @return Active data domain name.
     */
    public String getDataDomainFolder() {
        return dataDomainFolder;
    }

    /**
     * Returns the real data domain name on DQOps cloud that is mounted. This is the domain where the data is synchronized from/to.
     * @return Real data domain name in DQOps cloud.
     */
    public String getDataDomainCloud() {
        return dataDomainCloud;
    }

    /**
     * The email of the tenant (account) owner.
     * @return Tenant owner email.
     */
    public String getTenantOwner() {
        return tenantOwner;
    }

    /**
     * The tenant ID (account id).
     * @return The tenant id.
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * Returns the tenant group id.
     * @return Tenant group id.
     */
    public Integer getTenantGroupId() {
        return tenantGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDomainIdentity that = (UserDomainIdentity) o;

        if (!Objects.equals(userName, that.userName)) return false;
        if (domainRole != that.domainRole) return false;
        return Objects.equals(dataDomainFolder, that.dataDomainFolder);
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (domainRole != null ? domainRole.hashCode() : 0);
        result = 31 * result + (dataDomainFolder != null ? dataDomainFolder.hashCode() : 0);
        return result;
    }
}
