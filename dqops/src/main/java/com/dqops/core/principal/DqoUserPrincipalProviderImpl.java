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
import com.dqops.core.dqocloud.apikey.DqoCloudApiKey;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyProvider;
import com.dqops.core.dqocloud.datadomains.CliCurrentDataDomainService;
import com.dqops.core.dqocloud.login.DqoUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Service that returns the user principal of the user identified by the DQOps Cloud API Key.
 * This provider should be called by operations executed from the DQOps command line to obtain the principal or when DQOps is running in a single user mode.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DqoUserPrincipalProviderImpl implements DqoUserPrincipalProvider {
    private final DqoCloudApiKeyProvider dqoCloudApiKeyProvider;
    private final CliCurrentDataDomainService cliCurrentDataDomainService;
    private final DqoUserConfigurationProperties dqoUserConfigurationProperties;
    private final UserDomainIdentityFactory userDomainIdentityFactory;

    /**
     * Dependency injection constructor.
     * @param dqoCloudApiKeyProvider DQOps Cloud API Key provider service.
     * @param cliCurrentDataDomainService Service that returns the selected data domain that is used from command line.
     * @param dqoUserConfigurationProperties User home configuration parameters, with the default data domain that is mounted.
     * @param userDomainIdentityFactory User data domain identity factory.
     */
    @Autowired
    public DqoUserPrincipalProviderImpl(DqoCloudApiKeyProvider dqoCloudApiKeyProvider,
                                        CliCurrentDataDomainService cliCurrentDataDomainService,
                                        DqoUserConfigurationProperties dqoUserConfigurationProperties,
                                        UserDomainIdentityFactory userDomainIdentityFactory) {
        this.dqoCloudApiKeyProvider = dqoCloudApiKeyProvider;
        this.cliCurrentDataDomainService = cliCurrentDataDomainService;
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
        this.userDomainIdentityFactory = userDomainIdentityFactory;
    }

    /**
     * Creates a DQOps user principal for the user who has direct access to DQOps instance, running operations from CLI
     * or using the DQOps shell directly.
     * @return User principal that has full admin rights when the instance is not authenticated to DQOps Cloud or limited to the role in the DQOps Cloud Api key.
     */
    @Override
    public DqoUserPrincipal createLocalInstanceAdminPrincipal() {
        /******** Uncomment the following code and comment the rest to use a hardcoded principal with limited access rights for testing purposes */
    //    DqoUserRole testedRole = DqoUserRole.VIEWER;
    //    List<GrantedAuthority> testPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(testedRole);
    //    DqoUserPrincipal dqoUserPrincipalTest = new DqoUserPrincipal("", testedRole, testPrivileges, UserDomainIdentity.DEFAULT_DATA_DOMAIN, UserDomainIdentity.DEFAULT_DATA_DOMAIN, null, null, null);
    //    return dqoUserPrincipalTest;
        /******** end of testing code */

        DqoCloudApiKey dqoCloudApiKey = this.dqoCloudApiKeyProvider.getApiKey(null);
        if (dqoCloudApiKey == null) {
            // user not authenticated to DQOps Cloud, so we use a default token
            List<GrantedAuthority> adminPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(DqoUserRole.ADMIN);
            DqoUserPrincipal dqoUserPrincipalLocal = new DqoUserPrincipal("", DqoUserRole.ADMIN, adminPrivileges,
                    UserDomainIdentity.ROOT_DATA_DOMAIN, this.dqoUserConfigurationProperties.getDefaultDataDomain(), null, null, null, null);
            return dqoUserPrincipalLocal;
        }

        DqoCloudApiKeyPayload apiKeyPayload = dqoCloudApiKey.getApiKeyPayload();
        List<GrantedAuthority> grantedPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(apiKeyPayload.getAccountRole());
        String defaultDataDomainCloud = this.dqoUserConfigurationProperties.getDefaultDataDomain();
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal(apiKeyPayload.getSubject(), apiKeyPayload.getAccountRole(),
                grantedPrivileges, apiKeyPayload, UserDomainIdentity.ROOT_DATA_DOMAIN, defaultDataDomainCloud, null);

        return dqoUserPrincipal;
    }

    /**
     * Creates a DQOps user principal for the user who has direct access to DQOps instance, running operations from CLI
     * or using the DQOps shell directly.
     *
     * @param dataDomainName Data domain name.
     * @return User principal that has full admin rights when the instance is not authenticated to DQOps Cloud or limited to the role in the DQOps Cloud Api key.
     */
    @Override
    public DqoUserPrincipal createLocalDomainAdminPrincipal(String dataDomainName) {
        String domainMountName = Objects.equals(dataDomainName, this.dqoUserConfigurationProperties.getDefaultDataDomain()) ?
                UserDomainIdentity.ROOT_DATA_DOMAIN : dataDomainName;

        DqoCloudApiKey dqoCloudApiKey = this.dqoCloudApiKeyProvider.getApiKey(null);
        if (dqoCloudApiKey == null) {
            // user not authenticated to DQOps Cloud, so we use a default token
            List<GrantedAuthority> adminPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(DqoUserRole.ADMIN);
            DqoUserPrincipal dqoUserPrincipalLocal = new DqoUserPrincipal("", DqoUserRole.ADMIN, adminPrivileges,
                    domainMountName, this.dqoUserConfigurationProperties.getDefaultDataDomain(),
                    null, null, null, null);
            return dqoUserPrincipalLocal;
        }

        DqoCloudApiKeyPayload apiKeyPayload = dqoCloudApiKey.getApiKeyPayload();
        List<GrantedAuthority> grantedPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(apiKeyPayload.getAccountRole());
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal(
                apiKeyPayload.getSubject(), apiKeyPayload.getAccountRole(),
                grantedPrivileges, apiKeyPayload,
                domainMountName, dataDomainName, null);

        return dqoUserPrincipal;
    }

    /**
     * Returns the principal of the local user who has direct access to the command line and runs operations on the DQOps shell.
     *
     * @return The principal of the local user who is using DQOps from the terminal.
     */
    @Override
    public DqoUserPrincipal getLocalUserPrincipal() {
        DqoCloudApiKey dqoCloudApiKey = this.dqoCloudApiKeyProvider.getApiKey(null);
        if (dqoCloudApiKey == null) {
            // user not authenticated to DQOps Cloud, so we use a default token
            List<GrantedAuthority> adminPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(DqoUserRole.ADMIN);
            DqoUserPrincipal dqoUserPrincipalLocal = new DqoUserPrincipal("", DqoUserRole.ADMIN, adminPrivileges,
                    UserDomainIdentity.ROOT_DATA_DOMAIN, this.dqoUserConfigurationProperties.getDefaultDataDomain(), null, null, null, null);
            return dqoUserPrincipalLocal;
        }

        DqoCloudApiKeyPayload apiKeyPayload = dqoCloudApiKey.getApiKeyPayload();
        List<GrantedAuthority> grantedPrivileges = DqoPermissionGrantedAuthorities.getPrivilegesForRole(apiKeyPayload.getAccountRole()); // NOTE: we don't know the role of the user at the data domain, so we take the account role.. only a super admin can use multiple domains locally
        String currentDataDomainCloudName = this.cliCurrentDataDomainService.getCurrentDataDomain();
        String currentDataDomainFolderName = this.userDomainIdentityFactory.mapDataDomainCloudNameToFolder(currentDataDomainCloudName);
        DqoUserPrincipal dqoUserPrincipal = new DqoUserPrincipal(apiKeyPayload.getSubject(), apiKeyPayload.getAccountRole(),
                grantedPrivileges, apiKeyPayload, currentDataDomainFolderName, currentDataDomainCloudName, null);

        return dqoUserPrincipal;
    }
}
