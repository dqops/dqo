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
import com.dqops.connectors.ConnectionProviderRegistryObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.bigquery.*;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
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
    private BigQuerySqlRunnerImpl bigQuerySqlRunner;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        BigQueryConnectionProvider connectionProvider = (BigQueryConnectionProvider) ConnectionProviderRegistryObjectMother.getConnectionProvider(ProviderType.bigquery);
        BigQueryConnectionPoolImpl bigQueryConnectionPool = new BigQueryConnectionPoolImpl();
        SecretValueProvider secretValueProvider = SecretValueProviderObjectMother.getInstance();
		bigQuerySqlRunner = new BigQuerySqlRunnerImpl();
		this.sut = new BigQuerySourceConnection(bigQuerySqlRunner, secretValueProvider, connectionProvider, bigQueryConnectionPool);
		connectionSpec = BigQueryConnectionSpecObjectMother.create();
		this.sut.setConnectionSpec(connectionSpec);
        secretValueLookupContext = new SecretValueLookupContext(null);
    }

    @AfterEach
    void tearDown() {
		this.sut.close(); // maybe it does nothing, but it should be called anyway as an example
    }

    @Test
    void open_whenCalled_thenJustReturns() {
		this.sut.open(this.secretValueLookupContext);
    }

    @Test
    void getBigQueryService_whenOpenWasCalledBefore_thenReturnsBigQueryConnectionObject() {
		this.sut.open(this.secretValueLookupContext);
        Assertions.assertNotNull(this.sut.getBigQueryInternalConnection());
    }

    @Test
    void listSchemas_whenSchemasPresent_thenReturnsKnownSchemas() {
		this.sut.open(this.secretValueLookupContext);
        List<SourceSchemaModel> schemas = this.sut.listSchemas();

        Assertions.assertTrue(schemas.size() >= 1);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);
        Assertions.assertTrue(schemas.stream().anyMatch(m -> Objects.equals(m.getSchemaName(), expectedSchema)));
    }

    @Test
    void listSchemas_whenBillingProjectIdNotPresentAndListeningPublicGoogleProjects_thenUsesBillingProjectIdFromCredentials() {
        this.connectionSpec.getBigquery().setBillingProjectId(null);
        this.connectionSpec.getBigquery().setQuotaProjectId(null);
        this.connectionSpec.getBigquery().setSourceProjectId("bigquery-public-data");
        this.connectionSpec.getBigquery().setJobsCreateProject(BigQueryJobsCreateProject.create_jobs_in_default_project_from_credentials);

        this.sut.open(this.secretValueLookupContext);
        List<SourceSchemaModel> schemas = this.sut.listSchemas();

        Assertions.assertTrue(schemas.size() >= 100);
        Assertions.assertTrue(schemas.stream().anyMatch(m -> Objects.equals(m.getSchemaName(), "austin_crime")));
    }

    @Test
    void listTables_whenSchemaListed_thenReturnsTables() {
		this.sut.open(this.secretValueLookupContext);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);
        List<SourceTableModel> tables = this.sut.listTables(expectedSchema);

        Assertions.assertTrue(tables.size() > 0);
    }

    @Test
    void retrieveTableMetadata_whenFirstTableInSchemaIntrospected_thenReturnsTable() {
		this.sut.open(this.secretValueLookupContext);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);
        List<SourceTableModel> tables = this.sut.listTables(expectedSchema);
        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add(tables.get(0).getTableName().getTableName());

        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata(expectedSchema, tableNames, null, null);

        Assertions.assertEquals(1, tableSpecs.size());
        TableSpec tableSpec = tableSpecs.get(0);
        Assertions.assertTrue(tableSpec.getColumns().size() > 0);
    }

    @Test
    void retrieveTableMetadata_whenRetrievingMetadataOfAllTablesInPUBLICSchema_thenReturnsTables() {
		this.sut.open(this.secretValueLookupContext);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);
        List<SourceTableModel> tables = this.sut.listTables(expectedSchema);
        List<String> tableNames = tables.stream()
                .map(m -> m.getTableName().getTableName())
                .collect(Collectors.toList());
        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata(expectedSchema, tableNames, null, null);

        Assertions.assertTrue(tableSpecs.size() > 0);
    }

    @Test
    void executeQuery_whenBillingProjectIdNotPresentAndQueryingPublicGoogleProjects_thenUsesBillingProjectIdFromDefaultApplicationCredentials() {
        this.connectionSpec.getBigquery().setBillingProjectId(null);
        this.connectionSpec.getBigquery().setQuotaProjectId(null);
        this.connectionSpec.getBigquery().setSourceProjectId("bigquery-public-data");
        this.connectionSpec.getBigquery().setJobsCreateProject(BigQueryJobsCreateProject.create_jobs_in_default_project_from_credentials);
        this.sut.open(this.secretValueLookupContext);
        Table results = this.sut.executeQuery("select count(*) from `bigquery-public-data.austin_crime.crime`",
                JobCancellationToken.createDummyJobCancellationToken(), null, false);

        Assertions.assertNotNull(results);
        Assertions.assertEquals(1, results.rowCount());
    }

    // TODO: add more integration tests to list tables, retrieve the table metadata, etc.
}
