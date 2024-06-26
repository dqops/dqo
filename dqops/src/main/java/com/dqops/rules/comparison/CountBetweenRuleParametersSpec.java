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
 * Data quality rule that verifies if a data quality check readout is between begin and end values, defined as min_count and max_count.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class CountBetweenRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<CountBetweenRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Minimum accepted count (inclusive), leave empty when the limit is not assigned.")
    @SampleValues(values = "10")
    @RequiredField
    private Long minCount;

    @JsonPropertyDescription("Maximum accepted count (inclusive), leave empty when the limit is not assigned.")
    @SampleValues(values = "20")
    @RequiredField
    private Long maxCount;

    /**
     * Returns a minimum value for a data quality check readout, for example a minimum row count.
     * @return Minimum value for a data quality check readout.
     */
    public Long getMinCount() {
        return minCount;
    }

    /**
     * Sets a minimum data quality check readout that is accepted, for example a minimum row count.
     * @param minCount Minimum value that is accepted.
     */
    public void setMinCount(Long minCount) {
		this.setDirtyIf(!Objects.equals(this.minCount, minCount));
        this.minCount = minCount;
    }

    /**
     * Returns a maximum value for a data quality check readout, for example a maximum row count.
     * @return Maximum value for a data quality check readout.
     */
    public Long getMaxCount() {
        return maxCount;
    }

    /**
     * Sets a maximum data quality check readout that is accepted, for example a maximum row count.
     * @param maxCount Maximum value that is accepted.
     */
    public void setMaxCount(Long maxCount) {
        this.setDirtyIf(!Objects.equals(this.maxCount, maxCount));
        this.maxCount = maxCount;
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
        return "comparison/count_between";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.minCount != null) {
            double minActualValue = checkResultsSingleCheck.getActualValueColumn().min();
            if (minActualValue < this.minCount) {
                this.minCount = (long)minActualValue;
            }
        }

        if (this.maxCount != null) {
            double maxActualValue = checkResultsSingleCheck.getActualValueColumn().max();
            if (maxActualValue > this.maxCount) {
                this.maxCount = (long)maxActualValue;
            }
        }
    }
}
