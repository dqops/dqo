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
 * Container of built-in preconfigured data quality checks on a column level that are checking for negative values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnUniquenessProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_distinct_count", o -> o.profileDistinctCount);
            put("profile_distinct_percent", o -> o.profileDistinctPercent);
            put("profile_duplicate_count", o -> o.profileDuplicateCount);
            put("profile_duplicate_percent", o -> o.profileDuplicatePercent);

            put("profile_anomaly_differencing_distinct_count_30_days", o -> o.profileAnomalyDifferencingDistinctCount30Days);
            put("profile_anomaly_differencing_distinct_count", o -> o.profileAnomalyDifferencingDistinctCount);
            put("profile_anomaly_stationary_distinct_percent_30_days", o -> o.profileAnomalyStationaryDistinctPercent30Days);
            put("profile_anomaly_stationary_distinct_percent", o -> o.profileAnomalyStationaryDistinctPercent);
            put("profile_change_distinct_count", o -> o.profileChangeDistinctCount);
            put("profile_change_distinct_count_since_7_days", o -> o.profileChangeDistinctCountSince7Days);
            put("profile_change_distinct_count_since_30_days", o -> o.profileChangeDistinctCountSince30Days);
            put("profile_change_distinct_count_since_yesterday", o -> o.profileChangeDistinctCountSinceYesterday);
            put("profile_change_distinct_percent", o -> o.profileChangeDistinctPercent);
            put("profile_change_distinct_percent_since_7_days", o -> o.profileChangeDistinctPercentSince7Days);
            put("profile_change_distinct_percent_since_30_days", o -> o.profileChangeDistinctPercentSince30Days);
            put("profile_change_distinct_percent_since_yesterday", o -> o.profileChangeDistinctPercentSinceYesterday);

        }
    };

    @JsonPropertyDescription("Verifies that the number of distinct values in a column does not fall below the minimum accepted count.")
    private ColumnDistinctCountCheckSpec profileDistinctCount;

    @JsonPropertyDescription("Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.")
    private ColumnDistinctPercentCheckSpec profileDistinctPercent;

    @JsonPropertyDescription("Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.")
    private ColumnDuplicateCountCheckSpec profileDuplicateCount;

    @JsonPropertyDescription("Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.")
    private ColumnDuplicatePercentCheckSpec profileDuplicatePercent;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec profileAnomalyDifferencingDistinctCount30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyDifferencingDistinctCountCheckSpec profileAnomalyDifferencingDistinctCount;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.")
    private ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec profileAnomalyStationaryDistinctPercent30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnAnomalyStationaryDistinctPercentCheckSpec profileAnomalyStationaryDistinctPercent;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctCountCheckSpec profileChangeDistinctCount;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctCountSince7DaysCheckSpec profileChangeDistinctCountSince7Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctCountSince30DaysCheckSpec profileChangeDistinctCountSince30Days;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctCountSinceYesterdayCheckSpec profileChangeDistinctCountSinceYesterday;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnChangeDistinctPercentCheckSpec profileChangeDistinctPercent;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnChangeDistinctPercentSince7DaysCheckSpec profileChangeDistinctPercentSince7Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnChangeDistinctPercentSince30DaysCheckSpec profileChangeDistinctPercentSince30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnChangeDistinctPercentSinceYesterdayCheckSpec profileChangeDistinctPercentSinceYesterday;

    /**
     * Returns a distinct count check specification.
     * @return Distinct count check specification.
     */
    public ColumnDistinctCountCheckSpec getProfileDistinctCount() {
        return profileDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count check.
     * @param profileDistinctCount Distinct count check specification.
     */
    public void setProfileDistinctCount(ColumnDistinctCountCheckSpec profileDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctCount, profileDistinctCount));
        this.profileDistinctCount = profileDistinctCount;
        propagateHierarchyIdToField(profileDistinctCount, "profile_distinct_count");
    }

    /**
     * Returns a distinct percent check specification.
     * @return Distinct percent check specification.
     */
    public ColumnDistinctPercentCheckSpec getProfileDistinctPercent() {
        return profileDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent check.
     * @param profileDistinctPercent Distinct percent check specification.
     */
    public void setProfileDistinctPercent(ColumnDistinctPercentCheckSpec profileDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctPercent, profileDistinctPercent));
        this.profileDistinctPercent = profileDistinctPercent;
        propagateHierarchyIdToField(profileDistinctPercent, "profile_distinct_percent");
    }

    /**
     * Returns a duplicate count check specification.
     * @return Duplicate count check specification.
     */
    public ColumnDuplicateCountCheckSpec getProfileDuplicateCount() {
        return profileDuplicateCount;
    }

    /**
     * Sets a new specification of a duplicate count check.
     * @param profileDuplicateCount Duplicate count check specification.
     */
    public void setProfileDuplicateCount(ColumnDuplicateCountCheckSpec profileDuplicateCount) {
        this.setDirtyIf(!Objects.equals(this.profileDuplicateCount, profileDuplicateCount));
        this.profileDuplicateCount = profileDuplicateCount;
        propagateHierarchyIdToField(profileDuplicateCount, "profile_duplicate_count");
    }

    /**
     * Returns a duplicate percent check specification.
     * @return Duplicate percent check specification.
     */
    public ColumnDuplicatePercentCheckSpec getProfileDuplicatePercent() {
        return profileDuplicatePercent;
    }

    /**
     * Sets a new specification of a duplicate percent check.
     * @param profileDuplicatePercent Duplicate percent check specification.
     */
    public void setProfileDuplicatePercent(ColumnDuplicatePercentCheckSpec profileDuplicatePercent) {
        this.setDirtyIf(!Objects.equals(this.profileDuplicatePercent, profileDuplicatePercent));
        this.profileDuplicatePercent = profileDuplicatePercent;
        propagateHierarchyIdToField(profileDuplicatePercent, "profile_duplicate_percent");
    }

    /**
     * Returns a distinct count value anomaly 30 days check specification.
     * @return Distinct count value anomaly 30 days check specification.
     */
    public ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec getProfileAnomalyDifferencingDistinctCount30Days() {
        return profileAnomalyDifferencingDistinctCount30Days;
    }

    /**
     * Sets a new specification of a distinct count value anomaly 30 days check.
     * @param profileAnomalyDifferencingDistinctCount30Days Distinct count value anomaly 30 days check specification.
     */
    public void setProfileAnomalyDifferencingDistinctCount30Days(ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec profileAnomalyDifferencingDistinctCount30Days) {
        this.setDirtyIf(!Objects.equals(this.profileAnomalyDifferencingDistinctCount30Days, profileAnomalyDifferencingDistinctCount30Days));
        this.profileAnomalyDifferencingDistinctCount30Days = profileAnomalyDifferencingDistinctCount30Days;
        propagateHierarchyIdToField(profileAnomalyDifferencingDistinctCount30Days, "profile_anomaly_differencing_distinct_count_30_days");
    }

    /**
     * Returns a distinct count value anomaly check specification.
     * @return Distinct count value anomaly check specification.
     */
    public ColumnAnomalyDifferencingDistinctCountCheckSpec getProfileAnomalyDifferencingDistinctCount() {
        return profileAnomalyDifferencingDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value anomaly check.
     * @param profileAnomalyDifferencingDistinctCount Distinct count value anomaly check specification.
     */
    public void setProfileAnomalyDifferencingDistinctCount(ColumnAnomalyDifferencingDistinctCountCheckSpec profileAnomalyDifferencingDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.profileAnomalyDifferencingDistinctCount, profileAnomalyDifferencingDistinctCount));
        this.profileAnomalyDifferencingDistinctCount = profileAnomalyDifferencingDistinctCount;
        propagateHierarchyIdToField(profileAnomalyDifferencingDistinctCount, "profile_anomaly_differencing_distinct_count");
    }

    /**
     * Returns a distinct percent value anomaly 30 days check specification.
     * @return Distinct percent value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec getProfileAnomalyDifferencingDistinctPercent30Days() {
        return profileAnomalyStationaryDistinctPercent30Days;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly 30 days check.
     * @param profileAnomalyStationaryDistinctPercent30Days Distinct percent value anomaly 30 days check specification.
     */
    public void setProfileAnomalyStationaryDistinctPercent30Days(ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec profileAnomalyStationaryDistinctPercent30Days) {
        this.setDirtyIf(!Objects.equals(this.profileAnomalyStationaryDistinctPercent30Days, profileAnomalyStationaryDistinctPercent30Days));
        this.profileAnomalyStationaryDistinctPercent30Days = profileAnomalyStationaryDistinctPercent30Days;
        propagateHierarchyIdToField(profileAnomalyStationaryDistinctPercent30Days, "profile_anomaly_stationary_distinct_percent_30_days");
    }

    /**
     * Returns a distinct percent value anomaly check specification.
     * @return Distinct percent value anomaly check specification.
     */
    public ColumnAnomalyStationaryDistinctPercentCheckSpec getProfileAnomalyStationaryDistinctPercent() {
        return profileAnomalyStationaryDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly check.
     * @param profileAnomalyStationaryDistinctPercent Distinct percent value anomaly check specification.
     */
    public void setProfileAnomalyStationaryDistinctPercent(ColumnAnomalyStationaryDistinctPercentCheckSpec profileAnomalyStationaryDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.profileAnomalyStationaryDistinctPercent, profileAnomalyStationaryDistinctPercent));
        this.profileAnomalyStationaryDistinctPercent = profileAnomalyStationaryDistinctPercent;
        propagateHierarchyIdToField(profileAnomalyStationaryDistinctPercent, "profile_anomaly_stationary_distinct_percent");
    }

    /**
     * Returns the distinct count value change check specification.
     * @return Distinct count value change check specification.
     */
    public ColumnChangeDistinctCountCheckSpec getProfileChangeDistinctCount() {
        return profileChangeDistinctCount;
    }

    /**
     * Sets a new specification of a distinct count value change check.
     * @param profileChangeDistinctCount Distinct count value change check specification.
     */
    public void setProfileChangeDistinctCount(ColumnChangeDistinctCountCheckSpec profileChangeDistinctCount) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctCount, profileChangeDistinctCount));
        this.profileChangeDistinctCount = profileChangeDistinctCount;
        propagateHierarchyIdToField(profileChangeDistinctCount, "profile_change_distinct_count");
    }

    /**
     * Returns the distinct count value change since 7 days check specification.
     * @return Distinct count value change since 7 days check specification.
     */
    public ColumnChangeDistinctCountSince7DaysCheckSpec getProfileChangeDistinctCountSince7Days() {
        return profileChangeDistinctCountSince7Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 7 days check.
     * @param profileChangeDistinctCountSince7Days Distinct count value change since 7 days check specification.
     */
    public void setProfileChangeDistinctCountSince7Days(ColumnChangeDistinctCountSince7DaysCheckSpec profileChangeDistinctCountSince7Days) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctCountSince7Days, profileChangeDistinctCountSince7Days));
        this.profileChangeDistinctCountSince7Days = profileChangeDistinctCountSince7Days;
        propagateHierarchyIdToField(profileChangeDistinctCountSince7Days, "profile_change_distinct_count_since_7_days");
    }

    /**
     * Returns the distinct count value change since 30 days check specification.
     * @return Distinct count value change since 30 days check specification.
     */
    public ColumnChangeDistinctCountSince30DaysCheckSpec getProfileChangeDistinctCountSince30Days() {
        return profileChangeDistinctCountSince30Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 30 days check.
     * @param profileChangeDistinctCountSince30Days Distinct count value change since 30 days check specification.
     */
    public void setProfileChangeDistinctCountSince30Days(ColumnChangeDistinctCountSince30DaysCheckSpec profileChangeDistinctCountSince30Days) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctCountSince30Days, profileChangeDistinctCountSince30Days));
        this.profileChangeDistinctCountSince30Days = profileChangeDistinctCountSince30Days;
        propagateHierarchyIdToField(profileChangeDistinctCountSince30Days, "profile_change_distinct_count_since_30_days");
    }

    /**
     * Returns the distinct count value change since yesterday check specification.
     * @return Distinct count value change since yesterday check specification.
     */
    public ColumnChangeDistinctCountSinceYesterdayCheckSpec getProfileChangeDistinctCountSinceYesterday() {
        return profileChangeDistinctCountSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count value change since yesterday check .
     * @param profileChangeDistinctCountSinceYesterday Distinct count value change since yesterday check specification.
     */
    public void setProfileChangeDistinctCountSinceYesterday(ColumnChangeDistinctCountSinceYesterdayCheckSpec profileChangeDistinctCountSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctCountSinceYesterday, profileChangeDistinctCountSinceYesterday));
        this.profileChangeDistinctCountSinceYesterday = profileChangeDistinctCountSinceYesterday;
        propagateHierarchyIdToField(profileChangeDistinctCountSinceYesterday, "profile_change_distinct_count_since_yesterday");
    }

    /**
     * Returns the distinct percent value change check specification.
     * @return Distinct percent value change check specification.
     */
    public ColumnChangeDistinctPercentCheckSpec getProfileChangeDistinctPercent() {
        return profileChangeDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value change check.
     * @param profileChangeDistinctPercent Distinct percent value change check specification.
     */
    public void setProfileChangeDistinctPercent(ColumnChangeDistinctPercentCheckSpec profileChangeDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctPercent, profileChangeDistinctPercent));
        this.profileChangeDistinctPercent = profileChangeDistinctPercent;
        propagateHierarchyIdToField(profileChangeDistinctPercent, "profile_change_distinct_percent");
    }

    /**
     * Returns the distinct percent value change since 7 days check specification.
     * @return Distinct count percent change since 7 days check specification.
     */
    public ColumnChangeDistinctPercentSince7DaysCheckSpec getProfileChangeDistinctPercentSince7Days() {
        return profileChangeDistinctPercentSince7Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 7 days check.
     * @param profileChangeDistinctPercentSince7Days Distinct count percent change since 7 days check specification.
     */
    public void setProfileChangeDistinctPercentSince7Days(ColumnChangeDistinctPercentSince7DaysCheckSpec profileChangeDistinctPercentSince7Days) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctPercentSince7Days, profileChangeDistinctPercentSince7Days));
        this.profileChangeDistinctPercentSince7Days = profileChangeDistinctPercentSince7Days;
        propagateHierarchyIdToField(profileChangeDistinctPercentSince7Days, "profile_change_distinct_percent_since_7_days");
    }

    /**
     * Returns the distinct percent value change since 30 days check specification.
     * @return Distinct count percent change since 30 days check specification.
     */
    public ColumnChangeDistinctPercentSince30DaysCheckSpec getProfileChangeDistinctPercentSince30Days() {
        return profileChangeDistinctPercentSince30Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 30 days check specification.
     * @param profileChangeDistinctPercentSince30Days Distinct count percent change since 30 days check specification.
     */
    public void setProfileChangeDistinctPercentSince30Days(ColumnChangeDistinctPercentSince30DaysCheckSpec profileChangeDistinctPercentSince30Days) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctPercentSince30Days, profileChangeDistinctPercentSince30Days));
        this.profileChangeDistinctPercentSince30Days = profileChangeDistinctPercentSince30Days;
        propagateHierarchyIdToField(profileChangeDistinctPercentSince30Days, "profile_change_distinct_percent_since_30_days");
    }

    /**
     * Returns the distinct percent value change since yesterday check specification.
     * @return Distinct count percent change since yesterday check specification.
     */
    public ColumnChangeDistinctPercentSinceYesterdayCheckSpec getProfileChangeDistinctPercentSinceYesterday() {
        return profileChangeDistinctPercentSinceYesterday;
    }

    /**
     * Sets a new specification of a distinct count percent change since yesterday check specification.
     * @param profileChangeDistinctPercentSinceYesterday Distinct count percent change since yesterday check specification.
     */
    public void setProfileChangeDistinctPercentSinceYesterday(ColumnChangeDistinctPercentSinceYesterdayCheckSpec profileChangeDistinctPercentSinceYesterday) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctPercentSinceYesterday, profileChangeDistinctPercentSinceYesterday));
        this.profileChangeDistinctPercentSinceYesterday = profileChangeDistinctPercentSinceYesterday;
        propagateHierarchyIdToField(profileChangeDistinctPercentSinceYesterday, "profile_change_distinct_percent_since_yesterday");
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
    public ColumnUniquenessProfilingChecksSpec deepClone() {
        return (ColumnUniquenessProfilingChecksSpec)super.deepClone();
    }
}
