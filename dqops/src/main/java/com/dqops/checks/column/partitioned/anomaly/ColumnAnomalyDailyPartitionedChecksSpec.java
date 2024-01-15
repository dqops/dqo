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
public class ColumnAnomalyDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_sum_anomaly", o -> o.dailyPartitionSumAnomaly);
            put("daily_partition_mean_anomaly", o -> o.dailyPartitionMeanAnomaly);
            put("daily_partition_median_anomaly", o -> o.dailyPartitionMedianAnomaly);

            put("daily_partition_mean_change", o -> o.dailyPartitionMeanChange);
            put("daily_partition_mean_change_1_day", o -> o.dailyPartitionMeanChange1Day);
            put("daily_partition_mean_change_7_days", o -> o.dailyPartitionMeanChange7Days);
            put("daily_partition_mean_change_30_days", o -> o.dailyPartitionMeanChange30Days);

            put("daily_partition_median_change", o -> o.dailyPartitionMedianChange);
            put("daily_partition_median_change_1_day", o -> o.dailyPartitionMedianChange1Day);
            put("daily_partition_median_change_7_days", o -> o.dailyPartitionMedianChange7Days);
            put("daily_partition_median_change_30_days", o -> o.dailyPartitionMedianChange30Days);

            put("daily_partition_sum_change", o -> o.dailyPartitionSumChange);
            put("daily_partition_sum_change_1_day", o -> o.dailyPartitionSumChange1Day);
            put("daily_partition_sum_change_7_days", o -> o.dailyPartitionSumChange7Days);
            put("daily_partition_sum_change_30_days", o -> o.dailyPartitionSumChange30Days);
        }
    };

    @JsonPropertyDescription("Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.")
    private ColumnSumAnomalyStationaryPartitionCheckSpec dailyPartitionSumAnomaly;

    @JsonPropertyDescription("Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.")
    private ColumnMeanAnomalyStationaryCheckSpec dailyPartitionMeanAnomaly;

    @JsonPropertyDescription("Verifies that the median in a column is within a percentile from measurements made during the last 90 days.")
    private ColumnMedianAnomalyStationaryCheckSpec dailyPartitionMedianAnomaly;

    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout.")
    private ColumnMeanChangeCheckSpec dailyPartitionMeanChange;

    @JsonProperty("daily_partition_mean_change_1_day")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.")
    private ColumnMeanChange1DayCheckSpec dailyPartitionMeanChange1Day;

    @JsonProperty("daily_partition_mean_change_7_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.")
    private ColumnMeanChange7DaysCheckSpec dailyPartitionMeanChange7Days;

    @JsonProperty("daily_partition_mean_change_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.")
    private ColumnMeanChange30DaysCheckSpec dailyPartitionMeanChange30Days;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since the last readout.")
    private ColumnMedianChangeCheckSpec dailyPartitionMedianChange;

    @JsonProperty("daily_partition_median_change_1_day")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.")
    private ColumnMedianChange1DayCheckSpec dailyPartitionMedianChange1Day;

    @JsonProperty("daily_partition_median_change_7_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since the last readout from the last week.")
    private ColumnMedianChange7DaysCheckSpec dailyPartitionMedianChange7Days;

    @JsonProperty("daily_partition_median_change_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since the last readout from the last month.")
    private ColumnMedianChange30DaysCheckSpec dailyPartitionMedianChange30Days;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since the last readout.")
    private ColumnSumChangeCheckSpec dailyPartitionSumChange;

    @JsonProperty("daily_partition_sum_change_1_day")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.")
    private ColumnSumChange1DayCheckSpec dailyPartitionSumChange1Day;

    @JsonProperty("daily_partition_sum_change_7_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.")
    private ColumnSumChange7DaysCheckSpec dailyPartitionSumChange7Days;

    @JsonProperty("daily_partition_sum_change_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.")
    private ColumnSumChange30DaysCheckSpec dailyPartitionSumChange30Days;

    /**
     * Returns a sum anomaly 90 days check specification.
     * @return Sum anomaly 90 days check specification.
     */
    public ColumnSumAnomalyStationaryPartitionCheckSpec getDailyPartitionSumAnomaly() {
        return dailyPartitionSumAnomaly;
    }

    /**
     * Sets a new specification of a sum anomaly 90 days check.
     * @param dailyPartitionSumAnomaly Sum anomaly 90 days check specification.
     */
    public void setDailyPartitionSumAnomaly(ColumnSumAnomalyStationaryPartitionCheckSpec dailyPartitionSumAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumAnomaly, dailyPartitionSumAnomaly));
        this.dailyPartitionSumAnomaly = dailyPartitionSumAnomaly;
        propagateHierarchyIdToField(dailyPartitionSumAnomaly, "daily_partition_sum_anomaly");
    }

    /**
     * Returns a mean value anomaly 90 days check specification.
     * @return Mean value anomaly 90 days check specification.
     */
    public ColumnMeanAnomalyStationaryCheckSpec getDailyPartitionMeanAnomaly() {
        return dailyPartitionMeanAnomaly;
    }

    /**
     * Sets a new specification of a mean value anomaly 90 days check.
     * @param dailyPartitionMeanAnomaly Mean value anomaly 90 days check specification.
     */
    public void setDailyPartitionMeanAnomaly(ColumnMeanAnomalyStationaryCheckSpec dailyPartitionMeanAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanAnomaly, dailyPartitionMeanAnomaly));
        this.dailyPartitionMeanAnomaly = dailyPartitionMeanAnomaly;
        propagateHierarchyIdToField(dailyPartitionMeanAnomaly, "daily_partition_mean_anomaly");
    }

    /**
     * Returns a median anomaly 90 days check specification.
     * @return Median anomaly 90 days check specification.
     */
    public ColumnMedianAnomalyStationaryCheckSpec getDailyPartitionMedianAnomaly() {
        return dailyPartitionMedianAnomaly;
    }

    /**
     * Sets a new specification of a median anomaly 90 days check.
     * @param dailyPartitionMedianAnomaly Median anomaly 90 days check specification.
     */
    public void setDailyPartitionMedianAnomaly(ColumnMedianAnomalyStationaryCheckSpec dailyPartitionMedianAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianAnomaly, dailyPartitionMedianAnomaly));
        this.dailyPartitionMedianAnomaly = dailyPartitionMedianAnomaly;
        propagateHierarchyIdToField(dailyPartitionMedianAnomaly, "daily_partition_median_anomaly");
    }

    /**
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnMeanChangeCheckSpec getDailyPartitionMeanChange() {
        return dailyPartitionMeanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param dailyPartitionMeanChange Mean value change check.
     */
    public void setDailyPartitionMeanChange(ColumnMeanChangeCheckSpec dailyPartitionMeanChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanChange, dailyPartitionMeanChange));
        this.dailyPartitionMeanChange = dailyPartitionMeanChange;
        propagateHierarchyIdToField(dailyPartitionMeanChange, "daily_partition_mean_change");
    }

    /**
     * Returns the mean value change yesterday check.
     * @return Mean value change yesterday check.
     */
    public ColumnMeanChange1DayCheckSpec getDailyPartitionMeanChange1Day() {
        return dailyPartitionMeanChange1Day;
    }

    /**
     * Sets a new mean value change yesterday check.
     * @param dailyPartitionMeanChange1Day Mean value change yesterday check.
     */
    public void setDailyPartitionMeanChange1Day(ColumnMeanChange1DayCheckSpec dailyPartitionMeanChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanChange1Day, dailyPartitionMeanChange1Day));
        this.dailyPartitionMeanChange1Day = dailyPartitionMeanChange1Day;
        propagateHierarchyIdToField(dailyPartitionMeanChange1Day, "daily_partition_mean_change_1_day");
    }

    /**
     * Returns the mean value change 7 days check.
     * @return Mean value change 7 days check.
     */
    public ColumnMeanChange7DaysCheckSpec getDailyPartitionMeanChange7Days() {
        return dailyPartitionMeanChange7Days;
    }

    /**
     * Sets a new mean value change 7 days check.
     * @param dailyPartitionMeanChange7Days Mean value change 7 days check.
     */
    public void setDailyPartitionMeanChange7Days(ColumnMeanChange7DaysCheckSpec dailyPartitionMeanChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanChange7Days, dailyPartitionMeanChange7Days));
        this.dailyPartitionMeanChange7Days = dailyPartitionMeanChange7Days;
        propagateHierarchyIdToField(dailyPartitionMeanChange7Days, "daily_partition_mean_change_7_days");
    }

    /**
     * Returns the mean value change 30 days check.
     * @return Mean value change 30 days check.
     */
    public ColumnMeanChange30DaysCheckSpec getDailyPartitionMeanChange30Days() {
        return dailyPartitionMeanChange30Days;
    }

    /**
     * Sets a new mean value change 30 days check.
     * @param dailyPartitionMeanChange30Days Mean value change 30 days check.
     */
    public void setDailyPartitionMeanChange30Days(ColumnMeanChange30DaysCheckSpec dailyPartitionMeanChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMeanChange30Days, dailyPartitionMeanChange30Days));
        this.dailyPartitionMeanChange30Days = dailyPartitionMeanChange30Days;
        propagateHierarchyIdToField(dailyPartitionMeanChange30Days, "daily_partition_mean_change_30_days");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnMedianChangeCheckSpec getDailyPartitionMedianChange() {
        return dailyPartitionMedianChange;
    }

    /**
     * Sets a new median change check.
     * @param dailyPartitionMedianChange Median change check.
     */
    public void setDailyPartitionMedianChange(ColumnMedianChangeCheckSpec dailyPartitionMedianChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianChange, dailyPartitionMedianChange));
        this.dailyPartitionMedianChange = dailyPartitionMedianChange;
        propagateHierarchyIdToField(dailyPartitionMedianChange, "daily_partition_median_change");
    }

    /**
     * Returns the median change yesterday check.
     * @return Median change yesterday check.
     */
    public ColumnMedianChange1DayCheckSpec getDailyPartitionMedianChange1Day() {
        return dailyPartitionMedianChange1Day;
    }

    /**
     * Sets a new median change yesterday check.
     * @param dailyPartitionMedianChange1Day Median change yesterday check.
     */
    public void setDailyPartitionMedianChange1Day(ColumnMedianChange1DayCheckSpec dailyPartitionMedianChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianChange1Day, dailyPartitionMedianChange1Day));
        this.dailyPartitionMedianChange1Day = dailyPartitionMedianChange1Day;
        propagateHierarchyIdToField(dailyPartitionMedianChange1Day, "daily_partition_median_change_1_day");
    }

    /**
     * Returns the median change 7 days check.
     * @return Median change 7 days check.
     */
    public ColumnMedianChange7DaysCheckSpec getDailyPartitionMedianChange7Days() {
        return dailyPartitionMedianChange7Days;
    }

    /**
     * Sets a new median change 7 days check.
     * @param dailyPartitionMedianChange7Days Median change 7 days check.
     */
    public void setDailyPartitionMedianChange7Days(ColumnMedianChange7DaysCheckSpec dailyPartitionMedianChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianChange7Days, dailyPartitionMedianChange7Days));
        this.dailyPartitionMedianChange7Days = dailyPartitionMedianChange7Days;
        propagateHierarchyIdToField(dailyPartitionMedianChange7Days, "daily_partition_median_change_7_days");
    }

    /**
     * Returns the median change 30 days check.
     * @return Median change 30 days check.
     */
    public ColumnMedianChange30DaysCheckSpec getDailyPartitionMedianChange30Days() {
        return dailyPartitionMedianChange30Days;
    }

    /**
     * Sets a new median change 30 days check.
     * @param dailyPartitionMedianChange30Days Median change 30 days check.
     */
    public void setDailyPartitionMedianChange30Days(ColumnMedianChange30DaysCheckSpec dailyPartitionMedianChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMedianChange30Days, dailyPartitionMedianChange30Days));
        this.dailyPartitionMedianChange30Days = dailyPartitionMedianChange30Days;
        propagateHierarchyIdToField(dailyPartitionMedianChange30Days, "daily_partition_median_change_30_days");
    }

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnSumChangeCheckSpec getDailyPartitionSumChange() {
        return dailyPartitionSumChange;
    }

    /**
     * Sets a new sum change check.
     * @param dailyPartitionSumChange Sum change check.
     */
    public void setDailyPartitionSumChange(ColumnSumChangeCheckSpec dailyPartitionSumChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumChange, dailyPartitionSumChange));
        this.dailyPartitionSumChange = dailyPartitionSumChange;
        propagateHierarchyIdToField(dailyPartitionSumChange, "daily_partition_sum_change");
    }

    /**
     * Returns the sum change yesterday check.
     * @return Sum change yesterday check.
     */
    public ColumnSumChange1DayCheckSpec getDailyPartitionSumChange1Day() {
        return dailyPartitionSumChange1Day;
    }

    /**
     * Sets a new sum change yesterday check.
     * @param dailyPartitionSumChange1Day Sum change yesterday check.
     */
    public void setDailyPartitionSumChange1Day(ColumnSumChange1DayCheckSpec dailyPartitionSumChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumChange1Day, dailyPartitionSumChange1Day));
        this.dailyPartitionSumChange1Day = dailyPartitionSumChange1Day;
        propagateHierarchyIdToField(dailyPartitionSumChange1Day, "daily_partition_sum_change_1_day");
    }

    /**
     * Returns the sum change 7 days check.
     * @return Sum change 7 days check.
     */
    public ColumnSumChange7DaysCheckSpec getDailyPartitionSumChange7Days() {
        return dailyPartitionSumChange7Days;
    }

    /**
     * Sets a new sum change 7 days check.
     * @param dailyPartitionSumChange7Days Sum change 7 days check.
     */
    public void setDailyPartitionSumChange7Days(ColumnSumChange7DaysCheckSpec dailyPartitionSumChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionSumChange7Days, dailyPartitionSumChange7Days));
        this.dailyPartitionSumChange7Days = dailyPartitionSumChange7Days;
        propagateHierarchyIdToField(dailyPartitionSumChange7Days, "daily_partition_sum_change_7_days");
    }

    /**
     * Returns the sum change 30 days check.
     * @return Sum change 30 days check.
     */
    public ColumnSumChange30DaysCheckSpec getDailyPartitionSumChange30Days() {
        return dailyPartitionSumChange30Days;
    }

    /**
     * Sets a new sum change 30 days check.
     * @param dailyPartitionSumChange30Days Sum change 30 days check.
     */
    public void setDailyPartitionSumChange30Days(ColumnSumChange30DaysCheckSpec dailyPartitionSumChange30Days) {
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
        return CheckType.partitioned;
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
