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
package ai.dqo.checks.column.adhoc;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checks.nulls.ColumnMaxNullsCountCheckSpec;
import ai.dqo.checks.column.checks.nulls.ColumnMaxNullsPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for nulls.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocNullsChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocNullsChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("max_nulls_count", o -> o.maxNullsCount);
            put("max_nulls_percent", o -> o.maxNullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of nulls in a column does not exceed the maximum accepted count.")
    private ColumnMaxNullsCountCheckSpec maxNullsCount;

    @JsonPropertyDescription("Verifies that the percent of nulls in a column does not exceed the maximum accepted percentage.")
    private ColumnMaxNullsPercentCheckSpec maxNullsPercent;

    /**
     * Returns a maximum nulls count check.
     * @return Maximum nulls count check.
     */
    public ColumnMaxNullsCountCheckSpec getMaxNullsCount() {
        return maxNullsCount;
    }

    /**
     * Returns a maximum nulls percent check.
     * @return Maximum nulls percent check.
     */
    public ColumnMaxNullsPercentCheckSpec getMaxNullsPercent() {
        return maxNullsPercent;
    }

    /**
     * Sets a new definition of a maximum nulls count check.
     * @param maxNullsCount Maximum nulls count check.
     */
    public void setMaxNullsCount(ColumnMaxNullsCountCheckSpec maxNullsCount) {
        this.setDirtyIf(!Objects.equals(this.maxNullsCount, maxNullsCount));
        this.maxNullsCount = maxNullsCount;
        propagateHierarchyIdToField(maxNullsCount, "max_nulls_count");
    }

    /**
     * Sets a new definition of a maximum nulls percent check.
     * @param maxNullsPercent Maximum nulls percent check.
     */
    public void setMaxNullsPercent(ColumnMaxNullsPercentCheckSpec maxNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.maxNullsPercent, maxNullsPercent));
        this.maxNullsPercent = maxNullsPercent;
        propagateHierarchyIdToField(maxNullsPercent, "max_nulls_percent");
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
