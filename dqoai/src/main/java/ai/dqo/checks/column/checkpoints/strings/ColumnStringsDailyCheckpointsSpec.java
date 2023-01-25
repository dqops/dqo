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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_string_max_length", o -> o.dailyCheckpointStringMaxLength);
            put("daily_checkpoint_string_min_length", o -> o.dailyCheckpointStringMinLength);
            put("daily_checkpoint_string_mean_length", o -> o.dailyCheckpointStringMeanLength);
            put("daily_checkpoint_string_length_above_min_length_count", o -> o.dailyCheckpointStringLengthAboveMinLengthCount);
            put("daily_checkpoint_string_length_below_min_length_percent", o -> o.dailyCheckpointStringLengthBelowMinLengthPercent);


            put("daily_checkpoint_string_empty_count", o -> o.dailyCheckpointStringEmptyCount);
            put("daily_checkpoint_string_empty_percent", o -> o.dailyCheckpointStringEmptyPercent);
            put("daily_checkpoint_string_whitespace_count", o -> o.dailyCheckpointStringWhitespaceCount);
            put("daily_checkpoint_string_whitespace_percent", o -> o.dailyCheckpointStringWhitespacePercent);
            put("daily_checkpoint_string_surrounded_by_whitespace_count", o -> o.dailyCheckpointStringSurroundedByWhitespaceCount);
            put("daily_checkpoint_string_surrounded_by_whitespace_percent", o -> o.dailyCheckpointStringSurroundedByWhitespacePercent);

            put("daily_checkpoint_string_null_placeholder_count", o -> o.dailyCheckpointStringNullPlaceholderCount);
            put("daily_checkpoint_string_null_placeholder_percent", o -> o.dailyCheckpointStringNullPlaceholderPercent);
            put("daily_checkpoint_string_boolean_placeholder_percent", o -> o.dailyCheckpointStringBooleanPlaceholderPercent);
            put("daily_checkpoint_string_parsable_to_integer_percent", o -> o.dailyCheckpointStringParsableToIntegerPercent);
            put("daily_checkpoint_string_parsable_to_float_percent", o -> o.dailyCheckpointStringParsableToFloatPercent);

            put("daily_checkpoint_string_in_set_count", o -> o.dailyCheckpointStringInSetCount);
            put("daily_checkpoint_string_in_set_percent", o -> o.dailyCheckpointStringInSetPercent);

            put("daily_checkpoint_string_valid_dates_percent", o -> o.dailyCheckpointStringValidDatesPercent);
            put("daily_checkpoint_string_valid_usa_zipcode_percent", o -> o.dailyCheckpointStringValidUsaZipcodePercent);
            put("daily_checkpoint_string_valid_usa_phone_percent", o -> o.dailyCheckpointStringValidUsaPhonePercent);
            put("daily_checkpoint_string_valid_country_code_percent", o -> o.dailyCheckpointStringValidCountryCodePercent);
            put("daily_checkpoint_string_valid_currency_code_percent", o -> o.dailyCheckpointStringValidCurrencyCodePercent);
            put("daily_checkpoint_string_invalid_email_count", o -> o.dailyCheckpointStringInvalidEmailCount);
            put("daily_checkpoint_string_valid_email_percent", o -> o.dailyCheckpointStringValidEmailPercent);
            put("daily_checkpoint_string_invalid_uuid_count", o -> o.dailyCheckpointStringInvalidUuidCount);
            put("daily_checkpoint_string_valid_uuid_percent", o -> o.dailyCheckpointStringValidUuidPercent);
            put("daily_checkpoint_string_invalid_ip4_address_count", o -> o.dailyCheckpointStringInvalidIp4AddressCount);
            put("daily_checkpoint_string_valid_ip4_address_percent", o -> o.dailyCheckpointStringValidIp4AddressPercent);
            put("daily_checkpoint_string_invalid_ip6_address_count", o -> o.dailyCheckpointStringInvalidIp6AddressCount);
            put("daily_checkpoint_string_valid_ip6_address_percent", o -> o.dailyCheckpointStringValidIp6AddressPercent);




            put("daily_checkpoint_string_not_match_regex_count", o -> o.dailyCheckpointStringNotMatchRegexCount);
            put("daily_checkpoint_string_match_regex_percent", o -> o.dailyCheckpointStringMatchRegexPercent);
            put("daily_checkpoint_string_not_match_date_regex_count", o -> o.dailyCheckpointStringNotMatchDateRegexCount);
            put("daily_checkpoint_string_match_date_regex_percent", o -> o.dailyCheckpointStringMatchDateRegexPercent);
            put("daily_checkpoint_string_match_name_regex_percent", o -> o.dailyCheckpointStringMatchNameRegexPercent);

            put("daily_checkpoint_string_most_popular_values", o -> o.dailyCheckpointStringMostPopularValues);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringMaxLengthCheckSpec dailyCheckpointStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringMinLengthCheckSpec dailyCheckpointStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringMeanLengthCheckSpec dailyCheckpointStringMeanLength;

    @JsonPropertyDescription("The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringLengthAboveMinLengthCountCheckSpec dailyCheckpointStringLengthAboveMinLengthCount;

    @JsonPropertyDescription("The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringLengthBelowMinLengthPercentCheckSpec dailyCheckpointStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringEmptyCountCheckSpec dailyCheckpointStringEmptyCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringEmptyPercentCheckSpec dailyCheckpointStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringWhitespaceCountCheckSpec dailyCheckpointStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringWhitespacePercentCheckSpec dailyCheckpointStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringSurroundedByWhitespaceCountCheckSpec dailyCheckpointStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringSurroundedByWhitespacePercentCheckSpec dailyCheckpointStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringNullPlaceholderCountCheckSpec dailyCheckpointStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringNullPlaceholderPercentCheckSpec dailyCheckpointStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringBooleanPlaceholderPercentCheckSpec dailyCheckpointStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringParsableToIntegerPercentCheckSpec dailyCheckpointStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringParsableToFloatPercentCheckSpec dailyCheckpointStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the number of strings from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringInSetCountCheckSpec dailyCheckpointStringInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of strings from a set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringInSetPercentCheckSpec dailyCheckpointStringInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringValidDatesPercentCheckSpec dailyCheckpointStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringValidUsaZipcodePercentCheckSpec dailyCheckpointStringValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringValidUsaPhonePercentCheckSpec dailyCheckpointStringValidUsaPhonePercent;
    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringValidCountryCodePercentCheckSpec dailyCheckpointStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringValidCurrencyCodePercentCheckSpec dailyCheckpointStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringInvalidEmailCountCheckSpec dailyCheckpointStringInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringValidEmailPercentCheckSpec dailyCheckpointStringValidEmailPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringInvalidUuidCountCheckSpec dailyCheckpointStringInvalidUuidCount;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringValidUuidPercentCheckSpec dailyCheckpointStringValidUuidPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringInvalidIp4AddressCountCheckSpec dailyCheckpointStringInvalidIp4AddressCount;

    @JsonPropertyDescription("Verifies that the percentage of valid IP4 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringValidIp4AddressPercentCheckSpec dailyCheckpointStringValidIp4AddressPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringInvalidIp6AddressCountCheckSpec dailyCheckpointStringInvalidIp6AddressCount;

    @JsonPropertyDescription("Verifies that the percentage of valid IP6 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringValidIp6AddressPercentCheckSpec dailyCheckpointStringValidIp6AddressPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringNotMatchRegexCountCheckSpec dailyCheckpointStringNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringRegexMatchPercentCheckSpec dailyCheckpointStringMatchRegexPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringNotMatchDateRegexCountCheckSpec dailyCheckpointStringNotMatchDateRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringMatchDateRegexPercentCheckSpec dailyCheckpointStringMatchDateRegexPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the name format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringMatchNameRegexPercentCheckSpec dailyCheckpointStringMatchNameRegexPercent;

    @JsonPropertyDescription("Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.")
    private ColumnStringMostPopularValuesCheckSpec dailyCheckpointStringMostPopularValues;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnStringMaxLengthCheckSpec getDailyCheckpointStringMaxLength() {
        return dailyCheckpointStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param dailyCheckpointStringMaxLength Maximum string length below check.
     */
    public void setDailyCheckpointStringMaxLength(ColumnStringMaxLengthCheckSpec dailyCheckpointStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringMaxLength, dailyCheckpointStringMaxLength));
        this.dailyCheckpointStringMaxLength = dailyCheckpointStringMaxLength;
        propagateHierarchyIdToField(dailyCheckpointStringMaxLength, "daily_checkpoint_string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length below check.
     */
    public ColumnStringMinLengthCheckSpec getDailyCheckpointStringMinLength() {
        return dailyCheckpointStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param dailyCheckpointStringMinLength Minimum string length above check.
     */
    public void setDailyCheckpointStringMinLength(ColumnStringMinLengthCheckSpec dailyCheckpointStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringMinLength, dailyCheckpointStringMinLength));
        this.dailyCheckpointStringMinLength = dailyCheckpointStringMinLength;
        propagateHierarchyIdToField(dailyCheckpointStringMinLength, "daily_checkpoint_string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnStringMeanLengthCheckSpec getDailyCheckpointStringMeanLength() {
        return dailyCheckpointStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param dailyCheckpointStringMeanLength Mean string length between check.
     */
    public void setDailyCheckpointStringMeanLength(ColumnStringMeanLengthCheckSpec dailyCheckpointStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringMeanLength, dailyCheckpointStringMeanLength));
        this.dailyCheckpointStringMeanLength = dailyCheckpointStringMeanLength;
        propagateHierarchyIdToField(dailyCheckpointStringMeanLength, "daily_checkpoint_string_mean_length");
    }

    /**
     * Returns a string length above min length count check.
     * @return String length above min length count check.
     */
    public ColumnStringLengthAboveMinLengthCountCheckSpec getDailyCheckpointStringLengthAboveMinLengthCount() {
        return dailyCheckpointStringLengthAboveMinLengthCount;
    }

    /**
     * Sets a new definition of a string length above min length count check.
     * @param dailyCheckpointStringLengthAboveMinLengthCount String length above min length count check.
     */
    public void setDailyCheckpointStringLengthAboveMinLengthCount(ColumnStringLengthAboveMinLengthCountCheckSpec dailyCheckpointStringLengthAboveMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringLengthAboveMinLengthCount, dailyCheckpointStringLengthAboveMinLengthCount));
        this.dailyCheckpointStringLengthAboveMinLengthCount = dailyCheckpointStringLengthAboveMinLengthCount;
        propagateHierarchyIdToField(dailyCheckpointStringLengthAboveMinLengthCount, "daily_checkpoint_string_length_above_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return Mean string length below min length percent check.
     */
    public ColumnStringLengthBelowMinLengthPercentCheckSpec getDailyCheckpointStringLengthBelowMinLengthPercent() {
        return dailyCheckpointStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param dailyCheckpointStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setDailyCheckpointStringLengthBelowMinLengthPercent(ColumnStringLengthBelowMinLengthPercentCheckSpec dailyCheckpointStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringLengthBelowMinLengthPercent, dailyCheckpointStringLengthBelowMinLengthPercent));
        this.dailyCheckpointStringLengthBelowMinLengthPercent = dailyCheckpointStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(dailyCheckpointStringLengthBelowMinLengthPercent, "daily_checkpoint_string_length_below_min_length_percent");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnStringEmptyCountCheckSpec getDailyCheckpointStringEmptyCount() {
        return dailyCheckpointStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyCheckpointStringEmptyCount Max string empty count check.
     */
    public void setDailyCheckpointStringEmptyCount(ColumnStringEmptyCountCheckSpec dailyCheckpointStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringEmptyCount, dailyCheckpointStringEmptyCount));
        this.dailyCheckpointStringEmptyCount = dailyCheckpointStringEmptyCount;
        propagateHierarchyIdToField(dailyCheckpointStringEmptyCount, "daily_checkpoint_string_empty_count");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnStringEmptyPercentCheckSpec getDailyCheckpointStringEmptyPercent() {
        return dailyCheckpointStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param dailyCheckpointStringEmptyPercent Maximum string empty percent check.
     */
    public void setDailyCheckpointStringEmptyPercent(ColumnStringEmptyPercentCheckSpec dailyCheckpointStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringEmptyPercent, dailyCheckpointStringEmptyPercent));
        this.dailyCheckpointStringEmptyPercent = dailyCheckpointStringEmptyPercent;
        propagateHierarchyIdToField(dailyCheckpointStringEmptyPercent, "daily_checkpoint_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnStringWhitespaceCountCheckSpec getDailyCheckpointStringWhitespaceCount() {
        return dailyCheckpointStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param dailyCheckpointStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setDailyCheckpointStringWhitespaceCount(ColumnStringWhitespaceCountCheckSpec dailyCheckpointStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringWhitespaceCount, dailyCheckpointStringWhitespaceCount));
        this.dailyCheckpointStringWhitespaceCount = dailyCheckpointStringWhitespaceCount;
        propagateHierarchyIdToField(dailyCheckpointStringWhitespaceCount, "daily_checkpoint_string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnStringWhitespacePercentCheckSpec getDailyCheckpointStringWhitespacePercent() {
        return dailyCheckpointStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param dailyCheckpointStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setDailyCheckpointStringWhitespacePercent(ColumnStringWhitespacePercentCheckSpec dailyCheckpointStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringWhitespacePercent, dailyCheckpointStringWhitespacePercent));
        this.dailyCheckpointStringWhitespacePercent = dailyCheckpointStringWhitespacePercent;
        propagateHierarchyIdToField(dailyCheckpointStringWhitespacePercent, "daily_checkpoint_string_whitespace_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnStringSurroundedByWhitespaceCountCheckSpec getDailyCheckpointStringSurroundedByWhitespaceCount() {
        return dailyCheckpointStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param dailyCheckpointStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setDailyCheckpointStringSurroundedByWhitespaceCount(ColumnStringSurroundedByWhitespaceCountCheckSpec dailyCheckpointStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringSurroundedByWhitespaceCount, dailyCheckpointStringSurroundedByWhitespaceCount));
        this.dailyCheckpointStringSurroundedByWhitespaceCount = dailyCheckpointStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(dailyCheckpointStringSurroundedByWhitespaceCount, "daily_checkpoint_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnStringSurroundedByWhitespacePercentCheckSpec getDailyCheckpointStringSurroundedByWhitespacePercent() {
        return dailyCheckpointStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param dailyCheckpointStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setDailyCheckpointStringSurroundedByWhitespacePercent(ColumnStringSurroundedByWhitespacePercentCheckSpec dailyCheckpointStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringSurroundedByWhitespacePercent, dailyCheckpointStringSurroundedByWhitespacePercent));
        this.dailyCheckpointStringSurroundedByWhitespacePercent = dailyCheckpointStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyCheckpointStringSurroundedByWhitespacePercent, "daily_checkpoint_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnStringNullPlaceholderCountCheckSpec getDailyCheckpointStringNullPlaceholderCount() {
        return dailyCheckpointStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param dailyCheckpointStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setDailyCheckpointStringNullPlaceholderCount(ColumnStringNullPlaceholderCountCheckSpec dailyCheckpointStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringNullPlaceholderCount, dailyCheckpointStringNullPlaceholderCount));
        this.dailyCheckpointStringNullPlaceholderCount = dailyCheckpointStringNullPlaceholderCount;
        propagateHierarchyIdToField(dailyCheckpointStringNullPlaceholderCount, "daily_checkpoint_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnStringNullPlaceholderPercentCheckSpec getDailyCheckpointStringNullPlaceholderPercent() {
        return dailyCheckpointStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param dailyCheckpointStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setDailyCheckpointStringNullPlaceholderPercent(ColumnStringNullPlaceholderPercentCheckSpec dailyCheckpointStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringNullPlaceholderPercent, dailyCheckpointStringNullPlaceholderPercent));
        this.dailyCheckpointStringNullPlaceholderPercent = dailyCheckpointStringNullPlaceholderPercent;
        propagateHierarchyIdToField(dailyCheckpointStringNullPlaceholderPercent, "daily_checkpoint_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnStringBooleanPlaceholderPercentCheckSpec getDailyCheckpointStringBooleanPlaceholderPercent() {
        return dailyCheckpointStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param dailyCheckpointStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setDailyCheckpointStringBooleanPlaceholderPercent(ColumnStringBooleanPlaceholderPercentCheckSpec dailyCheckpointStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringBooleanPlaceholderPercent, dailyCheckpointStringBooleanPlaceholderPercent));
        this.dailyCheckpointStringBooleanPlaceholderPercent = dailyCheckpointStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(dailyCheckpointStringBooleanPlaceholderPercent, "daily_checkpoint_string_boolean_placeholder_percent");
    }

    /**
    * Returns a minimum string parsable to integer percent check.
    * @return Minimum string parsable to integer percent check.
    */
    public ColumnStringParsableToIntegerPercentCheckSpec getDailyCheckpointStringParsableToIntegerPercent() {
        return dailyCheckpointStringParsableToIntegerPercent;
    }

    /**
    * Sets a new definition of a minimum string parsable to integer percent check.
    * @param dailyCheckpointStringParsableToIntegerPercent Minimum string parsable to integer percent check.
    */
    public void setDailyCheckpointStringParsableToIntegerPercent(ColumnStringParsableToIntegerPercentCheckSpec dailyCheckpointStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringParsableToIntegerPercent, dailyCheckpointStringParsableToIntegerPercent));
        this.dailyCheckpointStringParsableToIntegerPercent = dailyCheckpointStringParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyCheckpointStringParsableToIntegerPercent, "daily_checkpoint_string_parsable_to_integer_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnStringParsableToFloatPercentCheckSpec getDailyCheckpointStringParsableToFloatPercent() {
        return dailyCheckpointStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param dailyCheckpointStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setDailyCheckpointStringParsableToFloatPercent(ColumnStringParsableToFloatPercentCheckSpec dailyCheckpointStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringParsableToFloatPercent, dailyCheckpointStringParsableToFloatPercent));
        this.dailyCheckpointStringParsableToFloatPercent = dailyCheckpointStringParsableToFloatPercent;
        propagateHierarchyIdToField(dailyCheckpointStringParsableToFloatPercent, "daily_checkpoint_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnStringInSetCountCheckSpec getDailyCheckpointStringInSetCount() {
        return dailyCheckpointStringInSetCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param dailyCheckpointStringInSetCount Minimum strings in set count check.
     */
    public void setDailyCheckpointStringInSetCount(ColumnStringInSetCountCheckSpec dailyCheckpointStringInSetCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringInSetCount, dailyCheckpointStringInSetCount));
        this.dailyCheckpointStringInSetCount = dailyCheckpointStringInSetCount;
        propagateHierarchyIdToField(dailyCheckpointStringInSetCount, "daily_checkpoint_string_in_set_count");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringInSetPercentCheckSpec getDailyCheckpointStringInSetPercent() {
        return dailyCheckpointStringInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param dailyCheckpointStringInSetPercent Minimum strings in set percent check.
     */
    public void setDailyCheckpointStringInSetPercent(ColumnStringInSetPercentCheckSpec dailyCheckpointStringInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringInSetPercent, dailyCheckpointStringInSetPercent));
        this.dailyCheckpointStringInSetPercent = dailyCheckpointStringInSetPercent;
        propagateHierarchyIdToField(dailyCheckpointStringInSetPercent, "daily_checkpoint_string_in_set_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnStringValidDatesPercentCheckSpec getDailyCheckpointStringValidDatesPercent() {
        return dailyCheckpointStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param dailyCheckpointStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setDailyCheckpointStringValidDatesPercent(ColumnStringValidDatesPercentCheckSpec dailyCheckpointStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringValidDatesPercent, dailyCheckpointStringValidDatesPercent));
        this.dailyCheckpointStringValidDatesPercent = dailyCheckpointStringValidDatesPercent;
        propagateHierarchyIdToField(dailyCheckpointStringValidDatesPercent, "daily_checkpoint_string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent check.
     */
    public ColumnStringValidUsaZipcodePercentCheckSpec getDailyCheckpointStringValidUsaZipcodePercent() {
        return dailyCheckpointStringValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid usa zip code percent check.
     * @param dailyCheckpointStringValidUsaZipcodePercent Minimum string valid usa zip code percent check.
     */
    public void setDailyCheckpointStringValidUsaZipcodePercent(ColumnStringValidUsaZipcodePercentCheckSpec dailyCheckpointStringValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringValidUsaZipcodePercent, dailyCheckpointStringValidUsaZipcodePercent));
        this.dailyCheckpointStringValidUsaZipcodePercent = dailyCheckpointStringValidUsaZipcodePercent;
        propagateHierarchyIdToField(dailyCheckpointStringValidUsaZipcodePercent, "daily_checkpoint_string_valid_usa_zipcode_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnStringValidUsaPhonePercentCheckSpec getDailyCheckpointStringValidUsaPhonePercent() {
        return dailyCheckpointStringValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum string valid USA phone percent check.
     * @param dailyCheckpointStringValidUsaPhonePercent Minimum string valid USA phone percent check.
     */
    public void setDailyCheckpointStringValidUsaPhonePercent(ColumnStringValidUsaPhonePercentCheckSpec dailyCheckpointStringValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringValidUsaPhonePercent, dailyCheckpointStringValidUsaPhonePercent));
        this.dailyCheckpointStringValidUsaPhonePercent = dailyCheckpointStringValidUsaPhonePercent;
        propagateHierarchyIdToField(dailyCheckpointStringValidUsaPhonePercent, "daily_checkpoint_string_valid_usa_phone_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent check.
     */
    public ColumnStringValidCountryCodePercentCheckSpec getDailyCheckpointStringValidCountryCodePercent() {
        return dailyCheckpointStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param dailyCheckpointStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setDailyCheckpointStringValidCountryCodePercent(ColumnStringValidCountryCodePercentCheckSpec dailyCheckpointStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringValidCountryCodePercent, dailyCheckpointStringValidCountryCodePercent));
        this.dailyCheckpointStringValidCountryCodePercent = dailyCheckpointStringValidCountryCodePercent;
        propagateHierarchyIdToField(dailyCheckpointStringValidCountryCodePercent, "daily_checkpoint_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent check.
     */
    public ColumnStringValidCurrencyCodePercentCheckSpec getDailyCheckpointStringValidCurrencyCodePercent() {
        return dailyCheckpointStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param dailyCheckpointStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setDailyCheckpointStringValidCurrencyCodePercent(ColumnStringValidCurrencyCodePercentCheckSpec dailyCheckpointStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringValidCurrencyCodePercent, dailyCheckpointStringValidCurrencyCodePercent));
        this.dailyCheckpointStringValidCurrencyCodePercent = dailyCheckpointStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(dailyCheckpointStringValidCurrencyCodePercent, "daily_checkpoint_string_valid_currency_code_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnStringInvalidEmailCountCheckSpec getDailyCheckpointStringInvalidEmailCount() {
        return dailyCheckpointStringInvalidEmailCount;
    }

    /**
     * Sets a new definition of a maximum invalid email count check.
     * @param dailyCheckpointStringInvalidEmailCount Maximum invalid email count check.
     */
    public void setDailyCheckpointStringInvalidEmailCount(ColumnStringInvalidEmailCountCheckSpec dailyCheckpointStringInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringInvalidEmailCount, dailyCheckpointStringInvalidEmailCount));
        this.dailyCheckpointStringInvalidEmailCount = dailyCheckpointStringInvalidEmailCount;
        propagateHierarchyIdToField(dailyCheckpointStringInvalidEmailCount, "daily_checkpoint_string_invalid_email_count");
    }

    /**
     * Returns a minimum valid email percent check.
     * @return Minimum valid email percent check.
     */
    public ColumnStringValidEmailPercentCheckSpec getDailyCheckpointStringValidEmailPercent() {
        return dailyCheckpointStringValidEmailPercent;
    }

    /**
     * Sets a new definition of a minimum valid email percent check.
     * @param dailyCheckpointStringValidEmailPercent Minimum valid email percent check.
     */
    public void setDailyCheckpointStringValidEmailPercent(ColumnStringValidEmailPercentCheckSpec dailyCheckpointStringValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringValidEmailPercent, dailyCheckpointStringValidEmailPercent));
        this.dailyCheckpointStringValidEmailPercent = dailyCheckpointStringValidEmailPercent;
        propagateHierarchyIdToField(dailyCheckpointStringValidEmailPercent, "daily_checkpoint_string_valid_email_percent");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnStringInvalidUuidCountCheckSpec getDailyCheckpointStringInvalidUuidCount() {
        return dailyCheckpointStringInvalidUuidCount;
    }

    /**
     * Sets a new definition of a maximum invalid UUID count check.
     * @param dailyCheckpointStringInvalidUuidCount Maximum invalid UUID count check.
     */
    public void setDailyCheckpointStringInvalidUuidCount(ColumnStringInvalidUuidCountCheckSpec dailyCheckpointStringInvalidUuidCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringInvalidUuidCount, dailyCheckpointStringInvalidUuidCount));
        this.dailyCheckpointStringInvalidUuidCount = dailyCheckpointStringInvalidUuidCount;
        propagateHierarchyIdToField(dailyCheckpointStringInvalidUuidCount, "daily_checkpoint_string_invalid_uuid_count");
    }

    /**
     * Returns a minimum valid UUID percent check.
     * @return Minimum valid UUID percent check.
     */
    public ColumnStringValidUuidPercentCheckSpec getDailyCheckpointStringValidUuidPercent() {
        return dailyCheckpointStringValidUuidPercent;
    }

    /**
     * Sets a new definition of a minimum valid UUID percent check.
     * @param dailyCheckpointStringValidUuidPercent Minimum valid UUID percent check.
     */
    public void setDailyCheckpointStringValidUuidPercent(ColumnStringValidUuidPercentCheckSpec dailyCheckpointStringValidUuidPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringValidUuidPercent, dailyCheckpointStringValidUuidPercent));
        this.dailyCheckpointStringValidUuidPercent = dailyCheckpointStringValidUuidPercent;
        propagateHierarchyIdToField(dailyCheckpointStringValidUuidPercent, "daily_checkpoint_string_valid_uuid_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnStringInvalidIp4AddressCountCheckSpec getDailyCheckpointStringInvalidIp4AddressCount() {
        return dailyCheckpointStringInvalidIp4AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP4 address count check.
     * @param dailyCheckpointStringInvalidIp4AddressCount Maximum invalid IP4 address count check.
     */
    public void setDailyCheckpointStringInvalidIp4AddressCount(ColumnStringInvalidIp4AddressCountCheckSpec dailyCheckpointStringInvalidIp4AddressCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringInvalidIp4AddressCount, dailyCheckpointStringInvalidIp4AddressCount));
        this.dailyCheckpointStringInvalidIp4AddressCount = dailyCheckpointStringInvalidIp4AddressCount;
        propagateHierarchyIdToField(dailyCheckpointStringInvalidIp4AddressCount, "daily_checkpoint_string_invalid_ip4_address_count");
    }

    /**
     * Returns a minimum valid IP4 address percent check.
     * @return Minimum valid IP4 address percent check.
     */
    public ColumnStringValidIp4AddressPercentCheckSpec getDailyCheckpointStringValidIp4AddressPercent() {
        return dailyCheckpointStringValidIp4AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP4 address percent check.
     * @param dailyCheckpointStringValidIp4AddressPercent Minimum valid IP4 address percent check.
     */
    public void setDailyCheckpointStringValidIp4AddressPercent(ColumnStringValidIp4AddressPercentCheckSpec dailyCheckpointStringValidIp4AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringValidIp4AddressPercent, dailyCheckpointStringValidIp4AddressPercent));
        this.dailyCheckpointStringValidIp4AddressPercent = dailyCheckpointStringValidIp4AddressPercent;
        propagateHierarchyIdToField(dailyCheckpointStringValidIp4AddressPercent, "daily_checkpoint_string_valid_ip4_address_percent");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnStringInvalidIp6AddressCountCheckSpec getDailyCheckpointStringInvalidIp6AddressCount() {
        return dailyCheckpointStringInvalidIp6AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP6 address count check.
     * @param dailyCheckpointStringInvalidIp6AddressCount Maximum invalid IP6 address count check.
     */
    public void setDailyCheckpointStringInvalidIp6AddressCount(ColumnStringInvalidIp6AddressCountCheckSpec dailyCheckpointStringInvalidIp6AddressCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringInvalidIp6AddressCount, dailyCheckpointStringInvalidIp6AddressCount));
        this.dailyCheckpointStringInvalidIp6AddressCount = dailyCheckpointStringInvalidIp6AddressCount;
        propagateHierarchyIdToField(dailyCheckpointStringInvalidIp6AddressCount, "daily_checkpoint_string_invalid_ip6_address_count");
    }

    /**
     * Returns a minimum valid IP6 address percent check.
     * @return Minimum valid IP6 address percent check.
     */
    public ColumnStringValidIp6AddressPercentCheckSpec getDailyCheckpointStringValidIp6AddressPercent() {
        return dailyCheckpointStringValidIp6AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP6 address percent check.
     * @param dailyCheckpointStringValidIp6AddressPercent Minimum valid IP6 address percent check.
     */
    public void setDailyCheckpointStringValidIp6AddressPercent(ColumnStringValidIp6AddressPercentCheckSpec dailyCheckpointStringValidIp6AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringValidIp6AddressPercent, dailyCheckpointStringValidIp6AddressPercent));
        this.dailyCheckpointStringValidIp6AddressPercent = dailyCheckpointStringValidIp6AddressPercent;
        propagateHierarchyIdToField(dailyCheckpointStringValidIp6AddressPercent, "daily_checkpoint_string_valid_ip6_address_percent");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnStringNotMatchRegexCountCheckSpec getDailyCheckpointStringNotMatchRegexCount() {
        return dailyCheckpointStringNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param dailyCheckpointStringNotMatchRegexCount Maximum not match regex count check.
     */
    public void setDailyCheckpointStringNotMatchRegexCount(ColumnStringNotMatchRegexCountCheckSpec dailyCheckpointStringNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringNotMatchRegexCount, dailyCheckpointStringNotMatchRegexCount));
        this.dailyCheckpointStringNotMatchRegexCount = dailyCheckpointStringNotMatchRegexCount;
        propagateHierarchyIdToField(dailyCheckpointStringNotMatchRegexCount, "daily_checkpoint_string_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnStringRegexMatchPercentCheckSpec getDailyCheckpointStringMatchRegexPercent() {
        return dailyCheckpointStringMatchRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param dailyCheckpointStringMatchRegexPercent Minimum match regex percent check.
     */
    public void setDailyCheckpointStringMatchRegexPercent(ColumnStringRegexMatchPercentCheckSpec dailyCheckpointStringMatchRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringMatchRegexPercent, dailyCheckpointStringMatchRegexPercent));
        this.dailyCheckpointStringMatchRegexPercent = dailyCheckpointStringMatchRegexPercent;
        propagateHierarchyIdToField(dailyCheckpointStringMatchRegexPercent, "daily_checkpoint_string_match_regex_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnStringNotMatchDateRegexCountCheckSpec getDailyCheckpointStringNotMatchDateRegexCount() {
        return dailyCheckpointStringNotMatchDateRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param dailyCheckpointStringNotMatchDateRegexCount Maximum not match date regex count check.
     */
    public void setDailyCheckpointStringNotMatchDateRegexCount(ColumnStringNotMatchDateRegexCountCheckSpec dailyCheckpointStringNotMatchDateRegexCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringNotMatchDateRegexCount, dailyCheckpointStringNotMatchDateRegexCount));
        this.dailyCheckpointStringNotMatchDateRegexCount = dailyCheckpointStringNotMatchDateRegexCount;
        propagateHierarchyIdToField(dailyCheckpointStringNotMatchDateRegexCount, "daily_checkpoint_string_not_match_date_regex_count");
    }

    /**
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnStringMatchDateRegexPercentCheckSpec getDailyCheckpointStringMatchDateRegexPercent() {
        return dailyCheckpointStringMatchDateRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param dailyCheckpointStringMatchDateRegexPercent Maximum match date regex percent check.
     */
    public void setDailyCheckpointStringMatchDateRegexPercent(ColumnStringMatchDateRegexPercentCheckSpec dailyCheckpointStringMatchDateRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringMatchDateRegexPercent, dailyCheckpointStringMatchDateRegexPercent));
        this.dailyCheckpointStringMatchDateRegexPercent = dailyCheckpointStringMatchDateRegexPercent;
        propagateHierarchyIdToField(dailyCheckpointStringMatchDateRegexPercent, "daily_checkpoint_string_match_date_regex_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnStringMatchNameRegexPercentCheckSpec getDailyCheckpointStringMatchNameRegexPercent() {
        return dailyCheckpointStringMatchNameRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param dailyCheckpointStringMatchNameRegexPercent Maximum match name regex percent check.
     */
    public void setDailyCheckpointStringMatchNameRegexPercent(ColumnStringMatchNameRegexPercentCheckSpec dailyCheckpointStringMatchNameRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringMatchNameRegexPercent, dailyCheckpointStringMatchNameRegexPercent));
        this.dailyCheckpointStringMatchNameRegexPercent = dailyCheckpointStringMatchNameRegexPercent;
        propagateHierarchyIdToField(dailyCheckpointStringMatchNameRegexPercent, "daily_checkpoint_string_match_name_regex_percent");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnStringMostPopularValuesCheckSpec getDailyCheckpointStringMostPopularValues() {
        return dailyCheckpointStringMostPopularValues;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param dailyCheckpointStringMostPopularValues Most popular values count check.
     */
    public void setDailyCheckpointStringMostPopularValues(ColumnStringMostPopularValuesCheckSpec dailyCheckpointStringMostPopularValues) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointStringMostPopularValues, dailyCheckpointStringMostPopularValues));
        this.dailyCheckpointStringMostPopularValues = dailyCheckpointStringMostPopularValues;
        propagateHierarchyIdToField(dailyCheckpointStringMostPopularValues, "daily_checkpoint_string_most_popular_values");
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
