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

// todo: description
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
    private Map<String, String> columns;
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
     * Formats the table properties to be used in SQL query. The set (non null) properties are added only.
     * @param filePathList The names of files with data.
     * @return The formatted source table with the properties.
     */
    public String buildSourceTablePropertiesString(List<String> filePathList){
        TablePropertiesFormatter tablePropertiesFormatter = new TablePropertiesFormatter("read_csv", filePathList);
        tablePropertiesFormatter.formatBooleanWhenSet(Fields.allVarchar, allVarchar);
        tablePropertiesFormatter.formatBooleanWhenSet(Fields.allowQuotedNulls, allowQuotedNulls);
        tablePropertiesFormatter.formatBooleanWhenSet(Fields.autoDetect, autoDetect);
        tablePropertiesFormatter.formatMapWhenSet(Fields.columns, columns);
        tablePropertiesFormatter.formatStringWhenSet(Fields.compression, compression);
        tablePropertiesFormatter.formatStringWhenSet(Fields.dateformat, dateformat);
        tablePropertiesFormatter.formatStringWhenSet(Fields.decimalSeparator, decimalSeparator);
        tablePropertiesFormatter.formatStringWhenSet(Fields.delim, delim);
        tablePropertiesFormatter.formatStringWhenSet(Fields.escape, escape);
        tablePropertiesFormatter.formatBooleanWhenSet(Fields.filename, filename);
        tablePropertiesFormatter.formatBooleanWhenSet(Fields.header, header);
        tablePropertiesFormatter.formatBooleanWhenSet(Fields.hivePartitioning, hivePartitioning);
        tablePropertiesFormatter.formatBooleanWhenSet(Fields.ignoreErrors, ignoreErrors);
        tablePropertiesFormatter.formatStringWhenSet(Fields.newLine, newLine);
        tablePropertiesFormatter.formatStringWhenSet(Fields.quote, quote);
        tablePropertiesFormatter.formatLongWhenSet(Fields.skip, skip);
        tablePropertiesFormatter.formatStringWhenSet(Fields.timestampformat, timestampformat);
        tablePropertiesFormatter.formatStringWhenSet(Fields.timestampformat, timestampformat);
        return tablePropertiesFormatter.toString();
    }

    // todo: docs for all methods below
    public Boolean getAllVarchar() {
        return allVarchar;
    }

    public void setAllVarchar(Boolean allVarchar) {
        this.allVarchar = allVarchar;
    }

    public Boolean getAllowQuotedNulls() {
        return allowQuotedNulls;
    }

    public void setAllowQuotedNulls(Boolean allowQuotedNulls) {
        this.allowQuotedNulls = allowQuotedNulls;
    }

    public Boolean getAutoDetect() {
        return autoDetect;
    }

    public void setAutoDetect(Boolean autoDetect) {
        this.autoDetect = autoDetect;
    }

    public Map<String, String> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, String> columns) {
        this.columns = columns;
    }

    public String getCompression() {
        return compression;
    }

    public void setCompression(String compression) {
        this.compression = compression;
    }

    public String getDateformat() {
        return dateformat;
    }

    public void setDateformat(String dateformat) {
        this.dateformat = dateformat;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public String getDelim() {
        return delim;
    }

    public void setDelim(String delim) {
        this.delim = delim;
    }

    public String getEscape() {
        return escape;
    }

    public void setEscape(String escape) {
        this.escape = escape;
    }

    public Boolean getFilename() {
        return filename;
    }

    public void setFilename(Boolean filename) {
        this.filename = filename;
    }

    public Boolean getHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }

    public Boolean getHivePartitioning() {
        return hivePartitioning;
    }

    public void setHivePartitioning(Boolean hivePartitioning) {
        this.hivePartitioning = hivePartitioning;
    }

    public Boolean getIgnoreErrors() {
        return ignoreErrors;
    }

    public void setIgnoreErrors(Boolean ignoreErrors) {
        this.ignoreErrors = ignoreErrors;
    }

    public String getNewLine() {
        return newLine;
    }

    public void setNewLine(String newLine) {
        this.newLine = newLine;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public Long getSkip() {
        return skip;
    }

    public void setSkip(Long skip) {
        this.skip = skip;
    }

    public String getTimestampformat() {
        return timestampformat;
    }

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
