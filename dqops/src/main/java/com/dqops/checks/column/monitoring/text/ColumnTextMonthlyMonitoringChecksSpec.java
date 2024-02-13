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
package com.dqops.checks.column.monitoring.text;

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
 * Container of text data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnTextMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnTextMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_text_min_length", o -> o.monthlyTextMinLength);
            put("monthly_text_max_length", o -> o.monthlyTextMaxLength);
            put("monthly_text_mean_length", o -> o.monthlyTextMeanLength);
            put("monthly_text_length_below_min_length", o -> o.monthlyTextLengthBelowMinLength);
            put("monthly_text_length_below_min_length_percent", o -> o.monthlyTextLengthBelowMinLengthPercent);
            put("monthly_text_length_above_max_length", o -> o.monthlyTextLengthAboveMaxLength);
            put("monthly_text_length_above_max_length_percent", o -> o.monthlyTextLengthAboveMaxLengthPercent);
            put("monthly_text_length_in_range_percent", o -> o.monthlyTextLengthInRangePercent);
        }
    };


    @JsonPropertyDescription("This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextMinLengthCheckSpec monthlyTextMinLength;

    @JsonPropertyDescription("This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextMaxLengthCheckSpec monthlyTextMaxLength;

    @JsonPropertyDescription("Verifies that the mean (average) length of texts in a column is within an accepted range. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextMeanLengthCheckSpec monthlyTextMeanLength;

    @JsonPropertyDescription("The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthCheckSpec monthlyTextLengthBelowMinLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyTextLengthBelowMinLengthPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthCheckSpec monthlyTextLengthAboveMaxLength;

    @JsonPropertyDescription("The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyTextLengthAboveMaxLengthPercent;

    @JsonPropertyDescription("The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextLengthInRangePercentCheckSpec monthlyTextLengthInRangePercent;


    /**
     * Returns a minimum string length above check.
     * @return Minimum string length above check.
     */
    public ColumnTextMinLengthCheckSpec getMonthlyTextMinLength() {
        return monthlyTextMinLength;
    }

    /**
     * Sets a new definition of a minimum string length check.
     * @param monthlyTextMinLength Minimum string length check.
     */
    public void setMonthlyTextMinLength(ColumnTextMinLengthCheckSpec monthlyTextMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextMinLength, monthlyTextMinLength));
        this.monthlyTextMinLength = monthlyTextMinLength;
        propagateHierarchyIdToField(monthlyTextMinLength, "monthly_text_min_length");
    }

    /**
     * Returns a maximum string length below check.
     * @return Maximum string length below check.
     */
    public ColumnTextMaxLengthCheckSpec getMonthlyTextMaxLength() {
        return monthlyTextMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length check.
     * @param monthlyTextMaxLength Maximum string length check.
     */
    public void setMonthlyTextMaxLength(ColumnTextMaxLengthCheckSpec monthlyTextMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextMaxLength, monthlyTextMaxLength));
        this.monthlyTextMaxLength = monthlyTextMaxLength;
        propagateHierarchyIdToField(monthlyTextMaxLength, "monthly_text_max_length");
    }

    /**
     * Returns a mean string length between check.
     * @return Mean string length between check.
     */
    public ColumnTextMeanLengthCheckSpec getMonthlyTextMeanLength() {
        return monthlyTextMeanLength;
    }

    /**
     * Sets a new definition of a mean string length check.
     * @param monthlyTextMeanLength Mean string length check.
     */
    public void setMonthlyTextMeanLength(ColumnTextMeanLengthCheckSpec monthlyTextMeanLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextMeanLength, monthlyTextMeanLength));
        this.monthlyTextMeanLength = monthlyTextMeanLength;
        propagateHierarchyIdToField(monthlyTextMeanLength, "monthly_text_mean_length");
    }

    /**
     * Returns a string length below min length count check.
     * @return String length below min length count check.
     */
    public ColumnTextLengthBelowMinLengthCheckSpec getMonthlyTextLengthBelowMinLength() {
        return monthlyTextLengthBelowMinLength;
    }

    /**
     * Sets a new definition of a string length below min length count check.
     * @param monthlyTextLengthBelowMinLength String length below min length count check.
     */
    public void setMonthlyTextLengthBelowMinLength(ColumnTextLengthBelowMinLengthCheckSpec monthlyTextLengthBelowMinLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthBelowMinLength, monthlyTextLengthBelowMinLength));
        this.monthlyTextLengthBelowMinLength = monthlyTextLengthBelowMinLength;
        propagateHierarchyIdToField(monthlyTextLengthBelowMinLength, "monthly_text_length_below_min_length");
    }

    /**
     * Returns a string length below min length percent check.
     * @return String length below min length percent check.
     */
    public ColumnTextLengthBelowMinLengthPercentCheckSpec getMonthlyTextLengthBelowMinLengthPercent() {
        return monthlyTextLengthBelowMinLengthPercent;
    }

    /**
     * Sets a new definition of a string length below min length percent check.
     * @param monthlyTextLengthBelowMinLengthPercent String length below min length percent check.
     */
    public void setMonthlyTextLengthBelowMinLengthPercent(ColumnTextLengthBelowMinLengthPercentCheckSpec monthlyTextLengthBelowMinLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthBelowMinLengthPercent, monthlyTextLengthBelowMinLengthPercent));
        this.monthlyTextLengthBelowMinLengthPercent = monthlyTextLengthBelowMinLengthPercent;
        propagateHierarchyIdToField(monthlyTextLengthBelowMinLengthPercent, "monthly_text_length_below_min_length_percent");
    }

    /**
     * Returns a string length above max length count check.
     * @return String length above max length count check.
     */
    public ColumnTextLengthAboveMaxLengthCheckSpec getMonthlyTextLengthAboveMaxLength() {
        return monthlyTextLengthAboveMaxLength;
    }

    /**
     * Sets a new definition of a string length above max length count check.
     * @param monthlyTextLengthAboveMaxLength String length above max length count check.
     */
    public void setMonthlyTextLengthAboveMaxLength(ColumnTextLengthAboveMaxLengthCheckSpec monthlyTextLengthAboveMaxLength) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthAboveMaxLength, monthlyTextLengthAboveMaxLength));
        this.monthlyTextLengthAboveMaxLength = monthlyTextLengthAboveMaxLength;
        propagateHierarchyIdToField(monthlyTextLengthAboveMaxLength, "monthly_text_length_above_max_length");
    }

    /**
     * Returns a string length above max length percent check.
     * @return String length above max length percent check.
     */
    public ColumnTextLengthAboveMaxLengthPercentCheckSpec getMonthlyTextLengthAboveMaxLengthPercent() {
        return monthlyTextLengthAboveMaxLengthPercent;
    }

    /**
     * Sets a new definition of a string length above max length percent check.
     * @param monthlyTextLengthAboveMaxLengthPercent String length above max length percent check.
     */
    public void setMonthlyTextLengthAboveMaxLengthPercent(ColumnTextLengthAboveMaxLengthPercentCheckSpec monthlyTextLengthAboveMaxLengthPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthAboveMaxLengthPercent, monthlyTextLengthAboveMaxLengthPercent));
        this.monthlyTextLengthAboveMaxLengthPercent = monthlyTextLengthAboveMaxLengthPercent;
        propagateHierarchyIdToField(monthlyTextLengthAboveMaxLengthPercent, "monthly_text_length_above_max_length_percent");
    }

    /**
     * Returns a string length in range percent check.
     * @return String length in range percent check.
     */
    public ColumnTextLengthInRangePercentCheckSpec getMonthlyTextLengthInRangePercent() {
        return monthlyTextLengthInRangePercent;
    }

    /**
     * Sets a new definition of a string length in range percent check.
     * @param monthlyTextLengthInRangePercent String length in range percent check.
     */
    public void setMonthlyTextLengthInRangePercent(ColumnTextLengthInRangePercentCheckSpec monthlyTextLengthInRangePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextLengthInRangePercent, monthlyTextLengthInRangePercent));
        this.monthlyTextLengthInRangePercent = monthlyTextLengthInRangePercent;
        propagateHierarchyIdToField(monthlyTextLengthInRangePercent, "monthly_text_length_in_range_percent");
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
    public ColumnTextMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnTextMonthlyMonitoringChecksSpec)super.deepClone();
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
