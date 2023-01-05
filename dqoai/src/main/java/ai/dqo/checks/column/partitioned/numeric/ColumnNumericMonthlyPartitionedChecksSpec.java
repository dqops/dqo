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
import ai.dqo.checks.column.numeric.*;
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
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNegativeCountCheckSpec monthlyPartitionNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNegativePercentCheckSpec monthlyPartitionNegativePercent;
    
    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the set count.")
    private ColumnNumbersInSetCountCheckSpec monthlyPartitionNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the set percentage.")
    private ColumnNumbersInSetPercentCheckSpec monthlyPartitionNumbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the set percentage.")
    private ColumnValuesInRangeNumericPercentCheckSpec monthlyPartitionValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the set percentage.")
    private ColumnValuesInRangeIntegersPercentCheckSpec monthlyPartitionValuesInRangeIntegersPercent;

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
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }
}