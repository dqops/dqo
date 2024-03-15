package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.SourceConnection;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbStorageType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DuckdbTestConnection {

    public void testConnection(SourceConnection sourceConnection){

        DuckdbParametersSpec duckdbParametersSpec = sourceConnection.getConnectionSpec().getDuckdb();

        Map<String, String> directories = sourceConnection.getConnectionSpec().getDuckdb().getDirectories();

        if(directories == null || directories.size() == 0){
            throw new RuntimeException("Virtual schema name is not configured in the Import configuration.");
        }

        Optional<Map.Entry<String, String>> schemaToEmptyPath = directories.entrySet().stream()
                .filter(x -> x.getValue() == null || x.getValue().isEmpty())
                .findAny();

        if(schemaToEmptyPath.isPresent()){
            throw new RuntimeException("A path is not filled in the schema " + schemaToEmptyPath.get().getKey() + ".");
        }

        directories.keySet().forEach(schema -> {
            List<SourceTableModel> tables;

            if(duckdbParametersSpec.getStorageType().equals(DuckdbStorageType.s3)) {
                tables = AwsTablesLister.listTables(duckdbParametersSpec, schema);
            }
            else {
                tables = LocalSystemTablesLister.listTables(duckdbParametersSpec, schema);
            }

            if(tables.isEmpty()){
                throw new RuntimeException("No files found in the path " + directories.get(schema) + ".");
            }

        });

    }

}
