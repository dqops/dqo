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
package ai.dqo.checks.column.adhoc;

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
 * Container of built-in preconfigured data quality checks on a column level that are checking for string.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocStringsChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocStringsChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("max_string_length_below", o -> o.maxStringLengthBelow);
            put("min_string_length_above", o -> o.minStringLengthAbove);
            put("mean_string_length_between", o -> o.meanStringLengthBetween);

            put("max_string_empty_count", o -> o.maxStringEmptyCount);
            put("max_string_empty_percent", o -> o.maxStringEmptyPercent);
            put("max_string_whitespace_count", o -> o.maxStringWhitespaceCount);
            put("max_string_whitespace_percent", o -> o.maxStringWhitespacePercent);
            put("max_string_surrounded_by_whitespace_count", o -> o.maxStringSurroundedByWhitespaceCount);
            put("max_string_surrounded_by_whitespace_percent", o -> o.maxStringSurroundedByWhitespacePercent);

            put("max_string_null_placeholder_count", o -> o.maxStringNullPlaceholderCount);
            put("max_string_null_placeholder_percent", o -> o.maxStringNullPlaceholderPercent);
            put("min_string_boolean_placeholder_percent", o -> o.minStringBooleanPlaceholderPercent);
            put("min_string_parsable_to_integer_percent", o -> o.minStringParsableToIntegerPercent);
            put("min_string_parsable_to_float_percent", o -> o.minStringParsableToFloatPercent);

            put("min_strings_in_set_count", o -> o.minStringsInSetCount);
            put("min_strings_in_set_percent", o -> o.minStringsInSetPercent);

            put("min_string_valid_dates_percent", o -> o.minStringValidDatesPercent);
            put("min_string_valid_usa_zipcode_percent", o -> o.minStringValidUsaZipcodePercent);
            put("min_string_valid_usa_phone_percent", o -> o.minStringValidUsaPhonePercent);
            put("min_string_valid_country_code_percent", o -> o.minStringValidCountryCodePercent);
            put("min_string_valid_currency_code_percent", o -> o.minStringValidCurrencyCodePercent);
            put("min_strings_in_set_count", o -> o.minStringsInSetCount);
            put("min_strings_in_set_percent", o -> o.minStringsInSetPercent);
            put("max_strings_invalid_email_count", o -> o.maxInvalidEmailCount);

        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length.")
    private ColumnMaxStringLengthBelowCheckSpec maxStringLengthBelow;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length.")
    private ColumnMinStringLengthAboveCheckSpec minStringLengthAbove;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length.")
    private ColumnMeanStringLengthBetweenCheckSpec meanStringLengthBetween;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.")
    private ColumnMaxStringEmptyPercentCheckSpec maxStringEmptyPercent;

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted quantity.")
    private ColumnMaxStringEmptyCountCheckSpec maxStringEmptyCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity.")
    private ColumnMaxStringWhitespaceCountCheckSpec maxStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length.")
    private ColumnMinStringValidDatesPercentCheckSpec minStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted quantity.")
    private ColumnMaxStringWhitespacePercentCheckSpec maxStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted quantity.")
    private ColumnMaxStringNullPlaceholderCountCheckSpec maxStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.")
    private ColumnMaxStringNullPlaceholderPercentCheckSpec maxStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage.")
    private ColumnMinStringBooleanPlaceholderPercentCheckSpec minStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted quantity.")
    private ColumnMaxStringSurroundedByWhitespaceCountCheckSpec maxStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage.")
    private ColumnMinStringParsableToIntegerPercentCheckSpec minStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.")
    private ColumnMaxStringSurroundedByWhitespacePercentCheckSpec maxStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage.")
    private ColumnMinStringParsableToFloatPercentCheckSpec minStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage.")
    private ColumnMinStringValidUsaZipcodePercentCheckSpec minStringValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage.")
    private ColumnMinStringValidUsaPhonePercentCheckSpec minStringValidUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage.")
    private ColumnMinValidCountryCodePercentCheckSpec minStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage.")
    private ColumnMinValidCurrencyCodePercentCheckSpec minStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of strings from set in a column does not exceed the minimum accepted count.")
    private ColumnMinStringsInSetCountCheckSpec minStringsInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage.")
    private ColumnMinStringsInSetPercentCheckSpec minStringsInSetPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted quantity.")
    private ColumnMaxInvalidEmailCountCheckSpec maxInvalidEmailCount;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnMaxStringLengthBelowCheckSpec getMaxStringLengthBelow() {
        return maxStringLengthBelow;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param maxStringLengthBelow Maximum string length below check.
     */
    public void setMaxStringLengthBelow(ColumnMaxStringLengthBelowCheckSpec maxStringLengthBelow) {
        this.setDirtyIf(!Objects.equals(this.maxStringLengthBelow, maxStringLengthBelow));
        this.maxStringLengthBelow = maxStringLengthBelow;
        propagateHierarchyIdToField(maxStringLengthBelow, "max_string_length_below");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnMinStringLengthAboveCheckSpec getMinStringLengthAbove() {
        return minStringLengthAbove;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param minStringLengthAbove Minimum string length above check.
     */
    public void setMinStringLengthAbove(ColumnMinStringLengthAboveCheckSpec minStringLengthAbove) {
        this.setDirtyIf(!Objects.equals(this.minStringLengthAbove, minStringLengthAbove));
        this.minStringLengthAbove = minStringLengthAbove;
        propagateHierarchyIdToField(minStringLengthAbove, "min_string_length_above");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnMeanStringLengthBetweenCheckSpec getMeanStringLengthBetween() {
        return meanStringLengthBetween;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param meanStringLengthBetween Mean string length between check.
     */
    public void setMeanStringLengthBetween(ColumnMeanStringLengthBetweenCheckSpec meanStringLengthBetween) {
        this.setDirtyIf(!Objects.equals(this.meanStringLengthBetween, meanStringLengthBetween));
        this.meanStringLengthBetween = meanStringLengthBetween;
        propagateHierarchyIdToField(meanStringLengthBetween, "mean_string_length_between");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnMaxStringEmptyPercentCheckSpec getMaxStringEmptyPercent() {
        return maxStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param maxStringEmptyPercent Maximum string empty percent check.
     */
    public void setMaxStringEmptyPercent(ColumnMaxStringEmptyPercentCheckSpec maxStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.maxStringEmptyPercent, maxStringEmptyPercent));
        this.maxStringEmptyPercent = maxStringEmptyPercent;
        propagateHierarchyIdToField(maxStringEmptyPercent, "max_string_empty_percent");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnMaxStringEmptyCountCheckSpec getMaxStringEmptyCount() {
        return maxStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param maxStringEmptyCount Max string empty count check.
     */
    public void setMaxStringEmptyCount(ColumnMaxStringEmptyCountCheckSpec maxStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.maxStringEmptyCount, maxStringEmptyCount));
        this.maxStringEmptyCount = maxStringEmptyCount;
        propagateHierarchyIdToField(maxStringEmptyCount, "max_string_empty_count");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnMaxStringWhitespaceCountCheckSpec getMaxStringWhitespaceCount() {
        return maxStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param maxStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setMaxStringWhitespaceCount(ColumnMaxStringWhitespaceCountCheckSpec maxStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.maxStringWhitespaceCount, maxStringWhitespaceCount));
        this.maxStringWhitespaceCount = maxStringWhitespaceCount;
        propagateHierarchyIdToField(maxStringWhitespaceCount, "max_string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnMaxStringWhitespacePercentCheckSpec getMaxStringWhitespacePercent() {
        return maxStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param maxStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setMaxStringWhitespacePercent(ColumnMaxStringWhitespacePercentCheckSpec maxStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.maxStringWhitespacePercent, maxStringWhitespacePercent));
        this.maxStringWhitespacePercent = maxStringWhitespacePercent;
        propagateHierarchyIdToField(maxStringWhitespacePercent, "max_string_whitespace_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnMinStringValidDatesPercentCheckSpec getMinStringValidDatesPercent() {
        return minStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param minStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setMinStringValidDatesPercent(ColumnMinStringValidDatesPercentCheckSpec minStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.minStringValidDatesPercent, minStringValidDatesPercent));
        this.minStringValidDatesPercent = minStringValidDatesPercent;
        propagateHierarchyIdToField(minStringValidDatesPercent, "min_string_valid_dates_percent");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnMaxStringNullPlaceholderCountCheckSpec getMaxStringNullPlaceholderCount() {
        return maxStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param maxStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setMaxStringNullPlaceholderCount(ColumnMaxStringNullPlaceholderCountCheckSpec maxStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.maxStringNullPlaceholderCount, maxStringNullPlaceholderCount));
        this.maxStringNullPlaceholderCount = maxStringNullPlaceholderCount;
        propagateHierarchyIdToField(maxStringNullPlaceholderCount, "max_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnMaxStringNullPlaceholderPercentCheckSpec getMaxStringNullPlaceholderPercent() {
        return maxStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param maxStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setMaxStringNullPlaceholderPercent(ColumnMaxStringNullPlaceholderPercentCheckSpec maxStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.maxStringNullPlaceholderPercent, maxStringNullPlaceholderPercent));
        this.maxStringNullPlaceholderPercent = maxStringNullPlaceholderPercent;
        propagateHierarchyIdToField(maxStringNullPlaceholderPercent, "max_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnMinStringBooleanPlaceholderPercentCheckSpec getMinStringBooleanPlaceholderPercent() {
        return minStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param minStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setMinStringBooleanPlaceholderPercent(ColumnMinStringBooleanPlaceholderPercentCheckSpec minStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.minStringBooleanPlaceholderPercent, minStringBooleanPlaceholderPercent));
        this.minStringBooleanPlaceholderPercent = minStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(minStringBooleanPlaceholderPercent, "min_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnMinStringParsableToIntegerPercentCheckSpec getMinStringParsableToIntegerPercent() {
        return minStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param minStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setMinStringParsableToIntegerPercent(ColumnMinStringParsableToIntegerPercentCheckSpec minStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.minStringParsableToIntegerPercent, minStringParsableToIntegerPercent));
        this.minStringParsableToIntegerPercent = minStringParsableToIntegerPercent;
        propagateHierarchyIdToField(minStringParsableToIntegerPercent, "min_string_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnMaxStringSurroundedByWhitespaceCountCheckSpec getMaxStringSurroundedByWhitespaceCount() {
        return maxStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param maxStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setMaxStringSurroundedByWhitespaceCount(ColumnMaxStringSurroundedByWhitespaceCountCheckSpec maxStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.maxStringSurroundedByWhitespaceCount, maxStringSurroundedByWhitespaceCount));
        this.maxStringSurroundedByWhitespaceCount = maxStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(maxStringSurroundedByWhitespaceCount, "max_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnMaxStringSurroundedByWhitespacePercentCheckSpec getMaxStringSurroundedByWhitespacePercent() {
        return maxStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param maxStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setMaxStringSurroundedByWhitespacePercent(ColumnMaxStringSurroundedByWhitespacePercentCheckSpec maxStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.maxStringSurroundedByWhitespacePercent, maxStringSurroundedByWhitespacePercent));
        this.maxStringSurroundedByWhitespacePercent = maxStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(maxStringSurroundedByWhitespacePercent, "max_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnMinStringParsableToFloatPercentCheckSpec getMinStringParsableToFloatPercent() {
        return minStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param minStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setMinStringParsableToFloatPercent(ColumnMinStringParsableToFloatPercentCheckSpec minStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.minStringParsableToFloatPercent, minStringParsableToFloatPercent));
        this.minStringParsableToFloatPercent = minStringParsableToFloatPercent;
        propagateHierarchyIdToField(minStringParsableToFloatPercent, "min_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent check.
     */
    public ColumnMinStringValidUsaZipcodePercentCheckSpec getMinStringValidUsaZipcodePercent() {
        return minStringValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid usa zip code percent check.
     * @param minStringValidUsaZipcodePercent Minimum string valid usa zip code percent check.
     */
    public void setMinStringValidUsaZipcodePercent(ColumnMinStringValidUsaZipcodePercentCheckSpec minStringValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.minStringValidUsaZipcodePercent, minStringValidUsaZipcodePercent));
        this.minStringValidUsaZipcodePercent = minStringValidUsaZipcodePercent;
        propagateHierarchyIdToField(minStringValidUsaZipcodePercent, "min_string_valid_usa_zipcode_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnMinStringValidUsaPhonePercentCheckSpec getMinStringValidUsaPhonePercent() {
        return minStringValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum string valid USA phone percent check.
     * @param minStringValidUsaPhonePercent Minimum string valid USA phone percent check.
     */
    public void setMinStringValidUsaPhonePercent(ColumnMinStringValidUsaPhonePercentCheckSpec minStringValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.minStringValidUsaPhonePercent, minStringValidUsaPhonePercent));
        this.minStringValidUsaPhonePercent = minStringValidUsaPhonePercent;
        propagateHierarchyIdToField(minStringValidUsaPhonePercent, "min_string_valid_usa_phone_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent check.
     */
    public ColumnMinValidCountryCodePercentCheckSpec getMinStringValidCountryCodePercent() {
        return minStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param minStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setMinStringValidCountryCodePercent(ColumnMinValidCountryCodePercentCheckSpec minStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.minStringValidCountryCodePercent, minStringValidCountryCodePercent));
        this.minStringValidCountryCodePercent = minStringValidCountryCodePercent;
        propagateHierarchyIdToField(minStringValidCountryCodePercent, "min_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent check.
     */
    public ColumnMinValidCurrencyCodePercentCheckSpec getMinStringValidCurrencyCodePercent() {
        return minStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param minStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setMinStringValidCurrencyCodePercent(ColumnMinValidCurrencyCodePercentCheckSpec minStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.minStringValidCurrencyCodePercent, minStringValidCurrencyCodePercent));
        this.minStringValidCurrencyCodePercent = minStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(minStringValidCurrencyCodePercent, "min_string_valid_currency_code_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnMinStringsInSetCountCheckSpec getMinStringsInSetCount() {
        return minStringsInSetCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param minStringsInSetCount Minimum strings in set count check.
     */
    public void setMinStringsInSetCount(ColumnMinStringsInSetCountCheckSpec minStringsInSetCount) {
        this.setDirtyIf(!Objects.equals(this.minStringsInSetCount, minStringsInSetCount));
        this.minStringsInSetCount = minStringsInSetCount;
        propagateHierarchyIdToField(minStringsInSetCount, "min_strings_in_set_count");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnMinStringsInSetPercentCheckSpec getMinStringsInSetPercent() {
        return minStringsInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param minStringsInSetPercent Minimum strings in set percent check.
     */
    public void setMinStringsInSetPercent(ColumnMinStringsInSetPercentCheckSpec minStringsInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.minStringsInSetPercent, minStringsInSetPercent));
        this.minStringsInSetPercent = minStringsInSetPercent;
        propagateHierarchyIdToField(minStringsInSetPercent, "min_strings_in_set_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnMaxInvalidEmailCountCheckSpec getMaxInvalidEmailCount() {
        return maxInvalidEmailCount;
    }

    /**
     * Sets a new definition of a maximum invalid email count check.
     * @param maxInvalidEmailCount Maximum invalid email count check.
     */
    public void setMaxInvalidEmailCount(ColumnMaxInvalidEmailCountCheckSpec maxInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.maxInvalidEmailCount, maxInvalidEmailCount));
        this.maxInvalidEmailCount = maxInvalidEmailCount;
        propagateHierarchyIdToField(maxInvalidEmailCount, "max_strings_invalid_email_count");
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
