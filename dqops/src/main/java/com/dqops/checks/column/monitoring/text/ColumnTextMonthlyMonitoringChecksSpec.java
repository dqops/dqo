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
package com.dqops.checks.column.monitoring.text;

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
 * Container of text data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_text_max_length", o -> o.monthlyTextMaxLength);
            put("monthly_text_min_length", o -> o.monthlyTextMinLength);
            put("monthly_text_mean_length", o -> o.monthlyTextMeanLength);
            put("monthly_text_length_below_min_length", o -> o.monthlyTextLengthBelowMinLength);
            put("monthly_text_length_below_min_length_percent", o -> o.monthlyTextLengthBelowMinLengthPercent);
            put("monthly_text_length_above_max_length", o -> o.monthlyTextLengthAboveMaxLength);
            put("monthly_text_length_above_max_length_percent", o -> o.monthlyTextLengthAboveMaxLengthPercent);
            put("monthly_text_length_in_range_percent", o -> o.monthlyTextLengthInRangePercent);

            put("monthly_text_parsable_to_boolean_percent", o -> o.monthlyTextParsableToBooleanPercent);
            put("monthly_text_parsable_to_integer_percent", o -> o.monthlyTextParsableToIntegerPercent);
            put("monthly_text_parsable_to_float_percent", o -> o.monthlyTextParsableToFloatPercent);
            put("monthly_text_parsable_to_date_percent", o -> o.monthlyTextParsableToDatePercent);

            put("monthly_text_surrounded_by_whitespace", o -> o.monthlyTextSurroundedByWhitespace);
            put("monthly_text_surrounded_by_whitespace_percent", o -> o.monthlyTextSurroundedByWhitespacePercent);
            put("monthly_text_valid_country_code_percent", o -> o.monthlyTextValidCountryCodePercent);
            put("monthly_text_valid_currency_code_percent", o -> o.monthlyTextValidCurrencyCodePercent);
        }
    };


    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextMaxLengthCheckSpec monthlyTextMaxLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextMinLengthCheckSpec monthlyTextMinLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextMeanLengthCheckSpec monthlyTextMeanLength;

    @JsonPropertyDescription("The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthCheckSpec monthlyTextLengthBelowMinLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyTextLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthCheckSpec monthlyTextLengthAboveMaxLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyTextLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthInRangePercentCheckSpec monthlyTextLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, " +
            "text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextParsableToBooleanPercentCheckSpec monthlyTextParsableToBooleanPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextParsableToIntegerPercentCheckSpec monthlyTextParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextParsableToFloatPercentCheckSpec monthlyTextParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextParsableToDatePercentCheckSpec monthlyTextParsableToDatePercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextSurroundedByWhitespaceCheckSpec monthlyTextSurroundedByWhitespace;

    @JsonPropertyDescription("Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyTextSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextValidCountryCodePercentCheckSpec monthlyTextValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextValidCurrencyCodePercentCheckSpec monthlyTextValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getMonthlyTextMaxLength() {
        return monthlyTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param monthlyTextMaxLength Maximum string length check.
     */
    public void setMonthlyTextMaxLength(ColumnTextMaxLengthCheckSpec monthlyTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextMaxLength, monthlyTextMaxLength));
        this.monthlyTextMaxLength = monthlyTextMaxLength;
        propagateHierarchyIdToField(monthlyTextMaxLength, "monthly_text_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getMonthlyTextMinLength() {
        return monthlyTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param monthlyTextMinLength Minimum string length check.
     */
    public void setMonthlyTextMinLength(ColumnTextMinLengthCheckSpec monthlyTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextMinLength, monthlyTextMinLength));
        this.monthlyTextMinLength = monthlyTextMinLength;
        propagateHierarchyIdToField(monthlyTextMinLength, "monthly_text_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getMonthlyTextMeanLength() {
        return monthlyTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param monthlyTextMeanLength Mean string length check.
     */
    public void setMonthlyTextMeanLength(ColumnTextMeanLengthCheckSpec monthlyTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextMeanLength, monthlyTextMeanLength));
        this.monthlyTextMeanLength = monthlyTextMeanLength;
        propagateHierarchyIdToField(monthlyTextMeanLength, "monthly_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getMonthlyTextLengthBelowMinLength() {
        return monthlyTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param monthlyTextLengthBelowMinLength String length below min length count check.
     */
    public void setMonthlyTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec monthlyTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthBelowMinLength, monthlyTextLengthBelowMinLength));
        this.monthlyTextLengthBelowMinLength = monthlyTextLengthBelowMinLength;
        propagateHierarchyIdToField(monthlyTextLengthBelowMinLength, "monthly_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getMonthlyTextLengthBelowMinLengthPercent() {
        return monthlyTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param monthlyTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setMonthlyTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthBelowMinLengthPercent, monthlyTextLengthBelowMinLengthPercent));
        this.monthlyTextLengthBelowMinLengthPercent = monthlyTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(monthlyTextLengthBelowMinLengthPercent, "monthly_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getMonthlyTextLengthAboveMaxLength() {
        return monthlyTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param monthlyTextLengthAboveMaxLength String length above max length count check.
     */
    public void setMonthlyTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec monthlyTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthAboveMaxLength, monthlyTextLengthAboveMaxLength));
        this.monthlyTextLengthAboveMaxLength = monthlyTextLengthAboveMaxLength;
        propagateHierarchyIdToField(monthlyTextLengthAboveMaxLength, "monthly_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getMonthlyTextLengthAboveMaxLengthPercent() {
        return monthlyTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param monthlyTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setMonthlyTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthAboveMaxLengthPercent, monthlyTextLengthAboveMaxLengthPercent));
        this.monthlyTextLengthAboveMaxLengthPercent = monthlyTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(monthlyTextLengthAboveMaxLengthPercent, "monthly_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getMonthlyTextLengthInRangePercent() {
        return monthlyTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param monthlyTextLengthInRangePercent String length in range percent check.
     */
    public void setMonthlyTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec monthlyTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthInRangePercent, monthlyTextLengthInRangePercent));
        this.monthlyTextLengthInRangePercent = monthlyTextLengthInRangePercent;
        propagateHierarchyIdToField(monthlyTextLengthInRangePercent, "monthly_text_length_in_range_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getMonthlyTextParsableToBooleanPercent() {
        return monthlyTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param monthlyTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setMonthlyTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec monthlyTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextParsableToBooleanPercent, monthlyTextParsableToBooleanPercent));
        this.monthlyTextParsableToBooleanPercent = monthlyTextParsableToBooleanPercent;
        propagateHierarchyIdToField(monthlyTextParsableToBooleanPercent, "monthly_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getMonthlyTextParsableToIntegerPercent() {
        return monthlyTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param monthlyTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setMonthlyTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec monthlyTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextParsableToIntegerPercent, monthlyTextParsableToIntegerPercent));
        this.monthlyTextParsableToIntegerPercent = monthlyTextParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyTextParsableToIntegerPercent, "monthly_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getMonthlyTextParsableToFloatPercent() {
        return monthlyTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param monthlyTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setMonthlyTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec monthlyTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextParsableToFloatPercent, monthlyTextParsableToFloatPercent));
        this.monthlyTextParsableToFloatPercent = monthlyTextParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyTextParsableToFloatPercent, "monthly_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getMonthlyTextParsableToDatePercent() {
        return monthlyTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param monthlyTextParsableToDatePercent String valid dates percent check.
     */
    public void setMonthlyTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec monthlyTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextParsableToDatePercent, monthlyTextParsableToDatePercent));
        this.monthlyTextParsableToDatePercent = monthlyTextParsableToDatePercent;
        propagateHierarchyIdToField(monthlyTextParsableToDatePercent, "monthly_text_parsable_to_date_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextSurroundedByWhitespaceCheckSpec getMonthlyTextSurroundedByWhitespace() {
        return monthlyTextSurroundedByWhitespace;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param monthlyTextSurroundedByWhitespace String surrounded by whitespace count check.
     */
    public void setMonthlyTextSurroundedByWhitespace(ColumnTextSurroundedByWhitespaceCheckSpec monthlyTextSurroundedByWhitespace) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextSurroundedByWhitespace, monthlyTextSurroundedByWhitespace));
        this.monthlyTextSurroundedByWhitespace = monthlyTextSurroundedByWhitespace;
        propagateHierarchyIdToField(monthlyTextSurroundedByWhitespace, "monthly_text_surrounded_by_whitespace");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getMonthlyTextSurroundedByWhitespacePercent() {
        return monthlyTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param monthlyTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setMonthlyTextSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyTextSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextSurroundedByWhitespacePercent, monthlyTextSurroundedByWhitespacePercent));
        this.monthlyTextSurroundedByWhitespacePercent = monthlyTextSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyTextSurroundedByWhitespacePercent, "monthly_text_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getMonthlyTextValidCountryCodePercent() {
        return monthlyTextValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param monthlyTextValidCountryCodePercent String valid country code percent check.
     */
    public void setMonthlyTextValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec monthlyTextValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextValidCountryCodePercent, monthlyTextValidCountryCodePercent));
        this.monthlyTextValidCountryCodePercent = monthlyTextValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyTextValidCountryCodePercent, "monthly_text_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getMonthlyTextValidCurrencyCodePercent() {
        return monthlyTextValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param monthlyTextValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setMonthlyTextValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec monthlyTextValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextValidCurrencyCodePercent, monthlyTextValidCurrencyCodePercent));
        this.monthlyTextValidCurrencyCodePercent = monthlyTextValidCurrencyCodePercent;
        propagateHierarchyIdToField(monthlyTextValidCurrencyCodePercent, "monthly_text_valid_currency_code_percent");
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
    public ColumnTextMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnTextMonthlyMonitoringChecksSpec)super.deepClone();
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
        return CheckTimeScale.monthly;
    }
}
