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
package com.dqops.checks.table.monitoring.availability;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.table.checkspecs.availability.TableAvailabilityCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a table level that are detecting the table availability.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableAvailabilityDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAvailabilityDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_table_availability", o -> o.dailyTableAvailability);
        }
    };

    @JsonPropertyDescription("Verifies availability on table in database using simple row count. Stores the most recent table availability status for each day when the data quality check was evaluated.")
    private TableAvailabilityCheckSpec dailyTableAvailability;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableAvailabilityCheckSpec getDailyTableAvailability() {
        return dailyTableAvailability;
    }

    /**
     * Sets a new check specification.
     * @param dailyTableAvailability Check specification.
     */
    public void setDailyTableAvailability(TableAvailabilityCheckSpec dailyTableAvailability) {
        this.setDirtyIf(!Objects.equals(this.dailyTableAvailability, dailyTableAvailability));
        this.dailyTableAvailability = dailyTableAvailability;
        propagateHierarchyIdToField(dailyTableAvailability, "daily_table_availability");
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
    public TableAvailabilityDailyMonitoringChecksSpec deepClone() {
        return (TableAvailabilityDailyMonitoringChecksSpec)super.deepClone();
    }
}