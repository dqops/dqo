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
package com.dqops.checks.column.partitioned.datetime;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDatetimeDateMatchFormatPercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDateValuesInFuturePercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDatetimeValueInRangeDatePercentCheckSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of date-time data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_date_match_format_percent", o -> o.dailyPartitionDateMatchFormatPercent);
            put("daily_partition_date_values_in_future_percent", o -> o.dailyPartitionDateValuesInFuturePercent);
            put("daily_partition_datetime_value_in_range_date_percent", o -> o.dailyPartitionDatetimeValueInRangeDatePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDatetimeDateMatchFormatPercentCheckSpec dailyPartitionDateMatchFormatPercent;

    @JsonPropertyDescription("Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDateValuesInFuturePercentCheckSpec dailyPartitionDateValuesInFuturePercent;

    @JsonPropertyDescription("Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDatetimeValueInRangeDatePercentCheckSpec dailyPartitionDatetimeValueInRangeDatePercent;

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnDatetimeDateMatchFormatPercentCheckSpec getDailyPartitionDateMatchFormatPercent() {
        return dailyPartitionDateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param dailyPartitionDateMatchFormatPercent Date match format percentage check.
     */
    public void setDailyPartitionDateMatchFormatPercent(ColumnDatetimeDateMatchFormatPercentCheckSpec dailyPartitionDateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDateMatchFormatPercent, dailyPartitionDateMatchFormatPercent));
        this.dailyPartitionDateMatchFormatPercent = dailyPartitionDateMatchFormatPercent;
        propagateHierarchyIdToField(dailyPartitionDateMatchFormatPercent, "daily_partition_date_match_format_percent");
    }

    /**
     * Returns a date values in future percentage check.
     * @return Maximum date values in future percentage check.
     */
    public ColumnDateValuesInFuturePercentCheckSpec getDailyPartitionDateValuesInFuturePercent() {
        return dailyPartitionDateValuesInFuturePercent;
    }

    /**
     * Sets a new definition of a date values in future percentage check.
     * @param dailyPartitionDateValuesInFuturePercent Date values in future percentage check.
     */
    public void setDailyPartitionDateValuesInFuturePercent(ColumnDateValuesInFuturePercentCheckSpec dailyPartitionDateValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDateValuesInFuturePercent, dailyPartitionDateValuesInFuturePercent));
        this.dailyPartitionDateValuesInFuturePercent = dailyPartitionDateValuesInFuturePercent;
        propagateHierarchyIdToField(dailyPartitionDateValuesInFuturePercent, "daily_partition_date_values_in_future_percent");
    }

    /**
     * Returns a datetime value in range date percentage check.
     * @return Maximum datetime value in range date percentage check.
     */
    public ColumnDatetimeValueInRangeDatePercentCheckSpec getDailyPartitionDatetimeValueInRangeDatePercent() {
        return dailyPartitionDatetimeValueInRangeDatePercent;
    }

    /**
     * Sets a new definition of a datetime value in range date percentage check.
     * @param dailyPartitionDatetimeValueInRangeDatePercent Datetime value in range date percentage check.
     */
    public void setDailyPartitionDatetimeValueInRangeDatePercent(ColumnDatetimeValueInRangeDatePercentCheckSpec dailyPartitionDatetimeValueInRangeDatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDatetimeValueInRangeDatePercent, dailyPartitionDatetimeValueInRangeDatePercent));
        this.dailyPartitionDatetimeValueInRangeDatePercent = dailyPartitionDatetimeValueInRangeDatePercent;
        propagateHierarchyIdToField(dailyPartitionDatetimeValueInRangeDatePercent, "daily_partition_datetime_value_in_range_date_percent");
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
