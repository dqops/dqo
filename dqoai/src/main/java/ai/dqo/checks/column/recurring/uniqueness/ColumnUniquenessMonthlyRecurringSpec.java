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
package ai.dqo.checks.column.recurring.uniqueness;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDuplicateCountCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnDuplicatePercentCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnUniqueCountCheckSpec;
import ai.dqo.checks.column.checkspecs.uniqueness.ColumnUniquePercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of uniqueness data quality recurring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessMonthlyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessMonthlyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_unique_count", o -> o.monthlyUniqueCount);
            put("monthly_unique_percent", o -> o.monthlyUniquePercent);
            put("monthly_duplicate_count", o -> o.monthlyDuplicateCount);
            put("monthly_duplicate_percent", o -> o.monthlyDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnUniqueCountCheckSpec monthlyUniqueCount;

    @JsonPropertyDescription("Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnUniquePercentCheckSpec monthlyUniquePercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDuplicateCountCheckSpec monthlyDuplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDuplicatePercentCheckSpec monthlyDuplicatePercent;

    /**
     * Returns a unique values count check specification.
     * @return Unique values count check specification.
     */
    public ColumnUniqueCountCheckSpec getMonthlyUniqueCount() {
        return monthlyUniqueCount;
    }

    /**
     * Sets a new specification of a unique values count check.
     * @param monthlyUniqueCount Unique values count check specification.
     */
    public void setMonthlyUniqueCount(ColumnUniqueCountCheckSpec monthlyUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyUniqueCount, monthlyUniqueCount));
        this.monthlyUniqueCount = monthlyUniqueCount;
        propagateHierarchyIdToField(monthlyUniqueCount, "monthly_unique_count");
    }

    /**
     * Returns a unique values percent check specification.
     * @return Unique values percent check specification.
     */
    public ColumnUniquePercentCheckSpec getMonthlyUniquePercent() {
        return monthlyUniquePercent;
    }

    /**
     * Sets a new specification of a unique values percent check.
     * @param monthlyUniquePercent Unique values count percent specification.
     */
    public void setMonthlyUniquePercent(ColumnUniquePercentCheckSpec monthlyUniquePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyUniquePercent, monthlyUniquePercent));
        this.monthlyUniquePercent = monthlyUniquePercent;
        propagateHierarchyIdToField(monthlyUniquePercent, "monthly_unique_percent");
    }

    /**
     * Returns a duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getMonthlyDuplicateCount() {
        return monthlyDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param monthlyDuplicateCount Duplicate values count check specification.
     */
    public void setMonthlyDuplicateCount(ColumnDuplicateCountCheckSpec monthlyDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyDuplicateCount, monthlyDuplicateCount));
        this.monthlyDuplicateCount = monthlyDuplicateCount;
        propagateHierarchyIdToField(monthlyDuplicateCount, "monthly_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getMonthlyDuplicatePercent() {
        return monthlyDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param monthlyDuplicatePercent Duplicate values percent check specification.
     */
    public void setMonthlyDuplicatePercent(ColumnDuplicatePercentCheckSpec monthlyDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDuplicatePercent, monthlyDuplicatePercent));
        this.monthlyDuplicatePercent = monthlyDuplicatePercent;
        propagateHierarchyIdToField(monthlyDuplicatePercent, "monthly_duplicate_percent");
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