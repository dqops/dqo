package com.dqops.metadata.sources.fileformat;

import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.config.DuckdbStorageType;
import com.dqops.connectors.duckdb.fileslisting.aws.AwsConstants;
import com.dqops.connectors.duckdb.fileslisting.azure.AzureConstants;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.fileformat.csv.CsvFileFormatSpec;
import com.dqops.metadata.sources.fileformat.iceberg.IcebergFileFormatSpec;
import com.dqops.metadata.sources.fileformat.json.JsonFileFormatSpec;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

/**
 * Provider for the File Format.
 */
public class FileFormatSpecProvider {

    /**
     * Resolves the file format based on table spec and parameters spec.
     * At first, tries to return file format from the table spec. If it is not available then tries to use a file format from parameters spec.
     * Also, always tries to use file paths from the table spec when set.
     *
     * @param duckdbParametersSpec duckdbParametersSpec
     * @param tableSpec tableSpec
     * @return FileFormatSpec
     */
    public static FileFormatSpec resolveFileFormat(DuckdbParametersSpec duckdbParametersSpec, TableSpec tableSpec) {
        DuckdbFilesFormatType filesType = duckdbParametersSpec.getFilesFormatType();
        if (filesType == null) {
            throw new RuntimeException("The files format is unknown. Please set files format on the connection.");
        }

        FileFormatSpec fileFormat = tableSpec.getFileFormat() == null ? new FileFormatSpec() : tableSpec.getFileFormat().deepClone();
        if(fileFormat.getFilePaths().isEmpty()){
            FilePathListSpec filePathListSpec = guessFilePaths(duckdbParametersSpec, tableSpec);
            fileFormat.setFilePaths(filePathListSpec);
        }

        if (fileFormat.isFormatSetForType(filesType)) {
            return fileFormat;
        }

        FileFormatSpec fileFormatCloned = fileFormat.deepClone();
        if (duckdbParametersSpec.isFormatSetForType()) {
            switch (filesType) {
                case csv: fileFormatCloned.setCsv(duckdbParametersSpec.getCsv().deepClone()); break;
                case json: fileFormatCloned.setJson(duckdbParametersSpec.getJson().deepClone()); break;
                case parquet: fileFormatCloned.setParquet(duckdbParametersSpec.getParquet().deepClone()); break;
                case iceberg: fileFormatCloned.setIceberg(duckdbParametersSpec.getIceberg().deepClone()); break;
            }
            return fileFormatCloned;
        }

        fillDefaultFileFormat(fileFormatCloned, filesType);
        return fileFormatCloned;
    }

    /**
     * Guesses file paths with use of the directories from parametersSpec, table's schema and table name.
     * @param duckdb Duckdb parameters spec
     * @param tableSpec Table spec
     * @return FilePathListSpec with filled a single filepath to the data that can be used by duckdb to make a query.
     */
    public static FilePathListSpec guessFilePaths(DuckdbParametersSpec duckdb, TableSpec tableSpec){
        FilePathListSpec filePathListSpec = new FilePathListSpec();

        Map<String, String> directories = duckdb.getDirectories();
        String tableName = tableSpec.getPhysicalTableName().getTableName();
        String schemaName = tableSpec.getPhysicalTableName().getSchemaName();
        String pathPrefix = directories.get(schemaName);
        if(pathPrefix == null){
            return filePathListSpec;
        }

        DuckdbStorageType storageType = duckdb.getStorageType();

        String filePath = isPathAbsoluteSystemsWide(tableName)
                ? tableName
                : createAbsoluteFilePathFrom(pathPrefix, tableName, storageType);

        DuckdbFilesFormatType filesType = duckdb.getFilesFormatType();

        if(filesType == DuckdbFilesFormatType.iceberg){
            filePathListSpec.add(filePath);
            return filePathListSpec;
        }

        boolean isSetHivePartitioning = duckdb.isSetHivePartitioning()
                || (tableSpec.getFileFormat() != null && tableSpec.getFileFormat().isSetHivePartitioning(filesType));

        // todo: what if the file extension eg. json will not match the source file types e.g. csv on the parameter spec??
        String fileExtension = tableSpec.getFileFormat() != null
                ? tableSpec.getFileFormat().getFullExtension(filesType)
                : duckdb.getFullExtension();
        String separator = (storageType == null || storageType.equals(DuckdbStorageType.local)) ? File.separator : "/";
        if(!filePath.toLowerCase().endsWith(fileExtension)){
            filePath = filePath + separator + (isSetHivePartitioning ? "**" + separator : "") + "*" + fileExtension;
        }
        filePathListSpec.add(filePath);
        return filePathListSpec;
    }

    /**
     * Creates an absolute path to the table name which stands for file name or a folder with files.
     * @param pathPrefix Path prefix, should be absolute.
     * @param tableName Table name
     * @param storageType Duckdb secrets type
     * @return Absolute file path string to data.
     */
    private static String createAbsoluteFilePathFrom(String pathPrefix, String tableName, DuckdbStorageType storageType){
        if(storageType == null || storageType.equals(DuckdbStorageType.local)){
            return Path.of(pathPrefix, tableName).toString();
        } else {
            String pathPrefixWithTrailingSlash = pathPrefix + (pathPrefix.endsWith("/") ? "" : "/");
            return pathPrefixWithTrailingSlash + tableName;
        }
    }


    /**
     * Returns whether the path is an absolute path regardless of the system.
     * @param path The path to the file or a directory.
     * @return State whether the path is an absolute one.
     */
    public static boolean isPathAbsoluteSystemsWide(String path){
        return path.startsWith("/")
                || path.startsWith("\\")
                || path.startsWith(AwsConstants.S3_URI_PREFIX)
                || path.startsWith(AzureConstants.BLOB_STORAGE_URI_PREFIX)
                || Path.of(path).isAbsolute();
    }

    /**
     * Sets the specific file format (based on DuckdbSourceFilesType's source file types) on the file format object passed as first argument.
     * @param fileFormatSpec
     * @param duckdbFilesFormatType
     */
    private static void fillDefaultFileFormat(FileFormatSpec fileFormatSpec, DuckdbFilesFormatType duckdbFilesFormatType){
        switch(duckdbFilesFormatType){
            case csv: fileFormatSpec.setCsv(new CsvFileFormatSpec()); break;
            case json: fileFormatSpec.setJson(new JsonFileFormatSpec()); break;
            case parquet: fileFormatSpec.setParquet(new ParquetFileFormatSpec()); break;
            case iceberg: fileFormatSpec.setIceberg(new IcebergFileFormatSpec()); break;
            default: throw new RuntimeException("Can't fill default file format for files type: " + duckdbFilesFormatType);
        }
    }

}
