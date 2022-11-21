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
package ai.dqo.checks;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.utils.datetime.LocalDateTimePeriodUtility;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Base class for a data quality check. A check is a pair of a sensor (that reads a value by querying the data) and a rule that validates the value returned by the sensor.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated
public abstract class AbstractCheckDeprecatedSpec extends AbstractSpec implements Cloneable {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractCheckDeprecatedSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("time_series_override", o -> o.timeSeriesOverride);
            put("data_streams_override", o -> o.dataStreamsOverride);
            put("schedule_override", o -> o.scheduleOverride);
            put("comments", o -> o.comments);
        }
    };

    @JsonPropertyDescription("Time series source configuration for a sensor query. When a time series configuration is assigned at a sensor level, it overrides any time series settings from the connection, table or column levels. Time series configuration chooses the source for the time series. Time series of data quality sensor readouts may be calculated from a timestamp column or a current time may be used. Also the time gradient (day, week) may be configured to analyse the data behavior at a correct scale.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    @Deprecated
    private TimeSeriesConfigurationSpec timeSeriesOverride;

    @JsonPropertyDescription("Data stream configuration for a sensor query. When a data stream configuration is assigned at a sensor level, it overrides any data stream settings from the connection, table or column levels. Dimensions are configured in two cases: (1) a static dimension is assigned to a table, when the data is partitioned at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a 'country' column used for partitioning.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    @Deprecated
    private DataStreamMappingSpec dataStreamsOverride;

    @JsonPropertyDescription("Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.")
    @ToString.Exclude
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private RecurringScheduleSpec scheduleOverride;

    @JsonPropertyDescription("Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private CommentsListSpec comments;


    /**
     * Returns the time series configuration for this sensor.
     * @return Time series configuration.
     */
    @Deprecated
    public TimeSeriesConfigurationSpec getTimeSeriesOverride() {
        return timeSeriesOverride;
    }

    /**
     * Sets a new time series configuration for this sensor.
     * @param timeSeriesOverride New time series configuration.
     */
    @Deprecated
    public void setTimeSeriesOverride(TimeSeriesConfigurationSpec timeSeriesOverride) {
        setDirtyIf(!Objects.equals(this.timeSeriesOverride, timeSeriesOverride));
        this.timeSeriesOverride = timeSeriesOverride;
        propagateHierarchyIdToField(timeSeriesOverride, "time_series_override");
    }

    /**
     * Returns the data stream configuration for the sensor.
     * @return Data stream configuration.
     */
    @Deprecated
    public DataStreamMappingSpec getDataStreamsOverride() {
        return dataStreamsOverride;
    }

    /**
     * Returns the data streams configuration for the sensor.
     * @param dataStreamsOverride Data streams configuration.
     */
    @Deprecated
    public void setDataStreamsOverride(DataStreamMappingSpec dataStreamsOverride) {
        setDirtyIf(!Objects.equals(this.dataStreamsOverride, dataStreamsOverride));
        this.dataStreamsOverride = dataStreamsOverride;
        propagateHierarchyIdToField(dataStreamsOverride, "data_streams_override");
    }

    /**
     * Returns the schedule configuration for running the checks automatically.
     * @return Schedule configuration.
     */
    public RecurringScheduleSpec getScheduleOverride() {
        return scheduleOverride;
    }

    /**
     * Stores a new schedule configuration.
     * @param scheduleOverride New schedule configuration.
     */
    public void setScheduleOverride(RecurringScheduleSpec scheduleOverride) {
        setDirtyIf(!Objects.equals(this.scheduleOverride, scheduleOverride));
        this.scheduleOverride = scheduleOverride;
        propagateHierarchyIdToField(scheduleOverride, "schedule_override");
    }

    /**
     * Returns a collection of comments for this connection.
     * @return List of comments (or null).
     */
    public CommentsListSpec getComments() {
        return comments;
    }

    /**
     * Sets a list of comments for this connection.
     * @param comments Comments list.
     */
    public void setComments(CommentsListSpec comments) {
        setDirtyIf(!Objects.equals(this.comments, comments));
        this.comments = comments;
        propagateHierarchyIdToField(comments, "comments");
    }

    /**
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     * @return Result value returned by an "accept" method of the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Returns the sensor parameters spec object that identifies the sensor definition to use and contains parameters.
     * @return Sensor parameters.
     */
    @JsonIgnore
    public abstract AbstractSensorParametersSpec getSensorParameters();

    /**
     * Returns a rule set for this check.
     * @return Rule set.
     */
    @JsonIgnore
    @Deprecated
    public abstract AbstractRuleSetSpec getRuleSet();

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public AbstractCheckDeprecatedSpec clone() {
        try {
            AbstractCheckDeprecatedSpec cloned = (AbstractCheckDeprecatedSpec)super.clone();
            if (cloned.dataStreamsOverride != null) {
                cloned.dataStreamsOverride = cloned.dataStreamsOverride.clone();
            }
            if (cloned.timeSeriesOverride != null) {
                cloned.timeSeriesOverride = cloned.timeSeriesOverride.clone();
            }
            if (cloned.scheduleOverride != null) {
                cloned.scheduleOverride = cloned.scheduleOverride.clone();
            }
            if (cloned.comments != null) {
                cloned.comments = cloned.comments.clone();
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object.");
        }
    }

    /**
     * Creates a cloned and trimmed version of the object. A trimmed and cloned copy is passed to the sensor.
     * All configurable variables that may use a secret value or environment variable expansion in the form ${ENV_VAR} are also expanded.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public AbstractCheckDeprecatedSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            AbstractCheckDeprecatedSpec cloned = (AbstractCheckDeprecatedSpec)super.clone();
            if (cloned.dataStreamsOverride != null) {
                cloned.dataStreamsOverride = dataStreamsOverride.expandAndTrim(secretValueProvider);
            }
            if (cloned.timeSeriesOverride != null) {
                cloned.timeSeriesOverride = timeSeriesOverride.expandAndTrim(secretValueProvider);
            }
            cloned.scheduleOverride = null;
            cloned.comments = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object.");
        }
    }

    /**
     * Reviews time windows for enabled rules that require historic data. Calculates the date before the <code>minTimePeriod</code>
     * that must be loaded to satisfy all the rules.
     * @param timeSeriesGradient Effective time series gradient that will be used for the calculation.
     * @param minTimePeriod Reference timestamp of the earliest sensor reading that will be evaluated by rules.
     * @return <code>minTimePeriod</code> when no time window is required or a date that is earlier to fill the time window with historic sensor readouts.
     */
    @Deprecated() // the time window will not be taken from the rule, but from the definition of the rule
    public LocalDateTime findEarliestRequiredHistoricReadoutDate(TimeSeriesGradient timeSeriesGradient, LocalDateTime minTimePeriod) {
        LocalDateTime minRequiredDateTime = minTimePeriod;
        List<AbstractRuleThresholdsSpec<?>> enabledRules = getRuleSet().getEnabledRules();

        for (AbstractRuleThresholdsSpec<?> ruleThresholdsSpec : enabledRules) {
            RuleTimeWindowSettingsSpec timeWindow = ruleThresholdsSpec.getTimeWindow();
            if (timeWindow == null) {
                continue;
            }

            LocalDateTime earliestRequiredReadout = LocalDateTimePeriodUtility.calculateLocalDateTimeMinusTimePeriods(
                    minTimePeriod, timeWindow.getPredictionTimeWindow(), timeSeriesGradient);
            if (earliestRequiredReadout.isBefore(minRequiredDateTime)) {
                minRequiredDateTime = earliestRequiredReadout;
            }
        }

        return minRequiredDateTime;
    }
}
