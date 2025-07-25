/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.metadata.sources.fileformat.json;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.fileformat.CompressionType;
import com.dqops.metadata.sources.fileformat.TableOptionsFormatter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import java.util.Objects;

/**
 * Json file format specification for querying data in the json format files.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class JsonFileFormatSpec extends AbstractSpec {

    private static final ChildHierarchyNodeFieldMapImpl<JsonFileFormatSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Whether to auto-detect detect the names of the keys and data types of the values automatically.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean autoDetect = true;

    @JsonPropertyDescription("The compression type for the file. By default this will be detected automatically from the file extension (e.g., t.json.gz will use gzip, t.json will use none). Options are 'none', 'gzip', 'zstd', and 'auto'.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private CompressionType compression;

    @JsonPropertyDescription("Whether the compression extension is present at the end of the file name.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean noCompressionExtension;

    @JsonPropertyDescription("Specifies a custom file name extension. The default is \".json\".")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String fileExtension;

    @JsonPropertyDescription("Whether strings representing integer values should be converted to a numerical type.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean convertStringsToIntegers;

    @JsonPropertyDescription("Specifies the date format to use when parsing dates.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String dateformat;

    @JsonPropertyDescription("Whether or not an extra filename column should be included in the result.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean filename;

    @JsonPropertyDescription("Json format. Can be one of ['auto', 'unstructured', 'newline_delimited', 'array'].")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private JsonFormatType format = JsonFormatType.auto;

    @JsonPropertyDescription("Whether or not to interpret the path as a hive partitioned path.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean hivePartitioning;

    @JsonPropertyDescription("Whether to ignore parse errors (only possible when format is 'newline_delimited').")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean ignoreErrors;

    @JsonPropertyDescription("Maximum nesting depth to which the automatic schema detection detects types. Set to -1 to fully detect nested JSON types.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long maximumDepth;

    @JsonPropertyDescription("The maximum size of a JSON object (in bytes).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long maximumObjectSize;

    @JsonPropertyDescription("Can be one of ['auto', 'true', 'false'].")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private JsonRecordsType records;

    @JsonPropertyDescription("The number of sample rows for auto detection of parameters.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long sampleSize;

    @JsonPropertyDescription("Specifies the date format to use when parsing timestamps.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String timestampformat;

    /**
     * Formats the table options to be used in SQL query. The set (non null) options are added only.
     * @param filePathList The names of files with data.
     * @return The formatted source table with the options.
     */
    public String buildSourceTableOptionsString(List<String> filePathList, TableSpec tableSpec){
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("read_json", filePathList);
        tableOptionsFormatter.formatValueWhenSet(Fields.autoDetect, autoDetect);
        tableOptionsFormatter.formatColumns("columns", tableSpec);
        tableOptionsFormatter.formatStringWhenSet(Fields.compression, compression);
        tableOptionsFormatter.formatValueWhenSet(Fields.convertStringsToIntegers, convertStringsToIntegers);
        tableOptionsFormatter.formatStringWhenSet(Fields.dateformat, dateformat);
        tableOptionsFormatter.formatValueWhenSet(Fields.filename, filename);
        tableOptionsFormatter.formatStringWhenSet(Fields.format, format);
        tableOptionsFormatter.formatValueWhenSet(Fields.hivePartitioning, hivePartitioning);
        tableOptionsFormatter.formatValueWhenSet(Fields.ignoreErrors, ignoreErrors);
        tableOptionsFormatter.formatValueWhenSet(Fields.maximumDepth, maximumDepth);
        tableOptionsFormatter.formatValueWhenSet(Fields.maximumObjectSize, maximumObjectSize);
        tableOptionsFormatter.formatStringWhenSet(Fields.records, records);
        tableOptionsFormatter.formatValueWhenSet(Fields.sampleSize, sampleSize);
        tableOptionsFormatter.formatStringWhenSet(Fields.timestampformat, timestampformat);
        return tableOptionsFormatter.build();
    }

    /**
     * Returns whether to auto-detect detect the names of the keys and data types of the values automatically.
     * @return The auto detect option.
     */
    public Boolean getAutoDetect() {
        return autoDetect;
    }

    /**
     * Sets whether to auto-detect detect the names of the keys and data types of the values automatically.
     * @param autoDetect Auto detect option.
     */
    public void setAutoDetect(Boolean autoDetect) {
        setDirtyIf(!Objects.equals(this.autoDetect, autoDetect));
        this.autoDetect = autoDetect;
    }

    /**
     * Returns the compression type for the file. By default this will be detected automatically from the file extension.
     * @return Compression type.
     */
    public CompressionType getCompression() {
        return compression;
    }

    /**
     * Sets the compression type for the file. By default this will be detected automatically from the file extension.
     * @param compression Compression type.
     */
    public void setCompression(CompressionType compression) {
        setDirtyIf(!Objects.equals(this.compression, compression));
        this.compression = compression;
    }

    /**
     * Returns the accountName
     * @return accountName.
     */
    public Boolean getNoCompressionExtension() {
        return noCompressionExtension;
    }

    /**
     * Sets noCompressionExtension.
     * @param noCompressionExtension noCompressionExtension.
     */
    public void setNoCompressionExtension(Boolean noCompressionExtension) {
        setDirtyIf(!Objects.equals(this.noCompressionExtension, noCompressionExtension));
        this.noCompressionExtension = noCompressionExtension;
    }

    /**
     * Returns a custom file extension.
     *
     * @return Custom file extension
     */
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * Sets a custom file name extension.
     *
     * @param fileExtension Custom file name extension.
     */
    public void setFileExtension(String fileExtension) {
        setDirtyIf(!Objects.equals(this.fileExtension, fileExtension));
        this.fileExtension = fileExtension;
    }

    /**
     * Returns whether strings representing integer values should be converted to a numerical type.
     * @return  Convert strings to integers option.
     */
    public Boolean getConvertStringsToIntegers() {
        return convertStringsToIntegers;
    }

    /**
     * Sets whether strings representing integer values should be converted to a numerical type.
     * @param convertStringsToIntegers Convert strings to integers option.
     */
    public void setConvertStringsToIntegers(Boolean convertStringsToIntegers) {
        setDirtyIf(!Objects.equals(this.convertStringsToIntegers, convertStringsToIntegers));
        this.convertStringsToIntegers = convertStringsToIntegers;
    }

    /**
     * Returns the date format to use when parsing dates.
     * @return Date format.
     */
    public String getDateformat() {
        return dateformat;
    }


    /**
     * Sets the date format to use when parsing dates.
     * @param dateformat Date format.
     */
    public void setDateformat(String dateformat) {
        setDirtyIf(!Objects.equals(this.dateformat, dateformat));
        this.dateformat = dateformat;
    }

    /**
     * Returns whether or not an extra filename column should be included in the result.
     * @return The filename option state.
     */
    public Boolean getFilename() {
        return filename;
    }

    /**
     * Sets that an extra filename column should be included in the result.
     * @param filename The filename option state.
     */
    public void setFilename(Boolean filename) {
        setDirtyIf(!Objects.equals(this.filename, filename));
        this.filename = filename;
    }

    /**
     * Returns the JSON format.
     * @return JSON format.
     */
    public JsonFormatType getFormat() {
        return format;
    }

    /**
     * Sets the JSON format.
     * @param format JSON format.
     */
    public void setFormat(JsonFormatType format) {
        setDirtyIf(!Objects.equals(this.format, format));
        this.format = format;
    }

    /**
     * Returns whether or not to interpret the path as a hive partitioned path.
     * @return The hive partitioning option state.
     */
    public Boolean getHivePartitioning() {
        return hivePartitioning;
    }

    /**
     * Sets whether or not to interpret the path as a hive partitioned path.
     * @param hivePartitioning The hive partitioning option state.
     */
    public void setHivePartitioning(Boolean hivePartitioning) {
        setDirtyIf(!Objects.equals(this.hivePartitioning, hivePartitioning));
        this.hivePartitioning = hivePartitioning;
    }

    /**
     * Returns option that ignore any parsing errors encountered - and instead ignore rows with errors.
     * @return Ignore errors option state.
     */
    public Boolean getIgnoreErrors() {
        return ignoreErrors;
    }

    /**
     * Sets option that ignore any parsing errors encountered - and instead ignore rows with errors.
     * @param ignoreErrors Ignore errors option state.
     */
    public void setIgnoreErrors(Boolean ignoreErrors) {
        setDirtyIf(!Objects.equals(this.ignoreErrors, ignoreErrors));
        this.ignoreErrors = ignoreErrors;
    }

    /**
     * Returns a maximum nesting depth to which the automatic schema detection detects types.
     * @return The maximum depth of json to be read.
     */
    public Long getMaximumDepth() {
        return maximumDepth;
    }

    /**
     * Sets a maximum nesting depth to which the automatic schema detection detects types.
     * @param maximumDepth The maximum depth of json to be read.
     */
    public void setMaximumDepth(Long maximumDepth) {
        setDirtyIf(!Objects.equals(this.maximumDepth, maximumDepth));
        this.maximumDepth = maximumDepth;
    }

    /**
     * Returns the maximum size of a JSON object (in bytes).
     * @return Maximum object size in bytes.
     */
    public Long getMaximumObjectSize() {
        return maximumObjectSize;
    }

    /**
     * Sets the maximum size of a JSON object (in bytes).
     * @param maximumObjectSize Maximum object size in bytes.
     */
    public void setMaximumObjectSize(Long maximumObjectSize) {
        setDirtyIf(!Objects.equals(this.maximumObjectSize, maximumObjectSize));
        this.maximumObjectSize = maximumObjectSize;
    }

    /**
     * Returns the records setup. Can be one of ['auto', 'true', 'false']
     * @return Records
     */
    public JsonRecordsType getRecords() {
        return records;
    }

    /**
     * Sets the records setup. Can be one of ['auto', 'true', 'false']
     * @param records Records
     */
    public void setRecords(JsonRecordsType records) {
        setDirtyIf(!Objects.equals(this.records, records));
        this.records = records;
    }

    /**
     * Returns the number of sample rows for auto detection of parameters.
     *
     * @return Number of rows for sampling.
     */
    public Long getSampleSize() {
        return sampleSize;
    }

    /**
     * Sets the number of sample rows for auto detection of parameters.
     *
     * @param sampleSize Number of rows for sampling.
     */
    public void setSampleSize(Long sampleSize) {
        setDirtyIf(!Objects.equals(this.sampleSize, sampleSize));
        this.sampleSize = sampleSize;
    }

    /**
     * Returns the date format to use when parsing timestamps.
     * @return Timestamp format.
     */
    public String getTimestampformat() {
        return timestampformat;
    }

    /**
     * Sets the date format to use when parsing timestamps.
     * @param timestampformat Timestamp format.
     */
    public void setTimestampformat(String timestampformat) {
        setDirtyIf(!Objects.equals(this.timestampformat, timestampformat));
        this.timestampformat = timestampformat;
    }

    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public JsonFileFormatSpec deepClone() {
        return (JsonFileFormatSpec)super.deepClone();
    }

    /**
     * Creates an expanded and trimmed deep copy of the spec.
     * Configurable properties will be expanded if they contain environment variables or secrets.
     *
     * @param secretValueProvider Secret value provider.
     * @param lookupContext       Secret value lookup context used to access shared credentials.
     * @return Cloned, trimmed and expanded table specification.
     */
    public JsonFileFormatSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        JsonFileFormatSpec cloned = this.deepClone();
        cloned.fileExtension = secretValueProvider.expandValue(cloned.fileExtension, lookupContext);
        cloned.dateformat = secretValueProvider.expandValue(cloned.dateformat, lookupContext);
        cloned.timestampformat = secretValueProvider.expandValue(cloned.timestampformat, lookupContext);
        return cloned;
    }

}
