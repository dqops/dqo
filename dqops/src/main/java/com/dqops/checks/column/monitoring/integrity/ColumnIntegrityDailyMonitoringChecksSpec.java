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
import com.dqops.connectors.DataTypeCategory;
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
 * Container of integrity data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_lookup_key_not_found", o -> o.dailyLookupKeyNotFound);
            put("daily_lookup_key_found_percent", o -> o.dailyLookupKeyFoundPercent);
        }
    };

    @JsonPropertyDescription("Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnIntegrityLookupKeyNotFoundCountCheckSpec dailyLookupKeyNotFound;

    @JsonPropertyDescription("Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyLookupKeyFoundPercent;

    /**
     * Returns an integrity value not match count check specification.
     * @return Integrity value not match count check specification.
     */
    public ColumnIntegrityLookupKeyNotFoundCountCheckSpec getDailyLookupKeyNotFound() {
        return dailyLookupKeyNotFound;
    }

    /**
     * Sets a new definition of an integrity value not match count check.
     * @param dailyLookupKeyNotFound Integrity value not match count check specification.
     */
    public void setDailyLookupKeyNotFound(ColumnIntegrityLookupKeyNotFoundCountCheckSpec dailyLookupKeyNotFound) {
        this.setDirtyIf(!Objects.equals(this.dailyLookupKeyNotFound, dailyLookupKeyNotFound));
        this.dailyLookupKeyNotFound = dailyLookupKeyNotFound;
        propagateHierarchyIdToField(dailyLookupKeyNotFound, "daily_lookup_key_not_found");
    }

    /**
     * Returns an integrity value match percent check specification.
     * @return Integrity value match percent check specification.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getDailyLookupKeyFoundPercent() {
        return dailyLookupKeyFoundPercent;
    }

    /**
     * Sets a new definition of an integrity value match percent check.
     * @param dailyLookupKeyFoundPercent Integrity value match percent check specification.
     */
    public void setDailyLookupKeyFoundPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec dailyLookupKeyFoundPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyLookupKeyFoundPercent, dailyLookupKeyFoundPercent));
        this.dailyLookupKeyFoundPercent = dailyLookupKeyFoundPercent;
        propagateHierarchyIdToField(dailyLookupKeyFoundPercent, "daily_lookup_key_found_percent");
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
        return CheckTimeScale.daily;
    }

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
    }
}
