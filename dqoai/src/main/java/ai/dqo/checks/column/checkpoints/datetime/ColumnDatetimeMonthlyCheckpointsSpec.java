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
import ai.dqo.checks.column.checkspecs.datetime.ColumnDateValuesInFuturePercentCheckSpec;
import ai.dqo.checks.column.checkspecs.datetime.ColumnDatetimeValueInRangeDatePercentCheckSpec;
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
            put("monthly_checkpoint_date_values_in_future_percent", o -> o.monthlyCheckpointDateValuesInFuturePercent);
            put("monthly_checkpoint_datetime_value_in_range_date_percent", o -> o.monthlyCheckpointDatetimeValueInRangeDatePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDateValuesInFuturePercentCheckSpec monthlyCheckpointDateValuesInFuturePercent;

    @JsonPropertyDescription("Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDatetimeValueInRangeDatePercentCheckSpec monthlyCheckpointDatetimeValueInRangeDatePercent;

    /**
     * Returns a date values in future percent check specification.
     * @return Date values in future percent check specification.
     */
    public ColumnDateValuesInFuturePercentCheckSpec getMonthlyCheckpointDateValuesInFuturePercent() {
        return monthlyCheckpointDateValuesInFuturePercent;
    }

    /**
     * Sets a new definition of a date values in future percent check.
     * @param monthlyCheckpointDateValuesInFuturePercent Date values in future percent check specification.
     */
    public void setMonthlyCheckpointDateValuesInFuturePercent(ColumnDateValuesInFuturePercentCheckSpec monthlyCheckpointDateValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointDateValuesInFuturePercent, monthlyCheckpointDateValuesInFuturePercent));
        this.monthlyCheckpointDateValuesInFuturePercent = monthlyCheckpointDateValuesInFuturePercent;
        propagateHierarchyIdToField(monthlyCheckpointDateValuesInFuturePercent, "monthly_checkpoint_date_values_in_future_percent");
    }

    /**
     * Returns a datetime value in range date percentage check.
     * @return Maximum datetime value in range date percentage check.
     */
    public ColumnDatetimeValueInRangeDatePercentCheckSpec getMonthlyCheckpointDatetimeValueInRangeDatePercent() {
        return monthlyCheckpointDatetimeValueInRangeDatePercent;
    }

    /**
     * Sets a new definition of a datetime value in range date percentage check.
     * @param monthlyCheckpointDatetimeValueInRangeDatePercent Datetime value in range date percentage check.
     */
    public void setMonthlyCheckpointDatetimeValueInRangeDatePercent(ColumnDatetimeValueInRangeDatePercentCheckSpec monthlyCheckpointDatetimeValueInRangeDatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointDatetimeValueInRangeDatePercent, monthlyCheckpointDatetimeValueInRangeDatePercent));
        this.monthlyCheckpointDatetimeValueInRangeDatePercent = monthlyCheckpointDatetimeValueInRangeDatePercent;
        propagateHierarchyIdToField(monthlyCheckpointDatetimeValueInRangeDatePercent, "monthly_checkpoint_datetime_value_in_range_date_percent");
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