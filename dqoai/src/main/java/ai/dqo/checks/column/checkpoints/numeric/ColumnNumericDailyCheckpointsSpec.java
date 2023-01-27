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
package ai.dqo.checks.column.checkpoints.numeric;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.numeric.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checkpoints on a column level that are checking numeric values at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_negative_count", o -> o.dailyCheckpointNegativeCount);
            put("daily_checkpoint_negative_percent", o -> o.dailyCheckpointNegativePercent);
            put("daily_checkpoint_numbers_in_set_count", o -> o.dailyCheckpointNumbersInSetCount);
            put("daily_checkpoint_numbers_in_set_percent", o -> o.dailyCheckpointNumbersInSetPercent);
            put("daily_checkpoint_values_in_range_numeric_percent", o -> o.dailyCheckpointValuesInRangeNumericPercent);
            put("daily_checkpoint_values_in_range_integers_percent", o -> o.dailyCheckpointValuesInRangeIntegersPercent);
            put("daily_checkpoint_max_in_range", o -> o.dailyCheckpointMaxInRange);
            put("daily_checkpoint_min_in_range", o -> o.dailyCheckpointMinInRange);
            put("daily_checkpoint_sum_in_range", o -> o.dailyCheckpointSumInRange);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNegativeCountCheckSpec dailyCheckpointNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNegativePercentCheckSpec dailyCheckpointNegativePercent;

    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNumbersInSetCountCheckSpec dailyCheckpointNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnNumbersInSetPercentCheckSpec dailyCheckpointNumbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnValuesInRangeNumericPercentCheckSpec dailyCheckpointValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnValuesInRangeIntegersPercentCheckSpec dailyCheckpointValuesInRangeIntegersPercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxInRangeCheckSpec dailyCheckpointMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMinInRangeCheckSpec dailyCheckpointMinInRange;

    @JsonPropertyDescription("Verifies that the sum of a column does not exceed the set range. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnSumInRangeCheckSpec dailyCheckpointSumInRange;

    /**
     * Returns a negative values count check specification.
     * @return Negative values count check specification.
     */
    public ColumnNegativeCountCheckSpec getDailyCheckpointNegativeCount() {
        return dailyCheckpointNegativeCount;
    }

    /**
     * Sets a new specification of a negative values count check.
     * @param dailyCheckpointNegativeCount Negative values count check specification.
     */
    public void setDailyCheckpointNegativeCount(ColumnNegativeCountCheckSpec dailyCheckpointNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNegativeCount, dailyCheckpointNegativeCount));
        this.dailyCheckpointNegativeCount = dailyCheckpointNegativeCount;
        propagateHierarchyIdToField(dailyCheckpointNegativeCount, "daily_checkpoint_negative_count");
    }

    /**
     * Returns a negative values percentage check specification.
     * @return Negative values percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getDailyCheckpointNegativePercent() {
        return dailyCheckpointNegativePercent;
    }

    /**
     * Sets a new specification of a negative values percentage check.
     * @param dailyCheckpointNegativePercent Negative values percentage check specification.
     */
    public void setDailyCheckpointNegativePercent(ColumnNegativePercentCheckSpec dailyCheckpointNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNegativePercent, dailyCheckpointNegativePercent));
        this.dailyCheckpointNegativePercent = dailyCheckpointNegativePercent;
        propagateHierarchyIdToField(dailyCheckpointNegativePercent, "daily_checkpoint_negative_percent");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnNumbersInSetCountCheckSpec getDailyCheckpointNumbersInSetCount() {
        return dailyCheckpointNumbersInSetCount;
    }

    /**
     * Sets a new specification of a numbers in set count check.
     * @param dailyCheckpointNumbersInSetCount Numbers in set count check.
     */
    public void setDailyCheckpointNumbersInSetCount(ColumnNumbersInSetCountCheckSpec dailyCheckpointNumbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNumbersInSetCount, dailyCheckpointNumbersInSetCount));
        this.dailyCheckpointNumbersInSetCount = dailyCheckpointNumbersInSetCount;
        propagateHierarchyIdToField(dailyCheckpointNumbersInSetCount, "daily_checkpoint_numbers_in_set_count");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumbersInSetPercentCheckSpec getDailyCheckpointNumbersInSetPercent() {
        return dailyCheckpointNumbersInSetPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyCheckpointNumbersInSetPercent Numbers in set percent check specification.
     */
    public void setDailyCheckpointNumbersInSetPercent(ColumnNumbersInSetPercentCheckSpec dailyCheckpointNumbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointNumbersInSetPercent, dailyCheckpointNumbersInSetPercent));
        this.dailyCheckpointNumbersInSetPercent = dailyCheckpointNumbersInSetPercent;
        propagateHierarchyIdToField(dailyCheckpointNumbersInSetPercent, "daily_checkpoint_numbers_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeNumericPercentCheckSpec getDailyCheckpointValuesInRangeNumericPercent() {
        return dailyCheckpointValuesInRangeNumericPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyCheckpointValuesInRangeNumericPercent Numbers in set percent check.
     */
    public void setDailyCheckpointValuesInRangeNumericPercent(ColumnValuesInRangeNumericPercentCheckSpec dailyCheckpointValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValuesInRangeNumericPercent, dailyCheckpointValuesInRangeNumericPercent));
        this.dailyCheckpointValuesInRangeNumericPercent = dailyCheckpointValuesInRangeNumericPercent;
        propagateHierarchyIdToField(dailyCheckpointValuesInRangeNumericPercent, "daily_checkpoint_values_in_range_numeric_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeIntegersPercentCheckSpec getDailyCheckpointValuesInRangeIntegersPercent() {
        return dailyCheckpointValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param dailyCheckpointValuesInRangeIntegersPercent Numbers in set percent check specification.
     */
    public void setDailyCheckpointValuesInRangeIntegersPercent(ColumnValuesInRangeIntegersPercentCheckSpec dailyCheckpointValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValuesInRangeIntegersPercent, dailyCheckpointValuesInRangeIntegersPercent));
        this.dailyCheckpointValuesInRangeIntegersPercent = dailyCheckpointValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(dailyCheckpointValuesInRangeIntegersPercent, "daily_checkpoint_values_in_range_integers_percent");
    }

    /**
     * Returns a max in range check specification.
     * @return Max in range check specification.
     */
    public ColumnMaxInRangeCheckSpec getDailyCheckpointMaxInRange() {
        return dailyCheckpointMaxInRange;
    }

    /**
     * Sets a new specification of a max in range check.
     * @param dailyCheckpointMaxInRange Max in range check specification.
     */
    public void setDailyCheckpointMaxInRange(ColumnMaxInRangeCheckSpec dailyCheckpointMaxInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxInRange, dailyCheckpointMaxInRange));
        this.dailyCheckpointMaxInRange = dailyCheckpointMaxInRange;
        propagateHierarchyIdToField(dailyCheckpointMaxInRange, "daily_checkpoint_max_in_range");
    }

    /**
     * Returns a min in range check specification.
     * @return Min in range check specification.
     */
    public ColumnMinInRangeCheckSpec getDailyCheckpointMinInRange() {
        return dailyCheckpointMinInRange;
    }

    /**
     * Sets a new specification of a min in range check.
     * @param dailyCheckpointMinInRange Min in range check specification.
     */
    public void setDailyCheckpointMinInRange(ColumnMinInRangeCheckSpec dailyCheckpointMinInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinInRange, dailyCheckpointMinInRange));
        this.dailyCheckpointMinInRange = dailyCheckpointMinInRange;
        propagateHierarchyIdToField(dailyCheckpointMinInRange, "daily_checkpoint_min_in_range");
    }

    /**
     * Returns a sum in range check specification.
     * @return Sum in range check specification.
     */
    public ColumnSumInRangeCheckSpec getDailyCheckpointSumInRange() {
        return dailyCheckpointSumInRange;
    }

    /**
     * Sets a new specification of a sum in range check.
     * @param dailyCheckpointSumInRange Sum in range check specification.
     */
    public void setDailyCheckpointSumInRange(ColumnSumInRangeCheckSpec dailyCheckpointSumInRange) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointSumInRange, dailyCheckpointSumInRange));
        this.dailyCheckpointSumInRange = dailyCheckpointSumInRange;
        propagateHierarchyIdToField(dailyCheckpointSumInRange, "daily_checkpoint_sum_in_range");
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