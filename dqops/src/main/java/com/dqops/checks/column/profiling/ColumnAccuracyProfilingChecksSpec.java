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

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.accuracy.*;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for accuracy.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAccuracyProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAccuracyProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_total_sum_match_percent", o -> o.profileTotalSumMatchPercent);
            put("profile_total_min_match_percent", o -> o.profileTotalMinMatchPercent);
            put("profile_total_max_match_percent", o -> o.profileTotalMaxMatchPercent);
            put("profile_total_average_match_percent", o -> o.profileTotalAverageMatchPercent);
            put("profile_total_not_null_count_match_percent", o -> o.profileTotalNotNullCountMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.")
    private ColumnAccuracyTotalSumMatchPercentCheckSpec profileTotalSumMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number.")
    private ColumnAccuracyTotalMinMatchPercentCheckSpec profileTotalMinMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number.")
    private ColumnAccuracyTotalMaxMatchPercentCheckSpec profileTotalMaxMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.")
    private ColumnAccuracyTotalAverageMatchPercentCheckSpec profileTotalAverageMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec profileTotalNotNullCountMatchPercent;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnAccuracyTotalSumMatchPercentCheckSpec getProfileTotalSumMatchPercent() {
        return profileTotalSumMatchPercent;
    }

    /**
     * Sets a new definition of an Accuracy total sum match percent check.
     * @param profileTotalSumMatchPercent accuracy total sum match percent check specification.
     */
    public void setProfileTotalSumMatchPercent(ColumnAccuracyTotalSumMatchPercentCheckSpec profileTotalSumMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTotalSumMatchPercent, profileTotalSumMatchPercent));
        this.profileTotalSumMatchPercent = profileTotalSumMatchPercent;
        propagateHierarchyIdToField(profileTotalSumMatchPercent, "profile_total_sum_match_percent");
    }

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnAccuracyTotalMinMatchPercentCheckSpec getProfileTotalMinMatchPercent() {
        return profileTotalMinMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param profileTotalMinMatchPercent Accuracy min percent check specification.
     */
    public void setProfileTotalMinMatchPercent(ColumnAccuracyTotalMinMatchPercentCheckSpec profileTotalMinMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTotalMinMatchPercent, profileTotalMinMatchPercent));
        this.profileTotalMinMatchPercent = profileTotalMinMatchPercent;
        propagateHierarchyIdToField(profileTotalMinMatchPercent, "profile_total_min_match_percent");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnAccuracyTotalMaxMatchPercentCheckSpec getProfileTotalMaxMatchPercent() {
        return profileTotalMaxMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param profileTotalMaxMatchPercent Accuracy max percent check specification.
     */
    public void setProfileTotalMaxMatchPercent(ColumnAccuracyTotalMaxMatchPercentCheckSpec profileTotalMaxMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTotalMaxMatchPercent, profileTotalMaxMatchPercent));
        this.profileTotalMaxMatchPercent = profileTotalMaxMatchPercent;
        propagateHierarchyIdToField(profileTotalMaxMatchPercent, "profile_total_max_match_percent");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnAccuracyTotalAverageMatchPercentCheckSpec getProfileTotalAverageMatchPercent() {
        return profileTotalAverageMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param profileTotalAverageMatchPercent Accuracy average percent check specification.
     */
    public void setProfileTotalAverageMatchPercent(ColumnAccuracyTotalAverageMatchPercentCheckSpec profileTotalAverageMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTotalAverageMatchPercent, profileTotalAverageMatchPercent));
        this.profileTotalAverageMatchPercent = profileTotalAverageMatchPercent;
        propagateHierarchyIdToField(profileTotalAverageMatchPercent, "profile_total_average_match_percent");
    }

    /**
     * Returns an accuracy row count percent check specification.
     * @return Accuracy row count percent check specification.
     */
    public ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec getProfileTotalNotNullCountMatchPercent() {
        return profileTotalNotNullCountMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy row count percent check.
     * @param profileTotalNotNullCountMatchPercent Accuracy row count percent check specification.
     */
    public void setProfileTotalNotNullCountMatchPercent(ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec profileTotalNotNullCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTotalNotNullCountMatchPercent, profileTotalNotNullCountMatchPercent));
        this.profileTotalNotNullCountMatchPercent = profileTotalNotNullCountMatchPercent;
        propagateHierarchyIdToField(profileTotalNotNullCountMatchPercent, "profile_total_not_null_count_match_percent");
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