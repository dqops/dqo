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
import ai.dqo.checks.column.numeric.*;
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
            put("daily_checkpoint_max_negative_count", o -> o.dailyCheckpointMaxNegativeCount);
            put("daily_checkpoint_max_negative_percent", o -> o.dailyCheckpointMaxNegativePercent);
            put("daily_checkpoint_min_numbers_in_set_count", o -> o.dailyCheckpointMinNumbersInSetCount);
            put("daily_checkpoint_min_numbers_in_set_percent", o -> o.dailyCheckpointMinNumbersInSetPercent);
            put("daily_checkpoint_min_values_in_range_numeric_percent", o -> o.dailyCheckpointMinValuesInRangeNumericPercent);
            put("daily_checkpoint_min_values_in_range_integers_percent", o -> o.dailyCheckpointMinValuesInRangeIntegersPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxNegativeCountCheckSpec dailyCheckpointMaxNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnMaxNegativePercentCheckSpec dailyCheckpointMaxNegativePercent;

    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count.")
    private ColumnMinNumbersInSetCountCheckSpec dailyCheckpointMinNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage.")
    private ColumnMinNumbersInSetPercentCheckSpec dailyCheckpointMinNumbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnMinValuesInRangeNumericPercentCheckSpec dailyCheckpointMinValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnMinValuesInRangeIntegersPercentCheckSpec dailyCheckpointMinValuesInRangeIntegersPercent;

    /**
     * Returns a maximum negative values count check.
     * @return Maximum negative values count check.
     */
    public ColumnMaxNegativeCountCheckSpec getDailyCheckpointMaxNegativeCount() {
        return dailyCheckpointMaxNegativeCount;
    }

    /**
     * Sets a new definition of a maximum negative values count check.
     * @param dailyCheckpointMaxNegativeCount Maximum negative values count check.
     */
    public void setDailyCheckpointMaxNegativeCount(ColumnMaxNegativeCountCheckSpec dailyCheckpointMaxNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxNegativeCount, dailyCheckpointMaxNegativeCount));
        this.dailyCheckpointMaxNegativeCount = dailyCheckpointMaxNegativeCount;
        propagateHierarchyIdToField(dailyCheckpointMaxNegativeCount, "daily_checkpoint_max_negative_count");
    }

    /**
     * Returns a maximum negative values percentage check.
     * @return Maximum negative values percentage check.
     */
    public ColumnMaxNegativePercentCheckSpec getDailyCheckpointMaxNegativePercent() {
        return dailyCheckpointMaxNegativePercent;
    }

    /**
     * Sets a new definition of a maximum negative values percentage check.
     * @param dailyCheckpointMaxNegativePercent Maximum negative values percentage check.
     */
    public void setDailyCheckpointMaxNegativePercent(ColumnMaxNegativePercentCheckSpec dailyCheckpointMaxNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMaxNegativePercent, dailyCheckpointMaxNegativePercent));
        this.dailyCheckpointMaxNegativePercent = dailyCheckpointMaxNegativePercent;
        propagateHierarchyIdToField(dailyCheckpointMaxNegativePercent, "daily_checkpoint_max_negative_percent");
    }

    /**
     * Returns a minimum Numbers in set count check.
     * @return Minimum Numbers in set count check.
     */
    public ColumnMinNumbersInSetCountCheckSpec getDailyCheckpointMinNumbersInSetCount() {
        return dailyCheckpointMinNumbersInSetCount;
    }

    /**
     * Sets a new definition of a minimum Numbers in set count check.
     * @param dailyCheckpointMinNumbersInSetCount Minimum Numbers in set count check.
     */
    public void setDailyCheckpointMinNumbersInSetCount(ColumnMinNumbersInSetCountCheckSpec dailyCheckpointMinNumbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinNumbersInSetCount, dailyCheckpointMinNumbersInSetCount));
        this.dailyCheckpointMinNumbersInSetCount = dailyCheckpointMinNumbersInSetCount;
        propagateHierarchyIdToField(dailyCheckpointMinNumbersInSetCount, "daily_checkpoint_min_numbers_in_set_count");
    }

    /**
     * Returns a minimum Numbers in set percent check.
     * @return Minimum Numbers in set percent check.
     */
    public ColumnMinNumbersInSetPercentCheckSpec getDailyCheckpointMinNumbersInSetPercent() {
        return dailyCheckpointMinNumbersInSetPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param dailyCheckpointMinNumbersInSetPercent Minimum Numbers in set percent check.
     */
    public void setDailyCheckpointMinNumbersInSetPercent(ColumnMinNumbersInSetPercentCheckSpec dailyCheckpointMinNumbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinNumbersInSetPercent, dailyCheckpointMinNumbersInSetPercent));
        this.dailyCheckpointMinNumbersInSetPercent = dailyCheckpointMinNumbersInSetPercent;
        propagateHierarchyIdToField(dailyCheckpointMinNumbersInSetPercent, "daily_checkpoint_min_numbers_in_set_percent");
    }

    /**
     * Returns a minimum Numbers in set percent check.
     * @return Minimum Numbers in set percent check.
     */
    public ColumnMinValuesInRangeNumericPercentCheckSpec getDailyCheckpointMinValuesInRangeNumericPercent() {
        return dailyCheckpointMinValuesInRangeNumericPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param dailyCheckpointMinValuesInRangeNumericPercent Minimum Numbers in set percent check.
     */
    public void setDailyCheckpointMinValuesInRangeNumericPercent(ColumnMinValuesInRangeNumericPercentCheckSpec dailyCheckpointMinValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinValuesInRangeNumericPercent, dailyCheckpointMinValuesInRangeNumericPercent));
        this.dailyCheckpointMinValuesInRangeNumericPercent = dailyCheckpointMinValuesInRangeNumericPercent;
        propagateHierarchyIdToField(dailyCheckpointMinValuesInRangeNumericPercent, "daily_checkpoint_min_values_in_range_numeric_percent");
    }

    /**
     * Returns a minimum Numbers in set percent check.
     * @return Minimum Numbers in set percent check.
     */
    public ColumnMinValuesInRangeIntegersPercentCheckSpec getDailyCheckpointMinValuesInRangeIntegersPercent() {
        return dailyCheckpointMinValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param dailyCheckpointMinValuesInRangeIntegersPercent Minimum Numbers in set percent check.
     */
    public void setDailyCheckpointMinValuesInRangeIntegersPercent(ColumnMinValuesInRangeIntegersPercentCheckSpec dailyCheckpointMinValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointMinValuesInRangeIntegersPercent, dailyCheckpointMinValuesInRangeIntegersPercent));
        this.dailyCheckpointMinValuesInRangeIntegersPercent = dailyCheckpointMinValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(dailyCheckpointMinValuesInRangeIntegersPercent, "daily_checkpoint_min_values_in_range_integers_percent");
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