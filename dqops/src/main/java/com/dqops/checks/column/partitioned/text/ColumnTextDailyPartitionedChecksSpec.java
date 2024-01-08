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
 * Container of text data quality partitioned checks on a column level that are checking at a dailyPartition level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_text_max_length", o -> o.dailyPartitionTextMaxLength);
            put("daily_partition_text_min_length", o -> o.dailyPartitionTextMinLength);
            put("daily_partition_text_mean_length", o -> o.dailyPartitionTextMeanLength);
            put("daily_partition_text_length_below_min_length", o -> o.dailyPartitionTextLengthBelowMinLength);
            put("daily_partition_text_length_below_min_length_percent", o -> o.dailyPartitionTextLengthBelowMinLengthPercent);
            put("daily_partition_text_length_above_max_length", o -> o.dailyPartitionTextLengthAboveMaxLength);
            put("daily_partition_text_length_above_max_length_percent", o -> o.dailyPartitionTextLengthAboveMaxLengthPercent);
            put("daily_partition_text_length_in_range_percent", o -> o.dailyPartitionTextLengthInRangePercent);

            put("daily_partition_text_parsable_to_boolean_percent", o -> o.dailyPartitionTextParsableToBooleanPercent);
            put("daily_partition_text_parsable_to_integer_percent", o -> o.dailyPartitionTextParsableToIntegerPercent);
            put("daily_partition_text_parsable_to_float_percent", o -> o.dailyPartitionTextParsableToFloatPercent);
            put("daily_partition_text_parsable_to_date_percent", o -> o.dailyPartitionTextParsableToDatePercent);

            put("daily_partition_text_surrounded_by_whitespace", o -> o.dailyPartitionStringSurroundedByWhitespace);
            put("daily_partition_text_surrounded_by_whitespace_percent", o -> o.dailyPartitionStringSurroundedByWhitespacePercent);
            put("daily_partition_text_valid_country_code_percent", o -> o.dailyPartitionStringValidCountryCodePercent);
            put("daily_partition_text_valid_currency_code_percent", o -> o.dailyPartitionStringValidCurrencyCodePercent);
        }
    };


    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextMaxLengthCheckSpec dailyPartitionTextMaxLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextMinLengthCheckSpec dailyPartitionTextMinLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextMeanLengthCheckSpec dailyPartitionTextMeanLength;

    @JsonPropertyDescription("The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthBelowMinLengthCheckSpec dailyPartitionTextLengthBelowMinLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec dailyPartitionTextLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthAboveMaxLengthCheckSpec dailyPartitionTextLengthAboveMaxLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec dailyPartitionTextLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthInRangePercentCheckSpec dailyPartitionTextLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, " +
            "text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextParsableToBooleanPercentCheckSpec dailyPartitionTextParsableToBooleanPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextParsableToIntegerPercentCheckSpec dailyPartitionTextParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextParsableToFloatPercentCheckSpec dailyPartitionTextParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextParsableToDatePercentCheckSpec dailyPartitionTextParsableToDatePercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextSurroundedByWhitespaceCheckSpec dailyPartitionStringSurroundedByWhitespace;

    @JsonPropertyDescription("Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec dailyPartitionStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextValidCountryCodePercentCheckSpec dailyPartitionStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextValidCurrencyCodePercentCheckSpec dailyPartitionStringValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getDailyTextMaxLength() {
        return dailyPartitionTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param dailyPartitionTextMaxLength Maximum string length check.
     */
    public void setDailyTextMaxLength(ColumnTextMaxLengthCheckSpec dailyPartitionTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMaxLength, dailyPartitionTextMaxLength));
        this.dailyPartitionTextMaxLength = dailyPartitionTextMaxLength;
        propagateHierarchyIdToField(dailyPartitionTextMaxLength, "daily_partition_text_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getDailyTextMinLength() {
        return dailyPartitionTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param dailyPartitionTextMinLength Minimum string length check.
     */
    public void setDailyTextMinLength(ColumnTextMinLengthCheckSpec dailyPartitionTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMinLength, dailyPartitionTextMinLength));
        this.dailyPartitionTextMinLength = dailyPartitionTextMinLength;
        propagateHierarchyIdToField(dailyPartitionTextMinLength, "daily_partition_text_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getDailyTextMeanLength() {
        return dailyPartitionTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param dailyPartitionTextMeanLength Mean string length check.
     */
    public void setDailyTextMeanLength(ColumnTextMeanLengthCheckSpec dailyPartitionTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMeanLength, dailyPartitionTextMeanLength));
        this.dailyPartitionTextMeanLength = dailyPartitionTextMeanLength;
        propagateHierarchyIdToField(dailyPartitionTextMeanLength, "daily_partition_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getDailyTextLengthBelowMinLength() {
        return dailyPartitionTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param dailyPartitionTextLengthBelowMinLength String length below min length count check.
     */
    public void setDailyTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec dailyPartitionTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthBelowMinLength, dailyPartitionTextLengthBelowMinLength));
        this.dailyPartitionTextLengthBelowMinLength = dailyPartitionTextLengthBelowMinLength;
        propagateHierarchyIdToField(dailyPartitionTextLengthBelowMinLength, "daily_partition_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getDailyTextLengthBelowMinLengthPercent() {
        return dailyPartitionTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param dailyPartitionTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setDailyTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec dailyPartitionTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthBelowMinLengthPercent, dailyPartitionTextLengthBelowMinLengthPercent));
        this.dailyPartitionTextLengthBelowMinLengthPercent = dailyPartitionTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(dailyPartitionTextLengthBelowMinLengthPercent, "daily_partition_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getDailyTextLengthAboveMaxLength() {
        return dailyPartitionTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param dailyPartitionTextLengthAboveMaxLength String length above max length count check.
     */
    public void setDailyTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec dailyPartitionTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthAboveMaxLength, dailyPartitionTextLengthAboveMaxLength));
        this.dailyPartitionTextLengthAboveMaxLength = dailyPartitionTextLengthAboveMaxLength;
        propagateHierarchyIdToField(dailyPartitionTextLengthAboveMaxLength, "daily_partition_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getDailyTextLengthAboveMaxLengthPercent() {
        return dailyPartitionTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param dailyPartitionTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setDailyTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec dailyPartitionTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthAboveMaxLengthPercent, dailyPartitionTextLengthAboveMaxLengthPercent));
        this.dailyPartitionTextLengthAboveMaxLengthPercent = dailyPartitionTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(dailyPartitionTextLengthAboveMaxLengthPercent, "daily_partition_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getDailyTextLengthInRangePercent() {
        return dailyPartitionTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param dailyPartitionTextLengthInRangePercent String length in range percent check.
     */
    public void setDailyTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec dailyPartitionTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthInRangePercent, dailyPartitionTextLengthInRangePercent));
        this.dailyPartitionTextLengthInRangePercent = dailyPartitionTextLengthInRangePercent;
        propagateHierarchyIdToField(dailyPartitionTextLengthInRangePercent, "daily_partition_text_length_in_range_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getDailyTextParsableToBooleanPercent() {
        return dailyPartitionTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param dailyPartitionTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setDailyTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec dailyPartitionTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToBooleanPercent, dailyPartitionTextParsableToBooleanPercent));
        this.dailyPartitionTextParsableToBooleanPercent = dailyPartitionTextParsableToBooleanPercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToBooleanPercent, "daily_partition_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getDailyTextParsableToIntegerPercent() {
        return dailyPartitionTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param dailyPartitionTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setDailyTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec dailyPartitionTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToIntegerPercent, dailyPartitionTextParsableToIntegerPercent));
        this.dailyPartitionTextParsableToIntegerPercent = dailyPartitionTextParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToIntegerPercent, "daily_partition_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getDailyTextParsableToFloatPercent() {
        return dailyPartitionTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param dailyPartitionTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setDailyTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec dailyPartitionTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToFloatPercent, dailyPartitionTextParsableToFloatPercent));
        this.dailyPartitionTextParsableToFloatPercent = dailyPartitionTextParsableToFloatPercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToFloatPercent, "daily_partition_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getDailyTextParsableToDatePercent() {
        return dailyPartitionTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param dailyPartitionTextParsableToDatePercent String valid dates percent check.
     */
    public void setDailyTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec dailyPartitionTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToDatePercent, dailyPartitionTextParsableToDatePercent));
        this.dailyPartitionTextParsableToDatePercent = dailyPartitionTextParsableToDatePercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToDatePercent, "daily_partition_text_parsable_to_date_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextSurroundedByWhitespaceCheckSpec getDailyStringSurroundedByWhitespace() {
        return dailyPartitionStringSurroundedByWhitespace;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param dailyPartitionStringSurroundedByWhitespace String surrounded by whitespace count check.
     */
    public void setDailyStringSurroundedByWhitespace(ColumnTextSurroundedByWhitespaceCheckSpec dailyPartitionStringSurroundedByWhitespace) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringSurroundedByWhitespace, dailyPartitionStringSurroundedByWhitespace));
        this.dailyPartitionStringSurroundedByWhitespace = dailyPartitionStringSurroundedByWhitespace;
        propagateHierarchyIdToField(dailyPartitionStringSurroundedByWhitespace, "daily_partition_text_surrounded_by_whitespace");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getDailyStringSurroundedByWhitespacePercent() {
        return dailyPartitionStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param dailyPartitionStringSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setDailyStringSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec dailyPartitionStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringSurroundedByWhitespacePercent, dailyPartitionStringSurroundedByWhitespacePercent));
        this.dailyPartitionStringSurroundedByWhitespacePercent = dailyPartitionStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyPartitionStringSurroundedByWhitespacePercent, "daily_partition_text_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getDailyStringValidCountryCodePercent() {
        return dailyPartitionStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param dailyPartitionStringValidCountryCodePercent String valid country code percent check.
     */
    public void setDailyStringValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec dailyPartitionStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidCountryCodePercent, dailyPartitionStringValidCountryCodePercent));
        this.dailyPartitionStringValidCountryCodePercent = dailyPartitionStringValidCountryCodePercent;
        propagateHierarchyIdToField(dailyPartitionStringValidCountryCodePercent, "daily_partition_text_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getDailyStringValidCurrencyCodePercent() {
        return dailyPartitionStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param dailyPartitionStringValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setDailyStringValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec dailyPartitionStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidCurrencyCodePercent, dailyPartitionStringValidCurrencyCodePercent));
        this.dailyPartitionStringValidCurrencyCodePercent = dailyPartitionStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(dailyPartitionStringValidCurrencyCodePercent, "daily_partition_text_valid_currency_code_percent");
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
        return CheckTimeScale.daily;
    }
}
