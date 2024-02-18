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
package com.dqops.checks.column.partitioned.whitespace;

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
 * Container of whitespace values detection data quality partitioned checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnWhitespaceMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnWhitespaceMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_empty_text_found", o -> o.monthlyPartitionEmptyTextFound);
            put("monthly_partition_whitespace_text_found", o -> o.monthlyPartitionWhitespaceTextFound);
            put("monthly_partition_null_placeholder_text_found", o -> o.monthlyPartitionNullPlaceholderTextFound);
            put("monthly_partition_empty_text_percent", o -> o.monthlyPartitionEmptyTextPercent);
            put("monthly_partition_whitespace_text_percent", o -> o.monthlyPartitionWhitespaceTextPercent);
            put("monthly_partition_null_placeholder_text_percent", o -> o.monthlyPartitionNullPlaceholderTextPercent);

            put("monthly_partition_text_surrounded_by_whitespace_found", o -> o.monthlyPartitionTextSurroundedByWhitespaceFound);
            put("monthly_partition_text_surrounded_by_whitespace_percent", o -> o.monthlyPartitionTextSurroundedByWhitespacePercent);
        }
    };

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each monthly partition.")
    private ColumnWhitespaceEmptyTextFoundCheckSpec monthlyPartitionEmptyTextFound;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each monthly partition.")
    private ColumnWhitespaceWhitespaceTextFoundCheckSpec monthlyPartitionWhitespaceTextFound;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each monthly partition.")
    private ColumnWhitespaceNullPlaceholderTextFoundCheckSpec monthlyPartitionNullPlaceholderTextFound;

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnWhitespaceEmptyTextPercentCheckSpec monthlyPartitionEmptyTextPercent;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each monthly partition.")
    private ColumnWhitespaceWhitespaceTextPercentCheckSpec monthlyPartitionWhitespaceTextPercent;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each monthly partition.")
    private ColumnWhitespaceNullPlaceholderTextPercentCheckSpec monthlyPartitionNullPlaceholderTextPercent;

    @JsonPropertyDescription("Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec monthlyPartitionTextSurroundedByWhitespaceFound;

    @JsonPropertyDescription("This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec monthlyPartitionTextSurroundedByWhitespacePercent;


    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnWhitespaceEmptyTextFoundCheckSpec getMonthlyPartitionEmptyTextFound() {
        return monthlyPartitionEmptyTextFound;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param monthlyPartitionEmptyTextFound Max string empty count check.
     */
    public void setMonthlyPartitionEmptyTextFound(ColumnWhitespaceEmptyTextFoundCheckSpec monthlyPartitionEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionEmptyTextFound, monthlyPartitionEmptyTextFound));
        this.monthlyPartitionEmptyTextFound = monthlyPartitionEmptyTextFound;
        propagateHierarchyIdToField(monthlyPartitionEmptyTextFound, "monthly_partition_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnWhitespaceWhitespaceTextFoundCheckSpec getMonthlyPartitionWhitespaceTextFound() {
        return monthlyPartitionWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param monthlyPartitionWhitespaceTextFound Maximum string whitespace count check.
     */
    public void setMonthlyPartitionWhitespaceTextFound(ColumnWhitespaceWhitespaceTextFoundCheckSpec monthlyPartitionWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionWhitespaceTextFound, monthlyPartitionWhitespaceTextFound));
        this.monthlyPartitionWhitespaceTextFound = monthlyPartitionWhitespaceTextFound;
        propagateHierarchyIdToField(monthlyPartitionWhitespaceTextFound, "monthly_partition_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnWhitespaceNullPlaceholderTextFoundCheckSpec getMonthlyPartitionNullPlaceholderTextFound() {
        return monthlyPartitionNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param monthlyPartitionNullPlaceholderTextFound Maximum string null placeholder count check.
     */
    public void setMonthlyPartitionNullPlaceholderTextFound(ColumnWhitespaceNullPlaceholderTextFoundCheckSpec monthlyPartitionNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNullPlaceholderTextFound, monthlyPartitionNullPlaceholderTextFound));
        this.monthlyPartitionNullPlaceholderTextFound = monthlyPartitionNullPlaceholderTextFound;
        propagateHierarchyIdToField(monthlyPartitionNullPlaceholderTextFound, "monthly_partition_null_placeholder_text_found");
    }

    /**
     * Returns a maximum empty string percentage check.
     * @return Maximum empty string percentage check.
     */
    public ColumnWhitespaceEmptyTextPercentCheckSpec getMonthlyPartitionEmptyTextPercent() {
        return monthlyPartitionEmptyTextPercent;
    }

    /**
     * Sets a new definition of a maximum empty string percentage check.
     * @param monthlyPartitionEmptyTextPercent Maximum empty string percentage check.
     */
    public void setMonthlyPartitionEmptyTextPercent(ColumnWhitespaceEmptyTextPercentCheckSpec monthlyPartitionEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionEmptyTextPercent, monthlyPartitionEmptyTextPercent));
        this.monthlyPartitionEmptyTextPercent = monthlyPartitionEmptyTextPercent;
        propagateHierarchyIdToField(monthlyPartitionEmptyTextPercent, "monthly_partition_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnWhitespaceWhitespaceTextPercentCheckSpec getMonthlyPartitionWhitespaceTextPercent() {
        return monthlyPartitionWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param monthlyPartitionWhitespaceTextPercent Maximum string whitespace percent check.
     */
    public void setMonthlyPartitionWhitespaceTextPercent(ColumnWhitespaceWhitespaceTextPercentCheckSpec monthlyPartitionWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionWhitespaceTextPercent, monthlyPartitionWhitespaceTextPercent));
        this.monthlyPartitionWhitespaceTextPercent = monthlyPartitionWhitespaceTextPercent;
        propagateHierarchyIdToField(monthlyPartitionWhitespaceTextPercent, "monthly_partition_whitespace_text_percent");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnWhitespaceNullPlaceholderTextPercentCheckSpec getMonthlyPartitionNullPlaceholderTextPercent() {
        return monthlyPartitionNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param monthlyPartitionNullPlaceholderTextPercent Maximum string null placeholder percent check.
     */
    public void setMonthlyPartitionNullPlaceholderTextPercent(ColumnWhitespaceNullPlaceholderTextPercentCheckSpec monthlyPartitionNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionNullPlaceholderTextPercent, monthlyPartitionNullPlaceholderTextPercent));
        this.monthlyPartitionNullPlaceholderTextPercent = monthlyPartitionNullPlaceholderTextPercent;
        propagateHierarchyIdToField(monthlyPartitionNullPlaceholderTextPercent, "monthly_partition_null_placeholder_text_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec getMonthlyPartitionTextSurroundedByWhitespaceFound() {
        return monthlyPartitionTextSurroundedByWhitespaceFound;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param monthlyPartitionTextSurroundedByWhitespaceFound String surrounded by whitespace count check.
     */
    public void setMonthlyPartitionTextSurroundedByWhitespaceFound(ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec monthlyPartitionTextSurroundedByWhitespaceFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextSurroundedByWhitespaceFound, monthlyPartitionTextSurroundedByWhitespaceFound));
        this.monthlyPartitionTextSurroundedByWhitespaceFound = monthlyPartitionTextSurroundedByWhitespaceFound;
        propagateHierarchyIdToField(monthlyPartitionTextSurroundedByWhitespaceFound, "monthly_partition_text_surrounded_by_whitespace_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec getMonthlyPartitionTextSurroundedByWhitespacePercent() {
        return monthlyPartitionTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param monthlyPartitionTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setMonthlyPartitionTextSurroundedByWhitespacePercent(ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec monthlyPartitionTextSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextSurroundedByWhitespacePercent, monthlyPartitionTextSurroundedByWhitespacePercent));
        this.monthlyPartitionTextSurroundedByWhitespacePercent = monthlyPartitionTextSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(monthlyPartitionTextSurroundedByWhitespacePercent, "monthly_partition_text_surrounded_by_whitespace_percent");
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
