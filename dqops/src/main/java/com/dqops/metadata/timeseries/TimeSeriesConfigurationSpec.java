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
package com.dqops.metadata.timeseries;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.parquet.Strings;

import java.util.Objects;

/**
 * Time series configuration on a table or sensor level. Defines how to get a time series information.
 * A time series may be extracted from a timestamp column in the table or the time series may be extracted from the time of the execution.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class TimeSeriesConfigurationSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<TimeSeriesConfigurationSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Time series mode. 'current_time' - the time series is formed from the time of the sensor execution, the current result is assigned to the current time, all time series rules (average, standard deviations) need a database to load previous results. 'timestamp_column' - the data in the table is grouped by the timestamp column that is truncated to the beginning of the period (day), this mode is useful to analyze fact tables that are assigned to an event time and we can measure each period of data separately.")
    private TimeSeriesMode mode = TimeSeriesMode.current_time;

    @JsonPropertyDescription("Time gradient (year, quarter, month, week, day, hour). The current time (for the current_time mode) or the value of the timestamp_column is truncated to the beginning of the time gradient period (day, etc). When the time gradient is not provided, the data quality check readouts are not rounded to the time window.")
    private TimePeriodGradient timeGradient;

    @JsonPropertyDescription("The name of the timestamp column when the mode is 'timestamp_column'. It must be a column name that stores a date or datetime. It could be a name of the date column for date partitioned data or a modification or insert timestamp column (modified_at, inserted_at, etc.). Completeness sensors need the timestamp column to detect missing time periods.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String timestampColumn;

    /**
     * Creates a default time series configuration (current time, no date/time rounding).
     * @return Default time series configuration.
     */
    public static TimeSeriesConfigurationSpec createCurrentTimeMilliseconds() {
        return new TimeSeriesConfigurationSpec() {{
			setMode(TimeSeriesMode.current_time);
            setTimeGradient(TimePeriodGradient.millisecond);
        }};
    }

    /**
     * Returns the time series mode.
     * @return Time series mode.
     */
    public TimeSeriesMode getMode() {
        return mode;
    }

    /**
     * Sets the time series mode.
     * @param mode New time series mode.
     */
    public void setMode(TimeSeriesMode mode) {
		setDirtyIf(!Objects.equals(this.mode, mode));
        this.mode = mode;
    }

    /**
     * Returns the time gradient.
     * @return Time gradient.
     */
    public TimePeriodGradient getTimeGradient() {
        return timeGradient;
    }

    /**
     * Sets the time gradient.
     * @param timeGradient Time gradient.
     */
    public void setTimeGradient(TimePeriodGradient timeGradient) {
		setDirtyIf(!Objects.equals(this.timeGradient, timeGradient));
        this.timeGradient = timeGradient;
    }

    /**
     * Sets the column name that stores the timestamp used to calculate a time series.
     * @return Timestamp column name.
     */
    public String getTimestampColumn() {
        return timestampColumn;
    }

    /**
     * Sets the timestamp column name.
     * @param timestampColumn Timestamp column name.
     */
    public void setTimestampColumn(String timestampColumn) {
		setDirtyIf(!Objects.equals(this.timestampColumn, timestampColumn));
        this.timestampColumn = timestampColumn;
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
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public TimeSeriesConfigurationSpec deepClone() {
        TimeSeriesConfigurationSpec cloned = (TimeSeriesConfigurationSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a clone of the object, expanding variables in parameters.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Cloned and expanded instance of the object.
     */
    public TimeSeriesConfigurationSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        TimeSeriesConfigurationSpec cloned = this.deepClone();
        cloned.timestampColumn = secretValueProvider.expandValue(cloned.timestampColumn, lookupContext);
        return cloned;
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        return this.mode == null && this.timeGradient == null &&
                Strings.isNullOrEmpty(this.timestampColumn);
    }
}
