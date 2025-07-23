/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.oracle.connectors;

import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistryObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.oracle.OracleConnectionSpecObjectMother;
import com.dqops.connectors.oracle.OracleSourceConnection;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.oracle.BaseOracleIntegrationTest;
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

@SpringBootTest
public class OracleSourceConnectionIntegrationTests extends BaseOracleIntegrationTest {
    private OracleSourceConnection sut;
    private ConnectionSpec connectionSpec;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(ProviderType.oracle);
        this.secretValueLookupContext = new SecretValueLookupContext(null);
        connectionSpec = OracleConnectionSpecObjectMother.create()
                .expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
		this.sut = (OracleSourceConnection)connectionProvider.createConnection(connectionSpec, false, secretValueLookupContext);
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
                SampleCsvFileNames.nulls_and_uniqueness, ProviderType.oracle);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.sut.open(this.secretValueLookupContext);
        String expectedSchema = SampleTableMetadataObjectMother.getSchemaForProvider(connectionSpec);

        String hashedTableName = sampleTableMetadata.getTableData().getHashedTableName();
        String tableFilter = hashedTableName.substring(2, hashedTableName.length() - 3);

        List<SourceTableModel> tables = this.sut.listTables(expectedSchema, tableFilter, 300, secretValueLookupContext);

        Assertions.assertEquals(1, tables.size());
    }

}
