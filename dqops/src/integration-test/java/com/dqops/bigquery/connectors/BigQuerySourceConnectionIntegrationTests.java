/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
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
        List<SourceTableModel> tables = this.sut.listTables(expectedSchema, null, 300, secretValueLookupContext);

        Assertions.assertTrue(tables.size() > 0);
    }

    @Test
    void listTables_whenUsedTableNameContainsFilter_thenReturnTableThatMatchFilter() {
        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.nulls_and_uniqueness, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.sut.open(this.secretValueLookupContext);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);

        String hashedTableName = sampleTableMetadata.getTableData().getHashedTableName();
        String tableFilter = hashedTableName.substring(2, hashedTableName.length() - 3);

        List<SourceTableModel> tables = this.sut.listTables(expectedSchema, tableFilter, 300, secretValueLookupContext);

        Assertions.assertEquals(1, tables.size());
    }

    @Test
    void retrieveTableMetadata_whenFirstTableInSchemaIntrospected_thenReturnsTable() {
		this.sut.open(this.secretValueLookupContext);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);
        List<SourceTableModel> tables = this.sut.listTables(expectedSchema, null, 300, secretValueLookupContext);
        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add(tables.get(0).getTableName().getTableName());

        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata(expectedSchema, null, 300, tableNames, null, null);

        Assertions.assertEquals(1, tableSpecs.size());
        TableSpec tableSpec = tableSpecs.get(0);
        Assertions.assertTrue(tableSpec.getColumns().size() > 0);
    }

    @Test
    void retrieveTableMetadata_whenRetrievingMetadataOfAllTablesInPUBLICSchema_thenReturnsTables() {
		this.sut.open(this.secretValueLookupContext);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);
        List<SourceTableModel> tables = this.sut.listTables(expectedSchema, null, 300, secretValueLookupContext);
        List<String> tableNames = tables.stream()
                .map(m -> m.getTableName().getTableName())
                .collect(Collectors.toList());
        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata(expectedSchema, null, 300, tableNames, null, null);

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
