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
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for {@link DqoLoggingConfigurationProperties}
 */
public final class DqoLoggingConfigurationPropertiesObjectMother {
    /**
     * Returns the default logging configuration properties, cloned to enable modifications.
     * The default configuration enables logging in the user home. It should not be used for unit tests when custom user homes
     * are created, because we can add too many log appenders.
     * @return Logging configuration properties.
     */
    public static DqoLoggingConfigurationProperties getDefault() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        DqoLoggingConfigurationProperties configurationProperties = beanFactory.getBean(DqoLoggingConfigurationProperties.class);
        return configurationProperties.clone();
    }

    /**
     * Creates a logging configuration that has logging disabled.
     * @return Logging configuration with disabled logging in the user home's .logs folder.
     */
    public static DqoLoggingConfigurationProperties getNoLoggingConfiguration() {
        DqoLoggingConfigurationProperties loggingConfigurationProperties = new DqoLoggingConfigurationProperties();
        loggingConfigurationProperties.setEnableUserHomeLogging(false);
        return loggingConfigurationProperties;
    }
}
