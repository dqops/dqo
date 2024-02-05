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
 * Container of blank values detection data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBlanksDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBlanksDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_empty_text_found", o -> o.dailyPartitionEmptyTextFound);
            put("daily_partition_whitespace_text_found", o -> o.dailyPartitionWhitespaceTextFound);
            put("daily_partition_null_placeholder_text_found", o -> o.dailyPartitionNullPlaceholderTextFound);
            put("daily_partition_empty_text_percent", o -> o.dailyPartitionEmptyTextPercent);
            put("daily_partition_whitespace_text_percent", o -> o.dailyPartitionWhitespaceTextPercent);
            put("daily_partition_null_placeholder_text_percent", o -> o.dailyPartitionNullPlaceholderTextPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.")
    private ColumnBlanksEmptyTextFoundCheckSpec dailyPartitionEmptyTextFound;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.")
    private ColumnBlanksWhitespaceTextFoundCheckSpec dailyPartitionWhitespaceTextFound;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.")
    private ColumnBlanksNullPlaceholderTextFoundCheckSpec dailyPartitionNullPlaceholderTextFound;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnBlanksEmptyTextPercentCheckSpec dailyPartitionEmptyTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each daily partition.")
    private ColumnBlanksWhitespaceTextPercentCheckSpec dailyPartitionWhitespaceTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnBlanksNullPlaceholderTextPercentCheckSpec dailyPartitionNullPlaceholderTextPercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnBlanksEmptyTextFoundCheckSpec getDailyPartitionEmptyTextFound() {
        return dailyPartitionEmptyTextFound;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyPartitionEmptyTextFound Max string empty count check.
     */
    public void setDailyPartitionEmptyTextFound(ColumnBlanksEmptyTextFoundCheckSpec dailyPartitionEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionEmptyTextFound, dailyPartitionEmptyTextFound));
        this.dailyPartitionEmptyTextFound = dailyPartitionEmptyTextFound;
        propagateHierarchyIdToField(dailyPartitionEmptyTextFound, "daily_partition_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnBlanksWhitespaceTextFoundCheckSpec getDailyPartitionWhitespaceTextFound() {
        return dailyPartitionWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param dailyPartitionWhitespaceTextFound Maximum string whitespace count check.
     */
    public void setDailyPartitionWhitespaceTextFound(ColumnBlanksWhitespaceTextFoundCheckSpec dailyPartitionWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionWhitespaceTextFound, dailyPartitionWhitespaceTextFound));
        this.dailyPartitionWhitespaceTextFound = dailyPartitionWhitespaceTextFound;
        propagateHierarchyIdToField(dailyPartitionWhitespaceTextFound, "daily_partition_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnBlanksNullPlaceholderTextFoundCheckSpec getDailyPartitionNullPlaceholderTextFound() {
        return dailyPartitionNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param dailyPartitionNullPlaceholderTextFound Maximum string null placeholder count check.
     */
    public void setDailyPartitionNullPlaceholderTextFound(ColumnBlanksNullPlaceholderTextFoundCheckSpec dailyPartitionNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullPlaceholderTextFound, dailyPartitionNullPlaceholderTextFound));
        this.dailyPartitionNullPlaceholderTextFound = dailyPartitionNullPlaceholderTextFound;
        propagateHierarchyIdToField(dailyPartitionNullPlaceholderTextFound, "daily_partition_null_placeholder_text_found");
    }

    /**
     * Returns a maximum empty string percentage check.
     * @return Maximum empty string percentage check.
     */
    public ColumnBlanksEmptyTextPercentCheckSpec getDailyPartitionEmptyTextPercent() {
        return dailyPartitionEmptyTextPercent;
    }

    /**
     * Sets a new definition of a maximum empty string percentage check.
     * @param dailyPartitionEmptyTextPercent Maximum empty string percentage check.
     */
    public void setDailyPartitionEmptyTextPercent(ColumnBlanksEmptyTextPercentCheckSpec dailyPartitionEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionEmptyTextPercent, dailyPartitionEmptyTextPercent));
        this.dailyPartitionEmptyTextPercent = dailyPartitionEmptyTextPercent;
        propagateHierarchyIdToField(dailyPartitionEmptyTextPercent, "daily_partition_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnBlanksWhitespaceTextPercentCheckSpec getDailyPartitionWhitespaceTextPercent() {
        return dailyPartitionWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param dailyPartitionWhitespaceTextPercent Maximum string whitespace percent check.
     */
    public void setDailyPartitionWhitespaceTextPercent(ColumnBlanksWhitespaceTextPercentCheckSpec dailyPartitionWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionWhitespaceTextPercent, dailyPartitionWhitespaceTextPercent));
        this.dailyPartitionWhitespaceTextPercent = dailyPartitionWhitespaceTextPercent;
        propagateHierarchyIdToField(dailyPartitionWhitespaceTextPercent, "daily_partition_whitespace_text_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextPercentCheckSpec getDailyPartitionNullPlaceholderTextPercent() {
        return dailyPartitionNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param dailyPartitionNullPlaceholderTextPercent Maximum string null placeholder percent check.
     */
    public void setDailyPartitionNullPlaceholderTextPercent(ColumnBlanksNullPlaceholderTextPercentCheckSpec dailyPartitionNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionNullPlaceholderTextPercent, dailyPartitionNullPlaceholderTextPercent));
        this.dailyPartitionNullPlaceholderTextPercent = dailyPartitionNullPlaceholderTextPercent;
        propagateHierarchyIdToField(dailyPartitionNullPlaceholderTextPercent, "daily_partition_null_placeholder_text_percent");
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
