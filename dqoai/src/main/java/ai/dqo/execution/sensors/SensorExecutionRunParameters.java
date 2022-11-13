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
package ai.dqo.execution.sensors;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.time.ZoneId;

/**
 * Sensor execution parameter object that contains all objects required to run the sensor.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class SensorExecutionRunParameters {
    private ConnectionSpec connection;
    private TableSpec table;
    private ColumnSpec column; // may be null
    @JsonIgnore
    private AbstractCheckSpec<?,?> check;
    private TimeSeriesConfigurationSpec timeSeries;
    private DataStreamMappingSpec dataStreams;
    private AbstractSensorParametersSpec sensorParameters;
    private ProviderDialectSettings dialectSettings;
    @JsonIgnore
    private Instant startedAt = Instant.now();
    @JsonIgnore
    private ZoneId connectionTimeZoneId;

    /**
     * Creates a sensor run parameters object with all objects required to run a sensor.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Column specification.
     * @param check Check specification.
     * @param timeSeries Effective time series configuration.
     * @param dataStreams Effective data streams configuration.
     * @param sensorParameters Sensor parameters.
     * @param dialectSettings Dialect settings.
     */
    public SensorExecutionRunParameters(
			ConnectionSpec connection,
			TableSpec table,
			ColumnSpec column,
			AbstractCheckSpec check,
            TimeSeriesConfigurationSpec timeSeries,
            DataStreamMappingSpec dataStreams,
			AbstractSensorParametersSpec sensorParameters,
			ProviderDialectSettings dialectSettings) {
        this.connection = connection;
        this.table = table;
        this.column = column;
        this.check = check;
        this.timeSeries = timeSeries;
        this.dataStreams = dataStreams;
        this.sensorParameters = sensorParameters;
        this.dialectSettings = dialectSettings;
        this.connectionTimeZoneId = connection.getJavaTimeZoneId();
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
     * Returns the data quality check specification.
     * @return Data quality check specification.
     */
    public AbstractCheckSpec<?, ?> getCheck() {
        return check;
    }

    /**
     * Sets the data quality check specification.
     * @param check Check specification.
     */
    public void setCheck(AbstractCheckSpec<?, ?> check) {
        this.check = check;
    }

    /**
     * Returns the effective time series configuration.
     * @return Effective time series configuration.
     */
    public TimeSeriesConfigurationSpec getTimeSeries() {
        return timeSeries;
    }

    /**
     * Sets the effective time series configuration.
     * @param timeSeries Effective time series configuration.
     */
    public void setTimeSeries(TimeSeriesConfigurationSpec timeSeries) {
        this.timeSeries = timeSeries;
    }

    /**
     * Returns the effective data streams configuration.
     * @return Effective data streams configuration.
     */
    public DataStreamMappingSpec getDataStreams() {
        return dataStreams;
    }

    /**
     * Sets the effective data streams configuration.
     * @param dataStreams Effective data streams configuration.
     */
    public void setDataStreams(DataStreamMappingSpec dataStreams) {
        this.dataStreams = dataStreams;
    }

    /**
     * Returns the sensor parameters.
     * @return Sensor parameters.
     */
    public AbstractSensorParametersSpec getSensorParameters() {
        return sensorParameters;
    }

    /**
     * Sets the sensor parameters.
     * @param sensorParameters Sensor parameters.
     */
    public void setSensorParameters(AbstractSensorParametersSpec sensorParameters) {
        this.sensorParameters = sensorParameters;
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
     * Returns a UTC timestamp when the sensor execution started. It is the time when the sensor execution parameters were created
     * which is the earliest time that we can consider as the start of the sensor execution.
     * @return Sensor execution started timestamp.
     */
    public Instant getStartedAt() {
        return startedAt;
    }

    /**
     * Sets the start timestamp when the sensor execution started.
     * @param startedAt Started at timestamp.
     */
    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    /**
     * Java time zone for the connection. By default, it is a parsed time zone retrieved from the connection.
     * @return Java time zone of the connection.
     */
    public ZoneId getConnectionTimeZoneId() {
        return connectionTimeZoneId;
    }

    /**
     * Sets the java time zone of the connection.
     * @param connectionTimeZoneId Connection time zone.
     */
    public void setConnectionTimeZoneId(ZoneId connectionTimeZoneId) {
        this.connectionTimeZoneId = connectionTimeZoneId;
    }

    /**
     * Finds the effective time series configuration as it will be used by the sensor. The priority is: check/column/table/connection.
     * @return Effective time series configuration or a default time series (current time, day granularity).
     */
    @JsonIgnore
    @Deprecated
    public TimeSeriesConfigurationSpec getEffectiveTimeSeries() {
        if (this.timeSeries != null) {
            return this.timeSeries;
        }

        if (this.column != null && this.column.getTimeSeriesOverride() != null) {
            return this.column.getTimeSeriesOverride();
        }

        if (this.table != null && this.table.getTimeSeries() != null) {
            return this.table.getTimeSeries();
        }

        if (this.connection != null && this.connection.getDefaultTimeSeries() != null) {
            return this.connection.getDefaultTimeSeries();
        }

        return TimeSeriesConfigurationSpec.createDefault();
    }
}
