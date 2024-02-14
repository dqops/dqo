package com.dqops.metadata.sources.fileformat;

import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
import com.dqops.metadata.sources.TableSpec;

public class FileFormatSpecProvider {

    /**
     * Resolves the file format based on table spec and parameters spec.
     * At first, tries to return file format from the table spec. If it is not available then tries to use a file format from parameters spec.
     *
     * @param duckdbParametersSpec
     * @param tableSpec
     * @return
     */
    public static FileFormatSpec resolveFileFormat(DuckdbParametersSpec duckdbParametersSpec, TableSpec tableSpec) {

        FileFormatSpec tableSpecFileFormat = tableSpec.getFileFormatOverride();
        if(tableSpecFileFormat != null && tableSpecFileFormat.isFormatSetForType(duckdbParametersSpec.getSourceFilesType())){
            return tableSpecFileFormat;
        }

        FilePathListSpec filePaths = new FilePathListSpec();
        if(tableSpecFileFormat.getFilePaths() != null && !tableSpecFileFormat.getFilePaths().isEmpty()){
            filePaths.addAll(tableSpecFileFormat.getFilePaths().deepClone());
        }

        FileFormatSpec parametersFileFormat = duckdbParametersSpec.getFileFormat().deepClone();
        if(parametersFileFormat != null && parametersFileFormat.isFormatSetForType(duckdbParametersSpec.getSourceFilesType())){
            parametersFileFormat.setFilePaths(filePaths);
            return parametersFileFormat;
        }

        FileFormatSpec newFileFormat = FileFormatSpecProvider.getNewFileFormat(duckdbParametersSpec.getSourceFilesType());
        newFileFormat.setFilePaths(filePaths);

        return newFileFormat;
    }

    private static FileFormatSpec getNewFileFormat(DuckdbSourceFilesType duckdbSourceFilesType){
        switch(duckdbSourceFilesType){
            case csv: return new FileFormatSpec(){{ setCsv(new CsvFileFormatSpec()); }};
            case json: return new FileFormatSpec(){{ setJson(new JsonFileFormatSpec()); }};
            case parquet: return new FileFormatSpec(){{ setParquet(new ParquetFileFormatSpec()); }};
            default: throw new RuntimeException("Cant create table options string for the given files. " + duckdbSourceFilesType);
        }
    }

}
