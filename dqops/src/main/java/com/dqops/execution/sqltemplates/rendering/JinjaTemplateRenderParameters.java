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
package com.dqops.execution.sqltemplates.rendering;

import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.duckdb.DuckdbParametersSpec;
import com.dqops.connectors.duckdb.DuckdbReadMode;
import com.dqops.connectors.duckdb.DuckdbSourceFilesType;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.execution.sensors.finder.SensorDefinitionFindResult;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Context object whose properties will be available as variables in the Jinja2 SQL template.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class JinjaTemplateRenderParameters {
    private ConnectionSpec connection;
    private TableSpec table;
    private PhysicalTableName targetTable;
    private ColumnSpec column; // may be null
    private String columnName; // may be null
    private AbstractSensorParametersSpec parameters;
    private TimeSeriesConfigurationSpec effectiveTimeSeries;
    private TimeWindowFilterParameters effectiveTimeWindowFilter;
    private DataGroupingConfigurationSpec effectiveDataGroupings;
    private SensorDefinitionSpec sensorDefinition;
    private ProviderSensorDefinitionSpec providerSensorDefinition;
    private ProviderDialectSettings dialectSettings;
    private String actualValueAlias = SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME;
    private String expectedValueAlias = SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME;
    private List<String> additionalFilters;
    private String tableFromFiles;

    /**
     * Creates a default, empty jinja template render parameters object.
     */
    public JinjaTemplateRenderParameters() {
    }

    /**
     * Creates a jinja render parameters using specs for all objects.
     * @param connection Connection spec.
     * @param table Table spec.
     * @param column Column spec.
     * @param columnName Column name.
     * @param parameters Sensor parameters spec.
     * @param effectiveTimeSeries Effective time series configuration.
     * @param effectiveTimeWindowFilter Effective time window filter for partitioned checks or checks with a time window (start and end dates).
     * @param effectiveDataGroupings Effective data groupings configuration.
     * @param sensorDefinition Sensor definition spec.
     * @param providerSensorDefinition Provider sensor definition spec.
     * @param dialectSettings Dialect settings with configuration of the dialect.
     * @param actualValueAlias The column alias that should be used for the actual value output column name.
     * @param expectedValueAlias The column alias that should be used for the expected value output column name.
     * @param additionalFilters List of additional filters.
     */
    public JinjaTemplateRenderParameters(ConnectionSpec connection,
										 TableSpec table,
										 ColumnSpec column,
										 String columnName,
										 AbstractSensorParametersSpec parameters,
                                         TimeSeriesConfigurationSpec effectiveTimeSeries,
                                         TimeWindowFilterParameters effectiveTimeWindowFilter,
                                         DataGroupingConfigurationSpec effectiveDataGroupings,
										 SensorDefinitionSpec sensorDefinition,
										 ProviderSensorDefinitionSpec providerSensorDefinition,
										 ProviderDialectSettings dialectSettings,
                                         String actualValueAlias,
                                         String expectedValueAlias,
                                         List<String> additionalFilters,
                                         String tableFromFiles) {
        this.connection = connection;
        this.table = table;
        this.targetTable = table.getPhysicalTableName();
        this.column = column;
        this.columnName = columnName;
        this.parameters = parameters;
        this.effectiveTimeSeries = effectiveTimeSeries;
        this.effectiveTimeWindowFilter = effectiveTimeWindowFilter;
        this.effectiveDataGroupings = effectiveDataGroupings;
        this.sensorDefinition = sensorDefinition;
        this.providerSensorDefinition = providerSensorDefinition;
        this.dialectSettings = dialectSettings;
        this.actualValueAlias = actualValueAlias;
        this.expectedValueAlias = expectedValueAlias;
        this.additionalFilters = additionalFilters;
        this.tableFromFiles = tableFromFiles;
    }

    /**
     * Creates a template render parameters using trimmed spec objects copied from the parameters.
     * Specification object trimming means that we make a clone of a table specification, but we are removing configuration of other checks to make the result json smaller.
     * @param sensorRunParameters Sensor run parameters with connection, table, column, sensor parameters to copy (with trimming).
     * @param sensorDefinitions Sensor definition specifications for the sensor itself and its provider sensor definition.
     * @return Jinja template render parameters that will be forwarded to Jinja2.
     */
    public static JinjaTemplateRenderParameters createFromTrimmedObjects(SensorExecutionRunParameters sensorRunParameters,
																		 SensorDefinitionFindResult sensorDefinitions) {
        JinjaTemplateRenderParameters result = new JinjaTemplateRenderParameters()
        {{
			setConnection(sensorRunParameters.getConnection().trim());
			setTable(sensorRunParameters.getTable().trim());
            setTargetTable(sensorRunParameters.getTable().getPhysicalTableName());
			setColumn(sensorRunParameters.getColumn() != null ? sensorRunParameters.getColumn().trim() : null);
			setColumnName(sensorRunParameters.getColumn() != null ? sensorRunParameters.getColumn().getColumnName() : null);
			setParameters(sensorRunParameters.getSensorParameters());
            setEffectiveTimeSeries(sensorRunParameters.getTimeSeries());
            setEffectiveDataGroupings(sensorRunParameters.getDataGroupings() != null ? sensorRunParameters.getDataGroupings().truncateToColumns() : null);
			setSensorDefinition(sensorDefinitions.getSensorDefinitionSpec().trim());
			setProviderSensorDefinition(sensorDefinitions.getProviderSensorDefinitionSpec().trim());
			setDialectSettings(sensorRunParameters.getDialectSettings());
            setEffectiveTimeWindowFilter(sensorRunParameters.getTimeWindowFilter());
            setActualValueAlias(sensorRunParameters.getActualValueAlias());
            setExpectedValueAlias(sensorRunParameters.getExpectedValueAlias());
            setAdditionalFilters(sensorRunParameters.getAdditionalFilters());

        }};

        DuckdbParametersSpec duckdbParametersSpec = sensorRunParameters.getConnection().getDuckdb();
        if(duckdbParametersSpec != null && duckdbParametersSpec.getDuckdbReadMode().equals(DuckdbReadMode.FILES)
            && duckdbParametersSpec.getDuckdbSourceFilesType() != null
        ){
            DuckdbSourceFilesType duckdbSourceFilesType = duckdbParametersSpec.getDuckdbSourceFilesType();
            FileFormatSpec fileFormatSpec = sensorRunParameters.getTable().getFileFormat();
            if(fileFormatSpec != null){
                result.setTableFromFiles(fileFormatSpec.buildTableOptionsString(duckdbSourceFilesType));
            }
        }

        return result;
    }

    /**
     * Returns a connection specification.
     * @return Connection specification.
     */
    public ConnectionSpec getConnection() {
        return connection;
    }

    /**
     * Sets a new connection specification.
     * @param connection Connection specification.
     */
    public void setConnection(ConnectionSpec connection) {
        this.connection = connection;
    }

    /**
     * Returns the table specification.
     * @return Table specification.
     */
    public TableSpec getTable() {
        return table;
    }

    /**
     * Sets a table specification.
     * @param table Table specification.
     */
    public void setTable(TableSpec table) {
        this.table = table;
    }

    /**
     * Returns the physical table name on which the check will be executed.
     * @return Physical table name.
     */
    public PhysicalTableName getTargetTable() {
        return targetTable;
    }

    /**
     * Sets the physical table name on which the check will be executed.
     * @param targetTable Physical table name.
     */
    public void setTargetTable(PhysicalTableName targetTable) {
        this.targetTable = targetTable;
    }

    /**
     * Returns a column specification. Not null only when a check is executed on a column level.
     * @return Column specification.
     */
    public ColumnSpec getColumn() {
        return column;
    }

    /**
     * Sets a column specification.
     * @param column Column specification.
     */
    public void setColumn(ColumnSpec column) {
        this.column = column;
    }

    /**
     * Returns the column name (physical column name), when the sensor is attached to a column.
     * @return Physical column name.
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets the physical column name, for sensors that are executed at a column level.
     * @param columnName Column name.
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Returns the sensor parameters.
     * @return Sensor parameters.
     */
    public AbstractSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets the sensor parameters.
     * @param parameters Sensor parameters.
     */
    public void setParameters(AbstractSensorParametersSpec parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns the effective time series configuration.
     * @return Effective time series configuration.
     */
    public TimeSeriesConfigurationSpec getEffectiveTimeSeries() {
        return effectiveTimeSeries;
    }

    /**
     * Sets the effective time series configuration.
     * @param effectiveTimeSeries Effective time series configuration.
     */
    public void setEffectiveTimeSeries(TimeSeriesConfigurationSpec effectiveTimeSeries) {
        this.effectiveTimeSeries = effectiveTimeSeries;
    }

    /**
     * Returns the effective time window filter used for the partitioned checks or when a period of time should be analyzed by any checks.
     * @return Effective time window filter.
     */
    public TimeWindowFilterParameters getEffectiveTimeWindowFilter() {
        return effectiveTimeWindowFilter;
    }

    /**
     * Sets teh effective time window filter for the partitioned checks or when a period of time should be analyzed by any checks.
     * @param effectiveTimeWindowFilter Effective time window filter.
     */
    public void setEffectiveTimeWindowFilter(TimeWindowFilterParameters effectiveTimeWindowFilter) {
        this.effectiveTimeWindowFilter = effectiveTimeWindowFilter;
    }

    /**
     * Returns the effective data grouping configuration.
     * @return Effective data grouping configuration.
     */
    public DataGroupingConfigurationSpec getEffectiveDataGroupings() {
        return effectiveDataGroupings;
    }

    /**
     * Sets the effective data grouping configuration.
     * @param effectiveDataGroupings Effective data grouping configuration.
     */
    public void setEffectiveDataGroupings(DataGroupingConfigurationSpec effectiveDataGroupings) {
        this.effectiveDataGroupings = effectiveDataGroupings;
    }

    /**
     * Returns the sensor definition specification.
     * @return Sensor definition specification.
     */
    public SensorDefinitionSpec getSensorDefinition() {
        return sensorDefinition;
    }

    /**
     * Sets the sensor definition specification.
     * @param sensorDefinition Sensor definition specification.
     */
    public void setSensorDefinition(SensorDefinitionSpec sensorDefinition) {
        this.sensorDefinition = sensorDefinition;
    }

    /**
     * Sensor definition specification.
     * @return Sensor definition specification.
     */
    public ProviderSensorDefinitionSpec getProviderSensorDefinition() {
        return providerSensorDefinition;
    }

    /**
     * Sets the sensor definition specification.
     * @param providerSensorDefinition Sensor definition specification.
     */
    public void setProviderSensorDefinition(ProviderSensorDefinitionSpec providerSensorDefinition) {
        this.providerSensorDefinition = providerSensorDefinition;
    }

    /**
     * Returns the dialect settings.
     * @return Dialect settings.
     */
    public ProviderDialectSettings getDialectSettings() {
        return dialectSettings;
    }

    /**
     * Sets the dialect settings.
     * @param dialectSettings Dialect settings.
     */
    public void setDialectSettings(ProviderDialectSettings dialectSettings) {
        this.dialectSettings = dialectSettings;
    }

    /**
     * Returns the column alias that should be used for the actual_value output column.
     * @return The alias for the actual_value primary sensor readout column.
     */
    public String getActualValueAlias() {
        return actualValueAlias;
    }

    /**
     * Sets the the column alias that should be used for the actual_value output column.
     * @param actualValueAlias The alias for the actual_value primary sensor readout column.
     */
    public void setActualValueAlias(String actualValueAlias) {
        this.actualValueAlias = actualValueAlias;
    }

    /**
     * Returns the column alias that should be used for the expected_value output column.
     * @return The alias for the expected_value secondary sensor readout column.
     */
    public String getExpectedValueAlias() {
        return expectedValueAlias;
    }

    /**
     * Sets the column alias that should be used for the expected_value output column.
     * @param expectedValueAlias The alias for the expected_value secondary sensor readout column.
     */
    public void setExpectedValueAlias(String expectedValueAlias) {
        this.expectedValueAlias = expectedValueAlias;
    }

    /**
     * Returns a list of additional filters.
     * @return List of additional filters.
     */
    public List<String> getAdditionalFilters() {
        return additionalFilters;
    }

    /**
     * Sets a reference to a list of additional filters.
     * @param additionalFilters A list of additional filters.
     */
    public void setAdditionalFilters(List<String> additionalFilters) {
        this.additionalFilters = additionalFilters;
    }

    /**
     * Returns the table from files with properties string.
     * @return The table properties string.
     */
    public String getTableFromFiles() {
        return tableFromFiles;
    }

    /**
     * Sets the table from files with properties string.
     * @param tableFromFiles The table properties string.
     */
    public void setTableFromFiles(String tableFromFiles) {
        this.tableFromFiles = tableFromFiles;
    }

}
