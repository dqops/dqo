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
package com.dqops.checks.column.monitoring.comparison;

import com.dqops.checks.column.checkspecs.comparison.*;
import com.dqops.checks.comparison.AbstractColumnComparisonCheckCategorySpec;
import com.dqops.checks.comparison.ColumnCompareCheckType;
import com.dqops.checks.comparison.ComparisonCheckRules;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 * between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 * This is the configuration for monthly monitoring checks that are counted in KPIs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnComparisonMonthlyMonitoringChecksSpec extends AbstractColumnComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnComparisonMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnComparisonCheckCategorySpec.FIELDS) {
        {
            put("monthly_sum_match", o -> o.monthlySumMatch);
            put("monthly_min_match", o -> o.monthlyMinMatch);
            put("monthly_max_match", o -> o.monthlyMaxMatch);
            put("monthly_mean_match", o -> o.monthlyMeanMatch);
            put("monthly_not_null_count_match", o -> o.monthlyNotNullCountMatch);
            put("monthly_null_count_match", o -> o.monthlyNullCountMatch);
        }
    };

    @JsonPropertyDescription("Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnComparisonSumMatchCheckSpec monthlySumMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnComparisonMinMatchCheckSpec monthlyMinMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnComparisonMaxMatchCheckSpec monthlyMaxMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnComparisonMeanMatchCheckSpec monthlyMeanMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnComparisonNotNullCountMatchCheckSpec monthlyNotNullCountMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnComparisonNullCountMatchCheckSpec monthlyNullCountMatch;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnComparisonSumMatchCheckSpec getMonthlySumMatch() {
        return monthlySumMatch;
    }

    /**
     * Sets a new definition of an Accuracy total sum match percent check.
     * @param monthlySumMatch accuracy total sum match percent check specification.
     */
    public void setMonthlySumMatch(ColumnComparisonSumMatchCheckSpec monthlySumMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlySumMatch, monthlySumMatch));
        this.monthlySumMatch = monthlySumMatch;
        propagateHierarchyIdToField(monthlySumMatch, "monthly_sum_match");
    }

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnComparisonMinMatchCheckSpec getMonthlyMinMatch() {
        return monthlyMinMatch;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param monthlyMinMatch Accuracy min percent check specification.
     */
    public void setMonthlyMinMatch(ColumnComparisonMinMatchCheckSpec monthlyMinMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyMinMatch, monthlyMinMatch));
        this.monthlyMinMatch = monthlyMinMatch;
        propagateHierarchyIdToField(monthlyMinMatch, "monthly_min_match");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnComparisonMaxMatchCheckSpec getMonthlyMaxMatch() {
        return monthlyMaxMatch;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param monthlyMaxMatch Accuracy max percent check specification.
     */
    public void setMonthlyMaxMatch(ColumnComparisonMaxMatchCheckSpec monthlyMaxMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyMaxMatch, monthlyMaxMatch));
        this.monthlyMaxMatch = monthlyMaxMatch;
        propagateHierarchyIdToField(monthlyMaxMatch, "monthly_max_match");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnComparisonMeanMatchCheckSpec getMonthlyMeanMatch() {
        return monthlyMeanMatch;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param monthlyMeanMatch Accuracy average percent check specification.
     */
    public void setMonthlyMeanMatch(ColumnComparisonMeanMatchCheckSpec monthlyMeanMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyMeanMatch, monthlyMeanMatch));
        this.monthlyMeanMatch = monthlyMeanMatch;
        propagateHierarchyIdToField(monthlyMeanMatch, "monthly_mean_match");
    }

    /**
     * Returns an accuracy not null count percent check specification.
     * @return Accuracy not null count percent check specification.
     */
    public ColumnComparisonNotNullCountMatchCheckSpec getMonthlyNotNullCountMatch() {
        return this.monthlyNotNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy not null count percent check.
     * @param monthlyNotNullCountMatch Accuracy not null count percent check specification.
     */
    public void setMonthlyNotNullCountMatch(ColumnComparisonNotNullCountMatchCheckSpec monthlyNotNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyNotNullCountMatch, monthlyNotNullCountMatch));
        this.monthlyNotNullCountMatch = monthlyNotNullCountMatch;
        propagateHierarchyIdToField(monthlyNotNullCountMatch, "monthly_not_null_count_match");
    }

    /**
     * Returns an accuracy null count percent check specification.
     * @return Accuracy null count percent check specification.
     */
    public ColumnComparisonNullCountMatchCheckSpec getMonthlyNullCountMatch() {
        return this.monthlyNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy null count percent check.
     * @param monthlyNullCountMatch Accuracy null count percent check specification.
     */
    public void setMonthlyNullCountMatch(ColumnComparisonNullCountMatchCheckSpec monthlyNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyNullCountMatch, monthlyNullCountMatch));
        this.monthlyNullCountMatch = monthlyNullCountMatch;
        propagateHierarchyIdToField(monthlyNullCountMatch, "monthly_null_count_match");
    }

    /**
     * Returns the check specification for the given check type or null when it is not present and <code>createWhenMissing</code> is false.
     *
     * @param columnCompareCheckType Compare check type.
     * @param createWhenMissing      When true and the check specification is not present, it is created, added to the check compare container and returned.
     * @return Check specification or null (when <code>createWhenMissing</code> is false).
     */
    @Override
    public ComparisonCheckRules getCheckSpec(ColumnCompareCheckType columnCompareCheckType, boolean createWhenMissing) {
        switch (columnCompareCheckType) {
            case min_match: {
                if (this.monthlyMinMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyMinMatch(new ColumnComparisonMinMatchCheckSpec());
                    }
                }

                return this.monthlyMinMatch;
            }

            case max_match: {
                if (this.monthlyMaxMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyMaxMatch(new ColumnComparisonMaxMatchCheckSpec());
                    }
                }

                return this.monthlyMaxMatch;
            }

            case sum_match: {
                if (this.monthlySumMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlySumMatch(new ColumnComparisonSumMatchCheckSpec());
                    }
                }

                return this.monthlySumMatch;
            }

            case mean_match: {
                if (this.monthlyMeanMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyMeanMatch(new ColumnComparisonMeanMatchCheckSpec());
                    }
                }

                return this.monthlyMeanMatch;
            }

            case null_count_match: {
                if (this.monthlyNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyNullCountMatch(new ColumnComparisonNullCountMatchCheckSpec());
                    }
                }

                return this.monthlyNullCountMatch;
            }

            case not_null_count_match: {
                if (this.monthlyNotNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyNotNullCountMatch(new ColumnComparisonNotNullCountMatchCheckSpec());
                    }
                }

                return this.monthlyNotNullCountMatch;
            }

            default:
                return null;
        }
    }

    /**
     * Removes the check specification for the given check.
     *
     * @param columnCompareCheckType Check type.
     */
    @Override
    public void removeCheckSpec(ColumnCompareCheckType columnCompareCheckType) {
        switch (columnCompareCheckType) {
            case min_match:
                this.setMonthlyMinMatch(null);
                break;

            case max_match:
                this.setMonthlyMaxMatch(null);
                break;

            case sum_match:
                this.setMonthlySumMatch(null);
                break;

            case mean_match:
                this.setMonthlyMeanMatch(null);
                break;

            case null_count_match:
                this.setMonthlyNullCountMatch(null);
                break;

            case not_null_count_match:
                this.setMonthlyNotNullCountMatch(null);
                break;
        }
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