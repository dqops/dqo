package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.metadata.sources.PhysicalTableName;

import java.util.List;
import java.util.stream.Collectors;

public abstract class RemoteTablesLister implements TablesLister {

    public abstract List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName);

    /**
     * Transforms list of files available at a remote storage to list of SourceTableModel and filters to valid objects that can be directories or files with a specific extension.
     * @param duckdb DuckdbParametersSpec
     * @param files List of files from a remote storage
     * @param schemaName Name of schema
     * @return The list of SourceTableModel
     */
    public List<SourceTableModel> filterAndTransform(DuckdbParametersSpec duckdb, List<String> files, String schemaName){
        List<SourceTableModel> sourceTableModels = files.stream()
                .filter(file -> isFolderOrFileOfValidExtension(file, duckdb.getFilesFormatType()))
                .map(file -> {
                    String cleanedFilePath = file.endsWith("/") ? file.substring(0, file.length() - 1) : file;
                    String fileName = cleanedFilePath.substring(cleanedFilePath.lastIndexOf("/") + 1);
                    PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, fileName);
                    SourceTableModel sourceTableModel = new SourceTableModel(schemaName, physicalTableName);
                    return sourceTableModel;
                }).collect(Collectors.toList());
        return sourceTableModels;
    }

    /**
     * Verifies whether the given file name is a valid file name that DuckDB can work with.
     * @param storageObjectName The file name in storage
     * @param filesType File type used for extension matching.
     * @return Whether the file is valid
     */
    private boolean isFolderOrFileOfValidExtension(String storageObjectName, DuckdbFilesFormatType filesType){
        String sourceFilesTypeString = filesType.toString();
        return storageObjectName.toLowerCase().endsWith("." + sourceFilesTypeString)
                || storageObjectName.toLowerCase().endsWith(".gz")
                || storageObjectName.endsWith("/");
    }

}
