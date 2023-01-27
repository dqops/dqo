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
 * Container of built-in preconfigured data quality checks on a column level for numeric values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAdHocNumericChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAdHocNumericChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("negative_count", o -> o.negativeCount);
            put("negative_percent", o -> o.negativePercent);
            put("numbers_in_set_count", o -> o.numbersInSetCount);
            put("numbers_in_set_percent", o -> o.numbersInSetPercent);
            put("values_in_range_numeric_percent", o -> o.valuesInRangeNumericPercent);
            put("values_in_range_integers_percent", o -> o.valuesInRangeIntegersPercent);
            put("max_in_range", o -> o.maxInRange);
            put("min_in_range", o -> o.minInRange);
            put("sum_in_range", o -> o.sumInRange);
        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count.")
    private ColumnNegativeCountCheckSpec negativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.")
    private ColumnNegativePercentCheckSpec negativePercent;

    @JsonPropertyDescription("Verifies that the number of numbers from set in a column does not exceed the minimum accepted count.")
    private ColumnNumbersInSetCountCheckSpec numbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of numbers from set in a column does not exceed the minimum accepted percentage.")
    private ColumnNumbersInSetPercentCheckSpec numbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnValuesInRangeNumericPercentCheckSpec valuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnValuesInRangeIntegersPercentCheckSpec valuesInRangeIntegersPercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column does not exceed the set range.")
    private ColumnMaxInRangeCheckSpec maxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column does not exceed the set range.")
    private ColumnMinInRangeCheckSpec minInRange;

    @JsonPropertyDescription("Verifies that the sum of a column does not exceed the set range.")
    private ColumnSumInRangeCheckSpec sumInRange;

    /**
     * Returns a negative count check specification.
     * @return Negative count check specification.
     */
    public ColumnNegativeCountCheckSpec getNegativeCount() {
        return negativeCount;
    }

    /**
     * Sets a new specification of a negative count check.
     * @param negativeCount Negative count check specification.
     */
    public void setNegativeCount(ColumnNegativeCountCheckSpec negativeCount) {
        this.setDirtyIf(!Objects.equals(this.negativeCount, negativeCount));
        this.negativeCount = negativeCount;
        propagateHierarchyIdToField(negativeCount, "negative_count");
    }

    /**
     * Returns a negative percentage check specification.
     * @return Negative percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getNegativePercent() {
        return negativePercent;
    }

    /**
     * Sets a new specification of a negative percentage check.
     * @param negativePercent Negative percentage check specification.
     */
    public void setNegativePercent(ColumnNegativePercentCheckSpec negativePercent) {
        this.setDirtyIf(!Objects.equals(this.negativePercent, negativePercent));
        this.negativePercent = negativePercent;
        propagateHierarchyIdToField(negativePercent, "negative_percent");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnNumbersInSetCountCheckSpec getNumbersInSetCount() {
        return numbersInSetCount;
    }

    /**
     * Sets a new specification of a numbers in set count check specification.
     * @param numbersInSetCount Numbers in set count check specification.
     */
    public void setNumbersInSetCount(ColumnNumbersInSetCountCheckSpec numbersInSetCount) {
        this.setDirtyIf(!Objects.equals(this.numbersInSetCount, numbersInSetCount));
        this.numbersInSetCount = numbersInSetCount;
        propagateHierarchyIdToField(numbersInSetCount, "numbers_in_set_count");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumbersInSetPercentCheckSpec getNumbersInSetPercent() {
        return numbersInSetPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check specification.
     * @param numbersInSetPercent Numbers in set percent check specification.
     */
    public void setNumbersInSetPercent(ColumnNumbersInSetPercentCheckSpec numbersInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.numbersInSetPercent, numbersInSetPercent));
        this.numbersInSetPercent = numbersInSetPercent;
        propagateHierarchyIdToField(numbersInSetPercent, "numbers_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeNumericPercentCheckSpec getValuesInRangeNumericPercent() {
        return valuesInRangeNumericPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param valuesInRangeNumericPercent Numbers in set percent check specification.
     */
    public void setValuesInRangeNumericPercent(ColumnValuesInRangeNumericPercentCheckSpec valuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.valuesInRangeNumericPercent, valuesInRangeNumericPercent));
        this.valuesInRangeNumericPercent = valuesInRangeNumericPercent;
        propagateHierarchyIdToField(valuesInRangeNumericPercent, "values_in_range_numeric_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeIntegersPercentCheckSpec getValuesInRangeIntegersPercent() {
        return valuesInRangeIntegersPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param valuesInRangeIntegersPercent Numbers in set percent check specification.
     */
    public void setValuesInRangeIntegersPercent(ColumnValuesInRangeIntegersPercentCheckSpec valuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.valuesInRangeIntegersPercent, valuesInRangeIntegersPercent));
        this.valuesInRangeIntegersPercent = valuesInRangeIntegersPercent;
        propagateHierarchyIdToField(valuesInRangeIntegersPercent, "values_in_range_integers_percent");
    }

    /**
     * Returns a max in range check specification.
     * @return Max in range check specification.
     */
    public ColumnMaxInRangeCheckSpec getMaxInRange() {
        return maxInRange;
    }

    /**
     * Sets a max in range percent check.
     * @param maxInRange Max in range check specification.
     */
    public void setMaxInRange(ColumnMaxInRangeCheckSpec maxInRange) {
        this.setDirtyIf(!Objects.equals(this.maxInRange, maxInRange));
        this.maxInRange = maxInRange;
        propagateHierarchyIdToField(maxInRange, "max_in_range");
    }

    /**
     * Returns a min in range check specification.
     * @return Min in range check specification.
     */
    public ColumnMinInRangeCheckSpec getMinInRange() {
        return minInRange;
    }

    /**
     * Sets a new specification of a min in range check.
     * @param minInRange Min in range check specification.
     */
    public void setMinInRange(ColumnMinInRangeCheckSpec minInRange) {
        this.setDirtyIf(!Objects.equals(this.minInRange, minInRange));
        this.minInRange = minInRange;
        propagateHierarchyIdToField(minInRange, "min_in_range");
    }

    /**
     * Returns a sum in range check specification.
     * @return Sum in range check specification.
     */
    public ColumnSumInRangeCheckSpec getSumInRange() {
        return sumInRange;
    }

    /**
     * Sets a new specification of a sum in range check.
     * @param sumInRange Sum in range check specification.
     */
    public void setSumInRange(ColumnSumInRangeCheckSpec sumInRange) {
        this.setDirtyIf(!Objects.equals(this.sumInRange, sumInRange));
        this.sumInRange = sumInRange;
        propagateHierarchyIdToField(sumInRange, "sum_in_range");
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
