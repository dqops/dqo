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
package com.dqops.sqlserver.connection;

import com.dqops.connectors.*;
import com.dqops.connectors.sqlserver.SqlServerConnectionSpecObjectMother;
import com.dqops.connectors.sqlserver.SqlServerSourceConnection;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProviderObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.sqlserver.BaseSqlServerIntegrationTest;
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
public class SqlServerSourceConnectionIntegrationTests extends BaseSqlServerIntegrationTest {
    private SqlServerSourceConnection sut;
    private ConnectionSpec connectionSpec;
    private SecretValueLookupContext secretValueLookupContext;

    @BeforeEach
    void setUp() {
        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(ProviderType.sqlserver);
        secretValueLookupContext = new SecretValueLookupContext(null);
        connectionSpec = SqlServerConnectionSpecObjectMother.create().expandAndTrim(SecretValueProviderObjectMother.getInstance(), secretValueLookupContext);
        this.sut = (SqlServerSourceConnection)connectionProvider.createConnection(connectionSpec, false, this.secretValueLookupContext);
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

        Assertions.assertEquals(12, schemas.size());
        Assertions.assertTrue(schemas.stream().anyMatch(m -> Objects.equals(m.getSchemaName(), "dbo")));
    }

    @Test
    void listTables_whenPUBLICSchemaListed_thenReturnsTables() {
        this.sut.open(this.secretValueLookupContext);
        List<SourceTableModel> tables = this.sut.listTables("dbo", null);

        Assertions.assertTrue(tables.size() > 0);
    }

    @Test
    void retrieveTableMetadata_whenFirstTableInSchemaIntrospected_thenReturnsTable() {
        this.sut.open(this.secretValueLookupContext);
        List<SourceTableModel> tables = this.sut.listTables("dbo", null);
        ArrayList<String> tableNames = new ArrayList<>();
        tableNames.add(tables.get(0).getTableName().getTableName());

        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata("dbo", tableNames, null);

        Assertions.assertEquals(1, tableSpecs.size());
        TableSpec tableSpec = tableSpecs.get(0);
        Assertions.assertTrue(tableSpec.getColumns().size() > 0);
    }

    @Test
    void retrieveTableMetadata_whenRetrievingMetadataOfAllTablesInPUBLICSchema_thenReturnsTables() {
        this.sut.open(this.secretValueLookupContext);
        List<SourceTableModel> tables = this.sut.listTables("dbo", null);
        List<String> tableNames = tables.stream()
                .map(m -> m.getTableName().getTableName())
                .collect(Collectors.toList());
        List<TableSpec> tableSpecs = this.sut.retrieveTableMetadata("dbo", tableNames, null);

        Assertions.assertTrue(tableSpecs.size() > 0);
    }
}
