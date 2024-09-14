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

import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.settings.SettingsWrapper;
import com.dqops.metadata.settings.domains.LocalDataDomainSpec;
import com.dqops.metadata.settings.domains.LocalDataDomainSpecMap;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Data domain registry that maintains the list of active data domains. When the list of domains is changed, it asks the data domain manager to update the domains.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LocalDataDomainRegistryImpl implements LocalDataDomainRegistry {
    private final UserHomeContextFactory userHomeContextFactory;
    private final DqoUserConfigurationProperties dqoUserConfigurationProperties;
    private final Object lock = new Object();
    private final LocalDataDomainSpecMap loadedDomains = new LocalDataDomainSpecMap();
    private LocalDataDomainManager localDataDomainManager;

    /**
     * Dependency injection constructor.
     * @param userHomeContextFactory User home factory to load the default domain.
     * @param dqoUserConfigurationProperties User home configuration parameters to detect if the instance was started with the default (root) domain and can have subdomain, or it is a single domain instance.
     */
    @Autowired
    public LocalDataDomainRegistryImpl(UserHomeContextFactory userHomeContextFactory,
                                       DqoUserConfigurationProperties dqoUserConfigurationProperties) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.dqoUserConfigurationProperties = dqoUserConfigurationProperties;
    }

    /**
     * Starts the service and loads the data domain list.
     * @param localDataDomainManager Back reference to the data domain manager that will be used to manage local domains. It is provided a back reference to avoid circular references.
     */
    @Override
    public void start(LocalDataDomainManager localDataDomainManager) {
        this.localDataDomainManager = localDataDomainManager;

        String defaultDataDomain = this.dqoUserConfigurationProperties.getDefaultDataDomain();
        if (!Objects.equals(defaultDataDomain, UserDomainIdentity.DEFAULT_DATA_DOMAIN)) {
            return; // this instance is bound to a non-default domain, it is a single domain instance
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY, true);
        LocalSettingsSpec localSettingsSpec = userHomeContext.getUserHome().getSettings().getSpec();
        if (localSettingsSpec == null) {
            return;
        }

        for (Map.Entry<String, LocalDataDomainSpec> domainKeySpec : localSettingsSpec.getDataDomains().entrySet()) {
            this.loadedDomains.put(domainKeySpec.getKey(), domainKeySpec.getValue());
            this.localDataDomainManager.initializeLocalDataDomain(domainKeySpec.getValue());
        }
    }

    /**
     * Activates a local data domain.
     * @param newDataDomainSpec Data domain specification.
     */
    public void updateLocalDataDomain(LocalDataDomainSpec newDataDomainSpec) {
        LocalDataDomainSpec existingDomain;
        LocalDataDomainSpec clonedNewDomainSpec = newDataDomainSpec.deepClone();

        synchronized (this.lock) {
            String dataDomainName = newDataDomainSpec.getDataDomainName();
            existingDomain = this.loadedDomains.get(dataDomainName);
            if (existingDomain != null && !Objects.equals(existingDomain, newDataDomainSpec)) {
                return; // no changes
            }

            this.loadedDomains.put(dataDomainName, clonedNewDomainSpec);
        }

        if (existingDomain == null) {
            this.localDataDomainManager.initializeLocalDataDomain(clonedNewDomainSpec);
        } else {
            this.localDataDomainManager.updateLocalDataDomain(existingDomain, clonedNewDomainSpec);
        }
    }

    /**
     * Deletes a local data domain.
     * @param oldDomainName Local data domain name.
     */
    public void deleteLocalDataDomain(String oldDomainName) {
        LocalDataDomainSpec existingDomain;

        synchronized (this.lock) {
            existingDomain = this.loadedDomains.get(oldDomainName);
            if (existingDomain == null) {
                return; // domain already deleted
            }

            this.loadedDomains.remove(oldDomainName);
        }

        this.localDataDomainManager.updateLocalDataDomain(existingDomain, null);
    }

    /**
     * Return a collection of nested data domain names (excluding the default domain).
     *
     * @return List of nested domains.
     */
    @Override
    public Collection<LocalDataDomainSpec> getNestedDataDomains() {
        if (this.localDataDomainManager == null) {
            throw new DqoRuntimeException("Data domain registry not initialized yet");
        }

        synchronized (this.lock) {
            return Collections.unmodifiableCollection(new ArrayList<>(this.loadedDomains.values()));
        }
    }

    /**
     * Replaces the current list of data domains with a new list of domains. Some domains are deleted, other created locally.
     *
     * @param newDataDomainList New list of data domains.
     */
    @Override
    public void replaceDataDomainList(List<LocalDataDomainSpec> newDataDomainList) {
        Map<String, LocalDataDomainSpec> currentDataDomainsMap = getNestedDataDomains()
                .stream()
                .collect(Collectors.toMap(spec -> spec.getDataDomainName(), spec -> spec));

        Map<String, LocalDataDomainSpec> newDataDomainsMap = newDataDomainList
                .stream()
                .collect(Collectors.toMap(spec -> spec.getDataDomainName(), spec -> spec));

        for (Map.Entry<String, LocalDataDomainSpec> newDomainEntry : newDataDomainsMap.entrySet()) {
            String domainName = newDomainEntry.getKey();

            LocalDataDomainSpec newDomainSpec = newDomainEntry.getValue();
            LocalDataDomainSpec clonedNewDomainSpec = newDomainSpec.deepClone();
            LocalDataDomainSpec existingDomain = currentDataDomainsMap.get(domainName);
            if (existingDomain != null) {
                clonedNewDomainSpec.copyLocalPropertiesFrom(existingDomain);
            }

            this.updateLocalDataDomain(newDomainSpec);
        }

        for (String oldDomainName : currentDataDomainsMap.keySet()) {
            if (newDataDomainsMap.containsKey(oldDomainName)) {
                continue; // this domain still exists
            }

            this.deleteLocalDataDomain(oldDomainName);
        }
    }

    /**
     * Adds a local data domain. Saves the domain to the local settings and starts the data domain locally.
     *
     * @param localDataDomainSpec Data domain specification.
     */
    @Override
    public void addDataDomain(LocalDataDomainSpec localDataDomainSpec) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY, false);
        SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();
        LocalSettingsSpec localSettingsSpec = settingsWrapper.getSpec();
        if (localSettingsSpec == null) {
            localSettingsSpec = new LocalSettingsSpec();
            settingsWrapper.setSpec(localSettingsSpec);
        }

        LocalDataDomainSpecMap dataDomains = localSettingsSpec.getDataDomains();
        dataDomains.put(localDataDomainSpec.getDataDomainName(), localDataDomainSpec);

        this.updateLocalDataDomain(localDataDomainSpec); // start the domain

        userHomeContext.flush(); // save the configuration
    }

    /**
     * Deletes a local data domain. Operations for the domain (job scheduling) is stopped, but the local data is preserved.
     *
     * @param dataDomainName Data domain name.
     */
    @Override
    public boolean deleteDataDomain(String dataDomainName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(UserDomainIdentity.LOCAL_INSTANCE_ADMIN_IDENTITY, false);
        SettingsWrapper settingsWrapper = userHomeContext.getUserHome().getSettings();
        LocalSettingsSpec localSettingsSpec = settingsWrapper.getSpec();
        if (localSettingsSpec == null) {
            return false;
        }

        LocalDataDomainSpecMap dataDomains = localSettingsSpec.getDataDomains();
        if (!dataDomains.containsKey(dataDomainName)) {
            return false;
        }

        dataDomains.remove(dataDomainName);
        userHomeContext.flush(); // save the configuration

        this.deleteLocalDataDomain(dataDomainName);

        return true;
    }
}
