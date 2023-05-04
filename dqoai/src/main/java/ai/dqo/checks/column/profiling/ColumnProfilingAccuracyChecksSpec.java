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
package ai.dqo.checks.column.profiling;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.accuracy.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class ColumnProfilingAccuracyChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnProfilingAccuracyChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("total_sum_match_percent", o -> o.totalSumMatchPercent);
            put("min_match_percent", o -> o.minMatchPercent);
            put("max_match_percent", o -> o.maxMatchPercent);
            put("average_match_percent", o -> o.averageMatchPercent);
            put("row_count_match_percent", o -> o.rowCountMatchPercent);
        }
    };

    @JsonPropertyDescription("Verifies that percentage of the difference in sum of a column in a table and sum of a column of another table does not exceed the set number.")
    private ColumnAccuracyTotalSumMatchPercentCheckSpec totalSumMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number.")
    private ColumnAccuracyMinMatchPercentCheckSpec minMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number.")
    private ColumnAccuracyMaxMatchPercentCheckSpec maxMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number.")
    private ColumnAccuracyAverageMatchPercentCheckSpec averageMatchPercent;

    @JsonPropertyDescription("Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnAccuracyNotNullCountMatchPercentCheckSpec rowCountMatchPercent;

    /**
     * Returns an accuracy total sum match percent check specification.
     * @return Accuracy total sum match percent check specification.
     */
    public ColumnAccuracyTotalSumMatchPercentCheckSpec getTotalSumMatchPercent() {
        return totalSumMatchPercent;
    }

    /**
     * Sets a new definition of an Accuracy total sum match percent check.
     * @param totalSumMatchPercent accuracy total sum match percent check specification.
     */
    public void setTotalSumMatchPercent(ColumnAccuracyTotalSumMatchPercentCheckSpec totalSumMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.totalSumMatchPercent, totalSumMatchPercent));
        this.totalSumMatchPercent = totalSumMatchPercent;
        propagateHierarchyIdToField(totalSumMatchPercent, "total_sum_match_percent");
    }

    /**
     * Returns an accuracy min percent check specification.
     * @return Accuracy min percent check specification.
     */
    public ColumnAccuracyMinMatchPercentCheckSpec getMinMatchPercent() {
        return minMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy min percent check.
     * @param minMatchPercent Accuracy min percent check specification.
     */
    public void setMinMatchPercent(ColumnAccuracyMinMatchPercentCheckSpec minMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.minMatchPercent, minMatchPercent));
        this.minMatchPercent = minMatchPercent;
        propagateHierarchyIdToField(minMatchPercent, "min_match_percent");
    }

    /**
     * Returns an accuracy max percent check specification.
     * @return Accuracy max percent check specification.
     */
    public ColumnAccuracyMaxMatchPercentCheckSpec getMaxMatchPercent() {
        return maxMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy max percent check.
     * @param maxMatchPercent Accuracy max percent check specification.
     */
    public void setMaxMatchPercent(ColumnAccuracyMaxMatchPercentCheckSpec maxMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.maxMatchPercent, maxMatchPercent));
        this.maxMatchPercent = maxMatchPercent;
        propagateHierarchyIdToField(maxMatchPercent, "max_match_percent");
    }

    /**
     * Returns an accuracy average percent check specification.
     * @return Accuracy average percent check specification.
     */
    public ColumnAccuracyAverageMatchPercentCheckSpec getAverageMatchPercent() {
        return averageMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy average percent check.
     * @param averageMatchPercent Accuracy average percent check specification.
     */
    public void setAverageMatchPercent(ColumnAccuracyAverageMatchPercentCheckSpec averageMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.averageMatchPercent, averageMatchPercent));
        this.averageMatchPercent = averageMatchPercent;
        propagateHierarchyIdToField(averageMatchPercent, "average_match_percent");
    }

    /**
     * Returns an accuracy row count percent check specification.
     * @return Accuracy row count percent check specification.
     */
    public ColumnAccuracyNotNullCountMatchPercentCheckSpec getRowCountMatchPercent() {
        return rowCountMatchPercent;
    }

    /**
     * Sets a new definition of an accuracy row count percent check.
     * @param rowCountMatchPercent Accuracy row count percent check specification.
     */
    public void setRowCountMatchPercent(ColumnAccuracyNotNullCountMatchPercentCheckSpec rowCountMatchPercent) {
        this.setDirtyIf(!Objects.equals(this.rowCountMatchPercent, rowCountMatchPercent));
        this.rowCountMatchPercent = rowCountMatchPercent;
        propagateHierarchyIdToField(rowCountMatchPercent, "row_count_match_percent");
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