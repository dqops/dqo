/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.connectors.duckdb;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.BaseProviderParametersSpec;
import com.dqops.metadata.sources.fileformat.CsvFileFormatSpec;
import com.dqops.metadata.sources.fileformat.JsonFileFormatSpec;
import com.dqops.metadata.sources.fileformat.ParquetFileFormatSpec;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import picocli.CommandLine;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * DuckDB connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DuckdbParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {
    private static final ChildHierarchyNodeFieldMapImpl<DuckdbParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
            put("csv", o -> o.csv);
            put("json", o -> o.json);
            put("parquet", o -> o.parquet);
        }
    };

    @CommandLine.Option(names = {"--duckdb-read-mode"}, description = "DuckDB read mode. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Type of source files for DuckDB. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private DuckdbReadMode readMode;

    @CommandLine.Option(names = {"--duckdb-source-files-type"}, description = "Type of source files for DuckDB. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Type of source files for DuckDB. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private DuckdbSourceFilesType sourceFilesType;

    @CommandLine.Option(names = {"--duckdb-database"}, description = "DuckDB database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("DuckDB database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String database;

    @CommandLine.Option(names = {"--duckdb-options"}, description = "DuckDB connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes.")
    @JsonPropertyDescription("DuckDB connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${DUCKDB_OPTIONS} configuration with a custom environment variable.")
    private String options;

    @CommandLine.Option(names = {"-Duck"}, description = "DuckDB additional properties that are added to the JDBC connection string")
    @JsonPropertyDescription("A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> properties;

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

    /**
     * Returns a readMode value.
     * @return readMode value.
     */
    public DuckdbReadMode getReadMode() {
        return readMode;
    }

    /**
     * Sets a readMode value.
     * @param readMode readMode value.
     */
    public void setReadMode(DuckdbReadMode readMode) {
        setDirtyIf(!Objects.equals(readMode, readMode));
        this.readMode = readMode;
    }

    /**
     * Returns a sourceFilesType value.
     * @return sourceFilesType value.
     */
    public DuckdbSourceFilesType getSourceFilesType() {
        return sourceFilesType;
    }

    /**
     * Sets a sourceFilesType value.
     * @param sourceFilesType sourceFilesType value.
     */
    public void setSourceFilesType(DuckdbSourceFilesType sourceFilesType) {
        setDirtyIf(!Objects.equals(sourceFilesType, sourceFilesType));
        this.sourceFilesType = sourceFilesType;
    }

    /**
     * Returns a physical database name.
     * @return Physical database name.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Sets a physical database name.
     * @param database Physical database name.
     */
    public void setDatabase(String database) {
        setDirtyIf(!Objects.equals(this.database, database));
        this.database = database;
    }

    /**
     * Returns the custom connection initialization options.
     * @return Connection initialization options.
     */
    public String getOptions() {
        return options;
    }

    /**
     * Sets the connection initialization options.
     * @param options Connection initialization options.
     */
    public void setOptions(String options) {
        setDirtyIf(!Objects.equals(this.options, options));
        this.options = options;
    }

    /**
     * Returns a key/value map of additional properties that are included in the JDBC connection string.
     * @return Key/value dictionary of additional JDBC properties.
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Sets a dictionary of additional connection parameters that are added to the JDBC connection string.
     * @param properties Key/value dictionary with extra parameters.
     */
    public void setProperties(Map<String, String> properties) {
        setDirtyIf(!Objects.equals(this.properties, properties));
        this.properties = properties != null ? Collections.unmodifiableMap(properties) : null;
    }

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

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Creates and returns a deep copy of this object.
     */
    @Override
    public DuckdbParametersSpec deepClone() {
        DuckdbParametersSpec cloned = (DuckdbParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public DuckdbParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        DuckdbParametersSpec cloned = this.deepClone();
        cloned.database = secretValueProvider.expandValue(cloned.database, lookupContext);
        cloned.options = secretValueProvider.expandValue(cloned.options, lookupContext);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties, lookupContext);
        cloned.csv = cloned.csv.expandAndTrim(secretValueProvider, lookupContext);
        cloned.json = cloned.json.expandAndTrim(secretValueProvider, lookupContext);

        return cloned;
    }

}
