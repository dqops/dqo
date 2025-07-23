/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.storage.localfiles.userhome;

import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsFactoryImpl;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalFactoryObjectMother;
import com.dqops.core.configuration.*;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.principal.UserDomainIdentityFactoryImpl;
import com.dqops.core.scheduler.defaults.DefaultSchedulesProviderImpl;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.serialization.YamlSerializerObjectMother;

/**
 * Object mother for {@link LocalUserHomeCreator}
 */
public class LocalUserHomeCreatorObjectMother {
    private static boolean isInitialized;

    /**
     * Returns a new instance of a dqo user home creator with a customized configuration to disable logging inside the .logs folder.
     * @return Non-logging dqo user home creator.
     */
    public static LocalUserHomeCreator createNewNoLogging() {
        HomeLocationFindService homeLocationFindService = BeanFactoryObjectMother.getBeanFactory().getBean(HomeLocationFindService.class);
        DqoLoggingConfigurationProperties noLoggingConfiguration = DqoLoggingConfigurationPropertiesObjectMother.getNoLoggingConfiguration();
        DqoUserConfigurationProperties defaultUserConfiguration = DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration();
        DqoDockerUserhomeConfigurationProperties defaultDockerUserhomeConfiguration = DqoDockerUserhomeConfigurationPropertiesObjectMother.createDefaultDockerUserhomeConfiguration();
        DqoSchedulerDefaultSchedulesConfigurationProperties defaultSchedulesConfigurationProperties = DqoSchedulerDefaultSchedulesConfigurationPropertiesObjectMother.getDefault();
        TerminalFactory terminalFactory = TerminalFactoryObjectMother.getDefault();
        UserHomeContextFactory userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        DefaultSchedulesProviderImpl defaultSchedulesProvider = new DefaultSchedulesProviderImpl(defaultSchedulesConfigurationProperties,
                userHomeContextFactory);
        DqoInstanceConfigurationProperties dqoInstanceConfigurationProperties = DqoInstanceConfigurationPropertiesObjectMother.getDefault();
        LocalUserHomeCreatorImpl localUserHomeCreator = new LocalUserHomeCreatorImpl(
                homeLocationFindService, userHomeContextFactory, terminalFactory, noLoggingConfiguration, defaultUserConfiguration, defaultDockerUserhomeConfiguration,
                dqoInstanceConfigurationProperties, YamlSerializerObjectMother.getDefault(), defaultSchedulesProvider,
                new DefaultObservabilityCheckSettingsFactoryImpl(),
                new UserDomainIdentityFactoryImpl(defaultUserConfiguration));
        return localUserHomeCreator;
    }

    /**
     * Returns the default (singleton) implementation of the local dqo user home creator. It should not be used to more than once
     * to initialize the user home because it will set up a file logger.
     * @return Default dqo user home creator.
     */
    public static LocalUserHomeCreator getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(LocalUserHomeCreator.class);
    }

    /**
     * Initialized a DQOps User home at a given location.
     * @param pathToUserHome Path to a dqo user home to initialize.
     */
    public static void initializeDqoUserHomeAt(String pathToUserHome) {
        LocalUserHomeCreator localUserHomeCreator = createNewNoLogging();
        localUserHomeCreator.initializeDqoUserHome(pathToUserHome);
    }

    /**
     * Initializes silently the default dqo user home.
     */
    public static void initializeDefaultDqoUserHomeSilentlyOnce() {
        if (isInitialized) {
            return;
        }

        getDefault().ensureDefaultUserHomeIsInitialized(true);

        isInitialized = true;
    }
}
