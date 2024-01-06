/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.checks.column.monitoring.strings;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.strings.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of strings data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_string_max_length", o -> o.monthlyStringMaxLength);
            put("monthly_string_min_length", o -> o.monthlyStringMinLength);
            put("monthly_string_mean_length", o -> o.monthlyStringMeanLength);
            put("monthly_string_length_below_min_length_count", o -> o.monthlyStringLengthBelowMinLengthCount);
            put("monthly_string_length_below_min_length_percent", o -> o.monthlyStringLengthBelowMinLengthPercent);
            put("monthly_string_length_above_max_length_count", o -> o.monthlyStringLengthAboveMaxLengthCount);
            put("monthly_string_length_above_max_length_percent", o -> o.monthlyStringLengthAboveMaxLengthPercent);
            put("monthly_string_length_in_range_percent", o -> o.monthlyStringLengthInRangePercent);

            put("monthly_string_empty_count", o -> o.monthlyStringEmptyCount);
            put("monthly_string_empty_percent", o -> o.monthlyStringEmptyPercent);
            put("monthly_string_whitespace_count", o -> o.monthlyStringWhitespaceCount);
            put("monthly_string_whitespace_percent", o -> o.monthlyStringWhitespacePercent);
            put("monthly_string_surrounded_by_whitespace_count", o -> o.monthlyStringSurroundedByWhitespaceCount);
            put("monthly_string_surrounded_by_whitespace_percent", o -> o.monthlyStringSurroundedByWhitespacePercent);

            put("monthly_string_null_placeholder_count", o -> o.monthlyStringNullPlaceholderCount);
            put("monthly_string_null_placeholder_percent", o -> o.monthlyStringNullPlaceholderPercent);
            put("monthly_string_boolean_placeholder_percent", o -> o.monthlyStringBooleanPlaceholderPercent);
            put("monthly_string_parsable_to_integer_percent", o -> o.monthlyStringParsableToIntegerPercent);
            put("monthly_string_parsable_to_float_percent", o -> o.monthlyStringParsableToFloatPercent);

            put("monthly_expected_strings_in_use_count", o -> o.monthlyExpectedStringsInUseCount);
            put("monthly_string_value_in_set_percent", o -> o.monthlyStringValueInSetPercent);

            put("monthly_string_valid_dates_percent", o -> o.monthlyStringValidDatesPercent);
            put("monthly_string_valid_country_code_percent", o -> o.monthlyStringValidCountryCodePercent);
            put("monthly_string_valid_currency_code_percent", o -> o.monthlyStringValidCurrencyCodePercent);
            put("monthly_string_invalid_email_count", o -> o.monthlyStringInvalidEmailCount);
            put("monthly_string_invalid_uuid_count", o -> o.monthlyStringInvalidUuidCount);
            put("monthly_string_valid_uuid_percent", o -> o.monthlyStringValidUuidPercent);
            put("monthly_string_invalid_ip4_address_count", o -> o.monthlyStringInvalidIp4AddressCount);
            put("monthly_string_invalid_ip6_address_count", o -> o.monthlyStringInvalidIp6AddressCount);

            put("monthly_string_not_match_regex_count", o -> o.monthlyStringNotMatchRegexCount);
            put("monthly_string_match_regex_percent", o -> o.monthlyStringMatchRegexPercent);
            put("monthly_string_not_match_date_regex_count", o -> o.monthlyStringNotMatchDateRegexCount);
            put("monthly_string_match_date_regex_percent", o -> o.monthlyStringMatchDateRegexPercent);
            put("monthly_string_match_name_regex_percent", o -> o.monthlyStringMatchNameRegexPercent);

            put("monthly_expected_strings_in_top_values_count", o -> o.monthlyExpectedStringsInTopValuesCount);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMaxLengthCheckSpec monthlyStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMinLengthCheckSpec monthlyStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMeanLengthCheckSpec monthlyStringMeanLength;

    @JsonPropertyDescription("The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringLengthBelowMinLengthCountCheckSpec monthlyStringLengthBelowMinLengthCount;

    @JsonPropertyDescription("The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringLengthBelowMinLengthPercentCheckSpec monthlyStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringLengthAboveMaxLengthCountCheckSpec monthlyStringLengthAboveMaxLengthCount;

    @JsonPropertyDescription("The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringLengthAboveMaxLengthPercentCheckSpec monthlyStringLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringLengthInRangePercentCheckSpec monthlyStringLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringEmptyCountCheckSpec monthlyStringEmptyCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringEmptyPercentCheckSpec monthlyStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidDatesPercentCheckSpec monthlyStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringWhitespaceCountCheckSpec monthlyStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringWhitespacePercentCheckSpec monthlyStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringSurroundedByWhitespaceCountCheckSpec monthlyStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringSurroundedByWhitespacePercentCheckSpec monthlyStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringNullPlaceholderCountCheckSpec monthlyStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringNullPlaceholderPercentCheckSpec monthlyStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringBooleanPlaceholderPercentCheckSpec monthlyStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringParsableToIntegerPercentCheckSpec monthlyStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringParsableToFloatPercentCheckSpec monthlyStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnExpectedStringsInUseCountCheckSpec monthlyExpectedStringsInUseCount;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValueInSetPercentCheckSpec monthlyStringValueInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidCountryCodePercentCheckSpec monthlyStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidCurrencyCodePercentCheckSpec monthlyStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInvalidEmailCountCheckSpec monthlyStringInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInvalidUuidCountCheckSpec monthlyStringInvalidUuidCount;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidUuidPercentCheckSpec monthlyStringValidUuidPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInvalidIp4AddressCountCheckSpec monthlyStringInvalidIp4AddressCount;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInvalidIp6AddressCountCheckSpec monthlyStringInvalidIp6AddressCount;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringNotMatchRegexCountCheckSpec monthlyStringNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMatchRegexPercentCheckSpec monthlyStringMatchRegexPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringNotMatchDateRegexCountCheckSpec monthlyStringNotMatchDateRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMatchDateRegexPercentCheckSpec monthlyStringMatchDateRegexPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMatchNameRegexPercentCheckSpec monthlyStringMatchNameRegexPercent;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnExpectedStringsInTopValuesCountCheckSpec monthlyExpectedStringsInTopValuesCount;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnStringMaxLengthCheckSpec getMonthlyStringMaxLength() {
        return monthlyStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param monthlyStringMaxLength Maximum string length below check.
     */
    public void setMonthlyStringMaxLength(ColumnStringMaxLengthCheckSpec monthlyStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringMaxLength, monthlyStringMaxLength));
        this.monthlyStringMaxLength = monthlyStringMaxLength;
        propagateHierarchyIdToField(monthlyStringMaxLength, "monthly_string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnStringMinLengthCheckSpec getMonthlyStringMinLength() {
        return monthlyStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param monthlyStringMinLength Minimum string length below check.
     */
    public void setMonthlyStringMinLength(ColumnStringMinLengthCheckSpec monthlyStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringMinLength, monthlyStringMinLength));
        this.monthlyStringMinLength = monthlyStringMinLength;
        propagateHierarchyIdToField(monthlyStringMinLength, "monthly_string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnStringMeanLengthCheckSpec getMonthlyStringMeanLength() {
        return monthlyStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param monthlyStringMeanLength Mean string length between check.
     */
    public void setMonthlyStringMeanLength(ColumnStringMeanLengthCheckSpec monthlyStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringMeanLength, monthlyStringMeanLength));
        this.monthlyStringMeanLength = monthlyStringMeanLength;
        propagateHierarchyIdToField(monthlyStringMeanLength, "monthly_string_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnStringLengthBelowMinLengthCountCheckSpec getMonthlyStringLengthBelowMinLengthCount() {
        return monthlyStringLengthBelowMinLengthCount;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param monthlyStringLengthBelowMinLengthCount String length below min length count check.
     */
    public void setMonthlyStringLengthBelowMinLengthCount(ColumnStringLengthBelowMinLengthCountCheckSpec monthlyStringLengthBelowMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthBelowMinLengthCount, monthlyStringLengthBelowMinLengthCount));
        this.monthlyStringLengthBelowMinLengthCount = monthlyStringLengthBelowMinLengthCount;
        propagateHierarchyIdToField(monthlyStringLengthBelowMinLengthCount, "monthly_string_length_below_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return Mean string length below min length percent check.
     */
    public ColumnStringLengthBelowMinLengthPercentCheckSpec getMonthlyStringLengthBelowMinLengthPercent() {
        return monthlyStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param monthlyStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setMonthlyStringLengthBelowMinLengthPercent(ColumnStringLengthBelowMinLengthPercentCheckSpec monthlyStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthBelowMinLengthPercent, monthlyStringLengthBelowMinLengthPercent));
        this.monthlyStringLengthBelowMinLengthPercent = monthlyStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(monthlyStringLengthBelowMinLengthPercent, "monthly_string_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnStringLengthAboveMaxLengthCountCheckSpec getMonthlyStringLengthAboveMaxLengthCount() {
        return monthlyStringLengthAboveMaxLengthCount;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param monthlyStringLengthAboveMaxLengthCount String length above max length count check.
     */
    public void setMonthlyStringLengthAboveMaxLengthCount(ColumnStringLengthAboveMaxLengthCountCheckSpec monthlyStringLengthAboveMaxLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthAboveMaxLengthCount, monthlyStringLengthAboveMaxLengthCount));
        this.monthlyStringLengthAboveMaxLengthCount = monthlyStringLengthAboveMaxLengthCount;
        propagateHierarchyIdToField(monthlyStringLengthAboveMaxLengthCount, "monthly_string_length_above_max_length_count");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnStringLengthAboveMaxLengthPercentCheckSpec getMonthlyStringLengthAboveMaxLengthPercent() {
        return monthlyStringLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param monthlyStringLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setMonthlyStringLengthAboveMaxLengthPercent(ColumnStringLengthAboveMaxLengthPercentCheckSpec monthlyStringLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthAboveMaxLengthPercent, monthlyStringLengthAboveMaxLengthPercent));
        this.monthlyStringLengthAboveMaxLengthPercent = monthlyStringLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(monthlyStringLengthAboveMaxLengthPercent, "monthly_string_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnStringLengthInRangePercentCheckSpec getMonthlyStringLengthInRangePercent() {
        return monthlyStringLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param monthlyStringLengthInRangePercent String length in range percent check.
     */
    public void setMonthlyStringLengthInRangePercent(ColumnStringLengthInRangePercentCheckSpec monthlyStringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthInRangePercent, monthlyStringLengthInRangePercent));
        this.monthlyStringLengthInRangePercent = monthlyStringLengthInRangePercent;
        propagateHierarchyIdToField(monthlyStringLengthInRangePercent, "monthly_string_length_in_range_percent");
    }

    /**
     * Returns max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnStringEmptyCountCheckSpec getMonthlyStringEmptyCount() {
        return monthlyStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyStringEmptyCount Max string empty count check.
     */
    public void setMonthlyStringEmptyCount(ColumnStringEmptyCountCheckSpec monthlyStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringEmptyCount, monthlyStringEmptyCount));
        this.monthlyStringEmptyCount = monthlyStringEmptyCount;
        propagateHierarchyIdToField(monthlyStringEmptyCount, "monthly_string_empty_count");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnStringEmptyPercentCheckSpec getMonthlyStringEmptyPercent() {
        return monthlyStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param monthlyStringEmptyPercent Maximum string empty percent check.
     */
    public void setMonthlyStringEmptyPercent(ColumnStringEmptyPercentCheckSpec monthlyStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringEmptyPercent, monthlyStringEmptyPercent));
        this.monthlyStringEmptyPercent = monthlyStringEmptyPercent;
        propagateHierarchyIdToField(monthlyStringEmptyPercent, "monthly_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnStringWhitespaceCountCheckSpec getMonthlyStringWhitespaceCount() {
        return monthlyStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setMonthlyStringWhitespaceCount(ColumnStringWhitespaceCountCheckSpec monthlyStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringWhitespaceCount, monthlyStringWhitespaceCount));
        this.monthlyStringWhitespaceCount = monthlyStringWhitespaceCount;
        propagateHierarchyIdToField(monthlyStringWhitespaceCount, "monthly_string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnStringWhitespacePercentCheckSpec getMonthlyStringWhitespacePercent() {
        return monthlyStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setMonthlyStringWhitespacePercent(ColumnStringWhitespacePercentCheckSpec monthlyStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringWhitespacePercent, monthlyStringWhitespacePercent));
        this.monthlyStringWhitespacePercent = monthlyStringWhitespacePercent;
        propagateHierarchyIdToField(monthlyStringWhitespacePercent, "monthly_string_whitespace_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnStringSurroundedByWhitespaceCountCheckSpec getMonthlyStringSurroundedByWhitespaceCount() {
        return monthlyStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param monthlyStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setMonthlyStringSurroundedByWhitespaceCount(ColumnStringSurroundedByWhitespaceCountCheckSpec monthlyStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringSurroundedByWhitespaceCount, monthlyStringSurroundedByWhitespaceCount));
        this.monthlyStringSurroundedByWhitespaceCount = monthlyStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(monthlyStringSurroundedByWhitespaceCount, "monthly_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnStringSurroundedByWhitespacePercentCheckSpec getMonthlyStringSurroundedByWhitespacePercent() {
        return monthlyStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param monthlyStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setMonthlyStringSurroundedByWhitespacePercent(ColumnStringSurroundedByWhitespacePercentCheckSpec monthlyStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringSurroundedByWhitespacePercent, monthlyStringSurroundedByWhitespacePercent));
        this.monthlyStringSurroundedByWhitespacePercent = monthlyStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyStringSurroundedByWhitespacePercent, "monthly_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnStringNullPlaceholderCountCheckSpec getMonthlyStringNullPlaceholderCount() {
        return monthlyStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setMonthlyStringNullPlaceholderCount(ColumnStringNullPlaceholderCountCheckSpec monthlyStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringNullPlaceholderCount, monthlyStringNullPlaceholderCount));
        this.monthlyStringNullPlaceholderCount = monthlyStringNullPlaceholderCount;
        propagateHierarchyIdToField(monthlyStringNullPlaceholderCount, "monthly_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnStringNullPlaceholderPercentCheckSpec getMonthlyStringNullPlaceholderPercent() {
        return monthlyStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyStringNullPlaceholderPercent(ColumnStringNullPlaceholderPercentCheckSpec monthlyStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringNullPlaceholderPercent, monthlyStringNullPlaceholderPercent));
        this.monthlyStringNullPlaceholderPercent = monthlyStringNullPlaceholderPercent;
        propagateHierarchyIdToField(monthlyStringNullPlaceholderPercent, "monthly_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnStringBooleanPlaceholderPercentCheckSpec getMonthlyStringBooleanPlaceholderPercent() {
        return monthlyStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param monthlyStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setMonthlyStringBooleanPlaceholderPercent(ColumnStringBooleanPlaceholderPercentCheckSpec monthlyStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringBooleanPlaceholderPercent, monthlyStringBooleanPlaceholderPercent));
        this.monthlyStringBooleanPlaceholderPercent = monthlyStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(monthlyStringBooleanPlaceholderPercent, "monthly_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnStringParsableToIntegerPercentCheckSpec getMonthlyStringParsableToIntegerPercent() {
        return monthlyStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param monthlyStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setMonthlyStringParsableToIntegerPercent(ColumnStringParsableToIntegerPercentCheckSpec monthlyStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringParsableToIntegerPercent, monthlyStringParsableToIntegerPercent));
        this.monthlyStringParsableToIntegerPercent = monthlyStringParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyStringParsableToIntegerPercent, "monthly_string_parsable_to_integer_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnStringParsableToFloatPercentCheckSpec getMonthlyStringParsableToFloatPercent() {
        return monthlyStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param monthlyStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setMonthlyStringParsableToFloatPercent(ColumnStringParsableToFloatPercentCheckSpec monthlyStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringParsableToFloatPercent, monthlyStringParsableToFloatPercent));
        this.monthlyStringParsableToFloatPercent = monthlyStringParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyStringParsableToFloatPercent, "monthly_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnExpectedStringsInUseCountCheckSpec getMonthlyExpectedStringsInUseCount() {
        return monthlyExpectedStringsInUseCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param dailyExpectedStringsInUseCount Minimum strings in set count check.
     */
    public void setMonthlyExpectedStringsInUseCount(ColumnExpectedStringsInUseCountCheckSpec dailyExpectedStringsInUseCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyExpectedStringsInUseCount, dailyExpectedStringsInUseCount));
        this.monthlyExpectedStringsInUseCount = dailyExpectedStringsInUseCount;
        propagateHierarchyIdToField(dailyExpectedStringsInUseCount, "monthly_expected_strings_in_use_count");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringValueInSetPercentCheckSpec getMonthlyStringValueInSetPercent() {
        return monthlyStringValueInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param monthlyStringValueInSetPercent Minimum strings in set percent check.
     */
    public void setMonthlyStringValueInSetPercent(ColumnStringValueInSetPercentCheckSpec monthlyStringValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringValueInSetPercent, monthlyStringValueInSetPercent));
        this.monthlyStringValueInSetPercent = monthlyStringValueInSetPercent;
        propagateHierarchyIdToField(monthlyStringValueInSetPercent, "monthly_string_value_in_set_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnStringValidDatesPercentCheckSpec getMonthlyStringValidDatesPercent() {
        return monthlyStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param monthlyStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setMonthlyStringValidDatesPercent(ColumnStringValidDatesPercentCheckSpec monthlyStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringValidDatesPercent, monthlyStringValidDatesPercent));
        this.monthlyStringValidDatesPercent = monthlyStringValidDatesPercent;
        propagateHierarchyIdToField(monthlyStringValidDatesPercent, "monthly_string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent check.
     */
    public ColumnStringValidCountryCodePercentCheckSpec getMonthlyStringValidCountryCodePercent() {
        return monthlyStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param monthlyStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setMonthlyStringValidCountryCodePercent(ColumnStringValidCountryCodePercentCheckSpec monthlyStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringValidCountryCodePercent, monthlyStringValidCountryCodePercent));
        this.monthlyStringValidCountryCodePercent = monthlyStringValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyStringValidCountryCodePercent, "monthly_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent check.
     */
    public ColumnStringValidCurrencyCodePercentCheckSpec getMonthlyStringValidCurrencyCodePercent() {
        return monthlyStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param monthlyStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setMonthlyStringValidCurrencyCodePercent(ColumnStringValidCurrencyCodePercentCheckSpec monthlyStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringValidCurrencyCodePercent, monthlyStringValidCurrencyCodePercent));
        this.monthlyStringValidCurrencyCodePercent = monthlyStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(monthlyStringValidCurrencyCodePercent, "monthly_string_valid_currency_code_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnStringInvalidEmailCountCheckSpec getMonthlyStringInvalidEmailCount() {
        return monthlyStringInvalidEmailCount;
    }

    /**
     * Sets a new definition of a maximum invalid email count check.
     * @param monthlyStringInvalidEmailCount Maximum invalid email count check.
     */
    public void setMonthlyStringInvalidEmailCount(ColumnStringInvalidEmailCountCheckSpec monthlyStringInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringInvalidEmailCount, monthlyStringInvalidEmailCount));
        this.monthlyStringInvalidEmailCount = monthlyStringInvalidEmailCount;
        propagateHierarchyIdToField(monthlyStringInvalidEmailCount, "monthly_string_invalid_email_count");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnStringInvalidUuidCountCheckSpec getMonthlyStringInvalidUuidCount() {
        return monthlyStringInvalidUuidCount;
    }

    /**
     * Sets a new definition of a maximum invalid UUID count check.
     * @param monthlyStringInvalidUuidCount Maximum invalid UUID count check.
     */
    public void setMonthlyStringInvalidUuidCount(ColumnStringInvalidUuidCountCheckSpec monthlyStringInvalidUuidCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringInvalidUuidCount, monthlyStringInvalidUuidCount));
        this.monthlyStringInvalidUuidCount = monthlyStringInvalidUuidCount;
        propagateHierarchyIdToField(monthlyStringInvalidUuidCount, "monthly_string_invalid_uuid_count");
    }

    /**
     * Returns a minimum valid UUID percent check.
     * @return Minimum valid UUID percent check.
     */
    public ColumnStringValidUuidPercentCheckSpec getMonthlyStringValidUuidPercent() {
        return monthlyStringValidUuidPercent;
    }

    /**
     * Sets a new definition of a minimum valid UUID percent check.
     * @param monthlyStringValidUuidPercent Minimum valid UUID percent check.
     */
    public void setMonthlyStringValidUuidPercent(ColumnStringValidUuidPercentCheckSpec monthlyStringValidUuidPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringValidUuidPercent, monthlyStringValidUuidPercent));
        this.monthlyStringValidUuidPercent = monthlyStringValidUuidPercent;
        propagateHierarchyIdToField(monthlyStringValidUuidPercent, "monthly_string_valid_uuid_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnStringInvalidIp4AddressCountCheckSpec getMonthlyStringInvalidIp4AddressCount() {
        return monthlyStringInvalidIp4AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP4 address count check.
     * @param monthlyStringInvalidIp4AddressCount Maximum invalid IP4 address count check.
     */
    public void setMonthlyStringInvalidIp4AddressCount(ColumnStringInvalidIp4AddressCountCheckSpec monthlyStringInvalidIp4AddressCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringInvalidIp4AddressCount, monthlyStringInvalidIp4AddressCount));
        this.monthlyStringInvalidIp4AddressCount = monthlyStringInvalidIp4AddressCount;
        propagateHierarchyIdToField(monthlyStringInvalidIp4AddressCount, "monthly_string_invalid_ip4_address_count");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnStringInvalidIp6AddressCountCheckSpec getMonthlyStringInvalidIp6AddressCount() {
        return monthlyStringInvalidIp6AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP6 address count check.
     * @param monthlyStringInvalidIp6AddressCount Maximum invalid IP6 address count check.
     */
    public void setMonthlyStringInvalidIp6AddressCount(ColumnStringInvalidIp6AddressCountCheckSpec monthlyStringInvalidIp6AddressCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringInvalidIp6AddressCount, monthlyStringInvalidIp6AddressCount));
        this.monthlyStringInvalidIp6AddressCount = monthlyStringInvalidIp6AddressCount;
        propagateHierarchyIdToField(monthlyStringInvalidIp6AddressCount, "monthly_string_invalid_ip6_address_count");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnStringNotMatchRegexCountCheckSpec getMonthlyStringNotMatchRegexCount() {
        return monthlyStringNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param monthlyStringNotMatchRegexCount Maximum not match regex count check.
     */
    public void setMonthlyStringNotMatchRegexCount(ColumnStringNotMatchRegexCountCheckSpec monthlyStringNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringNotMatchRegexCount, monthlyStringNotMatchRegexCount));
        this.monthlyStringNotMatchRegexCount = monthlyStringNotMatchRegexCount;
        propagateHierarchyIdToField(monthlyStringNotMatchRegexCount, "monthly_string_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnStringMatchRegexPercentCheckSpec getMonthlyStringMatchRegexPercent() {
        return monthlyStringMatchRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param monthlyStringMatchRegexPercent Minimum match regex percent check.
     */
    public void setMonthlyStringMatchRegexPercent(ColumnStringMatchRegexPercentCheckSpec monthlyStringMatchRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringMatchRegexPercent, monthlyStringMatchRegexPercent));
        this.monthlyStringMatchRegexPercent = monthlyStringMatchRegexPercent;
        propagateHierarchyIdToField(monthlyStringMatchRegexPercent, "monthly_string_match_regex_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnStringNotMatchDateRegexCountCheckSpec getMonthlyStringNotMatchDateRegexCount() {
        return monthlyStringNotMatchDateRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param monthlyStringNotMatchDateRegexCount Maximum not match date regex count check.
     */
    public void setMonthlyStringNotMatchDateRegexCount(ColumnStringNotMatchDateRegexCountCheckSpec monthlyStringNotMatchDateRegexCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringNotMatchDateRegexCount, monthlyStringNotMatchDateRegexCount));
        this.monthlyStringNotMatchDateRegexCount = monthlyStringNotMatchDateRegexCount;
        propagateHierarchyIdToField(monthlyStringNotMatchDateRegexCount, "monthly_string_not_match_date_regex_count");
    }

    /**
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnStringMatchDateRegexPercentCheckSpec getMonthlyStringMatchDateRegexPercent() {
        return monthlyStringMatchDateRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param monthlyStringMatchDateRegexPercent Maximum match date regex percent check.
     */
    public void setMonthlyStringMatchDateRegexPercent(ColumnStringMatchDateRegexPercentCheckSpec monthlyStringMatchDateRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringMatchDateRegexPercent, monthlyStringMatchDateRegexPercent));
        this.monthlyStringMatchDateRegexPercent = monthlyStringMatchDateRegexPercent;
        propagateHierarchyIdToField(monthlyStringMatchDateRegexPercent, "monthly_string_match_date_regex_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnStringMatchNameRegexPercentCheckSpec getMonthlyStringMatchNameRegexPercent() {
        return monthlyStringMatchNameRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param monthlyStringMatchNameRegexPercent Maximum match name regex percent check.
     */
    public void setMonthlyStringMatchNameRegexPercent(ColumnStringMatchNameRegexPercentCheckSpec monthlyStringMatchNameRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringMatchNameRegexPercent, monthlyStringMatchNameRegexPercent));
        this.monthlyStringMatchNameRegexPercent = monthlyStringMatchNameRegexPercent;
        propagateHierarchyIdToField(monthlyStringMatchNameRegexPercent, "monthly_string_match_name_regex_percent");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedStringsInTopValuesCountCheckSpec getMonthlyExpectedStringsInTopValuesCount() {
        return monthlyExpectedStringsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param monthlyExpectedStringsInTopValuesCount Most popular values count check.
     */
    public void setMonthlyExpectedStringsInTopValuesCount(ColumnExpectedStringsInTopValuesCountCheckSpec monthlyExpectedStringsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyExpectedStringsInTopValuesCount, monthlyExpectedStringsInTopValuesCount));
        this.monthlyExpectedStringsInTopValuesCount = monthlyExpectedStringsInTopValuesCount;
        propagateHierarchyIdToField(monthlyExpectedStringsInTopValuesCount, "monthly_expected_strings_in_top_values_count");
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

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public ColumnStringsMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnStringsMonthlyMonitoringChecksSpec)super.deepClone();
    }

    /**
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.column;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.monitoring;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
    }
}
