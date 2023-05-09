/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.checks.column.recurring.consistency;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.consistency.ColumnConsistencyDateMatchFormatPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.consistency.ColumnStringDatatypeChangedCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConsistencyMonthlyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConsistencyMonthlyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_date_match_format_percent", o -> o.monthlyDateMatchFormatPercent);
            put("monthly_string_datatype_changed", o -> o.monthlyStringDatatypeChanged);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly recurring.")
    private ColumnConsistencyDateMatchFormatPercentCheckSpec monthlyDateMatchFormatPercent;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnStringDatatypeChangedCheckSpec monthlyStringDatatypeChanged;

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnConsistencyDateMatchFormatPercentCheckSpec getMonthlyDateMatchFormatPercent() {
        return monthlyDateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param monthlyDateMatchFormatPercent Date match format percentage check.
     */
    public void setMonthlyDateMatchFormatPercent(ColumnConsistencyDateMatchFormatPercentCheckSpec monthlyDateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDateMatchFormatPercent, monthlyDateMatchFormatPercent));
        this.monthlyDateMatchFormatPercent = monthlyDateMatchFormatPercent;
        propagateHierarchyIdToField(monthlyDateMatchFormatPercent, "monthly_date_match_format_percent");
    }

    /**
     * Returns a count of expected values in datatype detect check.
     * @return Datatype detect check.
     */
    public ColumnStringDatatypeChangedCheckSpec getMonthlyStringDatatypeChanged() {
        return monthlyStringDatatypeChanged;
    }

    /**
     * Sets a new definition of a datatype detect check.
     * @param monthlyStringDatatypeChanged Datatype detect check.
     */
    public void setMonthlyStringDatatypeChanged(ColumnStringDatatypeChangedCheckSpec monthlyStringDatatypeChanged) {
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
}
