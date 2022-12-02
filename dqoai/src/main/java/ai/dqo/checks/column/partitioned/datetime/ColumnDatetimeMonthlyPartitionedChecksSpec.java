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
package ai.dqo.checks.column.partitioned.datetime;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.datetime.ColumnMaxDatetimeValuesInFuturePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_max_datetime_values_in_future_percent", o -> o.monthlyPartitionMaxDatetimeValuesInFuturePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of datetime values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnMaxDatetimeValuesInFuturePercentCheckSpec monthlyPartitionMaxDatetimeValuesInFuturePercent;

    /**
     * Returns a maximum datetime values in future percentage check.
     * @return Maximum datetime values in future percentage check.
     */
    public ColumnMaxDatetimeValuesInFuturePercentCheckSpec getMonthlyPartitionMaxDatetimeValuesInFuturePercent() {
        return monthlyPartitionMaxDatetimeValuesInFuturePercent;
    }

    /**
     * Sets a new definition of a maximum datetime values in future percentage check.
     * @param monthlyPartitionMaxDatetimeValuesInFuturePercent Maximum datetime values in future percentage check.
     */
    public void setMonthlyPartitionMaxDatetimeValuesInFuturePercent(ColumnMaxDatetimeValuesInFuturePercentCheckSpec monthlyPartitionMaxDatetimeValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxDatetimeValuesInFuturePercent, monthlyPartitionMaxDatetimeValuesInFuturePercent));
        this.monthlyPartitionMaxDatetimeValuesInFuturePercent = monthlyPartitionMaxDatetimeValuesInFuturePercent;
        propagateHierarchyIdToField(monthlyPartitionMaxDatetimeValuesInFuturePercent, "monthly_partition_max_datetime_values_in_future_percent");
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
