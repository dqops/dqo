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
package com.dqops.checks.column.recurring.uniqueness;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctCountCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctPercentCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDuplicateCountCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDuplicatePercentCheckSpec;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of uniqueness data quality recurring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_distinct_count", o -> o.dailyDistinctCount);
            put("daily_distinct_percent", o -> o.dailyDistinctPercent);
            put("daily_duplicate_count", o -> o.dailyDuplicateCount);
            put("daily_duplicate_percent", o -> o.dailyDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDistinctCountCheckSpec dailyDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDistinctPercentCheckSpec dailyDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDuplicateCountCheckSpec dailyDuplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDuplicatePercentCheckSpec dailyDuplicatePercent;

    /**
     * Returns a distinct values count check specification.
     * @return Distinct values count check specification.
     */
    public ColumnDistinctCountCheckSpec getDailyDistinctCount() {
        return dailyDistinctCount;
    }

    /**
     * Sets a new specification of a distinct values count check.
     * @param dailyDistinctCount Distinct values count check specification.
     */
    public void setDailyDistinctCount(ColumnDistinctCountCheckSpec dailyDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctCount, dailyDistinctCount));
        this.dailyDistinctCount = dailyDistinctCount;
        propagateHierarchyIdToField(dailyDistinctCount, "daily_distinct_count");
    }

    /**
     * Returns a distinct values percent check specification.
     * @return Distinct values percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getDailyDistinctPercent() {
        return dailyDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct values percent check.
     * @param dailyDistinctPercent Distinct values count percent specification.
     */
    public void setDailyDistinctPercent(ColumnDistinctPercentCheckSpec dailyDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctPercent, dailyDistinctPercent));
        this.dailyDistinctPercent = dailyDistinctPercent;
        propagateHierarchyIdToField(dailyDistinctPercent, "daily_distinct_percent");
    }

    /**
     * Returns a  duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getDailyDuplicateCount() {
        return dailyDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param dailyDuplicateCount Duplicate values count check specification.
     */
    public void setDailyDuplicateCount(ColumnDuplicateCountCheckSpec dailyDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.dailyDuplicateCount, dailyDuplicateCount));
        this.dailyDuplicateCount = dailyDuplicateCount;
        propagateHierarchyIdToField(dailyDuplicateCount, "daily_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getDailyDuplicatePercent() {
        return dailyDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param dailyDuplicatePercent Duplicate values percent check specification.
     */
    public void setDailyDuplicatePercent(ColumnDuplicatePercentCheckSpec dailyDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyDuplicatePercent, dailyDuplicatePercent));
        this.dailyDuplicatePercent = dailyDuplicatePercent;
        propagateHierarchyIdToField(dailyDuplicatePercent, "daily_duplicate_percent");
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
    public ColumnUniquenessDailyRecurringChecksSpec deepClone() {
        return (ColumnUniquenessDailyRecurringChecksSpec)super.deepClone();
    }
}