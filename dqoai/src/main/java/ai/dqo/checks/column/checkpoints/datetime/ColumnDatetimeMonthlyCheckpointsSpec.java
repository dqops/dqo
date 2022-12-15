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
import ai.dqo.checks.column.datetime.ColumnMaxDateValuesInFuturePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeMonthlyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeMonthlyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_max_datetime_values_in_future_percent", o -> o.monthlyCheckpointMaxDatetimeValuesInFuturePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of datetime values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnMaxDateValuesInFuturePercentCheckSpec monthlyCheckpointMaxDatetimeValuesInFuturePercent;

    /**
     * Returns a maximum datetime values in future percent check.
     * @return Maximum datetime values in future percent check.
     */
    public ColumnMaxDateValuesInFuturePercentCheckSpec getMonthlyCheckpointMaxDatetimeValuesInFuturePercent() {
        return monthlyCheckpointMaxDatetimeValuesInFuturePercent;
    }

    /**
     * Sets a new definition of a maximum datetime values in future percent check.
     * @param monthlyCheckpointMaxDatetimeValuesInFuturePercent Maximum datetime values in future percent check.
     */
    public void setMonthlyCheckpointMaxDatetimeValuesInFuturePercent(ColumnMaxDateValuesInFuturePercentCheckSpec monthlyCheckpointMaxDatetimeValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxDatetimeValuesInFuturePercent, monthlyCheckpointMaxDatetimeValuesInFuturePercent));
        this.monthlyCheckpointMaxDatetimeValuesInFuturePercent = monthlyCheckpointMaxDatetimeValuesInFuturePercent;
        propagateHierarchyIdToField(monthlyCheckpointMaxDatetimeValuesInFuturePercent, "monthly_checkpoint_max_datetime_values_in_future_percent");
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