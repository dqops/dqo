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
            put("daily_mean_anomaly_stationary", o -> o.dailyMeanAnomalyStationary);
            put("daily_mean_anomaly_stationary_30_days", o -> o.dailyMeanAnomalyStationary30Days);

            put("daily_median_anomaly_stationary", o -> o.dailyMedianAnomalyStationary);
            put("daily_median_anomaly_stationary_30_days", o -> o.dailyMedianAnomalyStationary30Days);

            put("daily_sum_anomaly_differencing", o -> o.dailySumAnomalyDifferencing);
            put("daily_sum_anomaly_differencing_30_days", o -> o.dailySumAnomalyDifferencing30Days);

            put("daily_mean_change", o -> o.dailyMeanChange);
            put("daily_mean_change_1_day", o -> o.dailyMeanChange1Day);
            put("daily_mean_change_7_days", o -> o.dailyMeanChange7Days);
            put("daily_mean_change_30_days", o -> o.dailyMeanChange30Days);

            put("daily_median_change", o -> o.dailyMedianChange);
            put("daily_median_change_1_day", o -> o.dailyMedianChange1Day);
            put("daily_median_change_7_days", o -> o.dailyMedianChange7Days);
            put("daily_median_change_30_days", o -> o.dailyMedianChange30Days);

            put("daily_sum_change", o -> o.dailySumChange);
            put("daily_sum_change_1_day", o -> o.dailySumChange1Day);
            put("daily_sum_change_7_days", o -> o.dailySumChange7Days);
            put("daily_sum_change_30_days", o -> o.dailySumChange30Days);
        }
    };

    @JsonProperty("daily_mean_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryMeanCheckSpec dailyMeanAnomalyStationary;

    @JsonProperty("daily_mean_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryMean30DaysCheckSpec dailyMeanAnomalyStationary30Days;

    @JsonProperty("daily_median_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryMedianCheckSpec dailyMedianAnomalyStationary;

    @JsonProperty("daily_median_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryMedian30DaysCheckSpec dailyMedianAnomalyStationary30Days;

    @JsonProperty("daily_sum_anomaly_differencing")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyDifferencingSumCheckSpec dailySumAnomalyDifferencing;

    @JsonProperty("daily_sum_anomaly_differencing_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyDifferencingSum30DaysCheckSpec dailySumAnomalyDifferencing30Days;

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout.")
    private ColumnMeanChangeCheckSpec dailyMeanChange;

    @JsonProperty("daily_mean_change_1_day")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnMeanChange1DayCheckSpec dailyMeanChange1Day;

    @JsonProperty("daily_mean_change_7_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last week.")
    private ColumnMeanChange7DaysCheckSpec dailyMeanChange7Days;

    @JsonProperty("daily_mean_change_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last month.")
    private ColumnMeanChange30DaysCheckSpec dailyMeanChange30Days;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout.")
    private ColumnMedianChangeCheckSpec dailyMedianChange;

    @JsonProperty("daily_median_change_1_day")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnMedianChange1DayCheckSpec dailyMedianChange1Day;

    @JsonProperty("daily_median_change_7_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last week.")
    private ColumnMedianChange7DaysCheckSpec dailyMedianChange7Days;

    @JsonProperty("daily_median_change_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last month.")
    private ColumnMedianChange30DaysCheckSpec dailyMedianChange30Days;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout.")
    private ColumnSumChangeCheckSpec dailySumChange;

    @JsonProperty("daily_sum_change_1_day")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnSumChange1DayCheckSpec dailySumChange1Day;

    @JsonProperty("daily_sum_change_7_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last week.")
    private ColumnSumChange7DaysCheckSpec dailySumChange7Days;

    @JsonProperty("daily_sum_change_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last month.")
    private ColumnSumChange30DaysCheckSpec dailySumChange30Days;


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
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnMeanChangeCheckSpec getDailyMeanChange() {
        return dailyMeanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param dailyMeanChange Mean value change check.
     */
    public void setDailyMeanChange(ColumnMeanChangeCheckSpec dailyMeanChange) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanChange, dailyMeanChange));
        this.dailyMeanChange = dailyMeanChange;
        propagateHierarchyIdToField(dailyMeanChange, "daily_mean_change");
    }

    /**
     * Returns the mean value change yesterday check.
     * @return Mean value change yesterday check.
     */
    public ColumnMeanChange1DayCheckSpec getDailyMeanChange1Day() {
        return dailyMeanChange1Day;
    }

    /**
     * Sets a new mean value change yesterday check.
     * @param dailyMeanChange1Day Mean value change yesterday check.
     */
    public void setDailyMeanChange1Day(ColumnMeanChange1DayCheckSpec dailyMeanChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanChange1Day, dailyMeanChange1Day));
        this.dailyMeanChange1Day = dailyMeanChange1Day;
        propagateHierarchyIdToField(dailyMeanChange1Day, "daily_mean_change_1_day");
    }

    /**
     * Returns the mean value change 7 days check.
     * @return Mean value change 7 days check.
     */
    public ColumnMeanChange7DaysCheckSpec getDailyMeanChange7Days() {
        return dailyMeanChange7Days;
    }

    /**
     * Sets a new mean value change 7 days check.
     * @param dailyMeanChange7Days Mean value change 7 days check.
     */
    public void setDailyMeanChange7Days(ColumnMeanChange7DaysCheckSpec dailyMeanChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanChange7Days, dailyMeanChange7Days));
        this.dailyMeanChange7Days = dailyMeanChange7Days;
        propagateHierarchyIdToField(dailyMeanChange7Days, "daily_mean_change_7_days");
    }

    /**
     * Returns the mean value change 30 days check.
     * @return Mean value change 30 days check.
     */
    public ColumnMeanChange30DaysCheckSpec getDailyMeanChange30Days() {
        return dailyMeanChange30Days;
    }

    /**
     * Sets a new mean value change 30 days check.
     * @param dailyMeanChange30Days Mean value change 30 days check.
     */
    public void setDailyMeanChange30Days(ColumnMeanChange30DaysCheckSpec dailyMeanChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanChange30Days, dailyMeanChange30Days));
        this.dailyMeanChange30Days = dailyMeanChange30Days;
        propagateHierarchyIdToField(dailyMeanChange30Days, "daily_mean_change_30_days");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnMedianChangeCheckSpec getDailyMedianChange() {
        return dailyMedianChange;
    }

    /**
     * Sets a new median change check.
     * @param dailyMedianChange Median change check.
     */
    public void setDailyMedianChange(ColumnMedianChangeCheckSpec dailyMedianChange) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianChange, dailyMedianChange));
        this.dailyMedianChange = dailyMedianChange;
        propagateHierarchyIdToField(dailyMedianChange, "daily_median_change");
    }

    /**
     * Returns the median change yesterday check.
     * @return Median change yesterday check.
     */
    public ColumnMedianChange1DayCheckSpec getDailyMedianChange1Day() {
        return dailyMedianChange1Day;
    }

    /**
     * Sets a new median change yesterday check.
     * @param dailyMedianChange1Day Median change yesterday check.
     */
    public void setDailyMedianChange1Day(ColumnMedianChange1DayCheckSpec dailyMedianChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianChange1Day, dailyMedianChange1Day));
        this.dailyMedianChange1Day = dailyMedianChange1Day;
        propagateHierarchyIdToField(dailyMedianChange1Day, "daily_median_change_1_day");
    }

    /**
     * Returns the median change 7 days check.
     * @return Median change 7 days check.
     */
    public ColumnMedianChange7DaysCheckSpec getDailyMedianChange7Days() {
        return dailyMedianChange7Days;
    }

    /**
     * Sets a new median change 7 days check.
     * @param dailyMedianChange7Days Median change 7 days check.
     */
    public void setDailyMedianChange7Days(ColumnMedianChange7DaysCheckSpec dailyMedianChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianChange7Days, dailyMedianChange7Days));
        this.dailyMedianChange7Days = dailyMedianChange7Days;
        propagateHierarchyIdToField(dailyMedianChange7Days, "daily_median_change_7_days");
    }

    /**
     * Returns the median change 30 days check.
     * @return Median change 30 days check.
     */
    public ColumnMedianChange30DaysCheckSpec getDailyMedianChange30Days() {
        return dailyMedianChange30Days;
    }

    /**
     * Sets a new median change 30 days check.
     * @param dailyMedianChange30Days Median change 30 days check.
     */
    public void setDailyMedianChange30Days(ColumnMedianChange30DaysCheckSpec dailyMedianChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianChange30Days, dailyMedianChange30Days));
        this.dailyMedianChange30Days = dailyMedianChange30Days;
        propagateHierarchyIdToField(dailyMedianChange30Days, "daily_median_change_30_days");
    }

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnSumChangeCheckSpec getDailySumChange() {
        return dailySumChange;
    }

    /**
     * Sets a new sum change check.
     * @param dailySumChange Sum change check.
     */
    public void setDailySumChange(ColumnSumChangeCheckSpec dailySumChange) {
        this.setDirtyIf(!Objects.equals(this.dailySumChange, dailySumChange));
        this.dailySumChange = dailySumChange;
        propagateHierarchyIdToField(dailySumChange, "daily_sum_change");
    }

    /**
     * Returns the sum change yesterday check.
     * @return Sum change yesterday check.
     */
    public ColumnSumChange1DayCheckSpec getDailySumChange1Day() {
        return dailySumChange1Day;
    }

    /**
     * Sets a new sum change yesterday check.
     * @param dailySumChange1Day Sum change yesterday check.
     */
    public void setDailySumChange1Day(ColumnSumChange1DayCheckSpec dailySumChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailySumChange1Day, dailySumChange1Day));
        this.dailySumChange1Day = dailySumChange1Day;
        propagateHierarchyIdToField(dailySumChange1Day, "daily_sum_change_1_day");
    }

    /**
     * Returns the sum change 7 days check.
     * @return Sum change 7 days check.
     */
    public ColumnSumChange7DaysCheckSpec getDailySumChange7Days() {
        return dailySumChange7Days;
    }

    /**
     * Sets a new sum change 7 days check.
     * @param dailySumChange7Days Sum change 7 days check.
     */
    public void setDailySumChange7Days(ColumnSumChange7DaysCheckSpec dailySumChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailySumChange7Days, dailySumChange7Days));
        this.dailySumChange7Days = dailySumChange7Days;
        propagateHierarchyIdToField(dailySumChange7Days, "daily_sum_change_7_days");
    }

    /**
     * Returns the sum change 30 days check.
     * @return Sum change 30 days check.
     */
    public ColumnSumChange30DaysCheckSpec getDailySumChange30Days() {
        return dailySumChange30Days;
    }

    /**
     * Sets a new sum change 30 days check.
     * @param dailySumChange30Days Sum change 30 days check.
     */
    public void setDailySumChange30Days(ColumnSumChange30DaysCheckSpec dailySumChange30Days) {
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
