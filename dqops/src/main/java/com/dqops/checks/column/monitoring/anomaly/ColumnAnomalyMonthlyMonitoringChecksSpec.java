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
package com.dqops.checks.column.monitoring.anomaly;

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
public class ColumnAnomalyMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_mean_change", o -> o.monthlyMeanChange);
            put("monthly_median_change", o -> o.monthlyMedianChange);
            put("monthly_sum_change", o -> o.monthlySumChange);
        }
    };

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout.")
    private ColumnChangeMeanCheckSpec monthlyMeanChange;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout.")
    private ColumnChangeMedianCheckSpec monthlyMedianChange;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout.")
    private ColumnChangeSumCheckSpec monthlySumChange;

    /**
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnChangeMeanCheckSpec getMonthlyMeanChange() {
        return monthlyMeanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param monthlyMeanChange Mean value change check.
     */
    public void setMonthlyMeanChange(ColumnChangeMeanCheckSpec monthlyMeanChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyMeanChange, monthlyMeanChange));
        this.monthlyMeanChange = monthlyMeanChange;
        propagateHierarchyIdToField(monthlyMeanChange, "monthly_mean_change");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnChangeMedianCheckSpec getMonthlyMedianChange() {
        return monthlyMedianChange;
    }

    /**
     * Sets a new median change check.
     * @param monthlyMedianChange Median change check.
     */
    public void setMonthlyMedianChange(ColumnChangeMedianCheckSpec monthlyMedianChange) {
        this.setDirtyIf(!Objects.equals(this.monthlyMedianChange, monthlyMedianChange));
        this.monthlyMedianChange = monthlyMedianChange;
        propagateHierarchyIdToField(monthlyMedianChange, "monthly_median_change");
    }

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnChangeSumCheckSpec getMonthlySumChange() {
        return monthlySumChange;
    }

    /**
     * Sets a new sum change check.
     * @param monthlySumChange Sum change check.
     */
    public void setMonthlySumChange(ColumnChangeSumCheckSpec monthlySumChange) {
        this.setDirtyIf(!Objects.equals(this.monthlySumChange, monthlySumChange));
        this.monthlySumChange = monthlySumChange;
        propagateHierarchyIdToField(monthlySumChange, "monthly_sum_change");
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
}
