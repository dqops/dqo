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
package ai.dqo.redshift.connectors;

import ai.dqo.bigquery.BaseBigQueryIntegrationTest;
import ai.dqo.connectors.*;
import ai.dqo.connectors.redshift.RedshiftConnectionSpecObjectMother;
import ai.dqo.connectors.redshift.RedshiftSourceConnection;
import ai.dqo.core.secrets.SecretValueProviderObjectMother;
import ai.dqo.metadata.sources.ConnectionSpec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RedshiftSourceConnectionIntegrationTests extends BaseBigQueryIntegrationTest {
    private RedshiftSourceConnection sut;
    private ConnectionSpec connectionSpec;

    @BeforeEach
    void setUp() {
        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(ProviderType.redshift);
        connectionSpec = RedshiftConnectionSpecObjectMother.create().expandAndTrim(SecretValueProviderObjectMother.getInstance());
        this.sut = (RedshiftSourceConnection)connectionProvider.createConnection(connectionSpec, false);
    }

    @AfterEach
    void tearDown() {
        this.sut.close(); // maybe it does nothing, but it should be called anyway as an example
    }

    @Test
    void open_whenCalled_thenJustReturns() {
        this.sut.open();
    }

}
