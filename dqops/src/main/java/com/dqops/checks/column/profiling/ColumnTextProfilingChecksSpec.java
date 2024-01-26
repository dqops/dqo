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
import com.dqops.connectors.DataTypeCategory;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking text values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_text_max_length", o -> o.profileTextMaxLength);
            put("profile_text_min_length", o -> o.profileTextMinLength);
            put("profile_text_mean_length", o -> o.profileTextMeanLength);
            put("profile_text_length_below_min_length", o -> o.profileTextLengthBelowMinLength);
            put("profile_text_length_below_min_length_percent", o -> o.profileTextLengthBelowMinLengthPercent);
            put("profile_text_length_above_max_length", o -> o.profileTextLengthAboveMaxLength);
            put("profile_text_length_above_max_length_percent", o -> o.profileTextLengthAboveMaxLengthPercent);
            put("profile_text_length_in_range_percent", o -> o.profileTextLengthInRangePercent);

            put("profile_text_parsable_to_boolean_percent", o -> o.profileTextParsableToBooleanPercent);
            put("profile_text_parsable_to_integer_percent", o -> o.profileTextParsableToIntegerPercent);
            put("profile_text_parsable_to_float_percent", o -> o.profileTextParsableToFloatPercent);
            put("profile_text_parsable_to_date_percent", o -> o.profileTextParsableToDatePercent);

            put("profile_text_surrounded_by_whitespace", o -> o.profileTextSurroundedByWhitespace);
            put("profile_text_surrounded_by_whitespace_percent", o -> o.profileTextSurroundedByWhitespacePercent);
            put("profile_text_valid_country_code_percent", o -> o.profileTextValidCountryCodePercent);
            put("profile_text_valid_currency_code_percent", o -> o.profileTextValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the maximum accepted length")
    private ColumnTextMaxLengthCheckSpec profileTextMaxLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not fall below the minimum accepted length")
    private ColumnTextMinLengthCheckSpec profileTextMinLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the mean accepted length")
    private ColumnTextMeanLengthCheckSpec profileTextMeanLength;

    @JsonPropertyDescription("The check counts the number of text values in the column that is below the length defined by the user as a parameter")
    private ColumnTextLengthBelowMinLengthCheckSpec profileTextLengthBelowMinLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is below the length defined by the user as a parameter")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec profileTextLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that is above the length defined by the user as a parameter")
    private ColumnTextLengthAboveMaxLengthCheckSpec profileTextLengthAboveMaxLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is above the length defined by the user as a parameter")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec profileTextLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check measures the percentage of those text values with length in the range provided by the user in the column")
    private ColumnTextLengthInRangePercentCheckSpec profileTextLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, " +
            "text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n.")
    private ColumnTextParsableToBooleanPercentCheckSpec profileTextParsableToBooleanPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage")
    private ColumnTextParsableToIntegerPercentCheckSpec profileTextParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage")
    private ColumnTextParsableToFloatPercentCheckSpec profileTextParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression")
    private ColumnTextParsableToDatePercentCheckSpec profileTextParsableToDatePercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table")
    private ColumnTextSurroundedByWhitespaceCheckSpec profileTextSurroundedByWhitespace;

    @JsonPropertyDescription("Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec profileTextSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage")
    private ColumnTextValidCountryCodePercentCheckSpec profileTextValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage")
    private ColumnTextValidCurrencyCodePercentCheckSpec profileTextValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getProfileTextMaxLength() {
        return profileTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param profileTextMaxLength Maximum string length check.
     */
    public void setProfileTextMaxLength(ColumnTextMaxLengthCheckSpec profileTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.profileTextMaxLength, profileTextMaxLength));
        this.profileTextMaxLength = profileTextMaxLength;
        propagateHierarchyIdToField(profileTextMaxLength, "profile_text_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getProfileTextMinLength() {
        return profileTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param profileTextMinLength Minimum string length check.
     */
    public void setProfileTextMinLength(ColumnTextMinLengthCheckSpec profileTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.profileTextMinLength, profileTextMinLength));
        this.profileTextMinLength = profileTextMinLength;
        propagateHierarchyIdToField(profileTextMinLength, "profile_text_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getProfileTextMeanLength() {
        return profileTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param profileTextMeanLength Mean string length check.
     */
    public void setProfileTextMeanLength(ColumnTextMeanLengthCheckSpec profileTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.profileTextMeanLength, profileTextMeanLength));
        this.profileTextMeanLength = profileTextMeanLength;
        propagateHierarchyIdToField(profileTextMeanLength, "profile_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getProfileTextLengthBelowMinLength() {
        return profileTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param profileTextLengthBelowMinLength String length below min length count check.
     */
    public void setProfileTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec profileTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.profileTextLengthBelowMinLength, profileTextLengthBelowMinLength));
        this.profileTextLengthBelowMinLength = profileTextLengthBelowMinLength;
        propagateHierarchyIdToField(profileTextLengthBelowMinLength, "profile_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getProfileTextLengthBelowMinLengthPercent() {
        return profileTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param profileTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setProfileTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec profileTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextLengthBelowMinLengthPercent, profileTextLengthBelowMinLengthPercent));
        this.profileTextLengthBelowMinLengthPercent = profileTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(profileTextLengthBelowMinLengthPercent, "profile_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getProfileTextLengthAboveMaxLength() {
        return profileTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param profileTextLengthAboveMaxLength String length above max length count check.
     */
    public void setProfileTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec profileTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.profileTextLengthAboveMaxLength, profileTextLengthAboveMaxLength));
        this.profileTextLengthAboveMaxLength = profileTextLengthAboveMaxLength;
        propagateHierarchyIdToField(profileTextLengthAboveMaxLength, "profile_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getProfileTextLengthAboveMaxLengthPercent() {
        return profileTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param profileTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setProfileTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec profileTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextLengthAboveMaxLengthPercent, profileTextLengthAboveMaxLengthPercent));
        this.profileTextLengthAboveMaxLengthPercent = profileTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(profileTextLengthAboveMaxLengthPercent, "profile_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getProfileTextLengthInRangePercent() {
        return profileTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param profileTextLengthInRangePercent String length in range percent check.
     */
    public void setProfileTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec profileTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextLengthInRangePercent, profileTextLengthInRangePercent));
        this.profileTextLengthInRangePercent = profileTextLengthInRangePercent;
        propagateHierarchyIdToField(profileTextLengthInRangePercent, "profile_text_length_in_range_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getProfileTextParsableToBooleanPercent() {
        return profileTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param profileTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setProfileTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec profileTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextParsableToBooleanPercent, profileTextParsableToBooleanPercent));
        this.profileTextParsableToBooleanPercent = profileTextParsableToBooleanPercent;
        propagateHierarchyIdToField(profileTextParsableToBooleanPercent, "profile_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getProfileTextParsableToIntegerPercent() {
        return profileTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param profileTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setProfileTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec profileTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextParsableToIntegerPercent, profileTextParsableToIntegerPercent));
        this.profileTextParsableToIntegerPercent = profileTextParsableToIntegerPercent;
        propagateHierarchyIdToField(profileTextParsableToIntegerPercent, "profile_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getProfileTextParsableToFloatPercent() {
        return profileTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param profileTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setProfileTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec profileTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextParsableToFloatPercent, profileTextParsableToFloatPercent));
        this.profileTextParsableToFloatPercent = profileTextParsableToFloatPercent;
        propagateHierarchyIdToField(profileTextParsableToFloatPercent, "profile_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getProfileTextParsableToDatePercent() {
        return profileTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param profileTextParsableToDatePercent String valid dates percent check.
     */
    public void setProfileTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec profileTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextParsableToDatePercent, profileTextParsableToDatePercent));
        this.profileTextParsableToDatePercent = profileTextParsableToDatePercent;
        propagateHierarchyIdToField(profileTextParsableToDatePercent, "profile_text_parsable_to_date_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextSurroundedByWhitespaceCheckSpec getProfileTextSurroundedByWhitespace() {
        return profileTextSurroundedByWhitespace;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param profileTextSurroundedByWhitespace String surrounded by whitespace count check.
     */
    public void setProfileTextSurroundedByWhitespace(ColumnTextSurroundedByWhitespaceCheckSpec profileTextSurroundedByWhitespace) {
        this.setDirtyIf(!Objects.equals(this.profileTextSurroundedByWhitespace, profileTextSurroundedByWhitespace));
        this.profileTextSurroundedByWhitespace = profileTextSurroundedByWhitespace;
        propagateHierarchyIdToField(profileTextSurroundedByWhitespace, "profile_text_surrounded_by_whitespace");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getProfileTextSurroundedByWhitespacePercent() {
        return profileTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param profileTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setProfileTextSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec profileTextSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextSurroundedByWhitespacePercent, profileTextSurroundedByWhitespacePercent));
        this.profileTextSurroundedByWhitespacePercent = profileTextSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(profileTextSurroundedByWhitespacePercent, "profile_text_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getProfileTextValidCountryCodePercent() {
        return profileTextValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param profileTextValidCountryCodePercent String valid country code percent check.
     */
    public void setProfileTextValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec profileTextValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextValidCountryCodePercent, profileTextValidCountryCodePercent));
        this.profileTextValidCountryCodePercent = profileTextValidCountryCodePercent;
        propagateHierarchyIdToField(profileTextValidCountryCodePercent, "profile_text_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getProfileTextValidCurrencyCodePercent() {
        return profileTextValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param profileTextValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setProfileTextValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec profileTextValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextValidCurrencyCodePercent, profileTextValidCurrencyCodePercent));
        this.profileTextValidCurrencyCodePercent = profileTextValidCurrencyCodePercent;
        propagateHierarchyIdToField(profileTextValidCurrencyCodePercent, "profile_text_valid_currency_code_percent");
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

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.STRING;
    }
}
