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
package com.dqops.sampledata;

import com.dqops.connectors.*;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Object that provides access to testable tables that were created from sample tables defined as CSV files.
 * Tables are created on demand (when they are requested for the first time).
 */
@Slf4j
public class ProviderTestDataProxy {
    private final ProviderType providerType;
    private final Map<ConnectionTablePair, SampleTableMetadata> existingTables = new HashMap<>();
    private final Map<ConnectionSchemaPair, List<SourceTableModel>> tablesInSchemas = new HashMap<>();

    /**
     * Creates an object that uses a connection provider.
     * @param providerType Connection provider.
     */
    public ProviderTestDataProxy(ProviderType providerType) {
        this.providerType = providerType;
    }

    /**
     * Checks if a table exist on the target connection. Creates and loads the data into the table on the first call.
     * @param sampleTableMetadata Metadata and data of the sample table to make.
     * @return Provided sample table metadata or a cached copy if a table with the same name is already present in the target database.
     */
    public SampleTableMetadata ensureTableExists(SampleTableMetadata sampleTableMetadata) {
        String targetTableName = sampleTableMetadata.getTableSpec().getPhysicalTableName().getTableName();
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ConnectionTablePair connectionTablePair = new ConnectionTablePair(connectionSpec, targetTableName);
        if (existingTables.containsKey(connectionTablePair)) {
            return existingTables.get(connectionTablePair);
        }

        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(this.providerType);

        List<SourceTableModel> tablesInSchema = prepareTablesInSchema(sampleTableMetadata, connectionProvider);

        Optional<SourceTableModel> existingSourceTable = tablesInSchema.stream()
                .filter(t -> t.getTableName().getTableName().equals(targetTableName)).findFirst();
        if (existingSourceTable.isPresent()) {
			existingTables.put(connectionTablePair, sampleTableMetadata); // the table was already created
            return sampleTableMetadata;
        }

        // we need to create the target table
        try (SourceConnection connectionForLoad = connectionProvider.createConnection(connectionSpec, true, new SecretValueLookupContext(null))) {
            connectionForLoad.createTable(sampleTableMetadata.getTableSpec());

            try {
                // TODO: we may consider splitting the table into row segments (up to let's say 1000 rows) and insert that much, because some databases don't like long formatted SQLs
                connectionForLoad.loadData(sampleTableMetadata.getTableSpec(), sampleTableMetadata.getTableData().getTable());
            } catch (Exception exception) {
                log.error(String.format("Cannot load data to the prepared table %s due to: %s,",
                                sampleTableMetadata.getTableSpec().getPhysicalTableName(),
                                exception.getMessage()),
                        exception);

                connectionForLoad.dropTable(sampleTableMetadata.getTableSpec());
            }
        }

		existingTables.put(connectionTablePair, sampleTableMetadata);
        return sampleTableMetadata;
    }

    private List<SourceTableModel> prepareTablesInSchema(SampleTableMetadata sampleTableMetadata,
                                                         ConnectionProvider connectionProvider){
        TableSpec tableSpec = sampleTableMetadata.getTableSpec();
        PhysicalTableName physicalTableName = tableSpec.getPhysicalTableName();
        String schemaName = physicalTableName.getSchemaName();
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ConnectionSchemaPair schemaListKey
                = new ConnectionSchemaPair(connectionSpec, schemaName);
        List<SourceTableModel> tablesInSchema = tablesInSchemas.get(schemaListKey);
        // the connectionWrapper with a tableSpec made that duckdb tries to use without creating it in the memory
        // the table name appears in the "existingSourceTable", avoiding creation the table in the memory which result tests throw table missing error messages
//        ConnectionWrapperImpl connectionWrapper = new ConnectionWrapperImpl();
//        connectionWrapper.setHierarchyId(new HierarchyId("connections", "test"));
//        connectionWrapper.setSpec(connectionSpec);
//        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(physicalTableName);
//        tableWrapper.setSpec(tableSpec);

        if (tablesInSchema == null) {
            SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(null);
            try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true, secretValueLookupContext)) {
                tablesInSchema = sourceConnection.listTables(schemaName, null);
                tablesInSchemas.put(schemaListKey, tablesInSchema);
            }
        }
        return tablesInSchema;
    }

}
