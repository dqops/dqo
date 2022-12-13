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
package ai.dqo.checks.column.partitioned.numeric;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.numeric.ColumnMaxNegativeCountCheckSpec;
import ai.dqo.checks.column.numeric.ColumnMaxNegativePercentCheckSpec;
import ai.dqo.checks.column.numeric.ColumnMinNumbersInSetCountCheckSpec;
import ai.dqo.checks.column.numeric.ColumnMinNumbersInSetPercentCheckSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import java.util.Objects;

/**
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_max_negative_count", o -> o.dailyPartitionMaxNegativeCount);
            put("daily_partition_max_negative_percent", o -> o.dailyPartitionMaxNegativePercent);
            put("daily_partition_min_numbers_in_set_count", o -> o.dailyPartitionMinNumbersInSetCount);
            put("daily_partition_min_numbers_in_set_percent", o -> o.dailyPartitionMinNumbersInSetPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxNegativeCountCheckSpec dailyPartitionMaxNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnMaxNegativePercentCheckSpec dailyPartitionMaxNegativePercent;

    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count.")
    private ColumnMinNumbersInSetCountCheckSpec dailyPartitionMinNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage.")
    private ColumnMinNumbersInSetPercentCheckSpec dailyPartitionMinNumbersInSetPercent;

    /**
     * Returns a maximum negative values count check.
     * @return Maximum negative values count check.
     */
    public ColumnMaxNegativeCountCheckSpec getDailyPartitionMaxNegativeCount() {
        return dailyPartitionMaxNegativeCount;
    }

    /**
     * Sets a new definition of a maximum negative values count check.
     * @param dailyPartitionMaxNegativeCount Maximum negative values count check.
     */
    public void setDailyPartitionMaxNegativeCount(ColumnMaxNegativeCountCheckSpec dailyPartitionMaxNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxNegativeCount, dailyPartitionMaxNegativeCount));
        this.dailyPartitionMaxNegativeCount = dailyPartitionMaxNegativeCount;
        propagateHierarchyIdToField(dailyPartitionMaxNegativeCount, "daily_partition_max_negative_count");
    }

    /**
     * Returns a maximum negative values percentage check.
     * @return Maximum negative values percentage check.
     */
    public ColumnMaxNegativePercentCheckSpec getDailyPartitionMaxNegativePercent() {
        return dailyPartitionMaxNegativePercent;
    }

    /**
     * Sets a new definition of a maximum negative values percentage check.
     * @param dailyPartitionMaxNegativePercent Maximum negative values percentage check.
     */
    public void setDailyPartitionMaxNegativePercent(ColumnMaxNegativePercentCheckSpec dailyPartitionMaxNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxNegativePercent, dailyPartitionMaxNegativePercent));
        this.dailyPartitionMaxNegativePercent = dailyPartitionMaxNegativePercent;
        propagateHierarchyIdToField(dailyPartitionMaxNegativePercent, "daily_partition_max_negative_percent");
    }

    /**
     * Returns a minimum Numbers in set count check.
     * @return Minimum Numbers in set count check.
     */
    public ColumnMinNumbersInSetCountCheckSpec getDailyPartitionMinNumbersInSetCount() {
        return dailyPartitionMinNumbersInSetCount;
    }

    /**
     * Sets a new definition of a minimum Numbers in set count check.
     * @param dailyPartitionMinNumbersInSetCount Minimum Numbers in set count check.
     */
    public void setDailyPartitionMinNumbersInSetCount(ColumnMinNumbersInSetCountCheckSpec dailyPartitionMinNumbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinNumbersInSetCount, dailyPartitionMinNumbersInSetCount));
        this.dailyPartitionMinNumbersInSetCount = dailyPartitionMinNumbersInSetCount;
        propagateHierarchyIdToField(dailyPartitionMinNumbersInSetCount, "daily_partition_min_numbers_in_set_count");
    }

    /**
     * Returns a minimum Numbers in set percent check.
     * @return Minimum Numbers in set percent check.
     */
    public ColumnMinNumbersInSetPercentCheckSpec getDailyPartitionMinNumbersInSetPercent() {
        return dailyPartitionMinNumbersInSetPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param dailyPartitionMinNumbersInSetPercent Minimum Numbers in set percent check.
     */
    public void setDailyPartitionMinNumbersInSetPercent(ColumnMinNumbersInSetPercentCheckSpec dailyPartitionMinNumbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinNumbersInSetPercent, dailyPartitionMinNumbersInSetPercent));
        this.dailyPartitionMinNumbersInSetPercent = dailyPartitionMinNumbersInSetPercent;
        propagateHierarchyIdToField(dailyPartitionMinNumbersInSetPercent, "daily_partition_min_numbers_in_set_percent");
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