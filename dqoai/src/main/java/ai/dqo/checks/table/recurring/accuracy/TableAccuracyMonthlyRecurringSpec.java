/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.checks.table.recurring.accuracy;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checkspecs.accuracy.TableAccuracyRowCountMatchPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class TableAccuracyMonthlyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableAccuracyMonthlyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_row_count_match_percent", o -> o.monthlyRowCountMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies the row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.")
    private TableAccuracyRowCountMatchPercentCheckSpec monthlyRowCountMatchPercent;

    /**
     * Returns a row count match percent check.
     * @return Row count match percent check.
     */
    public TableAccuracyRowCountMatchPercentCheckSpec getMonthlyRowCountMatchPercent() {
        return monthlyRowCountMatchPercent;
    }

    /**
     * Sets the reference to a row count match percent check.
     * @param monthlyRowCountMatchPercent Row count match percent check.
     */
    public void setMonthlyRowCountMatchPercent(TableAccuracyRowCountMatchPercentCheckSpec monthlyRowCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyRowCountMatchPercent, monthlyRowCountMatchPercent));
        this.monthlyRowCountMatchPercent = monthlyRowCountMatchPercent;
        propagateHierarchyIdToField(monthlyRowCountMatchPercent, "monthly_row_count_match_percent");
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