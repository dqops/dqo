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
package com.dqops.core.configuration;

import com.dqops.testutils.TestFolderUtilities;
import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.util.FileSystemUtils;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Object mother for the configuration.
 */
public final class DqoUserConfigurationPropertiesObjectMother {
    public static final String TEMPORARY_HOME_NAME = "temporary-user-home";

    /**
     * Creates a default configuration properties that pull the settings from environment variables and the application-test.resource file.
     * @return Default configuration that uses a testable home.
     */
    public static DqoUserConfigurationProperties createDefaultUserConfiguration() {
        DqoUserConfigurationProperties configurationProperties = BeanFactoryObjectMother.getBeanFactory().getBean(DqoUserConfigurationProperties.class);
        return configurationProperties;
    }

    /**
     * Creates a default configuration properties that pull the settings from environment variables and the application-test.resource file.
     * @param recreateHomeDirectory Recreates the target/test-user-home
     * @return Default configuration that uses a testable home.
     */
    public static DqoUserConfigurationProperties createDefaultUserConfiguration(boolean recreateHomeDirectory) {
        DqoUserConfigurationProperties configurationProperties = createDefaultUserConfiguration();

        if (recreateHomeDirectory) {
            try {
                Path path = Path.of(configurationProperties.getHome());
                if (Files.exists(path)) {
                    FileSystemUtils.deleteRecursively(path);
                }
                Files.createDirectories(path);
            }
            catch(Exception ex) {
                throw new RuntimeException("Cannot create a temporary home", ex);
            }
        }
        return configurationProperties;
    }

    /**
     * Creates a user home configuration using a temporary user home. The user home (target/temporary-user-home) is also removed and recreated as an empty file.
     * @param recreateHomeDirectory Recreate the temporary user directory (remove the directory with all content and create again).
     * @return User configuration.
     */
    public static DqoUserConfigurationProperties createConfigurationWithTemporaryUserHome(boolean recreateHomeDirectory) {
        try {
            DqoUserConfigurationProperties defaults = BeanFactoryObjectMother.getBeanFactory().getBean(DqoUserConfigurationProperties.class);
            DqoUserConfigurationProperties cloned = defaults.clone();
            String temporaryUserHomePath = TestFolderUtilities.ensureTestableFolder(TEMPORARY_HOME_NAME, recreateHomeDirectory);

            cloned.setHasLocalHome(true);
            cloned.setHome(temporaryUserHomePath);
            return cloned;
        }
        catch(Exception ex) {
            throw new RuntimeException("Cannot create a temporary home", ex);
        }
    }
}
