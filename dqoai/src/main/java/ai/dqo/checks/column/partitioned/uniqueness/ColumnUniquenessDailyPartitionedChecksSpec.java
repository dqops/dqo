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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_min_unique_count", o -> o.dailyPartitionMinUniqueCount);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMinUniqueCountCheckSpec dailyPartitionMinUniqueCount;

    /**
     * Returns a minimum unique values count check.
     * @return Minimum unique values count check.
     */
    public ColumnMinUniqueCountCheckSpec getDailyPartitionMinUniqueCount() {
        return dailyPartitionMinUniqueCount;
    }

    /**
     * Sets a new definition of a minimum unique values count check.
     * @param dailyPartitionMinUniqueCount Minimum unique values count check.
     */
    public void setDailyPartitionMinUniqueCount(ColumnMinUniqueCountCheckSpec dailyPartitionMinUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinUniqueCount, dailyPartitionMinUniqueCount));
        this.dailyPartitionMinUniqueCount = dailyPartitionMinUniqueCount;
        propagateHierarchyIdToField(dailyPartitionMinUniqueCount, "daily_partition_min_unique_count");
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