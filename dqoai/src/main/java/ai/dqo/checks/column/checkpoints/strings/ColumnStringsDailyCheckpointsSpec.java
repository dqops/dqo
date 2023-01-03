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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_max_string_length_below", o -> o.dailyCheckpointMaxStringLengthBelow);
            put("daily_checkpoint_min_string_length_above", o -> o.dailyCheckpointMinStringLengthAbove);
            put("daily_checkpoint_mean_string_length_between", o -> o.dailyCheckpointMeanStringLengthBetween);
            put("daily_checkpoint_max_string_empty_percent", o -> o.dailyCheckpointMaxStringEmptyPercent);
            put("daily_checkpoint_max_string_empty_count", o -> o.dailyCheckpointMaxStringEmptyCount);
            put("daily_checkpoint_max_string_whitespace_count", o -> o.dailyCheckpointMaxStringWhitespaceCount);
            put("daily_checkpoint_max_string_whitespace_percent", o -> o.dailyCheckpointMaxStringWhitespacePercent);
            put("daily_checkpoint_min_string_valid_dates_percent", o -> o.dailyCheckpointMinStringValidDatesPercent);
            put("daily_checkpoint_max_string_null_placeholder_count", o -> o.dailyCheckpointMaxStringNullPlaceholderCount);
            put("daily_checkpoint_max_string_null_placeholder_percent", o -> o.dailyCheckpointMaxStringNullPlaceholderPercent);
            put("daily_checkpoint_min_string_boolean_placeholder_percent", o -> o.dailyCheckpointMinStringBooleanPlaceholderPercent);
            put("daily_checkpoint_min_string_parsable_to_integer_percent", o -> o.dailyCheckpointMinStringParsableToIntegerPercent);
            put("daily_checkpoint_max_string_surrounded_by_whitespace_count", o -> o.dailyCheckpointMaxStringSurroundedByWhitespaceCount);
            put("daily_checkpoint_max_string_surrounded_by_whitespace_percent", o -> o.dailyCheckpointMaxStringSurroundedByWhitespacePercent);
            put("daily_checkpoint_min_string_parsable_to_float_percent", o -> o.dailyCheckpointMinStringParsableToFloatPercent);
            put("daily_checkpoint_min_string_valid_usa_zipcode_percent", o -> o.dailyCheckpointMinStringValidUsaZipcodePercent);
            put("daily_checkpoint_min_string_valid_usa_phone_percent", o -> o.dailyCheckpointMinStringValidUsaPhonePercent);
            put("daily_checkpoint_min_string_valid_country_code_percent", o -> o.dailyCheckpointMinStringValidCountryCodePercent);
            put("daily_checkpoint_min_string_valid_currency_code_percent", o -> o.dailyCheckpointMinStringValidCurrencyCodePercent);
            put("daily_checkpoint_min_strings_in_set_count", o -> o.dailyCheckpointMinStringsInSetCount);
            put("daily_checkpoint_min_strings_in_set_percent", o -> o.dailyCheckpointMinStringsInSetPercent);
            put("daily_checkpoint_max_strings_invalid_email_count", o -> o.dailyCheckpointMaxInvalidEmailCount);
            put("daily_checkpoint_min_valid_email_percent", o -> o.dailyCheckpointMinValidEmailPercent);
            put("daily_checkpoint_max_not_match_regex_count", o -> o.dailyCheckpointMaxNotMatchRegexCount);
            put("daily_checkpoint_min_regex_match_percent", o -> o.dailyCheckpointMinRegexMatchPercent);
            put("daily_checkpoint_max_not_match_date_regex_count", o -> o.dailyCheckpointMaxNotMatchDateRegexCount);

        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringLengthBelowCheckSpec dailyCheckpointMaxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringLengthAboveCheckSpec dailyCheckpointMinStringLengthAbove;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMeanStringLengthBetweenCheckSpec dailyCheckpointMeanStringLengthBetween;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringEmptyPercentCheckSpec dailyCheckpointMaxStringEmptyPercent;

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringEmptyCountCheckSpec dailyCheckpointMaxStringEmptyCount;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringValidDatesPercentCheckSpec dailyCheckpointMinStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringWhitespaceCountCheckSpec dailyCheckpointMaxStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringWhitespacePercentCheckSpec dailyCheckpointMaxStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringNullPlaceholderCountCheckSpec dailyCheckpointMaxStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringNullPlaceholderPercentCheckSpec dailyCheckpointMaxStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringBooleanPlaceholderPercentCheckSpec dailyCheckpointMinStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringParsableToIntegerPercentCheckSpec dailyCheckpointMinStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringSurroundedByWhitespaceCountCheckSpec dailyCheckpointMaxStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxStringSurroundedByWhitespacePercentCheckSpec dailyCheckpointMaxStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringParsableToFloatPercentCheckSpec dailyCheckpointMinStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringValidUsaZipcodePercentCheckSpec dailyCheckpointMinStringValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinStringValidUsaPhonePercentCheckSpec dailyCheckpointMinStringValidUsaPhonePercent;
    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinValidCountryCodePercentCheckSpec dailyCheckpointMinStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinValidCurrencyCodePercentCheckSpec dailyCheckpointMinStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of strings from set in a column does not exceed the minimum accepted count.")
    private ColumnMinStringsInSetCountCheckSpec dailyCheckpointMinStringsInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage.")
    private ColumnMinStringsInSetPercentCheckSpec dailyCheckpointMinStringsInSetPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxInvalidEmailCountCheckSpec dailyCheckpointMaxInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinValidEmailPercentCheckSpec dailyCheckpointMinValidEmailPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxNotMatchRegexCountCheckSpec dailyCheckpointMaxNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinRegexMatchPercentCheckSpec dailyCheckpointMinRegexMatchPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted quantity. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxNotMatchDateRegexCountCheckSpec dailyCheckpointMaxNotMatchDateRegexCount;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnMaxStringLengthBelowCheckSpec getDailyCheckpointMaxStringLengthBelow() {
        return dailyCheckpointMaxStringLengthBelow;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param dailyCheckpointMaxStringLengthBelow Maximum string length below check.
     */
    public void setDailyCheckpointMaxStringLengthBelow(ColumnMaxStringLengthBelowCheckSpec dailyCheckpointMaxStringLengthBelow) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringLengthBelow, dailyCheckpointMaxStringLengthBelow));
        this.dailyCheckpointMaxStringLengthBelow = dailyCheckpointMaxStringLengthBelow;
        propagateHierarchyIdToField(dailyCheckpointMaxStringLengthBelow, "daily_checkpoint_max_string_length_below");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length below check.
     */
    public ColumnMinStringLengthAboveCheckSpec getDailyCheckpointMinStringLengthAbove() {
        return dailyCheckpointMinStringLengthAbove;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param dailyCheckpointMinStringLengthAbove Minimum string length above check.
     */
    public void setDailyCheckpointMinStringLengthAbove(ColumnMinStringLengthAboveCheckSpec dailyCheckpointMinStringLengthAbove) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringLengthAbove, dailyCheckpointMinStringLengthAbove));
        this.dailyCheckpointMinStringLengthAbove = dailyCheckpointMinStringLengthAbove;
        propagateHierarchyIdToField(dailyCheckpointMinStringLengthAbove, "daily_checkpoint_min_string_length_above");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnMeanStringLengthBetweenCheckSpec getDailyCheckpointMeanStringLengthBetween() {
        return dailyCheckpointMeanStringLengthBetween;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param dailyCheckpointMeanStringLengthBetween Mean string length between check.
     */
    public void setDailyCheckpointMeanStringLengthBetween(ColumnMeanStringLengthBetweenCheckSpec dailyCheckpointMeanStringLengthBetween) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMeanStringLengthBetween, dailyCheckpointMeanStringLengthBetween));
        this.dailyCheckpointMeanStringLengthBetween = dailyCheckpointMeanStringLengthBetween;
        propagateHierarchyIdToField(dailyCheckpointMeanStringLengthBetween, "daily_checkpoint_mean_string_length_between");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnMaxStringEmptyPercentCheckSpec getDailyCheckpointMaxStringEmptyPercent() {
        return dailyCheckpointMaxStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param dailyCheckpointMaxStringEmptyPercent Maximum string empty percent check.
     */
    public void setDailyCheckpointMaxStringEmptyPercent(ColumnMaxStringEmptyPercentCheckSpec dailyCheckpointMaxStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringEmptyPercent, dailyCheckpointMaxStringEmptyPercent));
        this.dailyCheckpointMaxStringEmptyPercent = dailyCheckpointMaxStringEmptyPercent;
        propagateHierarchyIdToField(dailyCheckpointMaxStringEmptyPercent, "daily_checkpoint_max_string_empty_percent");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnMaxStringEmptyCountCheckSpec getDailyCheckpointMaxStringEmptyCount() {
        return dailyCheckpointMaxStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyCheckpointMaxStringEmptyCount Max string empty count check.
     */
    public void setDailyCheckpointMaxStringEmptyCount(ColumnMaxStringEmptyCountCheckSpec dailyCheckpointMaxStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringEmptyCount, dailyCheckpointMaxStringEmptyCount));
        this.dailyCheckpointMaxStringEmptyCount = dailyCheckpointMaxStringEmptyCount;
        propagateHierarchyIdToField(dailyCheckpointMaxStringEmptyCount, "daily_checkpoint_max_string_empty_count");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnMaxStringWhitespaceCountCheckSpec getDailyCheckpointMaxStringWhitespaceCount() {
        return dailyCheckpointMaxStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param dailyCheckpointMaxStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setDailyCheckpointMaxStringWhitespaceCount(ColumnMaxStringWhitespaceCountCheckSpec dailyCheckpointMaxStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringWhitespaceCount, dailyCheckpointMaxStringWhitespaceCount));
        this.dailyCheckpointMaxStringWhitespaceCount = dailyCheckpointMaxStringWhitespaceCount;
        propagateHierarchyIdToField(dailyCheckpointMaxStringWhitespaceCount, "daily_checkpoint_max_string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnMaxStringWhitespacePercentCheckSpec getDailyCheckpointMaxStringWhitespacePercent() {
        return dailyCheckpointMaxStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param dailyCheckpointMaxStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setDailyCheckpointMaxStringWhitespacePercent(ColumnMaxStringWhitespacePercentCheckSpec dailyCheckpointMaxStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringWhitespacePercent, dailyCheckpointMaxStringWhitespacePercent));
        this.dailyCheckpointMaxStringWhitespacePercent = dailyCheckpointMaxStringWhitespacePercent;
        propagateHierarchyIdToField(dailyCheckpointMaxStringWhitespacePercent, "daily_checkpoint_max_string_whitespace_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnMinStringValidDatesPercentCheckSpec getDailyCheckpointMinStringValidDatesPercent() {
        return dailyCheckpointMinStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param dailyCheckpointMinStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setDailyCheckpointMinStringValidDatesPercent(ColumnMinStringValidDatesPercentCheckSpec dailyCheckpointMinStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringValidDatesPercent, dailyCheckpointMinStringValidDatesPercent));
        this.dailyCheckpointMinStringValidDatesPercent = dailyCheckpointMinStringValidDatesPercent;
        propagateHierarchyIdToField(dailyCheckpointMinStringValidDatesPercent, "daily_checkpoint_min_string_valid_dates_percent");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnMaxStringNullPlaceholderCountCheckSpec getDailyCheckpointMaxStringNullPlaceholderCount() {
        return dailyCheckpointMaxStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param dailyCheckpointMaxStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setDailyCheckpointMaxStringNullPlaceholderCount(ColumnMaxStringNullPlaceholderCountCheckSpec dailyCheckpointMaxStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringNullPlaceholderCount, dailyCheckpointMaxStringNullPlaceholderCount));
        this.dailyCheckpointMaxStringNullPlaceholderCount = dailyCheckpointMaxStringNullPlaceholderCount;
        propagateHierarchyIdToField(dailyCheckpointMaxStringNullPlaceholderCount, "daily_checkpoint_max_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnMaxStringNullPlaceholderPercentCheckSpec getDailyCheckpointMaxStringNullPlaceholderPercent() {
        return dailyCheckpointMaxStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param dailyCheckpointMaxStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setDailyCheckpointMaxStringNullPlaceholderPercent(ColumnMaxStringNullPlaceholderPercentCheckSpec dailyCheckpointMaxStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringNullPlaceholderPercent, dailyCheckpointMaxStringNullPlaceholderPercent));
        this.dailyCheckpointMaxStringNullPlaceholderPercent = dailyCheckpointMaxStringNullPlaceholderPercent;
        propagateHierarchyIdToField(dailyCheckpointMaxStringNullPlaceholderPercent, "daily_checkpoint_max_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnMinStringBooleanPlaceholderPercentCheckSpec getDailyCheckpointMinStringBooleanPlaceholderPercent() {
        return dailyCheckpointMinStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param dailyCheckpointMinStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setDailyCheckpointMinStringBooleanPlaceholderPercent(ColumnMinStringBooleanPlaceholderPercentCheckSpec dailyCheckpointMinStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringBooleanPlaceholderPercent, dailyCheckpointMinStringBooleanPlaceholderPercent));
        this.dailyCheckpointMinStringBooleanPlaceholderPercent = dailyCheckpointMinStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(dailyCheckpointMinStringBooleanPlaceholderPercent, "daily_checkpoint_min_string_boolean_placeholder_percent");
    }

    /**
    * Returns a minimum string parsable to integer percent check.
    * @return Minimum string parsable to integer percent check.
    */
    public ColumnMinStringParsableToIntegerPercentCheckSpec getDailyCheckpointMinStringParsableToIntegerPercent() {
        return dailyCheckpointMinStringParsableToIntegerPercent;
    }

    /**
    * Sets a new definition of a minimum string parsable to integer percent check.
    * @param dailyCheckpointMinStringParsableToIntegerPercent Minimum string parsable to integer percent check.
    */
    public void setDailyCheckpointMinStringParsableToIntegerPercent(ColumnMinStringParsableToIntegerPercentCheckSpec dailyCheckpointMinStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringParsableToIntegerPercent, dailyCheckpointMinStringParsableToIntegerPercent));
        this.dailyCheckpointMinStringParsableToIntegerPercent = dailyCheckpointMinStringParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyCheckpointMinStringParsableToIntegerPercent, "daily_checkpoint_min_string_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnMaxStringSurroundedByWhitespaceCountCheckSpec getDailyCheckpointMaxStringSurroundedByWhitespaceCount() {
        return dailyCheckpointMaxStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param dailyCheckpointMaxStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setDailyCheckpointMaxStringSurroundedByWhitespaceCount(ColumnMaxStringSurroundedByWhitespaceCountCheckSpec dailyCheckpointMaxStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringSurroundedByWhitespaceCount, dailyCheckpointMaxStringSurroundedByWhitespaceCount));
        this.dailyCheckpointMaxStringSurroundedByWhitespaceCount = dailyCheckpointMaxStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(dailyCheckpointMaxStringSurroundedByWhitespaceCount, "daily_checkpoint_max_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnMaxStringSurroundedByWhitespacePercentCheckSpec getDailyCheckpointMaxStringSurroundedByWhitespacePercent() {
        return dailyCheckpointMaxStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param dailyCheckpointMaxStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setDailyCheckpointMaxStringSurroundedByWhitespacePercent(ColumnMaxStringSurroundedByWhitespacePercentCheckSpec dailyCheckpointMaxStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxStringSurroundedByWhitespacePercent, dailyCheckpointMaxStringSurroundedByWhitespacePercent));
        this.dailyCheckpointMaxStringSurroundedByWhitespacePercent = dailyCheckpointMaxStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyCheckpointMaxStringSurroundedByWhitespacePercent, "daily_checkpoint_max_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnMinStringParsableToFloatPercentCheckSpec getDailyCheckpointMinStringParsableToFloatPercent() {
        return dailyCheckpointMinStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param dailyCheckpointMinStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setDailyCheckpointMinStringParsableToFloatPercent(ColumnMinStringParsableToFloatPercentCheckSpec dailyCheckpointMinStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringParsableToFloatPercent, dailyCheckpointMinStringParsableToFloatPercent));
        this.dailyCheckpointMinStringParsableToFloatPercent = dailyCheckpointMinStringParsableToFloatPercent;
        propagateHierarchyIdToField(dailyCheckpointMinStringParsableToFloatPercent, "daily_checkpoint_min_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent check.
     */
    public ColumnMinStringValidUsaZipcodePercentCheckSpec getDailyCheckpointMinStringValidUsaZipcodePercent() {
        return dailyCheckpointMinStringValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid usa zip code percent check.
     * @param dailyCheckpointMinStringValidUsaZipcodePercent Minimum string valid usa zip code percent check.
     */
    public void setDailyCheckpointMinStringValidUsaZipcodePercent(ColumnMinStringValidUsaZipcodePercentCheckSpec dailyCheckpointMinStringValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringValidUsaZipcodePercent, dailyCheckpointMinStringValidUsaZipcodePercent));
        this.dailyCheckpointMinStringValidUsaZipcodePercent = dailyCheckpointMinStringValidUsaZipcodePercent;
        propagateHierarchyIdToField(dailyCheckpointMinStringValidUsaZipcodePercent, "daily_checkpoint_min_string_valid_usa_zipcode_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnMinStringValidUsaPhonePercentCheckSpec getDailyCheckpointMinStringValidUsaPhonePercent() {
        return dailyCheckpointMinStringValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum string valid USA phone percent check.
     * @param dailyCheckpointMinStringValidUsaPhonePercent Minimum string valid USA phone percent check.
     */
    public void setDailyCheckpointMinStringValidUsaPhonePercent(ColumnMinStringValidUsaPhonePercentCheckSpec dailyCheckpointMinStringValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringValidUsaPhonePercent, dailyCheckpointMinStringValidUsaPhonePercent));
        this.dailyCheckpointMinStringValidUsaPhonePercent = dailyCheckpointMinStringValidUsaPhonePercent;
        propagateHierarchyIdToField(dailyCheckpointMinStringValidUsaPhonePercent, "daily_checkpoint_min_string_valid_usa_phone_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent check.
     */
    public ColumnMinValidCountryCodePercentCheckSpec getDailyCheckpointMinStringValidCountryCodePercent() {
        return dailyCheckpointMinStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param dailyCheckpointMinStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setDailyCheckpointMinStringValidCountryCodePercent(ColumnMinValidCountryCodePercentCheckSpec dailyCheckpointMinStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringValidCountryCodePercent, dailyCheckpointMinStringValidCountryCodePercent));
        this.dailyCheckpointMinStringValidCountryCodePercent = dailyCheckpointMinStringValidCountryCodePercent;
        propagateHierarchyIdToField(dailyCheckpointMinStringValidCountryCodePercent, "daily_checkpoint_min_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent check.
     */
    public ColumnMinValidCurrencyCodePercentCheckSpec getDailyCheckpointMinStringValidCurrencyCodePercent() {
        return dailyCheckpointMinStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param dailyCheckpointMinStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setDailyCheckpointMinStringValidCurrencyCodePercent(ColumnMinValidCurrencyCodePercentCheckSpec dailyCheckpointMinStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringValidCurrencyCodePercent, dailyCheckpointMinStringValidCurrencyCodePercent));
        this.dailyCheckpointMinStringValidCurrencyCodePercent = dailyCheckpointMinStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(dailyCheckpointMinStringValidCurrencyCodePercent, "daily_checkpoint_min_string_valid_currency_code_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnMinStringsInSetCountCheckSpec getDailyCheckpointMinStringsInSetCount() {
        return dailyCheckpointMinStringsInSetCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param dailyCheckpointMinStringsInSetCount Minimum strings in set count check.
     */
    public void setDailyCheckpointMinStringsInSetCount(ColumnMinStringsInSetCountCheckSpec dailyCheckpointMinStringsInSetCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringsInSetCount, dailyCheckpointMinStringsInSetCount));
        this.dailyCheckpointMinStringsInSetCount = dailyCheckpointMinStringsInSetCount;
        propagateHierarchyIdToField(dailyCheckpointMinStringsInSetCount, "daily_checkpoint_min_strings_in_set_count");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnMinStringsInSetPercentCheckSpec getDailyCheckpointMinStringsInSetPercent() {
        return dailyCheckpointMinStringsInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param dailyCheckpointMinStringsInSetPercent Minimum strings in set percent check.
     */
    public void setDailyCheckpointMinStringsInSetPercent(ColumnMinStringsInSetPercentCheckSpec dailyCheckpointMinStringsInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinStringsInSetPercent, dailyCheckpointMinStringsInSetPercent));
        this.dailyCheckpointMinStringsInSetPercent = dailyCheckpointMinStringsInSetPercent;
        propagateHierarchyIdToField(dailyCheckpointMinStringsInSetPercent, "daily_checkpoint_min_strings_in_set_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnMaxInvalidEmailCountCheckSpec getDailyCheckpointMaxInvalidEmailCount() {
        return dailyCheckpointMaxInvalidEmailCount;
    }

    /**
     * Sets a new definition of a maximum invalid email count check.
     * @param dailyCheckpointMaxInvalidEmailCount Maximum invalid email count check.
     */
    public void setDailyCheckpointMaxInvalidEmailCount(ColumnMaxInvalidEmailCountCheckSpec dailyCheckpointMaxInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxInvalidEmailCount, dailyCheckpointMaxInvalidEmailCount));
        this.dailyCheckpointMaxInvalidEmailCount = dailyCheckpointMaxInvalidEmailCount;
        propagateHierarchyIdToField(dailyCheckpointMaxInvalidEmailCount, "daily_checkpoint_max_strings_invalid_email_count");
    }

    /**
     * Returns a minimum valid email percent check.
     * @return Minimum valid email percent check.
     */
    public ColumnMinValidEmailPercentCheckSpec getDailyCheckpointMinValidEmailPercent() {
        return dailyCheckpointMinValidEmailPercent;
    }

    /**
     * Sets a new definition of a minimum valid email percent check.
     * @param dailyCheckpointMinValidEmailPercent Minimum valid email percent check.
     */
    public void setDailyCheckpointMinValidEmailPercent(ColumnMinValidEmailPercentCheckSpec dailyCheckpointMinValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinValidEmailPercent, dailyCheckpointMinValidEmailPercent));
        this.dailyCheckpointMinValidEmailPercent = dailyCheckpointMinValidEmailPercent;
        propagateHierarchyIdToField(dailyCheckpointMinValidEmailPercent, "daily_checkpoint_min_valid_email_percent");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnMaxNotMatchRegexCountCheckSpec getDailyCheckpointMaxNotMatchRegexCount() {
        return dailyCheckpointMaxNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param dailyCheckpointMaxNotMatchRegexCount Maximum not match regex count check.
     */
    public void setDailyCheckpointMaxNotMatchRegexCount(ColumnMaxNotMatchRegexCountCheckSpec dailyCheckpointMaxNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxNotMatchRegexCount, dailyCheckpointMaxNotMatchRegexCount));
        this.dailyCheckpointMaxNotMatchRegexCount = dailyCheckpointMaxNotMatchRegexCount;
        propagateHierarchyIdToField(dailyCheckpointMaxNotMatchRegexCount, "daily_checkpoint_max_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnMinRegexMatchPercentCheckSpec getDailyCheckpointMinRegexMatchPercent() {
        return dailyCheckpointMinRegexMatchPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param dailyCheckpointMinRegexMatchPercent Minimum match regex percent check.
     */
    public void setDailyCheckpointMinRegexMatchPercent(ColumnMinRegexMatchPercentCheckSpec dailyCheckpointMinRegexMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinRegexMatchPercent, dailyCheckpointMinRegexMatchPercent));
        this.dailyCheckpointMinRegexMatchPercent = dailyCheckpointMinRegexMatchPercent;
        propagateHierarchyIdToField(dailyCheckpointMinRegexMatchPercent, "daily_checkpoint_min_regex_match_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnMaxNotMatchDateRegexCountCheckSpec getDailyCheckpointMaxNotMatchDateRegexCount() {
        return dailyCheckpointMaxNotMatchDateRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param dailyCheckpointMaxNotMatchDateRegexCount Maximum not match date regex count check.
     */
    public void setDailyCheckpointMaxNotMatchDateRegexCount(ColumnMaxNotMatchDateRegexCountCheckSpec dailyCheckpointMaxNotMatchDateRegexCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxNotMatchDateRegexCount, dailyCheckpointMaxNotMatchDateRegexCount));
        this.dailyCheckpointMaxNotMatchDateRegexCount = dailyCheckpointMaxNotMatchDateRegexCount;
        propagateHierarchyIdToField(dailyCheckpointMaxNotMatchDateRegexCount, "daily_checkpoint_max_not_match_date_regex_count");
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
