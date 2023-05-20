/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.checks.column.recurring.accuracy;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.accuracy.ColumnAccuracyAverageMatchPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.accuracy.ColumnAccuracyMaxMatchPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.accuracy.ColumnAccuracyMinMatchPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.accuracy.ColumnAccuracyTotalSumMatchPercentCheckSpec;
import ai.dqo.checks.column.checkspecs.accuracy.ColumnAccuracyNotNullCountMatchPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of accuracy data quality recurring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAccuracyDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAccuracyDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_total_sum_match_percent", o -> o.dailyTotalSumMatchPercent);
            put("daily_min_match_percent", o -> o.dailyMinMatchPercent);
            put("daily_max_match_percent", o -> o.dailyMaxMatchPercent);
            put("daily_average_match_percent", o -> o.dailyAverageMatchPercent);
            put("daily_not_null_count_match_percent", o -> o.dailyNotNullCountMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of difference in sum of a column in a table and sum of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnAccuracyTotalSumMatchPercentCheckSpec dailyTotalSumMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnAccuracyMinMatchPercentCheckSpec dailyMinMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnAccuracyMaxMatchPercentCheckSpec dailyMaxMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnAccuracyAverageMatchPercentCheckSpec dailyAverageMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnAccuracyNotNullCountMatchPercentCheckSpec dailyNotNullCountMatchPercent;

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
    public ColumnAccuracyMinMatchPercentCheckSpec getDailyMinMatchPercent() {
        return dailyMinMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param dailyMinMatchPercent Accuracy min percent check specification.
     */
    public void setDailyMinMatchPercent(ColumnAccuracyMinMatchPercentCheckSpec dailyMinMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyMinMatchPercent, dailyMinMatchPercent));
        this.dailyMinMatchPercent = dailyMinMatchPercent;
        propagateHierarchyIdToField(dailyMinMatchPercent, "daily_min_match_percent");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnAccuracyMaxMatchPercentCheckSpec getDailyMaxMatchPercent() {
        return dailyMaxMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param dailyMaxMatchPercent Accuracy max percent check specification.
     */
    public void setDailyMaxMatchPercent(ColumnAccuracyMaxMatchPercentCheckSpec dailyMaxMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyMaxMatchPercent, dailyMaxMatchPercent));
        this.dailyMaxMatchPercent = dailyMaxMatchPercent;
        propagateHierarchyIdToField(dailyMaxMatchPercent, "daily_max_match_percent");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnAccuracyAverageMatchPercentCheckSpec getDailyAverageMatchPercent() {
        return dailyAverageMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param dailyAverageMatchPercent Accuracy average percent check specification.
     */
    public void setDailyAverageMatchPercent(ColumnAccuracyAverageMatchPercentCheckSpec dailyAverageMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyAverageMatchPercent, dailyAverageMatchPercent));
        this.dailyAverageMatchPercent = dailyAverageMatchPercent;
        propagateHierarchyIdToField(dailyAverageMatchPercent, "daily_average_match_percent");
    }

    /**
     * Returns an accuracy row count percent check specification.
     * @return Accuracy row count percent check specification.
     */
    public ColumnAccuracyNotNullCountMatchPercentCheckSpec getDailyNotNullCountMatchPercent() {
        return dailyNotNullCountMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy row count percent check.
     * @param dailyNotNullCountMatchPercent Accuracy row count percent check specification.
     */
    public void setDailyNotNullCountMatchPercent(ColumnAccuracyNotNullCountMatchPercentCheckSpec dailyNotNullCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNotNullCountMatchPercent, dailyNotNullCountMatchPercent));
        this.dailyNotNullCountMatchPercent = dailyNotNullCountMatchPercent;
        propagateHierarchyIdToField(dailyNotNullCountMatchPercent, "daily_not_null_count_match_percent");
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