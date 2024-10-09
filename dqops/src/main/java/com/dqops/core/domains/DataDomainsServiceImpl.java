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

package com.dqops.core.domains;

import com.dqops.cloud.rest.api.DataDomainsApi;
import com.dqops.cloud.rest.handler.ApiClient;
import com.dqops.cloud.rest.model.DataDomainModel;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudLicenseType;
import com.dqops.core.dqocloud.client.DqoCloudApiClientFactory;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.settings.domains.LocalDataDomainSpec;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Data domain management service that synchronizes data domain with the DQOps Cloud, creates, deletes and synchronizes the list of data domains.
 */
@Service
@Slf4j
public class DataDomainsServiceImpl implements DataDomainsService {
    private final DqoCloudApiClientFactory dqoCloudApiClientFactory;
    private final DqoUserPrincipalProvider dqoUserPrincipalProvider;
    private final LocalDataDomainRegistry localDataDomainRegistry;
    private final DqoUserConfigurationProperties dqoUserConfigurationProperties;

    /**
     * Creates the service, given all dependencies.
     * @param dqoCloudApiClientFactory DQOps cloud client factory.
     * @param dqoUserPrincipalProvider Principal provider to get the admin principal of the root domain (the DQOps cloud API key).
     * @param localDataDomainRegistry Local data domain registry - to update the list of domains.
     * @param dqoUserConfigurationProperties DQOps user configuration properties with the default domain name.
     */
    @Autowired
    public DataDomainsServiceImpl(DqoCloudApiClientFactory dqoCloudApiClientFactory,
                                  DqoUserPrincipalProvider dqoUserPrincipalProvider,
                                  LocalDataDomainRegistry localDataDomainRegistry,
                                  DqoUserConfigurationProperties dqoUserConfigurationProperties) {
        this.dqoCloudApiClientFactory = dqoCloudApiClientFactory;
        this.dqoUserPrincipalProvider = dqoUserPrincipalProvider;
        this.localDataDomainRegistry = localDataDomainRegistry;
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
    }

    /**
     * Creates a DQOps Cloud data domain client.
     * @param userDomainIdentity Admin user identity within the root data domain.
     * @return DQOps cloud data domain client.
     */
    protected DataDomainsApi createDataDomainClient(UserDomainIdentity userDomainIdentity) {
        ApiClient authenticatedClient = this.dqoCloudApiClientFactory.createAuthenticatedClient(userDomainIdentity);

        return new DataDomainsApi(authenticatedClient);
    }

    /**
     * Validates if the user is allowed to use data domains.
     * @param userPrincipal User principal of the local administrator.
     * @throws DqoDataDomainException When the user is not allowed to use data domains.
     */
    protected void validateDqopsLicense(DqoUserPrincipal userPrincipal) {
        DqoCloudApiKeyPayload apiKeyPayload = userPrincipal.getApiKeyPayload();
        if (apiKeyPayload == null) {
            throw new DqoDataDomainException("The instance has no DQOps Cloud API Key. Data domains require a valid account.");
        }

        if (apiKeyPayload.getLicenseType() != DqoCloudLicenseType.ENTERPRISE) {
            throw new DqoDataDomainException("Only users with ENTERPRISE licenses are allowed to create data domains.");
        }
    }

    /**
     * Downloads the list of data domains from DQOps cloud and configures the domains in the local DQOps instance.
     * @param silent When synchronization failed, does not throw an exception and just go forward.
     */
    @Override
    public void synchronizeDataDomainList(boolean silent) {
        try {
            List<DataDomainModel> cloudDataDomainsList = loadNestedDataDomainsFromServer();

            List<LocalDataDomainSpec> newDataDomainList = new ArrayList<>();
            for (DataDomainModel cloudDataDomainModel : cloudDataDomainsList) {
                LocalDataDomainSpec newDataDomainSpec = LocalDataDomainSpec.createFromCloudDomainModel(cloudDataDomainModel);
                newDataDomainList.add(newDataDomainSpec);
            }

            this.localDataDomainRegistry.replaceDataDomainList(newDataDomainList);
        }
        catch (Exception ex) {
            if (silent) {
                log.error("Failed to download a list of data domains from DQOps Cloud, error: " + ex.getMessage(), ex);
                return;
            }
            throw new DqoDataDomainException("Cannot synchronize the list of data domains with DQOps Cloud", ex);
        }
    }

    /**
     * Calls the DQOps server and reads a list of nested data domains defined for this instance.
     * @return List of data domains.
     */
    protected List<DataDomainModel> loadNestedDataDomainsFromServer() {
        DqoUserPrincipal rootDomainAdminUser = this.dqoUserPrincipalProvider.createLocalInstanceAdminPrincipal();
        validateDqopsLicense(rootDomainAdminUser);
        UserDomainIdentity userDomainIdentity = rootDomainAdminUser.getDataDomainIdentity();
        DataDomainsApi dataDomainClient = this.createDataDomainClient(userDomainIdentity);
        String tenantIdFull = userDomainIdentity.getTenantId() + "/" + userDomainIdentity.getTenantGroupId();

        List<DataDomainModel> cloudDataDomainsList = dataDomainClient.getDataDomainsList(userDomainIdentity.getTenantOwner(), tenantIdFull);
        return cloudDataDomainsList;
    }

