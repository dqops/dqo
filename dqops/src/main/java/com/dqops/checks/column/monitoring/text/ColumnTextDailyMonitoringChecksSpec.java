/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.monitoring.text;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.text.*;
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
 * Container of text data quality monitoring checks on a column level that are monitoring tables at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_text_min_length", o -> o.dailyTextMinLength);
            put("daily_text_max_length", o -> o.dailyTextMaxLength);
            put("daily_text_mean_length", o -> o.dailyTextMeanLength);
            put("daily_text_length_below_min_length", o -> o.dailyTextLengthBelowMinLength);
            put("daily_text_length_below_min_length_percent", o -> o.dailyTextLengthBelowMinLengthPercent);
            put("daily_text_length_above_max_length", o -> o.dailyTextLengthAboveMaxLength);
            put("daily_text_length_above_max_length_percent", o -> o.dailyTextLengthAboveMaxLengthPercent);
            put("daily_text_length_in_range_percent", o -> o.dailyTextLengthInRangePercent);

            put("daily_min_word_count", o -> o.dailyMinWordCount);
            put("daily_max_word_count", o -> o.dailyMaxWordCount);
        }
    };

    @JsonPropertyDescription("This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextMinLengthCheckSpec dailyTextMinLength;

    @JsonPropertyDescription("This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextMaxLengthCheckSpec dailyTextMaxLength;

    @JsonPropertyDescription("Verifies that the mean (average) length of texts in a column is within an accepted range. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextMeanLengthCheckSpec dailyTextMeanLength;

    @JsonPropertyDescription("The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthCheckSpec dailyTextLengthBelowMinLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec dailyTextLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthCheckSpec dailyTextLengthAboveMaxLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec dailyTextLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextLengthInRangePercentCheckSpec dailyTextLengthInRangePercent;

    @JsonPropertyDescription("This check finds the lowest word count of text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the text contains too less words.")
    private ColumnTextMinWordCountCheckSpec dailyMinWordCount;

    @JsonPropertyDescription("This check finds the highest word count of text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the text contains too many words.")
    private ColumnTextMaxWordCountCheckSpec dailyMaxWordCount;

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getDailyTextMinLength() {
        return dailyTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param dailyTextMinLength Minimum string length check.
     */
    public void setDailyTextMinLength(ColumnTextMinLengthCheckSpec dailyTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextMinLength, dailyTextMinLength));
        this.dailyTextMinLength = dailyTextMinLength;
        propagateHierarchyIdToField(dailyTextMinLength, "daily_text_min_length");
    }

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getDailyTextMaxLength() {
        return dailyTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param dailyTextMaxLength Maximum string length check.
     */
    public void setDailyTextMaxLength(ColumnTextMaxLengthCheckSpec dailyTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextMaxLength, dailyTextMaxLength));
        this.dailyTextMaxLength = dailyTextMaxLength;
        propagateHierarchyIdToField(dailyTextMaxLength, "daily_text_max_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getDailyTextMeanLength() {
        return dailyTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param dailyTextMeanLength Mean string length check.
     */
    public void setDailyTextMeanLength(ColumnTextMeanLengthCheckSpec dailyTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextMeanLength, dailyTextMeanLength));
        this.dailyTextMeanLength = dailyTextMeanLength;
        propagateHierarchyIdToField(dailyTextMeanLength, "daily_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getDailyTextLengthBelowMinLength() {
        return dailyTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param dailyTextLengthBelowMinLength String length below min length count check.
     */
    public void setDailyTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec dailyTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthBelowMinLength, dailyTextLengthBelowMinLength));
        this.dailyTextLengthBelowMinLength = dailyTextLengthBelowMinLength;
        propagateHierarchyIdToField(dailyTextLengthBelowMinLength, "daily_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getDailyTextLengthBelowMinLengthPercent() {
        return dailyTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param dailyTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setDailyTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec dailyTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthBelowMinLengthPercent, dailyTextLengthBelowMinLengthPercent));
        this.dailyTextLengthBelowMinLengthPercent = dailyTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(dailyTextLengthBelowMinLengthPercent, "daily_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getDailyTextLengthAboveMaxLength() {
        return dailyTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param dailyTextLengthAboveMaxLength String length above max length count check.
     */
    public void setDailyTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec dailyTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthAboveMaxLength, dailyTextLengthAboveMaxLength));
        this.dailyTextLengthAboveMaxLength = dailyTextLengthAboveMaxLength;
        propagateHierarchyIdToField(dailyTextLengthAboveMaxLength, "daily_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getDailyTextLengthAboveMaxLengthPercent() {
        return dailyTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param dailyTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setDailyTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec dailyTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthAboveMaxLengthPercent, dailyTextLengthAboveMaxLengthPercent));
        this.dailyTextLengthAboveMaxLengthPercent = dailyTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(dailyTextLengthAboveMaxLengthPercent, "daily_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getDailyTextLengthInRangePercent() {
        return dailyTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param dailyTextLengthInRangePercent String length in range percent check.
     */
    public void setDailyTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec dailyTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextLengthInRangePercent, dailyTextLengthInRangePercent));
        this.dailyTextLengthInRangePercent = dailyTextLengthInRangePercent;
        propagateHierarchyIdToField(dailyTextLengthInRangePercent, "daily_text_length_in_range_percent");
    }

    /**
     * Returns a text min word count percent check.
     * @return Text min word count percent check.
     */
    public ColumnTextMinWordCountCheckSpec getDailyMinWordCount() {
        return dailyMinWordCount;
    }

    /**
     * Sets a new definition of a text min word count percent check.
     * @param dailyMinWordCount Text min word count percent check.
     */
    public void setDailyMinWordCount(ColumnTextMinWordCountCheckSpec dailyMinWordCount) {
        this.setDirtyIf(!Objects.equals(this.dailyMinWordCount, dailyMinWordCount));
        this.dailyMinWordCount = dailyMinWordCount;
        propagateHierarchyIdToField(dailyMinWordCount, "daily_min_word_count");
    }

    /**
     * Returns a text max word count percent check.
     * @return Text max word count percent check.
     */
    public ColumnTextMaxWordCountCheckSpec getDailyMaxWordCount() {
        return dailyMaxWordCount;
    }

    /**
     * Sets a new definition of a text max word count percent check.
     * @param dailyMaxWordCount Text max word count percent check.
     */
    public void setDailyMaxWordCount(ColumnTextMaxWordCountCheckSpec dailyMaxWordCount) {
        this.setDirtyIf(!Objects.equals(this.dailyMaxWordCount, dailyMaxWordCount));
        this.dailyMaxWordCount = dailyMaxWordCount;
        propagateHierarchyIdToField(dailyMaxWordCount, "daily_max_word_count");
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
    public ColumnTextDailyMonitoringChecksSpec deepClone() {
        return (ColumnTextDailyMonitoringChecksSpec)super.deepClone();
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
