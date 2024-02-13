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
import com.dqops.checks.column.checkspecs.blanks.*;
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

            put("monthly_text_surrounded_by_whitespace", o -> o.monthlyTextSurroundedByWhitespace);
            put("monthly_text_surrounded_by_whitespace_percent", o -> o.monthlyTextSurroundedByWhitespacePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksEmptyTextFoundCheckSpec monthlyEmptyTextFound;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksWhitespaceTextFoundCheckSpec monthlyWhitespaceTextFound;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksNullPlaceholderTextFoundCheckSpec monthlyNullPlaceholderTextFound;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksEmptyTextPercentCheckSpec monthlyEmptyTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksWhitespaceTextPercentCheckSpec monthlyWhitespaceTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksNullPlaceholderTextPercentCheckSpec monthlyNullPlaceholderTextPercent;

    @JsonPropertyDescription("The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextSurroundedByWhitespaceCheckSpec monthlyTextSurroundedByWhitespace;

    @JsonPropertyDescription("Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyTextSurroundedByWhitespacePercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnBlanksEmptyTextFoundCheckSpec getMonthlyEmptyTextFound() {
        return monthlyEmptyTextFound;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyEmptyTextFound Max string empty count check.
     */
    public void setMonthlyEmptyTextFound(ColumnBlanksEmptyTextFoundCheckSpec monthlyEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyEmptyTextFound, monthlyEmptyTextFound));
        this.monthlyEmptyTextFound = monthlyEmptyTextFound;
        propagateHierarchyIdToField(monthlyEmptyTextFound, "monthly_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnBlanksWhitespaceTextFoundCheckSpec getMonthlyWhitespaceTextFound() {
        return monthlyWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyWhitespaceTextFound Maximum string whitespace count check.
     */
    public void setMonthlyWhitespaceTextFound(ColumnBlanksWhitespaceTextFoundCheckSpec monthlyWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyWhitespaceTextFound, monthlyWhitespaceTextFound));
        this.monthlyWhitespaceTextFound = monthlyWhitespaceTextFound;
        propagateHierarchyIdToField(monthlyWhitespaceTextFound, "monthly_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnBlanksNullPlaceholderTextFoundCheckSpec getMonthlyNullPlaceholderTextFound() {
        return monthlyNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyNullPlaceholderTextFound Maximum string null placeholder count check.
     */
    public void setMonthlyNullPlaceholderTextFound(ColumnBlanksNullPlaceholderTextFoundCheckSpec monthlyNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyNullPlaceholderTextFound, monthlyNullPlaceholderTextFound));
        this.monthlyNullPlaceholderTextFound = monthlyNullPlaceholderTextFound;
        propagateHierarchyIdToField(monthlyNullPlaceholderTextFound, "monthly_null_placeholder_text_found");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnBlanksEmptyTextPercentCheckSpec getMonthlyEmptyTextPercent() {
        return monthlyEmptyTextPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param monthlyEmptyTextPercent Maximum string empty percent check.
     */
    public void setMonthlyEmptyTextPercent(ColumnBlanksEmptyTextPercentCheckSpec monthlyEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyEmptyTextPercent, monthlyEmptyTextPercent));
        this.monthlyEmptyTextPercent = monthlyEmptyTextPercent;
        propagateHierarchyIdToField(monthlyEmptyTextPercent, "monthly_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnBlanksWhitespaceTextPercentCheckSpec getMonthlyWhitespaceTextPercent() {
        return monthlyWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyWhitespaceTextPercent Maximum string whitespace percent check.
     */
    public void setMonthlyWhitespaceTextPercent(ColumnBlanksWhitespaceTextPercentCheckSpec monthlyWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyWhitespaceTextPercent, monthlyWhitespaceTextPercent));
        this.monthlyWhitespaceTextPercent = monthlyWhitespaceTextPercent;
        propagateHierarchyIdToField(monthlyWhitespaceTextPercent, "monthly_whitespace_text_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextPercentCheckSpec getMonthlyNullPlaceholderTextPercent() {
        return monthlyNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyNullPlaceholderTextPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyNullPlaceholderTextPercent(ColumnBlanksNullPlaceholderTextPercentCheckSpec monthlyNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyNullPlaceholderTextPercent, monthlyNullPlaceholderTextPercent));
        this.monthlyNullPlaceholderTextPercent = monthlyNullPlaceholderTextPercent;
        propagateHierarchyIdToField(monthlyNullPlaceholderTextPercent, "monthly_null_placeholder_text_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnTextSurroundedByWhitespaceCheckSpec getMonthlyTextSurroundedByWhitespace() {
        return monthlyTextSurroundedByWhitespace;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param monthlyTextSurroundedByWhitespace String surrounded by whitespace count check.
     */
    public void setMonthlyTextSurroundedByWhitespace(ColumnTextSurroundedByWhitespaceCheckSpec monthlyTextSurroundedByWhitespace) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextSurroundedByWhitespace, monthlyTextSurroundedByWhitespace));
        this.monthlyTextSurroundedByWhitespace = monthlyTextSurroundedByWhitespace;
        propagateHierarchyIdToField(monthlyTextSurroundedByWhitespace, "monthly_text_surrounded_by_whitespace");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnTextSurroundedByWhitespacePercentCheckSpec getMonthlyTextSurroundedByWhitespacePercent() {
        return monthlyTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param monthlyTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setMonthlyTextSurroundedByWhitespacePercent(ColumnTextSurroundedByWhitespacePercentCheckSpec monthlyTextSurroundedByWhitespacePercent) {
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
