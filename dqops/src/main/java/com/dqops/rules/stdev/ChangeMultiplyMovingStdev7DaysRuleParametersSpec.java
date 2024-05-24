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
package com.dqops.rules.stdev;

import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.fields.SampleValues;
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
 * Data quality rule that verifies if a data quality sensor readout value
 * doesn't excessively deviate from the moving average of increments on a time window.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ChangeMultiplyMovingStdev7DaysRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<ChangeMultiplyMovingStdev7DaysRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    @JsonPropertyDescription("How many multiples of the estimated standard deviation can the current" +
            " sensor readout be above the moving average within the time window? Set" +
            " the time window at the threshold level for all severity levels (warning, error," +
            " fatal) at once. The default is a time window of 7 periods (days, etc.)," +
            " but there must be at least 3 readouts to run the calculation.")
    @SampleValues(values = "1.5")
    private Double multiplyStdevAbove;

    @JsonPropertyDescription("How many multiples of the estimated standard deviation can the current" +
            " sensor readout be below the moving average within the time window? Set" +
            " the time window at the threshold level for all severity levels (warning, error," +
            " fatal) at once. The default is a time window of 7 periods (days, etc.)," +
            " but there must be at least 3 readouts to run the calculation.")
    @SampleValues(values = "2.5")
    private Double multiplyStdevBelow;

    /**
     * Multiplied factor used to calculate a multiplied stdev.
     * @return Multiple factor to calculate multiplied stdev.
     */
    public Double getMultiplyStdevAbove() {
        return multiplyStdevAbove;
    }

    /**
     *  Sets a multiple factor to calculate multiplied stdev.
     * @param multiplyStdevAbove Multiple factor.
     */
    public void setMultiplyStdevAbove(Double multiplyStdevAbove) {
        this.setDirtyIf(!Objects.equals(this.multiplyStdevAbove, multiplyStdevAbove));
        this.multiplyStdevAbove = multiplyStdevAbove;
    }

    /**
     * Multiplied factor used to calculate a multiplied stdev.
     * @return Multiple factor used to calculate a multiplied stdev.
     */
    public Double getMultiplyStdevBelow() {
        return multiplyStdevBelow;
    }

    /**
     * Sets multiple factor to calculate multiplied stdev.
     * @param multiplyStdevBelow Multiple factor.
     */
    public void setMultiplyStdevBelow(Double multiplyStdevBelow) {
        this.setDirtyIf(!Objects.equals(this.multiplyStdevBelow, multiplyStdevBelow));
        this.multiplyStdevBelow = multiplyStdevBelow;
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
        return "stdev/change_multiply_moving_stdev_7_days";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.multiplyStdevAbove != null) {
            this.multiplyStdevAbove = this.multiplyStdevAbove * 1.3;
        }

        if (this.multiplyStdevBelow != null) {
            this.multiplyStdevBelow = this.multiplyStdevBelow * 1.3;
        }
    }
}
