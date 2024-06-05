package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.SourceTableModel;

import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import org.apache.parquet.Strings;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Used to retrieve the list of files from a remote file storage that are used as tables by duckdb's listTables.
 */
public abstract class RemoteTablesLister implements TablesLister {

    /**
     * Returns list of SourceTableModel from a file storage.
     * @param duckdb DuckdbParametersSpec
     * @param schemaName The name of a virtual schema.
     * @param tableNameContains Optional filter for the file names.
     * @param limit The maximum number of results to return.
     * @return The list of SourceTableModel.
     */
    public abstract List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName, String tableNameContains, int limit);

    /**
     * Transforms list of files available at a remote storage to list of SourceTableModel and filters to valid objects that can be directories or files with a specific extension.
     * @param duckdb DuckdbParametersSpec
     * @param files List of files from a remote storage
     * @param schemaName Name of schema
     * @param tableNameContains Optional filter with a file name component.
     * @param limit The maximum number of results to return.
     * @return The list of SourceTableModel
     */
    public List<SourceTableModel> filterAndTransform(DuckdbParametersSpec duckdb,
                                                     List<String> files,
                                                     String schemaName,
                                                     String tableNameContains,
                                                     int limit) {
        List<SourceTableModel> sourceTableModels = files.stream()
                .filter(file -> isFolderOrFileOfValidExtension(file, duckdb.getFullExtension()))
                .map(file -> {
                    String cleanedFilePath = file.endsWith("/") ? file.substring(0, file.length() - 1) : file;
                    String fileName = cleanedFilePath.substring(cleanedFilePath.lastIndexOf("/") + 1);

                    if (!Strings.isNullOrEmpty(tableNameContains)) {
                        if (!fileName.contains(tableNameContains)) {
                            return null;
                        }
                    }

                    PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, fileName);
                    SourceTableModel sourceTableModel = new SourceTableModel(schemaName, physicalTableName);
                    return sourceTableModel;
                })
                .filter(Objects::nonNull)
                .limit(limit)
                .collect(Collectors.toList());
        return sourceTableModels;
    }

    /**
     * Verifies whether the given file name is a valid file name that DuckDB can work with.
     * @param storageObjectName The file name in storage
     * @param extension File extension.
     * @return Whether the file is valid
     */
    private boolean isFolderOrFileOfValidExtension(String storageObjectName, String extension){
        return storageObjectName.toLowerCase().endsWith(extension) || storageObjectName.endsWith("/");
    }

}
