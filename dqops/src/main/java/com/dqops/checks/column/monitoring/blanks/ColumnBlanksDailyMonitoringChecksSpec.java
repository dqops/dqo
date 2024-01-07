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
            put("daily_string_empty_count", o -> o.dailyStringEmptyCount);
            put("daily_string_whitespace_count", o -> o.dailyStringWhitespaceCount);
            put("daily_string_null_placeholder_count", o -> o.dailyStringNullPlaceholderCount);
            put("daily_string_empty_percent", o -> o.dailyStringEmptyPercent);
            put("daily_string_whitespace_percent", o -> o.dailyStringWhitespacePercent);
            put("daily_string_null_placeholder_percent", o -> o.dailyStringNullPlaceholderPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksEmptyTextFoundCheckSpec dailyStringEmptyCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksWhitespaceTextFoundCheckSpec dailyStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksNullPlaceholderTextFoundCheckSpec dailyStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksEmptyTextPercentCheckSpec dailyStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksWhitespaceTextPercentCheckSpec dailyStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnBlanksNullPlaceholderTextPercentCheckSpec dailyStringNullPlaceholderPercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnBlanksEmptyTextFoundCheckSpec getDailyStringEmptyCount() {
        return dailyStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyStringEmptyCount Max string empty count check.
     */
    public void setDailyStringEmptyCount(ColumnBlanksEmptyTextFoundCheckSpec dailyStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringEmptyCount, dailyStringEmptyCount));
        this.dailyStringEmptyCount = dailyStringEmptyCount;
        propagateHierarchyIdToField(dailyStringEmptyCount, "daily_string_empty_count");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnBlanksWhitespaceTextFoundCheckSpec getDailyStringWhitespaceCount() {
        return dailyStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param dailyStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setDailyStringWhitespaceCount(ColumnBlanksWhitespaceTextFoundCheckSpec dailyStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringWhitespaceCount, dailyStringWhitespaceCount));
        this.dailyStringWhitespaceCount = dailyStringWhitespaceCount;
        propagateHierarchyIdToField(dailyStringWhitespaceCount, "daily_string_whitespace_count");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnBlanksNullPlaceholderTextFoundCheckSpec getDailyStringNullPlaceholderCount() {
        return dailyStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param dailyStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setDailyStringNullPlaceholderCount(ColumnBlanksNullPlaceholderTextFoundCheckSpec dailyStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.dailyStringNullPlaceholderCount, dailyStringNullPlaceholderCount));
        this.dailyStringNullPlaceholderCount = dailyStringNullPlaceholderCount;
        propagateHierarchyIdToField(dailyStringNullPlaceholderCount, "daily_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnBlanksEmptyTextPercentCheckSpec getDailyStringEmptyPercent() {
        return dailyStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum string empty percent check.
     * @param dailyStringEmptyPercent Maximum string empty percent check.
     */
    public void setDailyStringEmptyPercent(ColumnBlanksEmptyTextPercentCheckSpec dailyStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringEmptyPercent, dailyStringEmptyPercent));
        this.dailyStringEmptyPercent = dailyStringEmptyPercent;
        propagateHierarchyIdToField(dailyStringEmptyPercent, "daily_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnBlanksWhitespaceTextPercentCheckSpec getDailyStringWhitespacePercent() {
        return dailyStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param dailyStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setDailyStringWhitespacePercent(ColumnBlanksWhitespaceTextPercentCheckSpec dailyStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringWhitespacePercent, dailyStringWhitespacePercent));
        this.dailyStringWhitespacePercent = dailyStringWhitespacePercent;
        propagateHierarchyIdToField(dailyStringWhitespacePercent, "daily_string_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextPercentCheckSpec getDailyStringNullPlaceholderPercent() {
        return dailyStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param dailyStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setDailyStringNullPlaceholderPercent(ColumnBlanksNullPlaceholderTextPercentCheckSpec dailyStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyStringNullPlaceholderPercent, dailyStringNullPlaceholderPercent));
        this.dailyStringNullPlaceholderPercent = dailyStringNullPlaceholderPercent;
        propagateHierarchyIdToField(dailyStringNullPlaceholderPercent, "daily_string_null_placeholder_percent");
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
