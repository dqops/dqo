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
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAccuracyMonthlyRecurringSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAccuracyMonthlyRecurringSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_total_sum_match_percent", o -> o.monthlyTotalSumMatchPercent);
            put("monthly_min_match_percent", o -> o.monthlyMinMatchPercent);
            put("monthly_max_match_percent", o -> o.monthlyMaxMatchPercent);
            put("monthly_average_match_percent", o -> o.monthlyAverageMatchPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the percentage of difference in sum of a column in a table and sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnAccuracyTotalSumMatchPercentCheckSpec monthlyTotalSumMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnAccuracyMinMatchPercentCheckSpec monthlyMinMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnAccuracyMaxMatchPercentCheckSpec monthlyMaxMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnAccuracyAverageMatchPercentCheckSpec monthlyAverageMatchPercent;

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
    public ColumnAccuracyMinMatchPercentCheckSpec getMonthlyMinMatchPercent() {
        return monthlyMinMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param monthlyMinMatchPercent Accuracy min percent check specification.
     */
    public void setMonthlyMinMatchPercent(ColumnAccuracyMinMatchPercentCheckSpec monthlyMinMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyMinMatchPercent, monthlyMinMatchPercent));
        this.monthlyMinMatchPercent = monthlyMinMatchPercent;
        propagateHierarchyIdToField(monthlyMinMatchPercent, "monthly_min_match_percent");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnAccuracyMaxMatchPercentCheckSpec getMonthlyMaxMatchPercent() {
        return monthlyMaxMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param monthlyMaxMatchPercent Accuracy max percent check specification.
     */
    public void setMonthlyMaxMatchPercent(ColumnAccuracyMaxMatchPercentCheckSpec monthlyMaxMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyMaxMatchPercent, monthlyMaxMatchPercent));
        this.monthlyMaxMatchPercent = monthlyMaxMatchPercent;
        propagateHierarchyIdToField(monthlyMaxMatchPercent, "monthly_max_match_percent");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnAccuracyAverageMatchPercentCheckSpec getMonthlyAverageMatchPercent() {
        return monthlyAverageMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param monthlyAverageMatchPercent Accuracy average percent check specification.
     */
    public void setMonthlyAverageMatchPercent(ColumnAccuracyAverageMatchPercentCheckSpec monthlyAverageMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyAverageMatchPercent, monthlyAverageMatchPercent));
        this.monthlyAverageMatchPercent = monthlyAverageMatchPercent;
        propagateHierarchyIdToField(monthlyAverageMatchPercent, "monthly_average_match_percent");
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