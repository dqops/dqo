/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
