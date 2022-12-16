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
package ai.dqo.checks.column.checkpoints.bool;

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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBoolDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBoolDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_min_true_percent", o -> o.dailyCheckpointMinTruePercent);
            put("daily_checkpoint_min_false_percent", o -> o.dailyCheckpointMinFalsePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of true values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinTruePercentCheckSpec dailyCheckpointMinTruePercent;

    @JsonPropertyDescription("Verifies that the percentage of false values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinFalsePercentCheckSpec dailyCheckpointMinFalsePercent;

    /**
     * Returns a minimum true percent check.
     * @return Minimum true percent check.
     */
    public ColumnMinTruePercentCheckSpec getDailyCheckpointMinTruePercent() {
        return dailyCheckpointMinTruePercent;
    }

    /**
     * Sets a new definition of a minimum true percent check.
     * @param dailyCheckpointMinTruePercent Minimum true percent check.
     */
    public void setDailyCheckpointMinTruePercent(ColumnMinTruePercentCheckSpec dailyCheckpointMinTruePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinTruePercent, dailyCheckpointMinTruePercent));
        this.dailyCheckpointMinTruePercent = dailyCheckpointMinTruePercent;
        propagateHierarchyIdToField(dailyCheckpointMinTruePercent, "daily_checkpoint_min_true_percent");
    }

    /**
     * Returns a minimum false percent check.
     * @return Minimum false percent check.
     */
    public ColumnMinFalsePercentCheckSpec getDailyCheckpointMinFalsePercent() {
        return dailyCheckpointMinFalsePercent;
    }

    /**
     * Sets a new definition of a minimum false percent check.
     * @param dailyCheckpointMinFalsePercent Minimum false percent check.
     */
    public void setDailyCheckpointMinFalsePercent(ColumnMinFalsePercentCheckSpec dailyCheckpointMinFalsePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinFalsePercent, dailyCheckpointMinFalsePercent));
        this.dailyCheckpointMinFalsePercent = dailyCheckpointMinFalsePercent;
        propagateHierarchyIdToField(dailyCheckpointMinFalsePercent, "daily_checkpoint_min_false_percent");
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