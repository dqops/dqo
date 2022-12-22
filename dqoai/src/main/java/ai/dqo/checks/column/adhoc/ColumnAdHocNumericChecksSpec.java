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
package ai.dqo.checks.column.adhoc;

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
 * Container of built-in preconfigured data quality checks on a column level for numeric values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocNumericChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocNumericChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("max_negative_count", o -> o.maxNegativeCount);
            put("max_negative_percent", o -> o.maxNegativePercent);
            put("min_numbers_in_set_count", o -> o.minNumbersInSetCount);
            put("min_numbers_in_set_percent", o -> o.minNumbersInSetPercent);
            put("min_values_in_range_numeric_percent", o -> o.minValuesInRangeNumericPercent);
            put("min_values_in_range_integers_percent", o -> o.minValuesInRangeIntegersPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count.")
    private ColumnMaxNegativeCountCheckSpec maxNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.")
    private ColumnMaxNegativePercentCheckSpec maxNegativePercent;

    @JsonPropertyDescription("Verifies that the number of Numbers from set in a column does not exceed the minimum accepted count.")
    private ColumnMinNumbersInSetCountCheckSpec minNumbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of Numbers from set in a column does not exceed the minimum accepted percentage.")
    private ColumnMinNumbersInSetPercentCheckSpec minNumbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnMinValuesInRangeNumericPercentCheckSpec minValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnMinValuesInRangeIntegersPercentCheckSpec minValuesInRangeIntegersPercent;

    /**
     * Returns a maximum negative count check.
     * @return Maximum negative count check.
     */
    public ColumnMaxNegativeCountCheckSpec getMaxNegativeCount() {
        return maxNegativeCount;
    }

    /**
     * Sets a new definition of a maximum negative count check.
     * @param maxNegativeCount Maximum negative count check.
     */
    public void setMaxNegativeCount(ColumnMaxNegativeCountCheckSpec maxNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.maxNegativeCount, maxNegativeCount));
        this.maxNegativeCount = maxNegativeCount;
        propagateHierarchyIdToField(maxNegativeCount, "max_negative_count");
    }

    /**
     * Returns a maximum negative percentage check.
     * @return Maximum negative percentage check.
     */
    public ColumnMaxNegativePercentCheckSpec getMaxNegativePercent() {
        return maxNegativePercent;
    }

    /**
     * Sets a new definition of a maximum negative percentage check.
     * @param maxNegativePercent Maximum negative percentage check.
     */
    public void setMaxNegativePercent(ColumnMaxNegativePercentCheckSpec maxNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.maxNegativePercent, maxNegativePercent));
        this.maxNegativePercent = maxNegativePercent;
        propagateHierarchyIdToField(maxNegativePercent, "max_negative_percent");
    }

    /**
     * Returns a minimum Numbers in set count check.
     * @return Minimum Numbers in set count check.
     */
    public ColumnMinNumbersInSetCountCheckSpec getMinNumbersInSetCount() {
        return minNumbersInSetCount;
    }

    /**
     * Sets a new definition of a minimum Numbers in set count check.
     * @param minNumbersInSetCount Minimum Numbers in set count check.
     */
    public void setMinNumbersInSetCount(ColumnMinNumbersInSetCountCheckSpec minNumbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.minNumbersInSetCount, minNumbersInSetCount));
        this.minNumbersInSetCount = minNumbersInSetCount;
        propagateHierarchyIdToField(minNumbersInSetCount, "min_numbers_in_set_count");
    }

    /**
     * Returns a minimum Numbers in set percent check.
     * @return Minimum Numbers in set percent check.
     */
    public ColumnMinNumbersInSetPercentCheckSpec getMinNumbersInSetPercent() {
        return minNumbersInSetPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param minNumbersInSetPercent Minimum Numbers in set percent check.
     */
    public void setMinNumbersInSetPercent(ColumnMinNumbersInSetPercentCheckSpec minNumbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.minNumbersInSetPercent, minNumbersInSetPercent));
        this.minNumbersInSetPercent = minNumbersInSetPercent;
        propagateHierarchyIdToField(minNumbersInSetPercent, "min_numbers_in_set_percent");
    }

    /**
     * Returns a minimum Numbers in set percent check.
     * @return Minimum Numbers in set percent check.
     */
    public ColumnMinValuesInRangeNumericPercentCheckSpec getMinValuesInRangeNumericPercent() {
        return minValuesInRangeNumericPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param minValuesInRangeNumericPercent Minimum Numbers in set percent check.
     */
    public void setMinValuesInRangeNumericPercent(ColumnMinValuesInRangeNumericPercentCheckSpec minValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.minValuesInRangeNumericPercent, minValuesInRangeNumericPercent));
        this.minValuesInRangeNumericPercent = minValuesInRangeNumericPercent;
        propagateHierarchyIdToField(minValuesInRangeNumericPercent, "min_values_in_range_numeric_percent");
    }

    /**
     * Returns a minimum Numbers in set percent check.
     * @return Minimum Numbers in set percent check.
     */
    public ColumnMinValuesInRangeIntegersPercentCheckSpec getMinValuesInRangeIntegersPercent() {
        return minValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new definition of a minimum Numbers in set percent check.
     * @param minValuesInRangeIntegersPercent Minimum Numbers in set percent check.
     */
    public void setMinValuesInRangeIntegersPercent(ColumnMinValuesInRangeIntegersPercentCheckSpec minValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.minValuesInRangeIntegersPercent, minValuesInRangeIntegersPercent));
        this.minValuesInRangeIntegersPercent = minValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(minValuesInRangeIntegersPercent, "min_values_in_range_integers_percent");
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
