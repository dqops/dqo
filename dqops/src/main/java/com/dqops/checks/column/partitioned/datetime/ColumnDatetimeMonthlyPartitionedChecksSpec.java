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
import com.dqops.checks.column.checkspecs.datetime.ColumnTextMatchDateFormatPercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDateValuesInFuturePercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDateInRangePercentCheckSpec;
import com.dqops.connectors.DataTypeCategory;
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
            put("monthly_partition_date_values_in_future_percent", o -> o.monthlyPartitionDateValuesInFuturePercent);
            put("monthly_partition_date_in_range_percent", o -> o.monthlyPartitionDateInRangePercent);
            put("monthly_partition_text_match_date_format_percent", o -> o.monthlyPartitionTextMatchDateFormatPercent);
        }
    };

    @JsonPropertyDescription("Detects dates in the future in date, datetime and timestamp columns. Measures a percentage of dates in the future. Raises a data quality issue when too many future dates are found. Stores a separate data quality check result for each monthly partition.")
    private ColumnDateValuesInFuturePercentCheckSpec monthlyPartitionDateValuesInFuturePercent;

    @JsonPropertyDescription("Verifies that the dates in date, datetime, or timestamp columns are within a reasonable range of dates. The default configuration detects fake dates such as 1900-01-01 and 2099-12-31. Measures the percentage of valid dates and raises a data quality issue when too many dates are found. Stores a separate data quality check result for each monthly partition.")
    private ColumnDateInRangePercentCheckSpec monthlyPartitionDateInRangePercent;

    @JsonPropertyDescription("Verifies that the values in text columns match one of the predefined date formats, such as an ISO 8601 date. Measures the percentage of valid date strings and raises a data quality issue when too many invalid date strings are found. Stores a separate data quality check result for each monthly partition.")
    private ColumnTextMatchDateFormatPercentCheckSpec monthlyPartitionTextMatchDateFormatPercent;

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
    public ColumnDateInRangePercentCheckSpec getMonthlyPartitionDateInRangePercent() {
        return monthlyPartitionDateInRangePercent;
    }

    /**
     * Sets a new definition of a datetime value in range date percentage check.
     * @param monthlyPartitionDateInRangePercent Datetime value in range date percentage check.
     */
    public void setMonthlyPartitionDateInRangePercent(ColumnDateInRangePercentCheckSpec monthlyPartitionDateInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDateInRangePercent, monthlyPartitionDateInRangePercent));
        this.monthlyPartitionDateInRangePercent = monthlyPartitionDateInRangePercent;
        propagateHierarchyIdToField(monthlyPartitionDateInRangePercent, "monthly_partition_date_in_range_percent");
    }

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnTextMatchDateFormatPercentCheckSpec getMonthlyPartitionTextMatchDateFormatPercent() {
        return monthlyPartitionTextMatchDateFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param monthlyPartitionTextMatchDateFormatPercent Date match format percentage check.
     */
    public void setMonthlyPartitionTextMatchDateFormatPercent(ColumnTextMatchDateFormatPercentCheckSpec monthlyPartitionTextMatchDateFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextMatchDateFormatPercent, monthlyPartitionTextMatchDateFormatPercent));
        this.monthlyPartitionTextMatchDateFormatPercent = monthlyPartitionTextMatchDateFormatPercent;
        propagateHierarchyIdToField(monthlyPartitionTextMatchDateFormatPercent, "monthly_partition_text_match_date_format_percent");
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

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.CONTAINS_DATE;
    }
}
