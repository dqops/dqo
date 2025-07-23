/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.monitoring.anomaly;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.anomaly.ColumnMeanChangeCheckSpec;
import com.dqops.checks.column.checkspecs.anomaly.ColumnMedianChangeCheckSpec;
import com.dqops.checks.column.checkspecs.anomaly.ColumnSumChangeCheckSpec;
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
 * Container of built-in preconfigured data quality checks on a column level for detecting anomalies.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAnomalyMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_sum_change", o -> o.monthlySumChange);
            put("monthly_mean_change", o -> o.monthlyMeanChange);
            put("monthly_median_change", o -> o.monthlyMedianChange);
        }
    };

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since the last readout.")
    private ColumnSumChangeCheckSpec monthlySumChange;

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since the last readout.")
    private ColumnMeanChangeCheckSpec monthlyMeanChange;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since the last readout.")
    private ColumnMedianChangeCheckSpec monthlyMedianChange;

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnSumChangeCheckSpec getMonthlySumChange() {
        return monthlySumChange;
    }

    /**
     * Sets a new sum change check.
     * @param monthlySumChange Sum change check.
     */
    public void setMonthlySumChange(ColumnSumChangeCheckSpec monthlySumChange) {
        this.setDirtyIf(!Objects.equals(this.monthlySumChange, monthlySumChange));
        this.monthlySumChange = monthlySumChange;
        propagateHierarchyIdToField(monthlySumChange, "monthly_sum_change");
    }

    /**
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnMeanChangeCheckSpec getMonthlyMeanChange() {
        return monthlyMeanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param monthlyMeanChange Mean value change check.
     */
    public void setMonthlyMeanChange(ColumnMeanChangeCheckSpec monthlyMeanChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyMeanChange, monthlyMeanChange));
        this.monthlyMeanChange = monthlyMeanChange;
        propagateHierarchyIdToField(monthlyMeanChange, "monthly_mean_change");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnMedianChangeCheckSpec getMonthlyMedianChange() {
        return monthlyMedianChange;
    }

    /**
     * Sets a new median change check.
     * @param monthlyMedianChange Median change check.
     */
    public void setMonthlyMedianChange(ColumnMedianChangeCheckSpec monthlyMedianChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyMedianChange, monthlyMedianChange));
        this.monthlyMedianChange = monthlyMedianChange;
        propagateHierarchyIdToField(monthlyMedianChange, "monthly_median_change");
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
    public ColumnAnomalyMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnAnomalyMonthlyMonitoringChecksSpec)super.deepClone();
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
        return DataTypeCategory.NUMERIC;
    }
}
