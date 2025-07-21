/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.monitoring.whitespace;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.whitespace.*;
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
 * Container of whitespace value detection data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnWhitespaceDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnWhitespaceDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_empty_text_found", o -> o.dailyEmptyTextFound);
            put("daily_whitespace_text_found", o -> o.dailyWhitespaceTextFound);
            put("daily_null_placeholder_text_found", o -> o.dailyNullPlaceholderTextFound);
            put("daily_empty_text_percent", o -> o.dailyEmptyTextPercent);
            put("daily_whitespace_text_percent", o -> o.dailyWhitespaceTextPercent);
            put("daily_null_placeholder_text_percent", o -> o.dailyNullPlaceholderTextPercent);

            put("daily_text_surrounded_by_whitespace_found", o -> o.dailyTextSurroundedByWhitespaceFound);
            put("daily_text_surrounded_by_whitespace_percent", o -> o.dailyTextSurroundedByWhitespacePercent);
        }
    };

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceEmptyTextFoundCheckSpec dailyEmptyTextFound;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceWhitespaceTextFoundCheckSpec dailyWhitespaceTextFound;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceNullPlaceholderTextFoundCheckSpec dailyNullPlaceholderTextFound;

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceEmptyTextPercentCheckSpec dailyEmptyTextPercent;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceWhitespaceTextPercentCheckSpec dailyWhitespaceTextPercent;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceNullPlaceholderTextPercentCheckSpec dailyNullPlaceholderTextPercent;

    @JsonPropertyDescription("Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec dailyTextSurroundedByWhitespaceFound;

    @JsonPropertyDescription("This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec dailyTextSurroundedByWhitespacePercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnWhitespaceEmptyTextFoundCheckSpec getDailyEmptyTextFound() {
        return dailyEmptyTextFound;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyEmptyTextFound Max string empty count check.
     */
    public void setDailyEmptyTextFound(ColumnWhitespaceEmptyTextFoundCheckSpec dailyEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyEmptyTextFound, dailyEmptyTextFound));
        this.dailyEmptyTextFound = dailyEmptyTextFound;
        propagateHierarchyIdToField(dailyEmptyTextFound, "daily_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnWhitespaceWhitespaceTextFoundCheckSpec getDailyWhitespaceTextFound() {
        return dailyWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param dailyWhitespaceTextFound Maximum string whitespace count check.
     */
    public void setDailyWhitespaceTextFound(ColumnWhitespaceWhitespaceTextFoundCheckSpec dailyWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyWhitespaceTextFound, dailyWhitespaceTextFound));
        this.dailyWhitespaceTextFound = dailyWhitespaceTextFound;
        propagateHierarchyIdToField(dailyWhitespaceTextFound, "daily_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnWhitespaceNullPlaceholderTextFoundCheckSpec getDailyNullPlaceholderTextFound() {
        return dailyNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param dailyNullPlaceholderTextFound Maximum string null placeholder count check.
     */
    public void setDailyNullPlaceholderTextFound(ColumnWhitespaceNullPlaceholderTextFoundCheckSpec dailyNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyNullPlaceholderTextFound, dailyNullPlaceholderTextFound));
        this.dailyNullPlaceholderTextFound = dailyNullPlaceholderTextFound;
        propagateHierarchyIdToField(dailyNullPlaceholderTextFound, "daily_null_placeholder_text_found");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnWhitespaceEmptyTextPercentCheckSpec getDailyEmptyTextPercent() {
        return dailyEmptyTextPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param dailyEmptyTextPercent Maximum string empty percent check.
     */
    public void setDailyEmptyTextPercent(ColumnWhitespaceEmptyTextPercentCheckSpec dailyEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyEmptyTextPercent, dailyEmptyTextPercent));
        this.dailyEmptyTextPercent = dailyEmptyTextPercent;
        propagateHierarchyIdToField(dailyEmptyTextPercent, "daily_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnWhitespaceWhitespaceTextPercentCheckSpec getDailyWhitespaceTextPercent() {
        return dailyWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param dailyWhitespaceTextPercent Maximum string whitespace percent check.
     */
    public void setDailyWhitespaceTextPercent(ColumnWhitespaceWhitespaceTextPercentCheckSpec dailyWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyWhitespaceTextPercent, dailyWhitespaceTextPercent));
        this.dailyWhitespaceTextPercent = dailyWhitespaceTextPercent;
        propagateHierarchyIdToField(dailyWhitespaceTextPercent, "daily_whitespace_text_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnWhitespaceNullPlaceholderTextPercentCheckSpec getDailyNullPlaceholderTextPercent() {
        return dailyNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param dailyNullPlaceholderTextPercent Maximum string null placeholder percent check.
     */
    public void setDailyNullPlaceholderTextPercent(ColumnWhitespaceNullPlaceholderTextPercentCheckSpec dailyNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNullPlaceholderTextPercent, dailyNullPlaceholderTextPercent));
        this.dailyNullPlaceholderTextPercent = dailyNullPlaceholderTextPercent;
        propagateHierarchyIdToField(dailyNullPlaceholderTextPercent, "daily_null_placeholder_text_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec getDailyTextSurroundedByWhitespaceFound() {
        return dailyTextSurroundedByWhitespaceFound;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param dailyTextSurroundedByWhitespaceFound String surrounded by whitespace count check.
     */
    public void setDailyTextSurroundedByWhitespaceFound(ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec dailyTextSurroundedByWhitespaceFound) {
        this.setDirtyIf(!Objects.equals(this.dailyTextSurroundedByWhitespaceFound, dailyTextSurroundedByWhitespaceFound));
        this.dailyTextSurroundedByWhitespaceFound = dailyTextSurroundedByWhitespaceFound;
        propagateHierarchyIdToField(dailyTextSurroundedByWhitespaceFound, "daily_text_surrounded_by_whitespace_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec getDailyTextSurroundedByWhitespacePercent() {
        return dailyTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param dailyTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setDailyTextSurroundedByWhitespacePercent(ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec dailyTextSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextSurroundedByWhitespacePercent, dailyTextSurroundedByWhitespacePercent));
        this.dailyTextSurroundedByWhitespacePercent = dailyTextSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyTextSurroundedByWhitespacePercent, "daily_text_surrounded_by_whitespace_percent");
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
    public ColumnWhitespaceDailyMonitoringChecksSpec deepClone() {
        return (ColumnWhitespaceDailyMonitoringChecksSpec)super.deepClone();
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
        return DataTypeCategory.STRING;
    }
}
