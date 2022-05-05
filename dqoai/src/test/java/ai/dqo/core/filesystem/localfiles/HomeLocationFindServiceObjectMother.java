/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.filesystem.localfiles;

import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import ai.dqo.core.configuration.DqoUserConfigurationProperties;
import ai.dqo.core.configuration.DqoUserConfigurationPropertiesObjectMother;
import ai.dqo.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

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
}
