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
        String effectiveCloudDomainName = dataDomainCloudName == null ? UserDomainIdentity.ROOT_DATA_DOMAIN : dataDomainCloudName;

        if (Objects.equals(UserDomainIdentity.ROOT_DATA_DOMAIN, this.dqoUserConfigurationProperties.getDefaultDataDomain())) {
            return effectiveCloudDomainName;
        } else {
            if (Objects.equals(effectiveCloudDomainName, this.dqoUserConfigurationProperties.getDefaultDataDomain())) {
                return UserDomainIdentity.ROOT_DATA_DOMAIN;
            } else if (Objects.equals(effectiveCloudDomainName, UserDomainIdentity.ROOT_DATA_DOMAIN)) {
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
        String effectiveCloudDomainName = dataDomainCloudName == null || Objects.equals(UserDomainIdentity.ROOT_DOMAIN_ALTERNATE_NAME, dataDomainCloudName) ?
                UserDomainIdentity.ROOT_DATA_DOMAIN : dataDomainCloudName;
        String dataDomainFolderName = mapDataDomainCloudNameToFolder(effectiveCloudDomainName);
        UserDomainIdentity dataDomainAdminIdentity = UserDomainIdentity.createDataDomainAdminIdentity(dataDomainFolderName, effectiveCloudDomainName);
        return dataDomainAdminIdentity;
    }
}
