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
package com.dqops.checks.column.partitioned.text;

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
 * Container of text data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_string_max_length", o -> o.monthlyPartitionStringMaxLength);
            put("monthly_partition_string_min_length", o -> o.monthlyPartitionStringMinLength);
            put("monthly_partition_string_mean_length", o -> o.monthlyPartitionStringMeanLength);
            put("monthly_partition_string_length_below_min_length_count", o -> o.monthlyPartitionStringLengthBelowMinLengthCount);
            put("monthly_partition_string_length_below_min_length_percent", o -> o.monthlyPartitionStringLengthBelowMinLengthPercent);
            put("monthly_partition_string_length_above_max_length_count", o -> o.monthlyPartitionStringLengthAboveMaxLengthCount);
            put("monthly_partition_string_length_above_max_length_percent", o -> o.monthlyPartitionStringLengthAboveMaxLengthPercent);
            put("monthly_partition_string_length_in_range_percent", o -> o.monthlyPartitionStringLengthInRangePercent);

            put("monthly_partition_string_surrounded_by_whitespace_count", o -> o.monthlyPartitionStringSurroundedByWhitespaceCount);
            put("monthly_partition_string_surrounded_by_whitespace_percent", o -> o.monthlyPartitionStringSurroundedByWhitespacePercent);
            put("monthly_partition_string_boolean_placeholder_percent", o -> o.monthlyPartitionStringBooleanPlaceholderPercent);
            put("monthly_partition_string_parsable_to_integer_percent", o -> o.monthlyPartitionStringParsableToIntegerPercent);
            put("monthly_partition_string_parsable_to_float_percent", o -> o.monthlyPartitionStringParsableToFloatPercent);
            
            put("monthly_partition_string_valid_dates_percent", o -> o.monthlyPartitionStringValidDatesPercent);
            put("monthly_partition_string_valid_country_code_percent", o -> o.monthlyPartitionStringValidCountryCodePercent);
            put("monthly_partition_string_valid_currency_code_percent", o -> o.monthlyPartitionStringValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextMaxLengthCheckSpec monthlyPartitionStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextMinLengthCheckSpec monthlyPartitionStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextMeanLengthCheckSpec monthlyPartitionStringMeanLength;

    @JsonPropertyDescription("The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextLengthBelowMinLengthCountCheckSpec monthlyPartitionStringLengthBelowMinLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyPartitionStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextLengthAboveMaxLengthCountCheckSpec monthlyPartitionStringLengthAboveMaxLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyPartitionStringLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextLengthInRangePercentCheckSpec monthlyPartitionStringLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextSurroundedByWhitespaceCountCheckSpec monthlyPartitionStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyPartitionStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextBooleanPlaceholderPercentCheckSpec monthlyPartitionStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextParsableToIntegerPercentCheckSpec monthlyPartitionStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextParsableToFloatPercentCheckSpec monthlyPartitionStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextValidDatesPercentCheckSpec monthlyPartitionStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextValidCountryCodePercentCheckSpec monthlyPartitionStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTextValidCurrencyCodePercentCheckSpec monthlyPartitionStringValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getMonthlyPartitionStringMaxLength() {
        return monthlyPartitionStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length below check.
     * @param monthlyPartitionStringMaxLength Maximum string length below check.
     */
    public void setMonthlyPartitionStringMaxLength(ColumnTextMaxLengthCheckSpec monthlyPartitionStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringMaxLength, monthlyPartitionStringMaxLength));
        this.monthlyPartitionStringMaxLength = monthlyPartitionStringMaxLength;
        propagateHierarchyIdToField(monthlyPartitionStringMaxLength, "monthly_partition_string_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getMonthlyPartitionStringMinLength() {
        return monthlyPartitionStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length above check.
     * @param monthlyPartitionStringMinLength Minimum string length above check.
     */
    public void setMonthlyPartitionStringMinLength(ColumnTextMinLengthCheckSpec monthlyPartitionStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringMinLength, monthlyPartitionStringMinLength));
        this.monthlyPartitionStringMinLength = monthlyPartitionStringMinLength;
        propagateHierarchyIdToField(monthlyPartitionStringMinLength, "monthly_partition_string_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getMonthlyPartitionStringMeanLength() {
        return monthlyPartitionStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param monthlyPartitionStringMeanLength Mean string length between check.
     */
    public void setMonthlyPartitionStringMeanLength(ColumnTextMeanLengthCheckSpec monthlyPartitionStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringMeanLength, monthlyPartitionStringMeanLength));
        this.monthlyPartitionStringMeanLength = monthlyPartitionStringMeanLength;
        propagateHierarchyIdToField(monthlyPartitionStringMeanLength, "monthly_partition_string_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCountCheckSpec getMonthlyPartitionStringLengthBelowMinLengthCount() {
        return monthlyPartitionStringLengthBelowMinLengthCount;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param monthlyPartitionStringLengthBelowMinLengthCount String length below min length count check.
     */
    public void setMonthlyPartitionStringLengthBelowMinLengthCount(ColumnTextLengthBelowMinLengthCountCheckSpec monthlyPartitionStringLengthBelowMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthBelowMinLengthCount, monthlyPartitionStringLengthBelowMinLengthCount));
        this.monthlyPartitionStringLengthBelowMinLengthCount = monthlyPartitionStringLengthBelowMinLengthCount;
        propagateHierarchyIdToField(monthlyPartitionStringLengthBelowMinLengthCount, "monthly_partition_string_length_below_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return Mean string length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getMonthlyPartitionStringLengthBelowMinLengthPercent() {
        return monthlyPartitionStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param monthlyPartitionStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setMonthlyPartitionStringLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyPartitionStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthBelowMinLengthPercent, monthlyPartitionStringLengthBelowMinLengthPercent));
        this.monthlyPartitionStringLengthBelowMinLengthPercent = monthlyPartitionStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(monthlyPartitionStringLengthBelowMinLengthPercent, "monthly_partition_string_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return Mean string length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCountCheckSpec getMonthlyPartitionStringLengthAboveMaxLengthCount() {
        return monthlyPartitionStringLengthAboveMaxLengthCount;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param monthlyPartitionStringLengthAboveMaxLengthCount String length above max length count check.
     */
    public void setMonthlyPartitionStringLengthAboveMaxLengthCount(ColumnTextLengthAboveMaxLengthCountCheckSpec monthlyPartitionStringLengthAboveMaxLengthCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthAboveMaxLengthCount, monthlyPartitionStringLengthAboveMaxLengthCount));
        this.monthlyPartitionStringLengthAboveMaxLengthCount = monthlyPartitionStringLengthAboveMaxLengthCount;
        propagateHierarchyIdToField(monthlyPartitionStringLengthAboveMaxLengthCount, "monthly_partition_string_length_above_max_length_count");
    }

    /**
     * Returns a string length above max length percent check.
     * @return Mean string length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getMonthlyPartitionStringLengthAboveMaxLengthPercent() {
        return monthlyPartitionStringLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param monthlyPartitionStringLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setMonthlyPartitionStringLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyPartitionStringLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthAboveMaxLengthPercent, monthlyPartitionStringLengthAboveMaxLengthPercent));
        this.monthlyPartitionStringLengthAboveMaxLengthPercent = monthlyPartitionStringLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(monthlyPartitionStringLengthAboveMaxLengthPercent, "monthly_partition_string_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return Mean string length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getMonthlyPartitionStringLengthInRangePercent() {
        return monthlyPartitionStringLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param monthlyPartitionStringLengthInRangePercent String length in range percent check.
     */
    public void setMonthlyPartitionStringLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec monthlyPartitionStringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringLengthInRangePercent, monthlyPartitionStringLengthInRangePercent));
        this.monthlyPartitionStringLengthInRangePercent = monthlyPartitionStringLengthInRangePercent;
        propagateHierarchyIdToField(monthlyPartitionStringLengthInRangePercent, "monthly_partition_string_length_in_range_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     *
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextSurroundedByWhitespaceCountCheckSpec getMonthlyPartitionStringSurroundedByWhitespaceCount() {
        return monthlyPartitionStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     *
     * @param monthlyPartitionStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setMonthlyPartitionStringSurroundedByWhitespaceCount(ColumnTextSurroundedByWhitespaceCountCheckSpec monthlyPartitionStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringSurroundedByWhitespaceCount, monthlyPartitionStringSurroundedByWhitespaceCount));
        this.monthlyPartitionStringSurroundedByWhitespaceCount = monthlyPartitionStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(monthlyPartitionStringSurroundedByWhitespaceCount, "monthly_partition_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     *
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getMonthlyPartitionStringSurroundedByWhitespacePercent() {
        return monthlyPartitionStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     *
     * @param monthlyPartitionStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setMonthlyPartitionStringSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyPartitionStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringSurroundedByWhitespacePercent, monthlyPartitionStringSurroundedByWhitespacePercent));
        this.monthlyPartitionStringSurroundedByWhitespacePercent = monthlyPartitionStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyPartitionStringSurroundedByWhitespacePercent, "monthly_partition_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnTextBooleanPlaceholderPercentCheckSpec getMonthlyPartitionStringBooleanPlaceholderPercent() {
        return monthlyPartitionStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param monthlyPartitionStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setMonthlyPartitionStringBooleanPlaceholderPercent(ColumnTextBooleanPlaceholderPercentCheckSpec monthlyPartitionStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringBooleanPlaceholderPercent, monthlyPartitionStringBooleanPlaceholderPercent));
        this.monthlyPartitionStringBooleanPlaceholderPercent = monthlyPartitionStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(monthlyPartitionStringBooleanPlaceholderPercent, "monthly_partition_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent  check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getMonthlyPartitionStringParsableToIntegerPercent() {
        return monthlyPartitionStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param monthlyPartitionStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setMonthlyPartitionStringParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec monthlyPartitionStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringParsableToIntegerPercent, monthlyPartitionStringParsableToIntegerPercent));
        this.monthlyPartitionStringParsableToIntegerPercent = monthlyPartitionStringParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyPartitionStringParsableToIntegerPercent, "monthly_partition_string_parsable_to_integer_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent  check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getMonthlyPartitionStringParsableToFloatPercent() {
        return monthlyPartitionStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param monthlyPartitionStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setMonthlyPartitionStringParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec monthlyPartitionStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringParsableToFloatPercent, monthlyPartitionStringParsableToFloatPercent));
        this.monthlyPartitionStringParsableToFloatPercent = monthlyPartitionStringParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyPartitionStringParsableToFloatPercent, "monthly_partition_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextValidDatesPercentCheckSpec getMonthlyPartitionStringValidDatesPercent() {
        return monthlyPartitionStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param monthlyPartitionStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setMonthlyPartitionStringValidDatesPercent(ColumnTextValidDatesPercentCheckSpec monthlyPartitionStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValidDatesPercent, monthlyPartitionStringValidDatesPercent));
        this.monthlyPartitionStringValidDatesPercent = monthlyPartitionStringValidDatesPercent;
        propagateHierarchyIdToField(monthlyPartitionStringValidDatesPercent, "monthly_partition_string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent  check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getMonthlyPartitionStringValidCountryCodePercent() {
        return monthlyPartitionStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param monthlyPartitionStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setMonthlyPartitionStringValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec monthlyPartitionStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValidCountryCodePercent, monthlyPartitionStringValidCountryCodePercent));
        this.monthlyPartitionStringValidCountryCodePercent = monthlyPartitionStringValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyPartitionStringValidCountryCodePercent, "monthly_partition_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent  check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getMonthlyPartitionStringValidCurrencyCodePercent() {
        return monthlyPartitionStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param monthlyPartitionStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setMonthlyPartitionStringValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec monthlyPartitionStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValidCurrencyCodePercent, monthlyPartitionStringValidCurrencyCodePercent));
        this.monthlyPartitionStringValidCurrencyCodePercent = monthlyPartitionStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(monthlyPartitionStringValidCurrencyCodePercent, "monthly_partition_string_valid_currency_code_percent");
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
        return CheckType.partitioned;
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
