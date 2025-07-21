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

import com.dqops.BaseTest;
import com.dqops.connectors.bigquery.BigQueryConnectionProvider;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConnectionProviderRegistryImplTests extends BaseTest {
    private ConnectionProviderRegistryImpl sut;
    private BeanFactory beanFactory;

    @BeforeEach
    void setUp() {
		beanFactory = BeanFactoryObjectMother.getBeanFactory();
		this.sut = new ConnectionProviderRegistryImpl(beanFactory);
    }

    @Test
    void connectorRegistry_whenRetrievedFromBeanFactory_thenIsSingleton() {
        ConnectionProviderRegistry registry = this.beanFactory.getBean(ConnectionProviderRegistry.class);
        ConnectionProviderRegistry registry2 = this.beanFactory.getBean(ConnectionProviderRegistry.class);
        Assertions.assertNotNull(registry);
        Assertions.assertSame(registry, registry2);
    }

    @Test
    void getConnectionProvider_whenBigQueryString_thenReturnsBigQueryInstance() {
        final String dialect = "bigquery";
        ConnectionProvider provider = this.sut.getConnectionProvider(dialect);
        Assertions.assertNotNull(provider);
        Assertions.assertSame(provider, this.sut.getConnectionProvider(dialect));
        Assertions.assertInstanceOf(BigQueryConnectionProvider.class, provider);
    }

    @Test
    void getConnectionProvider_whenBigQueryEnumGiven_thenReturnsBigQueryInstance() {
        final ProviderType dialect = ProviderType.bigquery;
        ConnectionProvider provider = this.sut.getConnectionProvider(dialect);
        Assertions.assertNotNull(provider);
        Assertions.assertSame(provider, this.sut.getConnectionProvider(dialect));
        Assertions.assertInstanceOf(BigQueryConnectionProvider.class, provider);
    }
}
