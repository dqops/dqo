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
package com.dqops.checks.column.monitoring.anomaly;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.anomaly.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ColumnAnomalyDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_mean_anomaly_stationary_30_days", o -> o.dailyMeanAnomalyStationary30Days);
            put("daily_mean_anomaly_stationary", o -> o.dailyMeanAnomalyStationary);
            
            put("daily_median_anomaly_stationary_30_days", o -> o.dailyMedianAnomalyStationary30Days);
            put("daily_median_anomaly_stationary", o -> o.dailyMedianAnomalyStationary);

            put("daily_sum_anomaly_differencing_30_days", o -> o.dailySumAnomalyDifferencing30Days);
            put("daily_sum_anomaly_differencing", o -> o.dailySumAnomalyDifferencing);

            put("daily_mean_change", o -> o.dailyMeanChange);
            put("daily_mean_change_yesterday", o -> o.dailyMeanChangeYesterday);
            put("daily_mean_change_7_days", o -> o.dailyMeanChange7Days);
            put("daily_mean_change_30_days", o -> o.dailyMeanChange30Days);

            put("daily_median_change", o -> o.dailyMedianChange);
            put("daily_median_change_yesterday", o -> o.dailyMedianChangeYesterday);
            put("daily_median_change_7_days", o -> o.dailyMedianChange7Days);
            put("daily_median_change_30_days", o -> o.dailyMedianChange30Days);

            put("daily_sum_change", o -> o.dailySumChange);
            put("daily_sum_change_yesterday", o -> o.dailySumChangeYesterday);
            put("daily_sum_change_7_days", o -> o.dailySumChange7Days);
            put("daily_sum_change_30_days", o -> o.dailySumChange30Days);
        }
    };

    @JsonProperty("daily_mean_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryMean30DaysCheckSpec dailyMeanAnomalyStationary30Days;

    @JsonProperty("daily_mean_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryMeanCheckSpec dailyMeanAnomalyStationary;

    @JsonProperty("daily_median_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryMedian30DaysCheckSpec dailyMedianAnomalyStationary30Days;

    @JsonProperty("daily_median_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryMedianCheckSpec dailyMedianAnomalyStationary;

    @JsonProperty("daily_sum_anomaly_differencing_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyDifferencingSum30DaysCheckSpec dailySumAnomalyDifferencing30Days;

    @JsonProperty("daily_sum_anomaly_differencing")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyDifferencingSumCheckSpec dailySumAnomalyDifferencing;

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout.")
    private ColumnChangeMeanCheckSpec dailyMeanChange;

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeMeanSinceYesterdayCheckSpec dailyMeanChangeYesterday;

    @JsonProperty("daily_mean_change_7_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeMeanSince7DaysCheckSpec dailyMeanChange7Days;

    @JsonProperty("daily_mean_change_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeMeanSince30DaysCheckSpec dailyMeanChange30Days;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout.")
    private ColumnChangeMedianCheckSpec dailyMedianChange;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeMedianSinceYesterdayCheckSpec dailyMedianChangeYesterday;

    @JsonProperty("daily_median_change_7_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeMedianSince7DaysCheckSpec dailyMedianChange7Days;

    @JsonProperty("daily_median_change_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeMedianSince30DaysCheckSpec dailyMedianChange30Days;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout.")
    private ColumnChangeSumCheckSpec dailySumChange;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeSumSinceYesterdayCheckSpec dailySumChangeYesterday;

    @JsonProperty("daily_sum_change_7_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeSumSince7DaysCheckSpec dailySumChange7Days;

    @JsonProperty("daily_sum_change_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeSumSince30DaysCheckSpec dailySumChange30Days;

    /**
     * Returns a mean value anomaly 30 days check specification.
     * @return Mean value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryMean30DaysCheckSpec getDailyMeanAnomalyStationary30Days() {
        return dailyMeanAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 30 days check.
     * @param dailyMeanAnomalyStationary30Days Mean value anomaly 30 days check specification.
     */
    public void setDailyMeanAnomalyStationary30Days(ColumnAnomalyStationaryMean30DaysCheckSpec dailyMeanAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanAnomalyStationary30Days, dailyMeanAnomalyStationary30Days));
        this.dailyMeanAnomalyStationary30Days = dailyMeanAnomalyStationary30Days;
        propagateHierarchyIdToField(dailyMeanAnomalyStationary30Days, "daily_mean_anomaly_stationary_30_days");
    }

    /**
     * Returns a mean value anomaly 90 days check specification.
     * @return Mean value anomaly 90 days check specification.
     */
    public ColumnAnomalyStationaryMeanCheckSpec getDailyMeanAnomalyStationary() {
        return dailyMeanAnomalyStationary;
    }

    /**
     * Sets a new specification of a mean value anomaly 90 days check.
     * @param dailyMeanAnomalyStationary Mean value anomaly 90 days check specification.
     */
    public void setDailyMeanAnomalyStationary(ColumnAnomalyStationaryMeanCheckSpec dailyMeanAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanAnomalyStationary, dailyMeanAnomalyStationary));
        this.dailyMeanAnomalyStationary = dailyMeanAnomalyStationary;
        propagateHierarchyIdToField(dailyMeanAnomalyStationary, "daily_mean_anomaly_stationary");
    }

    /**
     * Returns a median anomaly 30 days check specification.
     * @return Median anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryMedian30DaysCheckSpec getDailyMedianAnomalyStationary30Days() {
        return dailyMedianAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a median anomaly 30 days check.
     * @param dailyMedianAnomalyStationary30Days Median anomaly 30 days check specification.
     */
    public void setDailyMedianAnomalyStationary30Days(ColumnAnomalyStationaryMedian30DaysCheckSpec dailyMedianAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianAnomalyStationary30Days, dailyMedianAnomalyStationary30Days));
        this.dailyMedianAnomalyStationary30Days = dailyMedianAnomalyStationary30Days;
        propagateHierarchyIdToField(dailyMedianAnomalyStationary30Days, "daily_median_anomaly_stationary_30_days");
    }

    /**
     * Returns a median anomaly 90 days check specification.
     * @return Median anomaly 90 days check specification.
     */
    public ColumnAnomalyStationaryMedianCheckSpec getDailyMedianAnomalyStationary() {
        return dailyMedianAnomalyStationary;
    }

    /**
     * Sets a new specification of a median anomaly 90 days check.
     * @param dailyMedianAnomalyStationary Median anomaly 90 days check specification.
     */
    public void setDailyMedianAnomalyStationary(ColumnAnomalyStationaryMedianCheckSpec dailyMedianAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianAnomalyStationary, dailyMedianAnomalyStationary));
        this.dailyMedianAnomalyStationary = dailyMedianAnomalyStationary;
        propagateHierarchyIdToField(dailyMedianAnomalyStationary, "daily_median_anomaly_stationary");
    }

    /**
     * Returns a sum anomaly 30 days check specification.
     * @return Sum anomaly 30 days check specification.
     */
    public ColumnAnomalyDifferencingSum30DaysCheckSpec getDailySumAnomalyDifferencing30Days() {
        return dailySumAnomalyDifferencing30Days;
    }

    /**
     * Sets a new specification of a sum anomaly 30 days check.
     * @param dailySumAnomalyDifferencing30Days Sum anomaly 30 days check specification.
     */
    public void setDailySumAnomalyDifferencing30Days(ColumnAnomalyDifferencingSum30DaysCheckSpec dailySumAnomalyDifferencing30Days) {
        this.setDirtyIf(!Objects.equals(this.dailySumAnomalyDifferencing30Days, dailySumAnomalyDifferencing30Days));
        this.dailySumAnomalyDifferencing30Days = dailySumAnomalyDifferencing30Days;
        propagateHierarchyIdToField(dailySumAnomalyDifferencing30Days, "daily_sum_anomaly_differencing_30_days");
    }

    /**
     * Returns a sum anomaly 90 days check specification.
     * @return Sum anomaly 90 days check specification.
     */
    public ColumnAnomalyDifferencingSumCheckSpec getDailySumAnomalyDifferencing() {
        return dailySumAnomalyDifferencing;
    }

    /**
     * Sets a new specification of a sum anomaly 90 days check.
     * @param dailySumAnomalyDifferencing Sum anomaly 90 days check specification.
     */
    public void setDailySumAnomalyDifferencing(ColumnAnomalyDifferencingSumCheckSpec dailySumAnomalyDifferencing) {
        this.setDirtyIf(!Objects.equals(this.dailySumAnomalyDifferencing, dailySumAnomalyDifferencing));
        this.dailySumAnomalyDifferencing = dailySumAnomalyDifferencing;
        propagateHierarchyIdToField(dailySumAnomalyDifferencing, "daily_sum_anomaly_differencing");
    }

    /**
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnChangeMeanCheckSpec getDailyMeanChange() {
        return dailyMeanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param dailyMeanChange Mean value change check.
     */
    public void setDailyMeanChange(ColumnChangeMeanCheckSpec dailyMeanChange) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanChange, dailyMeanChange));
        this.dailyMeanChange = dailyMeanChange;
        propagateHierarchyIdToField(dailyMeanChange, "daily_mean_change");
    }

    /**
     * Returns the mean value change yesterday check.
     * @return Mean value change yesterday check.
     */
    public ColumnChangeMeanSinceYesterdayCheckSpec getDailyMeanChangeYesterday() {
        return dailyMeanChangeYesterday;
    }

    /**
     * Sets a new mean value change yesterday check.
     * @param dailyMeanChangeYesterday Mean value change yesterday check.
     */
    public void setDailyMeanChangeYesterday(ColumnChangeMeanSinceYesterdayCheckSpec dailyMeanChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanChangeYesterday, dailyMeanChangeYesterday));
        this.dailyMeanChangeYesterday = dailyMeanChangeYesterday;
        propagateHierarchyIdToField(dailyMeanChangeYesterday, "daily_mean_change_yesterday");
    }

    /**
     * Returns the mean value change 7 days check.
     * @return Mean value change 7 days check.
     */
    public ColumnChangeMeanSince7DaysCheckSpec getDailyMeanChange7Days() {
        return dailyMeanChange7Days;
    }

    /**
     * Sets a new mean value change 7 days check.
     * @param dailyMeanChange7Days Mean value change 7 days check.
     */
    public void setDailyMeanChange7Days(ColumnChangeMeanSince7DaysCheckSpec dailyMeanChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanChange7Days, dailyMeanChange7Days));
        this.dailyMeanChange7Days = dailyMeanChange7Days;
        propagateHierarchyIdToField(dailyMeanChange7Days, "daily_mean_change_7_days");
    }

    /**
     * Returns the mean value change 30 days check.
     * @return Mean value change 30 days check.
     */
    public ColumnChangeMeanSince30DaysCheckSpec getDailyMeanChange30Days() {
        return dailyMeanChange30Days;
    }

    /**
     * Sets a new mean value change 30 days check.
     * @param dailyMeanChange30Days Mean value change 30 days check.
     */
    public void setDailyMeanChange30Days(ColumnChangeMeanSince30DaysCheckSpec dailyMeanChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanChange30Days, dailyMeanChange30Days));
        this.dailyMeanChange30Days = dailyMeanChange30Days;
        propagateHierarchyIdToField(dailyMeanChange30Days, "daily_mean_change_30_days");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnChangeMedianCheckSpec getDailyMedianChange() {
        return dailyMedianChange;
    }

    /**
     * Sets a new median change check.
     * @param dailyMedianChange Median change check.
     */
    public void setDailyMedianChange(ColumnChangeMedianCheckSpec dailyMedianChange) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianChange, dailyMedianChange));
        this.dailyMedianChange = dailyMedianChange;
        propagateHierarchyIdToField(dailyMedianChange, "daily_median_change");
    }

    /**
     * Returns the median change yesterday check.
     * @return Median change yesterday check.
     */
    public ColumnChangeMedianSinceYesterdayCheckSpec getDailyMedianChangeYesterday() {
        return dailyMedianChangeYesterday;
    }

    /**
     * Sets a new median change yesterday check.
     * @param dailyMedianChangeYesterday Median change yesterday check.
     */
    public void setDailyMedianChangeYesterday(ColumnChangeMedianSinceYesterdayCheckSpec dailyMedianChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianChangeYesterday, dailyMedianChangeYesterday));
        this.dailyMedianChangeYesterday = dailyMedianChangeYesterday;
        propagateHierarchyIdToField(dailyMedianChangeYesterday, "daily_median_change_yesterday");
    }

    /**
     * Returns the median change 7 days check.
     * @return Median change 7 days check.
     */
    public ColumnChangeMedianSince7DaysCheckSpec getDailyMedianChange7Days() {
        return dailyMedianChange7Days;
    }

    /**
     * Sets a new median change 7 days check.
     * @param dailyMedianChange7Days Median change 7 days check.
     */
    public void setDailyMedianChange7Days(ColumnChangeMedianSince7DaysCheckSpec dailyMedianChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianChange7Days, dailyMedianChange7Days));
        this.dailyMedianChange7Days = dailyMedianChange7Days;
        propagateHierarchyIdToField(dailyMedianChange7Days, "daily_median_change_7_days");
    }

    /**
     * Returns the median change 30 days check.
     * @return Median change 30 days check.
     */
    public ColumnChangeMedianSince30DaysCheckSpec getDailyMedianChange30Days() {
        return dailyMedianChange30Days;
    }

    /**
     * Sets a new median change 30 days check.
     * @param dailyMedianChange30Days Median change 30 days check.
     */
    public void setDailyMedianChange30Days(ColumnChangeMedianSince30DaysCheckSpec dailyMedianChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianChange30Days, dailyMedianChange30Days));
        this.dailyMedianChange30Days = dailyMedianChange30Days;
        propagateHierarchyIdToField(dailyMedianChange30Days, "daily_median_change_30_days");
    }

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnChangeSumCheckSpec getDailySumChange() {
        return dailySumChange;
    }

    /**
     * Sets a new sum change check.
     * @param dailySumChange Sum change check.
     */
    public void setDailySumChange(ColumnChangeSumCheckSpec dailySumChange) {
        this.setDirtyIf(!Objects.equals(this.dailySumChange, dailySumChange));
        this.dailySumChange = dailySumChange;
        propagateHierarchyIdToField(dailySumChange, "daily_sum_change");
    }

    /**
     * Returns the sum change yesterday check.
     * @return Sum change yesterday check.
     */
    public ColumnChangeSumSinceYesterdayCheckSpec getDailySumChangeYesterday() {
        return dailySumChangeYesterday;
    }

    /**
     * Sets a new sum change yesterday check.
     * @param dailySumChangeYesterday Sum change yesterday check.
     */
    public void setDailySumChangeYesterday(ColumnChangeSumSinceYesterdayCheckSpec dailySumChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailySumChangeYesterday, dailySumChangeYesterday));
        this.dailySumChangeYesterday = dailySumChangeYesterday;
        propagateHierarchyIdToField(dailySumChangeYesterday, "daily_sum_change_yesterday");
    }

    /**
     * Returns the sum change 7 days check.
     * @return Sum change 7 days check.
     */
    public ColumnChangeSumSince7DaysCheckSpec getDailySumChange7Days() {
        return dailySumChange7Days;
    }

    /**
     * Sets a new sum change 7 days check.
     * @param dailySumChange7Days Sum change 7 days check.
     */
    public void setDailySumChange7Days(ColumnChangeSumSince7DaysCheckSpec dailySumChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailySumChange7Days, dailySumChange7Days));
        this.dailySumChange7Days = dailySumChange7Days;
        propagateHierarchyIdToField(dailySumChange7Days, "daily_sum_change_7_days");
    }

    /**
     * Returns the sum change 30 days check.
     * @return Sum change 30 days check.
     */
    public ColumnChangeSumSince30DaysCheckSpec getDailySumChange30Days() {
        return dailySumChange30Days;
    }

    /**
     * Sets a new sum change 30 days check.
     * @param dailySumChange30Days Sum change 30 days check.
     */
    public void setDailySumChange30Days(ColumnChangeSumSince30DaysCheckSpec dailySumChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailySumChange30Days, dailySumChange30Days));
        this.dailySumChange30Days = dailySumChange30Days;
        propagateHierarchyIdToField(dailySumChange30Days, "daily_sum_change_30_days");
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
    public ColumnAnomalyDailyMonitoringChecksSpec deepClone() {
        return (ColumnAnomalyDailyMonitoringChecksSpec)super.deepClone();
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
        return CheckType.monitoring;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return CheckTimeScale.daily;
    }
}
