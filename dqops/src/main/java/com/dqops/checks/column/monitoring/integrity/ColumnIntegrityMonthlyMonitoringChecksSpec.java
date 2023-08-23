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
 * Container of integrity data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnIntegrityMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnIntegrityMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_foreign_key_not_match_count", o -> o.monthlyForeignKeyNotMatchCount);
            put("monthly_foreign_key_match_percent", o -> o.monthlyForeignKeyMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnIntegrityForeignKeyNotMatchCountCheckSpec monthlyForeignKeyNotMatchCount;

    @JsonPropertyDescription("Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnIntegrityForeignKeyMatchPercentCheckSpec monthlyForeignKeyMatchPercent;


    /**
     * Returns an integrity value not match count check specification.
     * @return Integrity value not match count check specification.
     */
    public ColumnIntegrityForeignKeyNotMatchCountCheckSpec getMonthlyForeignKeyNotMatchCount() {
        return monthlyForeignKeyNotMatchCount;
    }

    /**
     * Sets a new definition of an integrity value not match count check.
     * @param monthlyForeignKeyNotMatchCount Integrity value not match count check specification.
     */
    public void setMonthlyForeignKeyNotMatchCount(ColumnIntegrityForeignKeyNotMatchCountCheckSpec monthlyForeignKeyNotMatchCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyForeignKeyNotMatchCount, monthlyForeignKeyNotMatchCount));
        this.monthlyForeignKeyNotMatchCount = monthlyForeignKeyNotMatchCount;
        propagateHierarchyIdToField(monthlyForeignKeyNotMatchCount, "monthly_foreign_key_not_match_count");
    }

    /**
     * Returns an integrity value match percent check specification.
     * @return Integrity value match percent check specification.
     */
    public ColumnIntegrityForeignKeyMatchPercentCheckSpec getMonthlyForeignKeyMatchPercent() {
        return monthlyForeignKeyMatchPercent;
    }

    /**
     * Sets a new definition of an integrity value match percent check.
     * @param monthlyForeignKeyMatchPercent Integrity value match percent check specification.
     */
    public void setMonthlyForeignKeyMatchPercent(ColumnIntegrityForeignKeyMatchPercentCheckSpec monthlyForeignKeyMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyForeignKeyMatchPercent, monthlyForeignKeyMatchPercent));
        this.monthlyForeignKeyMatchPercent = monthlyForeignKeyMatchPercent;
        propagateHierarchyIdToField(monthlyForeignKeyMatchPercent, "monthly_foreign_key_match_percent");
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
