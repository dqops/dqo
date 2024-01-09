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
 * Container of text data quality monitoring checks on a column level that are monitoring tables at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_text_max_length", o -> o.dailyTextMaxLength);
            put("daily_text_min_length", o -> o.dailyTextMinLength);
            put("daily_text_mean_length", o -> o.dailyTextMeanLength);
            put("daily_text_length_below_min_length", o -> o.dailyTextLengthBelowMinLength);
            put("daily_text_length_below_min_length_percent", o -> o.dailyTextLengthBelowMinLengthPercent);
            put("daily_text_length_above_max_length", o -> o.dailyTextLengthAboveMaxLength);
            put("daily_text_length_above_max_length_percent", o -> o.dailyTextLengthAboveMaxLengthPercent);
            put("daily_text_length_in_range_percent", o -> o.dailyTextLengthInRangePercent);

            put("daily_text_parsable_to_boolean_percent", o -> o.dailyTextParsableToBooleanPercent);
            put("daily_text_parsable_to_integer_percent", o -> o.dailyTextParsableToIntegerPercent);
            put("daily_text_parsable_to_float_percent", o -> o.dailyTextParsableToFloatPercent);
            put("daily_text_parsable_to_date_percent", o -> o.dailyTextParsableToDatePercent);

            put("daily_text_surrounded_by_whitespace", o -> o.dailyTextSurroundedByWhitespace);
            put("daily_text_surrounded_by_whitespace_percent", o -> o.dailyTextSurroundedByWhitespacePercent);
            put("daily_text_valid_country_code_percent", o -> o.dailyTextValidCountryCodePercent);
            put("daily_text_valid_currency_code_percent", o -> o.dailyTextValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextMaxLengthCheckSpec dailyTextMaxLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextMinLengthCheckSpec dailyTextMinLength;

    @JsonPropertyDescription("Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextMeanLengthCheckSpec dailyTextMeanLength;

    @JsonPropertyDescription("The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthCheckSpec dailyTextLengthBelowMinLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec dailyTextLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthCheckSpec dailyTextLengthAboveMaxLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec dailyTextLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthInRangePercentCheckSpec dailyTextLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, " +
            "text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextParsableToBooleanPercentCheckSpec dailyTextParsableToBooleanPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextParsableToIntegerPercentCheckSpec dailyTextParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextParsableToFloatPercentCheckSpec dailyTextParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextParsableToDatePercentCheckSpec dailyTextParsableToDatePercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextSurroundedByWhitespaceCheckSpec dailyTextSurroundedByWhitespace;

    @JsonPropertyDescription("Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec dailyTextSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextValidCountryCodePercentCheckSpec dailyTextValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextValidCurrencyCodePercentCheckSpec dailyTextValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getDailyTextMaxLength() {
        return dailyTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param dailyTextMaxLength Maximum string length check.
     */
    public void setDailyTextMaxLength(ColumnTextMaxLengthCheckSpec dailyTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextMaxLength, dailyTextMaxLength));
        this.dailyTextMaxLength = dailyTextMaxLength;
        propagateHierarchyIdToField(dailyTextMaxLength, "daily_text_max_length");
    }

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getDailyTextMinLength() {
        return dailyTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param dailyTextMinLength Minimum string length check.
     */
    public void setDailyTextMinLength(ColumnTextMinLengthCheckSpec dailyTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextMinLength, dailyTextMinLength));
        this.dailyTextMinLength = dailyTextMinLength;
        propagateHierarchyIdToField(dailyTextMinLength, "daily_text_min_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getDailyTextMeanLength() {
        return dailyTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param dailyTextMeanLength Mean string length check.
     */
    public void setDailyTextMeanLength(ColumnTextMeanLengthCheckSpec dailyTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextMeanLength, dailyTextMeanLength));
        this.dailyTextMeanLength = dailyTextMeanLength;
        propagateHierarchyIdToField(dailyTextMeanLength, "daily_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getDailyTextLengthBelowMinLength() {
        return dailyTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param dailyTextLengthBelowMinLength String length below min length count check.
     */
    public void setDailyTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec dailyTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthBelowMinLength, dailyTextLengthBelowMinLength));
        this.dailyTextLengthBelowMinLength = dailyTextLengthBelowMinLength;
        propagateHierarchyIdToField(dailyTextLengthBelowMinLength, "daily_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getDailyTextLengthBelowMinLengthPercent() {
        return dailyTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param dailyTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setDailyTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec dailyTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthBelowMinLengthPercent, dailyTextLengthBelowMinLengthPercent));
        this.dailyTextLengthBelowMinLengthPercent = dailyTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(dailyTextLengthBelowMinLengthPercent, "daily_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getDailyTextLengthAboveMaxLength() {
        return dailyTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param dailyTextLengthAboveMaxLength String length above max length count check.
     */
    public void setDailyTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec dailyTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthAboveMaxLength, dailyTextLengthAboveMaxLength));
        this.dailyTextLengthAboveMaxLength = dailyTextLengthAboveMaxLength;
        propagateHierarchyIdToField(dailyTextLengthAboveMaxLength, "daily_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getDailyTextLengthAboveMaxLengthPercent() {
        return dailyTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param dailyTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setDailyTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec dailyTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthAboveMaxLengthPercent, dailyTextLengthAboveMaxLengthPercent));
        this.dailyTextLengthAboveMaxLengthPercent = dailyTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(dailyTextLengthAboveMaxLengthPercent, "daily_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getDailyTextLengthInRangePercent() {
        return dailyTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param dailyTextLengthInRangePercent String length in range percent check.
     */
    public void setDailyTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec dailyTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthInRangePercent, dailyTextLengthInRangePercent));
        this.dailyTextLengthInRangePercent = dailyTextLengthInRangePercent;
        propagateHierarchyIdToField(dailyTextLengthInRangePercent, "daily_text_length_in_range_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getDailyTextParsableToBooleanPercent() {
        return dailyTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param dailyTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setDailyTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec dailyTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextParsableToBooleanPercent, dailyTextParsableToBooleanPercent));
        this.dailyTextParsableToBooleanPercent = dailyTextParsableToBooleanPercent;
        propagateHierarchyIdToField(dailyTextParsableToBooleanPercent, "daily_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getDailyTextParsableToIntegerPercent() {
        return dailyTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param dailyTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setDailyTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec dailyTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextParsableToIntegerPercent, dailyTextParsableToIntegerPercent));
        this.dailyTextParsableToIntegerPercent = dailyTextParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyTextParsableToIntegerPercent, "daily_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getDailyTextParsableToFloatPercent() {
        return dailyTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param dailyTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setDailyTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec dailyTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextParsableToFloatPercent, dailyTextParsableToFloatPercent));
        this.dailyTextParsableToFloatPercent = dailyTextParsableToFloatPercent;
        propagateHierarchyIdToField(dailyTextParsableToFloatPercent, "daily_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getDailyTextParsableToDatePercent() {
        return dailyTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param dailyTextParsableToDatePercent String valid dates percent check.
     */
    public void setDailyTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec dailyTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextParsableToDatePercent, dailyTextParsableToDatePercent));
        this.dailyTextParsableToDatePercent = dailyTextParsableToDatePercent;
        propagateHierarchyIdToField(dailyTextParsableToDatePercent, "daily_text_parsable_to_date_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextSurroundedByWhitespaceCheckSpec getDailyTextSurroundedByWhitespace() {
        return dailyTextSurroundedByWhitespace;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param dailyTextSurroundedByWhitespace String surrounded by whitespace count check.
     */
    public void setDailyTextSurroundedByWhitespace(ColumnTextSurroundedByWhitespaceCheckSpec dailyTextSurroundedByWhitespace) {
        this.setDirtyIf(!Objects.equals(this.dailyTextSurroundedByWhitespace, dailyTextSurroundedByWhitespace));
        this.dailyTextSurroundedByWhitespace = dailyTextSurroundedByWhitespace;
        propagateHierarchyIdToField(dailyTextSurroundedByWhitespace, "daily_text_surrounded_by_whitespace");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getDailyTextSurroundedByWhitespacePercent() {
        return dailyTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param dailyTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setDailyTextSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec dailyTextSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextSurroundedByWhitespacePercent, dailyTextSurroundedByWhitespacePercent));
        this.dailyTextSurroundedByWhitespacePercent = dailyTextSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyTextSurroundedByWhitespacePercent, "daily_text_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getDailyTextValidCountryCodePercent() {
        return dailyTextValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param dailyTextValidCountryCodePercent String valid country code percent check.
     */
    public void setDailyTextValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec dailyTextValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextValidCountryCodePercent, dailyTextValidCountryCodePercent));
        this.dailyTextValidCountryCodePercent = dailyTextValidCountryCodePercent;
        propagateHierarchyIdToField(dailyTextValidCountryCodePercent, "daily_text_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getDailyTextValidCurrencyCodePercent() {
        return dailyTextValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param dailyTextValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setDailyTextValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec dailyTextValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextValidCurrencyCodePercent, dailyTextValidCurrencyCodePercent));
        this.dailyTextValidCurrencyCodePercent = dailyTextValidCurrencyCodePercent;
        propagateHierarchyIdToField(dailyTextValidCurrencyCodePercent, "daily_text_valid_currency_code_percent");
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
    public ColumnTextDailyMonitoringChecksSpec deepClone() {
        return (ColumnTextDailyMonitoringChecksSpec)super.deepClone();
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
        return CheckTimeScale.daily;
    }
}
