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
package com.dqops.checks.column.partitioned.uniqueness;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctPercentCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDuplicateCountCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDuplicatePercentCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctCountCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of uniqueness data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_distinct_count", o -> o.dailyPartitionDistinctCount);
            put("daily_partition_distinct_percent", o -> o.dailyPartitionDistinctPercent);
            put("daily_partition_duplicate_count", o -> o.dailyPartitionDuplicateCount);
            put("daily_partition_duplicate_percent", o -> o.dailyPartitionDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDistinctCountCheckSpec dailyPartitionDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDistinctPercentCheckSpec dailyPartitionDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDuplicateCountCheckSpec dailyPartitionDuplicateCount;

    @JsonPropertyDescription("Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDuplicatePercentCheckSpec dailyPartitionDuplicatePercent;

    /**
     * Returns a distinct values count check specification.
     * @return Distinct values count check specification.
     */
    public ColumnDistinctCountCheckSpec getDailyPartitionDistinctCount() {
        return dailyPartitionDistinctCount;
    }

    /**
     * Sets a new specification of a distinct values count check.
     * @param dailyPartitionDistinctCount Distinct values count check specification.
     */
    public void setDailyPartitionDistinctCount(ColumnDistinctCountCheckSpec dailyPartitionDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctCount, dailyPartitionDistinctCount));
        this.dailyPartitionDistinctCount = dailyPartitionDistinctCount;
        propagateHierarchyIdToField(dailyPartitionDistinctCount, "daily_partition_distinct_count");
    }

    /**
     * Returns a distinct values percent check specification.
     * @return Distinct values percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getDailyPartitionDistinctPercent() {
        return dailyPartitionDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct values percent check.
     * @param dailyPartitionDistinctPercent Distinct values percent check specification.
     */
    public void setDailyPartitionDistinctPercent(ColumnDistinctPercentCheckSpec dailyPartitionDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctPercent, dailyPartitionDistinctPercent));
        this.dailyPartitionDistinctPercent = dailyPartitionDistinctPercent;
        propagateHierarchyIdToField(dailyPartitionDistinctPercent, "daily_partition_distinct_percent");
    }

    /**
     * Returns a duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getDailyPartitionDuplicateCount() {
        return dailyPartitionDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param dailyPartitionDuplicateCount Duplicate values count check specification.
     */
    public void setDailyPartitionDuplicateCount(ColumnDuplicateCountCheckSpec dailyPartitionDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDuplicateCount, dailyPartitionDuplicateCount));
        this.dailyPartitionDuplicateCount = dailyPartitionDuplicateCount;
        propagateHierarchyIdToField(dailyPartitionDuplicateCount, "daily_partition_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getDailyPartitionDuplicatePercent() {
        return dailyPartitionDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param dailyPartitionDuplicatePercent Duplicate values percent check specification.
     */
    public void setDailyPartitionDuplicatePercent(ColumnDuplicatePercentCheckSpec dailyPartitionDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDuplicatePercent, dailyPartitionDuplicatePercent));
        this.dailyPartitionDuplicatePercent = dailyPartitionDuplicatePercent;
        propagateHierarchyIdToField(dailyPartitionDuplicatePercent, "daily_partition_duplicate_percent");
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