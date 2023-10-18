/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.checks.column.monitoring.bool;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.bool.ColumnFalsePercentCheckSpec;
import com.dqops.checks.column.checkspecs.bool.ColumnTruePercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of boolean data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBoolDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBoolDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_true_percent", o -> o.dailyTruePercent);
            put("daily_false_percent", o -> o.dailyFalsePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTruePercentCheckSpec dailyTruePercent;

    @JsonPropertyDescription("Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnFalsePercentCheckSpec dailyFalsePercent;

    /**
     * Returns a true percent check specification.
     * @return True percent check specification.
     */
    public ColumnTruePercentCheckSpec getDailyTruePercent() {
        return dailyTruePercent;
    }

    /**
     * Sets a new definition of a true percent check.
     * @param dailyTruePercent True percent check specification.
     */
    public void setDailyTruePercent(ColumnTruePercentCheckSpec dailyTruePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTruePercent, dailyTruePercent));
        this.dailyTruePercent = dailyTruePercent;
        propagateHierarchyIdToField(dailyTruePercent, "daily_true_percent");
    }

    /**
     * Returns a false percent check specification.
     * @return False percent check specification.
     */
    public ColumnFalsePercentCheckSpec getDailyFalsePercent() {
        return dailyFalsePercent;
    }

    /**
     * Sets a new definition of a false percent check.
     * @param dailyFalsePercent False percent check specification.
     */
    public void setDailyFalsePercent(ColumnFalsePercentCheckSpec dailyFalsePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyFalsePercent, dailyFalsePercent));
        this.dailyFalsePercent = dailyFalsePercent;
        propagateHierarchyIdToField(dailyFalsePercent, "daily_false_percent");
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

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public ColumnBoolDailyMonitoringChecksSpec deepClone() {
        return (ColumnBoolDailyMonitoringChecksSpec)super.deepClone();
    }
}