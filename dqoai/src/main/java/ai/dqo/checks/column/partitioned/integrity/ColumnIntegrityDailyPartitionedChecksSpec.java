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
import ai.dqo.checks.column.checkspecs.integrity.ColumnIntegrityForeignKeyMatchPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.integrity.ColumnIntegrityForeignKeyNotMatchCountCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_foreign_key_not_match_count", o -> o.dailyPartitionForeignKeyNotMatchCount);
            put("daily_partition_foreign_key_match_percent", o -> o.dailyPartitionForeignKeyMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnIntegrityForeignKeyNotMatchCountCheckSpec dailyPartitionForeignKeyNotMatchCount;

    @JsonPropertyDescription("Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyPartitionForeignKeyMatchPercent;

    /**
     * Returns an integrity value not match count check.
     * @return Integrity value not match count check.
     */
    public ColumnIntegrityForeignKeyNotMatchCountCheckSpec getDailyPartitionForeignKeyNotMatchCount() {
        return dailyPartitionForeignKeyNotMatchCount;
    }

    /**
     * Sets a new definition of an integrity value not match count check.
     * @param dailyPartitionForeignKeyNotMatchCount Integrity value not match count check.
     */
    public void setDailyPartitionForeignKeyNotMatchCount(ColumnIntegrityForeignKeyNotMatchCountCheckSpec dailyPartitionForeignKeyNotMatchCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionForeignKeyNotMatchCount, dailyPartitionForeignKeyNotMatchCount));
        this.dailyPartitionForeignKeyNotMatchCount = dailyPartitionForeignKeyNotMatchCount;
        propagateHierarchyIdToField(dailyPartitionForeignKeyNotMatchCount, "daily_partition_foreign_key_not_match_count");
    }

    /**
     * Returns an integrity value match percent check.
     * @return Integrity value match percent check.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getDailyPartitionForeignKeyMatchPercent() {
        return dailyPartitionForeignKeyMatchPercent;
    }

    /**
     * Sets a new definition of an integrity value match percent check.
     * @param dailyPartitionForeignKeyMatchPercent Integrity value match percent check.
     */
    public void setDailyPartitionForeignKeyMatchPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyPartitionForeignKeyMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionForeignKeyMatchPercent, dailyPartitionForeignKeyMatchPercent));
        this.dailyPartitionForeignKeyMatchPercent = dailyPartitionForeignKeyMatchPercent;
        propagateHierarchyIdToField(dailyPartitionForeignKeyMatchPercent, "daily_partition_foreign_key_match_percent");
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
