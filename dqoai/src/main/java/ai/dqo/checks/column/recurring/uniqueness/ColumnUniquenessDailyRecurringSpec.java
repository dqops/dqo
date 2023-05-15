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
 * Container of uniqueness data quality recurring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessDailyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessDailyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_unique_count", o -> o.dailyUniqueCount);
            put("daily_unique_percent", o -> o.dailyUniquePercent);
            put("daily_duplicate_count", o -> o.dailyDuplicateCount);
            put("daily_duplicate_percent", o -> o.dailyDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not fall below the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnUniqueCountCheckSpec dailyUniqueCount;

    @JsonPropertyDescription("Verifies that the percentage of unique values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnUniquePercentCheckSpec dailyUniquePercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnDuplicateCountCheckSpec dailyDuplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnDuplicatePercentCheckSpec dailyDuplicatePercent;

    /**
     * Returns a unique values count check specification.
     * @return Unique values count check specification.
     */
    public ColumnUniqueCountCheckSpec getDailyUniqueCount() {
        return dailyUniqueCount;
    }

    /**
     * Sets a new specification of a unique values count check.
     * @param dailyUniqueCount Unique values count check specification.
     */
    public void setDailyUniqueCount(ColumnUniqueCountCheckSpec dailyUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.dailyUniqueCount, dailyUniqueCount));
        this.dailyUniqueCount = dailyUniqueCount;
        propagateHierarchyIdToField(dailyUniqueCount, "daily_unique_count");
    }

    /**
     * Returns a unique values percent check specification.
     * @return Unique values percent check specification.
     */
    public ColumnUniquePercentCheckSpec getDailyUniquePercent() {
        return dailyUniquePercent;
    }

    /**
     * Sets a new specification of a unique values percent check.
     * @param dailyUniquePercent Unique values count percent specification.
     */
    public void setDailyUniquePercent(ColumnUniquePercentCheckSpec dailyUniquePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyUniquePercent, dailyUniquePercent));
        this.dailyUniquePercent = dailyUniquePercent;
        propagateHierarchyIdToField(dailyUniquePercent, "daily_unique_percent");
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
}