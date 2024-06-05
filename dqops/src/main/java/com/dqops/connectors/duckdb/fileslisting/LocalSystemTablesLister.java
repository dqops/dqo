package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.parquet.Strings;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Used to retrieve the list of files from AWS s3 that are used as tables by duckdb's listTables.
 */
@Slf4j
@Component
public class LocalSystemTablesLister implements TablesLister {

    /**
     * Returns list of SourceTableModel from local system.
     * @param duckdb DuckdbParametersSpec
     * @param schemaName The name of a virtual schema.
     * @param tableNameContains Optional filter for table names that must contain this name.
     * @param limit The maximum number of tables to return.
     * @return The list of SourceTableModel.
     */
    public List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName, String tableNameContains, int limit){
        String pathPrefixString = duckdb.getDirectories().get(schemaName);
        File[] files = Path.of(pathPrefixString).toFile().listFiles();
        if(files == null){
            return null;
        }
        String folderPrefix = StringUtils.removeEnd(StringUtils.removeEnd(pathPrefixString, "/"), "\\");

        List<SourceTableModel> sourceTableModels = Arrays.stream(files).map(file -> {
            String fileName = file.toString().substring(folderPrefix.length() + 1);
            if (!Strings.isNullOrEmpty(tableNameContains)) {
                if (!fileName.contains(tableNameContains)) {
                    return null;
                }
            }
            String extension = duckdb.getFullExtension();
            if(!isFolderOrFileOfValidExtension(file, pathPrefixString, extension)) {
                return null;
            }
            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, fileName);
            SourceTableModel sourceTableModel = new SourceTableModel(schemaName, physicalTableName);
            return sourceTableModel;
        }).filter(Objects::nonNull)
                .limit(limit)
                .collect(Collectors.toList());

        return sourceTableModels;
    }

    /**
     * 
     * @param file Local system file.
     * @param absolutePathPrefix Prefix for the file.
     * @param extension File extension.
     * @return Whether the file is valid
     */
    private boolean isFolderOrFileOfValidExtension(File file,
                                                   String absolutePathPrefix,
                                                   String extension){
        String folderPrefix = StringUtils.removeEnd(StringUtils.removeEnd(absolutePathPrefix, "/"), "\\");
        String fileName = file.toString().substring(folderPrefix.length() + 1);
        return fileName.toLowerCase().endsWith(extension) || file.isDirectory();
    }

}
