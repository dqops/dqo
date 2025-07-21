/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.table.monitoring.accuracy;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.checkspecs.accuracy.TableAccuracyTotalRowCountMatchPercentCheckSpec;
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
 * Container of built-in preconfigured data quality checks on a table level that are verifying the accuracy of the table, comparing it with another reference table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableAccuracyDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAccuracyDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_total_row_count_match_percent", o -> o.dailyTotalRowCountMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies the total ow count of a tested table and compares it to a row count of a reference table. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private TableAccuracyTotalRowCountMatchPercentCheckSpec dailyTotalRowCountMatchPercent;

    /**
     * Returns a total row count match percent check.
     * @return Total row count match percent check.
     */
    public TableAccuracyTotalRowCountMatchPercentCheckSpec getDailyTotalRowCountMatchPercent() {
        return dailyTotalRowCountMatchPercent;
    }

    /**
     * Sets the reference to a total row count match percent check.
     * @param dailyTotalRowCountMatchPercent Total row count match percent check.
     */
    public void setDailyTotalRowCountMatchPercent(TableAccuracyTotalRowCountMatchPercentCheckSpec dailyTotalRowCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTotalRowCountMatchPercent, dailyTotalRowCountMatchPercent));
        this.dailyTotalRowCountMatchPercent = dailyTotalRowCountMatchPercent;
        propagateHierarchyIdToField(dailyTotalRowCountMatchPercent, "daily_total_row_count_match_percent");
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
        return DataTypeCategory.ANY;
    }
}