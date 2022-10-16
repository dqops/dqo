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
package ai.dqo.metadata.groupings;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
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
public class TimeSeriesConfigurationSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<TimeSeriesConfigurationSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Time series mode. 'current_time' - the time series is formed from the time of the sensor execution, the current result is assigned to the current time, all time series rules (average, standard deviations) need a database to load previous results. 'timestamp_column' - the data in the table is grouped by the timestamp column that is truncated to the beginning of the period (day), this mode is useful to analyze fact tables that are assigned to an event time and we can measure each period of data separately.")
    private TimeSeriesMode mode = TimeSeriesMode.current_time;

    @JsonPropertyDescription("Time gradient (year, quarter, month, week, day, hour). The current time (for the current_time mode) or the value of the timestamp_column is truncated to the beginning of the time gradient period (day, etc). The default time gradient is 'daily'.")
    private TimeSeriesGradient timeGradient = TimeSeriesGradient.DAY;

    @JsonPropertyDescription("The name of the timestamp column when the mode is 'timestamp_column'. It must be a column name that stores a date or datetime. It could be a name of the date column for date partitioned data or a modification or insert timestamp column (modified_at, inserted_at, etc.). Completeness sensors need the timestamp column to detect missing time periods.")
    private String timestampColumn;

    @JsonPropertyDescription("The time window for the earliest captured data. When the 'mode' is timestamp_column, a WHERE condition is added to the query to retrieve only the given number of time periods. When the time_gradient is daily, the WHERE clause would be equivalent to: WHERE {timestamp_column} >= now() - time_window_periods * '1 day'. As a result, only the last time_window_periods days (or any other periods of selected time gradients) will be analyzed. This parameter has no meaning when the mode is current_time.")
    private Integer timeWindowPeriods;

    @JsonPropertyDescription("Similar to the time_window_periods, but used when the --incremental parameter is used to capture sensor values. Usually when the database is monitored frequently, it could be 2-3 time periods.")
    private Integer incrementalTimeWindowPeriods;

    @JsonPropertyDescription("Number of most recent time gradient periods that should be excluded. This parameter is used when the mode is timestamp_column and we cannot analyze the data for the current time period (that contains 'now', which is today for a daily time gradient) because not all data have been received and the data quality score like the row count may still change. Set the value of this parameter to '1' to exclude today's result for a daily gradient. Use '2' to wait one full day until the data is analyzed.")
    private Integer excludedRecentPeriods;

    /**
     * Creates a default time series configuration (current time, daily granularity, no filters).
     * @return Default time series configuration.
     */
    public static TimeSeriesConfigurationSpec createDefault() {
        return new TimeSeriesConfigurationSpec() {{
			setMode(TimeSeriesMode.current_time);
			setTimeGradient(TimeSeriesGradient.DAY);
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
    public TimeSeriesGradient getTimeGradient() {
        return timeGradient;
    }

    /**
     * Sets the time gradient.
     * @param timeGradient Time gradient.
     */
    public void setTimeGradient(TimeSeriesGradient timeGradient) {
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
     * Returns the default limit of time window periods that are analyzed.
     * @return Time window periods to analyze.
     */
    public Integer getTimeWindowPeriods() {
        return timeWindowPeriods;
    }

    /**
     * Sets the default time window periods to analyze.
     * @param timeWindowPeriods Time window periods to analyze.
     */
    public void setTimeWindowPeriods(Integer timeWindowPeriods) {
		setDirtyIf(!Objects.equals(this.timeWindowPeriods, timeWindowPeriods));
        this.timeWindowPeriods = timeWindowPeriods;
    }

    /**
     * Sets the number of time window periods to analyze in the --incremental mode.
     * @return Time window periods.
     */
    public Integer getIncrementalTimeWindowPeriods() {
        return incrementalTimeWindowPeriods;
    }

    /**
     * Sets the number of time periods to analyze in --incremental mode.
     * @param incrementalTimeWindowPeriods Incremental time window periods to analyze.
     */
    public void setIncrementalTimeWindowPeriods(Integer incrementalTimeWindowPeriods) {
		setDirtyIf(!Objects.equals(this.incrementalTimeWindowPeriods, incrementalTimeWindowPeriods));
        this.incrementalTimeWindowPeriods = incrementalTimeWindowPeriods;
    }

    /**
     * Sets the number of recent (before the time of 'now') time periods to exclude, a WHERE timestamp_column < now - time_gradient * excluded_current_periods is used in the query.
     * @return Number of excluded recent periods.
     */
    public Integer getExcludedRecentPeriods() {
        return excludedRecentPeriods;
    }

    /**
     * Sets the number of recent (newest) time periods to exclude.
     * @param excludedRecentPeriods Number of excluded recent time periods.
     */
    public void setExcludedRecentPeriods(Integer excludedRecentPeriods) {
		setDirtyIf(!Objects.equals(this.excludedRecentPeriods, excludedRecentPeriods));
        this.excludedRecentPeriods = excludedRecentPeriods;
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
    public TimeSeriesConfigurationSpec clone() {
        try {
            TimeSeriesConfigurationSpec cloned = (TimeSeriesConfigurationSpec) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }

    /**
     * Creates a clone of the object, expanding variables in parameters.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded instance of the object.
     */
    public TimeSeriesConfigurationSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            TimeSeriesConfigurationSpec cloned = (TimeSeriesConfigurationSpec) super.clone();
            cloned.timestampColumn = secretValueProvider.expandValue(cloned.timestampColumn);
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        if (!super.isDefault()) {
            return false;
        }
        return this.mode == null && this.timeGradient == null &&
                Strings.isNullOrEmpty(this.timestampColumn) &&
				this.timeWindowPeriods == null &&
				this.incrementalTimeWindowPeriods == null &&
				this.excludedRecentPeriods == null;
    }
}
