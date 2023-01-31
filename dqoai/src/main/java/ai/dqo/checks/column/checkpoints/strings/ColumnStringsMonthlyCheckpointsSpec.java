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
import ai.dqo.checks.column.checkspecs.strings.*;
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
            put("monthly_checkpoint_string_max_length", o -> o.monthlyCheckpointStringMaxLength);
            put("monthly_checkpoint_string_min_length", o -> o.monthlyCheckpointStringMinLength);
            put("monthly_checkpoint_string_mean_length", o -> o.monthlyCheckpointStringMeanLength);
            put("monthly_checkpoint_string_length_below_min_length_count", o -> o.monthlyCheckpointStringLengthBelowMinLengthCount);
            put("monthly_checkpoint_string_length_below_min_length_percent", o -> o.monthlyCheckpointStringLengthBelowMinLengthPercent);
            put("monthly_checkpoint_string_length_above_max_length_count", o -> o.monthlyCheckpointStringLengthAboveMaxLengthCount);
            put("monthly_checkpoint_string_length_above_max_length_percent", o -> o.monthlyCheckpointStringLengthAboveMaxLengthPercent);



            put("monthly_checkpoint_string_empty_count", o -> o.monthlyCheckpointStringEmptyCount);
            put("monthly_checkpoint_string_empty_percent", o -> o.monthlyCheckpointStringEmptyPercent);
            put("monthly_checkpoint_string_whitespace_count", o -> o.monthlyCheckpointStringWhitespaceCount);
            put("monthly_checkpoint_string_whitespace_percent", o -> o.monthlyCheckpointStringWhitespacePercent);
            put("monthly_checkpoint_string_surrounded_by_whitespace_count", o -> o.monthlyCheckpointStringSurroundedByWhitespaceCount);
            put("monthly_checkpoint_string_surrounded_by_whitespace_percent", o -> o.monthlyCheckpointStringSurroundedByWhitespacePercent);

            put("monthly_checkpoint_string_null_placeholder_count", o -> o.monthlyCheckpointStringNullPlaceholderCount);
            put("monthly_checkpoint_string_null_placeholder_percent", o -> o.monthlyCheckpointStringNullPlaceholderPercent);
            put("monthly_checkpoint_string_boolean_placeholder_percent", o -> o.monthlyCheckpointStringBooleanPlaceholderPercent);
            put("monthly_checkpoint_string_parsable_to_integer_percent", o -> o.monthlyCheckpointStringParsableToIntegerPercent);
            put("monthly_checkpoint_string_parsable_to_float_percent", o -> o.monthlyCheckpointStringParsableToFloatPercent);

            put("monthly_checkpoint_string_in_set_count", o -> o.monthlyCheckpointStringInSetCount);
            put("monthly_checkpoint_string_in_set_percent", o -> o.monthlyCheckpointStringInSetPercent);

            put("monthly_checkpoint_string_valid_dates_percent", o -> o.monthlyCheckpointStringValidDatesPercent);
            put("monthly_checkpoint_string_valid_usa_zipcode_percent", o -> o.monthlyCheckpointStringValidUsaZipcodePercent);
            put("monthly_checkpoint_string_valid_usa_phone_percent", o -> o.monthlyCheckpointStringValidUsaPhonePercent);
            put("monthly_checkpoint_string_valid_country_code_percent", o -> o.monthlyCheckpointStringValidCountryCodePercent);
            put("monthly_checkpoint_string_valid_currency_code_percent", o -> o.monthlyCheckpointStringValidCurrencyCodePercent);
            put("monthly_checkpoint_string_invalid_email_count", o -> o.monthlyCheckpointStringInvalidEmailCount);
            put("monthly_checkpoint_string_valid_email_percent", o -> o.monthlyCheckpointStringValidEmailPercent);
            put("monthly_checkpoint_string_invalid_uuid_count", o -> o.monthlyCheckpointStringInvalidUuidCount);
            put("monthly_checkpoint_string_valid_uuid_percent", o -> o.monthlyCheckpointStringValidUuidPercent);
            put("monthly_checkpoint_string_invalid_ip4_address_count", o -> o.monthlyCheckpointStringInvalidIp4AddressCount);
            put("monthly_checkpoint_string_valid_ip4_address_percent", o -> o.monthlyCheckpointStringValidIp4AddressPercent);
            put("monthly_checkpoint_string_invalid_ip6_address_count", o -> o.monthlyCheckpointStringInvalidIp6AddressCount);
            put("monthly_checkpoint_string_valid_ip6_address_percent", o -> o.monthlyCheckpointStringValidIp6AddressPercent);



            put("monthly_checkpoint_string_not_match_regex_count", o -> o.monthlyCheckpointStringNotMatchRegexCount);
            put("monthly_checkpoint_string_match_regex_percent", o -> o.monthlyCheckpointStringMatchRegexPercent);
            put("monthly_checkpoint_string_not_match_date_regex_count", o -> o.monthlyCheckpointStringNotMatchDateRegexCount);
            put("monthly_checkpoint_string_match_date_regex_percent", o -> o.monthlyCheckpointStringMatchDateRegexPercent);
            put("monthly_checkpoint_string_match_name_regex_percent", o -> o.monthlyCheckpointStringMatchNameRegexPercent);

            put("monthly_checkpoint_string_most_popular_values", o -> o.monthlyCheckpointStringMostPopularValues);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMaxLengthCheckSpec monthlyCheckpointStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMinLengthCheckSpec monthlyCheckpointStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMeanLengthCheckSpec monthlyCheckpointStringMeanLength;

    @JsonPropertyDescription("The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringLengthBelowMinLengthCountCheckSpec monthlyCheckpointStringLengthBelowMinLengthCount;

    @JsonPropertyDescription("The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringLengthBelowMinLengthPercentCheckSpec monthlyCheckpointStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringLengthAboveMaxLengthCountCheckSpec monthlyCheckpointStringLengthAboveMaxLengthCount;

    @JsonPropertyDescription("The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringLengthAboveMaxLengthPercentCheckSpec monthlyCheckpointStringLengthAboveMaxLengthPercent;


    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringEmptyCountCheckSpec monthlyCheckpointStringEmptyCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringEmptyPercentCheckSpec monthlyCheckpointStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidDatesPercentCheckSpec monthlyCheckpointStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringWhitespaceCountCheckSpec monthlyCheckpointStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringWhitespacePercentCheckSpec monthlyCheckpointStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringSurroundedByWhitespaceCountCheckSpec monthlyCheckpointStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringSurroundedByWhitespacePercentCheckSpec monthlyCheckpointStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringNullPlaceholderCountCheckSpec monthlyCheckpointStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringNullPlaceholderPercentCheckSpec monthlyCheckpointStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringBooleanPlaceholderPercentCheckSpec monthlyCheckpointStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringParsableToIntegerPercentCheckSpec monthlyCheckpointStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringParsableToFloatPercentCheckSpec monthlyCheckpointStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the number of strings from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInSetCountCheckSpec monthlyCheckpointStringInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInSetPercentCheckSpec monthlyCheckpointStringInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidUsaZipcodePercentCheckSpec monthlyCheckpointStringValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidUsaPhonePercentCheckSpec monthlyCheckpointStringValidUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidCountryCodePercentCheckSpec monthlyCheckpointStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidCurrencyCodePercentCheckSpec monthlyCheckpointStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInvalidEmailCountCheckSpec monthlyCheckpointStringInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidEmailPercentCheckSpec monthlyCheckpointStringValidEmailPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInvalidUuidCountCheckSpec monthlyCheckpointStringInvalidUuidCount;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidUuidPercentCheckSpec monthlyCheckpointStringValidUuidPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInvalidIp4AddressCountCheckSpec monthlyCheckpointStringInvalidIp4AddressCount;

    @JsonPropertyDescription("Verifies that the percentage of valid IP4 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidIp4AddressPercentCheckSpec monthlyCheckpointStringValidIp4AddressPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringInvalidIp6AddressCountCheckSpec monthlyCheckpointStringInvalidIp6AddressCount;

    @JsonPropertyDescription("Verifies that the percentage of valid IP6 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringValidIp6AddressPercentCheckSpec monthlyCheckpointStringValidIp6AddressPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringNotMatchRegexCountCheckSpec monthlyCheckpointStringNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMatchRegexPercentCheckSpec monthlyCheckpointStringMatchRegexPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringNotMatchDateRegexCountCheckSpec monthlyCheckpointStringNotMatchDateRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMatchDateRegexPercentCheckSpec monthlyCheckpointStringMatchDateRegexPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnStringMatchNameRegexPercentCheckSpec monthlyCheckpointStringMatchNameRegexPercent;

    @JsonPropertyDescription("Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.")
    private ColumnStringMostPopularValuesCheckSpec monthlyCheckpointStringMostPopularValues;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnStringMaxLengthCheckSpec getMonthlyCheckpointStringMaxLength() {
        return monthlyCheckpointStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param monthlyCheckpointStringMaxLength Maximum string length below check.
     */
    public void setMonthlyCheckpointStringMaxLength(ColumnStringMaxLengthCheckSpec monthlyCheckpointStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringMaxLength, monthlyCheckpointStringMaxLength));
        this.monthlyCheckpointStringMaxLength = monthlyCheckpointStringMaxLength;
        propagateHierarchyIdToField(monthlyCheckpointStringMaxLength, "monthly_checkpoint_string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnStringMinLengthCheckSpec getMonthlyCheckpointStringMinLength() {
        return monthlyCheckpointStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param monthlyCheckpointStringMinLength Minimum string length below check.
     */
    public void setMonthlyCheckpointStringMinLength(ColumnStringMinLengthCheckSpec monthlyCheckpointStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringMinLength, monthlyCheckpointStringMinLength));
        this.monthlyCheckpointStringMinLength = monthlyCheckpointStringMinLength;
        propagateHierarchyIdToField(monthlyCheckpointStringMinLength, "monthly_checkpoint_string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnStringMeanLengthCheckSpec getMonthlyCheckpointStringMeanLength() {
        return monthlyCheckpointStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param monthlyCheckpointStringMeanLength Mean string length between check.
     */
    public void setMonthlyCheckpointStringMeanLength(ColumnStringMeanLengthCheckSpec monthlyCheckpointStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringMeanLength, monthlyCheckpointStringMeanLength));
        this.monthlyCheckpointStringMeanLength = monthlyCheckpointStringMeanLength;
        propagateHierarchyIdToField(monthlyCheckpointStringMeanLength, "monthly_checkpoint_string_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnStringLengthBelowMinLengthCountCheckSpec getMonthlyCheckpointStringLengthBelowMinLengthCount() {
        return monthlyCheckpointStringLengthBelowMinLengthCount;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param monthlyCheckpointStringLengthBelowMinLengthCount String length below min length count check.
     */
    public void setMonthlyCheckpointStringLengthBelowMinLengthCount(ColumnStringLengthBelowMinLengthCountCheckSpec monthlyCheckpointStringLengthBelowMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringLengthBelowMinLengthCount, monthlyCheckpointStringLengthBelowMinLengthCount));
        this.monthlyCheckpointStringLengthBelowMinLengthCount = monthlyCheckpointStringLengthBelowMinLengthCount;
        propagateHierarchyIdToField(monthlyCheckpointStringLengthBelowMinLengthCount, "monthly_checkpoint_string_length_below_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return Mean string length below min length percent check.
     */
    public ColumnStringLengthBelowMinLengthPercentCheckSpec getMonthlyCheckpointStringLengthBelowMinLengthPercent() {
        return monthlyCheckpointStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param monthlyCheckpointStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setMonthlyCheckpointStringLengthBelowMinLengthPercent(ColumnStringLengthBelowMinLengthPercentCheckSpec monthlyCheckpointStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringLengthBelowMinLengthPercent, monthlyCheckpointStringLengthBelowMinLengthPercent));
        this.monthlyCheckpointStringLengthBelowMinLengthPercent = monthlyCheckpointStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringLengthBelowMinLengthPercent, "monthly_checkpoint_string_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnStringLengthAboveMaxLengthCountCheckSpec getMonthlyCheckpointStringLengthAboveMaxLengthCount() {
        return monthlyCheckpointStringLengthAboveMaxLengthCount;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param monthlyCheckpointStringLengthAboveMaxLengthCount String length above max length count check.
     */
    public void setMonthlyCheckpointStringLengthAboveMaxLengthCount(ColumnStringLengthAboveMaxLengthCountCheckSpec monthlyCheckpointStringLengthAboveMaxLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringLengthAboveMaxLengthCount, monthlyCheckpointStringLengthAboveMaxLengthCount));
        this.monthlyCheckpointStringLengthAboveMaxLengthCount = monthlyCheckpointStringLengthAboveMaxLengthCount;
        propagateHierarchyIdToField(monthlyCheckpointStringLengthAboveMaxLengthCount, "monthly_checkpoint_string_length_above_max_length_count");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnStringLengthAboveMaxLengthPercentCheckSpec getMonthlyCheckpointStringLengthAboveMaxLengthPercent() {
        return monthlyCheckpointStringLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param monthlyCheckpointStringLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setMonthlyCheckpointStringLengthAboveMaxLengthPercent(ColumnStringLengthAboveMaxLengthPercentCheckSpec monthlyCheckpointStringLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringLengthAboveMaxLengthPercent, monthlyCheckpointStringLengthAboveMaxLengthPercent));
        this.monthlyCheckpointStringLengthAboveMaxLengthPercent = monthlyCheckpointStringLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringLengthAboveMaxLengthPercent, "monthly_checkpoint_string_length_above_max_length_percent");
    }

    /**
     * Returns max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnStringEmptyCountCheckSpec getMonthlyCheckpointStringEmptyCount() {
        return monthlyCheckpointStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyCheckpointStringEmptyCount Max string empty count check.
     */
    public void setMonthlyCheckpointStringEmptyCount(ColumnStringEmptyCountCheckSpec monthlyCheckpointStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringEmptyCount, monthlyCheckpointStringEmptyCount));
        this.monthlyCheckpointStringEmptyCount = monthlyCheckpointStringEmptyCount;
        propagateHierarchyIdToField(monthlyCheckpointStringEmptyCount, "monthly_checkpoint_string_empty_count");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnStringEmptyPercentCheckSpec getMonthlyCheckpointStringEmptyPercent() {
        return monthlyCheckpointStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param monthlyCheckpointStringEmptyPercent Maximum string empty percent check.
     */
    public void setMonthlyCheckpointStringEmptyPercent(ColumnStringEmptyPercentCheckSpec monthlyCheckpointStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringEmptyPercent, monthlyCheckpointStringEmptyPercent));
        this.monthlyCheckpointStringEmptyPercent = monthlyCheckpointStringEmptyPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringEmptyPercent, "monthly_checkpoint_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnStringWhitespaceCountCheckSpec getMonthlyCheckpointStringWhitespaceCount() {
        return monthlyCheckpointStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyCheckpointStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setMonthlyCheckpointStringWhitespaceCount(ColumnStringWhitespaceCountCheckSpec monthlyCheckpointStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringWhitespaceCount, monthlyCheckpointStringWhitespaceCount));
        this.monthlyCheckpointStringWhitespaceCount = monthlyCheckpointStringWhitespaceCount;
        propagateHierarchyIdToField(monthlyCheckpointStringWhitespaceCount, "monthly_checkpoint_string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnStringWhitespacePercentCheckSpec getMonthlyCheckpointStringWhitespacePercent() {
        return monthlyCheckpointStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyCheckpointStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setMonthlyCheckpointStringWhitespacePercent(ColumnStringWhitespacePercentCheckSpec monthlyCheckpointStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringWhitespacePercent, monthlyCheckpointStringWhitespacePercent));
        this.monthlyCheckpointStringWhitespacePercent = monthlyCheckpointStringWhitespacePercent;
        propagateHierarchyIdToField(monthlyCheckpointStringWhitespacePercent, "monthly_checkpoint_string_whitespace_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnStringSurroundedByWhitespaceCountCheckSpec getMonthlyCheckpointStringSurroundedByWhitespaceCount() {
        return monthlyCheckpointStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param monthlyCheckpointStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setMonthlyCheckpointStringSurroundedByWhitespaceCount(ColumnStringSurroundedByWhitespaceCountCheckSpec monthlyCheckpointStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringSurroundedByWhitespaceCount, monthlyCheckpointStringSurroundedByWhitespaceCount));
        this.monthlyCheckpointStringSurroundedByWhitespaceCount = monthlyCheckpointStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(monthlyCheckpointStringSurroundedByWhitespaceCount, "monthly_checkpoint_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnStringSurroundedByWhitespacePercentCheckSpec getMonthlyCheckpointStringSurroundedByWhitespacePercent() {
        return monthlyCheckpointStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param monthlyCheckpointStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setMonthlyCheckpointStringSurroundedByWhitespacePercent(ColumnStringSurroundedByWhitespacePercentCheckSpec monthlyCheckpointStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringSurroundedByWhitespacePercent, monthlyCheckpointStringSurroundedByWhitespacePercent));
        this.monthlyCheckpointStringSurroundedByWhitespacePercent = monthlyCheckpointStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyCheckpointStringSurroundedByWhitespacePercent, "monthly_checkpoint_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnStringNullPlaceholderCountCheckSpec getMonthlyCheckpointStringNullPlaceholderCount() {
        return monthlyCheckpointStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyCheckpointStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setMonthlyCheckpointStringNullPlaceholderCount(ColumnStringNullPlaceholderCountCheckSpec monthlyCheckpointStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringNullPlaceholderCount, monthlyCheckpointStringNullPlaceholderCount));
        this.monthlyCheckpointStringNullPlaceholderCount = monthlyCheckpointStringNullPlaceholderCount;
        propagateHierarchyIdToField(monthlyCheckpointStringNullPlaceholderCount, "monthly_checkpoint_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnStringNullPlaceholderPercentCheckSpec getMonthlyCheckpointStringNullPlaceholderPercent() {
        return monthlyCheckpointStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyCheckpointStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyCheckpointStringNullPlaceholderPercent(ColumnStringNullPlaceholderPercentCheckSpec monthlyCheckpointStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringNullPlaceholderPercent, monthlyCheckpointStringNullPlaceholderPercent));
        this.monthlyCheckpointStringNullPlaceholderPercent = monthlyCheckpointStringNullPlaceholderPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringNullPlaceholderPercent, "monthly_checkpoint_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnStringBooleanPlaceholderPercentCheckSpec getMonthlyCheckpointStringBooleanPlaceholderPercent() {
        return monthlyCheckpointStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param monthlyCheckpointStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setMonthlyCheckpointStringBooleanPlaceholderPercent(ColumnStringBooleanPlaceholderPercentCheckSpec monthlyCheckpointStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringBooleanPlaceholderPercent, monthlyCheckpointStringBooleanPlaceholderPercent));
        this.monthlyCheckpointStringBooleanPlaceholderPercent = monthlyCheckpointStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringBooleanPlaceholderPercent, "monthly_checkpoint_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnStringParsableToIntegerPercentCheckSpec getMonthlyCheckpointStringParsableToIntegerPercent() {
        return monthlyCheckpointStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param monthlyCheckpointStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setMonthlyCheckpointStringParsableToIntegerPercent(ColumnStringParsableToIntegerPercentCheckSpec monthlyCheckpointStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringParsableToIntegerPercent, monthlyCheckpointStringParsableToIntegerPercent));
        this.monthlyCheckpointStringParsableToIntegerPercent = monthlyCheckpointStringParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringParsableToIntegerPercent, "monthly_checkpoint_string_parsable_to_integer_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnStringParsableToFloatPercentCheckSpec getMonthlyCheckpointStringParsableToFloatPercent() {
        return monthlyCheckpointStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param monthlyCheckpointStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setMonthlyCheckpointStringParsableToFloatPercent(ColumnStringParsableToFloatPercentCheckSpec monthlyCheckpointStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringParsableToFloatPercent, monthlyCheckpointStringParsableToFloatPercent));
        this.monthlyCheckpointStringParsableToFloatPercent = monthlyCheckpointStringParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringParsableToFloatPercent, "monthly_checkpoint_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnStringInSetCountCheckSpec getMonthlyCheckpointStringInSetCount() {
        return monthlyCheckpointStringInSetCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param monthlyCheckpointStringInSetCount Minimum strings in set count check.
     */
    public void setMonthlyCheckpointStringInSetCount(ColumnStringInSetCountCheckSpec monthlyCheckpointStringInSetCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringInSetCount, monthlyCheckpointStringInSetCount));
        this.monthlyCheckpointStringInSetCount = monthlyCheckpointStringInSetCount;
        propagateHierarchyIdToField(monthlyCheckpointStringInSetCount, "monthly_checkpoint_string_in_set_count");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringInSetPercentCheckSpec getMonthlyCheckpointStringInSetPercent() {
        return monthlyCheckpointStringInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param monthlyCheckpointStringInSetPercent Minimum strings in set percent check.
     */
    public void setMonthlyCheckpointStringInSetPercent(ColumnStringInSetPercentCheckSpec monthlyCheckpointStringInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringInSetPercent, monthlyCheckpointStringInSetPercent));
        this.monthlyCheckpointStringInSetPercent = monthlyCheckpointStringInSetPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringInSetPercent, "monthly_checkpoint_string_in_set_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnStringValidDatesPercentCheckSpec getMonthlyCheckpointStringValidDatesPercent() {
        return monthlyCheckpointStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param monthlyCheckpointStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setMonthlyCheckpointStringValidDatesPercent(ColumnStringValidDatesPercentCheckSpec monthlyCheckpointStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringValidDatesPercent, monthlyCheckpointStringValidDatesPercent));
        this.monthlyCheckpointStringValidDatesPercent = monthlyCheckpointStringValidDatesPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringValidDatesPercent, "monthly_checkpoint_string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent check.
     */
    public ColumnStringValidUsaZipcodePercentCheckSpec getMonthlyCheckpointStringValidUsaZipcodePercent() {
        return monthlyCheckpointStringValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid usa zip code percent check.
     * @param monthlyCheckpointStringValidUsaZipcodePercent Minimum string valid usa zip code percent check.
     */
    public void setMonthlyCheckpointStringValidUsaZipcodePercent(ColumnStringValidUsaZipcodePercentCheckSpec monthlyCheckpointStringValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringValidUsaZipcodePercent, monthlyCheckpointStringValidUsaZipcodePercent));
        this.monthlyCheckpointStringValidUsaZipcodePercent = monthlyCheckpointStringValidUsaZipcodePercent;
        propagateHierarchyIdToField(monthlyCheckpointStringValidUsaZipcodePercent, "monthly_checkpoint_string_valid_usa_zipcode_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnStringValidUsaPhonePercentCheckSpec getMonthlyCheckpointStringValidUsaPhonePercent() {
        return monthlyCheckpointStringValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum string valid USA phone percent check.
     * @param monthlyCheckpointStringValidUsaPhonePercent Minimum string valid USA phone percent check.
     */
    public void setMonthlyCheckpointStringValidUsaPhonePercent(ColumnStringValidUsaPhonePercentCheckSpec monthlyCheckpointStringValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringValidUsaPhonePercent, monthlyCheckpointStringValidUsaPhonePercent));
        this.monthlyCheckpointStringValidUsaPhonePercent = monthlyCheckpointStringValidUsaPhonePercent;
        propagateHierarchyIdToField(monthlyCheckpointStringValidUsaPhonePercent, "monthly_checkpoint_string_valid_usa_phone_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent check.
     */
    public ColumnStringValidCountryCodePercentCheckSpec getMonthlyCheckpointStringValidCountryCodePercent() {
        return monthlyCheckpointStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param monthlyCheckpointStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setMonthlyCheckpointStringValidCountryCodePercent(ColumnStringValidCountryCodePercentCheckSpec monthlyCheckpointStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringValidCountryCodePercent, monthlyCheckpointStringValidCountryCodePercent));
        this.monthlyCheckpointStringValidCountryCodePercent = monthlyCheckpointStringValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyCheckpointStringValidCountryCodePercent, "monthly_checkpoint_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent check.
     */
    public ColumnStringValidCurrencyCodePercentCheckSpec getMonthlyCheckpointStringValidCurrencyCodePercent() {
        return monthlyCheckpointStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param monthlyCheckpointStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setMonthlyCheckpointStringValidCurrencyCodePercent(ColumnStringValidCurrencyCodePercentCheckSpec monthlyCheckpointStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringValidCurrencyCodePercent, monthlyCheckpointStringValidCurrencyCodePercent));
        this.monthlyCheckpointStringValidCurrencyCodePercent = monthlyCheckpointStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(monthlyCheckpointStringValidCurrencyCodePercent, "monthly_checkpoint_string_valid_currency_code_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnStringInvalidEmailCountCheckSpec getMonthlyCheckpointStringInvalidEmailCount() {
        return monthlyCheckpointStringInvalidEmailCount;
    }

    /**
     * Sets a new definition of a maximum invalid email count check.
     * @param monthlyCheckpointStringInvalidEmailCount Maximum invalid email count check.
     */
    public void setMonthlyCheckpointStringInvalidEmailCount(ColumnStringInvalidEmailCountCheckSpec monthlyCheckpointStringInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringInvalidEmailCount, monthlyCheckpointStringInvalidEmailCount));
        this.monthlyCheckpointStringInvalidEmailCount = monthlyCheckpointStringInvalidEmailCount;
        propagateHierarchyIdToField(monthlyCheckpointStringInvalidEmailCount, "monthly_checkpoint_string_invalid_email_count");
    }

    /**
     * Returns a minimum valid email percent check.
     * @return Minimum valid email percent check.
     */
    public ColumnStringValidEmailPercentCheckSpec getMonthlyCheckpointStringValidEmailPercent() {
        return monthlyCheckpointStringValidEmailPercent;
    }

    /**
     * Sets a new definition of a minimum valid email percent check.
     * @param monthlyCheckpointStringValidEmailPercent Minimum valid email percent check.
     */
    public void setMonthlyCheckpointStringValidEmailPercent(ColumnStringValidEmailPercentCheckSpec monthlyCheckpointStringValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringValidEmailPercent, monthlyCheckpointStringValidEmailPercent));
        this.monthlyCheckpointStringValidEmailPercent = monthlyCheckpointStringValidEmailPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringValidEmailPercent, "monthly_checkpoint_string_valid_email_percent");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnStringInvalidUuidCountCheckSpec getMonthlyCheckpointStringInvalidUuidCount() {
        return monthlyCheckpointStringInvalidUuidCount;
    }

    /**
     * Sets a new definition of a maximum invalid UUID count check.
     * @param monthlyCheckpointStringInvalidUuidCount Maximum invalid UUID count check.
     */
    public void setMonthlyCheckpointStringInvalidUuidCount(ColumnStringInvalidUuidCountCheckSpec monthlyCheckpointStringInvalidUuidCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringInvalidUuidCount, monthlyCheckpointStringInvalidUuidCount));
        this.monthlyCheckpointStringInvalidUuidCount = monthlyCheckpointStringInvalidUuidCount;
        propagateHierarchyIdToField(monthlyCheckpointStringInvalidUuidCount, "monthly_checkpoint_string_invalid_uuid_count");
    }

    /**
     * Returns a minimum valid UUID percent check.
     * @return Minimum valid UUID percent check.
     */
    public ColumnStringValidUuidPercentCheckSpec getMonthlyCheckpointStringValidUuidPercent() {
        return monthlyCheckpointStringValidUuidPercent;
    }

    /**
     * Sets a new definition of a minimum valid UUID percent check.
     * @param monthlyCheckpointStringValidUuidPercent Minimum valid UUID percent check.
     */
    public void setMonthlyCheckpointStringValidUuidPercent(ColumnStringValidUuidPercentCheckSpec monthlyCheckpointStringValidUuidPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringValidUuidPercent, monthlyCheckpointStringValidUuidPercent));
        this.monthlyCheckpointStringValidUuidPercent = monthlyCheckpointStringValidUuidPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringValidUuidPercent, "monthly_checkpoint_string_valid_uuid_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnStringInvalidIp4AddressCountCheckSpec getMonthlyCheckpointStringInvalidIp4AddressCount() {
        return monthlyCheckpointStringInvalidIp4AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP4 address count check.
     * @param monthlyCheckpointStringInvalidIp4AddressCount Maximum invalid IP4 address count check.
     */
    public void setMonthlyCheckpointStringInvalidIp4AddressCount(ColumnStringInvalidIp4AddressCountCheckSpec monthlyCheckpointStringInvalidIp4AddressCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringInvalidIp4AddressCount, monthlyCheckpointStringInvalidIp4AddressCount));
        this.monthlyCheckpointStringInvalidIp4AddressCount = monthlyCheckpointStringInvalidIp4AddressCount;
        propagateHierarchyIdToField(monthlyCheckpointStringInvalidIp4AddressCount, "monthly_checkpoint_string_invalid_ip4_address_count");
    }

    /**
     * Returns a minimum valid IP4 address percent check.
     * @return Minimum valid IP4 address percent check.
     */
    public ColumnStringValidIp4AddressPercentCheckSpec getMonthlyCheckpointStringValidIp4AddressPercent() {
        return monthlyCheckpointStringValidIp4AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP4 address percent check.
     * @param monthlyCheckpointStringValidIp4AddressPercent Minimum valid IP4 address percent check.
     */
    public void setMonthlyCheckpointStringValidIp4AddressPercent(ColumnStringValidIp4AddressPercentCheckSpec monthlyCheckpointStringValidIp4AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringValidIp4AddressPercent, monthlyCheckpointStringValidIp4AddressPercent));
        this.monthlyCheckpointStringValidIp4AddressPercent = monthlyCheckpointStringValidIp4AddressPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringValidIp4AddressPercent, "monthly_checkpoint_string_valid_ip4_address_percent");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnStringInvalidIp6AddressCountCheckSpec getMonthlyCheckpointStringInvalidIp6AddressCount() {
        return monthlyCheckpointStringInvalidIp6AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP6 address count check.
     * @param monthlyCheckpointStringInvalidIp6AddressCount Maximum invalid IP6 address count check.
     */
    public void setMonthlyCheckpointStringInvalidIp6AddressCount(ColumnStringInvalidIp6AddressCountCheckSpec monthlyCheckpointStringInvalidIp6AddressCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringInvalidIp6AddressCount, monthlyCheckpointStringInvalidIp6AddressCount));
        this.monthlyCheckpointStringInvalidIp6AddressCount = monthlyCheckpointStringInvalidIp6AddressCount;
        propagateHierarchyIdToField(monthlyCheckpointStringInvalidIp6AddressCount, "monthly_checkpoint_string_invalid_ip6_address_count");
    }

    /**
     * Returns a minimum valid IP6 address percent check.
     * @return Minimum valid IP6 address percent check.
     */
    public ColumnStringValidIp6AddressPercentCheckSpec getMonthlyCheckpointStringValidIp6AddressPercent() {
        return monthlyCheckpointStringValidIp6AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP6 address percent check.
     * @param monthlyCheckpointStringValidIp6AddressPercent Minimum valid IP6 address percent check.
     */
    public void setMonthlyCheckpointStringValidIp6AddressPercent(ColumnStringValidIp6AddressPercentCheckSpec monthlyCheckpointStringValidIp6AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringValidIp6AddressPercent, monthlyCheckpointStringValidIp6AddressPercent));
        this.monthlyCheckpointStringValidIp6AddressPercent = monthlyCheckpointStringValidIp6AddressPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringValidIp6AddressPercent, "monthly_checkpoint_string_valid_ip6_address_percent");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnStringNotMatchRegexCountCheckSpec getMonthlyCheckpointStringNotMatchRegexCount() {
        return monthlyCheckpointStringNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param monthlyCheckpointStringNotMatchRegexCount Maximum not match regex count check.
     */
    public void setMonthlyCheckpointStringNotMatchRegexCount(ColumnStringNotMatchRegexCountCheckSpec monthlyCheckpointStringNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringNotMatchRegexCount, monthlyCheckpointStringNotMatchRegexCount));
        this.monthlyCheckpointStringNotMatchRegexCount = monthlyCheckpointStringNotMatchRegexCount;
        propagateHierarchyIdToField(monthlyCheckpointStringNotMatchRegexCount, "monthly_checkpoint_string_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnStringMatchRegexPercentCheckSpec getMonthlyCheckpointStringMatchRegexPercent() {
        return monthlyCheckpointStringMatchRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param monthlyCheckpointStringMatchRegexPercent Minimum match regex percent check.
     */
    public void setMonthlyCheckpointStringMatchRegexPercent(ColumnStringMatchRegexPercentCheckSpec monthlyCheckpointStringMatchRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringMatchRegexPercent, monthlyCheckpointStringMatchRegexPercent));
        this.monthlyCheckpointStringMatchRegexPercent = monthlyCheckpointStringMatchRegexPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringMatchRegexPercent, "monthly_checkpoint_string_match_regex_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnStringNotMatchDateRegexCountCheckSpec getMonthlyCheckpointStringNotMatchDateRegexCount() {
        return monthlyCheckpointStringNotMatchDateRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param monthlyCheckpointStringNotMatchDateRegexCount Maximum not match date regex count check.
     */
    public void setMonthlyCheckpointStringNotMatchDateRegexCount(ColumnStringNotMatchDateRegexCountCheckSpec monthlyCheckpointStringNotMatchDateRegexCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringNotMatchDateRegexCount, monthlyCheckpointStringNotMatchDateRegexCount));
        this.monthlyCheckpointStringNotMatchDateRegexCount = monthlyCheckpointStringNotMatchDateRegexCount;
        propagateHierarchyIdToField(monthlyCheckpointStringNotMatchDateRegexCount, "monthly_checkpoint_string_not_match_date_regex_count");
    }

    /**
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnStringMatchDateRegexPercentCheckSpec getMonthlyCheckpointStringMatchDateRegexPercent() {
        return monthlyCheckpointStringMatchDateRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param monthlyCheckpointStringMatchDateRegexPercent Maximum match date regex percent check.
     */
    public void setMonthlyCheckpointStringMatchDateRegexPercent(ColumnStringMatchDateRegexPercentCheckSpec monthlyCheckpointStringMatchDateRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringMatchDateRegexPercent, monthlyCheckpointStringMatchDateRegexPercent));
        this.monthlyCheckpointStringMatchDateRegexPercent = monthlyCheckpointStringMatchDateRegexPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringMatchDateRegexPercent, "monthly_checkpoint_string_match_date_regex_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnStringMatchNameRegexPercentCheckSpec getMonthlyCheckpointStringMatchNameRegexPercent() {
        return monthlyCheckpointStringMatchNameRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param monthlyCheckpointStringMatchNameRegexPercent Maximum match name regex percent check.
     */
    public void setMonthlyCheckpointStringMatchNameRegexPercent(ColumnStringMatchNameRegexPercentCheckSpec monthlyCheckpointStringMatchNameRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringMatchNameRegexPercent, monthlyCheckpointStringMatchNameRegexPercent));
        this.monthlyCheckpointStringMatchNameRegexPercent = monthlyCheckpointStringMatchNameRegexPercent;
        propagateHierarchyIdToField(monthlyCheckpointStringMatchNameRegexPercent, "monthly_checkpoint_string_match_name_regex_percent");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnStringMostPopularValuesCheckSpec getMonthlyCheckpointStringMostPopularValues() {
        return monthlyCheckpointStringMostPopularValues;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param monthlyCheckpointStringMostPopularValues Most popular values count check.
     */
    public void setMonthlyCheckpointStringMostPopularValues(ColumnStringMostPopularValuesCheckSpec monthlyCheckpointStringMostPopularValues) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointStringMostPopularValues, monthlyCheckpointStringMostPopularValues));
        this.monthlyCheckpointStringMostPopularValues = monthlyCheckpointStringMostPopularValues;
        propagateHierarchyIdToField(monthlyCheckpointStringMostPopularValues, "monthly_checkpoint_string_most_popular_values");
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
