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
import ai.dqo.checks.column.checks.nulls.ColumnMaxNullsCountCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringLengthBelowCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringLengthAboveCheckSpec;
import ai.dqo.checks.column.strings.ColumnMeanStringLengthBetweenCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringEmptyPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringWhitespaceCountCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringWhitespacePercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringValidDatesPercentCheckSpec;
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
            put("daily_partition_mean_string_length_between", o -> o.dailyPartitionMeanStringLengthBetween);
            put("daily_partition_max_string_empty_percent", o -> o.dailyPartitionMaxStringEmptyPercent);
            put("daily_partition_max_string_whitespace_count", o -> o.dailyPartitionMaxStringWhitespaceCount);
            put("daily_partition_max_string_whitespace_percent", o -> o.dailyPartitionMaxStringWhitespacePercent);
            put("daily_partition_min_string_valid_dates_percent", o -> o.dailyPartitionMinStringValidDatesPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringLengthBelowCheckSpec dailyPartitionMaxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinStringLengthAboveCheckSpec dailyPartitionMinStringLengthAbove;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMeanStringLengthBetweenCheckSpec dailyPartitionMeanStringLengthBetween;

    @JsonPropertyDescription("Verifies that the percentage of string in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringEmptyPercentCheckSpec dailyPartitionMaxStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringWhitespaceCountCheckSpec dailyPartitionMaxStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringWhitespacePercentCheckSpec dailyPartitionMaxStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringValidDatesPercentCheckSpec dailyPartitionMinStringValidDatesPercent;

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
     * Returns a mean string length between  check.
     * @return Mean string length between  check.
     */
    public ColumnMeanStringLengthBetweenCheckSpec getDailyPartitionMeanStringLengthBetween() {
        return dailyPartitionMeanStringLengthBetween;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param dailyPartitionMeanStringLengthBetween Mean string length between check.
     */
    public void setDailyPartitionMeanStringLengthBetween(ColumnMeanStringLengthBetweenCheckSpec dailyPartitionMeanStringLengthBetween) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanStringLengthBetween, dailyPartitionMeanStringLengthBetween));
        this.dailyPartitionMeanStringLengthBetween = dailyPartitionMeanStringLengthBetween;
        propagateHierarchyIdToField(dailyPartitionMeanStringLengthBetween, "daily_partition_mean_string_length_between");
    }

    /**
     * Returns a maximum empty string percentage check.
     * @return Maximum empty string percentage check.
     */
    public ColumnMaxStringEmptyPercentCheckSpec getDailyPartitionMaxStringEmptyPercent() {
        return dailyPartitionMaxStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum empty string percentage check.
     * @param dailyPartitionMaxStringEmptyPercent Maximum empty string percentage check.
     */
    public void setDailyPartitionMaxStringEmptyPercent(ColumnMaxStringEmptyPercentCheckSpec dailyPartitionMaxStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxStringEmptyPercent, dailyPartitionMaxStringEmptyPercent));
        this.dailyPartitionMaxStringEmptyPercent = dailyPartitionMaxStringEmptyPercent;
        propagateHierarchyIdToField(dailyPartitionMaxStringEmptyPercent, "daily_partition_max_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnMaxStringWhitespaceCountCheckSpec getDailyPartitionMaxStringWhitespaceCount() {
        return dailyPartitionMaxStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param dailyPartitionMaxStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setDailyPartitionMaxStringWhitespaceCount(ColumnMaxStringWhitespaceCountCheckSpec dailyPartitionMaxStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxStringWhitespaceCount, dailyPartitionMaxStringWhitespaceCount));
        this.dailyPartitionMaxStringWhitespaceCount = dailyPartitionMaxStringWhitespaceCount;
        propagateHierarchyIdToField(dailyPartitionMaxStringWhitespaceCount, "daily_partition_max_string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnMaxStringWhitespacePercentCheckSpec getDailyPartitionMaxStringWhitespacePercent() {
        return dailyPartitionMaxStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param dailyPartitionMaxStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setDailyPartitionMaxStringWhitespacePercent(ColumnMaxStringWhitespacePercentCheckSpec dailyPartitionMaxStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxStringWhitespacePercent, dailyPartitionMaxStringWhitespacePercent));
        this.dailyPartitionMaxStringWhitespacePercent = dailyPartitionMaxStringWhitespacePercent;
        propagateHierarchyIdToField(dailyPartitionMaxStringWhitespacePercent, "daily_partition_max_string_whitespace_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnMinStringValidDatesPercentCheckSpec getDailyPartitionMinStringValidDatesPercent() {
        return dailyPartitionMinStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param dailyPartitionMinStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setDailyPartitionMinStringValidDatesPercent(ColumnMinStringValidDatesPercentCheckSpec dailyPartitionMinStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinStringValidDatesPercent, dailyPartitionMinStringValidDatesPercent));
        this.dailyPartitionMinStringValidDatesPercent = dailyPartitionMinStringValidDatesPercent;
        propagateHierarchyIdToField(dailyPartitionMinStringValidDatesPercent, "daily_partition_min_string_valid_dates_percent");
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
