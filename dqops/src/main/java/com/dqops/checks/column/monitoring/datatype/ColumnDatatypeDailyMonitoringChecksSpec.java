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
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeStringDatatypeChangedCheckSpec;
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeStringDatatypeDetectedCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of datatype data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatatypeDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatatypeDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_string_datatype_detected", o -> o.dailyStringDatatypeDetected);
            put("daily_string_datatype_changed", o -> o.dailyStringDatatypeChanged);
        }
    };

    @JsonPropertyDescription("Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDatatypeStringDatatypeDetectedCheckSpec dailyStringDatatypeDetected;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDatatypeStringDatatypeChangedCheckSpec dailyStringDatatypeChanged;

    /**
     * Returns a count of expected values in datatype detected check.
     * @return Datatype detected check.
     */
    public ColumnDatatypeStringDatatypeDetectedCheckSpec getDailyStringDatatypeDetected() {
        return dailyStringDatatypeDetected;
    }

    /**
     * Sets a new definition of a datatype detected check.
     * @param dailyStringDatatypeDetected Datatype detected check.
     */
    public void setDailyStringDatatypeDetected(ColumnDatatypeStringDatatypeDetectedCheckSpec dailyStringDatatypeDetected) {
        this.setDirtyIf(!Objects.equals(this.dailyStringDatatypeDetected, dailyStringDatatypeDetected));
        this.dailyStringDatatypeDetected = dailyStringDatatypeDetected;
        propagateHierarchyIdToField(dailyStringDatatypeDetected, "daily_string_datatype_detected");
    }

    /**
     * Returns a count of expected values in datatype changed check.
     * @return Datatype changed check.
     */
    public ColumnDatatypeStringDatatypeChangedCheckSpec getDailyStringDatatypeChanged() {
        return dailyStringDatatypeChanged;
    }

    /**
     * Sets a new definition of a datatype changed check.
     * @param dailyStringDatatypeChanged Datatype changed check.
     */
    public void setDailyStringDatatypeChanged(ColumnDatatypeStringDatatypeChangedCheckSpec dailyStringDatatypeChanged) {
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
    public ColumnDatatypeDailyMonitoringChecksSpec deepClone() {
        return (ColumnDatatypeDailyMonitoringChecksSpec)super.deepClone();
    }
}