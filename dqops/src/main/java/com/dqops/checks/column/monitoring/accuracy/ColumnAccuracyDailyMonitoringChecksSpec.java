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
import com.dqops.checks.column.checkspecs.accuracy.ColumnAccuracyTotalAverageMatchPercentCheckSpec;
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
 * Container of accuracy data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAccuracyDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAccuracyDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_total_sum_match_percent", o -> o.dailyTotalSumMatchPercent);
            put("daily_total_min_match_percent", o -> o.dailyTotalMinMatchPercent);
            put("daily_total_max_match_percent", o -> o.dailyTotalMaxMatchPercent);
            put("daily_total_average_match_percent", o -> o.dailyTotalAverageMatchPercent);
            put("daily_total_not_null_count_match_percent", o -> o.dailyTotalNotNullCountMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnAccuracyTotalSumMatchPercentCheckSpec dailyTotalSumMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnAccuracyTotalMinMatchPercentCheckSpec dailyTotalMinMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnAccuracyTotalMaxMatchPercentCheckSpec dailyTotalMaxMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnAccuracyTotalAverageMatchPercentCheckSpec dailyTotalAverageMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec dailyTotalNotNullCountMatchPercent;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnAccuracyTotalSumMatchPercentCheckSpec getDailyTotalSumMatchPercent() {
        return dailyTotalSumMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy total sum match percent check.
     * @param dailyTotalSumMatchPercent Accuracy total sum match percent check specification.
     */
    public void setDailyTotalSumMatchPercent(ColumnAccuracyTotalSumMatchPercentCheckSpec dailyTotalSumMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTotalSumMatchPercent, dailyTotalSumMatchPercent));
        this.dailyTotalSumMatchPercent = dailyTotalSumMatchPercent;
        propagateHierarchyIdToField(dailyTotalSumMatchPercent, "daily_total_sum_match_percent");
    }


    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnAccuracyTotalMinMatchPercentCheckSpec getDailyTotalMinMatchPercent() {
        return dailyTotalMinMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param dailyTotalMinMatchPercent Accuracy min percent check specification.
     */
    public void setDailyTotalMinMatchPercent(ColumnAccuracyTotalMinMatchPercentCheckSpec dailyTotalMinMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTotalMinMatchPercent, dailyTotalMinMatchPercent));
        this.dailyTotalMinMatchPercent = dailyTotalMinMatchPercent;
        propagateHierarchyIdToField(dailyTotalMinMatchPercent, "daily_total_min_match_percent");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnAccuracyTotalMaxMatchPercentCheckSpec getDailyTotalMaxMatchPercent() {
        return dailyTotalMaxMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param dailyTotalMaxMatchPercent Accuracy max percent check specification.
     */
    public void setDailyTotalMaxMatchPercent(ColumnAccuracyTotalMaxMatchPercentCheckSpec dailyTotalMaxMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTotalMaxMatchPercent, dailyTotalMaxMatchPercent));
        this.dailyTotalMaxMatchPercent = dailyTotalMaxMatchPercent;
        propagateHierarchyIdToField(dailyTotalMaxMatchPercent, "daily_total_max_match_percent");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnAccuracyTotalAverageMatchPercentCheckSpec getDailyTotalAverageMatchPercent() {
        return dailyTotalAverageMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param dailyTotalAverageMatchPercent Accuracy average percent check specification.
     */
    public void setDailyTotalAverageMatchPercent(ColumnAccuracyTotalAverageMatchPercentCheckSpec dailyTotalAverageMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTotalAverageMatchPercent, dailyTotalAverageMatchPercent));
        this.dailyTotalAverageMatchPercent = dailyTotalAverageMatchPercent;
        propagateHierarchyIdToField(dailyTotalAverageMatchPercent, "daily_total_average_match_percent");
    }

    /**
     * Returns an accuracy row count percent check specification.
     * @return Accuracy row count percent check specification.
     */
    public ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec getDailyTotalNotNullCountMatchPercent() {
        return dailyTotalNotNullCountMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy row count percent check.
     * @param dailyTotalNotNullCountMatchPercent Accuracy row count percent check specification.
     */
    public void setDailyTotalNotNullCountMatchPercent(ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec dailyTotalNotNullCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTotalNotNullCountMatchPercent, dailyTotalNotNullCountMatchPercent));
        this.dailyTotalNotNullCountMatchPercent = dailyTotalNotNullCountMatchPercent;
        propagateHierarchyIdToField(dailyTotalNotNullCountMatchPercent, "daily_total_not_null_count_match_percent");
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