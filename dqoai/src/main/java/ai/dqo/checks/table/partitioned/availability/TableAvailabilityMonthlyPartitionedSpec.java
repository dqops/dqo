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
package ai.dqo.checks.table.partitioned.availability;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.availability.TableAvailabilityRowCountCheckSpec;
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
public class TableAvailabilityMonthlyPartitionedSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAvailabilityMonthlyPartitionedSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_availability_row_count", o -> o.monthlyCheckpointAvailabilityRowCount);
        }
    };

    @JsonPropertyDescription("Verifies availability on table in database using simple row count")
    private TableAvailabilityRowCountCheckSpec monthlyCheckpointAvailabilityRowCount;

    /**
     * Returns a check specification.
     * @return New check specification.
     */
    public TableAvailabilityRowCountCheckSpec getMonthlyCheckpointAvailabilityRowCount() {
        return monthlyCheckpointAvailabilityRowCount;
    }

    /**
     * Sets a new check specification.
     * @param monthlyCheckpointAvailabilityRowCount Check specification.
     */
    public void setMonthlyCheckpointAvailabilityRowCount(TableAvailabilityRowCountCheckSpec monthlyCheckpointAvailabilityRowCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointAvailabilityRowCount, monthlyCheckpointAvailabilityRowCount));
        this.monthlyCheckpointAvailabilityRowCount = monthlyCheckpointAvailabilityRowCount;
        propagateHierarchyIdToField(monthlyCheckpointAvailabilityRowCount, "monthly_checkpoint_availability_row_count");
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