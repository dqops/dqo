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

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Csv file format specification for querying data in the csv format files.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class CsvFileFormatSpec extends AbstractSpec {

    private static final ChildHierarchyNodeFieldMapImpl<CsvFileFormatSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    private Boolean allVarchar;
    private Boolean allowQuotedNulls;
    private Boolean autoDetect;
    private SortedMap<String, String> columns;
    private String compression;
    private String dateformat;
    private String decimalSeparator;
    private String delim;
    private String escape;
    private Boolean filename;
    private Boolean header;
    private Boolean hivePartitioning;
    private Boolean ignoreErrors;
    private String newLine;
    private String quote;
    private Long skip;
    private String timestampformat;

    /**
     * Formats the table options to be used in SQL query. The set (non null) options are added only.
     * @param filePathList The names of files with data.
     * @return The formatted source table with the options.
     */
    public String buildSourceTableOptionsString(List<String> filePathList){
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("read_csv", filePathList);
        tableOptionsFormatter.formatValueWhenSet(Fields.allVarchar, allVarchar);
        tableOptionsFormatter.formatValueWhenSet(Fields.allowQuotedNulls, allowQuotedNulls);
        tableOptionsFormatter.formatValueWhenSet(Fields.autoDetect, autoDetect);
        tableOptionsFormatter.formatMapWhenSet(Fields.columns, columns);
        tableOptionsFormatter.formatStringWhenSet(Fields.compression, compression);
        tableOptionsFormatter.formatStringWhenSet(Fields.dateformat, dateformat);
        tableOptionsFormatter.formatStringWhenSet(Fields.decimalSeparator, decimalSeparator);
        tableOptionsFormatter.formatStringWhenSet(Fields.delim, delim);
        tableOptionsFormatter.formatStringWhenSet(Fields.escape, escape);
        tableOptionsFormatter.formatValueWhenSet(Fields.filename, filename);
        tableOptionsFormatter.formatValueWhenSet(Fields.header, header);
        tableOptionsFormatter.formatValueWhenSet(Fields.hivePartitioning, hivePartitioning);
        tableOptionsFormatter.formatValueWhenSet(Fields.ignoreErrors, ignoreErrors);
        tableOptionsFormatter.formatStringWhenSet(Fields.newLine, newLine);
        tableOptionsFormatter.formatStringWhenSet(Fields.quote, quote);
        tableOptionsFormatter.formatValueWhenSet(Fields.skip, skip);
        tableOptionsFormatter.formatStringWhenSet(Fields.timestampformat, timestampformat);
        return tableOptionsFormatter.toString();
    }

    /**
     * Returns the option that skip type detection for CSV parsing and assume all columns to be of type VARCHAR.
     * @return All varchar option.
     */
    public Boolean getAllVarchar() {
        return allVarchar;
    }

    /**
     * Sets the option that skip type detection for CSV parsing and assume all columns to be of type VARCHAR.
     * @param allVarchar All varchar.
     */
    public void setAllVarchar(Boolean allVarchar) {
        this.allVarchar = allVarchar;
    }

    /**
     * Returns the option that allow the conversion of quoted values to NULL values.
     * @return Allow quoted nulls option.
     */
    public Boolean getAllowQuotedNulls() {
        return allowQuotedNulls;
    }

    /**
     * Sets the option that allow the conversion of quoted values to NULL values.
     * @param allowQuotedNulls Allow quoted nulls.
     */
    public void setAllowQuotedNulls(Boolean allowQuotedNulls) {
        this.allowQuotedNulls = allowQuotedNulls;
    }

    /**
     * Returns the state of the option that enables auto detection of CSV parameters.
     * @return The auto detect option.
     */
    public Boolean getAutoDetect() {
        return autoDetect;
    }

    /**
     * Sets the option that enables auto detection of CSV parameters.
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
    public void setColumns(SortedMap<String, String> columns) {
        this.columns = columns;
    }

    /**
     * Returns the compression type for the file. By default this will be detected automatically from the file extension
     * @return Compression type.
     */
    public String getCompression() {
        return compression;
    }

    /**
     * Sets the compression type for the file. By default this will be detected automatically from the file extension
     * @param compression Compression type.
     */
    public void setCompression(String compression) {
        this.compression = compression;
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
     * Returns the decimal separator of numbers.
     * @return Decimal separator.
     */
    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    /**
     * Sets the decimal separator of numbers.
     * @param decimalSeparator Decimal separator.
     */
    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    /**
     * Returns the string that separates columns within each row (line) of the file.
     * @return Delimiter.
     */
    public String getDelim() {
        return delim;
    }

    /**
     * Sets the string that separates columns within each row (line) of the file.
     * @param delim Delimiter.
     */
    public void setDelim(String delim) {
        this.delim = delim;
    }

    /**
     * Returns the string that should appear before a data character sequence that matches the quote value.
     * @return Escape char sequence.
     */
    public String getEscape() {
        return escape;
    }

    /**
     * Sets the string that should appear before a data character sequence that matches the quote value.
     * @param escape Escape char sequence.
     */
    public void setEscape(String escape) {
        this.escape = escape;
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
     * Returns that the file contains a header line with the names of each column is in the file.
     * @return The header option state.
     */
    public Boolean getHeader() {
        return header;
    }

    /**
     * Sets that the file contains a header line with the names of each column is in the file.
     * @param header The header option state.
     */
    public void setHeader(Boolean header) {
        this.header = header;
    }

    /**
     * Returns whether or not to interpret the path as a hive partitioned path.
     * @return The hive partitioning option state.
     */
    public Boolean getHivePartitioning() {
        return hivePartitioning;
    }

    /**
     * Sets to interpret the path as a hive partitioned path.
     * @param hivePartitioning Hive partitioning option state.
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
     * Returns the new line character(s) in the file.
     * @return New line value.
     */
    public String getNewLine() {
        return newLine;
    }

    /**
     * Set the new line character(s) in the file. Options are '\r','\n', or '\r\n'.
     * @param newLine New line value.
     */
    public void setNewLine(String newLine) {
        this.newLine = newLine;
    }

    /**
     * Returns the quoting string to be used when a data value is quoted.
     * @return Quoting string.
     */
    public String getQuote() {
        return quote;
    }

    /**
     * Sets the quoting string to be used when a data value is quoted.
     * @param quote Quoting string.
     */
    public void setQuote(String quote) {
        this.quote = quote;
    }

    /**
     * Returns the number of lines at the top of the file to skip.
     * @return Number of lines to skip.
     */
    public Long getSkip() {
        return skip;
    }

    /**
     * Sets the number of lines at the top of the file to skip.
     * @param skip Number of lines to skip.
     */
    public void setSkip(Long skip) {
        this.skip = skip;
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
