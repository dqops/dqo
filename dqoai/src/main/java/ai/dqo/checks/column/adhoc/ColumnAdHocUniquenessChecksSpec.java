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
 * Container of built-in preconfigured data quality checks on a column level that are checking for negative values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocUniquenessChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocUniquenessChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("min_unique_count", o -> o.minUniqueCount);
        }
    };

    @JsonPropertyDescription("Verifies that the number of unique values in a column does not exceed the minimum accepted count.")
    private ColumnMinUniqueCountCheckSpec minUniqueCount;

    /**
     * Returns a minimum unique count check.
     * @return Minimum unique count check.
     */
    public ColumnMinUniqueCountCheckSpec getMinUniqueCount() {
        return minUniqueCount;
    }

    /**
     * Sets a new definition of a minimum unique count check.
     * @param minUniqueCount Minimum unique count check.
     */
    public void setMinUniqueCount(ColumnMinUniqueCountCheckSpec minUniqueCount) {
        this.setDirtyIf(!Objects.equals(this.minUniqueCount, minUniqueCount));
        this.minUniqueCount = minUniqueCount;
        propagateHierarchyIdToField(minUniqueCount, "min_unique_count");
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
