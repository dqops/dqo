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
            put("monthly_number_below_min_value", o -> o.monthlyNumberBelowMinValue);
            put("monthly_number_above_max_value", o -> o.monthlyNumberAboveMaxValue);
            put("monthly_negative_values", o -> o.monthlyNegativeValues);
            put("monthly_negative_values_percent", o -> o.monthlyNegativeValuesPercent);
            put("monthly_number_below_min_value_percent", o -> o.monthlyNumberBelowMinValuePercent);
            put("monthly_number_above_max_value_percent", o -> o.monthlyNumberAboveMaxValuePercent);
            put("monthly_number_in_range_percent", o -> o.monthlyNumberInRangePercent);
            put("monthly_integer_in_range_percent", o -> o.monthlyIntegerInRangePercent);
            put("monthly_min_in_range", o -> o.monthlyMinInRange);
            put("monthly_max_in_range", o -> o.monthlyMaxInRange);
            put("monthly_sum_in_range", o -> o.monthlySumInRange);
            put("monthly_mean_in_range", o -> o.monthlyMeanInRange);
            put("monthly_median_in_range", o -> o.monthlyMedianInRange);
            put("monthly_percentile_in_range", o -> o.monthlyPercentileInRange);
            put("monthly_percentile_10_in_range", o -> o.monthlyPercentile_10InRange);
            put("monthly_percentile_25_in_range", o -> o.monthlyPercentile_25InRange);
            put("monthly_percentile_75_in_range", o -> o.monthlyPercentile_75InRange);
            put("monthly_percentile_90_in_range", o -> o.monthlyPercentile_90InRange);
            put("monthly_sample_stddev_in_range", o -> o.monthlySampleStddevInRange);
            put("monthly_population_stddev_in_range", o -> o.monthlyPopulationStddevInRange);
            put("monthly_sample_variance_in_range", o -> o.monthlySampleVarianceInRange);
            put("monthly_population_variance_in_range", o -> o.monthlyPopulationVarianceInRange);
            put("monthly_invalid_latitude", o -> o.monthlyInvalidLatitude);
            put("monthly_valid_latitude_percent", o -> o.monthlyValidLatitudePercent);
            put("monthly_invalid_longitude", o -> o.monthlyInvalidLongitude);
            put("monthly_valid_longitude_percent", o -> o.monthlyValidLongitudePercent);
            put("monthly_non_negative_values", o -> o.monthlyNonNegativeValues);
            put("monthly_non_negative_values_percent", o -> o.monthlyNonNegativeValuesPercent);
        }
    };

    @JsonPropertyDescription("The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnNumberBelowMinValueCheckSpec monthlyNumberBelowMinValue;

    @JsonPropertyDescription("The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnNumberAboveMaxValueCheckSpec monthlyNumberAboveMaxValue;

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnNegativeCountCheckSpec monthlyNegativeValues;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnNegativePercentCheckSpec monthlyNegativeValuesPercent;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnNumberBelowMinValuePercentCheckSpec monthlyNumberBelowMinValuePercent;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnNumberAboveMaxValuePercentCheckSpec monthlyNumberAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnNumberInRangePercentCheckSpec monthlyNumberInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnIntegerInRangePercentCheckSpec monthlyIntegerInRangePercent;

    @JsonPropertyDescription("Verifies that the minimum value in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnMinInRangeCheckSpec monthlyMinInRange;

    @JsonPropertyDescription("Verifies that the maximum value in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnMaxInRangeCheckSpec monthlyMaxInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnSumInRangeCheckSpec monthlySumInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnMeanInRangeCheckSpec monthlyMeanInRange;

    @JsonPropertyDescription("Verifies that the median of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnMedianInRangeCheckSpec monthlyMedianInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnPercentileInRangeCheckSpec monthlyPercentileInRange;

    @JsonPropertyDescription("Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnPercentile10InRangeCheckSpec monthlyPercentile_10InRange;

    @JsonPropertyDescription("Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnPercentile25InRangeCheckSpec monthlyPercentile_25InRange;

    @JsonPropertyDescription("Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnPercentile75InRangeCheckSpec monthlyPercentile_75InRange;

    @JsonPropertyDescription("Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnPercentile90InRangeCheckSpec monthlyPercentile_90InRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnSampleStddevInRangeCheckSpec monthlySampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnPopulationStddevInRangeCheckSpec monthlyPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnSampleVarianceInRangeCheckSpec monthlySampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnPopulationVarianceInRangeCheckSpec monthlyPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnInvalidLatitudeCountCheckSpec monthlyInvalidLatitude;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnValidLatitudePercentCheckSpec monthlyValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnInvalidLongitudeCountCheckSpec monthlyInvalidLongitude;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnValidLongitudePercentCheckSpec monthlyValidLongitudePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnNonNegativeCountCheckSpec monthlyNonNegativeValues;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnNonNegativePercentCheckSpec monthlyNonNegativeValuesPercent;


    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnNumberBelowMinValueCheckSpec getMonthlyNumberBelowMinValue() {
        return monthlyNumberBelowMinValue;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param monthlyNumberBelowMinValue Numeric value below min value count check.
     */
    public void setMonthlyNumberBelowMinValue(ColumnNumberBelowMinValueCheckSpec monthlyNumberBelowMinValue) {
        this.setDirtyIf(!Objects.equals(this.monthlyNumberBelowMinValue, monthlyNumberBelowMinValue));
        this.monthlyNumberBelowMinValue = monthlyNumberBelowMinValue;
        propagateHierarchyIdToField(monthlyNumberBelowMinValue, "monthly_number_below_min_value");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnNumberAboveMaxValueCheckSpec getMonthlyNumberAboveMaxValue() {
        return monthlyNumberAboveMaxValue;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param monthlyNumberAboveMaxValue Numeric value above max value count check.
     */
    public void setMonthlyNumberAboveMaxValue(ColumnNumberAboveMaxValueCheckSpec monthlyNumberAboveMaxValue) {
        this.setDirtyIf(!Objects.equals(this.monthlyNumberAboveMaxValue, monthlyNumberAboveMaxValue));
        this.monthlyNumberAboveMaxValue = monthlyNumberAboveMaxValue;
        propagateHierarchyIdToField(monthlyNumberAboveMaxValue, "monthly_number_above_max_value");
    }

    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getMonthlyNegativeValues() {
        return monthlyNegativeValues;
    }

    /**
     * Sets a new specification of a negative values count check.
     * @param monthlyNegativeValues Negative values count check specification.
     */
    public void setMonthlyNegativeValues(ColumnNegativeCountCheckSpec monthlyNegativeValues) {
        this.setDirtyIf(!Objects.equals(this.monthlyNegativeValues, monthlyNegativeValues));
        this.monthlyNegativeValues = monthlyNegativeValues;
        propagateHierarchyIdToField(monthlyNegativeValues, "monthly_negative_values");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getMonthlyNegativeValuesPercent() {
        return monthlyNegativeValuesPercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param monthlyNegativeValuesPercent Negative values percentage check specification.
     */
    public void setMonthlyNegativeValuesPercent(ColumnNegativePercentCheckSpec monthlyNegativeValuesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNegativeValuesPercent, monthlyNegativeValuesPercent));
        this.monthlyNegativeValuesPercent = monthlyNegativeValuesPercent;
        propagateHierarchyIdToField(monthlyNegativeValuesPercent, "monthly_negative_values_percent");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnNumberBelowMinValuePercentCheckSpec getMonthlyNumberBelowMinValuePercent() {
        return monthlyNumberBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param monthlyNumberBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setMonthlyNumberBelowMinValuePercent(ColumnNumberBelowMinValuePercentCheckSpec monthlyNumberBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNumberBelowMinValuePercent, monthlyNumberBelowMinValuePercent));
        this.monthlyNumberBelowMinValuePercent = monthlyNumberBelowMinValuePercent;
        propagateHierarchyIdToField(monthlyNumberBelowMinValuePercent, "monthly_number_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnNumberAboveMaxValuePercentCheckSpec getMonthlyNumberAboveMaxValuePercent() {
        return monthlyNumberAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param monthlyNumberAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setMonthlyNumberAboveMaxValuePercent(ColumnNumberAboveMaxValuePercentCheckSpec monthlyNumberAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNumberAboveMaxValuePercent, monthlyNumberAboveMaxValuePercent));
        this.monthlyNumberAboveMaxValuePercent = monthlyNumberAboveMaxValuePercent;
        propagateHierarchyIdToField(monthlyNumberAboveMaxValuePercent, "monthly_number_above_max_value_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return numbers in set percent check specification.
     */
    public ColumnNumberInRangePercentCheckSpec getMonthlyNumberInRangePercent() {
        return monthlyNumberInRangePercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param monthlyNumberInRangePercent Numbers in set percent check specification.
     */
    public void setMonthlyNumberInRangePercent(ColumnNumberInRangePercentCheckSpec monthlyNumberInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNumberInRangePercent, monthlyNumberInRangePercent));
        this.monthlyNumberInRangePercent = monthlyNumberInRangePercent;
        propagateHierarchyIdToField(monthlyNumberInRangePercent, "monthly_number_in_range_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnIntegerInRangePercentCheckSpec getMonthlyIntegerInRangePercent() {
        return monthlyIntegerInRangePercent;
    }

    /**
     * Sets a new definition of a numbers in set percent check.
     * @param monthlyIntegerInRangePercent Numbers in set percent check specification.
     */
    public void setMonthlyIntegerInRangePercent(ColumnIntegerInRangePercentCheckSpec monthlyIntegerInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyIntegerInRangePercent, monthlyIntegerInRangePercent));
        this.monthlyIntegerInRangePercent = monthlyIntegerInRangePercent;
        propagateHierarchyIdToField(monthlyIntegerInRangePercent, "monthly_integer_in_range_percent");
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
     * Returns an invalid latitude count check specification.
     * @return Invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getMonthlyInvalidLatitude() {
        return monthlyInvalidLatitude;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param monthlyInvalidLatitude Invalid latitude count check specification.
     */
    public void setMonthlyInvalidLatitude(ColumnInvalidLatitudeCountCheckSpec monthlyInvalidLatitude) {
        this.setDirtyIf(!Objects.equals(this.monthlyInvalidLatitude, monthlyInvalidLatitude));
        this.monthlyInvalidLatitude = monthlyInvalidLatitude;
        propagateHierarchyIdToField(monthlyInvalidLatitude, "monthly_invalid_latitude");
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
    public ColumnInvalidLongitudeCountCheckSpec getMonthlyInvalidLongitude() {
        return monthlyInvalidLongitude;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param monthlyInvalidLongitude Invalid longitude count check specification.
     */
    public void setMonthlyInvalidLongitude(ColumnInvalidLongitudeCountCheckSpec monthlyInvalidLongitude) {
        this.setDirtyIf(!Objects.equals(this.monthlyInvalidLongitude, monthlyInvalidLongitude));
        this.monthlyInvalidLongitude = monthlyInvalidLongitude;
        propagateHierarchyIdToField(monthlyInvalidLongitude, "monthly_invalid_longitude");
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
     * Returns a non-negative values count check specification.
     * @return Non-negative values count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getMonthlyNonNegativeValues() {
        return monthlyNonNegativeValues;
    }

    /**
     * Sets a new specification of a non-negative values count check.
     * @param monthlyNonNegativeValues Non-negative values count check specification.
     */
    public void setMonthlyNonNegativeValues(ColumnNonNegativeCountCheckSpec monthlyNonNegativeValues) {
        this.setDirtyIf(!Objects.equals(this.monthlyNonNegativeValues, monthlyNonNegativeValues));
        this.monthlyNonNegativeValues = monthlyNonNegativeValues;
        propagateHierarchyIdToField(monthlyNonNegativeValues, "monthly_non_negative_values");
    }

    /**
     * Returns a non-negative values percentage check specification.
     * @return Non-negative values percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getMonthlyNonNegativeValuesPercent() {
        return monthlyNonNegativeValuesPercent;
    }

    /**
     * Sets a new specification of a non-negative values percentage check.
     * @param monthlyNonNegativeValuesPercent Non-negative values percentage check specification.
     */
    public void setMonthlyNonNegativeValuesPercent(ColumnNonNegativePercentCheckSpec monthlyNonNegativeValuesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNonNegativeValuesPercent, monthlyNonNegativeValuesPercent));
        this.monthlyNonNegativeValuesPercent = monthlyNonNegativeValuesPercent;
        propagateHierarchyIdToField(monthlyNonNegativeValuesPercent, "monthly_non_negative_values_percent");
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