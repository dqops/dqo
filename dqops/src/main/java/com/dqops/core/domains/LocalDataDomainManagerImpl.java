/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.core.domains;

import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.filesystem.virtual.HomeFolderPath;
import com.dqops.core.locks.UserHomeLockManager;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.metadata.settings.domains.LocalDataDomainSpec;
import com.dqops.metadata.storage.localfiles.userhome.LocalUserHomeCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * Data domain manager that maintains the configuration and activation of local data domains.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LocalDataDomainManagerImpl implements LocalDataDomainManager {
    private final LocalUserHomeCreator localUserHomeCreator;
    private final HomeLocationFindService homeLocationFindService;
    private final LocalDataDomainRegistry localDataDomainRegistry;
    private final UserHomeLockManager userHomeLockManager;
    private final JobSchedulerService jobSchedulerService;

    /**
     * Dependency injection constructor.
     * @param localUserHomeCreator User home creator to set up the domain folder structure.
     * @param homeLocationFindService Service to find the home location.
     * @param localDataDomainRegistry Data domain registry.
     * @param userHomeLockManager User home lock manager - to register locks.
     * @param jobSchedulerService Job scheduler service.
     */
    @Autowired
    public LocalDataDomainManagerImpl(LocalUserHomeCreator localUserHomeCreator,
                                      HomeLocationFindService homeLocationFindService,
                                      LocalDataDomainRegistry localDataDomainRegistry,
                                      UserHomeLockManager userHomeLockManager,
                                      JobSchedulerService jobSchedulerService) {
        this.localUserHomeCreator = localUserHomeCreator;
        this.homeLocationFindService = homeLocationFindService;
        this.localDataDomainRegistry = localDataDomainRegistry;
        this.userHomeLockManager = userHomeLockManager;
        this.jobSchedulerService = jobSchedulerService;
    }

    /**
     * Activates the data domains.
     */
    @Override
    public void start() {
        this.localDataDomainRegistry.start(this);
    }

    /**
     * Activates a local data domain.
     * @param dataDomainSpec Data domain specification.
     */
    @Override
    public void initializeLocalDataDomain(LocalDataDomainSpec dataDomainSpec) {
        String dataDomainName = dataDomainSpec.getDataDomainName();
        HomeFolderPath domainRootVirtualFolder = new HomeFolderPath(dataDomainName);
        Path pathToRootUserHome = Path.of(this.homeLocationFindService.getRootUserHomePath());
        Path dataDomainRootFolder = pathToRootUserHome.resolve(domainRootVirtualFolder.toRelativePath());
        String pathToDataDomainFolder = dataDomainRootFolder.toString();

        this.userHomeLockManager.createLocksForDataDomain(dataDomainName);

        if (this.localUserHomeCreator.isDqoUserHomeInitialized(pathToDataDomainFolder)) {
            this.localUserHomeCreator.upgradeUserHomeConfigurationWhenMissing(dataDomainName);
        } else {
            this.localUserHomeCreator.initializeDqoUserHome(pathToDataDomainFolder);
        }
    }

    /**
     * Updates the configuration of a data domain.
     *
     * @param existingDataDomainSpec Old data domain configuration.
     * @param updatedDataDomainSpec  New data domain configuration or null, when the domain should be stopped.
     */
    @Override
    public void updateLocalDataDomain(LocalDataDomainSpec existingDataDomainSpec, LocalDataDomainSpec updatedDataDomainSpec) {
        String domainName = existingDataDomainSpec != null ? existingDataDomainSpec.getDataDomainName() : updatedDataDomainSpec.getDataDomainName();

        boolean isNewDomain = existingDataDomainSpec == null;
        boolean deleteDomain = updatedDataDomainSpec == null;
        boolean updateDomainSettings = existingDataDomainSpec != null && updatedDataDomainSpec != null;

        this.jobSchedulerService.reconcileScheduledDomains(); // update the list of schedules (activate or deactivate)
    }
}
