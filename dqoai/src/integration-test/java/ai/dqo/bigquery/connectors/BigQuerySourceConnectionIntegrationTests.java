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
package ai.dqo.bigquery.connectors;

import ai.dqo.bigquery.BaseBigQueryIntegrationTest;
import ai.dqo.connectors.ConnectionProviderRegistryObjectMother;
import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.SourceSchemaModel;
import ai.dqo.connectors.SourceTableModel;
import ai.dqo.connectors.bigquery.*;
import ai.dqo.core.jobqueue.JobCancellationToken;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.core.secrets.SecretValueProviderObjectMother;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
public class BigQuerySourceConnectionIntegrationTests extends BaseBigQueryIntegrationTest {
    private BigQuerySourceConnection sut;
    private ConnectionSpec connectionSpec;
    private BigQuerySqlRunner bigQuerySqlRunner;

    @BeforeEach
    void setUp() {
        BigQueryConnectionProvider connectionProvider = (BigQueryConnectionProvider) ConnectionProviderRegistryObjectMother.getConnectionProvider(ProviderType.bigquery);
        BigQueryConnectionPoolImpl bigQueryConnectionPool = new BigQueryConnectionPoolImpl();
        SecretValueProvider secretValueProvider = SecretValueProviderObjectMother.getInstance();
		bigQuerySqlRunner = new BigQuerySqlRunner();
		this.sut = new BigQuerySourceConnection(bigQuerySqlRunner, secretValueProvider, connectionProvider, bigQueryConnectionPool);
		connectionSpec = BigQueryConnectionSpecObjectMother.create();
		this.sut.setConnectionSpec(connectionSpec);
    }

    @AfterEach
    void tearDown() {
		this.sut.close(); // maybe it does nothing, but it should be called anyway as an example
    }

    @Test
    void open_whenCalled_thenJustReturns() {
		this.sut.open();
    }

    @Test
    void getBigQueryService_whenOpenWasCalledBefore_thenReturnsBigQueryConnectionObject() {
		this.sut.open();
        Assertions.assertNotNull(this.sut.getBigQueryInternalConnection());
    }

    @Test
    void listSchemas_whenSchemasPresent_thenReturnsKnownSchemas() {
		this.sut.open();
        List<SourceSchemaModel> schemas = this.sut.listSchemas();

        Assertions.assertTrue(schemas.size() >= 1);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(ProviderType.bigquery);
        Assertions.assertTrue(schemas.stream().anyMatch(m -> Objects.equals(m.getSchemaName(), expectedSchema)));
    }

    @Test
    void listSchemas_whenBillingProjectIdNotPresentAndListeningPublicGoogleProjects_thenUsesBillingProjectIdFromCredentials() {
        this.connectionSpec.getBigquery().setBillingProjectId(null);
        this.connectionSpec.getBigquery().setQuotaProjectId(null);
        this.connectionSpec.getBigquery().setSourceProjectId("bigquery-public-data");
        this.sut.open();
        List<SourceSchemaModel> schemas = this.sut.listSchemas();

        Assertions.assertTrue(schemas.size() >= 100);
        Assertions.assertTrue(schemas.stream().anyMatch(m -> Objects.equals(m.getSchemaName(), "austin_crime")));
    }

    @Test
    void listTables_whenSchemaListed_thenReturnsTables() {
		this.sut.open();
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(ProviderType.bigquery);
        List<SourceTableModel> tables = this.sut.listTables(expectedSchema);

        Assertions.assertTrue(tables.size() > 0);
    }

    @Test
    void retrieveTableMetadata_whenFirstTableInSchemaIntrospected_thenReturnsTable() {
		this.sut.open();
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(ProviderType.bigquery);
        List<SourceTableModel> tables = this.sut.listTables(expectedSchema);
        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add(tables.get(0).getTableName().getTableName());

        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata(expectedSchema, tableNames);

        Assertions.assertEquals(1, tableSpecs.size());
        TableSpec tableSpec = tableSpecs.get(0);
        Assertions.assertTrue(tableSpec.getColumns().size() > 0);
    }

    @Test
    void retrieveTableMetadata_whenRetrievingMetadataOfAllTablesInPUBLICSchema_thenReturnsTables() {
		this.sut.open();
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(ProviderType.bigquery);
        List<SourceTableModel> tables = this.sut.listTables(expectedSchema);
        List<String> tableNames = tables.stream()
                .map(m -> m.getTableName().getTableName())
                .collect(Collectors.toList());
        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata(expectedSchema, tableNames);

        Assertions.assertTrue(tableSpecs.size() > 0);
    }

    @Test
    void executeQuery_whenBillingProjectIdNotPresentAndQueryingPublicGoogleProjects_thenUsesBillingProjectIdFromDefaultApplicationCredentials() {
        this.connectionSpec.getBigquery().setBillingProjectId(null);
        this.connectionSpec.getBigquery().setQuotaProjectId(null);
        this.connectionSpec.getBigquery().setSourceProjectId("bigquery-public-data");
        this.sut.open();
        Table results = this.sut.executeQuery("select count(*) from `bigquery-public-data.austin_crime.crime`",
                JobCancellationToken.createDummyJobCancellationToken());

        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.rowCount());
    }

    // TODO: add more integration tests to list tables, retrieve the table metadata, etc.
}
