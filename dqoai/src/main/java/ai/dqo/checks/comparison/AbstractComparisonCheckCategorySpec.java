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

package ai.dqo.checks.comparison;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

/**
 * Base class for comparison (accuracy) check category for a single target table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractComparisonCheckCategorySpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractComparisonCheckCategorySpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
        }
    };

    /**
     * Returns the name of a configured comparison. We need to know the comparison name to look up the comparison configuration,
     * which also identifies the reference table to compare to and the data grouping configurations to use.
     * @return Comparison name that matches the name of a named comparisons defined on the parent table level.
     */
    @JsonIgnore
    public String getComparisonName() {
        HierarchyId hierarchyId = this.getHierarchyId();
        if (hierarchyId == null) {
            return null;
        }
        return hierarchyId.getLast().toString();
    }
}
