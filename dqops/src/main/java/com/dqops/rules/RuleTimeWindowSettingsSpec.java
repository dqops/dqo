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
package com.dqops.rules;

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Rule historic data configuration. Specifies the number of past values for rules that are analyzing historic data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class RuleTimeWindowSettingsSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<RuleTimeWindowSettingsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Number of historic time periods to look back for results. Returns results from previous time periods before the sensor readout timestamp to be used in a rule. Time periods are used in rules that need historic data to calculate an average to detect anomalies. e.g. when the sensor is configured to use a 'day' time period, the rule will receive results from the time_periods number of days before the time period in the sensor readout. The default is 14 (days).")
    private int predictionTimeWindow = 14;

    @JsonPropertyDescription("Minimum number of past time periods with a sensor readout that must be present in the data in order to call the rule. The rule is not called and the sensor readout is discarded as not analyzable (not enough historic data to perform prediction) when the number of past sensor readouts is not met. The default is 7.")
    private int minPeriodsWithReadouts = 7;

    @JsonPropertyDescription("Time period grouping for collecting previous data quality sensor results for the data quality rules that use historic data for prediction. For example, when the default time period grouping 'day' is used, DQOps will find the most recent data quality sensor readout for each day and pass an array of most recent days per day in an array of historic sensor readout data points to a data quality rule for prediction.")
    private HistoricDataPointsGrouping historicDataPointGrouping = HistoricDataPointsGrouping.day;

    // TODO: what to do with missing values? we can have a parameter that missing values are: skipped (array date density would be wrong), nulls or interpolated

    /**
     * Gets the number of past periods that are passed to the rule.
     * @return Time periods.
     */
    public int getPredictionTimeWindow() {
        return predictionTimeWindow;
    }

    /**
     * Sets the number of past time periods required by the rule.
     * @param predictionTimeWindow Past time periods.
     */
    public void setPredictionTimeWindow(int predictionTimeWindow) {
		this.setDirtyIf(this.predictionTimeWindow != predictionTimeWindow);
        this.predictionTimeWindow = predictionTimeWindow;
    }

    /**
     * Returns the minimum number of historic sensor readouts that are required to call the rule.
     * @return Min time periods with readouts.
     */
    public int getMinPeriodsWithReadouts() {
        return minPeriodsWithReadouts;
    }

    /**
     * Sets the minimum number of past time periods with readouts.
     * @param minPeriodsWithReadouts Min periods with readouts.
     */
    public void setMinPeriodsWithReadouts(int minPeriodsWithReadouts) {
		this.setDirtyIf(this.minPeriodsWithReadouts != minPeriodsWithReadouts);
        this.minPeriodsWithReadouts = minPeriodsWithReadouts;
    }

    /**
     * Returns the time period grouping for collecting historic data points.
     * @return Time period grouping.
     */
    public HistoricDataPointsGrouping getHistoricDataPointGrouping() {
        return historicDataPointGrouping;
    }

    /**
     * Sets the time period grouping for collecting historic data points.
     * @param historicDataPointGrouping Time period grouping.
     */
    public void setHistoricDataPointGrouping(HistoricDataPointsGrouping historicDataPointGrouping) {
        this.setDirtyIf(!Objects.equals(this.historicDataPointGrouping, historicDataPointGrouping));
        this.historicDataPointGrouping = historicDataPointGrouping;
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
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    public boolean isDefault() {
        return false; // always render when not null
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public RuleTimeWindowSettingsSpec deepClone() {
       RuleTimeWindowSettingsSpec cloned = (RuleTimeWindowSettingsSpec)super.deepClone();
       return cloned;
    }
}
