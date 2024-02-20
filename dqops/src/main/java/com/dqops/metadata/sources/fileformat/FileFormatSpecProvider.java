package com.dqops.metadata.sources.fileformat;

import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
import com.dqops.metadata.sources.TableSpec;

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
        DuckdbSourceFilesType filesType = duckdbParametersSpec.getSourceFilesType();
        if (filesType == null) {
            return null;
        }

        FileFormatSpec fileFormat = tableSpec.getFileFormat() == null ? new FileFormatSpec() : tableSpec.getFileFormat();
        if(fileFormat.getFilePaths().isEmpty()){
            fileFormat.setFilePaths(guessFilePaths(duckdbParametersSpec, tableSpec));
        }

        if (fileFormat.isFormatSetForType(filesType)) {
            return fileFormat;
        }

        FileFormatSpec fileFormatCloned = fileFormat.deepClone();
        if (duckdbParametersSpec.isFormatSetForType(filesType)) {
            switch (filesType) {
                case csv: fileFormatCloned.setCsv(duckdbParametersSpec.getCsv().deepClone()); break;
                case json: fileFormatCloned.setJson(duckdbParametersSpec.getJson().deepClone()); break;
                case parquet: fileFormatCloned.setParquet(duckdbParametersSpec.getParquet().deepClone()); break;
            }
            return fileFormatCloned;
        }

        fillDefaultFileFormat(fileFormatCloned, filesType);
        return fileFormatCloned;
    }

    /**
     * Guesses file paths with use of the directories from parametersSpec, table's schema and table name.
     * @param duckdbParametersSpec Duckdb parameters spec
     * @param tableSpec Table spec
     * @return FilePathListSpec with filled a single filepath to the data that can be used by duckdb to make a query.
     */
    public static FilePathListSpec guessFilePaths(DuckdbParametersSpec duckdbParametersSpec, TableSpec tableSpec){
        FilePathListSpec filePathListSpec = new FilePathListSpec();

        Map<String, String> directories = duckdbParametersSpec.getDirectories();
        String tableName = tableSpec.getPhysicalTableName().getTableName();
        String schemaName = tableSpec.getPhysicalTableName().getSchemaName();
        String pathForSchema = directories.get(schemaName);
        String filePath = isPathAbsoluteSystemsWide(tableName) ? tableName : Path.of(schemaName, pathForSchema).toString();

        // todo: what if the file extension eg. json will not match the source file types e.g. csv on the parameter spec??
        String fileExtension = "." + duckdbParametersSpec.getSourceFilesType().toString();
        if(!filePath.toLowerCase().endsWith(fileExtension)){
            filePath = filePath + File.separator + "**" + fileExtension;
        }
        filePathListSpec.add(filePath);
        return filePathListSpec;
    }

    /**
     * Returns whether the path is an absolute path regardless of the system.
     * @param path The path to the file or a directory.
     * @return State whether the path is an absolute one.
     */
    public static boolean isPathAbsoluteSystemsWide(String path){
        return path.startsWith("/")
                || path.startsWith("\\")
                || path.startsWith("s3://")
                || Path.of(path).isAbsolute();
    }

    /**
     * Sets the specific file format (based on DuckdbSourceFilesType's source file types) on the file format object passed as first argument.
     * @param fileFormatSpec
     * @param duckdbSourceFilesType
     */
    private static void fillDefaultFileFormat(FileFormatSpec fileFormatSpec, DuckdbSourceFilesType duckdbSourceFilesType){
        switch(duckdbSourceFilesType){
            case csv: fileFormatSpec.setCsv(new CsvFileFormatSpec()); break;
            case json: fileFormatSpec.setJson(new JsonFileFormatSpec()); break;
            case parquet: fileFormatSpec.setParquet(new ParquetFileFormatSpec()); break;
            default: throw new RuntimeException("Cant fill default file format for files type: " + duckdbSourceFilesType);
        }
    }

}
