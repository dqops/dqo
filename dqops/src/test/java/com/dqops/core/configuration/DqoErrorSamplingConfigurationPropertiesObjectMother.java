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
 * Object mother for {@link DqoSensorLimitsConfigurationProperties}
 */
public class DqoErrorSamplingConfigurationPropertiesObjectMother {
    /**
     * Returns the default (but cloned) dqo sensor limit configuration.
     * @return DQOps sensor limit configuration.
     */
    public static DqoErrorSamplingConfigurationProperties getDefault() {
        return BeanFactoryObjectMother.getBeanFactory().getBean(DqoErrorSamplingConfigurationProperties.class).clone();
    }
}
