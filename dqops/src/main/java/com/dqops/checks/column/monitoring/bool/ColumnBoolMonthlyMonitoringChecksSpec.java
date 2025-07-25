/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.monitoring.bool;

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
 * Container of boolean monitoring data quality checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBoolMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBoolMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_true_percent", o -> o.monthlyTruePercent);
            put("monthly_false_percent", o -> o.monthlyFalsePercent);
        }
    };

    @JsonPropertyDescription("Measures the percentage of **true** values in a boolean column and verifies that it is within the accepted range. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnTruePercentCheckSpec monthlyTruePercent;

    @JsonPropertyDescription("Measures the percentage of **false** values in a boolean column and verifies that it is within the accepted range. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnFalsePercentCheckSpec monthlyFalsePercent;

    /**
     * Returns a true percent check specification.
     * @return True percent check specification.
     */
    public ColumnTruePercentCheckSpec getMonthlyTruePercent() {
        return monthlyTruePercent;
    }

    /**
     * Sets a new definition of a true percent check.
     * @param monthlyTruePercent True percent check specification.
     */
    public void setMonthlyTruePercent(ColumnTruePercentCheckSpec monthlyTruePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTruePercent, monthlyTruePercent));
        this.monthlyTruePercent = monthlyTruePercent;
        propagateHierarchyIdToField(monthlyTruePercent, "monthly_true_percent");
    }

    /**
     * Returns a false percent check specification.
     * @return False percent check specification.
     */
    public ColumnFalsePercentCheckSpec getMonthlyFalsePercent() {
        return monthlyFalsePercent;
    }

    /**
     * Sets a new definition of a false percent check.
     * @param monthlyFalsePercent False percent check specification.
     */
    public void setMonthlyFalsePercent(ColumnFalsePercentCheckSpec monthlyFalsePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyFalsePercent, monthlyFalsePercent));
        this.monthlyFalsePercent = monthlyFalsePercent;
        propagateHierarchyIdToField(monthlyFalsePercent, "monthly_false_percent");
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
    public ColumnBoolMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnBoolMonthlyMonitoringChecksSpec)super.deepClone();
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
        return CheckType.monitoring;
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