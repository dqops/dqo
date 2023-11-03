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
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.datetime.ColumnDatetimeDateMatchFormatPercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDateValuesInFuturePercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDatetimeValueInRangeDatePercentCheckSpec;
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
 * Container of date-time data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_date_match_format_percent", o -> o.monthlyPartitionDateMatchFormatPercent);
            put("monthly_partition_date_values_in_future_percent", o -> o.monthlyPartitionDateValuesInFuturePercent);
            put("monthly_partition_datetime_value_in_range_date_percent", o -> o.monthlyPartitionDatetimeValueInRangeDatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDatetimeDateMatchFormatPercentCheckSpec monthlyPartitionDateMatchFormatPercent;

    @JsonPropertyDescription("Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDateValuesInFuturePercentCheckSpec monthlyPartitionDateValuesInFuturePercent;

    @JsonPropertyDescription("Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDatetimeValueInRangeDatePercentCheckSpec monthlyPartitionDatetimeValueInRangeDatePercent;

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnDatetimeDateMatchFormatPercentCheckSpec getMonthlyPartitionDateMatchFormatPercent() {
        return monthlyPartitionDateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param monthlyPartitionDateMatchFormatPercent Date match format percentage check.
     */
    public void setMonthlyPartitionDateMatchFormatPercent(ColumnDatetimeDateMatchFormatPercentCheckSpec monthlyPartitionDateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDateMatchFormatPercent, monthlyPartitionDateMatchFormatPercent));
        this.monthlyPartitionDateMatchFormatPercent = monthlyPartitionDateMatchFormatPercent;
        propagateHierarchyIdToField(monthlyPartitionDateMatchFormatPercent, "monthly_partition_date_match_format_percent");
    }

    /**
     * Returns a date values in future percentage check.
     * @return Date values in future percentage check.
     */
    public ColumnDateValuesInFuturePercentCheckSpec getMonthlyPartitionDateValuesInFuturePercent() {
        return monthlyPartitionDateValuesInFuturePercent;
    }

    /**
     * Sets a new definition of date values in future percentage check.
     * @param monthlyPartitionDateValuesInFuturePercent Date values in future percentage check.
     */
    public void setMonthlyPartitionDateValuesInFuturePercent(ColumnDateValuesInFuturePercentCheckSpec monthlyPartitionDateValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDateValuesInFuturePercent, monthlyPartitionDateValuesInFuturePercent));
        this.monthlyPartitionDateValuesInFuturePercent = monthlyPartitionDateValuesInFuturePercent;
        propagateHierarchyIdToField(monthlyPartitionDateValuesInFuturePercent, "monthly_partition_date_values_in_future_percent");
    }

    /**
     * Returns a datetime value in range date percentage check.
     * @return Maximum datetime value in range date percentage check.
     */
    public ColumnDatetimeValueInRangeDatePercentCheckSpec getMonthlyPartitionDatetimeValueInRangeDatePercent() {
        return monthlyPartitionDatetimeValueInRangeDatePercent;
    }

    /**
     * Sets a new definition of a datetime value in range date percentage check.
     * @param monthlyPartitionDatetimeValueInRangeDatePercent Datetime value in range date percentage check.
     */
    public void setMonthlyPartitionDatetimeValueInRangeDatePercent(ColumnDatetimeValueInRangeDatePercentCheckSpec monthlyPartitionDatetimeValueInRangeDatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDatetimeValueInRangeDatePercent, monthlyPartitionDatetimeValueInRangeDatePercent));
        this.monthlyPartitionDatetimeValueInRangeDatePercent = monthlyPartitionDatetimeValueInRangeDatePercent;
        propagateHierarchyIdToField(monthlyPartitionDatetimeValueInRangeDatePercent, "monthly_partition_datetime_value_in_range_date_percent");
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
        return CheckType.partitioned;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.monthly;
    }
}
