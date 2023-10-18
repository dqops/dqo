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
package com.dqops.checks.column.partitioned.strings;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.strings.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of strings data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_string_max_length", o -> o.monthlyPartitionStringMaxLength);
            put("monthly_partition_string_min_length", o -> o.monthlyPartitionStringMinLength);
            put("monthly_partition_string_mean_length", o -> o.monthlyPartitionStringMeanLength);
            put("monthly_partition_string_length_below_min_length_count", o -> o.monthlyPartitionStringLengthBelowMinLengthCount);
            put("monthly_partition_string_length_below_min_length_percent", o -> o.monthlyPartitionStringLengthBelowMinLengthPercent);
            put("monthly_partition_string_length_above_max_length_count", o -> o.monthlyPartitionStringLengthAboveMaxLengthCount);
            put("monthly_partition_string_length_above_max_length_percent", o -> o.monthlyPartitionStringLengthAboveMaxLengthPercent);
            put("monthly_partition_string_length_in_range_percent", o -> o.monthlyPartitionStringLengthInRangePercent);

            put("monthly_partition_string_empty_count", o -> o.monthlyPartitionStringEmptyCount);
            put("monthly_partition_string_empty_percent", o -> o.monthlyPartitionStringEmptyPercent);
            put("monthly_partition_string_whitespace_count", o -> o.monthlyPartitionStringWhitespaceCount);
            put("monthly_partition_string_whitespace_percent", o -> o.monthlyPartitionStringWhitespacePercent);
            put("monthly_partition_string_surrounded_by_whitespace_count", o -> o.monthlyPartitionStringSurroundedByWhitespaceCount);
            put("monthly_partition_string_surrounded_by_whitespace_percent", o -> o.monthlyPartitionStringSurroundedByWhitespacePercent);

            put("monthly_partition_string_null_placeholder_count", o -> o.monthlyPartitionStringNullPlaceholderCount);
            put("monthly_partition_string_null_placeholder_percent", o -> o.monthlyPartitionStringNullPlaceholderPercent);
            put("monthly_partition_string_boolean_placeholder_percent", o -> o.monthlyPartitionStringBooleanPlaceholderPercent);
            put("monthly_partition_string_parsable_to_integer_percent", o -> o.monthlyPartitionStringParsableToIntegerPercent);
            put("monthly_partition_string_parsable_to_float_percent", o -> o.monthlyPartitionStringParsableToFloatPercent);
            
            put("monthly_partition_expected_strings_in_use_count", o -> o.monthlyPartitionExpectedStringsInUseCount);
            put("monthly_partition_string_value_in_set_percent", o -> o.monthlyPartitionStringValueInSetPercent);

            put("monthly_partition_string_valid_dates_percent", o -> o.monthlyPartitionStringValidDatesPercent);
            put("monthly_partition_string_valid_country_code_percent", o -> o.monthlyPartitionStringValidCountryCodePercent);
            put("monthly_partition_string_valid_currency_code_percent", o -> o.monthlyPartitionStringValidCurrencyCodePercent);
            put("monthly_partition_string_invalid_email_count", o -> o.monthlyPartitionStringInvalidEmailCount);
            put("monthly_partition_string_invalid_uuid_count", o -> o.monthlyPartitionStringInvalidUuidCount);
            put("monthly_partition_valid_uuid_percent", o -> o.monthlyPartitionValidUuidPercent);
            put("monthly_partition_string_invalid_ip4_address_count", o -> o.monthlyPartitionStringInvalidIp4AddressCount);
            put("monthly_partition_string_invalid_ip6_address_count", o -> o.monthlyPartitionStringInvalidIp6AddressCount);

            put("monthly_partition_string_not_match_regex_count", o -> o.monthlyPartitionStringNotMatchRegexCount);
            put("monthly_partition_string_match_regex_percent", o -> o.monthlyPartitionStringMatchRegexPercent);
            put("monthly_partition_string_not_match_date_regex_count", o -> o.monthlyPartitionStringNotMatchDateRegexCount);
            put("monthly_partition_string_match_date_regex_percent", o -> o.monthlyPartitionStringMatchDateRegexPercent);
            put("monthly_partition_string_match_name_regex_percent", o -> o.monthlyPartitionStringMatchNameRegexPercent);

            put("monthly_partition_expected_strings_in_top_values_count", o -> o.monthlyPartitionExpectedStringsInTopValuesCount);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringMaxLengthCheckSpec monthlyPartitionStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringMinLengthCheckSpec monthlyPartitionStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringMeanLengthCheckSpec monthlyPartitionStringMeanLength;

    @JsonPropertyDescription("The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringLengthBelowMinLengthCountCheckSpec monthlyPartitionStringLengthBelowMinLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringLengthBelowMinLengthPercentCheckSpec monthlyPartitionStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringLengthAboveMaxLengthCountCheckSpec monthlyPartitionStringLengthAboveMaxLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringLengthAboveMaxLengthPercentCheckSpec monthlyPartitionStringLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringLengthInRangePercentCheckSpec monthlyPartitionStringLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringEmptyCountCheckSpec monthlyPartitionStringEmptyCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringEmptyPercentCheckSpec monthlyPartitionStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringWhitespaceCountCheckSpec monthlyPartitionStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringWhitespacePercentCheckSpec monthlyPartitionStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringSurroundedByWhitespaceCountCheckSpec monthlyPartitionStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringSurroundedByWhitespacePercentCheckSpec monthlyPartitionStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringNullPlaceholderCountCheckSpec monthlyPartitionStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringNullPlaceholderPercentCheckSpec monthlyPartitionStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringBooleanPlaceholderPercentCheckSpec monthlyPartitionStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringParsableToIntegerPercentCheckSpec monthlyPartitionStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringParsableToFloatPercentCheckSpec monthlyPartitionStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnExpectedStringsInUseCountCheckSpec monthlyPartitionExpectedStringsInUseCount;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringValueInSetPercentCheckSpec monthlyPartitionStringValueInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringValidDatesPercentCheckSpec monthlyPartitionStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringValidCountryCodePercentCheckSpec monthlyPartitionStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringValidCurrencyCodePercentCheckSpec monthlyPartitionStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringInvalidEmailCountCheckSpec monthlyPartitionStringInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringInvalidUuidCountCheckSpec monthlyPartitionStringInvalidUuidCount;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringValidUuidPercentCheckSpec monthlyPartitionValidUuidPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringInvalidIp4AddressCountCheckSpec monthlyPartitionStringInvalidIp4AddressCount;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringInvalidIp6AddressCountCheckSpec monthlyPartitionStringInvalidIp6AddressCount;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringNotMatchRegexCountCheckSpec monthlyPartitionStringNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringMatchRegexPercentCheckSpec monthlyPartitionStringMatchRegexPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringNotMatchDateRegexCountCheckSpec monthlyPartitionStringNotMatchDateRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringMatchDateRegexPercentCheckSpec monthlyPartitionStringMatchDateRegexPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringMatchNameRegexPercentCheckSpec monthlyPartitionStringMatchNameRegexPercent;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnExpectedStringsInTopValuesCountCheckSpec monthlyPartitionExpectedStringsInTopValuesCount;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnStringMaxLengthCheckSpec getMonthlyPartitionStringMaxLength() {
        return monthlyPartitionStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param monthlyPartitionStringMaxLength Maximum string length below check.
     */
    public void setMonthlyPartitionStringMaxLength(ColumnStringMaxLengthCheckSpec monthlyPartitionStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringMaxLength, monthlyPartitionStringMaxLength));
        this.monthlyPartitionStringMaxLength = monthlyPartitionStringMaxLength;
        propagateHierarchyIdToField(monthlyPartitionStringMaxLength, "monthly_partition_string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnStringMinLengthCheckSpec getMonthlyPartitionStringMinLength() {
        return monthlyPartitionStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param monthlyPartitionStringMinLength Minimum string length above check.
     */
    public void setMonthlyPartitionStringMinLength(ColumnStringMinLengthCheckSpec monthlyPartitionStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringMinLength, monthlyPartitionStringMinLength));
        this.monthlyPartitionStringMinLength = monthlyPartitionStringMinLength;
        propagateHierarchyIdToField(monthlyPartitionStringMinLength, "monthly_partition_string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnStringMeanLengthCheckSpec getMonthlyPartitionStringMeanLength() {
        return monthlyPartitionStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param monthlyPartitionStringMeanLength Mean string length between check.
     */
    public void setMonthlyPartitionStringMeanLength(ColumnStringMeanLengthCheckSpec monthlyPartitionStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringMeanLength, monthlyPartitionStringMeanLength));
        this.monthlyPartitionStringMeanLength = monthlyPartitionStringMeanLength;
        propagateHierarchyIdToField(monthlyPartitionStringMeanLength, "monthly_partition_string_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnStringLengthBelowMinLengthCountCheckSpec getMonthlyPartitionStringLengthBelowMinLengthCount() {
        return monthlyPartitionStringLengthBelowMinLengthCount;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param monthlyPartitionStringLengthBelowMinLengthCount String length below min length count check.
     */
    public void setMonthlyPartitionStringLengthBelowMinLengthCount(ColumnStringLengthBelowMinLengthCountCheckSpec monthlyPartitionStringLengthBelowMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthBelowMinLengthCount, monthlyPartitionStringLengthBelowMinLengthCount));
        this.monthlyPartitionStringLengthBelowMinLengthCount = monthlyPartitionStringLengthBelowMinLengthCount;
        propagateHierarchyIdToField(monthlyPartitionStringLengthBelowMinLengthCount, "monthly_partition_string_length_below_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return Mean string length below min length percent check.
     */
    public ColumnStringLengthBelowMinLengthPercentCheckSpec getMonthlyPartitionStringLengthBelowMinLengthPercent() {
        return monthlyPartitionStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param monthlyPartitionStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setMonthlyPartitionStringLengthBelowMinLengthPercent(ColumnStringLengthBelowMinLengthPercentCheckSpec monthlyPartitionStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthBelowMinLengthPercent, monthlyPartitionStringLengthBelowMinLengthPercent));
        this.monthlyPartitionStringLengthBelowMinLengthPercent = monthlyPartitionStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(monthlyPartitionStringLengthBelowMinLengthPercent, "monthly_partition_string_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return Mean string length above max length count check.
     */
    public ColumnStringLengthAboveMaxLengthCountCheckSpec getMonthlyPartitionStringLengthAboveMaxLengthCount() {
        return monthlyPartitionStringLengthAboveMaxLengthCount;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param monthlyPartitionStringLengthAboveMaxLengthCount String length above max length count check.
     */
    public void setMonthlyPartitionStringLengthAboveMaxLengthCount(ColumnStringLengthAboveMaxLengthCountCheckSpec monthlyPartitionStringLengthAboveMaxLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthAboveMaxLengthCount, monthlyPartitionStringLengthAboveMaxLengthCount));
        this.monthlyPartitionStringLengthAboveMaxLengthCount = monthlyPartitionStringLengthAboveMaxLengthCount;
        propagateHierarchyIdToField(monthlyPartitionStringLengthAboveMaxLengthCount, "monthly_partition_string_length_above_max_length_count");
    }

    /**
     * Returns a string length above max length percent check.
     * @return Mean string length above max length percent check.
     */
    public ColumnStringLengthAboveMaxLengthPercentCheckSpec getMonthlyPartitionStringLengthAboveMaxLengthPercent() {
        return monthlyPartitionStringLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param monthlyPartitionStringLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setMonthlyPartitionStringLengthAboveMaxLengthPercent(ColumnStringLengthAboveMaxLengthPercentCheckSpec monthlyPartitionStringLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthAboveMaxLengthPercent, monthlyPartitionStringLengthAboveMaxLengthPercent));
        this.monthlyPartitionStringLengthAboveMaxLengthPercent = monthlyPartitionStringLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(monthlyPartitionStringLengthAboveMaxLengthPercent, "monthly_partition_string_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return Mean string length in range percent check.
     */
    public ColumnStringLengthInRangePercentCheckSpec getMonthlyPartitionStringLengthInRangePercent() {
        return monthlyPartitionStringLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param monthlyPartitionStringLengthInRangePercent String length in range percent check.
     */
    public void setMonthlyPartitionStringLengthInRangePercent(ColumnStringLengthInRangePercentCheckSpec monthlyPartitionStringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthInRangePercent, monthlyPartitionStringLengthInRangePercent));
        this.monthlyPartitionStringLengthInRangePercent = monthlyPartitionStringLengthInRangePercent;
        propagateHierarchyIdToField(monthlyPartitionStringLengthInRangePercent, "monthly_partition_string_length_in_range_percent");
    }


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnStringEmptyCountCheckSpec getMonthlyPartitionStringEmptyCount() {
        return monthlyPartitionStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyPartitionStringEmptyCount Max string empty count check.
     */
    public void setMonthlyPartitionStringEmptyCount(ColumnStringEmptyCountCheckSpec monthlyPartitionStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringEmptyCount, monthlyPartitionStringEmptyCount));
        this.monthlyPartitionStringEmptyCount = monthlyPartitionStringEmptyCount;
        propagateHierarchyIdToField(monthlyPartitionStringEmptyCount, "monthly_partition_string_empty_count");
    }

    /**
     * Returns a maximum empty string percentage check.
     * @return Maximum empty string percentage check.
     */
    public ColumnStringEmptyPercentCheckSpec getMonthlyPartitionStringEmptyPercent() {
        return monthlyPartitionStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum empty string percentage check.
     * @param monthlyPartitionStringEmptyPercent Maximum empty string percentage check.
     */
    public void setMonthlyPartitionStringEmptyPercent(ColumnStringEmptyPercentCheckSpec monthlyPartitionStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringEmptyPercent, monthlyPartitionStringEmptyPercent));
        this.monthlyPartitionStringEmptyPercent = monthlyPartitionStringEmptyPercent;
        propagateHierarchyIdToField(monthlyPartitionStringEmptyPercent, "monthly_partition_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnStringWhitespaceCountCheckSpec getMonthlyPartitionStringWhitespaceCount() {
        return monthlyPartitionStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyPartitionStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setMonthlyPartitionStringWhitespaceCount(ColumnStringWhitespaceCountCheckSpec monthlyPartitionStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringWhitespaceCount, monthlyPartitionStringWhitespaceCount));
        this.monthlyPartitionStringWhitespaceCount = monthlyPartitionStringWhitespaceCount;
        propagateHierarchyIdToField(monthlyPartitionStringWhitespaceCount, "monthly_partition_string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnStringWhitespacePercentCheckSpec getMonthlyPartitionStringWhitespacePercent() {
        return monthlyPartitionStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyPartitionStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setMonthlyPartitionStringWhitespacePercent(ColumnStringWhitespacePercentCheckSpec monthlyPartitionStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringWhitespacePercent, monthlyPartitionStringWhitespacePercent));
        this.monthlyPartitionStringWhitespacePercent = monthlyPartitionStringWhitespacePercent;
        propagateHierarchyIdToField(monthlyPartitionStringWhitespacePercent, "monthly_partition_string_whitespace_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     *
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnStringSurroundedByWhitespaceCountCheckSpec getMonthlyPartitionStringSurroundedByWhitespaceCount() {
        return monthlyPartitionStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     *
     * @param monthlyPartitionStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setMonthlyPartitionStringSurroundedByWhitespaceCount(ColumnStringSurroundedByWhitespaceCountCheckSpec monthlyPartitionStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringSurroundedByWhitespaceCount, monthlyPartitionStringSurroundedByWhitespaceCount));
        this.monthlyPartitionStringSurroundedByWhitespaceCount = monthlyPartitionStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(monthlyPartitionStringSurroundedByWhitespaceCount, "monthly_partition_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     *
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnStringSurroundedByWhitespacePercentCheckSpec getMonthlyPartitionStringSurroundedByWhitespacePercent() {
        return monthlyPartitionStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     *
     * @param monthlyPartitionStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setMonthlyPartitionStringSurroundedByWhitespacePercent(ColumnStringSurroundedByWhitespacePercentCheckSpec monthlyPartitionStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringSurroundedByWhitespacePercent, monthlyPartitionStringSurroundedByWhitespacePercent));
        this.monthlyPartitionStringSurroundedByWhitespacePercent = monthlyPartitionStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyPartitionStringSurroundedByWhitespacePercent, "monthly_partition_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnStringNullPlaceholderCountCheckSpec getMonthlyPartitionStringNullPlaceholderCount() {
        return monthlyPartitionStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyPartitionStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setMonthlyPartitionStringNullPlaceholderCount(ColumnStringNullPlaceholderCountCheckSpec monthlyPartitionStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringNullPlaceholderCount, monthlyPartitionStringNullPlaceholderCount));
        this.monthlyPartitionStringNullPlaceholderCount = monthlyPartitionStringNullPlaceholderCount;
        propagateHierarchyIdToField(monthlyPartitionStringNullPlaceholderCount, "monthly_partition_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnStringNullPlaceholderPercentCheckSpec getMonthlyPartitionStringNullPlaceholderPercent() {
        return monthlyPartitionStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyPartitionStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyPartitionStringNullPlaceholderPercent(ColumnStringNullPlaceholderPercentCheckSpec monthlyPartitionStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringNullPlaceholderPercent, monthlyPartitionStringNullPlaceholderPercent));
        this.monthlyPartitionStringNullPlaceholderPercent = monthlyPartitionStringNullPlaceholderPercent;
        propagateHierarchyIdToField(monthlyPartitionStringNullPlaceholderPercent, "monthly_partition_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnStringBooleanPlaceholderPercentCheckSpec getMonthlyPartitionStringBooleanPlaceholderPercent() {
        return monthlyPartitionStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param monthlyPartitionStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setMonthlyPartitionStringBooleanPlaceholderPercent(ColumnStringBooleanPlaceholderPercentCheckSpec monthlyPartitionStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringBooleanPlaceholderPercent, monthlyPartitionStringBooleanPlaceholderPercent));
        this.monthlyPartitionStringBooleanPlaceholderPercent = monthlyPartitionStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(monthlyPartitionStringBooleanPlaceholderPercent, "monthly_partition_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent  check.
     */
    public ColumnStringParsableToIntegerPercentCheckSpec getMonthlyPartitionStringParsableToIntegerPercent() {
        return monthlyPartitionStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param monthlyPartitionStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setMonthlyPartitionStringParsableToIntegerPercent(ColumnStringParsableToIntegerPercentCheckSpec monthlyPartitionStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringParsableToIntegerPercent, monthlyPartitionStringParsableToIntegerPercent));
        this.monthlyPartitionStringParsableToIntegerPercent = monthlyPartitionStringParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyPartitionStringParsableToIntegerPercent, "monthly_partition_string_parsable_to_integer_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent  check.
     */
    public ColumnStringParsableToFloatPercentCheckSpec getMonthlyPartitionStringParsableToFloatPercent() {
        return monthlyPartitionStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param monthlyPartitionStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setMonthlyPartitionStringParsableToFloatPercent(ColumnStringParsableToFloatPercentCheckSpec monthlyPartitionStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringParsableToFloatPercent, monthlyPartitionStringParsableToFloatPercent));
        this.monthlyPartitionStringParsableToFloatPercent = monthlyPartitionStringParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyPartitionStringParsableToFloatPercent, "monthly_partition_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnExpectedStringsInUseCountCheckSpec getMonthlyPartitionExpectedStringsInUseCount() {
        return monthlyPartitionExpectedStringsInUseCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param monthlyPartitionExpectedStringsInUseCount Minimum strings in set count check.
     */
    public void setMonthlyPartitionExpectedStringsInUseCount(ColumnExpectedStringsInUseCountCheckSpec monthlyPartitionExpectedStringsInUseCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionExpectedStringsInUseCount, monthlyPartitionExpectedStringsInUseCount));
        this.monthlyPartitionExpectedStringsInUseCount = monthlyPartitionExpectedStringsInUseCount;
        propagateHierarchyIdToField(monthlyPartitionExpectedStringsInUseCount, "monthly_partition_expected_strings_in_use_count");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringValueInSetPercentCheckSpec getMonthlyPartitionStringValueInSetPercent() {
        return monthlyPartitionStringValueInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param monthlyPartitionStringValueInSetPercent Minimum strings in set percent check.
     */
    public void setMonthlyPartitionStringValueInSetPercent(ColumnStringValueInSetPercentCheckSpec monthlyPartitionStringValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValueInSetPercent, monthlyPartitionStringValueInSetPercent));
        this.monthlyPartitionStringValueInSetPercent = monthlyPartitionStringValueInSetPercent;
        propagateHierarchyIdToField(monthlyPartitionStringValueInSetPercent, "monthly_partition_string_value_in_set_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnStringValidDatesPercentCheckSpec getMonthlyPartitionStringValidDatesPercent() {
        return monthlyPartitionStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param monthlyPartitionStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setMonthlyPartitionStringValidDatesPercent(ColumnStringValidDatesPercentCheckSpec monthlyPartitionStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValidDatesPercent, monthlyPartitionStringValidDatesPercent));
        this.monthlyPartitionStringValidDatesPercent = monthlyPartitionStringValidDatesPercent;
        propagateHierarchyIdToField(monthlyPartitionStringValidDatesPercent, "monthly_partition_string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent  check.
     */
    public ColumnStringValidCountryCodePercentCheckSpec getMonthlyPartitionStringValidCountryCodePercent() {
        return monthlyPartitionStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param monthlyPartitionStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setMonthlyPartitionStringValidCountryCodePercent(ColumnStringValidCountryCodePercentCheckSpec monthlyPartitionStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValidCountryCodePercent, monthlyPartitionStringValidCountryCodePercent));
        this.monthlyPartitionStringValidCountryCodePercent = monthlyPartitionStringValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyPartitionStringValidCountryCodePercent, "monthly_partition_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent  check.
     */
    public ColumnStringValidCurrencyCodePercentCheckSpec getMonthlyPartitionStringValidCurrencyCodePercent() {
        return monthlyPartitionStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param monthlyPartitionStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setMonthlyPartitionStringValidCurrencyCodePercent(ColumnStringValidCurrencyCodePercentCheckSpec monthlyPartitionStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValidCurrencyCodePercent, monthlyPartitionStringValidCurrencyCodePercent));
        this.monthlyPartitionStringValidCurrencyCodePercent = monthlyPartitionStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(monthlyPartitionStringValidCurrencyCodePercent, "monthly_partition_string_valid_currency_code_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnStringInvalidEmailCountCheckSpec getMonthlyPartitionStringInvalidEmailCount() {
        return monthlyPartitionStringInvalidEmailCount;
    }

    /**
     * Sets a new definition of a maximum invalid email count check.
     * @param monthlyPartitionStringInvalidEmailCount Maximum invalid email count check.
     */
    public void setMonthlyPartitionStringInvalidEmailCount(ColumnStringInvalidEmailCountCheckSpec monthlyPartitionStringInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringInvalidEmailCount, monthlyPartitionStringInvalidEmailCount));
        this.monthlyPartitionStringInvalidEmailCount = monthlyPartitionStringInvalidEmailCount;
        propagateHierarchyIdToField(monthlyPartitionStringInvalidEmailCount, "monthly_partition_string_invalid_email_count");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnStringInvalidUuidCountCheckSpec getMonthlyPartitionStringInvalidUuidCount() {
        return monthlyPartitionStringInvalidUuidCount;
    }

    /**
     * Sets a new definition of a maximum invalid UUID count check.
     * @param monthlyPartitionStringInvalidUuidCount Maximum invalid UUID count check.
     */
    public void setMonthlyPartitionStringInvalidUuidCount(ColumnStringInvalidUuidCountCheckSpec monthlyPartitionStringInvalidUuidCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringInvalidUuidCount, monthlyPartitionStringInvalidUuidCount));
        this.monthlyPartitionStringInvalidUuidCount = monthlyPartitionStringInvalidUuidCount;
        propagateHierarchyIdToField(monthlyPartitionStringInvalidUuidCount, "monthly_partition_string_invalid_uuid_count");
    }

    /**
     * Returns a minimum valid UUID percent check.
     * @return Minimum valid UUID percent check.
     */
    public ColumnStringValidUuidPercentCheckSpec getMonthlyPartitionValidUuidPercent() {
        return monthlyPartitionValidUuidPercent;
    }

    /**
     * Sets a new definition of a minimum valid UUID percent check.
     * @param monthlyPartitionValidUuidPercent Minimum valid UUID percent check.
     */
    public void setMonthlyPartitionValidUuidPercent(ColumnStringValidUuidPercentCheckSpec monthlyPartitionValidUuidPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValidUuidPercent, monthlyPartitionValidUuidPercent));
        this.monthlyPartitionValidUuidPercent = monthlyPartitionValidUuidPercent;
        propagateHierarchyIdToField(monthlyPartitionValidUuidPercent, "monthly_partition_valid_uuid_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnStringInvalidIp4AddressCountCheckSpec getMonthlyPartitionStringInvalidIp4AddressCount() {
        return monthlyPartitionStringInvalidIp4AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP4 address count check.
     * @param monthlyPartitionStringInvalidIp4AddressCount Maximum invalid IP4 address count check.
     */
    public void setMonthlyPartitionStringInvalidIp4AddressCount(ColumnStringInvalidIp4AddressCountCheckSpec monthlyPartitionStringInvalidIp4AddressCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringInvalidIp4AddressCount, monthlyPartitionStringInvalidIp4AddressCount));
        this.monthlyPartitionStringInvalidIp4AddressCount = monthlyPartitionStringInvalidIp4AddressCount;
        propagateHierarchyIdToField(monthlyPartitionStringInvalidIp4AddressCount, "monthly_partition_string_invalid_ip4_address_count");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnStringInvalidIp6AddressCountCheckSpec getMonthlyPartitionStringInvalidIp6AddressCount() {
        return monthlyPartitionStringInvalidIp6AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP6 address count check.
     * @param monthlyPartitionStringInvalidIp6AddressCount Maximum invalid IP6 address count check.
     */
    public void setMonthlyPartitionStringInvalidIp6AddressCount(ColumnStringInvalidIp6AddressCountCheckSpec monthlyPartitionStringInvalidIp6AddressCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringInvalidIp6AddressCount, monthlyPartitionStringInvalidIp6AddressCount));
        this.monthlyPartitionStringInvalidIp6AddressCount = monthlyPartitionStringInvalidIp6AddressCount;
        propagateHierarchyIdToField(monthlyPartitionStringInvalidIp6AddressCount, "monthly_partition_string_invalid_ip6_address_count");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnStringNotMatchRegexCountCheckSpec getMonthlyPartitionStringNotMatchRegexCount() {
        return monthlyPartitionStringNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param monthlyPartitionStringNotMatchRegexCount Maximum not match regex count check.
     */
    public void setMonthlyPartitionStringNotMatchRegexCount(ColumnStringNotMatchRegexCountCheckSpec monthlyPartitionStringNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringNotMatchRegexCount, monthlyPartitionStringNotMatchRegexCount));
        this.monthlyPartitionStringNotMatchRegexCount = monthlyPartitionStringNotMatchRegexCount;
        propagateHierarchyIdToField(monthlyPartitionStringNotMatchRegexCount, "monthly_partition_string_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnStringMatchRegexPercentCheckSpec getMonthlyPartitionStringMatchRegexPercent() {
        return monthlyPartitionStringMatchRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param monthlyPartitionStringMatchRegexPercent Minimum match regex percent check.
     */
    public void setMonthlyPartitionStringMatchRegexPercent(ColumnStringMatchRegexPercentCheckSpec monthlyPartitionStringMatchRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringMatchRegexPercent, monthlyPartitionStringMatchRegexPercent));
        this.monthlyPartitionStringMatchRegexPercent = monthlyPartitionStringMatchRegexPercent;
        propagateHierarchyIdToField(monthlyPartitionStringMatchRegexPercent, "monthly_partition_string_match_regex_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnStringNotMatchDateRegexCountCheckSpec getMonthlyPartitionStringNotMatchDateRegexCount() {
        return monthlyPartitionStringNotMatchDateRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param monthlyPartitionStringNotMatchDateRegexCount Maximum not match date regex count check.
     */
    public void setMonthlyPartitionStringNotMatchDateRegexCount(ColumnStringNotMatchDateRegexCountCheckSpec monthlyPartitionStringNotMatchDateRegexCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringNotMatchDateRegexCount, monthlyPartitionStringNotMatchDateRegexCount));
        this.monthlyPartitionStringNotMatchDateRegexCount = monthlyPartitionStringNotMatchDateRegexCount;
        propagateHierarchyIdToField(monthlyPartitionStringNotMatchDateRegexCount, "monthly_partition_string_not_match_date_regex_count");
    }

    /**
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnStringMatchDateRegexPercentCheckSpec getMonthlyPartitionStringMatchDateRegexPercent() {
        return monthlyPartitionStringMatchDateRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param monthlyPartitionStringMatchDateRegexPercent Maximum match date regex percent check.
     */
    public void setMonthlyPartitionStringMatchDateRegexPercent(ColumnStringMatchDateRegexPercentCheckSpec monthlyPartitionStringMatchDateRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringMatchDateRegexPercent, monthlyPartitionStringMatchDateRegexPercent));
        this.monthlyPartitionStringMatchDateRegexPercent = monthlyPartitionStringMatchDateRegexPercent;
        propagateHierarchyIdToField(monthlyPartitionStringMatchDateRegexPercent, "monthly_partition_string_match_date_regex_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnStringMatchNameRegexPercentCheckSpec getMonthlyPartitionStringMatchNameRegexPercent() {
        return monthlyPartitionStringMatchNameRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param monthlyPartitionStringMatchNameRegexPercent Maximum match name regex percent check.
     */
    public void setMonthlyPartitionStringMatchNameRegexPercent(ColumnStringMatchNameRegexPercentCheckSpec monthlyPartitionStringMatchNameRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringMatchNameRegexPercent, monthlyPartitionStringMatchNameRegexPercent));
        this.monthlyPartitionStringMatchNameRegexPercent = monthlyPartitionStringMatchNameRegexPercent;
        propagateHierarchyIdToField(monthlyPartitionStringMatchNameRegexPercent, "monthly_partition_string_match_name_regex_percent");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedStringsInTopValuesCountCheckSpec getMonthlyPartitionExpectedStringsInTopValuesCount() {
        return monthlyPartitionExpectedStringsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param monthlyPartitionExpectedStringsInTopValuesCount Most popular values count check.
     */
    public void setMonthlyPartitionExpectedStringsInTopValuesCount(ColumnExpectedStringsInTopValuesCountCheckSpec monthlyPartitionExpectedStringsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionExpectedStringsInTopValuesCount, monthlyPartitionExpectedStringsInTopValuesCount));
        this.monthlyPartitionExpectedStringsInTopValuesCount = monthlyPartitionExpectedStringsInTopValuesCount;
        propagateHierarchyIdToField(monthlyPartitionExpectedStringsInTopValuesCount, "monthly_partition_expected_strings_in_top_values_count");
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
