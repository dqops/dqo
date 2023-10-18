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
