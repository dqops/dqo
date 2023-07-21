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
package com.dqops.checks.column.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctCountCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDistinctPercentCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDuplicateCountCheckSpec;
import com.dqops.checks.column.checkspecs.uniqueness.ColumnDuplicatePercentCheckSpec;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class ColumnUniquenessProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("distinct_count", o -> o.distinctCount);
            put("distinct_percent", o -> o.distinctPercent);
            put("duplicate_count", o -> o.duplicateCount);
            put("duplicate_percent", o -> o.duplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count.")
    private ColumnDistinctCountCheckSpec distinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.")
    private ColumnDistinctPercentCheckSpec distinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.")
    private ColumnDuplicateCountCheckSpec duplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.")
    private ColumnDuplicatePercentCheckSpec duplicatePercent;

    /**
     * Returns a distinct count check specification.
     * @return Distinct count check specification.
     */
    public ColumnDistinctCountCheckSpec getDistinctCount() {
        return distinctCount;
    }

    /**
     * Sets a new specification of a distinct count check.
     * @param distinctCount Distinct count check specification.
     */
    public void setDistinctCount(ColumnDistinctCountCheckSpec distinctCount) {
        this.setDirtyIf(!Objects.equals(this.distinctCount, distinctCount));
        this.distinctCount = distinctCount;
        propagateHierarchyIdToField(distinctCount, "distinct_count");
    }

    /**
     * Returns a distinct percent check specification.
     * @return Distinct percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getDistinctPercent() {
        return distinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent check.
     * @param distinctPercent Distinct percent check specification.
     */
    public void setDistinctPercent(ColumnDistinctPercentCheckSpec distinctPercent) {
        this.setDirtyIf(!Objects.equals(this.distinctPercent, distinctPercent));
        this.distinctPercent = distinctPercent;
        propagateHierarchyIdToField(distinctPercent, "distinct_percent");
    }

    /**
     * Returns a duplicate count check specification.
     * @return Duplicate count check specification.
     */
    public ColumnDuplicateCountCheckSpec getDuplicateCount() {
        return duplicateCount;
    }

    /**
     * Sets a new specification of a duplicate count check.
     * @param duplicateCount Duplicate count check specification.
     */
    public void setDuplicateCount(ColumnDuplicateCountCheckSpec duplicateCount) {
        this.setDirtyIf(!Objects.equals(this.duplicateCount, duplicateCount));
        this.duplicateCount = duplicateCount;
        propagateHierarchyIdToField(duplicateCount, "duplicate_count");
    }

    /**
     * Returns a duplicate percent check specification.
     * @return Duplicate percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getDuplicatePercent() {
        return duplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate percent check.
     * @param duplicatePercent Duplicate percent check specification.
     */
    public void setDuplicatePercent(ColumnDuplicatePercentCheckSpec duplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.duplicatePercent, duplicatePercent));
        this.duplicatePercent = duplicatePercent;
        propagateHierarchyIdToField(duplicatePercent, "duplicate_percent");
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

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public ColumnUniquenessProfilingChecksSpec deepClone() {
        return (ColumnUniquenessProfilingChecksSpec)super.deepClone();
    }
}
