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
package com.dqops.checks.column.monitoring.datatype;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeDateMatchFormatPercentCheckSpec;
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeStringDatatypeChangedCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of datatype data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatatypeMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatatypeMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_date_match_format_percent", o -> o.monthlyDateMatchFormatPercent);
            put("monthly_string_datatype_changed", o -> o.monthlyStringDatatypeChanged);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly monitoring.")
    private ColumnDatatypeDateMatchFormatPercentCheckSpec monthlyDateMatchFormatPercent;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDatatypeStringDatatypeChangedCheckSpec monthlyStringDatatypeChanged;

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnDatatypeDateMatchFormatPercentCheckSpec getMonthlyDateMatchFormatPercent() {
        return monthlyDateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param monthlyDateMatchFormatPercent Date match format percentage check.
     */
    public void setMonthlyDateMatchFormatPercent(ColumnDatatypeDateMatchFormatPercentCheckSpec monthlyDateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDateMatchFormatPercent, monthlyDateMatchFormatPercent));
        this.monthlyDateMatchFormatPercent = monthlyDateMatchFormatPercent;
        propagateHierarchyIdToField(monthlyDateMatchFormatPercent, "monthly_date_match_format_percent");
    }

    /**
     * Returns a count of expected values in datatype detect check.
     * @return Datatype detect check.
     */
    public ColumnDatatypeStringDatatypeChangedCheckSpec getMonthlyStringDatatypeChanged() {
        return monthlyStringDatatypeChanged;
    }

    /**
     * Sets a new definition of a datatype detect check.
     * @param monthlyStringDatatypeChanged Datatype detect check.
     */
    public void setMonthlyStringDatatypeChanged(ColumnDatatypeStringDatatypeChangedCheckSpec monthlyStringDatatypeChanged) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringDatatypeChanged, monthlyStringDatatypeChanged));
        this.monthlyStringDatatypeChanged = monthlyStringDatatypeChanged;
        propagateHierarchyIdToField(monthlyStringDatatypeChanged, "monthly_string_datatype_changed");
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
    public ColumnDatatypeMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnDatatypeMonthlyMonitoringChecksSpec)super.deepClone();
    }
}
