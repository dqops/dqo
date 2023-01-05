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
package ai.dqo.checks.column.partitioned.bool;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.bool.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBoolMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBoolMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_true_percent", o -> o.monthlyPartitionTruePercent);
            put("monthly_partition_false_percent", o -> o.monthlyPartitionFalsePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnTruePercentCheckSpec monthlyPartitionTruePercent;

    @JsonPropertyDescription("Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnFalsePercentCheckSpec monthlyPartitionFalsePercent;

    /**
     * Returns a true check.
     * @return True check.
     */
    public ColumnTruePercentCheckSpec getMonthlyPartitionTruePercent() {
        return monthlyPartitionTruePercent;
    }

    /**
     * Sets a new definition of a true check.
     * @param monthlyPartitionTruePercent True check.
     */
    public void setMonthlyPartitionTruePercent(ColumnTruePercentCheckSpec monthlyPartitionTruePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTruePercent, monthlyPartitionTruePercent));
        this.monthlyPartitionTruePercent = monthlyPartitionTruePercent;
        propagateHierarchyIdToField(monthlyPartitionTruePercent, "monthly_partition_true_percent");
    }

    /**
     * Returns a false check.
     * @return False check.
     */
    public ColumnFalsePercentCheckSpec getMonthlyPartitionFalsePercent() {
        return monthlyPartitionFalsePercent;
    }

    /**
     * Sets a new definition of a false check.
     * @param monthlyPartitionFalsePercent False check.
     */
    public void setMonthlyPartitionFalsePercent(ColumnFalsePercentCheckSpec monthlyPartitionFalsePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionFalsePercent, monthlyPartitionFalsePercent));
        this.monthlyPartitionFalsePercent = monthlyPartitionFalsePercent;
        propagateHierarchyIdToField(monthlyPartitionFalsePercent, "monthly_partition_false_percent");
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
