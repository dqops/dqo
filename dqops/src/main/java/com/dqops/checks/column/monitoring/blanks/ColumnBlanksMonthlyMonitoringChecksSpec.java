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
package com.dqops.checks.column.monitoring.blanks;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.blanks.*;
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
 * Container of blank value detection data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBlanksMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBlanksMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_string_empty_count", o -> o.monthlyStringEmptyCount);
            put("monthly_string_whitespace_count", o -> o.monthlyStringWhitespaceCount);
            put("monthly_string_null_placeholder_count", o -> o.monthlyStringNullPlaceholderCount);
            put("monthly_string_empty_percent", o -> o.monthlyStringEmptyPercent);
            put("monthly_string_whitespace_percent", o -> o.monthlyStringWhitespacePercent);
            put("monthly_string_null_placeholder_percent", o -> o.monthlyStringNullPlaceholderPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksEmptyTextFoundCheckSpec monthlyStringEmptyCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksWhitespaceTextFoundCheckSpec monthlyStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksNullPlaceholderTextFoundCheckSpec monthlyStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksEmptyTextPercentCheckSpec monthlyStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksWhitespaceTextPercentCheckSpec monthlyStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksNullPlaceholderTextPercentCheckSpec monthlyStringNullPlaceholderPercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnBlanksEmptyTextFoundCheckSpec getMonthlyStringEmptyCount() {
        return monthlyStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyStringEmptyCount Max string empty count check.
     */
    public void setMonthlyStringEmptyCount(ColumnBlanksEmptyTextFoundCheckSpec monthlyStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringEmptyCount, monthlyStringEmptyCount));
        this.monthlyStringEmptyCount = monthlyStringEmptyCount;
        propagateHierarchyIdToField(monthlyStringEmptyCount, "monthly_string_empty_count");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnBlanksWhitespaceTextFoundCheckSpec getMonthlyStringWhitespaceCount() {
        return monthlyStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setMonthlyStringWhitespaceCount(ColumnBlanksWhitespaceTextFoundCheckSpec monthlyStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringWhitespaceCount, monthlyStringWhitespaceCount));
        this.monthlyStringWhitespaceCount = monthlyStringWhitespaceCount;
        propagateHierarchyIdToField(monthlyStringWhitespaceCount, "monthly_string_whitespace_count");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnBlanksNullPlaceholderTextFoundCheckSpec getMonthlyStringNullPlaceholderCount() {
        return monthlyStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setMonthlyStringNullPlaceholderCount(ColumnBlanksNullPlaceholderTextFoundCheckSpec monthlyStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringNullPlaceholderCount, monthlyStringNullPlaceholderCount));
        this.monthlyStringNullPlaceholderCount = monthlyStringNullPlaceholderCount;
        propagateHierarchyIdToField(monthlyStringNullPlaceholderCount, "monthly_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnBlanksEmptyTextPercentCheckSpec getMonthlyStringEmptyPercent() {
        return monthlyStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param monthlyStringEmptyPercent Maximum string empty percent check.
     */
    public void setMonthlyStringEmptyPercent(ColumnBlanksEmptyTextPercentCheckSpec monthlyStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringEmptyPercent, monthlyStringEmptyPercent));
        this.monthlyStringEmptyPercent = monthlyStringEmptyPercent;
        propagateHierarchyIdToField(monthlyStringEmptyPercent, "monthly_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnBlanksWhitespaceTextPercentCheckSpec getMonthlyStringWhitespacePercent() {
        return monthlyStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setMonthlyStringWhitespacePercent(ColumnBlanksWhitespaceTextPercentCheckSpec monthlyStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringWhitespacePercent, monthlyStringWhitespacePercent));
        this.monthlyStringWhitespacePercent = monthlyStringWhitespacePercent;
        propagateHierarchyIdToField(monthlyStringWhitespacePercent, "monthly_string_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextPercentCheckSpec getMonthlyStringNullPlaceholderPercent() {
        return monthlyStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyStringNullPlaceholderPercent(ColumnBlanksNullPlaceholderTextPercentCheckSpec monthlyStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyStringNullPlaceholderPercent, monthlyStringNullPlaceholderPercent));
        this.monthlyStringNullPlaceholderPercent = monthlyStringNullPlaceholderPercent;
        propagateHierarchyIdToField(monthlyStringNullPlaceholderPercent, "monthly_string_null_placeholder_percent");
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
    public ColumnBlanksMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnBlanksMonthlyMonitoringChecksSpec)super.deepClone();
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
}
