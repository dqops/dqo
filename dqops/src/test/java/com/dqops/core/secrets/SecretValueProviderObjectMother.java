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
