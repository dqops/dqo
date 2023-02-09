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
package ai.dqo.checks.column.checkpoints.accuracy;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.accuracy.ColumnAccuracyValueMatchCountCheckSpec;
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
public class ColumnAccuracyMonthlyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAccuracyMonthlyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_accuracy_value_match_count", o -> o.monthlyCheckpointAccuracyValueMatchCount);

        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnAccuracyValueMatchCountCheckSpec monthlyCheckpointAccuracyValueMatchCount;

    /**
     * Returns an accuracy value match count check specification.
     * @return Accuracy value match count check specification.
     */
    public ColumnAccuracyValueMatchCountCheckSpec getMonthlyCheckpointAccuracyValueMatchCount() {
        return monthlyCheckpointAccuracyValueMatchCount;
    }

    /**
     * Sets a new definition of an accuracy value match count check.
     * @param monthlyCheckpointAccuracyValueMatchCount Accuracy value match count check specification.
     */
    public void setMonthlyCheckpointAccuracyValueMatchCount(ColumnAccuracyValueMatchCountCheckSpec monthlyCheckpointAccuracyValueMatchCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointAccuracyValueMatchCount, monthlyCheckpointAccuracyValueMatchCount));
        this.monthlyCheckpointAccuracyValueMatchCount = monthlyCheckpointAccuracyValueMatchCount;
        propagateHierarchyIdToField(monthlyCheckpointAccuracyValueMatchCount, "monthly_checkpoint_accuracy_value_match_count");
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
