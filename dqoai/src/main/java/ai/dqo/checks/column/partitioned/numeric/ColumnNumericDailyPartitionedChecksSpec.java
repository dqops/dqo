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
            put("daily_partition_numbers_in_set_count", o -> o.dailyPartitionNumbersInSetCount);
            put("daily_partition_numbers_in_set_percent", o -> o.dailyPartitionNumbersInSetPercent);
            put("daily_partition_values_in_range_numeric_percent", o -> o.dailyPartitionValuesInRangeNumericPercent);
            put("daily_partition_values_in_range_integers_percent", o -> o.dailyPartitionValuesInRangeIntegersPercent);
            put("daily_partition_max_in_range", o -> o.dailyPartitionMaxInRange);
            put("daily_partition_min_in_range", o -> o.dailyPartitionMinInRange);
            put("daily_partition_sum_in_range", o -> o.dailyPartitionSumInRange);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNegativeCountCheckSpec dailyPartitionNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNegativePercentCheckSpec dailyPartitionNegativePercent;

    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNumbersInSetCountCheckSpec dailyPartitionNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNumbersInSetPercentCheckSpec dailyPartitionNumbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValuesInRangeNumericPercentCheckSpec dailyPartitionValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnValuesInRangeIntegersPercentCheckSpec dailyPartitionValuesInRangeIntegersPercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column does not exceed the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxInRangeCheckSpec dailyPartitionMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column does not exceed the set range. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinInRangeCheckSpec dailyPartitionMinInRange;

    @JsonPropertyDescription("Verifies that the sum of a column does not exceed the set range. Creates a separate data quality check (and an alert) for each daily partition.")
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