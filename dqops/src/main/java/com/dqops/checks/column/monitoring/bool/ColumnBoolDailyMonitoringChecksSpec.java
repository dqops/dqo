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
 * Container of boolean data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBoolDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBoolDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_true_percent", o -> o.dailyTruePercent);
            put("daily_false_percent", o -> o.dailyFalsePercent);
        }
    };

    @JsonPropertyDescription("Measures the percentage of **true** values in a boolean column and verifies that it is within the accepted range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTruePercentCheckSpec dailyTruePercent;

    @JsonPropertyDescription("Measures the percentage of **false** values in a boolean column and verifies that it is within the accepted range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnFalsePercentCheckSpec dailyFalsePercent;

    /**
     * Returns a true percent check specification.
     * @return True percent check specification.
     */
    public ColumnTruePercentCheckSpec getDailyTruePercent() {
        return dailyTruePercent;
    }

    /**
     * Sets a new definition of a true percent check.
     * @param dailyTruePercent True percent check specification.
     */
    public void setDailyTruePercent(ColumnTruePercentCheckSpec dailyTruePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTruePercent, dailyTruePercent));
        this.dailyTruePercent = dailyTruePercent;
        propagateHierarchyIdToField(dailyTruePercent, "daily_true_percent");
    }

    /**
     * Returns a false percent check specification.
     * @return False percent check specification.
     */
    public ColumnFalsePercentCheckSpec getDailyFalsePercent() {
        return dailyFalsePercent;
    }

    /**
     * Sets a new definition of a false percent check.
     * @param dailyFalsePercent False percent check specification.
     */
    public void setDailyFalsePercent(ColumnFalsePercentCheckSpec dailyFalsePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyFalsePercent, dailyFalsePercent));
        this.dailyFalsePercent = dailyFalsePercent;
        propagateHierarchyIdToField(dailyFalsePercent, "daily_false_percent");
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
    public ColumnBoolDailyMonitoringChecksSpec deepClone() {
        return (ColumnBoolDailyMonitoringChecksSpec)super.deepClone();
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
        return CheckTimeScale.daily;
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