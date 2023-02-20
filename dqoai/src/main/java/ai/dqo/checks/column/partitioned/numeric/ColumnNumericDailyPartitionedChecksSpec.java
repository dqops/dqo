/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.checks.column.partitioned.numeric;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.numeric.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
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
            put("daily_partition_numbers_in_set_count", o -> o.dailyPartitionNumbersInSetCount);
            put("daily_partition_numbers_in_set_percent", o -> o.dailyPartitionNumbersInSetPercent);
            put("daily_partition_values_in_range_numeric_percent", o -> o.dailyPartitionValuesInRangeNumericPercent);
            put("daily_partition_values_in_range_integers_percent", o -> o.dailyPartitionValuesInRangeIntegersPercent);
            put("daily_partition_value_below_min_value_count", o -> o.dailyPartitionValueBelowMinValueCount);
            put("daily_partition_value_below_min_value_percent", o -> o.dailyPartitionValueBelowMinValuePercent);
            put("daily_partition_value_above_max_value_count", o -> o.dailyPartitionValueAboveMaxValueCount);
            put("daily_partition_value_above_max_value_percent", o -> o.dailyPartitionValueAboveMaxValuePercent);
            put("daily_partition_max_in_range", o -> o.dailyPartitionMaxInRange);
            put("daily_partition_min_in_range", o -> o.dailyPartitionMinInRange);
            put("daily_partition_mean_in_range", o -> o.dailyPartitionMeanInRange);
            put("daily_partition_percentile", o -> o.dailyPartitionPercentile);
            put("daily_partition_sample_stddev_in_range", o -> o.dailyPartitionSampleStddevInRange);
            put("daily_partition_population_stddev_in_range", o -> o.dailyPartitionPopulationStddevInRange);
            put("daily_partition_sample_variance_in_range", o -> o.dailyPartitionSampleVarianceInRange);
            put("daily_partition_population_variance_in_range", o -> o.dailyPartitionPopulationVarianceInRange);
            put("daily_partition_sum_in_range", o -> o.dailyPartitionSumInRange);
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

    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNumbersInSetCountCheckSpec dailyPartitionNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNumbersInSetPercentCheckSpec dailyPartitionNumbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValuesInRangeNumericPercentCheckSpec dailyPartitionValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValuesInRangeIntegersPercentCheckSpec dailyPartitionValuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts those values with value below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValueBelowMinValueCountCheckSpec dailyPartitionValueBelowMinValueCount;

    @JsonPropertyDescription("The check percentage of those values with value below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValueBelowMinValuePercentCheckSpec dailyPartitionValueBelowMinValuePercent;

    @JsonPropertyDescription("The check counts those values with value above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValueAboveMaxValueCountCheckSpec dailyPartitionValueAboveMaxValueCount;

    @JsonPropertyDescription("The check percentage of those values with value above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValueAboveMaxValuePercentCheckSpec dailyPartitionValueAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxInRangeCheckSpec dailyPartitionMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinInRangeCheckSpec dailyPartitionMinInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMeanInRangeCheckSpec dailyPartitionMeanInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPercentileCheckSpec dailyPartitionPercentile;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnSampleStddevInRangeCheckSpec dailyPartitionSampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPopulationStddevInRangeCheckSpec dailyPartitionPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample Variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnSampleVarianceInRangeCheckSpec dailyPartitionSampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population Variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnPopulationVarianceInRangeCheckSpec dailyPartitionPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnSumInRangeCheckSpec dailyPartitionSumInRange;



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
    public ColumnNumbersInSetCountCheckSpec getDailyPartitionNumbersInSetCount() {
        return dailyPartitionNumbersInSetCount;
    }

    /**
     * Sets a new specification of a numbers in set count check.
     * @param dailyPartitionNumbersInSetCount Numbers in set count check specification.
     */
    public void setDailyPartitionNumbersInSetCount(ColumnNumbersInSetCountCheckSpec dailyPartitionNumbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNumbersInSetCount, dailyPartitionNumbersInSetCount));
        this.dailyPartitionNumbersInSetCount = dailyPartitionNumbersInSetCount;
        propagateHierarchyIdToField(dailyPartitionNumbersInSetCount, "daily_partition_numbers_in_set_count");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumbersInSetPercentCheckSpec getDailyPartitionNumbersInSetPercent() {
        return dailyPartitionNumbersInSetPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyPartitionNumbersInSetPercent Numbers in set percent check specification.
     */
    public void setDailyPartitionNumbersInSetPercent(ColumnNumbersInSetPercentCheckSpec dailyPartitionNumbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNumbersInSetPercent, dailyPartitionNumbersInSetPercent));
        this.dailyPartitionNumbersInSetPercent = dailyPartitionNumbersInSetPercent;
        propagateHierarchyIdToField(dailyPartitionNumbersInSetPercent, "daily_partition_numbers_in_set_percent");
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
     * Returns a percentile check specification.
     * @return Percentile check specification.
     */
    public ColumnPercentileCheckSpec getDailyPartitionPercentile() {
        return dailyPartitionPercentile;
    }

    /**
     * Sets a new specification of a percentile check.
     * @param dailyPartitionPercentile percentile check specification.
     */
    public void setDailyPartitionPercentile(ColumnPercentileCheckSpec dailyPartitionPercentile) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionPercentile, dailyPartitionPercentile));
        this.dailyPartitionPercentile = dailyPartitionPercentile;
        propagateHierarchyIdToField(dailyPartitionPercentile, "daily_partition_percentile");
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
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }
}