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
            put("monthly_partition_empty_text_found", o -> o.monthlyPartitionEmptyTextFound);
            put("monthly_partition_whitespace_text_found", o -> o.monthlyPartitionWhitespaceTextFound);
            put("monthly_partition_null_placeholder_text_found", o -> o.monthlyPartitionNullPlaceholderTextFound);
            put("monthly_partition_empty_text_percent", o -> o.monthlyPartitionEmptyTextPercent);
            put("monthly_partition_whitespace_text_percent", o -> o.monthlyPartitionWhitespaceTextPercent);
            put("monthly_partition_null_placeholder_text_percent", o -> o.monthlyPartitionNullPlaceholderTextPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksEmptyTextFoundCheckSpec monthlyPartitionEmptyTextFound;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksWhitespaceTextFoundCheckSpec monthlyPartitionWhitespaceTextFound;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksNullPlaceholderTextFoundCheckSpec monthlyPartitionNullPlaceholderTextFound;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksEmptyTextPercentCheckSpec monthlyPartitionEmptyTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksWhitespaceTextPercentCheckSpec monthlyPartitionWhitespaceTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnBlanksNullPlaceholderTextPercentCheckSpec monthlyPartitionNullPlaceholderTextPercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnBlanksEmptyTextFoundCheckSpec getMonthlyPartitionEmptyTextFound() {
        return monthlyPartitionEmptyTextFound;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyPartitionEmptyTextFound Max string empty count check.
     */
    public void setMonthlyPartitionEmptyTextFound(ColumnBlanksEmptyTextFoundCheckSpec monthlyPartitionEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionEmptyTextFound, monthlyPartitionEmptyTextFound));
        this.monthlyPartitionEmptyTextFound = monthlyPartitionEmptyTextFound;
        propagateHierarchyIdToField(monthlyPartitionEmptyTextFound, "monthly_partition_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnBlanksWhitespaceTextFoundCheckSpec getMonthlyPartitionWhitespaceTextFound() {
        return monthlyPartitionWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyPartitionWhitespaceTextFound Maximum string whitespace count check.
     */
    public void setMonthlyPartitionWhitespaceTextFound(ColumnBlanksWhitespaceTextFoundCheckSpec monthlyPartitionWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionWhitespaceTextFound, monthlyPartitionWhitespaceTextFound));
        this.monthlyPartitionWhitespaceTextFound = monthlyPartitionWhitespaceTextFound;
        propagateHierarchyIdToField(monthlyPartitionWhitespaceTextFound, "monthly_partition_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnBlanksNullPlaceholderTextFoundCheckSpec getMonthlyPartitionNullPlaceholderTextFound() {
        return monthlyPartitionNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyPartitionNullPlaceholderTextFound Maximum string null placeholder count check.
     */
    public void setMonthlyPartitionNullPlaceholderTextFound(ColumnBlanksNullPlaceholderTextFoundCheckSpec monthlyPartitionNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNullPlaceholderTextFound, monthlyPartitionNullPlaceholderTextFound));
        this.monthlyPartitionNullPlaceholderTextFound = monthlyPartitionNullPlaceholderTextFound;
        propagateHierarchyIdToField(monthlyPartitionNullPlaceholderTextFound, "monthly_partition_null_placeholder_text_found");
    }

    /**
     * Returns a maximum empty string percentage check.
     * @return Maximum empty string percentage check.
     */
    public ColumnBlanksEmptyTextPercentCheckSpec getMonthlyPartitionEmptyTextPercent() {
        return monthlyPartitionEmptyTextPercent;
    }

    /**
     * Sets a new definition of a maximum empty string percentage check.
     * @param monthlyPartitionEmptyTextPercent Maximum empty string percentage check.
     */
    public void setMonthlyPartitionEmptyTextPercent(ColumnBlanksEmptyTextPercentCheckSpec monthlyPartitionEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionEmptyTextPercent, monthlyPartitionEmptyTextPercent));
        this.monthlyPartitionEmptyTextPercent = monthlyPartitionEmptyTextPercent;
        propagateHierarchyIdToField(monthlyPartitionEmptyTextPercent, "monthly_partition_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnBlanksWhitespaceTextPercentCheckSpec getMonthlyPartitionWhitespaceTextPercent() {
        return monthlyPartitionWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyPartitionWhitespaceTextPercent Maximum string whitespace percent check.
     */
    public void setMonthlyPartitionWhitespaceTextPercent(ColumnBlanksWhitespaceTextPercentCheckSpec monthlyPartitionWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionWhitespaceTextPercent, monthlyPartitionWhitespaceTextPercent));
        this.monthlyPartitionWhitespaceTextPercent = monthlyPartitionWhitespaceTextPercent;
        propagateHierarchyIdToField(monthlyPartitionWhitespaceTextPercent, "monthly_partition_whitespace_text_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextPercentCheckSpec getMonthlyPartitionNullPlaceholderTextPercent() {
        return monthlyPartitionNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyPartitionNullPlaceholderTextPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyPartitionNullPlaceholderTextPercent(ColumnBlanksNullPlaceholderTextPercentCheckSpec monthlyPartitionNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNullPlaceholderTextPercent, monthlyPartitionNullPlaceholderTextPercent));
        this.monthlyPartitionNullPlaceholderTextPercent = monthlyPartitionNullPlaceholderTextPercent;
        propagateHierarchyIdToField(monthlyPartitionNullPlaceholderTextPercent, "monthly_partition_null_placeholder_text_percent");
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
