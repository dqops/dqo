package com.dqops.metadata.sources.fileformat.csv;

import com.dqops.connectors.duckdb.config.DuckdbCompressionTypeOption;
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

    @JsonPropertyDescription("Option to skip type detection for CSV parsing and assume all columns to be of type VARCHAR.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean allVarchar;

    @JsonPropertyDescription("Option to allow the conversion of quoted values to NULL values.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean allowQuotedNulls;

    @JsonPropertyDescription("Enables auto detection of CSV parameters.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean autoDetect = true;

    @JsonPropertyDescription("The compression type for the file. By default this will be detected automatically from the file extension (e.g., t.csv.gz will use gzip, t.csv will use none). Options are none, gzip, zstd.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private CompressionType compression;

    @JsonPropertyDescription("Whether the compression extension is present at the end of the file name.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean noCompressionExtension;

    @JsonPropertyDescription("Specifies the date format to use when parsing dates.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String dateformat;

    @JsonPropertyDescription("The decimal separator of numbers.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String decimalSeparator;

    @JsonPropertyDescription("Specifies the string that separates columns within each row (line) of the file.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String delim;

    @JsonPropertyDescription("Specifies the string that should appear before a data character sequence that matches the quote value.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String escape;

    @JsonPropertyDescription("Whether or not an extra filename column should be included in the result.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean filename;

    @JsonPropertyDescription("Specifies that the file contains a header line with the names of each column in the file.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean header;

    @JsonPropertyDescription("Whether or not to interpret the path as a hive partitioned path.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean hivePartitioning;

    @JsonPropertyDescription("Option to ignore any parsing errors encountered - and instead ignore rows with errors.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean ignoreErrors;

    @JsonPropertyDescription("Set the new line character(s) in the file. Options are '\\r','\\n', or '\\r\\n'.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private NewLineCharacterType newLine;

    @JsonPropertyDescription("Specifies the quoting string to be used when a data value is quoted.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String quote;

    @JsonPropertyDescription("The number of sample rows for auto detection of parameters.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long sampleSize;

    @JsonPropertyDescription("The number of lines at the top of the file to skip.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long skip;

    @JsonPropertyDescription("Specifies the date format to use when parsing timestamps.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String timestampformat;

    /**
     * Formats the table options to be used in SQL query. The set (non null) options are added only.
     *
     * @param filePathList The names of files with data.
     * @return The formatted source table with the options.
     */
    public String buildSourceTableOptionsString(List<String> filePathList, TableSpec tableSpec) {
        TableOptionsFormatter tableOptionsFormatter = new TableOptionsFormatter("read_csv", filePathList);
        tableOptionsFormatter.formatValueWhenSet(Fields.allVarchar, allVarchar);
        tableOptionsFormatter.formatValueWhenSet(Fields.allowQuotedNulls, allowQuotedNulls);
        tableOptionsFormatter.formatValueWhenSet(Fields.autoDetect, autoDetect);
        tableOptionsFormatter.formatColumns("columns", tableSpec);
        tableOptionsFormatter.formatStringWhenSet(Fields.compression, DuckdbCompressionTypeOption.fromCompressionType(compression));
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
        tableOptionsFormatter.formatValueWhenSet(Fields.sampleSize, sampleSize);
        tableOptionsFormatter.formatValueWhenSet(Fields.skip, skip);
        tableOptionsFormatter.formatStringWhenSet(Fields.timestampformat, timestampformat);
        return tableOptionsFormatter.build();
    }

    /**
     * Returns the option that skip type detection for CSV parsing and assume all columns to be of type VARCHAR.
     *
     * @return All varchar option.
     */
    public Boolean getAllVarchar() {
        return allVarchar;
    }

    /**
     * Sets the option that skip type detection for CSV parsing and assume all columns to be of type VARCHAR.
     *
     * @param allVarchar All varchar.
     */
    public void setAllVarchar(Boolean allVarchar) {
        setDirtyIf(!Objects.equals(this.allVarchar, allVarchar));
        this.allVarchar = allVarchar;
    }

    /**
     * Returns the option that allow the conversion of quoted values to NULL values.
     *
     * @return Allow quoted nulls option.
     */
    public Boolean getAllowQuotedNulls() {
        return allowQuotedNulls;
    }

    /**
     * Sets the option that allow the conversion of quoted values to NULL values.
     *
     * @param allowQuotedNulls Allow quoted nulls.
     */
    public void setAllowQuotedNulls(Boolean allowQuotedNulls) {
        setDirtyIf(!Objects.equals(this.allowQuotedNulls, allowQuotedNulls));
        this.allowQuotedNulls = allowQuotedNulls;
    }

    /**
     * Returns the state of the option that enables auto detection of CSV parameters.
     *
     * @return The auto detect option.
     */
    public Boolean getAutoDetect() {
        return autoDetect;
    }

    /**
     * Sets the option that enables auto detection of CSV parameters.
     *
     * @param autoDetect Auto detect option.
     */
    public void setAutoDetect(Boolean autoDetect) {
        setDirtyIf(!Objects.equals(this.autoDetect, autoDetect));
        this.autoDetect = autoDetect;
    }

    /**
     * Returns the compression type for the file. By default this will be detected automatically from the file extension
     *
     * @return Compression type.
     */
    public CompressionType getCompression() {
        return compression;
    }

    /**
     * Sets the compression type for the file. By default this will be detected automatically from the file extension
     *
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
     * Returns the date format to use when parsing dates.
     *
     * @return Date format.
     */
    public String getDateformat() {
        return dateformat;
    }


    /**
     * Sets the date format to use when parsing dates.
     *
     * @param dateformat Date format.
     */
    public void setDateformat(String dateformat) {
        setDirtyIf(!Objects.equals(this.dateformat, dateformat));
        this.dateformat = dateformat;
    }

    /**
     * Returns the decimal separator of numbers.
     *
     * @return Decimal separator.
     */
    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    /**
     * Sets the decimal separator of numbers.
     *
     * @param decimalSeparator Decimal separator.
     */
    public void setDecimalSeparator(String decimalSeparator) {
        setDirtyIf(!Objects.equals(this.decimalSeparator, decimalSeparator));
        this.decimalSeparator = decimalSeparator;
    }

    /**
     * Returns the string that separates columns within each row (line) of the file.
     *
     * @return Delimiter.
     */
    public String getDelim() {
        return delim;
    }

    /**
     * Sets the string that separates columns within each row (line) of the file.
     *
     * @param delim Delimiter.
     */
    public void setDelim(String delim) {
        setDirtyIf(!Objects.equals(this.delim, delim));
        this.delim = delim;
    }

    /**
     * Returns the string that should appear before a data character sequence that matches the quote value.
     *
     * @return Escape char sequence.
     */
    public String getEscape() {
        return escape;
    }

    /**
     * Sets the string that should appear before a data character sequence that matches the quote value.
     *
     * @param escape Escape char sequence.
     */
    public void setEscape(String escape) {
        setDirtyIf(!Objects.equals(this.escape, escape));
        this.escape = escape;
    }

    /**
     * Returns whether or not an extra filename column should be included in the result.
     *
     * @return The filename option state.
     */
    public Boolean getFilename() {
        return filename;
    }

    /**
     * Sets that an extra filename column should be included in the result.
     *
     * @param filename The filename option state.
     */
    public void setFilename(Boolean filename) {
        setDirtyIf(!Objects.equals(this.filename, filename));
        this.filename = filename;
    }

    /**
     * Returns that the file contains a header line with the names of each column is in the file.
     *
     * @return The header option state.
     */
    public Boolean getHeader() {
        return header;
    }

    /**
     * Sets that the file contains a header line with the names of each column is in the file.
     *
     * @param header The header option state.
     */
    public void setHeader(Boolean header) {
        setDirtyIf(!Objects.equals(this.header, header));
        this.header = header;
    }

    /**
     * Returns whether or not to interpret the path as a hive partitioned path.
     *
     * @return The hive partitioning option state.
     */
    public Boolean getHivePartitioning() {
        return hivePartitioning;
    }

    /**
     * Sets to interpret the path as a hive partitioned path.
     *
     * @param hivePartitioning Hive partitioning option state.
     */
    public void setHivePartitioning(Boolean hivePartitioning) {
        setDirtyIf(!Objects.equals(this.hivePartitioning, hivePartitioning));
        this.hivePartitioning = hivePartitioning;
    }

    /**
     * Returns option that ignore any parsing errors encountered - and instead ignore rows with errors.
     *
     * @return Ignore errors option state.
     */
    public Boolean getIgnoreErrors() {
        return ignoreErrors;
    }

    /**
     * Sets option that ignore any parsing errors encountered - and instead ignore rows with errors.
     *
     * @param ignoreErrors Ignore errors option state.
     */
    public void setIgnoreErrors(Boolean ignoreErrors) {
        setDirtyIf(!Objects.equals(this.ignoreErrors, ignoreErrors));
        this.ignoreErrors = ignoreErrors;
    }

    /**
     * Returns the new line character(s) in the file.
     *
     * @return New line value.
     */
    public NewLineCharacterType getNewLine() {
        return newLine;
    }

    /**
     * Set the new line character(s) in the file. Options are '\r','\n', or '\r\n'.
     *
     * @param newLine New line value.
     */
    public void setNewLine(NewLineCharacterType newLine) {
        setDirtyIf(!Objects.equals(this.newLine, newLine));
        this.newLine = newLine;
    }

    /**
     * Returns the quoting string to be used when a data value is quoted.
     *
     * @return Quoting string.
     */
    public String getQuote() {
        return quote;
    }

    /**
     * Sets the quoting string to be used when a data value is quoted.
     *
     * @param quote Quoting string.
     */
    public void setQuote(String quote) {
        setDirtyIf(!Objects.equals(this.quote, quote));
        this.quote = quote;
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
     * Returns the number of lines at the top of the file to skip.
     *
     * @return Number of lines to skip.
     */
    public Long getSkip() {
        return skip;
    }

    /**
     * Sets the number of lines at the top of the file to skip.
     *
     * @param skip Number of lines to skip.
     */
    public void setSkip(Long skip) {
        setDirtyIf(!Objects.equals(this.skip, skip));
        this.skip = skip;
    }

    /**
     * Returns the date format to use when parsing timestamps.
     *
     * @return Timestamp format.
     */
    public String getTimestampformat() {
        return timestampformat;
    }

    /**
     * Sets the date format to use when parsing timestamps.
     *
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
    public CsvFileFormatSpec deepClone() {
        return (CsvFileFormatSpec)super.deepClone();
    }

    /**
     * Creates an expanded and trimmed deep copy of the spec.
     * Configurable properties will be expanded if they contain environment variables or secrets.
     *
     * @param secretValueProvider Secret value provider.
     * @param lookupContext       Secret value lookup context used to access shared credentials.
     * @return Cloned, trimmed and expanded table specification.
     */
    public CsvFileFormatSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        CsvFileFormatSpec cloned = this.deepClone();
        cloned.dateformat = secretValueProvider.expandValue(cloned.dateformat, lookupContext);
        cloned.decimalSeparator = secretValueProvider.expandValue(cloned.decimalSeparator, lookupContext);
        cloned.delim = secretValueProvider.expandValue(cloned.delim, lookupContext);
        cloned.escape = secretValueProvider.expandValue(cloned.escape, lookupContext);
        cloned.quote = secretValueProvider.expandValue(cloned.quote, lookupContext);
        cloned.timestampformat = secretValueProvider.expandValue(cloned.timestampformat, lookupContext);
        return cloned;
    }
}
