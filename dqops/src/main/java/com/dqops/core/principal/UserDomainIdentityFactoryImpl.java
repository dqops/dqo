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

import com.dqops.core.configuration.DqoUserConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Factory that creates a user domain identity, taking into account which data domain is mounted in the root DQOps user home folder.
 */
@Component
public class UserDomainIdentityFactoryImpl implements UserDomainIdentityFactory {
    private final DqoUserConfigurationProperties dqoUserConfigurationProperties;

    /**
     * Default injection container.
     * @param dqoUserConfigurationProperties --dqo.user. configuration parameters, especially the default data domain name.
     */
    @Autowired
    public UserDomainIdentityFactoryImpl(DqoUserConfigurationProperties dqoUserConfigurationProperties) {
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
    }

    /**
     * Maps the name of a real data domain (used on the DQOps Cloud data lake) to the local folder. Takes into account that
     * a data domain could be mounted at the root DQOps user home folder.
     * @param dataDomainCloudName Data domain name as used in DQOps cloud (the real data domain name).
     * @return Local data domain folder name (just name, no path).
     */
    @Override
    public String mapDataDomainCloudNameToFolder(String dataDomainCloudName) {
        String effectiveCloudDomainName = dataDomainCloudName == null ? UserDomainIdentity.DEFAULT_DATA_DOMAIN : dataDomainCloudName;

        if (Objects.equals(UserDomainIdentity.DEFAULT_DATA_DOMAIN, this.dqoUserConfigurationProperties.getDefaultDataDomain())) {
            return effectiveCloudDomainName;
        } else {
            if (Objects.equals(effectiveCloudDomainName, this.dqoUserConfigurationProperties.getDefaultDataDomain())) {
                return UserDomainIdentity.DEFAULT_DATA_DOMAIN;
            } else if (Objects.equals(effectiveCloudDomainName, UserDomainIdentity.DEFAULT_DATA_DOMAIN)) {
                return UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME;
            } else {
                return effectiveCloudDomainName;
            }
        }
    }

    /**
     * Creates a data domain identity for the admin user, given the data domain name on the cloud.
     * Checks what data domain was mounted at the local DQOps user home root folder and picks the right folder name where the data will be mounted.
     * @param dataDomainCloudName Data domain name as used in DQOps cloud (the real data domain name).
     * @return User domain identity for the admin user.
     */
    @Override
    public UserDomainIdentity createDataDomainAdminIdentityForCloudDomain(String dataDomainCloudName) {
        String effectiveCloudDomainName = dataDomainCloudName == null ? UserDomainIdentity.DEFAULT_DATA_DOMAIN : dataDomainCloudName;
        String dataDomainFolderName = mapDataDomainCloudNameToFolder(effectiveCloudDomainName);
        UserDomainIdentity dataDomainAdminIdentity = UserDomainIdentity.createDataDomainAdminIdentity(dataDomainFolderName, effectiveCloudDomainName);
        return dataDomainAdminIdentity;
    }

    /**
     * Creates a data domain identity for the admin user, given the data domain name mapped locally.
     * Checks what data domain was mounted at the local DQOps user home root folder and picks the right folder name where the data will be mounted.
     * @param localDataDomainName Data domain name as used locally.
     * @return User domain identity for the admin user.
     */
    @Override
    public UserDomainIdentity createDataDomainAdminIdentityForLocalDomain(String localDataDomainName) {
        if (!Objects.equals(UserDomainIdentity.DEFAULT_DATA_DOMAIN, this.dqoUserConfigurationProperties.getDefaultDataDomain()) &&
            Objects.equals(localDataDomainName, UserDomainIdentity.DEFAULT_DATA_DOMAIN)) {
            return createDataDomainAdminIdentityForCloudDomain(this.dqoUserConfigurationProperties.getDefaultDataDomain());
        }

        return createDataDomainAdminIdentityForCloudDomain(localDataDomainName);
    }
}
