package com.dqops.core.configuration;

import com.dqops.utils.BeanFactoryObjectMother;

/**
 * Object mother for a DQOps instance configuration properties.
 */
public class DqoInstanceConfigurationPropertiesObjectMother {
    /**
     * Returns the instance configuration properties with the test configuration.
     * @return Instance configuration properties.
     */
    public static DqoInstanceConfigurationProperties getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(DqoInstanceConfigurationProperties.class).clone();
    }
}
