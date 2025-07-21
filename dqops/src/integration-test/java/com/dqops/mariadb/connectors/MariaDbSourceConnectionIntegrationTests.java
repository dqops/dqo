/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.mariadb.connectors;

import com.dqops.connectors.*;
import com.dqops.connectors.mariadb.MariaDbConnectionSpecObjectMother;
import com.dqops.connectors.mariadb.MariaDbSourceConnection;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.mariadb.BaseMariaDbIntegrationTest;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

@SpringBootTest
public class MariaDbSourceConnectionIntegrationTests extends BaseMariaDbIntegrationTest {
    private MariaDbSourceConnection sut;
    private ConnectionSpec connectionSpec;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(ProviderType.mariadb);
        this.secretValueLookupContext = new SecretValueLookupContext(null);
        connectionSpec = MariaDbConnectionSpecObjectMother.create()
                .expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
		this.sut = (MariaDbSourceConnection)connectionProvider.createConnection(connectionSpec, false, secretValueLookupContext);
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
        Assertions.assertTrue(schemas.stream().anyMatch(m -> Objects.equals(m.getSchemaName(), MariaDbConnectionSpecObjectMother.getSchemaName())));
    }

    @Test
    void listTables_whenUsedTableNameContainsFilter_thenReturnTableThatMatchFilter() {
        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                SampleCsvFileNames.nulls_and_uniqueness, ProviderType.mariadb);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.sut.open(this.secretValueLookupContext);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);

        String hashedTableName = sampleTableMetadata.getTableData().getHashedTableName();
        String tableFilter = hashedTableName.substring(2, hashedTableName.length() - 3);

        List<SourceTableModel> tables = this.sut.listTables(expectedSchema, tableFilter, 300, secretValueLookupContext);

        Assertions.assertEquals(1, tables.size());
    }

}
