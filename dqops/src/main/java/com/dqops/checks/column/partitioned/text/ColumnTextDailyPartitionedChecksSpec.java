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
package com.dqops.checks.column.partitioned.text;

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
 * Container of text data quality partitioned checks on a column level that are checking at a daily partition level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_text_min_length", o -> o.dailyPartitionTextMinLength);
            put("daily_partition_text_max_length", o -> o.dailyPartitionTextMaxLength);
            put("daily_partition_text_mean_length", o -> o.dailyPartitionTextMeanLength);
            put("daily_partition_text_length_below_min_length", o -> o.dailyPartitionTextLengthBelowMinLength);
            put("daily_partition_text_length_below_min_length_percent", o -> o.dailyPartitionTextLengthBelowMinLengthPercent);
            put("daily_partition_text_length_above_max_length", o -> o.dailyPartitionTextLengthAboveMaxLength);
            put("daily_partition_text_length_above_max_length_percent", o -> o.dailyPartitionTextLengthAboveMaxLengthPercent);
            put("daily_partition_text_length_in_range_percent", o -> o.dailyPartitionTextLengthInRangePercent);

            put("daily_partition_min_word_count", o -> o.dailyPartitionMinWordCount);
            put("daily_partition_max_word_count", o -> o.dailyPartitionMaxWordCount);
        }
    };


    @JsonPropertyDescription("This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextMinLengthCheckSpec dailyPartitionTextMinLength;

    @JsonPropertyDescription("This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextMaxLengthCheckSpec dailyPartitionTextMaxLength;

    @JsonPropertyDescription("Verifies that the mean (average) length of texts in a column is within an accepted range. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextMeanLengthCheckSpec dailyPartitionTextMeanLength;

    @JsonPropertyDescription("The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthBelowMinLengthCheckSpec dailyPartitionTextLengthBelowMinLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec dailyPartitionTextLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthAboveMaxLengthCheckSpec dailyPartitionTextLengthAboveMaxLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec dailyPartitionTextLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextLengthInRangePercentCheckSpec dailyPartitionTextLengthInRangePercent;

    @JsonPropertyDescription("This check finds the lowest word count of text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the text contains too less words.")
    private ColumnTextMinWordCountCheckSpec dailyPartitionMinWordCount;

    @JsonPropertyDescription("This check finds the highest word count of text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the text contains too many words.")
    private ColumnTextMaxWordCountCheckSpec dailyPartitionMaxWordCount;

    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getDailyPartitionTextMinLength() {
        return dailyPartitionTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param dailyPartitionTextMinLength Minimum string length check.
     */
    public void setDailyPartitionTextMinLength(ColumnTextMinLengthCheckSpec dailyPartitionTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMinLength, dailyPartitionTextMinLength));
        this.dailyPartitionTextMinLength = dailyPartitionTextMinLength;
        propagateHierarchyIdToField(dailyPartitionTextMinLength, "daily_partition_text_min_length");
    }

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getDailyPartitionTextMaxLength() {
        return dailyPartitionTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param dailyPartitionTextMaxLength Maximum string length check.
     */
    public void setDailyPartitionTextMaxLength(ColumnTextMaxLengthCheckSpec dailyPartitionTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMaxLength, dailyPartitionTextMaxLength));
        this.dailyPartitionTextMaxLength = dailyPartitionTextMaxLength;
        propagateHierarchyIdToField(dailyPartitionTextMaxLength, "daily_partition_text_max_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getDailyPartitionTextMeanLength() {
        return dailyPartitionTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param dailyPartitionTextMeanLength Mean string length check.
     */
    public void setDailyPartitionTextMeanLength(ColumnTextMeanLengthCheckSpec dailyPartitionTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMeanLength, dailyPartitionTextMeanLength));
        this.dailyPartitionTextMeanLength = dailyPartitionTextMeanLength;
        propagateHierarchyIdToField(dailyPartitionTextMeanLength, "daily_partition_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getDailyPartitionTextLengthBelowMinLength() {
        return dailyPartitionTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param dailyPartitionTextLengthBelowMinLength String length below min length count check.
     */
    public void setDailyPartitionTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec dailyPartitionTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthBelowMinLength, dailyPartitionTextLengthBelowMinLength));
        this.dailyPartitionTextLengthBelowMinLength = dailyPartitionTextLengthBelowMinLength;
        propagateHierarchyIdToField(dailyPartitionTextLengthBelowMinLength, "daily_partition_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getDailyPartitionTextLengthBelowMinLengthPercent() {
        return dailyPartitionTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param dailyPartitionTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setDailyPartitionTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec dailyPartitionTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthBelowMinLengthPercent, dailyPartitionTextLengthBelowMinLengthPercent));
        this.dailyPartitionTextLengthBelowMinLengthPercent = dailyPartitionTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(dailyPartitionTextLengthBelowMinLengthPercent, "daily_partition_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getDailyPartitionTextLengthAboveMaxLength() {
        return dailyPartitionTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param dailyPartitionTextLengthAboveMaxLength String length above max length count check.
     */
    public void setDailyPartitionTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec dailyPartitionTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthAboveMaxLength, dailyPartitionTextLengthAboveMaxLength));
        this.dailyPartitionTextLengthAboveMaxLength = dailyPartitionTextLengthAboveMaxLength;
        propagateHierarchyIdToField(dailyPartitionTextLengthAboveMaxLength, "daily_partition_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getDailyPartitionTextLengthAboveMaxLengthPercent() {
        return dailyPartitionTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param dailyPartitionTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setDailyPartitionTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec dailyPartitionTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthAboveMaxLengthPercent, dailyPartitionTextLengthAboveMaxLengthPercent));
        this.dailyPartitionTextLengthAboveMaxLengthPercent = dailyPartitionTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(dailyPartitionTextLengthAboveMaxLengthPercent, "daily_partition_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getDailyPartitionTextLengthInRangePercent() {
        return dailyPartitionTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param dailyPartitionTextLengthInRangePercent String length in range percent check.
     */
    public void setDailyPartitionTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec dailyPartitionTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextLengthInRangePercent, dailyPartitionTextLengthInRangePercent));
        this.dailyPartitionTextLengthInRangePercent = dailyPartitionTextLengthInRangePercent;
        propagateHierarchyIdToField(dailyPartitionTextLengthInRangePercent, "daily_partition_text_length_in_range_percent");
    }

    /**
     * Returns a text min word count percent check.
     * @return Text min word count percent check.
     */
    public ColumnTextMinWordCountCheckSpec getDailyPartitionMinWordCount() {
        return dailyPartitionMinWordCount;
    }

    /**
     * Sets a new definition of a text min word count percent check.
     * @param dailyPartitionMinWordCount Text min word count percent check.
     */
    public void setDailyPartitionMinWordCount(ColumnTextMinWordCountCheckSpec dailyPartitionMinWordCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMinWordCount, dailyPartitionMinWordCount));
        this.dailyPartitionMinWordCount = dailyPartitionMinWordCount;
        propagateHierarchyIdToField(dailyPartitionMinWordCount, "daily_partition_min_word_count");
    }

    /**
     * Returns a text max word count percent check.
     * @return Text max word count percent check.
     */
    public ColumnTextMaxWordCountCheckSpec getDailyPartitionMaxWordCount() {
        return dailyPartitionMaxWordCount;
    }

    /**
     * Sets a new definition of a text max word count percent check.
     * @param dailyPartitionMaxWordCount Text max word count percent check.
     */
    public void setDailyPartitionMaxWordCount(ColumnTextMaxWordCountCheckSpec dailyPartitionMaxWordCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionMaxWordCount, dailyPartitionMaxWordCount));
        this.dailyPartitionMaxWordCount = dailyPartitionMaxWordCount;
        propagateHierarchyIdToField(dailyPartitionMaxWordCount, "daily_partition_max_word_count");
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
