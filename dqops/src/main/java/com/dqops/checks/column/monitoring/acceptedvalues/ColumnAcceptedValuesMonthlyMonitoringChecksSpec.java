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
package com.dqops.checks.column.monitoring.acceptedvalues;

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
 * Container of accepted values data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_string_value_in_set_percent", o -> o.monthlyStringValueInSetPercent);
            put("monthly_number_value_in_set_percent", o -> o.monthlyNumberValueInSetPercent);
            put("monthly_expected_strings_in_use_count", o -> o.monthlyExpectedStringsInUseCount);
            put("monthly_expected_strings_in_top_values_count", o -> o.monthlyExpectedStringsInTopValuesCount);
            put("monthly_expected_numbers_in_use_count", o -> o.monthlyExpectedNumbersInUseCount);
        }
    };

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnStringValueInSetPercentCheckSpec monthlyStringValueInSetPercent;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnNumberValueInSetPercentCheckSpec monthlyNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnExpectedStringsInUseCountCheckSpec monthlyExpectedStringsInUseCount;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnExpectedStringsInTopValuesCountCheckSpec monthlyExpectedStringsInTopValuesCount;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnExpectedNumbersInUseCountCheckSpec monthlyExpectedNumbersInUseCount;


    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringValueInSetPercentCheckSpec getMonthlyStringValueInSetPercent() {
        return monthlyStringValueInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param monthlyStringValueInSetPercent Minimum strings in set percent check.
     */
    public void setMonthlyStringValueInSetPercent(ColumnStringValueInSetPercentCheckSpec monthlyStringValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringValueInSetPercent, monthlyStringValueInSetPercent));
        this.monthlyStringValueInSetPercent = monthlyStringValueInSetPercent;
        propagateHierarchyIdToField(monthlyStringValueInSetPercent, "monthly_string_value_in_set_percent");
    }

    /**
     * Returns a numbers valid percent check specification.
     * @return Numbers valid percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getMonthlyNumberValueInSetPercent() {
        return monthlyNumberValueInSetPercent;
    }

    /**
     * Sets a new specification of a numbers valid percent check.
     * @param monthlyNumberValueInSetPercent Number valid percent check specification.
     */
    public void setMonthlyNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec monthlyNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNumberValueInSetPercent, monthlyNumberValueInSetPercent));
        this.monthlyNumberValueInSetPercent = monthlyNumberValueInSetPercent;
        propagateHierarchyIdToField(monthlyNumberValueInSetPercent, "monthly_number_value_in_set_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnExpectedStringsInUseCountCheckSpec getMonthlyExpectedStringsInUseCount() {
        return monthlyExpectedStringsInUseCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param monthlyExpectedStringsInUseCount Minimum strings in set count check.
     */
    public void setMonthlyExpectedStringsInUseCount(ColumnExpectedStringsInUseCountCheckSpec monthlyExpectedStringsInUseCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyExpectedStringsInUseCount, monthlyExpectedStringsInUseCount));
        this.monthlyExpectedStringsInUseCount = monthlyExpectedStringsInUseCount;
        propagateHierarchyIdToField(monthlyExpectedStringsInUseCount, "monthly_expected_strings_in_use_count");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedStringsInTopValuesCountCheckSpec getMonthlyExpectedStringsInTopValuesCount() {
        return monthlyExpectedStringsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param monthlyExpectedStringsInTopValuesCount Most popular values count check.
     */
    public void setMonthlyExpectedStringsInTopValuesCount(ColumnExpectedStringsInTopValuesCountCheckSpec monthlyExpectedStringsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyExpectedStringsInTopValuesCount, monthlyExpectedStringsInTopValuesCount));
        this.monthlyExpectedStringsInTopValuesCount = monthlyExpectedStringsInTopValuesCount;
        propagateHierarchyIdToField(monthlyExpectedStringsInTopValuesCount, "monthly_expected_strings_in_top_values_count");
    }

    /**
     * Returns a numbers found count check specification.
     * @return Numbers found count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getMonthlyExpectedNumbersInUseCount() {
        return monthlyExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers found count check.
     * @param monthlyExpectedNumbersInUseCount Numbers found count check.
     */
    public void setMonthlyExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec monthlyExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyExpectedNumbersInUseCount, monthlyExpectedNumbersInUseCount));
        this.monthlyExpectedNumbersInUseCount = monthlyExpectedNumbersInUseCount;
        propagateHierarchyIdToField(monthlyExpectedNumbersInUseCount, "monthly_expected_numbers_in_use_count");
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
    public ColumnAcceptedValuesMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnAcceptedValuesMonthlyMonitoringChecksSpec)super.deepClone();
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
