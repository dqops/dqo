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
package ai.dqo.checks.column.checkpoints.datetime;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.datetime.ColumnDateValuesInFuturePercentCheckSpec;
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
public class ColumnDatetimeDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_date_values_in_future_percent", o -> o.dailyCheckpointDateValuesInFuturePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnDateValuesInFuturePercentCheckSpec dailyCheckpointDateValuesInFuturePercent;

    /**
     * Returns a date values in future percent check specification.
     * @return Date values in future percent check specification.
     */
    public ColumnDateValuesInFuturePercentCheckSpec getDailyCheckpointDateValuesInFuturePercent() {
        return dailyCheckpointDateValuesInFuturePercent;
    }

    /**
     * Sets a new definition of a date values in future percent check.
     * @param dailyCheckpointDateValuesInFuturePercent Date values in future percent check specification.
     */
    public void setDailyCheckpointDateValuesInFuturePercent(ColumnDateValuesInFuturePercentCheckSpec dailyCheckpointDateValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointDateValuesInFuturePercent, dailyCheckpointDateValuesInFuturePercent));
        this.dailyCheckpointDateValuesInFuturePercent = dailyCheckpointDateValuesInFuturePercent;
        propagateHierarchyIdToField(dailyCheckpointDateValuesInFuturePercent, "daily_checkpoint_date_values_in_future_percent");
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