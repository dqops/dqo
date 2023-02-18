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
package ai.dqo.checks.column.partitioned.integrity;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.integrity.*;
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
public class ColumnIntegrityMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_foreign_key_not_match_count", o -> o.monthlyPartitionForeignKeyNotMatchCount);
            put("monthly_partition_foreign_key_match_percent", o -> o.monthlyPartitionForeignKeyMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnIntegrityForeignKeyNotMatchCountCheckSpec monthlyPartitionForeignKeyNotMatchCount;

    @JsonPropertyDescription("Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec monthlyPartitionForeignKeyMatchPercent;

    /**
     * Returns an integrity value not match count check.
     * @return Integrity value not match count check.
     */
    public ColumnIntegrityForeignKeyNotMatchCountCheckSpec getMonthlyPartitionForeignKeyNotMatchCount() {
        return monthlyPartitionForeignKeyNotMatchCount;
    }

    /**
     * Sets a new definition of an integrity value not match count check.
     * @param monthlyPartitionForeignKeyNotMatchCount Integrity value not match count check.
     */
    public void setMonthlyPartitionForeignKeyNotMatchCount(ColumnIntegrityForeignKeyNotMatchCountCheckSpec monthlyPartitionForeignKeyNotMatchCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionForeignKeyNotMatchCount, monthlyPartitionForeignKeyNotMatchCount));
        this.monthlyPartitionForeignKeyNotMatchCount = monthlyPartitionForeignKeyNotMatchCount;
        propagateHierarchyIdToField(monthlyPartitionForeignKeyNotMatchCount, "monthly_partition_foreign_key_not_match_count");
    }

    /**
     * Returns an integrity value match percent check.
     * @return Integrity value match percent check.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getMonthlyPartitionForeignKeyMatchPercent() {
        return monthlyPartitionForeignKeyMatchPercent;
    }

    /**
     * Sets a new definition of an integrity value match percent check.
     * @param monthlyPartitionForeignKeyMatchPercent Integrity value match percent check.
     */
    public void setMonthlyPartitionForeignKeyMatchPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec monthlyPartitionForeignKeyMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionForeignKeyMatchPercent, monthlyPartitionForeignKeyMatchPercent));
        this.monthlyPartitionForeignKeyMatchPercent = monthlyPartitionForeignKeyMatchPercent;
        propagateHierarchyIdToField(monthlyPartitionForeignKeyMatchPercent, "monthly_partition_foreign_key_match_percent");
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
