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
package ai.dqo.checks.column.partitioned.accuracy;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.accuracy.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAccuracyMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAccuracyMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_accuracy_value_match_count", o -> o.monthlyPartitionAccuracyValueMatchCount);

        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnAccuracyValueMatchCountCheckSpec monthlyPartitionAccuracyValueMatchCount;

    /**
     * Returns an accuracy value match count check.
     * @return Accuracy value match count check.
     */
    public ColumnAccuracyValueMatchCountCheckSpec getMonthlyPartitionAccuracyValueMatchCount() {
        return monthlyPartitionAccuracyValueMatchCount;
    }

    /**
     * Sets a new definition of an accuracy value match count check.
     * @param monthlyPartitionAccuracyValueMatchCount Accuracy value match count check.
     */
    public void setMonthlyPartitionAccuracyValueMatchCount(ColumnAccuracyValueMatchCountCheckSpec monthlyPartitionAccuracyValueMatchCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionAccuracyValueMatchCount, monthlyPartitionAccuracyValueMatchCount));
        this.monthlyPartitionAccuracyValueMatchCount = monthlyPartitionAccuracyValueMatchCount;
        propagateHierarchyIdToField(monthlyPartitionAccuracyValueMatchCount, "monthly_partition_accuracy_value_match_count");
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
