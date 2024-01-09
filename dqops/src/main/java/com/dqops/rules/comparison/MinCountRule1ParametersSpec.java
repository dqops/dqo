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
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies if a data quality check readout is greater or equal a minimum value. The default value is 1 row, to detect if a table is not empty.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class MinCountRule1ParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<MinCountRule1ParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Minimum accepted value for the actual_value returned by the sensor (inclusive).")
    private Long minCount = 1L;

    /**
     * Default constructor, the minimum accepted value is 0.
     */
    public MinCountRule1ParametersSpec() {
    }

    /**
     * Creates a rule with a given value.
     * @param minCount Minimum accepted value.
     */
    public MinCountRule1ParametersSpec(Long minCount) {
        this.minCount = minCount;
    }

    /**
     * Creates a rule with a given value.
     * @param minCount Minimum accepted value.
     */
    public MinCountRule1ParametersSpec(int minCount) {
        this.minCount = (long)minCount;
    }

    /**
     * Minimum value for a data quality check readout, for example a minimum row count.
     * @return Minimum value for a data quality check readout.
     */
    public Long getMinCount() {
        return minCount;
    }

    /**
     * Changes the minimum value (threshold) for a data quality readout.
     * @param minCount Minimum value.
     */
    public void setMinCount(Long minCount) {
        this.setDirtyIf(!Objects.equals(this.minCount, minCount));
        this.minCount = minCount;
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
        return "comparison/min_count";
    }

    public static class MinCountRule1ParametersSpecSampleFactory implements SampleValueFactory<MinCountRule1ParametersSpec> {
        @Override
        public MinCountRule1ParametersSpec createSample() {
            return new MinCountRule1ParametersSpec();
        }
    }
}