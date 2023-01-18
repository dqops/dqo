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
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDuplicateCountCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDuplicatePercentCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnUniqueCountCheckSpec;
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
public class ColumnUniquenessMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_unique_count", o -> o.monthlyPartitionUniqueCount);
            put("monthly_partition_duplicate_count", o -> o.monthlyPartitionDuplicateCount);
            put("monthly_partition_duplicate_percent", o -> o.monthlyPartitionDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnUniqueCountCheckSpec monthlyPartitionUniqueCount;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDuplicateCountCheckSpec monthlyPartitionDuplicateCount;

    @JsonPropertyDescription("Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDuplicatePercentCheckSpec monthlyPartitionDuplicatePercent;

    /**
     * Returns a unique values count check specification.
     * @return Unique values count check specification.
     */
    public ColumnUniqueCountCheckSpec getMonthlyPartitionUniqueCount() {
        return monthlyPartitionUniqueCount;
    }

    /**
     * Sets a new specification of a unique values count check.
     * @param monthlyPartitionUniqueCount Unique values count check specification.
     */
    public void setMonthlyPartitionUniqueCount(ColumnUniqueCountCheckSpec monthlyPartitionUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionUniqueCount, monthlyPartitionUniqueCount));
        this.monthlyPartitionUniqueCount = monthlyPartitionUniqueCount;
        propagateHierarchyIdToField(monthlyPartitionUniqueCount, "monthly_partition_unique_count");
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