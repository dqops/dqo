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
import com.dqops.checks.column.checkspecs.blanks.ColumnTextSurroundedByWhitespaceCheckSpec;
import com.dqops.checks.column.checkspecs.blanks.ColumnTextSurroundedByWhitespacePercentCheckSpec;
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
 * Container of text data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_text_min_length", o -> o.monthlyPartitionTextMinLength);
            put("monthly_partition_text_max_length", o -> o.monthlyPartitionTextMaxLength);
            put("monthly_partition_text_mean_length", o -> o.monthlyPartitionTextMeanLength);
            put("monthly_partition_text_length_below_min_length", o -> o.monthlyPartitionTextLengthBelowMinLength);
            put("monthly_partition_text_length_below_min_length_percent", o -> o.monthlyPartitionTextLengthBelowMinLengthPercent);
            put("monthly_partition_text_length_above_max_length", o -> o.monthlyPartitionTextLengthAboveMaxLength);
            put("monthly_partition_text_length_above_max_length_percent", o -> o.monthlyPartitionTextLengthAboveMaxLengthPercent);
            put("monthly_partition_text_length_in_range_percent", o -> o.monthlyPartitionTextLengthInRangePercent);
        }
    };


    @JsonPropertyDescription("This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextMinLengthCheckSpec monthlyPartitionTextMinLength;

    @JsonPropertyDescription("This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextMaxLengthCheckSpec monthlyPartitionTextMaxLength;

    @JsonPropertyDescription("Verifies that the mean (average) length of texts in a column is within an accepted range. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextMeanLengthCheckSpec monthlyPartitionTextMeanLength;

    @JsonPropertyDescription("The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthBelowMinLengthCheckSpec monthlyPartitionTextLengthBelowMinLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyPartitionTextLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthAboveMaxLengthCheckSpec monthlyPartitionTextLengthAboveMaxLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyPartitionTextLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextLengthInRangePercentCheckSpec monthlyPartitionTextLengthInRangePercent;


    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getMonthlyPartitionTextMinLength() {
        return monthlyPartitionTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param monthlyPartitionTextMinLength Minimum string length check.
     */
    public void setMonthlyPartitionTextMinLength(ColumnTextMinLengthCheckSpec monthlyPartitionTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextMinLength, monthlyPartitionTextMinLength));
        this.monthlyPartitionTextMinLength = monthlyPartitionTextMinLength;
        propagateHierarchyIdToField(monthlyPartitionTextMinLength, "monthly_partition_text_min_length");
    }

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getMonthlyPartitionTextMaxLength() {
        return monthlyPartitionTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param monthlyPartitionTextMaxLength Maximum string length check.
     */
    public void setMonthlyPartitionTextMaxLength(ColumnTextMaxLengthCheckSpec monthlyPartitionTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextMaxLength, monthlyPartitionTextMaxLength));
        this.monthlyPartitionTextMaxLength = monthlyPartitionTextMaxLength;
        propagateHierarchyIdToField(monthlyPartitionTextMaxLength, "monthly_partition_text_max_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getMonthlyPartitionTextMeanLength() {
        return monthlyPartitionTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param monthlyPartitionTextMeanLength Mean string length check.
     */
    public void setMonthlyPartitionTextMeanLength(ColumnTextMeanLengthCheckSpec monthlyPartitionTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextMeanLength, monthlyPartitionTextMeanLength));
        this.monthlyPartitionTextMeanLength = monthlyPartitionTextMeanLength;
        propagateHierarchyIdToField(monthlyPartitionTextMeanLength, "monthly_partition_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getMonthlyPartitionTextLengthBelowMinLength() {
        return monthlyPartitionTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param monthlyPartitionTextLengthBelowMinLength String length below min length count check.
     */
    public void setMonthlyPartitionTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec monthlyPartitionTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthBelowMinLength, monthlyPartitionTextLengthBelowMinLength));
        this.monthlyPartitionTextLengthBelowMinLength = monthlyPartitionTextLengthBelowMinLength;
        propagateHierarchyIdToField(monthlyPartitionTextLengthBelowMinLength, "monthly_partition_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getMonthlyPartitionTextLengthBelowMinLengthPercent() {
        return monthlyPartitionTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param monthlyPartitionTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setMonthlyPartitionTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyPartitionTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthBelowMinLengthPercent, monthlyPartitionTextLengthBelowMinLengthPercent));
        this.monthlyPartitionTextLengthBelowMinLengthPercent = monthlyPartitionTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(monthlyPartitionTextLengthBelowMinLengthPercent, "monthly_partition_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getMonthlyPartitionTextLengthAboveMaxLength() {
        return monthlyPartitionTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param monthlyPartitionTextLengthAboveMaxLength String length above max length count check.
     */
    public void setMonthlyPartitionTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec monthlyPartitionTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthAboveMaxLength, monthlyPartitionTextLengthAboveMaxLength));
        this.monthlyPartitionTextLengthAboveMaxLength = monthlyPartitionTextLengthAboveMaxLength;
        propagateHierarchyIdToField(monthlyPartitionTextLengthAboveMaxLength, "monthly_partition_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getMonthlyPartitionTextLengthAboveMaxLengthPercent() {
        return monthlyPartitionTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param monthlyPartitionTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setMonthlyPartitionTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyPartitionTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthAboveMaxLengthPercent, monthlyPartitionTextLengthAboveMaxLengthPercent));
        this.monthlyPartitionTextLengthAboveMaxLengthPercent = monthlyPartitionTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(monthlyPartitionTextLengthAboveMaxLengthPercent, "monthly_partition_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getMonthlyPartitionTextLengthInRangePercent() {
        return monthlyPartitionTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param monthlyPartitionTextLengthInRangePercent String length in range percent check.
     */
    public void setMonthlyPartitionTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec monthlyPartitionTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextLengthInRangePercent, monthlyPartitionTextLengthInRangePercent));
        this.monthlyPartitionTextLengthInRangePercent = monthlyPartitionTextLengthInRangePercent;
        propagateHierarchyIdToField(monthlyPartitionTextLengthInRangePercent, "monthly_partition_text_length_in_range_percent");
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
        return CheckTimeScale.monthly;
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
