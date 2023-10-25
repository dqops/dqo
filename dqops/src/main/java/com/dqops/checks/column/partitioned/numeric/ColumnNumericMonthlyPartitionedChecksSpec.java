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
            put("monthly_partition_negative_count", o -> o.monthlyPartitionNegativeCount);
            put("monthly_partition_negative_percent", o -> o.monthlyPartitionNegativePercent);
            put("monthly_partition_non_negative_count", o -> o.monthlyPartitionNonNegativeCount);
            put("monthly_partition_non_negative_percent", o -> o.monthlyPartitionNonNegativePercent);
            put("monthly_partition_expected_numbers_in_use_count", o -> o.monthlyPartitionExpectedNumbersInUseCount);
            put("monthly_partition_number_value_in_set_percent", o -> o.monthlyPartitionNumberValueInSetPercent);
            put("monthly_partition_values_in_range_numeric_percent", o -> o.monthlyPartitionValuesInRangeNumericPercent);
            put("monthly_partition_values_in_range_integers_percent", o -> o.monthlyPartitionValuesInRangeIntegersPercent);
            put("monthly_partition_value_below_min_value_count", o -> o.monthlyPartitionValueBelowMinValueCount);
            put("monthly_partition_value_below_min_value_percent", o -> o.monthlyPartitionValueBelowMinValuePercent);
            put("monthly_partition_value_above_max_value_count", o -> o.monthlyPartitionValueAboveMaxValueCount);
            put("monthly_partition_value_above_max_value_percent", o -> o.monthlyPartitionValueAboveMaxValuePercent);
            put("monthly_partition_max_in_range", o -> o.monthlyPartitionMaxInRange);
            put("monthly_partition_min_in_range", o -> o.monthlyPartitionMinInRange);
            put("monthly_partition_mean_in_range", o -> o.monthlyPartitionMeanInRange);
            put("monthly_partition_percentile_in_range", o -> o.monthlyPartitionPercentileInRange);
            put("monthly_partition_median_in_range", o -> o.monthlyPartitionMedianInRange);
            put("monthly_partition_percentile_10_in_range", o -> o.monthlyPartitionPercentile_10InRange);
            put("monthly_partition_percentile_25_in_range", o -> o.monthlyPartitionPercentile_25InRange);
            put("monthly_partition_percentile_75_in_range", o -> o.monthlyPartitionPercentile_75InRange);
            put("monthly_partition_percentile_90_in_range", o -> o.monthlyPartitionPercentile_90InRange);
            put("monthly_partition_sample_stddev_in_range", o -> o.monthlyPartitionSampleStddevInRange);
            put("monthly_partition_population_stddev_in_range", o -> o.monthlyPartitionPopulationStddevInRange);
            put("monthly_partition_sample_variance_in_range", o -> o.monthlyPartitionSampleVarianceInRange);
            put("monthly_partition_population_variance_in_range", o -> o.monthlyPartitionPopulationVarianceInRange);
            put("monthly_partition_sum_in_range", o -> o.monthlyPartitionSumInRange);
            put("monthly_partition_invalid_latitude_count", o -> o.monthlyPartitionInvalidLatitudeCount);
            put("monthly_partition_valid_latitude_percent", o -> o.monthlyPartitionValidLatitudePercent);
            put("monthly_partition_invalid_longitude_count", o -> o.monthlyPartitionInvalidLongitudeCount);
            put("monthly_partition_valid_longitude_percent", o -> o.monthlyPartitionValidLongitudePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNegativeCountCheckSpec monthlyPartitionNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNegativePercentCheckSpec monthlyPartitionNegativePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNonNegativeCountCheckSpec monthlyPartitionNonNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNonNegativePercentCheckSpec monthlyPartitionNonNegativePercent;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnExpectedNumbersInUseCountCheckSpec monthlyPartitionExpectedNumbersInUseCount;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNumberValueInSetPercentCheckSpec monthlyPartitionNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValuesInRangeNumericPercentCheckSpec monthlyPartitionValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValuesInRangeIntegersPercentCheckSpec monthlyPartitionValuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValueBelowMinValueCountCheckSpec monthlyPartitionValueBelowMinValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValueBelowMinValuePercentCheckSpec monthlyPartitionValueBelowMinValuePercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValueAboveMaxValueCountCheckSpec monthlyPartitionValueAboveMaxValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValueAboveMaxValuePercentCheckSpec monthlyPartitionValueAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxInRangeCheckSpec monthlyPartitionMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinInRangeCheckSpec monthlyPartitionMinInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMeanInRangeCheckSpec monthlyPartitionMeanInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPercentileInRangeCheckSpec monthlyPartitionPercentileInRange;

    @JsonPropertyDescription("Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMedianInRangeCheckSpec monthlyPartitionMedianInRange;

    @JsonPropertyDescription("Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPercentile10InRangeCheckSpec monthlyPartitionPercentile_10InRange;

    @JsonPropertyDescription("Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPercentile25InRangeCheckSpec monthlyPartitionPercentile_25InRange;

    @JsonPropertyDescription("Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPercentile75InRangeCheckSpec monthlyPartitionPercentile_75InRange;

    @JsonPropertyDescription("Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPercentile90InRangeCheckSpec monthlyPartitionPercentile_90InRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnSampleStddevInRangeCheckSpec monthlyPartitionSampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPopulationStddevInRangeCheckSpec monthlyPartitionPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnSampleVarianceInRangeCheckSpec monthlyPartitionSampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPopulationVarianceInRangeCheckSpec monthlyPartitionPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnSumInRangeCheckSpec monthlyPartitionSumInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnInvalidLatitudeCountCheckSpec monthlyPartitionInvalidLatitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValidLatitudePercentCheckSpec monthlyPartitionValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnInvalidLongitudeCountCheckSpec monthlyPartitionInvalidLongitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValidLongitudePercentCheckSpec monthlyPartitionValidLongitudePercent;

    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getMonthlyPartitionNegativeCount() {
        return monthlyPartitionNegativeCount;
    }

    /**
     * Sets a new specification of a negative values count check.
     * @param monthlyPartitionNegativeCount Negative values count check specification.
     */
    public void setMonthlyPartitionNegativeCount(ColumnNegativeCountCheckSpec monthlyPartitionNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNegativeCount, monthlyPartitionNegativeCount));
        this.monthlyPartitionNegativeCount = monthlyPartitionNegativeCount;
        propagateHierarchyIdToField(monthlyPartitionNegativeCount, "monthly_partition_negative_count");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getMonthlyPartitionNegativePercent() {
        return monthlyPartitionNegativePercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param monthlyPartitionNegativePercent Negative values percentage check specification.
     */
    public void setMonthlyPartitionNegativePercent(ColumnNegativePercentCheckSpec monthlyPartitionNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNegativePercent, monthlyPartitionNegativePercent));
        this.monthlyPartitionNegativePercent = monthlyPartitionNegativePercent;
        propagateHierarchyIdToField(monthlyPartitionNegativePercent, "monthly_partition_negative_percent");
    }

    /**
     * Returns a non-negative values count check specification.
     * @return Non-negative values count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getMonthlyPartitionNonNegativeCount() {
        return monthlyPartitionNonNegativeCount;
    }

    /**
     * Sets a new specification of a maximum non-negative values count check.
     * @param monthlyPartitionNonNegativeCount Non-negative values count check specification.
     */
    public void setMonthlyPartitionNonNegativeCount(ColumnNonNegativeCountCheckSpec monthlyPartitionNonNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNonNegativeCount, monthlyPartitionNonNegativeCount));
        this.monthlyPartitionNonNegativeCount = monthlyPartitionNonNegativeCount;
        propagateHierarchyIdToField(monthlyPartitionNonNegativeCount, "monthly_partition_non_negative_count");
    }

    /**
     * Returns a non-negative values percentage check specification.
     * @return Non-negative values percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getMonthlyPartitionNonNegativePercent() {
        return monthlyPartitionNonNegativePercent;
    }

    /**
     * Sets a new specification of a non-negative values percentage check.
     * @param monthlyPartitionNonNegativePercent Non-negative values percentage check specification.
     */
    public void setMonthlyPartitionNonNegativePercent(ColumnNonNegativePercentCheckSpec monthlyPartitionNonNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNonNegativePercent, monthlyPartitionNonNegativePercent));
        this.monthlyPartitionNonNegativePercent = monthlyPartitionNonNegativePercent;
        propagateHierarchyIdToField(monthlyPartitionNonNegativePercent, "monthly_partition_non_negative_percent");
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
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getMonthlyPartitionNumberValueInSetPercent() {
        return monthlyPartitionNumberValueInSetPercent;
    }

    /**
     * Sets a new specification of a numbers valid percent check .
     * @param monthlyPartitionNumberValueInSetPercent Minimum Numbers valid percent check.
     */
    public void setMonthlyPartitionNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec monthlyPartitionNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumberValueInSetPercent, monthlyPartitionNumberValueInSetPercent));
        this.monthlyPartitionNumberValueInSetPercent = monthlyPartitionNumberValueInSetPercent;
        propagateHierarchyIdToField(monthlyPartitionNumberValueInSetPercent, "monthly_partition_number_value_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeNumericPercentCheckSpec getMonthlyPartitionValuesInRangeNumericPercent() {
        return monthlyPartitionValuesInRangeNumericPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param monthlyPartitionValuesInRangeNumericPercent Numbers in set percent check specification.
     */
    public void setMonthlyPartitionValuesInRangeNumericPercent(ColumnValuesInRangeNumericPercentCheckSpec monthlyPartitionValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValuesInRangeNumericPercent, monthlyPartitionValuesInRangeNumericPercent));
        this.monthlyPartitionValuesInRangeNumericPercent = monthlyPartitionValuesInRangeNumericPercent;
        propagateHierarchyIdToField(monthlyPartitionValuesInRangeNumericPercent, "monthly_partition_values_in_range_numeric_percent");
    }


    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeIntegersPercentCheckSpec getMonthlyPartitionValuesInRangeIntegersPercent() {
        return monthlyPartitionValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param monthlyPartitionValuesInRangeIntegersPercent Numbers in set percent check specification.
     */
    public void setMonthlyPartitionValuesInRangeIntegersPercent(ColumnValuesInRangeIntegersPercentCheckSpec monthlyPartitionValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValuesInRangeIntegersPercent, monthlyPartitionValuesInRangeIntegersPercent));
        this.monthlyPartitionValuesInRangeIntegersPercent = monthlyPartitionValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(monthlyPartitionValuesInRangeIntegersPercent, "monthly_partition_values_in_range_integers_percent");
    }

    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnValueBelowMinValueCountCheckSpec getMonthlyPartitionValueBelowMinValueCount() {
        return monthlyPartitionValueBelowMinValueCount;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param monthlyPartitionValueBelowMinValueCount Numeric value below min value count check.
     */
    public void setMonthlyPartitionValueBelowMinValueCount(ColumnValueBelowMinValueCountCheckSpec monthlyPartitionValueBelowMinValueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValueBelowMinValueCount, monthlyPartitionValueBelowMinValueCount));
        this.monthlyPartitionValueBelowMinValueCount = monthlyPartitionValueBelowMinValueCount;
        propagateHierarchyIdToField(monthlyPartitionValueBelowMinValueCount, "monthly_partition_value_below_min_value_count");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnValueBelowMinValuePercentCheckSpec getMonthlyPartitionValueBelowMinValuePercent() {
        return monthlyPartitionValueBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param monthlyPartitionValueBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setMonthlyPartitionValueBelowMinValuePercent(ColumnValueBelowMinValuePercentCheckSpec monthlyPartitionValueBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValueBelowMinValuePercent, monthlyPartitionValueBelowMinValuePercent));
        this.monthlyPartitionValueBelowMinValuePercent = monthlyPartitionValueBelowMinValuePercent;
        propagateHierarchyIdToField(monthlyPartitionValueBelowMinValuePercent, "monthly_partition_value_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnValueAboveMaxValueCountCheckSpec getMonthlyPartitionValueAboveMaxValueCount() {
        return monthlyPartitionValueAboveMaxValueCount;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param monthlyPartitionValueAboveMaxValueCount Numeric value above max value count check.
     */
    public void setMonthlyPartitionValueAboveMaxValueCount(ColumnValueAboveMaxValueCountCheckSpec monthlyPartitionValueAboveMaxValueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValueAboveMaxValueCount, monthlyPartitionValueAboveMaxValueCount));
        this.monthlyPartitionValueAboveMaxValueCount = monthlyPartitionValueAboveMaxValueCount;
        propagateHierarchyIdToField(monthlyPartitionValueAboveMaxValueCount, "monthly_partition_value_above_max_value_count");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnValueAboveMaxValuePercentCheckSpec getMonthlyPartitionValueAboveMaxValuePercent() {
        return monthlyPartitionValueAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param monthlyPartitionValueAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setMonthlyPartitionValueAboveMaxValuePercent(ColumnValueAboveMaxValuePercentCheckSpec monthlyPartitionValueAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValueAboveMaxValuePercent, monthlyPartitionValueAboveMaxValuePercent));
        this.monthlyPartitionValueAboveMaxValuePercent = monthlyPartitionValueAboveMaxValuePercent;
        propagateHierarchyIdToField(monthlyPartitionValueAboveMaxValuePercent, "monthly_partition_value_above_max_value_percent");
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
     * Returns an invalid latitude count check specification.
     * @return invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getMonthlyPartitionInvalidLatitudeCount() {
        return monthlyPartitionInvalidLatitudeCount;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param monthlyPartitionInvalidLatitudeCount invalid latitude count check specification.
     */
    public void setMonthlyPartitionInvalidLatitudeCount(ColumnInvalidLatitudeCountCheckSpec monthlyPartitionInvalidLatitudeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidLatitudeCount, monthlyPartitionInvalidLatitudeCount));
        this.monthlyPartitionInvalidLatitudeCount = monthlyPartitionInvalidLatitudeCount;
        propagateHierarchyIdToField(monthlyPartitionInvalidLatitudeCount, "monthly_partition_invalid_latitude_count");
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
    public ColumnInvalidLongitudeCountCheckSpec getMonthlyPartitionInvalidLongitudeCount() {
        return monthlyPartitionInvalidLongitudeCount;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param monthlyPartitionInvalidLongitudeCount invalid longitude count check specification.
     */
    public void setMonthlyPartitionInvalidLongitudeCount(ColumnInvalidLongitudeCountCheckSpec monthlyPartitionInvalidLongitudeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidLongitudeCount, monthlyPartitionInvalidLongitudeCount));
        this.monthlyPartitionInvalidLongitudeCount = monthlyPartitionInvalidLongitudeCount;
        propagateHierarchyIdToField(monthlyPartitionInvalidLongitudeCount, "monthly_partition_invalid_longitude_count");
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
}