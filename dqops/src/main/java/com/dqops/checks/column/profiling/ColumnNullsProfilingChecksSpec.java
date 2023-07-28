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
package com.dqops.checks.column.profiling;

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
 * Container of built-in preconfigured data quality checks on a column level that are checking for nulls.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("nulls_count", o -> o.nullsCount);
            put("nulls_percent", o -> o.nullsPercent);
            
            put("nulls_percent_anomaly_stationary_30_days", o ->o.nullsPercentAnomalyStationary30Days);
            put("nulls_percent_anomaly_stationary", o ->o.nullsPercentAnomalyStationary);
            
            put("nulls_percent_change", o ->o.nullsPercentChange);
            put("nulls_percent_change_yesterday", o ->o.nullsPercentChangeYesterday);
            put("nulls_percent_change_7_days", o ->o.nullsPercentChange7Days);
            put("nulls_percent_change_30_days", o ->o.nullsPercentChange30Days);
            
            put("not_nulls_count", o -> o.notNullsCount);
            put("not_nulls_percent", o -> o.notNullsPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of null values in a column does not exceed the maximum accepted count.")
    private ColumnNullsCountCheckSpec nullsCount;

    @JsonPropertyDescription("Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.")
    private ColumnNullsPercentCheckSpec nullsPercent;

    @JsonProperty("nulls_percent_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryNullPercent30DaysCheckSpec nullsPercentAnomalyStationary30Days;

    @JsonPropertyDescription("Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryNullPercentCheckSpec nullsPercentAnomalyStationary;

    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout.")
    private ColumnChangeNullPercentCheckSpec nullsPercentChange;

    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnChangeNullPercentSinceYesterdayCheckSpec nullsPercentChangeYesterday;

    @JsonProperty("nulls_percent_change_7_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.")
    private ColumnChangeNullPercentSince7DaysCheckSpec nullsPercentChange7Days;

    @JsonProperty("nulls_percent_change_30_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.")
    private ColumnChangeNullPercentSince30DaysCheckSpec nullsPercentChange30Days;

    @JsonPropertyDescription("Verifies that the number of not null values in a column does not exceed the minimum accepted count.")
    private ColumnNotNullsCountCheckSpec notNullsCount;

    @JsonPropertyDescription("Verifies that the percent of not null values in a column does not exceed the minimum accepted percentage.")
    private ColumnNotNullsPercentCheckSpec notNullsPercent;

    /**
     * Returns a nulls count check specification.
     * @return Nulls count check specification.
     */
    public ColumnNullsCountCheckSpec getNullsCount() {
        return nullsCount;
    }

    /**
     * Sets a new nulls count check  specification.
     * @param nullsCount Nulls count check specification.
     */
    public void setNullsCount(ColumnNullsCountCheckSpec nullsCount) {
        this.setDirtyIf(!Objects.equals(this.nullsCount, nullsCount));
        this.nullsCount = nullsCount;
        propagateHierarchyIdToField(nullsCount, "nulls_count");
    }

    /**
     * Returns a nulls percent check specification.
     * @return Nulls percent check specification.
     */
    public ColumnNullsPercentCheckSpec getNullsPercent() {
        return nullsPercent;
    }

    /**
     * Sets a new percent check specification.
     * @param nullsPercent Nulls percent check specification.
     */
    public void setNullsPercent(ColumnNullsPercentCheckSpec nullsPercent) {
        this.setDirtyIf(!Objects.equals(this.nullsPercent, nullsPercent));
        this.nullsPercent = nullsPercent;
        propagateHierarchyIdToField(nullsPercent, "nulls_percent");
    }
    
    /**
     * Returns a null percent value anomaly 30 days check specification.
     * @return Null percent value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryNullPercent30DaysCheckSpec getNullsPercentAnomalyStationary30Days() {
        return nullsPercentAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a null percent value anomaly 30 days check.
     * @param nullsPercentAnomalyStationary30Days Null percent value anomaly 30 days check specification.
     */
    public void setNullsPercentAnomalyStationary30Days(ColumnAnomalyStationaryNullPercent30DaysCheckSpec nullsPercentAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.nullsPercentAnomalyStationary30Days, nullsPercentAnomalyStationary30Days));
        this.nullsPercentAnomalyStationary30Days = nullsPercentAnomalyStationary30Days;
        propagateHierarchyIdToField(nullsPercentAnomalyStationary30Days, "nulls_percent_anomaly_stationary_30_days");
    }

    /**
     * Returns a null percent value anomaly 90 days check specification.
     * @return Null percent value anomaly 90 days check specification.
     */
    public ColumnAnomalyStationaryNullPercentCheckSpec getNullsPercentAnomalyStationary() {
        return nullsPercentAnomalyStationary;
    }

    /**
     * Sets a new specification of a null percent value anomaly 90 days check.
     * @param nullsPercentAnomalyStationary Null percent value anomaly 90 days check specification.
     */
    public void setNullsPercentAnomalyStationary(ColumnAnomalyStationaryNullPercentCheckSpec nullsPercentAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.nullsPercentAnomalyStationary, nullsPercentAnomalyStationary));
        this.nullsPercentAnomalyStationary = nullsPercentAnomalyStationary;
        propagateHierarchyIdToField(nullsPercentAnomalyStationary, "nulls_percent_anomaly_stationary");
    }

    /**
     * Returns the null percent value change check.
     * @return Null percent value change check.
     */
    public ColumnChangeNullPercentCheckSpec getNullsPercentChange() {
        return nullsPercentChange;
    }

    /**
     * Sets a new null percent value change check.
     * @param nullsPercentChange Null percent value change check.
     */
    public void setNullsPercentChange(ColumnChangeNullPercentCheckSpec nullsPercentChange) {
        this.setDirtyIf(!Objects.equals(this.nullsPercentChange, nullsPercentChange));
        this.nullsPercentChange = nullsPercentChange;
        propagateHierarchyIdToField(nullsPercentChange, "nulls_percent_change");
    }

    /**
     * Returns the null percent value change yesterday check.
     * @return Null percent value change yesterday check.
     */
    public ColumnChangeNullPercentSinceYesterdayCheckSpec getNullsPercentChangeYesterday() {
        return nullsPercentChangeYesterday;
    }

    /**
     * Sets a new null percent value change yesterday check.
     * @param nullsPercentChangeYesterday Null percent value change yesterday check.
     */
    public void setNullsPercentChangeYesterday(ColumnChangeNullPercentSinceYesterdayCheckSpec nullsPercentChangeYesterday) {
        this.setDirtyIf(!Objects.equals(this.nullsPercentChangeYesterday, nullsPercentChangeYesterday));
        this.nullsPercentChangeYesterday = nullsPercentChangeYesterday;
        propagateHierarchyIdToField(nullsPercentChangeYesterday, "nulls_percent_change_yesterday");
    }

    /**
     * Returns the null percent value change 7 days check.
     * @return Null percent value change 7 days check.
     */
    public ColumnChangeNullPercentSince7DaysCheckSpec getNullsPercentChange7Days() {
        return nullsPercentChange7Days;
    }

    /**
     * Sets a new null percent value change 7 days check.
     * @param nullsPercentChange7Days Null percent value change 7 days check.
     */
    public void setNullsPercentChange7Days(ColumnChangeNullPercentSince7DaysCheckSpec nullsPercentChange7Days) {
        this.setDirtyIf(!Objects.equals(this.nullsPercentChange7Days, nullsPercentChange7Days));
        this.nullsPercentChange7Days = nullsPercentChange7Days;
        propagateHierarchyIdToField(nullsPercentChange7Days, "nulls_percent_change_7_days");
    }

    /**
     * Returns the null percent value change 30 days check.
     * @return Null percent value change 30 days check.
     */
    public ColumnChangeNullPercentSince30DaysCheckSpec getNullsPercentChange30Days() {
        return nullsPercentChange30Days;
    }

    /**
     * Sets a new null percent value change 30 days check.
     * @param nullsPercentChange30Days Null percent value change 30 days check.
     */
    public void setNullsPercentChange30Days(ColumnChangeNullPercentSince30DaysCheckSpec nullsPercentChange30Days) {
        this.setDirtyIf(!Objects.equals(this.nullsPercentChange30Days, nullsPercentChange30Days));
        this.nullsPercentChange30Days = nullsPercentChange30Days;
        propagateHierarchyIdToField(nullsPercentChange30Days, "nulls_percent_change_30_days");
    }

    /**
     * Returns a not nulls count check specification.
     * @return Not nulls count check specification.
     */
    public ColumnNotNullsCountCheckSpec getNotNullsCount() {
        return notNullsCount;
    }

    /**
     * Sets a new not nulls count check  specification.
     * @param notNullsCount Not nulls count check specification.
     */
    public void setNotNullsCount(ColumnNotNullsCountCheckSpec notNullsCount) {
        this.setDirtyIf(!Objects.equals(this.notNullsCount, notNullsCount));
        this.notNullsCount = notNullsCount;
        propagateHierarchyIdToField(notNullsCount, "not_nulls_count");
    }


    /**
     * Returns a not nulls percent check specification.
     * @return Not nulls percent check specification.
     */
    public ColumnNotNullsPercentCheckSpec getNotNullsPercent() {
        return notNullsPercent;
    }

    /**
     * Sets a new not null percent check specification.
     * @param notNullsPercent Not nulls percent check specification.
     */
    public void setNotNullsPercent(ColumnNotNullsPercentCheckSpec notNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.notNullsPercent, notNullsPercent));
        this.notNullsPercent = notNullsPercent;
        propagateHierarchyIdToField(notNullsPercent, "not_nulls_percent");
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
    public ColumnNullsProfilingChecksSpec deepClone() {
        return (ColumnNullsProfilingChecksSpec)super.deepClone();
    }
}
