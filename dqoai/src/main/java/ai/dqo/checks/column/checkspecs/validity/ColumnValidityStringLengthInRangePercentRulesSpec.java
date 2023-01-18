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
package ai.dqo.checks.column.checkspecs.validity;

import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.comparison.MinValueRuleThresholdsSpec;
import ai.dqo.rules.comparison.ValueEqualsRuleThresholdsSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column string length in range percent rules (a list of supported rules).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated
public class ColumnValidityStringLengthInRangePercentRulesSpec extends AbstractRuleSetSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnValidityStringLengthInRangePercentRulesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleSetSpec.FIELDS) {
        {
			put("min_count", o -> o.minCount);
			put("count_equals", o -> o.countEquals);
        }
    };

    @JsonPropertyDescription("Minimum string length in range percent at various alert severity levels (thresholds)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MinValueRuleThresholdsSpec minCount;

    @JsonPropertyDescription("Verifies that the string length in range percent equals a given number (within a delta)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ValueEqualsRuleThresholdsSpec countEquals;

    /**
     * Minimum value thresholds.
     * @return Minimum value thresholds.
     */
    public MinValueRuleThresholdsSpec getMinCount() {
        return minCount;
    }

    /**
     * Sets the minimum value thresholds.
     * @param minCount Minimum value thresholds.
     */
    public void setMinCount(MinValueRuleThresholdsSpec minCount) {
		this.setDirtyIf(!Objects.equals(this.minCount, minCount));
        this.minCount = minCount;
		this.propagateHierarchyIdToField(minCount, "min_count");
    }

    /**
     * Returns the row count equals value.
     * @return Row count equals thresholds.
     */
    public ValueEqualsRuleThresholdsSpec getCountEquals() {
        return countEquals;
    }

    /**
     * Sets the count equals thresholds.
     * @param countEquals Count equals.
     */
    public void setCountEquals(ValueEqualsRuleThresholdsSpec countEquals) {
		this.setDirtyIf(!Objects.equals(this.countEquals, countEquals));
        this.countEquals = countEquals;
		this.propagateHierarchyIdToField(countEquals, "count_equals");
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
}
