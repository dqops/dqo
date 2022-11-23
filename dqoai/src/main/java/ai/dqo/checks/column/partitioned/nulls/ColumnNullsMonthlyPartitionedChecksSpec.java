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
import ai.dqo.checks.column.checks.nulls.ColumnMaxNullsCountCheckSpec;
import ai.dqo.checks.column.checks.nulls.ColumnMaxNullsPercentCheckSpec;
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
            put("monthly_partition_max_nulls_count", o -> o.monthlyPartitionMaxNullsCount);
            put("monthly_partition_max_nulls_percent", o -> o.monthlyPartitionMaxNullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of nulls in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monhtly partition.")
    private ColumnMaxNullsCountCheckSpec monthlyPartitionMaxNullsCount;

    @JsonPropertyDescription("Verifies that the number of nulls in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monhtly partition.")
    private ColumnMaxNullsPercentCheckSpec monthlyPartitionMaxNullsPercent;

    /**
     * Returns a maximum nulls count check.
     * @return Maximum nulls count check.
     */
    public ColumnMaxNullsCountCheckSpec getMonthlyPartitionMaxNullsCount() {
        return monthlyPartitionMaxNullsCount;
    }

    /**
     * Sets a new definition of a maximum nulls count check.
     * @param monthlyPartitionMaxNullsCount Maximum nulls count check.
     */
    public void setMonthlyPartitionMaxNullsCount(ColumnMaxNullsCountCheckSpec monthlyPartitionMaxNullsCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxNullsCount, monthlyPartitionMaxNullsCount));
        this.monthlyPartitionMaxNullsCount = monthlyPartitionMaxNullsCount;
        propagateHierarchyIdToField(monthlyPartitionMaxNullsCount, "monthly_partition_max_nulls_count");
    }

    /**
     * Returns a maximum nulls percent check.
     * @return Maximum nulls percent check.
     */
    public ColumnMaxNullsPercentCheckSpec getMonthlyPartitionMaxNullsPercent() {
        return monthlyPartitionMaxNullsPercent;
    }

    /**
     * Sets a new definition of a maximum nulls percent check.
     * @param monthlyPartitionMaxNullsPercent Maximum nulls percent check.
     */
    public void setMonthlyPartitionMaxNullsPercent(ColumnMaxNullsPercentCheckSpec monthlyPartitionMaxNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxNullsPercent, monthlyPartitionMaxNullsPercent));
        this.monthlyPartitionMaxNullsPercent = monthlyPartitionMaxNullsPercent;
        propagateHierarchyIdToField(monthlyPartitionMaxNullsPercent, "monthly_partition_max_nulls_percent");
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
