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
package com.dqops.rules.comparison;

import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies that a data quality check readout equals a given value. A margin of error may be configured.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class EqualsRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<EqualsRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Expected value for the actual_value returned by the sensor. The sensor value should equal expected_value +/- the error_margin.")
    @SampleValues(values = "10.0")
    @RequiredField
    private Double expectedValue;

    @JsonPropertyDescription("Error margin for comparison.")
    @SampleValues(values = "0.01")
    private Double errorMargin;

    /**
     * Creates the default object that expects 0.
     */
    public EqualsRuleParametersSpec() {
    }

    /**
     * Creates an equals rule parameter given an expected value.
     * @param expectedValue Expected value.
     */
    public EqualsRuleParametersSpec(Double expectedValue) {
        this.expectedValue = expectedValue;
    }

    /**
     * Creates a value equals rule given the value and error margin.
     * @param expectedValue Expected value.
     * @param errorMargin Error margin.
     */
    public EqualsRuleParametersSpec(Double expectedValue, Double errorMargin) {
        this.expectedValue = expectedValue;
        this.errorMargin = errorMargin;
    }

    /**
     * Returns the expected value for the sensor readout.
     * @return Expected value.
     */
    public Double getExpectedValue() {
        return expectedValue;
    }

    /**
     * Sets the expected value.
     * @param expectedValue New expected value.
     */
    public void setExpectedValue(Double expectedValue) {
        this.setDirtyIf(!Objects.equals(this.expectedValue,expectedValue));
        this.expectedValue = expectedValue;
    }

    /**
     * Returns a fixed error margin for an accepted value. A data quality readout value is accepted when it
     * is in the range (expectedValue - errorValue) <= quality check readout value <= (expectedValue + errorValue)
     * @return Error value.
     */
    public Double getErrorMargin() {
        return errorMargin;
    }

    /**
     * Sets an error margin.
     * @param errorMargin New error margin.
     */
    public void setErrorMargin(Double errorMargin) {
        this.setDirtyIf(!Objects.equals(this.errorMargin,errorMargin));
        this.errorMargin = errorMargin;
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
        return "comparison/equals";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.errorMargin == null) {
            this.expectedValue = checkResultsSingleCheck.getActualValueColumn().median();
            return;
        }

        if (this.errorMargin <= 0.0) {
            this.errorMargin = (checkResultsSingleCheck.getActualValueColumn().max() - checkResultsSingleCheck.getActualValueColumn().min()) / 2.0;
            return;
        }

        this.errorMargin = this.errorMargin * 1.3;
    }
}
