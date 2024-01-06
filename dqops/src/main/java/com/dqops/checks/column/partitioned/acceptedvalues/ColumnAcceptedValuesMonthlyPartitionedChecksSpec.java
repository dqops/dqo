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
 * Container of accepted values data quality partitioned checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_string_value_in_set_percent", o -> o.monthlyPartitionStringValueInSetPercent);
            put("monthly_partition_expected_numbers_in_use_count", o -> o.monthlyPartitionExpectedNumbersInUseCount);
            put("monthly_partition_expected_strings_in_use_count", o -> o.monthlyPartitionExpectedStringsInUseCount);
            put("monthly_partition_expected_strings_in_top_values_count", o -> o.monthlyPartitionExpectedStringsInTopValuesCount);
            put("monthly_partition_number_value_in_set_percent", o -> o.monthlyPartitionNumberValueInSetPercent);
        }
    };

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnStringValueInSetPercentCheckSpec monthlyPartitionStringValueInSetPercent;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnNumberValueInSetPercentCheckSpec monthlyPartitionNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnExpectedStringsInUseCountCheckSpec monthlyPartitionExpectedStringsInUseCount;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnExpectedStringsInTopValuesCountCheckSpec monthlyPartitionExpectedStringsInTopValuesCount;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnExpectedNumbersInUseCountCheckSpec monthlyPartitionExpectedNumbersInUseCount;


    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringValueInSetPercentCheckSpec getMonthlyPartitionStringValueInSetPercent() {
        return monthlyPartitionStringValueInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param monthlyPartitionStringValueInSetPercent Minimum strings in set percent check.
     */
    public void setMonthlyPartitionStringValueInSetPercent(ColumnStringValueInSetPercentCheckSpec monthlyPartitionStringValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringValueInSetPercent, monthlyPartitionStringValueInSetPercent));
        this.monthlyPartitionStringValueInSetPercent = monthlyPartitionStringValueInSetPercent;
        propagateHierarchyIdToField(monthlyPartitionStringValueInSetPercent, "monthly_partition_string_value_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getMonthlyPartitionNumberValueInSetPercent() {
        return monthlyPartitionNumberValueInSetPercent;
    }

    /**
     * Sets a new specification of a numbers found percent check.
     * @param monthlyPartitionNumberValueInSetPercent Numbers found percent check specification.
     */
    public void setMonthlyPartitionNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec monthlyPartitionNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNumberValueInSetPercent, monthlyPartitionNumberValueInSetPercent));
        this.monthlyPartitionNumberValueInSetPercent = monthlyPartitionNumberValueInSetPercent;
        propagateHierarchyIdToField(monthlyPartitionNumberValueInSetPercent, "monthly_partition_number_value_in_set_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnExpectedStringsInUseCountCheckSpec getMonthlyPartitionExpectedStringsInUseCount() {
        return monthlyPartitionExpectedStringsInUseCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param monthlyPartitionExpectedStringsInUseCount Minimum strings in set count check.
     */
    public void setMonthlyPartitionExpectedStringsInUseCount(ColumnExpectedStringsInUseCountCheckSpec monthlyPartitionExpectedStringsInUseCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionExpectedStringsInUseCount, monthlyPartitionExpectedStringsInUseCount));
        this.monthlyPartitionExpectedStringsInUseCount = monthlyPartitionExpectedStringsInUseCount;
        propagateHierarchyIdToField(monthlyPartitionExpectedStringsInUseCount, "monthly_partition_expected_strings_in_use_count");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedStringsInTopValuesCountCheckSpec getMonthlyPartitionExpectedStringsInTopValuesCount() {
        return monthlyPartitionExpectedStringsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param monthlyPartitionExpectedStringsInTopValuesCount Most popular values count check.
     */
    public void setMonthlyPartitionExpectedStringsInTopValuesCount(ColumnExpectedStringsInTopValuesCountCheckSpec monthlyPartitionExpectedStringsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionExpectedStringsInTopValuesCount, monthlyPartitionExpectedStringsInTopValuesCount));
        this.monthlyPartitionExpectedStringsInTopValuesCount = monthlyPartitionExpectedStringsInTopValuesCount;
        propagateHierarchyIdToField(monthlyPartitionExpectedStringsInTopValuesCount, "monthly_partition_expected_strings_in_top_values_count");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getMonthlyPartitionExpectedNumbersInUseCount() {
        return monthlyPartitionExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers in set count check.
     * @param monthlyPartitionExpectedNumbersInUseCount Numbers in set count check specification.
     */
    public void setMonthlyPartitionExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec monthlyPartitionExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionExpectedNumbersInUseCount, monthlyPartitionExpectedNumbersInUseCount));
        this.monthlyPartitionExpectedNumbersInUseCount = monthlyPartitionExpectedNumbersInUseCount;
        propagateHierarchyIdToField(monthlyPartitionExpectedNumbersInUseCount, "monthly_partition_expected_numbers_in_use_count");
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
