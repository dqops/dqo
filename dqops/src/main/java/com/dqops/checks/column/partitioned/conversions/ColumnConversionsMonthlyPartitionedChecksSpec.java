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
package com.dqops.checks.column.partitioned.conversions;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.conversions.ColumnTextParsableToBooleanPercentCheckSpec;
import com.dqops.checks.column.checkspecs.conversions.ColumnTextParsableToDatePercentCheckSpec;
import com.dqops.checks.column.checkspecs.conversions.ColumnTextParsableToFloatPercentCheckSpec;
import com.dqops.checks.column.checkspecs.conversions.ColumnTextParsableToIntegerPercentCheckSpec;
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
 * Container of conversion test checks that are monitoring if text values are convertible to a target data type at a monthly partition level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConversionsMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConversionsMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_text_parsable_to_boolean_percent", o -> o.monthlyPartitionTextParsableToBooleanPercent);
            put("monthly_partition_text_parsable_to_integer_percent", o -> o.monthlyPartitionTextParsableToIntegerPercent);
            put("monthly_partition_text_parsable_to_float_percent", o -> o.monthlyPartitionTextParsableToFloatPercent);
            put("monthly_partition_text_parsable_to_date_percent", o -> o.monthlyPartitionTextParsableToDatePercent);
        }
    };


    @JsonPropertyDescription("Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, " +
            "text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextParsableToBooleanPercentCheckSpec monthlyPartitionTextParsableToBooleanPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextParsableToIntegerPercentCheckSpec monthlyPartitionTextParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextParsableToFloatPercentCheckSpec monthlyPartitionTextParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.")
    private ColumnTextParsableToDatePercentCheckSpec monthlyPartitionTextParsableToDatePercent;


    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getMonthlyPartitionTextParsableToBooleanPercent() {
        return monthlyPartitionTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param monthlyPartitionTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setMonthlyPartitionTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec monthlyPartitionTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextParsableToBooleanPercent, monthlyPartitionTextParsableToBooleanPercent));
        this.monthlyPartitionTextParsableToBooleanPercent = monthlyPartitionTextParsableToBooleanPercent;
        propagateHierarchyIdToField(monthlyPartitionTextParsableToBooleanPercent, "monthly_partition_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getMonthlyPartitionTextParsableToIntegerPercent() {
        return monthlyPartitionTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param monthlyPartitionTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setMonthlyPartitionTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec monthlyPartitionTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextParsableToIntegerPercent, monthlyPartitionTextParsableToIntegerPercent));
        this.monthlyPartitionTextParsableToIntegerPercent = monthlyPartitionTextParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyPartitionTextParsableToIntegerPercent, "monthly_partition_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getMonthlyPartitionTextParsableToFloatPercent() {
        return monthlyPartitionTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param monthlyPartitionTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setMonthlyPartitionTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec monthlyPartitionTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextParsableToFloatPercent, monthlyPartitionTextParsableToFloatPercent));
        this.monthlyPartitionTextParsableToFloatPercent = monthlyPartitionTextParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyPartitionTextParsableToFloatPercent, "monthly_partition_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getMonthlyPartitionTextParsableToDatePercent() {
        return monthlyPartitionTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param monthlyPartitionTextParsableToDatePercent String valid dates percent check.
     */
    public void setMonthlyPartitionTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec monthlyPartitionTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextParsableToDatePercent, monthlyPartitionTextParsableToDatePercent));
        this.monthlyPartitionTextParsableToDatePercent = monthlyPartitionTextParsableToDatePercent;
        propagateHierarchyIdToField(monthlyPartitionTextParsableToDatePercent, "monthly_partition_text_parsable_to_date_percent");
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
