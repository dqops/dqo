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
package com.dqops.checks.column.monitoring.integrity;

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
 * Container of integrity data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_lookup_key_not_found", o -> o.monthlyLookupKeyNotFound);
            put("monthly_lookup_key_found_percent", o -> o.monthlyLookupKeyFoundPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnIntegrityLookupKeyNotFoundCountCheckSpec monthlyLookupKeyNotFound;

    @JsonPropertyDescription("Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec monthlyLookupKeyFoundPercent;


    /**
     * Returns an integrity value not match count check specification.
     * @return Integrity value not match count check specification.
     */
    public ColumnIntegrityLookupKeyNotFoundCountCheckSpec getMonthlyLookupKeyNotFound() {
        return monthlyLookupKeyNotFound;
    }

    /**
     * Sets a new definition of an integrity value not match count check.
     * @param monthlyLookupKeyNotFound Integrity value not match count check specification.
     */
    public void setMonthlyLookupKeyNotFound(ColumnIntegrityLookupKeyNotFoundCountCheckSpec monthlyLookupKeyNotFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyLookupKeyNotFound, monthlyLookupKeyNotFound));
        this.monthlyLookupKeyNotFound = monthlyLookupKeyNotFound;
        propagateHierarchyIdToField(monthlyLookupKeyNotFound, "monthly_lookup_key_not_found");
    }

    /**
     * Returns an integrity value match percent check specification.
     * @return Integrity value match percent check specification.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getMonthlyLookupKeyFoundPercent() {
        return monthlyLookupKeyFoundPercent;
    }

    /**
     * Sets a new definition of an integrity value match percent check.
     * @param monthlyLookupKeyFoundPercent Integrity value match percent check specification.
     */
    public void setMonthlyLookupKeyFoundPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec monthlyLookupKeyFoundPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyLookupKeyFoundPercent, monthlyLookupKeyFoundPercent));
        this.monthlyLookupKeyFoundPercent = monthlyLookupKeyFoundPercent;
        propagateHierarchyIdToField(monthlyLookupKeyFoundPercent, "monthly_lookup_key_found_percent");
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
        return CheckType.monitoring;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
    }
}
