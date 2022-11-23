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
package ai.dqo.checks.column.checkpoints.strings;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.strings.ColumnMaxStringLengthBelowCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringLengthAboveCheckSpec;
import ai.dqo.checks.column.strings.ColumnStringEmptyCountCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsMonthlyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsMonthlyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_max_string_length_below", o -> o.monthlyCheckpointMaxStringLengthBelow);
            put("monthly_checkpoint_min_string_length_above", o -> o.monthlyCheckpointMinStringLengthAbove);
            put("monthly_checkpoint_string_empty_count", o -> o.monthlyCheckpointStringEmptyCount);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringLengthBelowCheckSpec monthlyCheckpointMaxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringLengthAboveCheckSpec monthlyCheckpointMinStringLengthAbove;

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringEmptyCountCheckSpec monthlyCheckpointStringEmptyCount;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnMaxStringLengthBelowCheckSpec getMonthlyCheckpointMaxStringLengthBelow() {
        return monthlyCheckpointMaxStringLengthBelow;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param monthlyCheckpointMaxStringLengthBelow Maximum string length below check.
     */
    public void setMonthlyCheckpointMaxStringLengthBelow(ColumnMaxStringLengthBelowCheckSpec monthlyCheckpointMaxStringLengthBelow) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxStringLengthBelow, monthlyCheckpointMaxStringLengthBelow));
        this.monthlyCheckpointMaxStringLengthBelow = monthlyCheckpointMaxStringLengthBelow;
        propagateHierarchyIdToField(monthlyCheckpointMaxStringLengthBelow, "monthly_checkpoint_max_string_length_below");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnMinStringLengthAboveCheckSpec getMonthlyCheckpointMinStringLengthAbove() {
        return monthlyCheckpointMinStringLengthAbove;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param monthlyCheckpointMinStringLengthAbove Minimum string length below check.
     */
    public void setMonthlyCheckpointMinStringLengthAbove(ColumnMinStringLengthAboveCheckSpec monthlyCheckpointMinStringLengthAbove) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinStringLengthAbove, monthlyCheckpointMinStringLengthAbove));
        this.monthlyCheckpointMinStringLengthAbove = monthlyCheckpointMinStringLengthAbove;
        propagateHierarchyIdToField(monthlyCheckpointMinStringLengthAbove, "monthly_checkpoint_max_string_length_below");
    }

    /**
     * Returns string empty count check.
     * @return String empty count check.
     */
    public ColumnStringEmptyCountCheckSpec getMonthlyCheckpointStringEmptyCount() {
        return monthlyCheckpointStringEmptyCount;
    }

    /**
     * Sets a new definition of a string empty count check.
     * @param monthlyCheckpointStringEmptyCount String empty count check.
     */
    public void setMonthlyCheckpointStringEmptyCount(ColumnStringEmptyCountCheckSpec monthlyCheckpointStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringEmptyCount, monthlyCheckpointStringEmptyCount));
        this.monthlyCheckpointStringEmptyCount = monthlyCheckpointStringEmptyCount;
        propagateHierarchyIdToField(monthlyCheckpointStringEmptyCount, "monthly_checkpoint_string_count");
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
