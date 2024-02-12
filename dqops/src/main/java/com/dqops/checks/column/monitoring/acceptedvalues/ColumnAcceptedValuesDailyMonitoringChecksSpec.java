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
package com.dqops.checks.column.monitoring.acceptedvalues;

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
 * Container of accepted values data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_text_found_in_set_percent", o -> o.dailyTextFoundInSetPercent);
            put("daily_number_found_in_set_percent", o -> o.dailyNumberFoundInSetPercent);
            put("daily_expected_text_values_in_use_count", o -> o.dailyExpectedTextValuesInUseCount);
            put("daily_expected_texts_in_top_values_count", o -> o.dailyExpectedTextsInTopValuesCount);
            put("daily_expected_numbers_in_use_count", o -> o.dailyExpectedNumbersInUseCount);

            put("daily_text_valid_country_code_percent", o -> o.dailyTextValidCountryCodePercent);
            put("daily_text_valid_currency_code_percent", o -> o.dailyTextValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextFoundInSetPercentCheckSpec dailyTextFoundInSetPercent;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNumberFoundInSetPercentCheckSpec dailyNumberFoundInSetPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnExpectedTextValuesInUseCountCheckSpec dailyExpectedTextValuesInUseCount;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnExpectedTextsInTopValuesCountCheckSpec dailyExpectedTextsInTopValuesCount;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnExpectedNumbersInUseCountCheckSpec dailyExpectedNumbersInUseCount;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextValidCountryCodePercentCheckSpec dailyTextValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextValidCurrencyCodePercentCheckSpec dailyTextValidCurrencyCodePercent;


    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextFoundInSetPercentCheckSpec getDailyTextFoundInSetPercent() {
        return dailyTextFoundInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param dailyTextFoundInSetPercent Minimum strings in set percent check.
     */
    public void setDailyTextFoundInSetPercent(ColumnTextFoundInSetPercentCheckSpec dailyTextFoundInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextFoundInSetPercent, dailyTextFoundInSetPercent));
        this.dailyTextFoundInSetPercent = dailyTextFoundInSetPercent;
        propagateHierarchyIdToField(dailyTextFoundInSetPercent, "daily_text_found_in_set_percent");
    }

    /**
     * Returns a numbers valid percent check specification.
     * @return Numbers valid percent check specification.
     */
    public ColumnNumberFoundInSetPercentCheckSpec getDailyNumberFoundInSetPercent() {
        return dailyNumberFoundInSetPercent;
    }

    /**
     * Sets a new specification of a numbers valid percent check.
     * @param dailyNumberFoundInSetPercent Number valid percent check specification.
     */
    public void setDailyNumberFoundInSetPercent(ColumnNumberFoundInSetPercentCheckSpec dailyNumberFoundInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNumberFoundInSetPercent, dailyNumberFoundInSetPercent));
        this.dailyNumberFoundInSetPercent = dailyNumberFoundInSetPercent;
        propagateHierarchyIdToField(dailyNumberFoundInSetPercent, "daily_number_found_in_set_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnExpectedTextValuesInUseCountCheckSpec getDailyExpectedTextValuesInUseCount() {
        return dailyExpectedTextValuesInUseCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param dailyExpectedTextValuesInUseCount Minimum strings in set count check.
     */
    public void setDailyExpectedTextValuesInUseCount(ColumnExpectedTextValuesInUseCountCheckSpec dailyExpectedTextValuesInUseCount) {
        this.setDirtyIf(!Objects.equals(this.dailyExpectedTextValuesInUseCount, dailyExpectedTextValuesInUseCount));
        this.dailyExpectedTextValuesInUseCount = dailyExpectedTextValuesInUseCount;
        propagateHierarchyIdToField(dailyExpectedTextValuesInUseCount, "daily_expected_text_values_in_use_count");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedTextsInTopValuesCountCheckSpec getDailyExpectedTextsInTopValuesCount() {
        return dailyExpectedTextsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param dailyExpectedTextsInTopValuesCount Most popular values count check.
     */
    public void setDailyExpectedTextsInTopValuesCount(ColumnExpectedTextsInTopValuesCountCheckSpec dailyExpectedTextsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.dailyExpectedTextsInTopValuesCount, dailyExpectedTextsInTopValuesCount));
        this.dailyExpectedTextsInTopValuesCount = dailyExpectedTextsInTopValuesCount;
        propagateHierarchyIdToField(dailyExpectedTextsInTopValuesCount, "daily_expected_texts_in_top_values_count");
    }

    /**
     * Returns a numbers found count check specification.
     * @return Numbers found count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getDailyExpectedNumbersInUseCount() {
        return dailyExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers found count check.
     * @param dailyExpectedNumbersInUseCount Numbers found count check.
     */
    public void setDailyExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec dailyExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.dailyExpectedNumbersInUseCount, dailyExpectedNumbersInUseCount));
        this.dailyExpectedNumbersInUseCount = dailyExpectedNumbersInUseCount;
        propagateHierarchyIdToField(dailyExpectedNumbersInUseCount, "daily_expected_numbers_in_use_count");
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
    public ColumnAcceptedValuesDailyMonitoringChecksSpec deepClone() {
        return (ColumnAcceptedValuesDailyMonitoringChecksSpec)super.deepClone();
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
