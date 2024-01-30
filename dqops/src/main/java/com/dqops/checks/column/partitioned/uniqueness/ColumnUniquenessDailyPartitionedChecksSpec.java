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
import com.dqops.connectors.DataTypeCategory;
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

            put("daily_partition_distinct_count_anomaly", o -> o.dailyPartitionDistinctCountAnomaly);
            put("daily_partition_distinct_percent_anomaly", o -> o.dailyPartitionDistinctPercentAnomaly);

            put("daily_partition_distinct_count_change", o -> o.dailyPartitionDistinctCountChange);
            put("daily_partition_distinct_count_change_1_day", o -> o.dailyPartitionDistinctCountChange1Day);
            put("daily_partition_distinct_count_change_7_days", o -> o.dailyPartitionDistinctCountChange7Days);
            put("daily_partition_distinct_count_change_30_days", o -> o.dailyPartitionDistinctCountChange30Days);
            put("daily_partition_distinct_percent_change", o -> o.dailyPartitionDistinctPercentChange);
            put("daily_partition_distinct_percent_change_1_day", o -> o.dailyPartitionDistinctPercentChange1Day);
            put("daily_partition_distinct_percent_change_7_days", o -> o.dailyPartitionDistinctPercentChange7Days);
            put("daily_partition_distinct_percent_change_30_days", o -> o.dailyPartitionDistinctPercentChange30Days);
        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores a separate data quality check result for each daily partition.")
    private ColumnDistinctCountCheckSpec dailyPartitionDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores a separate data quality check result for each daily partition.")
    private ColumnDistinctPercentCheckSpec dailyPartitionDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.")
    private ColumnDuplicateCountCheckSpec dailyPartitionDuplicateCount;

    @JsonPropertyDescription("Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each daily partition.")
    private ColumnDuplicatePercentCheckSpec dailyPartitionDuplicatePercent;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnDistinctCountAnomalyStationaryPartitionCheckSpec dailyPartitionDistinctCountAnomaly;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnDistinctPercentAnomalyStationaryCheckSpec dailyPartitionDistinctPercentAnomaly;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnDistinctCountChangeCheckSpec dailyPartitionDistinctCountChange;

    @JsonProperty("daily_partition_distinct_count_change_1_day")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnDistinctCountChange1DayCheckSpec dailyPartitionDistinctCountChange1Day;

    @JsonProperty("daily_partition_distinct_count_change_7_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last week.")
    private ColumnDistinctCountChange7DaysCheckSpec dailyPartitionDistinctCountChange7Days;

    @JsonProperty("daily_partition_distinct_count_change_30_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last month.")
    private ColumnDistinctCountChange30DaysCheckSpec dailyPartitionDistinctCountChange30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnDistinctPercentChangeCheckSpec dailyPartitionDistinctPercentChange;

    @JsonProperty("daily_partition_distinct_percent_change_1_day")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnDistinctPercentChange1DayCheckSpec dailyPartitionDistinctPercentChange1Day;

    @JsonProperty("daily_partition_distinct_percent_change_7_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last week.")
    private ColumnDistinctPercentChange7DaysCheckSpec dailyPartitionDistinctPercentChange7Days;

    @JsonProperty("daily_partition_distinct_percent_change_30_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last month.")
    private ColumnDistinctPercentChange30DaysCheckSpec dailyPartitionDistinctPercentChange30Days;


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
     * Returns a distinct count value anomaly check specification.
     * @return Distinct count value anomaly check specification.
     */
    public ColumnDistinctCountAnomalyStationaryPartitionCheckSpec getDailyPartitionDistinctCountAnomaly() {
        return dailyPartitionDistinctCountAnomaly;
    }

    /**
     * Sets a new specification of a distinct count value anomaly check.
     * @param dailyPartitionDistinctCountAnomaly Distinct count value anomaly check specification.
     */
    public void setDailyPartitionDistinctCountAnomaly(ColumnDistinctCountAnomalyStationaryPartitionCheckSpec dailyPartitionDistinctCountAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctCountAnomaly, dailyPartitionDistinctCountAnomaly));
        this.dailyPartitionDistinctCountAnomaly = dailyPartitionDistinctCountAnomaly;
        propagateHierarchyIdToField(dailyPartitionDistinctCountAnomaly, "daily_partition_distinct_count_anomaly");
    }

    /**
     * Returns a distinct percent value anomaly check specification.
     * @return Distinct percent value anomaly check specification.
     */
    public ColumnDistinctPercentAnomalyStationaryCheckSpec getDailyPartitionDistinctPercentAnomaly() {
        return dailyPartitionDistinctPercentAnomaly;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly check.
     * @param dailyPartitionDistinctPercentAnomaly Distinct percent value anomaly check specification.
     */
    public void setDailyPartitionDistinctPercentAnomaly(ColumnDistinctPercentAnomalyStationaryCheckSpec dailyPartitionDistinctPercentAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctPercentAnomaly, dailyPartitionDistinctPercentAnomaly));
        this.dailyPartitionDistinctPercentAnomaly = dailyPartitionDistinctPercentAnomaly;
        propagateHierarchyIdToField(dailyPartitionDistinctPercentAnomaly, "daily_partition_distinct_percent_anomaly");
    }

    /**
     * Returns the distinct count value change check specification.
     * @return Distinct count value change check specification.
     */
    public ColumnDistinctCountChangeCheckSpec getDailyPartitionDistinctCountChange() {
        return dailyPartitionDistinctCountChange;
    }

    /**
     * Sets a new specification of a distinct count value change check.
     * @param dailyPartitionDistinctCountChange Distinct count value change check specification.
     */
    public void setDailyPartitionDistinctCountChange(ColumnDistinctCountChangeCheckSpec dailyPartitionDistinctCountChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctCountChange, dailyPartitionDistinctCountChange));
        this.dailyPartitionDistinctCountChange = dailyPartitionDistinctCountChange;
        propagateHierarchyIdToField(dailyPartitionDistinctCountChange, "daily_partition_distinct_count_change");
    }

    /**
     * Returns the distinct count value change since yesterday check specification.
     * @return Distinct count value change since yesterday check specification.
     */
    public ColumnDistinctCountChange1DayCheckSpec getDailyPartitionDistinctCountChange1Day() {
        return dailyPartitionDistinctCountChange1Day;
    }

    /**
     * Sets a new specification of a distinct count value change since yesterday check .
     * @param dailyPartitionDistinctCountChange1Day Distinct count value change since yesterday check specification.
     */
    public void setDailyPartitionDistinctCountChange1Day(ColumnDistinctCountChange1DayCheckSpec dailyPartitionDistinctCountChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctCountChange1Day, dailyPartitionDistinctCountChange1Day));
        this.dailyPartitionDistinctCountChange1Day = dailyPartitionDistinctCountChange1Day;
        propagateHierarchyIdToField(dailyPartitionDistinctCountChange1Day, "daily_partition_distinct_count_change_1_day");
    }

    /**
     * Returns the distinct count value change since 7 days check specification.
     * @return Distinct count value change since 7 days check specification.
     */
    public ColumnDistinctCountChange7DaysCheckSpec getDailyPartitionDistinctCountChange7Days() {
        return dailyPartitionDistinctCountChange7Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 7 days check.
     * @param dailyPartitionDistinctCountChange7Days Distinct count value change since 7 days check specification.
     */
    public void setDailyPartitionDistinctCountChange7Days(ColumnDistinctCountChange7DaysCheckSpec dailyPartitionDistinctCountChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctCountChange7Days, dailyPartitionDistinctCountChange7Days));
        this.dailyPartitionDistinctCountChange7Days = dailyPartitionDistinctCountChange7Days;
        propagateHierarchyIdToField(dailyPartitionDistinctCountChange7Days, "daily_partition_distinct_count_change_7_days");
    }

    /**
     * Returns the distinct count value change since 30 days check specification.
     * @return Distinct count value change since 30 days check specification.
     */
    public ColumnDistinctCountChange30DaysCheckSpec getDailyPartitionDistinctCountChange30Days() {
        return dailyPartitionDistinctCountChange30Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 30 days check.
     * @param dailyPartitionDistinctCountChange30Days Distinct count value change since 30 days check specification.
     */
    public void setDailyPartitionDistinctCountChange30Days(ColumnDistinctCountChange30DaysCheckSpec dailyPartitionDistinctCountChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctCountChange30Days, dailyPartitionDistinctCountChange30Days));
        this.dailyPartitionDistinctCountChange30Days = dailyPartitionDistinctCountChange30Days;
        propagateHierarchyIdToField(dailyPartitionDistinctCountChange30Days, "daily_partition_distinct_count_change_30_days");
    }

    /**
     * Returns the distinct percent value change check specification.
     * @return Distinct percent value change check specification.
     */
    public ColumnDistinctPercentChangeCheckSpec getDailyPartitionDistinctPercentChange() {
        return dailyPartitionDistinctPercentChange;
    }

    /**
     * Sets a new specification of a distinct percent value change check.
     * @param dailyPartitionDistinctPercentChange Distinct percent value change check specification.
     */
    public void setDailyPartitionDistinctPercentChange(ColumnDistinctPercentChangeCheckSpec dailyPartitionDistinctPercentChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctPercentChange, dailyPartitionDistinctPercentChange));
        this.dailyPartitionDistinctPercentChange = dailyPartitionDistinctPercentChange;
        propagateHierarchyIdToField(dailyPartitionDistinctPercentChange, "daily_partition_distinct_percent_change");
    }

    /**
     * Returns the distinct percent value change since yesterday check specification.
     * @return Distinct count percent change since yesterday check specification.
     */
    public ColumnDistinctPercentChange1DayCheckSpec getDailyPartitionDistinctPercentChange1Day() {
        return dailyPartitionDistinctPercentChange1Day;
    }

    /**
     * Sets a new specification of a distinct count percent change since yesterday check specification.
     * @param dailyPartitionDistinctPercentChange1Day Distinct count percent change since yesterday check specification.
     */
    public void setDailyPartitionDistinctPercentChange1Day(ColumnDistinctPercentChange1DayCheckSpec dailyPartitionDistinctPercentChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctPercentChange1Day, dailyPartitionDistinctPercentChange1Day));
        this.dailyPartitionDistinctPercentChange1Day = dailyPartitionDistinctPercentChange1Day;
        propagateHierarchyIdToField(dailyPartitionDistinctPercentChange1Day, "daily_partition_distinct_percent_change_1_day");
    }

    /**
     * Returns the distinct percent value change since 7 days check specification.
     * @return Distinct count percent change since 7 days check specification.
     */
    public ColumnDistinctPercentChange7DaysCheckSpec getDailyPartitionDistinctPercentChange7Days() {
        return dailyPartitionDistinctPercentChange7Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 7 days check.
     * @param dailyPartitionDistinctPercentChange7Days Distinct count percent change since 7 days check specification.
     */
    public void setDailyPartitionDistinctPercentChange7Days(ColumnDistinctPercentChange7DaysCheckSpec dailyPartitionDistinctPercentChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctPercentChange7Days, dailyPartitionDistinctPercentChange7Days));
        this.dailyPartitionDistinctPercentChange7Days = dailyPartitionDistinctPercentChange7Days;
        propagateHierarchyIdToField(dailyPartitionDistinctPercentChange7Days, "daily_partition_distinct_percent_change_7_days");
    }

    /**
     * Returns the distinct percent value change since 30 days check specification.
     * @return Distinct count percent change since 30 days check specification.
     */
    public ColumnDistinctPercentChange30DaysCheckSpec getDailyPartitionDistinctPercentChange30Days() {
        return dailyPartitionDistinctPercentChange30Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 30 days check specification.
     * @param dailyPartitionDistinctPercentChange30Days Distinct count percent change since 30 days check specification.
     */
    public void setDailyPartitionDistinctPercentChange30Days(ColumnDistinctPercentChange30DaysCheckSpec dailyPartitionDistinctPercentChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDistinctPercentChange30Days, dailyPartitionDistinctPercentChange30Days));
        this.dailyPartitionDistinctPercentChange30Days = dailyPartitionDistinctPercentChange30Days;
        propagateHierarchyIdToField(dailyPartitionDistinctPercentChange30Days, "daily_partition_distinct_percent_change_30_days");
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

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.ANY;
    }
}