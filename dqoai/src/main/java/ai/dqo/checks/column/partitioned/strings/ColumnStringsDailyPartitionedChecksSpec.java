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
package ai.dqo.checks.column.partitioned.strings;

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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_max_string_length_below", o -> o.dailyPartitionMaxStringLengthBelow);
            put("daily_partition_min_string_length_above", o -> o.dailyPartitionMinStringLengthAbove);
            put("daily_partition_string_empty_count", o -> o.dailyPartitionStringEmptyCount);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringLengthBelowCheckSpec dailyPartitionMaxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinStringLengthAboveCheckSpec dailyPartitionMinStringLengthAbove;

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringEmptyCountCheckSpec dailyPartitionStringEmptyCount;

    /**
     * Returns a maximum string length below  check.
     * @return Maximum string length below  check.
     */
    public ColumnMaxStringLengthBelowCheckSpec getDailyPartitionMaxStringLengthBelow() {
        return dailyPartitionMaxStringLengthBelow;
    }

    /**
     * Sets a new definition of a maximum string length below  check.
     * @param dailyPartitionMaxStringLengthBelow Maximum string length below  check.
     */
    public void setDailyPartitionMaxStringLengthBelow(ColumnMaxStringLengthBelowCheckSpec dailyPartitionMaxStringLengthBelow) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxStringLengthBelow, dailyPartitionMaxStringLengthBelow));
        this.dailyPartitionMaxStringLengthBelow = dailyPartitionMaxStringLengthBelow;
        propagateHierarchyIdToField(dailyPartitionMaxStringLengthBelow, "daily_partition_max_string_length_below");
    }

    /**
     * Returns a minimum string length below  check.
     * @return Minimum string length below  check.
     */
    public ColumnMinStringLengthAboveCheckSpec getDailyPartitionMinStringLengthAbove() {
        return dailyPartitionMinStringLengthAbove;
    }

    /**
     * Sets a new definition of a minimum string length below  check.
     * @param dailyPartitionMinStringLengthAbove Minimum string length above check.
     */
    public void setDailyPartitionMinStringLengthAbove(ColumnMinStringLengthAboveCheckSpec dailyPartitionMinStringLengthAbove) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinStringLengthAbove, dailyPartitionMinStringLengthAbove));
        this.dailyPartitionMinStringLengthAbove = dailyPartitionMinStringLengthAbove;
        propagateHierarchyIdToField(dailyPartitionMaxStringLengthBelow, "daily_partition_min_string_length_above");
    }

    /**
     * Returns a string empty count check.
     * @return String empty count check.
     */
    public ColumnStringEmptyCountCheckSpec getDailyPartitionStringEmptyCount() {
        return dailyPartitionStringEmptyCount;
    }

    /**
     * Sets a new definition of a string empty count check.
     * @param dailyPartitionStringEmptyCount String empty count check.
     */
    public void setDailyPartitionStringEmptyCount(ColumnStringEmptyCountCheckSpec dailyPartitionStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringEmptyCount, dailyPartitionStringEmptyCount));
        this.dailyPartitionStringEmptyCount = dailyPartitionStringEmptyCount;
        propagateHierarchyIdToField(dailyPartitionStringEmptyCount, "daily_partition_string_empty_count");
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
