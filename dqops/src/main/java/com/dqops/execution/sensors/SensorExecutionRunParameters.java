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
package com.dqops.execution.sensors;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.CheckType;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.execution.checks.EffectiveSensorRuleNames;
import com.dqops.execution.sqltemplates.rendering.ErrorSamplingRenderParameters;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpecProvider;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import org.apache.parquet.Strings;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    private TableComparisonConfigurationSpec tableComparisonConfiguration;
    private String referenceColumnName;
    @JsonIgnore
    private Instant startedAt = Instant.now();
    @JsonIgnore
    private CheckSearchFilters checkSearchFilter;
    private String actualValueAlias = SensorReadoutsColumnNames.ACTUAL_VALUE_COLUMN_NAME;
    private String expectedValueAlias = SensorReadoutsColumnNames.EXPECTED_VALUE_COLUMN_NAME;
    @JsonIgnore
    private boolean success;
    @JsonIgnore
    private Throwable sensorConfigurationException;
    private List<String> additionalFilters = new ArrayList<>();
    private int rowCountLimit = 1000;
    private boolean failOnSensorReadoutLimitExceeded = true;
    private ErrorSamplingRenderParameters errorSamplingRenderParameters;


    /**
     * Creates a sensor execution run parameters when the sensor configuration failed and was not successful.
     * @param check The check that failed to be configured for execution.
     * @param sensorConfigurationException Exception that describes why the sensor failed to be configured.
     */
    public SensorExecutionRunParameters(AbstractCheckSpec<?,?,?,?> check, Throwable sensorConfigurationException) {
        this.success = false;
        this.check = check;
        this.sensorConfigurationException = sensorConfigurationException;
    }

    /**
     * Creates a sensor run parameters object with all objects required to run a sensor.
     * @param connection Connection specification.
     * @param table Table specification.
     * @param column Column specification.
     * @param check Check specification (when a quality check is executed).
     * @param profiler Profiler specification (when a profiler is executed).
     * @param effectiveSensorRuleNames A pair of effective sensor names and rule names. The sensor name and rule name are retrieved from the check definition (for custom checks) or from the sensor parameters and rule parameters for built-in checks.
     * @param checkType Check type (profiling, monitoring, partitioned).
     * @param timeSeries Effective time series configuration.
     * @param timeWindowFilter Time window filter (optional), configures the absolute time range of data to analyze and/or the time window (recent days/months) for incremental partition checks.
     * @param dataGroupings Effective data groupings configuration.
     * @param tableComparisonConfiguration Optional table comparison configuration.
     * @param referenceColumnName Optional name of the reference column to which we are comparing.
     * @param sensorParameters Sensor parameters.
     * @param dialectSettings Dialect settings.
     * @param checkSearchFilter Check search filter to find this particular check.
     * @param rowCountLimit Sets the row count limit.
     * @param failOnSensorReadoutLimitExceeded Fail when the row count limit is exceeded.
     * @param errorSamplingRenderParameters Optional parameters for error sampling. When present (not null), an error sampling template is used to capture error samples.
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
            TableComparisonConfigurationSpec tableComparisonConfiguration,
            String referenceColumnName,
			AbstractSensorParametersSpec sensorParameters,
			ProviderDialectSettings dialectSettings,
            CheckSearchFilters checkSearchFilter,
            int rowCountLimit,
            boolean failOnSensorReadoutLimitExceeded,
            ErrorSamplingRenderParameters errorSamplingRenderParameters) {
        this.success = true;
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
        this.tableComparisonConfiguration = tableComparisonConfiguration;
        this.referenceColumnName = referenceColumnName;
        this.sensorParameters = sensorParameters;
        this.dialectSettings = dialectSettings;
        this.checkSearchFilter = checkSearchFilter;
        this.rowCountLimit = rowCountLimit;
        this.failOnSensorReadoutLimitExceeded = failOnSensorReadoutLimitExceeded;
        this.errorSamplingRenderParameters = errorSamplingRenderParameters;
    }

    /**
     * Returns true if the sensor parameter preparation was successful.
     * @return True when the sensor was prepared correctly, false when the table mis configuration caused some issues.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the flag if the sensor preparation was successful.
     * @param success Sensor was configured correctly.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Checks if the sensor run parameters preparation failed late enough to know the connection and table name,
     * in order to generate an entry for the error table.
     * @return True when the sensor readout normalization service will be able to generate the error.
     */
    @JsonIgnore
    public boolean hasEnoughInformationForReportingDetailedError() {
        return this.connection != null && this.table != null && this.sensorParameters != null &&
                (this.check != null || this.profiler != null);
    }

    /**
     * Returns an exception that was thrown when the sensor was prepared.
     * @return Sensor preparation exception.
     */
    public Throwable getSensorConfigurationException() {
        return sensorConfigurationException;
    }

    /**
     * Sets an exception that was raised when the sensor configuration was prepared.
     * @param sensorConfigurationException Sensor configuration exception.
     */
    public void setSensorConfigurationException(Throwable sensorConfigurationException) {
        this.sensorConfigurationException = sensorConfigurationException;
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
     * Returns the check type (profiling, monitoring, partitioned).
     * @return Check type.
     */
    public CheckType getCheckType() {
        return checkType;
    }

    /**
     * Sets the check type (profiling, monitoring, partitioned).
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
     * Returns the table comparison configuration, only for table comparison checks.
     * @return Table comparison configuration or null when it is not a table comparison check.
     */
    public TableComparisonConfigurationSpec getTableComparisonConfiguration() {
        return tableComparisonConfiguration;
    }

    /**
     * Sets a reference to the table comparison configuration.
     * @param tableComparisonConfiguration Table comparison configuration.
     */
    public void setTableComparisonConfiguration(TableComparisonConfigurationSpec tableComparisonConfiguration) {
        this.tableComparisonConfiguration = tableComparisonConfiguration;
    }

    /**
     * Returns the name of the reference column from the reference table (the source of truth) to which we are comparing the current column.
     * @return Reference column name.
     */
    public String getReferenceColumnName() {
        return referenceColumnName;
    }

    /**
     * Sets the name of the reference column from the reference table (the source of truth) to which we are comparing the current column.
     * @param referenceColumnName Reference column name.
     */
    public void setReferenceColumnName(String referenceColumnName) {
        this.referenceColumnName = referenceColumnName;
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
     * Returns a list of additional filters (SQL fragments) that will be added to the WHERE clause.
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
     * Returns the row count limit.
     * @return Row count limit.
     */
    public int getRowCountLimit() {
        return rowCountLimit;
    }

    /**
     * Sets the row count limit.
     * @param rowCountLimit Row count limit.
     */
    public void setRowCountLimit(int rowCountLimit) {
        this.rowCountLimit = rowCountLimit;
    }

    /**
     * Returns true if the sensor execution should fail if the data source returned too many results.
     * @return True - fail, false - truncate the results and process only up to the limit.
     */
    public boolean isFailOnSensorReadoutLimitExceeded() {
        return failOnSensorReadoutLimitExceeded;
    }

    /**
     * Sets the flag to fail the sensor execution when the result count limit was exceeded.
     * @param failOnSensorReadoutLimitExceeded Fail when result count exceeded.
     */
    public void setFailOnSensorReadoutLimitExceeded(boolean failOnSensorReadoutLimitExceeded) {
        this.failOnSensorReadoutLimitExceeded = failOnSensorReadoutLimitExceeded;
    }

    /**
     * Returns the configuration of error sampling. When this object is not null, error sampling is used instead of running the sensor.
     * @return Error sampling configuration.
     */
    public ErrorSamplingRenderParameters getErrorSamplingRenderParameters() {
        return errorSamplingRenderParameters;
    }

    /**
     * Sets an error sampling configuration.
     * @param errorSamplingRenderParameters Error sampling configuration, when not null, captures error samples.
     */
    public void setErrorSamplingRenderParameters(ErrorSamplingRenderParameters errorSamplingRenderParameters) {
        this.errorSamplingRenderParameters = errorSamplingRenderParameters;
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

    /**
     * Appends an extra additional filter.
     * @param filter Extra additional filter.
     */
    public void appendAdditionalFilter(String filter) {
        if (Strings.isNullOrEmpty(filter)) {
            return;
        }

        if (this.additionalFilters == null) {
            this.additionalFilters = new ArrayList<>();
        }
        this.additionalFilters.add(filter);
    }

    /**
     * Returns a string representation of the object.
     * The output describes the target connection, table, column, check, sensor.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.check != null) {
            stringBuilder.append("Check ");
            stringBuilder.append(this.check.getHierarchyId() != null ? this.check.getHierarchyId().toString() : "");
        } else if (this.profiler != null) {
            stringBuilder.append("Statistics collector ");
            stringBuilder.append(this.profiler.getHierarchyId() != null ? this.profiler.getHierarchyId().toString() : "");
        } else {
            stringBuilder.append("Sensor ");
        }

        if (this.connection != null) {
            stringBuilder.append(", on connection: ");
            stringBuilder.append(this.connection.getConnectionName());
        }

        if (this.table != null) {
            stringBuilder.append(", table: ");
            stringBuilder.append(this.table.getPhysicalTableName());
        }

        if (this.column != null) {
            stringBuilder.append(", column: ");
            stringBuilder.append(this.column.getColumnName());
        }

        // todo: more and better info

        if (this.connection != null && this.connection.getDuckdb() != null && this.connection.getDuckdb().getFilesFormatType() != null) {
            FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(this.connection.getDuckdb(), this.table);
            if (fileFormatSpec != null) {
                stringBuilder.append(", file format: ");
                stringBuilder.append(fileFormatSpec.toString());
            }
        }

        return stringBuilder.toString();
    }
}
