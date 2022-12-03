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
import ai.dqo.checks.column.numeric.ColumnMaxNegativeCountCheckSpec;
import ai.dqo.checks.column.numeric.ColumnMaxNegativePercentCheckSpec;
import ai.dqo.checks.column.uniqueness.ColumnMaxDuplicateCountCheckSpec;
import ai.dqo.checks.column.uniqueness.ColumnMaxDuplicatePercentCheckSpec;
import ai.dqo.checks.column.uniqueness.ColumnMinUniqueCountCheckSpec;
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
            put("monthly_checkpoint_min_unique_count", o -> o.monthlyCheckpointMinUniqueCount);
            put("monthly_checkpoint_max_duplicate_count", o -> o.monthlyCheckpointMaxDuplicateCount);
            put("monthly_checkpoint_max_duplicate_percent", o -> o.monthlyCheckpointMaxDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinUniqueCountCheckSpec monthlyCheckpointMinUniqueCount;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxDuplicateCountCheckSpec monthlyCheckpointMaxDuplicateCount;

    @JsonPropertyDescription("Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxDuplicatePercentCheckSpec monthlyCheckpointMaxDuplicatePercent;

    /**
     * Returns a minimum unique values count check.
     * @return Minimum unique values count check.
     */
    public ColumnMinUniqueCountCheckSpec getMonthlyCheckpointMinUniqueCount() {
        return monthlyCheckpointMinUniqueCount;
    }

    /**
     * Sets a new definition of a minimum unique values count check.
     * @param monthlyCheckpointMinUniqueCount Minimum unique values count check.
     */
    public void setMonthlyCheckpointMinUniqueCount(ColumnMinUniqueCountCheckSpec monthlyCheckpointMinUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinUniqueCount, monthlyCheckpointMinUniqueCount));
        this.monthlyCheckpointMinUniqueCount = monthlyCheckpointMinUniqueCount;
        propagateHierarchyIdToField(monthlyCheckpointMinUniqueCount, "monthly_checkpoint_min_unique_count");
    }

    /**
     * Returns a maximum duplicate values count check.
     * @return Maximum duplicate values count check.
     */
    public ColumnMaxDuplicateCountCheckSpec getMonthlyCheckpointMaxDuplicateCount() {
        return monthlyCheckpointMaxDuplicateCount;
    }

    /**
     * Sets a new definition of a maximum duplicate values count check.
     * @param monthlyCheckpointMaxDuplicateCount Maximum duplicate values count check.
     */
    public void setMonthlyCheckpointMaxDuplicateCount(ColumnMaxDuplicateCountCheckSpec monthlyCheckpointMaxDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxDuplicateCount, monthlyCheckpointMaxDuplicateCount));
        this.monthlyCheckpointMaxDuplicateCount = monthlyCheckpointMaxDuplicateCount;
        propagateHierarchyIdToField(monthlyCheckpointMaxDuplicateCount, "monthly_checkpoint_max_duplicate_count");
    }

    /**
     * Returns a maximum duplicate values percent check.
     * @return Maximum duplicate values percent check.
     */
    public ColumnMaxDuplicatePercentCheckSpec getMonthlyCheckpointMaxDuplicatePercent() {
        return monthlyCheckpointMaxDuplicatePercent;
    }

    /**
     * Sets a new definition of a maximum duplicate values percent check.
     * @param monthlyCheckpointMaxDuplicatePercent Maximum duplicate values percent check.
     */
    public void setMonthlyCheckpointMaxDuplicatePercent(ColumnMaxDuplicatePercentCheckSpec monthlyCheckpointMaxDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMaxDuplicatePercent, monthlyCheckpointMaxDuplicatePercent));
        this.monthlyCheckpointMaxDuplicatePercent = monthlyCheckpointMaxDuplicatePercent;
        propagateHierarchyIdToField(monthlyCheckpointMaxDuplicatePercent, "monthly_checkpoint_max_duplicate_percent");
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