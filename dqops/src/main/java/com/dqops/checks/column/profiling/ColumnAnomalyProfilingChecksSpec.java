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
import com.dqops.checks.column.checkspecs.anomaly.*;
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
 * Container of built-in preconfigured data quality checks on a column level for detecting anomalies.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAnomalyProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAnomalyProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_mean_anomaly_stationary", o -> o.profileMeanAnomalyStationary);
            put("profile_mean_anomaly_stationary_30_days", o -> o.profileMeanAnomalyStationary30Days);

            put("profile_median_anomaly_stationary", o -> o.profileMedianAnomalyStationary);
            put("profile_median_anomaly_stationary_30_days", o -> o.profileMedianAnomalyStationary30Days);

            put("profile_sum_anomaly_differencing", o -> o.profileSumAnomalyDifferencing);
            put("profile_sum_anomaly_differencing_30_days", o -> o.profileSumAnomalyDifferencing30Days);

            put("profile_mean_change", o -> o.profileMeanChange);
            put("profile_mean_change_1_day", o -> o.profileMeanChange1Day);
            put("profile_mean_change_7_days", o -> o.profileMeanChange7Days);
            put("profile_mean_change_30_days", o -> o.profileMeanChange30Days);

            put("profile_median_change", o -> o.profileMedianChange);
            put("profile_median_change_yesterday", o -> o.profileMedianChange1Day);
            put("profile_median_change_7_days", o -> o.profileMedianChange7Days);
            put("profile_median_change_30_days", o -> o.profileMedianChange30Days);

            put("profile_sum_change", o -> o.profileSumChange);
            put("profile_sum_change_yesterday", o -> o.profileSumChange1Day);
            put("profile_sum_change_7_days", o -> o.profileSumChange7Days);
            put("profile_sum_change_30_days", o -> o.profileSumChange30Days);
        }
    };

    @JsonProperty("profile_mean_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryMeanCheckSpec profileMeanAnomalyStationary;

    @JsonProperty("profile_mean_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryMean30DaysCheckSpec profileMeanAnomalyStationary30Days;

    @JsonProperty("profile_median_anomaly_stationary")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyStationaryMedianCheckSpec profileMedianAnomalyStationary;

    @JsonProperty("profile_median_anomaly_stationary_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyStationaryMedian30DaysCheckSpec profileMedianAnomalyStationary30Days;

    @JsonProperty("profile_sum_anomaly_differencing_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.")
    private ColumnAnomalyDifferencingSum30DaysCheckSpec profileSumAnomalyDifferencing30Days;

    @JsonProperty("profile_sum_anomaly_differencing")
    @JsonPropertyDescription("Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.")
    private ColumnAnomalyDifferencingSumCheckSpec profileSumAnomalyDifferencing;
    
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout.")
    private ColumnMeanChangeCheckSpec profileMeanChange;

    @JsonProperty("profile_mean_change_1_day")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnMeanChange1DayCheckSpec profileMeanChange1Day;

    @JsonProperty("profile_mean_change_7_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last week.")
    private ColumnMeanChange7DaysCheckSpec profileMeanChange7Days;

    @JsonProperty("profile_mean_change_30_days")
    @JsonPropertyDescription("Verifies that the mean value in a column changed in a fixed rate since last readout from last month.")
    private ColumnMeanChange30DaysCheckSpec profileMeanChange30Days;

    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout.")
    private ColumnMedianChangeCheckSpec profileMedianChange;

    @JsonProperty("profile_median_change_1_day")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnMedianChange1DayCheckSpec profileMedianChange1Day;

    @JsonProperty("profile_median_change_7_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last week.")
    private ColumnMedianChange7DaysCheckSpec profileMedianChange7Days;

    @JsonProperty("profile_median_change_30_days")
    @JsonPropertyDescription("Verifies that the median in a column changed in a fixed rate since last readout from last month.")
    private ColumnMedianChange30DaysCheckSpec profileMedianChange30Days;

    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout.")
    private ColumnSumChangeCheckSpec profileSumChange;

    @JsonProperty("profile_sum_change_1_day")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnSumChange1DayCheckSpec profileSumChange1Day;

    @JsonProperty("profile_sum_change_7_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last week.")
    private ColumnSumChange7DaysCheckSpec profileSumChange7Days;

    @JsonProperty("profile_sum_change_30_days")
    @JsonPropertyDescription("Verifies that the sum in a column changed in a fixed rate since last readout from last month.")
    private ColumnSumChange30DaysCheckSpec profileSumChange30Days;

    /**
     * Returns a mean value anomaly 90 days check specification.
     * @return Mean value anomaly 90 days check specification.
     */
    public ColumnAnomalyStationaryMeanCheckSpec getProfileMeanAnomalyStationary() {
        return profileMeanAnomalyStationary;
    }

    /**
     * Sets a new specification of a mean value anomaly 90 days check.
     * @param profileMeanAnomalyStationary Mean value anomaly 90 days check specification.
     */
    public void setProfileMeanAnomalyStationary(ColumnAnomalyStationaryMeanCheckSpec profileMeanAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.profileMeanAnomalyStationary, profileMeanAnomalyStationary));
        this.profileMeanAnomalyStationary = profileMeanAnomalyStationary;
        propagateHierarchyIdToField(profileMeanAnomalyStationary, "profile_mean_anomaly_stationary");
    }

    /**
     * Returns a mean value anomaly 30 days check specification.
     * @return Mean value anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryMean30DaysCheckSpec getProfileMeanAnomalyStationary30Days() {
        return profileMeanAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a mean value anomaly 30 days check.
     * @param profileMeanAnomalyStationary30Days Mean value anomaly 30 days check specification.
     */
    public void setProfileMeanAnomalyStationary30Days(ColumnAnomalyStationaryMean30DaysCheckSpec profileMeanAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.profileMeanAnomalyStationary30Days, profileMeanAnomalyStationary30Days));
        this.profileMeanAnomalyStationary30Days = profileMeanAnomalyStationary30Days;
        propagateHierarchyIdToField(profileMeanAnomalyStationary30Days, "profile_mean_anomaly_stationary_30_days");
    }

    /**
     * Returns a median anomaly 90 days check specification.
     * @return Median anomaly 90 days check specification.
     */
    public ColumnAnomalyStationaryMedianCheckSpec getProfileMedianAnomalyStationary() {
        return profileMedianAnomalyStationary;
    }

    /**
     * Sets a new specification of a median anomaly 90 days check.
     * @param profileMedianAnomalyStationary Median anomaly 90 days check specification.
     */
    public void setProfileMedianAnomalyStationary(ColumnAnomalyStationaryMedianCheckSpec profileMedianAnomalyStationary) {
        this.setDirtyIf(!Objects.equals(this.profileMedianAnomalyStationary, profileMedianAnomalyStationary));
        this.profileMedianAnomalyStationary = profileMedianAnomalyStationary;
        propagateHierarchyIdToField(profileMedianAnomalyStationary, "profile_median_anomaly_stationary");
    }

    /**
     * Returns a median anomaly 30 days check specification.
     * @return Median anomaly 30 days check specification.
     */
    public ColumnAnomalyStationaryMedian30DaysCheckSpec getProfileMedianAnomalyStationary30Days() {
        return profileMedianAnomalyStationary30Days;
    }

    /**
     * Sets a new specification of a median anomaly 30 days check.
     * @param profileMedianAnomalyStationary30Days Median anomaly 30 days check specification.
     */
    public void setProfileMedianAnomalyStationary30Days(ColumnAnomalyStationaryMedian30DaysCheckSpec profileMedianAnomalyStationary30Days) {
        this.setDirtyIf(!Objects.equals(this.profileMedianAnomalyStationary30Days, profileMedianAnomalyStationary30Days));
        this.profileMedianAnomalyStationary30Days = profileMedianAnomalyStationary30Days;
        propagateHierarchyIdToField(profileMedianAnomalyStationary30Days, "profile_median_anomaly_stationary_30_days");
    }

    /**
     * Returns a sum anomaly 90 days check specification.
     * @return Sum anomaly 90 days check specification.
     */
    public ColumnAnomalyDifferencingSumCheckSpec getProfileSumAnomalyDifferencing() {
        return profileSumAnomalyDifferencing;
    }

    /**
     * Sets a new specification of a sum anomaly 90 days check.
     * @param profileSumAnomalyDifferencing Sum anomaly 90 days check specification.
     */
    public void setProfileSumAnomalyDifferencing(ColumnAnomalyDifferencingSumCheckSpec profileSumAnomalyDifferencing) {
        this.setDirtyIf(!Objects.equals(this.profileSumAnomalyDifferencing, profileSumAnomalyDifferencing));
        this.profileSumAnomalyDifferencing = profileSumAnomalyDifferencing;
        propagateHierarchyIdToField(profileSumAnomalyDifferencing, "profile_sum_anomaly_differencing");
    }

    /**
     * Returns a sum anomaly 30 days check specification.
     * @return Sum anomaly 30 days check specification.
     */
    public ColumnAnomalyDifferencingSum30DaysCheckSpec getProfileSumAnomalyDifferencing30Days() {
        return profileSumAnomalyDifferencing30Days;
    }

    /**
     * Sets a new specification of a sum anomaly 30 days check.
     * @param profileSumAnomalyDifferencing30Days Sum anomaly 30 days check specification.
     */
    public void setProfileSumAnomalyDifferencing30Days(ColumnAnomalyDifferencingSum30DaysCheckSpec profileSumAnomalyDifferencing30Days) {
        this.setDirtyIf(!Objects.equals(this.profileSumAnomalyDifferencing30Days, profileSumAnomalyDifferencing30Days));
        this.profileSumAnomalyDifferencing30Days = profileSumAnomalyDifferencing30Days;
        propagateHierarchyIdToField(profileSumAnomalyDifferencing30Days, "profile_sum_anomaly_differencing_30_days");
    }

    /**
     * Returns the mean value change check.
     * @return Mean value change check.
     */
    public ColumnMeanChangeCheckSpec getProfileMeanChange() {
        return profileMeanChange;
    }

    /**
     * Sets a new mean value change check.
     * @param profileMeanChange Mean value change check.
     */
    public void setProfileMeanChange(ColumnMeanChangeCheckSpec profileMeanChange) {
        this.setDirtyIf(!Objects.equals(this.profileMeanChange, profileMeanChange));
        this.profileMeanChange = profileMeanChange;
        propagateHierarchyIdToField(profileMeanChange, "profile_mean_change");
    }

    /**
     * Returns the mean value change yesterday check.
     * @return Mean value change yesterday check.
     */
    public ColumnMeanChange1DayCheckSpec getProfileMeanChange1Day() {
        return profileMeanChange1Day;
    }

    /**
     * Sets a new mean value change yesterday check.
     * @param profileMeanChange1Day Mean value change yesterday check.
     */
    public void setProfileMeanChange1Day(ColumnMeanChange1DayCheckSpec profileMeanChange1Day) {
        this.setDirtyIf(!Objects.equals(this.profileMeanChange1Day, profileMeanChange1Day));
        this.profileMeanChange1Day = profileMeanChange1Day;
        propagateHierarchyIdToField(profileMeanChange1Day, "profile_mean_change_1_day");
    }

    /**
     * Returns the mean value change 7 days check.
     * @return Mean value change 7 days check.
     */
    public ColumnMeanChange7DaysCheckSpec getProfileMeanChange7Days() {
        return profileMeanChange7Days;
    }

    /**
     * Sets a new mean value change 7 days check.
     * @param profileMeanChange7Days Mean value change 7 days check.
     */
    public void setProfileMeanChange7Days(ColumnMeanChange7DaysCheckSpec profileMeanChange7Days) {
        this.setDirtyIf(!Objects.equals(this.profileMeanChange7Days, profileMeanChange7Days));
        this.profileMeanChange7Days = profileMeanChange7Days;
        propagateHierarchyIdToField(profileMeanChange7Days, "profile_mean_change_7_days");
    }

    /**
     * Returns the mean value change 30 days check.
     * @return Mean value change 30 days check.
     */
    public ColumnMeanChange30DaysCheckSpec getProfileMeanChange30Days() {
        return profileMeanChange30Days;
    }

    /**
     * Sets a new mean value change 30 days check.
     * @param profileMeanChange30Days Mean value change 30 days check.
     */
    public void setProfileMeanChange30Days(ColumnMeanChange30DaysCheckSpec profileMeanChange30Days) {
        this.setDirtyIf(!Objects.equals(this.profileMeanChange30Days, profileMeanChange30Days));
        this.profileMeanChange30Days = profileMeanChange30Days;
        propagateHierarchyIdToField(profileMeanChange30Days, "profile_mean_change_30_days");
    }

    /**
     * Returns the median change check.
     * @return Median change check.
     */
    public ColumnMedianChangeCheckSpec getProfileMedianChange() {
        return profileMedianChange;
    }

    /**
     * Sets a new median change check.
     * @param profileMedianChange Median change check.
     */
    public void setProfileMedianChange(ColumnMedianChangeCheckSpec profileMedianChange) {
        this.setDirtyIf(!Objects.equals(this.profileMedianChange, profileMedianChange));
        this.profileMedianChange = profileMedianChange;
        propagateHierarchyIdToField(profileMedianChange, "profile_median_change");
    }

    /**
     * Returns the median change yesterday check.
     * @return Median change yesterday check.
     */
    public ColumnMedianChange1DayCheckSpec getProfileMedianChange1Day() {
        return profileMedianChange1Day;
    }

    /**
     * Sets a new median change yesterday check.
     * @param profileMedianChange1Day Median change yesterday check.
     */
    public void setProfileMedianChange1Day(ColumnMedianChange1DayCheckSpec profileMedianChange1Day) {
        this.setDirtyIf(!Objects.equals(this.profileMedianChange1Day, profileMedianChange1Day));
        this.profileMedianChange1Day = profileMedianChange1Day;
        propagateHierarchyIdToField(profileMedianChange1Day, "profile_median_change_1_day");
    }

    /**
     * Returns the median change 7 days check.
     * @return Median change 7 days check.
     */
    public ColumnMedianChange7DaysCheckSpec getProfileMedianChange7Days() {
        return profileMedianChange7Days;
    }

    /**
     * Sets a new median change 7 days check.
     * @param profileMedianChange7Days Median change 7 days check.
     */
    public void setProfileMedianChange7Days(ColumnMedianChange7DaysCheckSpec profileMedianChange7Days) {
        this.setDirtyIf(!Objects.equals(this.profileMedianChange7Days, profileMedianChange7Days));
        this.profileMedianChange7Days = profileMedianChange7Days;
        propagateHierarchyIdToField(profileMedianChange7Days, "profile_median_change_7_days");
    }

    /**
     * Returns the median change 30 days check.
     * @return Median change 30 days check.
     */
    public ColumnMedianChange30DaysCheckSpec getProfileMedianChange30Days() {
        return profileMedianChange30Days;
    }

    /**
     * Sets a new median change 30 days check.
     * @param profileMedianChange30Days Median change 30 days check.
     */
    public void setProfileMedianChange30Days(ColumnMedianChange30DaysCheckSpec profileMedianChange30Days) {
        this.setDirtyIf(!Objects.equals(this.profileMedianChange30Days, profileMedianChange30Days));
        this.profileMedianChange30Days = profileMedianChange30Days;
        propagateHierarchyIdToField(profileMedianChange30Days, "profile_median_change_30_days");
    }

    /**
     * Returns the sum change check.
     * @return Sum change check.
     */
    public ColumnSumChangeCheckSpec getProfileSumChange() {
        return profileSumChange;
    }

    /**
     * Sets a new sum change check.
     * @param profileSumChange Sum change check.
     */
    public void setProfileSumChange(ColumnSumChangeCheckSpec profileSumChange) {
        this.setDirtyIf(!Objects.equals(this.profileSumChange, profileSumChange));
        this.profileSumChange = profileSumChange;
        propagateHierarchyIdToField(profileSumChange, "profile_sum_change");
    }

    /**
     * Returns the sum change yesterday check.
     * @return Sum change yesterday check.
     */
    public ColumnSumChange1DayCheckSpec getProfileSumChange1Day() {
        return profileSumChange1Day;
    }

    /**
     * Sets a new sum change yesterday check.
     * @param profileSumChange1Day Sum change yesterday check.
     */
    public void setProfileSumChange1Day(ColumnSumChange1DayCheckSpec profileSumChange1Day) {
        this.setDirtyIf(!Objects.equals(this.profileSumChange1Day, profileSumChange1Day));
        this.profileSumChange1Day = profileSumChange1Day;
        propagateHierarchyIdToField(profileSumChange1Day, "profile_sum_change_1_day");
    }

    /**
     * Returns the sum change 7 days check.
     * @return Sum change 7 days check.
     */
    public ColumnSumChange7DaysCheckSpec getProfileSumChange7Days() {
        return profileSumChange7Days;
    }

    /**
     * Sets a new sum change 7 days check.
     * @param profileSumChange7Days Sum change 7 days check.
     */
    public void setProfileSumChange7Days(ColumnSumChange7DaysCheckSpec profileSumChange7Days) {
        this.setDirtyIf(!Objects.equals(this.profileSumChange7Days, profileSumChange7Days));
        this.profileSumChange7Days = profileSumChange7Days;
        propagateHierarchyIdToField(profileSumChange7Days, "profile_sum_change_7_days");
    }

    /**
     * Returns the sum change 30 days check.
     * @return Sum change 30 days check.
     */
    public ColumnSumChange30DaysCheckSpec getProfileSumChange30Days() {
        return profileSumChange30Days;
    }

    /**
     * Sets a new sum change 30 days check.
     * @param profileSumChange30Days Sum change 30 days check.
     */
    public void setProfileSumChange30Days(ColumnSumChange30DaysCheckSpec profileSumChange30Days) {
        this.setDirtyIf(!Objects.equals(this.profileSumChange30Days, profileSumChange30Days));
        this.profileSumChange30Days = profileSumChange30Days;
        propagateHierarchyIdToField(profileSumChange30Days, "profile_sum_change_30_days");
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
    public ColumnAnomalyProfilingChecksSpec deepClone() {
        return (ColumnAnomalyProfilingChecksSpec)super.deepClone();
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
