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
import com.dqops.checks.column.checkspecs.acceptedvalues.ColumnExpectedNumbersInUseCountCheckSpec;
import com.dqops.checks.column.checkspecs.acceptedvalues.ColumnNumberValueInSetPercentCheckSpec;
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
            put("profile_number_below_min_value", o -> o.profileNumberBelowMinValue);
            put("profile_number_above_max_value", o -> o.profileNumberAboveMaxValue);
            put("profile_negative_values", o -> o.profileNegativeValues);
            put("profile_negative_values_percent", o -> o.profileNegativeValuesPercent);
            put("profile_number_below_min_value_percent", o -> o.profileNumberBelowMinValuePercent);
            put("profile_number_above_max_value_percent", o -> o.profileNumberAboveMaxValuePercent);
            put("profile_number_in_range_percent", o -> o.profileNumberInRangePercent);
            put("profile_integer_in_range_percent", o -> o.profileIntegerInRangePercent);
            put("profile_min_in_range", o -> o.profileMinInRange);
            put("profile_max_in_range", o -> o.profileMaxInRange);
            put("profile_sum_in_range", o -> o.profileSumInRange);
            put("profile_mean_in_range", o -> o.profileMeanInRange);
            put("profile_median_in_range", o -> o.profileMedianInRange);
            put("profile_percentile_in_range", o -> o.profilePercentileInRange);
            put("profile_percentile_10_in_range", o -> o.profilePercentile_10InRange);
            put("profile_percentile_25_in_range", o -> o.profilePercentile_25InRange);
            put("profile_percentile_75_in_range", o -> o.profilePercentile_75InRange);
            put("profile_percentile_90_in_range", o -> o.profilePercentile_90InRange);
            put("profile_sample_stddev_in_range", o -> o.profileSampleStddevInRange);
            put("profile_population_stddev_in_range", o -> o.profilePopulationStddevInRange);
            put("profile_sample_variance_in_range", o -> o.profileSampleVarianceInRange);
            put("profile_population_variance_in_range", o -> o.profilePopulationVarianceInRange);
            put("profile_invalid_latitude", o -> o.profileInvalidLatitude);
            put("profile_valid_latitude_percent", o -> o.profileValidLatitudePercent);
            put("profile_invalid_longitude", o -> o.profileInvalidLongitude);
            put("profile_valid_longitude_percent", o -> o.profileValidLongitudePercent);
            put("profile_non_negative_values", o -> o.profileNonNegativeValues);
            put("profile_non_negative_values_percent", o -> o.profileNonNegativeValuesPercent);
        }
    };

    @JsonPropertyDescription("The check counts the number of values in the column that is below the value defined by the user as a parameter.")
    private ColumnNumberBelowMinValueCheckSpec profileNumberBelowMinValue;

    @JsonPropertyDescription("The check counts the number of values in the column that is above the value defined by the user as a parameter.")
    private ColumnNumberAboveMaxValueCheckSpec profileNumberAboveMaxValue;

    @JsonPropertyDescription("Verifies that the number of negative values in a column does not exceed the maximum accepted count.")
    private ColumnNegativeCountCheckSpec profileNegativeValues;

    @JsonPropertyDescription("Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.")
    private ColumnNegativePercentCheckSpec profileNegativeValuesPercent;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is below the value defined by the user as a parameter.")
    private ColumnNumberBelowMinValuePercentCheckSpec profileNumberBelowMinValuePercent;

    @JsonPropertyDescription("The check counts the percentage of values in the column that is above the value defined by the user as a parameter.")
    private ColumnNumberAboveMaxValuePercentCheckSpec profileNumberAboveMaxValuePercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnNumberInRangePercentCheckSpec profileNumberInRangePercent;

    @JsonPropertyDescription("Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.")
    private ColumnIntegerInRangePercentCheckSpec profileIntegerInRangePercent;

    @JsonPropertyDescription("Verifies that the minimal value in a column is not outside the set range.")
    private ColumnMinInRangeCheckSpec profileMinInRange;

    @JsonPropertyDescription("Verifies that the maximal value in a column is not outside the set range.")
    private ColumnMaxInRangeCheckSpec profileMaxInRange;

    @JsonPropertyDescription("Verifies that the sum of all values in a column is not outside the set range.")
    private ColumnSumInRangeCheckSpec profileSumInRange;

    @JsonPropertyDescription("Verifies that the average (mean) of all values in a column is not outside the set range.")
    private ColumnMeanInRangeCheckSpec profileMeanInRange;

    @JsonPropertyDescription("Verifies that the median of all values in a column is not outside the set range.")
    private ColumnMedianInRangeCheckSpec profileMedianInRange;

    @JsonPropertyDescription("Verifies that the percentile of all values in a column is not outside the set range.")
    private ColumnPercentileInRangeCheckSpec profilePercentileInRange;

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

    @JsonPropertyDescription("Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.")
    private ColumnInvalidLatitudeCountCheckSpec profileInvalidLatitude;

    @JsonPropertyDescription("Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.")
    private ColumnValidLatitudePercentCheckSpec profileValidLatitudePercent;

    @JsonPropertyDescription("Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.")
    private ColumnInvalidLongitudeCountCheckSpec profileInvalidLongitude;

    @JsonPropertyDescription("Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.")
    private ColumnValidLongitudePercentCheckSpec profileValidLongitudePercent;

    @JsonPropertyDescription("Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.")
    private ColumnNonNegativeCountCheckSpec profileNonNegativeValues;

    @JsonPropertyDescription("Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.")
    private ColumnNonNegativePercentCheckSpec profileNonNegativeValuesPercent;

    /**
     * Returns a numeric value below min value count check.
     * @return Numeric value below min value count check.
     */
    public ColumnNumberBelowMinValueCheckSpec getProfileNumberBelowMinValue() {
        return profileNumberBelowMinValue;
    }

    /**
     * Sets a new definition of a numeric value below min value count check.
     * @param profileNumberBelowMinValue Numeric value below min value count check.
     */
    public void setProfileNumberBelowMinValue(ColumnNumberBelowMinValueCheckSpec profileNumberBelowMinValue) {
        this.setDirtyIf(!Objects.equals(this.profileNumberBelowMinValue, profileNumberBelowMinValue));
        this.profileNumberBelowMinValue = profileNumberBelowMinValue;
        propagateHierarchyIdToField(profileNumberBelowMinValue, "profile_number_below_min_value");
    }

    /**
     * Returns a numeric value above max value count check.
     * @return Numeric value above max value count check.
     */
    public ColumnNumberAboveMaxValueCheckSpec getProfileNumberAboveMaxValue() {
        return profileNumberAboveMaxValue;
    }

    /**
     * Sets a new definition of a numeric value above max value count check.
     * @param profileNumberAboveMaxValue Numeric value above max value count check.
     */
    public void setProfileNumberAboveMaxValue(ColumnNumberAboveMaxValueCheckSpec profileNumberAboveMaxValue) {
        this.setDirtyIf(!Objects.equals(this.profileNumberAboveMaxValue, profileNumberAboveMaxValue));
        this.profileNumberAboveMaxValue = profileNumberAboveMaxValue;
        propagateHierarchyIdToField(profileNumberAboveMaxValue, "profile_number_above_max_value");
    }

    /**
     * Returns a negative count check specification.
     * @return Negative count check specification.
     */
    public ColumnNegativeCountCheckSpec getProfileNegativeValues() {
        return profileNegativeValues;
    }

    /**
     * Sets a new specification of a negative count check.
     * @param profileNegativeValues Negative count check specification.
     */
    public void setProfileNegativeValues(ColumnNegativeCountCheckSpec profileNegativeValues) {
        this.setDirtyIf(!Objects.equals(this.profileNegativeValues, profileNegativeValues));
        this.profileNegativeValues = profileNegativeValues;
        propagateHierarchyIdToField(profileNegativeValues, "profile_negative_values");
    }

    /**
     * Returns a negative percentage check specification.
     * @return Negative percentage check specification.
     */
    public ColumnNegativePercentCheckSpec getProfileNegativeValuesPercent() {
        return profileNegativeValuesPercent;
    }

    /**
     * Sets a new specification of a negative percentage check.
     * @param profileNegativeValuesPercent Negative percentage check specification.
     */
    public void setProfileNegativeValuesPercent(ColumnNegativePercentCheckSpec profileNegativeValuesPercent) {
        this.setDirtyIf(!Objects.equals(this.profileNegativeValuesPercent, profileNegativeValuesPercent));
        this.profileNegativeValuesPercent = profileNegativeValuesPercent;
        propagateHierarchyIdToField(profileNegativeValuesPercent, "profile_negative_values_percent");
    }

    /**
     * Returns a numeric value below min value percent check.
     * @return Numeric value below min value percent check.
     */
    public ColumnNumberBelowMinValuePercentCheckSpec getProfileNumberBelowMinValuePercent() {
        return profileNumberBelowMinValuePercent;
    }

    /**
     * Sets a new definition of a numeric value below min value percent check.
     * @param profileNumberBelowMinValuePercent Numeric value below min value percent check.
     */
    public void setProfileNumberBelowMinValuePercent(ColumnNumberBelowMinValuePercentCheckSpec profileNumberBelowMinValuePercent) {
        this.setDirtyIf(!Objects.equals(this.profileNumberBelowMinValuePercent, profileNumberBelowMinValuePercent));
        this.profileNumberBelowMinValuePercent = profileNumberBelowMinValuePercent;
        propagateHierarchyIdToField(profileNumberBelowMinValuePercent, "profile_number_below_min_value_percent");
    }

    /**
     * Returns a numeric value above max value percent check.
     * @return Numeric value above max value percent check.
     */
    public ColumnNumberAboveMaxValuePercentCheckSpec getProfileNumberAboveMaxValuePercent() {
        return profileNumberAboveMaxValuePercent;
    }

    /**
     * Sets a new definition of a numeric value above max value percent check.
     * @param profileNumberAboveMaxValuePercent Numeric value above max value percent check.
     */
    public void setProfileNumberAboveMaxValuePercent(ColumnNumberAboveMaxValuePercentCheckSpec profileNumberAboveMaxValuePercent) {
        this.setDirtyIf(!Objects.equals(this.profileNumberAboveMaxValuePercent, profileNumberAboveMaxValuePercent));
        this.profileNumberAboveMaxValuePercent = profileNumberAboveMaxValuePercent;
        propagateHierarchyIdToField(profileNumberAboveMaxValuePercent, "profile_number_above_max_value_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnNumberInRangePercentCheckSpec getProfileNumberInRangePercent() {
        return profileNumberInRangePercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param profileNumberInRangePercent Numbers in set percent check specification.
     */
    public void setProfileNumberInRangePercent(ColumnNumberInRangePercentCheckSpec profileNumberInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.profileNumberInRangePercent, profileNumberInRangePercent));
        this.profileNumberInRangePercent = profileNumberInRangePercent;
        propagateHierarchyIdToField(profileNumberInRangePercent, "profile_number_in_range_percent");
    }

    /**
     * Returns a numbers in set percent check specification.
     * @return Numbers in set percent check specification.
     */
    public ColumnIntegerInRangePercentCheckSpec getProfileIntegerInRangePercent() {
        return profileIntegerInRangePercent;
    }

    /**
     * Sets a new specification of a numbers in set percent check.
     * @param profileIntegerInRangePercent Numbers in set percent check specification.
     */
    public void setProfileIntegerInRangePercent(ColumnIntegerInRangePercentCheckSpec profileIntegerInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.profileIntegerInRangePercent, profileIntegerInRangePercent));
        this.profileIntegerInRangePercent = profileIntegerInRangePercent;
        propagateHierarchyIdToField(profileIntegerInRangePercent, "profile_integer_in_range_percent");
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
     * Returns an invalid latitude count check specification.
     * @return Invalid latitude count check specification.
     */
    public ColumnInvalidLatitudeCountCheckSpec getProfileInvalidLatitude() {
        return profileInvalidLatitude;
    }

    /**
     * Sets a new specification of an invalid latitude count check.
     * @param profileInvalidLatitude Invalid latitude count check specification.
     */
    public void setProfileInvalidLatitude(ColumnInvalidLatitudeCountCheckSpec profileInvalidLatitude) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidLatitude, profileInvalidLatitude));
        this.profileInvalidLatitude = profileInvalidLatitude;
        propagateHierarchyIdToField(profileInvalidLatitude, "profile_invalid_latitude");
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
    public ColumnInvalidLongitudeCountCheckSpec getProfileInvalidLongitude() {
        return profileInvalidLongitude;
    }

    /**
     * Sets a new specification of an invalid longitude count check.
     * @param profileInvalidLongitude Invalid longitude count check specification.
     */
    public void setProfileInvalidLongitude(ColumnInvalidLongitudeCountCheckSpec profileInvalidLongitude) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidLongitude, profileInvalidLongitude));
        this.profileInvalidLongitude = profileInvalidLongitude;
        propagateHierarchyIdToField(profileInvalidLongitude, "profile_invalid_longitude");
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
     * Returns a non-negative count check specification.
     * @return Non-negative count check specification.
     */
    public ColumnNonNegativeCountCheckSpec getProfileNonNegativeValues() {
        return profileNonNegativeValues;
    }

    /**
     * Sets a new specification of a non-negative count check.
     * @param profileNonNegativeValues Non-negative count check specification.
     */
    public void setProfileNonNegativeValues(ColumnNonNegativeCountCheckSpec profileNonNegativeValues) {
        this.setDirtyIf(!Objects.equals(this.profileNonNegativeValues, profileNonNegativeValues));
        this.profileNonNegativeValues = profileNonNegativeValues;
        propagateHierarchyIdToField(profileNonNegativeValues, "profile_non_negative_values");
    }

    /**
     * Returns a negative percentage check specification.
     * @return Negative percentage check specification.
     */
    public ColumnNonNegativePercentCheckSpec getProfileNonNegativeValuesPercent() {
        return profileNonNegativeValuesPercent;
    }

    /**
     * Sets a new specification of a negative percentage check.
     * @param profileNonNegativeValuesPercent Negative percentage check specification.
     */
    public void setProfileNonNegativeValuesPercent(ColumnNonNegativePercentCheckSpec profileNonNegativeValuesPercent) {
        this.setDirtyIf(!Objects.equals(this.profileNonNegativeValuesPercent, profileNonNegativeValuesPercent));
        this.profileNonNegativeValuesPercent = profileNonNegativeValuesPercent;
        propagateHierarchyIdToField(profileNonNegativeValuesPercent, "profile_non_negative_values_percent");
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
