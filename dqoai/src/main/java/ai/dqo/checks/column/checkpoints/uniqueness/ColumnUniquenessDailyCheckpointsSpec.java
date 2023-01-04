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
package ai.dqo.checks.column.checkpoints.uniqueness;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.uniqueness.ColumnDuplicateCountCheckSpec;
import ai.dqo.checks.column.uniqueness.ColumnDuplicatePercentCheckSpec;
import ai.dqo.checks.column.uniqueness.ColumnUniqueCountCheckSpec;
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
public class ColumnUniquenessDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_unique_count", o -> o.dailyCheckpointUniqueCount);
            put("daily_checkpoint_duplicate_count", o -> o.dailyCheckpointDuplicateCount);
            put("daily_checkpoint_duplicate_percent", o -> o.dailyCheckpointDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnUniqueCountCheckSpec dailyCheckpointUniqueCount;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDuplicateCountCheckSpec dailyCheckpointDuplicateCount;

    @JsonPropertyDescription("Verifies that the percent of duplicate values in a column does not exceed the set percent. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDuplicatePercentCheckSpec dailyCheckpointDuplicatePercent;

    /**
     * Returns a unique values count check specification.
     * @return Unique values count check specification.
     */
    public ColumnUniqueCountCheckSpec getDailyCheckpointUniqueCount() {
        return dailyCheckpointUniqueCount;
    }

    /**
     * Sets a new specification of a unique values count check.
     * @param dailyCheckpointUniqueCount Unique values count check specification.
     */
    public void setDailyCheckpointUniqueCount(ColumnUniqueCountCheckSpec dailyCheckpointUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointUniqueCount, dailyCheckpointUniqueCount));
        this.dailyCheckpointUniqueCount = dailyCheckpointUniqueCount;
        propagateHierarchyIdToField(dailyCheckpointUniqueCount, "daily_checkpoint_unique_count");
    }

    /**
     * Returns a  duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getDailyCheckpointDuplicateCount() {
        return dailyCheckpointDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param dailyCheckpointDuplicateCount Duplicate values count check specification.
     */
    public void setDailyCheckpointDuplicateCount(ColumnDuplicateCountCheckSpec dailyCheckpointDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointDuplicateCount, dailyCheckpointDuplicateCount));
        this.dailyCheckpointDuplicateCount = dailyCheckpointDuplicateCount;
        propagateHierarchyIdToField(dailyCheckpointDuplicateCount, "daily_checkpoint_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getDailyCheckpointDuplicatePercent() {
        return dailyCheckpointDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param dailyCheckpointDuplicatePercent Duplicate values percent check specification.
     */
    public void setDailyCheckpointDuplicatePercent(ColumnDuplicatePercentCheckSpec dailyCheckpointDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointDuplicatePercent, dailyCheckpointDuplicatePercent));
        this.dailyCheckpointDuplicatePercent = dailyCheckpointDuplicatePercent;
        propagateHierarchyIdToField(dailyCheckpointDuplicatePercent, "daily_checkpoint_duplicate_percent");
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