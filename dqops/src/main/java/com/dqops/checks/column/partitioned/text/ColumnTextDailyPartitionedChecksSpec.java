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

            put("daily_partition_text_surrounded_by_whitespace", o -> o.dailyPartitionTextSurroundedByWhitespace);
            put("daily_partition_text_surrounded_by_whitespace_percent", o -> o.dailyPartitionTextSurroundedByWhitespacePercent);
            put("daily_partition_text_valid_country_code_percent", o -> o.dailyPartitionTextValidCountryCodePercent);
            put("daily_partition_text_valid_currency_code_percent", o -> o.dailyPartitionTextValidCurrencyCodePercent);
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
    private ColumnTextSurroundedByWhitespaceCheckSpec dailyPartitionTextSurroundedByWhitespace;

    @JsonPropertyDescription("Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec dailyPartitionTextSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextValidCountryCodePercentCheckSpec dailyPartitionTextValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextValidCurrencyCodePercentCheckSpec dailyPartitionTextValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getDailyPartitionTextMaxLength() {
        return dailyPartitionTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param dailyPartitionTextMaxLength Maximum string length check.
     */
    public void setDailyPartitionTextMaxLength(ColumnTextMaxLengthCheckSpec dailyPartitionTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMaxLength, dailyPartitionTextMaxLength));
        this.dailyPartitionTextMaxLength = dailyPartitionTextMaxLength;
        propagateHierarchyIdToField(dailyPartitionTextMaxLength, "daily_partition_text_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getDailyPartitionTextMinLength() {
        return dailyPartitionTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param dailyPartitionTextMinLength Minimum string length check.
     */
    public void setDailyPartitionTextMinLength(ColumnTextMinLengthCheckSpec dailyPartitionTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMinLength, dailyPartitionTextMinLength));
        this.dailyPartitionTextMinLength = dailyPartitionTextMinLength;
        propagateHierarchyIdToField(dailyPartitionTextMinLength, "daily_partition_text_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getDailyPartitionTextMeanLength() {
        return dailyPartitionTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param dailyPartitionTextMeanLength Mean string length check.
     */
    public void setDailyPartitionTextMeanLength(ColumnTextMeanLengthCheckSpec dailyPartitionTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMeanLength, dailyPartitionTextMeanLength));
        this.dailyPartitionTextMeanLength = dailyPartitionTextMeanLength;
        propagateHierarchyIdToField(dailyPartitionTextMeanLength, "daily_partition_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getDailyPartitionTextLengthBelowMinLength() {
        return dailyPartitionTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param dailyPartitionTextLengthBelowMinLength String length below min length count check.
     */
    public void setDailyPartitionTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec dailyPartitionTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthBelowMinLength, dailyPartitionTextLengthBelowMinLength));
        this.dailyPartitionTextLengthBelowMinLength = dailyPartitionTextLengthBelowMinLength;
        propagateHierarchyIdToField(dailyPartitionTextLengthBelowMinLength, "daily_partition_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getDailyPartitionTextLengthBelowMinLengthPercent() {
        return dailyPartitionTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param dailyPartitionTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setDailyPartitionTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec dailyPartitionTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthBelowMinLengthPercent, dailyPartitionTextLengthBelowMinLengthPercent));
        this.dailyPartitionTextLengthBelowMinLengthPercent = dailyPartitionTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(dailyPartitionTextLengthBelowMinLengthPercent, "daily_partition_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getDailyPartitionTextLengthAboveMaxLength() {
        return dailyPartitionTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param dailyPartitionTextLengthAboveMaxLength String length above max length count check.
     */
    public void setDailyPartitionTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec dailyPartitionTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthAboveMaxLength, dailyPartitionTextLengthAboveMaxLength));
        this.dailyPartitionTextLengthAboveMaxLength = dailyPartitionTextLengthAboveMaxLength;
        propagateHierarchyIdToField(dailyPartitionTextLengthAboveMaxLength, "daily_partition_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getDailyPartitionTextLengthAboveMaxLengthPercent() {
        return dailyPartitionTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param dailyPartitionTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setDailyPartitionTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec dailyPartitionTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthAboveMaxLengthPercent, dailyPartitionTextLengthAboveMaxLengthPercent));
        this.dailyPartitionTextLengthAboveMaxLengthPercent = dailyPartitionTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(dailyPartitionTextLengthAboveMaxLengthPercent, "daily_partition_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getDailyPartitionTextLengthInRangePercent() {
        return dailyPartitionTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param dailyPartitionTextLengthInRangePercent String length in range percent check.
     */
    public void setDailyPartitionTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec dailyPartitionTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthInRangePercent, dailyPartitionTextLengthInRangePercent));
        this.dailyPartitionTextLengthInRangePercent = dailyPartitionTextLengthInRangePercent;
        propagateHierarchyIdToField(dailyPartitionTextLengthInRangePercent, "daily_partition_text_length_in_range_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getDailyPartitionTextParsableToBooleanPercent() {
        return dailyPartitionTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param dailyPartitionTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setDailyPartitionTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec dailyPartitionTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToBooleanPercent, dailyPartitionTextParsableToBooleanPercent));
        this.dailyPartitionTextParsableToBooleanPercent = dailyPartitionTextParsableToBooleanPercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToBooleanPercent, "daily_partition_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getDailyPartitionTextParsableToIntegerPercent() {
        return dailyPartitionTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param dailyPartitionTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setDailyPartitionTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec dailyPartitionTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToIntegerPercent, dailyPartitionTextParsableToIntegerPercent));
        this.dailyPartitionTextParsableToIntegerPercent = dailyPartitionTextParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToIntegerPercent, "daily_partition_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getDailyPartitionTextParsableToFloatPercent() {
        return dailyPartitionTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param dailyPartitionTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setDailyPartitionTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec dailyPartitionTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToFloatPercent, dailyPartitionTextParsableToFloatPercent));
        this.dailyPartitionTextParsableToFloatPercent = dailyPartitionTextParsableToFloatPercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToFloatPercent, "daily_partition_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getDailyPartitionTextParsableToDatePercent() {
        return dailyPartitionTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param dailyPartitionTextParsableToDatePercent String valid dates percent check.
     */
    public void setDailyPartitionTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec dailyPartitionTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToDatePercent, dailyPartitionTextParsableToDatePercent));
        this.dailyPartitionTextParsableToDatePercent = dailyPartitionTextParsableToDatePercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToDatePercent, "daily_partition_text_parsable_to_date_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextSurroundedByWhitespaceCheckSpec getDailyPartitionTextSurroundedByWhitespace() {
        return dailyPartitionTextSurroundedByWhitespace;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param dailyPartitionTextSurroundedByWhitespace String surrounded by whitespace count check.
     */
    public void setDailyPartitionTextSurroundedByWhitespace(ColumnTextSurroundedByWhitespaceCheckSpec dailyPartitionTextSurroundedByWhitespace) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextSurroundedByWhitespace, dailyPartitionTextSurroundedByWhitespace));
        this.dailyPartitionTextSurroundedByWhitespace = dailyPartitionTextSurroundedByWhitespace;
        propagateHierarchyIdToField(dailyPartitionTextSurroundedByWhitespace, "daily_partition_text_surrounded_by_whitespace");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getDailyPartitionTextSurroundedByWhitespacePercent() {
        return dailyPartitionTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param dailyPartitionTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setDailyPartitionTextSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec dailyPartitionTextSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextSurroundedByWhitespacePercent, dailyPartitionTextSurroundedByWhitespacePercent));
        this.dailyPartitionTextSurroundedByWhitespacePercent = dailyPartitionTextSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyPartitionTextSurroundedByWhitespacePercent, "daily_partition_text_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getDailyPartitionTextValidCountryCodePercent() {
        return dailyPartitionTextValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param dailyPartitionTextValidCountryCodePercent String valid country code percent check.
     */
    public void setDailyPartitionTextValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec dailyPartitionTextValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextValidCountryCodePercent, dailyPartitionTextValidCountryCodePercent));
        this.dailyPartitionTextValidCountryCodePercent = dailyPartitionTextValidCountryCodePercent;
        propagateHierarchyIdToField(dailyPartitionTextValidCountryCodePercent, "daily_partition_text_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getDailyPartitionTextValidCurrencyCodePercent() {
        return dailyPartitionTextValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param dailyPartitionTextValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setDailyPartitionTextValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec dailyPartitionTextValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextValidCurrencyCodePercent, dailyPartitionTextValidCurrencyCodePercent));
        this.dailyPartitionTextValidCurrencyCodePercent = dailyPartitionTextValidCurrencyCodePercent;
        propagateHierarchyIdToField(dailyPartitionTextValidCurrencyCodePercent, "daily_partition_text_valid_currency_code_percent");
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