    /**
     * Returns a list of all local data domains. It also returns the default data domain.
     * @return Return all data domains.
     */
    @Override
    public List<LocalDataDomainModel> getAllDataDomains() {
        Collection<LocalDataDomainSpec> allLocalDataDomains = this.localDataDomainRegistry.getAllLocalDataDomains();
        if (allLocalDataDomains == null) {
            return new ArrayList<>();
        }

        List<LocalDataDomainModel> allDomains = new ArrayList<>();
        allDomains.addAll(
            allLocalDataDomains.stream()
                .map(localDataDomainSpec -> LocalDataDomainModel.createFromSpec(localDataDomainSpec))
                .sorted(Comparator.comparing(model -> model.getDomainName().toLowerCase(Locale.ROOT))) // comparing by lower case will make sure that the "(default)" domain is first
                .collect(Collectors.toList())
        );

        return allDomains;
    }

    /**
     * Verifies that the given character can be used in a data domain name.
     * @param c Character to test.
     * @return True - a valid character, false - not valid.
     */
    public boolean isValidDataDomainCharacter(char c) {
        if (c == ' ' || c == '-' || c == '_') {
            return true;
        }

        if (c >= '0' && c <= '9') {
            return true;
        }

        if (c >= 'a' && c <= 'z') {
            return true;
        }

        if (c >= 'A' && c <= 'Z') {
            return true;
        }

        return false;
    }

    /**
     * Checks if the proposed data domain display name is valid.
     * @param dataDomainName Proposed data domain display name.
     * @return True when the name is valid, false when it contains invalid characters.
     */
    public boolean validateProposedDataDomainName(String dataDomainName) {
        if (Strings.isNullOrEmpty(dataDomainName)) {
            return false;
        }

        char firstChar = dataDomainName.charAt(0);
        if (!Character.isLetter(firstChar)) {
            return false;
        }

        char lastChar = dataDomainName.charAt(dataDomainName.length() - 1);
        if (!Character.isLetter(lastChar) && !Character.isDigit(lastChar)) {
            return false;
        }

        for (int i = 0; i < dataDomainName.length(); i++) {
            char c = dataDomainName.charAt(i);
            if (!isValidDataDomainCharacter(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a new data domain on the server and then create a local data domain.
     * @param dataDomainDisplayName Data domain display name.
     * @return Data domain model.
     */
    @Override
    public LocalDataDomainModel createDataDomain(String dataDomainDisplayName) {
        try {
            boolean isValidName = validateProposedDataDomainName(dataDomainDisplayName);
            if (!isValidName) {
                throw new DqoDataDomainException("The data domain name '" + dataDomainDisplayName + "' is invalid. Only names containing digits, spaces, and latin letters are accepted.");
            }

            LocalDataDomainSpec localDataDomainSpec = createDataDomainOnServer(dataDomainDisplayName);

            this.localDataDomainRegistry.addNestedDataDomain(localDataDomainSpec);

            LocalDataDomainModel localDataDomainModel = LocalDataDomainModel.createFromSpec(localDataDomainSpec);
            return localDataDomainModel;
        }
        catch (DqoDataDomainException dqoDataDomainException) {
            throw dqoDataDomainException;
        }
        catch (Exception ex) {
            throw new DqoDataDomainException("Cannot create a new data domain. Your DQOps Cloud API Key is invalid, or you don't have an ENTERPRISE license of DQOps. Error: " + ex.getMessage(), ex);
        }
    }

    /**
     * Creates a new nested data domain on the server.
     * @param dataDomainDisplayName Data domain display name.
     * @return Data domain information.
     */
    protected LocalDataDomainSpec createDataDomainOnServer(String dataDomainDisplayName) {
        DqoUserPrincipal rootDomainAdminUser = this.dqoUserPrincipalProvider.createLocalInstanceAdminPrincipal();
        validateDqopsLicense(rootDomainAdminUser);
        UserDomainIdentity userDomainIdentity = rootDomainAdminUser.getDataDomainIdentity();
        DataDomainsApi dataDomainClient = this.createDataDomainClient(userDomainIdentity);
        String tenantIdFull = userDomainIdentity.getTenantId() + "/" + userDomainIdentity.getTenantGroupId();

        DataDomainModel cloudDataDomainModel = dataDomainClient.createDataDomain(dataDomainDisplayName, userDomainIdentity.getTenantOwner(), tenantIdFull);
        LocalDataDomainSpec localDataDomainSpec = LocalDataDomainSpec.createFromCloudDomainModel(cloudDataDomainModel);
        return localDataDomainSpec;
    }

    /**
     * Deletes a data domain from the server and locally.
     * @param dataDomainName Data domain name.
     */
    @Override
    public void deleteDataDomain(String dataDomainName) {
        try {
            DqoUserPrincipal rootDomainAdminUser = this.dqoUserPrincipalProvider.createLocalInstanceAdminPrincipal();
            validateDqopsLicense(rootDomainAdminUser);
            UserDomainIdentity userDomainIdentity = rootDomainAdminUser.getDataDomainIdentity();
            DataDomainsApi dataDomainClient = this.createDataDomainClient(userDomainIdentity);
            String tenantIdFull = userDomainIdentity.getTenantId() + "/" + userDomainIdentity.getTenantGroupId();

            this.localDataDomainRegistry.deleteNestedDataDomain(dataDomainName);
            dataDomainClient.deleteDataDomain(dataDomainName, userDomainIdentity.getTenantOwner(), tenantIdFull);
        }
        catch (Exception ex) {
            throw new DqoDataDomainException("Cannot delete a data domain. Your DQOps Cloud API Key is invalid, or you don't have an ENTERPRISE license of DQOps. Error: " + ex.getMessage(), ex);
        }
    }
}
