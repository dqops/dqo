/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.snowflake.connectors;

import com.dqops.connectors.*;
import com.dqops.connectors.snowflake.SnowflakeConnectionSpecObjectMother;
import com.dqops.connectors.snowflake.SnowflakeSourceConnection;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.snowflake.BaseSnowflakeIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
public class SnowflakeSourceConnectionIntegrationTests extends BaseSnowflakeIntegrationTest {
    private SnowflakeSourceConnection sut;
    private ConnectionSpec connectionSpec;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(ProviderType.snowflake);
        this.secretValueLookupContext = new SecretValueLookupContext(null);
        connectionSpec = SnowflakeConnectionSpecObjectMother.create()
                .expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
		this.sut = (SnowflakeSourceConnection)connectionProvider.createConnection(connectionSpec, false, secretValueLookupContext);
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
    void listSchemas_whenSchemasPresent_thenReturnsKnownSchemas() {
		this.sut.open(this.secretValueLookupContext);
        List<SourceSchemaModel> schemas = this.sut.listSchemas();

        Assertions.assertEquals(1, schemas.size());
        Assertions.assertTrue(schemas.stream().anyMatch(m -> Objects.equals(m.getSchemaName(), "PUBLIC")));
    }

    @Test
    void listTables_whenPUBLICSchemaListed_thenReturnsTables() {
		this.sut.open(this.secretValueLookupContext);
        List<SourceTableModel> tables = this.sut.listTables("PUBLIC", null, 300, secretValueLookupContext);

        Assertions.assertTrue(tables.size() > 0);
    }

    @Test
    void listTables_whenUsedTableNameContainsFilter_thenReturnTableThatMatchFilter() {
        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.nulls_and_uniqueness, ProviderType.snowflake);
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
        List<SourceTableModel> tables = this.sut.listTables("PUBLIC", null, 300, secretValueLookupContext);
        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add(tables.get(0).getTableName().getTableName());

        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata("PUBLIC", null, 300, tableNames, null, null);

        Assertions.assertEquals(1, tableSpecs.size());
        TableSpec tableSpec = tableSpecs.get(0);
        Assertions.assertTrue(tableSpec.getColumns().size() > 0);
    }

    @Test
    void retrieveTableMetadata_whenRetrievingMetadataOfAllTablesInPUBLICSchema_thenReturnsTables() {
		this.sut.open(this.secretValueLookupContext);
        List<SourceTableModel> tables = this.sut.listTables("PUBLIC", null, 300, secretValueLookupContext);
        List<String> tableNames = tables.stream()
                .map(m -> m.getTableName().getTableName())
                .collect(Collectors.toList());
                List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata("PUBLIC", null, 300, tableNames, null, null);

        Assertions.assertTrue(tableSpecs.size() > 0);
    }
}
