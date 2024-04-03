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
 * Data quality rule that verifies if a data quality check percentage readout is between an accepted range of percentages.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BetweenPercentRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<BetweenPercentRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Minimum accepted percentage of rows passing the check (inclusive).")
    @SampleValues(values = "80.0")
    private Double minPercent = 100.0;

    @JsonPropertyDescription("Maximum accepted percentage of rows passing the check (inclusive).")
    @SampleValues(values = "90.0")
    private Double maxPercent;

    /**
     * Returns a minimum value for a data quality check readout, a minimum percentage.
     * @return Minimum value for a data quality check readout.
     */
    public Double getMinPercent() {
        return minPercent;
    }

    /**
     * Sets a minimum data quality check readout that is accepted, a minimum percentage.
     * @param minPercent Minimum value that is accepted.
     */
    public void setMinPercent(Double minPercent) {
		this.setDirtyIf(!Objects.equals(this.minPercent, minPercent));
        this.minPercent = minPercent;
    }

    /**
     * Returns a maximum value for a data quality check readout, a maximum percentage.
     * @return Maximum value for a data quality check readout.
     */
    public Double getMaxPercent() {
        return maxPercent;
    }

    /**
     * Sets a maximum data quality check readout that is accepted, a maximum percentage.
     * @param maxPercent Maximum value that is accepted.
     */
    public void setMaxPercent(Double maxPercent) {
        this.setDirtyIf(!Objects.equals(this.maxPercent, maxPercent));
        this.maxPercent = maxPercent;
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
        return "comparison/between_percent";
    }
}
