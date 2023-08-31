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
package com.dqops.checks.column.partitioned.uniqueness;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.uniqueness.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of uniqueness data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_distinct_count", o -> o.dailyPartitionDistinctCount);
            put("daily_partition_distinct_percent", o -> o.dailyPartitionDistinctPercent);
            put("daily_partition_duplicate_count", o -> o.dailyPartitionDuplicateCount);
            put("daily_partition_duplicate_percent", o -> o.dailyPartitionDuplicatePercent);

            put("daily_partition_anomaly_stationary_distinct_count_30_days", o -> o.dailyPartitionAnomalyStationaryDistinctCount30Days);
            put("daily_partition_anomaly_stationary_distinct_count", o -> o.dailyPartitionAnomalyStationaryDistinctCount);
            put("daily_partition_anomaly_stationary_distinct_percent_30_days", o -> o.dailyPartitionAnomalyStationaryDistinctPercent30Days);
            put("daily_partition_anomaly_stationary_distinct_percent", o -> o.dailyPartitionAnomalyStationaryDistinctPercent);
            put("daily_partition_change_distinct_count", o -> o.dailyPartitionChangeDistinctCount);
            put("daily_partition_change_distinct_count_since_7_days", o -> o.dailyPartitionChangeDistinctCountSince7Days);
            put("daily_partition_change_distinct_count_since_30_days", o -> o.dailyPartitionChangeDistinctCountSince30Days);
            put("daily_partition_change_distinct_count_since_yesterday", o -> o.dailyPartitionChangeDistinctCountSinceYesterday);
            put("daily_partition_change_distinct_percent", o -> o.dailyPartitionChangeDistinctPercent);
            put("daily_partition_change_distinct_percent_since_7_days", o -> o.dailyPartitionChangeDistinctPercentSince7Days);
            put("daily_partition_change_distinct_percent_since_30_days", o -> o.dailyPartitionChangeDistinctPercentSince30Days);
            put("daily_partition_change_distinct_percent_since_yesterday", o -> o.dailyPartitionChangeDistinctPercentSinceYesterday);

        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDistinctCountCheckSpec dailyPartitionDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDistinctPercentCheckSpec dailyPartitionDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDuplicateCountCheckSpec dailyPartitionDuplicateCount;

    @JsonPropertyDescription("Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnDuplicatePercentCheckSpec dailyPartitionDuplicatePercent;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec dailyPartitionAnomalyStationaryDistinctCount30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryPartitionDistinctCountCheckSpec dailyPartitionAnomalyStationaryDistinctCount;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec dailyPartitionAnomalyStationaryDistinctPercent30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryDistinctPercentCheckSpec dailyPartitionAnomalyStationaryDistinctPercent;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctCountCheckSpec dailyPartitionChangeDistinctCount;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctCountSince7DaysCheckSpec dailyPartitionChangeDistinctCountSince7Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctCountSince30DaysCheckSpec dailyPartitionChangeDistinctCountSince30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctCountSinceYesterdayCheckSpec dailyPartitionChangeDistinctCountSinceYesterday;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctPercentCheckSpec dailyPartitionChangeDistinctPercent;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctPercentSince7DaysCheckSpec dailyPartitionChangeDistinctPercentSince7Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctPercentSince30DaysCheckSpec dailyPartitionChangeDistinctPercentSince30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctPercentSinceYesterdayCheckSpec dailyPartitionChangeDistinctPercentSinceYesterday;

    /**
     * Returns a distinct values count check specification.
     * @return Distinct values count check specification.
     */
    public ColumnDistinctCountCheckSpec getDailyPartitionDistinctCount() {
        return dailyPartitionDistinctCount;
    }

    /**
     * Sets a new specification of a distinct values count check.
     * @param dailyPartitionDistinctCount Distinct values count check specification.
     */
    public void setDailyPartitionDistinctCount(ColumnDistinctCountCheckSpec dailyPartitionDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctCount, dailyPartitionDistinctCount));
        this.dailyPartitionDistinctCount = dailyPartitionDistinctCount;
        propagateHierarchyIdToField(dailyPartitionDistinctCount, "daily_partition_distinct_count");
    }

    /**
     * Returns a distinct values percent check specification.
     * @return Distinct values percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getDailyPartitionDistinctPercent() {
        return dailyPartitionDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct values percent check.
     * @param dailyPartitionDistinctPercent Distinct values percent check specification.
     */
    public void setDailyPartitionDistinctPercent(ColumnDistinctPercentCheckSpec dailyPartitionDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctPercent, dailyPartitionDistinctPercent));
        this.dailyPartitionDistinctPercent = dailyPartitionDistinctPercent;
        propagateHierarchyIdToField(dailyPartitionDistinctPercent, "daily_partition_distinct_percent");
    }

    /**
     * Returns a duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getDailyPartitionDuplicateCount() {
        return dailyPartitionDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param dailyPartitionDuplicateCount Duplicate values count check specification.
     */
    public void setDailyPartitionDuplicateCount(ColumnDuplicateCountCheckSpec dailyPartitionDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDuplicateCount, dailyPartitionDuplicateCount));
        this.dailyPartitionDuplicateCount = dailyPartitionDuplicateCount;
        propagateHierarchyIdToField(dailyPartitionDuplicateCount, "daily_partition_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getDailyPartitionDuplicatePercent() {
        return dailyPartitionDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param dailyPartitionDuplicatePercent Duplicate values percent check specification.
     */
    public void setDailyPartitionDuplicatePercent(ColumnDuplicatePercentCheckSpec dailyPartitionDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDuplicatePercent, dailyPartitionDuplicatePercent));
        this.dailyPartitionDuplicatePercent = dailyPartitionDuplicatePercent;
        propagateHierarchyIdToField(dailyPartitionDuplicatePercent, "daily_partition_duplicate_percent");
    }


    /**
     * Returns a distinct count value anomaly 30 days check specification.
     * @return Distinct count value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec getDailyPartitionAnomalyDifferencingDistinctCount30Days() {
        return dailyPartitionAnomalyStationaryDistinctCount30Days;
    }

    /**
     * Sets a new specification of a distinct count value anomaly 30 days check.
     * @param dailyPartitionAnomalyStationaryDistinctCount30Days Distinct count value anomaly 30 days check specification.
     */
    public void setDailyPartitionAnomalyStationaryDistinctCount30Days(ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec dailyPartitionAnomalyStationaryDistinctCount30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionAnomalyStationaryDistinctCount30Days, dailyPartitionAnomalyStationaryDistinctCount30Days));
        this.dailyPartitionAnomalyStationaryDistinctCount30Days = dailyPartitionAnomalyStationaryDistinctCount30Days;
        propagateHierarchyIdToField(dailyPartitionAnomalyStationaryDistinctCount30Days, "daily_partition_anomaly_stationary_distinct_count_30_days");
    }

    /**
     * Returns a distinct count value anomaly check specification.
     * @return Distinct count value anomaly check specification.
     */
    public ColumnAnomalyStationaryPartitionDistinctCountCheckSpec getDailyPartitionAnomalyStationaryDistinctCount() {
        return dailyPartitionAnomalyStationaryDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value anomaly check.
     * @param dailyPartitionAnomalyStationaryDistinctCount Distinct count value anomaly check specification.
     */
    public void setDailyPartitionAnomalyStationaryDistinctCount(ColumnAnomalyStationaryPartitionDistinctCountCheckSpec dailyPartitionAnomalyStationaryDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionAnomalyStationaryDistinctCount, dailyPartitionAnomalyStationaryDistinctCount));
        this.dailyPartitionAnomalyStationaryDistinctCount = dailyPartitionAnomalyStationaryDistinctCount;
        propagateHierarchyIdToField(dailyPartitionAnomalyStationaryDistinctCount, "daily_partition_anomaly_stationary_distinct_count");
    }

    /**
     * Returns a distinct percent value anomaly 30 days check specification.
     * @return Distinct percent value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec getDailyPartitionAnomalyStationaryDistinctPercent30Days() {
        return dailyPartitionAnomalyStationaryDistinctPercent30Days;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly 30 days check.
     * @param dailyPartitionAnomalyStationaryDistinctPercent30Days Distinct percent value anomaly 30 days check specification.
     */
    public void setDailyPartitionAnomalyStationaryDistinctPercent30Days(ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec dailyPartitionAnomalyStationaryDistinctPercent30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionAnomalyStationaryDistinctPercent30Days, dailyPartitionAnomalyStationaryDistinctPercent30Days));
        this.dailyPartitionAnomalyStationaryDistinctPercent30Days = dailyPartitionAnomalyStationaryDistinctPercent30Days;
        propagateHierarchyIdToField(dailyPartitionAnomalyStationaryDistinctPercent30Days, "daily_partition_anomaly_stationary_distinct_percent_30_days");
    }

    /**
     * Returns a distinct percent value anomaly check specification.
     * @return Distinct percent value anomaly check specification.
     */
    public ColumnAnomalyStationaryDistinctPercentCheckSpec getDailyPartitionAnomalyStationaryDistinctPercent() {
        return dailyPartitionAnomalyStationaryDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly check.
     * @param dailyPartitionAnomalyStationaryDistinctPercent Distinct percent value anomaly check specification.
     */
    public void setDailyPartitionAnomalyStationaryDistinctPercent(ColumnAnomalyStationaryDistinctPercentCheckSpec dailyPartitionAnomalyStationaryDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionAnomalyStationaryDistinctPercent, dailyPartitionAnomalyStationaryDistinctPercent));
        this.dailyPartitionAnomalyStationaryDistinctPercent = dailyPartitionAnomalyStationaryDistinctPercent;
        propagateHierarchyIdToField(dailyPartitionAnomalyStationaryDistinctPercent, "daily_partition_anomaly_stationary_distinct_percent");
    }

    /**
     * Returns the distinct count value change check specification.
     * @return Distinct count value change check specification.
     */
    public ColumnChangeDistinctCountCheckSpec getDailyPartitionChangeDistinctCount() {
        return dailyPartitionChangeDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value change check.
     * @param dailyPartitionChangeDistinctCount Distinct count value change check specification.
     */
    public void setDailyPartitionChangeDistinctCount(ColumnChangeDistinctCountCheckSpec dailyPartitionChangeDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionChangeDistinctCount, dailyPartitionChangeDistinctCount));
        this.dailyPartitionChangeDistinctCount = dailyPartitionChangeDistinctCount;
        propagateHierarchyIdToField(dailyPartitionChangeDistinctCount, "daily_partition_change_distinct_count");
    }

    /**
     * Returns the distinct count value change since 7 days check specification.
     * @return Distinct count value change since 7 days check specification.
     */
    public ColumnChangeDistinctCountSince7DaysCheckSpec getDailyPartitionChangeDistinctCountSince7Days() {
        return dailyPartitionChangeDistinctCountSince7Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 7 days check.
     * @param dailyPartitionChangeDistinctCountSince7Days Distinct count value change since 7 days check specification.
     */
    public void setDailyPartitionChangeDistinctCountSince7Days(ColumnChangeDistinctCountSince7DaysCheckSpec dailyPartitionChangeDistinctCountSince7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionChangeDistinctCountSince7Days, dailyPartitionChangeDistinctCountSince7Days));
        this.dailyPartitionChangeDistinctCountSince7Days = dailyPartitionChangeDistinctCountSince7Days;
        propagateHierarchyIdToField(dailyPartitionChangeDistinctCountSince7Days, "daily_partition_change_distinct_count_since_7_days");
    }

    /**
     * Returns the distinct count value change since 30 days check specification.
     * @return Distinct count value change since 30 days check specification.
     */
    public ColumnChangeDistinctCountSince30DaysCheckSpec getDailyPartitionChangeDistinctCountSince30Days() {
        return dailyPartitionChangeDistinctCountSince30Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 30 days check.
     * @param dailyPartitionChangeDistinctCountSince30Days Distinct count value change since 30 days check specification.
     */
    public void setDailyPartitionChangeDistinctCountSince30Days(ColumnChangeDistinctCountSince30DaysCheckSpec dailyPartitionChangeDistinctCountSince30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionChangeDistinctCountSince30Days, dailyPartitionChangeDistinctCountSince30Days));
        this.dailyPartitionChangeDistinctCountSince30Days = dailyPartitionChangeDistinctCountSince30Days;
        propagateHierarchyIdToField(dailyPartitionChangeDistinctCountSince30Days, "daily_partition_change_distinct_count_since_30_days");
    }

    /**
     * Returns the distinct count value change since yesterday check specification.
     * @return Distinct count value change since yesterday check specification.
     */
    public ColumnChangeDistinctCountSinceYesterdayCheckSpec getDailyPartitionChangeDistinctCountSinceYesterday() {
        return dailyPartitionChangeDistinctCountSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count value change since yesterday check .
     * @param dailyPartitionChangeDistinctCountSinceYesterday Distinct count value change since yesterday check specification.
     */
    public void setDailyPartitionChangeDistinctCountSinceYesterday(ColumnChangeDistinctCountSinceYesterdayCheckSpec dailyPartitionChangeDistinctCountSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionChangeDistinctCountSinceYesterday, dailyPartitionChangeDistinctCountSinceYesterday));
        this.dailyPartitionChangeDistinctCountSinceYesterday = dailyPartitionChangeDistinctCountSinceYesterday;
        propagateHierarchyIdToField(dailyPartitionChangeDistinctCountSinceYesterday, "daily_partition_change_distinct_count_since_yesterday");
    }

    /**
     * Returns the distinct percent value change check specification.
     * @return Distinct percent value change check specification.
     */
    public ColumnChangeDistinctPercentCheckSpec getDailyPartitionChangeDistinctPercent() {
        return dailyPartitionChangeDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value change check.
     * @param dailyPartitionChangeDistinctPercent Distinct percent value change check specification.
     */
    public void setDailyPartitionChangeDistinctPercent(ColumnChangeDistinctPercentCheckSpec dailyPartitionChangeDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionChangeDistinctPercent, dailyPartitionChangeDistinctPercent));
        this.dailyPartitionChangeDistinctPercent = dailyPartitionChangeDistinctPercent;
        propagateHierarchyIdToField(dailyPartitionChangeDistinctPercent, "daily_partition_change_distinct_percent");
    }

    /**
     * Returns the distinct percent value change since 7 days check specification.
     * @return Distinct count percent change since 7 days check specification.
     */
    public ColumnChangeDistinctPercentSince7DaysCheckSpec getDailyPartitionChangeDistinctPercentSince7Days() {
        return dailyPartitionChangeDistinctPercentSince7Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 7 days check.
     * @param dailyPartitionChangeDistinctPercentSince7Days Distinct count percent change since 7 days check specification.
     */
    public void setDailyPartitionChangeDistinctPercentSince7Days(ColumnChangeDistinctPercentSince7DaysCheckSpec dailyPartitionChangeDistinctPercentSince7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionChangeDistinctPercentSince7Days, dailyPartitionChangeDistinctPercentSince7Days));
        this.dailyPartitionChangeDistinctPercentSince7Days = dailyPartitionChangeDistinctPercentSince7Days;
        propagateHierarchyIdToField(dailyPartitionChangeDistinctPercentSince7Days, "daily_partition_change_distinct_percent_since_7_days");
    }

    /**
     * Returns the distinct percent value change since 30 days check specification.
     * @return Distinct count percent change since 30 days check specification.
     */
    public ColumnChangeDistinctPercentSince30DaysCheckSpec getDailyPartitionChangeDistinctPercentSince30Days() {
        return dailyPartitionChangeDistinctPercentSince30Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 30 days check specification.
     * @param dailyPartitionChangeDistinctPercentSince30Days Distinct count percent change since 30 days check specification.
     */
    public void setDailyPartitionChangeDistinctPercentSince30Days(ColumnChangeDistinctPercentSince30DaysCheckSpec dailyPartitionChangeDistinctPercentSince30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionChangeDistinctPercentSince30Days, dailyPartitionChangeDistinctPercentSince30Days));
        this.dailyPartitionChangeDistinctPercentSince30Days = dailyPartitionChangeDistinctPercentSince30Days;
        propagateHierarchyIdToField(dailyPartitionChangeDistinctPercentSince30Days, "daily_partition_change_distinct_percent_since_30_days");
    }

    /**
     * Returns the distinct percent value change since yesterday check specification.
     * @return Distinct count percent change since yesterday check specification.
     */
    public ColumnChangeDistinctPercentSinceYesterdayCheckSpec getDailyPartitionChangeDistinctPercentSinceYesterday() {
        return dailyPartitionChangeDistinctPercentSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count percent change since yesterday check specification.
     * @param dailyPartitionChangeDistinctPercentSinceYesterday Distinct count percent change since yesterday check specification.
     */
    public void setDailyPartitionChangeDistinctPercentSinceYesterday(ColumnChangeDistinctPercentSinceYesterdayCheckSpec dailyPartitionChangeDistinctPercentSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionChangeDistinctPercentSinceYesterday, dailyPartitionChangeDistinctPercentSinceYesterday));
        this.dailyPartitionChangeDistinctPercentSinceYesterday = dailyPartitionChangeDistinctPercentSinceYesterday;
        propagateHierarchyIdToField(dailyPartitionChangeDistinctPercentSinceYesterday, "daily_partition_change_distinct_percent_since_yesterday");
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