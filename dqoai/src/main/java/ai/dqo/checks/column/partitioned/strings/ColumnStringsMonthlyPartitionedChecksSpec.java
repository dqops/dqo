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
import ai.dqo.checks.column.strings.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_max_string_length_below", o -> o.monthlyPartitionMaxStringLengthBelow);
            put("monthly_partition_min_string_length_above", o -> o.monthlyPartitionMinStringLengthAbove);
            put("monthly_partition_mean_string_length_between", o -> o.monthlyPartitionMeanStringLengthBetween);
            put("monthly_partition_max_string_empty_percent", o -> o.monthlyPartitionMaxStringEmptyPercent);
            put("monthly_partition_max_string_empty_count", o -> o.monthlyPartitionMaxStringEmptyCount);
            put("monthly_partition_max_string_whitespace_count", o -> o.monthlyPartitionMaxStringWhitespaceCount);
            put("monthly_partition_max_string_whitespace_percent", o -> o.monthlyPartitionMaxStringWhitespacePercent);
            put("monthly_partition_min_string_valid_dates_percent", o -> o.monthlyPartitionMinStringValidDatesPercent);
            put("monthly_partition_max_string_null_placeholder_count", o -> o.monthlyPartitionMaxStringNullPlaceholderCount);
            put("monthly_partition_max_string_null_placeholder_percent", o -> o.monthlyPartitionMaxStringNullPlaceholderPercent);
            put("monthly_partition_min_string_boolean_placeholder_percent", o -> o.monthlyPartitionMinStringBooleanPlaceholderPercent);
            put("monthly_partition_max_string_surrounded_by_whitespace_count", o -> o.monthlyPartitionMaxStringSurroundedByWhitespaceCount);
            put("monthly_partition_min_string_parsable_to_integer_percent", o -> o.monthlyPartitionMinStringParsableToIntegerPercent);
            put("monthly_partition_max_string_surrounded_by_whitespace_percent", o -> o.monthlyPartitionMaxStringSurroundedByWhitespacePercent);
            put("monthly_partition_min_string_parsable_to_float_percent", o -> o.monthlyPartitionMinStringParsableToFloatPercent);
            put("monthly_partition_min_string_valid_usa_zipcode_percent", o -> o.monthlyPartitionMinStringValidUsaZipcodePercent);
            put("monthly_partition_min_string_valid_usa_phone_percent", o -> o.monthlyPartitionMinStringValidUsaPhonePercent);
            put("monthly_partition_min_string_valid_country_code_percent", o -> o.monthlyPartitionMinStringValidCountryCodePercent);
            put("monthly_partition_min_string_valid_currency_code_percent", o -> o.monthlyPartitionMinStringValidCurrencyCodePercent);
            put("monthly_partition_min_strings_in_set_count", o -> o.monthlyPartitionMinStringsInSetCount);
            put("monthly_partition_min_strings_in_set_percent", o -> o.monthlyPartitionMinStringsInSetPercent);
            put("monthly_partition_max_strings_invalid_email_count", o -> o.monthlyPartitionMaxInvalidEmailCount);
            put("monthly_partition_min_valid_email_percent", o -> o.monthlyPartitionMinValidEmailPercent);
            put("monthly_partition_max_not_match_regex_count", o -> o.monthlyPartitionMaxNotMatchRegexCount);
            put("monthly_partition_min_regex_match_percent", o -> o.monthlyPartitionMinRegexMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxStringLengthBelowCheckSpec monthlyPartitionMaxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinStringLengthAboveCheckSpec monthlyPartitionMinStringLengthAbove;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMeanStringLengthBetweenCheckSpec monthlyPartitionMeanStringLengthBetween;

    @JsonPropertyDescription("Verifies that the percentage of string in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxStringEmptyPercentCheckSpec monthlyPartitionMaxStringEmptyPercent;

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxStringEmptyCountCheckSpec monthlyPartitionMaxStringEmptyCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxStringWhitespaceCountCheckSpec monthlyPartitionMaxStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinStringValidDatesPercentCheckSpec monthlyPartitionMinStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxStringWhitespacePercentCheckSpec monthlyPartitionMaxStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxStringNullPlaceholderCountCheckSpec monthlyPartitionMaxStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxStringNullPlaceholderPercentCheckSpec monthlyPartitionMaxStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinStringBooleanPlaceholderPercentCheckSpec monthlyPartitionMinStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxStringSurroundedByWhitespaceCountCheckSpec monthlyPartitionMaxStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinStringParsableToIntegerPercentCheckSpec monthlyPartitionMinStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxStringSurroundedByWhitespacePercentCheckSpec monthlyPartitionMaxStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinStringParsableToFloatPercentCheckSpec monthlyPartitionMinStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinStringValidUsaZipcodePercentCheckSpec monthlyPartitionMinStringValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinStringValidUsaPhonePercentCheckSpec monthlyPartitionMinStringValidUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinValidCountryCodePercentCheckSpec monthlyPartitionMinStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinValidCurrencyCodePercentCheckSpec monthlyPartitionMinStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of strings from set in a column does not exceed the minimum accepted count.")
    private ColumnMinStringsInSetCountCheckSpec monthlyPartitionMinStringsInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage.")
    private ColumnMinStringsInSetPercentCheckSpec monthlyPartitionMinStringsInSetPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxInvalidEmailCountCheckSpec monthlyPartitionMaxInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinValidEmailPercentCheckSpec monthlyPartitionMinValidEmailPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted quantity. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxNotMatchRegexCountCheckSpec monthlyPartitionMaxNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted quantity. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinRegexMatchPercentCheckSpec monthlyPartitionMinRegexMatchPercent;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnMaxStringLengthBelowCheckSpec getMonthlyPartitionMaxStringLengthBelow() {
        return monthlyPartitionMaxStringLengthBelow;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param monthlyPartitionMaxStringLengthBelow Maximum string length below check.
     */
    public void setMonthlyPartitionMaxStringLengthBelow(ColumnMaxStringLengthBelowCheckSpec monthlyPartitionMaxStringLengthBelow) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxStringLengthBelow, monthlyPartitionMaxStringLengthBelow));
        this.monthlyPartitionMaxStringLengthBelow = monthlyPartitionMaxStringLengthBelow;
        propagateHierarchyIdToField(monthlyPartitionMaxStringLengthBelow, "monthly_partition_max_string_length_below");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnMinStringLengthAboveCheckSpec getMonthlyPartitionMinStringLengthAbove() {
        return monthlyPartitionMinStringLengthAbove;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param monthlyPartitionMinStringLengthAbove Minimum string length above check.
     */
    public void setMonthlyPartitionMinStringLengthAbove(ColumnMinStringLengthAboveCheckSpec monthlyPartitionMinStringLengthAbove) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringLengthAbove, monthlyPartitionMinStringLengthAbove));
        this.monthlyPartitionMinStringLengthAbove = monthlyPartitionMinStringLengthAbove;
        propagateHierarchyIdToField(monthlyPartitionMinStringLengthAbove, "monthly_partition_min_string_length_above");
    }

    /**
     * Returns a maximum empty string percentage check.
     * @return Maximum empty string percentage check.
     */
    public ColumnMaxStringEmptyPercentCheckSpec getMonthlyPartitionMaxStringEmptyPercent() {
        return monthlyPartitionMaxStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum empty string percentage check.
     * @param monthlyPartitionMaxStringEmptyPercent Maximum empty string percentage check.
     */
    public void setMonthlyPartitionMaxStringEmptyPercent(ColumnMaxStringEmptyPercentCheckSpec monthlyPartitionMaxStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxStringEmptyPercent, monthlyPartitionMaxStringEmptyPercent));
        this.monthlyPartitionMaxStringEmptyPercent = monthlyPartitionMaxStringEmptyPercent;
        propagateHierarchyIdToField(monthlyPartitionMaxStringEmptyPercent, "monthly_partition_max_string_empty_percent");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnMeanStringLengthBetweenCheckSpec getMonthlyPartitionMeanStringLengthBetween() {
        return monthlyPartitionMeanStringLengthBetween;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param monthlyPartitionMeanStringLengthBetween Mean string length between check.
     */
    public void setMonthlyPartitionMeanStringLengthBetween(ColumnMeanStringLengthBetweenCheckSpec monthlyPartitionMeanStringLengthBetween) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMeanStringLengthBetween, monthlyPartitionMeanStringLengthBetween));
        this.monthlyPartitionMeanStringLengthBetween = monthlyPartitionMeanStringLengthBetween;
        propagateHierarchyIdToField(monthlyPartitionMeanStringLengthBetween, "monthly_partition_mean_string_length_between");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnMaxStringEmptyCountCheckSpec getMonthlyPartitionMaxStringEmptyCount() {
        return monthlyPartitionMaxStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyPartitionMaxStringEmptyCount Max string empty count check.
     */
    public void setMonthlyPartitionMaxStringEmptyCount(ColumnMaxStringEmptyCountCheckSpec monthlyPartitionMaxStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxStringEmptyCount, monthlyPartitionMaxStringEmptyCount));
        this.monthlyPartitionMaxStringEmptyCount = monthlyPartitionMaxStringEmptyCount;
        propagateHierarchyIdToField(monthlyPartitionMaxStringEmptyCount, "monthly_partition_max_string_empty_count");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnMaxStringWhitespaceCountCheckSpec getMonthlyPartitionMaxStringWhitespaceCount() {
        return monthlyPartitionMaxStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyPartitionMaxStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setMonthlyPartitionMaxStringWhitespaceCount(ColumnMaxStringWhitespaceCountCheckSpec monthlyPartitionMaxStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxStringWhitespaceCount, monthlyPartitionMaxStringWhitespaceCount));
        this.monthlyPartitionMaxStringWhitespaceCount = monthlyPartitionMaxStringWhitespaceCount;
        propagateHierarchyIdToField(monthlyPartitionMaxStringWhitespaceCount, "monthly_partition_max_string_whitespace_count");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnMinStringValidDatesPercentCheckSpec getMonthlyPartitionMinStringValidDatesPercent() {
        return monthlyPartitionMinStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param monthlyPartitionMinStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setMonthlyPartitionMinStringValidDatesPercent(ColumnMinStringValidDatesPercentCheckSpec monthlyPartitionMinStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringValidDatesPercent, monthlyPartitionMinStringValidDatesPercent));
        this.monthlyPartitionMinStringValidDatesPercent = monthlyPartitionMinStringValidDatesPercent;
        propagateHierarchyIdToField(monthlyPartitionMinStringValidDatesPercent, "monthly_partition_min_string_valid_dates_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnMaxStringWhitespacePercentCheckSpec getMonthlyPartitionMaxStringWhitespacePercent() {
        return monthlyPartitionMaxStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyPartitionMaxStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setMonthlyPartitionMaxStringWhitespacePercent(ColumnMaxStringWhitespacePercentCheckSpec monthlyPartitionMaxStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxStringWhitespacePercent, monthlyPartitionMaxStringWhitespacePercent));
        this.monthlyPartitionMaxStringWhitespacePercent = monthlyPartitionMaxStringWhitespacePercent;
        propagateHierarchyIdToField(monthlyPartitionMaxStringWhitespacePercent, "monthly_partition_max_string_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnMaxStringNullPlaceholderCountCheckSpec getMonthlyPartitionMaxStringNullPlaceholderCount() {
        return monthlyPartitionMaxStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyPartitionMaxStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setMonthlyPartitionMaxStringNullPlaceholderCount(ColumnMaxStringNullPlaceholderCountCheckSpec monthlyPartitionMaxStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxStringNullPlaceholderCount, monthlyPartitionMaxStringNullPlaceholderCount));
        this.monthlyPartitionMaxStringNullPlaceholderCount = monthlyPartitionMaxStringNullPlaceholderCount;
        propagateHierarchyIdToField(monthlyPartitionMaxStringNullPlaceholderCount, "monthly_partition_max_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnMaxStringNullPlaceholderPercentCheckSpec getMonthlyPartitionMaxStringNullPlaceholderPercent() {
        return monthlyPartitionMaxStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyPartitionMaxStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyPartitionMaxStringNullPlaceholderPercent(ColumnMaxStringNullPlaceholderPercentCheckSpec monthlyPartitionMaxStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxStringNullPlaceholderPercent, monthlyPartitionMaxStringNullPlaceholderPercent));
        this.monthlyPartitionMaxStringNullPlaceholderPercent = monthlyPartitionMaxStringNullPlaceholderPercent;
        propagateHierarchyIdToField(monthlyPartitionMaxStringNullPlaceholderPercent, "monthly_partition_max_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnMinStringBooleanPlaceholderPercentCheckSpec getMonthlyPartitionMinStringBooleanPlaceholderPercent() {
        return monthlyPartitionMinStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param monthlyPartitionMinStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setMonthlyPartitionMinStringBooleanPlaceholderPercent(ColumnMinStringBooleanPlaceholderPercentCheckSpec monthlyPartitionMinStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringBooleanPlaceholderPercent, monthlyPartitionMinStringBooleanPlaceholderPercent));
        this.monthlyPartitionMinStringBooleanPlaceholderPercent = monthlyPartitionMinStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(monthlyPartitionMinStringBooleanPlaceholderPercent, "monthly_partition_min_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent  check.
     */
    public ColumnMinStringParsableToIntegerPercentCheckSpec getMonthlyPartitionMinStringParsableToIntegerPercent() {
        return monthlyPartitionMinStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param monthlyPartitionMinStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setMonthlyPartitionMinStringParsableToIntegerPercent(ColumnMinStringParsableToIntegerPercentCheckSpec monthlyPartitionMinStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringParsableToIntegerPercent, monthlyPartitionMinStringParsableToIntegerPercent));
        this.monthlyPartitionMinStringParsableToIntegerPercent = monthlyPartitionMinStringParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyPartitionMinStringParsableToIntegerPercent, "monthly_partition_min_string_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     *
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnMaxStringSurroundedByWhitespaceCountCheckSpec getMonthlyPartitionMaxStringSurroundedByWhitespaceCount() {
        return monthlyPartitionMaxStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     *
     * @param monthlyPartitionMaxStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setMonthlyPartitionMaxStringSurroundedByWhitespaceCount(ColumnMaxStringSurroundedByWhitespaceCountCheckSpec monthlyPartitionMaxStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxStringSurroundedByWhitespaceCount, monthlyPartitionMaxStringSurroundedByWhitespaceCount));
        this.monthlyPartitionMaxStringSurroundedByWhitespaceCount = monthlyPartitionMaxStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(monthlyPartitionMaxStringSurroundedByWhitespaceCount, "monthly_partition_max_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     *
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnMaxStringSurroundedByWhitespacePercentCheckSpec getMonthlyPartitionMaxStringSurroundedByWhitespacePercent() {
        return monthlyPartitionMaxStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     *
     * @param monthlyPartitionMaxStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setMonthlyPartitionMaxStringSurroundedByWhitespacePercent(ColumnMaxStringSurroundedByWhitespacePercentCheckSpec monthlyPartitionMaxStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxStringSurroundedByWhitespacePercent, monthlyPartitionMaxStringSurroundedByWhitespacePercent));
        this.monthlyPartitionMaxStringSurroundedByWhitespacePercent = monthlyPartitionMaxStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyPartitionMaxStringSurroundedByWhitespacePercent, "monthly_partition_max_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent  check.
     */
    public ColumnMinStringParsableToFloatPercentCheckSpec getMonthlyPartitionMinStringParsableToFloatPercent() {
        return monthlyPartitionMinStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param monthlyPartitionMinStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setMonthlyPartitionMinStringParsableToFloatPercent(ColumnMinStringParsableToFloatPercentCheckSpec monthlyPartitionMinStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringParsableToFloatPercent, monthlyPartitionMinStringParsableToFloatPercent));
        this.monthlyPartitionMinStringParsableToFloatPercent = monthlyPartitionMinStringParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyPartitionMinStringParsableToFloatPercent, "monthly_partition_min_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent  check.
     */
    public ColumnMinStringValidUsaZipcodePercentCheckSpec getMonthlyPartitionMinStringValidUsaZipcodePercent() {
        return monthlyPartitionMinStringValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid usa zip code percent check.
     * @param monthlyPartitionMinStringValidUsaZipcodePercent Minimum string valid usa zip code percent check.
     */
    public void setMonthlyPartitionMinStringValidUsaZipcodePercent(ColumnMinStringValidUsaZipcodePercentCheckSpec monthlyPartitionMinStringValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringValidUsaZipcodePercent, monthlyPartitionMinStringValidUsaZipcodePercent));
        this.monthlyPartitionMinStringValidUsaZipcodePercent = monthlyPartitionMinStringValidUsaZipcodePercent;
        propagateHierarchyIdToField(monthlyPartitionMinStringValidUsaZipcodePercent, "monthly_partition_min_string_valid_usa_zipcode_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent  check.
     */
    public ColumnMinStringValidUsaPhonePercentCheckSpec getMonthlyPartitionMinStringValidUsaPhonePercent() {
        return monthlyPartitionMinStringValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum string valid USA phone percent check.
     * @param monthlyPartitionMinStringValidUsaPhonePercent Minimum string valid USA phone percent check.
     */
    public void setMonthlyPartitionMinStringValidUsaPhonePercent(ColumnMinStringValidUsaPhonePercentCheckSpec monthlyPartitionMinStringValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringValidUsaPhonePercent, monthlyPartitionMinStringValidUsaPhonePercent));
        this.monthlyPartitionMinStringValidUsaPhonePercent = monthlyPartitionMinStringValidUsaPhonePercent;
        propagateHierarchyIdToField(monthlyPartitionMinStringValidUsaPhonePercent, "monthly_partition_min_string_valid_usa_phone_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent  check.
     */
    public ColumnMinValidCountryCodePercentCheckSpec getMonthlyPartitionMinStringValidCountryCodePercent() {
        return monthlyPartitionMinStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param monthlyPartitionMinStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setMonthlyPartitionMinStringValidCountryCodePercent(ColumnMinValidCountryCodePercentCheckSpec monthlyPartitionMinStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringValidCountryCodePercent, monthlyPartitionMinStringValidCountryCodePercent));
        this.monthlyPartitionMinStringValidCountryCodePercent = monthlyPartitionMinStringValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyPartitionMinStringValidCountryCodePercent, "monthly_partition_min_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent  check.
     */
    public ColumnMinValidCurrencyCodePercentCheckSpec getMonthlyPartitionMinStringValidCurrencyCodePercent() {
        return monthlyPartitionMinStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param monthlyPartitionMinStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setMonthlyPartitionMinStringValidCurrencyCodePercent(ColumnMinValidCurrencyCodePercentCheckSpec monthlyPartitionMinStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringValidCurrencyCodePercent, monthlyPartitionMinStringValidCurrencyCodePercent));
        this.monthlyPartitionMinStringValidCurrencyCodePercent = monthlyPartitionMinStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(monthlyPartitionMinStringValidCurrencyCodePercent, "monthly_partition_min_string_valid_currency_code_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnMinStringsInSetCountCheckSpec getMonthlyPartitionMinStringsInSetCount() {
        return monthlyPartitionMinStringsInSetCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param monthlyPartitionMinStringsInSetCount Minimum strings in set count check.
     */
    public void setMonthlyPartitionMinStringsInSetCount(ColumnMinStringsInSetCountCheckSpec monthlyPartitionMinStringsInSetCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringsInSetCount, monthlyPartitionMinStringsInSetCount));
        this.monthlyPartitionMinStringsInSetCount = monthlyPartitionMinStringsInSetCount;
        propagateHierarchyIdToField(monthlyPartitionMinStringsInSetCount, "monthly_partition_min_strings_in_set_count");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnMinStringsInSetPercentCheckSpec getMonthlyPartitionMinStringsInSetPercent() {
        return monthlyPartitionMinStringsInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param monthlyPartitionMinStringsInSetPercent Minimum strings in set percent check.
     */
    public void setMonthlyPartitionMinStringsInSetPercent(ColumnMinStringsInSetPercentCheckSpec monthlyPartitionMinStringsInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinStringsInSetPercent, monthlyPartitionMinStringsInSetPercent));
        this.monthlyPartitionMinStringsInSetPercent = monthlyPartitionMinStringsInSetPercent;
        propagateHierarchyIdToField(monthlyPartitionMinStringsInSetPercent, "monthly_partition_min_strings_in_set_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnMaxInvalidEmailCountCheckSpec getMonthlyPartitionMaxInvalidEmailCount() {
        return monthlyPartitionMaxInvalidEmailCount;
    }

    /**
     * Sets a new definition of a maximum invalid email count check.
     * @param monthlyPartitionMaxInvalidEmailCount Maximum invalid email count check.
     */
    public void setMonthlyPartitionMaxInvalidEmailCount(ColumnMaxInvalidEmailCountCheckSpec monthlyPartitionMaxInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxInvalidEmailCount, monthlyPartitionMaxInvalidEmailCount));
        this.monthlyPartitionMaxInvalidEmailCount = monthlyPartitionMaxInvalidEmailCount;
        propagateHierarchyIdToField(monthlyPartitionMaxInvalidEmailCount, "monthly_partition_max_strings_invalid_email_count");
    }

    /**
     * Returns a minimum valid email percent check.
     * @return Minimum valid email percent check.
     */
    public ColumnMinValidEmailPercentCheckSpec getMonthlyPartitionMinValidEmailPercent() {
        return monthlyPartitionMinValidEmailPercent;
    }

    /**
     * Sets a new definition of a minimum valid email percent check.
     * @param monthlyPartitionMinValidEmailPercent Minimum valid email percent check.
     */
    public void setMonthlyPartitionMinValidEmailPercent(ColumnMinValidEmailPercentCheckSpec monthlyPartitionMinValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinValidEmailPercent, monthlyPartitionMinValidEmailPercent));
        this.monthlyPartitionMinValidEmailPercent = monthlyPartitionMinValidEmailPercent;
        propagateHierarchyIdToField(monthlyPartitionMinValidEmailPercent, "monthly_partition_min_valid_email_percent");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnMaxNotMatchRegexCountCheckSpec getMonthlyPartitionMaxNotMatchRegexCount() {
        return monthlyPartitionMaxNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param monthlyPartitionMaxNotMatchRegexCount Maximum not match regex count check.
     */
    public void setMonthlyPartitionMaxNotMatchRegexCount(ColumnMaxNotMatchRegexCountCheckSpec monthlyPartitionMaxNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxNotMatchRegexCount, monthlyPartitionMaxNotMatchRegexCount));
        this.monthlyPartitionMaxNotMatchRegexCount = monthlyPartitionMaxNotMatchRegexCount;
        propagateHierarchyIdToField(monthlyPartitionMaxNotMatchRegexCount, "monthly_partition_max_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnMinRegexMatchPercentCheckSpec getMonthlyPartitionMinRegexMatchPercent() {
        return monthlyPartitionMinRegexMatchPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param monthlyPartitionMinRegexMatchPercent Minimum match regex percent check.
     */
    public void setMonthlyPartitionMinRegexMatchPercent(ColumnMinRegexMatchPercentCheckSpec monthlyPartitionMinRegexMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinRegexMatchPercent, monthlyPartitionMinRegexMatchPercent));
        this.monthlyPartitionMinRegexMatchPercent = monthlyPartitionMinRegexMatchPercent;
        propagateHierarchyIdToField(monthlyPartitionMinRegexMatchPercent, "monthly_partition_min_regex_match_percent");
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
