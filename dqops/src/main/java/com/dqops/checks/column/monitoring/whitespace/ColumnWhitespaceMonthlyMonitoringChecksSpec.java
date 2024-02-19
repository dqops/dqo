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
 * Container of whitespace value detection data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnWhitespaceMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnWhitespaceMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_empty_text_found", o -> o.monthlyEmptyTextFound);
            put("monthly_whitespace_text_found", o -> o.monthlyWhitespaceTextFound);
            put("monthly_null_placeholder_text_found", o -> o.monthlyNullPlaceholderTextFound);
            put("monthly_empty_text_percent", o -> o.monthlyEmptyTextPercent);
            put("monthly_whitespace_text_percent", o -> o.monthlyWhitespaceTextPercent);
            put("monthly_null_placeholder_text_percent", o -> o.monthlyNullPlaceholderTextPercent);

            put("monthly_text_surrounded_by_whitespace_found", o -> o.monthlyTextSurroundedByWhitespaceFound);
            put("monthly_text_surrounded_by_whitespace_percent", o -> o.monthlyTextSurroundedByWhitespacePercent);
        }
    };

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceEmptyTextFoundCheckSpec monthlyEmptyTextFound;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceWhitespaceTextFoundCheckSpec monthlyWhitespaceTextFound;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceNullPlaceholderTextFoundCheckSpec monthlyNullPlaceholderTextFound;

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceEmptyTextPercentCheckSpec monthlyEmptyTextPercent;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceWhitespaceTextPercentCheckSpec monthlyWhitespaceTextPercent;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnWhitespaceNullPlaceholderTextPercentCheckSpec monthlyNullPlaceholderTextPercent;

    @JsonPropertyDescription("Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec monthlyTextSurroundedByWhitespaceFound;

    @JsonPropertyDescription("This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec monthlyTextSurroundedByWhitespacePercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnWhitespaceEmptyTextFoundCheckSpec getMonthlyEmptyTextFound() {
        return monthlyEmptyTextFound;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyEmptyTextFound Max string empty count check.
     */
    public void setMonthlyEmptyTextFound(ColumnWhitespaceEmptyTextFoundCheckSpec monthlyEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyEmptyTextFound, monthlyEmptyTextFound));
        this.monthlyEmptyTextFound = monthlyEmptyTextFound;
        propagateHierarchyIdToField(monthlyEmptyTextFound, "monthly_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnWhitespaceWhitespaceTextFoundCheckSpec getMonthlyWhitespaceTextFound() {
        return monthlyWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyWhitespaceTextFound Maximum string whitespace count check.
     */
    public void setMonthlyWhitespaceTextFound(ColumnWhitespaceWhitespaceTextFoundCheckSpec monthlyWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyWhitespaceTextFound, monthlyWhitespaceTextFound));
        this.monthlyWhitespaceTextFound = monthlyWhitespaceTextFound;
        propagateHierarchyIdToField(monthlyWhitespaceTextFound, "monthly_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnWhitespaceNullPlaceholderTextFoundCheckSpec getMonthlyNullPlaceholderTextFound() {
        return monthlyNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyNullPlaceholderTextFound Maximum string null placeholder count check.
     */
    public void setMonthlyNullPlaceholderTextFound(ColumnWhitespaceNullPlaceholderTextFoundCheckSpec monthlyNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyNullPlaceholderTextFound, monthlyNullPlaceholderTextFound));
        this.monthlyNullPlaceholderTextFound = monthlyNullPlaceholderTextFound;
        propagateHierarchyIdToField(monthlyNullPlaceholderTextFound, "monthly_null_placeholder_text_found");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnWhitespaceEmptyTextPercentCheckSpec getMonthlyEmptyTextPercent() {
        return monthlyEmptyTextPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param monthlyEmptyTextPercent Maximum string empty percent check.
     */
    public void setMonthlyEmptyTextPercent(ColumnWhitespaceEmptyTextPercentCheckSpec monthlyEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyEmptyTextPercent, monthlyEmptyTextPercent));
        this.monthlyEmptyTextPercent = monthlyEmptyTextPercent;
        propagateHierarchyIdToField(monthlyEmptyTextPercent, "monthly_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnWhitespaceWhitespaceTextPercentCheckSpec getMonthlyWhitespaceTextPercent() {
        return monthlyWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyWhitespaceTextPercent Maximum string whitespace percent check.
     */
    public void setMonthlyWhitespaceTextPercent(ColumnWhitespaceWhitespaceTextPercentCheckSpec monthlyWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyWhitespaceTextPercent, monthlyWhitespaceTextPercent));
        this.monthlyWhitespaceTextPercent = monthlyWhitespaceTextPercent;
        propagateHierarchyIdToField(monthlyWhitespaceTextPercent, "monthly_whitespace_text_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnWhitespaceNullPlaceholderTextPercentCheckSpec getMonthlyNullPlaceholderTextPercent() {
        return monthlyNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyNullPlaceholderTextPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyNullPlaceholderTextPercent(ColumnWhitespaceNullPlaceholderTextPercentCheckSpec monthlyNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNullPlaceholderTextPercent, monthlyNullPlaceholderTextPercent));
        this.monthlyNullPlaceholderTextPercent = monthlyNullPlaceholderTextPercent;
        propagateHierarchyIdToField(monthlyNullPlaceholderTextPercent, "monthly_null_placeholder_text_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec getMonthlyTextSurroundedByWhitespaceFound() {
        return monthlyTextSurroundedByWhitespaceFound;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param monthlyTextSurroundedByWhitespaceFound String surrounded by whitespace count check.
     */
    public void setMonthlyTextSurroundedByWhitespaceFound(ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec monthlyTextSurroundedByWhitespaceFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextSurroundedByWhitespaceFound, monthlyTextSurroundedByWhitespaceFound));
        this.monthlyTextSurroundedByWhitespaceFound = monthlyTextSurroundedByWhitespaceFound;
        propagateHierarchyIdToField(monthlyTextSurroundedByWhitespaceFound, "monthly_text_surrounded_by_whitespace_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec getMonthlyTextSurroundedByWhitespacePercent() {
        return monthlyTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param monthlyTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setMonthlyTextSurroundedByWhitespacePercent(ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec monthlyTextSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextSurroundedByWhitespacePercent, monthlyTextSurroundedByWhitespacePercent));
        this.monthlyTextSurroundedByWhitespacePercent = monthlyTextSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyTextSurroundedByWhitespacePercent, "monthly_text_surrounded_by_whitespace_percent");
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
    public ColumnWhitespaceMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnWhitespaceMonthlyMonitoringChecksSpec)super.deepClone();
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
