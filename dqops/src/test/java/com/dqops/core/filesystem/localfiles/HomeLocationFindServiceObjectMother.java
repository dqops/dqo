/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.filesystem.localfiles;

import com.dqops.core.configuration.DqoConfigurationProperties;
import com.dqops.core.configuration.DqoConfigurationPropertiesObjectMother;
import com.dqops.core.configuration.DqoUserConfigurationProperties;
import com.dqops.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

import java.nio.file.Path;

/**
 * Object mother for HomeLocationFindService.
 */
public class HomeLocationFindServiceObjectMother {
    /**
     * Returns the default home location finder.
     * @return Default home location finder.
     */
    public static HomeLocationFindService getDefaultHomeFinder() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(HomeLocationFindService.class);
    }

    /**
     * Returns the default home location finder.
     * @param recreateUserHomeDirectory Recreate the user home directory.
     * @return Default home location finder.
     */
    public static HomeLocationFindService getDefaultHomeFinder(boolean recreateUserHomeDirectory) {
        DqoUserConfigurationProperties configurationWithTemporaryUserHome =
                DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration(recreateUserHomeDirectory);
        DqoConfigurationProperties dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        return new HomeLocationFindServiceImpl(configurationWithTemporaryUserHome, dqoConfigurationProperties);
    }

    /**
     * Returns a user home finder for the test user home.
     * @param recreateUserHomeDirectory Recreate the user home directory.
     * @return Home finder for the test user home.
     */
    public static HomeLocationFindServiceImpl getWithTestUserHome(boolean recreateUserHomeDirectory) {
        DqoUserConfigurationProperties configurationWithTemporaryUserHome =
                DqoUserConfigurationPropertiesObjectMother.createConfigurationWithTemporaryUserHome(recreateUserHomeDirectory);
        DqoConfigurationProperties dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        return new HomeLocationFindServiceImpl(configurationWithTemporaryUserHome, dqoConfigurationProperties);
    }

    /**
     * Returns a user home finder for a user home in the given path.
     * @param userHomePath Absolute path to the user home.
     * @return Home finder for the test user home.
     */
    public static HomeLocationFindServiceImpl getCustomUserHome(Path userHomePath) {
        DqoUserConfigurationProperties userConfigurationProperties =
                DqoUserConfigurationPropertiesObjectMother.createDefaultUserConfiguration();
        userConfigurationProperties.setHome(userHomePath.toString());
        DqoConfigurationProperties dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
        return new HomeLocationFindServiceImpl(userConfigurationProperties, dqoConfigurationProperties);
    }
}
