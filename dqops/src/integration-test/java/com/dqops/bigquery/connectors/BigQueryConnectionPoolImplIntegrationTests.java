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
package com.dqops.bigquery.connectors;

import com.dqops.bigquery.BaseBigQueryIntegrationTest;
import com.dqops.connectors.bigquery.BigQueryConnectionPool;
import com.dqops.connectors.bigquery.BigQueryConnectionPoolImpl;
import com.dqops.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import com.dqops.connectors.bigquery.BigQueryInternalConnection;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.BeanFactoryObjectMother;
import com.google.cloud.bigquery.BigQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BigQueryConnectionPoolImplIntegrationTests extends BaseBigQueryIntegrationTest {
    private BigQueryConnectionPoolImpl sut;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        BeanFactory beanFactory = BeanFactoryObjectMother.getBeanFactory();
		this.sut = (BigQueryConnectionPoolImpl) beanFactory.getBean(BigQueryConnectionPool.class);
        this.secretValueLookupContext = new SecretValueLookupContext(null);
    }

    @Test
    void getBigQueryService_whenCalledOnce_thenReturnsConnection() {
        ConnectionSpec connectionSpec = BigQueryConnectionSpecObjectMother.create();
        BigQueryInternalConnection bigQueryInternalConnection = this.sut.getBigQueryService(connectionSpec, this.secretValueLookupContext);
        BigQuery bigQueryService = bigQueryInternalConnection.getBigQueryClient();
        Assertions.assertNotNull(bigQueryService);
    }

    @Test
    void getBigQueryService_whenCalledAgain_thenReturnsTheSameInstance() {
        ConnectionSpec connectionSpec = BigQueryConnectionSpecObjectMother.create();
        BigQueryInternalConnection bigQueryInternalConnection = this.sut.getBigQueryService(connectionSpec, this.secretValueLookupContext);
        BigQuery bigQueryService = bigQueryInternalConnection.getBigQueryClient();
        Assertions.assertNotNull(bigQueryService);
        Assertions.assertSame(bigQueryService, this.sut.getBigQueryService(connectionSpec, this.secretValueLookupContext).getBigQueryClient());
    }

    @Test
    void getBigQueryService_whenCalledWithQuotaProject_thenReturnsConnection() {
        ConnectionSpec connectionSpec = BigQueryConnectionSpecObjectMother.create();
        connectionSpec.getBigquery().setQuotaProjectId(connectionSpec.getBigquery().getSourceProjectId());
        BigQueryInternalConnection bigQueryInternalConnection = this.sut.getBigQueryService(connectionSpec, this.secretValueLookupContext);
        BigQuery bigQueryService = bigQueryInternalConnection.getBigQueryClient();
//        Assertions.assertNull(connectionSpec.getBigquery().getSourceProjectId(), connectionSpec.getBigquery().getSourceProjectId());
        Assertions.assertNotNull(bigQueryService);
    }

    @Test
    void getBigQueryService_whenCalledWithBillingProject_thenReturnsConnection() {
        ConnectionSpec connectionSpec = BigQueryConnectionSpecObjectMother.create();
        connectionSpec.getBigquery().setBillingProjectId(connectionSpec.getBigquery().getSourceProjectId());
        BigQueryInternalConnection bigQueryInternalConnection = this.sut.getBigQueryService(connectionSpec, this.secretValueLookupContext);
        BigQuery bigQueryService = bigQueryInternalConnection.getBigQueryClient();
//        Assertions.assertNull(connectionSpec.getBigquery().getSourceProjectId(), connectionSpec.getBigquery().getSourceProjectId());
        Assertions.assertNotNull(bigQueryService);
    }
}
