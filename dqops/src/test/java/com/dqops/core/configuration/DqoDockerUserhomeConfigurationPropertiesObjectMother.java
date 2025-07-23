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

import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Object mother for the configuration.
 */
public final class DqoDockerUserhomeConfigurationPropertiesObjectMother {

    /**
     * Creates a default configuration properties that pull the settings from environment variables and the application-test.resource file.
     * @return Default configuration.
     */
    public static DqoDockerUserhomeConfigurationProperties createDefaultDockerUserhomeConfiguration() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(DqoDockerUserhomeConfigurationProperties.class).clone();
    }
}
