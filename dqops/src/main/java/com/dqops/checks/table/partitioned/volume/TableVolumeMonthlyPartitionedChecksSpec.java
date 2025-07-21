/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.partitioned.volume;

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
            "The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the partition is not empty.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountCheckSpec monthlyPartitionRowCount;

    @JsonPropertyDescription("Detects when the partition's volume (row count) change between the current monthly partition and the previous partition exceeds the maximum accepted change percentage.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableRowCountChangeCheckSpec monthlyPartitionRowCountChange;

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
    public TableRowCountChangeCheckSpec getMonthlyPartitionRowCountChange() {
        return monthlyPartitionRowCountChange;
    }

    /**
     * Sets a new row count change check.
     * @param monthlyPartitionRowCountChange Row count change check.
     */
    public void setMonthlyPartitionRowCountChange(TableRowCountChangeCheckSpec monthlyPartitionRowCountChange) {
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

    public static class TableVolumeMonthlyPartitionedChecksSpecSampleFactory implements SampleValueFactory<TableVolumeMonthlyPartitionedChecksSpec> {
        @Override
        public TableVolumeMonthlyPartitionedChecksSpec createSample() {
            return new TableVolumeMonthlyPartitionedChecksSpec() {{
                setMonthlyPartitionRowCount(new TableRowCountCheckSpec.TableRowCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
