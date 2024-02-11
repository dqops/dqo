package com.dqops.metadata.sources.fileformat;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * File format specification for data loaded from the physical files of one of supported formats.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class FileFormatSpec extends AbstractSpec {

    private static final ChildHierarchyNodeFieldMapImpl<FileFormatSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("file_paths", o -> o.filePaths);
            put("csv_file_format", o -> o.csvFileFormat);
            put("json_file_format", o -> o.jsonFileFormat);
            put("parquet_file_format", o -> o.parquetFileFormat);
        }
    };

    @JsonPropertyDescription("Csv file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CsvFileFormatSpec csvFileFormat;

    @JsonPropertyDescription("Json file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private JsonFileFormatSpec jsonFileFormat;

    @JsonPropertyDescription("Parquet file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ParquetFileFormatSpec parquetFileFormat;

    @JsonPropertyDescription("The list of paths to files with data that are used as a source.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private FilePathListSpec filePaths = new FilePathListSpec();

    public FileFormatSpec() {
    }

    /**
     * Returns the csv file format specification.
     * @return Csv file format specification.
     */
    public CsvFileFormatSpec getCsvFileFormat() {
        return csvFileFormat;
    }

    /**
     * Sets the csv file format specification.
     * @param csvFileFormat Csv file format specification.
     */
    public void setCsvFileFormat(CsvFileFormatSpec csvFileFormat) {
        setDirtyIf(!Objects.equals(this.csvFileFormat, csvFileFormat));
        this.csvFileFormat = csvFileFormat;
        propagateHierarchyIdToField(csvFileFormat, "csv_file_format");
    }

    /**
     * Returns the json file format specification.
     * @return Json file format specification.
     */
    public JsonFileFormatSpec getJsonFileFormat() {
        return jsonFileFormat;
    }

    /**
     * Sets the json file format specification.
     * @param jsonFileFormat Json file format specification.
     */
    public void setJsonFileFormat(JsonFileFormatSpec jsonFileFormat) {
        setDirtyIf(!Objects.equals(this.jsonFileFormat, jsonFileFormat));
        this.jsonFileFormat = jsonFileFormat;
        propagateHierarchyIdToField(jsonFileFormat, "json_file_format");
    }

    /**
     * Returns the parquet file format specification.
     * @return Parquet file format specification.
     */
    public ParquetFileFormatSpec getParquetFileFormat() {
        return parquetFileFormat;
    }

    /**
     * Sets the parquet file format specification.
     * @param parquetFileFormat Parquet file format specification.
     */
    public void setParquetFileFormat(ParquetFileFormatSpec parquetFileFormat) {
        setDirtyIf(!Objects.equals(this.parquetFileFormat, parquetFileFormat));
        this.parquetFileFormat = parquetFileFormat;
        propagateHierarchyIdToField(parquetFileFormat, "parquet_file_format");
    }

    /**
     * Returns the list of paths to files with data that are used as a source.
     * @return List with file paths.
     */
    public FilePathListSpec getFilePaths() {
        return filePaths;
    }

    /**
     * Sets the list of paths to files with data that are used as a source.
     * @param filePaths List with file paths.
     */
    public void setFilePaths(FilePathListSpec filePaths) {
        setDirtyIf(!Objects.equals(this.filePaths, filePaths));
        this.filePaths = filePaths;
        propagateHierarchyIdToField(filePaths, "file_paths");
    }

    /**
     * Builds the table options string for use in SQL query that contains file paths to the source data files and options for the files.
     * @return Table options string.
     */
    public String buildTableOptionsString(){
        if (csvFileFormat != null) {
            return csvFileFormat.buildSourceTableOptionsString(filePaths);
        }
//        if(jsonFileFormat != null){   // todo
//
//        }
//        if(parquetFileFormat != null){    // todo
//
//        }

        throw new RuntimeException("Cant create table options string for the given files. " + this.toString());
    }

    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Returns a string representation of the object.
     * The output describes the target file format spec with its details.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.filePaths != null) {
            stringBuilder.append("Files: ");
            stringBuilder.append(String.join(", ", this.filePaths) );
        }

        return stringBuilder.toString();
    }

}
