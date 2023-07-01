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
package com.dqops.checks.column.recurring.uniqueness;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctCountCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctPercentCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDuplicateCountCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDuplicatePercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of uniqueness data quality recurring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessMonthlyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessMonthlyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_distinct_count", o -> o.monthlyDistinctCount);
            put("monthly_distinct_percent", o -> o.monthlyDistinctPercent);
            put("monthly_duplicate_count", o -> o.monthlyDuplicateCount);
            put("monthly_duplicate_percent", o -> o.monthlyDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDistinctCountCheckSpec monthlyDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDistinctPercentCheckSpec monthlyDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDuplicateCountCheckSpec monthlyDuplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDuplicatePercentCheckSpec monthlyDuplicatePercent;

    /**
     * Returns a distinct values count check specification.
     * @return Distinct values count check specification.
     */
    public ColumnDistinctCountCheckSpec getMonthlyDistinctCount() {
        return monthlyDistinctCount;
    }

    /**
     * Sets a new specification of a distinct values count check.
     * @param monthlyDistinctCount Distinct values count check specification.
     */
    public void setMonthlyDistinctCount(ColumnDistinctCountCheckSpec monthlyDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyDistinctCount, monthlyDistinctCount));
        this.monthlyDistinctCount = monthlyDistinctCount;
        propagateHierarchyIdToField(monthlyDistinctCount, "monthly_distinct_count");
    }

    /**
     * Returns a distinct values percent check specification.
     * @return Distinct values percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getMonthlyDistinctPercent() {
        return monthlyDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct values percent check.
     * @param monthlyDistinctPercent Distinct values count percent specification.
     */
    public void setMonthlyDistinctPercent(ColumnDistinctPercentCheckSpec monthlyDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDistinctPercent, monthlyDistinctPercent));
        this.monthlyDistinctPercent = monthlyDistinctPercent;
        propagateHierarchyIdToField(monthlyDistinctPercent, "monthly_distinct_percent");
    }

    /**
     * Returns a duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getMonthlyDuplicateCount() {
        return monthlyDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param monthlyDuplicateCount Duplicate values count check specification.
     */
    public void setMonthlyDuplicateCount(ColumnDuplicateCountCheckSpec monthlyDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyDuplicateCount, monthlyDuplicateCount));
        this.monthlyDuplicateCount = monthlyDuplicateCount;
        propagateHierarchyIdToField(monthlyDuplicateCount, "monthly_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getMonthlyDuplicatePercent() {
        return monthlyDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param monthlyDuplicatePercent Duplicate values percent check specification.
     */
    public void setMonthlyDuplicatePercent(ColumnDuplicatePercentCheckSpec monthlyDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDuplicatePercent, monthlyDuplicatePercent));
        this.monthlyDuplicatePercent = monthlyDuplicatePercent;
        propagateHierarchyIdToField(monthlyDuplicatePercent, "monthly_duplicate_percent");
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