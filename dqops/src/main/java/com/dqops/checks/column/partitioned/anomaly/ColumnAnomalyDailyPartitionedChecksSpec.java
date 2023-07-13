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
package com.dqops.checks.column.partitioned.anomaly;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.anomaly.*;
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
public class ColumnAnomalyDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_mean_anomaly_stationary_30_days", o -> o.dailyPartitionMeanAnomalyStationary30Days);
            put("daily_partition_mean_anomaly_stationary", o -> o.dailyPartitionMeanAnomalyStationary);

            put("daily_partition_median_anomaly_stationary_30_days", o -> o.dailyPartitionMedianAnomalyStationary30Days);
            put("daily_partition_median_anomaly_stationary", o -> o.dailyPartitionMedianAnomalyStationary);

            put("daily_partition_sum_anomaly_stationary_30_days", o -> o.dailyPartitionSumAnomalyStationary30Days);
            put("daily_partition_sum_anomaly_stationary", o -> o.dailyPartitionSumAnomalyStationary);

            put("daily_partition_mean_change", o -> o.dailyPartitionMeanChange);
            put("daily_partition_mean_change_yesterday", o -> o.dailyPartitionMeanChangeYesterday);
            put("daily_partition_mean_change_7_days", o -> o.dailyPartitionMeanChange7Days);
            put("daily_partition_mean_change_30_days", o -> o.dailyPartitionMeanChange30Days);

            put("daily_partition_median_change", o -> o.dailyPartitionMedianChange);
            put("daily_partition_median_change_yesterday", o -> o.dailyPartitionMedianChangeYesterday);
            put("daily_partition_median_change_7_days", o -> o.dailyPartitionMedianChange7Days);
            put("daily_partition_median_change_30_days", o -> o.dailyPartitionMedianChange30Days);

            put("daily_partition_sum_change", o -> o.dailyPartitionSumChange);
            put("daily_partition_sum_change_yesterday", o -> o.dailyPartitionSumChangeYesterday);
            put("daily_partition_sum_change_7_days", o -> o.dailyPartitionSumChange7Days);
            put("daily_partition_sum_change_30_days", o -> o.dailyPartitionSumChange30Days);
        }
    };

    @JsonProperty("daily_partition_mean_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryMean30DaysCheckSpec dailyPartitionMeanAnomalyStationary30Days;

    @JsonProperty("daily_partition_mean_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryMeanCheckSpec dailyPartitionMeanAnomalyStationary;

    @JsonProperty("daily_partition_median_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the median in a column is within a percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryMedian30DaysCheckSpec dailyPartitionMedianAnomalyStationary30Days;

    @JsonProperty("daily_partition_median_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the median in a column is within a percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryMedianCheckSpec dailyPartitionMedianAnomalyStationary;

    @JsonProperty("daily_partition_sum_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryPartitionSum30DaysCheckSpec dailyPartitionSumAnomalyStationary30Days;

    @JsonProperty("daily_partition_sum_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryPartitionSumCheckSpec dailyPartitionSumAnomalyStationary;

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout.")
    private ColumnChangeMeanCheckSpec dailyPartitionMeanChange;

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeMeanSinceYesterdayCheckSpec dailyPartitionMeanChangeYesterday;

    @JsonProperty("daily_partition_mean_change_7_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeMeanSince7DaysCheckSpec dailyPartitionMeanChange7Days;

    @JsonProperty("daily_partition_mean_change_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeMeanSince30DaysCheckSpec dailyPartitionMeanChange30Days;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout.")
    private ColumnChangeMedianCheckSpec dailyPartitionMedianChange;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeMedianSinceYesterdayCheckSpec dailyPartitionMedianChangeYesterday;

    @JsonProperty("daily_partition_median_change_7_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeMedianSince7DaysCheckSpec dailyPartitionMedianChange7Days;

    @JsonProperty("daily_partition_median_change_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeMedianSince30DaysCheckSpec dailyPartitionMedianChange30Days;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout.")
    private ColumnChangeSumCheckSpec dailyPartitionSumChange;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeSumSinceYesterdayCheckSpec dailyPartitionSumChangeYesterday;

    @JsonProperty("daily_partition_sum_change_7_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeSumSince7DaysCheckSpec dailyPartitionSumChange7Days;

    @JsonProperty("daily_partition_sum_change_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeSumSince30DaysCheckSpec dailyPartitionSumChange30Days;

    /**
     * Returns a mean value anomaly 30 days check specification.
     * @return Mean value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryMean30DaysCheckSpec getDailyPartitionMeanAnomalyStationary30Days() {
        return dailyPartitionMeanAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 30 days check.
     * @param dailyPartitionMeanAnomalyStationary30Days Mean value anomaly 30 days check specification.
     */
    public void setDailyPartitionMeanAnomalyStationary30Days(ColumnAnomalyStationaryMean30DaysCheckSpec dailyPartitionMeanAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanAnomalyStationary30Days, dailyPartitionMeanAnomalyStationary30Days));
        this.dailyPartitionMeanAnomalyStationary30Days = dailyPartitionMeanAnomalyStationary30Days;
        propagateHierarchyIdToField(dailyPartitionMeanAnomalyStationary30Days, "daily_partition_mean_anomaly_stationary_30_days");
    }

    /**
     * Returns a mean value anomaly 60 days check specification.
     * @return Mean value anomaly 60 days check specification.
     */
    public ColumnAnomalyStationaryMeanCheckSpec getDailyPartitionMeanAnomalyStationary() {
        return dailyPartitionMeanAnomalyStationary;
    }

    /**
     * Sets a new specification of a mean value anomaly 60 days check.
     * @param dailyPartitionMeanAnomalyStationary Mean value anomaly 60 days check specification.
     */
    public void setDailyPartitionMeanAnomalyStationary(ColumnAnomalyStationaryMeanCheckSpec dailyPartitionMeanAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanAnomalyStationary, dailyPartitionMeanAnomalyStationary));
        this.dailyPartitionMeanAnomalyStationary = dailyPartitionMeanAnomalyStationary;
        propagateHierarchyIdToField(dailyPartitionMeanAnomalyStationary, "daily_partition_mean_anomaly_stationary");
    }

    /**
     * Returns a median anomaly 30 days check specification.
     * @return Median anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryMedian30DaysCheckSpec getDailyPartitionMedianAnomalyStationary30Days() {
        return dailyPartitionMedianAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a median anomaly 30 days check.
     * @param dailyPartitionMedianAnomalyStationary30Days Median anomaly 30 days check specification.
     */
    public void setDailyPartitionMedianAnomalyStationary30Days(ColumnAnomalyStationaryMedian30DaysCheckSpec dailyPartitionMedianAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianAnomalyStationary30Days, dailyPartitionMedianAnomalyStationary30Days));
        this.dailyPartitionMedianAnomalyStationary30Days = dailyPartitionMedianAnomalyStationary30Days;
        propagateHierarchyIdToField(dailyPartitionMedianAnomalyStationary30Days, "daily_partition_median_anomaly_stationary_30_days");
    }

    /**
     * Returns a median anomaly 60 days check specification.
     * @return Median anomaly 60 days check specification.
     */
    public ColumnAnomalyStationaryMedianCheckSpec getDailyPartitionMedianAnomalyStationary() {
        return dailyPartitionMedianAnomalyStationary;
    }

    /**
     * Sets a new specification of a median anomaly 60 days check.
     * @param dailyPartitionMedianAnomalyStationary Median anomaly 60 days check specification.
     */
    public void setDailyPartitionMedianAnomalyStationary(ColumnAnomalyStationaryMedianCheckSpec dailyPartitionMedianAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianAnomalyStationary, dailyPartitionMedianAnomalyStationary));
        this.dailyPartitionMedianAnomalyStationary = dailyPartitionMedianAnomalyStationary;
        propagateHierarchyIdToField(dailyPartitionMedianAnomalyStationary, "daily_partition_median_anomaly_stationary");
    }

    /**
     * Returns a sum anomaly 30 days check specification.
     * @return Sum anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryPartitionSum30DaysCheckSpec getDailyPartitionSumAnomalyStationary30Days() {
        return dailyPartitionSumAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a sum anomaly 30 days check.
     * @param dailyPartitionSumAnomalyStationary30Days Sum anomaly 30 days check specification.
     */
    public void setDailyPartitionSumAnomalyStationary30Days(ColumnAnomalyStationaryPartitionSum30DaysCheckSpec dailyPartitionSumAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumAnomalyStationary30Days, dailyPartitionSumAnomalyStationary30Days));
        this.dailyPartitionSumAnomalyStationary30Days = dailyPartitionSumAnomalyStationary30Days;
        propagateHierarchyIdToField(dailyPartitionSumAnomalyStationary30Days, "daily_partition_sum_anomaly_stationary_30_days");
    }

    /**
     * Returns a sum anomaly 60 days check specification.
     * @return Sum anomaly 60 days check specification.
     */
    public ColumnAnomalyStationaryPartitionSumCheckSpec getDailyPartitionSumAnomalyStationary() {
        return dailyPartitionSumAnomalyStationary;
    }

    /**
     * Sets a new specification of a sum anomaly 60 days check.
     * @param dailyPartitionSumAnomalyStationary Sum anomaly 60 days check specification.
     */
    public void setDailyPartitionSumAnomalyStationary(ColumnAnomalyStationaryPartitionSumCheckSpec dailyPartitionSumAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumAnomalyStationary, dailyPartitionSumAnomalyStationary));
        this.dailyPartitionSumAnomalyStationary = dailyPartitionSumAnomalyStationary;
        propagateHierarchyIdToField(dailyPartitionSumAnomalyStationary, "daily_partition_sum_anomaly_stationary");
    }

    /**
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnChangeMeanCheckSpec getDailyPartitionMeanChange() {
        return dailyPartitionMeanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param dailyPartitionMeanChange Mean value change check.
     */
    public void setDailyPartitionMeanChange(ColumnChangeMeanCheckSpec dailyPartitionMeanChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanChange, dailyPartitionMeanChange));
        this.dailyPartitionMeanChange = dailyPartitionMeanChange;
        propagateHierarchyIdToField(dailyPartitionMeanChange, "daily_partition_mean_change");
    }

    /**
     * Returns the mean value change yesterday check.
     * @return Mean value change yesterday check.
     */
    public ColumnChangeMeanSinceYesterdayCheckSpec getDailyPartitionMeanChangeYesterday() {
        return dailyPartitionMeanChangeYesterday;
    }

    /**
     * Sets a new mean value change yesterday check.
     * @param dailyPartitionMeanChangeYesterday Mean value change yesterday check.
     */
    public void setDailyPartitionMeanChangeYesterday(ColumnChangeMeanSinceYesterdayCheckSpec dailyPartitionMeanChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanChangeYesterday, dailyPartitionMeanChangeYesterday));
        this.dailyPartitionMeanChangeYesterday = dailyPartitionMeanChangeYesterday;
        propagateHierarchyIdToField(dailyPartitionMeanChangeYesterday, "daily_partition_mean_change_yesterday");
    }

    /**
     * Returns the mean value change 7 days check.
     * @return Mean value change 7 days check.
     */
    public ColumnChangeMeanSince7DaysCheckSpec getDailyPartitionMeanChange7Days() {
        return dailyPartitionMeanChange7Days;
    }

    /**
     * Sets a new mean value change 7 days check.
     * @param dailyPartitionMeanChange7Days Mean value change 7 days check.
     */
    public void setDailyPartitionMeanChange7Days(ColumnChangeMeanSince7DaysCheckSpec dailyPartitionMeanChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanChange7Days, dailyPartitionMeanChange7Days));
        this.dailyPartitionMeanChange7Days = dailyPartitionMeanChange7Days;
        propagateHierarchyIdToField(dailyPartitionMeanChange7Days, "daily_partition_mean_change_7_days");
    }

    /**
     * Returns the mean value change 30 days check.
     * @return Mean value change 30 days check.
     */
    public ColumnChangeMeanSince30DaysCheckSpec getDailyPartitionMeanChange30Days() {
        return dailyPartitionMeanChange30Days;
    }

    /**
     * Sets a new mean value change 30 days check.
     * @param dailyPartitionMeanChange30Days Mean value change 30 days check.
     */
    public void setDailyPartitionMeanChange30Days(ColumnChangeMeanSince30DaysCheckSpec dailyPartitionMeanChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanChange30Days, dailyPartitionMeanChange30Days));
        this.dailyPartitionMeanChange30Days = dailyPartitionMeanChange30Days;
        propagateHierarchyIdToField(dailyPartitionMeanChange30Days, "daily_partition_mean_change_30_days");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnChangeMedianCheckSpec getDailyPartitionMedianChange() {
        return dailyPartitionMedianChange;
    }

    /**
     * Sets a new median change check.
     * @param dailyPartitionMedianChange Median change check.
     */
    public void setDailyPartitionMedianChange(ColumnChangeMedianCheckSpec dailyPartitionMedianChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianChange, dailyPartitionMedianChange));
        this.dailyPartitionMedianChange = dailyPartitionMedianChange;
        propagateHierarchyIdToField(dailyPartitionMedianChange, "daily_partition_median_change");
    }

    /**
     * Returns the median change yesterday check.
     * @return Median change yesterday check.
     */
    public ColumnChangeMedianSinceYesterdayCheckSpec getDailyPartitionMedianChangeYesterday() {
        return dailyPartitionMedianChangeYesterday;
    }

    /**
     * Sets a new median change yesterday check.
     * @param dailyPartitionMedianChangeYesterday Median change yesterday check.
     */
    public void setDailyPartitionMedianChangeYesterday(ColumnChangeMedianSinceYesterdayCheckSpec dailyPartitionMedianChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianChangeYesterday, dailyPartitionMedianChangeYesterday));
        this.dailyPartitionMedianChangeYesterday = dailyPartitionMedianChangeYesterday;
        propagateHierarchyIdToField(dailyPartitionMedianChangeYesterday, "daily_partition_median_change_yesterday");
    }

    /**
     * Returns the median change 7 days check.
     * @return Median change 7 days check.
     */
    public ColumnChangeMedianSince7DaysCheckSpec getDailyPartitionMedianChange7Days() {
        return dailyPartitionMedianChange7Days;
    }

    /**
     * Sets a new median change 7 days check.
     * @param dailyPartitionMedianChange7Days Median change 7 days check.
     */
    public void setDailyPartitionMedianChange7Days(ColumnChangeMedianSince7DaysCheckSpec dailyPartitionMedianChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianChange7Days, dailyPartitionMedianChange7Days));
        this.dailyPartitionMedianChange7Days = dailyPartitionMedianChange7Days;
        propagateHierarchyIdToField(dailyPartitionMedianChange7Days, "daily_partition_median_change_7_days");
    }

    /**
     * Returns the median change 30 days check.
     * @return Median change 30 days check.
     */
    public ColumnChangeMedianSince30DaysCheckSpec getDailyPartitionMedianChange30Days() {
        return dailyPartitionMedianChange30Days;
    }

    /**
     * Sets a new median change 30 days check.
     * @param dailyPartitionMedianChange30Days Median change 30 days check.
     */
    public void setDailyPartitionMedianChange30Days(ColumnChangeMedianSince30DaysCheckSpec dailyPartitionMedianChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianChange30Days, dailyPartitionMedianChange30Days));
        this.dailyPartitionMedianChange30Days = dailyPartitionMedianChange30Days;
        propagateHierarchyIdToField(dailyPartitionMedianChange30Days, "daily_partition_median_change_30_days");
    }

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnChangeSumCheckSpec getDailyPartitionSumChange() {
        return dailyPartitionSumChange;
    }

    /**
     * Sets a new sum change check.
     * @param dailyPartitionSumChange Sum change check.
     */
    public void setDailyPartitionSumChange(ColumnChangeSumCheckSpec dailyPartitionSumChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumChange, dailyPartitionSumChange));
        this.dailyPartitionSumChange = dailyPartitionSumChange;
        propagateHierarchyIdToField(dailyPartitionSumChange, "daily_partition_sum_change");
    }

    /**
     * Returns the sum change yesterday check.
     * @return Sum change yesterday check.
     */
    public ColumnChangeSumSinceYesterdayCheckSpec getDailyPartitionSumChangeYesterday() {
        return dailyPartitionSumChangeYesterday;
    }

    /**
     * Sets a new sum change yesterday check.
     * @param dailyPartitionSumChangeYesterday Sum change yesterday check.
     */
    public void setDailyPartitionSumChangeYesterday(ColumnChangeSumSinceYesterdayCheckSpec dailyPartitionSumChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumChangeYesterday, dailyPartitionSumChangeYesterday));
        this.dailyPartitionSumChangeYesterday = dailyPartitionSumChangeYesterday;
        propagateHierarchyIdToField(dailyPartitionSumChangeYesterday, "daily_partition_sum_change_yesterday");
    }

    /**
     * Returns the sum change 7 days check.
     * @return Sum change 7 days check.
     */
    public ColumnChangeSumSince7DaysCheckSpec getDailyPartitionSumChange7Days() {
        return dailyPartitionSumChange7Days;
    }

    /**
     * Sets a new sum change 7 days check.
     * @param dailyPartitionSumChange7Days Sum change 7 days check.
     */
    public void setDailyPartitionSumChange7Days(ColumnChangeSumSince7DaysCheckSpec dailyPartitionSumChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumChange7Days, dailyPartitionSumChange7Days));
        this.dailyPartitionSumChange7Days = dailyPartitionSumChange7Days;
        propagateHierarchyIdToField(dailyPartitionSumChange7Days, "daily_partition_sum_change_7_days");
    }

    /**
     * Returns the sum change 30 days check.
     * @return Sum change 30 days check.
     */
    public ColumnChangeSumSince30DaysCheckSpec getDailyPartitionSumChange30Days() {
        return dailyPartitionSumChange30Days;
    }

    /**
     * Sets a new sum change 30 days check.
     * @param dailyPartitionSumChange30Days Sum change 30 days check.
     */
    public void setDailyPartitionSumChange30Days(ColumnChangeSumSince30DaysCheckSpec dailyPartitionSumChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumChange30Days, dailyPartitionSumChange30Days));
        this.dailyPartitionSumChange30Days = dailyPartitionSumChange30Days;
        propagateHierarchyIdToField(dailyPartitionSumChange30Days, "daily_partition_sum_change_30_days");
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
