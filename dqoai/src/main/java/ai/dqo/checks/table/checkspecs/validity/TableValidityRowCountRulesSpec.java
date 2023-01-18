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
package ai.dqo.checks.table.checkspecs.validity;

import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.comparison.MaxValueRuleThresholdsSpec;
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
 * Table validity row count check rules.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated
public class TableValidityRowCountRulesSpec extends AbstractRuleSetSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableValidityRowCountRulesSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleSetSpec.FIELDS) {
        {
			put("min_row_count", o -> o.minRowCount);
            put("max_row_count", o -> o.maxRowCount);
			put("row_count_equals", o -> o.rowCountEquals);
        }
    };

    @JsonPropertyDescription("Minimum row counts at various alert severity levels (thresholds)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MinValueRuleThresholdsSpec minRowCount;

    @JsonPropertyDescription("Maximum row counts at various alert severity levels (thresholds)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private MaxValueRuleThresholdsSpec maxRowCount;

    @JsonPropertyDescription("Verifies that the row count equals a given number (within a delta)")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private ValueEqualsRuleThresholdsSpec rowCountEquals;

    /**
     * Minimum value thresholds.
     * @return Minimum value thresholds.
     */
    public MinValueRuleThresholdsSpec getMinRowCount() {
        return minRowCount;
    }

    /**
     * Sets the minimum value thresholds.
     * @param minRowCount Minimum value thresholds.
     */
    public void setMinRowCount(MinValueRuleThresholdsSpec minRowCount) {
		this.setDirtyIf(!Objects.equals(this.minRowCount, minRowCount));
        this.minRowCount = minRowCount;
		this.propagateHierarchyIdToField(minRowCount, "min_row_count");
    }

    /**
     * Maximum value thresholds.
     * @return Maximum value thresholds.
     */
    public MaxValueRuleThresholdsSpec getMaxRowCount() {
        return maxRowCount;
    }

    /**
     * Sets the maximum value thresholds.
     * @param maxRowCount Maximum value thresholds.
     */
    public void setMaxRowCount(MaxValueRuleThresholdsSpec maxRowCount) {
        this.setDirtyIf(!Objects.equals(this.maxRowCount, maxRowCount));
        this.maxRowCount = maxRowCount;
        this.propagateHierarchyIdToField(maxRowCount, "max_row_count");
    }

    /**
     * Returns the row count equals value.
     * @return Row count equals thresholds.
     */
    public ValueEqualsRuleThresholdsSpec getRowCountEquals() {
        return rowCountEquals;
    }

    /**
     * Sets the row count equals thresholds.
     * @param rowCountEquals Row count equals.
     */
    public void setRowCountEquals(ValueEqualsRuleThresholdsSpec rowCountEquals) {
		this.setDirtyIf(!Objects.equals(this.rowCountEquals, rowCountEquals));
        this.rowCountEquals = rowCountEquals;
		this.propagateHierarchyIdToField(rowCountEquals, "row_count_equals");
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
