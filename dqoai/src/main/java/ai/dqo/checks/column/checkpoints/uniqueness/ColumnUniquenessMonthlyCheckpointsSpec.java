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
package ai.dqo.checks.column.checkpoints.uniqueness;

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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessMonthlyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessMonthlyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_unique_count", o -> o.monthlyCheckpointUniqueCount);
            put("monthly_checkpoint_unique_percent", o -> o.monthlyCheckpointUniquePercent);
            put("monthly_checkpoint_duplicate_count", o -> o.monthlyCheckpointDuplicateCount);
            put("monthly_checkpoint_duplicate_percent", o -> o.monthlyCheckpointDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnUniqueCountCheckSpec monthlyCheckpointUniqueCount;

    @JsonPropertyDescription("Verifies that the percentage of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnUniquePercentCheckSpec monthlyCheckpointUniquePercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDuplicateCountCheckSpec monthlyCheckpointDuplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDuplicatePercentCheckSpec monthlyCheckpointDuplicatePercent;

    /**
     * Returns a unique values count check specification.
     * @return Unique values count check specification.
     */
    public ColumnUniqueCountCheckSpec getMonthlyCheckpointUniqueCount() {
        return monthlyCheckpointUniqueCount;
    }

    /**
     * Sets a new specification of a unique values count check.
     * @param monthlyCheckpointUniqueCount Unique values count check specification.
     */
    public void setMonthlyCheckpointUniqueCount(ColumnUniqueCountCheckSpec monthlyCheckpointUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointUniqueCount, monthlyCheckpointUniqueCount));
        this.monthlyCheckpointUniqueCount = monthlyCheckpointUniqueCount;
        propagateHierarchyIdToField(monthlyCheckpointUniqueCount, "monthly_checkpoint_unique_count");
    }

    /**
     * Returns a unique values percent check specification.
     * @return Unique values percent check specification.
     */
    public ColumnUniquePercentCheckSpec getMonthlyCheckpointUniquePercent() {
        return monthlyCheckpointUniquePercent;
    }

    /**
     * Sets a new specification of a unique values percent check.
     * @param monthlyCheckpointUniquePercent Unique values count percent specification.
     */
    public void setMonthlyCheckpointUniquePercent(ColumnUniquePercentCheckSpec monthlyCheckpointUniquePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointUniquePercent, monthlyCheckpointUniquePercent));
        this.monthlyCheckpointUniquePercent = monthlyCheckpointUniquePercent;
        propagateHierarchyIdToField(monthlyCheckpointUniquePercent, "monthly_checkpoint_unique_percent");
    }

    /**
     * Returns a duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getMonthlyCheckpointDuplicateCount() {
        return monthlyCheckpointDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param monthlyCheckpointDuplicateCount Duplicate values count check specification.
     */
    public void setMonthlyCheckpointDuplicateCount(ColumnDuplicateCountCheckSpec monthlyCheckpointDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointDuplicateCount, monthlyCheckpointDuplicateCount));
        this.monthlyCheckpointDuplicateCount = monthlyCheckpointDuplicateCount;
        propagateHierarchyIdToField(monthlyCheckpointDuplicateCount, "monthly_checkpoint_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getMonthlyCheckpointDuplicatePercent() {
        return monthlyCheckpointDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param monthlyCheckpointDuplicatePercent Duplicate values percent check specification.
     */
    public void setMonthlyCheckpointDuplicatePercent(ColumnDuplicatePercentCheckSpec monthlyCheckpointDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointDuplicatePercent, monthlyCheckpointDuplicatePercent));
        this.monthlyCheckpointDuplicatePercent = monthlyCheckpointDuplicatePercent;
        propagateHierarchyIdToField(monthlyCheckpointDuplicatePercent, "monthly_checkpoint_duplicate_percent");
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