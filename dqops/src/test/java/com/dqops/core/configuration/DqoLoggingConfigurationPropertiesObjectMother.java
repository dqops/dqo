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
