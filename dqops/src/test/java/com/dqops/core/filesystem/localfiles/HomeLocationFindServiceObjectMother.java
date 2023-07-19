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
