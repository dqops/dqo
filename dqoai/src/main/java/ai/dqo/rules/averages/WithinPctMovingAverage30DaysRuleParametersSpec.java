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
package ai.dqo.rules.averages;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.AbstractRuleParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies if a data quality sensor reading value is not above X percent of the moving average of a time window.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class WithinPctMovingAverage30DaysRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<WithinPctMovingAverage30DaysRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    /**
     * Default constructor.
     */
    public WithinPctMovingAverage30DaysRuleParametersSpec() {
        this.maxPercentAbove = null;
        this.maxPercentBelow = null;
    }


    @JsonPropertyDescription("Maximum percent (e.q. 3%) that the current sensor reading could be above a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readings must exist to run the calculation.")
    private Double maxPercentAbove;

    @JsonPropertyDescription("Maximum percent (e.q. 3%) that the current sensor reading could be below a moving average within the time window. Set the time window at the threshold level for all severity levels (low, medium, high) at once. The default is a 14 time periods (days, etc.) time window, but at least 7 readings must exist to run the calculation.")
    private Double maxPercentBelow;

    /**
     * Minimum percent value for a data quality check reading, for example a minimum row count.
     * @return A percent that is used to calculate lower limit.
     */
    public Double getMaxPercentBelow() {
        return maxPercentBelow;
    }

    /**
     * Changes the minimum value (threshold) for a data quality reading.
     * @param maxPercentBelow
     */
    public void setMaxPercentBelow(Double maxPercentBelow) {
        this.setDirtyIf(!Objects.equals(this.maxPercentBelow, maxPercentBelow));
        this.maxPercentBelow = maxPercentBelow;
    }

    /**
     * Maximum percent value for a data quality check reading, for example a minimum row count.
     * @return Maximum value for a data quality check reading.
     */
    public Double getMaxPercentAbove() {
        return maxPercentAbove;
    }

    /**
     * Changes the maximum value (threshold) for a data quality reading.
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
        return "averages/within_pct_moving_average_30_days";
    }
}
