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
package com.dqops.checks.column.profiling;

import com.dqops.checks.column.checkspecs.comparison.*;
import com.dqops.checks.comparison.AbstractColumnComparisonCheckCategorySpec;
import com.dqops.checks.comparison.ColumnCompareCheckType;
import com.dqops.checks.comparison.ComparisonCheckRules;
import com.dqops.checks.table.checkspecs.comparison.TableComparisonRowCountMatchCheckSpec;
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
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnComparisonProfilingChecksSpec extends AbstractColumnComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnComparisonProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnComparisonCheckCategorySpec.FIELDS) {
        {
            put("sum_match", o -> o.sumMatch);
            put("min_match", o -> o.minMatch);
            put("max_match", o -> o.maxMatch);
            put("mean_match", o -> o.meanMatch);
            put("not_null_count_match", o -> o.notNullCountMatch);
            put("null_count_match", o -> o.nullCountMatch);
        }
    };

    @JsonPropertyDescription("Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonSumMatchCheckSpec sumMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonMinMatchCheckSpec minMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonMaxMatchCheckSpec maxMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonMeanMatchCheckSpec meanMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonNotNullCountMatchCheckSpec notNullCountMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonNullCountMatchCheckSpec nullCountMatch;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnComparisonSumMatchCheckSpec getSumMatch() {
        return sumMatch;
    }

    /**
     * Sets a new definition of an Accuracy total sum match percent check.
     * @param sumMatch accuracy total sum match percent check specification.
     */
    public void setSumMatch(ColumnComparisonSumMatchCheckSpec sumMatch) {
        this.setDirtyIf(!Objects.equals(this.sumMatch, sumMatch));
        this.sumMatch = sumMatch;
        propagateHierarchyIdToField(sumMatch, "sum_match");
    }

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnComparisonMinMatchCheckSpec getMinMatch() {
        return minMatch;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param minMatch Accuracy min percent check specification.
     */
    public void setMinMatch(ColumnComparisonMinMatchCheckSpec minMatch) {
        this.setDirtyIf(!Objects.equals(this.minMatch, minMatch));
        this.minMatch = minMatch;
        propagateHierarchyIdToField(minMatch, "min_match");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnComparisonMaxMatchCheckSpec getMaxMatch() {
        return maxMatch;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param maxMatch Accuracy max percent check specification.
     */
    public void setMaxMatch(ColumnComparisonMaxMatchCheckSpec maxMatch) {
        this.setDirtyIf(!Objects.equals(this.maxMatch, maxMatch));
        this.maxMatch = maxMatch;
        propagateHierarchyIdToField(maxMatch, "max_match");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnComparisonMeanMatchCheckSpec getMeanMatch() {
        return meanMatch;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param meanMatch Accuracy average percent check specification.
     */
    public void setMeanMatch(ColumnComparisonMeanMatchCheckSpec meanMatch) {
        this.setDirtyIf(!Objects.equals(this.meanMatch, meanMatch));
        this.meanMatch = meanMatch;
        propagateHierarchyIdToField(meanMatch, "mean_match");
    }

    /**
     * Returns an accuracy not null count percent check specification.
     * @return Accuracy not null count percent check specification.
     */
    public ColumnComparisonNotNullCountMatchCheckSpec getNotNullCountMatch() {
        return this.notNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy not null count percent check.
     * @param notNullCountMatch Accuracy not null count percent check specification.
     */
    public void setNotNullCountMatch(ColumnComparisonNotNullCountMatchCheckSpec notNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.notNullCountMatch, notNullCountMatch));
        this.notNullCountMatch = notNullCountMatch;
        propagateHierarchyIdToField(notNullCountMatch, "not_null_count_match");
    }

    /**
     * Returns an accuracy null count percent check specification.
     * @return Accuracy null count percent check specification.
     */
    public ColumnComparisonNullCountMatchCheckSpec getNullCountMatch() {
        return this.nullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy null count percent check.
     * @param nullCountMatch Accuracy null count percent check specification.
     */
    public void setNullCountMatch(ColumnComparisonNullCountMatchCheckSpec nullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.nullCountMatch, nullCountMatch));
        this.nullCountMatch = nullCountMatch;
        propagateHierarchyIdToField(nullCountMatch, "null_count_match");
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
                if (this.minMatch == null) {
                    if (createWhenMissing) {
                        this.setMinMatch(new ColumnComparisonMinMatchCheckSpec());
                    }
                }

                return this.minMatch;
            }

            case max_match: {
                if (this.maxMatch == null) {
                    if (createWhenMissing) {
                        this.setMaxMatch(new ColumnComparisonMaxMatchCheckSpec());
                    }
                }

                return this.maxMatch;
            }

            case sum_match: {
                if (this.sumMatch == null) {
                    if (createWhenMissing) {
                        this.setSumMatch(new ColumnComparisonSumMatchCheckSpec());
                    }
                }

                return this.sumMatch;
            }

            case mean_match: {
                if (this.meanMatch == null) {
                    if (createWhenMissing) {
                        this.setMeanMatch(new ColumnComparisonMeanMatchCheckSpec());
                    }
                }

                return this.meanMatch;
            }

            case null_count_match: {
                if (this.nullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setNullCountMatch(new ColumnComparisonNullCountMatchCheckSpec());
                    }
                }

                return this.nullCountMatch;
            }

            case not_null_count_match: {
                if (this.notNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setNotNullCountMatch(new ColumnComparisonNotNullCountMatchCheckSpec());
                    }
                }

                return this.notNullCountMatch;
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
                this.setMinMatch(null);
                break;

            case max_match:
                this.setMaxMatch(null);
                break;

            case sum_match:
                this.setSumMatch(null);
                break;

            case mean_match:
                this.setMeanMatch(null);
                break;

            case null_count_match:
                this.setNullCountMatch(null);
                break;

            case not_null_count_match:
                this.setNotNullCountMatch(null);
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