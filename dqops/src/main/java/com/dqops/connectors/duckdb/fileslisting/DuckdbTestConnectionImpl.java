package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbStorageType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Used for testing a connection for DuckDB.
 */
@Component
public class DuckdbTestConnectionImpl implements DuckdbTestConnection {

    /**
     * Tests the connection by listing files. Throws exception on misconfiguration such as no schema name, lack or invalid paths, no files in a path, etc.
     * @param duckdbParametersSpec DuckdbParametersSpec that allow to list files.
     */
    public void testConnection(DuckdbParametersSpec duckdbParametersSpec){

        Map<String, String> directories = duckdbParametersSpec.getDirectories();

        if(directories == null || directories.isEmpty()){
            throw new RuntimeException("Virtual schema name is not configured in the Import configuration.");
        }

        Optional<Map.Entry<String, String>> schemaToEmptyPath = directories.entrySet().stream()
                .filter(x -> x.getValue() == null || x.getValue().isEmpty())
                .findAny();

        if(schemaToEmptyPath.isPresent()){
            throw new RuntimeException("A path is not filled in the schema: " + schemaToEmptyPath.get().getKey());
        }

        directories.keySet().forEach(schema -> {
            List<SourceTableModel> tables;

            DuckdbStorageType storageType = duckdbParametersSpec.getStorageType();
            if(storageType != null && storageType.equals(DuckdbStorageType.s3)) {

                Optional<Map.Entry<String, String>> pathWithInvalidPrefix = directories.entrySet().stream()
                        .filter(x -> !x.getValue().toLowerCase().startsWith(AwsConstants.S3_URI_PREFIX))
                        .findAny();

                if(pathWithInvalidPrefix.isPresent()){
                    throw new RuntimeException("S3 path must start with " + AwsConstants.S3_URI_PREFIX + " " + pathWithInvalidPrefix.get().getKey());
                }

                tables = AwsTablesLister.listTables(duckdbParametersSpec, schema);
            }
            else {
                tables = LocalSystemTablesLister.listTables(duckdbParametersSpec, schema);
            }

            if(tables == null || tables.isEmpty()){
                throw new RuntimeException("No files found in the path " + directories.get(schema));
            }
        });

    }

}
