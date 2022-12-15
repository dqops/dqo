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
import ai.dqo.checks.column.uniqueness.ColumnMaxDuplicateCountCheckSpec;
import ai.dqo.checks.column.uniqueness.ColumnMaxDuplicatePercentCheckSpec;
import ai.dqo.checks.column.uniqueness.ColumnMinUniqueCountCheckSpec;
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
            put("monthly_partition_min_unique_count", o -> o.monthlyPartitionMinUniqueCount);
            put("monthly_partition_max_duplicate_count", o -> o.monthlyPartitionMaxDuplicateCount);
            put("monthly_partition_max_duplicate_percent", o -> o.monthlyPartitionMaxDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMinUniqueCountCheckSpec monthlyPartitionMinUniqueCount;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxDuplicateCountCheckSpec monthlyPartitionMaxDuplicateCount;

    @JsonPropertyDescription("Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxDuplicatePercentCheckSpec monthlyPartitionMaxDuplicatePercent;

    /**
     * Returns a minimum unique values count check.
     * @return Minimum unique values count check.
     */
    public ColumnMinUniqueCountCheckSpec getMonthlyPartitionMinUniqueCount() {
        return monthlyPartitionMinUniqueCount;
    }

    /**
     * Sets a new definition of a minimum unique values count check.
     * @param monthlyPartitionMinUniqueCount Minimum unique values count check.
     */
    public void setMonthlyPartitionMinUniqueCount(ColumnMinUniqueCountCheckSpec monthlyPartitionMinUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinUniqueCount, monthlyPartitionMinUniqueCount));
        this.monthlyPartitionMinUniqueCount = monthlyPartitionMinUniqueCount;
        propagateHierarchyIdToField(monthlyPartitionMinUniqueCount, "monthly_partition_min_unique_count");
    }

    /**
     * Returns a maximum duplicate values count check.
     * @return Maximum duplicate values count check.
     */
    public ColumnMaxDuplicateCountCheckSpec getMonthlyPartitionMaxDuplicateCount() {
        return monthlyPartitionMaxDuplicateCount;
    }

    /**
     * Sets a new definition of a maximum duplicate values count check.
     * @param monthlyPartitionMaxDuplicateCount Maximum duplicate values count check.
     */
    public void setMonthlyPartitionMaxDuplicateCount(ColumnMaxDuplicateCountCheckSpec monthlyPartitionMaxDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxDuplicateCount, monthlyPartitionMaxDuplicateCount));
        this.monthlyPartitionMaxDuplicateCount = monthlyPartitionMaxDuplicateCount;
        propagateHierarchyIdToField(monthlyPartitionMaxDuplicateCount, "monthly_partition_max_duplicate_count");
    }

    /**
     * Returns a maximum duplicate values percent check.
     * @return Maximum duplicate values percent check.
     */
    public ColumnMaxDuplicatePercentCheckSpec getMonthlyPartitionMaxDuplicatePercent() {
        return monthlyPartitionMaxDuplicatePercent;
    }

    /**
     * Sets a new definition of a maximum duplicate values percent check.
     * @param monthlyPartitionMaxDuplicatePercent Maximum duplicate values percent check.
     */
    public void setMonthlyPartitionMaxDuplicatePercent(ColumnMaxDuplicatePercentCheckSpec monthlyPartitionMaxDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxDuplicatePercent, monthlyPartitionMaxDuplicatePercent));
        this.monthlyPartitionMaxDuplicatePercent = monthlyPartitionMaxDuplicatePercent;
        propagateHierarchyIdToField(monthlyPartitionMaxDuplicatePercent, "monthly_partition_max_duplicate_percent");
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