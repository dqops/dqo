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
package com.dqops.checks.column.partitioned.acceptedvalues;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.acceptedvalues.*;
import com.dqops.checks.column.checkspecs.strings.*;
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
 * Container of accepted values data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_string_value_in_set_percent", o -> o.dailyPartitionStringValueInSetPercent);
            put("daily_partition_expected_numbers_in_use_count", o -> o.dailyPartitionExpectedNumbersInUseCount);
            put("daily_partition_expected_strings_in_use_count", o -> o.dailyPartitionExpectedStringsInUseCount);
            put("daily_partition_expected_strings_in_top_values_count", o -> o.dailyPartitionExpectedStringsInTopValuesCount);
            put("daily_partition_number_value_in_set_percent", o -> o.dailyPartitionNumberValueInSetPercent);
        }
    };

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValueInSetPercentCheckSpec dailyPartitionStringValueInSetPercent;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNumberValueInSetPercentCheckSpec dailyPartitionNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnExpectedStringsInUseCountCheckSpec dailyPartitionExpectedStringsInUseCount;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnExpectedStringsInTopValuesCountCheckSpec dailyPartitionExpectedStringsInTopValuesCount;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnExpectedNumbersInUseCountCheckSpec dailyPartitionExpectedNumbersInUseCount;


    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringValueInSetPercentCheckSpec getDailyPartitionStringValueInSetPercent() {
        return dailyPartitionStringValueInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param dailyPartitionStringValueInSetPercent Minimum strings in set percent check.
     */
    public void setDailyPartitionStringValueInSetPercent(ColumnStringValueInSetPercentCheckSpec dailyPartitionStringValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValueInSetPercent, dailyPartitionStringValueInSetPercent));
        this.dailyPartitionStringValueInSetPercent = dailyPartitionStringValueInSetPercent;
        propagateHierarchyIdToField(dailyPartitionStringValueInSetPercent, "daily_partition_string_value_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getDailyPartitionNumberValueInSetPercent() {
        return dailyPartitionNumberValueInSetPercent;
    }

    /**
     * Sets a new specification of a numbers found percent check.
     * @param dailyPartitionNumberValueInSetPercent Numbers found percent check specification.
     */
    public void setDailyPartitionNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec dailyPartitionNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNumberValueInSetPercent, dailyPartitionNumberValueInSetPercent));
        this.dailyPartitionNumberValueInSetPercent = dailyPartitionNumberValueInSetPercent;
        propagateHierarchyIdToField(dailyPartitionNumberValueInSetPercent, "daily_partition_number_value_in_set_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnExpectedStringsInUseCountCheckSpec getDailyPartitionExpectedStringsInUseCount() {
        return dailyPartitionExpectedStringsInUseCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param dailyPartitionExpectedStringsInUseCount Minimum strings in set count check.
     */
    public void setDailyPartitionExpectedStringsInUseCount(ColumnExpectedStringsInUseCountCheckSpec dailyPartitionExpectedStringsInUseCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionExpectedStringsInUseCount, dailyPartitionExpectedStringsInUseCount));
        this.dailyPartitionExpectedStringsInUseCount = dailyPartitionExpectedStringsInUseCount;
        propagateHierarchyIdToField(dailyPartitionExpectedStringsInUseCount, "daily_partition_expected_strings_in_use_count");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedStringsInTopValuesCountCheckSpec getDailyPartitionExpectedStringsInTopValuesCount() {
        return dailyPartitionExpectedStringsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param dailyPartitionExpectedStringsInTopValuesCount Most popular values count check.
     */
    public void setDailyPartitionExpectedStringsInTopValuesCount(ColumnExpectedStringsInTopValuesCountCheckSpec dailyPartitionExpectedStringsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionExpectedStringsInTopValuesCount, dailyPartitionExpectedStringsInTopValuesCount));
        this.dailyPartitionExpectedStringsInTopValuesCount = dailyPartitionExpectedStringsInTopValuesCount;
        propagateHierarchyIdToField(dailyPartitionExpectedStringsInTopValuesCount, "daily_partition_expected_strings_in_top_values_count");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getDailyPartitionExpectedNumbersInUseCount() {
        return dailyPartitionExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers in set count check.
     * @param dailyPartitionExpectedNumbersInUseCount Numbers in set count check specification.
     */
    public void setDailyPartitionExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec dailyPartitionExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionExpectedNumbersInUseCount, dailyPartitionExpectedNumbersInUseCount));
        this.dailyPartitionExpectedNumbersInUseCount = dailyPartitionExpectedNumbersInUseCount;
        propagateHierarchyIdToField(dailyPartitionExpectedNumbersInUseCount, "daily_partition_expected_numbers_in_use_count");
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
        return CheckTimeScale.daily;
    }
}
