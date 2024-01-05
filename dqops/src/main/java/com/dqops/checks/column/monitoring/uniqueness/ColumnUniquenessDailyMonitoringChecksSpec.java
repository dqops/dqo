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

            put("daily_distinct_count_anomaly", o -> o.dailyDistinctCountAnomaly);
            put("daily_distinct_percent_anomaly", o -> o.dailyDistinctPercentAnomaly);

            put("daily_distinct_count_change", o -> o.dailyDistinctCountChange);
            put("daily_distinct_count_change_1_day", o -> o.dailyDistinctCountChange1Day);
            put("daily_distinct_count_change_7_days", o -> o.dailyDistinctCountChange7Days);
            put("daily_distinct_count_change_30_days", o -> o.dailyDistinctCountChange30Days);
            put("daily_distinct_percent_change", o -> o.dailyDistinctPercentChange);
            put("daily_distinct_percent_change_1_day", o -> o.dailyDistinctPercentChange1Day);
            put("daily_distinct_percent_change_7_days", o -> o.dailyDistinctPercentChange7Days);
            put("daily_distinct_percent_change_30_days", o -> o.dailyDistinctPercentChange30Days);
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

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnDistinctCountAnomalyDifferencingCheckSpec dailyDistinctCountAnomaly;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnDistinctPercentAnomalyStationaryCheckSpec dailyDistinctPercentAnomaly;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnDistinctCountChangeCheckSpec dailyDistinctCountChange;

    @JsonProperty("daily_distinct_count_change_1_day")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnDistinctCountChange1DayCheckSpec dailyDistinctCountChange1Day;

    @JsonProperty("daily_distinct_count_change_7_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnDistinctCountChange7DaysCheckSpec dailyDistinctCountChange7Days;

    @JsonProperty("daily_distinct_count_change_30_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnDistinctCountChange30DaysCheckSpec dailyDistinctCountChange30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnDistinctPercentChangeCheckSpec dailyDistinctPercentChange;

    @JsonProperty("daily_distinct_percent_change_1_day")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnDistinctPercentChange1DayCheckSpec dailyDistinctPercentChange1Day;

    @JsonProperty("daily_distinct_percent_change_7_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnDistinctPercentChange7DaysCheckSpec dailyDistinctPercentChange7Days;

    @JsonProperty("daily_distinct_percent_change_30_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnDistinctPercentChange30DaysCheckSpec dailyDistinctPercentChange30Days;


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
     * Returns a distinct count value anomaly check specification.
     * @return Distinct count value anomaly check specification.
     */
    public ColumnDistinctCountAnomalyDifferencingCheckSpec getDailyDistinctCountAnomaly() {
        return dailyDistinctCountAnomaly;
    }

    /**
     * Sets a new specification of a distinct count value anomaly check.
     * @param dailyDistinctCountAnomaly Distinct count value anomaly check specification.
     */
    public void setDailyDistinctCountAnomaly(ColumnDistinctCountAnomalyDifferencingCheckSpec dailyDistinctCountAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctCountAnomaly, dailyDistinctCountAnomaly));
        this.dailyDistinctCountAnomaly = dailyDistinctCountAnomaly;
        propagateHierarchyIdToField(dailyDistinctCountAnomaly, "daily_distinct_count_anomaly");
    }

    /**
     * Returns a distinct percent value anomaly check specification.
     * @return Distinct percent value anomaly check specification.
     */
    public ColumnDistinctPercentAnomalyStationaryCheckSpec getDailyDistinctPercentAnomaly() {
        return dailyDistinctPercentAnomaly;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly check.
     * @param dailyDistinctPercentAnomaly Distinct percent value anomaly check specification.
     */
    public void setDailyDistinctPercentAnomaly(ColumnDistinctPercentAnomalyStationaryCheckSpec dailyDistinctPercentAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctPercentAnomaly, dailyDistinctPercentAnomaly));
        this.dailyDistinctPercentAnomaly = dailyDistinctPercentAnomaly;
        propagateHierarchyIdToField(dailyDistinctPercentAnomaly, "daily_distinct_percent_anomaly");
    }

    /**
     * Returns the distinct count value change check specification.
     * @return Distinct count value change check specification.
     */
    public ColumnDistinctCountChangeCheckSpec getDailyDistinctCountChange() {
        return dailyDistinctCountChange;
    }

    /**
     * Sets a new specification of a distinct count value change check.
     * @param dailyDistinctCountChange Distinct count value change check specification.
     */
    public void setDailyDistinctCountChange(ColumnDistinctCountChangeCheckSpec dailyDistinctCountChange) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctCountChange, dailyDistinctCountChange));
        this.dailyDistinctCountChange = dailyDistinctCountChange;
        propagateHierarchyIdToField(dailyDistinctCountChange, "daily_distinct_count_change");
    }

    /**
     * Returns the distinct count value change since yesterday check specification.
     * @return Distinct count value change since yesterday check specification.
     */
    public ColumnDistinctCountChange1DayCheckSpec getDailyDistinctCountChange1Day() {
        return dailyDistinctCountChange1Day;
    }

    /**
     * Sets a new specification of a distinct count value change since yesterday check .
     * @param dailyDistinctCountChange1Day Distinct count value change since yesterday check specification.
     */
    public void setDailyDistinctCountChange1Day(ColumnDistinctCountChange1DayCheckSpec dailyDistinctCountChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctCountChange1Day, dailyDistinctCountChange1Day));
        this.dailyDistinctCountChange1Day = dailyDistinctCountChange1Day;
        propagateHierarchyIdToField(dailyDistinctCountChange1Day, "daily_distinct_count_change_1_day");
    }

    /**
     * Returns the distinct count value change since 7 days check specification.
     * @return Distinct count value change since 7 days check specification.
     */
    public ColumnDistinctCountChange7DaysCheckSpec getDailyDistinctCountChange7Days() {
        return dailyDistinctCountChange7Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 7 days check.
     * @param dailyDistinctCountChange7Days Distinct count value change since 7 days check specification.
     */
    public void setDailyDistinctCountChange7Days(ColumnDistinctCountChange7DaysCheckSpec dailyDistinctCountChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctCountChange7Days, dailyDistinctCountChange7Days));
        this.dailyDistinctCountChange7Days = dailyDistinctCountChange7Days;
        propagateHierarchyIdToField(dailyDistinctCountChange7Days, "daily_distinct_count_change_7_days");
    }

    /**
     * Returns the distinct count value change since 30 days check specification.
     * @return Distinct count value change since 30 days check specification.
     */
    public ColumnDistinctCountChange30DaysCheckSpec getDailyDistinctCountChange30Days() {
        return dailyDistinctCountChange30Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 30 days check.
     * @param dailyDistinctCountChange30Days Distinct count value change since 30 days check specification.
     */
    public void setDailyDistinctCountChange30Days(ColumnDistinctCountChange30DaysCheckSpec dailyDistinctCountChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctCountChange30Days, dailyDistinctCountChange30Days));
        this.dailyDistinctCountChange30Days = dailyDistinctCountChange30Days;
        propagateHierarchyIdToField(dailyDistinctCountChange30Days, "daily_distinct_count_change_30_days");
    }

    /**
     * Returns the distinct percent value change check specification.
     * @return Distinct percent value change check specification.
     */
    public ColumnDistinctPercentChangeCheckSpec getDailyDistinctPercentChange() {
        return dailyDistinctPercentChange;
    }

    /**
     * Sets a new specification of a distinct percent value change check.
     * @param dailyDistinctPercentChange Distinct percent value change check specification.
     */
    public void setDailyDistinctPercentChange(ColumnDistinctPercentChangeCheckSpec dailyDistinctPercentChange) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctPercentChange, dailyDistinctPercentChange));
        this.dailyDistinctPercentChange = dailyDistinctPercentChange;
        propagateHierarchyIdToField(dailyDistinctPercentChange, "daily_distinct_percent_change");
    }

    /**
     * Returns the distinct percent value change since yesterday check specification.
     * @return Distinct count percent change since yesterday check specification.
     */
    public ColumnDistinctPercentChange1DayCheckSpec getDailyDistinctPercentChange1Day() {
        return dailyDistinctPercentChange1Day;
    }

    /**
     * Sets a new specification of a distinct count percent change since yesterday check specification.
     * @param dailyDistinctPercentChange1Day Distinct count percent change since yesterday check specification.
     */
    public void setDailyDistinctPercentChange1Day(ColumnDistinctPercentChange1DayCheckSpec dailyDistinctPercentChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctPercentChange1Day, dailyDistinctPercentChange1Day));
        this.dailyDistinctPercentChange1Day = dailyDistinctPercentChange1Day;
        propagateHierarchyIdToField(dailyDistinctPercentChange1Day, "daily_distinct_percent_change_1_day");
    }

    /**
     * Returns the distinct percent value change since 7 days check specification.
     * @return Distinct count percent change since 7 days check specification.
     */
    public ColumnDistinctPercentChange7DaysCheckSpec getDailyDistinctPercentChange7Days() {
        return dailyDistinctPercentChange7Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 7 days check.
     * @param dailyDistinctPercentChange7Days Distinct count percent change since 7 days check specification.
     */
    public void setDailyDistinctPercentChange7Days(ColumnDistinctPercentChange7DaysCheckSpec dailyDistinctPercentChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctPercentChange7Days, dailyDistinctPercentChange7Days));
        this.dailyDistinctPercentChange7Days = dailyDistinctPercentChange7Days;
        propagateHierarchyIdToField(dailyDistinctPercentChange7Days, "daily_distinct_percent_change_7_days");
    }

    /**
     * Returns the distinct percent value change since 30 days check specification.
     * @return Distinct count percent change since 30 days check specification.
     */
    public ColumnDistinctPercentChange30DaysCheckSpec getDailyDistinctPercentChange30Days() {
        return dailyDistinctPercentChange30Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 30 days check specification.
     * @param dailyDistinctPercentChange30Days Distinct count percent change since 30 days check specification.
     */
    public void setDailyDistinctPercentChange30Days(ColumnDistinctPercentChange30DaysCheckSpec dailyDistinctPercentChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyDistinctPercentChange30Days, dailyDistinctPercentChange30Days));
        this.dailyDistinctPercentChange30Days = dailyDistinctPercentChange30Days;
        propagateHierarchyIdToField(dailyDistinctPercentChange30Days, "daily_distinct_percent_change_30_days");
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