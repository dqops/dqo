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
import com.dqops.checks.column.checkspecs.blanks.*;
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
 * Container of strings data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_string_max_length", o -> o.dailyStringMaxLength);
            put("daily_string_min_length", o -> o.dailyStringMinLength);
            put("daily_string_mean_length", o -> o.dailyStringMeanLength);
            put("daily_string_length_below_min_length_count", o -> o.dailyStringLengthBelowMinLengthCount);
            put("daily_string_length_below_min_length_percent", o -> o.dailyStringLengthBelowMinLengthPercent);
            put("daily_string_length_above_max_length_count", o -> o.dailyStringLengthAboveMaxLengthCount);
            put("daily_string_length_above_max_length_percent", o -> o.dailyStringLengthAboveMaxLengthPercent);
            put("daily_string_length_in_range_percent", o -> o.dailyStringLengthInRangePercent);

            put("daily_string_surrounded_by_whitespace_count", o -> o.dailyStringSurroundedByWhitespaceCount);
            put("daily_string_surrounded_by_whitespace_percent", o -> o.dailyStringSurroundedByWhitespacePercent);
            put("daily_string_boolean_placeholder_percent", o -> o.dailyStringBooleanPlaceholderPercent);
            put("daily_string_parsable_to_integer_percent", o -> o.dailyStringParsableToIntegerPercent);
            put("daily_string_parsable_to_float_percent", o -> o.dailyStringParsableToFloatPercent);

            put("daily_string_valid_dates_percent", o -> o.dailyStringValidDatesPercent);
            put("daily_string_valid_country_code_percent", o -> o.dailyStringValidCountryCodePercent);
            put("daily_string_valid_currency_code_percent", o -> o.dailyStringValidCurrencyCodePercent);
            put("daily_string_invalid_email_count", o -> o.dailyStringInvalidEmailCount);
            put("daily_string_invalid_uuid_count", o -> o.dailyStringInvalidUuidCount);
            put("daily_string_valid_uuid_percent", o -> o.dailyStringValidUuidPercent);
            put("daily_string_invalid_ip4_address_count", o -> o.dailyStringInvalidIp4AddressCount);
            put("daily_string_invalid_ip6_address_count", o -> o.dailyStringInvalidIp6AddressCount);

            put("daily_string_not_match_regex_count", o -> o.dailyStringNotMatchRegexCount);
            put("daily_string_match_regex_percent", o -> o.dailyStringMatchRegexPercent);
            put("daily_string_not_match_date_regex_count", o -> o.dailyStringNotMatchDateRegexCount);
            put("daily_string_match_date_regex_percent", o -> o.dailyStringMatchDateRegexPercent);
            put("daily_string_match_name_regex_percent", o -> o.dailyStringMatchNameRegexPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringMaxLengthCheckSpec dailyStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringMinLengthCheckSpec dailyStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringMeanLengthCheckSpec dailyStringMeanLength;

    @JsonPropertyDescription("The check counts the number of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringLengthBelowMinLengthCountCheckSpec dailyStringLengthBelowMinLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringLengthBelowMinLengthPercentCheckSpec dailyStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringLengthAboveMaxLengthCountCheckSpec dailyStringLengthAboveMaxLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringLengthAboveMaxLengthPercentCheckSpec dailyStringLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringLengthInRangePercentCheckSpec dailyStringLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringSurroundedByWhitespaceCountCheckSpec dailyStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringSurroundedByWhitespacePercentCheckSpec dailyStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringBooleanPlaceholderPercentCheckSpec dailyStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringParsableToIntegerPercentCheckSpec dailyStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringParsableToFloatPercentCheckSpec dailyStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringValidDatesPercentCheckSpec dailyStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringValidCountryCodePercentCheckSpec dailyStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringValidCurrencyCodePercentCheckSpec dailyStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringInvalidEmailCountCheckSpec dailyStringInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringInvalidUuidCountCheckSpec dailyStringInvalidUuidCount;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringValidUuidPercentCheckSpec dailyStringValidUuidPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringInvalidIp4AddressCountCheckSpec dailyStringInvalidIp4AddressCount;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringInvalidIp6AddressCountCheckSpec dailyStringInvalidIp6AddressCount;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringNotMatchRegexCountCheckSpec dailyStringNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringMatchRegexPercentCheckSpec dailyStringMatchRegexPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringNotMatchDateRegexCountCheckSpec dailyStringNotMatchDateRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringMatchDateRegexPercentCheckSpec dailyStringMatchDateRegexPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringMatchNameRegexPercentCheckSpec dailyStringMatchNameRegexPercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnStringMaxLengthCheckSpec getDailyStringMaxLength() {
        return dailyStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param dailyStringMaxLength Maximum string length below check.
     */
    public void setDailyStringMaxLength(ColumnStringMaxLengthCheckSpec dailyStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyStringMaxLength, dailyStringMaxLength));
        this.dailyStringMaxLength = dailyStringMaxLength;
        propagateHierarchyIdToField(dailyStringMaxLength, "daily_string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length below check.
     */
    public ColumnStringMinLengthCheckSpec getDailyStringMinLength() {
        return dailyStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param dailyStringMinLength Minimum string length above check.
     */
    public void setDailyStringMinLength(ColumnStringMinLengthCheckSpec dailyStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyStringMinLength, dailyStringMinLength));
        this.dailyStringMinLength = dailyStringMinLength;
        propagateHierarchyIdToField(dailyStringMinLength, "daily_string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnStringMeanLengthCheckSpec getDailyStringMeanLength() {
        return dailyStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param dailyStringMeanLength Mean string length between check.
     */
    public void setDailyStringMeanLength(ColumnStringMeanLengthCheckSpec dailyStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.dailyStringMeanLength, dailyStringMeanLength));
        this.dailyStringMeanLength = dailyStringMeanLength;
        propagateHierarchyIdToField(dailyStringMeanLength, "daily_string_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnStringLengthBelowMinLengthCountCheckSpec getDailyStringLengthBelowMinLengthCount() {
        return dailyStringLengthBelowMinLengthCount;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param dailyStringLengthBelowMinLengthCount String length below min length count check.
     */
    public void setDailyStringLengthBelowMinLengthCount(ColumnStringLengthBelowMinLengthCountCheckSpec dailyStringLengthBelowMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringLengthBelowMinLengthCount, dailyStringLengthBelowMinLengthCount));
        this.dailyStringLengthBelowMinLengthCount = dailyStringLengthBelowMinLengthCount;
        propagateHierarchyIdToField(dailyStringLengthBelowMinLengthCount, "daily_string_length_below_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return Mean string length below min length percent check.
     */
    public ColumnStringLengthBelowMinLengthPercentCheckSpec getDailyStringLengthBelowMinLengthPercent() {
        return dailyStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param dailyStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setDailyStringLengthBelowMinLengthPercent(ColumnStringLengthBelowMinLengthPercentCheckSpec dailyStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringLengthBelowMinLengthPercent, dailyStringLengthBelowMinLengthPercent));
        this.dailyStringLengthBelowMinLengthPercent = dailyStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(dailyStringLengthBelowMinLengthPercent, "daily_string_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnStringLengthAboveMaxLengthCountCheckSpec getDailyStringLengthAboveMaxLengthCount() {
        return dailyStringLengthAboveMaxLengthCount;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param dailyStringLengthAboveMaxLengthCount String length above max length count check.
     */
    public void setDailyStringLengthAboveMaxLengthCount(ColumnStringLengthAboveMaxLengthCountCheckSpec dailyStringLengthAboveMaxLengthCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringLengthAboveMaxLengthCount, dailyStringLengthAboveMaxLengthCount));
        this.dailyStringLengthAboveMaxLengthCount = dailyStringLengthAboveMaxLengthCount;
        propagateHierarchyIdToField(dailyStringLengthAboveMaxLengthCount, "daily_string_length_above_max_length_count");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnStringLengthAboveMaxLengthPercentCheckSpec getDailyStringLengthAboveMaxLengthPercent() {
        return dailyStringLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param dailyStringLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setDailyStringLengthAboveMaxLengthPercent(ColumnStringLengthAboveMaxLengthPercentCheckSpec dailyStringLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringLengthAboveMaxLengthPercent, dailyStringLengthAboveMaxLengthPercent));
        this.dailyStringLengthAboveMaxLengthPercent = dailyStringLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(dailyStringLengthAboveMaxLengthPercent, "daily_string_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnStringLengthInRangePercentCheckSpec getDailyStringLengthInRangePercent() {
        return dailyStringLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param dailyStringLengthInRangePercent String length in range percent check.
     */
    public void setDailyStringLengthInRangePercent(ColumnStringLengthInRangePercentCheckSpec dailyStringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringLengthInRangePercent, dailyStringLengthInRangePercent));
        this.dailyStringLengthInRangePercent = dailyStringLengthInRangePercent;
        propagateHierarchyIdToField(dailyStringLengthInRangePercent, "daily_string_length_in_range_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnStringSurroundedByWhitespaceCountCheckSpec getDailyStringSurroundedByWhitespaceCount() {
        return dailyStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param dailyStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setDailyStringSurroundedByWhitespaceCount(ColumnStringSurroundedByWhitespaceCountCheckSpec dailyStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringSurroundedByWhitespaceCount, dailyStringSurroundedByWhitespaceCount));
        this.dailyStringSurroundedByWhitespaceCount = dailyStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(dailyStringSurroundedByWhitespaceCount, "daily_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnStringSurroundedByWhitespacePercentCheckSpec getDailyStringSurroundedByWhitespacePercent() {
        return dailyStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param dailyStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setDailyStringSurroundedByWhitespacePercent(ColumnStringSurroundedByWhitespacePercentCheckSpec dailyStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringSurroundedByWhitespacePercent, dailyStringSurroundedByWhitespacePercent));
        this.dailyStringSurroundedByWhitespacePercent = dailyStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyStringSurroundedByWhitespacePercent, "daily_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnStringBooleanPlaceholderPercentCheckSpec getDailyStringBooleanPlaceholderPercent() {
        return dailyStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param dailyStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setDailyStringBooleanPlaceholderPercent(ColumnStringBooleanPlaceholderPercentCheckSpec dailyStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringBooleanPlaceholderPercent, dailyStringBooleanPlaceholderPercent));
        this.dailyStringBooleanPlaceholderPercent = dailyStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(dailyStringBooleanPlaceholderPercent, "daily_string_boolean_placeholder_percent");
    }

    /**
    * Returns a minimum string parsable to integer percent check.
    * @return Minimum string parsable to integer percent check.
    */
    public ColumnStringParsableToIntegerPercentCheckSpec getDailyStringParsableToIntegerPercent() {
        return dailyStringParsableToIntegerPercent;
    }

    /**
    * Sets a new definition of a minimum string parsable to integer percent check.
    * @param dailyStringParsableToIntegerPercent Minimum string parsable to integer percent check.
    */
    public void setDailyStringParsableToIntegerPercent(ColumnStringParsableToIntegerPercentCheckSpec dailyStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringParsableToIntegerPercent, dailyStringParsableToIntegerPercent));
        this.dailyStringParsableToIntegerPercent = dailyStringParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyStringParsableToIntegerPercent, "daily_string_parsable_to_integer_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnStringParsableToFloatPercentCheckSpec getDailyStringParsableToFloatPercent() {
        return dailyStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param dailyStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setDailyStringParsableToFloatPercent(ColumnStringParsableToFloatPercentCheckSpec dailyStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringParsableToFloatPercent, dailyStringParsableToFloatPercent));
        this.dailyStringParsableToFloatPercent = dailyStringParsableToFloatPercent;
        propagateHierarchyIdToField(dailyStringParsableToFloatPercent, "daily_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnStringValidDatesPercentCheckSpec getDailyStringValidDatesPercent() {
        return dailyStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param dailyStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setDailyStringValidDatesPercent(ColumnStringValidDatesPercentCheckSpec dailyStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringValidDatesPercent, dailyStringValidDatesPercent));
        this.dailyStringValidDatesPercent = dailyStringValidDatesPercent;
        propagateHierarchyIdToField(dailyStringValidDatesPercent, "daily_string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent check.
     */
    public ColumnStringValidCountryCodePercentCheckSpec getDailyStringValidCountryCodePercent() {
        return dailyStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param dailyStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setDailyStringValidCountryCodePercent(ColumnStringValidCountryCodePercentCheckSpec dailyStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringValidCountryCodePercent, dailyStringValidCountryCodePercent));
        this.dailyStringValidCountryCodePercent = dailyStringValidCountryCodePercent;
        propagateHierarchyIdToField(dailyStringValidCountryCodePercent, "daily_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent check.
     */
    public ColumnStringValidCurrencyCodePercentCheckSpec getDailyStringValidCurrencyCodePercent() {
        return dailyStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param dailyStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setDailyStringValidCurrencyCodePercent(ColumnStringValidCurrencyCodePercentCheckSpec dailyStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringValidCurrencyCodePercent, dailyStringValidCurrencyCodePercent));
        this.dailyStringValidCurrencyCodePercent = dailyStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(dailyStringValidCurrencyCodePercent, "daily_string_valid_currency_code_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnStringInvalidEmailCountCheckSpec getDailyStringInvalidEmailCount() {
        return dailyStringInvalidEmailCount;
    }

    /**
     * Sets a new definition of a maximum invalid email count check.
     * @param dailyStringInvalidEmailCount Maximum invalid email count check.
     */
    public void setDailyStringInvalidEmailCount(ColumnStringInvalidEmailCountCheckSpec dailyStringInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringInvalidEmailCount, dailyStringInvalidEmailCount));
        this.dailyStringInvalidEmailCount = dailyStringInvalidEmailCount;
        propagateHierarchyIdToField(dailyStringInvalidEmailCount, "daily_string_invalid_email_count");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnStringInvalidUuidCountCheckSpec getDailyStringInvalidUuidCount() {
        return dailyStringInvalidUuidCount;
    }

    /**
     * Sets a new definition of a maximum invalid UUID count check.
     * @param dailyStringInvalidUuidCount Maximum invalid UUID count check.
     */
    public void setDailyStringInvalidUuidCount(ColumnStringInvalidUuidCountCheckSpec dailyStringInvalidUuidCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringInvalidUuidCount, dailyStringInvalidUuidCount));
        this.dailyStringInvalidUuidCount = dailyStringInvalidUuidCount;
        propagateHierarchyIdToField(dailyStringInvalidUuidCount, "daily_string_invalid_uuid_count");
    }

    /**
     * Returns a minimum valid UUID percent check.
     * @return Minimum valid UUID percent check.
     */
    public ColumnStringValidUuidPercentCheckSpec getDailyStringValidUuidPercent() {
        return dailyStringValidUuidPercent;
    }

    /**
     * Sets a new definition of a minimum valid UUID percent check.
     * @param dailyStringValidUuidPercent Minimum valid UUID percent check.
     */
    public void setDailyStringValidUuidPercent(ColumnStringValidUuidPercentCheckSpec dailyStringValidUuidPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringValidUuidPercent, dailyStringValidUuidPercent));
        this.dailyStringValidUuidPercent = dailyStringValidUuidPercent;
        propagateHierarchyIdToField(dailyStringValidUuidPercent, "daily_string_valid_uuid_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnStringInvalidIp4AddressCountCheckSpec getDailyStringInvalidIp4AddressCount() {
        return dailyStringInvalidIp4AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP4 address count check.
     * @param dailyStringInvalidIp4AddressCount Maximum invalid IP4 address count check.
     */
    public void setDailyStringInvalidIp4AddressCount(ColumnStringInvalidIp4AddressCountCheckSpec dailyStringInvalidIp4AddressCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringInvalidIp4AddressCount, dailyStringInvalidIp4AddressCount));
        this.dailyStringInvalidIp4AddressCount = dailyStringInvalidIp4AddressCount;
        propagateHierarchyIdToField(dailyStringInvalidIp4AddressCount, "daily_string_invalid_ip4_address_count");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnStringInvalidIp6AddressCountCheckSpec getDailyStringInvalidIp6AddressCount() {
        return dailyStringInvalidIp6AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP6 address count check.
     * @param dailyStringInvalidIp6AddressCount Maximum invalid IP6 address count check.
     */
    public void setDailyStringInvalidIp6AddressCount(ColumnStringInvalidIp6AddressCountCheckSpec dailyStringInvalidIp6AddressCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringInvalidIp6AddressCount, dailyStringInvalidIp6AddressCount));
        this.dailyStringInvalidIp6AddressCount = dailyStringInvalidIp6AddressCount;
        propagateHierarchyIdToField(dailyStringInvalidIp6AddressCount, "daily_string_invalid_ip6_address_count");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnStringNotMatchRegexCountCheckSpec getDailyStringNotMatchRegexCount() {
        return dailyStringNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param dailyStringNotMatchRegexCount Maximum not match regex count check.
     */
    public void setDailyStringNotMatchRegexCount(ColumnStringNotMatchRegexCountCheckSpec dailyStringNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringNotMatchRegexCount, dailyStringNotMatchRegexCount));
        this.dailyStringNotMatchRegexCount = dailyStringNotMatchRegexCount;
        propagateHierarchyIdToField(dailyStringNotMatchRegexCount, "daily_string_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnStringMatchRegexPercentCheckSpec getDailyStringMatchRegexPercent() {
        return dailyStringMatchRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param dailyStringMatchRegexPercent Minimum match regex percent check.
     */
    public void setDailyStringMatchRegexPercent(ColumnStringMatchRegexPercentCheckSpec dailyStringMatchRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringMatchRegexPercent, dailyStringMatchRegexPercent));
        this.dailyStringMatchRegexPercent = dailyStringMatchRegexPercent;
        propagateHierarchyIdToField(dailyStringMatchRegexPercent, "daily_string_match_regex_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnStringNotMatchDateRegexCountCheckSpec getDailyStringNotMatchDateRegexCount() {
        return dailyStringNotMatchDateRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param dailyStringNotMatchDateRegexCount Maximum not match date regex count check.
     */
    public void setDailyStringNotMatchDateRegexCount(ColumnStringNotMatchDateRegexCountCheckSpec dailyStringNotMatchDateRegexCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringNotMatchDateRegexCount, dailyStringNotMatchDateRegexCount));
        this.dailyStringNotMatchDateRegexCount = dailyStringNotMatchDateRegexCount;
        propagateHierarchyIdToField(dailyStringNotMatchDateRegexCount, "daily_string_not_match_date_regex_count");
    }

    /**
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnStringMatchDateRegexPercentCheckSpec getDailyStringMatchDateRegexPercent() {
        return dailyStringMatchDateRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param dailyStringMatchDateRegexPercent Maximum match date regex percent check.
     */
    public void setDailyStringMatchDateRegexPercent(ColumnStringMatchDateRegexPercentCheckSpec dailyStringMatchDateRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringMatchDateRegexPercent, dailyStringMatchDateRegexPercent));
        this.dailyStringMatchDateRegexPercent = dailyStringMatchDateRegexPercent;
        propagateHierarchyIdToField(dailyStringMatchDateRegexPercent, "daily_string_match_date_regex_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnStringMatchNameRegexPercentCheckSpec getDailyStringMatchNameRegexPercent() {
        return dailyStringMatchNameRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param dailyStringMatchNameRegexPercent Maximum match name regex percent check.
     */
    public void setDailyStringMatchNameRegexPercent(ColumnStringMatchNameRegexPercentCheckSpec dailyStringMatchNameRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringMatchNameRegexPercent, dailyStringMatchNameRegexPercent));
        this.dailyStringMatchNameRegexPercent = dailyStringMatchNameRegexPercent;
        propagateHierarchyIdToField(dailyStringMatchNameRegexPercent, "daily_string_match_name_regex_percent");
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
    public ColumnStringsDailyMonitoringChecksSpec deepClone() {
        return (ColumnStringsDailyMonitoringChecksSpec)super.deepClone();
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
        return CheckTimeScale.daily;
    }
}
