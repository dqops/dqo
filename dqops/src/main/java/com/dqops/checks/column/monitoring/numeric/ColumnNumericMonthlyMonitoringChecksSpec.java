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
package com.dqops.checks.column.monitoring.numeric;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.numeric.*;
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
 * Container of built-in preconfigured data quality monitoring on a column level that are checking numeric values at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_negative_count", o -> o.monthlyNegativeCount);
            put("monthly_negative_percent", o -> o.monthlyNegativePercent);
            put("monthly_non_negative_count", o -> o.monthlyNonNegativeCount);
            put("monthly_non_negative_percent", o -> o.monthlyNonNegativePercent);
            put("monthly_expected_numbers_in_use_count", o -> o.monthlyExpectedNumbersInUseCount);
            put("monthly_number_value_in_set_percent", o -> o.monthlyNumberValueInSetPercent);
            put("monthly_values_in_range_numeric_percent", o -> o.monthlyValuesInRangeNumericPercent);
            put("monthly_values_in_range_integers_percent", o -> o.monthlyValuesInRangeIntegersPercent);
            put("monthly_value_below_min_value_count", o -> o.monthlyValueBelowMinValueCount);
            put("monthly_value_below_min_value_percent", o -> o.monthlyValueBelowMinValuePercent);
            put("monthly_value_above_max_value_count", o -> o.monthlyValueAboveMaxValueCount);
            put("monthly_value_above_max_value_percent", o -> o.monthlyValueAboveMaxValuePercent);
            put("monthly_max_in_range", o -> o.monthlyMaxInRange);
            put("monthly_min_in_range", o -> o.monthlyMinInRange);
            put("monthly_mean_in_range", o -> o.monthlyMeanInRange);
            put("monthly_percentile_in_range", o -> o.monthlyPercentileInRange);
            put("monthly_median_in_range", o -> o.monthlyMedianInRange);
            put("monthly_percentile_10_in_range", o -> o.monthlyPercentile_10InRange);
            put("monthly_percentile_25_in_range", o -> o.monthlyPercentile_25InRange);
            put("monthly_percentile_75_in_range", o -> o.monthlyPercentile_75InRange);
            put("monthly_percentile_90_in_range", o -> o.monthlyPercentile_90InRange);
            put("monthly_sample_stddev_in_range", o -> o.monthlySampleStddevInRange);
            put("monthly_population_stddev_in_range", o -> o.monthlyPopulationStddevInRange);
            put("monthly_sample_variance_in_range", o -> o.monthlySampleVarianceInRange);
            put("monthly_population_variance_in_range", o -> o.monthlyPopulationVarianceInRange);
            put("monthly_sum_in_range", o -> o.monthlySumInRange);
            put("monthly_invalid_latitude_count", o -> o.monthlyInvalidLatitudeCount);
            put("monthly_valid_latitude_percent", o -> o.monthlyValidLatitudePercent);
            put("monthly_invalid_longitude_count", o -> o.monthlyInvalidLongitudeCount);
            put("monthly_valid_longitude_percent", o -> o.monthlyValidLongitudePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNegativeCountCheckSpec monthlyNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNegativePercentCheckSpec monthlyNegativePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNonNegativeCountCheckSpec monthlyNonNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNonNegativePercentCheckSpec monthlyNonNegativePercent;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnExpectedNumbersInUseCountCheckSpec monthlyExpectedNumbersInUseCount;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNumberValueInSetPercentCheckSpec monthlyNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValuesInRangeNumericPercentCheckSpec monthlyValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValuesInRangeIntegersPercentCheckSpec monthlyValuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValueBelowMinValueCountCheckSpec monthlyValueBelowMinValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValueBelowMinValuePercentCheckSpec monthlyValueBelowMinValuePercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValueAboveMaxValueCountCheckSpec monthlyValueAboveMaxValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValueAboveMaxValuePercentCheckSpec monthlyValueAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxInRangeCheckSpec monthlyMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinInRangeCheckSpec monthlyMinInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMeanInRangeCheckSpec monthlyMeanInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPercentileInRangeCheckSpec monthlyPercentileInRange;

    @JsonPropertyDescription("Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMedianInRangeCheckSpec monthlyMedianInRange;

    @JsonPropertyDescription("Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPercentile10InRangeCheckSpec monthlyPercentile_10InRange;

    @JsonPropertyDescription("Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPercentile25InRangeCheckSpec monthlyPercentile_25InRange;

    @JsonPropertyDescription("Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPercentile75InRangeCheckSpec monthlyPercentile_75InRange;

    @JsonPropertyDescription("Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPercentile90InRangeCheckSpec monthlyPercentile_90InRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSampleStddevInRangeCheckSpec monthlySampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPopulationStddevInRangeCheckSpec monthlyPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSampleVarianceInRangeCheckSpec monthlySampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPopulationVarianceInRangeCheckSpec monthlyPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSumInRangeCheckSpec monthlySumInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnInvalidLatitudeCountCheckSpec monthlyInvalidLatitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValidLatitudePercentCheckSpec monthlyValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnInvalidLongitudeCountCheckSpec monthlyInvalidLongitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValidLongitudePercentCheckSpec monthlyValidLongitudePercent;

    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getMonthlyNegativeCount() {
        return monthlyNegativeCount;
    }

    /**
     * Sets a new specification of a negative values count check.
     * @param monthlyNegativeCount Negative values count check specification.
     */
    public void setMonthlyNegativeCount(ColumnNegativeCountCheckSpec monthlyNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyNegativeCount, monthlyNegativeCount));
        this.monthlyNegativeCount = monthlyNegativeCount;
        propagateHierarchyIdToField(monthlyNegativeCount, "monthly_negative_count");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getMonthlyNegativePercent() {
        return monthlyNegativePercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param monthlyNegativePercent Negative values percentage check specification.
     */
    public void setMonthlyNegativePercent(ColumnNegativePercentCheckSpec monthlyNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNegativePercent, monthlyNegativePercent));
        this.monthlyNegativePercent = monthlyNegativePercent;
        propagateHierarchyIdToField(monthlyNegativePercent, "monthly_negative_percent");
    }

    /**
     * Returns a non-negative values count check specification.
     * @return Non-negative values count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getMonthlyNonNegativeCount() {
        return monthlyNonNegativeCount;
    }

    /**
     * Sets a new specification of a non-negative values count check.
     * @param monthlyNonNegativeCount Non-negative values count check specification.
     */
    public void setMonthlyNonNegativeCount(ColumnNonNegativeCountCheckSpec monthlyNonNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyNonNegativeCount, monthlyNonNegativeCount));
        this.monthlyNonNegativeCount = monthlyNonNegativeCount;
        propagateHierarchyIdToField(monthlyNonNegativeCount, "monthly_non_negative_count");
    }


    /**
     * Returns a non-negative values percentage check specification.
     * @return Non-negative values percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getMonthlyNonNegativePercent() {
        return monthlyNonNegativePercent;
    }

    /**
     * Sets a new specification of a non-negative values percentage check.
     * @param monthlyNonNegativePercent Non-negative values percentage check specification.
     */
    public void setMonthlyNonNegativePercent(ColumnNonNegativePercentCheckSpec monthlyNonNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNonNegativePercent, monthlyNonNegativePercent));
        this.monthlyNonNegativePercent = monthlyNonNegativePercent;
        propagateHierarchyIdToField(monthlyNonNegativePercent, "monthly_non_negative_percent");
    }

    /**
     * Returns a numbers found count check specification.
     * @return Minimum Numbers found count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getMonthlyExpectedNumbersInUseCount() {
        return monthlyExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers found count check.
     * @param monthlyExpectedNumbersInUseCount Numbers found count check specification.
     */
    public void setMonthlyExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec monthlyExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyExpectedNumbersInUseCount, monthlyExpectedNumbersInUseCount));
        this.monthlyExpectedNumbersInUseCount = monthlyExpectedNumbersInUseCount;
        propagateHierarchyIdToField(monthlyExpectedNumbersInUseCount, "monthly_expected_numbers_in_use_count");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getMonthlyNumberValueInSetPercent() {
        return monthlyNumberValueInSetPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param monthlyNumberValueInSetPercent Minimum Numbers in set percent check.
     */
    public void setMonthlyNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec monthlyNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNumberValueInSetPercent, monthlyNumberValueInSetPercent));
        this.monthlyNumberValueInSetPercent = monthlyNumberValueInSetPercent;
        propagateHierarchyIdToField(monthlyNumberValueInSetPercent, "monthly_number_value_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return numbers in set percent check specification.
     */
    public ColumnValuesInRangeNumericPercentCheckSpec getMonthlyValuesInRangeNumericPercent() {
        return monthlyValuesInRangeNumericPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param monthlyValuesInRangeNumericPercent Numbers in set percent check specification.
     */
    public void setMonthlyValuesInRangeNumericPercent(ColumnValuesInRangeNumericPercentCheckSpec monthlyValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyValuesInRangeNumericPercent, monthlyValuesInRangeNumericPercent));
        this.monthlyValuesInRangeNumericPercent = monthlyValuesInRangeNumericPercent;
        propagateHierarchyIdToField(monthlyValuesInRangeNumericPercent, "monthly_values_in_range_numeric_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeIntegersPercentCheckSpec getMonthlyValuesInRangeIntegersPercent() {
        return monthlyValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new definition of a numbers in set percent check.
     * @param monthlyValuesInRangeIntegersPercent Numbers in set percent check specification.
     */
    public void setMonthlyValuesInRangeIntegersPercent(ColumnValuesInRangeIntegersPercentCheckSpec monthlyValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyValuesInRangeIntegersPercent, monthlyValuesInRangeIntegersPercent));
        this.monthlyValuesInRangeIntegersPercent = monthlyValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(monthlyValuesInRangeIntegersPercent, "monthly_values_in_range_integers_percent");
    }

    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnValueBelowMinValueCountCheckSpec getMonthlyValueBelowMinValueCount() {
        return monthlyValueBelowMinValueCount;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param monthlyValueBelowMinValueCount Numeric value below min value count check.
     */
    public void setMonthlyValueBelowMinValueCount(ColumnValueBelowMinValueCountCheckSpec monthlyValueBelowMinValueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyValueBelowMinValueCount, monthlyValueBelowMinValueCount));
        this.monthlyValueBelowMinValueCount = monthlyValueBelowMinValueCount;
        propagateHierarchyIdToField(monthlyValueBelowMinValueCount, "monthly_value_below_min_value_count");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnValueBelowMinValuePercentCheckSpec getMonthlyValueBelowMinValuePercent() {
        return monthlyValueBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param monthlyValueBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setMonthlyValueBelowMinValuePercent(ColumnValueBelowMinValuePercentCheckSpec monthlyValueBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyValueBelowMinValuePercent, monthlyValueBelowMinValuePercent));
        this.monthlyValueBelowMinValuePercent = monthlyValueBelowMinValuePercent;
        propagateHierarchyIdToField(monthlyValueBelowMinValuePercent, "monthly_value_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnValueAboveMaxValueCountCheckSpec getMonthlyValueAboveMaxValueCount() {
        return monthlyValueAboveMaxValueCount;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param monthlyValueAboveMaxValueCount Numeric value above max value count check.
     */
    public void setMonthlyValueAboveMaxValueCount(ColumnValueAboveMaxValueCountCheckSpec monthlyValueAboveMaxValueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyValueAboveMaxValueCount, monthlyValueAboveMaxValueCount));
        this.monthlyValueAboveMaxValueCount = monthlyValueAboveMaxValueCount;
        propagateHierarchyIdToField(monthlyValueAboveMaxValueCount, "monthly_value_above_max_value_count");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnValueAboveMaxValuePercentCheckSpec getMonthlyValueAboveMaxValuePercent() {
        return monthlyValueAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param monthlyValueAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setMonthlyValueAboveMaxValuePercent(ColumnValueAboveMaxValuePercentCheckSpec monthlyValueAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyValueAboveMaxValuePercent, monthlyValueAboveMaxValuePercent));
        this.monthlyValueAboveMaxValuePercent = monthlyValueAboveMaxValuePercent;
        propagateHierarchyIdToField(monthlyValueAboveMaxValuePercent, "monthly_value_above_max_value_percent");
    }

    /**
     * Returns a max in range check specification.
     * @return Max in range check specification.
     */
    public ColumnMaxInRangeCheckSpec getMonthlyMaxInRange() {
        return monthlyMaxInRange;
    }

    /**
     * Sets a new specification of a max in range check.
     * @param monthlyMaxInRange Max in range check specification.
     */
    public void setMonthlyMaxInRange(ColumnMaxInRangeCheckSpec monthlyMaxInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyMaxInRange, monthlyMaxInRange));
        this.monthlyMaxInRange = monthlyMaxInRange;
        propagateHierarchyIdToField(monthlyMaxInRange, "monthly_max_in_range");
    }

    /**
     * Returns a min in range check specification.
     * @return Min in range check specification.
     */
    public ColumnMinInRangeCheckSpec getMonthlyMinInRange() {
        return monthlyMinInRange;
    }

    /**
     * Sets a new specification of a min in range check.
     * @param monthlyMinInRange Min in range check specification.
     */
    public void setMonthlyMinInRange(ColumnMinInRangeCheckSpec monthlyMinInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyMinInRange, monthlyMinInRange));
        this.monthlyMinInRange = monthlyMinInRange;
        propagateHierarchyIdToField(monthlyMinInRange, "monthly_min_in_range");
    }

    /**
     * Returns a mean in range check specification.
     * @return Mean in range check specification.
     */
    public ColumnMeanInRangeCheckSpec getMonthlyMeanInRange() {
        return monthlyMeanInRange;
    }

    /**
     * Sets a new specification of a mean in range check.
     * @param monthlyMeanInRange Mean in range check specification.
     */
    public void setMonthlyMeanInRange(ColumnMeanInRangeCheckSpec monthlyMeanInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyMeanInRange, monthlyMeanInRange));
        this.monthlyMeanInRange = monthlyMeanInRange;
        propagateHierarchyIdToField(monthlyMeanInRange, "monthly_mean_in_range");
    }

    /**
     * Returns a percentile in range check specification.
     * @return Percentile in range check specification.
     */
    public ColumnPercentileInRangeCheckSpec getMonthlyPercentileInRange() {
        return monthlyPercentileInRange;
    }

    /**
     * Sets a new specification of a percentile in range check.
     * @param monthlyPercentileInRange Percentile in range check specification.
     */
    public void setMonthlyPercentileInRange(ColumnPercentileInRangeCheckSpec monthlyPercentileInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPercentileInRange, monthlyPercentileInRange));
        this.monthlyPercentileInRange = monthlyPercentileInRange;
        propagateHierarchyIdToField(monthlyPercentileInRange, "monthly_percentile_in_range");
    }

    /**
     * Returns a median in range check specification.
     * @return median in range check specification.
     */
    public ColumnMedianInRangeCheckSpec getMonthlyMedianInRange() {
        return monthlyMedianInRange;
    }

    /**
     * Sets a new specification of a median in range check.
     * @param monthlyMedianInRange median in range check specification.
     */
    public void setMonthlyMedianInRange(ColumnMedianInRangeCheckSpec monthlyMedianInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyMedianInRange, monthlyMedianInRange));
        this.monthlyMedianInRange = monthlyMedianInRange;
        propagateHierarchyIdToField(monthlyMedianInRange, "monthly_median_in_range");
    }

    /**
     * Returns a percentile 10 in range check specification.
     * @return Percentile 10 in range check specification.
     */
    public ColumnPercentile10InRangeCheckSpec getMonthlyPercentile_10InRange() {
        return monthlyPercentile_10InRange;
    }

    /**
     * Sets a new specification of a percentile 10 in range check.
     * @param monthlyPercentile_10InRange Percentile 10 in range check specification.
     */
    public void setMonthlyPercentile_10InRange(ColumnPercentile10InRangeCheckSpec monthlyPercentile_10InRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPercentile_10InRange, monthlyPercentile_10InRange));
        this.monthlyPercentile_10InRange = monthlyPercentile_10InRange;
        propagateHierarchyIdToField(monthlyPercentile_10InRange, "monthly_percentile_10_in_range");
    }

    /**
     * Returns a percentile 25 in range check specification.
     * @return Percentile 25 in range check specification.
     */
    public ColumnPercentile25InRangeCheckSpec getMonthlyPercentile_25InRange() {
        return monthlyPercentile_25InRange;
    }

    /**
     * Sets a new specification of a percentile 25 in range check.
     * @param monthlyPercentile_25InRange Percentile 25 in range check specification.
     */
    public void setMonthlyPercentile_25InRange(ColumnPercentile25InRangeCheckSpec monthlyPercentile_25InRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPercentile_25InRange, monthlyPercentile_25InRange));
        this.monthlyPercentile_25InRange = monthlyPercentile_25InRange;
        propagateHierarchyIdToField(monthlyPercentile_25InRange, "monthly_percentile_25_in_range");
    }

    /**
     * Returns a percentile 75 in range check specification.
     * @return Percentile 75 in range check specification.
     */
    public ColumnPercentile75InRangeCheckSpec getMonthlyPercentile_75InRange() {
        return monthlyPercentile_75InRange;
    }

    /**
     * Sets a new specification of a percentile 75 in range check.
     * @param monthlyPercentile_75InRange Percentile 75 in range check specification.
     */
    public void setMonthlyPercentile_75InRange(ColumnPercentile75InRangeCheckSpec monthlyPercentile_75InRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPercentile_75InRange, monthlyPercentile_75InRange));
        this.monthlyPercentile_75InRange = monthlyPercentile_75InRange;
        propagateHierarchyIdToField(monthlyPercentile_75InRange, "monthly_percentile_75_in_range");
    }

    /**
     * Returns a percentile 90 in range check specification.
     * @return Percentile 90 in range check specification.
     */
    public ColumnPercentile90InRangeCheckSpec getMonthlyPercentile_90InRange() {
        return monthlyPercentile_90InRange;
    }

    /**
     * Sets a new specification of a percentile 90 in range check.
     * @param monthlyPercentile_90InRange Percentile 90 in range check specification.
     */
    public void setMonthlyPercentile_90InRange(ColumnPercentile90InRangeCheckSpec monthlyPercentile_90InRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPercentile_90InRange, monthlyPercentile_90InRange));
        this.monthlyPercentile_90InRange = monthlyPercentile_90InRange;
        propagateHierarchyIdToField(monthlyPercentile_90InRange, "monthly_percentile_90_in_range");
    }


    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleStddevInRangeCheckSpec getMonthlySampleStddevInRange() {
        return monthlySampleStddevInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param monthlySampleStddevInRange Sample standard deviation in range check specification.
     */
    public void setMonthlySampleStddevInRange(ColumnSampleStddevInRangeCheckSpec monthlySampleStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlySampleStddevInRange, monthlySampleStddevInRange));
        this.monthlySampleStddevInRange = monthlySampleStddevInRange;
        propagateHierarchyIdToField(monthlySampleStddevInRange, "monthly_sample_stddev_in_range");
    }

    /**
     * Returns a population standard deviation in range check specification.
     * @return Population standard deviation in range check specification.
     */
    public ColumnPopulationStddevInRangeCheckSpec getMonthlyPopulationStddevInRange() {
        return monthlyPopulationStddevInRange;
    }

    /**
     * Sets a new specification of a population standard deviation in range check.
     * @param monthlyPopulationStddevInRange Population standard deviation in range check specification.
     */
    public void setMonthlyPopulationStddevInRange(ColumnPopulationStddevInRangeCheckSpec monthlyPopulationStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPopulationStddevInRange, monthlyPopulationStddevInRange));
        this.monthlyPopulationStddevInRange = monthlyPopulationStddevInRange;
        propagateHierarchyIdToField(monthlyPopulationStddevInRange, "monthly_population_stddev_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleVarianceInRangeCheckSpec getMonthlySampleVarianceInRange() {
        return monthlySampleVarianceInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param monthlySampleVarianceInRange Sample standard deviation in range check specification.
     */
    public void setMonthlySampleVarianceInRange(ColumnSampleVarianceInRangeCheckSpec monthlySampleVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlySampleVarianceInRange, monthlySampleVarianceInRange));
        this.monthlySampleVarianceInRange = monthlySampleVarianceInRange;
        propagateHierarchyIdToField(monthlySampleVarianceInRange, "monthly_sample_variance_in_range");
    }

    /**
     * Returns a population variance in range check specification.
     * @return Population variance in range check specification.
     */
    public ColumnPopulationVarianceInRangeCheckSpec getMonthlyPopulationVarianceInRange() {
        return monthlyPopulationVarianceInRange;
    }

    /**
     * Sets a new specification of a population variance in range check.
     * @param monthlyPopulationVarianceInRange Population variance in range check specification.
     */
    public void setMonthlyPopulationVarianceInRange(ColumnPopulationVarianceInRangeCheckSpec monthlyPopulationVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPopulationVarianceInRange, monthlyPopulationVarianceInRange));
        this.monthlyPopulationVarianceInRange = monthlyPopulationVarianceInRange;
        propagateHierarchyIdToField(monthlyPopulationVarianceInRange, "monthly_population_variance_in_range");
    }

    /**
     * Returns a sum in range check specification.
     * @return Sum in range check specification.
     */
    public ColumnSumInRangeCheckSpec getMonthlySumInRange() {
        return monthlySumInRange;
    }

    /**
     * Sets a new specification of a sum in range check.
     * @param monthlySumInRange Sum in range check specification.
     */
    public void setMonthlySumInRange(ColumnSumInRangeCheckSpec monthlySumInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlySumInRange, monthlySumInRange));
        this.monthlySumInRange = monthlySumInRange;
        propagateHierarchyIdToField(monthlySumInRange, "monthly_sum_in_range");
    }

    /**
     * Returns an invalid latitude count check specification.
     * @return Invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getMonthlyInvalidLatitudeCount() {
        return monthlyInvalidLatitudeCount;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param monthlyInvalidLatitudeCount Invalid latitude count check specification.
     */
    public void setMonthlyInvalidLatitudeCount(ColumnInvalidLatitudeCountCheckSpec monthlyInvalidLatitudeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyInvalidLatitudeCount, monthlyInvalidLatitudeCount));
        this.monthlyInvalidLatitudeCount = monthlyInvalidLatitudeCount;
        propagateHierarchyIdToField(monthlyInvalidLatitudeCount, "monthly_invalid_latitude_count");
    }

    /**
     * Returns a valid latitude percent check specification.
     * @return Valid latitude percent check specification.
     */
    public ColumnValidLatitudePercentCheckSpec getMonthlyValidLatitudePercent() {
        return monthlyValidLatitudePercent;
    }

    /**
     * Sets a new specification of a valid latitude percent check.
     * @param monthlyValidLatitudePercent Valid latitude percent check specification.
     */
    public void setMonthlyValidLatitudePercent(ColumnValidLatitudePercentCheckSpec monthlyValidLatitudePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyValidLatitudePercent, monthlyValidLatitudePercent));
        this.monthlyValidLatitudePercent = monthlyValidLatitudePercent;
        propagateHierarchyIdToField(monthlyValidLatitudePercent, "monthly_valid_latitude_percent");
    }

    /**
     * Returns an invalid longitude count check specification.
     * @return Invalid longitude count check specification.
     */
    public ColumnInvalidLongitudeCountCheckSpec getMonthlyInvalidLongitudeCount() {
        return monthlyInvalidLongitudeCount;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param monthlyInvalidLongitudeCount Invalid longitude count check specification.
     */
    public void setMonthlyInvalidLongitudeCount(ColumnInvalidLongitudeCountCheckSpec monthlyInvalidLongitudeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyInvalidLongitudeCount, monthlyInvalidLongitudeCount));
        this.monthlyInvalidLongitudeCount = monthlyInvalidLongitudeCount;
        propagateHierarchyIdToField(monthlyInvalidLongitudeCount, "monthly_invalid_longitude_count");
    }

    /**
     * Returns a valid longitude percent check specification.
     * @return Valid longitude percent check specification.
     */
    public ColumnValidLongitudePercentCheckSpec getMonthlyValidLongitudePercent() {
        return monthlyValidLongitudePercent;
    }

    /**
     * Sets a new specification of a valid longitude percent check.
     * @param monthlyValidLongitudePercent Valid longitude percent check specification.
     */
    public void setMonthlyValidLongitudePercent(ColumnValidLongitudePercentCheckSpec monthlyValidLongitudePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyValidLongitudePercent, monthlyValidLongitudePercent));
        this.monthlyValidLongitudePercent = monthlyValidLongitudePercent;
        propagateHierarchyIdToField(monthlyValidLongitudePercent, "monthly_valid_longitude_percent");
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
    public ColumnNumericMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnNumericMonthlyMonitoringChecksSpec)super.deepClone();
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