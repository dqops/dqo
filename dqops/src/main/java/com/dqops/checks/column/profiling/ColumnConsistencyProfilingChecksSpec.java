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
import com.dqops.checks.column.checkspecs.consistency.ColumnConsistencyDateMatchFormatPercentCheckSpec;
import com.dqops.checks.column.checkspecs.consistency.ColumnStringDatatypeChangedCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for consistency.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConsistencyProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConsistencyProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("date_match_format_percent", o -> o.dateMatchFormatPercent);
            put("string_datatype_changed", o -> o.stringDatatypeChanged);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a column does not exceed the minimum accepted percentage.")
    private ColumnConsistencyDateMatchFormatPercentCheckSpec dateMatchFormatPercent;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.")
    private ColumnStringDatatypeChangedCheckSpec stringDatatypeChanged;

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnConsistencyDateMatchFormatPercentCheckSpec getDateMatchFormatPercent() {
        return dateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param dateMatchFormatPercent Date match format percentage check.
     */
    public void setDateMatchFormatPercent(ColumnConsistencyDateMatchFormatPercentCheckSpec dateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dateMatchFormatPercent, dateMatchFormatPercent));
        this.dateMatchFormatPercent = dateMatchFormatPercent;
        propagateHierarchyIdToField(dateMatchFormatPercent, "date_match_format_percent");
    }

    /**
     * Returns a count of expected values in datatype changed check.
     * @return Datatype changed check.
     */
    public ColumnStringDatatypeChangedCheckSpec getStringDatatypeChanged() {
        return stringDatatypeChanged;
    }

    /**
     * Sets a new definition of a datatype changed check.
     * @param stringDatatypeChanged Datatype changed check.
     */
    public void setStringDatatypeChanged(ColumnStringDatatypeChangedCheckSpec stringDatatypeChanged) {
        this.setDirtyIf(!Objects.equals(this.stringDatatypeChanged, stringDatatypeChanged));
        this.stringDatatypeChanged = stringDatatypeChanged;
        propagateHierarchyIdToField(stringDatatypeChanged, "string_datatype_changed");
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