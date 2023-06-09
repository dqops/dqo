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
package ai.dqo.checks.table.partitioned.volume;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.volume.TableChangeRowCountCheckSpec;
import ai.dqo.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of table level monthly partitioned volume data quality checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableVolumeMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableVolumeMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_min_row_count", o -> o.monthlyPartitionMinRowCount);
            put("monthly_partition_row_count_change", o -> o.monthlyPartitionRowCountChange);
        }
    };

    @JsonPropertyDescription("Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountCheckSpec monthlyPartitionMinRowCount;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableChangeRowCountCheckSpec monthlyPartitionRowCountChange;

    /**
     * Returns the minimum row count check configuration.
     * @return Minimum row count check specification.
     */
    public TableRowCountCheckSpec getMonthlyPartitionMinRowCount() {
        return monthlyPartitionMinRowCount;
    }

    /**
     * Sets the minimum row count.
     * @param monthlyPartitionMinRowCount New row count check.
     */
    public void setMonthlyPartitionMinRowCount(TableRowCountCheckSpec monthlyPartitionMinRowCount) {
		this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinRowCount, monthlyPartitionMinRowCount));
        this.monthlyPartitionMinRowCount = monthlyPartitionMinRowCount;
		this.propagateHierarchyIdToField(monthlyPartitionMinRowCount, "monthly_partition_min_row_count");
    }

    /**
     * Returns the row count change check.
     * @return Row count change check.
     */
    public TableChangeRowCountCheckSpec getMonthlyPartitionRowCountChange() {
        return monthlyPartitionRowCountChange;
    }

    /**
     * Sets a new row count change check.
     * @param monthlyPartitionRowCountChange Row count change check.
     */
    public void setMonthlyPartitionRowCountChange(TableChangeRowCountCheckSpec monthlyPartitionRowCountChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionRowCountChange, monthlyPartitionRowCountChange));
        this.monthlyPartitionRowCountChange = monthlyPartitionRowCountChange;
        propagateHierarchyIdToField(monthlyPartitionRowCountChange, "monthly_partition_row_count_change");
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
