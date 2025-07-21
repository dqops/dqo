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

/**
 * Factory that creates a user domain identity, taking into account which data domain is mounted in the root DQOps user home folder.
 */
public interface UserDomainIdentityFactory {
    /**
     * Maps the name of a real data domain (used on the DQOps Cloud data lake) to the local folder. Takes into account that
     * a data domain could be mounted at the root DQOps user home folder.
     *
     * @param dataDomainCloudName Data domain name as used in DQOps cloud (the real data domain name).
     * @return Local data domain folder name (just name, no path).
     */
    String mapDataDomainCloudNameToFolder(String dataDomainCloudName);

    /**
     * Creates a data domain identity for the admin user, given the data domain name on the cloud.
     * Checks what data domain was mounted at the local DQOps user home root folder and picks the right folder name where the data will be mounted.
     *
     * @param dataDomainCloudName Data domain name as used in DQOps cloud (the real data domain name).
     * @return User domain identity for the admin user.
     */
    UserDomainIdentity createDataDomainAdminIdentityForCloudDomain(String dataDomainCloudName);
}
