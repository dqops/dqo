package com.dqops.metadata.sources.fileformat;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

// todo: description
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class FileFormatSpec extends AbstractSpec {

    private static final ChildHierarchyNodeFieldMapImpl<FileFormatSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("file_paths", o -> o.filePathList);
            put("csv_file_format", o -> o.csvFileFormat);
            put("json_file_format", o -> o.jsonFileFormat);
            put("parquet_file_format", o -> o.parquetFileFormat);
        }
    };

    // todo  JsonPropertyDescription
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CsvFileFormatSpec csvFileFormat;

    // todo  JsonPropertyDescription
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private JsonFileFormatSpec jsonFileFormat;

    // todo  JsonPropertyDescription
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ParquetFileFormatSpec parquetFileFormat;

    // todo  JsonPropertyDescription
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private FilePathListSpec filePathList;

    public FileFormatSpec(String filePaths) {
        this.filePathList = new FilePathListSpec(){{
            add(filePaths);
        }};
    }

    // todo
    public CsvFileFormatSpec getCsvFileFormat() {
        return csvFileFormat;
    }

    // todo
    public void setCsvFileFormat(CsvFileFormatSpec csvFileFormat) {
        setDirtyIf(!Objects.equals(this.csvFileFormat, csvFileFormat));
        this.csvFileFormat = csvFileFormat;
        propagateHierarchyIdToField(csvFileFormat, "csv_file_format");
    }

    // todo
    public JsonFileFormatSpec getJsonFileFormat() {
        return jsonFileFormat;
    }

    // todo
    public void setJsonFileFormat(JsonFileFormatSpec jsonFileFormat) {
        setDirtyIf(!Objects.equals(this.jsonFileFormat, jsonFileFormat));
        this.jsonFileFormat = jsonFileFormat;
        propagateHierarchyIdToField(jsonFileFormat, "json_file_format");
    }


    // todo
    public ParquetFileFormatSpec getParquetFileFormat() {
        return parquetFileFormat;
    }

    // todo
    public void setParquetFileFormat(ParquetFileFormatSpec parquetFileFormat) {
        setDirtyIf(!Objects.equals(this.parquetFileFormat, parquetFileFormat));
        this.parquetFileFormat = parquetFileFormat;
        propagateHierarchyIdToField(parquetFileFormat, "parquet_file_format");
    }

    // todo
    public FilePathListSpec getFilePaths() {
        return filePathList;
    }

    // todo
    public void setFilePaths(FilePathListSpec filePathList) {
        setDirtyIf(!Objects.equals(this.filePathList, filePathList));
        this.filePathList = filePathList;
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
        if (this.filePathList != null) {
            stringBuilder.append("Files: ");
            stringBuilder.append(String.join(", ", this.filePathList) );
        }

        return stringBuilder.toString();
    }

}
