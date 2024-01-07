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
package com.dqops.checks.column.partitioned.strings;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.blanks.*;
import com.dqops.checks.column.checkspecs.strings.*;
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
 * Container of strings data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnStringsDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_string_max_length", o -> o.dailyPartitionStringMaxLength);
            put("daily_partition_string_min_length", o -> o.dailyPartitionStringMinLength);
            put("daily_partition_string_mean_length", o -> o.dailyPartitionStringMeanLength);
            put("daily_partition_string_length_below_min_length_count", o -> o.dailyPartitionStringLengthBelowMinLengthCount);
            put("daily_partition_string_length_below_min_length_percent", o -> o.dailyPartitionStringLengthBelowMinLengthPercent);
            put("daily_partition_string_length_above_max_length_count", o -> o.dailyPartitionStringLengthAboveMaxLengthCount);
            put("daily_partition_string_length_above_max_length_percent", o -> o.dailyPartitionStringLengthAboveMaxLengthPercent);
            put("daily_partition_string_length_in_range_percent", o -> o.dailyPartitionStringLengthInRangePercent);

            put("daily_partition_string_surrounded_by_whitespace_count", o -> o.dailyPartitionStringSurroundedByWhitespaceCount);
            put("daily_partition_string_surrounded_by_whitespace_percent", o -> o.dailyPartitionStringSurroundedByWhitespacePercent);
            put("daily_partition_string_boolean_placeholder_percent", o -> o.dailyPartitionStringBooleanPlaceholderPercent);
            put("daily_partition_string_parsable_to_integer_percent", o -> o.dailyPartitionStringParsableToIntegerPercent);
            put("daily_partition_string_parsable_to_float_percent", o -> o.dailyPartitionStringParsableToFloatPercent);

            put("daily_partition_string_valid_dates_percent", o -> o.dailyPartitionStringValidDatesPercent);
            put("daily_partition_string_valid_country_code_percent", o -> o.dailyPartitionStringValidCountryCodePercent);
            put("daily_partition_string_valid_currency_code_percent", o -> o.dailyPartitionStringValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringMaxLengthCheckSpec dailyPartitionStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringMinLengthCheckSpec dailyPartitionStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringMeanLengthCheckSpec dailyPartitionStringMeanLength;

    @JsonPropertyDescription("The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringLengthBelowMinLengthCountCheckSpec dailyPartitionStringLengthBelowMinLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringLengthBelowMinLengthPercentCheckSpec dailyPartitionStringLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringLengthAboveMaxLengthCountCheckSpec dailyPartitionStringLengthAboveMaxLengthCount;

    @JsonPropertyDescription("The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringLengthAboveMaxLengthPercentCheckSpec dailyPartitionStringLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringLengthInRangePercentCheckSpec dailyPartitionStringLengthInRangePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringSurroundedByWhitespaceCountCheckSpec dailyPartitionStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringSurroundedByWhitespacePercentCheckSpec dailyPartitionStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringBooleanPlaceholderPercentCheckSpec dailyPartitionStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringParsableToIntegerPercentCheckSpec dailyPartitionStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringParsableToFloatPercentCheckSpec dailyPartitionStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidDatesPercentCheckSpec dailyPartitionStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidCountryCodePercentCheckSpec dailyPartitionStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidCurrencyCodePercentCheckSpec dailyPartitionStringValidCurrencyCodePercent;


    /**
     * Returns a maximum string length below  check.
     * @return Maximum string length below  check.
     */
    public ColumnStringMaxLengthCheckSpec getDailyPartitionStringMaxLength() {
        return dailyPartitionStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length below  check.
     * @param dailyPartitionStringMaxLength Maximum string length below  check.
     */
    public void setDailyPartitionStringMaxLength(ColumnStringMaxLengthCheckSpec dailyPartitionStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringMaxLength, dailyPartitionStringMaxLength));
        this.dailyPartitionStringMaxLength = dailyPartitionStringMaxLength;
        propagateHierarchyIdToField(dailyPartitionStringMaxLength, "daily_partition_string_max_length");
    }

    /**
     * Returns a minimum string length below  check.
     * @return Minimum string length below  check.
     */
    public ColumnStringMinLengthCheckSpec getDailyPartitionStringMinLength() {
        return dailyPartitionStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length below  check.
     * @param dailyPartitionStringMinLength Minimum string length above check.
     */
    public void setDailyPartitionStringMinLength(ColumnStringMinLengthCheckSpec dailyPartitionStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringMinLength, dailyPartitionStringMinLength));
        this.dailyPartitionStringMinLength = dailyPartitionStringMinLength;
        propagateHierarchyIdToField(dailyPartitionStringMinLength, "daily_partition_string_min_length");
    }

    /**
     * Returns a mean string length between  check.
     * @return Mean string length between  check.
     */
    public ColumnStringMeanLengthCheckSpec getDailyPartitionStringMeanLength() {
        return dailyPartitionStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param dailyPartitionStringMeanLength Mean string length between check.
     */
    public void setDailyPartitionStringMeanLength(ColumnStringMeanLengthCheckSpec dailyPartitionStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringMeanLength, dailyPartitionStringMeanLength));
        this.dailyPartitionStringMeanLength = dailyPartitionStringMeanLength;
        propagateHierarchyIdToField(dailyPartitionStringMeanLength, "daily_partition_string_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return Mean string length below min length count check.
     */
    public ColumnStringLengthBelowMinLengthCountCheckSpec getDailyPartitionStringLengthBelowMinLengthCount() {
        return dailyPartitionStringLengthBelowMinLengthCount;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param dailyPartitionStringLengthBelowMinLengthCount String length below min length count check.
     */
    public void setDailyPartitionStringLengthBelowMinLengthCount(ColumnStringLengthBelowMinLengthCountCheckSpec dailyPartitionStringLengthBelowMinLengthCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringLengthBelowMinLengthCount, dailyPartitionStringLengthBelowMinLengthCount));
        this.dailyPartitionStringLengthBelowMinLengthCount = dailyPartitionStringLengthBelowMinLengthCount;
        propagateHierarchyIdToField(dailyPartitionStringLengthBelowMinLengthCount, "daily_partition_string_length_below_min_length_count");
    }

    /**
     * Returns a string length below min length percent check.
     * @return Mean string length below min length percent check.
     */
    public ColumnStringLengthBelowMinLengthPercentCheckSpec getDailyPartitionStringLengthBelowMinLengthPercent() {
        return dailyPartitionStringLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param dailyPartitionStringLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setDailyPartitionStringLengthBelowMinLengthPercent(ColumnStringLengthBelowMinLengthPercentCheckSpec dailyPartitionStringLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringLengthBelowMinLengthPercent, dailyPartitionStringLengthBelowMinLengthPercent));
        this.dailyPartitionStringLengthBelowMinLengthPercent = dailyPartitionStringLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(dailyPartitionStringLengthBelowMinLengthPercent, "daily_partition_string_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return Mean string length above max length count check.
     */
    public ColumnStringLengthAboveMaxLengthCountCheckSpec getDailyPartitionStringLengthAboveMaxLengthCount() {
        return dailyPartitionStringLengthAboveMaxLengthCount;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param dailyPartitionStringLengthAboveMaxLengthCount String length above max length count check.
     */
    public void setDailyPartitionStringLengthAboveMaxLengthCount(ColumnStringLengthAboveMaxLengthCountCheckSpec dailyPartitionStringLengthAboveMaxLengthCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringLengthAboveMaxLengthCount, dailyPartitionStringLengthAboveMaxLengthCount));
        this.dailyPartitionStringLengthAboveMaxLengthCount = dailyPartitionStringLengthAboveMaxLengthCount;
        propagateHierarchyIdToField(dailyPartitionStringLengthAboveMaxLengthCount, "daily_partition_string_length_above_max_length_count");
    }

    /**
     * Returns a string length above max length percent check.
     * @return Mean string length above max length percent check.
     */
    public ColumnStringLengthAboveMaxLengthPercentCheckSpec getDailyPartitionStringLengthAboveMaxLengthPercent() {
        return dailyPartitionStringLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param dailyPartitionStringLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setDailyPartitionStringLengthAboveMaxLengthPercent(ColumnStringLengthAboveMaxLengthPercentCheckSpec dailyPartitionStringLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringLengthAboveMaxLengthPercent, dailyPartitionStringLengthAboveMaxLengthPercent));
        this.dailyPartitionStringLengthAboveMaxLengthPercent = dailyPartitionStringLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(dailyPartitionStringLengthAboveMaxLengthPercent, "daily_partition_string_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return Mean string length in range percent check.
     */
    public ColumnStringLengthInRangePercentCheckSpec getDailyPartitionStringLengthInRangePercent() {
        return dailyPartitionStringLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param dailyPartitionStringLengthInRangePercent String length in range percent check.
     */
    public void setDailyPartitionStringLengthInRangePercent(ColumnStringLengthInRangePercentCheckSpec dailyPartitionStringLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringLengthInRangePercent, dailyPartitionStringLengthInRangePercent));
        this.dailyPartitionStringLengthInRangePercent = dailyPartitionStringLengthInRangePercent;
        propagateHierarchyIdToField(dailyPartitionStringLengthInRangePercent, "daily_partition_string_length_in_range_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnStringSurroundedByWhitespaceCountCheckSpec getDailyPartitionStringSurroundedByWhitespaceCount() {
        return dailyPartitionStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param dailyPartitionStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setDailyPartitionStringSurroundedByWhitespaceCount(ColumnStringSurroundedByWhitespaceCountCheckSpec dailyPartitionStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringSurroundedByWhitespaceCount, dailyPartitionStringSurroundedByWhitespaceCount));
        this.dailyPartitionStringSurroundedByWhitespaceCount = dailyPartitionStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(dailyPartitionStringSurroundedByWhitespaceCount, "daily_partition_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnStringSurroundedByWhitespacePercentCheckSpec getDailyPartitionStringSurroundedByWhitespacePercent() {
        return dailyPartitionStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param dailyPartitionStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setDailyPartitionStringSurroundedByWhitespacePercent(ColumnStringSurroundedByWhitespacePercentCheckSpec dailyPartitionStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringSurroundedByWhitespacePercent, dailyPartitionStringSurroundedByWhitespacePercent));
        this.dailyPartitionStringSurroundedByWhitespacePercent = dailyPartitionStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyPartitionStringSurroundedByWhitespacePercent, "daily_partition_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnStringBooleanPlaceholderPercentCheckSpec getDailyPartitionStringBooleanPlaceholderPercent() {
        return dailyPartitionStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param dailyPartitionStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setDailyPartitionStringBooleanPlaceholderPercent(ColumnStringBooleanPlaceholderPercentCheckSpec dailyPartitionStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringBooleanPlaceholderPercent, dailyPartitionStringBooleanPlaceholderPercent));
        this.dailyPartitionStringBooleanPlaceholderPercent = dailyPartitionStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(dailyPartitionStringBooleanPlaceholderPercent, "daily_partition_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent  check.
     */
    public ColumnStringParsableToIntegerPercentCheckSpec getDailyPartitionStringParsableToIntegerPercent() {
        return dailyPartitionStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param dailyPartitionStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setDailyPartitionStringParsableToIntegerPercent(ColumnStringParsableToIntegerPercentCheckSpec dailyPartitionStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringParsableToIntegerPercent, dailyPartitionStringParsableToIntegerPercent));
        this.dailyPartitionStringParsableToIntegerPercent = dailyPartitionStringParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyPartitionStringParsableToIntegerPercent, "daily_partition_string_parsable_to_integer_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent  check.
     */
    public ColumnStringParsableToFloatPercentCheckSpec getDailyPartitionStringParsableToFloatPercent() {
        return dailyPartitionStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param dailyPartitionStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setDailyPartitionStringParsableToFloatPercent(ColumnStringParsableToFloatPercentCheckSpec dailyPartitionStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringParsableToFloatPercent, dailyPartitionStringParsableToFloatPercent));
        this.dailyPartitionStringParsableToFloatPercent = dailyPartitionStringParsableToFloatPercent;
        propagateHierarchyIdToField(dailyPartitionStringParsableToFloatPercent, "daily_partition_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnStringValidDatesPercentCheckSpec getDailyPartitionStringValidDatesPercent() {
        return dailyPartitionStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param dailyPartitionStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setDailyPartitionStringValidDatesPercent(ColumnStringValidDatesPercentCheckSpec dailyPartitionStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidDatesPercent, dailyPartitionStringValidDatesPercent));
        this.dailyPartitionStringValidDatesPercent = dailyPartitionStringValidDatesPercent;
        propagateHierarchyIdToField(dailyPartitionStringValidDatesPercent, "daily_partition_string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent  check.
     */
    public ColumnStringValidCountryCodePercentCheckSpec getDailyPartitionStringValidCountryCodePercent() {
        return dailyPartitionStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param dailyPartitionStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setDailyPartitionStringValidCountryCodePercent(ColumnStringValidCountryCodePercentCheckSpec dailyPartitionStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidCountryCodePercent, dailyPartitionStringValidCountryCodePercent));
        this.dailyPartitionStringValidCountryCodePercent = dailyPartitionStringValidCountryCodePercent;
        propagateHierarchyIdToField(dailyPartitionStringValidCountryCodePercent, "daily_partition_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent  check.
     */
    public ColumnStringValidCurrencyCodePercentCheckSpec getDailyPartitionStringValidCurrencyCodePercent() {
        return dailyPartitionStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param dailyPartitionStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setDailyPartitionStringValidCurrencyCodePercent(ColumnStringValidCurrencyCodePercentCheckSpec dailyPartitionStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidCurrencyCodePercent, dailyPartitionStringValidCurrencyCodePercent));
        this.dailyPartitionStringValidCurrencyCodePercent = dailyPartitionStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(dailyPartitionStringValidCurrencyCodePercent, "daily_partition_string_valid_currency_code_percent");
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
