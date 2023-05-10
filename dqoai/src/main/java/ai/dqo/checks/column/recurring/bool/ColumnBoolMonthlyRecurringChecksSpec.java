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
package ai.dqo.checks.column.recurring.bool;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.bool.ColumnFalsePercentCheckSpec;
import ai.dqo.checks.column.checkspecs.bool.ColumnTruePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of boolean recurring data quality checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBoolMonthlyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBoolMonthlyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_true_percent", o -> o.monthlyTruePercent);
            put("monthly_false_percent", o -> o.monthlyFalsePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnTruePercentCheckSpec monthlyTruePercent;

    @JsonPropertyDescription("Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
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
}