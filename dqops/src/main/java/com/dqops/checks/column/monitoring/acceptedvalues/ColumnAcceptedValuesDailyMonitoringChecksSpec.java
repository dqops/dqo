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
 * Container of accepted values data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_string_value_in_set_percent", o -> o.dailyStringValueInSetPercent);
            put("daily_number_value_in_set_percent", o -> o.dailyNumberValueInSetPercent);
            put("daily_expected_strings_in_use_count", o -> o.dailyExpectedStringsInUseCount);
            put("daily_expected_strings_in_top_values_count", o -> o.dailyExpectedStringsInTopValuesCount);
            put("daily_expected_numbers_in_use_count", o -> o.dailyExpectedNumbersInUseCount);
        }
    };

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnStringValueInSetPercentCheckSpec dailyStringValueInSetPercent;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNumberValueInSetPercentCheckSpec dailyNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnExpectedStringsInUseCountCheckSpec dailyExpectedStringsInUseCount;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnExpectedStringsInTopValuesCountCheckSpec dailyExpectedStringsInTopValuesCount;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnExpectedNumbersInUseCountCheckSpec dailyExpectedNumbersInUseCount;


    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringValueInSetPercentCheckSpec getDailyStringValueInSetPercent() {
        return dailyStringValueInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param dailyStringValueInSetPercent Minimum strings in set percent check.
     */
    public void setDailyStringValueInSetPercent(ColumnStringValueInSetPercentCheckSpec dailyStringValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringValueInSetPercent, dailyStringValueInSetPercent));
        this.dailyStringValueInSetPercent = dailyStringValueInSetPercent;
        propagateHierarchyIdToField(dailyStringValueInSetPercent, "daily_string_value_in_set_percent");
    }

    /**
     * Returns a numbers valid percent check specification.
     * @return Numbers valid percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getDailyNumberValueInSetPercent() {
        return dailyNumberValueInSetPercent;
    }

    /**
     * Sets a new specification of a numbers valid percent check.
     * @param dailyNumberValueInSetPercent Number valid percent check specification.
     */
    public void setDailyNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec dailyNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNumberValueInSetPercent, dailyNumberValueInSetPercent));
        this.dailyNumberValueInSetPercent = dailyNumberValueInSetPercent;
        propagateHierarchyIdToField(dailyNumberValueInSetPercent, "daily_number_value_in_set_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnExpectedStringsInUseCountCheckSpec getDailyExpectedStringsInUseCount() {
        return dailyExpectedStringsInUseCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param dailyExpectedStringsInUseCount Minimum strings in set count check.
     */
    public void setDailyExpectedStringsInUseCount(ColumnExpectedStringsInUseCountCheckSpec dailyExpectedStringsInUseCount) {
        this.setDirtyIf(!Objects.equals(this.dailyExpectedStringsInUseCount, dailyExpectedStringsInUseCount));
        this.dailyExpectedStringsInUseCount = dailyExpectedStringsInUseCount;
        propagateHierarchyIdToField(dailyExpectedStringsInUseCount, "daily_expected_strings_in_use_count");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedStringsInTopValuesCountCheckSpec getDailyExpectedStringsInTopValuesCount() {
        return dailyExpectedStringsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param dailyExpectedStringsInTopValuesCount Most popular values count check.
     */
    public void setDailyExpectedStringsInTopValuesCount(ColumnExpectedStringsInTopValuesCountCheckSpec dailyExpectedStringsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.dailyExpectedStringsInTopValuesCount, dailyExpectedStringsInTopValuesCount));
        this.dailyExpectedStringsInTopValuesCount = dailyExpectedStringsInTopValuesCount;
        propagateHierarchyIdToField(dailyExpectedStringsInTopValuesCount, "daily_expected_strings_in_top_values_count");
    }

    /**
     * Returns a numbers found count check specification.
     * @return Numbers found count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getDailyExpectedNumbersInUseCount() {
        return dailyExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers found count check.
     * @param dailyExpectedNumbersInUseCount Numbers found count check.
     */
    public void setDailyExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec dailyExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.dailyExpectedNumbersInUseCount, dailyExpectedNumbersInUseCount));
        this.dailyExpectedNumbersInUseCount = dailyExpectedNumbersInUseCount;
        propagateHierarchyIdToField(dailyExpectedNumbersInUseCount, "daily_expected_numbers_in_use_count");
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
    public ColumnAcceptedValuesDailyMonitoringChecksSpec deepClone() {
        return (ColumnAcceptedValuesDailyMonitoringChecksSpec)super.deepClone();
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
        return CheckTimeScale.daily;
    }
}
