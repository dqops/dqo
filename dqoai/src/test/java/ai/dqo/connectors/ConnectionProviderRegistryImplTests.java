/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.connectors;

import ai.dqo.BaseTest;
import ai.dqo.connectors.bigquery.BigQueryConnectionProvider;
import ai.dqo.utils.BeanFactoryObjectMother;
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
