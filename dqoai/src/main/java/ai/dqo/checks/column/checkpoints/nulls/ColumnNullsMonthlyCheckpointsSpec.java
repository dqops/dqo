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
package ai.dqo.checks.column.checkpoints.nulls;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsNotNullPercentCheckSpec;
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
public class ColumnNullsMonthlyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsMonthlyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_nulls_count", o -> o.monthlyCheckpointNullsCount);
            put("monthly_checkpoint_nulls_percent", o -> o.monthlyCheckpointNullsPercent);
            put("monthly_checkpoint_nulls_not_null_percent", o -> o.monthlyCheckpointNullsNotNullPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNullsCountCheckSpec monthlyCheckpointNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnNullsPercentCheckSpec monthlyCheckpointNullsPercent;

    @JsonPropertyDescription("Verifies that the percentage of not nulls in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNullsNotNullPercentCheckSpec monthlyCheckpointNullsNotNullPercent;

    /**
     * Returns a nulls count check specification.
     * @return Nulls count check specification.
     */
    public ColumnNullsCountCheckSpec getMonthlyCheckpointNullsCount() {
        return monthlyCheckpointNullsCount;
    }

    /**
     * Sets a new definition of a nulls count check.
     * @param monthlyCheckpointNullsCount Nulls count check specification.
     */
    public void setMonthlyCheckpointNullsCount(ColumnNullsCountCheckSpec monthlyCheckpointNullsCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointNullsCount, monthlyCheckpointNullsCount));
        this.monthlyCheckpointNullsCount = monthlyCheckpointNullsCount;
        propagateHierarchyIdToField(monthlyCheckpointNullsCount, "monthly_checkpoint_nulls_count");
    }

    /**
     * Returns a nulls percent check specification.
     * @return Nulls percent check specification.
     */
    public ColumnNullsPercentCheckSpec getMonthlyCheckpointNullsPercent() {
        return monthlyCheckpointNullsPercent;
    }

    /**
     * Sets a new definition of a nulls percent check.
     * @param monthlyCheckpointNullsPercent Nulls percent check specification.
     */
    public void setMonthlyCheckpointNullsPercent(ColumnNullsPercentCheckSpec monthlyCheckpointNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointNullsPercent, monthlyCheckpointNullsPercent));
        this.monthlyCheckpointNullsPercent = monthlyCheckpointNullsPercent;
        propagateHierarchyIdToField(monthlyCheckpointNullsPercent, "monthly_checkpoint_nulls_percent");
    }

    /**
     * Returns a not nulls percent check specification.
     * @return Not nulls percent check specification.
     */
    public ColumnNullsNotNullPercentCheckSpec getMonthlyCheckpointNullsNotNullPercent() {
        return monthlyCheckpointNullsNotNullPercent;
    }

    /**
     * Sets a new definition of a not nulls percent check.
     * @param monthlyCheckpointNullsNotNullPercent Not nulls percent check specification.
     */
    public void setMonthlyCheckpointNullsNotNullPercent(ColumnNullsNotNullPercentCheckSpec monthlyCheckpointNullsNotNullPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointNullsNotNullPercent, monthlyCheckpointNullsNotNullPercent));
        this.monthlyCheckpointNullsNotNullPercent = monthlyCheckpointNullsNotNullPercent;
        propagateHierarchyIdToField(monthlyCheckpointNullsNotNullPercent, "monthly_checkpoint_nulls_percent");
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
