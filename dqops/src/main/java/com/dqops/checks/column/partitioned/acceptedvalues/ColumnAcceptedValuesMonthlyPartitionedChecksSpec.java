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
 * Container of accepted values data quality partitioned checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_text_found_in_set_percent", o -> o.monthlyPartitionTextFoundInSetPercent);
            put("monthly_partition_expected_numbers_in_use_count", o -> o.monthlyPartitionExpectedNumbersInUseCount);
            put("monthly_partition_expected_text_values_in_use_count", o -> o.monthlyPartitionExpectedTextValuesInUseCount);
            put("monthly_partition_expected_texts_in_top_values_count", o -> o.monthlyPartitionExpectedTextsInTopValuesCount);
            put("monthly_partition_number_found_in_set_percent", o -> o.monthlyPartitionNumberFoundInSetPercent);

            put("monthly_partition_text_valid_country_code_percent", o -> o.monthlyPartitionTextValidCountryCodePercent);
            put("monthly_partition_text_valid_currency_code_percent", o -> o.monthlyPartitionTextValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnTextFoundInSetPercentCheckSpec monthlyPartitionTextFoundInSetPercent;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnNumberFoundInSetPercentCheckSpec monthlyPartitionNumberFoundInSetPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.")
    private ColumnExpectedTextValuesInUseCountCheckSpec monthlyPartitionExpectedTextValuesInUseCount;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each monthly partition.")
    private ColumnExpectedTextsInTopValuesCountCheckSpec monthlyPartitionExpectedTextsInTopValuesCount;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.")
    private ColumnExpectedNumbersInUseCountCheckSpec monthlyPartitionExpectedNumbersInUseCount;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextValidCountryCodePercentCheckSpec monthlyPartitionTextValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextValidCurrencyCodePercentCheckSpec monthlyPartitionTextValidCurrencyCodePercent;


    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextFoundInSetPercentCheckSpec getMonthlyPartitionTextFoundInSetPercent() {
        return monthlyPartitionTextFoundInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param monthlyPartitionTextFoundInSetPercent Minimum strings in set percent check.
     */
    public void setMonthlyPartitionTextFoundInSetPercent(ColumnTextFoundInSetPercentCheckSpec monthlyPartitionTextFoundInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextFoundInSetPercent, monthlyPartitionTextFoundInSetPercent));
        this.monthlyPartitionTextFoundInSetPercent = monthlyPartitionTextFoundInSetPercent;
        propagateHierarchyIdToField(monthlyPartitionTextFoundInSetPercent, "monthly_partition_text_found_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberFoundInSetPercentCheckSpec getMonthlyPartitionNumberFoundInSetPercent() {
        return monthlyPartitionNumberFoundInSetPercent;
    }

    /**
     * Sets a new specification of a numbers found percent check.
     * @param monthlyPartitionNumberFoundInSetPercent Numbers found percent check specification.
     */
    public void setMonthlyPartitionNumberFoundInSetPercent(ColumnNumberFoundInSetPercentCheckSpec monthlyPartitionNumberFoundInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumberFoundInSetPercent, monthlyPartitionNumberFoundInSetPercent));
        this.monthlyPartitionNumberFoundInSetPercent = monthlyPartitionNumberFoundInSetPercent;
        propagateHierarchyIdToField(monthlyPartitionNumberFoundInSetPercent, "monthly_partition_number_found_in_set_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnExpectedTextValuesInUseCountCheckSpec getMonthlyPartitionExpectedTextValuesInUseCount() {
        return monthlyPartitionExpectedTextValuesInUseCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param monthlyPartitionExpectedTextValuesInUseCount Minimum strings in set count check.
     */
    public void setMonthlyPartitionExpectedTextValuesInUseCount(ColumnExpectedTextValuesInUseCountCheckSpec monthlyPartitionExpectedTextValuesInUseCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionExpectedTextValuesInUseCount, monthlyPartitionExpectedTextValuesInUseCount));
        this.monthlyPartitionExpectedTextValuesInUseCount = monthlyPartitionExpectedTextValuesInUseCount;
        propagateHierarchyIdToField(monthlyPartitionExpectedTextValuesInUseCount, "monthly_partition_expected_text_values_in_use_count");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedTextsInTopValuesCountCheckSpec getMonthlyPartitionExpectedTextsInTopValuesCount() {
        return monthlyPartitionExpectedTextsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param monthlyPartitionExpectedTextsInTopValuesCount Most popular values count check.
     */
    public void setMonthlyPartitionExpectedTextsInTopValuesCount(ColumnExpectedTextsInTopValuesCountCheckSpec monthlyPartitionExpectedTextsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionExpectedTextsInTopValuesCount, monthlyPartitionExpectedTextsInTopValuesCount));
        this.monthlyPartitionExpectedTextsInTopValuesCount = monthlyPartitionExpectedTextsInTopValuesCount;
        propagateHierarchyIdToField(monthlyPartitionExpectedTextsInTopValuesCount, "monthly_partition_expected_texts_in_top_values_count");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getMonthlyPartitionExpectedNumbersInUseCount() {
        return monthlyPartitionExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers in set count check.
     * @param monthlyPartitionExpectedNumbersInUseCount Numbers in set count check specification.
     */
    public void setMonthlyPartitionExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec monthlyPartitionExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionExpectedNumbersInUseCount, monthlyPartitionExpectedNumbersInUseCount));
        this.monthlyPartitionExpectedNumbersInUseCount = monthlyPartitionExpectedNumbersInUseCount;
        propagateHierarchyIdToField(monthlyPartitionExpectedNumbersInUseCount, "monthly_partition_expected_numbers_in_use_count");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getMonthlyPartitionTextValidCountryCodePercent() {
        return monthlyPartitionTextValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param monthlyPartitionTextValidCountryCodePercent String valid country code percent check.
     */
    public void setMonthlyPartitionTextValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec monthlyPartitionTextValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextValidCountryCodePercent, monthlyPartitionTextValidCountryCodePercent));
        this.monthlyPartitionTextValidCountryCodePercent = monthlyPartitionTextValidCountryCodePercent;
        propagateHierarchyIdToField(monthlyPartitionTextValidCountryCodePercent, "monthly_partition_text_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getMonthlyPartitionTextValidCurrencyCodePercent() {
        return monthlyPartitionTextValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param monthlyPartitionTextValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setMonthlyPartitionTextValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec monthlyPartitionTextValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextValidCurrencyCodePercent, monthlyPartitionTextValidCurrencyCodePercent));
        this.monthlyPartitionTextValidCurrencyCodePercent = monthlyPartitionTextValidCurrencyCodePercent;
        propagateHierarchyIdToField(monthlyPartitionTextValidCurrencyCodePercent, "monthly_partition_text_valid_currency_code_percent");
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
