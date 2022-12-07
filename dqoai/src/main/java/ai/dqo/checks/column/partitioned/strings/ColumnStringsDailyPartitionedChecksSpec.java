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
import ai.dqo.checks.column.strings.ColumnMeanStringLengthBetweenCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringEmptyPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMeanStringLengthBetweenCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringEmptyPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringEmptyCountCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringWhitespaceCountCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringWhitespacePercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringValidDatesPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringNullPlaceholderCountCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringNullPlaceholderPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringBooleanPlaceholderPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringParsableToIntegerPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringSurroundedByWhitespaceCountCheckSpec;
import ai.dqo.checks.column.strings.ColumnMaxStringSurroundedByWhitespacePercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringParsableToFloatPercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringValidUsaPhonePercentCheckSpec;
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
            put("daily_partition_max_string_empty_count", o -> o.dailyPartitionMaxStringEmptyCount);
            put("daily_partition_max_string_whitespace_count", o -> o.dailyPartitionMaxStringWhitespaceCount);
            put("daily_partition_max_string_whitespace_percent", o -> o.dailyPartitionMaxStringWhitespacePercent);
            put("daily_partition_min_string_valid_dates_percent", o -> o.dailyPartitionMinStringValidDatesPercent);
            put("daily_partition_max_string_null_placeholder_count", o -> o.dailyPartitionMaxStringNullPlaceholderCount);
            put("daily_partition_max_string_null_placeholder_percent", o -> o.dailyPartitionMaxStringNullPlaceholderPercent);
            put("daily_partition_min_string_boolean_placeholder_percent", o -> o.dailyPartitionMinStringBooleanPlaceholderPercent);
            put("daily_partition_min_string_parsable_to_integer_percent", o -> o.dailyPartitionMinStringParsableToIntegerPercent);
            put("daily_partition_max_string_surrounded_by_whitespace_count", o -> o.dailyPartitionMaxStringSurroundedByWhitespaceCount);
            put("daily_partition_max_string_surrounded_by_whitespace_percent", o -> o.dailyPartitionMaxStringSurroundedByWhitespacePercent);
            put("daily_partition_min_string_parsable_to_float_percent", o -> o.dailyPartitionMinStringParsableToFloatPercent);
            put("daily_partition_min_string_valid_usa_phone_percent", o -> o.dailyPartitionMinStringValidUsaPhonePercent);

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

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringEmptyCountCheckSpec dailyPartitionMaxStringEmptyCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringWhitespaceCountCheckSpec dailyPartitionMaxStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringWhitespacePercentCheckSpec dailyPartitionMaxStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringValidDatesPercentCheckSpec dailyPartitionMinStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringNullPlaceholderCountCheckSpec dailyPartitionMaxStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringNullPlaceholderPercentCheckSpec dailyPartitionMaxStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinStringBooleanPlaceholderPercentCheckSpec dailyPartitionMinStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringSurroundedByWhitespaceCountCheckSpec dailyPartitionMaxStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinStringParsableToIntegerPercentCheckSpec dailyPartitionMinStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxStringSurroundedByWhitespacePercentCheckSpec dailyPartitionMaxStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinStringParsableToFloatPercentCheckSpec dailyPartitionMinStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinStringValidUsaPhonePercentCheckSpec dailyPartitionMinStringValidUsaPhonePercent;

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
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnMaxStringEmptyCountCheckSpec getDailyPartitionMaxStringEmptyCount() {
        return dailyPartitionMaxStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyPartitionMaxStringEmptyCount Max string empty count check.
     */
    public void setDailyPartitionMaxStringEmptyCount(ColumnMaxStringEmptyCountCheckSpec dailyPartitionMaxStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxStringEmptyCount, dailyPartitionMaxStringEmptyCount));
        this.dailyPartitionMaxStringEmptyCount = dailyPartitionMaxStringEmptyCount;
        propagateHierarchyIdToField(dailyPartitionMaxStringEmptyCount, "daily_partition_max_string_empty_count");
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
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnMaxStringNullPlaceholderCountCheckSpec getDailyPartitionMaxStringNullPlaceholderCount() {
        return dailyPartitionMaxStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param dailyPartitionMaxStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setDailyPartitionMaxStringNullPlaceholderCount(ColumnMaxStringNullPlaceholderCountCheckSpec dailyPartitionMaxStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxStringNullPlaceholderCount, dailyPartitionMaxStringNullPlaceholderCount));
        this.dailyPartitionMaxStringNullPlaceholderCount = dailyPartitionMaxStringNullPlaceholderCount;
        propagateHierarchyIdToField(dailyPartitionMaxStringNullPlaceholderCount, "daily_partition_max_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnMaxStringNullPlaceholderPercentCheckSpec getDailyPartitionMaxStringNullPlaceholderPercent() {
        return dailyPartitionMaxStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param dailyPartitionMaxStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setDailyPartitionMaxStringNullPlaceholderPercent(ColumnMaxStringNullPlaceholderPercentCheckSpec dailyPartitionMaxStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxStringNullPlaceholderPercent, dailyPartitionMaxStringNullPlaceholderPercent));
        this.dailyPartitionMaxStringNullPlaceholderPercent = dailyPartitionMaxStringNullPlaceholderPercent;
        propagateHierarchyIdToField(dailyPartitionMaxStringNullPlaceholderPercent, "daily_partition_max_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnMinStringBooleanPlaceholderPercentCheckSpec getDailyPartitionMinStringBooleanPlaceholderPercent() {
        return dailyPartitionMinStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param dailyPartitionMinStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setDailyPartitionMinStringBooleanPlaceholderPercent(ColumnMinStringBooleanPlaceholderPercentCheckSpec dailyPartitionMinStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinStringBooleanPlaceholderPercent, dailyPartitionMinStringBooleanPlaceholderPercent));
        this.dailyPartitionMinStringBooleanPlaceholderPercent = dailyPartitionMinStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(dailyPartitionMinStringBooleanPlaceholderPercent, "daily_partition_min_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent  check.
     */
    public ColumnMinStringParsableToIntegerPercentCheckSpec getDailyPartitionMinStringParsableToIntegerPercent() {
        return dailyPartitionMinStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param dailyPartitionMinStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setDailyPartitionMinStringParsableToIntegerPercent(ColumnMinStringParsableToIntegerPercentCheckSpec dailyPartitionMinStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinStringParsableToIntegerPercent, dailyPartitionMinStringParsableToIntegerPercent));
        this.dailyPartitionMinStringParsableToIntegerPercent = dailyPartitionMinStringParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyPartitionMinStringParsableToIntegerPercent, "daily_partition_min_string_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnMaxStringSurroundedByWhitespaceCountCheckSpec getDailyPartitionMaxStringSurroundedByWhitespaceCount() {
        return dailyPartitionMaxStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param dailyPartitionMaxStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setDailyPartitionMaxStringSurroundedByWhitespaceCount(ColumnMaxStringSurroundedByWhitespaceCountCheckSpec dailyPartitionMaxStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxStringSurroundedByWhitespaceCount, dailyPartitionMaxStringSurroundedByWhitespaceCount));
        this.dailyPartitionMaxStringSurroundedByWhitespaceCount = dailyPartitionMaxStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(dailyPartitionMaxStringSurroundedByWhitespaceCount, "daily_partition_max_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     *
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnMaxStringSurroundedByWhitespacePercentCheckSpec getDailyPartitionMaxStringSurroundedByWhitespacePercent() {
        return dailyPartitionMaxStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     *
     * @param dailyPartitionMaxStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setDailyPartitionMaxStringSurroundedByWhitespacePercent(ColumnMaxStringSurroundedByWhitespacePercentCheckSpec dailyPartitionMaxStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxStringSurroundedByWhitespacePercent, dailyPartitionMaxStringSurroundedByWhitespacePercent));
        this.dailyPartitionMaxStringSurroundedByWhitespacePercent = dailyPartitionMaxStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyPartitionMaxStringSurroundedByWhitespacePercent, "daily_partition_max_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent  check.
     */
    public ColumnMinStringParsableToFloatPercentCheckSpec getDailyPartitionMinStringParsableToFloatPercent() {
        return dailyPartitionMinStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param dailyPartitionMinStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setDailyPartitionMinStringParsableToFloatPercent(ColumnMinStringParsableToFloatPercentCheckSpec dailyPartitionMinStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinStringParsableToFloatPercent, dailyPartitionMinStringParsableToFloatPercent));
        this.dailyPartitionMinStringParsableToFloatPercent = dailyPartitionMinStringParsableToFloatPercent;
        propagateHierarchyIdToField(dailyPartitionMinStringParsableToFloatPercent, "daily_partition_min_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent  check.
     */
    public ColumnMinStringValidUsaPhonePercentCheckSpec getDailyPartitionMinStringValidUsaPhonePercent() {
        return dailyPartitionMinStringValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum string valid USA phone percent check.
     * @param dailyPartitionMinStringValidUsaPhonePercent Minimum string valid USA phone percent check.
     */
    public void setDailyPartitionMinStringValidUsaPhonePercent(ColumnMinStringValidUsaPhonePercentCheckSpec dailyPartitionMinStringValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinStringValidUsaPhonePercent, dailyPartitionMinStringValidUsaPhonePercent));
        this.dailyPartitionMinStringValidUsaPhonePercent = dailyPartitionMinStringValidUsaPhonePercent;
        propagateHierarchyIdToField(dailyPartitionMinStringValidUsaPhonePercent, "daily_partition_min_string_valid_usa_phone_percent");
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
