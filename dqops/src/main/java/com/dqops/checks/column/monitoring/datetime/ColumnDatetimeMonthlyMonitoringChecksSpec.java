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
package com.dqops.checks.column.monitoring.datetime;

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
 * Container of date-time data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_date_values_in_future_percent", o -> o.monthlyDateValuesInFuturePercent);
            put("monthly_datetime_value_in_range_date_percent", o -> o.monthlyDatetimeValueInRangeDatePercent);
            put("monthly_date_match_format_percent", o -> o.monthlyDateMatchFormatPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDateValuesInFuturePercentCheckSpec monthlyDateValuesInFuturePercent;

    @JsonPropertyDescription("Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDatetimeValueInRangeDatePercentCheckSpec monthlyDatetimeValueInRangeDatePercent;

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a text column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly monitoring.")
    private ColumnDatetimeDateMatchFormatPercentCheckSpec monthlyDateMatchFormatPercent;

    /**
     * Returns a date values in future percent check specification.
     * @return Date values in future percent check specification.
     */
    public ColumnDateValuesInFuturePercentCheckSpec getMonthlyDateValuesInFuturePercent() {
        return monthlyDateValuesInFuturePercent;
    }

    /**
     * Sets a new definition of a date values in future percent check.
     * @param monthlyDateValuesInFuturePercent Date values in future percent check specification.
     */
    public void setMonthlyDateValuesInFuturePercent(ColumnDateValuesInFuturePercentCheckSpec monthlyDateValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDateValuesInFuturePercent, monthlyDateValuesInFuturePercent));
        this.monthlyDateValuesInFuturePercent = monthlyDateValuesInFuturePercent;
        propagateHierarchyIdToField(monthlyDateValuesInFuturePercent, "monthly_date_values_in_future_percent");
    }

    /**
     * Returns a datetime value in range date percentage check.
     * @return Maximum datetime value in range date percentage check.
     */
    public ColumnDatetimeValueInRangeDatePercentCheckSpec getMonthlyDatetimeValueInRangeDatePercent() {
        return monthlyDatetimeValueInRangeDatePercent;
    }

    /**
     * Sets a new definition of a datetime value in range date percentage check.
     * @param monthlyDatetimeValueInRangeDatePercent Datetime value in range date percentage check.
     */
    public void setMonthlyDatetimeValueInRangeDatePercent(ColumnDatetimeValueInRangeDatePercentCheckSpec monthlyDatetimeValueInRangeDatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDatetimeValueInRangeDatePercent, monthlyDatetimeValueInRangeDatePercent));
        this.monthlyDatetimeValueInRangeDatePercent = monthlyDatetimeValueInRangeDatePercent;
        propagateHierarchyIdToField(monthlyDatetimeValueInRangeDatePercent, "monthly_datetime_value_in_range_date_percent");
    }

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnDatetimeDateMatchFormatPercentCheckSpec getMonthlyDateMatchFormatPercent() {
        return monthlyDateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param monthlyDateMatchFormatPercent Date match format percentage check.
     */
    public void setMonthlyDateMatchFormatPercent(ColumnDatetimeDateMatchFormatPercentCheckSpec monthlyDateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDateMatchFormatPercent, monthlyDateMatchFormatPercent));
        this.monthlyDateMatchFormatPercent = monthlyDateMatchFormatPercent;
        propagateHierarchyIdToField(monthlyDateMatchFormatPercent, "monthly_date_match_format_percent");
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
    public ColumnDatetimeMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnDatetimeMonthlyMonitoringChecksSpec)super.deepClone();
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
        return CheckType.monitoring;
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