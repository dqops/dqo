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
package com.dqops.checks.column.partitioned.integrity;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.integrity.ColumnIntegrityForeignKeyMatchPercentCheckSpec;
import com.dqops.checks.column.checkspecs.integrity.ColumnIntegrityLookupKeyNotFoundCountCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of integrity data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_lookup_key_not_found", o -> o.dailyPartitionLookupKeyNotFound);
            put("daily_partition_lookup_key_found_percent", o -> o.dailyPartitionLookupKeyFoundPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores a separate data quality check result for each daily partition.")
    private ColumnIntegrityLookupKeyNotFoundCountCheckSpec dailyPartitionLookupKeyNotFound;

    @JsonPropertyDescription("Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores a separate data quality check result for each daily partition.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyPartitionLookupKeyFoundPercent;

    /**
     * Returns an integrity value not match count check.
     * @return Integrity value not match count check.
     */
    public ColumnIntegrityLookupKeyNotFoundCountCheckSpec getDailyPartitionLookupKeyNotFound() {
        return dailyPartitionLookupKeyNotFound;
    }

    /**
     * Sets a new definition of an integrity value not match count check.
     * @param dailyPartitionLookupKeyNotFound Integrity value not match count check.
     */
    public void setDailyPartitionLookupKeyNotFound(ColumnIntegrityLookupKeyNotFoundCountCheckSpec dailyPartitionLookupKeyNotFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionLookupKeyNotFound, dailyPartitionLookupKeyNotFound));
        this.dailyPartitionLookupKeyNotFound = dailyPartitionLookupKeyNotFound;
        propagateHierarchyIdToField(dailyPartitionLookupKeyNotFound, "daily_partition_lookup_key_not_found");
    }

    /**
     * Returns an integrity value match percent check.
     * @return Integrity value match percent check.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getDailyPartitionLookupKeyFoundPercent() {
        return dailyPartitionLookupKeyFoundPercent;
    }

    /**
     * Sets a new definition of an integrity value match percent check.
     * @param dailyPartitionLookupKeyFoundPercent Integrity value match percent check.
     */
    public void setDailyPartitionLookupKeyFoundPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyPartitionLookupKeyFoundPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionLookupKeyFoundPercent, dailyPartitionLookupKeyFoundPercent));
        this.dailyPartitionLookupKeyFoundPercent = dailyPartitionLookupKeyFoundPercent;
        propagateHierarchyIdToField(dailyPartitionLookupKeyFoundPercent, "daily_partition_lookup_key_found_percent");
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
     * Gets the check target appropriate for all checks in this category.
     *
     * @return Corresponding check target.
     */
    @Override
    @JsonIgnore
    public CheckTarget getCheckTarget() {
        return CheckTarget.column;
    }

    /**
     * Gets the check type appropriate for all checks in this category.
     *
     * @return Corresponding check type.
     */
    @Override
    @JsonIgnore
    public CheckType getCheckType() {
        return CheckType.partitioned;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.daily;
    }
}
