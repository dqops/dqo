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
package com.dqops.checks.column.partitioned.consistency;

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
 * Container of consistency data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConsistencyMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConsistencyMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_date_match_format_percent", o -> o.monthlyPartitionDateMatchFormatPercent);
            put("monthly_partition_string_datatype_changed", o -> o.monthlyPartitionStringDatatypeChanged);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnConsistencyDateMatchFormatPercentCheckSpec monthlyPartitionDateMatchFormatPercent;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringDatatypeChangedCheckSpec monthlyPartitionStringDatatypeChanged;

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnConsistencyDateMatchFormatPercentCheckSpec getMonthlyPartitionDateMatchFormatPercent() {
        return monthlyPartitionDateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param monthlyPartitionDateMatchFormatPercent Date match format percentage check.
     */
    public void setMonthlyPartitionDateMatchFormatPercent(ColumnConsistencyDateMatchFormatPercentCheckSpec monthlyPartitionDateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDateMatchFormatPercent, monthlyPartitionDateMatchFormatPercent));
        this.monthlyPartitionDateMatchFormatPercent = monthlyPartitionDateMatchFormatPercent;
        propagateHierarchyIdToField(monthlyPartitionDateMatchFormatPercent, "monthly_partition_date_match_format_percent");
    }

    /**
     * Returns a count of expected values in datatype changed check.
     * @return Datatype changed check.
     */
    public ColumnStringDatatypeChangedCheckSpec getMonthlyPartitionStringDatatypeChanged() {
        return monthlyPartitionStringDatatypeChanged;
    }

    /**
     * Sets a new definition of a datatype changed check.
     * @param monthlyPartitionStringDatatypeChanged Datatype changed check.
     */
    public void setMonthlyPartitionStringDatatypeChanged(ColumnStringDatatypeChangedCheckSpec monthlyPartitionStringDatatypeChanged) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringDatatypeChanged, monthlyPartitionStringDatatypeChanged));
        this.monthlyPartitionStringDatatypeChanged = monthlyPartitionStringDatatypeChanged;
        propagateHierarchyIdToField(monthlyPartitionStringDatatypeChanged, "monthly_partition_string_datatype_changed");
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
