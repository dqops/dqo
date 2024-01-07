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
package com.dqops.checks.column.partitioned.blanks;

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
 * Container of blank values detection data quality partitioned checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBlanksMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBlanksMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_string_empty_count", o -> o.monthlyPartitionStringEmptyCount);
            put("monthly_partition_string_whitespace_count", o -> o.monthlyPartitionStringWhitespaceCount);
            put("monthly_partition_string_null_placeholder_count", o -> o.monthlyPartitionStringNullPlaceholderCount);
            put("monthly_partition_string_empty_percent", o -> o.monthlyPartitionStringEmptyPercent);
            put("monthly_partition_string_whitespace_percent", o -> o.monthlyPartitionStringWhitespacePercent);
            put("monthly_partition_string_null_placeholder_percent", o -> o.monthlyPartitionStringNullPlaceholderPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksEmptyTextFoundCheckSpec monthlyPartitionStringEmptyCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksWhitespaceTextFoundCheckSpec monthlyPartitionStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksNullPlaceholderTextFoundCheckSpec monthlyPartitionStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksEmptyTextPercentCheckSpec monthlyPartitionStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksWhitespaceTextPercentCheckSpec monthlyPartitionStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksNullPlaceholderTextPercentCheckSpec monthlyPartitionStringNullPlaceholderPercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnBlanksEmptyTextFoundCheckSpec getMonthlyPartitionStringEmptyCount() {
        return monthlyPartitionStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyPartitionStringEmptyCount Max string empty count check.
     */
    public void setMonthlyPartitionStringEmptyCount(ColumnBlanksEmptyTextFoundCheckSpec monthlyPartitionStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringEmptyCount, monthlyPartitionStringEmptyCount));
        this.monthlyPartitionStringEmptyCount = monthlyPartitionStringEmptyCount;
        propagateHierarchyIdToField(monthlyPartitionStringEmptyCount, "monthly_partition_string_empty_count");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnBlanksWhitespaceTextFoundCheckSpec getMonthlyPartitionStringWhitespaceCount() {
        return monthlyPartitionStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyPartitionStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setMonthlyPartitionStringWhitespaceCount(ColumnBlanksWhitespaceTextFoundCheckSpec monthlyPartitionStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringWhitespaceCount, monthlyPartitionStringWhitespaceCount));
        this.monthlyPartitionStringWhitespaceCount = monthlyPartitionStringWhitespaceCount;
        propagateHierarchyIdToField(monthlyPartitionStringWhitespaceCount, "monthly_partition_string_whitespace_count");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnBlanksNullPlaceholderTextFoundCheckSpec getMonthlyPartitionStringNullPlaceholderCount() {
        return monthlyPartitionStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyPartitionStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setMonthlyPartitionStringNullPlaceholderCount(ColumnBlanksNullPlaceholderTextFoundCheckSpec monthlyPartitionStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringNullPlaceholderCount, monthlyPartitionStringNullPlaceholderCount));
        this.monthlyPartitionStringNullPlaceholderCount = monthlyPartitionStringNullPlaceholderCount;
        propagateHierarchyIdToField(monthlyPartitionStringNullPlaceholderCount, "monthly_partition_string_null_placeholder_count");
    }

    /**
     * Returns a maximum empty string percentage check.
     * @return Maximum empty string percentage check.
     */
    public ColumnBlanksEmptyTextPercentCheckSpec getMonthlyPartitionStringEmptyPercent() {
        return monthlyPartitionStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum empty string percentage check.
     * @param monthlyPartitionStringEmptyPercent Maximum empty string percentage check.
     */
    public void setMonthlyPartitionStringEmptyPercent(ColumnBlanksEmptyTextPercentCheckSpec monthlyPartitionStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringEmptyPercent, monthlyPartitionStringEmptyPercent));
        this.monthlyPartitionStringEmptyPercent = monthlyPartitionStringEmptyPercent;
        propagateHierarchyIdToField(monthlyPartitionStringEmptyPercent, "monthly_partition_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnBlanksWhitespaceTextPercentCheckSpec getMonthlyPartitionStringWhitespacePercent() {
        return monthlyPartitionStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyPartitionStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setMonthlyPartitionStringWhitespacePercent(ColumnBlanksWhitespaceTextPercentCheckSpec monthlyPartitionStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringWhitespacePercent, monthlyPartitionStringWhitespacePercent));
        this.monthlyPartitionStringWhitespacePercent = monthlyPartitionStringWhitespacePercent;
        propagateHierarchyIdToField(monthlyPartitionStringWhitespacePercent, "monthly_partition_string_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextPercentCheckSpec getMonthlyPartitionStringNullPlaceholderPercent() {
        return monthlyPartitionStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyPartitionStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyPartitionStringNullPlaceholderPercent(ColumnBlanksNullPlaceholderTextPercentCheckSpec monthlyPartitionStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionStringNullPlaceholderPercent, monthlyPartitionStringNullPlaceholderPercent));
        this.monthlyPartitionStringNullPlaceholderPercent = monthlyPartitionStringNullPlaceholderPercent;
        propagateHierarchyIdToField(monthlyPartitionStringNullPlaceholderPercent, "monthly_partition_string_null_placeholder_percent");
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
}
