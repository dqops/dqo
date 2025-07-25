/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.partitioned.whitespace;

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
 * Container of whitespace values detection data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnWhitespaceDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnWhitespaceDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_empty_text_found", o -> o.dailyPartitionEmptyTextFound);
            put("daily_partition_whitespace_text_found", o -> o.dailyPartitionWhitespaceTextFound);
            put("daily_partition_null_placeholder_text_found", o -> o.dailyPartitionNullPlaceholderTextFound);
            put("daily_partition_empty_text_percent", o -> o.dailyPartitionEmptyTextPercent);
            put("daily_partition_whitespace_text_percent", o -> o.dailyPartitionWhitespaceTextPercent);
            put("daily_partition_null_placeholder_text_percent", o -> o.dailyPartitionNullPlaceholderTextPercent);

            put("daily_partition_text_surrounded_by_whitespace_found", o -> o.dailyPartitionTextSurroundedByWhitespaceFound);
            put("daily_partition_text_surrounded_by_whitespace_percent", o -> o.dailyPartitionTextSurroundedByWhitespacePercent);
        }
    };

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each daily partition.")
    private ColumnWhitespaceEmptyTextFoundCheckSpec dailyPartitionEmptyTextFound;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each daily partition.")
    private ColumnWhitespaceWhitespaceTextFoundCheckSpec dailyPartitionWhitespaceTextFound;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each daily partition.")
    private ColumnWhitespaceNullPlaceholderTextFoundCheckSpec dailyPartitionNullPlaceholderTextFound;

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnWhitespaceEmptyTextPercentCheckSpec dailyPartitionEmptyTextPercent;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each daily partition.")
    private ColumnWhitespaceWhitespaceTextPercentCheckSpec dailyPartitionWhitespaceTextPercent;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each daily partition.")
    private ColumnWhitespaceNullPlaceholderTextPercentCheckSpec dailyPartitionNullPlaceholderTextPercent;

    @JsonPropertyDescription("Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec dailyPartitionTextSurroundedByWhitespaceFound;

    @JsonPropertyDescription("This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec dailyPartitionTextSurroundedByWhitespacePercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnWhitespaceEmptyTextFoundCheckSpec getDailyPartitionEmptyTextFound() {
        return dailyPartitionEmptyTextFound;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyPartitionEmptyTextFound Max string empty count check.
     */
    public void setDailyPartitionEmptyTextFound(ColumnWhitespaceEmptyTextFoundCheckSpec dailyPartitionEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionEmptyTextFound, dailyPartitionEmptyTextFound));
        this.dailyPartitionEmptyTextFound = dailyPartitionEmptyTextFound;
        propagateHierarchyIdToField(dailyPartitionEmptyTextFound, "daily_partition_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnWhitespaceWhitespaceTextFoundCheckSpec getDailyPartitionWhitespaceTextFound() {
        return dailyPartitionWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param dailyPartitionWhitespaceTextFound Maximum string whitespace count check.
     */
    public void setDailyPartitionWhitespaceTextFound(ColumnWhitespaceWhitespaceTextFoundCheckSpec dailyPartitionWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionWhitespaceTextFound, dailyPartitionWhitespaceTextFound));
        this.dailyPartitionWhitespaceTextFound = dailyPartitionWhitespaceTextFound;
        propagateHierarchyIdToField(dailyPartitionWhitespaceTextFound, "daily_partition_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnWhitespaceNullPlaceholderTextFoundCheckSpec getDailyPartitionNullPlaceholderTextFound() {
        return dailyPartitionNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param dailyPartitionNullPlaceholderTextFound Maximum string null placeholder count check.
     */
    public void setDailyPartitionNullPlaceholderTextFound(ColumnWhitespaceNullPlaceholderTextFoundCheckSpec dailyPartitionNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullPlaceholderTextFound, dailyPartitionNullPlaceholderTextFound));
        this.dailyPartitionNullPlaceholderTextFound = dailyPartitionNullPlaceholderTextFound;
        propagateHierarchyIdToField(dailyPartitionNullPlaceholderTextFound, "daily_partition_null_placeholder_text_found");
    }

    /**
     * Returns a maximum empty string percentage check.
     * @return Maximum empty string percentage check.
     */
    public ColumnWhitespaceEmptyTextPercentCheckSpec getDailyPartitionEmptyTextPercent() {
        return dailyPartitionEmptyTextPercent;
    }

    /**
     * Sets a new definition of a maximum empty string percentage check.
     * @param dailyPartitionEmptyTextPercent Maximum empty string percentage check.
     */
    public void setDailyPartitionEmptyTextPercent(ColumnWhitespaceEmptyTextPercentCheckSpec dailyPartitionEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionEmptyTextPercent, dailyPartitionEmptyTextPercent));
        this.dailyPartitionEmptyTextPercent = dailyPartitionEmptyTextPercent;
        propagateHierarchyIdToField(dailyPartitionEmptyTextPercent, "daily_partition_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnWhitespaceWhitespaceTextPercentCheckSpec getDailyPartitionWhitespaceTextPercent() {
        return dailyPartitionWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param dailyPartitionWhitespaceTextPercent Maximum string whitespace percent check.
     */
    public void setDailyPartitionWhitespaceTextPercent(ColumnWhitespaceWhitespaceTextPercentCheckSpec dailyPartitionWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionWhitespaceTextPercent, dailyPartitionWhitespaceTextPercent));
        this.dailyPartitionWhitespaceTextPercent = dailyPartitionWhitespaceTextPercent;
        propagateHierarchyIdToField(dailyPartitionWhitespaceTextPercent, "daily_partition_whitespace_text_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnWhitespaceNullPlaceholderTextPercentCheckSpec getDailyPartitionNullPlaceholderTextPercent() {
        return dailyPartitionNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param dailyPartitionNullPlaceholderTextPercent Maximum string null placeholder percent check.
     */
    public void setDailyPartitionNullPlaceholderTextPercent(ColumnWhitespaceNullPlaceholderTextPercentCheckSpec dailyPartitionNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullPlaceholderTextPercent, dailyPartitionNullPlaceholderTextPercent));
        this.dailyPartitionNullPlaceholderTextPercent = dailyPartitionNullPlaceholderTextPercent;
        propagateHierarchyIdToField(dailyPartitionNullPlaceholderTextPercent, "daily_partition_null_placeholder_text_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec getDailyPartitionTextSurroundedByWhitespaceFound() {
        return dailyPartitionTextSurroundedByWhitespaceFound;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param dailyPartitionTextSurroundedByWhitespaceFound String surrounded by whitespace count check.
     */
    public void setDailyPartitionTextSurroundedByWhitespaceFound(ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec dailyPartitionTextSurroundedByWhitespaceFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextSurroundedByWhitespaceFound, dailyPartitionTextSurroundedByWhitespaceFound));
        this.dailyPartitionTextSurroundedByWhitespaceFound = dailyPartitionTextSurroundedByWhitespaceFound;
        propagateHierarchyIdToField(dailyPartitionTextSurroundedByWhitespaceFound, "daily_partition_text_surrounded_by_whitespace_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec getDailyPartitionTextSurroundedByWhitespacePercent() {
        return dailyPartitionTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param dailyPartitionTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setDailyPartitionTextSurroundedByWhitespacePercent(ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec dailyPartitionTextSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextSurroundedByWhitespacePercent, dailyPartitionTextSurroundedByWhitespacePercent));
        this.dailyPartitionTextSurroundedByWhitespacePercent = dailyPartitionTextSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyPartitionTextSurroundedByWhitespacePercent, "daily_partition_text_surrounded_by_whitespace_percent");
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
        return DataTypeCategory.STRING;
    }
}
