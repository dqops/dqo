/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
        DqoUserConfigurationProperties configurationProperties = BeanFactoryObjectMother.getBeanFactory().getBean(DqoUserConfigurationProperties.class).clone();
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
