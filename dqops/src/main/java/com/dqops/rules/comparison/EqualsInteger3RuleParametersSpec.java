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
 * Data quality rule that verifies that a data quality check readout equals a given integer value, with an expected value preconfigured as 3.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class EqualsInteger3RuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<EqualsInteger3RuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Expected value for the actual_value returned by the sensor. It must be an integer value. The default value is 3.")
    @RequiredField
    private Long expectedValue = 3L;

    /**
     * Creates the default object that expects 3.
     */
    public EqualsInteger3RuleParametersSpec() {
    }

    /**
     * Creates an equals rule parameter given an expected value.
     * @param expectedValue Expected value.
     */
    public EqualsInteger3RuleParametersSpec(Long expectedValue) {
        this.expectedValue = expectedValue;
    }

    /**
     * Returns the expected value for the sensor readout.
     * @return Expected value.
     */
    public Long getExpectedValue() {
        return expectedValue;
    }

    /**
     * Sets the expected value.
     * @param expectedValue New expected value.
     */
    public void setExpectedValue(Long expectedValue) {
        this.setDirtyIf(!Objects.equals(this.expectedValue,expectedValue));
        this.expectedValue = expectedValue;
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
        return "comparison/equals_integer";
    }
}
