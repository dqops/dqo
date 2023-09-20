/*
 * Copyright © 2021 DQOps (support@dqops.com)
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

package com.dqops.checks.comparison;

import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.comparison.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     * Returns the check specification for the given check type or null when it is not present and <code>createWhenMissing</code> is false.
     * @param columnCompareCheckType Compare check type.
     * @param createWhenMissing When true and the check specification is not present, it is created, added to the check compare container and returned.
     * @return Check specification or null (when <code>createWhenMissing</code> is false).
     */
    public abstract ComparisonCheckRules getCheckSpec(ColumnCompareCheckType columnCompareCheckType, boolean createWhenMissing);

    /**
     * Removes the check specification for the given check.
     * @param columnCompareCheckType Check type.
     */
    public abstract void removeCheckSpec(ColumnCompareCheckType columnCompareCheckType);

    /**
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.column;
    }
}
