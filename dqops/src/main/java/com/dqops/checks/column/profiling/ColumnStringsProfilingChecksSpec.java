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
package com.dqops.checks.column.profiling;

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
 * Container of built-in preconfigured data quality checks on a column level that are checking for string.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_string_max_length", o -> o.profileStringMaxLength);
            put("profile_string_min_length", o -> o.profileStringMinLength);
            put("profile_string_mean_length", o -> o.profileStringMeanLength);
            put("profile_string_length_below_min_length_count", o -> o.profileStringLengthBelowMinLengthCount);
            put("profile_string_length_below_min_length_percent", o -> o.profileStringLengthBelowMinLengthPercent);
            put("profile_string_length_above_max_length_count", o -> o.profileStringLengthAboveMaxLengthCount);
            put("profile_string_length_above_max_length_percent", o -> o.profileStringLengthAboveMaxLengthPercent);
            put("profile_string_length_in_range_percent", o -> o.profileStringLengthInRangePercent);

            put("profile_string_empty_count", o -> o.profileStringEmptyCount);
            put("profile_string_empty_percent", o -> o.profileStringEmptyPercent);
            put("profile_string_whitespace_count", o -> o.profileStringWhitespaceCount);
            put("profile_string_whitespace_percent", o -> o.profileStringWhitespacePercent);
            put("profile_string_surrounded_by_whitespace_count", o -> o.profileStringSurroundedByWhitespaceCount);
            put("profile_string_surrounded_by_whitespace_percent", o -> o.profileStringSurroundedByWhitespacePercent);

            put("profile_string_null_placeholder_count", o -> o.profileStringNullPlaceholderCount);
            put("profile_string_null_placeholder_percent", o -> o.profileStringNullPlaceholderPercent);
            put("profile_string_boolean_placeholder_percent", o -> o.profileStringBooleanPlaceholderPercent);
            put("profile_string_parsable_to_integer_percent", o -> o.profileStringParsableToIntegerPercent);
            put("profile_string_parsable_to_float_percent", o -> o.profileStringParsableToFloatPercent);

            put("profile_expected_strings_in_use_count", o -> o.profileExpectedStringsInUseCount);
            put("profile_string_value_in_set_percent", o -> o.profileStringValueInSetPercent);

            put("profile_string_valid_dates_percent", o -> o.profileStringValidDatesPercent);
            put("profile_string_valid_country_code_percent", o -> o.profileStringValidCountryCodePercent);
            put("profile_string_valid_currency_code_percent", o -> o.profileStringValidCurrencyCodePercent);
            put("profile_string_invalid_email_count", o -> o.profileStringInvalidEmailCount);
            put("profile_string_invalid_uuid_count", o -> o.profileStringInvalidUuidCount);
            put("profile_string_valid_uuid_percent", o -> o.profileStringValidUuidPercent);
            put("profile_string_invalid_ip4_address_count", o -> o.profileStringInvalidIp4AddressCount);
            put("profile_string_invalid_ip6_address_count", o -> o.profileStringInvalidIp6AddressCount);

            put("profile_string_not_match_regex_count", o -> o.profileStringNotMatchRegexCount);
            put("profile_string_match_regex_percent", o -> o.profileStringMatchRegexPercent);
            put("profile_string_not_match_date_regex_count", o -> o.profileStringNotMatchDateRegexCount);
            put("profile_string_match_date_regex_percent", o -> o.profileStringMatchDateRegexPercent);
            put("profile_string_match_name_regex_percent", o -> o.profileStringMatchNameRegexPercent);

            put("profile_expected_strings_in_top_values_count", o -> o.profileExpectedStringsInTopValuesCount);

            put("profile_string_datatype_detected", o -> o.profileStringDatatypeDetected);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length.")
    private ColumnStringMaxLengthCheckSpec profileStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not fall below the minimum accepted length.")
    private ColumnStringMinLengthCheckSpec profileStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length.")
    private ColumnStringMeanLengthCheckSpec profileStringMeanLength;

    @JsonPropertyDescription("The check counts the number of strings in the column that is below the length defined by the user as a parameter.")
    private ColumnStringLengthBelowMinLengthCountCheckSpec profileStringLengthBelowMinLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is below the length defined by the user as a parameter.")
    private ColumnStringLengthBelowMinLengthPercentCheckSpec profileStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of strings in the column that is above the length defined by the user as a parameter.")
    private ColumnStringLengthAboveMaxLengthCountCheckSpec profileStringLengthAboveMaxLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is above the length defined by the user as a parameter.")
    private ColumnStringLengthAboveMaxLengthPercentCheckSpec profileStringLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check counts the percentage of those strings with length in the range provided by the user in the column. ")
    private ColumnStringLengthInRangePercentCheckSpec profileStringLengthInRangePercent;

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted count.")
    private ColumnStringEmptyCountCheckSpec profileStringEmptyCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.")
    private ColumnStringEmptyPercentCheckSpec profileStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.")
    private ColumnStringWhitespaceCountCheckSpec profileStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.")
    private ColumnStringWhitespacePercentCheckSpec profileStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.")
    private ColumnStringSurroundedByWhitespaceCountCheckSpec profileStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.")
    private ColumnStringSurroundedByWhitespacePercentCheckSpec profileStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.")
    private ColumnStringNullPlaceholderCountCheckSpec profileStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.")
    private ColumnStringNullPlaceholderPercentCheckSpec profileStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage.")
    private ColumnStringBooleanPlaceholderPercentCheckSpec profileStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage.")
    private ColumnStringParsableToIntegerPercentCheckSpec profileStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage.")
    private ColumnStringParsableToFloatPercentCheckSpec profileStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).")
    private ColumnExpectedStringsInUseCountCheckSpec profileExpectedStringsInUseCount;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.")
    private ColumnStringValueInSetPercentCheckSpec profileStringValueInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage.")
    private ColumnStringValidDatesPercentCheckSpec profileStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.")
    private ColumnStringValidCountryCodePercentCheckSpec profileStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage.")
    private ColumnStringValidCurrencyCodePercentCheckSpec profileStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.")
    private ColumnStringInvalidEmailCountCheckSpec profileStringInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count.")
    private ColumnStringInvalidUuidCountCheckSpec profileStringInvalidUuidCount;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage.")
    private ColumnStringValidUuidPercentCheckSpec profileStringValidUuidPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.")
    private ColumnStringInvalidIp4AddressCountCheckSpec profileStringInvalidIp4AddressCount;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count.")
    private ColumnStringInvalidIp6AddressCountCheckSpec profileStringInvalidIp6AddressCount;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.")
    private ColumnStringNotMatchRegexCountCheckSpec profileStringNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage.")
    private ColumnStringMatchRegexPercentCheckSpec profileStringMatchRegexPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.")
    private ColumnStringNotMatchDateRegexCountCheckSpec profileStringNotMatchDateRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage.")
    private ColumnStringMatchDateRegexPercentCheckSpec profileStringMatchDateRegexPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the name regex in a column does not fall below the minimum accepted percentage.")
    private ColumnStringMatchNameRegexPercentCheckSpec profileStringMatchNameRegexPercent;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values.")
    private ColumnExpectedStringsInTopValuesCountCheckSpec profileExpectedStringsInTopValuesCount;

    @JsonPropertyDescription("Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.")
    private ColumnStringDatatypeDetectedCheckSpec profileStringDatatypeDetected;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnStringMaxLengthCheckSpec getProfileStringMaxLength() {
        return profileStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param profileStringMaxLength Maximum string length check.
     */
    public void setProfileStringMaxLength(ColumnStringMaxLengthCheckSpec profileStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.profileStringMaxLength, profileStringMaxLength));
        this.profileStringMaxLength = profileStringMaxLength;
        propagateHierarchyIdToField(profileStringMaxLength, "profile_string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnStringMinLengthCheckSpec getProfileStringMinLength() {
        return profileStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param profileStringMinLength Minimum string length check.
     */
    public void setProfileStringMinLength(ColumnStringMinLengthCheckSpec profileStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.profileStringMinLength, profileStringMinLength));
        this.profileStringMinLength = profileStringMinLength;
        propagateHierarchyIdToField(profileStringMinLength, "profile_string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnStringMeanLengthCheckSpec getProfileStringMeanLength() {
        return profileStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param profileStringMeanLength Mean string length check.
     */
    public void setProfileStringMeanLength(ColumnStringMeanLengthCheckSpec profileStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.profileStringMeanLength, profileStringMeanLength));
        this.profileStringMeanLength = profileStringMeanLength;
        propagateHierarchyIdToField(profileStringMeanLength, "profile_string_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnStringLengthBelowMinLengthCountCheckSpec getProfileStringLengthBelowMinLengthCount() {
        return profileStringLengthBelowMinLengthCount;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param profileStringLengthBelowMinLengthCount String length below min length count check.
     */
    public void setProfileStringLengthBelowMinLengthCount(ColumnStringLengthBelowMinLengthCountCheckSpec profileStringLengthBelowMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthBelowMinLengthCount, profileStringLengthBelowMinLengthCount));
        this.profileStringLengthBelowMinLengthCount = profileStringLengthBelowMinLengthCount;
        propagateHierarchyIdToField(profileStringLengthBelowMinLengthCount, "profile_string_length_below_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnStringLengthBelowMinLengthPercentCheckSpec getProfileStringLengthBelowMinLengthPercent() {
        return profileStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param profileStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setProfileStringLengthBelowMinLengthPercent(ColumnStringLengthBelowMinLengthPercentCheckSpec profileStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthBelowMinLengthPercent, profileStringLengthBelowMinLengthPercent));
        this.profileStringLengthBelowMinLengthPercent = profileStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(profileStringLengthBelowMinLengthPercent, "profile_string_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnStringLengthAboveMaxLengthCountCheckSpec getProfileStringLengthAboveMaxLengthCount() {
        return profileStringLengthAboveMaxLengthCount;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param profileStringLengthAboveMaxLengthCount String length above max length count check.
     */
    public void setProfileStringLengthAboveMaxLengthCount(ColumnStringLengthAboveMaxLengthCountCheckSpec profileStringLengthAboveMaxLengthCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthAboveMaxLengthCount, profileStringLengthAboveMaxLengthCount));
        this.profileStringLengthAboveMaxLengthCount = profileStringLengthAboveMaxLengthCount;
        propagateHierarchyIdToField(profileStringLengthAboveMaxLengthCount, "profile_string_length_above_max_length_count");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnStringLengthAboveMaxLengthPercentCheckSpec getProfileStringLengthAboveMaxLengthPercent() {
        return profileStringLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param profileStringLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setProfileStringLengthAboveMaxLengthPercent(ColumnStringLengthAboveMaxLengthPercentCheckSpec profileStringLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthAboveMaxLengthPercent, profileStringLengthAboveMaxLengthPercent));
        this.profileStringLengthAboveMaxLengthPercent = profileStringLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(profileStringLengthAboveMaxLengthPercent, "profile_string_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnStringLengthInRangePercentCheckSpec getProfileStringLengthInRangePercent() {
        return profileStringLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param profileStringLengthInRangePercent String length in range percent check.
     */
    public void setProfileStringLengthInRangePercent(ColumnStringLengthInRangePercentCheckSpec profileStringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthInRangePercent, profileStringLengthInRangePercent));
        this.profileStringLengthInRangePercent = profileStringLengthInRangePercent;
        propagateHierarchyIdToField(profileStringLengthInRangePercent, "profile_string_length_in_range_percent");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnStringEmptyCountCheckSpec getProfileStringEmptyCount() {
        return profileStringEmptyCount;
    }

    /**
     * Sets a new definition of a string empty count check.
     * @param profileStringEmptyCount String empty count check.
     */
    public void setProfileStringEmptyCount(ColumnStringEmptyCountCheckSpec profileStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringEmptyCount, profileStringEmptyCount));
        this.profileStringEmptyCount = profileStringEmptyCount;
        propagateHierarchyIdToField(profileStringEmptyCount, "profile_string_empty_count");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnStringEmptyPercentCheckSpec getProfileStringEmptyPercent() {
        return profileStringEmptyPercent;
    }

    /**
     * Sets a new definition of a string empty percent check.
     * @param profileStringEmptyPercent String empty percent check.
     */
    public void setProfileStringEmptyPercent(ColumnStringEmptyPercentCheckSpec profileStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringEmptyPercent, profileStringEmptyPercent));
        this.profileStringEmptyPercent = profileStringEmptyPercent;
        propagateHierarchyIdToField(profileStringEmptyPercent, "profile_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnStringWhitespaceCountCheckSpec getProfileStringWhitespaceCount() {
        return profileStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a string whitespace count check.
     * @param profileStringWhitespaceCount String whitespace count check.
     */
    public void setProfileStringWhitespaceCount(ColumnStringWhitespaceCountCheckSpec profileStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringWhitespaceCount, profileStringWhitespaceCount));
        this.profileStringWhitespaceCount = profileStringWhitespaceCount;
        propagateHierarchyIdToField(profileStringWhitespaceCount, "profile_string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnStringWhitespacePercentCheckSpec getProfileStringWhitespacePercent() {
        return profileStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a string whitespace percent check.
     * @param profileStringWhitespacePercent String whitespace percent check.
     */
    public void setProfileStringWhitespacePercent(ColumnStringWhitespacePercentCheckSpec profileStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringWhitespacePercent, profileStringWhitespacePercent));
        this.profileStringWhitespacePercent = profileStringWhitespacePercent;
        propagateHierarchyIdToField(profileStringWhitespacePercent, "profile_string_whitespace_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnStringSurroundedByWhitespaceCountCheckSpec getProfileStringSurroundedByWhitespaceCount() {
        return profileStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param profileStringSurroundedByWhitespaceCount String surrounded by whitespace count check.
     */
    public void setProfileStringSurroundedByWhitespaceCount(ColumnStringSurroundedByWhitespaceCountCheckSpec profileStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringSurroundedByWhitespaceCount, profileStringSurroundedByWhitespaceCount));
        this.profileStringSurroundedByWhitespaceCount = profileStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(profileStringSurroundedByWhitespaceCount, "profile_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnStringSurroundedByWhitespacePercentCheckSpec getProfileStringSurroundedByWhitespacePercent() {
        return profileStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param profileStringSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setProfileStringSurroundedByWhitespacePercent(ColumnStringSurroundedByWhitespacePercentCheckSpec profileStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringSurroundedByWhitespacePercent, profileStringSurroundedByWhitespacePercent));
        this.profileStringSurroundedByWhitespacePercent = profileStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(profileStringSurroundedByWhitespacePercent, "profile_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnStringNullPlaceholderCountCheckSpec getProfileStringNullPlaceholderCount() {
        return profileStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a string null placeholder count check.
     * @param profileStringNullPlaceholderCount String null placeholder count check.
     */
    public void setProfileStringNullPlaceholderCount(ColumnStringNullPlaceholderCountCheckSpec profileStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringNullPlaceholderCount, profileStringNullPlaceholderCount));
        this.profileStringNullPlaceholderCount = profileStringNullPlaceholderCount;
        propagateHierarchyIdToField(profileStringNullPlaceholderCount, "profile_string_null_placeholder_count");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnStringNullPlaceholderPercentCheckSpec getProfileStringNullPlaceholderPercent() {
        return profileStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a string null placeholder percent check.
     * @param profileStringNullPlaceholderPercent String null placeholder percent check.
     */
    public void setProfileStringNullPlaceholderPercent(ColumnStringNullPlaceholderPercentCheckSpec profileStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringNullPlaceholderPercent, profileStringNullPlaceholderPercent));
        this.profileStringNullPlaceholderPercent = profileStringNullPlaceholderPercent;
        propagateHierarchyIdToField(profileStringNullPlaceholderPercent, "profile_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnStringBooleanPlaceholderPercentCheckSpec getProfileStringBooleanPlaceholderPercent() {
        return profileStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param profileStringBooleanPlaceholderPercent String boolean placeholder percent check.
     */
    public void setProfileStringBooleanPlaceholderPercent(ColumnStringBooleanPlaceholderPercentCheckSpec profileStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringBooleanPlaceholderPercent, profileStringBooleanPlaceholderPercent));
        this.profileStringBooleanPlaceholderPercent = profileStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(profileStringBooleanPlaceholderPercent, "profile_string_boolean_placeholder_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnStringParsableToIntegerPercentCheckSpec getProfileStringParsableToIntegerPercent() {
        return profileStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param profileStringParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setProfileStringParsableToIntegerPercent(ColumnStringParsableToIntegerPercentCheckSpec profileStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringParsableToIntegerPercent, profileStringParsableToIntegerPercent));
        this.profileStringParsableToIntegerPercent = profileStringParsableToIntegerPercent;
        propagateHierarchyIdToField(profileStringParsableToIntegerPercent, "profile_string_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnStringParsableToFloatPercentCheckSpec getProfileStringParsableToFloatPercent() {
        return profileStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param profileStringParsableToFloatPercent String parsable to float percent check.
     */
    public void setProfileStringParsableToFloatPercent(ColumnStringParsableToFloatPercentCheckSpec profileStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringParsableToFloatPercent, profileStringParsableToFloatPercent));
        this.profileStringParsableToFloatPercent = profileStringParsableToFloatPercent;
        propagateHierarchyIdToField(profileStringParsableToFloatPercent, "profile_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnExpectedStringsInUseCountCheckSpec getProfileExpectedStringsInUseCount() {
        return profileExpectedStringsInUseCount;
    }

    /**
     * Sets a new definition of a string in set count check.
     * @param profileExpectedStringsInUseCount String in set count check.
     */
    public void setProfileExpectedStringsInUseCount(ColumnExpectedStringsInUseCountCheckSpec profileExpectedStringsInUseCount) {
        this.setDirtyIf(!Objects.equals(this.profileExpectedStringsInUseCount, profileExpectedStringsInUseCount));
        this.profileExpectedStringsInUseCount = profileExpectedStringsInUseCount;
        propagateHierarchyIdToField(profileExpectedStringsInUseCount, "profile_expected_strings_in_use_count");
    }

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent check.
     */
    public ColumnStringValueInSetPercentCheckSpec getProfileStringValueInSetPercent() {
        return profileStringValueInSetPercent;
    }

    /**
     * Sets a new definition of a strings in set percent check.
     * @param profileStringValueInSetPercent Strings in set percent check.
     */
    public void setProfileStringValueInSetPercent(ColumnStringValueInSetPercentCheckSpec profileStringValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringValueInSetPercent, profileStringValueInSetPercent));
        this.profileStringValueInSetPercent = profileStringValueInSetPercent;
        propagateHierarchyIdToField(profileStringValueInSetPercent, "profile_string_value_in_set_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnStringValidDatesPercentCheckSpec getProfileStringValidDatesPercent() {
        return profileStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param profileStringValidDatesPercent String valid dates percent check.
     */
    public void setProfileStringValidDatesPercent(ColumnStringValidDatesPercentCheckSpec profileStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringValidDatesPercent, profileStringValidDatesPercent));
        this.profileStringValidDatesPercent = profileStringValidDatesPercent;
        propagateHierarchyIdToField(profileStringValidDatesPercent, "profile_string_valid_dates_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnStringValidCountryCodePercentCheckSpec getProfileStringValidCountryCodePercent() {
        return profileStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param profileStringValidCountryCodePercent String valid country code percent check.
     */
    public void setProfileStringValidCountryCodePercent(ColumnStringValidCountryCodePercentCheckSpec profileStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringValidCountryCodePercent, profileStringValidCountryCodePercent));
        this.profileStringValidCountryCodePercent = profileStringValidCountryCodePercent;
        propagateHierarchyIdToField(profileStringValidCountryCodePercent, "profile_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringValidCurrencyCodePercentCheckSpec getProfileStringValidCurrencyCodePercent() {
        return profileStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param profileStringValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setProfileStringValidCurrencyCodePercent(ColumnStringValidCurrencyCodePercentCheckSpec profileStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringValidCurrencyCodePercent, profileStringValidCurrencyCodePercent));
        this.profileStringValidCurrencyCodePercent = profileStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(profileStringValidCurrencyCodePercent, "profile_string_valid_currency_code_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnStringInvalidEmailCountCheckSpec getProfileStringInvalidEmailCount() {
        return profileStringInvalidEmailCount;
    }

    /**
     * Sets a new definition of an invalid email count check.
     * @param profileStringInvalidEmailCount Invalid email count check.
     */
    public void setProfileStringInvalidEmailCount(ColumnStringInvalidEmailCountCheckSpec profileStringInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringInvalidEmailCount, profileStringInvalidEmailCount));
        this.profileStringInvalidEmailCount = profileStringInvalidEmailCount;
        propagateHierarchyIdToField(profileStringInvalidEmailCount, "profile_string_invalid_email_count");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnStringInvalidUuidCountCheckSpec getProfileStringInvalidUuidCount() {
        return profileStringInvalidUuidCount;
    }

    /**
     * Sets a new definition of an invalid UUID count check.
     * @param profileStringInvalidUuidCount Invalid UUID count check.
     */
    public void setProfileStringInvalidUuidCount(ColumnStringInvalidUuidCountCheckSpec profileStringInvalidUuidCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringInvalidUuidCount, profileStringInvalidUuidCount));
        this.profileStringInvalidUuidCount = profileStringInvalidUuidCount;
        propagateHierarchyIdToField(profileStringInvalidUuidCount, "profile_string_invalid_uuid_count");
    }

    /**
     * Returns a valid UUID percent check.
     * @return Valid UUID percent check.
     */
    public ColumnStringValidUuidPercentCheckSpec getProfileStringValidUuidPercent() {
        return profileStringValidUuidPercent;
    }

    /**
     * Sets a new definition of a valid UUID percent check.
     * @param profileStringValidUuidPercent Valid UUID percent check.
     */
    public void setProfileStringValidUuidPercent(ColumnStringValidUuidPercentCheckSpec profileStringValidUuidPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringValidUuidPercent, profileStringValidUuidPercent));
        this.profileStringValidUuidPercent = profileStringValidUuidPercent;
        propagateHierarchyIdToField(profileStringValidUuidPercent, "profile_string_valid_uuid_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnStringInvalidIp4AddressCountCheckSpec getProfileStringInvalidIp4AddressCount() {
        return profileStringInvalidIp4AddressCount;
    }

    /**
     * Sets a new definition of an invalid IP4 address count check.
     * @param profileStringInvalidIp4AddressCount Invalid IP4 address count check.
     */
    public void setProfileStringInvalidIp4AddressCount(ColumnStringInvalidIp4AddressCountCheckSpec profileStringInvalidIp4AddressCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringInvalidIp4AddressCount, profileStringInvalidIp4AddressCount));
        this.profileStringInvalidIp4AddressCount = profileStringInvalidIp4AddressCount;
        propagateHierarchyIdToField(profileStringInvalidIp4AddressCount, "profile_string_invalid_ip4_address_count");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnStringInvalidIp6AddressCountCheckSpec getProfileStringInvalidIp6AddressCount() {
        return profileStringInvalidIp6AddressCount;
    }

    /**
     * Sets a new definition of an invalid IP6 address count check.
     * @param profileStringInvalidIp6AddressCount Invalid IP6 address count check.
     */
    public void setProfileStringInvalidIp6AddressCount(ColumnStringInvalidIp6AddressCountCheckSpec profileStringInvalidIp6AddressCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringInvalidIp6AddressCount, profileStringInvalidIp6AddressCount));
        this.profileStringInvalidIp6AddressCount = profileStringInvalidIp6AddressCount;
        propagateHierarchyIdToField(profileStringInvalidIp6AddressCount, "profile_string_invalid_ip6_address_count");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnStringNotMatchRegexCountCheckSpec getProfileStringNotMatchRegexCount() {
        return profileStringNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param profileStringNotMatchRegexCount Maximum not match regex count check.
     */
    public void setProfileStringNotMatchRegexCount(ColumnStringNotMatchRegexCountCheckSpec profileStringNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringNotMatchRegexCount, profileStringNotMatchRegexCount));
        this.profileStringNotMatchRegexCount = profileStringNotMatchRegexCount;
        propagateHierarchyIdToField(profileStringNotMatchRegexCount, "profile_string_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnStringMatchRegexPercentCheckSpec getProfileStringMatchRegexPercent() {
        return profileStringMatchRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param profileStringMatchRegexPercent Minimum match regex percent check.
     */
    public void setProfileStringMatchRegexPercent(ColumnStringMatchRegexPercentCheckSpec profileStringMatchRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringMatchRegexPercent, profileStringMatchRegexPercent));
        this.profileStringMatchRegexPercent = profileStringMatchRegexPercent;
        propagateHierarchyIdToField(profileStringMatchRegexPercent, "profile_string_match_regex_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnStringNotMatchDateRegexCountCheckSpec getProfileStringNotMatchDateRegexCount() {
        return profileStringNotMatchDateRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param profileStringNotMatchDateRegexCount Maximum not match date regex count check.
     */
    public void setProfileStringNotMatchDateRegexCount(ColumnStringNotMatchDateRegexCountCheckSpec profileStringNotMatchDateRegexCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringNotMatchDateRegexCount, profileStringNotMatchDateRegexCount));
        this.profileStringNotMatchDateRegexCount = profileStringNotMatchDateRegexCount;
        propagateHierarchyIdToField(profileStringNotMatchDateRegexCount, "profile_string_not_match_date_regex_count");
    }

    /**
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnStringMatchDateRegexPercentCheckSpec getProfileStringMatchDateRegexPercent() {
        return profileStringMatchDateRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param profileStringMatchDateRegexPercent Maximum match date regex percent check.
     */
    public void setProfileStringMatchDateRegexPercent(ColumnStringMatchDateRegexPercentCheckSpec profileStringMatchDateRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringMatchDateRegexPercent, profileStringMatchDateRegexPercent));
        this.profileStringMatchDateRegexPercent = profileStringMatchDateRegexPercent;
        propagateHierarchyIdToField(profileStringMatchDateRegexPercent, "profile_string_match_date_regex_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnStringMatchNameRegexPercentCheckSpec getProfileStringMatchNameRegexPercent() {
        return profileStringMatchNameRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param profileStringMatchNameRegexPercent Maximum match name regex percent check.
     */
    public void setProfileStringMatchNameRegexPercent(ColumnStringMatchNameRegexPercentCheckSpec profileStringMatchNameRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringMatchNameRegexPercent, profileStringMatchNameRegexPercent));
        this.profileStringMatchNameRegexPercent = profileStringMatchNameRegexPercent;
        propagateHierarchyIdToField(profileStringMatchNameRegexPercent, "profile_string_match_name_regex_percent");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedStringsInTopValuesCountCheckSpec getProfileExpectedStringsInTopValuesCount() {
        return profileExpectedStringsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param profileExpectedStringsInTopValuesCount Most popular values count check.
     */
    public void setProfileExpectedStringsInTopValuesCount(ColumnExpectedStringsInTopValuesCountCheckSpec profileExpectedStringsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.profileExpectedStringsInTopValuesCount, profileExpectedStringsInTopValuesCount));
        this.profileExpectedStringsInTopValuesCount = profileExpectedStringsInTopValuesCount;
        propagateHierarchyIdToField(profileExpectedStringsInTopValuesCount, "profile_expected_strings_in_top_values_count");
    }

    /**
     * Returns a count of expected values in datatype detected check.
     * @return Datatype detected check.
     */
    public ColumnStringDatatypeDetectedCheckSpec getProfileStringDatatypeDetected() {
        return profileStringDatatypeDetected;
    }

    /**
     * Sets a new definition of a datatype detected check.
     * @param profileStringDatatypeDetected Datatype detected check.
     */
    public void setProfileStringDatatypeDetected(ColumnStringDatatypeDetectedCheckSpec profileStringDatatypeDetected) {
        this.setDirtyIf(!Objects.equals(this.profileStringDatatypeDetected, profileStringDatatypeDetected));
        this.profileStringDatatypeDetected = profileStringDatatypeDetected;
        propagateHierarchyIdToField(profileStringDatatypeDetected, "profile_string_datatype_detected");
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
    public ColumnStringsProfilingChecksSpec deepClone() {
        return (ColumnStringsProfilingChecksSpec)super.deepClone();
    }
}
