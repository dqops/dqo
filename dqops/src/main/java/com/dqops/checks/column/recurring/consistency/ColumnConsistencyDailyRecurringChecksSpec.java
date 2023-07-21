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
package com.dqops.checks.column.recurring.consistency;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.consistency.ColumnConsistencyDateMatchFormatPercentCheckSpec;
import com.dqops.checks.column.checkspecs.consistency.ColumnStringDatatypeChangedCheckSpec;
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
 * Container of consistency data quality recurring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConsistencyDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConsistencyDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_date_match_format_percent", o -> o.dailyDateMatchFormatPercent);
            put("daily_string_datatype_changed", o -> o.dailyStringDatatypeChanged);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily recurring.")
    private ColumnConsistencyDateMatchFormatPercentCheckSpec dailyDateMatchFormatPercent;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringDatatypeChangedCheckSpec dailyStringDatatypeChanged;

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnConsistencyDateMatchFormatPercentCheckSpec getDailyDateMatchFormatPercent() {
        return dailyDateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param dailyDateMatchFormatPercent Date match format percentage check.
     */
    public void setDailyDateMatchFormatPercent(ColumnConsistencyDateMatchFormatPercentCheckSpec dailyDateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyDateMatchFormatPercent, dailyDateMatchFormatPercent));
        this.dailyDateMatchFormatPercent = dailyDateMatchFormatPercent;
        propagateHierarchyIdToField(dailyDateMatchFormatPercent, "daily_date_match_format_percent");
    }

    /**
     * Returns a count of expected values in datatype changed check.
     * @return Datatype changed check.
     */
    public ColumnStringDatatypeChangedCheckSpec getDailyStringDatatypeChanged() {
        return dailyStringDatatypeChanged;
    }

    /**
     * Sets a new definition of a datatype changed check.
     * @param dailyStringDatatypeChanged Datatype changed check.
     */
    public void setDailyStringDatatypeChanged(ColumnStringDatatypeChangedCheckSpec dailyStringDatatypeChanged) {
        this.setDirtyIf(!Objects.equals(this.dailyStringDatatypeChanged, dailyStringDatatypeChanged));
        this.dailyStringDatatypeChanged = dailyStringDatatypeChanged;
        propagateHierarchyIdToField(dailyStringDatatypeChanged, "daily_string_datatype_changed");
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
    public ColumnConsistencyDailyRecurringChecksSpec deepClone() {
        return (ColumnConsistencyDailyRecurringChecksSpec)super.deepClone();
    }
}
