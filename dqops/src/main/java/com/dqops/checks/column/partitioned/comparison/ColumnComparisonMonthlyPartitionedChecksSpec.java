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
package com.dqops.checks.column.partitioned.comparison;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.comparison.*;
import com.dqops.checks.comparison.AbstractColumnComparisonCheckCategorySpec;
import com.dqops.checks.comparison.ColumnCompareCheckType;
import com.dqops.checks.comparison.ComparisonCheckRules;
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
 * Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 * between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 * This is the configuration for daily partitioned checks that are counted in KPIs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnComparisonMonthlyPartitionedChecksSpec extends AbstractColumnComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnComparisonMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnComparisonCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_sum_match", o -> o.monthlyPartitionSumMatch);
            put("monthly_partition_min_match", o -> o.monthlyPartitionMinMatch);
            put("monthly_partition_max_match", o -> o.monthlyPartitionMaxMatch);
            put("monthly_partition_mean_match", o -> o.monthlyPartitionMeanMatch);
            put("monthly_partition_not_null_count_match", o -> o.monthlyPartitionNotNullCountMatch);
            put("monthly_partition_null_count_match", o -> o.monthlyPartitionNullCountMatch);
        }
    };

    @JsonPropertyDescription("Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonSumMatchCheckSpec monthlyPartitionSumMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonMinMatchCheckSpec monthlyPartitionMinMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonMaxMatchCheckSpec monthlyPartitionMaxMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonMeanMatchCheckSpec monthlyPartitionMeanMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonNotNullCountMatchCheckSpec monthlyPartitionNotNullCountMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonNullCountMatchCheckSpec monthlyPartitionNullCountMatch;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnComparisonSumMatchCheckSpec getMonthlyPartitionSumMatch() {
        return monthlyPartitionSumMatch;
    }

    /**
     * Sets a new definition of an Accuracy total sum match percent check.
     * @param monthlyPartitionSumMatch accuracy total sum match percent check specification.
     */
    public void setMonthlyPartitionSumMatch(ColumnComparisonSumMatchCheckSpec monthlyPartitionSumMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionSumMatch, monthlyPartitionSumMatch));
        this.monthlyPartitionSumMatch = monthlyPartitionSumMatch;
        propagateHierarchyIdToField(monthlyPartitionSumMatch, "monthly_partition_sum_match");
    }

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnComparisonMinMatchCheckSpec getMonthlyPartitionMinMatch() {
        return monthlyPartitionMinMatch;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param monthlyPartitionMinMatch Accuracy min percent check specification.
     */
    public void setMonthlyPartitionMinMatch(ColumnComparisonMinMatchCheckSpec monthlyPartitionMinMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMinMatch, monthlyPartitionMinMatch));
        this.monthlyPartitionMinMatch = monthlyPartitionMinMatch;
        propagateHierarchyIdToField(monthlyPartitionMinMatch, "monthly_partition_min_match");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnComparisonMaxMatchCheckSpec getMonthlyPartitionMaxMatch() {
        return monthlyPartitionMaxMatch;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param monthlyPartitionMaxMatch Accuracy max percent check specification.
     */
    public void setMonthlyPartitionMaxMatch(ColumnComparisonMaxMatchCheckSpec monthlyPartitionMaxMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMaxMatch, monthlyPartitionMaxMatch));
        this.monthlyPartitionMaxMatch = monthlyPartitionMaxMatch;
        propagateHierarchyIdToField(monthlyPartitionMaxMatch, "monthly_partition_max_match");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnComparisonMeanMatchCheckSpec getMonthlyPartitionMeanMatch() {
        return monthlyPartitionMeanMatch;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param monthlyPartitionMeanMatch Accuracy average percent check specification.
     */
    public void setMonthlyPartitionMeanMatch(ColumnComparisonMeanMatchCheckSpec monthlyPartitionMeanMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionMeanMatch, monthlyPartitionMeanMatch));
        this.monthlyPartitionMeanMatch = monthlyPartitionMeanMatch;
        propagateHierarchyIdToField(monthlyPartitionMeanMatch, "monthly_partition_mean_match");
    }

    /**
     * Returns an accuracy not null count percent check specification.
     * @return Accuracy not null count percent check specification.
     */
    public ColumnComparisonNotNullCountMatchCheckSpec getMonthlyPartitionNotNullCountMatch() {
        return this.monthlyPartitionNotNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy not null count percent check.
     * @param monthlyPartitionNotNullCountMatch Accuracy not null count percent check specification.
     */
    public void setMonthlyPartitionNotNullCountMatch(ColumnComparisonNotNullCountMatchCheckSpec monthlyPartitionNotNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNotNullCountMatch, monthlyPartitionNotNullCountMatch));
        this.monthlyPartitionNotNullCountMatch = monthlyPartitionNotNullCountMatch;
        propagateHierarchyIdToField(monthlyPartitionNotNullCountMatch, "monthly_partition_not_null_count_match");
    }

    /**
     * Returns an accuracy null count percent check specification.
     * @return Accuracy null count percent check specification.
     */
    public ColumnComparisonNullCountMatchCheckSpec getMonthlyPartitionNullCountMatch() {
        return this.monthlyPartitionNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy null count percent check.
     * @param monthlyPartitionNullCountMatch Accuracy null count percent check specification.
     */
    public void setMonthlyPartitionNullCountMatch(ColumnComparisonNullCountMatchCheckSpec monthlyPartitionNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNullCountMatch, monthlyPartitionNullCountMatch));
        this.monthlyPartitionNullCountMatch = monthlyPartitionNullCountMatch;
        propagateHierarchyIdToField(monthlyPartitionNullCountMatch, "monthly_partition_null_count_match");
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
                if (this.monthlyPartitionMinMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyPartitionMinMatch(new ColumnComparisonMinMatchCheckSpec());
                    }
                }

                return this.monthlyPartitionMinMatch;
            }

            case max_match: {
                if (this.monthlyPartitionMaxMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyPartitionMaxMatch(new ColumnComparisonMaxMatchCheckSpec());
                    }
                }

                return this.monthlyPartitionMaxMatch;
            }

            case sum_match: {
                if (this.monthlyPartitionSumMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyPartitionSumMatch(new ColumnComparisonSumMatchCheckSpec());
                    }
                }

                return this.monthlyPartitionSumMatch;
            }

            case mean_match: {
                if (this.monthlyPartitionMeanMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyPartitionMeanMatch(new ColumnComparisonMeanMatchCheckSpec());
                    }
                }

                return this.monthlyPartitionMeanMatch;
            }

            case null_count_match: {
                if (this.monthlyPartitionNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyPartitionNullCountMatch(new ColumnComparisonNullCountMatchCheckSpec());
                    }
                }

                return this.monthlyPartitionNullCountMatch;
            }

            case not_null_count_match: {
                if (this.monthlyPartitionNotNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setMonthlyPartitionNotNullCountMatch(new ColumnComparisonNotNullCountMatchCheckSpec());
                    }
                }

                return this.monthlyPartitionNotNullCountMatch;
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
                this.setMonthlyPartitionMinMatch(null);
                break;

            case max_match:
                this.setMonthlyPartitionMaxMatch(null);
                break;

            case sum_match:
                this.setMonthlyPartitionSumMatch(null);
                break;

            case mean_match:
                this.setMonthlyPartitionMeanMatch(null);
                break;

            case null_count_match:
                this.setMonthlyPartitionNullCountMatch(null);
                break;

            case not_null_count_match:
                this.setMonthlyPartitionNotNullCountMatch(null);
                break;
        }
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
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }
}