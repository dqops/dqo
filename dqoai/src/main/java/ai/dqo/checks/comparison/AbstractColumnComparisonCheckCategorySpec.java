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

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Base class for column level comparison (accuracy) checks that run accuracy checks for one comparison (comparing against one target table)
 * for one column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractColumnComparisonCheckCategorySpec extends AbstractComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractColumnComparisonCheckCategorySpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractComparisonCheckCategorySpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("The name of the reference column name in the reference table. It is the column to which the current column is compared to.")
    private String referenceColumn;

    /**
     * Returns the name of the reference column name in the reference table. It is the column to which the current column is compared to.
     * @return The name of the reference column.
     */
    public String getReferenceColumn() {
        return referenceColumn;
    }

    /**
     * Sets the name of the reference column from the reference table that we are comparing to.
     * @param referenceColumn The name of the reference column.
     */
    public void setReferenceColumn(String referenceColumn) {
        this.setDirtyIf(!Objects.equals(this.referenceColumn, referenceColumn));
        this.referenceColumn = referenceColumn;
    }
}
