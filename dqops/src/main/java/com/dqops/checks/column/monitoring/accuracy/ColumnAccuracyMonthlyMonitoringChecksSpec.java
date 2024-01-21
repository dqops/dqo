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
package com.dqops.checks.column.monitoring.accuracy;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.accuracy.*;
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
 * Container of accuracy data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAccuracyMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAccuracyMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_total_sum_match_percent", o -> o.monthlyTotalSumMatchPercent);
            put("monthly_total_min_match_percent", o -> o.monthlyTotalMinMatchPercent);
            put("monthly_total_max_match_percent", o -> o.monthlyTotalMaxMatchPercent);
            put("monthly_total_average_match_percent", o -> o.monthlyTotalAverageMatchPercent);
            put("monthly_total_not_null_count_match_percent", o -> o.monthlyTotalNotNullCountMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnAccuracyTotalSumMatchPercentCheckSpec monthlyTotalSumMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnAccuracyTotalMinMatchPercentCheckSpec monthlyTotalMinMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnAccuracyTotalMaxMatchPercentCheckSpec monthlyTotalMaxMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnAccuracyTotalAverageMatchPercentCheckSpec monthlyTotalAverageMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec monthlyTotalNotNullCountMatchPercent;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnAccuracyTotalSumMatchPercentCheckSpec getMonthlyTotalSumMatchPercent() {
        return monthlyTotalSumMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy total sum match percent check.
     * @param monthlyTotalSumMatchPercent Accuracy total sum match percent check specification.
     */
    public void setMonthlyTotalSumMatchPercent(ColumnAccuracyTotalSumMatchPercentCheckSpec monthlyTotalSumMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTotalSumMatchPercent, monthlyTotalSumMatchPercent));
        this.monthlyTotalSumMatchPercent = monthlyTotalSumMatchPercent;
        propagateHierarchyIdToField(monthlyTotalSumMatchPercent, "monthly_total_sum_match_percent");
    }

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnAccuracyTotalMinMatchPercentCheckSpec getMonthlyTotalMinMatchPercent() {
        return monthlyTotalMinMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param monthlyTotalMinMatchPercent Accuracy min percent check specification.
     */
    public void setMonthlyTotalMinMatchPercent(ColumnAccuracyTotalMinMatchPercentCheckSpec monthlyTotalMinMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTotalMinMatchPercent, monthlyTotalMinMatchPercent));
        this.monthlyTotalMinMatchPercent = monthlyTotalMinMatchPercent;
        propagateHierarchyIdToField(monthlyTotalMinMatchPercent, "monthly_total_min_match_percent");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnAccuracyTotalMaxMatchPercentCheckSpec getMonthlyTotalMaxMatchPercent() {
        return monthlyTotalMaxMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param monthlyTotalMaxMatchPercent Accuracy max percent check specification.
     */
    public void setMonthlyTotalMaxMatchPercent(ColumnAccuracyTotalMaxMatchPercentCheckSpec monthlyTotalMaxMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTotalMaxMatchPercent, monthlyTotalMaxMatchPercent));
        this.monthlyTotalMaxMatchPercent = monthlyTotalMaxMatchPercent;
        propagateHierarchyIdToField(monthlyTotalMaxMatchPercent, "monthly_total_max_match_percent");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnAccuracyTotalAverageMatchPercentCheckSpec getMonthlyTotalAverageMatchPercent() {
        return monthlyTotalAverageMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param monthlyTotalAverageMatchPercent Accuracy average percent check specification.
     */
    public void setMonthlyTotalAverageMatchPercent(ColumnAccuracyTotalAverageMatchPercentCheckSpec monthlyTotalAverageMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTotalAverageMatchPercent, monthlyTotalAverageMatchPercent));
        this.monthlyTotalAverageMatchPercent = monthlyTotalAverageMatchPercent;
        propagateHierarchyIdToField(monthlyTotalAverageMatchPercent, "monthly_total_average_match_percent");
    }

    /**
     * Returns an accuracy row count percent check specification.
     * @return Accuracy row count percent check specification.
     */
    public ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec getMonthlyTotalNotNullCountMatchPercent() {
        return monthlyTotalNotNullCountMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy row count percent check.
     * @param monthlyTotalNotNullCountMatchPercent Accuracy row count percent check specification.
     */
    public void setMonthlyTotalNotNullCountMatchPercent(ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec monthlyTotalNotNullCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTotalNotNullCountMatchPercent, monthlyTotalNotNullCountMatchPercent));
        this.monthlyTotalNotNullCountMatchPercent = monthlyTotalNotNullCountMatchPercent;
        propagateHierarchyIdToField(monthlyTotalNotNullCountMatchPercent, "monthly_total_not_null_count_match_percent");
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