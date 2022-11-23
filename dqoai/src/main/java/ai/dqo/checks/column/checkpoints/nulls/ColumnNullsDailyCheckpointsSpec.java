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
package ai.dqo.checks.column.checkpoints.nulls;

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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_max_nulls_count", o -> o.dailyCheckpointMaxNullsCount);
            put("daily_checkpoint_max_nulls_percent", o -> o.dailyCheckpointMaxNullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of nulls in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxNullsCountCheckSpec dailyCheckpointMaxNullsCount;

    @JsonPropertyDescription("Verifies that the number of nulls in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxNullsPercentCheckSpec dailyCheckpointMaxNullsPercent;

    /**
     * Returns a maximum nulls count check.
     * @return Maximum nulls count check.
     */
    public ColumnMaxNullsCountCheckSpec getDailyCheckpointMaxNullsCount() {
        return dailyCheckpointMaxNullsCount;
    }

    /**
     * Sets a new definition of a maximum nulls count check.
     * @param dailyCheckpointMaxNullsCount Maximum nulls count check.
     */
    public void setDailyCheckpointMaxNullsCount(ColumnMaxNullsCountCheckSpec dailyCheckpointMaxNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxNullsCount, dailyCheckpointMaxNullsCount));
        this.dailyCheckpointMaxNullsCount = dailyCheckpointMaxNullsCount;
        propagateHierarchyIdToField(dailyCheckpointMaxNullsCount, "daily_checkpoint_max_nulls_count");
    }

    /**
     * Returns a maximum nulls percent check.
     * @return Maximum nulls percent check.
     */
    public ColumnMaxNullsPercentCheckSpec getDailyCheckpointMaxNullsPercent() {
        return dailyCheckpointMaxNullsPercent;
    }

    /**
     * Sets a new definition of a maximum nulls percent check.
     * @param dailyCheckpointMaxNullsPercent Maximum nulls percent check.
     */
    public void setDailyCheckpointMaxNullsPercent(ColumnMaxNullsPercentCheckSpec dailyCheckpointMaxNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxNullsPercent, dailyCheckpointMaxNullsPercent));
        this.dailyCheckpointMaxNullsPercent = dailyCheckpointMaxNullsPercent;
        propagateHierarchyIdToField(dailyCheckpointMaxNullsPercent, "daily_checkpoint_max_nulls_percent");
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
