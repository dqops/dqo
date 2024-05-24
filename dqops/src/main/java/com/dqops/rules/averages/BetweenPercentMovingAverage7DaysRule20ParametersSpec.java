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
package com.dqops.rules.averages;

import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies if a data quality sensor readout value is not above X percent of the moving average within a time window.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BetweenPercentMovingAverage7DaysRule20ParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<BetweenPercentMovingAverage7DaysRule20ParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    /**
     * Default constructor.
     */
    public BetweenPercentMovingAverage7DaysRule20ParametersSpec() {
    }


    @JsonPropertyDescription("The maximum percentage (e.g., 3%) by which the current sensor readout can be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.")
    private Double maxPercentAbove = 20.0;

    @JsonPropertyDescription("The maximum percentage (e.g., 3%) by which the current sensor readout can be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readouts must exist to run the calculation.")
    private Double maxPercentBelow = 20.0;

    /**
     * Minimum percent value for a data quality check readout, for example a minimum row count.
     * @return A percent that is used to calculate lower limit.
     */
    public Double getMaxPercentBelow() {
        return maxPercentBelow;
    }

    /**
     * Changes the minimum value (threshold) for a data quality readout.
     * @param maxPercentBelow
     */
    public void setMaxPercentBelow(Double maxPercentBelow) {
        this.setDirtyIf(!Objects.equals(this.maxPercentBelow, maxPercentBelow));
        this.maxPercentBelow = maxPercentBelow;
    }

    /**
     * Maximum percent value for a data quality check readout, for example a minimum row count.
     * @return Maximum value for a data quality check readout.
     */
    public Double getMaxPercentAbove() {
        return maxPercentAbove;
    }

    /**
     * Changes the maximum value (threshold) for a data quality readout.
     * @param maxPercentAbove Maximum value.
     */
    public void setMaxPercentAbove(Double maxPercentAbove) {
        this.setDirtyIf(!Objects.equals(this.maxPercentAbove, maxPercentAbove));
        this.maxPercentAbove = maxPercentAbove;
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
     * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
     *
     * @return Rule definition name (python module name without .py extension).
     */
    @Override
    public String getRuleDefinitionName() {
        return "averages/between_percent_moving_average_7_days";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.maxPercentAbove != null) {
            this.maxPercentAbove *= 1.3;
        }

        if (this.maxPercentBelow != null) {
            this.maxPercentBelow *= 1.3;
        }
    }
}
