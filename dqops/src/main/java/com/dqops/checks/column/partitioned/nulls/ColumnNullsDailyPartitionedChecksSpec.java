/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.partitioned.nulls;

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
            put("daily_partition_nulls_percent_anomaly", o ->o.dailyPartitionNullsPercentAnomaly);

            put("daily_partition_not_nulls_count", o -> o.dailyPartitionNotNullsCount);
            put("daily_partition_not_nulls_percent", o -> o.dailyPartitionNotNullsPercent);

            put("daily_partition_empty_column_found", o -> o.dailyPartitionEmptyColumnFound);

            put("daily_partition_nulls_percent_change", o ->o.dailyPartitionNullsPercentChange);
            put("daily_partition_nulls_percent_change_1_day", o ->o.dailyPartitionNullsPercentChange1Day);
            put("daily_partition_nulls_percent_change_7_days", o ->o.dailyPartitionNullsPercentChange7Days);
            put("daily_partition_nulls_percent_change_30_days", o ->o.dailyPartitionNullsPercentChange30Days);
        }
    };

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Counts the number of rows having a null value. Raises a data quality issue when the count of null values is above a max_count threshold. Stores a separate data quality check result for each daily partition.")
    private ColumnNullsCountCheckSpec dailyPartitionNullsCount;

    @JsonPropertyDescription("Detects incomplete columns that contain any null values. Measures the percentage of rows having a null value. Raises a data quality issue when the percentage of null values is above a max_percent threshold. Stores a separate data quality check result for each daily partition.")
    private ColumnNullsPercentCheckSpec dailyPartitionNullsPercent;

    @JsonPropertyDescription("Detects day-to-day anomalies in the percentage of null values. Raises a data quality issue when the rate of null values increases or decreases too much during the last 90 days.")
    private ColumnNullPercentAnomalyStationaryCheckSpec dailyPartitionNullsPercentAnomaly;

    @JsonPropertyDescription("Verifies that a column contains a minimum number of non-null values. The default value of the *min_count* parameter is 1 to detect at least one value in a monitored column. Raises a data quality issue when the count of non-null values is below min_count. Stores a separate data quality check result for each daily partition.")
    private ColumnNotNullsCountCheckSpec dailyPartitionNotNullsCount;

    @JsonPropertyDescription("Detects columns that contain too many non-null values. Measures the percentage of rows that have non-null values. Raises a data quality issue when the percentage of non-null values is above max_percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnNotNullsPercentCheckSpec dailyPartitionNotNullsPercent;

    @JsonPropertyDescription("Detects empty columns that contain only null values. Counts the number of rows that have non-null values. Raises a data quality issue when the column is empty. Stores a separate data quality check result for each daily partition.")
    private ColumnEmptyColumnFoundCheckSpec dailyPartitionEmptyColumnFound;

    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since last readout.")
    private ColumnNullPercentChangeCheckSpec dailyPartitionNullsPercentChange;

    @JsonProperty("daily_partition_nulls_percent_change_1_day")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.")
    private ColumnNullPercentChange1DayCheckSpec dailyPartitionNullsPercentChange1Day;

    @JsonProperty("daily_partition_nulls_percent_change_7_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.")
    private ColumnNullPercentChange7DaysCheckSpec dailyPartitionNullsPercentChange7Days;

    @JsonProperty("daily_partition_nulls_percent_change_30_days")
    @JsonPropertyDescription("Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.")
    private ColumnNullPercentChange30DaysCheckSpec dailyPartitionNullsPercentChange30Days;

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
     * Returns a null percent value anomaly 90 days check specification.
     * @return Null percent value anomaly 90 days check specification.
     */
    public ColumnNullPercentAnomalyStationaryCheckSpec getDailyPartitionNullsPercentAnomaly() {
        return dailyPartitionNullsPercentAnomaly;
    }

    /**
     * Sets a new specification of a null percent value anomaly 90 days check.
     * @param dailyPartitionNullsPercentAnomaly Null percent value anomaly 90 days check specification.
     */
    public void setDailyPartitionNullsPercentAnomaly(ColumnNullPercentAnomalyStationaryCheckSpec dailyPartitionNullsPercentAnomaly) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentAnomaly, dailyPartitionNullsPercentAnomaly));
        this.dailyPartitionNullsPercentAnomaly = dailyPartitionNullsPercentAnomaly;
        propagateHierarchyIdToField(dailyPartitionNullsPercentAnomaly, "daily_partition_nulls_percent_anomaly");
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
     * Returns an empty column found check specification.
     * @return Empty column found check specification.
     */
    public ColumnEmptyColumnFoundCheckSpec getDailyPartitionEmptyColumnFound() {
        return dailyPartitionEmptyColumnFound;
    }

    /**
     * Sets an empty column found check specification
     * @param dailyPartitionEmptyColumnFound Empty column found check specification.
     */
    public void setDailyPartitionEmptyColumnFound(ColumnEmptyColumnFoundCheckSpec dailyPartitionEmptyColumnFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionEmptyColumnFound, dailyPartitionEmptyColumnFound));
        this.dailyPartitionEmptyColumnFound = dailyPartitionEmptyColumnFound;
        propagateHierarchyIdToField(dailyPartitionEmptyColumnFound, "daily_partition_empty_column_found");
    }

    /**
     * Returns the null percent value change check.
     * @return Null percent value change check.
     */
    public ColumnNullPercentChangeCheckSpec getDailyPartitionNullsPercentChange() {
        return dailyPartitionNullsPercentChange;
    }

    /**
     * Sets a new null percent value change check.
     * @param dailyPartitionNullsPercentChange Null percent value change check.
     */
    public void setDailyPartitionNullsPercentChange(ColumnNullPercentChangeCheckSpec dailyPartitionNullsPercentChange) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentChange, dailyPartitionNullsPercentChange));
        this.dailyPartitionNullsPercentChange = dailyPartitionNullsPercentChange;
        propagateHierarchyIdToField(dailyPartitionNullsPercentChange, "daily_partition_nulls_percent_change");
    }

    /**
     * Returns the null percent value change yesterday check.
     * @return Null percent value change yesterday check.
     */
    public ColumnNullPercentChange1DayCheckSpec getDailyPartitionNullsPercentChange1Day() {
        return dailyPartitionNullsPercentChange1Day;
    }

    /**
     * Sets a new null percent value change yesterday check.
     * @param dailyPartitionNullsPercentChange1Day Null percent value change yesterday check.
     */
    public void setDailyPartitionNullsPercentChange1Day(ColumnNullPercentChange1DayCheckSpec dailyPartitionNullsPercentChange1Day) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentChange1Day, dailyPartitionNullsPercentChange1Day));
        this.dailyPartitionNullsPercentChange1Day = dailyPartitionNullsPercentChange1Day;
        propagateHierarchyIdToField(dailyPartitionNullsPercentChange1Day, "daily_partition_nulls_percent_change_1_day");
    }

    /**
     * Returns the null percent value change 7 days check.
     * @return Null percent value change 7 days check.
     */
    public ColumnNullPercentChange7DaysCheckSpec getDailyPartitionNullsPercentChange7Days() {
        return dailyPartitionNullsPercentChange7Days;
    }

    /**
     * Sets a new null percent value change 7 days check.
     * @param dailyPartitionNullsPercentChange7Days Null percent value change 7 days check.
     */
    public void setDailyPartitionNullsPercentChange7Days(ColumnNullPercentChange7DaysCheckSpec dailyPartitionNullsPercentChange7Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentChange7Days, dailyPartitionNullsPercentChange7Days));
        this.dailyPartitionNullsPercentChange7Days = dailyPartitionNullsPercentChange7Days;
        propagateHierarchyIdToField(dailyPartitionNullsPercentChange7Days, "daily_partition_nulls_percent_change_7_days");
    }

    /**
     * Returns the null percent value change 30 days check.
     * @return Null percent value change 30 days check.
     */
    public ColumnNullPercentChange30DaysCheckSpec getDailyPartitionNullsPercentChange30Days() {
        return dailyPartitionNullsPercentChange30Days;
    }

    /**
     * Sets a new null percent value change 30 days check.
     * @param dailyPartitionNullsPercentChange30Days Null percent value change 30 days check.
     */
    public void setDailyPartitionNullsPercentChange30Days(ColumnNullPercentChange30DaysCheckSpec dailyPartitionNullsPercentChange30Days) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullsPercentChange30Days, dailyPartitionNullsPercentChange30Days));
        this.dailyPartitionNullsPercentChange30Days = dailyPartitionNullsPercentChange30Days;
        propagateHierarchyIdToField(dailyPartitionNullsPercentChange30Days, "daily_partition_nulls_percent_change_30_days");
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

    public static class ColumnNullsDailyPartitionedChecksSpecSampleFactory implements SampleValueFactory<ColumnNullsDailyPartitionedChecksSpec> {
        @Override
        public ColumnNullsDailyPartitionedChecksSpec createSample() {
            return new ColumnNullsDailyPartitionedChecksSpec() {{
                setDailyPartitionNullsCount(new ColumnNullsCountCheckSpec.ColumnNullsCountCheckSpecSampleFactory().createSample());
            }};
        }
    }
}
