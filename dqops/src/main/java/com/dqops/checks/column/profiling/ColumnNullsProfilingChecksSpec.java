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
import com.dqops.checks.column.checkspecs.nulls.*;
import com.dqops.connectors.DataTypeCategory;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for nulls.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnNullsProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnNullsProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_nulls_count", o -> o.profileNullsCount);
            put("profile_nulls_percent", o -> o.profileNullsPercent);
            put("profile_nulls_percent_anomaly", o ->o.profileNullsPercentAnomaly);

            put("profile_not_nulls_count", o -> o.profileNotNullsCount);
            put("profile_not_nulls_percent", o -> o.profileNotNullsPercent);

            put("profile_empty_column_found", o -> o.profileEmptyColumnFound);

            put("profile_nulls_percent_change", o ->o.profileNullsPercentChange);
            put("profile_nulls_percent_change_1_day", o ->o.profileNullsPercentChange1Day);
            put("profile_nulls_percent_change_7_days", o ->o.profileNullsPercentChange7Days);
            put("profile_nulls_percent_change_30_days", o ->o.profileNullsPercentChange30Days);
        }
    };

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Counts the number of rows having a null value. Raises a data quality issue when the count of null values is above a max_count threshold.")
    private ColumnNullsCountCheckSpec profileNullsCount;

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Measures the percentage of rows having a null value. Raises a data quality issue when the percentage of null values is above a max_percent threshold.")
    private ColumnNullsPercentCheckSpec profileNullsPercent;

    @JsonPropertyDescription("Detects day-to-day anomalies in the percentage of null values. Raises a data quality issue when the rate of null values increases or decreases too much during the last 90 days.")
    private ColumnNullPercentAnomalyStationaryCheckSpec profileNullsPercentAnomaly;

    @JsonPropertyDescription("Verifies that a column contains a minimum number of non-null values. The default value of the *min_count* parameter is 1 to detect at least one value in a monitored column. Raises a data quality issue when the count of non-null values is below min_count.")
    private ColumnNotNullsCountCheckSpec profileNotNullsCount;

    @JsonPropertyDescription("Detects incomplete columns that contain too few non-null values. Measures the percentage of rows that have non-null values. Raises a data quality issue when the percentage of non-null values is below min_percentage.")
    private ColumnNotNullsPercentCheckSpec profileNotNullsPercent;

    @JsonPropertyDescription("Detects empty columns that contain only null values. Counts the number of rows that have non-null values. Raises a data quality issue when the column is empty.")
    private EmptyColumnFoundCheckSpec profileEmptyColumnFound;

    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout.")
    private ColumnNullPercentChangeCheckSpec profileNullsPercentChange;

    @JsonProperty("profile_nulls_percent_change_1_day")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.")
    private ColumnNullPercentChange1DayCheckSpec profileNullsPercentChange1Day;

    @JsonProperty("profile_nulls_percent_change_7_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.")
    private ColumnNullPercentChange7DaysCheckSpec profileNullsPercentChange7Days;

    @JsonProperty("profile_nulls_percent_change_30_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.")
    private ColumnNullPercentChange30DaysCheckSpec profileNullsPercentChange30Days;


    /**
     * Returns a nulls count check specification.
     * @return Nulls count check specification.
     */
    public ColumnNullsCountCheckSpec getProfileNullsCount() {
        return profileNullsCount;
    }

    /**
     * Sets a new nulls count check  specification.
     * @param profileNullsCount Nulls count check specification.
     */
    public void setProfileNullsCount(ColumnNullsCountCheckSpec profileNullsCount) {
        this.setDirtyIf(!Objects.equals(this.profileNullsCount, profileNullsCount));
        this.profileNullsCount = profileNullsCount;
        propagateHierarchyIdToField(profileNullsCount, "profile_nulls_count");
    }

    /**
     * Returns a nulls percent check specification.
     * @return Nulls percent check specification.
     */
    public ColumnNullsPercentCheckSpec getProfileNullsPercent() {
        return profileNullsPercent;
    }

    /**
     * Sets a new percent check specification.
     * @param profileNullsPercent Nulls percent check specification.
     */
    public void setProfileNullsPercent(ColumnNullsPercentCheckSpec profileNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.profileNullsPercent, profileNullsPercent));
        this.profileNullsPercent = profileNullsPercent;
        propagateHierarchyIdToField(profileNullsPercent, "profile_nulls_percent");
    }

    /**
     * Returns a null percent value anomaly 90 days check specification.
     * @return Null percent value anomaly 90 days check specification.
     */
    public ColumnNullPercentAnomalyStationaryCheckSpec getProfileNullsPercentAnomaly() {
        return profileNullsPercentAnomaly;
    }

    /**
     * Sets a new specification of a null percent value anomaly 90 days check.
     * @param profileNullsPercentAnomaly Null percent value anomaly 90 days check specification.
     */
    public void setProfileNullsPercentAnomaly(ColumnNullPercentAnomalyStationaryCheckSpec profileNullsPercentAnomaly) {
        this.setDirtyIf(!Objects.equals(this.profileNullsPercentAnomaly, profileNullsPercentAnomaly));
        this.profileNullsPercentAnomaly = profileNullsPercentAnomaly;
        propagateHierarchyIdToField(profileNullsPercentAnomaly, "profile_nulls_percent_anomaly");
    }

    /**
     * Returns a not nulls count check specification.
     * @return Not nulls count check specification.
     */
    public ColumnNotNullsCountCheckSpec getProfileNotNullsCount() {
        return profileNotNullsCount;
    }

    /**
     * Sets a new not nulls count check specification.
     * @param profileNotNullsCount Not nulls count check specification.
     */
    public void setProfileNotNullsCount(ColumnNotNullsCountCheckSpec profileNotNullsCount) {
        this.setDirtyIf(!Objects.equals(this.profileNotNullsCount, profileNotNullsCount));
        this.profileNotNullsCount = profileNotNullsCount;
        propagateHierarchyIdToField(profileNotNullsCount, "profile_not_nulls_count");
    }

    /**
     * Returns a not nulls percent check specification.
     * @return Not nulls percent check specification.
     */
    public ColumnNotNullsPercentCheckSpec getProfileNotNullsPercent() {
        return profileNotNullsPercent;
    }

    /**
     * Sets a new not null percent check specification.
     * @param profileNotNullsPercent Not nulls percent check specification.
     */
    public void setProfileNotNullsPercent(ColumnNotNullsPercentCheckSpec profileNotNullsPercent) {
        this.setDirtyIf(!Objects.equals(this.profileNotNullsPercent, profileNotNullsPercent));
        this.profileNotNullsPercent = profileNotNullsPercent;
        propagateHierarchyIdToField(profileNotNullsPercent, "profile_not_nulls_percent");
    }

    /**
     * Returns an empty column found check specification.
     * @return Empty column found check specification.
     */
    public EmptyColumnFoundCheckSpec getProfileEmptyColumnFound() {
        return profileEmptyColumnFound;
    }

    /**
     * Sets an empty column found check specification
     * @param profileEmptyColumnFound Empty column found check specification.
     */
    public void setProfileEmptyColumnFound(EmptyColumnFoundCheckSpec profileEmptyColumnFound) {
        this.setDirtyIf(!Objects.equals(this.profileEmptyColumnFound, profileEmptyColumnFound));
        this.profileEmptyColumnFound = profileEmptyColumnFound;
        propagateHierarchyIdToField(profileEmptyColumnFound, "profile_empty_column_found");
    }

    /**
     * Returns the null percent value change check.
     * @return Null percent value change check.
     */
    public ColumnNullPercentChangeCheckSpec getProfileNullsPercentChange() {
        return profileNullsPercentChange;
    }

    /**
     * Sets a new null percent value change check.
     * @param profileNullsPercentChange Null percent value change check.
     */
    public void setProfileNullsPercentChange(ColumnNullPercentChangeCheckSpec profileNullsPercentChange) {
        this.setDirtyIf(!Objects.equals(this.profileNullsPercentChange, profileNullsPercentChange));
        this.profileNullsPercentChange = profileNullsPercentChange;
        propagateHierarchyIdToField(profileNullsPercentChange, "profile_nulls_percent_change");
    }

    /**
     * Returns the null percent value change yesterday check.
     * @return Null percent value change yesterday check.
     */
    public ColumnNullPercentChange1DayCheckSpec getProfileNullsPercentChange1Day() {
        return profileNullsPercentChange1Day;
    }

    /**
     * Sets a new null percent value change yesterday check.
     * @param profileNullsPercentChange1Day Null percent value change yesterday check.
     */
    public void setProfileNullsPercentChange1Day(ColumnNullPercentChange1DayCheckSpec profileNullsPercentChange1Day) {
        this.setDirtyIf(!Objects.equals(this.profileNullsPercentChange1Day, profileNullsPercentChange1Day));
        this.profileNullsPercentChange1Day = profileNullsPercentChange1Day;
        propagateHierarchyIdToField(profileNullsPercentChange1Day, "profile_nulls_percent_change_1_day");
    }

    /**
     * Returns the null percent value change 7 days check.
     * @return Null percent value change 7 days check.
     */
    public ColumnNullPercentChange7DaysCheckSpec getProfileNullsPercentChange7Days() {
        return profileNullsPercentChange7Days;
    }

    /**
     * Sets a new null percent value change 7 days check.
     * @param profileNullsPercentChange7Days Null percent value change 7 days check.
     */
    public void setProfileNullsPercentChange7Days(ColumnNullPercentChange7DaysCheckSpec profileNullsPercentChange7Days) {
        this.setDirtyIf(!Objects.equals(this.profileNullsPercentChange7Days, profileNullsPercentChange7Days));
        this.profileNullsPercentChange7Days = profileNullsPercentChange7Days;
        propagateHierarchyIdToField(profileNullsPercentChange7Days, "profile_nulls_percent_change_7_days");
    }

    /**
     * Returns the null percent value change 30 days check.
     * @return Null percent value change 30 days check.
     */
    public ColumnNullPercentChange30DaysCheckSpec getProfileNullsPercentChange30Days() {
        return profileNullsPercentChange30Days;
    }

    /**
     * Sets a new null percent value change 30 days check.
     * @param profileNullsPercentChange30Days Null percent value change 30 days check.
     */
    public void setProfileNullsPercentChange30Days(ColumnNullPercentChange30DaysCheckSpec profileNullsPercentChange30Days) {
        this.setDirtyIf(!Objects.equals(this.profileNullsPercentChange30Days, profileNullsPercentChange30Days));
        this.profileNullsPercentChange30Days = profileNullsPercentChange30Days;
        propagateHierarchyIdToField(profileNullsPercentChange30Days, "profile_nulls_percent_change_30_days");
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

    public static class ColumnNullsProfilingChecksSpecSampleFactory implements SampleValueFactory<ColumnNullsProfilingChecksSpec> {
        @Override
        public ColumnNullsProfilingChecksSpec createSample() {
            return new ColumnNullsProfilingChecksSpec() {{
                setProfileNullsCount(new ColumnNullsCountCheckSpec.ColumnNullsCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
