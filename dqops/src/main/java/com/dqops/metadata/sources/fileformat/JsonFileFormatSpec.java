package com.dqops.metadata.sources.fileformat;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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

    private Boolean autoDetect;
    private Map<String, String> columns;
    private String compression;
    private Boolean convertStringsToIntegers;
    private String dateformat;
    private Boolean filename;
    private String format;
    private Boolean hivePartitioning;
    private Boolean ignoreErrors;
    private BigInteger maximumDepth;
    private Long maximumObjectSize;
    private String records;
    private String timestampformat;

    /**
     * Formats the table options to be used in SQL query. The set (non null) options are added only.
     * @param filePathList The names of files with data.
     * @return The formatted source table with the options.
     */
    public String buildSourceTableOptionsString(List<String> filePathList){
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("read_json", filePathList);
        tableOptionsFormatter.formatValueWhenSet(Fields.autoDetect, autoDetect);
        tableOptionsFormatter.formatMapWhenSet(Fields.columns, columns);
        tableOptionsFormatter.formatStringWhenSet(Fields.compression, compression);
        tableOptionsFormatter.formatValueWhenSet(Fields.convertStringsToIntegers, convertStringsToIntegers);
        tableOptionsFormatter.formatStringWhenSet(Fields.dateformat, dateformat);
        tableOptionsFormatter.formatValueWhenSet(Fields.filename, filename);
        tableOptionsFormatter.formatStringWhenSet(Fields.format, format);
        tableOptionsFormatter.formatValueWhenSet(Fields.hivePartitioning, hivePartitioning);
        tableOptionsFormatter.formatValueWhenSet(Fields.ignoreErrors, ignoreErrors);
        tableOptionsFormatter.formatValueWhenSet(Fields.maximumDepth, maximumDepth);
        tableOptionsFormatter.formatValueWhenSet(Fields.maximumObjectSize, maximumObjectSize);
        tableOptionsFormatter.formatValueWhenSet(Fields.records, records);
        tableOptionsFormatter.formatStringWhenSet(Fields.timestampformat, timestampformat);
        return tableOptionsFormatter.toString();
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
        this.autoDetect = autoDetect;
    }

    /**
     * Returns the columns map.
     * @return Columns map.
     */
    public Map<String, String> getColumns() {
        return columns;
    }

    /**
     * Sets the columns map.
     * @param columns Columns map.
     */
    public void setColumns(Map<String, String> columns) {
        this.columns = columns;
    }

    /**
     * Returns the compression type for the file. By default this will be detected automatically from the file extension.
     * @return Compression type.
     */
    public String getCompression() {
        return compression;
    }

    /**
     * Sets the compression type for the file. By default this will be detected automatically from the file extension.
     * @param compression Compression type.
     */
    public void setCompression(String compression) {
        this.compression = compression;
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
        this.filename = filename;
    }

    /**
     * Returns the JSON format. Can be one of ['auto', 'unstructured', 'newline_delimited', 'array'].
     * @return JSON format.
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the JSON format. Can be one of ['auto', 'unstructured', 'newline_delimited', 'array'].
     * @param format JSON format.
     */
    public void setFormat(String format) {
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
        this.ignoreErrors = ignoreErrors;
    }

    /**
     * Returns a maximum nesting depth to which the automatic schema detection detects types.
     * @return The maximum depth of json to be read.
     */
    public BigInteger getMaximumDepth() {
        return maximumDepth;
    }

    /**
     * Sets a maximum nesting depth to which the automatic schema detection detects types.
     * @param maximumDepth The maximum depth of json to be read.
     */
    public void setMaximumDepth(BigInteger maximumDepth) {
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
        this.maximumObjectSize = maximumObjectSize;
    }


    /**
     * Returns the records setup. Can be one of ['auto', 'true', 'false']
     * @return Records
     */
    public String getRecords() {
        return records;
    }

    /**
     * Sets the records setup. Can be one of ['auto', 'true', 'false']
     * @param records Records
     */
    public void setRecords(String records) {
        this.records = records;
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

}
