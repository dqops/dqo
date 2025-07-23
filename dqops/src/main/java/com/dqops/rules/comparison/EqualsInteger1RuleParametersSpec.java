/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.comparison;

import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
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
 * Data quality rule that verifies that a data quality check readout equals a given integer value, with an expected value preconfigured as 1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class EqualsInteger1RuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<EqualsInteger1RuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Expected value for the actual_value returned by the sensor. It must be an integer value. The default value is 1.")
    @RequiredField
    private Long expectedValue = 1L;

    /**
     * Creates the default object that expects 1.
     */
    public EqualsInteger1RuleParametersSpec() {
    }

    /**
     * Creates an equals rule parameter given an expected value.
     * @param expectedValue Expected value.
     */
    public EqualsInteger1RuleParametersSpec(Long expectedValue) {
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

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        this.expectedValue = (long)checkResultsSingleCheck.getActualValueColumn().max();
    }
}
