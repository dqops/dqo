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

            put("profile_distinct_count_anomaly", o -> o.profileDistinctCountAnomaly);
            put("profile_distinct_percent_anomaly", o -> o.profileDistinctPercentAnomaly);

            put("profile_distinct_count_change", o -> o.profileDistinctCountChange);
            put("profile_distinct_count_change_1_day", o -> o.profileDistinctCountChange1Day);
            put("profile_distinct_count_change_7_days", o -> o.profileDistinctCountChange7Days);
            put("profile_distinct_count_change_30_days", o -> o.profileDistinctCountChange30Days);

            put("profile_distinct_percent_change", o -> o.profileChangeDistinctPercent);
            put("profile_distinct_percent_change_1_day", o -> o.profileChangeDistinctPercent1Day);
            put("profile_distinct_percent_change_7_days", o -> o.profileChangeDistinctPercent7Days);
            put("profile_distinct_percent_change_30_days", o -> o.profileChangeDistinctPercent30Days);
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

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnDistinctCountAnomalyDifferencingCheckSpec profileDistinctCountAnomaly;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.")
    private ColumnDistinctPercentAnomalyStationaryCheckSpec profileDistinctPercentAnomaly;

    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnDistinctCountChangeCheckSpec profileDistinctCountChange;

    @JsonProperty("profile_distinct_count_change_1_day")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnDistinctCountChange1DayCheckSpec profileDistinctCountChange1Day;

    @JsonProperty("profile_distinct_count_change_7_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnDistinctCountChange7DaysCheckSpec profileDistinctCountChange7Days;

    @JsonProperty("profile_distinct_count_change_30_days")
    @JsonPropertyDescription("Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnDistinctCountChange30DaysCheckSpec profileDistinctCountChange30Days;

    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.")
    private ColumnDistinctPercentChangeCheckSpec profileChangeDistinctPercent;

    @JsonProperty("profile_change_distinct_percent_1_day")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.")
    private ColumnDistinctPercentChange1DayCheckSpec profileChangeDistinctPercent1Day;

    @JsonProperty("profile_change_distinct_percent_7_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.")
    private ColumnDistinctPercentChange7DaysCheckSpec profileChangeDistinctPercent7Days;

    @JsonProperty("profile_change_distinct_percent_30_days")
    @JsonPropertyDescription("Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.")
    private ColumnDistinctPercentChange30DaysCheckSpec profileChangeDistinctPercent30Days;


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
     * Returns a distinct count value anomaly check specification.
     * @return Distinct count value anomaly check specification.
     */
    public ColumnDistinctCountAnomalyDifferencingCheckSpec getProfileDistinctCountAnomaly() {
        return profileDistinctCountAnomaly;
    }

    /**
     * Sets a new specification of a distinct count value anomaly check.
     * @param profileDistinctCountAnomaly Distinct count value anomaly check specification.
     */
    public void setProfileDistinctCountAnomaly(ColumnDistinctCountAnomalyDifferencingCheckSpec profileDistinctCountAnomaly) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctCountAnomaly, profileDistinctCountAnomaly));
        this.profileDistinctCountAnomaly = profileDistinctCountAnomaly;
        propagateHierarchyIdToField(profileDistinctCountAnomaly, "profile_distinct_count_anomaly");
    }

    /**
     * Returns a distinct percent value anomaly check specification.
     * @return Distinct percent value anomaly check specification.
     */
    public ColumnDistinctPercentAnomalyStationaryCheckSpec getProfileDistinctPercentAnomaly() {
        return profileDistinctPercentAnomaly;
    }

    /**
     * Sets a new specification of a distinct percent value anomaly check.
     * @param profileDistinctPercentAnomaly Distinct percent value anomaly check specification.
     */
    public void setProfileDistinctPercentAnomaly(ColumnDistinctPercentAnomalyStationaryCheckSpec profileDistinctPercentAnomaly) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctPercentAnomaly, profileDistinctPercentAnomaly));
        this.profileDistinctPercentAnomaly = profileDistinctPercentAnomaly;
        propagateHierarchyIdToField(profileDistinctPercentAnomaly, "profile_distinct_percent_anomaly");
    }

    /**
     * Returns the distinct count value change check specification.
     * @return Distinct count value change check specification.
     */
    public ColumnDistinctCountChangeCheckSpec getProfileDistinctCountChange() {
        return profileDistinctCountChange;
    }

    /**
     * Sets a new specification of a distinct count value change check.
     * @param profileDistinctCountChange Distinct count value change check specification.
     */
    public void setProfileDistinctCountChange(ColumnDistinctCountChangeCheckSpec profileDistinctCountChange) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctCountChange, profileDistinctCountChange));
        this.profileDistinctCountChange = profileDistinctCountChange;
        propagateHierarchyIdToField(profileDistinctCountChange, "profile_change_distinct_count");
    }

    /**
     * Returns the distinct count value change since yesterday check specification.
     * @return Distinct count value change since yesterday check specification.
     */
    public ColumnDistinctCountChange1DayCheckSpec getProfileDistinctCountChange1Day() {
        return profileDistinctCountChange1Day;
    }

    /**
     * Sets a new specification of a distinct count value change since yesterday check .
     * @param profileDistinctCountChange1Day Distinct count value change since yesterday check specification.
     */
    public void setProfileDistinctCountChange1Day(ColumnDistinctCountChange1DayCheckSpec profileDistinctCountChange1Day) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctCountChange1Day, profileDistinctCountChange1Day));
        this.profileDistinctCountChange1Day = profileDistinctCountChange1Day;
        propagateHierarchyIdToField(profileDistinctCountChange1Day, "profile_change_distinct_count_1_day");
    }

    /**
     * Returns the distinct count value change since 7 days check specification.
     * @return Distinct count value change since 7 days check specification.
     */
    public ColumnDistinctCountChange7DaysCheckSpec getProfileDistinctCountChange7Days() {
        return profileDistinctCountChange7Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 7 days check.
     * @param profileDistinctCountChange7Days Distinct count value change since 7 days check specification.
     */
    public void setProfileDistinctCountChange7Days(ColumnDistinctCountChange7DaysCheckSpec profileDistinctCountChange7Days) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctCountChange7Days, profileDistinctCountChange7Days));
        this.profileDistinctCountChange7Days = profileDistinctCountChange7Days;
        propagateHierarchyIdToField(profileDistinctCountChange7Days, "profile_change_distinct_count_7_days");
    }

    /**
     * Returns the distinct count value change since 30 days check specification.
     * @return Distinct count value change since 30 days check specification.
     */
    public ColumnDistinctCountChange30DaysCheckSpec getProfileDistinctCountChange30Days() {
        return profileDistinctCountChange30Days;
    }

    /**
     * Sets a new specification of a distinct count value change since 30 days check.
     * @param profileDistinctCountChange30Days Distinct count value change since 30 days check specification.
     */
    public void setProfileDistinctCountChange30Days(ColumnDistinctCountChange30DaysCheckSpec profileDistinctCountChange30Days) {
        this.setDirtyIf(!Objects.equals(this.profileDistinctCountChange30Days, profileDistinctCountChange30Days));
        this.profileDistinctCountChange30Days = profileDistinctCountChange30Days;
        propagateHierarchyIdToField(profileDistinctCountChange30Days, "profile_change_distinct_count_30_days");
    }

    /**
     * Returns the distinct percent value change check specification.
     * @return Distinct percent value change check specification.
     */
    public ColumnDistinctPercentChangeCheckSpec getProfileChangeDistinctPercent() {
        return profileChangeDistinctPercent;
    }

    /**
     * Sets a new specification of a distinct percent value change check.
     * @param profileChangeDistinctPercent Distinct percent value change check specification.
     */
    public void setProfileChangeDistinctPercent(ColumnDistinctPercentChangeCheckSpec profileChangeDistinctPercent) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctPercent, profileChangeDistinctPercent));
        this.profileChangeDistinctPercent = profileChangeDistinctPercent;
        propagateHierarchyIdToField(profileChangeDistinctPercent, "profile_change_distinct_percent");
    }

    /**
     * Returns the distinct percent value change since yesterday check specification.
     * @return Distinct count percent change since yesterday check specification.
     */
    public ColumnDistinctPercentChange1DayCheckSpec getProfileChangeDistinctPercent1Day() {
        return profileChangeDistinctPercent1Day;
    }

    /**
     * Sets a new specification of a distinct count percent change since yesterday check specification.
     * @param profileChangeDistinctPercent1Day Distinct count percent change since yesterday check specification.
     */
    public void setProfileChangeDistinctPercent1Day(ColumnDistinctPercentChange1DayCheckSpec profileChangeDistinctPercent1Day) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctPercent1Day, profileChangeDistinctPercent1Day));
        this.profileChangeDistinctPercent1Day = profileChangeDistinctPercent1Day;
        propagateHierarchyIdToField(profileChangeDistinctPercent1Day, "profile_change_distinct_percent_1_day");
    }

    /**
     * Returns the distinct percent value change since 7 days check specification.
     * @return Distinct count percent change since 7 days check specification.
     */
    public ColumnDistinctPercentChange7DaysCheckSpec getProfileChangeDistinctPercent7Days() {
        return profileChangeDistinctPercent7Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 7 days check.
     * @param profileChangeDistinctPercent7Days Distinct count percent change since 7 days check specification.
     */
    public void setProfileChangeDistinctPercent7Days(ColumnDistinctPercentChange7DaysCheckSpec profileChangeDistinctPercent7Days) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctPercent7Days, profileChangeDistinctPercent7Days));
        this.profileChangeDistinctPercent7Days = profileChangeDistinctPercent7Days;
        propagateHierarchyIdToField(profileChangeDistinctPercent7Days, "profile_change_distinct_percent_7_days");
    }

    /**
     * Returns the distinct percent value change since 30 days check specification.
     * @return Distinct count percent change since 30 days check specification.
     */
    public ColumnDistinctPercentChange30DaysCheckSpec getProfileChangeDistinctPercent30Days() {
        return profileChangeDistinctPercent30Days;
    }

    /**
     * Sets a new specification of a distinct count percent change since 30 days check specification.
     * @param profileChangeDistinctPercent30Days Distinct count percent change since 30 days check specification.
     */
    public void setProfileChangeDistinctPercent30Days(ColumnDistinctPercentChange30DaysCheckSpec profileChangeDistinctPercent30Days) {
        this.setDirtyIf(!Objects.equals(this.profileChangeDistinctPercent30Days, profileChangeDistinctPercent30Days));
        this.profileChangeDistinctPercent30Days = profileChangeDistinctPercent30Days;
        propagateHierarchyIdToField(profileChangeDistinctPercent30Days, "profile_change_distinct_percent_30_days");
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
        return CheckType.profiling;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return null;
    }
}
