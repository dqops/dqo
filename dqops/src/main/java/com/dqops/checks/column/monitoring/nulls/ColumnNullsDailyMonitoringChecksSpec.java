/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.monitoring.nulls;

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
            put("daily_nulls_percent_anomaly", o ->o.dailyNullsPercentAnomaly);

            put("daily_not_nulls_count", o -> o.dailyNotNullsCount);
            put("daily_not_nulls_percent", o -> o.dailyNotNullsPercent);

            put("daily_empty_column_found", o -> o.dailyEmptyColumnFound);

            put("daily_nulls_percent_change", o ->o.dailyNullsPercentChange);
            put("daily_nulls_percent_change_1_day", o ->o.dailyNullsPercentChange1Day);
            put("daily_nulls_percent_change_7_days", o ->o.dailyNullsPercentChange7Days);
            put("daily_nulls_percent_change_30_days", o ->o.dailyNullsPercentChange30Days);
        }
    };

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Counts the number of rows having a null value. Raises a data quality issue when the count of null values is above a max_count threshold. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNullsCountCheckSpec dailyNullsCount;

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Measures the percentage of rows having a null value. Raises a data quality issue when the percentage of null values is above a max_percent threshold. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNullsPercentCheckSpec dailyNullsPercent;

    @JsonPropertyDescription("Detects day-to-day anomalies in the percentage of null values. Raises a data quality issue when the rate of null values increases or decreases too much during the last 90 days.")
    private ColumnNullPercentAnomalyStationaryCheckSpec dailyNullsPercentAnomaly;

    @JsonPropertyDescription("Verifies that a column contains a minimum number of non-null values. The default value of the *min_count* parameter is 1 to detect at least one value in a monitored column. Raises a data quality issue when the count of non-null values is below min_count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnNotNullsCountCheckSpec dailyNotNullsCount;

    @JsonPropertyDescription("Detects columns that contain too many non-null values. Measures the percentage of rows that have non-null values. Raises a data quality issue when the percentage of non-null values is above max_percentage.")
    private ColumnNotNullsPercentCheckSpec dailyNotNullsPercent;

    @JsonPropertyDescription("Detects empty columns that contain only null values. Counts the number of rows that have non-null values. Raises a data quality issue when the column is empty. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnEmptyColumnFoundCheckSpec dailyEmptyColumnFound;

    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since the last readout.")
    private ColumnNullPercentChangeCheckSpec dailyNullsPercentChange;

    @JsonProperty("daily_nulls_percent_change_1_day")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.")
    private ColumnNullPercentChange1DayCheckSpec dailyNullsPercentChange1Day;

    @JsonProperty("daily_nulls_percent_change_7_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.")
    private ColumnNullPercentChange7DaysCheckSpec dailyNullsPercentChange7Days;

    @JsonProperty("daily_nulls_percent_change_30_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.")
    private ColumnNullPercentChange30DaysCheckSpec dailyNullsPercentChange30Days;

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
     * Returns a null percent value anomaly 90 days check specification.
     * @return Null percent value anomaly 90 days check specification.
     */
    public ColumnNullPercentAnomalyStationaryCheckSpec getDailyNullsPercentAnomaly() {
        return dailyNullsPercentAnomaly;
    }

    /**
     * Sets a new specification of a null percent value anomaly 90 days check.
     * @param dailyNullsPercentAnomaly Null percent value anomaly 90 days check specification.
     */
    public void setDailyNullsPercentAnomaly(ColumnNullPercentAnomalyStationaryCheckSpec dailyNullsPercentAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentAnomaly, dailyNullsPercentAnomaly));
        this.dailyNullsPercentAnomaly = dailyNullsPercentAnomaly;
        propagateHierarchyIdToField(dailyNullsPercentAnomaly, "daily_nulls_percent_anomaly");
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
     * Returns an empty column found check specification.
     * @return Empty column found check specification.
     */
    public ColumnEmptyColumnFoundCheckSpec getDailyEmptyColumnFound() {
        return dailyEmptyColumnFound;
    }

    /**
     * Sets an empty column found check specification
     * @param dailyEmptyColumnFound Empty column found check specification.
     */
    public void setDailyEmptyColumnFound(ColumnEmptyColumnFoundCheckSpec dailyEmptyColumnFound) {
        this.setDirtyIf(!Objects.equals(this.dailyEmptyColumnFound, dailyEmptyColumnFound));
        this.dailyEmptyColumnFound = dailyEmptyColumnFound;
        propagateHierarchyIdToField(dailyEmptyColumnFound, "daily_empty_column_found");
    }

    /**
     * Returns the null percent value change check.
     * @return Null percent value change check.
     */
    public ColumnNullPercentChangeCheckSpec getDailyNullsPercentChange() {
        return dailyNullsPercentChange;
    }

    /**
     * Sets a new null percent value change check.
     * @param dailyNullsPercentChange Null percent value change check.
     */
    public void setDailyNullsPercentChange(ColumnNullPercentChangeCheckSpec dailyNullsPercentChange) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentChange, dailyNullsPercentChange));
        this.dailyNullsPercentChange = dailyNullsPercentChange;
        propagateHierarchyIdToField(dailyNullsPercentChange, "daily_nulls_percent_change");
    }

    /**
     * Returns the null percent value change yesterday check.
     * @return Null percent value change yesterday check.
     */
    public ColumnNullPercentChange1DayCheckSpec getDailyNullsPercentChange1Day() {
        return dailyNullsPercentChange1Day;
    }

    /**
     * Sets a new null percent value change yesterday check.
     * @param dailyNullsPercentChange1Day Null percent value change yesterday check.
     */
    public void setDailyNullsPercentChange1Day(ColumnNullPercentChange1DayCheckSpec dailyNullsPercentChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentChange1Day, dailyNullsPercentChange1Day));
        this.dailyNullsPercentChange1Day = dailyNullsPercentChange1Day;
        propagateHierarchyIdToField(dailyNullsPercentChange1Day, "daily_nulls_percent_change_1_day");
    }

    /**
     * Returns the null percent value change 7 days check.
     * @return Null percent value change 7 days check.
     */
    public ColumnNullPercentChange7DaysCheckSpec getDailyNullsPercentChange7Days() {
        return dailyNullsPercentChange7Days;
    }

    /**
     * Sets a new null percent value change 7 days check.
     * @param dailyNullsPercentChange7Days Null percent value change 7 days check.
     */
    public void setDailyNullsPercentChange7Days(ColumnNullPercentChange7DaysCheckSpec dailyNullsPercentChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentChange7Days, dailyNullsPercentChange7Days));
        this.dailyNullsPercentChange7Days = dailyNullsPercentChange7Days;
        propagateHierarchyIdToField(dailyNullsPercentChange7Days, "daily_nulls_percent_change_7_days");
    }

    /**
     * Returns the null percent value change 30 days check.
     * @return Null percent value change 30 days check.
     */
    public ColumnNullPercentChange30DaysCheckSpec getDailyNullsPercentChange30Days() {
        return dailyNullsPercentChange30Days;
    }

    /**
     * Sets a new null percent value change 30 days check.
     * @param dailyNullsPercentChange30Days Null percent value change 30 days check.
     */
    public void setDailyNullsPercentChange30Days(ColumnNullPercentChange30DaysCheckSpec dailyNullsPercentChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyNullsPercentChange30Days, dailyNullsPercentChange30Days));
        this.dailyNullsPercentChange30Days = dailyNullsPercentChange30Days;
        propagateHierarchyIdToField(dailyNullsPercentChange30Days, "daily_nulls_percent_change_30_days");
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

    public static class ColumnNullsDailyMonitoringChecksSpecSampleFactory implements SampleValueFactory<ColumnNullsDailyMonitoringChecksSpec> {
        @Override
        public ColumnNullsDailyMonitoringChecksSpec createSample() {
            return new ColumnNullsDailyMonitoringChecksSpec() {{
                setDailyNullsCount(new ColumnNullsCountCheckSpec.ColumnNullsCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
