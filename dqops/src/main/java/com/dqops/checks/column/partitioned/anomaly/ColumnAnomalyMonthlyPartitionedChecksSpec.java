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
import com.dqops.checks.column.checkspecs.anomaly.ColumnChangeMeanCheckSpec;
import com.dqops.checks.column.checkspecs.anomaly.ColumnChangeMedianCheckSpec;
import com.dqops.checks.column.checkspecs.anomaly.ColumnChangeSumCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
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

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout.")
    private ColumnChangeMeanCheckSpec monthlyPartitionMeanChange;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout.")
    private ColumnChangeMedianCheckSpec monthlyPartitionMedianChange;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout.")
    private ColumnChangeSumCheckSpec monthlyPartitionSumChange;

    /**
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnChangeMeanCheckSpec getMonthlyPartitionMeanChange() {
        return monthlyPartitionMeanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param monthlyPartitionMeanChange Mean value change check.
     */
    public void setMonthlyPartitionMeanChange(ColumnChangeMeanCheckSpec monthlyPartitionMeanChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMeanChange, monthlyPartitionMeanChange));
        this.monthlyPartitionMeanChange = monthlyPartitionMeanChange;
        propagateHierarchyIdToField(monthlyPartitionMeanChange, "monthly_partition_mean_change");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnChangeMedianCheckSpec getMonthlyPartitionMedianChange() {
        return monthlyPartitionMedianChange;
    }

    /**
     * Sets a new median change check.
     * @param monthlyPartitionMedianChange Median change check.
     */
    public void setMonthlyPartitionMedianChange(ColumnChangeMedianCheckSpec monthlyPartitionMedianChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMedianChange, monthlyPartitionMedianChange));
        this.monthlyPartitionMedianChange = monthlyPartitionMedianChange;
        propagateHierarchyIdToField(monthlyPartitionMedianChange, "monthly_partition_median_change");
    }

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnChangeSumCheckSpec getMonthlyPartitionSumChange() {
        return monthlyPartitionSumChange;
    }

    /**
     * Sets a new sum change check.
     * @param monthlyPartitionSumChange Sum change check.
     */
    public void setMonthlyPartitionSumChange(ColumnChangeSumCheckSpec monthlyPartitionSumChange) {
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
}
