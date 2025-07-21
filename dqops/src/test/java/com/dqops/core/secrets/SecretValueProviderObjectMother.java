/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.secrets;

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for SecretValueProvider.
 */
public class SecretValueProviderObjectMother {
    /**
     * Returns the singleton instance of a secret value provider.
     * @return Secret value provider.
     */
    public static SecretValueProvider getInstance() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(SecretValueProvider.class);
    }

    /**
     * Expands possible environment variables or secrets in a given value.
     * If the <code>propertyString</code> is ${ENV_VAR_NAME}, then an ENV_VAR_NAME environment variable is resolved.
     * Other values supported could be just spring configuration properties.
     * @param propertyString Property with ${name} tags to resolve.
     * @return Resolved (expanded) property string.
     */
    public static String resolveProperty(String propertyString) {
        SecretValueProvider instance = getInstance();
        return instance.expandValue(propertyString, new SecretValueLookupContext(null));
    }
}
