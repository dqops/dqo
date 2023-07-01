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
package com.dqops.checks.column.partitioned.nulls;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNotNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNotNullsPercentCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsPercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of nulls data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_nulls_count", o -> o.dailyPartitionNullsCount);
            put("daily_partition_nulls_percent", o -> o.dailyPartitionNullsPercent);
            put("daily_partition_not_nulls_count", o -> o.dailyPartitionNotNullsCount);
            put("daily_partition_not_nulls_percent", o -> o.dailyPartitionNotNullsPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNullsCountCheckSpec dailyPartitionNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNullsPercentCheckSpec dailyPartitionNullsPercent;

    @JsonPropertyDescription("Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNotNullsCountCheckSpec dailyPartitionNotNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNotNullsPercentCheckSpec dailyPartitionNotNullsPercent;

    /**
     * Returns a nulls count check.
     * @return Nulls count check.
     */
    public ColumnNullsCountCheckSpec getDailyPartitionNullsCount() {
        return dailyPartitionNullsCount;
    }

    /**
     * Sets a new definition of a nulls count check.
     * @param dailyPartitionNullsCount Nulls count check.
     */
    public void setDailyPartitionNullsCount(ColumnNullsCountCheckSpec dailyPartitionNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsCount, dailyPartitionNullsCount));
        this.dailyPartitionNullsCount = dailyPartitionNullsCount;
        propagateHierarchyIdToField(dailyPartitionNullsCount, "daily_partition_nulls_count");
    }

    /**
     * Returns a nulls percent check.
     * @return Nulls percent check.
     */
    public ColumnNullsPercentCheckSpec getDailyPartitionNullsPercent() {
        return dailyPartitionNullsPercent;
    }

    /**
     * Sets a new definition of a nulls percent check.
     * @param dailyPartitionNullsPercent Nulls percent check.
     */
    public void setDailyPartitionNullsPercent(ColumnNullsPercentCheckSpec dailyPartitionNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercent, dailyPartitionNullsPercent));
        this.dailyPartitionNullsPercent = dailyPartitionNullsPercent;
        propagateHierarchyIdToField(dailyPartitionNullsPercent, "daily_partition_nulls_percent");
    }

    /**
     * Returns a not nulls count check.
     * @return Not nulls count check.
     */
    public ColumnNotNullsCountCheckSpec getDailyPartitionNotNullsCount() {
        return dailyPartitionNotNullsCount;
    }

    /**
     * Sets a new definition of a not nulls count check.
     * @param dailyPartitionNotNullsCount Not nulls count check.
     */
    public void setDailyPartitionNotNullsCount(ColumnNotNullsCountCheckSpec dailyPartitionNotNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNotNullsCount, dailyPartitionNotNullsCount));
        this.dailyPartitionNotNullsCount = dailyPartitionNotNullsCount;
        propagateHierarchyIdToField(dailyPartitionNotNullsCount, "daily_partition_not_nulls_count");
    }

    /**
     * Returns a not nulls percent check.
     * @return Not nulls percent check.
     */
    public ColumnNotNullsPercentCheckSpec getDailyPartitionNotNullsPercent() {
        return dailyPartitionNotNullsPercent;
    }

    /**
     * Sets a new definition of a not nulls percent check.
     * @param dailyPartitionNotNullsPercent Not nulls percent check.
     */
    public void setDailyPartitionNotNullsPercent(ColumnNotNullsPercentCheckSpec dailyPartitionNotNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNotNullsPercent, dailyPartitionNotNullsPercent));
        this.dailyPartitionNotNullsPercent = dailyPartitionNotNullsPercent;
        propagateHierarchyIdToField(dailyPartitionNotNullsPercent, "daily_partition_not_nulls_percent");
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
