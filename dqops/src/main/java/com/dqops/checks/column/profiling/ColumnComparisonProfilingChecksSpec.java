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
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnComparisonProfilingChecksSpec extends AbstractColumnComparisonCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnComparisonProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractColumnComparisonCheckCategorySpec.FIELDS) {
        {
            put("profile_sum_match", o -> o.profileSumMatch);
            put("profile_min_match", o -> o.profileMinMatch);
            put("profile_max_match", o -> o.profileMaxMatch);
            put("profile_mean_match", o -> o.profileMeanMatch);
            put("profile_not_null_count_match", o -> o.profileNotNullCountMatch);
            put("profile_null_count_match", o -> o.profileNullCountMatch);
        }
    };

    @JsonPropertyDescription("Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonSumMatchCheckSpec profileSumMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonMinMatchCheckSpec profileMinMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonMaxMatchCheckSpec profileMaxMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonMeanMatchCheckSpec profileMeanMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonNotNullCountMatchCheckSpec profileNotNullCountMatch;

    @JsonPropertyDescription("Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.")
    private ColumnComparisonNullCountMatchCheckSpec profileNullCountMatch;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnComparisonSumMatchCheckSpec getProfileSumMatch() {
        return profileSumMatch;
    }

    /**
     * Sets a new definition of an Accuracy total sum match percent check.
     * @param profileSumMatch accuracy total sum match percent check specification.
     */
    public void setProfileSumMatch(ColumnComparisonSumMatchCheckSpec profileSumMatch) {
        this.setDirtyIf(!Objects.equals(this.profileSumMatch, profileSumMatch));
        this.profileSumMatch = profileSumMatch;
        propagateHierarchyIdToField(profileSumMatch, "profile_sum_match");
    }

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnComparisonMinMatchCheckSpec getProfileMinMatch() {
        return profileMinMatch;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param profileMinMatch Accuracy min percent check specification.
     */
    public void setProfileMinMatch(ColumnComparisonMinMatchCheckSpec profileMinMatch) {
        this.setDirtyIf(!Objects.equals(this.profileMinMatch, profileMinMatch));
        this.profileMinMatch = profileMinMatch;
        propagateHierarchyIdToField(profileMinMatch, "profile_min_match");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnComparisonMaxMatchCheckSpec getProfileMaxMatch() {
        return profileMaxMatch;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param profileMaxMatch Accuracy max percent check specification.
     */
    public void setProfileMaxMatch(ColumnComparisonMaxMatchCheckSpec profileMaxMatch) {
        this.setDirtyIf(!Objects.equals(this.profileMaxMatch, profileMaxMatch));
        this.profileMaxMatch = profileMaxMatch;
        propagateHierarchyIdToField(profileMaxMatch, "profile_max_match");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnComparisonMeanMatchCheckSpec getProfileMeanMatch() {
        return profileMeanMatch;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param profileMeanMatch Accuracy average percent check specification.
     */
    public void setProfileMeanMatch(ColumnComparisonMeanMatchCheckSpec profileMeanMatch) {
        this.setDirtyIf(!Objects.equals(this.profileMeanMatch, profileMeanMatch));
        this.profileMeanMatch = profileMeanMatch;
        propagateHierarchyIdToField(profileMeanMatch, "profile_mean_match");
    }

    /**
     * Returns an accuracy not null count percent check specification.
     * @return Accuracy not null count percent check specification.
     */
    public ColumnComparisonNotNullCountMatchCheckSpec getProfileNotNullCountMatch() {
        return this.profileNotNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy not null count percent check.
     * @param profileNotNullCountMatch Accuracy not null count percent check specification.
     */
    public void setProfileNotNullCountMatch(ColumnComparisonNotNullCountMatchCheckSpec profileNotNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.profileNotNullCountMatch, profileNotNullCountMatch));
        this.profileNotNullCountMatch = profileNotNullCountMatch;
        propagateHierarchyIdToField(profileNotNullCountMatch, "profile_not_null_count_match");
    }

    /**
     * Returns an accuracy null count percent check specification.
     * @return Accuracy null count percent check specification.
     */
    public ColumnComparisonNullCountMatchCheckSpec getProfileNullCountMatch() {
        return this.profileNullCountMatch;
    }

    /**
     * Sets a new definition of an accuracy null count percent check.
     * @param profileNullCountMatch Accuracy null count percent check specification.
     */
    public void setProfileNullCountMatch(ColumnComparisonNullCountMatchCheckSpec profileNullCountMatch) {
        this.setDirtyIf(!Objects.equals(this.profileNullCountMatch, profileNullCountMatch));
        this.profileNullCountMatch = profileNullCountMatch;
        propagateHierarchyIdToField(profileNullCountMatch, "profile_null_count_match");
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
                if (this.profileMinMatch == null) {
                    if (createWhenMissing) {
                        this.setProfileMinMatch(new ColumnComparisonMinMatchCheckSpec());
                    }
                }

                return this.profileMinMatch;
            }

            case max_match: {
                if (this.profileMaxMatch == null) {
                    if (createWhenMissing) {
                        this.setProfileMaxMatch(new ColumnComparisonMaxMatchCheckSpec());
                    }
                }

                return this.profileMaxMatch;
            }

            case sum_match: {
                if (this.profileSumMatch == null) {
                    if (createWhenMissing) {
                        this.setProfileSumMatch(new ColumnComparisonSumMatchCheckSpec());
                    }
                }

                return this.profileSumMatch;
            }

            case mean_match: {
                if (this.profileMeanMatch == null) {
                    if (createWhenMissing) {
                        this.setProfileMeanMatch(new ColumnComparisonMeanMatchCheckSpec());
                    }
                }

                return this.profileMeanMatch;
            }

            case null_count_match: {
                if (this.profileNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setProfileNullCountMatch(new ColumnComparisonNullCountMatchCheckSpec());
                    }
                }

                return this.profileNullCountMatch;
            }

            case not_null_count_match: {
                if (this.profileNotNullCountMatch == null) {
                    if (createWhenMissing) {
                        this.setProfileNotNullCountMatch(new ColumnComparisonNotNullCountMatchCheckSpec());
                    }
                }

                return this.profileNotNullCountMatch;
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
                this.setProfileMinMatch(null);
                break;

            case max_match:
                this.setProfileMaxMatch(null);
                break;

            case sum_match:
                this.setProfileSumMatch(null);
                break;

            case mean_match:
                this.setProfileMeanMatch(null);
                break;

            case null_count_match:
                this.setProfileNullCountMatch(null);
                break;

            case not_null_count_match:
                this.setProfileNotNullCountMatch(null);
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
        return CheckType.profiling;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return null;
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