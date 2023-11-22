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
package com.dqops.checks.table.partitioned.volume;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.volume.TableChangeRowCountCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.dqops.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
            put("monthly_partition_row_count", o -> o.monthlyPartitionRowCount);
            put("monthly_partition_row_count_change", o -> o.monthlyPartitionRowCountChange);
        }
    };

    @JsonPropertyDescription("Verifies that each monthly partition in the tested table has at least a minimum accepted number of rows. " +
            "The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the partition is not empty. " +
            "When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountCheckSpec monthlyPartitionRowCount;

    @JsonPropertyDescription("Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableChangeRowCountCheckSpec monthlyPartitionRowCountChange;

    /**
     * Returns the minimum row count check configuration.
     * @return Minimum row count check specification.
     */
    public TableRowCountCheckSpec getMonthlyPartitionRowCount() {
        return monthlyPartitionRowCount;
    }

    /**
     * Sets the minimum row count.
     * @param monthlyPartitionRowCount New row count check.
     */
    public void setMonthlyPartitionRowCount(TableRowCountCheckSpec monthlyPartitionRowCount) {
		this.setDirtyIf(!Objects.equals(this.monthlyPartitionRowCount, monthlyPartitionRowCount));
        this.monthlyPartitionRowCount = monthlyPartitionRowCount;
		this.propagateHierarchyIdToField(monthlyPartitionRowCount, "monthly_partition_row_count");
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

    /**
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.table;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.partitioned;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
    }

    public static class TableVolumeMonthlyPartitionedChecksSpecSampleFactory implements SampleValueFactory<TableVolumeMonthlyPartitionedChecksSpec> {
        @Override
        public TableVolumeMonthlyPartitionedChecksSpec createSample() {
            return new TableVolumeMonthlyPartitionedChecksSpec() {{
                setMonthlyPartitionRowCount(new TableRowCountCheckSpec.TableRowCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
