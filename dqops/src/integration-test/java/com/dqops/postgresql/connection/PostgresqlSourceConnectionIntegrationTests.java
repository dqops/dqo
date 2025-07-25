/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.postgresql.connection;

import com.dqops.connectors.*;
import com.dqops.connectors.postgresql.PostgresqlConnectionSpecObjectMother;
import com.dqops.connectors.postgresql.PostgresqlEngineType;
import com.dqops.connectors.postgresql.PostgresqlSourceConnection;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.postgresql.BasePostgresqlIntegrationTest;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PostgresqlSourceConnectionIntegrationTests extends BasePostgresqlIntegrationTest {
    private PostgresqlSourceConnection sut;
    private ConnectionSpec connectionSpec;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(ProviderType.postgresql);
        secretValueLookupContext = new SecretValueLookupContext(null);
        connectionSpec = PostgresqlConnectionSpecObjectMother.create().expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        this.sut = (PostgresqlSourceConnection)connectionProvider.createConnection(connectionSpec, false, this.secretValueLookupContext);
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
    void listTables_whenUsedTableNameContainsFilter_thenReturnTableThatMatchFilter() {
        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.nulls_and_uniqueness, ProviderType.postgresql);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.sut.open(this.secretValueLookupContext);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);

        String hashedTableName = sampleTableMetadata.getTableData().getHashedTableName();
        String tableFilter = hashedTableName.substring(2, hashedTableName.length() - 3);

        List<SourceTableModel> tables = this.sut.listTables(expectedSchema, tableFilter, 300, secretValueLookupContext);

        Assertions.assertEquals(1, tables.size());
    }

    @Test
    void retrieveTableMetadata_tableHasUniqueColumn_columnHasSetIsIdFieldToTrue() {
        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.postgresql);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);

        this.sut.open(this.secretValueLookupContext);

        String tableName = sampleTableMetadata.getTableSpec().getPhysicalTableName().getTableName();

        String alterTableQuery = String.format("ALTER TABLE %s ADD UNIQUE (id)", tableName);
        this.sut.executeCommand(alterTableQuery, JobCancellationToken.createDummyJobCancellationToken());

        List<SourceTableModel> tables = this.sut.listTables("public", tableName, 300, secretValueLookupContext);
        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add(tables.get(0).getTableName().getTableName());

        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata("public", tableName, 300, tableNames, null, null);

        TableSpec tableSpec = tableSpecs.get(0);
        Assertions.assertTrue(tableSpec.getColumns().get("id").isId());
        Assertions.assertFalse(tableSpec.getColumns().get("date").isId());
        Assertions.assertFalse(tableSpec.getColumns().get("value").isId());
    }

    @Test
    void listSchemas_whenEngineTypeIsTimescale_returnsNotSystemSchemas() {
        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.postgresql);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);

        this.sut.open(this.secretValueLookupContext);

        this.connectionSpec.getPostgresql().setPostgresqlEngineType(PostgresqlEngineType.timescale);
        List<SourceSchemaModel> schemas = this.sut.listSchemas();

        Assertions.assertEquals(1, schemas.size());
        Assertions.assertEquals("public", schemas.get(0).getSchemaName());
    }

}
