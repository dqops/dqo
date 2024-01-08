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
            put("monthly_string_max_length", o -> o.monthlyStringMaxLength);
            put("monthly_string_min_length", o -> o.monthlyStringMinLength);
            put("monthly_string_mean_length", o -> o.monthlyStringMeanLength);
            put("monthly_string_length_below_min_length_count", o -> o.monthlyStringLengthBelowMinLengthCount);
            put("monthly_string_length_below_min_length_percent", o -> o.monthlyStringLengthBelowMinLengthPercent);
            put("monthly_string_length_above_max_length_count", o -> o.monthlyStringLengthAboveMaxLengthCount);
            put("monthly_string_length_above_max_length_percent", o -> o.monthlyStringLengthAboveMaxLengthPercent);
            put("monthly_string_length_in_range_percent", o -> o.monthlyStringLengthInRangePercent);

            put("monthly_string_surrounded_by_whitespace_count", o -> o.monthlyStringSurroundedByWhitespaceCount);
            put("monthly_string_surrounded_by_whitespace_percent", o -> o.monthlyStringSurroundedByWhitespacePercent);
            put("monthly_string_boolean_placeholder_percent", o -> o.monthlyStringBooleanPlaceholderPercent);
            put("monthly_string_parsable_to_integer_percent", o -> o.monthlyStringParsableToIntegerPercent);
            put("monthly_string_parsable_to_float_percent", o -> o.monthlyStringParsableToFloatPercent);

            put("monthly_string_valid_dates_percent", o -> o.monthlyStringValidDatesPercent);
            put("monthly_string_valid_country_code_percent", o -> o.monthlyStringValidCountryCodePercent);
            put("monthly_string_valid_currency_code_percent", o -> o.monthlyStringValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextMaxLengthCheckSpec monthlyStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextMinLengthCheckSpec monthlyStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextMeanLengthCheckSpec monthlyStringMeanLength;

    @JsonPropertyDescription("The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthCountCheckSpec monthlyStringLengthBelowMinLengthCount;

    @JsonPropertyDescription("The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthCountCheckSpec monthlyStringLengthAboveMaxLengthCount;

    @JsonPropertyDescription("The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyStringLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextLengthInRangePercentCheckSpec monthlyStringLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextValidDatesPercentCheckSpec monthlyStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextSurroundedByWhitespaceCountCheckSpec monthlyStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextBooleanPlaceholderPercentCheckSpec monthlyStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextParsableToIntegerPercentCheckSpec monthlyStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextParsableToFloatPercentCheckSpec monthlyStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextValidCountryCodePercentCheckSpec monthlyStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTextValidCurrencyCodePercentCheckSpec monthlyStringValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getMonthlyStringMaxLength() {
        return monthlyStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param monthlyStringMaxLength Maximum string length below check.
     */
    public void setMonthlyStringMaxLength(ColumnTextMaxLengthCheckSpec monthlyStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringMaxLength, monthlyStringMaxLength));
        this.monthlyStringMaxLength = monthlyStringMaxLength;
        propagateHierarchyIdToField(monthlyStringMaxLength, "monthly_string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getMonthlyStringMinLength() {
        return monthlyStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param monthlyStringMinLength Minimum string length below check.
     */
    public void setMonthlyStringMinLength(ColumnTextMinLengthCheckSpec monthlyStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringMinLength, monthlyStringMinLength));
        this.monthlyStringMinLength = monthlyStringMinLength;
        propagateHierarchyIdToField(monthlyStringMinLength, "monthly_string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getMonthlyStringMeanLength() {
        return monthlyStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param monthlyStringMeanLength Mean string length between check.
     */
    public void setMonthlyStringMeanLength(ColumnTextMeanLengthCheckSpec monthlyStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringMeanLength, monthlyStringMeanLength));
        this.monthlyStringMeanLength = monthlyStringMeanLength;
        propagateHierarchyIdToField(monthlyStringMeanLength, "monthly_string_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCountCheckSpec getMonthlyStringLengthBelowMinLengthCount() {
        return monthlyStringLengthBelowMinLengthCount;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param monthlyStringLengthBelowMinLengthCount String length below min length count check.
     */
    public void setMonthlyStringLengthBelowMinLengthCount(ColumnTextLengthBelowMinLengthCountCheckSpec monthlyStringLengthBelowMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthBelowMinLengthCount, monthlyStringLengthBelowMinLengthCount));
        this.monthlyStringLengthBelowMinLengthCount = monthlyStringLengthBelowMinLengthCount;
        propagateHierarchyIdToField(monthlyStringLengthBelowMinLengthCount, "monthly_string_length_below_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return Mean string length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getMonthlyStringLengthBelowMinLengthPercent() {
        return monthlyStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param monthlyStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setMonthlyStringLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthBelowMinLengthPercent, monthlyStringLengthBelowMinLengthPercent));
        this.monthlyStringLengthBelowMinLengthPercent = monthlyStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(monthlyStringLengthBelowMinLengthPercent, "monthly_string_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCountCheckSpec getMonthlyStringLengthAboveMaxLengthCount() {
        return monthlyStringLengthAboveMaxLengthCount;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param monthlyStringLengthAboveMaxLengthCount String length above max length count check.
     */
    public void setMonthlyStringLengthAboveMaxLengthCount(ColumnTextLengthAboveMaxLengthCountCheckSpec monthlyStringLengthAboveMaxLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthAboveMaxLengthCount, monthlyStringLengthAboveMaxLengthCount));
        this.monthlyStringLengthAboveMaxLengthCount = monthlyStringLengthAboveMaxLengthCount;
        propagateHierarchyIdToField(monthlyStringLengthAboveMaxLengthCount, "monthly_string_length_above_max_length_count");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getMonthlyStringLengthAboveMaxLengthPercent() {
        return monthlyStringLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param monthlyStringLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setMonthlyStringLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyStringLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthAboveMaxLengthPercent, monthlyStringLengthAboveMaxLengthPercent));
        this.monthlyStringLengthAboveMaxLengthPercent = monthlyStringLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(monthlyStringLengthAboveMaxLengthPercent, "monthly_string_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getMonthlyStringLengthInRangePercent() {
        return monthlyStringLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param monthlyStringLengthInRangePercent String length in range percent check.
     */
    public void setMonthlyStringLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec monthlyStringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringLengthInRangePercent, monthlyStringLengthInRangePercent));
        this.monthlyStringLengthInRangePercent = monthlyStringLengthInRangePercent;
        propagateHierarchyIdToField(monthlyStringLengthInRangePercent, "monthly_string_length_in_range_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextSurroundedByWhitespaceCountCheckSpec getMonthlyStringSurroundedByWhitespaceCount() {
        return monthlyStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param monthlyStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setMonthlyStringSurroundedByWhitespaceCount(ColumnTextSurroundedByWhitespaceCountCheckSpec monthlyStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringSurroundedByWhitespaceCount, monthlyStringSurroundedByWhitespaceCount));
        this.monthlyStringSurroundedByWhitespaceCount = monthlyStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(monthlyStringSurroundedByWhitespaceCount, "monthly_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getMonthlyStringSurroundedByWhitespacePercent() {
        return monthlyStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param monthlyStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setMonthlyStringSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringSurroundedByWhitespacePercent, monthlyStringSurroundedByWhitespacePercent));
        this.monthlyStringSurroundedByWhitespacePercent = monthlyStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyStringSurroundedByWhitespacePercent, "monthly_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnTextBooleanPlaceholderPercentCheckSpec getMonthlyStringBooleanPlaceholderPercent() {
        return monthlyStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param monthlyStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setMonthlyStringBooleanPlaceholderPercent(ColumnTextBooleanPlaceholderPercentCheckSpec monthlyStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringBooleanPlaceholderPercent, monthlyStringBooleanPlaceholderPercent));
        this.monthlyStringBooleanPlaceholderPercent = monthlyStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(monthlyStringBooleanPlaceholderPercent, "monthly_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getMonthlyStringParsableToIntegerPercent() {
        return monthlyStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param monthlyStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setMonthlyStringParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec monthlyStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringParsableToIntegerPercent, monthlyStringParsableToIntegerPercent));
        this.monthlyStringParsableToIntegerPercent = monthlyStringParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyStringParsableToIntegerPercent, "monthly_string_parsable_to_integer_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getMonthlyStringParsableToFloatPercent() {
        return monthlyStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param monthlyStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setMonthlyStringParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec monthlyStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringParsableToFloatPercent, monthlyStringParsableToFloatPercent));
        this.monthlyStringParsableToFloatPercent = monthlyStringParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyStringParsableToFloatPercent, "monthly_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextValidDatesPercentCheckSpec getMonthlyStringValidDatesPercent() {
        return monthlyStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param monthlyStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setMonthlyStringValidDatesPercent(ColumnTextValidDatesPercentCheckSpec monthlyStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringValidDatesPercent, monthlyStringValidDatesPercent));
        this.monthlyStringValidDatesPercent = monthlyStringValidDatesPercent;
        propagateHierarchyIdToField(monthlyStringValidDatesPercent, "monthly_string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getMonthlyStringValidCountryCodePercent() {
        return monthlyStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param monthlyStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setMonthlyStringValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec monthlyStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringValidCountryCodePercent, monthlyStringValidCountryCodePercent));
        this.monthlyStringValidCountryCodePercent = monthlyStringValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyStringValidCountryCodePercent, "monthly_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getMonthlyStringValidCurrencyCodePercent() {
        return monthlyStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param monthlyStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setMonthlyStringValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec monthlyStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringValidCurrencyCodePercent, monthlyStringValidCurrencyCodePercent));
        this.monthlyStringValidCurrencyCodePercent = monthlyStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(monthlyStringValidCurrencyCodePercent, "monthly_string_valid_currency_code_percent");
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
