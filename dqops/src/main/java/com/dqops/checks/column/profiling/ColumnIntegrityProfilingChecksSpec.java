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
import com.dqops.checks.column.checkspecs.integrity.ColumnIntegrityForeignKeyMatchPercentCheckSpec;
import com.dqops.checks.column.checkspecs.integrity.ColumnIntegrityForeignKeyNotMatchCountCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for integrity.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_foreign_key_not_match_count", o -> o.profileForeignKeyNotMatchCount);
            put("profile_foreign_key_match_percent", o -> o.profileForeignKeyMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.")
    private ColumnIntegrityForeignKeyNotMatchCountCheckSpec profileForeignKeyNotMatchCount;

    @JsonPropertyDescription("Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec profileForeignKeyMatchPercent;

    /**
     * Returns an integrity value not match count check specification.
     * @return Integrity value not match count check specification.
     */
    public ColumnIntegrityForeignKeyNotMatchCountCheckSpec getProfileForeignKeyNotMatchCount() {
        return profileForeignKeyNotMatchCount;
    }

    /**
     * Sets integrity value not match count check specification.
     * @param profileForeignKeyNotMatchCount Integrity value not match count check specification.
     */
    public void setProfileForeignKeyNotMatchCount(ColumnIntegrityForeignKeyNotMatchCountCheckSpec profileForeignKeyNotMatchCount) {
        this.setDirtyIf(!Objects.equals(this.profileForeignKeyNotMatchCount, profileForeignKeyNotMatchCount));
        this.profileForeignKeyNotMatchCount = profileForeignKeyNotMatchCount;
        propagateHierarchyIdToField(profileForeignKeyNotMatchCount, "profile_foreign_key_not_match_count");
    }

    /**
     * Returns an integrity value match percent check specification.
     * @return Integrity value match percent check specification.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getProfileForeignKeyMatchPercent() {
        return profileForeignKeyMatchPercent;
    }

    /**
     * Sets integrity value match percent check specification.
     * @param profileForeignKeyMatchPercent Integrity value match percent check specification.
     */
    public void setProfileForeignKeyMatchPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec profileForeignKeyMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.profileForeignKeyMatchPercent, profileForeignKeyMatchPercent));
        this.profileForeignKeyMatchPercent = profileForeignKeyMatchPercent;
        propagateHierarchyIdToField(profileForeignKeyMatchPercent, "profile_foreign_key_match_percent");
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