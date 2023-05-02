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
package ai.dqo.checks.table.recurring.availability;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.availability.TableAvailabilityCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableAvailabilityMonthlyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAvailabilityMonthlyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_table_availability", o -> o.monthlyTableAvailability);
        }
    };

    @JsonPropertyDescription("Verifies availability on table in database using simple row count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private TableAvailabilityCheckSpec monthlyTableAvailability;


    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableAvailabilityCheckSpec getMonthlyTableAvailability() {
        return monthlyTableAvailability;
    }

    /**
     * Sets a new check specification.
     * @param monthlyTableAvailability Check specification.
     */
    public void setMonthlyTableAvailability(TableAvailabilityCheckSpec monthlyTableAvailability) {
        this.setDirtyIf(!Objects.equals(this.monthlyTableAvailability, monthlyTableAvailability));
        this.monthlyTableAvailability = monthlyTableAvailability;
        propagateHierarchyIdToField(monthlyTableAvailability, "monthly_table_availability");
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