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
package ai.dqo.checks.table.checkspecs.consistency;

import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.comparison.MaxValueRuleThresholdsSpec;
import ai.dqo.rules.comparison.MinValueRuleThresholdsSpec;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Table consistency row count check rules.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated
public class TableConsistencyRowCountRulesSpec extends AbstractRuleSetSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableConsistencyRowCountRulesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleSetSpec.FIELDS) {
        {
			put("min_count", o -> o.minCount);
            put("max_count", o -> o.maxCount);
        }
    };

    @JsonPropertyDescription("Minimum row counts at various alert severity levels (thresholds)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MinValueRuleThresholdsSpec minCount;

    @JsonPropertyDescription("Maximum row counts at various alert severity levels (thresholds)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxValueRuleThresholdsSpec maxCount;

    /**
     * Minimum value thresholds.
     * @return Minimum value thresholds.
     */
    public MinValueRuleThresholdsSpec getMinCount() {
        return minCount;
    }

    /**
     * Maximum value thresholds.
     * @return Maximum value thresholds.
     */
    public MaxValueRuleThresholdsSpec getMaxCount() {
        return maxCount;
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
     * Sets the maximum value thresholds.
     * @param maxCount Maximum value thresholds.
     */
    public void setMaxCount(MaxValueRuleThresholdsSpec maxCount) {
        this.setDirtyIf(!Objects.equals(this.maxCount, maxCount));
        this.maxCount = maxCount;
        this.propagateHierarchyIdToField(maxCount, "max_count");
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
