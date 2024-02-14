package com.dqops.metadata.sources.fileformat;

import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
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
            put("csv", o -> o.csv);
            put("json", o -> o.json);
            put("parquet", o -> o.parquet);
        }
    };

    @JsonPropertyDescription("Csv file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CsvFileFormatSpec csv;

    @JsonPropertyDescription("Json file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private JsonFileFormatSpec json;

    @JsonPropertyDescription("Parquet file format specification.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ParquetFileFormatSpec parquet;

    @JsonPropertyDescription("The list of paths to files with data that are used as a source.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private FilePathListSpec filePaths = new FilePathListSpec();

    /**
     * Returns the csv file format specification.
     * @return Csv file format specification.
     */
    public CsvFileFormatSpec getCsv() {
        return csv;
    }

    /**
     * Sets the csv file format specification.
     * @param csv Csv file format specification.
     */
    public void setCsv(CsvFileFormatSpec csv) {
        setDirtyIf(!Objects.equals(this.csv, csv));
        this.csv = csv;
        propagateHierarchyIdToField(csv, "csv");
    }

    /**
     * Returns the json file format specification.
     * @return Json file format specification.
     */
    public JsonFileFormatSpec getJson() {
        return json;
    }

    /**
     * Sets the json file format specification.
     * @param json Json file format specification.
     */
    public void setJson(JsonFileFormatSpec json) {
        setDirtyIf(!Objects.equals(this.json, json));
        this.json = json;
        propagateHierarchyIdToField(json, "json");
    }

    /**
     * Returns the parquet file format specification.
     * @return Parquet file format specification.
     */
    public ParquetFileFormatSpec getParquet() {
        return parquet;
    }

    /**
     * Sets the parquet file format specification.
     * @param parquet Parquet file format specification.
     */
    public void setParquet(ParquetFileFormatSpec parquet) {
        setDirtyIf(!Objects.equals(this.parquet, parquet));
        this.parquet = parquet;
        propagateHierarchyIdToField(parquet, "parquet");
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
    public String buildTableOptionsString(DuckdbSourceFilesType duckdbSourceFilesType){
        if(duckdbSourceFilesType.equals(DuckdbSourceFilesType.csv)){
            if(csv == null){
                csv = new CsvFileFormatSpec();
            }
            return csv.buildSourceTableOptionsString(filePaths);
        }
        if(duckdbSourceFilesType.equals(DuckdbSourceFilesType.json)){
            if(json == null){
                json = new JsonFileFormatSpec();
            }
            return json.buildSourceTableOptionsString(filePaths);
        }
        if(duckdbSourceFilesType.equals(DuckdbSourceFilesType.parquet)){
            if(parquet == null){
                parquet = new ParquetFileFormatSpec();
            }
            return parquet.buildSourceTableOptionsString(filePaths);
        }
        throw new RuntimeException("Cant create table options string for the given files. " + this.toString());
    }

    /**
     * Returns state that whether the file format for the specific file type is set.
     * @param duckdbSourceFilesType Type of files.
     * @return State that whether the file format for the specific file type is set.
     */
    public boolean isFormatSetForType(DuckdbSourceFilesType duckdbSourceFilesType){
        switch(duckdbSourceFilesType){
            case csv: return this.getCsv() != null;
            case json: return this.getJson() != null;
            case parquet: return this.getParquet() != null;
            default: throw new RuntimeException("The file format is not supported : " + duckdbSourceFilesType);
        }
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

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public FileFormatSpec deepClone() {
        return (FileFormatSpec)super.deepClone();
    }

    /**
     * Creates an expanded and trimmed (no checks for columns, no comments) deep copy of the file format spec.
     * Configurable properties will be expanded if they contain environment variables or secrets.
     * @param secretValueProvider Secret value provider.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     * @return Cloned, trimmed and expanded file format specification.
     */
    public FileFormatSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext secretValueLookupContext) {
        FileFormatSpec cloned = this.deepClone();
        if (cloned.csv != null) {
            cloned.csv = cloned.csv.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.json != null) {
            cloned.json = cloned.json.expandAndTrim(secretValueProvider, secretValueLookupContext);
        }
        if (cloned.parquet != null) {
            cloned.parquet = (ParquetFileFormatSpec) cloned.parquet.deepClone();
        }
        if (cloned.filePaths != null) {
            cloned.filePaths = cloned.filePaths.deepClone();
        }
        return cloned;
    }

}
