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
import ai.dqo.checks.column.strings.ColumnMeanStringLengthBetweenCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringEmptyPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringEmptyCountCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_max_string_length_below", o -> o.dailyCheckpointMaxStringLengthBelow);
            put("daily_checkpoint_min_string_length_above", o -> o.dailyCheckpointMinStringLengthAbove);
            put("daily_checkpoint_mean_string_length_between", o -> o.dailyCheckpointMeanStringLengthBetween);
            put("daily_checkpoint_max_string_empty_percent", o -> o.dailyCheckpointMaxStringEmptyPercent);
            put("daily_checkpoint_max_string_empty_count", o -> o.dailyCheckpointMaxStringEmptyCount);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringLengthBelowCheckSpec dailyCheckpointMaxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringLengthAboveCheckSpec dailyCheckpointMinStringLengthAbove;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMeanStringLengthBetweenCheckSpec dailyCheckpointMeanStringLengthBetween;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringEmptyPercentCheckSpec dailyCheckpointMaxStringEmptyPercent;


    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringEmptyCountCheckSpec dailyCheckpointMaxStringEmptyCount;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnMaxStringLengthBelowCheckSpec getDailyCheckpointMaxStringLengthBelow() {
        return dailyCheckpointMaxStringLengthBelow;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param dailyCheckpointMaxStringLengthBelow Maximum string length below check.
     */
    public void setDailyCheckpointMaxStringLengthBelow(ColumnMaxStringLengthBelowCheckSpec dailyCheckpointMaxStringLengthBelow) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringLengthBelow, dailyCheckpointMaxStringLengthBelow));
        this.dailyCheckpointMaxStringLengthBelow = dailyCheckpointMaxStringLengthBelow;
        propagateHierarchyIdToField(dailyCheckpointMaxStringLengthBelow, "daily_checkpoint_max_string_length_below");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length below check.
     */
    public ColumnMinStringLengthAboveCheckSpec getDailyCheckpointMinStringLengthAbove() {
        return dailyCheckpointMinStringLengthAbove;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param dailyCheckpointMinStringLengthAbove Minimum string length above check.
     */
    public void setDailyCheckpointMinStringLengthAbove(ColumnMinStringLengthAboveCheckSpec dailyCheckpointMinStringLengthAbove) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringLengthAbove, dailyCheckpointMinStringLengthAbove));
        this.dailyCheckpointMinStringLengthAbove = dailyCheckpointMinStringLengthAbove;
        propagateHierarchyIdToField(dailyCheckpointMinStringLengthAbove, "daily_checkpoint_min_string_length_above");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnMeanStringLengthBetweenCheckSpec getDailyCheckpointMeanStringLengthBetween() {
        return dailyCheckpointMeanStringLengthBetween;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param dailyCheckpointMeanStringLengthBetween Mean string length between check.
     */
    public void setDailyCheckpointMeanStringLengthBetween(ColumnMeanStringLengthBetweenCheckSpec dailyCheckpointMeanStringLengthBetween) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMeanStringLengthBetween, dailyCheckpointMeanStringLengthBetween));
        this.dailyCheckpointMeanStringLengthBetween = dailyCheckpointMeanStringLengthBetween;
        propagateHierarchyIdToField(dailyCheckpointMeanStringLengthBetween, "daily_checkpoint_mean_string_length_between");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnMaxStringEmptyPercentCheckSpec getDailyCheckpointMaxStringEmptyPercent() {
        return dailyCheckpointMaxStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param dailyCheckpointMaxStringEmptyPercent Maximum string empty percent check.
     */
    public void setDailyCheckpointMaxStringEmptyPercent(ColumnMaxStringEmptyPercentCheckSpec dailyCheckpointMaxStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringEmptyPercent, dailyCheckpointMaxStringEmptyPercent));
        this.dailyCheckpointMaxStringEmptyPercent = dailyCheckpointMaxStringEmptyPercent;
        propagateHierarchyIdToField(dailyCheckpointMaxStringEmptyPercent, "daily_checkpoint_max_string_empty_percent");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnMaxStringEmptyCountCheckSpec getDailyCheckpointMaxStringEmptyCount() {
        return dailyCheckpointMaxStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyCheckpointMaxStringEmptyCount Max string empty count check.
     */
    public void setDailyCheckpointMaxStringEmptyCount(ColumnMaxStringEmptyCountCheckSpec dailyCheckpointMaxStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringEmptyCount, dailyCheckpointMaxStringEmptyCount));
        this.dailyCheckpointMaxStringEmptyCount = dailyCheckpointMaxStringEmptyCount;
        propagateHierarchyIdToField(dailyCheckpointMaxStringEmptyCount, "daily_checkpoint_max_string_empty_count");
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
