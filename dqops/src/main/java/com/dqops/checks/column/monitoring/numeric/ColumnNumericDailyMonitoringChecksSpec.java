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
            put("daily_negative_count", o -> o.dailyNegativeCount);
            put("daily_negative_percent", o -> o.dailyNegativePercent);
            put("daily_non_negative_count", o -> o.dailyNonNegativeCount);
            put("daily_non_negative_percent", o -> o.dailyNonNegativePercent);
            put("daily_expected_numbers_in_use_count", o -> o.dailyExpectedNumbersInUseCount);
            put("daily_number_value_in_set_percent", o -> o.dailyNumberValueInSetPercent);
            put("daily_values_in_range_numeric_percent", o -> o.dailyValuesInRangeNumericPercent);
            put("daily_values_in_range_integers_percent", o -> o.dailyValuesInRangeIntegersPercent);
            put("daily_value_below_min_value_count", o -> o.dailyValueBelowMinValueCount);
            put("daily_value_below_min_value_percent", o -> o.dailyValueBelowMinValuePercent);
            put("daily_value_above_max_value_count", o -> o.dailyValueAboveMaxValueCount);
            put("daily_value_above_max_value_percent", o -> o.dailyValueAboveMaxValuePercent);
            put("daily_max_in_range", o -> o.dailyMaxInRange);
            put("daily_min_in_range", o -> o.dailyMinInRange);
            put("daily_mean_in_range", o -> o.dailyMeanInRange);
            put("daily_percentile_in_range", o -> o.dailyPercentileInRange);
            put("daily_median_in_range", o -> o.dailyMedianInRange);
            put("daily_percentile_10_in_range", o -> o.dailyPercentile_10InRange);
            put("daily_percentile_25_in_range", o -> o.dailyPercentile_25InRange);
            put("daily_percentile_75_in_range", o -> o.dailyPercentile_75InRange);
            put("daily_percentile_90_in_range", o -> o.dailyPercentile_90InRange);
            put("daily_sample_stddev_in_range", o -> o.dailySampleStddevInRange);
            put("daily_population_stddev_in_range", o -> o.dailyPopulationStddevInRange);
            put("daily_sample_variance_in_range", o -> o.dailySampleVarianceInRange);
            put("daily_population_variance_in_range", o -> o.dailyPopulationVarianceInRange);
            put("daily_sum_in_range", o -> o.dailySumInRange);
            put("daily_invalid_latitude_count", o -> o.dailyInvalidLatitudeCount);
            put("daily_valid_latitude_percent", o -> o.dailyValidLatitudePercent);
            put("daily_invalid_longitude_count", o -> o.dailyInvalidLongitudeCount);
            put("daily_valid_longitude_percent", o -> o.dailyValidLongitudePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNegativeCountCheckSpec dailyNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNegativePercentCheckSpec dailyNegativePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNonNegativeCountCheckSpec dailyNonNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNonNegativePercentCheckSpec dailyNonNegativePercent;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnExpectedNumbersInUseCountCheckSpec dailyExpectedNumbersInUseCount;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNumberValueInSetPercentCheckSpec dailyNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValuesInRangeNumericPercentCheckSpec dailyValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValuesInRangeIntegersPercentCheckSpec dailyValuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValueBelowMinValueCountCheckSpec dailyValueBelowMinValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValueBelowMinValuePercentCheckSpec dailyValueBelowMinValuePercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValueAboveMaxValueCountCheckSpec dailyValueAboveMaxValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValueAboveMaxValuePercentCheckSpec dailyValueAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnMaxInRangeCheckSpec dailyMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnMinInRangeCheckSpec dailyMinInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnMeanInRangeCheckSpec dailyMeanInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentileInRangeCheckSpec dailyPercentileInRange;

    @JsonPropertyDescription("Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMedianInRangeCheckSpec dailyMedianInRange;

    @JsonPropertyDescription("Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentile10InRangeCheckSpec dailyPercentile_10InRange;

    @JsonPropertyDescription("Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentile25InRangeCheckSpec dailyPercentile_25InRange;

    @JsonPropertyDescription("Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentile75InRangeCheckSpec dailyPercentile_75InRange;

    @JsonPropertyDescription("Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPercentile90InRangeCheckSpec dailyPercentile_90InRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSampleStddevInRangeCheckSpec dailySampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPopulationStddevInRangeCheckSpec dailyPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSampleVarianceInRangeCheckSpec dailySampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPopulationVarianceInRangeCheckSpec dailyPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnSumInRangeCheckSpec dailySumInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnInvalidLatitudeCountCheckSpec dailyInvalidLatitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValidLatitudePercentCheckSpec dailyValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnInvalidLongitudeCountCheckSpec dailyInvalidLongitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnValidLongitudePercentCheckSpec dailyValidLongitudePercent;

    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getDailyNegativeCount() {
        return dailyNegativeCount;
    }

    /**
     * Sets a new specification of a negative values count check.
     * @param dailyNegativeCount Negative values count check specification.
     */
    public void setDailyNegativeCount(ColumnNegativeCountCheckSpec dailyNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyNegativeCount, dailyNegativeCount));
        this.dailyNegativeCount = dailyNegativeCount;
        propagateHierarchyIdToField(dailyNegativeCount, "daily_negative_count");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getDailyNegativePercent() {
        return dailyNegativePercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param dailyNegativePercent Negative values percentage check specification.
     */
    public void setDailyNegativePercent(ColumnNegativePercentCheckSpec dailyNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNegativePercent, dailyNegativePercent));
        this.dailyNegativePercent = dailyNegativePercent;
        propagateHierarchyIdToField(dailyNegativePercent, "daily_negative_percent");
    }

    /**
     * Returns a non-negative values count check specification.
     * @return Non-negative values count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getDailyNonNegativeCount() {
        return dailyNonNegativeCount;
    }

    /**
     * Sets a new specification of a non-negative values count check.
     * @param dailyNonNegativeCount Non-negative values count check specification.
     */
    public void setDailyNonNegativeCount(ColumnNonNegativeCountCheckSpec dailyNonNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyNonNegativeCount, dailyNonNegativeCount));
        this.dailyNonNegativeCount = dailyNonNegativeCount;
        propagateHierarchyIdToField(dailyNonNegativeCount, "daily_non_negative_count");
    }

    /**
     * Returns a non-negative values percentage check specification.
     * @return Non-negative values percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getDailyNonNegativePercent() {
        return dailyNonNegativePercent;
    }

    /**
     * Sets a new specification of a non-negative values percentage check.
     * @param dailyNonNegativePercent Non-negative values percentage check specification.
     */
    public void setDailyNonNegativePercent(ColumnNonNegativePercentCheckSpec dailyNonNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNonNegativePercent, dailyNonNegativePercent));
        this.dailyNonNegativePercent = dailyNonNegativePercent;
        propagateHierarchyIdToField(dailyNonNegativePercent, "daily_non_negative_percent");
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
     * Returns a numbers valid percent check specification.
     * @return Numbers valid percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getDailyNumberValueInSetPercent() {
        return dailyNumberValueInSetPercent;
    }

    /**
     * Sets a new specification of a numbers valid percent check.
     * @param dailyNumberValueInSetPercent Number valid percent check specification.
     */
    public void setDailyNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec dailyNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNumberValueInSetPercent, dailyNumberValueInSetPercent));
        this.dailyNumberValueInSetPercent = dailyNumberValueInSetPercent;
        propagateHierarchyIdToField(dailyNumberValueInSetPercent, "daily_number_value_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeNumericPercentCheckSpec getDailyValuesInRangeNumericPercent() {
        return dailyValuesInRangeNumericPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyValuesInRangeNumericPercent Numbers in set percent check.
     */
    public void setDailyValuesInRangeNumericPercent(ColumnValuesInRangeNumericPercentCheckSpec dailyValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyValuesInRangeNumericPercent, dailyValuesInRangeNumericPercent));
        this.dailyValuesInRangeNumericPercent = dailyValuesInRangeNumericPercent;
        propagateHierarchyIdToField(dailyValuesInRangeNumericPercent, "daily_values_in_range_numeric_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeIntegersPercentCheckSpec getDailyValuesInRangeIntegersPercent() {
        return dailyValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyValuesInRangeIntegersPercent Numbers in set percent check specification.
     */
    public void setDailyValuesInRangeIntegersPercent(ColumnValuesInRangeIntegersPercentCheckSpec dailyValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyValuesInRangeIntegersPercent, dailyValuesInRangeIntegersPercent));
        this.dailyValuesInRangeIntegersPercent = dailyValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(dailyValuesInRangeIntegersPercent, "daily_values_in_range_integers_percent");
    }

    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnValueBelowMinValueCountCheckSpec getDailyValueBelowMinValueCount() {
        return dailyValueBelowMinValueCount;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param dailyValueBelowMinValueCount Numeric value below min value count check.
     */
    public void setDailyValueBelowMinValueCount(ColumnValueBelowMinValueCountCheckSpec dailyValueBelowMinValueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyValueBelowMinValueCount, dailyValueBelowMinValueCount));
        this.dailyValueBelowMinValueCount = dailyValueBelowMinValueCount;
        propagateHierarchyIdToField(dailyValueBelowMinValueCount, "daily_value_below_min_value_count");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnValueBelowMinValuePercentCheckSpec getDailyValueBelowMinValuePercent() {
        return dailyValueBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param dailyValueBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setDailyValueBelowMinValuePercent(ColumnValueBelowMinValuePercentCheckSpec dailyValueBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyValueBelowMinValuePercent, dailyValueBelowMinValuePercent));
        this.dailyValueBelowMinValuePercent = dailyValueBelowMinValuePercent;
        propagateHierarchyIdToField(dailyValueBelowMinValuePercent, "daily_value_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnValueAboveMaxValueCountCheckSpec getDailyValueAboveMaxValueCount() {
        return dailyValueAboveMaxValueCount;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param dailyValueAboveMaxValueCount Numeric value above max value count check.
     */
    public void setDailyValueAboveMaxValueCount(ColumnValueAboveMaxValueCountCheckSpec dailyValueAboveMaxValueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyValueAboveMaxValueCount, dailyValueAboveMaxValueCount));
        this.dailyValueAboveMaxValueCount = dailyValueAboveMaxValueCount;
        propagateHierarchyIdToField(dailyValueAboveMaxValueCount, "daily_value_above_max_value_count");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnValueAboveMaxValuePercentCheckSpec getDailyValueAboveMaxValuePercent() {
        return dailyValueAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param dailyValueAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setDailyValueAboveMaxValuePercent(ColumnValueAboveMaxValuePercentCheckSpec dailyValueAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyValueAboveMaxValuePercent, dailyValueAboveMaxValuePercent));
        this.dailyValueAboveMaxValuePercent = dailyValueAboveMaxValuePercent;
        propagateHierarchyIdToField(dailyValueAboveMaxValuePercent, "daily_value_above_max_value_percent");
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
     * Returns an invalid latitude count check specification.
     * @return Invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getDailyInvalidLatitudeCount() {
        return dailyInvalidLatitudeCount;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param dailyInvalidLatitudeCount Invalid latitude count check specification.
     */
    public void setDailyInvalidLatitudeCount(ColumnInvalidLatitudeCountCheckSpec dailyInvalidLatitudeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidLatitudeCount, dailyInvalidLatitudeCount));
        this.dailyInvalidLatitudeCount = dailyInvalidLatitudeCount;
        propagateHierarchyIdToField(dailyInvalidLatitudeCount, "daily_invalid_latitude_count");
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
    public ColumnInvalidLongitudeCountCheckSpec getDailyInvalidLongitudeCount() {
        return dailyInvalidLongitudeCount;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param dailyInvalidLongitudeCount Invalid longitude count check specification.
     */
    public void setDailyInvalidLongitudeCount(ColumnInvalidLongitudeCountCheckSpec dailyInvalidLongitudeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidLongitudeCount, dailyInvalidLongitudeCount));
        this.dailyInvalidLongitudeCount = dailyInvalidLongitudeCount;
        propagateHierarchyIdToField(dailyInvalidLongitudeCount, "daily_invalid_longitude_count");
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