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
 * Data quality rule that verifies the results of the data quality checks that count the number of values
 * present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value)
 * to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than
 * the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list)
 * and the actual number of values found in the column that match the list.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class MaxMissingRule0WarningParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<MaxMissingRule0WarningParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The maximum number of values from the expected_values list that were not found in the column (inclusive).")
    @RequiredField
    private Long maxMissing = 0L;

    /**
     * Default constructor, the minimum accepted value is 0.
     */
    public MaxMissingRule0WarningParametersSpec() {
    }

    /**
     * Creates a rule with a given value.
     * @param maxMissing Maximum accepted value.
     */
    public MaxMissingRule0WarningParametersSpec(Long maxMissing) {
        this.maxMissing = maxMissing;
    }

    /**
     * Creates a rule with a given value.
     * @param maxMissing Maximum accepted value.
     */
    public MaxMissingRule0WarningParametersSpec(int maxMissing) {
        this.maxMissing = (long)maxMissing;
    }

    /**
     * Returns the maximum number of values that are missing.
     * @return Maximum number of values that are missing.
     */
    public Long getMaxMissing() {
        return maxMissing;
    }

    /**
     * Sets the maximum number of values that are missing.
     * @param maxMissing Maximum number of values that are missing.
     */
    public void setMaxMissing(Long maxMissing) {
        this.setDirtyIf(!Objects.equals(this.maxMissing, maxMissing));
        this.maxMissing = maxMissing;
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
        return "comparison/max_missing";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.maxMissing == null) {
            return;
        }

        this.maxMissing = this.maxMissing + 1 + (long)(this.maxMissing * 0.25);
    }
}