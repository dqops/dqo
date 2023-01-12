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
            put("string_max_length", o -> o.stringMaxLength);
            put("string_min_length", o -> o.stringMinLength);
            put("string_mean_length", o -> o.stringMeanLength);

            put("string_empty_count", o -> o.stringEmptyCount);
            put("string_empty_percent", o -> o.stringEmptyPercent);
            put("string_whitespace_count", o -> o.stringWhitespaceCount);
            put("string_whitespace_percent", o -> o.stringWhitespacePercent);
            put("string_surrounded_by_whitespace_count", o -> o.stringSurroundedByWhitespaceCount);
            put("string_surrounded_by_whitespace_percent", o -> o.stringSurroundedByWhitespacePercent);

            put("string_null_placeholder_count", o -> o.stringNullPlaceholderCount);
            put("string_null_placeholder_percent", o -> o.stringNullPlaceholderPercent);
            put("string_boolean_placeholder_percent", o -> o.stringBooleanPlaceholderPercent);
            put("string_parsable_to_integer_percent", o -> o.stringParsableToIntegerPercent);
            put("string_parsable_to_float_percent", o -> o.stringParsableToFloatPercent);

            put("string_in_set_count", o -> o.stringInSetCount);
            put("string_in_set_percent", o -> o.stringInSetPercent);

            put("string_valid_dates_percent", o -> o.stringValidDatesPercent);
            put("string_valid_usa_zipcode_percent", o -> o.stringValidUsaZipcodePercent);
            put("string_valid_usa_phone_percent", o -> o.stringValidUsaPhonePercent);
            put("string_valid_country_code_percent", o -> o.stringValidCountryCodePercent);
            put("string_valid_currency_code_percent", o -> o.stringValidCurrencyCodePercent);
            put("string_invalid_email_count", o -> o.stringInvalidEmailCount);
            put("string_valid_email_percent", o -> o.stringValidEmailPercent);

            put("string_not_match_regex_count", o -> o.stringNotMatchRegexCount);
            put("string_match_regex_percent", o -> o.stringMatchRegexPercent);
            put("string_not_match_date_regex_count", o -> o.stringNotMatchDateRegexCount);
            put("string_match_date_regex_percent", o -> o.stringMatchDateRegexPercent);
            put("string_match_name_regex_percent", o -> o.stringMatchNameRegexPercent);

            put("string_top_popular_values_set", o -> o.stringTopPopularValuesSet);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length.")
    private ColumnStringMaxLengthCheckSpec stringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length.")
    private ColumnStringMinLengthCheckSpec stringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length.")
    private ColumnStringMeanLengthCheckSpec stringMeanLength;

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted count.")
    private ColumnStringEmptyCountCheckSpec stringEmptyCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.")
    private ColumnStringEmptyPercentCheckSpec stringEmptyPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.")
    private ColumnStringWhitespaceCountCheckSpec stringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.")
    private ColumnStringWhitespacePercentCheckSpec stringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.")
    private ColumnStringSurroundedByWhitespaceCountCheckSpec stringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.")
    private ColumnStringSurroundedByWhitespacePercentCheckSpec stringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.")
    private ColumnStringNullPlaceholderCountCheckSpec stringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.")
    private ColumnStringNullPlaceholderPercentCheckSpec stringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage.")
    private ColumnStringBooleanPlaceholderPercentCheckSpec stringBooleanPlaceholderPercent;


    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage.")
    private ColumnStringParsableToIntegerPercentCheckSpec stringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage.")
    private ColumnStringParsableToFloatPercentCheckSpec stringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the number of strings from a set in a column does not exceed the minimum accepted count.")
    private ColumnStringInSetCountCheckSpec stringInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of strings from a set in a column does not exceed the minimum accepted percentage.")
    private ColumnStringInSetPercentCheckSpec stringInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage.")
    private ColumnStringValidDatesPercentCheckSpec stringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage.")
    private ColumnStringValidUsaZipcodePercentCheckSpec stringValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage.")
    private ColumnStringValidUsaPhonePercentCheckSpec stringValidUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage.")
    private ColumnStringValidCountryCodePercentCheckSpec stringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage.")
    private ColumnStringValidCurrencyCodePercentCheckSpec stringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.")
    private ColumnStringInvalidEmailCountCheckSpec stringInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage.")
    private ColumnStringValidEmailPercentCheckSpec stringValidEmailPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.")
    private ColumnStringNotMatchRegexCountCheckSpec stringNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage.")
    private ColumnStringRegexMatchPercentCheckSpec stringMatchRegexPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.")
    private ColumnStringNotMatchDateRegexCountCheckSpec stringNotMatchDateRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage.")
    private ColumnStringMatchDateRegexPercentCheckSpec stringMatchDateRegexPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage.")
    private ColumnStringMatchNameRegexPercentCheckSpec stringMatchNameRegexPercent;

    @JsonPropertyDescription("Verifies that the number of top values from a set in a column does not exceed the minimum accepted count.")
    private ColumnStringTopPopularValuesSetCheckSpec stringTopPopularValuesSet;

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnStringMaxLengthCheckSpec getStringMaxLength() {
        return stringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param stringMaxLength Maximum string length check.
     */
    public void setStringMaxLength(ColumnStringMaxLengthCheckSpec stringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.stringMaxLength, stringMaxLength));
        this.stringMaxLength = stringMaxLength;
        propagateHierarchyIdToField(stringMaxLength, "string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnStringMinLengthCheckSpec getStringMinLength() {
        return stringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param stringMinLength Minimum string length check.
     */
    public void setStringMinLength(ColumnStringMinLengthCheckSpec stringMinLength) {
        this.setDirtyIf(!Objects.equals(this.stringMinLength, stringMinLength));
        this.stringMinLength = stringMinLength;
        propagateHierarchyIdToField(stringMinLength, "string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnStringMeanLengthCheckSpec getStringMeanLength() {
        return stringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param stringMeanLength Mean string length check.
     */
    public void setStringMeanLength(ColumnStringMeanLengthCheckSpec stringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.stringMeanLength, stringMeanLength));
        this.stringMeanLength = stringMeanLength;
        propagateHierarchyIdToField(stringMeanLength, "string_mean_length");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnStringEmptyCountCheckSpec getStringEmptyCount() {
        return stringEmptyCount;
    }

    /**
     * Sets a new definition of a string empty count check.
     * @param stringEmptyCount String empty count check.
     */
    public void setStringEmptyCount(ColumnStringEmptyCountCheckSpec stringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.stringEmptyCount, stringEmptyCount));
        this.stringEmptyCount = stringEmptyCount;
        propagateHierarchyIdToField(stringEmptyCount, "string_empty_count");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnStringEmptyPercentCheckSpec getStringEmptyPercent() {
        return stringEmptyPercent;
    }

    /**
     * Sets a new definition of a string empty percent check.
     * @param stringEmptyPercent String empty percent check.
     */
    public void setStringEmptyPercent(ColumnStringEmptyPercentCheckSpec stringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.stringEmptyPercent, stringEmptyPercent));
        this.stringEmptyPercent = stringEmptyPercent;
        propagateHierarchyIdToField(stringEmptyPercent, "string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnStringWhitespaceCountCheckSpec getStringWhitespaceCount() {
        return stringWhitespaceCount;
    }

    /**
     * Sets a new definition of a string whitespace count check.
     * @param stringWhitespaceCount String whitespace count check.
     */
    public void setStringWhitespaceCount(ColumnStringWhitespaceCountCheckSpec stringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.stringWhitespaceCount, stringWhitespaceCount));
        this.stringWhitespaceCount = stringWhitespaceCount;
        propagateHierarchyIdToField(stringWhitespaceCount, "string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnStringWhitespacePercentCheckSpec getStringWhitespacePercent() {
        return stringWhitespacePercent;
    }

    /**
     * Sets a new definition of a string whitespace percent check.
     * @param stringWhitespacePercent String whitespace percent check.
     */
    public void setStringWhitespacePercent(ColumnStringWhitespacePercentCheckSpec stringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.stringWhitespacePercent, stringWhitespacePercent));
        this.stringWhitespacePercent = stringWhitespacePercent;
        propagateHierarchyIdToField(stringWhitespacePercent, "string_whitespace_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnStringSurroundedByWhitespaceCountCheckSpec getStringSurroundedByWhitespaceCount() {
        return stringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param stringSurroundedByWhitespaceCount String surrounded by whitespace count check.
     */
    public void setStringSurroundedByWhitespaceCount(ColumnStringSurroundedByWhitespaceCountCheckSpec stringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.stringSurroundedByWhitespaceCount, stringSurroundedByWhitespaceCount));
        this.stringSurroundedByWhitespaceCount = stringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(stringSurroundedByWhitespaceCount, "string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnStringSurroundedByWhitespacePercentCheckSpec getStringSurroundedByWhitespacePercent() {
        return stringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param stringSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setStringSurroundedByWhitespacePercent(ColumnStringSurroundedByWhitespacePercentCheckSpec stringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.stringSurroundedByWhitespacePercent, stringSurroundedByWhitespacePercent));
        this.stringSurroundedByWhitespacePercent = stringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(stringSurroundedByWhitespacePercent, "string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnStringNullPlaceholderCountCheckSpec getStringNullPlaceholderCount() {
        return stringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a string null placeholder count check.
     * @param stringNullPlaceholderCount String null placeholder count check.
     */
    public void setStringNullPlaceholderCount(ColumnStringNullPlaceholderCountCheckSpec stringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.stringNullPlaceholderCount, stringNullPlaceholderCount));
        this.stringNullPlaceholderCount = stringNullPlaceholderCount;
        propagateHierarchyIdToField(stringNullPlaceholderCount, "string_null_placeholder_count");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnStringNullPlaceholderPercentCheckSpec getStringNullPlaceholderPercent() {
        return stringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a string null placeholder percent check.
     * @param stringNullPlaceholderPercent String null placeholder percent check.
     */
    public void setStringNullPlaceholderPercent(ColumnStringNullPlaceholderPercentCheckSpec stringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.stringNullPlaceholderPercent, stringNullPlaceholderPercent));
        this.stringNullPlaceholderPercent = stringNullPlaceholderPercent;
        propagateHierarchyIdToField(stringNullPlaceholderPercent, "string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnStringBooleanPlaceholderPercentCheckSpec getStringBooleanPlaceholderPercent() {
        return stringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param stringBooleanPlaceholderPercent String boolean placeholder percent check.
     */
    public void setStringBooleanPlaceholderPercent(ColumnStringBooleanPlaceholderPercentCheckSpec stringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.stringBooleanPlaceholderPercent, stringBooleanPlaceholderPercent));
        this.stringBooleanPlaceholderPercent = stringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(stringBooleanPlaceholderPercent, "string_boolean_placeholder_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnStringParsableToIntegerPercentCheckSpec getStringParsableToIntegerPercent() {
        return stringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param stringParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setStringParsableToIntegerPercent(ColumnStringParsableToIntegerPercentCheckSpec stringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.stringParsableToIntegerPercent, stringParsableToIntegerPercent));
        this.stringParsableToIntegerPercent = stringParsableToIntegerPercent;
        propagateHierarchyIdToField(stringParsableToIntegerPercent, "string_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnStringParsableToFloatPercentCheckSpec getStringParsableToFloatPercent() {
        return stringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param stringParsableToFloatPercent String parsable to float percent check.
     */
    public void setStringParsableToFloatPercent(ColumnStringParsableToFloatPercentCheckSpec stringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.stringParsableToFloatPercent, stringParsableToFloatPercent));
        this.stringParsableToFloatPercent = stringParsableToFloatPercent;
        propagateHierarchyIdToField(stringParsableToFloatPercent, "string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnStringInSetCountCheckSpec getStringInSetCount() {
        return stringInSetCount;
    }

    /**
     * Sets a new definition of a string in set count check.
     * @param stringInSetCount String in set count check.
     */
    public void setStringInSetCount(ColumnStringInSetCountCheckSpec stringInSetCount) {
        this.setDirtyIf(!Objects.equals(this.stringInSetCount, stringInSetCount));
        this.stringInSetCount = stringInSetCount;
        propagateHierarchyIdToField(stringInSetCount, "string_in_set_count");
    }

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent check.
     */
    public ColumnStringInSetPercentCheckSpec getStringInSetPercent() {
        return stringInSetPercent;
    }

    /**
     * Sets a new definition of a strings in set percent check.
     * @param stringInSetPercent Strings in set percent check.
     */
    public void setStringInSetPercent(ColumnStringInSetPercentCheckSpec stringInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.stringInSetPercent, stringInSetPercent));
        this.stringInSetPercent = stringInSetPercent;
        propagateHierarchyIdToField(stringInSetPercent, "string_in_set_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnStringValidDatesPercentCheckSpec getStringValidDatesPercent() {
        return stringValidDatesPercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param stringValidDatesPercent String valid dates percent check.
     */
    public void setStringValidDatesPercent(ColumnStringValidDatesPercentCheckSpec stringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.stringValidDatesPercent, stringValidDatesPercent));
        this.stringValidDatesPercent = stringValidDatesPercent;
        propagateHierarchyIdToField(stringValidDatesPercent, "string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent check.
     */
    public ColumnStringValidUsaZipcodePercentCheckSpec getStringValidUsaZipcodePercent() {
        return stringValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a string valid usa zip code percent check.
     * @param stringValidUsaZipcodePercent String valid usa zip code percent check.
     */
    public void setStringValidUsaZipcodePercent(ColumnStringValidUsaZipcodePercentCheckSpec stringValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.stringValidUsaZipcodePercent, stringValidUsaZipcodePercent));
        this.stringValidUsaZipcodePercent = stringValidUsaZipcodePercent;
        propagateHierarchyIdToField(stringValidUsaZipcodePercent, "string_valid_usa_zipcode_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent check.
     */
    public ColumnStringValidUsaPhonePercentCheckSpec getStringValidUsaPhonePercent() {
        return stringValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a string valid USA phone percent check.
     * @param stringValidUsaPhonePercent String valid USA phone percent check.
     */
    public void setStringValidUsaPhonePercent(ColumnStringValidUsaPhonePercentCheckSpec stringValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.stringValidUsaPhonePercent, stringValidUsaPhonePercent));
        this.stringValidUsaPhonePercent = stringValidUsaPhonePercent;
        propagateHierarchyIdToField(stringValidUsaPhonePercent, "string_valid_usa_phone_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnStringValidCountryCodePercentCheckSpec getStringValidCountryCodePercent() {
        return stringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param stringValidCountryCodePercent String valid country code percent check.
     */
    public void setStringValidCountryCodePercent(ColumnStringValidCountryCodePercentCheckSpec stringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.stringValidCountryCodePercent, stringValidCountryCodePercent));
        this.stringValidCountryCodePercent = stringValidCountryCodePercent;
        propagateHierarchyIdToField(stringValidCountryCodePercent, "string_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringValidCurrencyCodePercentCheckSpec getStringValidCurrencyCodePercent() {
        return stringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param stringValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setStringValidCurrencyCodePercent(ColumnStringValidCurrencyCodePercentCheckSpec stringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.stringValidCurrencyCodePercent, stringValidCurrencyCodePercent));
        this.stringValidCurrencyCodePercent = stringValidCurrencyCodePercent;
        propagateHierarchyIdToField(stringValidCurrencyCodePercent, "string_valid_currency_code_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnStringInvalidEmailCountCheckSpec getStringInvalidEmailCount() {
        return stringInvalidEmailCount;
    }

    /**
     * Sets a new definition of an invalid email count check.
     * @param stringInvalidEmailCount Invalid email count check.
     */
    public void setStringInvalidEmailCount(ColumnStringInvalidEmailCountCheckSpec stringInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.stringInvalidEmailCount, stringInvalidEmailCount));
        this.stringInvalidEmailCount = stringInvalidEmailCount;
        propagateHierarchyIdToField(stringInvalidEmailCount, "string_invalid_email_count");
    }

    /**
     * Returns a valid email percent check.
     * @return Valid email percent check.
     */
    public ColumnStringValidEmailPercentCheckSpec getStringValidEmailPercent() {
        return stringValidEmailPercent;
    }

    /**
     * Sets a new definition of a valid email percent check.
     * @param stringValidEmailPercent Valid email percent check.
     */
    public void setStringValidEmailPercent(ColumnStringValidEmailPercentCheckSpec stringValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.stringValidEmailPercent, stringValidEmailPercent));
        this.stringValidEmailPercent = stringValidEmailPercent;
        propagateHierarchyIdToField(stringValidEmailPercent, "string_valid_email_percent");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnStringNotMatchRegexCountCheckSpec getStringNotMatchRegexCount() {
        return stringNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param stringNotMatchRegexCount Maximum not match regex count check.
     */
    public void setStringNotMatchRegexCount(ColumnStringNotMatchRegexCountCheckSpec stringNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.stringNotMatchRegexCount, stringNotMatchRegexCount));
        this.stringNotMatchRegexCount = stringNotMatchRegexCount;
        propagateHierarchyIdToField(stringNotMatchRegexCount, "string_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnStringRegexMatchPercentCheckSpec getStringMatchRegexPercent() {
        return stringMatchRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param stringMatchRegexPercent Minimum match regex percent check.
     */
    public void setStringMatchRegexPercent(ColumnStringRegexMatchPercentCheckSpec stringMatchRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.stringMatchRegexPercent, stringMatchRegexPercent));
        this.stringMatchRegexPercent = stringMatchRegexPercent;
        propagateHierarchyIdToField(stringMatchRegexPercent, "string_match_regex_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnStringNotMatchDateRegexCountCheckSpec getStringNotMatchDateRegexCount() {
        return stringNotMatchDateRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param stringNotMatchDateRegexCount Maximum not match date regex count check.
     */
    public void setStringNotMatchDateRegexCount(ColumnStringNotMatchDateRegexCountCheckSpec stringNotMatchDateRegexCount) {
        this.setDirtyIf(!Objects.equals(this.stringNotMatchDateRegexCount, stringNotMatchDateRegexCount));
        this.stringNotMatchDateRegexCount = stringNotMatchDateRegexCount;
        propagateHierarchyIdToField(stringNotMatchDateRegexCount, "string_not_match_date_regex_count");
    }

    /**
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnStringMatchDateRegexPercentCheckSpec getStringMatchDateRegexPercent() {
        return stringMatchDateRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param stringMatchDateRegexPercent Maximum match date regex percent check.
     */
    public void setStringMatchDateRegexPercent(ColumnStringMatchDateRegexPercentCheckSpec stringMatchDateRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.stringMatchDateRegexPercent, stringMatchDateRegexPercent));
        this.stringMatchDateRegexPercent = stringMatchDateRegexPercent;
        propagateHierarchyIdToField(stringMatchDateRegexPercent, "string_match_date_regex_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnStringMatchNameRegexPercentCheckSpec getStringMatchNameRegexPercent() {
        return stringMatchNameRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param stringMatchNameRegexPercent Maximum match name regex percent check.
     */
    public void setStringMatchNameRegexPercent(ColumnStringMatchNameRegexPercentCheckSpec stringMatchNameRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.stringMatchNameRegexPercent, stringMatchNameRegexPercent));
        this.stringMatchNameRegexPercent = stringMatchNameRegexPercent;
        propagateHierarchyIdToField(stringMatchNameRegexPercent, "string_match_name_regex_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnStringTopPopularValuesSetCheckSpec getStringTopPopularValuesSet() {
        return stringTopPopularValuesSet;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param stringTopPopularValuesSet Maximum match name regex percent check.
     */
    public void setStringMatchNameRegexPercent(ColumnStringTopPopularValuesSetCheckSpec stringTopPopularValuesSet) {
        this.setDirtyIf(!Objects.equals(this.stringTopPopularValuesSet, stringTopPopularValuesSet));
        this.stringTopPopularValuesSet = stringTopPopularValuesSet;
        propagateHierarchyIdToField(stringTopPopularValuesSet, "string_top_popular_values_set");
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
