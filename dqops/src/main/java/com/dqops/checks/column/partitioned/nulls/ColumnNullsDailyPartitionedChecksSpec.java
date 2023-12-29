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
package com.dqops.checks.column.partitioned.nulls;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.nulls.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of nulls data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_nulls_count", o -> o.dailyPartitionNullsCount);
            put("daily_partition_nulls_percent", o -> o.dailyPartitionNullsPercent);

            put("daily_partition_nulls_percent_anomaly_stationary_30_days", o ->o.dailyPartitionNullsPercentAnomalyStationary30Days);
            put("daily_partition_nulls_percent_anomaly_stationary", o ->o.dailyPartitionNullsPercentAnomalyStationary);

            put("daily_partition_nulls_percent_change", o ->o.dailyPartitionNullsPercentChange);
            put("daily_partition_nulls_percent_change_yesterday", o ->o.dailyPartitionNullsPercentChangeYesterday);
            put("daily_partition_nulls_percent_change_7_days", o ->o.dailyPartitionNullsPercentChange7Days);
            put("daily_partition_nulls_percent_change_30_days", o ->o.dailyPartitionNullsPercentChange30Days);

            put("daily_partition_not_nulls_count", o -> o.dailyPartitionNotNullsCount);
            put("daily_partition_not_nulls_percent", o -> o.dailyPartitionNotNullsPercent);

        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNullsCountCheckSpec dailyPartitionNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNullsPercentCheckSpec dailyPartitionNullsPercent;

    @JsonProperty("daily_partition_nulls_percent_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryNullPercent30DaysCheckSpec dailyPartitionNullsPercentAnomalyStationary30Days;

    @JsonPropertyDescription("Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryNullPercentCheckSpec dailyPartitionNullsPercentAnomalyStationary;

    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout.")
    private ColumnChangeNullPercentCheckSpec dailyPartitionNullsPercentChange;

    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeNullPercentSinceYesterdayCheckSpec dailyPartitionNullsPercentChangeYesterday;

    @JsonProperty("daily_partition_nulls_percent_change_7_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeNullPercentSince7DaysCheckSpec dailyPartitionNullsPercentChange7Days;

    @JsonProperty("daily_partition_nulls_percent_change_30_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeNullPercentSince30DaysCheckSpec dailyPartitionNullsPercentChange30Days;


    @JsonPropertyDescription("Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNotNullsCountCheckSpec dailyPartitionNotNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnNotNullsPercentCheckSpec dailyPartitionNotNullsPercent;

    /**
     * Returns a nulls count check.
     * @return Nulls count check.
     */
    public ColumnNullsCountCheckSpec getDailyPartitionNullsCount() {
        return dailyPartitionNullsCount;
    }

    /**
     * Sets a new definition of a nulls count check.
     * @param dailyPartitionNullsCount Nulls count check.
     */
    public void setDailyPartitionNullsCount(ColumnNullsCountCheckSpec dailyPartitionNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsCount, dailyPartitionNullsCount));
        this.dailyPartitionNullsCount = dailyPartitionNullsCount;
        propagateHierarchyIdToField(dailyPartitionNullsCount, "daily_partition_nulls_count");
    }

    /**
     * Returns a nulls percent check.
     * @return Nulls percent check.
     */
    public ColumnNullsPercentCheckSpec getDailyPartitionNullsPercent() {
        return dailyPartitionNullsPercent;
    }

    /**
     * Sets a new definition of a nulls percent check.
     * @param dailyPartitionNullsPercent Nulls percent check.
     */
    public void setDailyPartitionNullsPercent(ColumnNullsPercentCheckSpec dailyPartitionNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercent, dailyPartitionNullsPercent));
        this.dailyPartitionNullsPercent = dailyPartitionNullsPercent;
        propagateHierarchyIdToField(dailyPartitionNullsPercent, "daily_partition_nulls_percent");
    }

    /**
     * Returns a null percent value anomaly 30 days check specification.
     * @return Null percent value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryNullPercent30DaysCheckSpec getDailyPartitionNullsPercentAnomalyStationary30Days() {
        return dailyPartitionNullsPercentAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a null percent value anomaly 30 days check.
     * @param dailyPartitionNullsPercentAnomalyStationary30Days Null percent value anomaly 30 days check specification.
     */
    public void setDailyPartitionNullsPercentAnomalyStationary30Days(ColumnAnomalyStationaryNullPercent30DaysCheckSpec dailyPartitionNullsPercentAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentAnomalyStationary30Days, dailyPartitionNullsPercentAnomalyStationary30Days));
        this.dailyPartitionNullsPercentAnomalyStationary30Days = dailyPartitionNullsPercentAnomalyStationary30Days;
        propagateHierarchyIdToField(dailyPartitionNullsPercentAnomalyStationary30Days, "daily_partition_nulls_percent_anomaly_stationary_30_days");
    }

    /**
     * Returns a null percent value anomaly 90 days check specification.
     * @return Null percent value anomaly 90 days check specification.
     */
    public ColumnAnomalyStationaryNullPercentCheckSpec getDailyPartitionNullsPercentAnomalyStationary() {
        return dailyPartitionNullsPercentAnomalyStationary;
    }

    /**
     * Sets a new specification of a null percent value anomaly 90 days check.
     * @param dailyPartitionNullsPercentAnomalyStationary Null percent value anomaly 90 days check specification.
     */
    public void setDailyPartitionNullsPercentAnomalyStationary(ColumnAnomalyStationaryNullPercentCheckSpec dailyPartitionNullsPercentAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentAnomalyStationary, dailyPartitionNullsPercentAnomalyStationary));
        this.dailyPartitionNullsPercentAnomalyStationary = dailyPartitionNullsPercentAnomalyStationary;
        propagateHierarchyIdToField(dailyPartitionNullsPercentAnomalyStationary, "daily_partition_nulls_percent_anomaly_stationary");
    }

    /**
     * Returns the null percent value change check.
     * @return Null percent value change check.
     */
    public ColumnChangeNullPercentCheckSpec getDailyPartitionNullsPercentChange() {
        return dailyPartitionNullsPercentChange;
    }

    /**
     * Sets a new null percent value change check.
     * @param dailyPartitionNullsPercentChange Null percent value change check.
     */
    public void setDailyPartitionNullsPercentChange(ColumnChangeNullPercentCheckSpec dailyPartitionNullsPercentChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentChange, dailyPartitionNullsPercentChange));
        this.dailyPartitionNullsPercentChange = dailyPartitionNullsPercentChange;
        propagateHierarchyIdToField(dailyPartitionNullsPercentChange, "daily_partition_nulls_percent_change");
    }

    /**
     * Returns the null percent value change yesterday check.
     * @return Null percent value change yesterday check.
     */
    public ColumnChangeNullPercentSinceYesterdayCheckSpec getDailyPartitionNullsPercentChangeYesterday() {
        return dailyPartitionNullsPercentChangeYesterday;
    }

    /**
     * Sets a new null percent value change yesterday check.
     * @param dailyPartitionNullsPercentChangeYesterday Null percent value change yesterday check.
     */
    public void setDailyPartitionNullsPercentChangeYesterday(ColumnChangeNullPercentSinceYesterdayCheckSpec dailyPartitionNullsPercentChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentChangeYesterday, dailyPartitionNullsPercentChangeYesterday));
        this.dailyPartitionNullsPercentChangeYesterday = dailyPartitionNullsPercentChangeYesterday;
        propagateHierarchyIdToField(dailyPartitionNullsPercentChangeYesterday, "daily_partition_nulls_percent_change_yesterday");
    }

    /**
     * Returns the null percent value change 7 days check.
     * @return Null percent value change 7 days check.
     */
    public ColumnChangeNullPercentSince7DaysCheckSpec getDailyPartitionNullsPercentChange7Days() {
        return dailyPartitionNullsPercentChange7Days;
    }

    /**
     * Sets a new null percent value change 7 days check.
     * @param dailyPartitionNullsPercentChange7Days Null percent value change 7 days check.
     */
    public void setDailyPartitionNullsPercentChange7Days(ColumnChangeNullPercentSince7DaysCheckSpec dailyPartitionNullsPercentChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentChange7Days, dailyPartitionNullsPercentChange7Days));
        this.dailyPartitionNullsPercentChange7Days = dailyPartitionNullsPercentChange7Days;
        propagateHierarchyIdToField(dailyPartitionNullsPercentChange7Days, "daily_partition_nulls_percent_change_7_days");
    }

    /**
     * Returns the null percent value change 30 days check.
     * @return Null percent value change 30 days check.
     */
    public ColumnChangeNullPercentSince30DaysCheckSpec getDailyPartitionNullsPercentChange30Days() {
        return dailyPartitionNullsPercentChange30Days;
    }

    /**
     * Sets a new null percent value change 30 days check.
     * @param dailyPartitionNullsPercentChange30Days Null percent value change 30 days check.
     */
    public void setDailyPartitionNullsPercentChange30Days(ColumnChangeNullPercentSince30DaysCheckSpec dailyPartitionNullsPercentChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentChange30Days, dailyPartitionNullsPercentChange30Days));
        this.dailyPartitionNullsPercentChange30Days = dailyPartitionNullsPercentChange30Days;
        propagateHierarchyIdToField(dailyPartitionNullsPercentChange30Days, "daily_partition_nulls_percent_change_30_days");
    }

    /**
     * Returns a not nulls count check.
     * @return Not nulls count check.
     */
    public ColumnNotNullsCountCheckSpec getDailyPartitionNotNullsCount() {
        return dailyPartitionNotNullsCount;
    }

    /**
     * Sets a new definition of a not nulls count check.
     * @param dailyPartitionNotNullsCount Not nulls count check.
     */
    public void setDailyPartitionNotNullsCount(ColumnNotNullsCountCheckSpec dailyPartitionNotNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNotNullsCount, dailyPartitionNotNullsCount));
        this.dailyPartitionNotNullsCount = dailyPartitionNotNullsCount;
        propagateHierarchyIdToField(dailyPartitionNotNullsCount, "daily_partition_not_nulls_count");
    }

    /**
     * Returns a not nulls percent check.
     * @return Not nulls percent check.
     */
    public ColumnNotNullsPercentCheckSpec getDailyPartitionNotNullsPercent() {
        return dailyPartitionNotNullsPercent;
    }

    /**
     * Sets a new definition of a not nulls percent check.
     * @param dailyPartitionNotNullsPercent Not nulls percent check.
     */
    public void setDailyPartitionNotNullsPercent(ColumnNotNullsPercentCheckSpec dailyPartitionNotNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNotNullsPercent, dailyPartitionNotNullsPercent));
        this.dailyPartitionNotNullsPercent = dailyPartitionNotNullsPercent;
        propagateHierarchyIdToField(dailyPartitionNotNullsPercent, "daily_partition_not_nulls_percent");
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

    public static class ColumnNullsDailyPartitionedChecksSpecSampleFactory implements SampleValueFactory<ColumnNullsDailyPartitionedChecksSpec> {
        @Override
        public ColumnNullsDailyPartitionedChecksSpec createSample() {
            return new ColumnNullsDailyPartitionedChecksSpec() {{
                setDailyPartitionNullsCount(new ColumnNullsCountCheckSpec.ColumnNullsCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
