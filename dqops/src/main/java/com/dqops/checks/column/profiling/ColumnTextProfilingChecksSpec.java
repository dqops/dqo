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
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.text.*;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for string.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_string_max_length", o -> o.profileStringMaxLength);
            put("profile_string_min_length", o -> o.profileStringMinLength);
            put("profile_string_mean_length", o -> o.profileStringMeanLength);
            put("profile_string_length_below_min_length_count", o -> o.profileStringLengthBelowMinLengthCount);
            put("profile_string_length_below_min_length_percent", o -> o.profileStringLengthBelowMinLengthPercent);
            put("profile_string_length_above_max_length_count", o -> o.profileStringLengthAboveMaxLengthCount);
            put("profile_string_length_above_max_length_percent", o -> o.profileStringLengthAboveMaxLengthPercent);
            put("profile_string_length_in_range_percent", o -> o.profileStringLengthInRangePercent);

            put("profile_string_boolean_placeholder_percent", o -> o.profileStringBooleanPlaceholderPercent);
            put("profile_string_parsable_to_integer_percent", o -> o.profileStringParsableToIntegerPercent);
            put("profile_string_parsable_to_float_percent", o -> o.profileStringParsableToFloatPercent);
            put("profile_string_surrounded_by_whitespace_count", o -> o.profileStringSurroundedByWhitespaceCount);
            put("profile_string_surrounded_by_whitespace_percent", o -> o.profileStringSurroundedByWhitespacePercent);

            put("profile_string_valid_dates_percent", o -> o.profileStringValidDatesPercent);
            put("profile_string_valid_country_code_percent", o -> o.profileStringValidCountryCodePercent);
            put("profile_string_valid_currency_code_percent", o -> o.profileStringValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length.")
    private ColumnTextMaxLengthCheckSpec profileStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not fall below the minimum accepted length.")
    private ColumnTextMinLengthCheckSpec profileStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length.")
    private ColumnTextMeanLengthCheckSpec profileStringMeanLength;

    @JsonPropertyDescription("The check counts the number of strings in the column that is below the length defined by the user as a parameter.")
    private ColumnTextLengthBelowMinLengthCountCheckSpec profileStringLengthBelowMinLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is below the length defined by the user as a parameter.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec profileStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of strings in the column that is above the length defined by the user as a parameter.")
    private ColumnTextLengthAboveMaxLengthCountCheckSpec profileStringLengthAboveMaxLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is above the length defined by the user as a parameter.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec profileStringLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check counts the percentage of those strings with length in the range provided by the user in the column. ")
    private ColumnTextLengthInRangePercentCheckSpec profileStringLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage.")
    private ColumnTextBooleanPlaceholderPercentCheckSpec profileStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage.")
    private ColumnTextParsableToIntegerPercentCheckSpec profileStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage.")
    private ColumnTextParsableToFloatPercentCheckSpec profileStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.")
    private ColumnTextSurroundedByWhitespaceCountCheckSpec profileStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec profileStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage.")
    private ColumnTextValidDatesPercentCheckSpec profileStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.")
    private ColumnTextValidCountryCodePercentCheckSpec profileStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage.")
    private ColumnTextValidCurrencyCodePercentCheckSpec profileStringValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getProfileStringMaxLength() {
        return profileStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param profileStringMaxLength Maximum string length check.
     */
    public void setProfileStringMaxLength(ColumnTextMaxLengthCheckSpec profileStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.profileStringMaxLength, profileStringMaxLength));
        this.profileStringMaxLength = profileStringMaxLength;
        propagateHierarchyIdToField(profileStringMaxLength, "profile_string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getProfileStringMinLength() {
        return profileStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param profileStringMinLength Minimum string length check.
     */
    public void setProfileStringMinLength(ColumnTextMinLengthCheckSpec profileStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.profileStringMinLength, profileStringMinLength));
        this.profileStringMinLength = profileStringMinLength;
        propagateHierarchyIdToField(profileStringMinLength, "profile_string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getProfileStringMeanLength() {
        return profileStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param profileStringMeanLength Mean string length check.
     */
    public void setProfileStringMeanLength(ColumnTextMeanLengthCheckSpec profileStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.profileStringMeanLength, profileStringMeanLength));
        this.profileStringMeanLength = profileStringMeanLength;
        propagateHierarchyIdToField(profileStringMeanLength, "profile_string_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCountCheckSpec getProfileStringLengthBelowMinLengthCount() {
        return profileStringLengthBelowMinLengthCount;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param profileStringLengthBelowMinLengthCount String length below min length count check.
     */
    public void setProfileStringLengthBelowMinLengthCount(ColumnTextLengthBelowMinLengthCountCheckSpec profileStringLengthBelowMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthBelowMinLengthCount, profileStringLengthBelowMinLengthCount));
        this.profileStringLengthBelowMinLengthCount = profileStringLengthBelowMinLengthCount;
        propagateHierarchyIdToField(profileStringLengthBelowMinLengthCount, "profile_string_length_below_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getProfileStringLengthBelowMinLengthPercent() {
        return profileStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param profileStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setProfileStringLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec profileStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthBelowMinLengthPercent, profileStringLengthBelowMinLengthPercent));
        this.profileStringLengthBelowMinLengthPercent = profileStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(profileStringLengthBelowMinLengthPercent, "profile_string_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCountCheckSpec getProfileStringLengthAboveMaxLengthCount() {
        return profileStringLengthAboveMaxLengthCount;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param profileStringLengthAboveMaxLengthCount String length above max length count check.
     */
    public void setProfileStringLengthAboveMaxLengthCount(ColumnTextLengthAboveMaxLengthCountCheckSpec profileStringLengthAboveMaxLengthCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthAboveMaxLengthCount, profileStringLengthAboveMaxLengthCount));
        this.profileStringLengthAboveMaxLengthCount = profileStringLengthAboveMaxLengthCount;
        propagateHierarchyIdToField(profileStringLengthAboveMaxLengthCount, "profile_string_length_above_max_length_count");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getProfileStringLengthAboveMaxLengthPercent() {
        return profileStringLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param profileStringLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setProfileStringLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec profileStringLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthAboveMaxLengthPercent, profileStringLengthAboveMaxLengthPercent));
        this.profileStringLengthAboveMaxLengthPercent = profileStringLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(profileStringLengthAboveMaxLengthPercent, "profile_string_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getProfileStringLengthInRangePercent() {
        return profileStringLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param profileStringLengthInRangePercent String length in range percent check.
     */
    public void setProfileStringLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec profileStringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringLengthInRangePercent, profileStringLengthInRangePercent));
        this.profileStringLengthInRangePercent = profileStringLengthInRangePercent;
        propagateHierarchyIdToField(profileStringLengthInRangePercent, "profile_string_length_in_range_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextBooleanPlaceholderPercentCheckSpec getProfileStringBooleanPlaceholderPercent() {
        return profileStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param profileStringBooleanPlaceholderPercent String boolean placeholder percent check.
     */
    public void setProfileStringBooleanPlaceholderPercent(ColumnTextBooleanPlaceholderPercentCheckSpec profileStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringBooleanPlaceholderPercent, profileStringBooleanPlaceholderPercent));
        this.profileStringBooleanPlaceholderPercent = profileStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(profileStringBooleanPlaceholderPercent, "profile_string_boolean_placeholder_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getProfileStringParsableToIntegerPercent() {
        return profileStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param profileStringParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setProfileStringParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec profileStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringParsableToIntegerPercent, profileStringParsableToIntegerPercent));
        this.profileStringParsableToIntegerPercent = profileStringParsableToIntegerPercent;
        propagateHierarchyIdToField(profileStringParsableToIntegerPercent, "profile_string_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getProfileStringParsableToFloatPercent() {
        return profileStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param profileStringParsableToFloatPercent String parsable to float percent check.
     */
    public void setProfileStringParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec profileStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringParsableToFloatPercent, profileStringParsableToFloatPercent));
        this.profileStringParsableToFloatPercent = profileStringParsableToFloatPercent;
        propagateHierarchyIdToField(profileStringParsableToFloatPercent, "profile_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextSurroundedByWhitespaceCountCheckSpec getProfileStringSurroundedByWhitespaceCount() {
        return profileStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param profileStringSurroundedByWhitespaceCount String surrounded by whitespace count check.
     */
    public void setProfileStringSurroundedByWhitespaceCount(ColumnTextSurroundedByWhitespaceCountCheckSpec profileStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringSurroundedByWhitespaceCount, profileStringSurroundedByWhitespaceCount));
        this.profileStringSurroundedByWhitespaceCount = profileStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(profileStringSurroundedByWhitespaceCount, "profile_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getProfileStringSurroundedByWhitespacePercent() {
        return profileStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param profileStringSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setProfileStringSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec profileStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringSurroundedByWhitespacePercent, profileStringSurroundedByWhitespacePercent));
        this.profileStringSurroundedByWhitespacePercent = profileStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(profileStringSurroundedByWhitespacePercent, "profile_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextValidDatesPercentCheckSpec getProfileStringValidDatesPercent() {
        return profileStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param profileStringValidDatesPercent String valid dates percent check.
     */
    public void setProfileStringValidDatesPercent(ColumnTextValidDatesPercentCheckSpec profileStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringValidDatesPercent, profileStringValidDatesPercent));
        this.profileStringValidDatesPercent = profileStringValidDatesPercent;
        propagateHierarchyIdToField(profileStringValidDatesPercent, "profile_string_valid_dates_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getProfileStringValidCountryCodePercent() {
        return profileStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param profileStringValidCountryCodePercent String valid country code percent check.
     */
    public void setProfileStringValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec profileStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringValidCountryCodePercent, profileStringValidCountryCodePercent));
        this.profileStringValidCountryCodePercent = profileStringValidCountryCodePercent;
        propagateHierarchyIdToField(profileStringValidCountryCodePercent, "profile_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getProfileStringValidCurrencyCodePercent() {
        return profileStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param profileStringValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setProfileStringValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec profileStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringValidCurrencyCodePercent, profileStringValidCurrencyCodePercent));
        this.profileStringValidCurrencyCodePercent = profileStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(profileStringValidCurrencyCodePercent, "profile_string_valid_currency_code_percent");
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
    public ColumnTextProfilingChecksSpec deepClone() {
        return (ColumnTextProfilingChecksSpec)super.deepClone();
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
        return CheckType.profiling;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return null;
    }
}
