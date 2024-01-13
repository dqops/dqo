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
 * Container of built-in preconfigured data quality monitoring on a column level that are checking numeric values at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_number_below_min_value", o -> o.dailyNumberBelowMinValue);
            put("daily_number_above_max_value", o -> o.dailyNumberAboveMaxValue);
            put("daily_negative_values", o -> o.dailyNegativeValues);
            put("daily_negative_values_percent", o -> o.dailyNegativeValuesPercent);
            put("daily_number_below_min_value_percent", o -> o.dailyNumberBelowMinValuePercent);
            put("daily_number_above_max_value_percent", o -> o.dailyNumberAboveMaxValuePercent);
            put("daily_number_in_range_percent", o -> o.dailyNumberInRangePercent);
            put("daily_integer_in_range_percent", o -> o.dailyIntegerInRangePercent);
            put("daily_min_in_range", o -> o.dailyMinInRange);
            put("daily_max_in_range", o -> o.dailyMaxInRange);
            put("daily_sum_in_range", o -> o.dailySumInRange);
            put("daily_mean_in_range", o -> o.dailyMeanInRange);
            put("daily_median_in_range", o -> o.dailyMedianInRange);
            put("daily_percentile_in_range", o -> o.dailyPercentileInRange);
            put("daily_percentile_10_in_range", o -> o.dailyPercentile_10InRange);
            put("daily_percentile_25_in_range", o -> o.dailyPercentile_25InRange);
            put("daily_percentile_75_in_range", o -> o.dailyPercentile_75InRange);
            put("daily_percentile_90_in_range", o -> o.dailyPercentile_90InRange);
            put("daily_sample_stddev_in_range", o -> o.dailySampleStddevInRange);
            put("daily_population_stddev_in_range", o -> o.dailyPopulationStddevInRange);
            put("daily_sample_variance_in_range", o -> o.dailySampleVarianceInRange);
            put("daily_population_variance_in_range", o -> o.dailyPopulationVarianceInRange);
            put("daily_invalid_latitude", o -> o.dailyInvalidLatitude);
            put("daily_valid_latitude_percent", o -> o.dailyValidLatitudePercent);
            put("daily_invalid_longitude", o -> o.dailyInvalidLongitude);
            put("daily_valid_longitude_percent", o -> o.dailyValidLongitudePercent);
            put("daily_non_negative_values", o -> o.dailyNonNegativeValues);
            put("daily_non_negative_values_percent", o -> o.dailyNonNegativeValuesPercent);
        }
    };

    @JsonPropertyDescription("The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNumberBelowMinValueCheckSpec dailyNumberBelowMinValue;

    @JsonPropertyDescription("The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNumberAboveMaxValueCheckSpec dailyNumberAboveMaxValue;

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNegativeCountCheckSpec dailyNegativeValues;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNegativePercentCheckSpec dailyNegativeValuesPercent;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNumberBelowMinValuePercentCheckSpec dailyNumberBelowMinValuePercent;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNumberAboveMaxValuePercentCheckSpec dailyNumberAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNumberInRangePercentCheckSpec dailyNumberInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnIntegerInRangePercentCheckSpec dailyIntegerInRangePercent;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnMinInRangeCheckSpec dailyMinInRange;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnMaxInRangeCheckSpec dailyMaxInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSumInRangeCheckSpec dailySumInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnMeanInRangeCheckSpec dailyMeanInRange;

    @JsonPropertyDescription("Verifies that the median of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.")
    private ColumnMedianInRangeCheckSpec dailyMedianInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentileInRangeCheckSpec dailyPercentileInRange;

    @JsonPropertyDescription("Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentile10InRangeCheckSpec dailyPercentile_10InRange;

    @JsonPropertyDescription("Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentile25InRangeCheckSpec dailyPercentile_25InRange;

    @JsonPropertyDescription("Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentile75InRangeCheckSpec dailyPercentile_75InRange;

    @JsonPropertyDescription("Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentile90InRangeCheckSpec dailyPercentile_90InRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSampleStddevInRangeCheckSpec dailySampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPopulationStddevInRangeCheckSpec dailyPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSampleVarianceInRangeCheckSpec dailySampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the expected range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPopulationVarianceInRangeCheckSpec dailyPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnInvalidLatitudeCountCheckSpec dailyInvalidLatitude;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValidLatitudePercentCheckSpec dailyValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnInvalidLongitudeCountCheckSpec dailyInvalidLongitude;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValidLongitudePercentCheckSpec dailyValidLongitudePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNonNegativeCountCheckSpec dailyNonNegativeValues;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNonNegativePercentCheckSpec dailyNonNegativeValuesPercent;


    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnNumberBelowMinValueCheckSpec getDailyNumberBelowMinValue() {
        return dailyNumberBelowMinValue;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param dailyNumberBelowMinValue Numeric value below min value count check.
     */
    public void setDailyNumberBelowMinValue(ColumnNumberBelowMinValueCheckSpec dailyNumberBelowMinValue) {
        this.setDirtyIf(!Objects.equals(this.dailyNumberBelowMinValue, dailyNumberBelowMinValue));
        this.dailyNumberBelowMinValue = dailyNumberBelowMinValue;
        propagateHierarchyIdToField(dailyNumberBelowMinValue, "daily_number_below_min_value");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnNumberAboveMaxValueCheckSpec getDailyNumberAboveMaxValue() {
        return dailyNumberAboveMaxValue;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param dailyNumberAboveMaxValue Numeric value above max value count check.
     */
    public void setDailyNumberAboveMaxValue(ColumnNumberAboveMaxValueCheckSpec dailyNumberAboveMaxValue) {
        this.setDirtyIf(!Objects.equals(this.dailyNumberAboveMaxValue, dailyNumberAboveMaxValue));
        this.dailyNumberAboveMaxValue = dailyNumberAboveMaxValue;
        propagateHierarchyIdToField(dailyNumberAboveMaxValue, "daily_number_above_max_value");
    }

    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getDailyNegativeValues() {
        return dailyNegativeValues;
    }

    /**
     * Sets a new specification of a negative values count check.
     * @param dailyNegativeValues Negative values count check specification.
     */
    public void setDailyNegativeValues(ColumnNegativeCountCheckSpec dailyNegativeValues) {
        this.setDirtyIf(!Objects.equals(this.dailyNegativeValues, dailyNegativeValues));
        this.dailyNegativeValues = dailyNegativeValues;
        propagateHierarchyIdToField(dailyNegativeValues, "daily_negative_values");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getDailyNegativeValuesPercent() {
        return dailyNegativeValuesPercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param dailyNegativeValuesPercent Negative values percentage check specification.
     */
    public void setDailyNegativeValuesPercent(ColumnNegativePercentCheckSpec dailyNegativeValuesPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNegativeValuesPercent, dailyNegativeValuesPercent));
        this.dailyNegativeValuesPercent = dailyNegativeValuesPercent;
        propagateHierarchyIdToField(dailyNegativeValuesPercent, "daily_negative_values_percent");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnNumberBelowMinValuePercentCheckSpec getDailyNumberBelowMinValuePercent() {
        return dailyNumberBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param dailyNumberBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setDailyNumberBelowMinValuePercent(ColumnNumberBelowMinValuePercentCheckSpec dailyNumberBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNumberBelowMinValuePercent, dailyNumberBelowMinValuePercent));
        this.dailyNumberBelowMinValuePercent = dailyNumberBelowMinValuePercent;
        propagateHierarchyIdToField(dailyNumberBelowMinValuePercent, "daily_number_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnNumberAboveMaxValuePercentCheckSpec getDailyNumberAboveMaxValuePercent() {
        return dailyNumberAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param dailyNumberAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setDailyNumberAboveMaxValuePercent(ColumnNumberAboveMaxValuePercentCheckSpec dailyNumberAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNumberAboveMaxValuePercent, dailyNumberAboveMaxValuePercent));
        this.dailyNumberAboveMaxValuePercent = dailyNumberAboveMaxValuePercent;
        propagateHierarchyIdToField(dailyNumberAboveMaxValuePercent, "daily_number_above_max_value_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberInRangePercentCheckSpec getDailyNumberInRangePercent() {
        return dailyNumberInRangePercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyNumberInRangePercent Numbers in set percent check.
     */
    public void setDailyNumberInRangePercent(ColumnNumberInRangePercentCheckSpec dailyNumberInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNumberInRangePercent, dailyNumberInRangePercent));
        this.dailyNumberInRangePercent = dailyNumberInRangePercent;
        propagateHierarchyIdToField(dailyNumberInRangePercent, "daily_number_in_range_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnIntegerInRangePercentCheckSpec getDailyIntegerInRangePercent() {
        return dailyIntegerInRangePercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyIntegerInRangePercent Numbers in set percent check specification.
     */
    public void setDailyIntegerInRangePercent(ColumnIntegerInRangePercentCheckSpec dailyIntegerInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyIntegerInRangePercent, dailyIntegerInRangePercent));
        this.dailyIntegerInRangePercent = dailyIntegerInRangePercent;
        propagateHierarchyIdToField(dailyIntegerInRangePercent, "daily_integer_in_range_percent");
    }

    /**
     * Returns a min in range check specification.
     * @return Min in range check specification.
     */
    public ColumnMinInRangeCheckSpec getDailyMinInRange() {
        return dailyMinInRange;
    }

    /**
     * Sets a new specification of a min in range check.
     * @param dailyMinInRange Min in range check specification.
     */
    public void setDailyMinInRange(ColumnMinInRangeCheckSpec dailyMinInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyMinInRange, dailyMinInRange));
        this.dailyMinInRange = dailyMinInRange;
        propagateHierarchyIdToField(dailyMinInRange, "daily_min_in_range");
    }

    /**
     * Returns a max in range check specification.
     * @return Max in range check specification.
     */
    public ColumnMaxInRangeCheckSpec getDailyMaxInRange() {
        return dailyMaxInRange;
    }

    /**
     * Sets a new specification of a max in range check.
     * @param dailyMaxInRange Max in range check specification.
     */
    public void setDailyMaxInRange(ColumnMaxInRangeCheckSpec dailyMaxInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyMaxInRange, dailyMaxInRange));
        this.dailyMaxInRange = dailyMaxInRange;
        propagateHierarchyIdToField(dailyMaxInRange, "daily_max_in_range");
    }

    /**
     * Returns a sum in range check specification.
     * @return Sum in range check specification.
     */
    public ColumnSumInRangeCheckSpec getDailySumInRange() {
        return dailySumInRange;
    }

    /**
     * Sets a new specification of a sum in range check.
     * @param dailySumInRange Sum in range check specification.
     */
    public void setDailySumInRange(ColumnSumInRangeCheckSpec dailySumInRange) {
        this.setDirtyIf(!Objects.equals(this.dailySumInRange, dailySumInRange));
        this.dailySumInRange = dailySumInRange;
        propagateHierarchyIdToField(dailySumInRange, "daily_sum_in_range");
    }

    /**
     * Returns a mean in range check specification.
     * @return Mean in range check specification.
     */
    public ColumnMeanInRangeCheckSpec getDailyMeanInRange() {
        return dailyMeanInRange;
    }

    /**
     * Sets a new specification of a mean in range check.
     * @param dailyMeanInRange Mean in range check specification.
     */
    public void setDailyMeanInRange(ColumnMeanInRangeCheckSpec dailyMeanInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanInRange, dailyMeanInRange));
        this.dailyMeanInRange = dailyMeanInRange;
        propagateHierarchyIdToField(dailyMeanInRange, "daily_mean_in_range");
    }

    /**
     * Returns a median in range check specification.
     * @return median in range check specification.
     */
    public ColumnMedianInRangeCheckSpec getDailyMedianInRange() {
        return dailyMedianInRange;
    }

    /**
     * Sets a new specification of a median in range check.
     * @param dailyMedianInRange median in range check specification.
     */
    public void setDailyMedianInRange(ColumnMedianInRangeCheckSpec dailyMedianInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianInRange, dailyMedianInRange));
        this.dailyMedianInRange = dailyMedianInRange;
        propagateHierarchyIdToField(dailyMedianInRange, "daily_median_in_range");
    }

    /**
     * Returns a percentile in range check specification.
     * @return Percentile in range check specification.
     */
    public ColumnPercentileInRangeCheckSpec getDailyPercentileInRange() {
        return dailyPercentileInRange;
    }

    /**
     * Sets a new specification of a percentile in range check.
     * @param dailyPercentileInRange Percentile in range check specification.
     */
    public void setDailyPercentileInRange(ColumnPercentileInRangeCheckSpec dailyPercentileInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPercentileInRange, dailyPercentileInRange));
        this.dailyPercentileInRange = dailyPercentileInRange;
        propagateHierarchyIdToField(dailyPercentileInRange, "daily_percentile_in_range");
    }

    /**
     * Returns a percentile 10 in range check specification.
     * @return Percentile 10 in range check specification.
     */
    public ColumnPercentile10InRangeCheckSpec getDailyPercentile_10InRange() {
        return dailyPercentile_10InRange;
    }

    /**
     * Sets a new specification of a percentile 10 in range check.
     * @param dailyPercentile_10InRange Percentile 10 in range check specification.
     */
    public void setDailyPercentile_10InRange(ColumnPercentile10InRangeCheckSpec dailyPercentile_10InRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPercentile_10InRange, dailyPercentile_10InRange));
        this.dailyPercentile_10InRange = dailyPercentile_10InRange;
        propagateHierarchyIdToField(dailyPercentile_10InRange, "daily_percentile_10_in_range");
    }

    /**
     * Returns a percentile 25 in range check specification.
     * @return Percentile 25 in range check specification.
     */
    public ColumnPercentile25InRangeCheckSpec getDailyPercentile_25InRange() {
        return dailyPercentile_25InRange;
    }

    /**
     * Sets a new specification of a percentile 25 in range check.
     * @param dailyPercentile_25InRange Percentile 25 in range check specification.
     */
    public void setDailyPercentile_25InRange(ColumnPercentile25InRangeCheckSpec dailyPercentile_25InRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPercentile_25InRange, dailyPercentile_25InRange));
        this.dailyPercentile_25InRange = dailyPercentile_25InRange;
        propagateHierarchyIdToField(dailyPercentile_25InRange, "daily_percentile_25_in_range");
    }

    /**
     * Returns a percentile 75 in range check specification.
     * @return Percentile 75 in range check specification.
     */
    public ColumnPercentile75InRangeCheckSpec getDailyPercentile_75InRange() {
        return dailyPercentile_75InRange;
    }

    /**
     * Sets a new specification of a percentile 75 in range check.
     * @param dailyPercentile_75InRange Percentile 75 in range check specification.
     */
    public void setDailyPercentile_75InRange(ColumnPercentile75InRangeCheckSpec dailyPercentile_75InRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPercentile_75InRange, dailyPercentile_75InRange));
        this.dailyPercentile_75InRange = dailyPercentile_75InRange;
        propagateHierarchyIdToField(dailyPercentile_75InRange, "daily_percentile_75_in_range");
    }

    /**
     * Returns a percentile 90 in range check specification.
     * @return Percentile 90 in range check specification.
     */
    public ColumnPercentile90InRangeCheckSpec getDailyPercentile_90InRange() {
        return dailyPercentile_90InRange;
    }

    /**
     * Sets a new specification of a percentile 90 in range check.
     * @param dailyPercentile_90InRange Percentile 90 in range check specification.
     */
    public void setDailyPercentile_90InRange(ColumnPercentile90InRangeCheckSpec dailyPercentile_90InRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPercentile_90InRange, dailyPercentile_90InRange));
        this.dailyPercentile_90InRange = dailyPercentile_90InRange;
        propagateHierarchyIdToField(dailyPercentile_90InRange, "daily_percentile_90_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleStddevInRangeCheckSpec getDailySampleStddevInRange() {
        return dailySampleStddevInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param dailySampleStddevInRange Sample standard deviation in range check specification.
     */
    public void setDailySampleStddevInRange(ColumnSampleStddevInRangeCheckSpec dailySampleStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.dailySampleStddevInRange, dailySampleStddevInRange));
        this.dailySampleStddevInRange = dailySampleStddevInRange;
        propagateHierarchyIdToField(dailySampleStddevInRange, "daily_sample_stddev_in_range");
    }

    /**
     * Returns a population standard deviation in range check specification.
     * @return Population standard deviation in range check specification.
     */
    public ColumnPopulationStddevInRangeCheckSpec getDailyPopulationStddevInRange() {
        return dailyPopulationStddevInRange;
    }

    /**
     * Sets a new specification of a population standard deviation in range check.
     * @param dailyPopulationStddevInRange Population standard deviation in range check specification.
     */
    public void setDailyPopulationStddevInRange(ColumnPopulationStddevInRangeCheckSpec dailyPopulationStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPopulationStddevInRange, dailyPopulationStddevInRange));
        this.dailyPopulationStddevInRange = dailyPopulationStddevInRange;
        propagateHierarchyIdToField(dailyPopulationStddevInRange, "daily_population_stddev_in_range");
    }

    /**
     * Returns a sample variance in range check specification.
     * @return Sample variance in range check specification.
     */
    public ColumnSampleVarianceInRangeCheckSpec getDailySampleVarianceInRange() {
        return dailySampleVarianceInRange;
    }

    /**
     * Sets a new specification of a sample variance in range check.
     * @param dailySampleVarianceInRange Sample variance in range check specification.
     */
    public void setDailySampleVarianceInRange(ColumnSampleVarianceInRangeCheckSpec dailySampleVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.dailySampleVarianceInRange, dailySampleVarianceInRange));
        this.dailySampleVarianceInRange = dailySampleVarianceInRange;
        propagateHierarchyIdToField(dailySampleVarianceInRange, "daily_sample_variance_in_range");
    }

    /**
     * Returns a population variance in range check specification.
     * @return Population variance in range check specification.
     */
    public ColumnPopulationVarianceInRangeCheckSpec getDailyPopulationVarianceInRange() {
        return dailyPopulationVarianceInRange;
    }

    /**
     * Sets a new specification of a population variance in range check.
     * @param dailyPopulationVarianceInRange Population variance in range check specification.
     */
    public void setDailyPopulationVarianceInRange(ColumnPopulationVarianceInRangeCheckSpec dailyPopulationVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyPopulationVarianceInRange, dailyPopulationVarianceInRange));
        this.dailyPopulationVarianceInRange = dailyPopulationVarianceInRange;
        propagateHierarchyIdToField(dailyPopulationVarianceInRange, "daily_population_variance_in_range");
    }

    /**
     * Returns an invalid latitude count check specification.
     * @return Invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getDailyInvalidLatitude() {
        return dailyInvalidLatitude;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param dailyInvalidLatitude Invalid latitude count check specification.
     */
    public void setDailyInvalidLatitude(ColumnInvalidLatitudeCountCheckSpec dailyInvalidLatitude) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidLatitude, dailyInvalidLatitude));
        this.dailyInvalidLatitude = dailyInvalidLatitude;
        propagateHierarchyIdToField(dailyInvalidLatitude, "daily_invalid_latitude");
    }

    /**
     * Returns a valid latitude percent check specification.
     * @return Valid latitude percent check specification.
     */
    public ColumnValidLatitudePercentCheckSpec getDailyValidLatitudePercent() {
        return dailyValidLatitudePercent;
    }

    /**
     * Sets a new specification of a valid latitude percent check.
     * @param dailyValidLatitudePercent Valid latitude percent check specification.
     */
    public void setDailyValidLatitudePercent(ColumnValidLatitudePercentCheckSpec dailyValidLatitudePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyValidLatitudePercent, dailyValidLatitudePercent));
        this.dailyValidLatitudePercent = dailyValidLatitudePercent;
        propagateHierarchyIdToField(dailyValidLatitudePercent, "daily_valid_latitude_percent");
    }

    /**
     * Returns an invalid longitude count check specification.
     * @return Invalid longitude count check specification.
     */
    public ColumnInvalidLongitudeCountCheckSpec getDailyInvalidLongitude() {
        return dailyInvalidLongitude;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param dailyInvalidLongitude Invalid longitude count check specification.
     */
    public void setDailyInvalidLongitude(ColumnInvalidLongitudeCountCheckSpec dailyInvalidLongitude) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidLongitude, dailyInvalidLongitude));
        this.dailyInvalidLongitude = dailyInvalidLongitude;
        propagateHierarchyIdToField(dailyInvalidLongitude, "daily_invalid_longitude");
    }

    /**
     * Returns a valid longitude percent check specification.
     * @return Valid longitude percent check specification.
     */
    public ColumnValidLongitudePercentCheckSpec getDailyValidLongitudePercent() {
        return dailyValidLongitudePercent;
    }

    /**
     * Sets a new specification of a valid longitude percent check.
     * @param dailyValidLongitudePercent Valid longitude percent check specification.
     */
    public void setDailyValidLongitudePercent(ColumnValidLongitudePercentCheckSpec dailyValidLongitudePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyValidLongitudePercent, dailyValidLongitudePercent));
        this.dailyValidLongitudePercent = dailyValidLongitudePercent;
        propagateHierarchyIdToField(dailyValidLongitudePercent, "daily_valid_longitude_percent");
    }

    /**
     * Returns a non-negative values count check specification.
     * @return Non-negative values count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getDailyNonNegativeValues() {
        return dailyNonNegativeValues;
    }

    /**
     * Sets a new specification of a non-negative values count check.
     * @param dailyNonNegativeValues Non-negative values count check specification.
     */
    public void setDailyNonNegativeValues(ColumnNonNegativeCountCheckSpec dailyNonNegativeValues) {
        this.setDirtyIf(!Objects.equals(this.dailyNonNegativeValues, dailyNonNegativeValues));
        this.dailyNonNegativeValues = dailyNonNegativeValues;
        propagateHierarchyIdToField(dailyNonNegativeValues, "daily_non_negative_values");
    }

    /**
     * Returns a non-negative values percentage check specification.
     * @return Non-negative values percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getDailyNonNegativeValuesPercent() {
        return dailyNonNegativeValuesPercent;
    }

    /**
     * Sets a new specification of a non-negative values percentage check.
     * @param dailyNonNegativeValuesPercent Non-negative values percentage check specification.
     */
    public void setDailyNonNegativeValuesPercent(ColumnNonNegativePercentCheckSpec dailyNonNegativeValuesPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNonNegativeValuesPercent, dailyNonNegativeValuesPercent));
        this.dailyNonNegativeValuesPercent = dailyNonNegativeValuesPercent;
        propagateHierarchyIdToField(dailyNonNegativeValuesPercent, "daily_non_negative_values_percent");
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
    public ColumnNumericDailyMonitoringChecksSpec deepClone() {
        return (ColumnNumericDailyMonitoringChecksSpec)super.deepClone();
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