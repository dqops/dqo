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
package com.dqops.checks.column.monitoring.uniqueness;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.uniqueness.*;
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
 * Container of uniqueness data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_distinct_count", o -> o.dailyDistinctCount);
            put("daily_distinct_percent", o -> o.dailyDistinctPercent);
            put("daily_duplicate_count", o -> o.dailyDuplicateCount);
            put("daily_duplicate_percent", o -> o.dailyDuplicatePercent);

            put("daily_anomaly_differencing_distinct_count_30_days", o -> o.dailyAnomalyDifferencingDistinctCount30Days);
            put("daily_anomaly_differencing_distinct_count", o -> o.dailyAnomalyDifferencingDistinctCount);
            put("daily_anomaly_stationary_distinct_percent_30_days", o -> o.dailyAnomalyStationaryDistinctPercent30Days);
            put("daily_anomaly_stationary_distinct_percent", o -> o.dailyAnomalyStationaryDistinctPercent);
            put("daily_change_distinct_count", o -> o.dailyChangeDistinctCount);
            put("daily_change_distinct_count_since_7_days", o -> o.dailyChangeDistinctCountSince7Days);
            put("daily_change_distinct_count_since_30_days", o -> o.dailyChangeDistinctCountSince30Days);
            put("daily_change_distinct_count_since_yesterday", o -> o.dailyChangeDistinctCountSinceYesterday);
            put("daily_change_distinct_percent", o -> o.dailyChangeDistinctPercent);
            put("daily_change_distinct_percent_since_7_days", o -> o.dailyChangeDistinctPercentSince7Days);
            put("daily_change_distinct_percent_since_30_days", o -> o.dailyChangeDistinctPercentSince30Days);
            put("daily_change_distinct_percent_since_yesterday", o -> o.dailyChangeDistinctPercentSinceYesterday);

        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDistinctCountCheckSpec dailyDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDistinctPercentCheckSpec dailyDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDuplicateCountCheckSpec dailyDuplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDuplicatePercentCheckSpec dailyDuplicatePercent;

    @JsonProperty("daily_anomaly_differencing_distinct_count_30_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec dailyAnomalyDifferencingDistinctCount30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyDifferencingDistinctCountCheckSpec dailyAnomalyDifferencingDistinctCount;

    @JsonProperty("daily_anomaly_stationary_distinct_percent_30_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec dailyAnomalyStationaryDistinctPercent30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryDistinctPercentCheckSpec dailyAnomalyStationaryDistinctPercent;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctCountCheckSpec dailyChangeDistinctCount;

    @JsonProperty("daily_change_distinct_count_since_7_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctCountSince7DaysCheckSpec dailyChangeDistinctCountSince7Days;

    @JsonProperty("daily_change_distinct_count_since_30_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctCountSince30DaysCheckSpec dailyChangeDistinctCountSince30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctCountSinceYesterdayCheckSpec dailyChangeDistinctCountSinceYesterday;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctPercentCheckSpec dailyChangeDistinctPercent;

    @JsonProperty("daily_change_distinct_percent_since_7_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctPercentSince7DaysCheckSpec dailyChangeDistinctPercentSince7Days;

    @JsonProperty("daily_change_distinct_percent_since_30_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctPercentSince30DaysCheckSpec dailyChangeDistinctPercentSince30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctPercentSinceYesterdayCheckSpec dailyChangeDistinctPercentSinceYesterday;

    /**
     * Returns a distinct values count check specification.
     * @return Distinct values count check specification.
     */
    public ColumnDistinctCountCheckSpec getDailyDistinctCount() {
        return dailyDistinctCount;
    }

    /**
     * Sets a new specification of a distinct values count check.
     * @param dailyDistinctCount Distinct values count check specification.
     */
    public void setDailyDistinctCount(ColumnDistinctCountCheckSpec dailyDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctCount, dailyDistinctCount));
        this.dailyDistinctCount = dailyDistinctCount;
        propagateHierarchyIdToField(dailyDistinctCount, "daily_distinct_count");
    }

    /**
     * Returns a distinct values percent check specification.
     * @return Distinct values percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getDailyDistinctPercent() {
        return dailyDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct values percent check.
     * @param dailyDistinctPercent Distinct values count percent specification.
     */
    public void setDailyDistinctPercent(ColumnDistinctPercentCheckSpec dailyDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctPercent, dailyDistinctPercent));
        this.dailyDistinctPercent = dailyDistinctPercent;
        propagateHierarchyIdToField(dailyDistinctPercent, "daily_distinct_percent");
    }

    /**
     * Returns a  duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getDailyDuplicateCount() {
        return dailyDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param dailyDuplicateCount Duplicate values count check specification.
     */
    public void setDailyDuplicateCount(ColumnDuplicateCountCheckSpec dailyDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.dailyDuplicateCount, dailyDuplicateCount));
        this.dailyDuplicateCount = dailyDuplicateCount;
        propagateHierarchyIdToField(dailyDuplicateCount, "daily_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getDailyDuplicatePercent() {
        return dailyDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param dailyDuplicatePercent Duplicate values percent check specification.
     */
    public void setDailyDuplicatePercent(ColumnDuplicatePercentCheckSpec dailyDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyDuplicatePercent, dailyDuplicatePercent));
        this.dailyDuplicatePercent = dailyDuplicatePercent;
        propagateHierarchyIdToField(dailyDuplicatePercent, "daily_duplicate_percent");
    }

    /**
     * Returns a distinct count value anomaly 30 days check specification.
     * @return Distinct count value anomaly 30 days check specification.
     */
    public ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec getDailyAnomalyDifferencingDistinctCount30Days() {
        return dailyAnomalyDifferencingDistinctCount30Days;
    }

    /**
     * Sets a new specification of a distinct count value anomaly 30 days check.
     * @param dailyAnomalyDifferencingDistinctCount30Days Distinct count value anomaly 30 days check specification.
     */
    public void setDailyAnomalyDifferencingDistinctCount30Days(ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec dailyAnomalyDifferencingDistinctCount30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyAnomalyDifferencingDistinctCount30Days, dailyAnomalyDifferencingDistinctCount30Days));
        this.dailyAnomalyDifferencingDistinctCount30Days = dailyAnomalyDifferencingDistinctCount30Days;
        propagateHierarchyIdToField(dailyAnomalyDifferencingDistinctCount30Days, "daily_anomaly_differencing_distinct_count_30_days");
    }

    /**
     * Returns a distinct count value anomaly check specification.
     * @return Distinct count value anomaly check specification.
     */
    public ColumnAnomalyDifferencingDistinctCountCheckSpec getDailyAnomalyDifferencingDistinctCount() {
        return dailyAnomalyDifferencingDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value anomaly check.
     * @param dailyAnomalyDifferencingDistinctCount Distinct count value anomaly check specification.
     */
    public void setDailyAnomalyDifferencingDistinctCount(ColumnAnomalyDifferencingDistinctCountCheckSpec dailyAnomalyDifferencingDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.dailyAnomalyDifferencingDistinctCount, dailyAnomalyDifferencingDistinctCount));
        this.dailyAnomalyDifferencingDistinctCount = dailyAnomalyDifferencingDistinctCount;
        propagateHierarchyIdToField(dailyAnomalyDifferencingDistinctCount, "daily_anomaly_differencing_distinct_count");
    }

    /**
     * Returns a distinct percent value anomaly 30 days check specification.
     * @return Distinct percent value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec getDailyAnomalyStationaryDistinctPercent30Days() {
        return dailyAnomalyStationaryDistinctPercent30Days;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly 30 days check.
     * @param dailyAnomalyStationaryDistinctPercent30Days Distinct percent value anomaly 30 days check specification.
     */
    public void setDailyAnomalyStationaryDistinctPercent30Days(ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec dailyAnomalyStationaryDistinctPercent30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyAnomalyStationaryDistinctPercent30Days, dailyAnomalyStationaryDistinctPercent30Days));
        this.dailyAnomalyStationaryDistinctPercent30Days = dailyAnomalyStationaryDistinctPercent30Days;
        propagateHierarchyIdToField(dailyAnomalyStationaryDistinctPercent30Days, "daily_anomaly_stationary_distinct_percent_30_days");
    }

    /**
     * Returns a distinct percent value anomaly check specification.
     * @return Distinct percent value anomaly check specification.
     */
    public ColumnAnomalyStationaryDistinctPercentCheckSpec getDailyAnomalyStationaryDistinctPercent() {
        return dailyAnomalyStationaryDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly check.
     * @param dailyAnomalyStationaryDistinctPercent Distinct percent value anomaly check specification.
     */
    public void setDailyAnomalyStationaryDistinctPercent(ColumnAnomalyStationaryDistinctPercentCheckSpec dailyAnomalyStationaryDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyAnomalyStationaryDistinctPercent, dailyAnomalyStationaryDistinctPercent));
        this.dailyAnomalyStationaryDistinctPercent = dailyAnomalyStationaryDistinctPercent;
        propagateHierarchyIdToField(dailyAnomalyStationaryDistinctPercent, "daily_anomaly_stationary_distinct_percent");
    }

    /**
     * Returns the distinct count value change check specification.
     * @return Distinct count value change check specification.
     */
    public ColumnChangeDistinctCountCheckSpec getDailyChangeDistinctCount() {
        return dailyChangeDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value change check.
     * @param dailyChangeDistinctCount Distinct count value change check specification.
     */
    public void setDailyChangeDistinctCount(ColumnChangeDistinctCountCheckSpec dailyChangeDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.dailyChangeDistinctCount, dailyChangeDistinctCount));
        this.dailyChangeDistinctCount = dailyChangeDistinctCount;
        propagateHierarchyIdToField(dailyChangeDistinctCount, "daily_change_distinct_count");
    }

    /**
     * Returns the distinct count value change since 7 days check specification.
     * @return Distinct count value change since 7 days check specification.
     */
    public ColumnChangeDistinctCountSince7DaysCheckSpec getDailyChangeDistinctCountSince7Days() {
        return dailyChangeDistinctCountSince7Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 7 days check.
     * @param dailyChangeDistinctCountSince7Days Distinct count value change since 7 days check specification.
     */
    public void setDailyChangeDistinctCountSince7Days(ColumnChangeDistinctCountSince7DaysCheckSpec dailyChangeDistinctCountSince7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyChangeDistinctCountSince7Days, dailyChangeDistinctCountSince7Days));
        this.dailyChangeDistinctCountSince7Days = dailyChangeDistinctCountSince7Days;
        propagateHierarchyIdToField(dailyChangeDistinctCountSince7Days, "daily_change_distinct_count_since_7_days");
    }

    /**
     * Returns the distinct count value change since 30 days check specification.
     * @return Distinct count value change since 30 days check specification.
     */
    public ColumnChangeDistinctCountSince30DaysCheckSpec getDailyChangeDistinctCountSince30Days() {
        return dailyChangeDistinctCountSince30Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 30 days check.
     * @param dailyChangeDistinctCountSince30Days Distinct count value change since 30 days check specification.
     */
    public void setDailyChangeDistinctCountSince30Days(ColumnChangeDistinctCountSince30DaysCheckSpec dailyChangeDistinctCountSince30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyChangeDistinctCountSince30Days, dailyChangeDistinctCountSince30Days));
        this.dailyChangeDistinctCountSince30Days = dailyChangeDistinctCountSince30Days;
        propagateHierarchyIdToField(dailyChangeDistinctCountSince30Days, "daily_change_distinct_count_since_30_days");
    }

    /**
     * Returns the distinct count value change since yesterday check specification.
     * @return Distinct count value change since yesterday check specification.
     */
    public ColumnChangeDistinctCountSinceYesterdayCheckSpec getDailyChangeDistinctCountSinceYesterday() {
        return dailyChangeDistinctCountSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count value change since yesterday check .
     * @param dailyChangeDistinctCountSinceYesterday Distinct count value change since yesterday check specification.
     */
    public void setDailyChangeDistinctCountSinceYesterday(ColumnChangeDistinctCountSinceYesterdayCheckSpec dailyChangeDistinctCountSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyChangeDistinctCountSinceYesterday, dailyChangeDistinctCountSinceYesterday));
        this.dailyChangeDistinctCountSinceYesterday = dailyChangeDistinctCountSinceYesterday;
        propagateHierarchyIdToField(dailyChangeDistinctCountSinceYesterday, "daily_change_distinct_count_since_yesterday");
    }

    /**
     * Returns the distinct percent value change check specification.
     * @return Distinct percent value change check specification.
     */
    public ColumnChangeDistinctPercentCheckSpec getDailyChangeDistinctPercent() {
        return dailyChangeDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value change check.
     * @param dailyChangeDistinctPercent Distinct percent value change check specification.
     */
    public void setDailyChangeDistinctPercent(ColumnChangeDistinctPercentCheckSpec dailyChangeDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyChangeDistinctPercent, dailyChangeDistinctPercent));
        this.dailyChangeDistinctPercent = dailyChangeDistinctPercent;
        propagateHierarchyIdToField(dailyChangeDistinctPercent, "daily_change_distinct_percent");
    }

    /**
     * Returns the distinct percent value change since 7 days check specification.
     * @return Distinct count percent change since 7 days check specification.
     */
    public ColumnChangeDistinctPercentSince7DaysCheckSpec getDailyChangeDistinctPercentSince7Days() {
        return dailyChangeDistinctPercentSince7Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 7 days check.
     * @param dailyChangeDistinctPercentSince7Days Distinct count percent change since 7 days check specification.
     */
    public void setDailyChangeDistinctPercentSince7Days(ColumnChangeDistinctPercentSince7DaysCheckSpec dailyChangeDistinctPercentSince7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyChangeDistinctPercentSince7Days, dailyChangeDistinctPercentSince7Days));
        this.dailyChangeDistinctPercentSince7Days = dailyChangeDistinctPercentSince7Days;
        propagateHierarchyIdToField(dailyChangeDistinctPercentSince7Days, "daily_change_distinct_percent_since_7_days");
    }

    /**
     * Returns the distinct percent value change since 30 days check specification.
     * @return Distinct count percent change since 30 days check specification.
     */
    public ColumnChangeDistinctPercentSince30DaysCheckSpec getDailyChangeDistinctPercentSince30Days() {
        return dailyChangeDistinctPercentSince30Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 30 days check specification.
     * @param dailyChangeDistinctPercentSince30Days Distinct count percent change since 30 days check specification.
     */
    public void setDailyChangeDistinctPercentSince30Days(ColumnChangeDistinctPercentSince30DaysCheckSpec dailyChangeDistinctPercentSince30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyChangeDistinctPercentSince30Days, dailyChangeDistinctPercentSince30Days));
        this.dailyChangeDistinctPercentSince30Days = dailyChangeDistinctPercentSince30Days;
        propagateHierarchyIdToField(dailyChangeDistinctPercentSince30Days, "daily_change_distinct_percent_since_30_days");
    }

    /**
     * Returns the distinct percent value change since yesterday check specification.
     * @return Distinct count percent change since yesterday check specification.
     */
    public ColumnChangeDistinctPercentSinceYesterdayCheckSpec getDailyChangeDistinctPercentSinceYesterday() {
        return dailyChangeDistinctPercentSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count percent change since yesterday check specification.
     * @param dailyChangeDistinctPercentSinceYesterday Distinct count percent change since yesterday check specification.
     */
    public void setDailyChangeDistinctPercentSinceYesterday(ColumnChangeDistinctPercentSinceYesterdayCheckSpec dailyChangeDistinctPercentSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyChangeDistinctPercentSinceYesterday, dailyChangeDistinctPercentSinceYesterday));
        this.dailyChangeDistinctPercentSinceYesterday = dailyChangeDistinctPercentSinceYesterday;
        propagateHierarchyIdToField(dailyChangeDistinctPercentSinceYesterday, "daily_change_distinct_percent_since_yesterday");
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
    public ColumnUniquenessDailyMonitoringChecksSpec deepClone() {
        return (ColumnUniquenessDailyMonitoringChecksSpec)super.deepClone();
    }
}