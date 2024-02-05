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
 * Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 * between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 * This is the configuration for daily partitioned checks that are counted in KPIs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnComparisonDailyPartitionedChecksSpec extends AbstractColumnComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnComparisonDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnComparisonCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_sum_match", o -> o.dailyPartitionSumMatch);
            put("daily_partition_min_match", o -> o.dailyPartitionMinMatch);
            put("daily_partition_max_match", o -> o.dailyPartitionMaxMatch);
            put("daily_partition_mean_match", o -> o.dailyPartitionMeanMatch);
            put("daily_partition_not_null_count_match", o -> o.dailyPartitionNotNullCountMatch);
            put("daily_partition_null_count_match", o -> o.dailyPartitionNullCountMatch);
        }
    };

    @JsonPropertyDescription("Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonSumMatchCheckSpec dailyPartitionSumMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonMinMatchCheckSpec dailyPartitionMinMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonMaxMatchCheckSpec dailyPartitionMaxMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonMeanMatchCheckSpec dailyPartitionMeanMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonNotNullCountMatchCheckSpec dailyPartitionNotNullCountMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).")
    private ColumnComparisonNullCountMatchCheckSpec dailyPartitionNullCountMatch;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnComparisonSumMatchCheckSpec getDailyPartitionSumMatch() {
        return dailyPartitionSumMatch;
    }

    /**
     * Sets a new definition of an Accuracy total sum match percent check.
     * @param dailyPartitionSumMatch accuracy total sum match percent check specification.
     */
    public void setDailyPartitionSumMatch(ColumnComparisonSumMatchCheckSpec dailyPartitionSumMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumMatch, dailyPartitionSumMatch));
        this.dailyPartitionSumMatch = dailyPartitionSumMatch;
        propagateHierarchyIdToField(dailyPartitionSumMatch, "daily_partition_sum_match");
    }

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnComparisonMinMatchCheckSpec getDailyPartitionMinMatch() {
        return dailyPartitionMinMatch;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param dailyPartitionMinMatch Accuracy min percent check specification.
     */
    public void setDailyPartitionMinMatch(ColumnComparisonMinMatchCheckSpec dailyPartitionMinMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinMatch, dailyPartitionMinMatch));
        this.dailyPartitionMinMatch = dailyPartitionMinMatch;
        propagateHierarchyIdToField(dailyPartitionMinMatch, "daily_partition_min_match");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnComparisonMaxMatchCheckSpec getDailyPartitionMaxMatch() {
        return dailyPartitionMaxMatch;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param dailyPartitionMaxMatch Accuracy max percent check specification.
     */
    public void setDailyPartitionMaxMatch(ColumnComparisonMaxMatchCheckSpec dailyPartitionMaxMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxMatch, dailyPartitionMaxMatch));
        this.dailyPartitionMaxMatch = dailyPartitionMaxMatch;
        propagateHierarchyIdToField(dailyPartitionMaxMatch, "daily_partition_max_match");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnComparisonMeanMatchCheckSpec getDailyPartitionMeanMatch() {
        return dailyPartitionMeanMatch;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param dailyPartitionMeanMatch Accuracy average percent check specification.
     */
    public void setDailyPartitionMeanMatch(ColumnComparisonMeanMatchCheckSpec dailyPartitionMeanMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanMatch, dailyPartitionMeanMatch));
        this.dailyPartitionMeanMatch = dailyPartitionMeanMatch;
        propagateHierarchyIdToField(dailyPartitionMeanMatch, "daily_partition_mean_match");
    }

    /**
     * Returns an accuracy not null count percent check specification.
     * @return Accuracy not null count percent check specification.
     */
    public ColumnComparisonNotNullCountMatchCheckSpec getDailyPartitionNotNullCountMatch() {
        return this.dailyPartitionNotNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy not null count percent check.
     * @param dailyPartitionNotNullCountMatch Accuracy not null count percent check specification.
     */
    public void setDailyPartitionNotNullCountMatch(ColumnComparisonNotNullCountMatchCheckSpec dailyPartitionNotNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNotNullCountMatch, dailyPartitionNotNullCountMatch));
        this.dailyPartitionNotNullCountMatch = dailyPartitionNotNullCountMatch;
        propagateHierarchyIdToField(dailyPartitionNotNullCountMatch, "daily_partition_not_null_count_match");
    }

    /**
     * Returns an accuracy null count percent check specification.
     * @return Accuracy null count percent check specification.
     */
    public ColumnComparisonNullCountMatchCheckSpec getDailyPartitionNullCountMatch() {
        return this.dailyPartitionNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy null count percent check.
     * @param dailyPartitionNullCountMatch Accuracy null count percent check specification.
     */
    public void setDailyPartitionNullCountMatch(ColumnComparisonNullCountMatchCheckSpec dailyPartitionNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullCountMatch, dailyPartitionNullCountMatch));
        this.dailyPartitionNullCountMatch = dailyPartitionNullCountMatch;
        propagateHierarchyIdToField(dailyPartitionNullCountMatch, "daily_partition_null_count_match");
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
                if (this.dailyPartitionMinMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyPartitionMinMatch(new ColumnComparisonMinMatchCheckSpec());
                    }
                }

                return this.dailyPartitionMinMatch;
            }

            case max_match: {
                if (this.dailyPartitionMaxMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyPartitionMaxMatch(new ColumnComparisonMaxMatchCheckSpec());
                    }
                }

                return this.dailyPartitionMaxMatch;
            }

            case sum_match: {
                if (this.dailyPartitionSumMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyPartitionSumMatch(new ColumnComparisonSumMatchCheckSpec());
                    }
                }

                return this.dailyPartitionSumMatch;
            }

            case mean_match: {
                if (this.dailyPartitionMeanMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyPartitionMeanMatch(new ColumnComparisonMeanMatchCheckSpec());
                    }
                }

                return this.dailyPartitionMeanMatch;
            }

            case null_count_match: {
                if (this.dailyPartitionNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyPartitionNullCountMatch(new ColumnComparisonNullCountMatchCheckSpec());
                    }
                }

                return this.dailyPartitionNullCountMatch;
            }

            case not_null_count_match: {
                if (this.dailyPartitionNotNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyPartitionNotNullCountMatch(new ColumnComparisonNotNullCountMatchCheckSpec());
                    }
                }

                return this.dailyPartitionNotNullCountMatch;
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
                this.setDailyPartitionMinMatch(null);
                break;

            case max_match:
                this.setDailyPartitionMaxMatch(null);
                break;

            case sum_match:
                this.setDailyPartitionSumMatch(null);
                break;

            case mean_match:
                this.setDailyPartitionMeanMatch(null);
                break;

            case null_count_match:
                this.setDailyPartitionNullCountMatch(null);
                break;

            case not_null_count_match:
                this.setDailyPartitionNotNullCountMatch(null);
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
        return CheckTimeScale.daily;
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
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
    }
}