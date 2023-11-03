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
package com.dqops.checks.column.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.numeric.*;
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
 * Container of built-in preconfigured data quality checks on a column level for numeric values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNumericProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNumericProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_negative_count", o -> o.profileNegativeCount);
            put("profile_negative_percent", o -> o.profileNegativePercent);
            put("profile_non_negative_count", o -> o.profileNonNegativeCount);
            put("profile_non_negative_percent", o -> o.profileNonNegativePercent);
            put("profile_expected_numbers_in_use_count", o -> o.profileExpectedNumbersInUseCount);
            put("profile_number_value_in_set_percent", o -> o.profileNumberValueInSetPercent);
            put("profile_values_in_range_numeric_percent", o -> o.profileValuesInRangeNumericPercent);
            put("profile_values_in_range_integers_percent", o -> o.profileValuesInRangeIntegersPercent);
            put("profile_value_below_min_value_count", o -> o.profileValueBelowMinValueCount);
            put("profile_value_below_min_value_percent", o -> o.profileValueBelowMinValuePercent);
            put("profile_value_above_max_value_count", o -> o.profileValueAboveMaxValueCount);
            put("profile_value_above_max_value_percent", o -> o.profileValueAboveMaxValuePercent);
            put("profile_max_in_range", o -> o.profileMaxInRange);
            put("profile_min_in_range", o -> o.profileMinInRange);
            put("profile_mean_in_range", o -> o.profileMeanInRange);
            put("profile_percentile_in_range", o -> o.profilePercentileInRange);
            put("profile_median_in_range", o -> o.profileMedianInRange);
            put("profile_percentile_10_in_range", o -> o.profilePercentile_10InRange);
            put("profile_percentile_25_in_range", o -> o.profilePercentile_25InRange);
            put("profile_percentile_75_in_range", o -> o.profilePercentile_75InRange);
            put("profile_percentile_90_in_range", o -> o.profilePercentile_90InRange);
            put("profile_sample_stddev_in_range", o -> o.profileSampleStddevInRange);
            put("profile_population_stddev_in_range", o -> o.profilePopulationStddevInRange);
            put("profile_sample_variance_in_range", o -> o.profileSampleVarianceInRange);
            put("profile_population_variance_in_range", o -> o.profilePopulationVarianceInRange);
            put("profile_sum_in_range", o -> o.profileSumInRange);
            put("profile_invalid_latitude_count", o -> o.profileInvalidLatitudeCount);
            put("profile_valid_latitude_percent", o -> o.profileValidLatitudePercent);
            put("profile_invalid_longitude_count", o -> o.profileInvalidLongitudeCount);
            put("profile_valid_longitude_percent", o -> o.profileValidLongitudePercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count.")
    private ColumnNegativeCountCheckSpec profileNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.")
    private ColumnNegativePercentCheckSpec profileNegativePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.")
    private ColumnNonNegativeCountCheckSpec profileNonNegativeCount;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.")
    private ColumnNonNegativePercentCheckSpec profileNonNegativePercent;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).")
    private ColumnExpectedNumbersInUseCountCheckSpec profileExpectedNumbersInUseCount;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.")
    private ColumnNumberValueInSetPercentCheckSpec profileNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnValuesInRangeNumericPercentCheckSpec profileValuesInRangeNumericPercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnValuesInRangeIntegersPercentCheckSpec profileValuesInRangeIntegersPercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is below the value defined by the user as a parameter.")
    private ColumnValueBelowMinValueCountCheckSpec profileValueBelowMinValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is below the value defined by the user as a parameter.")
    private ColumnValueBelowMinValuePercentCheckSpec profileValueBelowMinValuePercent;

    @JsonPropertyDescription("The check counts the number of values in the column that is above the value defined by the user as a parameter.")
    private ColumnValueAboveMaxValueCountCheckSpec profileValueAboveMaxValueCount;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is above the value defined by the user as a parameter.")
    private ColumnValueAboveMaxValuePercentCheckSpec profileValueAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the set range.")
    private ColumnMaxInRangeCheckSpec profileMaxInRange;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the set range.")
    private ColumnMinInRangeCheckSpec profileMinInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the set range.")
    private ColumnMeanInRangeCheckSpec profileMeanInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the set range.")
    private ColumnPercentileInRangeCheckSpec profilePercentileInRange;

    @JsonPropertyDescription("Verifies that the median of all values in a column is not outside the set range.")
    private ColumnMedianInRangeCheckSpec profileMedianInRange;

    @JsonPropertyDescription("Verifies that the percentile 10 of all values in a column is not outside the set range.")
    private ColumnPercentile10InRangeCheckSpec profilePercentile_10InRange;

    @JsonPropertyDescription("Verifies that the percentile 25 of all values in a column is not outside the set range.")
    private ColumnPercentile25InRangeCheckSpec profilePercentile_25InRange;

    @JsonPropertyDescription("Verifies that the percentile 75 of all values in a column is not outside the set range.")
    private ColumnPercentile75InRangeCheckSpec profilePercentile_75InRange;

    @JsonPropertyDescription("Verifies that the percentile 90 of all values in a column is not outside the set range.")
    private ColumnPercentile90InRangeCheckSpec profilePercentile_90InRange;

    @JsonPropertyDescription("Verifies that the sample standard deviation of all values in a column is not outside the set range.")
    private ColumnSampleStddevInRangeCheckSpec profileSampleStddevInRange;

    @JsonPropertyDescription("Verifies that the population standard deviation of all values in a column is not outside the set range.")
    private ColumnPopulationStddevInRangeCheckSpec profilePopulationStddevInRange;

    @JsonPropertyDescription("Verifies that the sample variance of all values in a column is not outside the set range.")
    private ColumnSampleVarianceInRangeCheckSpec profileSampleVarianceInRange;

    @JsonPropertyDescription("Verifies that the population variance of all values in a column is not outside the set range.")
    private ColumnPopulationVarianceInRangeCheckSpec profilePopulationVarianceInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the set range.")
    private ColumnSumInRangeCheckSpec profileSumInRange;

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.")
    private ColumnInvalidLatitudeCountCheckSpec profileInvalidLatitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.")
    private ColumnValidLatitudePercentCheckSpec profileValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.")
    private ColumnInvalidLongitudeCountCheckSpec profileInvalidLongitudeCount;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.")
    private ColumnValidLongitudePercentCheckSpec profileValidLongitudePercent;

    /**
     * Returns a negative count check specification.
     * @return Negative count check specification.
     */
    public ColumnNegativeCountCheckSpec getProfileNegativeCount() {
        return profileNegativeCount;
    }

    /**
     * Sets a new specification of a negative count check.
     * @param profileNegativeCount Negative count check specification.
     */
    public void setProfileNegativeCount(ColumnNegativeCountCheckSpec profileNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.profileNegativeCount, profileNegativeCount));
        this.profileNegativeCount = profileNegativeCount;
        propagateHierarchyIdToField(profileNegativeCount, "profile_negative_count");
    }

    /**
     * Returns a negative percentage check specification.
     * @return Negative percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getProfileNegativePercent() {
        return profileNegativePercent;
    }

    /**
     * Sets a new specification of a negative percentage check.
     * @param profileNegativePercent Negative percentage check specification.
     */
    public void setProfileNegativePercent(ColumnNegativePercentCheckSpec profileNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.profileNegativePercent, profileNegativePercent));
        this.profileNegativePercent = profileNegativePercent;
        propagateHierarchyIdToField(profileNegativePercent, "profile_negative_percent");
    }

    /**
     * Returns a non-negative count check specification.
     * @return Non-negative count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getProfileNonNegativeCount() {
        return profileNonNegativeCount;
    }

    /**
     * Sets a new specification of a non-negative count check.
     * @param profileNonNegativeCount Non-negative count check specification.
     */
    public void setProfileNonNegativeCount(ColumnNonNegativeCountCheckSpec profileNonNegativeCount) {
        this.setDirtyIf(!Objects.equals(this.profileNonNegativeCount, profileNonNegativeCount));
        this.profileNonNegativeCount = profileNonNegativeCount;
        propagateHierarchyIdToField(profileNonNegativeCount, "profile_non_negative_count");
    }

    /**
     * Returns a negative percentage check specification.
     * @return Negative percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getProfileNonNegativePercent() {
        return profileNonNegativePercent;
    }

    /**
     * Sets a new specification of a negative percentage check.
     * @param profileNonNegativePercent Negative percentage check specification.
     */
    public void setProfileNonNegativePercent(ColumnNonNegativePercentCheckSpec profileNonNegativePercent) {
        this.setDirtyIf(!Objects.equals(this.profileNonNegativePercent, profileNonNegativePercent));
        this.profileNonNegativePercent = profileNonNegativePercent;
        propagateHierarchyIdToField(profileNonNegativePercent, "profile_non_negative_percent");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getProfileExpectedNumbersInUseCount() {
        return profileExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers in set count check specification.
     * @param profileExpectedNumbersInUseCount Numbers in set count check specification.
     */
    public void setProfileExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec profileExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.profileExpectedNumbersInUseCount, profileExpectedNumbersInUseCount));
        this.profileExpectedNumbersInUseCount = profileExpectedNumbersInUseCount;
        propagateHierarchyIdToField(profileExpectedNumbersInUseCount, "profile_expected_numbers_in_use_count");
    }

    /**
     * Returns a numbers valid percent check specification.
     * @return Numbers valid percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getProfileNumberValueInSetPercent() {
        return profileNumberValueInSetPercent;
    }

    /**
     * Sets a new specification of a numbers valid percent check specification.
     * @param profileNumberValueInSetPercent Numbers valid percent check specification.
     */
    public void setProfileNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec profileNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.profileNumberValueInSetPercent, profileNumberValueInSetPercent));
        this.profileNumberValueInSetPercent = profileNumberValueInSetPercent;
        propagateHierarchyIdToField(profileNumberValueInSetPercent, "profile_number_value_in_set_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeNumericPercentCheckSpec getProfileValuesInRangeNumericPercent() {
        return profileValuesInRangeNumericPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param profileValuesInRangeNumericPercent Numbers in set percent check specification.
     */
    public void setProfileValuesInRangeNumericPercent(ColumnValuesInRangeNumericPercentCheckSpec profileValuesInRangeNumericPercent) {
        this.setDirtyIf(!Objects.equals(this.profileValuesInRangeNumericPercent, profileValuesInRangeNumericPercent));
        this.profileValuesInRangeNumericPercent = profileValuesInRangeNumericPercent;
        propagateHierarchyIdToField(profileValuesInRangeNumericPercent, "profile_values_in_range_numeric_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnValuesInRangeIntegersPercentCheckSpec getProfileValuesInRangeIntegersPercent() {
        return profileValuesInRangeIntegersPercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param profileValuesInRangeIntegersPercent Numbers in set percent check specification.
     */
    public void setProfileValuesInRangeIntegersPercent(ColumnValuesInRangeIntegersPercentCheckSpec profileValuesInRangeIntegersPercent) {
        this.setDirtyIf(!Objects.equals(this.profileValuesInRangeIntegersPercent, profileValuesInRangeIntegersPercent));
        this.profileValuesInRangeIntegersPercent = profileValuesInRangeIntegersPercent;
        propagateHierarchyIdToField(profileValuesInRangeIntegersPercent, "profile_values_in_range_integers_percent");
    }

    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnValueBelowMinValueCountCheckSpec getProfileValueBelowMinValueCount() {
        return profileValueBelowMinValueCount;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param profileValueBelowMinValueCount Numeric value below min value count check.
     */
    public void setProfileValueBelowMinValueCount(ColumnValueBelowMinValueCountCheckSpec profileValueBelowMinValueCount) {
        this.setDirtyIf(!Objects.equals(this.profileValueBelowMinValueCount, profileValueBelowMinValueCount));
        this.profileValueBelowMinValueCount = profileValueBelowMinValueCount;
        propagateHierarchyIdToField(profileValueBelowMinValueCount, "profile_value_below_min_value_count");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnValueBelowMinValuePercentCheckSpec getProfileValueBelowMinValuePercent() {
        return profileValueBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param profileValueBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setProfileValueBelowMinValuePercent(ColumnValueBelowMinValuePercentCheckSpec profileValueBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.profileValueBelowMinValuePercent, profileValueBelowMinValuePercent));
        this.profileValueBelowMinValuePercent = profileValueBelowMinValuePercent;
        propagateHierarchyIdToField(profileValueBelowMinValuePercent, "profile_value_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnValueAboveMaxValueCountCheckSpec getProfileValueAboveMaxValueCount() {
        return profileValueAboveMaxValueCount;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param profileValueAboveMaxValueCount Numeric value above max value count check.
     */
    public void setProfileValueAboveMaxValueCount(ColumnValueAboveMaxValueCountCheckSpec profileValueAboveMaxValueCount) {
        this.setDirtyIf(!Objects.equals(this.profileValueAboveMaxValueCount, profileValueAboveMaxValueCount));
        this.profileValueAboveMaxValueCount = profileValueAboveMaxValueCount;
        propagateHierarchyIdToField(profileValueAboveMaxValueCount, "profile_value_above_max_value_count");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnValueAboveMaxValuePercentCheckSpec getProfileValueAboveMaxValuePercent() {
        return profileValueAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param profileValueAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setProfileValueAboveMaxValuePercent(ColumnValueAboveMaxValuePercentCheckSpec profileValueAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.profileValueAboveMaxValuePercent, profileValueAboveMaxValuePercent));
        this.profileValueAboveMaxValuePercent = profileValueAboveMaxValuePercent;
        propagateHierarchyIdToField(profileValueAboveMaxValuePercent, "profile_value_above_max_value_percent");
    }

    /**
     * Returns a max in range check specification.
     * @return Max in range check specification.
     */
    public ColumnMaxInRangeCheckSpec getProfileMaxInRange() {
        return profileMaxInRange;
    }

    /**
     * Sets a max in range percent check.
     * @param profileMaxInRange Max in range check specification.
     */
    public void setProfileMaxInRange(ColumnMaxInRangeCheckSpec profileMaxInRange) {
        this.setDirtyIf(!Objects.equals(this.profileMaxInRange, profileMaxInRange));
        this.profileMaxInRange = profileMaxInRange;
        propagateHierarchyIdToField(profileMaxInRange, "profile_max_in_range");
    }

    /**
     * Returns a min in range check specification.
     * @return Min in range check specification.
     */
    public ColumnMinInRangeCheckSpec getProfileMinInRange() {
        return profileMinInRange;
    }

    /**
     * Sets a new specification of a min in range check.
     * @param profileMinInRange Min in range check specification.
     */
    public void setProfileMinInRange(ColumnMinInRangeCheckSpec profileMinInRange) {
        this.setDirtyIf(!Objects.equals(this.profileMinInRange, profileMinInRange));
        this.profileMinInRange = profileMinInRange;
        propagateHierarchyIdToField(profileMinInRange, "profile_min_in_range");
    }

    /**
     * Returns a mean in range check specification.
     * @return Mean in range check specification.
     */
    public ColumnMeanInRangeCheckSpec getProfileMeanInRange() {
        return profileMeanInRange;
    }

    /**
     * Sets a new specification of a mean in range check.
     * @param profileMeanInRange Mean in range check specification.
     */
    public void setProfileMeanInRange(ColumnMeanInRangeCheckSpec profileMeanInRange) {
        this.setDirtyIf(!Objects.equals(this.profileMeanInRange, profileMeanInRange));
        this.profileMeanInRange = profileMeanInRange;
        propagateHierarchyIdToField(profileMeanInRange, "profile_mean_in_range");
    }

    /**
     * Returns a percentile in range check specification.
     * @return Percentile in range check specification.
     */
    public ColumnPercentileInRangeCheckSpec getProfilePercentileInRange() {
        return profilePercentileInRange;
    }

    /**
     * Sets a new specification of a percentile in range check.
     * @param profilePercentileInRange Percentile in range check specification.
     */
    public void setProfilePercentileInRange(ColumnPercentileInRangeCheckSpec profilePercentileInRange) {
        this.setDirtyIf(!Objects.equals(this.profilePercentileInRange, profilePercentileInRange));
        this.profilePercentileInRange = profilePercentileInRange;
        propagateHierarchyIdToField(profilePercentileInRange, "profile_percentile_in_range");
    }

    /**
     * Returns a median in range check specification.
     * @return median in range check specification.
     */
    public ColumnMedianInRangeCheckSpec getProfileMedianInRange() {
        return profileMedianInRange;
    }

    /**
     * Sets a new specification of a median in range check.
     * @param profileMedianInRange median in range check specification.
     */
    public void setProfileMedianInRange(ColumnMedianInRangeCheckSpec profileMedianInRange) {
        this.setDirtyIf(!Objects.equals(this.profileMedianInRange, profileMedianInRange));
        this.profileMedianInRange = profileMedianInRange;
        propagateHierarchyIdToField(profileMedianInRange, "profile_median_in_range");
    }

    /**
     * Returns a percentile 10 in range check specification.
     * @return Percentile 10 in range check specification.
     */
    public ColumnPercentile10InRangeCheckSpec getProfilePercentile_10InRange() {
        return profilePercentile_10InRange;
    }

    /**
     * Sets a new specification of a percentile 10 in range check.
     * @param profilePercentile_10InRange Percentile 10 in range check specification.
     */
    public void setProfilePercentile_10InRange(ColumnPercentile10InRangeCheckSpec profilePercentile_10InRange) {
        this.setDirtyIf(!Objects.equals(this.profilePercentile_10InRange, profilePercentile_10InRange));
        this.profilePercentile_10InRange = profilePercentile_10InRange;
        propagateHierarchyIdToField(profilePercentile_10InRange, "profile_percentile_10_in_range");
    }

    /**
     * Returns a percentile 25 in range check specification.
     * @return Percentile 25 in range check specification.
     */
    public ColumnPercentile25InRangeCheckSpec getProfilePercentile_25InRange() {
        return profilePercentile_25InRange;
    }

    /**
     * Sets a new specification of a percentile 25 in range check.
     * @param profilePercentile_25InRange Percentile 25 in range check specification.
     */
    public void setProfilePercentile_25InRange(ColumnPercentile25InRangeCheckSpec profilePercentile_25InRange) {
        this.setDirtyIf(!Objects.equals(this.profilePercentile_25InRange, profilePercentile_25InRange));
        this.profilePercentile_25InRange = profilePercentile_25InRange;
        propagateHierarchyIdToField(profilePercentile_25InRange, "profile_percentile_25_in_range");
    }

    /**
     * Returns a percentile 75 in range check specification.
     * @return Percentile 75 in range check specification.
     */
    public ColumnPercentile75InRangeCheckSpec getProfilePercentile_75InRange() {
        return profilePercentile_75InRange;
    }

    /**
     * Sets a new specification of a percentile 75 in range check.
     * @param profilePercentile_75InRange Percentile 75 in range check specification.
     */
    public void setProfilePercentile_75InRange(ColumnPercentile75InRangeCheckSpec profilePercentile_75InRange) {
        this.setDirtyIf(!Objects.equals(this.profilePercentile_75InRange, profilePercentile_75InRange));
        this.profilePercentile_75InRange = profilePercentile_75InRange;
        propagateHierarchyIdToField(profilePercentile_75InRange, "profile_percentile_75_in_range");
    }

    /**
     * Returns a percentile 90 in range check specification.
     * @return Percentile 90 in range check specification.
     */
    public ColumnPercentile90InRangeCheckSpec getProfilePercentile_90InRange() {
        return profilePercentile_90InRange;
    }

    /**
     * Sets a new specification of a percentile 90 in range check.
     * @param profilePercentile_90InRange Percentile 90 in range check specification.
     */
    public void setProfilePercentile_90InRange(ColumnPercentile90InRangeCheckSpec profilePercentile_90InRange) {
        this.setDirtyIf(!Objects.equals(this.profilePercentile_90InRange, profilePercentile_90InRange));
        this.profilePercentile_90InRange = profilePercentile_90InRange;
        propagateHierarchyIdToField(profilePercentile_90InRange, "profile_percentile_90_in_range");
    }

    /**
     * Returns a sample standard deviation in range check specification.
     * @return Sample standard deviation in range check specification.
     */
    public ColumnSampleStddevInRangeCheckSpec getProfileSampleStddevInRange() {
        return profileSampleStddevInRange;
    }

    /**
     * Sets a new specification of a sample standard deviation in range check.
     * @param profileSampleStddevInRange Sample standard deviation in range check specification.
     */
    public void setProfileSampleStddevInRange(ColumnSampleStddevInRangeCheckSpec profileSampleStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.profileSampleStddevInRange, profileSampleStddevInRange));
        this.profileSampleStddevInRange = profileSampleStddevInRange;
        propagateHierarchyIdToField(profileSampleStddevInRange, "profile_sample_stddev_in_range");
    }

    /**
     * Returns a population standard deviation in range check specification.
     * @return Population standard deviation in range check specification.
     */
    public ColumnPopulationStddevInRangeCheckSpec getProfilePopulationStddevInRange() {
        return profilePopulationStddevInRange;
    }

    /**
     * Sets a new specification of a population standard deviation in range check.
     * @param profilePopulationStddevInRange Population standard deviation in range check specification.
     */
    public void setProfilePopulationStddevInRange(ColumnPopulationStddevInRangeCheckSpec profilePopulationStddevInRange) {
        this.setDirtyIf(!Objects.equals(this.profilePopulationStddevInRange, profilePopulationStddevInRange));
        this.profilePopulationStddevInRange = profilePopulationStddevInRange;
        propagateHierarchyIdToField(profilePopulationStddevInRange, "profile_population_stddev_in_range");
    }

    /**
     * Returns a sample variance in range check specification.
     * @return Sample variance in range check specification.
     */
    public ColumnSampleVarianceInRangeCheckSpec getProfileSampleVarianceInRange() {
        return profileSampleVarianceInRange;
    }

    /**
     * Sets a new specification of a sample variance in range check.
     * @param profileSampleVarianceInRange Sample variance in range check specification.
     */
    public void setProfileSampleVarianceInRange(ColumnSampleVarianceInRangeCheckSpec profileSampleVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.profileSampleVarianceInRange, profileSampleVarianceInRange));
        this.profileSampleVarianceInRange = profileSampleVarianceInRange;
        propagateHierarchyIdToField(profileSampleVarianceInRange, "profile_sample_variance_in_range");
    }

    /**
     * Returns a population variance in range check specification.
     * @return Population variance in range check specification.
     */
    public ColumnPopulationVarianceInRangeCheckSpec getProfilePopulationVarianceInRange() {
        return profilePopulationVarianceInRange;
    }

    /**
     * Sets a new specification of a population variance in range check.
     * @param profilePopulationVarianceInRange Population variance in range check specification.
     */
    public void setProfilePopulationVarianceInRange(ColumnPopulationVarianceInRangeCheckSpec profilePopulationVarianceInRange) {
        this.setDirtyIf(!Objects.equals(this.profilePopulationVarianceInRange, profilePopulationVarianceInRange));
        this.profilePopulationVarianceInRange = profilePopulationVarianceInRange;
        propagateHierarchyIdToField(profilePopulationVarianceInRange, "profile_population_variance_in_range");
    }

    /**
     * Returns a sum in range check specification.
     * @return Sum in range check specification.
     */
    public ColumnSumInRangeCheckSpec getProfileSumInRange() {
        return profileSumInRange;
    }

    /**
     * Sets a new specification of a sum in range check.
     * @param profileSumInRange Sum in range check specification.
     */
    public void setProfileSumInRange(ColumnSumInRangeCheckSpec profileSumInRange) {
        this.setDirtyIf(!Objects.equals(this.profileSumInRange, profileSumInRange));
        this.profileSumInRange = profileSumInRange;
        propagateHierarchyIdToField(profileSumInRange, "profile_sum_in_range");
    }

    /**
     * Returns an invalid latitude count check specification.
     * @return Invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getProfileInvalidLatitudeCount() {
        return profileInvalidLatitudeCount;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param profileInvalidLatitudeCount Invalid latitude count check specification.
     */
    public void setProfileInvalidLatitudeCount(ColumnInvalidLatitudeCountCheckSpec profileInvalidLatitudeCount) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidLatitudeCount, profileInvalidLatitudeCount));
        this.profileInvalidLatitudeCount = profileInvalidLatitudeCount;
        propagateHierarchyIdToField(profileInvalidLatitudeCount, "profile_invalid_latitude_count");
    }

    /**
     * Returns a valid latitude percent check specification.
     * @return Valid latitude percent check specification.
     */
    public ColumnValidLatitudePercentCheckSpec getProfileValidLatitudePercent() {
        return profileValidLatitudePercent;
    }

    /**
     * Sets a new specification of a valid latitude percent check.
     * @param profileValidLatitudePercent Valid latitude count percent specification.
     */
    public void setProfileValidLatitudePercent(ColumnValidLatitudePercentCheckSpec profileValidLatitudePercent) {
        this.setDirtyIf(!Objects.equals(this.profileValidLatitudePercent, profileValidLatitudePercent));
        this.profileValidLatitudePercent = profileValidLatitudePercent;
        propagateHierarchyIdToField(profileValidLatitudePercent, "profile_valid_latitude_percent");
    }

    /**
     * Returns an invalid longitude count check specification.
     * @return Invalid longitude count check specification.
     */
    public ColumnInvalidLongitudeCountCheckSpec getProfileInvalidLongitudeCount() {
        return profileInvalidLongitudeCount;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param profileInvalidLongitudeCount Invalid longitude count check specification.
     */
    public void setProfileInvalidLongitudeCount(ColumnInvalidLongitudeCountCheckSpec profileInvalidLongitudeCount) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidLongitudeCount, profileInvalidLongitudeCount));
        this.profileInvalidLongitudeCount = profileInvalidLongitudeCount;
        propagateHierarchyIdToField(profileInvalidLongitudeCount, "profile_invalid_longitude_count");
    }

    /**
     * Returns a valid longitude percent check specification.
     * @return Valid longitude percent check specification.
     */
    public ColumnValidLongitudePercentCheckSpec getProfileValidLongitudePercent() {
        return profileValidLongitudePercent;
    }

    /**
     * Sets a new specification of a valid longitude percent check.
     * @param profileValidLongitudePercent Valid longitude count percent specification.
     */
    public void setProfileValidLongitudePercent(ColumnValidLongitudePercentCheckSpec profileValidLongitudePercent) {
        this.setDirtyIf(!Objects.equals(this.profileValidLongitudePercent, profileValidLongitudePercent));
        this.profileValidLongitudePercent = profileValidLongitudePercent;
        propagateHierarchyIdToField(profileValidLongitudePercent, "profile_valid_longitude_percent");
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

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public ColumnNumericProfilingChecksSpec deepClone() {
        return (ColumnNumericProfilingChecksSpec)super.deepClone();
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
        return CheckType.profiling;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return null;
    }
}
