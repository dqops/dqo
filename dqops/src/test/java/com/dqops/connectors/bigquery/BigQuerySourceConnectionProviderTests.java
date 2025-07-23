/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.bigquery;

import com.dqops.BaseTest;
import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BigQuerySourceConnectionProviderTests extends BaseTest {
    private BigQueryConnectionProvider sut;

    @BeforeEach
    void setUp() {
		this.sut = new BigQueryConnectionProvider(BeanFactoryObjectMother.getBeanFactory(), new BigQueryProviderDialectSettings());
    }

    @Test
    void getBean_whenInstanceRetrievedFromIoC_thenReturnsCorrectInstanceAsSingleton() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
        ConnectionProvider connectionProvider = beanFactory.getBean("bigquery-provider", ConnectionProvider.class);
        Assertions.assertNotNull(connectionProvider);
        Assertions.assertInstanceOf(BigQueryConnectionProvider.class, connectionProvider);
        Assertions.assertSame(connectionProvider, beanFactory.getBean("bigquery-provider", ConnectionProvider.class));
    }

    @Test
    void createConnection_whenCalledWithBigQueryConnectionClosed_thenCreatesConnection() {
        ConnectionSpec connectionSpec = BigQueryConnectionSpecObjectMother.createLegacy();
        BigQuerySourceConnection connection = this.sut.createConnection(connectionSpec, false, new SecretValueLookupContext(null));
        Assertions.assertNotNull(connection);
        Assertions.assertSame(connectionSpec, connection.getConnectionSpec());
    }

    @Test
    void getDialectSettings_whenBigQueryDialectSettingRetrieved_thenAreCorrect() {
        ConnectionSpec connectionSpec = BigQueryConnectionSpecObjectMother.createLegacy();
        ProviderDialectSettings dialectSettings = this.sut.getDialectSettings(connectionSpec);
        Assertions.assertNotNull(dialectSettings);
        Assertions.assertEquals("`", dialectSettings.getQuoteBegin());
        Assertions.assertEquals("`", dialectSettings.getQuoteEnd());
        Assertions.assertEquals("``", dialectSettings.getQuoteEscape());
    }
}
