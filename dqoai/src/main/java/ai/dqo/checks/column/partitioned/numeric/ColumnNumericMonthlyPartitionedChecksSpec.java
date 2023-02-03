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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_negative_count", o -> o.monthlyPartitionNegativeCount);
            put("monthly_partition_negative_percent", o -> o.monthlyPartitionNegativePercent);
            put("monthly_partition_numbers_in_set_count", o -> o.monthlyPartitionNumbersInSetCount);
            put("monthly_partition_numbers_in_set_percent", o -> o.monthlyPartitionNumbersInSetPercent);
            put("monthly_partition_values_in_range_numeric_percent", o -> o.monthlyPartitionValuesInRangeNumericPercent);
            put("monthly_partition_values_in_range_integers_percent", o -> o.monthlyPartitionValuesInRangeIntegersPercent);
            put("monthly_partition_value_below_min_value_count", o -> o.monthlyPartitionValueBelowMinValueCount);
            put("monthly_partition_value_below_min_value_percent", o -> o.monthlyPartitionValueBelowMinValuePercent);
            put("monthly_partition_value_above_max_value_count", o -> o.monthlyPartitionValueAboveMaxValueCount);
            put("monthly_partition_value_above_max_value_percent", o -> o.monthlyPartitionValueAboveMaxValuePercent);
            put("monthly_partition_max_in_range", o -> o.monthlyPartitionMaxInRange);
            put("monthly_partition_min_in_range", o -> o.monthlyPartitionMinInRange);
            put("monthly_partition_mean_in_range", o -> o.monthlyPartitionMeanInRange);
            put("monthly_partition_sample_stddev_in_range", o -> o.monthlyPartitionSampleStddevInRange);
            put("monthly_partition_population_stddev_in_range", o -> o.monthlyPartitionPopulationStddevInRange);
            put("monthly_partition_sample_variance_in_range", o -> o.monthlyPartitionSampleVarianceInRange);
            put("monthly_partition_population_variance_in_range", o -> o.monthlyPartitionPopulationVarianceInRange);
            put("monthly_partition_sum_in_range", o -> o.monthlyPartitionSumInRange);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNegativeCountCheckSpec monthlyPartitionNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNegativePercentCheckSpec monthlyPartitionNegativePercent;
    
    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNumbersInSetCountCheckSpec monthlyPartitionNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNumbersInSetPercentCheckSpec monthlyPartitionNumbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValuesInRangeNumericPercentCheckSpec monthlyPartitionValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValuesInRangeIntegersPercentCheckSpec monthlyPartitionValuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts those values with value below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValueBelowMinValueCountCheckSpec monthlyPartitionValueBelowMinValueCount;

    @JsonPropertyDescription("The check percentage of those values with value below the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValueBelowMinValuePercentCheckSpec monthlyPartitionValueBelowMinValuePercent;

    @JsonPropertyDescription("The check counts those values with value above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValueAboveMaxValueCountCheckSpec monthlyPartitionValueAboveMaxValueCount;

    @JsonPropertyDescription("The check percentage of those values with value above the one provided by the user in a column. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnValueAboveMaxValuePercentCheckSpec monthlyPartitionValueAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxInRangeCheckSpec monthlyPartitionMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinInRangeCheckSpec monthlyPartitionMinInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMeanInRangeCheckSpec monthlyPartitionMeanInRange;

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

    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getMonthlyPartitionNegativeCount() {
        return monthlyPartitionNegativeCount;
    }

    /**
     * Sets a new specification of a megative values count check.
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
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnNumbersInSetCountCheckSpec getMonthlyPartitionNumbersInSetCount() {
        return monthlyPartitionNumbersInSetCount;
    }

    /**
     * Sets a new specification of a numbers in set count check.
     * @param monthlyPartitionNumbersInSetCount Numbers in set count check specification.
     */
    public void setMonthlyPartitionNumbersInSetCount(ColumnNumbersInSetCountCheckSpec monthlyPartitionNumbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumbersInSetCount, monthlyPartitionNumbersInSetCount));
        this.monthlyPartitionNumbersInSetCount = monthlyPartitionNumbersInSetCount;
        propagateHierarchyIdToField(monthlyPartitionNumbersInSetCount, "monthly_partition_numbers_in_set_count");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumbersInSetPercentCheckSpec getMonthlyPartitionNumbersInSetPercent() {
        return monthlyPartitionNumbersInSetPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check .
     * @param monthlyPartitionNumbersInSetPercent Minimum Numbers in set percent check.
     */
    public void setMonthlyPartitionNumbersInSetPercent(ColumnNumbersInSetPercentCheckSpec monthlyPartitionNumbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumbersInSetPercent, monthlyPartitionNumbersInSetPercent));
        this.monthlyPartitionNumbersInSetPercent = monthlyPartitionNumbersInSetPercent;
        propagateHierarchyIdToField(monthlyPartitionNumbersInSetPercent, "monthly_partition_numbers_in_set_percent");
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
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }
}