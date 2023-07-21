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
import com.dqops.checks.column.checkspecs.anomaly.*;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level for detecting anomalies.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAnomalyProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("mean_anomaly_stationary_30_days", o -> o.meanAnomalyStationary30Days);
            put("mean_anomaly_stationary", o -> o.meanAnomalyStationary);

            put("median_anomaly_stationary_30_days", o -> o.medianAnomalyStationary30Days);
            put("median_anomaly_stationary", o -> o.medianAnomalyStationary);

            put("sum_anomaly_differencing_30_days", o -> o.sumAnomalyDifferencing30Days);
            put("sum_anomaly_differencing", o -> o.sumAnomalyDifferencing);

            put("mean_change", o -> o.meanChange);
            put("mean_change_yesterday", o -> o.meanChangeYesterday);
            put("mean_change_7_days", o -> o.meanChange7Days);
            put("mean_change_30_days", o -> o.meanChange30Days);

            put("median_change", o -> o.medianChange);
            put("median_change_yesterday", o -> o.medianChangeYesterday);
            put("median_change_7_days", o -> o.medianChange7Days);
            put("median_change_30_days", o -> o.medianChange30Days);

            put("sum_change", o -> o.sumChange);
            put("sum_change_yesterday", o -> o.sumChangeYesterday);
            put("sum_change_7_days", o -> o.sumChange7Days);
            put("sum_change_30_days", o -> o.sumChange30Days);
        }
    };

    @JsonProperty("mean_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryMean30DaysCheckSpec meanAnomalyStationary30Days;

    @JsonProperty("mean_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryMeanCheckSpec meanAnomalyStationary;

    @JsonProperty("median_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryMedian30DaysCheckSpec medianAnomalyStationary30Days;

    @JsonProperty("median_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryMedianCheckSpec medianAnomalyStationary;

    @JsonProperty("sum_anomaly_differencing_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyDifferencingSum30DaysCheckSpec sumAnomalyDifferencing30Days;

    @JsonProperty("sum_anomaly_differencing")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyDifferencingSumCheckSpec sumAnomalyDifferencing;
    
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout.")
    private ColumnChangeMeanCheckSpec meanChange;

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeMeanSinceYesterdayCheckSpec meanChangeYesterday;

    @JsonProperty("mean_change_7_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeMeanSince7DaysCheckSpec meanChange7Days;

    @JsonProperty("mean_change_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeMeanSince30DaysCheckSpec meanChange30Days;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout.")
    private ColumnChangeMedianCheckSpec medianChange;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeMedianSinceYesterdayCheckSpec medianChangeYesterday;

    @JsonProperty("median_change_7_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeMedianSince7DaysCheckSpec medianChange7Days;

    @JsonProperty("median_change_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeMedianSince30DaysCheckSpec medianChange30Days;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout.")
    private ColumnChangeSumCheckSpec sumChange;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeSumSinceYesterdayCheckSpec sumChangeYesterday;

    @JsonProperty("sum_change_7_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeSumSince7DaysCheckSpec sumChange7Days;

    @JsonProperty("sum_change_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeSumSince30DaysCheckSpec sumChange30Days;


    /**
     * Returns a mean value anomaly 30 days check specification.
     * @return Mean value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryMean30DaysCheckSpec getMeanAnomalyStationary30Days() {
        return meanAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 30 days check.
     * @param meanAnomalyStationary30Days Mean value anomaly 30 days check specification.
     */
    public void setMeanAnomalyStationary30Days(ColumnAnomalyStationaryMean30DaysCheckSpec meanAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.meanAnomalyStationary30Days, meanAnomalyStationary30Days));
        this.meanAnomalyStationary30Days = meanAnomalyStationary30Days;
        propagateHierarchyIdToField(meanAnomalyStationary30Days, "mean_anomaly_stationary_30_days");
    }

    /**
     * Returns a mean value anomaly 60 days check specification.
     * @return Mean value anomaly 60 days check specification.
     */
    public ColumnAnomalyStationaryMeanCheckSpec getMeanAnomalyStationary() {
        return meanAnomalyStationary;
    }

    /**
     * Sets a new specification of a mean value anomaly 60 days check.
     * @param meanAnomalyStationary Mean value anomaly 60 days check specification.
     */
    public void setMeanAnomalyStationary(ColumnAnomalyStationaryMeanCheckSpec meanAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.meanAnomalyStationary, meanAnomalyStationary));
        this.meanAnomalyStationary = meanAnomalyStationary;
        propagateHierarchyIdToField(meanAnomalyStationary, "mean_anomaly_stationary");
    }

    /**
     * Returns a median anomaly 30 days check specification.
     * @return Median anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryMedian30DaysCheckSpec getMedianAnomalyStationary30Days() {
        return medianAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a median anomaly 30 days check.
     * @param medianAnomalyStationary30Days Median anomaly 30 days check specification.
     */
    public void setMedianAnomalyStationary30Days(ColumnAnomalyStationaryMedian30DaysCheckSpec medianAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.medianAnomalyStationary30Days, medianAnomalyStationary30Days));
        this.medianAnomalyStationary30Days = medianAnomalyStationary30Days;
        propagateHierarchyIdToField(medianAnomalyStationary30Days, "median_anomaly_stationary_30_days");
    }

    /**
     * Returns a median anomaly 60 days check specification.
     * @return Median anomaly 60 days check specification.
     */
    public ColumnAnomalyStationaryMedianCheckSpec getMedianAnomalyStationary() {
        return medianAnomalyStationary;
    }

    /**
     * Sets a new specification of a median anomaly 60 days check.
     * @param medianAnomalyStationary Median anomaly 60 days check specification.
     */
    public void setMedianAnomalyStationary(ColumnAnomalyStationaryMedianCheckSpec medianAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.medianAnomalyStationary, medianAnomalyStationary));
        this.medianAnomalyStationary = medianAnomalyStationary;
        propagateHierarchyIdToField(medianAnomalyStationary, "median_anomaly_stationary");
    }

    /**
     * Returns a sum anomaly 30 days check specification.
     * @return Sum anomaly 30 days check specification.
     */
    public ColumnAnomalyDifferencingSum30DaysCheckSpec getSumAnomalyDifferencing30Days() {
        return sumAnomalyDifferencing30Days;
    }

    /**
     * Sets a new specification of a sum anomaly 30 days check.
     * @param sumAnomalyDifferencing30Days Sum anomaly 30 days check specification.
     */
    public void setSumAnomalyDifferencing30Days(ColumnAnomalyDifferencingSum30DaysCheckSpec sumAnomalyDifferencing30Days) {
        this.setDirtyIf(!Objects.equals(this.sumAnomalyDifferencing30Days, sumAnomalyDifferencing30Days));
        this.sumAnomalyDifferencing30Days = sumAnomalyDifferencing30Days;
        propagateHierarchyIdToField(sumAnomalyDifferencing30Days, "sum_anomaly_differencing_30_days");
    }

    /**
     * Returns a sum anomaly 60 days check specification.
     * @return Sum anomaly 60 days check specification.
     */
    public ColumnAnomalyDifferencingSumCheckSpec getSumAnomalyDifferencing() {
        return sumAnomalyDifferencing;
    }
    
    /**
     * Sets a new specification of a sum anomaly 60 days check.
     * @param sumAnomalyDifferencing Sum anomaly 60 days check specification.
     */
    public void setSumAnomalyDifferencing(ColumnAnomalyDifferencingSumCheckSpec sumAnomalyDifferencing) {
        this.setDirtyIf(!Objects.equals(this.sumAnomalyDifferencing, sumAnomalyDifferencing));
        this.sumAnomalyDifferencing = sumAnomalyDifferencing;
        propagateHierarchyIdToField(sumAnomalyDifferencing, "sum_anomaly_differencing");
    }

    /**
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnChangeMeanCheckSpec getMeanChange() {
        return meanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param meanChange Mean value change check.
     */
    public void setMeanChange(ColumnChangeMeanCheckSpec meanChange) {
        this.setDirtyIf(!Objects.equals(this.meanChange, meanChange));
        this.meanChange = meanChange;
        propagateHierarchyIdToField(meanChange, "mean_change");
    }

    /**
     * Returns the mean value change yesterday check.
     * @return Mean value change yesterday check.
     */
    public ColumnChangeMeanSinceYesterdayCheckSpec getMeanChangeYesterday() {
        return meanChangeYesterday;
    }

    /**
     * Sets a new mean value change yesterday check.
     * @param meanChangeYesterday Mean value change yesterday check.
     */
    public void setMeanChangeYesterday(ColumnChangeMeanSinceYesterdayCheckSpec meanChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.meanChangeYesterday, meanChangeYesterday));
        this.meanChangeYesterday = meanChangeYesterday;
        propagateHierarchyIdToField(meanChangeYesterday, "mean_change_yesterday");
    }

    /**
     * Returns the mean value change 7 days check.
     * @return Mean value change 7 days check.
     */
    public ColumnChangeMeanSince7DaysCheckSpec getMeanChange7Days() {
        return meanChange7Days;
    }

    /**
     * Sets a new mean value change 7 days check.
     * @param meanChange7Days Mean value change 7 days check.
     */
    public void setMeanChange7Days(ColumnChangeMeanSince7DaysCheckSpec meanChange7Days) {
        this.setDirtyIf(!Objects.equals(this.meanChange7Days, meanChange7Days));
        this.meanChange7Days = meanChange7Days;
        propagateHierarchyIdToField(meanChange7Days, "mean_change_7_days");
    }

    /**
     * Returns the mean value change 30 days check.
     * @return Mean value change 30 days check.
     */
    public ColumnChangeMeanSince30DaysCheckSpec getMeanChange30Days() {
        return meanChange30Days;
    }

    /**
     * Sets a new mean value change 30 days check.
     * @param meanChange30Days Mean value change 30 days check.
     */
    public void setMeanChange30Days(ColumnChangeMeanSince30DaysCheckSpec meanChange30Days) {
        this.setDirtyIf(!Objects.equals(this.meanChange30Days, meanChange30Days));
        this.meanChange30Days = meanChange30Days;
        propagateHierarchyIdToField(meanChange30Days, "mean_change_30_days");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnChangeMedianCheckSpec getMedianChange() {
        return medianChange;
    }

    /**
     * Sets a new median change check.
     * @param medianChange Median change check.
     */
    public void setMedianChange(ColumnChangeMedianCheckSpec medianChange) {
        this.setDirtyIf(!Objects.equals(this.medianChange, medianChange));
        this.medianChange = medianChange;
        propagateHierarchyIdToField(medianChange, "median_change");
    }

    /**
     * Returns the median change yesterday check.
     * @return Median change yesterday check.
     */
    public ColumnChangeMedianSinceYesterdayCheckSpec getMedianChangeYesterday() {
        return medianChangeYesterday;
    }

    /**
     * Sets a new median change yesterday check.
     * @param medianChangeYesterday Median change yesterday check.
     */
    public void setMedianChangeYesterday(ColumnChangeMedianSinceYesterdayCheckSpec medianChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.medianChangeYesterday, medianChangeYesterday));
        this.medianChangeYesterday = medianChangeYesterday;
        propagateHierarchyIdToField(medianChangeYesterday, "median_change_yesterday");
    }

    /**
     * Returns the median change 7 days check.
     * @return Median change 7 days check.
     */
    public ColumnChangeMedianSince7DaysCheckSpec getMedianChange7Days() {
        return medianChange7Days;
    }

    /**
     * Sets a new median change 7 days check.
     * @param medianChange7Days Median change 7 days check.
     */
    public void setMedianChange7Days(ColumnChangeMedianSince7DaysCheckSpec medianChange7Days) {
        this.setDirtyIf(!Objects.equals(this.medianChange7Days, medianChange7Days));
        this.medianChange7Days = medianChange7Days;
        propagateHierarchyIdToField(medianChange7Days, "median_change_7_days");
    }

    /**
     * Returns the median change 30 days check.
     * @return Median change 30 days check.
     */
    public ColumnChangeMedianSince30DaysCheckSpec getMedianChange30Days() {
        return medianChange30Days;
    }

    /**
     * Sets a new median change 30 days check.
     * @param medianChange30Days Median change 30 days check.
     */
    public void setMedianChange30Days(ColumnChangeMedianSince30DaysCheckSpec medianChange30Days) {
        this.setDirtyIf(!Objects.equals(this.medianChange30Days, medianChange30Days));
        this.medianChange30Days = medianChange30Days;
        propagateHierarchyIdToField(medianChange30Days, "median_change_30_days");
    }

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnChangeSumCheckSpec getSumChange() {
        return sumChange;
    }

    /**
     * Sets a new sum change check.
     * @param sumChange Sum change check.
     */
    public void setSumChange(ColumnChangeSumCheckSpec sumChange) {
        this.setDirtyIf(!Objects.equals(this.sumChange, sumChange));
        this.sumChange = sumChange;
        propagateHierarchyIdToField(sumChange, "sum_change");
    }

    /**
     * Returns the sum change yesterday check.
     * @return Sum change yesterday check.
     */
    public ColumnChangeSumSinceYesterdayCheckSpec getSumChangeYesterday() {
        return sumChangeYesterday;
    }

    /**
     * Sets a new sum change yesterday check.
     * @param sumChangeYesterday Sum change yesterday check.
     */
    public void setSumChangeYesterday(ColumnChangeSumSinceYesterdayCheckSpec sumChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.sumChangeYesterday, sumChangeYesterday));
        this.sumChangeYesterday = sumChangeYesterday;
        propagateHierarchyIdToField(sumChangeYesterday, "sum_change_yesterday");
    }

    /**
     * Returns the sum change 7 days check.
     * @return Sum change 7 days check.
     */
    public ColumnChangeSumSince7DaysCheckSpec getSumChange7Days() {
        return sumChange7Days;
    }

    /**
     * Sets a new sum change 7 days check.
     * @param sumChange7Days Sum change 7 days check.
     */
    public void setSumChange7Days(ColumnChangeSumSince7DaysCheckSpec sumChange7Days) {
        this.setDirtyIf(!Objects.equals(this.sumChange7Days, sumChange7Days));
        this.sumChange7Days = sumChange7Days;
        propagateHierarchyIdToField(sumChange7Days, "sum_change_7_days");
    }

    /**
     * Returns the sum change 30 days check.
     * @return Sum change 30 days check.
     */
    public ColumnChangeSumSince30DaysCheckSpec getSumChange30Days() {
        return sumChange30Days;
    }

    /**
     * Sets a new sum change 30 days check.
     * @param sumChange30Days Sum change 30 days check.
     */
    public void setSumChange30Days(ColumnChangeSumSince30DaysCheckSpec sumChange30Days) {
        this.setDirtyIf(!Objects.equals(this.sumChange30Days, sumChange30Days));
        this.sumChange30Days = sumChange30Days;
        propagateHierarchyIdToField(sumChange30Days, "sum_change_30_days");
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
    public ColumnAnomalyProfilingChecksSpec deepClone() {
        return (ColumnAnomalyProfilingChecksSpec)super.deepClone();
    }
}
