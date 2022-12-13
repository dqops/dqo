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
import ai.dqo.checks.column.strings.ColumnMinStringValidUsaZipcodePercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinStringValidUsaPhonePercentCheckSpec;
import ai.dqo.checks.column.strings.ColumnMinValidCountryCodePercentCheckSpec;
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
            put("monthly_checkpoint_max_string_empty_percent", o -> o.monthlyCheckpointMaxStringEmptyPercent);
            put("monthly_checkpoint_max_string_empty_count", o -> o.monthlyCheckpointMaxStringEmptyCount);
            put("monthly_checkpoint_max_string_whitespace_count", o -> o.monthlyCheckpointMaxStringWhitespaceCount);
            put("monthly_checkpoint_max_string_whitespace_percent", o -> o.monthlyCheckpointMaxStringWhitespacePercent);
            put("monthly_checkpoint_min_string_valid_dates_percent", o -> o.monthlyCheckpointMinStringValidDatesPercent);
            put("monthly_checkpoint_max_string_null_placeholder_count", o -> o.monthlyCheckpointMaxStringNullPlaceholderCount);
            put("monthly_checkpoint_max_string_null_placeholder_percent", o -> o.monthlyCheckpointMaxStringNullPlaceholderPercent);
            put("monthly_checkpoint_min_string_boolean_placeholder_percent", o -> o.monthlyCheckpointMinStringBooleanPlaceholderPercent);
            put("monthly_checkpoint_max_string_surrounded_by_whitespace_count", o -> o.monthlyCheckpointMaxStringSurroundedByWhitespaceCount);
            put("monthly_checkpoint_min_string_parsable_to_integer_percent", o -> o.monthlyCheckpointMinStringParsableToIntegerPercent);
            put("monthly_checkpoint_max_string_surrounded_by_whitespace_percent", o -> o.monthlyCheckpointMaxStringSurroundedByWhitespacePercent);
            put("monthly_checkpoint_min_string_parsable_to_float_percent", o -> o.monthlyCheckpointMinStringParsableToFloatPercent);
            put("monthly_checkpoint_min_string_valid_usa_zipcode_percent", o -> o.monthlyCheckpointMinStringValidUsaZipcodePercent);
            put("monthly_checkpoint_min_string_valid_usa_phone_percent", o -> o.monthlyCheckpointMinStringValidUsaPhonePercent);
            put("monthly_checkpoint_min_string_valid_country_code_percent", o -> o.monthlyCheckpointMinStringValidCountryCodePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringLengthBelowCheckSpec monthlyCheckpointMaxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringLengthAboveCheckSpec monthlyCheckpointMinStringLengthAbove;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMeanStringLengthBetweenCheckSpec monthlyCheckpointMeanStringLengthBetween;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringEmptyPercentCheckSpec monthlyCheckpointMaxStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringValidDatesPercentCheckSpec monthlyCheckpointMinStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringEmptyCountCheckSpec monthlyCheckpointMaxStringEmptyCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringWhitespaceCountCheckSpec monthlyCheckpointMaxStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringWhitespacePercentCheckSpec monthlyCheckpointMaxStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringNullPlaceholderCountCheckSpec monthlyCheckpointMaxStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringNullPlaceholderPercentCheckSpec monthlyCheckpointMaxStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringBooleanPlaceholderPercentCheckSpec monthlyCheckpointMinStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringParsableToIntegerPercentCheckSpec monthlyCheckpointMinStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringSurroundedByWhitespaceCountCheckSpec monthlyCheckpointMaxStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxStringSurroundedByWhitespacePercentCheckSpec monthlyCheckpointMaxStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringParsableToFloatPercentCheckSpec monthlyCheckpointMinStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringValidUsaZipcodePercentCheckSpec monthlyCheckpointMinStringValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringValidUsaPhonePercentCheckSpec monthlyCheckpointMinStringValidUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinValidCountryCodePercentCheckSpec monthlyCheckpointMinStringValidCountryCodePercent;

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
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnMaxStringEmptyPercentCheckSpec getMonthlyCheckpointMaxStringEmptyPercent() {
        return monthlyCheckpointMaxStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param monthlyCheckpointMaxStringEmptyPercent Maximum string empty percent check.
     */
    public void setMonthlyCheckpointMaxStringEmptyPercent(ColumnMaxStringEmptyPercentCheckSpec monthlyCheckpointMaxStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxStringEmptyPercent, monthlyCheckpointMaxStringEmptyPercent));
        this.monthlyCheckpointMaxStringEmptyPercent = monthlyCheckpointMaxStringEmptyPercent;
        propagateHierarchyIdToField(monthlyCheckpointMaxStringEmptyPercent, "monthly_checkpoint_max_string_empty_percent");
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
     * Returns max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnMaxStringEmptyCountCheckSpec getMonthlyCheckpointMaxStringEmptyCount() {
        return monthlyCheckpointMaxStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyCheckpointMaxStringEmptyCount Max string empty count check.
     */
    public void setMonthlyCheckpointMaxStringEmptyCount(ColumnMaxStringEmptyCountCheckSpec monthlyCheckpointMaxStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxStringEmptyCount, monthlyCheckpointMaxStringEmptyCount));
        this.monthlyCheckpointMaxStringEmptyCount = monthlyCheckpointMaxStringEmptyCount;
        propagateHierarchyIdToField(monthlyCheckpointMaxStringEmptyCount, "monthly_checkpoint_max_string_empty_count");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnMaxStringWhitespaceCountCheckSpec getMonthlyCheckpointMaxStringWhitespaceCount() {
        return monthlyCheckpointMaxStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyCheckpointMaxStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setMonthlyCheckpointMaxStringWhitespaceCount(ColumnMaxStringWhitespaceCountCheckSpec monthlyCheckpointMaxStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxStringWhitespaceCount, monthlyCheckpointMaxStringWhitespaceCount));
        this.monthlyCheckpointMaxStringWhitespaceCount = monthlyCheckpointMaxStringWhitespaceCount;
        propagateHierarchyIdToField(monthlyCheckpointMaxStringWhitespaceCount, "monthly_checkpoint_max_string_whitespace_count");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnMinStringValidDatesPercentCheckSpec getMonthlyCheckpointMinStringValidDatesPercent() {
        return monthlyCheckpointMinStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param monthlyCheckpointMinStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setMonthlyCheckpointMinStringValidDatesPercent(ColumnMinStringValidDatesPercentCheckSpec monthlyCheckpointMinStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinStringValidDatesPercent, monthlyCheckpointMinStringValidDatesPercent));
        this.monthlyCheckpointMinStringValidDatesPercent = monthlyCheckpointMinStringValidDatesPercent;
        propagateHierarchyIdToField(monthlyCheckpointMinStringValidDatesPercent, "monthly_checkpoint_min_string_valid_dates_percent");
    }
    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnMaxStringWhitespacePercentCheckSpec getMonthlyCheckpointMaxStringWhitespacePercent() {
        return monthlyCheckpointMaxStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyCheckpointMaxStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setMonthlyCheckpointMaxStringWhitespacePercent(ColumnMaxStringWhitespacePercentCheckSpec monthlyCheckpointMaxStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxStringWhitespacePercent, monthlyCheckpointMaxStringWhitespacePercent));
        this.monthlyCheckpointMaxStringWhitespacePercent = monthlyCheckpointMaxStringWhitespacePercent;
        propagateHierarchyIdToField(monthlyCheckpointMaxStringWhitespacePercent, "monthly_checkpoint_max_string_whitespace_percent");
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
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnMaxStringNullPlaceholderPercentCheckSpec getMonthlyCheckpointMaxStringNullPlaceholderPercent() {
        return monthlyCheckpointMaxStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyCheckpointMaxStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyCheckpointMaxStringNullPlaceholderPercent(ColumnMaxStringNullPlaceholderPercentCheckSpec monthlyCheckpointMaxStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxStringNullPlaceholderPercent, monthlyCheckpointMaxStringNullPlaceholderPercent));
        this.monthlyCheckpointMaxStringNullPlaceholderPercent = monthlyCheckpointMaxStringNullPlaceholderPercent;
        propagateHierarchyIdToField(monthlyCheckpointMaxStringNullPlaceholderPercent, "monthly_checkpoint_max_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnMinStringBooleanPlaceholderPercentCheckSpec getMonthlyCheckpointMinStringBooleanPlaceholderPercent() {
        return monthlyCheckpointMinStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param monthlyCheckpointMinStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setMonthlyCheckpointMinStringBooleanPlaceholderPercent(ColumnMinStringBooleanPlaceholderPercentCheckSpec monthlyCheckpointMinStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinStringBooleanPlaceholderPercent, monthlyCheckpointMinStringBooleanPlaceholderPercent));
        this.monthlyCheckpointMinStringBooleanPlaceholderPercent = monthlyCheckpointMinStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(monthlyCheckpointMinStringBooleanPlaceholderPercent, "monthly_checkpoint_min_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnMinStringParsableToIntegerPercentCheckSpec getMonthlyCheckpointMinStringParsableToIntegerPercent() {
        return monthlyCheckpointMinStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param monthlyCheckpointMinStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setMonthlyCheckpointMinStringParsableToIntegerPercent(ColumnMinStringParsableToIntegerPercentCheckSpec monthlyCheckpointMinStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinStringParsableToIntegerPercent, monthlyCheckpointMinStringParsableToIntegerPercent));
        this.monthlyCheckpointMinStringParsableToIntegerPercent = monthlyCheckpointMinStringParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyCheckpointMinStringParsableToIntegerPercent, "monthly_checkpoint_min_string_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnMaxStringSurroundedByWhitespaceCountCheckSpec getMonthlyCheckpointMaxStringSurroundedByWhitespaceCount() {
        return monthlyCheckpointMaxStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param monthlyCheckpointMaxStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setMonthlyCheckpointMaxStringSurroundedByWhitespaceCount(ColumnMaxStringSurroundedByWhitespaceCountCheckSpec monthlyCheckpointMaxStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxStringSurroundedByWhitespaceCount, monthlyCheckpointMaxStringSurroundedByWhitespaceCount));
        this.monthlyCheckpointMaxStringSurroundedByWhitespaceCount = monthlyCheckpointMaxStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(monthlyCheckpointMaxStringSurroundedByWhitespaceCount, "monthly_checkpoint_max_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnMaxStringSurroundedByWhitespacePercentCheckSpec getMonthlyCheckpointMaxStringSurroundedByWhitespacePercent() {
        return monthlyCheckpointMaxStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param monthlyCheckpointMaxStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setMonthlyCheckpointMaxStringSurroundedByWhitespacePercent(ColumnMaxStringSurroundedByWhitespacePercentCheckSpec monthlyCheckpointMaxStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxStringSurroundedByWhitespacePercent, monthlyCheckpointMaxStringSurroundedByWhitespacePercent));
        this.monthlyCheckpointMaxStringSurroundedByWhitespacePercent = monthlyCheckpointMaxStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyCheckpointMaxStringSurroundedByWhitespacePercent, "monthly_checkpoint_max_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnMinStringParsableToFloatPercentCheckSpec getMonthlyCheckpointMinStringParsableToFloatPercent() {
        return monthlyCheckpointMinStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param monthlyCheckpointMinStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setMonthlyCheckpointMinStringParsableToFloatPercent(ColumnMinStringParsableToFloatPercentCheckSpec monthlyCheckpointMinStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinStringParsableToFloatPercent, monthlyCheckpointMinStringParsableToFloatPercent));
        this.monthlyCheckpointMinStringParsableToFloatPercent = monthlyCheckpointMinStringParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyCheckpointMinStringParsableToFloatPercent, "monthly_checkpoint_min_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent check.
     */
    public ColumnMinStringValidUsaZipcodePercentCheckSpec getMonthlyCheckpointMinStringValidUsaZipcodePercent() {
        return monthlyCheckpointMinStringValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid usa zip code percent check.
     * @param monthlyCheckpointMinStringValidUsaZipcodePercent Minimum string valid usa zip code percent check.
     */
    public void setMonthlyCheckpointMinStringValidUsaZipcodePercent(ColumnMinStringValidUsaZipcodePercentCheckSpec monthlyCheckpointMinStringValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinStringValidUsaZipcodePercent, monthlyCheckpointMinStringValidUsaZipcodePercent));
        this.monthlyCheckpointMinStringValidUsaZipcodePercent = monthlyCheckpointMinStringValidUsaZipcodePercent;
        propagateHierarchyIdToField(monthlyCheckpointMinStringValidUsaZipcodePercent, "monthly_checkpoint_min_string_valid_usa_zipcode_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnMinStringValidUsaPhonePercentCheckSpec getMonthlyCheckpointMinStringValidUsaPhonePercent() {
        return monthlyCheckpointMinStringValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum string valid USA phone percent check.
     * @param monthlyCheckpointMinStringValidUsaPhonePercent Minimum string valid USA phone percent check.
     */
    public void setMonthlyCheckpointMinStringValidUsaPhonePercent(ColumnMinStringValidUsaPhonePercentCheckSpec monthlyCheckpointMinStringValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinStringValidUsaPhonePercent, monthlyCheckpointMinStringValidUsaPhonePercent));
        this.monthlyCheckpointMinStringValidUsaPhonePercent = monthlyCheckpointMinStringValidUsaPhonePercent;
        propagateHierarchyIdToField(monthlyCheckpointMinStringValidUsaPhonePercent, "monthly_checkpoint_min_string_valid_usa_phone_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent check.
     */
    public ColumnMinValidCountryCodePercentCheckSpec getMonthlyCheckpointMinStringValidCountryCodePercent() {
        return monthlyCheckpointMinStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param monthlyCheckpointMinStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setMonthlyCheckpointMinStringValidCountryCodePercent(ColumnMinValidCountryCodePercentCheckSpec monthlyCheckpointMinStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinStringValidCountryCodePercent, monthlyCheckpointMinStringValidCountryCodePercent));
        this.monthlyCheckpointMinStringValidCountryCodePercent = monthlyCheckpointMinStringValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyCheckpointMinStringValidCountryCodePercent, "monthly_checkpoint_min_string_valid_country_code_percent");
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
