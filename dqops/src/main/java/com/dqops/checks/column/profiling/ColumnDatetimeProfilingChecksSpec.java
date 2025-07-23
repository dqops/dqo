/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.datetime.ColumnDateValuesInFuturePercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnTextMatchDateFormatPercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDateInRangePercentCheckSpec;
import com.dqops.connectors.DataTypeCategory;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Container of built-in preconfigured data quality checks on a column level that are checking for datetime.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_date_values_in_future_percent", o -> o.profileDateValuesInFuturePercent);
            put("profile_date_in_range_percent", o -> o.profileDateInRangePercent);
            put("profile_text_match_date_format_percent", o -> o.profileTextMatchDateFormatPercent);
        }
    };

    @JsonPropertyDescription("Detects dates in the future in date, datetime and timestamp columns. Measures a percentage of dates in the future. Raises a data quality issue when too many future dates are found.")
    private ColumnDateValuesInFuturePercentCheckSpec profileDateValuesInFuturePercent;

    @JsonPropertyDescription("Verifies that the dates in date, datetime, or timestamp columns are within a reasonable range of dates. The default configuration detects fake dates such as 1900-01-01 and 2099-12-31. Measures the percentage of valid dates and raises a data quality issue when too many dates are found.")
    private ColumnDateInRangePercentCheckSpec profileDateInRangePercent;

    @JsonPropertyDescription("Verifies that the values in text columns match one of the predefined date formats, such as an ISO 8601 date. Measures the percentage of valid date strings and raises a data quality issue when too many invalid date strings are found.")
    private ColumnTextMatchDateFormatPercentCheckSpec profileTextMatchDateFormatPercent;


    /**
     * Returns a date values in future percent check specification.
     * @return Date values in future percent check specification.
     */
    public ColumnDateValuesInFuturePercentCheckSpec getProfileDateValuesInFuturePercent() {
        return profileDateValuesInFuturePercent;
    }

    /**
     * Sets a new date values in future percent check specification.
     * @param profileDateValuesInFuturePercent Date values in future percent check specification.
     */
    public void setProfileDateValuesInFuturePercent(ColumnDateValuesInFuturePercentCheckSpec profileDateValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.profileDateValuesInFuturePercent, profileDateValuesInFuturePercent));
        this.profileDateValuesInFuturePercent = profileDateValuesInFuturePercent;
        propagateHierarchyIdToField(profileDateValuesInFuturePercent, "profile_date_values_in_future_percent");
    }

    /**
     * Returns a datetime value in range date percentage check.
     * @return Maximum datetime value in range date percentage check.
     */
    public ColumnDateInRangePercentCheckSpec getProfileDateInRangePercent() {
        return profileDateInRangePercent;
    }

    /**
     * Sets a new definition of a datetime value in range date percentage check.
     * @param profileDateInRangePercent Datetime value in range date percentage check.
     */
    public void setProfileDateInRangePercent(ColumnDateInRangePercentCheckSpec profileDateInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.profileDateInRangePercent, profileDateInRangePercent));
        this.profileDateInRangePercent = profileDateInRangePercent;
        propagateHierarchyIdToField(profileDateInRangePercent, "profile_date_in_range_percent");
    }

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnTextMatchDateFormatPercentCheckSpec getProfileTextMatchDateFormatPercent() {
        return profileTextMatchDateFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param profileTextMatchDateFormatPercent Date match format percentage check.
     */
    public void setProfileTextMatchDateFormatPercent(ColumnTextMatchDateFormatPercentCheckSpec profileTextMatchDateFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextMatchDateFormatPercent, profileTextMatchDateFormatPercent));
        this.profileTextMatchDateFormatPercent = profileTextMatchDateFormatPercent;
        propagateHierarchyIdToField(profileTextMatchDateFormatPercent, "profile_text_match_date_format_percent");
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
    public ColumnDatetimeProfilingChecksSpec deepClone() {
        return (ColumnDatetimeProfilingChecksSpec)super.deepClone();
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
        return DataTypeCategory.CONTAINS_DATE;
    }
}