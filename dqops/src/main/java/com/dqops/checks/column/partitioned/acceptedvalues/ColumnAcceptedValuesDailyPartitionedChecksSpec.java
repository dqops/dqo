/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.partitioned.acceptedvalues;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.acceptedvalues.*;
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
 * Container of accepted values data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_text_found_in_set_percent", o -> o.dailyPartitionTextFoundInSetPercent);
            put("daily_partition_expected_numbers_in_use_count", o -> o.dailyPartitionExpectedNumbersInUseCount);
            put("daily_partition_expected_text_values_in_use_count", o -> o.dailyPartitionExpectedTextValuesInUseCount);
            put("daily_partition_expected_texts_in_top_values_count", o -> o.dailyPartitionExpectedTextsInTopValuesCount);
            put("daily_partition_number_found_in_set_percent", o -> o.dailyPartitionNumberFoundInSetPercent);

            put("daily_partition_text_valid_country_code_percent", o -> o.dailyPartitionTextValidCountryCodePercent);
            put("daily_partition_text_valid_currency_code_percent", o -> o.dailyPartitionTextValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnTextFoundInSetPercentCheckSpec dailyPartitionTextFoundInSetPercent;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnNumberFoundInSetPercentCheckSpec dailyPartitionNumberFoundInSetPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.")
    private ColumnExpectedTextValuesInUseCountCheckSpec dailyPartitionExpectedTextValuesInUseCount;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each daily partition.")
    private ColumnExpectedTextsInTopValuesCountCheckSpec dailyPartitionExpectedTextsInTopValuesCount;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.")
    private ColumnExpectedNumbersInUseCountCheckSpec dailyPartitionExpectedNumbersInUseCount;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextValidCountryCodePercentCheckSpec dailyPartitionTextValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextValidCurrencyCodePercentCheckSpec dailyPartitionTextValidCurrencyCodePercent;


    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextFoundInSetPercentCheckSpec getDailyPartitionTextFoundInSetPercent() {
        return dailyPartitionTextFoundInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param dailyPartitionTextFoundInSetPercent Minimum strings in set percent check.
     */
    public void setDailyPartitionTextFoundInSetPercent(ColumnTextFoundInSetPercentCheckSpec dailyPartitionTextFoundInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextFoundInSetPercent, dailyPartitionTextFoundInSetPercent));
        this.dailyPartitionTextFoundInSetPercent = dailyPartitionTextFoundInSetPercent;
        propagateHierarchyIdToField(dailyPartitionTextFoundInSetPercent, "daily_partition_text_found_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberFoundInSetPercentCheckSpec getDailyPartitionNumberFoundInSetPercent() {
        return dailyPartitionNumberFoundInSetPercent;
    }

    /**
     * Sets a new specification of a numbers found percent check.
     * @param dailyPartitionNumberFoundInSetPercent Numbers found percent check specification.
     */
    public void setDailyPartitionNumberFoundInSetPercent(ColumnNumberFoundInSetPercentCheckSpec dailyPartitionNumberFoundInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNumberFoundInSetPercent, dailyPartitionNumberFoundInSetPercent));
        this.dailyPartitionNumberFoundInSetPercent = dailyPartitionNumberFoundInSetPercent;
        propagateHierarchyIdToField(dailyPartitionNumberFoundInSetPercent, "daily_partition_number_found_in_set_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnExpectedTextValuesInUseCountCheckSpec getDailyPartitionExpectedTextValuesInUseCount() {
        return dailyPartitionExpectedTextValuesInUseCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param dailyPartitionExpectedTextValuesInUseCount Minimum strings in set count check.
     */
    public void setDailyPartitionExpectedTextValuesInUseCount(ColumnExpectedTextValuesInUseCountCheckSpec dailyPartitionExpectedTextValuesInUseCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionExpectedTextValuesInUseCount, dailyPartitionExpectedTextValuesInUseCount));
        this.dailyPartitionExpectedTextValuesInUseCount = dailyPartitionExpectedTextValuesInUseCount;
        propagateHierarchyIdToField(dailyPartitionExpectedTextValuesInUseCount, "daily_partition_expected_text_values_in_use_count");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedTextsInTopValuesCountCheckSpec getDailyPartitionExpectedTextsInTopValuesCount() {
        return dailyPartitionExpectedTextsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param dailyPartitionExpectedTextsInTopValuesCount Most popular values count check.
     */
    public void setDailyPartitionExpectedTextsInTopValuesCount(ColumnExpectedTextsInTopValuesCountCheckSpec dailyPartitionExpectedTextsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionExpectedTextsInTopValuesCount, dailyPartitionExpectedTextsInTopValuesCount));
        this.dailyPartitionExpectedTextsInTopValuesCount = dailyPartitionExpectedTextsInTopValuesCount;
        propagateHierarchyIdToField(dailyPartitionExpectedTextsInTopValuesCount, "daily_partition_expected_texts_in_top_values_count");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getDailyPartitionExpectedNumbersInUseCount() {
        return dailyPartitionExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers in set count check.
     * @param dailyPartitionExpectedNumbersInUseCount Numbers in set count check specification.
     */
    public void setDailyPartitionExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec dailyPartitionExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionExpectedNumbersInUseCount, dailyPartitionExpectedNumbersInUseCount));
        this.dailyPartitionExpectedNumbersInUseCount = dailyPartitionExpectedNumbersInUseCount;
        propagateHierarchyIdToField(dailyPartitionExpectedNumbersInUseCount, "daily_partition_expected_numbers_in_use_count");
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
        return DataTypeCategory.COMPARABLE;
    }
}
