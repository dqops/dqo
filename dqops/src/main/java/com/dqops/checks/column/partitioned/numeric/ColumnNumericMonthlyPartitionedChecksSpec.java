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
package com.dqops.checks.column.partitioned.numeric;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.numeric.*;
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
 * Container of numeric data quality partitioned checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_number_below_min_value", o -> o.monthlyPartitionNumberBelowMinValue);
            put("monthly_partition_number_above_max_value", o -> o.monthlyPartitionNumberAboveMaxValue);
            put("monthly_partition_negative_values", o -> o.monthlyPartitionNegativeValues);
            put("monthly_partition_negative_values_percent", o -> o.monthlyPartitionNegativeValuesPercent);
            put("monthly_partition_number_below_min_value_percent", o -> o.monthlyPartitionNumberBelowMinValuePercent);
            put("monthly_partition_number_above_max_value_percent", o -> o.monthlyPartitionNumberAboveMaxValuePercent);
            put("monthly_partition_number_in_range_percent", o -> o.monthlyPartitionNumberInRangePercent);
            put("monthly_partition_integer_in_range_percent", o -> o.monthlyPartitionIntegerInRangePercent);
            put("monthly_partition_min_in_range", o -> o.monthlyPartitionMinInRange);
            put("monthly_partition_max_in_range", o -> o.monthlyPartitionMaxInRange);
            put("monthly_partition_sum_in_range", o -> o.monthlyPartitionSumInRange);
            put("monthly_partition_mean_in_range", o -> o.monthlyPartitionMeanInRange);
            put("monthly_partition_median_in_range", o -> o.monthlyPartitionMedianInRange);
            put("monthly_partition_percentile_in_range", o -> o.monthlyPartitionPercentileInRange);
            put("monthly_partition_percentile_10_in_range", o -> o.monthlyPartitionPercentile_10InRange);
            put("monthly_partition_percentile_25_in_range", o -> o.monthlyPartitionPercentile_25InRange);
            put("monthly_partition_percentile_75_in_range", o -> o.monthlyPartitionPercentile_75InRange);
            put("monthly_partition_percentile_90_in_range", o -> o.monthlyPartitionPercentile_90InRange);
            put("monthly_partition_sample_stddev_in_range", o -> o.monthlyPartitionSampleStddevInRange);
            put("monthly_partition_population_stddev_in_range", o -> o.monthlyPartitionPopulationStddevInRange);
            put("monthly_partition_sample_variance_in_range", o -> o.monthlyPartitionSampleVarianceInRange);
            put("monthly_partition_population_variance_in_range", o -> o.monthlyPartitionPopulationVarianceInRange);
            put("monthly_partition_invalid_latitude", o -> o.monthlyPartitionInvalidLatitude);
            put("monthly_partition_valid_latitude_percent", o -> o.monthlyPartitionValidLatitudePercent);
            put("monthly_partition_invalid_longitude", o -> o.monthlyPartitionInvalidLongitude);
            put("monthly_partition_valid_longitude_percent", o -> o.monthlyPartitionValidLongitudePercent);
            put("monthly_partition_non_negative_values", o -> o.monthlyPartitionNonNegativeValues);
            put("monthly_partition_non_negative_values_percent", o -> o.monthlyPartitionNonNegativeValuesPercent);
        }
    };

    @JsonPropertyDescription("The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.")
    private ColumnNumberBelowMinValueCheckSpec monthlyPartitionNumberBelowMinValue;

    @JsonPropertyDescription("The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.")
    private ColumnNumberAboveMaxValueCheckSpec monthlyPartitionNumberAboveMaxValue;

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.")
    private ColumnNegativeCountCheckSpec monthlyPartitionNegativeValues;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnNegativePercentCheckSpec monthlyPartitionNegativeValuesPercent;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.")
    private ColumnNumberBelowMinValuePercentCheckSpec monthlyPartitionNumberBelowMinValuePercent;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.")
    private ColumnNumberAboveMaxValuePercentCheckSpec monthlyPartitionNumberAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnNumberInRangePercentCheckSpec monthlyPartitionNumberInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnIntegerInRangePercentCheckSpec monthlyPartitionIntegerInRangePercent;

    @JsonPropertyDescription("Verifies that the minimum value in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnMinInRangeCheckSpec monthlyPartitionMinInRange;

    @JsonPropertyDescription("Verifies that the maximum value in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnMaxInRangeCheckSpec monthlyPartitionMaxInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnSumInRangeCheckSpec monthlyPartitionSumInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnMeanInRangeCheckSpec monthlyPartitionMeanInRange;

    @JsonPropertyDescription("Verifies that the median of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnMedianInRangeCheckSpec monthlyPartitionMedianInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnPercentileInRangeCheckSpec monthlyPartitionPercentileInRange;

    @JsonPropertyDescription("Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnPercentile10InRangeCheckSpec monthlyPartitionPercentile_10InRange;

    @JsonPropertyDescription("Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnPercentile25InRangeCheckSpec monthlyPartitionPercentile_25InRange;

    @JsonPropertyDescription("Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnPercentile75InRangeCheckSpec monthlyPartitionPercentile_75InRange;

    @JsonPropertyDescription("Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnPercentile90InRangeCheckSpec monthlyPartitionPercentile_90InRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnSampleStddevInRangeCheckSpec monthlyPartitionSampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnPopulationStddevInRangeCheckSpec monthlyPartitionPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnSampleVarianceInRangeCheckSpec monthlyPartitionSampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.")
    private ColumnPopulationVarianceInRangeCheckSpec monthlyPartitionPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.")
    private ColumnInvalidLatitudeCountCheckSpec monthlyPartitionInvalidLatitude;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnValidLatitudePercentCheckSpec monthlyPartitionValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.")
    private ColumnInvalidLongitudeCountCheckSpec monthlyPartitionInvalidLongitude;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnValidLongitudePercentCheckSpec monthlyPartitionValidLongitudePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.")
    private ColumnNonNegativeCountCheckSpec monthlyPartitionNonNegativeValues;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnNonNegativePercentCheckSpec monthlyPartitionNonNegativeValuesPercent;

    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnNumberBelowMinValueCheckSpec getMonthlyPartitionNumberBelowMinValue() {
        return monthlyPartitionNumberBelowMinValue;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param monthlyPartitionNumberBelowMinValue Numeric value below min value count check.
     */
    public void setMonthlyPartitionNumberBelowMinValue(ColumnNumberBelowMinValueCheckSpec monthlyPartitionNumberBelowMinValue) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumberBelowMinValue, monthlyPartitionNumberBelowMinValue));
        this.monthlyPartitionNumberBelowMinValue = monthlyPartitionNumberBelowMinValue;
        propagateHierarchyIdToField(monthlyPartitionNumberBelowMinValue, "monthly_partition_number_below_min_value");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnNumberAboveMaxValueCheckSpec getMonthlyPartitionNumberAboveMaxValue() {
        return monthlyPartitionNumberAboveMaxValue;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param monthlyPartitionNumberAboveMaxValue Numeric value above max value count check.
     */
    public void setMonthlyPartitionNumberAboveMaxValue(ColumnNumberAboveMaxValueCheckSpec monthlyPartitionNumberAboveMaxValue) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumberAboveMaxValue, monthlyPartitionNumberAboveMaxValue));
        this.monthlyPartitionNumberAboveMaxValue = monthlyPartitionNumberAboveMaxValue;
        propagateHierarchyIdToField(monthlyPartitionNumberAboveMaxValue, "monthly_partition_number_above_max_value");
    }

    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getMonthlyPartitionNegativeValues() {
        return monthlyPartitionNegativeValues;
    }

    /**
     * Sets a new specification of a negative values count check.
     * @param monthlyPartitionNegativeValues Negative values count check specification.
     */
    public void setMonthlyPartitionNegativeValues(ColumnNegativeCountCheckSpec monthlyPartitionNegativeValues) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNegativeValues, monthlyPartitionNegativeValues));
        this.monthlyPartitionNegativeValues = monthlyPartitionNegativeValues;
        propagateHierarchyIdToField(monthlyPartitionNegativeValues, "monthly_partition_negative_values");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getMonthlyPartitionNegativeValuesPercent() {
        return monthlyPartitionNegativeValuesPercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param monthlyPartitionNegativeValuesPercent Negative values percentage check specification.
     */
    public void setMonthlyPartitionNegativeValuesPercent(ColumnNegativePercentCheckSpec monthlyPartitionNegativeValuesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNegativeValuesPercent, monthlyPartitionNegativeValuesPercent));
        this.monthlyPartitionNegativeValuesPercent = monthlyPartitionNegativeValuesPercent;
        propagateHierarchyIdToField(monthlyPartitionNegativeValuesPercent, "monthly_partition_negative_values_percent");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnNumberBelowMinValuePercentCheckSpec getMonthlyPartitionNumberBelowMinValuePercent() {
        return monthlyPartitionNumberBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param monthlyPartitionNumberBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setMonthlyPartitionNumberBelowMinValuePercent(ColumnNumberBelowMinValuePercentCheckSpec monthlyPartitionNumberBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumberBelowMinValuePercent, monthlyPartitionNumberBelowMinValuePercent));
        this.monthlyPartitionNumberBelowMinValuePercent = monthlyPartitionNumberBelowMinValuePercent;
        propagateHierarchyIdToField(monthlyPartitionNumberBelowMinValuePercent, "monthly_partition_number_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnNumberAboveMaxValuePercentCheckSpec getMonthlyPartitionNumberAboveMaxValuePercent() {
        return monthlyPartitionNumberAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param monthlyPartitionNumberAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setMonthlyPartitionNumberAboveMaxValuePercent(ColumnNumberAboveMaxValuePercentCheckSpec monthlyPartitionNumberAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumberAboveMaxValuePercent, monthlyPartitionNumberAboveMaxValuePercent));
        this.monthlyPartitionNumberAboveMaxValuePercent = monthlyPartitionNumberAboveMaxValuePercent;
        propagateHierarchyIdToField(monthlyPartitionNumberAboveMaxValuePercent, "monthly_partition_number_above_max_value_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberInRangePercentCheckSpec getMonthlyPartitionNumberInRangePercent() {
        return monthlyPartitionNumberInRangePercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param monthlyPartitionNumberInRangePercent Numbers in set percent check specification.
     */
    public void setMonthlyPartitionNumberInRangePercent(ColumnNumberInRangePercentCheckSpec monthlyPartitionNumberInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumberInRangePercent, monthlyPartitionNumberInRangePercent));
        this.monthlyPartitionNumberInRangePercent = monthlyPartitionNumberInRangePercent;
        propagateHierarchyIdToField(monthlyPartitionNumberInRangePercent, "monthly_partition_number_in_range_percent");
    }


    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnIntegerInRangePercentCheckSpec getMonthlyPartitionIntegerInRangePercent() {
        return monthlyPartitionIntegerInRangePercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param monthlyPartitionIntegerInRangePercent Numbers in set percent check specification.
     */
    public void setMonthlyPartitionIntegerInRangePercent(ColumnIntegerInRangePercentCheckSpec monthlyPartitionIntegerInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionIntegerInRangePercent, monthlyPartitionIntegerInRangePercent));
        this.monthlyPartitionIntegerInRangePercent = monthlyPartitionIntegerInRangePercent;
        propagateHierarchyIdToField(monthlyPartitionIntegerInRangePercent, "monthly_partition_integer_in_range_percent");
    }

    /**
     * Returns a min in range check specification.
     * @return Min in range check specification.
     */
    public ColumnMinInRangeCheckSpec getMonthlyPartitionMinInRange() {
        return monthlyPartitionMinInRange;
    }

    /**
     * Sets a new specification of a min in range check.
     * @param monthlyPartitionMinInRange Min in range check specification.
     */
    public void setMonthlyPartitionMinInRange(ColumnMinInRangeCheckSpec monthlyPartitionMinInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinInRange, monthlyPartitionMinInRange));
        this.monthlyPartitionMinInRange = monthlyPartitionMinInRange;
        propagateHierarchyIdToField(monthlyPartitionMinInRange, "monthly_partition_min_in_range");
    }

    /**
     * Returns a max in range check specification.
     * @return Max in range check specification.
     */
    public ColumnMaxInRangeCheckSpec getMonthlyPartitionMaxInRange() {
        return monthlyPartitionMaxInRange;
    }

    /**
     * Sets a new specification of a max in range check.
     * @param monthlyPartitionMaxInRange Max in range check specification.
     */
    public void setMonthlyPartitionMaxInRange(ColumnMaxInRangeCheckSpec monthlyPartitionMaxInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxInRange, monthlyPartitionMaxInRange));
        this.monthlyPartitionMaxInRange = monthlyPartitionMaxInRange;
        propagateHierarchyIdToField(monthlyPartitionMaxInRange, "monthly_partition_max_in_range");
    }

    /**
     * Returns a sum in range check specification.
     * @return Sum in range check specification.
     */
    public ColumnSumInRangeCheckSpec getMonthlyPartitionSumInRange() {
        return monthlyPartitionSumInRange;
    }

    /**
     * Sets a new specification of a sum in range check.
     * @param monthlyPartitionSumInRange Sum in range check specification.
     */
    public void setMonthlyPartitionSumInRange(ColumnSumInRangeCheckSpec monthlyPartitionSumInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSumInRange, monthlyPartitionSumInRange));
        this.monthlyPartitionSumInRange = monthlyPartitionSumInRange;
        propagateHierarchyIdToField(monthlyPartitionSumInRange, "monthly_partition_sum_in_range");
    }

    /**
     * Returns a mean in range check specification.
     * @return mean in range check specification.
     */
    public ColumnMeanInRangeCheckSpec getMonthlyPartitionMeanInRange() {
        return monthlyPartitionMeanInRange;
    }

    /**
     * Sets a new specification of a mean in range check.
     * @param monthlyPartitionMeanInRange Mean in range check specification.
     */
    public void setMonthlyPartitionMeanInRange(ColumnMeanInRangeCheckSpec monthlyPartitionMeanInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMeanInRange, monthlyPartitionMeanInRange));
        this.monthlyPartitionMeanInRange = monthlyPartitionMeanInRange;
        propagateHierarchyIdToField(monthlyPartitionMeanInRange, "monthly_partition_mean_in_range");
    }

    /**
     * Returns a median in range check specification.
     * @return median in range check specification.
     */
    public ColumnMedianInRangeCheckSpec getMonthlyPartitionMedianInRange() {
        return monthlyPartitionMedianInRange;
    }

    /**
     * Sets a new specification of a median in range check.
     * @param monthlyPartitionMedianInRange median in range check specification.
     */
    public void setMonthlyPartitionMedianInRange(ColumnMedianInRangeCheckSpec monthlyPartitionMedianInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMedianInRange, monthlyPartitionMedianInRange));
        this.monthlyPartitionMedianInRange = monthlyPartitionMedianInRange;
        propagateHierarchyIdToField(monthlyPartitionMedianInRange, "monthly_partition_median_in_range");
    }

    /**
     * Returns a percentile in range check specification.
     * @return Percentile in range check specification.
     */
    public ColumnPercentileInRangeCheckSpec getMonthlyPartitionPercentileInRange() {
        return monthlyPartitionPercentileInRange;
    }

    /**
     * Sets a new specification of a percentile in range check.
     * @param monthlyPartitionPercentileInRange percentile in range check specification.
     */
    public void setMonthlyPartitionPercentileInRange(ColumnPercentileInRangeCheckSpec monthlyPartitionPercentileInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionPercentileInRange, monthlyPartitionPercentileInRange));
        this.monthlyPartitionPercentileInRange = monthlyPartitionPercentileInRange;
        propagateHierarchyIdToField(monthlyPartitionPercentileInRange, "monthly_partition_percentile_in_range");
    }

    /**
     * Returns a percentile 10 in range check specification.
     * @return Percentile 10 in range check specification.
     */
    public ColumnPercentile10InRangeCheckSpec getMonthlyPartitionPercentile_10InRange() {
        return monthlyPartitionPercentile_10InRange;
    }

    /**
     * Sets a new specification of a percentile 10 in range check.
     * @param monthlyPartitionPercentile_10InRange percentile 10 in range check specification.
     */
    public void setMonthlyPartitionPercentile_10InRange(ColumnPercentile10InRangeCheckSpec monthlyPartitionPercentile_10InRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionPercentile_10InRange, monthlyPartitionPercentile_10InRange));
        this.monthlyPartitionPercentile_10InRange = monthlyPartitionPercentile_10InRange;
        propagateHierarchyIdToField(monthlyPartitionPercentile_10InRange, "monthly_partition_percentile_10_in_range");
    }

    /**
     * Returns a percentile 25 in range check specification.
     * @return Percentile 25 in range check specification.
     */
    public ColumnPercentile25InRangeCheckSpec getMonthlyPartitionPercentile_25InRange() {
        return monthlyPartitionPercentile_25InRange;
    }

    /**
     * Sets a new specification of a percentile 25 in range check.
     * @param monthlyPartitionPercentile_25InRange percentile 25 in range check specification.
     */
    public void setMonthlyPartitionPercentile_25InRange(ColumnPercentile25InRangeCheckSpec monthlyPartitionPercentile_25InRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionPercentile_25InRange, monthlyPartitionPercentile_25InRange));
        this.monthlyPartitionPercentile_25InRange = monthlyPartitionPercentile_25InRange;
        propagateHierarchyIdToField(monthlyPartitionPercentile_25InRange, "monthly_partition_percentile_25_in_range");
    }

    /**
     * Returns a percentile 75 in range check specification.
     * @return Percentile 75 in range check specification.
     */
    public ColumnPercentile75InRangeCheckSpec getMonthlyPartitionPercentile_75InRange() {
        return monthlyPartitionPercentile_75InRange;
    }

    /**
     * Sets a new specification of a percentile 75 in range check.
     * @param monthlyPartitionPercentile_75InRange percentile 75 in range check specification.
     */
    public void setMonthlyPartitionPercentile_75InRange(ColumnPercentile75InRangeCheckSpec monthlyPartitionPercentile_75InRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionPercentile_75InRange, monthlyPartitionPercentile_75InRange));
        this.monthlyPartitionPercentile_75InRange = monthlyPartitionPercentile_75InRange;
        propagateHierarchyIdToField(monthlyPartitionPercentile_75InRange, "monthly_partition_percentile_75_in_range");
    }

    /**
     * Returns a percentile 90 in range check specification.
     * @return Percentile 90 in range check specification.
     */
    public ColumnPercentile90InRangeCheckSpec getMonthlyPartitionPercentile_90InRange() {
        return monthlyPartitionPercentile_90InRange;
    }

    /**
     * Sets a new specification of a percentile 90 in range check.
     * @param monthlyPartitionPercentile_90InRange percentile 90 in range check specification.
     */
    public void setMonthlyPartitionPercentile_90InRange(ColumnPercentile90InRangeCheckSpec monthlyPartitionPercentile_90InRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionPercentile_90InRange, monthlyPartitionPercentile_90InRange));
        this.monthlyPartitionPercentile_90InRange = monthlyPartitionPercentile_90InRange;
        propagateHierarchyIdToField(monthlyPartitionPercentile_90InRange, "monthly_partition_percentile_90_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleStddevInRangeCheckSpec getMonthlyPartitionSampleStddevInRange() {
        return monthlyPartitionSampleStddevInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param monthlyPartitionSampleStddevInRange Sample standard deviation in range check specification.
     */
    public void setMonthlyPartitionSampleStddevInRange(ColumnSampleStddevInRangeCheckSpec monthlyPartitionSampleStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSampleStddevInRange, monthlyPartitionSampleStddevInRange));
        this.monthlyPartitionSampleStddevInRange = monthlyPartitionSampleStddevInRange;
        propagateHierarchyIdToField(monthlyPartitionSampleStddevInRange, "monthly_partition_sample_stddev_in_range");
    }

    /**
     * Returns a population standard deviation in range check specification.
     * @return Population standard deviation in range check specification.
     */
    public ColumnPopulationStddevInRangeCheckSpec getMonthlyPartitionPopulationStddevInRange() {
        return monthlyPartitionPopulationStddevInRange;
    }

    /**
     * Sets a new specification of a population standard deviation in range check.
     * @param monthlyPartitionPopulationStddevInRange Population standard deviation in range check specification.
     */
    public void setMonthlyPartitionPopulationStddevInRange(ColumnPopulationStddevInRangeCheckSpec monthlyPartitionPopulationStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionPopulationStddevInRange, monthlyPartitionPopulationStddevInRange));
        this.monthlyPartitionPopulationStddevInRange = monthlyPartitionPopulationStddevInRange;
        propagateHierarchyIdToField(monthlyPartitionPopulationStddevInRange, "monthly_partition_population_stddev_in_range");
    }

    /**
     * Returns a sample variance in range check specification.
     * @return Sample variance in range check specification.
     */
    public ColumnSampleVarianceInRangeCheckSpec getMonthlyPartitionSampleVarianceInRange() {
        return monthlyPartitionSampleVarianceInRange;
    }

    /**
     * Sets a new specification of a sample variance in range check.
     * @param monthlyPartitionSampleVarianceInRange Sample variance in range check specification.
     */
    public void setMonthlyPartitionSampleVarianceInRange(ColumnSampleVarianceInRangeCheckSpec monthlyPartitionSampleVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSampleVarianceInRange, monthlyPartitionSampleVarianceInRange));
        this.monthlyPartitionSampleVarianceInRange = monthlyPartitionSampleVarianceInRange;
        propagateHierarchyIdToField(monthlyPartitionSampleVarianceInRange, "monthly_partition_sample_variance_in_range");
    }

    /**
     * Returns a population variance in range check specification.
     * @return Population variance in range check specification.
     */
    public ColumnPopulationVarianceInRangeCheckSpec getMonthlyPartitionPopulationVarianceInRange() {
        return monthlyPartitionPopulationVarianceInRange;
    }

    /**
     * Sets a new specification of a population variance in range check.
     * @param monthlyPartitionPopulationVarianceInRange Population variance in range check specification.
     */
    public void setMonthlyPartitionPopulationVarianceInRange(ColumnPopulationVarianceInRangeCheckSpec monthlyPartitionPopulationVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionPopulationVarianceInRange, monthlyPartitionPopulationVarianceInRange));
        this.monthlyPartitionPopulationVarianceInRange = monthlyPartitionPopulationVarianceInRange;
        propagateHierarchyIdToField(monthlyPartitionPopulationVarianceInRange, "monthly_partition_population_variance_in_range");
    }

    /**
     * Returns an invalid latitude count check specification.
     * @return invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getMonthlyPartitionInvalidLatitude() {
        return monthlyPartitionInvalidLatitude;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param monthlyPartitionInvalidLatitude invalid latitude count check specification.
     */
    public void setMonthlyPartitionInvalidLatitude(ColumnInvalidLatitudeCountCheckSpec monthlyPartitionInvalidLatitude) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidLatitude, monthlyPartitionInvalidLatitude));
        this.monthlyPartitionInvalidLatitude = monthlyPartitionInvalidLatitude;
        propagateHierarchyIdToField(monthlyPartitionInvalidLatitude, "monthly_partition_invalid_latitude");
    }

    /**
     * Returns a valid latitude percent check specification.
     * @return Valid latitude percent check specification.
     */
    public ColumnValidLatitudePercentCheckSpec getMonthlyPartitionValidLatitudePercent() {
        return monthlyPartitionValidLatitudePercent;
    }

    /**
     * Sets a new specification of a valid latitude percent check.
     * @param monthlyPartitionValidLatitudePercent Valid latitude percent check specification.
     */
    public void setMonthlyPartitionValidLatitudePercent(ColumnValidLatitudePercentCheckSpec monthlyPartitionValidLatitudePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValidLatitudePercent, monthlyPartitionValidLatitudePercent));
        this.monthlyPartitionValidLatitudePercent = monthlyPartitionValidLatitudePercent;
        propagateHierarchyIdToField(monthlyPartitionValidLatitudePercent, "monthly_partition_valid_latitude_percent");
    }

    /**
     * Returns an invalid longitude count check specification.
     * @return invalid longitude count check specification.
     */
    public ColumnInvalidLongitudeCountCheckSpec getMonthlyPartitionInvalidLongitude() {
        return monthlyPartitionInvalidLongitude;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param monthlyPartitionInvalidLongitude invalid longitude count check specification.
     */
    public void setMonthlyPartitionInvalidLongitude(ColumnInvalidLongitudeCountCheckSpec monthlyPartitionInvalidLongitude) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidLongitude, monthlyPartitionInvalidLongitude));
        this.monthlyPartitionInvalidLongitude = monthlyPartitionInvalidLongitude;
        propagateHierarchyIdToField(monthlyPartitionInvalidLongitude, "monthly_partition_invalid_longitude");
    }

    /**
     * Returns a valid longitude percent check specification.
     * @return Valid longitude percent check specification.
     */
    public ColumnValidLongitudePercentCheckSpec getMonthlyPartitionValidLongitudePercent() {
        return monthlyPartitionValidLongitudePercent;
    }

    /**
     * Sets a new specification of a valid longitude percent check.
     * @param monthlyPartitionValidLongitudePercent Valid longitude percent check specification.
     */
    public void setMonthlyPartitionValidLongitudePercent(ColumnValidLongitudePercentCheckSpec monthlyPartitionValidLongitudePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValidLongitudePercent, monthlyPartitionValidLongitudePercent));
        this.monthlyPartitionValidLongitudePercent = monthlyPartitionValidLongitudePercent;
        propagateHierarchyIdToField(monthlyPartitionValidLongitudePercent, "monthly_partition_valid_longitude_percent");
    }

    /**
     * Returns a non-negative values count check specification.
     * @return Non-negative values count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getMonthlyPartitionNonNegativeValues() {
        return monthlyPartitionNonNegativeValues;
    }

    /**
     * Sets a new specification of a maximum non-negative values count check.
     * @param monthlyPartitionNonNegativeValues Non-negative values count check specification.
     */
    public void setMonthlyPartitionNonNegativeValues(ColumnNonNegativeCountCheckSpec monthlyPartitionNonNegativeValues) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNonNegativeValues, monthlyPartitionNonNegativeValues));
        this.monthlyPartitionNonNegativeValues = monthlyPartitionNonNegativeValues;
        propagateHierarchyIdToField(monthlyPartitionNonNegativeValues, "monthly_partition_non_negative_values");
    }

    /**
     * Returns a non-negative values percentage check specification.
     * @return Non-negative values percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getMonthlyPartitionNonNegativeValuesPercent() {
        return monthlyPartitionNonNegativeValuesPercent;
    }

    /**
     * Sets a new specification of a non-negative values percentage check.
     * @param monthlyPartitionNonNegativeValuesPercent Non-negative values percentage check specification.
     */
    public void setMonthlyPartitionNonNegativeValuesPercent(ColumnNonNegativePercentCheckSpec monthlyPartitionNonNegativeValuesPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNonNegativeValuesPercent, monthlyPartitionNonNegativeValuesPercent));
        this.monthlyPartitionNonNegativeValuesPercent = monthlyPartitionNonNegativeValuesPercent;
        propagateHierarchyIdToField(monthlyPartitionNonNegativeValuesPercent, "monthly_partition_non_negative_values_percent");
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
        return DataTypeCategory.NUMERIC;
    }
}