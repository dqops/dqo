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
package ai.dqo.checks.column.partitioned.nulls;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking daily partitions or rows for each day of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_nulls_count", o -> o.monthlyPartitionNullsCount);
            put("monthly_partition_nulls_percent", o -> o.monthlyPartitionNullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monhtly partition.")
    private ColumnNullsCountCheckSpec monthlyPartitionNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monhtly partition.")
    private ColumnNullsPercentCheckSpec monthlyPartitionNullsPercent;

    /**
     * Returns a nulls count check.
     * @return Nulls count check.
     */
    public ColumnNullsCountCheckSpec getMonthlyPartitionNullsCount() {
        return monthlyPartitionNullsCount;
    }

    /**
     * Sets a new definition of a nulls count check.
     * @param monthlyPartitionNullsCount Nulls count check.
     */
    public void setMonthlyPartitionNullsCount(ColumnNullsCountCheckSpec monthlyPartitionNullsCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNullsCount, monthlyPartitionNullsCount));
        this.monthlyPartitionNullsCount = monthlyPartitionNullsCount;
        propagateHierarchyIdToField(monthlyPartitionNullsCount, "monthly_partition_nulls_count");
    }

    /**
     * Returns a nulls percent check.
     * @return Nulls percent check.
     */
    public ColumnNullsPercentCheckSpec getMonthlyPartitionNullsPercent() {
        return monthlyPartitionNullsPercent;
    }

    /**
     * Sets a new definition of a nulls percent check.
     * @param monthlyPartitionNullsPercent Nulls percent check.
     */
    public void setMonthlyPartitionNullsPercent(ColumnNullsPercentCheckSpec monthlyPartitionNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNullsPercent, monthlyPartitionNullsPercent));
        this.monthlyPartitionNullsPercent = monthlyPartitionNullsPercent;
        propagateHierarchyIdToField(monthlyPartitionNullsPercent, "monthly_partition_nulls_percent");
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
