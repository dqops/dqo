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
package com.dqops.checks.column.recurring.anomaly;

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
public class ColumnAnomalyDailyRecurringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyDailyRecurringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_mean_anomaly_30_days", o -> o.dailyMeanAnomaly30Days);
            put("daily_mean_anomaly_60_days", o -> o.dailyMeanAnomaly60Days);
            
            put("daily_median_anomaly_30_days", o -> o.dailyMedianAnomaly30Days);
            put("daily_median_anomaly_60_days", o -> o.dailyMedianAnomaly60Days);

            put("daily_sum_anomaly_30_days", o -> o.dailySumAnomaly30Days);
            put("daily_sum_anomaly_60_days", o -> o.dailySumAnomaly60Days);

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

    @JsonProperty("daily_mean_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyMeanChange30DaysCheckSpec dailyMeanAnomaly30Days;

    @JsonProperty("daily_mean_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalyMeanChange60DaysCheckSpec dailyMeanAnomaly60Days;

    @JsonProperty("daily_median_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyMedianChange30DaysCheckSpec dailyMedianAnomaly30Days;

    @JsonProperty("daily_median_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalyMedianChange60DaysCheckSpec dailyMedianAnomaly60Days;

    @JsonProperty("daily_sum_anomaly_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalySumChange30DaysCheckSpec dailySumAnomaly30Days;

    @JsonProperty("daily_sum_anomaly_60_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.")
    private ColumnAnomalySumChange60DaysCheckSpec dailySumAnomaly60Days;

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
    public ColumnAnomalyMeanChange30DaysCheckSpec getDailyMeanAnomaly30Days() {
        return dailyMeanAnomaly30Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 30 days check.
     * @param dailyMeanAnomaly30Days Mean value anomaly 30 days check specification.
     */
    public void setDailyMeanAnomaly30Days(ColumnAnomalyMeanChange30DaysCheckSpec dailyMeanAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanAnomaly30Days, dailyMeanAnomaly30Days));
        this.dailyMeanAnomaly30Days = dailyMeanAnomaly30Days;
        propagateHierarchyIdToField(dailyMeanAnomaly30Days, "daily_mean_anomaly_30_days");
    }

    /**
     * Returns a mean value anomaly 60 days check specification.
     * @return Mean value anomaly 60 days check specification.
     */
    public ColumnAnomalyMeanChange60DaysCheckSpec getDailyMeanAnomaly60Days() {
        return dailyMeanAnomaly60Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 60 days check.
     * @param dailyMeanAnomaly60Days Mean value anomaly 60 days check specification.
     */
    public void setDailyMeanAnomaly60Days(ColumnAnomalyMeanChange60DaysCheckSpec dailyMeanAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMeanAnomaly60Days, dailyMeanAnomaly60Days));
        this.dailyMeanAnomaly60Days = dailyMeanAnomaly60Days;
        propagateHierarchyIdToField(dailyMeanAnomaly60Days, "daily_mean_anomaly_60_days");
    }

    /**
     * Returns a median anomaly 30 days check specification.
     * @return Median anomaly 30 days check specification.
     */
    public ColumnAnomalyMedianChange30DaysCheckSpec getDailyMedianAnomaly30Days() {
        return dailyMedianAnomaly30Days;
    }

    /**
     * Sets a new specification of a median anomaly 30 days check.
     * @param dailyMedianAnomaly30Days Median anomaly 30 days check specification.
     */
    public void setDailyMedianAnomaly30Days(ColumnAnomalyMedianChange30DaysCheckSpec dailyMedianAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianAnomaly30Days, dailyMedianAnomaly30Days));
        this.dailyMedianAnomaly30Days = dailyMedianAnomaly30Days;
        propagateHierarchyIdToField(dailyMedianAnomaly30Days, "daily_median_anomaly_30_days");
    }

    /**
     * Returns a median anomaly 60 days check specification.
     * @return Median anomaly 60 days check specification.
     */
    public ColumnAnomalyMedianChange60DaysCheckSpec getDailyMedianAnomaly60Days() {
        return dailyMedianAnomaly60Days;
    }

    /**
     * Sets a new specification of a median anomaly 60 days check.
     * @param dailyMedianAnomaly60Days Median anomaly 60 days check specification.
     */
    public void setDailyMedianAnomaly60Days(ColumnAnomalyMedianChange60DaysCheckSpec dailyMedianAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailyMedianAnomaly60Days, dailyMedianAnomaly60Days));
        this.dailyMedianAnomaly60Days = dailyMedianAnomaly60Days;
        propagateHierarchyIdToField(dailyMedianAnomaly60Days, "daily_median_anomaly_60_days");
    }

    /**
     * Returns a sum anomaly 30 days check specification.
     * @return Sum anomaly 30 days check specification.
     */
    public ColumnAnomalySumChange30DaysCheckSpec getDailySumAnomaly30Days() {
        return dailySumAnomaly30Days;
    }

    /**
     * Sets a new specification of a sum anomaly 30 days check.
     * @param dailySumAnomaly30Days Sum anomaly 30 days check specification.
     */
    public void setDailySumAnomaly30Days(ColumnAnomalySumChange30DaysCheckSpec dailySumAnomaly30Days) {
        this.setDirtyIf(!Objects.equals(this.dailySumAnomaly30Days, dailySumAnomaly30Days));
        this.dailySumAnomaly30Days = dailySumAnomaly30Days;
        propagateHierarchyIdToField(dailySumAnomaly30Days, "daily_sum_anomaly_30_days");
    }

    /**
     * Returns a sum anomaly 60 days check specification.
     * @return Sum anomaly 60 days check specification.
     */
    public ColumnAnomalySumChange60DaysCheckSpec getDailySumAnomaly60Days() {
        return dailySumAnomaly60Days;
    }

    /**
     * Sets a new specification of a sum anomaly 60 days check.
     * @param dailySumAnomaly60Days Sum anomaly 60 days check specification.
     */
    public void setDailySumAnomaly60Days(ColumnAnomalySumChange60DaysCheckSpec dailySumAnomaly60Days) {
        this.setDirtyIf(!Objects.equals(this.dailySumAnomaly60Days, dailySumAnomaly60Days));
        this.dailySumAnomaly60Days = dailySumAnomaly60Days;
        propagateHierarchyIdToField(dailySumAnomaly60Days, "daily_sum_anomaly_60_days");
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
    public ColumnAnomalyDailyRecurringChecksSpec deepClone() {
        return (ColumnAnomalyDailyRecurringChecksSpec)super.deepClone();
    }
}
