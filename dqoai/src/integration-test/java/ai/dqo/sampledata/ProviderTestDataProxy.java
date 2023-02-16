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
package ai.dqo.sampledata;

import ai.dqo.connectors.*;
import ai.dqo.metadata.sources.ConnectionSpec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Object that provides access to testable tables that were created from sample tables defined as CSV files.
 * Tables are created on demand (when they are requested for the first time).
 */
public class ProviderTestDataProxy {
    private final ProviderType providerType;
    private final Map<ConnectionTablePair, SampleTableMetadata> existingTables = new HashMap<>();

    private final Map<ConnectionTablePair, SampleTableMetadataForeign> existingForeignTables = new HashMap<>();
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
        String targetTableName = sampleTableMetadata.getTableSpec().getTarget().getTableName();
        ConnectionSpec connectionSpec = sampleTableMetadata.getConnectionSpec();
        ConnectionTablePair connectionTablePair = new ConnectionTablePair(connectionSpec, targetTableName);
        if (existingTables.containsKey(connectionTablePair)) {
            return existingTables.get(connectionTablePair);
        }

        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(this.providerType);
        String schemaName = sampleTableMetadata.getTableSpec().getTarget().getSchemaName();
        ConnectionSchemaPair schemaListKey = new ConnectionSchemaPair(connectionSpec, schemaName);
        List<SourceTableModel> tablesInSchema = tablesInSchemas.get(schemaListKey);
        if (tablesInSchema == null) {
            try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true)) {
                tablesInSchema = sourceConnection.listTables(schemaName);
				tablesInSchemas.put(schemaListKey, tablesInSchema);
            }
        }

        Optional<SourceTableModel> existingSourceTable = tablesInSchema.stream().filter(t -> t.getTableName().getTableName().equals(targetTableName)).findFirst();
        if (existingSourceTable.isPresent()) {
			existingTables.put(connectionTablePair, sampleTableMetadata); // the table was already created
            return sampleTableMetadata;
        }

        // we need to create the target table
        try (SourceConnection connectionForLoad = connectionProvider.createConnection(connectionSpec, true)) {
            connectionForLoad.createTable(sampleTableMetadata.getTableSpec());

            // TODO: we may consider splitting the table into row segments (up to let's say 1000 rows) and insert that much, because some databases don't like long formatted SQLs
            connectionForLoad.loadData(sampleTableMetadata.getTableSpec(), sampleTableMetadata.getTableData().getTable());
        }

		existingTables.put(connectionTablePair, sampleTableMetadata);
        return sampleTableMetadata;
    }

    /**
     * Checks if a table exist on the target connection. Creates and loads the data into the table on the first call.
     * @param sampleTableMetadataForeign Metadata and data of the sample table to make.
     * @return Provided sample table metadata or a cached copy if a table with the same name is already present in the target database.
     */
    public SampleTableMetadataForeign ensureForeignTableExists(SampleTableMetadataForeign sampleTableMetadataForeign) {
        String targetTableName = sampleTableMetadataForeign.getTableSpec().getTarget().getTableName();
        ConnectionSpec connectionSpec = sampleTableMetadataForeign.getConnectionSpec();
        ConnectionTablePair connectionTablePair = new ConnectionTablePair(connectionSpec, targetTableName);
        if (existingTables.containsKey(connectionTablePair)) {
            return existingForeignTables.get(connectionTablePair);
        }

        ConnectionProvider connectionProvider = ConnectionProviderRegistryObjectMother.getConnectionProvider(this.providerType);
        String schemaName = sampleTableMetadataForeign.getTableSpec().getTarget().getSchemaName();
        ConnectionSchemaPair schemaListKey = new ConnectionSchemaPair(connectionSpec, schemaName);
        List<SourceTableModel> tablesInSchema = tablesInSchemas.get(schemaListKey);
        if (tablesInSchema == null) {
            try (SourceConnection sourceConnection = connectionProvider.createConnection(connectionSpec, true)) {
                tablesInSchema = sourceConnection.listTables(schemaName);
                tablesInSchemas.put(schemaListKey, tablesInSchema);
            }
        }

        Optional<SourceTableModel> existingSourceTable = tablesInSchema.stream().filter(t -> t.getTableName().getTableName().equals(targetTableName)).findFirst();
        if (existingSourceTable.isPresent()) {
            existingForeignTables.put(connectionTablePair, sampleTableMetadataForeign); // the table was already created
            return sampleTableMetadataForeign;
        }

        // we need to create the target table
        try (SourceConnection connectionForLoad = connectionProvider.createConnection(connectionSpec, true)) {
            connectionForLoad.createTable(sampleTableMetadataForeign.getTableSpec());

            // TODO: we may consider splitting the table into row segments (up to let's say 1000 rows) and insert that much, because some databases don't like long formatted SQLs
            connectionForLoad.loadData(sampleTableMetadataForeign.getTableSpec(), sampleTableMetadataForeign.getTableData().getTable());
        }

        existingForeignTables.put(connectionTablePair, sampleTableMetadataForeign);
        return sampleTableMetadataForeign;
    }
}
