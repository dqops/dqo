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
package com.dqops.checks.column.monitoring.nulls;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.column.checkspecs.nulls.*;
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
 * Container of nulls data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_nulls_count", o -> o.dailyNullsCount);
            put("daily_nulls_percent", o -> o.dailyNullsPercent);

            put("daily_nulls_percent_anomaly_stationary_30_days", o ->o.dailyNullsPercentAnomalyStationary30Days);
            put("daily_nulls_percent_anomaly_stationary", o ->o.dailyNullsPercentAnomalyStationary);

            put("daily_nulls_percent_change", o ->o.dailyNullsPercentChange);
            put("daily_nulls_percent_change_yesterday", o ->o.dailyNullsPercentChangeYesterday);
            put("daily_nulls_percent_change_7_days", o ->o.dailyNullsPercentChange7Days);
            put("daily_nulls_percent_change_30_days", o ->o.dailyNullsPercentChange30Days);
            
            put("daily_not_nulls_count", o -> o.dailyNotNullsCount);
            put("daily_not_nulls_percent", o -> o.dailyNotNullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNullsCountCheckSpec dailyNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNullsPercentCheckSpec dailyNullsPercent;

    @JsonProperty("daily_nulls_percent_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryNullPercent30DaysCheckSpec dailyNullsPercentAnomalyStationary30Days;

    @JsonPropertyDescription("Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryNullPercentCheckSpec dailyNullsPercentAnomalyStationary;

    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout.")
    private ColumnChangeNullPercentCheckSpec dailyNullsPercentChange;

    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeNullPercentSinceYesterdayCheckSpec dailyNullsPercentChangeYesterday;

    @JsonProperty("daily_nulls_percent_change_7_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeNullPercentSince7DaysCheckSpec dailyNullsPercentChange7Days;

    @JsonProperty("daily_nulls_percent_change_30_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeNullPercentSince30DaysCheckSpec dailyNullsPercentChange30Days;

    @JsonPropertyDescription("Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNotNullsCountCheckSpec dailyNotNullsCount;

    @JsonPropertyDescription("Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNotNullsPercentCheckSpec dailyNotNullsPercent;

    /**
     * Returns nulls count check specification.
     * @return Nulls count check specification.
     */
    public ColumnNullsCountCheckSpec getDailyNullsCount() {
        return dailyNullsCount;
    }

    /**
     * Sets a new definition of a nulls count check.
     * @param dailyNullsCount Nulls count check specification.
     */
    public void setDailyNullsCount(ColumnNullsCountCheckSpec dailyNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsCount, dailyNullsCount));
        this.dailyNullsCount = dailyNullsCount;
        propagateHierarchyIdToField(dailyNullsCount, "daily_nulls_count");
    }

    /**
     * Returns a nulls percent check specification.
     * @return Nulls percent check specification.
     */
    public ColumnNullsPercentCheckSpec getDailyNullsPercent() {
        return dailyNullsPercent;
    }

    /**
     * Sets a new definition of a nulls percent check.
     * @param dailyNullsPercent Nulls percent check specification.
     */
    public void setDailyNullsPercent(ColumnNullsPercentCheckSpec dailyNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercent, dailyNullsPercent));
        this.dailyNullsPercent = dailyNullsPercent;
        propagateHierarchyIdToField(dailyNullsPercent, "daily_nulls_percent");
    }

    /**
     * Returns a null percent value anomaly 30 days check specification.
     * @return Null percent value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryNullPercent30DaysCheckSpec getDailyNullsPercentAnomalyStationary30Days() {
        return dailyNullsPercentAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a null percent value anomaly 30 days check.
     * @param dailyNullsPercentAnomalyStationary30Days Null percent value anomaly 30 days check specification.
     */
    public void setDailyNullsPercentAnomalyStationary30Days(ColumnAnomalyStationaryNullPercent30DaysCheckSpec dailyNullsPercentAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentAnomalyStationary30Days, dailyNullsPercentAnomalyStationary30Days));
        this.dailyNullsPercentAnomalyStationary30Days = dailyNullsPercentAnomalyStationary30Days;
        propagateHierarchyIdToField(dailyNullsPercentAnomalyStationary30Days, "daily_nulls_percent_anomaly_stationary_30_days");
    }

    /**
     * Returns a null percent value anomaly 90 days check specification.
     * @return Null percent value anomaly 90 days check specification.
     */
    public ColumnAnomalyStationaryNullPercentCheckSpec getDailyNullsPercentAnomalyStationary() {
        return dailyNullsPercentAnomalyStationary;
    }

    /**
     * Sets a new specification of a null percent value anomaly 90 days check.
     * @param dailyNullsPercentAnomalyStationary Null percent value anomaly 90 days check specification.
     */
    public void setDailyNullsPercentAnomalyStationary(ColumnAnomalyStationaryNullPercentCheckSpec dailyNullsPercentAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentAnomalyStationary, dailyNullsPercentAnomalyStationary));
        this.dailyNullsPercentAnomalyStationary = dailyNullsPercentAnomalyStationary;
        propagateHierarchyIdToField(dailyNullsPercentAnomalyStationary, "daily_nulls_percent_anomaly_stationary");
    }

    /**
     * Returns the null percent value change check.
     * @return Null percent value change check.
     */
    public ColumnChangeNullPercentCheckSpec getDailyNullsPercentChange() {
        return dailyNullsPercentChange;
    }

    /**
     * Sets a new null percent value change check.
     * @param dailyNullsPercentChange Null percent value change check.
     */
    public void setDailyNullsPercentChange(ColumnChangeNullPercentCheckSpec dailyNullsPercentChange) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentChange, dailyNullsPercentChange));
        this.dailyNullsPercentChange = dailyNullsPercentChange;
        propagateHierarchyIdToField(dailyNullsPercentChange, "daily_nulls_percent_change");
    }

    /**
     * Returns the null percent value change yesterday check.
     * @return Null percent value change yesterday check.
     */
    public ColumnChangeNullPercentSinceYesterdayCheckSpec getDailyNullsPercentChangeYesterday() {
        return dailyNullsPercentChangeYesterday;
    }

    /**
     * Sets a new null percent value change yesterday check.
     * @param dailyNullsPercentChangeYesterday Null percent value change yesterday check.
     */
    public void setDailyNullsPercentChangeYesterday(ColumnChangeNullPercentSinceYesterdayCheckSpec dailyNullsPercentChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentChangeYesterday, dailyNullsPercentChangeYesterday));
        this.dailyNullsPercentChangeYesterday = dailyNullsPercentChangeYesterday;
        propagateHierarchyIdToField(dailyNullsPercentChangeYesterday, "daily_nulls_percent_change_yesterday");
    }

    /**
     * Returns the null percent value change 7 days check.
     * @return Null percent value change 7 days check.
     */
    public ColumnChangeNullPercentSince7DaysCheckSpec getDailyNullsPercentChange7Days() {
        return dailyNullsPercentChange7Days;
    }

    /**
     * Sets a new null percent value change 7 days check.
     * @param dailyNullsPercentChange7Days Null percent value change 7 days check.
     */
    public void setDailyNullsPercentChange7Days(ColumnChangeNullPercentSince7DaysCheckSpec dailyNullsPercentChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentChange7Days, dailyNullsPercentChange7Days));
        this.dailyNullsPercentChange7Days = dailyNullsPercentChange7Days;
        propagateHierarchyIdToField(dailyNullsPercentChange7Days, "daily_nulls_percent_change_7_days");
    }

    /**
     * Returns the null percent value change 30 days check.
     * @return Null percent value change 30 days check.
     */
    public ColumnChangeNullPercentSince30DaysCheckSpec getDailyNullsPercentChange30Days() {
        return dailyNullsPercentChange30Days;
    }

    /**
     * Sets a new null percent value change 30 days check.
     * @param dailyNullsPercentChange30Days Null percent value change 30 days check.
     */
    public void setDailyNullsPercentChange30Days(ColumnChangeNullPercentSince30DaysCheckSpec dailyNullsPercentChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentChange30Days, dailyNullsPercentChange30Days));
        this.dailyNullsPercentChange30Days = dailyNullsPercentChange30Days;
        propagateHierarchyIdToField(dailyNullsPercentChange30Days, "daily_nulls_percent_change_30_days");
    }
    
    /**
     * Returns not nulls count check specification.
     * @return Not nulls count check specification.
     */
    public ColumnNotNullsCountCheckSpec getDailyNotNullsCount() {
        return dailyNotNullsCount;
    }

    /**
     * Sets a new definition of a not nulls count check.
     * @param dailyNotNullsCount Not nulls count check specification.
     */
    public void setDailyNotNullsCount(ColumnNotNullsCountCheckSpec dailyNotNullsCount) {
        this.setDirtyIf(!Objects.equals(this.dailyNotNullsCount, dailyNotNullsCount));
        this.dailyNotNullsCount = dailyNotNullsCount;
        propagateHierarchyIdToField(dailyNotNullsCount, "daily_not_nulls_count");
    }

    /**
     * Returns a not nulls percent check specification.
     * @return Not nulls percent check specification.
     */
    public ColumnNotNullsPercentCheckSpec getDailyNotNullsPercent() {
        return dailyNotNullsPercent;
    }

    /**
     * Sets a new definition of a not nulls percent check.
     * @param dailyNotNullsPercent Not nulls percent check specification.
     */
    public void setDailyNotNullsPercent(ColumnNotNullsPercentCheckSpec dailyNotNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNotNullsPercent, dailyNotNullsPercent));
        this.dailyNotNullsPercent = dailyNotNullsPercent;
        propagateHierarchyIdToField(dailyNotNullsPercent, "daily_not_nulls_percent");
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
    public ColumnNullsDailyMonitoringChecksSpec deepClone() {
        return (ColumnNullsDailyMonitoringChecksSpec)super.deepClone();
    }
}