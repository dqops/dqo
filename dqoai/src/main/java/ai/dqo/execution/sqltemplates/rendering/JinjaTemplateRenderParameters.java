/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.sqltemplates.rendering;

import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.TimeWindowFilterParameters;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindResult;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionSpec;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionSpec;
import ai.dqo.metadata.groupings.DataGroupingConfigurationSpec;
import ai.dqo.metadata.timeseries.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

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
    private DataGroupingConfigurationSpec effectiveGroupings;
    private SensorDefinitionSpec sensorDefinition;
    private ProviderSensorDefinitionSpec providerSensorDefinition;
    private ProviderDialectSettings dialectSettings;
    private String actualValueAlias = SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME;
    private String expectedValueAlias = SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME;

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
     * @param effectiveGroupings Effective data groupings configuration.
     * @param sensorDefinition Sensor definition spec.
     * @param providerSensorDefinition Provider sensor definition spec.
     * @param dialectSettings Dialect settings with configuration of the dialect.
     * @param actualValueAlias The column alias that should be used for the actual value output column name.
     * @param expectedValueAlias The column alias that should be used for the expected value output column name.
     */
    public JinjaTemplateRenderParameters(ConnectionSpec connection,
										 TableSpec table,
										 ColumnSpec column,
										 String columnName,
										 AbstractSensorParametersSpec parameters,
                                         TimeSeriesConfigurationSpec effectiveTimeSeries,
                                         TimeWindowFilterParameters effectiveTimeWindowFilter,
                                         DataGroupingConfigurationSpec effectiveGroupings,
										 SensorDefinitionSpec sensorDefinition,
										 ProviderSensorDefinitionSpec providerSensorDefinition,
										 ProviderDialectSettings dialectSettings,
                                         String actualValueAlias,
                                         String expectedValueAlias) {
        this.connection = connection;
        this.table = table;
        this.targetTable = table.getPhysicalTableName();
        this.column = column;
        this.columnName = columnName;
        this.parameters = parameters;
        this.effectiveTimeSeries = effectiveTimeSeries;
        this.effectiveTimeWindowFilter = effectiveTimeWindowFilter;
        this.effectiveGroupings = effectiveGroupings;
        this.sensorDefinition = sensorDefinition;
        this.providerSensorDefinition = providerSensorDefinition;
        this.dialectSettings = dialectSettings;
        this.actualValueAlias = actualValueAlias;
        this.expectedValueAlias = expectedValueAlias;
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
            setEffectiveGroupings(sensorRunParameters.getGroupings() != null ? sensorRunParameters.getGroupings().truncateToColumns() : null);
			setSensorDefinition(sensorDefinitions.getSensorDefinitionSpec().trim());
			setProviderSensorDefinition(sensorDefinitions.getProviderSensorDefinitionSpec().trim());
			setDialectSettings(sensorRunParameters.getDialectSettings());
            setEffectiveTimeWindowFilter(sensorRunParameters.getTimeWindowFilter());
            setActualValueAlias(sensorRunParameters.getActualValueAlias());
            setExpectedValueAlias(sensorRunParameters.getExpectedValueAlias());
        }};

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
    public DataGroupingConfigurationSpec getEffectiveGroupings() {
        return effectiveGroupings;
    }

    /**
     * Sets the effective data grouping configuration.
     * @param effectiveGroupings Effective data grouping configuration.
     */
    public void setEffectiveGroupings(DataGroupingConfigurationSpec effectiveGroupings) {
        this.effectiveGroupings = effectiveGroupings;
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
}
