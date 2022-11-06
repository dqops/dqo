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
package ai.dqo.checks.table.checkpoints.standard;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.table.checks.standard.TableMinRowCountCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.utils.serialization.IgnoreEmptyYamlSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of table level monthly checkpoints for standard data quality checks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TableStandardMonthlyCheckpointSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableStandardMonthlyCheckpointSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
           put("monthly_checkpoint_min_row_count", o -> o.monthlyCheckpointMinRowCount);
        }
    };

    @JsonPropertyDescription("Monthly checkpoint of the minimum number of rows on a table")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
    private TableMinRowCountCheckSpec monthlyCheckpointMinRowCount;

    /**
     * Returns the minimum row count check configuration.
     * @return Minimum row count check specification.
     */
    public TableMinRowCountCheckSpec getMonthlyCheckpointMinRowCount() {
        return monthlyCheckpointMinRowCount;
    }

    /**
     * Sets the minimum row count.
     * @param monthlyCheckpointMinRowCount New row count check.
     */
    public void setMonthlyCheckpointMinRowCount(TableMinRowCountCheckSpec monthlyCheckpointMinRowCount) {
		this.setDirtyIf(!Objects.equals(this.monthlyCheckpointMinRowCount, monthlyCheckpointMinRowCount));
        this.monthlyCheckpointMinRowCount = monthlyCheckpointMinRowCount;
		this.propagateHierarchyIdToField(monthlyCheckpointMinRowCount, "monthly_checkpoint_min_row_count");
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
