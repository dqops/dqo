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
package com.dqops.checks.table.recurring.accuracy;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.table.checkspecs.accuracy.TableAccuracyTotalRowCountMatchPercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a table level that are verifying the accuracy of the table, comparing it with another reference table, on a monthly basis.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableAccuracyMonthlyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAccuracyMonthlyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_total_row_count_match_percent", o -> o.monthlyTotalRowCountMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies the total row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.")
    private TableAccuracyTotalRowCountMatchPercentCheckSpec monthlyTotalRowCountMatchPercent;

    /**
     * Returns a total row count match percent check.
     * @return Total row count match percent check.
     */
    public TableAccuracyTotalRowCountMatchPercentCheckSpec getMonthlyTotalRowCountMatchPercent() {
        return monthlyTotalRowCountMatchPercent;
    }

    /**
     * Sets the reference to a total row count match percent check.
     * @param monthlyTotalRowCountMatchPercent Total row count match percent check.
     */
    public void setMonthlyTotalRowCountMatchPercent(TableAccuracyTotalRowCountMatchPercentCheckSpec monthlyTotalRowCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTotalRowCountMatchPercent, monthlyTotalRowCountMatchPercent));
        this.monthlyTotalRowCountMatchPercent = monthlyTotalRowCountMatchPercent;
        propagateHierarchyIdToField(monthlyTotalRowCountMatchPercent, "monthly_total_row_count_match_percent");
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