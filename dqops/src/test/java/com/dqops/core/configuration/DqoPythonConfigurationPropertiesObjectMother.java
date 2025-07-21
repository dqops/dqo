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
 * Object mother for {@link DqoPythonConfigurationProperties} python configuration properties.
 */
public class DqoPythonConfigurationPropertiesObjectMother {
    /**
     * Returns the default python configuration properties, cloned to enable modifications.
     * @return Python configuration properties.
     */
    public static DqoPythonConfigurationProperties getDefaultCloned() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        DqoPythonConfigurationProperties configurationProperties = beanFactory.getBean(DqoPythonConfigurationProperties.class);
        return configurationProperties.clone();
    }
}
