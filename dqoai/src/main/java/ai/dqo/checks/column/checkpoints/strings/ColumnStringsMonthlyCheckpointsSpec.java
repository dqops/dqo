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
import ai.dqo.checks.column.checks.nulls.ColumnMaxNullsCountCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringLengthBelowCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringLengthAboveCheckSpec;
import ai.dqo.checks.column.strings.ColumnMeanStringLengthBetweenCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringNullPlaceholderCountCheckSpec;
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
            put("monthly_checkpoint_mean_string_length_between", o -> o.monthlyCheckpointMeanStringLengthBetween);
            put("monthly_checkpoint_max_string_null_placeholder_count", o -> o.monthlyCheckpointMaxStringNullPlaceholderCount);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringLengthBelowCheckSpec monthlyCheckpointMaxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringLengthAboveCheckSpec monthlyCheckpointMinStringLengthAbove;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMeanStringLengthBetweenCheckSpec monthlyCheckpointMeanStringLengthBetween;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringNullPlaceholderCountCheckSpec monthlyCheckpointMaxStringNullPlaceholderCount;

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
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnMeanStringLengthBetweenCheckSpec getMonthlyCheckpointMeanStringLengthBetween() {
        return monthlyCheckpointMeanStringLengthBetween;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param monthlyCheckpointMeanStringLengthBetween Mean string length between check.
     */
    public void setMonthlyCheckpointMeanStringLengthBetween(ColumnMeanStringLengthBetweenCheckSpec monthlyCheckpointMeanStringLengthBetween) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMeanStringLengthBetween, monthlyCheckpointMeanStringLengthBetween));
        this.monthlyCheckpointMeanStringLengthBetween = monthlyCheckpointMeanStringLengthBetween;
        propagateHierarchyIdToField(monthlyCheckpointMeanStringLengthBetween, "monthly_checkpoint_mean_string_length_between");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnMaxStringNullPlaceholderCountCheckSpec getMonthlyCheckpointMaxStringNullPlaceholderCount() {
        return monthlyCheckpointMaxStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyCheckpointMaxStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setMonthlyCheckpointMaxStringNullPlaceholderCount(ColumnMaxStringNullPlaceholderCountCheckSpec monthlyCheckpointMaxStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxStringNullPlaceholderCount, monthlyCheckpointMaxStringNullPlaceholderCount));
        this.monthlyCheckpointMaxStringNullPlaceholderCount = monthlyCheckpointMaxStringNullPlaceholderCount;
        propagateHierarchyIdToField(monthlyCheckpointMaxStringNullPlaceholderCount, "monthly_checkpoint_max_string_null_placeholder_count");
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
