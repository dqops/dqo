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
 * Container of uniqueness data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_distinct_count", o -> o.monthlyDistinctCount);
            put("monthly_distinct_percent", o -> o.monthlyDistinctPercent);
            put("monthly_duplicate_count", o -> o.monthlyDuplicateCount);
            put("monthly_duplicate_percent", o -> o.monthlyDuplicatePercent);

            put("monthly_anomaly_differencing_distinct_count_30_days", o -> o.monthlyAnomalyDifferencingDistinctCount30Days);
            put("monthly_anomaly_differencing_distinct_count", o -> o.monthlyAnomalyDifferencingDistinctCount);
            put("monthly_anomaly_stationary_distinct_percent_30_days", o -> o.monthlyAnomalyStationaryDistinctPercent30Days);
            put("monthly_anomaly_stationary_distinct_percent", o -> o.monthlyAnomalyStationaryDistinctPercent);
            put("monthly_change_distinct_count", o -> o.monthlyChangeDistinctCount);
            put("monthly_change_distinct_count_since_7_days", o -> o.monthlyChangeDistinctCountSince7Days);
            put("monthly_change_distinct_count_since_30_days", o -> o.monthlyChangeDistinctCountSince30Days);
            put("monthly_change_distinct_count_since_yesterday", o -> o.monthlyChangeDistinctCountSinceYesterday);
            put("monthly_change_distinct_percent", o -> o.monthlyChangeDistinctPercent);
            put("monthly_change_distinct_percent_since_7_days", o -> o.monthlyChangeDistinctPercentSince7Days);
            put("monthly_change_distinct_percent_since_30_days", o -> o.monthlyChangeDistinctPercentSince30Days);
            put("monthly_change_distinct_percent_since_yesterday", o -> o.monthlyChangeDistinctPercentSinceYesterday);

        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDistinctCountCheckSpec monthlyDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDistinctPercentCheckSpec monthlyDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDuplicateCountCheckSpec monthlyDuplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnDuplicatePercentCheckSpec monthlyDuplicatePercent;
    @JsonProperty("monthly_anomaly_differencing_distinct_count_30_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec monthlyAnomalyDifferencingDistinctCount30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyDifferencingDistinctCountCheckSpec monthlyAnomalyDifferencingDistinctCount;

    @JsonProperty("monthly_anomaly_stationary_distinct_percent_30_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec monthlyAnomalyStationaryDistinctPercent30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryDistinctPercentCheckSpec monthlyAnomalyStationaryDistinctPercent;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctCountCheckSpec monthlyChangeDistinctCount;

    @JsonProperty("monthly_change_distinct_count_since_7_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctCountSince7DaysCheckSpec monthlyChangeDistinctCountSince7Days;

    @JsonProperty("monthly_change_distinct_count_since_30_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctCountSince30DaysCheckSpec monthlyChangeDistinctCountSince30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctCountSinceYesterdayCheckSpec monthlyChangeDistinctCountSinceYesterday;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctPercentCheckSpec monthlyChangeDistinctPercent;

    @JsonProperty("monthly_change_distinct_percent_since_7_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctPercentSince7DaysCheckSpec monthlyChangeDistinctPercentSince7Days;

    @JsonProperty("monthly_change_distinct_percent_since_30_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctPercentSince30DaysCheckSpec monthlyChangeDistinctPercentSince30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctPercentSinceYesterdayCheckSpec monthlyChangeDistinctPercentSinceYesterday;


    /**
     * Returns a distinct values count check specification.
     * @return Distinct values count check specification.
     */
    public ColumnDistinctCountCheckSpec getMonthlyDistinctCount() {
        return monthlyDistinctCount;
    }

    /**
     * Sets a new specification of a distinct values count check.
     * @param monthlyDistinctCount Distinct values count check specification.
     */
    public void setMonthlyDistinctCount(ColumnDistinctCountCheckSpec monthlyDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyDistinctCount, monthlyDistinctCount));
        this.monthlyDistinctCount = monthlyDistinctCount;
        propagateHierarchyIdToField(monthlyDistinctCount, "monthly_distinct_count");
    }

    /**
     * Returns a distinct values percent check specification.
     * @return Distinct values percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getMonthlyDistinctPercent() {
        return monthlyDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct values percent check.
     * @param monthlyDistinctPercent Distinct values count percent specification.
     */
    public void setMonthlyDistinctPercent(ColumnDistinctPercentCheckSpec monthlyDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDistinctPercent, monthlyDistinctPercent));
        this.monthlyDistinctPercent = monthlyDistinctPercent;
        propagateHierarchyIdToField(monthlyDistinctPercent, "monthly_distinct_percent");
    }

    /**
     * Returns a duplicate values count check specification.
     * @return Duplicate values count check specification.
     */
    public ColumnDuplicateCountCheckSpec getMonthlyDuplicateCount() {
        return monthlyDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate values count check.
     * @param monthlyDuplicateCount Duplicate values count check specification.
     */
    public void setMonthlyDuplicateCount(ColumnDuplicateCountCheckSpec monthlyDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyDuplicateCount, monthlyDuplicateCount));
        this.monthlyDuplicateCount = monthlyDuplicateCount;
        propagateHierarchyIdToField(monthlyDuplicateCount, "monthly_duplicate_count");
    }

    /**
     * Returns a duplicate values percent check specification.
     * @return Duplicate values percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getMonthlyDuplicatePercent() {
        return monthlyDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate values percent check.
     * @param monthlyDuplicatePercent Duplicate values percent check specification.
     */
    public void setMonthlyDuplicatePercent(ColumnDuplicatePercentCheckSpec monthlyDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyDuplicatePercent, monthlyDuplicatePercent));
        this.monthlyDuplicatePercent = monthlyDuplicatePercent;
        propagateHierarchyIdToField(monthlyDuplicatePercent, "monthly_duplicate_percent");
    }


    /**
     * Returns a distinct count value anomaly 30 days check specification.
     * @return Distinct count value anomaly 30 days check specification.
     */
    public ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec getMonthlyAnomalyDifferencingDistinctCount30Days() {
        return monthlyAnomalyDifferencingDistinctCount30Days;
    }

    /**
     * Sets a new specification of a distinct count value anomaly 30 days check.
     * @param monthlyAnomalyDifferencingDistinctCount30Days Distinct count value anomaly 30 days check specification.
     */
    public void setMonthlyAnomalyDifferencingDistinctCount30Days(ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec monthlyAnomalyDifferencingDistinctCount30Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyAnomalyDifferencingDistinctCount30Days, monthlyAnomalyDifferencingDistinctCount30Days));
        this.monthlyAnomalyDifferencingDistinctCount30Days = monthlyAnomalyDifferencingDistinctCount30Days;
        propagateHierarchyIdToField(monthlyAnomalyDifferencingDistinctCount30Days, "monthly_anomaly_differencing_distinct_count_30_days");
    }

    /**
     * Returns a distinct count value anomaly check specification.
     * @return Distinct count value anomaly check specification.
     */
    public ColumnAnomalyDifferencingDistinctCountCheckSpec getMonthlyAnomalyDifferencingDistinctCount() {
        return monthlyAnomalyDifferencingDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value anomaly check.
     * @param monthlyAnomalyDifferencingDistinctCount Distinct count value anomaly check specification.
     */
    public void setMonthlyAnomalyDifferencingDistinctCount(ColumnAnomalyDifferencingDistinctCountCheckSpec monthlyAnomalyDifferencingDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyAnomalyDifferencingDistinctCount, monthlyAnomalyDifferencingDistinctCount));
        this.monthlyAnomalyDifferencingDistinctCount = monthlyAnomalyDifferencingDistinctCount;
        propagateHierarchyIdToField(monthlyAnomalyDifferencingDistinctCount, "monthly_anomaly_differencing_distinct_count");
    }

    /**
     * Returns a distinct percent value anomaly 30 days check specification.
     * @return Distinct percent value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec getMonthlyAnomalyStationaryDistinctPercent30Days() {
        return monthlyAnomalyStationaryDistinctPercent30Days;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly 30 days check.
     * @param monthlyAnomalyStationaryDistinctPercent30Days Distinct percent value anomaly 30 days check specification.
     */
    public void setMonthlyAnomalyStationaryDistinctPercent30Days(ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec monthlyAnomalyStationaryDistinctPercent30Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyAnomalyStationaryDistinctPercent30Days, monthlyAnomalyStationaryDistinctPercent30Days));
        this.monthlyAnomalyStationaryDistinctPercent30Days = monthlyAnomalyStationaryDistinctPercent30Days;
        propagateHierarchyIdToField(monthlyAnomalyStationaryDistinctPercent30Days, "monthly_anomaly_stationary_distinct_percent_30_days");
    }

    /**
     * Returns a distinct percent value anomaly check specification.
     * @return Distinct percent value anomaly check specification.
     */
    public ColumnAnomalyStationaryDistinctPercentCheckSpec getMonthlyAnomalyStationaryDistinctPercent() {
        return monthlyAnomalyStationaryDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly check.
     * @param monthlyAnomalyStationaryDistinctPercent Distinct percent value anomaly check specification.
     */
    public void setMonthlyAnomalyStationaryDistinctPercent(ColumnAnomalyStationaryDistinctPercentCheckSpec monthlyAnomalyStationaryDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyAnomalyStationaryDistinctPercent, monthlyAnomalyStationaryDistinctPercent));
        this.monthlyAnomalyStationaryDistinctPercent = monthlyAnomalyStationaryDistinctPercent;
        propagateHierarchyIdToField(monthlyAnomalyStationaryDistinctPercent, "monthly_anomaly_stationary_distinct_percent");
    }

    /**
     * Returns the distinct count value change check specification.
     * @return Distinct count value change check specification.
     */
    public ColumnChangeDistinctCountCheckSpec getMonthlyChangeDistinctCount() {
        return monthlyChangeDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value change check.
     * @param monthlyChangeDistinctCount Distinct count value change check specification.
     */
    public void setMonthlyChangeDistinctCount(ColumnChangeDistinctCountCheckSpec monthlyChangeDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyChangeDistinctCount, monthlyChangeDistinctCount));
        this.monthlyChangeDistinctCount = monthlyChangeDistinctCount;
        propagateHierarchyIdToField(monthlyChangeDistinctCount, "monthly_change_distinct_count");
    }

    /**
     * Returns the distinct count value change since 7 days check specification.
     * @return Distinct count value change since 7 days check specification.
     */
    public ColumnChangeDistinctCountSince7DaysCheckSpec getMonthlyChangeDistinctCountSince7Days() {
        return monthlyChangeDistinctCountSince7Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 7 days check.
     * @param monthlyChangeDistinctCountSince7Days Distinct count value change since 7 days check specification.
     */
    public void setMonthlyChangeDistinctCountSince7Days(ColumnChangeDistinctCountSince7DaysCheckSpec monthlyChangeDistinctCountSince7Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyChangeDistinctCountSince7Days, monthlyChangeDistinctCountSince7Days));
        this.monthlyChangeDistinctCountSince7Days = monthlyChangeDistinctCountSince7Days;
        propagateHierarchyIdToField(monthlyChangeDistinctCountSince7Days, "monthly_change_distinct_count_since_7_days");
    }

    /**
     * Returns the distinct count value change since 30 days check specification.
     * @return Distinct count value change since 30 days check specification.
     */
    public ColumnChangeDistinctCountSince30DaysCheckSpec getMonthlyChangeDistinctCountSince30Days() {
        return monthlyChangeDistinctCountSince30Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 30 days check.
     * @param monthlyChangeDistinctCountSince30Days Distinct count value change since 30 days check specification.
     */
    public void setMonthlyChangeDistinctCountSince30Days(ColumnChangeDistinctCountSince30DaysCheckSpec monthlyChangeDistinctCountSince30Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyChangeDistinctCountSince30Days, monthlyChangeDistinctCountSince30Days));
        this.monthlyChangeDistinctCountSince30Days = monthlyChangeDistinctCountSince30Days;
        propagateHierarchyIdToField(monthlyChangeDistinctCountSince30Days, "monthly_change_distinct_count_since_30_days");
    }

    /**
     * Returns the distinct count value change since yesterday check specification.
     * @return Distinct count value change since yesterday check specification.
     */
    public ColumnChangeDistinctCountSinceYesterdayCheckSpec getMonthlyChangeDistinctCountSinceYesterday() {
        return monthlyChangeDistinctCountSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count value change since yesterday check .
     * @param monthlyChangeDistinctCountSinceYesterday Distinct count value change since yesterday check specification.
     */
    public void setMonthlyChangeDistinctCountSinceYesterday(ColumnChangeDistinctCountSinceYesterdayCheckSpec monthlyChangeDistinctCountSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.monthlyChangeDistinctCountSinceYesterday, monthlyChangeDistinctCountSinceYesterday));
        this.monthlyChangeDistinctCountSinceYesterday = monthlyChangeDistinctCountSinceYesterday;
        propagateHierarchyIdToField(monthlyChangeDistinctCountSinceYesterday, "monthly_change_distinct_count_since_yesterday");
    }

    /**
     * Returns the distinct percent value change check specification.
     * @return Distinct percent value change check specification.
     */
    public ColumnChangeDistinctPercentCheckSpec getMonthlyChangeDistinctPercent() {
        return monthlyChangeDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value change check.
     * @param monthlyChangeDistinctPercent Distinct percent value change check specification.
     */
    public void setMonthlyChangeDistinctPercent(ColumnChangeDistinctPercentCheckSpec monthlyChangeDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyChangeDistinctPercent, monthlyChangeDistinctPercent));
        this.monthlyChangeDistinctPercent = monthlyChangeDistinctPercent;
        propagateHierarchyIdToField(monthlyChangeDistinctPercent, "monthly_change_distinct_percent");
    }

    /**
     * Returns the distinct percent value change since 7 days check specification.
     * @return Distinct count percent change since 7 days check specification.
     */
    public ColumnChangeDistinctPercentSince7DaysCheckSpec getMonthlyChangeDistinctPercentSince7Days() {
        return monthlyChangeDistinctPercentSince7Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 7 days check.
     * @param monthlyChangeDistinctPercentSince7Days Distinct count percent change since 7 days check specification.
     */
    public void setMonthlyChangeDistinctPercentSince7Days(ColumnChangeDistinctPercentSince7DaysCheckSpec monthlyChangeDistinctPercentSince7Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyChangeDistinctPercentSince7Days, monthlyChangeDistinctPercentSince7Days));
        this.monthlyChangeDistinctPercentSince7Days = monthlyChangeDistinctPercentSince7Days;
        propagateHierarchyIdToField(monthlyChangeDistinctPercentSince7Days, "monthly_change_distinct_percent_since_7_days");
    }

    /**
     * Returns the distinct percent value change since 30 days check specification.
     * @return Distinct count percent change since 30 days check specification.
     */
    public ColumnChangeDistinctPercentSince30DaysCheckSpec getMonthlyChangeDistinctPercentSince30Days() {
        return monthlyChangeDistinctPercentSince30Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 30 days check specification.
     * @param monthlyChangeDistinctPercentSince30Days Distinct count percent change since 30 days check specification.
     */
    public void setMonthlyChangeDistinctPercentSince30Days(ColumnChangeDistinctPercentSince30DaysCheckSpec monthlyChangeDistinctPercentSince30Days) {
        this.setDirtyIf(!Objects.equals(this.monthlyChangeDistinctPercentSince30Days, monthlyChangeDistinctPercentSince30Days));
        this.monthlyChangeDistinctPercentSince30Days = monthlyChangeDistinctPercentSince30Days;
        propagateHierarchyIdToField(monthlyChangeDistinctPercentSince30Days, "monthly_change_distinct_percent_since_30_days");
    }

    /**
     * Returns the distinct percent value change since yesterday check specification.
     * @return Distinct count percent change since yesterday check specification.
     */
    public ColumnChangeDistinctPercentSinceYesterdayCheckSpec getMonthlyChangeDistinctPercentSinceYesterday() {
        return monthlyChangeDistinctPercentSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count percent change since yesterday check specification.
     * @param monthlyChangeDistinctPercentSinceYesterday Distinct count percent change since yesterday check specification.
     */
    public void setMonthlyChangeDistinctPercentSinceYesterday(ColumnChangeDistinctPercentSinceYesterdayCheckSpec monthlyChangeDistinctPercentSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.monthlyChangeDistinctPercentSinceYesterday, monthlyChangeDistinctPercentSinceYesterday));
        this.monthlyChangeDistinctPercentSinceYesterday = monthlyChangeDistinctPercentSinceYesterday;
        propagateHierarchyIdToField(monthlyChangeDistinctPercentSinceYesterday, "monthly_change_distinct_percent_since_yesterday");
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
    public ColumnUniquenessMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnUniquenessMonthlyMonitoringChecksSpec)super.deepClone();
    }
}