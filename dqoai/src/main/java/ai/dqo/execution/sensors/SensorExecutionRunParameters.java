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
import ai.dqo.checks.CheckType;
import ai.dqo.connectors.ProviderDialectSettings;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.execution.checks.EffectiveSensorRuleNames;
import ai.dqo.metadata.groupings.DataGroupingConfigurationSpec;
import ai.dqo.metadata.timeseries.TimePeriodGradient;
import ai.dqo.metadata.timeseries.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.statistics.AbstractStatisticsCollectorSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.time.Instant;

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
    private CheckType checkType;
    @JsonIgnore
    private AbstractCheckSpec<?,?,?,?> check;
    @JsonIgnore
    private AbstractStatisticsCollectorSpec<?> profiler;
    private EffectiveSensorRuleNames effectiveSensorRuleNames;
    private TimeSeriesConfigurationSpec timeSeries;
    private TimeWindowFilterParameters timeWindowFilter;
    private DataGroupingConfigurationSpec dataGroupings;
    private AbstractSensorParametersSpec sensorParameters;
    private ProviderDialectSettings dialectSettings;
    @JsonIgnore
    private Instant startedAt = Instant.now();
    @JsonIgnore
    private CheckSearchFilters checkSearchFilter;
    private String actualValueAlias = SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME;
    private String expectedValueAlias = SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME;

    /**
     * Creates a sensor run parameters object with all objects required to run a sensor.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Column specification.
     * @param check Check specification (when a quality check is executed).
     * @param profiler Profiler specification (when a profiler is executed).
     * @param effectiveSensorRuleNames A pair of effective sensor names and rule names. The sensor name and rule name are retrieved from the check definition (for custom checks) or from the sensor parameters and rule parameters for built-in checks.
     * @param checkType Check type (profiling, recurring, partitioned).
     * @param timeSeries Effective time series configuration.
     * @param timeWindowFilter Time window filter (optional), configures the absolute time range of data to analyze and/or the time window (recent days/months) for incremental partition checks.
     * @param dataGroupings Effective data groupings configuration.
     * @param sensorParameters Sensor parameters.
     * @param dialectSettings Dialect settings.
     * @param checkSearchFilter Check search filter to find this particular check.
     */
    public SensorExecutionRunParameters(
			ConnectionSpec connection,
			TableSpec table,
			ColumnSpec column,
			AbstractCheckSpec<?,?,?,?> check,
            AbstractStatisticsCollectorSpec<?> profiler,
            EffectiveSensorRuleNames effectiveSensorRuleNames,
            CheckType checkType,
            TimeSeriesConfigurationSpec timeSeries,
            TimeWindowFilterParameters timeWindowFilter,
            DataGroupingConfigurationSpec dataGroupings,
			AbstractSensorParametersSpec sensorParameters,
			ProviderDialectSettings dialectSettings,
            CheckSearchFilters checkSearchFilter) {
        this.connection = connection;
        this.table = table;
        this.column = column;
        this.check = check;
        this.profiler = profiler;
        this.effectiveSensorRuleNames = effectiveSensorRuleNames;
        this.checkType = checkType;
        this.timeSeries = timeSeries;
        this.timeWindowFilter = timeWindowFilter;
        this.dataGroupings = dataGroupings;
        this.sensorParameters = sensorParameters;
        this.dialectSettings = dialectSettings;
        this.checkSearchFilter = checkSearchFilter;
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
    public AbstractCheckSpec<?,?,?,?> getCheck() {
        return check;
    }

    /**
     * Sets the data quality check specification.
     * @param check Check specification.
     */
    public void setCheck(AbstractCheckSpec<?,?,?,?> check) {
        this.check = check;
    }

    /**
     * Returns the profiler instance that is executed.
     * @return Profiler instance.
     */
    public AbstractStatisticsCollectorSpec<?> getProfiler() {
        return profiler;
    }

    /**
     * Sets the profiler instance.
     * @param profiler Profiler instance.
     */
    public void setProfiler(AbstractStatisticsCollectorSpec<?> profiler) {
        this.profiler = profiler;
    }

    /**
     * Returns the effective sensor and rule names.
     * @return Effective sensor and rule names.
     */
    public EffectiveSensorRuleNames getEffectiveSensorRuleNames() {
        return effectiveSensorRuleNames;
    }

    /**
     * Sets the object that contains the effective sensor and rule names.
     * @param effectiveSensorRuleNames Effective sensor and rule names.
     */
    public void setEffectiveSensorRuleNames(EffectiveSensorRuleNames effectiveSensorRuleNames) {
        this.effectiveSensorRuleNames = effectiveSensorRuleNames;
    }

    /**
     * Returns the check type (profiling, recurring, partitioned).
     * @return Check type.
     */
    public CheckType getCheckType() {
        return checkType;
    }

    /**
     * Sets the check type (profiling, recurring, partitioned).
     * @param checkType Check type.
     */
    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
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
     * Returns the time window filter when a date/time range filters were applied.
     * @return Time window filter.
     */
    public TimeWindowFilterParameters getTimeWindowFilter() {
        return timeWindowFilter;
    }

    /**
     * Sets a time window filter, when the time window filter was applied.
     * @param timeWindowFilter Time window filter.
     */
    public void setTimeWindowFilter(TimeWindowFilterParameters timeWindowFilter) {
        this.timeWindowFilter = timeWindowFilter;
    }

    /**
     * Returns the effective data groupings configuration.
     * @return Effective data groupings configuration.
     */
    public DataGroupingConfigurationSpec getDataGroupings() {
        return dataGroupings;
    }

    /**
     * Sets the effective data groupings configuration.
     * @param dataGroupings Effective data groupings configuration.
     */
    public void setDataGroupings(DataGroupingConfigurationSpec dataGroupings) {
        this.dataGroupings = dataGroupings;
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
     * Returns a check search filter that will find this check again (to rerun it, or report it).
     * @return Check search filter to find this check.
     */
    public CheckSearchFilters getCheckSearchFilter() {
        return checkSearchFilter;
    }

    /**
     * Sets a check search filter that will find this check.
     * @param checkSearchFilter Check search filter.
     */
    public void setCheckSearchFilter(CheckSearchFilters checkSearchFilter) {
        this.checkSearchFilter = checkSearchFilter;
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
     * Returns the time series gradient that is used for time partitioning the analyzed table.
     * @return Time period gradient.
     */
    @JsonIgnore
    public TimePeriodGradient getTimePeriodGradient() {
        if (this.timeSeries == null) {
            return null;
        }

        return this.timeSeries.getTimeGradient();
    }
}
