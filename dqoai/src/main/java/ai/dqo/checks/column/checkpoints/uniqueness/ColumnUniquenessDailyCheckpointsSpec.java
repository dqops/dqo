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
import ai.dqo.checks.column.numeric.ColumnMaxNegativeCountCheckSpec;
import ai.dqo.checks.column.numeric.ColumnMaxNegativePercentCheckSpec;
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
public class ColumnUniquenessDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_min_unique_count", o -> o.dailyCheckpointMinUniqueCount);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinUniqueCountCheckSpec dailyCheckpointMinUniqueCount;

    /**
     * Returns a minimum unique values count check.
     * @return Minimum unique values count check.
     */
    public ColumnMinUniqueCountCheckSpec getDailyCheckpointMinUniqueCount() {
        return dailyCheckpointMinUniqueCount;
    }

    /**
     * Sets a new definition of a minimum unique values count check.
     * @param dailyCheckpointMinUniqueCount Minimum unique values count check.
     */
    public void setDailyCheckpointMinUniqueCount(ColumnMinUniqueCountCheckSpec dailyCheckpointMinUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinUniqueCount, dailyCheckpointMinUniqueCount));
        this.dailyCheckpointMinUniqueCount = dailyCheckpointMinUniqueCount;
        propagateHierarchyIdToField(dailyCheckpointMinUniqueCount, "daily_checkpoint_min_unique_count");
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