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
package com.dqops.checks.column.partitioned.anomaly;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.anomaly.ColumnMeanChangeCheckSpec;
import com.dqops.checks.column.checkspecs.anomaly.ColumnMedianChangeCheckSpec;
import com.dqops.checks.column.checkspecs.anomaly.ColumnSumChangeCheckSpec;
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
public class ColumnAnomalyMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_mean_change", o -> o.monthlyPartitionMeanChange);
            put("monthly_partition_median_change", o -> o.monthlyPartitionMedianChange);
            put("monthly_partition_sum_change", o -> o.monthlyPartitionSumChange);
        }
    };

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since the last readout.")
    private ColumnMeanChangeCheckSpec monthlyPartitionMeanChange;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since the last readout.")
    private ColumnMedianChangeCheckSpec monthlyPartitionMedianChange;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since the last readout.")
    private ColumnSumChangeCheckSpec monthlyPartitionSumChange;

    /**
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnMeanChangeCheckSpec getMonthlyPartitionMeanChange() {
        return monthlyPartitionMeanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param monthlyPartitionMeanChange Mean value change check.
     */
    public void setMonthlyPartitionMeanChange(ColumnMeanChangeCheckSpec monthlyPartitionMeanChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMeanChange, monthlyPartitionMeanChange));
        this.monthlyPartitionMeanChange = monthlyPartitionMeanChange;
        propagateHierarchyIdToField(monthlyPartitionMeanChange, "monthly_partition_mean_change");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnMedianChangeCheckSpec getMonthlyPartitionMedianChange() {
        return monthlyPartitionMedianChange;
    }

    /**
     * Sets a new median change check.
     * @param monthlyPartitionMedianChange Median change check.
     */
    public void setMonthlyPartitionMedianChange(ColumnMedianChangeCheckSpec monthlyPartitionMedianChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMedianChange, monthlyPartitionMedianChange));
        this.monthlyPartitionMedianChange = monthlyPartitionMedianChange;
        propagateHierarchyIdToField(monthlyPartitionMedianChange, "monthly_partition_median_change");
    }

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnSumChangeCheckSpec getMonthlyPartitionSumChange() {
        return monthlyPartitionSumChange;
    }

    /**
     * Sets a new sum change check.
     * @param monthlyPartitionSumChange Sum change check.
     */
    public void setMonthlyPartitionSumChange(ColumnSumChangeCheckSpec monthlyPartitionSumChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSumChange, monthlyPartitionSumChange));
        this.monthlyPartitionSumChange = monthlyPartitionSumChange;
        propagateHierarchyIdToField(monthlyPartitionSumChange, "monthly_partition_sum_change");
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
}
