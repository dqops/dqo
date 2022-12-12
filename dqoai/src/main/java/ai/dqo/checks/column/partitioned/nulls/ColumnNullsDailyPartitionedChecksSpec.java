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
import ai.dqo.checks.column.checkspecs.nulls.ColumnMaxNullsCountCheckSpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnMaxNullsPercentCheckSpec;
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
public class ColumnNullsDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_max_nulls_count", o -> o.dailyPartitionMaxNullsCount);
            put("daily_partition_max_nulls_percent", o -> o.dailyPartitionMaxNullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of nulls in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxNullsCountCheckSpec dailyPartitionMaxNullsCount;

    @JsonPropertyDescription("Verifies that the number of nulls in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxNullsPercentCheckSpec dailyPartitionMaxNullsPercent;

    /**
     * Returns a maximum nulls count check.
     * @return Maximum nulls count check.
     */
    public ColumnMaxNullsCountCheckSpec getDailyPartitionMaxNullsCount() {
        return dailyPartitionMaxNullsCount;
    }

    /**
     * Sets a new definition of a maximum nulls count check.
     * @param dailyPartitionMaxNullsCount Maximum nulls count check.
     */
    public void setDailyPartitionMaxNullsCount(ColumnMaxNullsCountCheckSpec dailyPartitionMaxNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxNullsCount, dailyPartitionMaxNullsCount));
        this.dailyPartitionMaxNullsCount = dailyPartitionMaxNullsCount;
        propagateHierarchyIdToField(dailyPartitionMaxNullsCount, "daily_partition_max_nulls_count");
    }

    /**
     * Returns a maximum nulls percent check.
     * @return Maximum nulls percent check.
     */
    public ColumnMaxNullsPercentCheckSpec getDailyPartitionMaxNullsPercent() {
        return dailyPartitionMaxNullsPercent;
    }

    /**
     * Sets a new definition of a maximum nulls percent check.
     * @param dailyPartitionMaxNullsPercent Maximum nulls percent check.
     */
    public void setDailyPartitionMaxNullsPercent(ColumnMaxNullsPercentCheckSpec dailyPartitionMaxNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxNullsPercent, dailyPartitionMaxNullsPercent));
        this.dailyPartitionMaxNullsPercent = dailyPartitionMaxNullsPercent;
        propagateHierarchyIdToField(dailyPartitionMaxNullsPercent, "daily_partition_max_nulls_percent");
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
