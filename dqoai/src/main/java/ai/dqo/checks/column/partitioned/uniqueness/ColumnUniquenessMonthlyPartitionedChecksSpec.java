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
package ai.dqo.checks.column.partitioned.uniqueness;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDistinctCountCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDistinctPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDuplicateCountCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDuplicatePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of uniqueness data quality partitioned checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_distinct_count", o -> o.monthlyPartitionDistinctCount);
            put("monthly_partition_distinct_percent", o -> o.monthlyPartitionDistinctPercent);
            put("monthly_partition_duplicate_count", o -> o.monthlyPartitionDuplicateCount);
            put("monthly_partition_duplicate_percent", o -> o.monthlyPartitionDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDistinctCountCheckSpec monthlyPartitionDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDistinctPercentCheckSpec monthlyPartitionDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDuplicateCountCheckSpec monthlyPartitionDuplicateCount;

    @JsonPropertyDescription("Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDuplicatePercentCheckSpec monthlyPartitionDuplicatePercent;

    /**
     * Returns a distinct values count check specification.
     * @return Distinct values count check specification.
     */
    public ColumnDistinctCountCheckSpec getMonthlyPartitionDistinctCount() {
        return monthlyPartitionDistinctCount;
    }

    /**
     * Sets a new specification of a distinct values count check.
     * @param monthlyPartitionDistinctCount Distinct values count check specification.
     */
    public void setMonthlyPartitionDistinctCount(ColumnDistinctCountCheckSpec monthlyPartitionDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDistinctCount, monthlyPartitionDistinctCount));
        this.monthlyPartitionDistinctCount = monthlyPartitionDistinctCount;
        propagateHierarchyIdToField(monthlyPartitionDistinctCount, "monthly_partition_distinct_count");
    }

    /**
     * Returns a distinct values percent check specification.
     * @return Distinct values percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getMonthlyPartitionDistinctPercent() {
        return monthlyPartitionDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct values percent check.
     * @param monthlyPartitionDistinctPercent Distinct values percent check specification.
     */
    public void setMonthlyPartitionDistinctPercent(ColumnDistinctPercentCheckSpec monthlyPartitionDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDistinctPercent, monthlyPartitionDistinctPercent));
        this.monthlyPartitionDistinctPercent = monthlyPartitionDistinctPercent;
        propagateHierarchyIdToField(monthlyPartitionDistinctPercent, "monthly_partition_distinct_percent");
    }

    /**
     * Returns a duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getMonthlyPartitionDuplicateCount() {
        return monthlyPartitionDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param monthlyPartitionDuplicateCount Duplicate values count check specification.
     */
    public void setMonthlyPartitionDuplicateCount(ColumnDuplicateCountCheckSpec monthlyPartitionDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDuplicateCount, monthlyPartitionDuplicateCount));
        this.monthlyPartitionDuplicateCount = monthlyPartitionDuplicateCount;
        propagateHierarchyIdToField(monthlyPartitionDuplicateCount, "monthly_partition_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getMonthlyPartitionDuplicatePercent() {
        return monthlyPartitionDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param monthlyPartitionDuplicatePercent Duplicate values percent check specification.
     */
    public void setMonthlyPartitionDuplicatePercent(ColumnDuplicatePercentCheckSpec monthlyPartitionDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDuplicatePercent, monthlyPartitionDuplicatePercent));
        this.monthlyPartitionDuplicatePercent = monthlyPartitionDuplicatePercent;
        propagateHierarchyIdToField(monthlyPartitionDuplicatePercent, "monthly_partition_duplicate_percent");
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