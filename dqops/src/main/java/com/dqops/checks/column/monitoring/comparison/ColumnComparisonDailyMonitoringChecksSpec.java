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
 * This is the configuration for daily monitoring checks that are counted in KPIs.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnComparisonDailyMonitoringChecksSpec extends AbstractColumnComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnComparisonDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnComparisonCheckCategorySpec.FIELDS) {
        {
            put("daily_sum_match", o -> o.dailySumMatch);
            put("daily_min_match", o -> o.dailyMinMatch);
            put("daily_max_match", o -> o.dailyMaxMatch);
            put("daily_mean_match", o -> o.dailyMeanMatch);
            put("daily_not_null_count_match", o -> o.dailyNotNullCountMatch);
            put("daily_null_count_match", o -> o.dailyNullCountMatch);
        }
    };

    @JsonPropertyDescription("Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnComparisonSumMatchCheckSpec dailySumMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnComparisonMinMatchCheckSpec dailyMinMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnComparisonMaxMatchCheckSpec dailyMaxMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnComparisonMeanMatchCheckSpec dailyMeanMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnComparisonNotNullCountMatchCheckSpec dailyNotNullCountMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnComparisonNullCountMatchCheckSpec dailyNullCountMatch;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnComparisonSumMatchCheckSpec getDailySumMatch() {
        return dailySumMatch;
    }

    /**
     * Sets a new definition of an Accuracy total sum match percent check.
     * @param dailySumMatch accuracy total sum match percent check specification.
     */
    public void setDailySumMatch(ColumnComparisonSumMatchCheckSpec dailySumMatch) {
        this.setDirtyIf(!Objects.equals(this.dailySumMatch, dailySumMatch));
        this.dailySumMatch = dailySumMatch;
        propagateHierarchyIdToField(dailySumMatch, "daily_sum_match");
    }

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnComparisonMinMatchCheckSpec getDailyMinMatch() {
        return dailyMinMatch;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param dailyMinMatch Accuracy min percent check specification.
     */
    public void setDailyMinMatch(ColumnComparisonMinMatchCheckSpec dailyMinMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyMinMatch, dailyMinMatch));
        this.dailyMinMatch = dailyMinMatch;
        propagateHierarchyIdToField(dailyMinMatch, "daily_min_match");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnComparisonMaxMatchCheckSpec getDailyMaxMatch() {
        return dailyMaxMatch;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param dailyMaxMatch Accuracy max percent check specification.
     */
    public void setDailyMaxMatch(ColumnComparisonMaxMatchCheckSpec dailyMaxMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyMaxMatch, dailyMaxMatch));
        this.dailyMaxMatch = dailyMaxMatch;
        propagateHierarchyIdToField(dailyMaxMatch, "daily_max_match");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnComparisonMeanMatchCheckSpec getDailyMeanMatch() {
        return dailyMeanMatch;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param dailyMeanMatch Accuracy average percent check specification.
     */
    public void setDailyMeanMatch(ColumnComparisonMeanMatchCheckSpec dailyMeanMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanMatch, dailyMeanMatch));
        this.dailyMeanMatch = dailyMeanMatch;
        propagateHierarchyIdToField(dailyMeanMatch, "daily_mean_match");
    }

    /**
     * Returns an accuracy not null count percent check specification.
     * @return Accuracy not null count percent check specification.
     */
    public ColumnComparisonNotNullCountMatchCheckSpec getDailyNotNullCountMatch() {
        return this.dailyNotNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy not null count percent check.
     * @param dailyNotNullCountMatch Accuracy not null count percent check specification.
     */
    public void setDailyNotNullCountMatch(ColumnComparisonNotNullCountMatchCheckSpec dailyNotNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyNotNullCountMatch, dailyNotNullCountMatch));
        this.dailyNotNullCountMatch = dailyNotNullCountMatch;
        propagateHierarchyIdToField(dailyNotNullCountMatch, "daily_not_null_count_match");
    }

    /**
     * Returns an accuracy null count percent check specification.
     * @return Accuracy null count percent check specification.
     */
    public ColumnComparisonNullCountMatchCheckSpec getDailyNullCountMatch() {
        return this.dailyNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy null count percent check.
     * @param dailyNullCountMatch Accuracy null count percent check specification.
     */
    public void setDailyNullCountMatch(ColumnComparisonNullCountMatchCheckSpec dailyNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.dailyNullCountMatch, dailyNullCountMatch));
        this.dailyNullCountMatch = dailyNullCountMatch;
        propagateHierarchyIdToField(dailyNullCountMatch, "daily_null_count_match");
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
                if (this.dailyMinMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyMinMatch(new ColumnComparisonMinMatchCheckSpec());
                    }
                }

                return this.dailyMinMatch;
            }

            case max_match: {
                if (this.dailyMaxMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyMaxMatch(new ColumnComparisonMaxMatchCheckSpec());
                    }
                }

                return this.dailyMaxMatch;
            }

            case sum_match: {
                if (this.dailySumMatch == null) {
                    if (createWhenMissing) {
                        this.setDailySumMatch(new ColumnComparisonSumMatchCheckSpec());
                    }
                }

                return this.dailySumMatch;
            }

            case mean_match: {
                if (this.dailyMeanMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyMeanMatch(new ColumnComparisonMeanMatchCheckSpec());
                    }
                }

                return this.dailyMeanMatch;
            }

            case null_count_match: {
                if (this.dailyNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyNullCountMatch(new ColumnComparisonNullCountMatchCheckSpec());
                    }
                }

                return this.dailyNullCountMatch;
            }

            case not_null_count_match: {
                if (this.dailyNotNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setDailyNotNullCountMatch(new ColumnComparisonNotNullCountMatchCheckSpec());
                    }
                }

                return this.dailyNotNullCountMatch;
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
                this.setDailyMinMatch(null);
                break;

            case max_match:
                this.setDailyMaxMatch(null);
                break;

            case sum_match:
                this.setDailySumMatch(null);
                break;

            case mean_match:
                this.setDailyMeanMatch(null);
                break;

            case null_count_match:
                this.setDailyNullCountMatch(null);
                break;

            case not_null_count_match:
                this.setDailyNotNullCountMatch(null);
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