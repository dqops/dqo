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
            put("non_negative_count", o -> o.nonNegativeCount);
            put("non_negative_percent", o -> o.nonNegativePercent);
            put("numbers_in_set_count", o -> o.numbersInSetCount);
            put("numbers_in_set_percent", o -> o.numbersInSetPercent);
            put("values_in_range_numeric_percent", o -> o.valuesInRangeNumericPercent);
            put("values_in_range_integers_percent", o -> o.valuesInRangeIntegersPercent);
            put("value_below_min_value_count", o -> o.valueBelowMinValueCount);
            put("value_below_min_value_percent", o -> o.valueBelowMinValuePercent);
            put("value_above_max_value_count", o -> o.valueAboveMaxValueCount);
            put("value_above_max_value_percent", o -> o.valueAboveMaxValuePercent);
            put("max_in_range", o -> o.maxInRange);
            put("min_in_range", o -> o.minInRange);
            put("mean_in_range", o -> o.meanInRange);
            put("percentile_in_range", o -> o.percentileInRange);
            put("percentile_50_in_range", o -> o.percentile50InRange);
            put("sample_stddev_in_range", o -> o.sampleStddevInRange);
            put("population_stddev_in_range", o -> o.populationStddevInRange);
            put("sample_variance_in_range", o -> o.sampleVarianceInRange);
            put("population_variance_in_range", o -> o.populationVarianceInRange);
            put("sum_in_range", o -> o.sumInRange);
            put("invalid_latitude_count", o -> o.invalidLatitudeCount);
            put("valid_latitude_percent", o -> o.validLatitudePercent);
            put("invalid_longitude_count", o -> o.invalidLongitudeCount);
            put("valid_longitude_percent", o -> o.validLongitudePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count.")
    private ColumnNegativeCountCheckSpec negativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.")
    private ColumnNegativePercentCheckSpec negativePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.")
    private ColumnNonNegativeCountCheckSpec nonNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.")
    private ColumnNonNegativePercentCheckSpec nonNegativePercent;

    @JsonPropertyDescription("Verifies that the number of numbers from set in a column does not exceed the minimum accepted count.")
    private ColumnNumbersInSetCountCheckSpec numbersInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of numbers from set in a column does not exceed the minimum accepted percentage.")
    private ColumnNumbersInSetPercentCheckSpec numbersInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnValuesInRangeNumericPercentCheckSpec valuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnValuesInRangeIntegersPercentCheckSpec valuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts those values with value below the one provided by the user in a column.")
    private ColumnValueBelowMinValueCountCheckSpec valueBelowMinValueCount;

    @JsonPropertyDescription("The check percentage of those values with value below the one provided by the user in a column.")
    private ColumnValueBelowMinValuePercentCheckSpec valueBelowMinValuePercent;

    @JsonPropertyDescription("The check counts those values with value above the one provided by the user in a column.")
    private ColumnValueAboveMaxValueCountCheckSpec valueAboveMaxValueCount;

    @JsonPropertyDescription("The check percentage of those values with value above the one provided by the user in a column.")
    private ColumnValueAboveMaxValuePercentCheckSpec valueAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the set range.")
    private ColumnMaxInRangeCheckSpec maxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the set range.")
    private ColumnMinInRangeCheckSpec minInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the set range.")
    private ColumnMeanInRangeCheckSpec meanInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the set range.")
    private ColumnPercentileInRangeCheckSpec percentileInRange;

    @JsonPropertyDescription("Verifies that the percentile 50 of all values in a column is not outside the set range.")
    private ColumnPercentile50InRangeCheckSpec percentile50InRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the set range.")
    private ColumnSampleStddevInRangeCheckSpec sampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the set range.")
    private ColumnPopulationStddevInRangeCheckSpec populationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the set range.")
    private ColumnSampleVarianceInRangeCheckSpec sampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the set range.")
    private ColumnPopulationVarianceInRangeCheckSpec populationVarianceInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the set range.")
    private ColumnSumInRangeCheckSpec sumInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.")
    private ColumnInvalidLatitudeCountCheckSpec invalidLatitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not exceed the minimum accepted percentage.")
    private ColumnValidLatitudePercentCheckSpec validLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.")
    private ColumnInvalidLongitudeCountCheckSpec invalidLongitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not exceed the minimum accepted percentage.")
    private ColumnValidLongitudePercentCheckSpec validLongitudePercent;

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
     * Returns a non-negative count check specification.
     * @return Non-negative count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getNonNegativeCount() {
        return nonNegativeCount;
    }

    /**
     * Sets a new specification of a non-negative count check.
     * @param nonNegativeCount Non-negative count check specification.
     */
    public void setNonNegativeCount(ColumnNonNegativeCountCheckSpec nonNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.nonNegativeCount, nonNegativeCount));
        this.nonNegativeCount = nonNegativeCount;
        propagateHierarchyIdToField(nonNegativeCount, "non_negative_count");
    }

    /**
     * Returns a negative percentage check specification.
     * @return Negative percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getNonNegativePercent() {
        return nonNegativePercent;
    }

    /**
     * Sets a new specification of a negative percentage check.
     * @param nonNegativePercent Negative percentage check specification.
     */
    public void setNonNegativePercent(ColumnNonNegativePercentCheckSpec nonNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.nonNegativePercent, nonNegativePercent));
        this.nonNegativePercent = nonNegativePercent;
        propagateHierarchyIdToField(nonNegativePercent, "non_negative_percent");
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
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnValueBelowMinValueCountCheckSpec getValueBelowMinValueCount() {
        return valueBelowMinValueCount;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param valueBelowMinValueCount Numeric value below min value count check.
     */
    public void setValueBelowMinValueCount(ColumnValueBelowMinValueCountCheckSpec valueBelowMinValueCount) {
        this.setDirtyIf(!Objects.equals(this.valueBelowMinValueCount, valueBelowMinValueCount));
        this.valueBelowMinValueCount = valueBelowMinValueCount;
        propagateHierarchyIdToField(valueBelowMinValueCount, "value_below_min_value_count");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnValueBelowMinValuePercentCheckSpec getValueBelowMinValuePercent() {
        return valueBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param valueBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setValueBelowMinValuePercent(ColumnValueBelowMinValuePercentCheckSpec valueBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.valueBelowMinValuePercent, valueBelowMinValuePercent));
        this.valueBelowMinValuePercent = valueBelowMinValuePercent;
        propagateHierarchyIdToField(valueBelowMinValuePercent, "value_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnValueAboveMaxValueCountCheckSpec getValueAboveMaxValueCount() {
        return valueAboveMaxValueCount;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param valueAboveMaxValueCount Numeric value above max value count check.
     */
    public void setValueAboveMaxValueCount(ColumnValueAboveMaxValueCountCheckSpec valueAboveMaxValueCount) {
        this.setDirtyIf(!Objects.equals(this.valueAboveMaxValueCount, valueAboveMaxValueCount));
        this.valueAboveMaxValueCount = valueAboveMaxValueCount;
        propagateHierarchyIdToField(valueAboveMaxValueCount, "value_above_max_value_count");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnValueAboveMaxValuePercentCheckSpec getValueAboveMaxValuePercent() {
        return valueAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param valueAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setValueAboveMaxValuePercent(ColumnValueAboveMaxValuePercentCheckSpec valueAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.valueAboveMaxValuePercent, valueAboveMaxValuePercent));
        this.valueAboveMaxValuePercent = valueAboveMaxValuePercent;
        propagateHierarchyIdToField(valueAboveMaxValuePercent, "value_above_max_value_percent");
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
     * Returns a mean in range check specification.
     * @return Mean in range check specification.
     */
    public ColumnMeanInRangeCheckSpec getMeanInRange() {
        return meanInRange;
    }

    /**
     * Sets a new specification of a mean in range check.
     * @param meanInRange Mean in range check specification.
     */
    public void setMeanInRange(ColumnMeanInRangeCheckSpec meanInRange) {
        this.setDirtyIf(!Objects.equals(this.meanInRange, meanInRange));
        this.meanInRange = meanInRange;
        propagateHierarchyIdToField(meanInRange, "mean_in_range");
    }

    /**
     * Returns a percentile in range check specification.
     * @return Percentile in range check specification.
     */
    public ColumnPercentileInRangeCheckSpec getPercentileInRange() {
        return percentileInRange;
    }

    /**
     * Sets a new specification of a percentile in range check.
     * @param percentileInRange Percentile in range check specification.
     */
    public void setPercentileInRange(ColumnPercentileInRangeCheckSpec percentileInRange) {
        this.setDirtyIf(!Objects.equals(this.percentileInRange, percentileInRange));
        this.percentileInRange = percentileInRange;
        propagateHierarchyIdToField(percentileInRange, "percentile_in_range");
    }

    /**
     * Returns a percentile 50 in range check specification.
     * @return Percentile 50 in range check specification.
     */
    public ColumnPercentile50InRangeCheckSpec getPercentile50InRange() {
        return percentile50InRange;
    }

    /**
     * Sets a new specification of a percentile 50 in range check.
     * @param percentile50InRange Percentile 50 in range check specification.
     */
    public void setPercentile50InRange(ColumnPercentile50InRangeCheckSpec percentile50InRange) {
        this.setDirtyIf(!Objects.equals(this.percentile50InRange, percentile50InRange));
        this.percentile50InRange = percentile50InRange;
        propagateHierarchyIdToField(percentile50InRange, "percentile_50_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleStddevInRangeCheckSpec getSampleStddevInRange() {
        return sampleStddevInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param sampleStddevInRange Sample standard deviation in range check specification.
     */
    public void setSampleStddevInRange(ColumnSampleStddevInRangeCheckSpec sampleStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.sampleStddevInRange, sampleStddevInRange));
        this.sampleStddevInRange = sampleStddevInRange;
        propagateHierarchyIdToField(sampleStddevInRange, "sample_stddev_in_range");
    }

    /**
     * Returns a population standard deviation in range check specification.
     * @return Population standard deviation in range check specification.
     */
    public ColumnPopulationStddevInRangeCheckSpec getPopulationStddevInRange() {
        return populationStddevInRange;
    }

    /**
     * Sets a new specification of a population standard deviation in range check.
     * @param populationStddevInRange Population standard deviation in range check specification.
     */
    public void setPopulationStddevInRange(ColumnPopulationStddevInRangeCheckSpec populationStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.populationStddevInRange, populationStddevInRange));
        this.populationStddevInRange = populationStddevInRange;
        propagateHierarchyIdToField(populationStddevInRange, "population_stddev_in_range");
    }

    /**
     * Returns a sample variance in range check specification.
     * @return Sample variance in range check specification.
     */
    public ColumnSampleVarianceInRangeCheckSpec getSampleVarianceInRange() {
        return sampleVarianceInRange;
    }

    /**
     * Sets a new specification of a sample variance in range check.
     * @param sampleVarianceInRange Sample variance in range check specification.
     */
    public void setSampleVarianceInRange(ColumnSampleVarianceInRangeCheckSpec sampleVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.sampleVarianceInRange, sampleVarianceInRange));
        this.sampleVarianceInRange = sampleVarianceInRange;
        propagateHierarchyIdToField(sampleVarianceInRange, "sample_variance_in_range");
    }

    /**
     * Returns a population variance in range check specification.
     * @return Population variance in range check specification.
     */
    public ColumnPopulationVarianceInRangeCheckSpec getPopulationVarianceInRange() {
        return populationVarianceInRange;
    }

    /**
     * Sets a new specification of a population variance in range check.
     * @param populationVarianceInRange Population variance in range check specification.
     */
    public void setPopulationVarianceInRange(ColumnPopulationVarianceInRangeCheckSpec populationVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.populationVarianceInRange, populationVarianceInRange));
        this.populationVarianceInRange = populationVarianceInRange;
        propagateHierarchyIdToField(populationVarianceInRange, "population_variance_in_range");
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
     * Returns an invalid latitude count check specification.
     * @return Invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getInvalidLatitudeCount() {
        return invalidLatitudeCount;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param invalidLatitudeCount Invalid latitude count check specification.
     */
    public void setInvalidLatitudeCount(ColumnInvalidLatitudeCountCheckSpec invalidLatitudeCount) {
        this.setDirtyIf(!Objects.equals(this.invalidLatitudeCount, invalidLatitudeCount));
        this.invalidLatitudeCount = invalidLatitudeCount;
        propagateHierarchyIdToField(invalidLatitudeCount, "invalid_latitude_count");
    }

    /**
     * Returns a valid latitude percent check specification.
     * @return Valid latitude percent check specification.
     */
    public ColumnValidLatitudePercentCheckSpec getValidLatitudePercent() {
        return validLatitudePercent;
    }

    /**
     * Sets a new specification of a valid latitude percent check.
     * @param validLatitudePercent Valid latitude count percent specification.
     */
    public void setValidLatitudePercent(ColumnValidLatitudePercentCheckSpec validLatitudePercent) {
        this.setDirtyIf(!Objects.equals(this.validLatitudePercent, validLatitudePercent));
        this.validLatitudePercent = validLatitudePercent;
        propagateHierarchyIdToField(validLatitudePercent, "valid_latitude_percent");
    }

    /**
     * Returns an invalid longitude count check specification.
     * @return Invalid longitude count check specification.
     */
    public ColumnInvalidLongitudeCountCheckSpec getInvalidLongitudeCount() {
        return invalidLongitudeCount;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param invalidLongitudeCount Invalid longitude count check specification.
     */
    public void setInvalidLongitudeCount(ColumnInvalidLongitudeCountCheckSpec invalidLongitudeCount) {
        this.setDirtyIf(!Objects.equals(this.invalidLongitudeCount, invalidLongitudeCount));
        this.invalidLongitudeCount = invalidLongitudeCount;
        propagateHierarchyIdToField(invalidLongitudeCount, "invalid_longitude_count");
    }

    /**
     * Returns a valid longitude percent check specification.
     * @return Valid longitude percent check specification.
     */
    public ColumnValidLongitudePercentCheckSpec getValidLongitudePercent() {
        return validLongitudePercent;
    }

    /**
     * Sets a new specification of a valid longitude percent check.
     * @param validLongitudePercent Valid longitude count percent specification.
     */
    public void setValidLongitudePercent(ColumnValidLongitudePercentCheckSpec validLongitudePercent) {
        this.setDirtyIf(!Objects.equals(this.validLongitudePercent, validLongitudePercent));
        this.validLongitudePercent = validLongitudePercent;
        propagateHierarchyIdToField(validLongitudePercent, "valid_longitude_percent");
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
