/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.partitioned.bool;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.bool.ColumnFalsePercentCheckSpec;
import com.dqops.checks.column.checkspecs.bool.ColumnTruePercentCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of boolean data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBoolMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBoolMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_true_percent", o -> o.monthlyPartitionTruePercent);
            put("monthly_partition_false_percent", o -> o.monthlyPartitionFalsePercent);
        }
    };

    @JsonPropertyDescription("Measures the percentage of **true** values in a boolean column and verifies that it is within the accepted range. Stores a separate data quality check result for each monthly partition.")
    private ColumnTruePercentCheckSpec monthlyPartitionTruePercent;

    @JsonPropertyDescription("Measures the percentage of **false** values in a boolean column and verifies that it is within the accepted range. Stores a separate data quality check result for each monthly partition.")
    private ColumnFalsePercentCheckSpec monthlyPartitionFalsePercent;

    /**
     * Returns a true check.
     * @return True check.
     */
    public ColumnTruePercentCheckSpec getMonthlyPartitionTruePercent() {
        return monthlyPartitionTruePercent;
    }

    /**
     * Sets a new definition of a true check.
     * @param monthlyPartitionTruePercent True check.
     */
    public void setMonthlyPartitionTruePercent(ColumnTruePercentCheckSpec monthlyPartitionTruePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTruePercent, monthlyPartitionTruePercent));
        this.monthlyPartitionTruePercent = monthlyPartitionTruePercent;
        propagateHierarchyIdToField(monthlyPartitionTruePercent, "monthly_partition_true_percent");
    }

    /**
     * Returns a false check.
     * @return False check.
     */
    public ColumnFalsePercentCheckSpec getMonthlyPartitionFalsePercent() {
        return monthlyPartitionFalsePercent;
    }

    /**
     * Sets a new definition of a false check.
     * @param monthlyPartitionFalsePercent False check.
     */
    public void setMonthlyPartitionFalsePercent(ColumnFalsePercentCheckSpec monthlyPartitionFalsePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionFalsePercent, monthlyPartitionFalsePercent));
        this.monthlyPartitionFalsePercent = monthlyPartitionFalsePercent;
        propagateHierarchyIdToField(monthlyPartitionFalsePercent, "monthly_partition_false_percent");
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
        return CheckTarget.column;
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
        return DataTypeCategory.BOOLEAN;
    }
}
