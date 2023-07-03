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
package com.dqops.connectors;

import com.dqops.utils.BeanFactoryObjectMother;
import org.springframework.beans.factory.BeanFactory;

/**
 * Object mother for ConnectionProviderRegistry.
 */
public class ConnectionProviderRegistryObjectMother {
    /**
     * Returns the shared (singleton) instance of the provider registry.
     * @return Provider registry instance.
     */
    public static ConnectionProviderRegistry getInstance() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        return beanFactory.getBean(ConnectionProviderRegistry.class);
    }

    /**
     * Returns a connection provider instance from the shared registry.
     * @param providerType Provider type.
     * @return Connection provider.
     */
    public static ConnectionProvider getConnectionProvider(ProviderType providerType) {
        ConnectionProviderRegistry providerRegistry = getInstance();
        return providerRegistry.getConnectionProvider(providerType);
    }
}
