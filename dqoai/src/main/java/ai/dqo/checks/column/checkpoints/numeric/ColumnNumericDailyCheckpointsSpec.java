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
package ai.dqo.checks.column.checkpoints.numeric;

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
 * Container of built-in preconfigured data quality checkpoints on a column level that are checking numeric values at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_negative_count", o -> o.dailyCheckpointNegativeCount);
            put("daily_checkpoint_negative_percent", o -> o.dailyCheckpointNegativePercent);
            put("daily_checkpoint_non_negative_count", o -> o.dailyCheckpointNonNegativeCount);
            put("daily_checkpoint_non_negative_percent", o -> o.dailyCheckpointNonNegativePercent);
            put("daily_checkpoint_numbers_in_set_count", o -> o.dailyCheckpointNumbersInSetCount);
            put("daily_checkpoint_numbers_in_set_percent", o -> o.dailyCheckpointNumbersInSetPercent);
            put("daily_checkpoint_values_in_range_numeric_percent", o -> o.dailyCheckpointValuesInRangeNumericPercent);
            put("daily_checkpoint_values_in_range_integers_percent", o -> o.dailyCheckpointValuesInRangeIntegersPercent);
            put("daily_checkpoint_value_below_min_value_count", o -> o.dailyCheckpointValueBelowMinValueCount);
            put("daily_checkpoint_value_below_min_value_percent", o -> o.dailyCheckpointValueBelowMinValuePercent);
            put("daily_checkpoint_value_above_max_value_count", o -> o.dailyCheckpointValueAboveMaxValueCount);
            put("daily_checkpoint_value_above_max_value_percent", o -> o.dailyCheckpointValueAboveMaxValuePercent);
            put("daily_checkpoint_max_in_range", o -> o.dailyCheckpointMaxInRange);
            put("daily_checkpoint_min_in_range", o -> o.dailyCheckpointMinInRange);
            put("daily_checkpoint_mean_in_range", o -> o.dailyCheckpointMeanInRange);
            put("daily_checkpoint_percentile_in_range", o -> o.dailyCheckpointPercentileInRange);
            put("daily_checkpoint_sample_stddev_in_range", o -> o.dailyCheckpointSampleStddevInRange);
            put("daily_checkpoint_population_stddev_in_range", o -> o.dailyCheckpointPopulationStddevInRange);
            put("daily_checkpoint_sample_variance_in_range", o -> o.dailyCheckpointSampleVarianceInRange);
            put("daily_checkpoint_population_variance_in_range", o -> o.dailyCheckpointPopulationVarianceInRange);
            put("daily_checkpoint_sum_in_range", o -> o.dailyCheckpointSumInRange);
            put("daily_checkpoint_invalid_latitude_count", o -> o.dailyCheckpointInvalidLatitudeCount);
            put("daily_checkpoint_valid_latitude_percent", o -> o.dailyCheckpointValidLatitudePercent);
            put("daily_checkpoint_invalid_longitude_count", o -> o.dailyCheckpointInvalidLongitudeCount);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNegativeCountCheckSpec dailyCheckpointNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNegativePercentCheckSpec dailyCheckpointNegativePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNonNegativeCountCheckSpec dailyCheckpointNonNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNonNegativePercentCheckSpec dailyCheckpointNonNegativePercent;

    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNumbersInSetCountCheckSpec dailyCheckpointNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNumbersInSetPercentCheckSpec dailyCheckpointNumbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnValuesInRangeNumericPercentCheckSpec dailyCheckpointValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnValuesInRangeIntegersPercentCheckSpec dailyCheckpointValuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts those values with value below the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnValueBelowMinValueCountCheckSpec dailyCheckpointValueBelowMinValueCount;

    @JsonPropertyDescription("The check percentage of those values with value below the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnValueBelowMinValuePercentCheckSpec dailyCheckpointValueBelowMinValuePercent;

    @JsonPropertyDescription("The check counts those values with value above the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnValueAboveMaxValueCountCheckSpec dailyCheckpointValueAboveMaxValueCount;

    @JsonPropertyDescription("The check percentage of those values with value below the one provided by the user in a column. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnValueAboveMaxValuePercentCheckSpec dailyCheckpointValueAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxInRangeCheckSpec dailyCheckpointMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinInRangeCheckSpec dailyCheckpointMinInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMeanInRangeCheckSpec dailyCheckpointMeanInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPercentileInRangeCheckSpec dailyCheckpointPercentileInRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnSampleStddevInRangeCheckSpec dailyCheckpointSampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPopulationStddevInRangeCheckSpec dailyCheckpointPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnSampleVarianceInRangeCheckSpec dailyCheckpointSampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPopulationVarianceInRangeCheckSpec dailyCheckpointPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnSumInRangeCheckSpec dailyCheckpointSumInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnInvalidLatitudeCountCheckSpec dailyCheckpointInvalidLatitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnValidLatitudePercentCheckSpec dailyCheckpointValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnInvalidLongitudeCountCheckSpec dailyCheckpointInvalidLongitudeCount;

    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getDailyCheckpointNegativeCount() {
        return dailyCheckpointNegativeCount;
    }

    /**
     * Sets a new specification of a negative values count check.
     * @param dailyCheckpointNegativeCount Negative values count check specification.
     */
    public void setDailyCheckpointNegativeCount(ColumnNegativeCountCheckSpec dailyCheckpointNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNegativeCount, dailyCheckpointNegativeCount));
        this.dailyCheckpointNegativeCount = dailyCheckpointNegativeCount;
        propagateHierarchyIdToField(dailyCheckpointNegativeCount, "daily_checkpoint_negative_count");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getDailyCheckpointNegativePercent() {
        return dailyCheckpointNegativePercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param dailyCheckpointNegativePercent Negative values percentage check specification.
     */
    public void setDailyCheckpointNegativePercent(ColumnNegativePercentCheckSpec dailyCheckpointNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNegativePercent, dailyCheckpointNegativePercent));
        this.dailyCheckpointNegativePercent = dailyCheckpointNegativePercent;
        propagateHierarchyIdToField(dailyCheckpointNegativePercent, "daily_checkpoint_negative_percent");
    }

    /**
     * Returns a non-negative values count check specification.
     * @return Non-negative values count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getDailyCheckpointNonNegativeCount() {
        return dailyCheckpointNonNegativeCount;
    }

    /**
     * Sets a new specification of a non-negative values count check.
     * @param dailyCheckpointNonNegativeCount Non-negative values count check specification.
     */
    public void setDailyCheckpointNonNegativeCount(ColumnNonNegativeCountCheckSpec dailyCheckpointNonNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNonNegativeCount, dailyCheckpointNonNegativeCount));
        this.dailyCheckpointNonNegativeCount = dailyCheckpointNonNegativeCount;
        propagateHierarchyIdToField(dailyCheckpointNonNegativeCount, "daily_checkpoint_non_negative_count");
    }

    /**
     * Returns a non-negative values percentage check specification.
     * @return Non-negative values percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getDailyCheckpointNonNegativePercent() {
        return dailyCheckpointNonNegativePercent;
    }

    /**
     * Sets a new specification of a non-negative values percentage check.
     * @param dailyCheckpointNonNegativePercent Non-negative values percentage check specification.
     */
    public void setDailyCheckpointNonNegativePercent(ColumnNonNegativePercentCheckSpec dailyCheckpointNonNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNonNegativePercent, dailyCheckpointNonNegativePercent));
        this.dailyCheckpointNonNegativePercent = dailyCheckpointNonNegativePercent;
        propagateHierarchyIdToField(dailyCheckpointNonNegativePercent, "daily_checkpoint_non_negative_percent");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnNumbersInSetCountCheckSpec getDailyCheckpointNumbersInSetCount() {
        return dailyCheckpointNumbersInSetCount;
    }

    /**
     * Sets a new specification of a numbers in set count check.
     * @param dailyCheckpointNumbersInSetCount Numbers in set count check.
     */
    public void setDailyCheckpointNumbersInSetCount(ColumnNumbersInSetCountCheckSpec dailyCheckpointNumbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNumbersInSetCount, dailyCheckpointNumbersInSetCount));
        this.dailyCheckpointNumbersInSetCount = dailyCheckpointNumbersInSetCount;
        propagateHierarchyIdToField(dailyCheckpointNumbersInSetCount, "daily_checkpoint_numbers_in_set_count");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumbersInSetPercentCheckSpec getDailyCheckpointNumbersInSetPercent() {
        return dailyCheckpointNumbersInSetPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyCheckpointNumbersInSetPercent Numbers in set percent check specification.
     */
    public void setDailyCheckpointNumbersInSetPercent(ColumnNumbersInSetPercentCheckSpec dailyCheckpointNumbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNumbersInSetPercent, dailyCheckpointNumbersInSetPercent));
        this.dailyCheckpointNumbersInSetPercent = dailyCheckpointNumbersInSetPercent;
        propagateHierarchyIdToField(dailyCheckpointNumbersInSetPercent, "daily_checkpoint_numbers_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeNumericPercentCheckSpec getDailyCheckpointValuesInRangeNumericPercent() {
        return dailyCheckpointValuesInRangeNumericPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyCheckpointValuesInRangeNumericPercent Numbers in set percent check.
     */
    public void setDailyCheckpointValuesInRangeNumericPercent(ColumnValuesInRangeNumericPercentCheckSpec dailyCheckpointValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValuesInRangeNumericPercent, dailyCheckpointValuesInRangeNumericPercent));
        this.dailyCheckpointValuesInRangeNumericPercent = dailyCheckpointValuesInRangeNumericPercent;
        propagateHierarchyIdToField(dailyCheckpointValuesInRangeNumericPercent, "daily_checkpoint_values_in_range_numeric_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeIntegersPercentCheckSpec getDailyCheckpointValuesInRangeIntegersPercent() {
        return dailyCheckpointValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyCheckpointValuesInRangeIntegersPercent Numbers in set percent check specification.
     */
    public void setDailyCheckpointValuesInRangeIntegersPercent(ColumnValuesInRangeIntegersPercentCheckSpec dailyCheckpointValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValuesInRangeIntegersPercent, dailyCheckpointValuesInRangeIntegersPercent));
        this.dailyCheckpointValuesInRangeIntegersPercent = dailyCheckpointValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(dailyCheckpointValuesInRangeIntegersPercent, "daily_checkpoint_values_in_range_integers_percent");
    }

    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnValueBelowMinValueCountCheckSpec getDailyCheckpointValueBelowMinValueCount() {
        return dailyCheckpointValueBelowMinValueCount;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param dailyCheckpointValueBelowMinValueCount Numeric value below min value count check.
     */
    public void setDailyCheckpointValueBelowMinValueCount(ColumnValueBelowMinValueCountCheckSpec dailyCheckpointValueBelowMinValueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValueBelowMinValueCount, dailyCheckpointValueBelowMinValueCount));
        this.dailyCheckpointValueBelowMinValueCount = dailyCheckpointValueBelowMinValueCount;
        propagateHierarchyIdToField(dailyCheckpointValueBelowMinValueCount, "daily_checkpoint_value_below_min_value_count");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnValueBelowMinValuePercentCheckSpec getDailyCheckpointValueBelowMinValuePercent() {
        return dailyCheckpointValueBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param dailyCheckpointValueBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setDailyCheckpointValueBelowMinValuePercent(ColumnValueBelowMinValuePercentCheckSpec dailyCheckpointValueBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValueBelowMinValuePercent, dailyCheckpointValueBelowMinValuePercent));
        this.dailyCheckpointValueBelowMinValuePercent = dailyCheckpointValueBelowMinValuePercent;
        propagateHierarchyIdToField(dailyCheckpointValueBelowMinValuePercent, "daily_checkpoint_value_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnValueAboveMaxValueCountCheckSpec getDailyCheckpointValueAboveMaxValueCount() {
        return dailyCheckpointValueAboveMaxValueCount;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param dailyCheckpointValueAboveMaxValueCount Numeric value above max value count check.
     */
    public void setDailyCheckpointValueAboveMaxValueCount(ColumnValueAboveMaxValueCountCheckSpec dailyCheckpointValueAboveMaxValueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValueAboveMaxValueCount, dailyCheckpointValueAboveMaxValueCount));
        this.dailyCheckpointValueAboveMaxValueCount = dailyCheckpointValueAboveMaxValueCount;
        propagateHierarchyIdToField(dailyCheckpointValueAboveMaxValueCount, "daily_checkpoint_value_above_max_value_count");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnValueAboveMaxValuePercentCheckSpec getDailyCheckpointValueAboveMaxValuePercent() {
        return dailyCheckpointValueAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param dailyCheckpointValueAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setDailyCheckpointValueAboveMaxValuePercent(ColumnValueAboveMaxValuePercentCheckSpec dailyCheckpointValueAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValueAboveMaxValuePercent, dailyCheckpointValueAboveMaxValuePercent));
        this.dailyCheckpointValueAboveMaxValuePercent = dailyCheckpointValueAboveMaxValuePercent;
        propagateHierarchyIdToField(dailyCheckpointValueAboveMaxValuePercent, "daily_checkpoint_value_above_max_value_percent");
    }

    /**
     * Returns a max in range check specification.
     * @return Max in range check specification.
     */
    public ColumnMaxInRangeCheckSpec getDailyCheckpointMaxInRange() {
        return dailyCheckpointMaxInRange;
    }

    /**
     * Sets a new specification of a max in range check.
     * @param dailyCheckpointMaxInRange Max in range check specification.
     */
    public void setDailyCheckpointMaxInRange(ColumnMaxInRangeCheckSpec dailyCheckpointMaxInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxInRange, dailyCheckpointMaxInRange));
        this.dailyCheckpointMaxInRange = dailyCheckpointMaxInRange;
        propagateHierarchyIdToField(dailyCheckpointMaxInRange, "daily_checkpoint_max_in_range");
    }

    /**
     * Returns a min in range check specification.
     * @return Min in range check specification.
     */
    public ColumnMinInRangeCheckSpec getDailyCheckpointMinInRange() {
        return dailyCheckpointMinInRange;
    }

    /**
     * Sets a new specification of a min in range check.
     * @param dailyCheckpointMinInRange Min in range check specification.
     */
    public void setDailyCheckpointMinInRange(ColumnMinInRangeCheckSpec dailyCheckpointMinInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinInRange, dailyCheckpointMinInRange));
        this.dailyCheckpointMinInRange = dailyCheckpointMinInRange;
        propagateHierarchyIdToField(dailyCheckpointMinInRange, "daily_checkpoint_min_in_range");
    }

    /**
     * Returns a mean in range check specification.
     * @return Mean in range check specification.
     */
    public ColumnMeanInRangeCheckSpec getDailyCheckpointMeanInRange() {
        return dailyCheckpointMeanInRange;
    }

    /**
     * Sets a new specification of a mean in range check.
     * @param dailyCheckpointMeanInRange Mean in range check specification.
     */
    public void setDailyCheckpointMeanInRange(ColumnMeanInRangeCheckSpec dailyCheckpointMeanInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMeanInRange, dailyCheckpointMeanInRange));
        this.dailyCheckpointMeanInRange = dailyCheckpointMeanInRange;
        propagateHierarchyIdToField(dailyCheckpointMeanInRange, "daily_checkpoint_mean_in_range");
    }

    /**
     * Returns a percentile in range check specification.
     * @return Percentile in range check specification.
     */
    public ColumnPercentileInRangeCheckSpec getDailyCheckpointPercentileInRange() {
        return dailyCheckpointPercentileInRange;
    }

    /**
     * Sets a new specification of a percentile in range check.
     * @param dailyCheckpointPercentileInRange Percentile in range check specification.
     */
    public void setDailyCheckpointPercentileInRange(ColumnPercentileInRangeCheckSpec dailyCheckpointPercentileInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointPercentileInRange, dailyCheckpointPercentileInRange));
        this.dailyCheckpointPercentileInRange = dailyCheckpointPercentileInRange;
        propagateHierarchyIdToField(dailyCheckpointPercentileInRange, "daily_checkpoint_percentile_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleStddevInRangeCheckSpec getDailyCheckpointSampleStddevInRange() {
        return dailyCheckpointSampleStddevInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param dailyCheckpointSampleStddevInRange Sample standard deviation in range check specification.
     */
    public void setDailyCheckpointSampleStddevInRange(ColumnSampleStddevInRangeCheckSpec dailyCheckpointSampleStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSampleStddevInRange, dailyCheckpointSampleStddevInRange));
        this.dailyCheckpointSampleStddevInRange = dailyCheckpointSampleStddevInRange;
        propagateHierarchyIdToField(dailyCheckpointSampleStddevInRange, "daily_checkpoint_sample_stddev_in_range");
    }

    /**
     * Returns a population standard deviation in range check specification.
     * @return Population standard deviation in range check specification.
     */
    public ColumnPopulationStddevInRangeCheckSpec getDailyCheckpointPopulationStddevInRange() {
        return dailyCheckpointPopulationStddevInRange;
    }

    /**
     * Sets a new specification of a population standard deviation in range check.
     * @param dailyCheckpointPopulationStddevInRange Population standard deviation in range check specification.
     */
    public void setDailyCheckpointPopulationStddevInRange(ColumnPopulationStddevInRangeCheckSpec dailyCheckpointPopulationStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointPopulationStddevInRange, dailyCheckpointPopulationStddevInRange));
        this.dailyCheckpointPopulationStddevInRange = dailyCheckpointPopulationStddevInRange;
        propagateHierarchyIdToField(dailyCheckpointPopulationStddevInRange, "daily_checkpoint_population_stddev_in_range");
    }

    /**
     * Returns a sample variance in range check specification.
     * @return Sample variance in range check specification.
     */
    public ColumnSampleVarianceInRangeCheckSpec getDailyCheckpointSampleVarianceInRange() {
        return dailyCheckpointSampleVarianceInRange;
    }

    /**
     * Sets a new specification of a sample variance in range check.
     * @param dailyCheckpointSampleVarianceInRange Sample variance in range check specification.
     */
    public void setDailyCheckpointSampleVarianceInRange(ColumnSampleVarianceInRangeCheckSpec dailyCheckpointSampleVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSampleVarianceInRange, dailyCheckpointSampleVarianceInRange));
        this.dailyCheckpointSampleVarianceInRange = dailyCheckpointSampleVarianceInRange;
        propagateHierarchyIdToField(dailyCheckpointSampleVarianceInRange, "daily_checkpoint_sample_variance_in_range");
    }

    /**
     * Returns a population variance in range check specification.
     * @return Population variance in range check specification.
     */
    public ColumnPopulationVarianceInRangeCheckSpec getDailyCheckpointPopulationVarianceInRange() {
        return dailyCheckpointPopulationVarianceInRange;
    }

    /**
     * Sets a new specification of a population variance in range check.
     * @param dailyCheckpointPopulationVarianceInRange Population variance in range check specification.
     */
    public void setDailyCheckpointPopulationVarianceInRange(ColumnPopulationVarianceInRangeCheckSpec dailyCheckpointPopulationVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointPopulationVarianceInRange, dailyCheckpointPopulationVarianceInRange));
        this.dailyCheckpointPopulationVarianceInRange = dailyCheckpointPopulationVarianceInRange;
        propagateHierarchyIdToField(dailyCheckpointPopulationVarianceInRange, "daily_checkpoint_population_variance_in_range");
    }

    /**
     * Returns a sum in range check specification.
     * @return Sum in range check specification.
     */
    public ColumnSumInRangeCheckSpec getDailyCheckpointSumInRange() {
        return dailyCheckpointSumInRange;
    }

    /**
     * Sets a new specification of a sum in range check.
     * @param dailyCheckpointSumInRange Sum in range check specification.
     */
    public void setDailyCheckpointSumInRange(ColumnSumInRangeCheckSpec dailyCheckpointSumInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSumInRange, dailyCheckpointSumInRange));
        this.dailyCheckpointSumInRange = dailyCheckpointSumInRange;
        propagateHierarchyIdToField(dailyCheckpointSumInRange, "daily_checkpoint_sum_in_range");
    }

    /**
     * Returns an invalid latitude count check specification.
     * @return Invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getDailyCheckpointInvalidLatitudeCount() {
        return dailyCheckpointInvalidLatitudeCount;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param dailyCheckpointInvalidLatitudeCount Invalid latitude count check specification.
     */
    public void setDailyCheckpointInvalidLatitudeCount(ColumnInvalidLatitudeCountCheckSpec dailyCheckpointInvalidLatitudeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointInvalidLatitudeCount, dailyCheckpointInvalidLatitudeCount));
        this.dailyCheckpointInvalidLatitudeCount = dailyCheckpointInvalidLatitudeCount;
        propagateHierarchyIdToField(dailyCheckpointInvalidLatitudeCount, "daily_checkpoint_invalid_latitude_count");
    }

    /**
     * Returns a valid latitude percent check specification.
     * @return Valid latitude percent check specification.
     */
    public ColumnValidLatitudePercentCheckSpec getDailyCheckpointValidLatitudePercent() {
        return dailyCheckpointValidLatitudePercent;
    }

    /**
     * Sets a new specification of a valid latitude percent check.
     * @param dailyCheckpointValidLatitudePercent Valid latitude percent check specification.
     */
    public void setDailyCheckpointValidLatitudePercent(ColumnValidLatitudePercentCheckSpec dailyCheckpointValidLatitudePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValidLatitudePercent, dailyCheckpointValidLatitudePercent));
        this.dailyCheckpointValidLatitudePercent = dailyCheckpointValidLatitudePercent;
        propagateHierarchyIdToField(dailyCheckpointValidLatitudePercent, "daily_checkpoint_valid_latitude_percent");
    }

    /**
     * Returns an invalid longitude count check specification.
     * @return Invalid longitude count check specification.
     */
    public ColumnInvalidLongitudeCountCheckSpec getDailyCheckpointInvalidLongitudeCount() {
        return dailyCheckpointInvalidLongitudeCount;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param dailyCheckpointInvalidLongitudeCount Invalid longitude count check specification.
     */
    public void setDailyCheckpointInvalidLongitudeCount(ColumnInvalidLongitudeCountCheckSpec dailyCheckpointInvalidLongitudeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointInvalidLongitudeCount, dailyCheckpointInvalidLongitudeCount));
        this.dailyCheckpointInvalidLongitudeCount = dailyCheckpointInvalidLongitudeCount;
        propagateHierarchyIdToField(dailyCheckpointInvalidLongitudeCount, "daily_checkpoint_invalid_longitude_count");
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