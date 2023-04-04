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
package ai.dqo.checks.column.recurring.nulls;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNotNullsCountCheckSpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNotNullsPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsMonthlyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsMonthlyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_nulls_count", o -> o.monthlyNullsCount);
            put("monthly_nulls_percent", o -> o.monthlyNullsPercent);
            put("monthly_not_nulls_count", o -> o.monthlyNotNullsCount);
            put("monthly_not_nulls_percent", o -> o.monthlyNotNullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNullsCountCheckSpec monthlyNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNullsPercentCheckSpec monthlyNullsPercent;

    @JsonPropertyDescription("Verifies that the number of not null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNotNullsCountCheckSpec monthlyNotNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of not nulls in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNotNullsPercentCheckSpec monthlyNotNullsPercent;

    /**
     * Returns a nulls count check specification.
     * @return Nulls count check specification.
     */
    public ColumnNullsCountCheckSpec getMonthlyNullsCount() {
        return monthlyNullsCount;
    }

    /**
     * Sets a new definition of a nulls count check.
     * @param monthlyNullsCount Nulls count check specification.
     */
    public void setMonthlyNullsCount(ColumnNullsCountCheckSpec monthlyNullsCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyNullsCount, monthlyNullsCount));
        this.monthlyNullsCount = monthlyNullsCount;
        propagateHierarchyIdToField(monthlyNullsCount, "monthly_nulls_count");
    }

    /**
     * Returns a nulls percent check specification.
     * @return Nulls percent check specification.
     */
    public ColumnNullsPercentCheckSpec getMonthlyNullsPercent() {
        return monthlyNullsPercent;
    }

    /**
     * Sets a new definition of a nulls percent check.
     * @param monthlyNullsPercent Nulls percent check specification.
     */
    public void setMonthlyNullsPercent(ColumnNullsPercentCheckSpec monthlyNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNullsPercent, monthlyNullsPercent));
        this.monthlyNullsPercent = monthlyNullsPercent;
        propagateHierarchyIdToField(monthlyNullsPercent, "monthly_nulls_percent");
    }

    /**
     * Returns not nulls count check specification.
     * @return Not nulls count check specification.
     */
    public ColumnNotNullsCountCheckSpec getMonthlyNotNullsCount() {
        return monthlyNotNullsCount;
    }

    /**
     * Sets a new definition of a not nulls count check.
     * @param monthlyNotNullsCount Not nulls count check specification.
     */
    public void setMonthlyNotNullsCount(ColumnNotNullsCountCheckSpec monthlyNotNullsCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyNotNullsCount, monthlyNotNullsCount));
        this.monthlyNotNullsCount = monthlyNotNullsCount;
        propagateHierarchyIdToField(monthlyNotNullsCount, "monthly_not_nulls_count");
    }

    /**
     * Returns a not nulls percent check specification.
     * @return Not nulls percent check specification.
     */
    public ColumnNotNullsPercentCheckSpec getMonthlyNotNullsPercent() {
        return monthlyNotNullsPercent;
    }

    /**
     * Sets a new definition of a not nulls percent check.
     * @param monthlyNotNullsPercent Not nulls percent check specification.
     */
    public void setMonthlyNotNullsPercent(ColumnNotNullsPercentCheckSpec monthlyNotNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNotNullsPercent, monthlyNotNullsPercent));
        this.monthlyNotNullsPercent = monthlyNotNullsPercent;
        propagateHierarchyIdToField(monthlyNotNullsPercent, "monthly_not_nulls_percent");
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
