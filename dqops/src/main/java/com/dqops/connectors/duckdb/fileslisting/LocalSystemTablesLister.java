package com.dqops.connectors.duckdb.fileslisting;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbFilesFormatType;
import com.dqops.metadata.sources.PhysicalTableName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

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
public class LocalSystemTablesLister {

    /**
     * Returns list of SourceTableModel from local system.
     * @param duckdb DuckdbParametersSpec
     * @param schemaName The name of a virtual schema.
     * @return The list of SourceTableModel.
     */
    public static List<SourceTableModel> listTables(DuckdbParametersSpec duckdb, String schemaName){
        String pathPrefixString = duckdb.getDirectories().get(schemaName);
        File[] files = Path.of(pathPrefixString).toFile().listFiles();
        if(files == null){
            return null;
        }
        String folderPrefix = StringUtils.removeEnd(StringUtils.removeEnd(pathPrefixString, "/"), "\\");

        DuckdbFilesFormatType sourceFilesTypeString = duckdb.getFilesFormatType();

        List<SourceTableModel> sourceTableModels = Arrays.stream(files).map(file -> {
            String fileName = file.toString().substring(folderPrefix.length() + 1);
            if(!isFolderOrFileOfValidExtension(file, pathPrefixString, sourceFilesTypeString)) {
                return null;
            }
            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, fileName);
            SourceTableModel sourceTableModel = new SourceTableModel(schemaName, physicalTableName);
            return sourceTableModel;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        return sourceTableModels;
    }

    /**
     * 
     * @param file Local system file.
     * @param absolutePathPrefix Prefix for the file.
     * @param filesType File type used for extension matching.
     * @return Whether the file is valid
     */
    private static boolean isFolderOrFileOfValidExtension(File file, String absolutePathPrefix, DuckdbFilesFormatType filesType){
        String folderPrefix = StringUtils.removeEnd(StringUtils.removeEnd(absolutePathPrefix, "/"), "\\");
        String fileName = file.toString().substring(folderPrefix.length() + 1);

        String sourceFilesTypeString = filesType.toString();
        return fileName.toLowerCase().endsWith("." + sourceFilesTypeString)
                || fileName.toLowerCase().endsWith(".gz")
                || file.isDirectory();
    }

}
