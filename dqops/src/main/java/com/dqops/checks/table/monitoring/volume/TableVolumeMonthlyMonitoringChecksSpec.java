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
package com.dqops.checks.table.monitoring.volume;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.volume.TableRowCountChangeCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.connectors.DataTypeCategory;
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
 * Container of table level monthly monitoring for volume data quality checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableVolumeMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableVolumeMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_row_count", o -> o.monthlyRowCount);
            put("monthly_row_count_change", o -> o.monthlyRowCountChange);
        }
    };

    @JsonPropertyDescription("Verifies that the tested table has at least a minimum accepted number of rows. " +
            "The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the table is not empty. " +
            "Stores the most recent captured row count value for each month when the row count was evaluated.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountCheckSpec monthlyRowCount;

    @JsonPropertyDescription("Detects when the volume (row count) changes since the last known row count from a previous month exceeds the maximum accepted change percentage.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountChangeCheckSpec monthlyRowCountChange;

    /**
     * Returns the row count check configuration.
     * @return Row count check specification.
     */
    public TableRowCountCheckSpec getMonthlyRowCount() {
        return monthlyRowCount;
    }

    /**
     * Sets the row count.
     * @param monthlyRowCount New row count check.
     */
    public void setMonthlyRowCount(TableRowCountCheckSpec monthlyRowCount) {
		this.setDirtyIf(!Objects.equals(this.monthlyRowCount, monthlyRowCount));
        this.monthlyRowCount = monthlyRowCount;
		this.propagateHierarchyIdToField(monthlyRowCount, "monthly_row_count");
    }

    /**
     * Returns the row count change check.
     * @return Row count change check.
     */
    public TableRowCountChangeCheckSpec getMonthlyRowCountChange() {
        return monthlyRowCountChange;
    }

    /**
     * Sets a new row count change check.
     * @param monthlyRowCountChange Row count change check.
     */
    public void setMonthlyRowCountChange(TableRowCountChangeCheckSpec monthlyRowCountChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyRowCountChange, monthlyRowCountChange));
        this.monthlyRowCountChange = monthlyRowCountChange;
        propagateHierarchyIdToField(monthlyRowCountChange, "monthly_row_count_change");
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
    public TableVolumeMonthlyMonitoringChecksSpec deepClone() {
        return (TableVolumeMonthlyMonitoringChecksSpec)super.deepClone();
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
        return CheckType.monitoring;
    }

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
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

    public static class TableVolumeMonthlyMonitoringChecksSpecSampleFactory implements SampleValueFactory<TableVolumeMonthlyMonitoringChecksSpec> {
        @Override
        public TableVolumeMonthlyMonitoringChecksSpec createSample() {
            return new TableVolumeMonthlyMonitoringChecksSpec() {{
                setMonthlyRowCount(new TableRowCountCheckSpec.TableRowCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
