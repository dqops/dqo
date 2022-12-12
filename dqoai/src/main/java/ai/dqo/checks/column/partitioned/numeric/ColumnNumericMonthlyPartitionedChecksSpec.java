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
import ai.dqo.checks.column.numeric.ColumnMaxNegativeCountCheckSpec;
import ai.dqo.checks.column.numeric.ColumnMaxNegativePercentCheckSpec;
import ai.dqo.checks.column.numeric.ColumnMinNumbersInSetCountCheckSpec;
import ai.dqo.checks.column.numeric.ColumnMinNumbersInSetPercentCheckSpec;
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
            put("monthly_partition_max_negative_count", o -> o.monthlyPartitionMaxNegativeCount);
            put("monthly_partition_max_negative_percent", o -> o.monthlyPartitionMaxNegativePercent);
            put("monthly_partition_min_numbers_in_set_count", o -> o.monthlyPartitionMinNumbersInSetCount);
            put("monthly_partition_min_numbers_in_set_percent", o -> o.monthlyPartitionMinNumbersInSetPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxNegativeCountCheckSpec monthlyPartitionMaxNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxNegativePercentCheckSpec monthlyPartitionMaxNegativePercent;
    
    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count.")
    private ColumnMinNumbersInSetCountCheckSpec monthlyPartitionMinNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage.")
    private ColumnMinNumbersInSetPercentCheckSpec monthlyPartitionMinNumbersInSetPercent;

    /**
     * Returns a maximum negative values count check.
     * @return Maximum negative values count check.
     */
    public ColumnMaxNegativeCountCheckSpec getMonthlyPartitionMaxNegativeCount() {
        return monthlyPartitionMaxNegativeCount;
    }

    /**
     * Sets a new definition of a maximum negative values count check.
     * @param monthlyPartitionMaxNegativeCount Maximum negative values count check.
     */
    public void setMonthlyPartitionMaxNegativeCount(ColumnMaxNegativeCountCheckSpec monthlyPartitionMaxNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxNegativeCount, monthlyPartitionMaxNegativeCount));
        this.monthlyPartitionMaxNegativeCount = monthlyPartitionMaxNegativeCount;
        propagateHierarchyIdToField(monthlyPartitionMaxNegativeCount, "monthly_partition_max_negative_count");
    }

    /**
     * Returns a maximum negative values percentage check.
     * @return Maximum negative values percentage check.
     */
    public ColumnMaxNegativePercentCheckSpec getMonthlyPartitionMaxNegativePercent() {
        return monthlyPartitionMaxNegativePercent;
    }

    /**
     * Sets a new definition of a maximum negative values percentage check.
     * @param monthlyPartitionMaxNegativePercent Maximum negative values percentage check.
     */
    public void setMonthlyPartitionMaxNegativePercent(ColumnMaxNegativePercentCheckSpec monthlyPartitionMaxNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxNegativePercent, monthlyPartitionMaxNegativePercent));
        this.monthlyPartitionMaxNegativePercent = monthlyPartitionMaxNegativePercent;
        propagateHierarchyIdToField(monthlyPartitionMaxNegativePercent, "monthly_partition_max_negative_percent");
    }

    /**
     * Returns a minimum Numbers in set count check.
     * @return Minimum Numbers in set count check.
     */
    public ColumnMinNumbersInSetCountCheckSpec getMonthlyPartitionMinNumbersInSetCount() {
        return monthlyPartitionMinNumbersInSetCount;
    }

    /**
     * Sets a new definition of a minimum Numbers in set count check.
     * @param monthlyPartitionMinNumbersInSetCount Minimum Numbers in set count check.
     */
    public void setMonthlyPartitionMinNumbersInSetCount(ColumnMinNumbersInSetCountCheckSpec monthlyPartitionMinNumbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinNumbersInSetCount, monthlyPartitionMinNumbersInSetCount));
        this.monthlyPartitionMinNumbersInSetCount = monthlyPartitionMinNumbersInSetCount;
        propagateHierarchyIdToField(monthlyPartitionMinNumbersInSetCount, "monthly_partition_min_numbers_in_set_count");
    }

    /**
     * Returns a minimum Numbers in set percent check.
     * @return Minimum Numbers in set percent check.
     */
    public ColumnMinNumbersInSetPercentCheckSpec getMonthlyPartitionMinNumbersInSetPercent() {
        return monthlyPartitionMinNumbersInSetPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param monthlyPartitionMinNumbersInSetPercent Minimum Numbers in set percent check.
     */
    public void setMonthlyPartitionMinNumbersInSetPercent(ColumnMinNumbersInSetPercentCheckSpec monthlyPartitionMinNumbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinNumbersInSetPercent, monthlyPartitionMinNumbersInSetPercent));
        this.monthlyPartitionMinNumbersInSetPercent = monthlyPartitionMinNumbersInSetPercent;
        propagateHierarchyIdToField(monthlyPartitionMinNumbersInSetPercent, "monthly_partition_min_numbers_in_set_percent");
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