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
 * Container of built-in preconfigured data quality checkpoints on a column level that are checking numeric values at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericMonthlyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericMonthlyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_negative_count", o -> o.monthlyCheckpointNegativeCount);
            put("monthly_checkpoint_negative_percent", o -> o.monthlyCheckpointNegativePercent);
            put("monthly_checkpoint_numbers_in_set_count", o -> o.monthlyCheckpointNumbersInSetCount);
            put("monthly_checkpoint_numbers_in_set_percent", o -> o.monthlyCheckpointNumbersInSetPercent);
            put("monthly_checkpoint_values_in_range_numeric_percent", o -> o.monthlyCheckpointValuesInRangeNumericPercent);
            put("monthly_checkpoint_values_in_range_integers_percent", o -> o.monthlyCheckpointValuesInRangeIntegersPercent);
            put("monthly_checkpoint_value_below_min_value_count", o -> o.monthlyCheckpointValueBelowMinValueCount);
            put("monthly_checkpoint_max_in_range", o -> o.monthlyCheckpointMaxInRange);
            put("monthly_checkpoint_min_in_range", o -> o.monthlyCheckpointMinInRange);
            put("monthly_checkpoint_mean_in_range", o -> o.monthlyCheckpointMeanInRange);
            put("monthly_checkpoint_sample_stddev_in_range", o -> o.monthlyCheckpointSampleStddevInRange);
            put("monthly_checkpoint_population_stddev_in_range", o -> o.monthlyCheckpointPopulationStddevInRange);
            put("monthly_checkpoint_sample_variance_in_range", o -> o.monthlyCheckpointSampleVarianceInRange);
            put("monthly_checkpoint_population_variance_in_range", o -> o.monthlyCheckpointPopulationVarianceInRange);
            put("monthly_checkpoint_sum_in_range", o -> o.monthlyCheckpointSumInRange);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNegativeCountCheckSpec monthlyCheckpointNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNegativePercentCheckSpec monthlyCheckpointNegativePercent;

    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNumbersInSetCountCheckSpec monthlyCheckpointNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNumbersInSetPercentCheckSpec monthlyCheckpointNumbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValuesInRangeNumericPercentCheckSpec monthlyCheckpointValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValuesInRangeIntegersPercentCheckSpec monthlyCheckpointValuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts those values with value below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnValueBelowMinValueCountCheckSpec monthlyCheckpointValueBelowMinValueCount;

    @JsonPropertyDescription("Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxInRangeCheckSpec monthlyCheckpointMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMinInRangeCheckSpec monthlyCheckpointMinInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMeanInRangeCheckSpec monthlyCheckpointMeanInRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSampleStddevInRangeCheckSpec monthlyCheckpointSampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPopulationStddevInRangeCheckSpec monthlyCheckpointPopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSampleVarianceInRangeCheckSpec monthlyCheckpointSampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPopulationVarianceInRangeCheckSpec monthlyCheckpointPopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnSumInRangeCheckSpec monthlyCheckpointSumInRange;


    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getMonthlyCheckpointNegativeCount() {
        return monthlyCheckpointNegativeCount;
    }

    /**
     * Sets a new specification of a negative values count check.
     * @param monthlyCheckpointNegativeCount Negative values count check specification.
     */
    public void setMonthlyCheckpointNegativeCount(ColumnNegativeCountCheckSpec monthlyCheckpointNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointNegativeCount, monthlyCheckpointNegativeCount));
        this.monthlyCheckpointNegativeCount = monthlyCheckpointNegativeCount;
        propagateHierarchyIdToField(monthlyCheckpointNegativeCount, "monthly_checkpoint_negative_count");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getMonthlyCheckpointNegativePercent() {
        return monthlyCheckpointNegativePercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param monthlyCheckpointNegativePercent Negative values percentage check specification.
     */
    public void setMonthlyCheckpointNegativePercent(ColumnNegativePercentCheckSpec monthlyCheckpointNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointNegativePercent, monthlyCheckpointNegativePercent));
        this.monthlyCheckpointNegativePercent = monthlyCheckpointNegativePercent;
        propagateHierarchyIdToField(monthlyCheckpointNegativePercent, "monthly_checkpoint_negative_percent");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Minimum Numbers in set count check specification.
     */
    public ColumnNumbersInSetCountCheckSpec getMonthlyCheckpointNumbersInSetCount() {
        return monthlyCheckpointNumbersInSetCount;
    }

    /**
     * Sets a new specification of a numbers in set count check.
     * @param monthlyCheckpointNumbersInSetCount Numbers in set count check specification.
     */
    public void setMonthlyCheckpointNumbersInSetCount(ColumnNumbersInSetCountCheckSpec monthlyCheckpointNumbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointNumbersInSetCount, monthlyCheckpointNumbersInSetCount));
        this.monthlyCheckpointNumbersInSetCount = monthlyCheckpointNumbersInSetCount;
        propagateHierarchyIdToField(monthlyCheckpointNumbersInSetCount, "monthly_checkpoint_numbers_in_set_count");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumbersInSetPercentCheckSpec getMonthlyCheckpointNumbersInSetPercent() {
        return monthlyCheckpointNumbersInSetPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param monthlyCheckpointNumbersInSetPercent Minimum Numbers in set percent check.
     */
    public void setMonthlyCheckpointNumbersInSetPercent(ColumnNumbersInSetPercentCheckSpec monthlyCheckpointNumbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointNumbersInSetPercent, monthlyCheckpointNumbersInSetPercent));
        this.monthlyCheckpointNumbersInSetPercent = monthlyCheckpointNumbersInSetPercent;
        propagateHierarchyIdToField(monthlyCheckpointNumbersInSetPercent, "monthly_checkpoint_min_numbers_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return numbers in set percent check specification.
     */
    public ColumnValuesInRangeNumericPercentCheckSpec getMonthlyCheckpointValuesInRangeNumericPercent() {
        return monthlyCheckpointValuesInRangeNumericPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param monthlyCheckpointValuesInRangeNumericPercent Numbers in set percent check specification.
     */
    public void setMonthlyCheckpointValuesInRangeNumericPercent(ColumnValuesInRangeNumericPercentCheckSpec monthlyCheckpointValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointValuesInRangeNumericPercent, monthlyCheckpointValuesInRangeNumericPercent));
        this.monthlyCheckpointValuesInRangeNumericPercent = monthlyCheckpointValuesInRangeNumericPercent;
        propagateHierarchyIdToField(monthlyCheckpointValuesInRangeNumericPercent, "monthly_checkpoint_values_in_range_numeric_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeIntegersPercentCheckSpec getMonthlyCheckpointValuesInRangeIntegersPercent() {
        return monthlyCheckpointValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new definition of a numbers in set percent check.
     * @param monthlyCheckpointValuesInRangeIntegersPercent Numbers in set percent check specification.
     */
    public void setMonthlyCheckpointValuesInRangeIntegersPercent(ColumnValuesInRangeIntegersPercentCheckSpec monthlyCheckpointValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointValuesInRangeIntegersPercent, monthlyCheckpointValuesInRangeIntegersPercent));
        this.monthlyCheckpointValuesInRangeIntegersPercent = monthlyCheckpointValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(monthlyCheckpointValuesInRangeIntegersPercent, "monthly_checkpoint_values_in_range_integers_percent");
    }

    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnValueBelowMinValueCountCheckSpec getMonthlyCheckpointValueBelowMinValueCount() {
        return monthlyCheckpointValueBelowMinValueCount;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param monthlyCheckpointValueBelowMinValueCount Numeric value below min value count check.
     */
    public void setMonthlyCheckpointValueBelowMinValueCount(ColumnValueBelowMinValueCountCheckSpec monthlyCheckpointValueBelowMinValueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointValueBelowMinValueCount, monthlyCheckpointValueBelowMinValueCount));
        this.monthlyCheckpointValueBelowMinValueCount = monthlyCheckpointValueBelowMinValueCount;
        propagateHierarchyIdToField(monthlyCheckpointValueBelowMinValueCount, "monthly_checkpoint_value_below_min_value_count");
    }

    /**
     * Returns a max in range check specification.
     * @return Max in range check specification.
     */
    public ColumnMaxInRangeCheckSpec getMonthlyCheckpointMaxInRange() {
        return monthlyCheckpointMaxInRange;
    }

    /**
     * Sets a new specification of a max in range check.
     * @param monthlyCheckpointMaxInRange Max in range check specification.
     */
    public void setMonthlyCheckpointMaxInRange(ColumnMaxInRangeCheckSpec monthlyCheckpointMaxInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxInRange, monthlyCheckpointMaxInRange));
        this.monthlyCheckpointMaxInRange = monthlyCheckpointMaxInRange;
        propagateHierarchyIdToField(monthlyCheckpointMaxInRange, "monthly_checkpoint_max_in_range");
    }

    /**
     * Returns a min in range check specification.
     * @return Min in range check specification.
     */
    public ColumnMinInRangeCheckSpec getMonthlyCheckpointMinInRange() {
        return monthlyCheckpointMinInRange;
    }

    /**
     * Sets a new specification of a min in range check.
     * @param monthlyCheckpointMinInRange Min in range check specification.
     */
    public void setMonthlyCheckpointMinInRange(ColumnMinInRangeCheckSpec monthlyCheckpointMinInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinInRange, monthlyCheckpointMinInRange));
        this.monthlyCheckpointMinInRange = monthlyCheckpointMinInRange;
        propagateHierarchyIdToField(monthlyCheckpointMinInRange, "monthly_checkpoint_min_in_range");
    }

    /**
     * Returns a mean in range check specification.
     * @return Mean in range check specification.
     */
    public ColumnMeanInRangeCheckSpec getMonthlyCheckpointMeanInRange() {
        return monthlyCheckpointMeanInRange;
    }

    /**
     * Sets a new specification of a mean in range check.
     * @param monthlyCheckpointMeanInRange Mean in range check specification.
     */
    public void setMonthlyCheckpointMeanInRange(ColumnMeanInRangeCheckSpec monthlyCheckpointMeanInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMeanInRange, monthlyCheckpointMeanInRange));
        this.monthlyCheckpointMeanInRange = monthlyCheckpointMeanInRange;
        propagateHierarchyIdToField(monthlyCheckpointMeanInRange, "monthly_checkpoint_mean_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleStddevInRangeCheckSpec getMonthlyCheckpointSampleStddevInRange() {
        return monthlyCheckpointSampleStddevInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param monthlyCheckpointSampleStddevInRange Sample standard deviation in range check specification.
     */
    public void setMonthlyCheckpointSampleStddevInRange(ColumnSampleStddevInRangeCheckSpec monthlyCheckpointSampleStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSampleStddevInRange, monthlyCheckpointSampleStddevInRange));
        this.monthlyCheckpointSampleStddevInRange = monthlyCheckpointSampleStddevInRange;
        propagateHierarchyIdToField(monthlyCheckpointSampleStddevInRange, "monthly_checkpoint_sample_stddev_in_range");
    }

    /**
     * Returns a population standard deviation in range check specification.
     * @return Population standard deviation in range check specification.
     */
    public ColumnPopulationStddevInRangeCheckSpec getMonthlyCheckpointPopulationStddevInRange() {
        return monthlyCheckpointPopulationStddevInRange;
    }

    /**
     * Sets a new specification of a population standard deviation in range check.
     * @param monthlyCheckpointPopulationStddevInRange Population standard deviation in range check specification.
     */
    public void setMonthlyCheckpointPopulationStddevInRange(ColumnPopulationStddevInRangeCheckSpec monthlyCheckpointPopulationStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointPopulationStddevInRange, monthlyCheckpointPopulationStddevInRange));
        this.monthlyCheckpointPopulationStddevInRange = monthlyCheckpointPopulationStddevInRange;
        propagateHierarchyIdToField(monthlyCheckpointPopulationStddevInRange, "monthly_checkpoint_population_stddev_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleVarianceInRangeCheckSpec getMonthlyCheckpointSampleVarianceInRange() {
        return monthlyCheckpointSampleVarianceInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param monthlyCheckpointSampleVarianceInRange Sample standard deviation in range check specification.
     */
    public void setMonthlyCheckpointSampleVarianceInRange(ColumnSampleVarianceInRangeCheckSpec monthlyCheckpointSampleVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSampleVarianceInRange, monthlyCheckpointSampleVarianceInRange));
        this.monthlyCheckpointSampleVarianceInRange = monthlyCheckpointSampleVarianceInRange;
        propagateHierarchyIdToField(monthlyCheckpointSampleVarianceInRange, "monthly_checkpoint_sample_variance_in_range");
    }

    /**
     * Returns a population variance in range check specification.
     * @return Population variance in range check specification.
     */
    public ColumnPopulationVarianceInRangeCheckSpec getMonthlyCheckpointPopulationVarianceInRange() {
        return monthlyCheckpointPopulationVarianceInRange;
    }

    /**
     * Sets a new specification of a population variance in range check.
     * @param monthlyCheckpointPopulationVarianceInRange Population variance in range check specification.
     */
    public void setMonthlyCheckpointPopulationVarianceInRange(ColumnPopulationVarianceInRangeCheckSpec monthlyCheckpointPopulationVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointPopulationVarianceInRange, monthlyCheckpointPopulationVarianceInRange));
        this.monthlyCheckpointPopulationVarianceInRange = monthlyCheckpointPopulationVarianceInRange;
        propagateHierarchyIdToField(monthlyCheckpointPopulationVarianceInRange, "monthly_checkpoint_population_variance_in_range");
    }

    /**
     * Returns a sum in range check specification.
     * @return Sum in range check specification.
     */
    public ColumnSumInRangeCheckSpec getMonthlyCheckpointSumInRange() {
        return monthlyCheckpointSumInRange;
    }

    /**
     * Sets a new specification of a sum in range check.
     * @param monthlyCheckpointSumInRange Sum in range check specification.
     */
    public void setMonthlyCheckpointSumInRange(ColumnSumInRangeCheckSpec monthlyCheckpointSumInRange) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointSumInRange, monthlyCheckpointSumInRange));
        this.monthlyCheckpointSumInRange = monthlyCheckpointSumInRange;
        propagateHierarchyIdToField(monthlyCheckpointSumInRange, "monthly_checkpoint_sum_in_range");
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