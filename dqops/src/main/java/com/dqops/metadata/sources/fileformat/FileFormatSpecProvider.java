package com.dqops.metadata.sources.fileformat;

import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
import com.dqops.metadata.sources.TableSpec;

/**
 * Provider for the File Format.
 */
public class FileFormatSpecProvider {

    /**
     * Resolves the file format based on table spec and parameters spec.
     * At first, tries to return file format from the table spec. If it is not available then tries to use a file format from parameters spec.
     * Also file paths are always tries to be used from the table spec when set.
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

    private static void fillDefaultFileFormat(FileFormatSpec fileFormatSpec, DuckdbSourceFilesType duckdbSourceFilesType){
        switch(duckdbSourceFilesType){
            case csv: fileFormatSpec.setCsv(new CsvFileFormatSpec()); break;
            case json: fileFormatSpec.setJson(new JsonFileFormatSpec()); break;
            case parquet: fileFormatSpec.setParquet(new ParquetFileFormatSpec()); break;
            default: throw new RuntimeException("Cant fill default file format for files type: " + duckdbSourceFilesType);
        }
    }

}
