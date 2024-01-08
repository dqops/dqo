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
            put("monthly_partition_text_max_length", o -> o.monthlyPartitionTextMaxLength);
            put("monthly_partition_text_min_length", o -> o.monthlyPartitionTextMinLength);
            put("monthly_partition_text_mean_length", o -> o.monthlyPartitionTextMeanLength);
            put("monthly_partition_text_length_below_min_length", o -> o.monthlyPartitionTextLengthBelowMinLength);
            put("monthly_partition_text_length_below_min_length_percent", o -> o.monthlyPartitionTextLengthBelowMinLengthPercent);
            put("monthly_partition_text_length_above_max_length", o -> o.monthlyPartitionTextLengthAboveMaxLength);
            put("monthly_partition_text_length_above_max_length_percent", o -> o.monthlyPartitionTextLengthAboveMaxLengthPercent);
            put("monthly_partition_text_length_in_range_percent", o -> o.monthlyPartitionTextLengthInRangePercent);

            put("monthly_partition_text_parsable_to_boolean_percent", o -> o.monthlyPartitionTextParsableToBooleanPercent);
            put("monthly_partition_text_parsable_to_integer_percent", o -> o.monthlyPartitionTextParsableToIntegerPercent);
            put("monthly_partition_text_parsable_to_float_percent", o -> o.monthlyPartitionTextParsableToFloatPercent);
            put("monthly_partition_text_parsable_to_date_percent", o -> o.monthlyPartitionTextParsableToDatePercent);

            put("monthly_partition_text_surrounded_by_whitespace", o -> o.monthlyPartitionStringSurroundedByWhitespace);
            put("monthly_partition_text_surrounded_by_whitespace_percent", o -> o.monthlyPartitionStringSurroundedByWhitespacePercent);
            put("monthly_partition_text_valid_country_code_percent", o -> o.monthlyPartitionStringValidCountryCodePercent);
            put("monthly_partition_text_valid_currency_code_percent", o -> o.monthlyPartitionStringValidCurrencyCodePercent);
        }
    };


    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextMaxLengthCheckSpec monthlyPartitionTextMaxLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextMinLengthCheckSpec monthlyPartitionTextMinLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextMeanLengthCheckSpec monthlyPartitionTextMeanLength;

    @JsonPropertyDescription("The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthBelowMinLengthCheckSpec monthlyPartitionTextLengthBelowMinLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyPartitionTextLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthAboveMaxLengthCheckSpec monthlyPartitionTextLengthAboveMaxLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyPartitionTextLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthInRangePercentCheckSpec monthlyPartitionTextLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, " +
            "text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextParsableToBooleanPercentCheckSpec monthlyPartitionTextParsableToBooleanPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextParsableToIntegerPercentCheckSpec monthlyPartitionTextParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextParsableToFloatPercentCheckSpec monthlyPartitionTextParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextParsableToDatePercentCheckSpec monthlyPartitionTextParsableToDatePercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextSurroundedByWhitespaceCheckSpec monthlyPartitionStringSurroundedByWhitespace;

    @JsonPropertyDescription("Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyPartitionStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextValidCountryCodePercentCheckSpec monthlyPartitionStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextValidCurrencyCodePercentCheckSpec monthlyPartitionStringValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getMonthlyTextMaxLength() {
        return monthlyPartitionTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param monthlyPartitionTextMaxLength Maximum string length check.
     */
    public void setMonthlyTextMaxLength(ColumnTextMaxLengthCheckSpec monthlyPartitionTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextMaxLength, monthlyPartitionTextMaxLength));
        this.monthlyPartitionTextMaxLength = monthlyPartitionTextMaxLength;
        propagateHierarchyIdToField(monthlyPartitionTextMaxLength, "monthly_partition_text_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getMonthlyTextMinLength() {
        return monthlyPartitionTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param monthlyPartitionTextMinLength Minimum string length check.
     */
    public void setMonthlyTextMinLength(ColumnTextMinLengthCheckSpec monthlyPartitionTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextMinLength, monthlyPartitionTextMinLength));
        this.monthlyPartitionTextMinLength = monthlyPartitionTextMinLength;
        propagateHierarchyIdToField(monthlyPartitionTextMinLength, "monthly_partition_text_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getMonthlyTextMeanLength() {
        return monthlyPartitionTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param monthlyPartitionTextMeanLength Mean string length check.
     */
    public void setMonthlyTextMeanLength(ColumnTextMeanLengthCheckSpec monthlyPartitionTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextMeanLength, monthlyPartitionTextMeanLength));
        this.monthlyPartitionTextMeanLength = monthlyPartitionTextMeanLength;
        propagateHierarchyIdToField(monthlyPartitionTextMeanLength, "monthly_partition_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getMonthlyTextLengthBelowMinLength() {
        return monthlyPartitionTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param monthlyPartitionTextLengthBelowMinLength String length below min length count check.
     */
    public void setMonthlyTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec monthlyPartitionTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthBelowMinLength, monthlyPartitionTextLengthBelowMinLength));
        this.monthlyPartitionTextLengthBelowMinLength = monthlyPartitionTextLengthBelowMinLength;
        propagateHierarchyIdToField(monthlyPartitionTextLengthBelowMinLength, "monthly_partition_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getMonthlyTextLengthBelowMinLengthPercent() {
        return monthlyPartitionTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param monthlyPartitionTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setMonthlyTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyPartitionTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthBelowMinLengthPercent, monthlyPartitionTextLengthBelowMinLengthPercent));
        this.monthlyPartitionTextLengthBelowMinLengthPercent = monthlyPartitionTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(monthlyPartitionTextLengthBelowMinLengthPercent, "monthly_partition_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getMonthlyTextLengthAboveMaxLength() {
        return monthlyPartitionTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param monthlyPartitionTextLengthAboveMaxLength String length above max length count check.
     */
    public void setMonthlyTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec monthlyPartitionTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthAboveMaxLength, monthlyPartitionTextLengthAboveMaxLength));
        this.monthlyPartitionTextLengthAboveMaxLength = monthlyPartitionTextLengthAboveMaxLength;
        propagateHierarchyIdToField(monthlyPartitionTextLengthAboveMaxLength, "monthly_partition_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getMonthlyTextLengthAboveMaxLengthPercent() {
        return monthlyPartitionTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param monthlyPartitionTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setMonthlyTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyPartitionTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthAboveMaxLengthPercent, monthlyPartitionTextLengthAboveMaxLengthPercent));
        this.monthlyPartitionTextLengthAboveMaxLengthPercent = monthlyPartitionTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(monthlyPartitionTextLengthAboveMaxLengthPercent, "monthly_partition_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getMonthlyTextLengthInRangePercent() {
        return monthlyPartitionTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param monthlyPartitionTextLengthInRangePercent String length in range percent check.
     */
    public void setMonthlyTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec monthlyPartitionTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthInRangePercent, monthlyPartitionTextLengthInRangePercent));
        this.monthlyPartitionTextLengthInRangePercent = monthlyPartitionTextLengthInRangePercent;
        propagateHierarchyIdToField(monthlyPartitionTextLengthInRangePercent, "monthly_partition_text_length_in_range_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getMonthlyTextParsableToBooleanPercent() {
        return monthlyPartitionTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param monthlyPartitionTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setMonthlyTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec monthlyPartitionTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextParsableToBooleanPercent, monthlyPartitionTextParsableToBooleanPercent));
        this.monthlyPartitionTextParsableToBooleanPercent = monthlyPartitionTextParsableToBooleanPercent;
        propagateHierarchyIdToField(monthlyPartitionTextParsableToBooleanPercent, "monthly_partition_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getMonthlyTextParsableToIntegerPercent() {
        return monthlyPartitionTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param monthlyPartitionTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setMonthlyTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec monthlyPartitionTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextParsableToIntegerPercent, monthlyPartitionTextParsableToIntegerPercent));
        this.monthlyPartitionTextParsableToIntegerPercent = monthlyPartitionTextParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyPartitionTextParsableToIntegerPercent, "monthly_partition_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getMonthlyTextParsableToFloatPercent() {
        return monthlyPartitionTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param monthlyPartitionTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setMonthlyTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec monthlyPartitionTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextParsableToFloatPercent, monthlyPartitionTextParsableToFloatPercent));
        this.monthlyPartitionTextParsableToFloatPercent = monthlyPartitionTextParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyPartitionTextParsableToFloatPercent, "monthly_partition_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getMonthlyTextParsableToDatePercent() {
        return monthlyPartitionTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param monthlyPartitionTextParsableToDatePercent String valid dates percent check.
     */
    public void setMonthlyTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec monthlyPartitionTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextParsableToDatePercent, monthlyPartitionTextParsableToDatePercent));
        this.monthlyPartitionTextParsableToDatePercent = monthlyPartitionTextParsableToDatePercent;
        propagateHierarchyIdToField(monthlyPartitionTextParsableToDatePercent, "monthly_partition_text_parsable_to_date_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextSurroundedByWhitespaceCheckSpec getMonthlyStringSurroundedByWhitespace() {
        return monthlyPartitionStringSurroundedByWhitespace;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param monthlyPartitionStringSurroundedByWhitespace String surrounded by whitespace count check.
     */
    public void setMonthlyStringSurroundedByWhitespace(ColumnTextSurroundedByWhitespaceCheckSpec monthlyPartitionStringSurroundedByWhitespace) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringSurroundedByWhitespace, monthlyPartitionStringSurroundedByWhitespace));
        this.monthlyPartitionStringSurroundedByWhitespace = monthlyPartitionStringSurroundedByWhitespace;
        propagateHierarchyIdToField(monthlyPartitionStringSurroundedByWhitespace, "monthly_partition_text_surrounded_by_whitespace");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getMonthlyStringSurroundedByWhitespacePercent() {
        return monthlyPartitionStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param monthlyPartitionStringSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setMonthlyStringSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyPartitionStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringSurroundedByWhitespacePercent, monthlyPartitionStringSurroundedByWhitespacePercent));
        this.monthlyPartitionStringSurroundedByWhitespacePercent = monthlyPartitionStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyPartitionStringSurroundedByWhitespacePercent, "monthly_partition_text_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getMonthlyStringValidCountryCodePercent() {
        return monthlyPartitionStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param monthlyPartitionStringValidCountryCodePercent String valid country code percent check.
     */
    public void setMonthlyStringValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec monthlyPartitionStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValidCountryCodePercent, monthlyPartitionStringValidCountryCodePercent));
        this.monthlyPartitionStringValidCountryCodePercent = monthlyPartitionStringValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyPartitionStringValidCountryCodePercent, "monthly_partition_text_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getMonthlyStringValidCurrencyCodePercent() {
        return monthlyPartitionStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param monthlyPartitionStringValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setMonthlyStringValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec monthlyPartitionStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValidCurrencyCodePercent, monthlyPartitionStringValidCurrencyCodePercent));
        this.monthlyPartitionStringValidCurrencyCodePercent = monthlyPartitionStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(monthlyPartitionStringValidCurrencyCodePercent, "monthly_partition_text_valid_currency_code_percent");
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
