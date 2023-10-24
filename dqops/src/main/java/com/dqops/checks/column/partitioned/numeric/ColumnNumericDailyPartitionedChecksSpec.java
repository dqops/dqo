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
 * Container of numeric data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_negative_count", o -> o.dailyPartitionNegativeCount);
            put("daily_partition_negative_percent", o -> o.dailyPartitionNegativePercent);
            put("daily_partition_non_negative_count", o -> o.dailyPartitionNonNegativeCount);
            put("daily_partition_non_negative_percent", o -> o.dailyPartitionNonNegativePercent);
            put("daily_partition_expected_numbers_in_use_count", o -> o.dailyPartitionExpectedNumbersInUseCount);
            put("daily_partition_number_value_in_set_percent", o -> o.dailyPartitionNumberValueInSetPercent);
            put("daily_partition_values_in_range_numeric_percent", o -> o.dailyPartitionValuesInRangeNumericPercent);
            put("daily_partition_values_in_range_integers_percent", o -> o.dailyPartitionValuesInRangeIntegersPercent);
            put("daily_partition_value_below_min_value_count", o -> o.dailyPartitionValueBelowMinValueCount);
            put("daily_partition_value_below_min_value_percent", o -> o.dailyPartitionValueBelowMinValuePercent);
            put("daily_partition_value_above_max_value_count", o -> o.dailyPartitionValueAboveMaxValueCount);
            put("daily_partition_value_above_max_value_percent", o -> o.dailyPartitionValueAboveMaxValuePercent);
            put("daily_partition_max_in_range", o -> o.dailyPartitionMaxInRange);
            put("daily_partition_min_in_range", o -> o.dailyPartitionMinInRange);
            put("daily_partition_mean_in_range", o -> o.dailyPartitionMeanInRange);
            put("daily_partition_percentile_in_range", o -> o.dailyPartitionPercentileInRange);
            put("daily_partition_median_in_range", o -> o.dailyPartitionMedianInRange);
            put("daily_partition_percentile_10_in_range", o -> o.dailyPartitionPercentile_10InRange);
            put("daily_partition_percentile_25_in_range", o -> o.dailyPartitionPercentile_25InRange);
            put("daily_partition_percentile_75_in_range", o -> o.dailyPartitionPercentile_75InRange);
            put("daily_partition_percentile_90_in_range", o -> o.dailyPartitionPercentile_90InRange);
            put("daily_partition_sample_stddev_in_range", o -> o.dailyPartitionSampleStddevInRange);
            put("daily_partition_population_stddev_in_range", o -> o.dailyPartitionPopulationStddevInRange);
            put("daily_partition_sample_variance_in_range", o -> o.dailyPartitionSampleVarianceInRange);
            put("daily_partition_population_variance_in_range", o -> o.dailyPartitionPopulationVarianceInRange);
            put("daily_partition_sum_in_range", o -> o.dailyPartitionSumInRange);
            put("daily_partition_invalid_latitude_count", o -> o.dailyPartitionInvalidLatitudeCount);
            put("daily_partition_valid_latitude_percent", o -> o.dailyPartitionValidLatitudePercent);
            put("daily_partition_invalid_longitude_count", o -> o.dailyPartitionInvalidLongitudeCount);
            put("daily_partition_valid_longitude_percent", o -> o.dailyPartitionValidLongitudePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNegativeCountCheckSpec dailyPartitionNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNegativePercentCheckSpec dailyPartitionNegativePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNonNegativeCountCheckSpec dailyPartitionNonNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNonNegativePercentCheckSpec dailyPartitionNonNegativePercent;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnExpectedNumbersInUseCountCheckSpec dailyPartitionExpectedNumbersInUseCount;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNumberValueInSetPercentCheckSpec dailyPartitionNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValuesInRangeNumericPercentCheckSpec dailyPartitionValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValuesInRangeIntegersPercentCheckSpec dailyPartitionValuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValueBelowMinValueCountCheckSpec dailyPartitionValueBelowMinValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValueBelowMinValuePercentCheckSpec dailyPartitionValueBelowMinValuePercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValueAboveMaxValueCountCheckSpec dailyPartitionValueAboveMaxValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValueAboveMaxValuePercentCheckSpec dailyPartitionValueAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxInRangeCheckSpec dailyPartitionMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinInRangeCheckSpec dailyPartitionMinInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMeanInRangeCheckSpec dailyPartitionMeanInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPercentileInRangeCheckSpec dailyPartitionPercentileInRange;

    @JsonPropertyDescription("Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMedianInRangeCheckSpec dailyPartitionMedianInRange;

    @JsonPropertyDescription("Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPercentile10InRangeCheckSpec dailyPartitionPercentile_10InRange;

    @JsonPropertyDescription("Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPercentile25InRangeCheckSpec dailyPartitionPercentile_25InRange;

    @JsonPropertyDescription("Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPercentile75InRangeCheckSpec dailyPartitionPercentile_75InRange;

    @JsonPropertyDescription("Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPercentile90InRangeCheckSpec dailyPartitionPercentile_90InRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnSampleStddevInRangeCheckSpec dailyPartitionSampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPopulationStddevInRangeCheckSpec dailyPartitionPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnSampleVarianceInRangeCheckSpec dailyPartitionSampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPopulationVarianceInRangeCheckSpec dailyPartitionPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnSumInRangeCheckSpec dailyPartitionSumInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnInvalidLatitudeCountCheckSpec dailyPartitionInvalidLatitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValidLatitudePercentCheckSpec dailyPartitionValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnInvalidLongitudeCountCheckSpec dailyPartitionInvalidLongitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValidLongitudePercentCheckSpec dailyPartitionValidLongitudePercent;


    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getDailyPartitionNegativeCount() {
        return dailyPartitionNegativeCount;
    }

    /**
     * Sets a new specification of a maximum negative values count check.
     * @param dailyPartitionNegativeCount Negative values count check specification.
     */
    public void setDailyPartitionNegativeCount(ColumnNegativeCountCheckSpec dailyPartitionNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNegativeCount, dailyPartitionNegativeCount));
        this.dailyPartitionNegativeCount = dailyPartitionNegativeCount;
        propagateHierarchyIdToField(dailyPartitionNegativeCount, "daily_partition_negative_count");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getDailyPartitionNegativePercent() {
        return dailyPartitionNegativePercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param dailyPartitionNegativePercent Negative values percentage check specification.
     */
    public void setDailyPartitionNegativePercent(ColumnNegativePercentCheckSpec dailyPartitionNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNegativePercent, dailyPartitionNegativePercent));
        this.dailyPartitionNegativePercent = dailyPartitionNegativePercent;
        propagateHierarchyIdToField(dailyPartitionNegativePercent, "daily_partition_negative_percent");
    }

    /**
     * Returns a non-negative values count check specification.
     * @return Non-negative values count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getDailyPartitionNonNegativeCount() {
        return dailyPartitionNonNegativeCount;
    }

    /**
     * Sets a new specification of a maximum non-negative values count check.
     * @param dailyPartitionNonNegativeCount Non-negative values count check specification.
     */
    public void setDailyPartitionNonNegativeCount(ColumnNonNegativeCountCheckSpec dailyPartitionNonNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNonNegativeCount, dailyPartitionNonNegativeCount));
        this.dailyPartitionNonNegativeCount = dailyPartitionNonNegativeCount;
        propagateHierarchyIdToField(dailyPartitionNonNegativeCount, "daily_partition_non_negative_count");
    }

    /**
     * Returns a non-negative values percentage check specification.
     * @return Non-negative values percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getDailyPartitionNonNegativePercent() {
        return dailyPartitionNonNegativePercent;
    }

    /**
     * Sets a new specification of a non-negative values percentage check.
     * @param dailyPartitionNonNegativePercent Non-negative values percentage check specification.
     */
    public void setDailyPartitionNonNegativePercent(ColumnNonNegativePercentCheckSpec dailyPartitionNonNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNonNegativePercent, dailyPartitionNonNegativePercent));
        this.dailyPartitionNonNegativePercent = dailyPartitionNonNegativePercent;
        propagateHierarchyIdToField(dailyPartitionNonNegativePercent, "daily_partition_non_negative_percent");
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
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getDailyPartitionNumberValueInSetPercent() {
        return dailyPartitionNumberValueInSetPercent;
    }

    /**
     * Sets a new specification of a numbers found percent check.
     * @param dailyPartitionNumberValueInSetPercent Numbers found percent check specification.
     */
    public void setDailyPartitionNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec dailyPartitionNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNumberValueInSetPercent, dailyPartitionNumberValueInSetPercent));
        this.dailyPartitionNumberValueInSetPercent = dailyPartitionNumberValueInSetPercent;
        propagateHierarchyIdToField(dailyPartitionNumberValueInSetPercent, "daily_partition_number_value_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeNumericPercentCheckSpec getDailyPartitionValuesInRangeNumericPercent() {
        return dailyPartitionValuesInRangeNumericPercent;
    }

    /**
     * Sets a new definition of a numbers in set percent check specification.
     * @param dailyPartitionValuesInRangeNumericPercent Numbers in set percent check specification.
     */
    public void setDailyPartitionValuesInRangeNumericPercent(ColumnValuesInRangeNumericPercentCheckSpec dailyPartitionValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValuesInRangeNumericPercent, dailyPartitionValuesInRangeNumericPercent));
        this.dailyPartitionValuesInRangeNumericPercent = dailyPartitionValuesInRangeNumericPercent;
        propagateHierarchyIdToField(dailyPartitionValuesInRangeNumericPercent, "daily_partition_values_in_range_numeric_percent");
    }


    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeIntegersPercentCheckSpec getDailyPartitionValuesInRangeIntegersPercent() {
        return dailyPartitionValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyPartitionValuesInRangeIntegersPercent Numbers in set percent check specification.
     */
    public void setDailyPartitionValuesInRangeIntegersPercent(ColumnValuesInRangeIntegersPercentCheckSpec dailyPartitionValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValuesInRangeIntegersPercent, dailyPartitionValuesInRangeIntegersPercent));
        this.dailyPartitionValuesInRangeIntegersPercent = dailyPartitionValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(dailyPartitionValuesInRangeIntegersPercent, "daily_partition_values_in_range_integers_percent");
    }

    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnValueBelowMinValueCountCheckSpec getDailyPartitionValueBelowMinValueCount() {
        return dailyPartitionValueBelowMinValueCount;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param dailyPartitionValueBelowMinValueCount Numeric value below min value count check.
     */
    public void setDailyPartitionValueBelowMinValueCount(ColumnValueBelowMinValueCountCheckSpec dailyPartitionValueBelowMinValueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValueBelowMinValueCount, dailyPartitionValueBelowMinValueCount));
        this.dailyPartitionValueBelowMinValueCount = dailyPartitionValueBelowMinValueCount;
        propagateHierarchyIdToField(dailyPartitionValueBelowMinValueCount, "daily_partition_value_below_min_value_count");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnValueBelowMinValuePercentCheckSpec getDailyPartitionValueBelowMinValuePercent() {
        return dailyPartitionValueBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param dailyPartitionValueBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setDailyPartitionValueBelowMinValuePercent(ColumnValueBelowMinValuePercentCheckSpec dailyPartitionValueBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValueBelowMinValuePercent, dailyPartitionValueBelowMinValuePercent));
        this.dailyPartitionValueBelowMinValuePercent = dailyPartitionValueBelowMinValuePercent;
        propagateHierarchyIdToField(dailyPartitionValueBelowMinValuePercent, "daily_partition_value_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnValueAboveMaxValueCountCheckSpec getDailyPartitionValueAboveMaxValueCount() {
        return dailyPartitionValueAboveMaxValueCount;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param dailyPartitionValueAboveMaxValueCount Numeric value above max value count check.
     */
    public void setDailyPartitionValueAboveMaxValueCount(ColumnValueAboveMaxValueCountCheckSpec dailyPartitionValueAboveMaxValueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValueAboveMaxValueCount, dailyPartitionValueAboveMaxValueCount));
        this.dailyPartitionValueAboveMaxValueCount = dailyPartitionValueAboveMaxValueCount;
        propagateHierarchyIdToField(dailyPartitionValueAboveMaxValueCount, "daily_partition_value_above_max_value_count");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnValueAboveMaxValuePercentCheckSpec getDailyPartitionValueAboveMaxValuePercent() {
        return dailyPartitionValueAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param dailyPartitionValueAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setDailyPartitionValueAboveMaxValuePercent(ColumnValueAboveMaxValuePercentCheckSpec dailyPartitionValueAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValueAboveMaxValuePercent, dailyPartitionValueAboveMaxValuePercent));
        this.dailyPartitionValueAboveMaxValuePercent = dailyPartitionValueAboveMaxValuePercent;
        propagateHierarchyIdToField(dailyPartitionValueAboveMaxValuePercent, "daily_partition_value_above_max_value_percent");
    }

    /**
     * Returns a max in range check specification.
     * @return Max in range check specification.
     */
    public ColumnMaxInRangeCheckSpec getDailyPartitionMaxInRange() {
        return dailyPartitionMaxInRange;
    }

    /**
     * Sets a new specification of a max in range check.
     * @param dailyPartitionMaxInRange Max in range check specification.
     */
    public void setDailyPartitionMaxInRange(ColumnMaxInRangeCheckSpec dailyPartitionMaxInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxInRange, dailyPartitionMaxInRange));
        this.dailyPartitionMaxInRange = dailyPartitionMaxInRange;
        propagateHierarchyIdToField(dailyPartitionMaxInRange, "daily_partition_max_in_range");
    }

    /**
     * Returns a min in range check specification.
     * @return Min in range check specification.
     */
    public ColumnMinInRangeCheckSpec getDailyPartitionMinInRange() {
        return dailyPartitionMinInRange;
    }

    /**
     * Sets a new specification of a min in range check.
     * @param dailyPartitionMinInRange Min in range check specification.
     */
    public void setDailyPartitionMinInRange(ColumnMinInRangeCheckSpec dailyPartitionMinInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinInRange, dailyPartitionMinInRange));
        this.dailyPartitionMinInRange = dailyPartitionMinInRange;
        propagateHierarchyIdToField(dailyPartitionMinInRange, "daily_partition_min_in_range");
    }

    /**
     * Returns a mean in range check specification.
     * @return Mean in range check specification.
     */
    public ColumnMeanInRangeCheckSpec getDailyPartitionMeanInRange() {
        return dailyPartitionMeanInRange;
    }

    /**
     * Sets a new specification of a mean in range check.
     * @param dailyPartitionMeanInRange mean in range check specification.
     */
    public void setDailyPartitionMeanInRange(ColumnMeanInRangeCheckSpec dailyPartitionMeanInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanInRange, dailyPartitionMeanInRange));
        this.dailyPartitionMeanInRange = dailyPartitionMeanInRange;
        propagateHierarchyIdToField(dailyPartitionMeanInRange, "daily_partition_mean_in_range");
    }

    /**
     * Returns a percentile in range check specification.
     * @return Percentile in range check specification.
     */
    public ColumnPercentileInRangeCheckSpec getDailyPartitionPercentileInRange() {
        return dailyPartitionPercentileInRange;
    }

    /**
     * Sets a new specification of a percentile in range check.
     * @param dailyPartitionPercentileInRange percentile in range check specification.
     */
    public void setDailyPartitionPercentileInRange(ColumnPercentileInRangeCheckSpec dailyPartitionPercentileInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionPercentileInRange, dailyPartitionPercentileInRange));
        this.dailyPartitionPercentileInRange = dailyPartitionPercentileInRange;
        propagateHierarchyIdToField(dailyPartitionPercentileInRange, "daily_partition_percentile_in_range");
    }

    /**
     * Returns a median in range check specification.
     * @return median in range check specification.
     */
    public ColumnMedianInRangeCheckSpec getDailyPartitionMedianInRange() {
        return dailyPartitionMedianInRange;
    }

    /**
     * Sets a new specification of a median in range check.
     * @param dailyPartitionMedianInRange median in range check specification.
     */
    public void setDailyPartitionMedianInRange(ColumnMedianInRangeCheckSpec dailyPartitionMedianInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianInRange, dailyPartitionMedianInRange));
        this.dailyPartitionMedianInRange = dailyPartitionMedianInRange;
        propagateHierarchyIdToField(dailyPartitionMedianInRange, "daily_partition_median_in_range");
    }

    /**
     * Returns a percentile 10 in range check specification.
     * @return Percentile 10 in range check specification.
     */
    public ColumnPercentile10InRangeCheckSpec getDailyPartitionPercentile_10InRange() {
        return dailyPartitionPercentile_10InRange;
    }

    /**
     * Sets a new specification of a percentile 10 in range check.
     * @param dailyPartitionPercentile_10InRange percentile 10 in range check specification.
     */
    public void setDailyPartitionPercentile_10InRange(ColumnPercentile10InRangeCheckSpec dailyPartitionPercentile_10InRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionPercentile_10InRange, dailyPartitionPercentile_10InRange));
        this.dailyPartitionPercentile_10InRange = dailyPartitionPercentile_10InRange;
        propagateHierarchyIdToField(dailyPartitionPercentile_10InRange, "daily_partition_percentile_10_in_range");
    }

    /**
     * Returns a percentile 25 in range check specification.
     * @return Percentile 25 in range check specification.
     */
    public ColumnPercentile25InRangeCheckSpec getDailyPartitionPercentile_25InRange() {
        return dailyPartitionPercentile_25InRange;
    }

    /**
     * Sets a new specification of a percentile 25 in range check.
     * @param dailyPartitionPercentile_25InRange percentile 25 in range check specification.
     */
    public void setDailyPartitionPercentile_25InRange(ColumnPercentile25InRangeCheckSpec dailyPartitionPercentile_25InRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionPercentile_25InRange, dailyPartitionPercentile_25InRange));
        this.dailyPartitionPercentile_25InRange = dailyPartitionPercentile_25InRange;
        propagateHierarchyIdToField(dailyPartitionPercentile_25InRange, "daily_partition_percentile_25_in_range");
    }

    /**
     * Returns a percentile 75 in range check specification.
     * @return Percentile 75 in range check specification.
     */
    public ColumnPercentile75InRangeCheckSpec getDailyPartitionPercentile_75InRange() {
        return dailyPartitionPercentile_75InRange;
    }

    /**
     * Sets a new specification of a percentile 75 in range check.
     * @param dailyPartitionPercentile_75InRange percentile 75 in range check specification.
     */
    public void setDailyPartitionPercentile_75InRange(ColumnPercentile75InRangeCheckSpec dailyPartitionPercentile_75InRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionPercentile_75InRange, dailyPartitionPercentile_75InRange));
        this.dailyPartitionPercentile_75InRange = dailyPartitionPercentile_75InRange;
        propagateHierarchyIdToField(dailyPartitionPercentile_75InRange, "daily_partition_percentile_75_in_range");
    }

    /**
     * Returns a percentile 90 in range check specification.
     * @return Percentile 90 in range check specification.
     */
    public ColumnPercentile90InRangeCheckSpec getDailyPartitionPercentile_90InRange() {
        return dailyPartitionPercentile_90InRange;
    }

    /**
     * Sets a new specification of a percentile 90 in range check.
     * @param dailyPartitionPercentile_90InRange percentile 90 in range check specification.
     */
    public void setDailyPartitionPercentile_90InRange(ColumnPercentile90InRangeCheckSpec dailyPartitionPercentile_90InRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionPercentile_90InRange, dailyPartitionPercentile_90InRange));
        this.dailyPartitionPercentile_90InRange = dailyPartitionPercentile_90InRange;
        propagateHierarchyIdToField(dailyPartitionPercentile_90InRange, "daily_partition_percentile_90_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleStddevInRangeCheckSpec getDailyPartitionSampleStddevInRange() {
        return dailyPartitionSampleStddevInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param dailyPartitionSampleStddevInRange Sample standard deviation in range check specification.
     */
    public void setDailyPartitionSampleStddevInRange(ColumnSampleStddevInRangeCheckSpec dailyPartitionSampleStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSampleStddevInRange, dailyPartitionSampleStddevInRange));
        this.dailyPartitionSampleStddevInRange = dailyPartitionSampleStddevInRange;
        propagateHierarchyIdToField(dailyPartitionSampleStddevInRange, "daily_partition_sample_stddev_in_range");
    }

    /**
     * Returns a population standard deviation in range check specification.
     * @return Population standard deviation in range check specification.
     */
    public ColumnPopulationStddevInRangeCheckSpec getDailyPartitionPopulationStddevInRange() {
        return dailyPartitionPopulationStddevInRange;
    }

    /**
     * Sets a new specification of a population standard deviation in range check.
     * @param dailyPartitionPopulationStddevInRange Population standard deviation in range check specification.
     */
    public void setDailyPartitionPopulationStddevInRange(ColumnPopulationStddevInRangeCheckSpec dailyPartitionPopulationStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionPopulationStddevInRange, dailyPartitionPopulationStddevInRange));
        this.dailyPartitionPopulationStddevInRange = dailyPartitionPopulationStddevInRange;
        propagateHierarchyIdToField(dailyPartitionPopulationStddevInRange, "daily_partition_population_stddev_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleVarianceInRangeCheckSpec getDailyPartitionSampleVarianceInRange() {
        return dailyPartitionSampleVarianceInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param dailyPartitionSampleVarianceInRange Sample standard deviation in range check specification.
     */
    public void setDailyPartitionSampleVarianceInRange(ColumnSampleVarianceInRangeCheckSpec dailyPartitionSampleVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSampleVarianceInRange, dailyPartitionSampleVarianceInRange));
        this.dailyPartitionSampleVarianceInRange = dailyPartitionSampleVarianceInRange;
        propagateHierarchyIdToField(dailyPartitionSampleVarianceInRange, "daily_partition_sample_variance_in_range");
    }

    /**
     * Returns a population Variance in range check specification.
     * @return Population Variance in range check specification.
     */
    public ColumnPopulationVarianceInRangeCheckSpec getDailyPartitionPopulationVarianceInRange() {
        return dailyPartitionPopulationVarianceInRange;
    }

    /**
     * Sets a new specification of a population Variance in range check.
     * @param dailyPartitionPopulationVarianceInRange Population Variance in range check specification.
     */
    public void setDailyPartitionPopulationVarianceInRange(ColumnPopulationVarianceInRangeCheckSpec dailyPartitionPopulationVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionPopulationVarianceInRange, dailyPartitionPopulationVarianceInRange));
        this.dailyPartitionPopulationVarianceInRange = dailyPartitionPopulationVarianceInRange;
        propagateHierarchyIdToField(dailyPartitionPopulationVarianceInRange, "daily_partition_population_variance_in_range");
    }

    /**
     * Returns a sum in range check specification.
     * @return Sum in range check specification.
     */
    public ColumnSumInRangeCheckSpec getDailyPartitionSumInRange() {
        return dailyPartitionSumInRange;
    }

    /**
     * Sets a new specification of a sum in range check.
     * @param dailyPartitionSumInRange Sum in range check specification.
     */
    public void setDailyPartitionSumInRange(ColumnSumInRangeCheckSpec dailyPartitionSumInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumInRange, dailyPartitionSumInRange));
        this.dailyPartitionSumInRange = dailyPartitionSumInRange;
        propagateHierarchyIdToField(dailyPartitionSumInRange, "daily_partition_sum_in_range");
    }

    /**
     * Returns an invalid latitude count check specification.
     * @return invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getDailyPartitionInvalidLatitudeCount() {
        return dailyPartitionInvalidLatitudeCount;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param dailyPartitionInvalidLatitudeCount invalid latitude count check specification.
     */
    public void setDailyPartitionInvalidLatitudeCount(ColumnInvalidLatitudeCountCheckSpec dailyPartitionInvalidLatitudeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidLatitudeCount, dailyPartitionInvalidLatitudeCount));
        this.dailyPartitionInvalidLatitudeCount = dailyPartitionInvalidLatitudeCount;
        propagateHierarchyIdToField(dailyPartitionInvalidLatitudeCount, "daily_partition_invalid_latitude_count");
    }

    /**
     * Returns a valid latitude percent check specification.
     * @return Valid latitude percent check specification.
     */
    public ColumnValidLatitudePercentCheckSpec getDailyPartitionValidLatitudePercent() {
        return dailyPartitionValidLatitudePercent;
    }

    /**
     * Sets a new specification of a valid latitude percent check.
     * @param dailyPartitionValidLatitudePercent Valid latitude percent check specification.
     */
    public void setDailyPartitionValidLatitudePercent(ColumnValidLatitudePercentCheckSpec dailyPartitionValidLatitudePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValidLatitudePercent, dailyPartitionValidLatitudePercent));
        this.dailyPartitionValidLatitudePercent = dailyPartitionValidLatitudePercent;
        propagateHierarchyIdToField(dailyPartitionValidLatitudePercent, "daily_partition_valid_latitude_percent");
    }

    /**
     * Returns an invalid longitude count check specification.
     * @return invalid longitude count check specification.
     */
    public ColumnInvalidLongitudeCountCheckSpec getDailyPartitionInvalidLongitudeCount() {
        return dailyPartitionInvalidLongitudeCount;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param dailyPartitionInvalidLongitudeCount invalid longitude count check specification.
     */
    public void setDailyPartitionInvalidLongitudeCount(ColumnInvalidLongitudeCountCheckSpec dailyPartitionInvalidLongitudeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidLongitudeCount, dailyPartitionInvalidLongitudeCount));
        this.dailyPartitionInvalidLongitudeCount = dailyPartitionInvalidLongitudeCount;
        propagateHierarchyIdToField(dailyPartitionInvalidLongitudeCount, "daily_partition_invalid_longitude_count");
    }

    /**
     * Returns a valid longitude percent check specification.
     * @return Valid longitude percent check specification.
     */
    public ColumnValidLongitudePercentCheckSpec getDailyPartitionValidLongitudePercent() {
        return dailyPartitionValidLongitudePercent;
    }

    /**
     * Sets a new specification of a valid longitude percent check.
     * @param dailyPartitionValidLongitudePercent Valid longitude percent check specification.
     */
    public void setDailyPartitionValidLongitudePercent(ColumnValidLongitudePercentCheckSpec dailyPartitionValidLongitudePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValidLongitudePercent, dailyPartitionValidLongitudePercent));
        this.dailyPartitionValidLongitudePercent = dailyPartitionValidLongitudePercent;
        propagateHierarchyIdToField(dailyPartitionValidLongitudePercent, "daily_partition_valid_longitude_percent");
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