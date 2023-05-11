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
package ai.dqo.checks.column.partitioned.consistency;

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
 * Container of consistency data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConsistencyDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConsistencyDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_date_match_format_percent", o -> o.dailyPartitionDateMatchFormatPercent);
            put("daily_partition_string_datatype_changed", o -> o.dailyPartitionStringDatatypeChanged);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnConsistencyDateMatchFormatPercentCheckSpec dailyPartitionDateMatchFormatPercent;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringDatatypeChangedCheckSpec dailyPartitionStringDatatypeChanged;

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnConsistencyDateMatchFormatPercentCheckSpec getDailyPartitionDateMatchFormatPercent() {
        return dailyPartitionDateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param dailyPartitionDateMatchFormatPercent Date match format percentage check.
     */
    public void setDailyPartitionDateMatchFormatPercent(ColumnConsistencyDateMatchFormatPercentCheckSpec dailyPartitionDateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDateMatchFormatPercent, dailyPartitionDateMatchFormatPercent));
        this.dailyPartitionDateMatchFormatPercent = dailyPartitionDateMatchFormatPercent;
        propagateHierarchyIdToField(dailyPartitionDateMatchFormatPercent, "daily_partition_date_match_format_percent");
    }

    /**
     * Returns a count of expected values in datatype changed check.
     * @return Datatype changed check.
     */
    public ColumnStringDatatypeChangedCheckSpec getDailyPartitionStringDatatypeChanged() {
        return dailyPartitionStringDatatypeChanged;
    }

    /**
     * Sets a new definition of a datatype changed check.
     * @param dailyPartitionStringDatatypeChanged Datatype changed check.
     */
    public void setDailyPartitionStringDatatypeChanged(ColumnStringDatatypeChangedCheckSpec dailyPartitionStringDatatypeChanged) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringDatatypeChanged, dailyPartitionStringDatatypeChanged));
        this.dailyPartitionStringDatatypeChanged = dailyPartitionStringDatatypeChanged;
        propagateHierarchyIdToField(dailyPartitionStringDatatypeChanged, "daily_partition_string_datatype_changed");
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
