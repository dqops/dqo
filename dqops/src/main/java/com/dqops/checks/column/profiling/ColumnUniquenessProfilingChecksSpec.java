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
            put("profile_distinct_count", o -> o.profileDistinctCount);
            put("profile_distinct_percent", o -> o.profileDistinctPercent);
            put("profile_duplicate_count", o -> o.profileDuplicateCount);
            put("profile_duplicate_percent", o -> o.profileDuplicatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count.")
    private ColumnDistinctCountCheckSpec profileDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.")
    private ColumnDistinctPercentCheckSpec profileDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.")
    private ColumnDuplicateCountCheckSpec profileDuplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.")
    private ColumnDuplicatePercentCheckSpec profileDuplicatePercent;

    /**
     * Returns a distinct count check specification.
     * @return Distinct count check specification.
     */
    public ColumnDistinctCountCheckSpec getProfileDistinctCount() {
        return profileDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count check.
     * @param profileDistinctCount Distinct count check specification.
     */
    public void setProfileDistinctCount(ColumnDistinctCountCheckSpec profileDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctCount, profileDistinctCount));
        this.profileDistinctCount = profileDistinctCount;
        propagateHierarchyIdToField(profileDistinctCount, "profile_distinct_count");
    }

    /**
     * Returns a distinct percent check specification.
     * @return Distinct percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getProfileDistinctPercent() {
        return profileDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent check.
     * @param profileDistinctPercent Distinct percent check specification.
     */
    public void setProfileDistinctPercent(ColumnDistinctPercentCheckSpec profileDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctPercent, profileDistinctPercent));
        this.profileDistinctPercent = profileDistinctPercent;
        propagateHierarchyIdToField(profileDistinctPercent, "profile_distinct_percent");
    }

    /**
     * Returns a duplicate count check specification.
     * @return Duplicate count check specification.
     */
    public ColumnDuplicateCountCheckSpec getProfileDuplicateCount() {
        return profileDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate count check.
     * @param profileDuplicateCount Duplicate count check specification.
     */
    public void setProfileDuplicateCount(ColumnDuplicateCountCheckSpec profileDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.profileDuplicateCount, profileDuplicateCount));
        this.profileDuplicateCount = profileDuplicateCount;
        propagateHierarchyIdToField(profileDuplicateCount, "profile_duplicate_count");
    }

    /**
     * Returns a duplicate percent check specification.
     * @return Duplicate percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getProfileDuplicatePercent() {
        return profileDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate percent check.
     * @param profileDuplicatePercent Duplicate percent check specification.
     */
    public void setProfileDuplicatePercent(ColumnDuplicatePercentCheckSpec profileDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.profileDuplicatePercent, profileDuplicatePercent));
        this.profileDuplicatePercent = profileDuplicatePercent;
        propagateHierarchyIdToField(profileDuplicatePercent, "profile_duplicate_percent");
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
