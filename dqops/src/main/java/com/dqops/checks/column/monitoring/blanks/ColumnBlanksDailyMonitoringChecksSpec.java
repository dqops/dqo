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
 * Container of blank value detection data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBlanksDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBlanksDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_empty_text_found", o -> o.dailyEmptyTextFound);
            put("daily_whitespace_text_found", o -> o.dailyWhitespaceTextFound);
            put("daily_null_placeholder_text_found", o -> o.dailyNullPlaceholderTextFound);
            put("daily_empty_text_percent", o -> o.dailyEmptyTextPercent);
            put("daily_whitespace_text_percent", o -> o.dailyWhitespaceTextPercent);
            put("daily_null_placeholder_text_percent", o -> o.dailyNullPlaceholderTextPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksEmptyTextFoundCheckSpec dailyEmptyTextFound;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksWhitespaceTextFoundCheckSpec dailyWhitespaceTextFound;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksNullPlaceholderTextFoundCheckSpec dailyNullPlaceholderTextFound;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksEmptyTextPercentCheckSpec dailyEmptyTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksWhitespaceTextPercentCheckSpec dailyWhitespaceTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksNullPlaceholderTextPercentCheckSpec dailyNullPlaceholderTextPercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnBlanksEmptyTextFoundCheckSpec getDailyEmptyTextFound() {
        return dailyEmptyTextFound;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyEmptyTextFound Max string empty count check.
     */
    public void setDailyEmptyTextFound(ColumnBlanksEmptyTextFoundCheckSpec dailyEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyEmptyTextFound, dailyEmptyTextFound));
        this.dailyEmptyTextFound = dailyEmptyTextFound;
        propagateHierarchyIdToField(dailyEmptyTextFound, "daily_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnBlanksWhitespaceTextFoundCheckSpec getDailyWhitespaceTextFound() {
        return dailyWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param dailyWhitespaceTextFound Maximum string whitespace count check.
     */
    public void setDailyWhitespaceTextFound(ColumnBlanksWhitespaceTextFoundCheckSpec dailyWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyWhitespaceTextFound, dailyWhitespaceTextFound));
        this.dailyWhitespaceTextFound = dailyWhitespaceTextFound;
        propagateHierarchyIdToField(dailyWhitespaceTextFound, "daily_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnBlanksNullPlaceholderTextFoundCheckSpec getDailyNullPlaceholderTextFound() {
        return dailyNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param dailyNullPlaceholderTextFound Maximum string null placeholder count check.
     */
    public void setDailyNullPlaceholderTextFound(ColumnBlanksNullPlaceholderTextFoundCheckSpec dailyNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyNullPlaceholderTextFound, dailyNullPlaceholderTextFound));
        this.dailyNullPlaceholderTextFound = dailyNullPlaceholderTextFound;
        propagateHierarchyIdToField(dailyNullPlaceholderTextFound, "daily_null_placeholder_text_found");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnBlanksEmptyTextPercentCheckSpec getDailyEmptyTextPercent() {
        return dailyEmptyTextPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param dailyEmptyTextPercent Maximum string empty percent check.
     */
    public void setDailyEmptyTextPercent(ColumnBlanksEmptyTextPercentCheckSpec dailyEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyEmptyTextPercent, dailyEmptyTextPercent));
        this.dailyEmptyTextPercent = dailyEmptyTextPercent;
        propagateHierarchyIdToField(dailyEmptyTextPercent, "daily_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnBlanksWhitespaceTextPercentCheckSpec getDailyWhitespaceTextPercent() {
        return dailyWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param dailyWhitespaceTextPercent Maximum string whitespace percent check.
     */
    public void setDailyWhitespaceTextPercent(ColumnBlanksWhitespaceTextPercentCheckSpec dailyWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyWhitespaceTextPercent, dailyWhitespaceTextPercent));
        this.dailyWhitespaceTextPercent = dailyWhitespaceTextPercent;
        propagateHierarchyIdToField(dailyWhitespaceTextPercent, "daily_whitespace_text_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextPercentCheckSpec getDailyNullPlaceholderTextPercent() {
        return dailyNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param dailyNullPlaceholderTextPercent Maximum string null placeholder percent check.
     */
    public void setDailyNullPlaceholderTextPercent(ColumnBlanksNullPlaceholderTextPercentCheckSpec dailyNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyNullPlaceholderTextPercent, dailyNullPlaceholderTextPercent));
        this.dailyNullPlaceholderTextPercent = dailyNullPlaceholderTextPercent;
        propagateHierarchyIdToField(dailyNullPlaceholderTextPercent, "daily_null_placeholder_text_percent");
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
    public ColumnBlanksDailyMonitoringChecksSpec deepClone() {
        return (ColumnBlanksDailyMonitoringChecksSpec)super.deepClone();
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
}
