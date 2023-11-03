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
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.uniqueness.*;
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
 * Container of uniqueness data quality partitioned checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_distinct_count", o -> o.monthlyPartitionDistinctCount);
            put("monthly_partition_distinct_percent", o -> o.monthlyPartitionDistinctPercent);
            put("monthly_partition_duplicate_count", o -> o.monthlyPartitionDuplicateCount);
            put("monthly_partition_duplicate_percent", o -> o.monthlyPartitionDuplicatePercent);

            put("monthly_partition_anomaly_stationary_distinct_count_30_days", o -> o.monthlyPartitionAnomalyStationaryDistinctCount30Days);
            put("monthly_partition_anomaly_stationary_distinct_count", o -> o.monthlyPartitionAnomalyStationaryDistinctCount);
            put("monthly_partition_anomaly_stationary_distinct_percent_30_days", o -> o.monthlyPartitionAnomalyStationaryDistinctPercent30Days);
            put("monthly_partition_anomaly_stationary_distinct_percent", o -> o.monthlyPartitionAnomalyStationaryDistinctPercent);
            put("monthly_partition_change_distinct_count", o -> o.monthlyPartitionChangeDistinctCount);
            put("monthly_partition_change_distinct_count_since_7_days", o -> o.monthlyPartitionChangeDistinctCountSince7Days);
            put("monthly_partition_change_distinct_count_since_30_days", o -> o.monthlyPartitionChangeDistinctCountSince30Days);
            put("monthly_partition_change_distinct_count_since_yesterday", o -> o.monthlyPartitionChangeDistinctCountSinceYesterday);
            put("monthly_partition_change_distinct_percent", o -> o.monthlyPartitionChangeDistinctPercent);
            put("monthly_partition_change_distinct_percent_since_7_days", o -> o.monthlyPartitionChangeDistinctPercentSince7Days);
            put("monthly_partition_change_distinct_percent_since_30_days", o -> o.monthlyPartitionChangeDistinctPercentSince30Days);
            put("monthly_partition_change_distinct_percent_since_yesterday", o -> o.monthlyPartitionChangeDistinctPercentSinceYesterday);

        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDistinctCountCheckSpec monthlyPartitionDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDistinctPercentCheckSpec monthlyPartitionDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDuplicateCountCheckSpec monthlyPartitionDuplicateCount;

    @JsonPropertyDescription("Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnDuplicatePercentCheckSpec monthlyPartitionDuplicatePercent;

    @JsonProperty("monthly_partition_anomaly_stationary_distinct_count_30_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec monthlyPartitionAnomalyStationaryDistinctCount30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryPartitionDistinctCountCheckSpec monthlyPartitionAnomalyStationaryDistinctCount;

    @JsonProperty("monthly_partition_anomaly_stationary_distinct_percent_30_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec monthlyPartitionAnomalyStationaryDistinctPercent30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryDistinctPercentCheckSpec monthlyPartitionAnomalyStationaryDistinctPercent;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctCountCheckSpec monthlyPartitionChangeDistinctCount;

    @JsonProperty("monthly_partition_change_distinct_count_since_7_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctCountSince7DaysCheckSpec monthlyPartitionChangeDistinctCountSince7Days;

    @JsonProperty("monthly_partition_change_distinct_count_since_30_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctCountSince30DaysCheckSpec monthlyPartitionChangeDistinctCountSince30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctCountSinceYesterdayCheckSpec monthlyPartitionChangeDistinctCountSinceYesterday;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctPercentCheckSpec monthlyPartitionChangeDistinctPercent;

    @JsonProperty("monthly_partition_change_distinct_percent_since_7_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctPercentSince7DaysCheckSpec monthlyPartitionChangeDistinctPercentSince7Days;

    @JsonProperty("monthly_partition_change_distinct_percent_since_30_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctPercentSince30DaysCheckSpec monthlyPartitionChangeDistinctPercentSince30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctPercentSinceYesterdayCheckSpec monthlyPartitionChangeDistinctPercentSinceYesterday;

    /**
     * Returns a distinct values count check specification.
     * @return Distinct values count check specification.
     */
    public ColumnDistinctCountCheckSpec getMonthlyPartitionDistinctCount() {
        return monthlyPartitionDistinctCount;
    }

    /**
     * Sets a new specification of a distinct values count check.
     * @param monthlyPartitionDistinctCount Distinct values count check specification.
     */
    public void setMonthlyPartitionDistinctCount(ColumnDistinctCountCheckSpec monthlyPartitionDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDistinctCount, monthlyPartitionDistinctCount));
        this.monthlyPartitionDistinctCount = monthlyPartitionDistinctCount;
        propagateHierarchyIdToField(monthlyPartitionDistinctCount, "monthly_partition_distinct_count");
    }

    /**
     * Returns a distinct values percent check specification.
     * @return Distinct values percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getMonthlyPartitionDistinctPercent() {
        return monthlyPartitionDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct values percent check.
     * @param monthlyPartitionDistinctPercent Distinct values percent check specification.
     */
    public void setMonthlyPartitionDistinctPercent(ColumnDistinctPercentCheckSpec monthlyPartitionDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDistinctPercent, monthlyPartitionDistinctPercent));
        this.monthlyPartitionDistinctPercent = monthlyPartitionDistinctPercent;
        propagateHierarchyIdToField(monthlyPartitionDistinctPercent, "monthly_partition_distinct_percent");
    }

    /**
     * Returns a duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getMonthlyPartitionDuplicateCount() {
        return monthlyPartitionDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param monthlyPartitionDuplicateCount Duplicate values count check specification.
     */
    public void setMonthlyPartitionDuplicateCount(ColumnDuplicateCountCheckSpec monthlyPartitionDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDuplicateCount, monthlyPartitionDuplicateCount));
        this.monthlyPartitionDuplicateCount = monthlyPartitionDuplicateCount;
        propagateHierarchyIdToField(monthlyPartitionDuplicateCount, "monthly_partition_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getMonthlyPartitionDuplicatePercent() {
        return monthlyPartitionDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param monthlyPartitionDuplicatePercent Duplicate values percent check specification.
     */
    public void setMonthlyPartitionDuplicatePercent(ColumnDuplicatePercentCheckSpec monthlyPartitionDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDuplicatePercent, monthlyPartitionDuplicatePercent));
        this.monthlyPartitionDuplicatePercent = monthlyPartitionDuplicatePercent;
        propagateHierarchyIdToField(monthlyPartitionDuplicatePercent, "monthly_partition_duplicate_percent");
    }


    /**
     * Returns a distinct count value anomaly 30 days check specification.
     * @return Distinct count value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec getMonthlyPartitionAnomalyStationaryDistinctCount30Days() {
        return monthlyPartitionAnomalyStationaryDistinctCount30Days;
    }

    /**
     * Sets a new specification of a distinct count value anomaly 30 days check.
     * @param monthlyPartitionAnomalyStationaryDistinctCount30Days Distinct count value anomaly 30 days check specification.
     */
    public void setMonthlyPartitionAnomalyStationaryDistinctCount30Days(ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec monthlyPartitionAnomalyStationaryDistinctCount30Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionAnomalyStationaryDistinctCount30Days, monthlyPartitionAnomalyStationaryDistinctCount30Days));
        this.monthlyPartitionAnomalyStationaryDistinctCount30Days = monthlyPartitionAnomalyStationaryDistinctCount30Days;
        propagateHierarchyIdToField(monthlyPartitionAnomalyStationaryDistinctCount30Days, "monthly_partition_anomaly_stationary_distinct_count_30_days");
    }

    /**
     * Returns a distinct count value anomaly check specification.
     * @return Distinct count value anomaly check specification.
     */
    public ColumnAnomalyStationaryPartitionDistinctCountCheckSpec getMonthlyPartitionAnomalyStationaryDistinctCount() {
        return monthlyPartitionAnomalyStationaryDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value anomaly check.
     * @param monthlyPartitionAnomalyStationaryDistinctCount Distinct count value anomaly check specification.
     */
    public void setMonthlyPartitionAnomalyStationaryDistinctCount(ColumnAnomalyStationaryPartitionDistinctCountCheckSpec monthlyPartitionAnomalyStationaryDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionAnomalyStationaryDistinctCount, monthlyPartitionAnomalyStationaryDistinctCount));
        this.monthlyPartitionAnomalyStationaryDistinctCount = monthlyPartitionAnomalyStationaryDistinctCount;
        propagateHierarchyIdToField(monthlyPartitionAnomalyStationaryDistinctCount, "monthly_partition_anomaly_stationary_distinct_count");
    }

    /**
     * Returns a distinct percent value anomaly 30 days check specification.
     * @return Distinct percent value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec getMonthlyPartitionAnomalyStationaryDistinctPercent30Days() {
        return monthlyPartitionAnomalyStationaryDistinctPercent30Days;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly 30 days check.
     * @param monthlyPartitionAnomalyStationaryDistinctPercent30Days Distinct percent value anomaly 30 days check specification.
     */
    public void setMonthlyPartitionAnomalyStationaryDistinctPercent30Days(ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec monthlyPartitionAnomalyStationaryDistinctPercent30Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionAnomalyStationaryDistinctPercent30Days, monthlyPartitionAnomalyStationaryDistinctPercent30Days));
        this.monthlyPartitionAnomalyStationaryDistinctPercent30Days = monthlyPartitionAnomalyStationaryDistinctPercent30Days;
        propagateHierarchyIdToField(monthlyPartitionAnomalyStationaryDistinctPercent30Days, "monthly_partition_anomaly_stationary_distinct_percent_30_days");
    }

    /**
     * Returns a distinct percent value anomaly check specification.
     * @return Distinct percent value anomaly check specification.
     */
    public ColumnAnomalyStationaryDistinctPercentCheckSpec getMonthlyPartitionAnomalyStationaryDistinctPercent() {
        return monthlyPartitionAnomalyStationaryDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly check.
     * @param monthlyPartitionAnomalyStationaryDistinctPercent Distinct percent value anomaly check specification.
     */
    public void setMonthlyPartitionAnomalyStationaryDistinctPercent(ColumnAnomalyStationaryDistinctPercentCheckSpec monthlyPartitionAnomalyStationaryDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionAnomalyStationaryDistinctPercent, monthlyPartitionAnomalyStationaryDistinctPercent));
        this.monthlyPartitionAnomalyStationaryDistinctPercent = monthlyPartitionAnomalyStationaryDistinctPercent;
        propagateHierarchyIdToField(monthlyPartitionAnomalyStationaryDistinctPercent, "monthly_partition_anomaly_stationary_distinct_percent");
    }

    /**
     * Returns the distinct count value change check specification.
     * @return Distinct count value change check specification.
     */
    public ColumnChangeDistinctCountCheckSpec getMonthlyPartitionChangeDistinctCount() {
        return monthlyPartitionChangeDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value change check.
     * @param monthlyPartitionChangeDistinctCount Distinct count value change check specification.
     */
    public void setMonthlyPartitionChangeDistinctCount(ColumnChangeDistinctCountCheckSpec monthlyPartitionChangeDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionChangeDistinctCount, monthlyPartitionChangeDistinctCount));
        this.monthlyPartitionChangeDistinctCount = monthlyPartitionChangeDistinctCount;
        propagateHierarchyIdToField(monthlyPartitionChangeDistinctCount, "monthly_partition_change_distinct_count");
    }

    /**
     * Returns the distinct count value change since 7 days check specification.
     * @return Distinct count value change since 7 days check specification.
     */
    public ColumnChangeDistinctCountSince7DaysCheckSpec getMonthlyPartitionChangeDistinctCountSince7Days() {
        return monthlyPartitionChangeDistinctCountSince7Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 7 days check.
     * @param monthlyPartitionChangeDistinctCountSince7Days Distinct count value change since 7 days check specification.
     */
    public void setMonthlyPartitionChangeDistinctCountSince7Days(ColumnChangeDistinctCountSince7DaysCheckSpec monthlyPartitionChangeDistinctCountSince7Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionChangeDistinctCountSince7Days, monthlyPartitionChangeDistinctCountSince7Days));
        this.monthlyPartitionChangeDistinctCountSince7Days = monthlyPartitionChangeDistinctCountSince7Days;
        propagateHierarchyIdToField(monthlyPartitionChangeDistinctCountSince7Days, "monthly_partition_change_distinct_count_since_7_days");
    }

    /**
     * Returns the distinct count value change since 30 days check specification.
     * @return Distinct count value change since 30 days check specification.
     */
    public ColumnChangeDistinctCountSince30DaysCheckSpec getMonthlyPartitionChangeDistinctCountSince30Days() {
        return monthlyPartitionChangeDistinctCountSince30Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 30 days check.
     * @param monthlyPartitionChangeDistinctCountSince30Days Distinct count value change since 30 days check specification.
     */
    public void setMonthlyPartitionChangeDistinctCountSince30Days(ColumnChangeDistinctCountSince30DaysCheckSpec monthlyPartitionChangeDistinctCountSince30Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionChangeDistinctCountSince30Days, monthlyPartitionChangeDistinctCountSince30Days));
        this.monthlyPartitionChangeDistinctCountSince30Days = monthlyPartitionChangeDistinctCountSince30Days;
        propagateHierarchyIdToField(monthlyPartitionChangeDistinctCountSince30Days, "monthly_partition_change_distinct_count_since_30_days");
    }

    /**
     * Returns the distinct count value change since yesterday check specification.
     * @return Distinct count value change since yesterday check specification.
     */
    public ColumnChangeDistinctCountSinceYesterdayCheckSpec getMonthlyPartitionChangeDistinctCountSinceYesterday() {
        return monthlyPartitionChangeDistinctCountSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count value change since yesterday check .
     * @param monthlyPartitionChangeDistinctCountSinceYesterday Distinct count value change since yesterday check specification.
     */
    public void setMonthlyPartitionChangeDistinctCountSinceYesterday(ColumnChangeDistinctCountSinceYesterdayCheckSpec monthlyPartitionChangeDistinctCountSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionChangeDistinctCountSinceYesterday, monthlyPartitionChangeDistinctCountSinceYesterday));
        this.monthlyPartitionChangeDistinctCountSinceYesterday = monthlyPartitionChangeDistinctCountSinceYesterday;
        propagateHierarchyIdToField(monthlyPartitionChangeDistinctCountSinceYesterday, "monthly_partition_change_distinct_count_since_yesterday");
    }

    /**
     * Returns the distinct percent value change check specification.
     * @return Distinct percent value change check specification.
     */
    public ColumnChangeDistinctPercentCheckSpec getMonthlyPartitionChangeDistinctPercent() {
        return monthlyPartitionChangeDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value change check.
     * @param monthlyPartitionChangeDistinctPercent Distinct percent value change check specification.
     */
    public void setMonthlyPartitionChangeDistinctPercent(ColumnChangeDistinctPercentCheckSpec monthlyPartitionChangeDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionChangeDistinctPercent, monthlyPartitionChangeDistinctPercent));
        this.monthlyPartitionChangeDistinctPercent = monthlyPartitionChangeDistinctPercent;
        propagateHierarchyIdToField(monthlyPartitionChangeDistinctPercent, "monthly_partition_change_distinct_percent");
    }

    /**
     * Returns the distinct percent value change since 7 days check specification.
     * @return Distinct count percent change since 7 days check specification.
     */
    public ColumnChangeDistinctPercentSince7DaysCheckSpec getMonthlyPartitionChangeDistinctPercentSince7Days() {
        return monthlyPartitionChangeDistinctPercentSince7Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 7 days check.
     * @param monthlyPartitionChangeDistinctPercentSince7Days Distinct count percent change since 7 days check specification.
     */
    public void setMonthlyPartitionChangeDistinctPercentSince7Days(ColumnChangeDistinctPercentSince7DaysCheckSpec monthlyPartitionChangeDistinctPercentSince7Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionChangeDistinctPercentSince7Days, monthlyPartitionChangeDistinctPercentSince7Days));
        this.monthlyPartitionChangeDistinctPercentSince7Days = monthlyPartitionChangeDistinctPercentSince7Days;
        propagateHierarchyIdToField(monthlyPartitionChangeDistinctPercentSince7Days, "monthly_partition_change_distinct_percent_since_7_days");
    }

    /**
     * Returns the distinct percent value change since 30 days check specification.
     * @return Distinct count percent change since 30 days check specification.
     */
    public ColumnChangeDistinctPercentSince30DaysCheckSpec getMonthlyPartitionChangeDistinctPercentSince30Days() {
        return monthlyPartitionChangeDistinctPercentSince30Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 30 days check specification.
     * @param monthlyPartitionChangeDistinctPercentSince30Days Distinct count percent change since 30 days check specification.
     */
    public void setMonthlyPartitionChangeDistinctPercentSince30Days(ColumnChangeDistinctPercentSince30DaysCheckSpec monthlyPartitionChangeDistinctPercentSince30Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionChangeDistinctPercentSince30Days, monthlyPartitionChangeDistinctPercentSince30Days));
        this.monthlyPartitionChangeDistinctPercentSince30Days = monthlyPartitionChangeDistinctPercentSince30Days;
        propagateHierarchyIdToField(monthlyPartitionChangeDistinctPercentSince30Days, "monthly_partition_change_distinct_percent_since_30_days");
    }

    /**
     * Returns the distinct percent value change since yesterday check specification.
     * @return Distinct count percent change since yesterday check specification.
     */
    public ColumnChangeDistinctPercentSinceYesterdayCheckSpec getMonthlyPartitionChangeDistinctPercentSinceYesterday() {
        return monthlyPartitionChangeDistinctPercentSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count percent change since yesterday check specification.
     * @param monthlyPartitionChangeDistinctPercentSinceYesterday Distinct count percent change since yesterday check specification.
     */
    public void setMonthlyPartitionChangeDistinctPercentSinceYesterday(ColumnChangeDistinctPercentSinceYesterdayCheckSpec monthlyPartitionChangeDistinctPercentSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionChangeDistinctPercentSinceYesterday, monthlyPartitionChangeDistinctPercentSinceYesterday));
        this.monthlyPartitionChangeDistinctPercentSinceYesterday = monthlyPartitionChangeDistinctPercentSinceYesterday;
        propagateHierarchyIdToField(monthlyPartitionChangeDistinctPercentSinceYesterday, "monthly_partition_change_distinct_percent_since_yesterday");
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
        return CheckTimeScale.monthly;
    }
}