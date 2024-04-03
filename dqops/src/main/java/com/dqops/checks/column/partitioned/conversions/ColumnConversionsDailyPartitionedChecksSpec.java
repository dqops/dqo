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
 * Container of conversion test checks that are monitoring if text values are convertible to a target data type at a daily partition level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConversionsDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConversionsDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_text_parsable_to_boolean_percent", o -> o.dailyPartitionTextParsableToBooleanPercent);
            put("daily_partition_text_parsable_to_integer_percent", o -> o.dailyPartitionTextParsableToIntegerPercent);
            put("daily_partition_text_parsable_to_float_percent", o -> o.dailyPartitionTextParsableToFloatPercent);
            put("daily_partition_text_parsable_to_date_percent", o -> o.dailyPartitionTextParsableToDatePercent);
        }
    };


    @JsonPropertyDescription("Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, " +
            "text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextParsableToBooleanPercentCheckSpec dailyPartitionTextParsableToBooleanPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextParsableToIntegerPercentCheckSpec dailyPartitionTextParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextParsableToFloatPercentCheckSpec dailyPartitionTextParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.")
    private ColumnTextParsableToDatePercentCheckSpec dailyPartitionTextParsableToDatePercent;


    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getDailyPartitionTextParsableToBooleanPercent() {
        return dailyPartitionTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param dailyPartitionTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setDailyPartitionTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec dailyPartitionTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToBooleanPercent, dailyPartitionTextParsableToBooleanPercent));
        this.dailyPartitionTextParsableToBooleanPercent = dailyPartitionTextParsableToBooleanPercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToBooleanPercent, "daily_partition_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getDailyPartitionTextParsableToIntegerPercent() {
        return dailyPartitionTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param dailyPartitionTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setDailyPartitionTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec dailyPartitionTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToIntegerPercent, dailyPartitionTextParsableToIntegerPercent));
        this.dailyPartitionTextParsableToIntegerPercent = dailyPartitionTextParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToIntegerPercent, "daily_partition_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getDailyPartitionTextParsableToFloatPercent() {
        return dailyPartitionTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param dailyPartitionTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setDailyPartitionTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec dailyPartitionTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToFloatPercent, dailyPartitionTextParsableToFloatPercent));
        this.dailyPartitionTextParsableToFloatPercent = dailyPartitionTextParsableToFloatPercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToFloatPercent, "daily_partition_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getDailyPartitionTextParsableToDatePercent() {
        return dailyPartitionTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param dailyPartitionTextParsableToDatePercent String valid dates percent check.
     */
    public void setDailyPartitionTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec dailyPartitionTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextParsableToDatePercent, dailyPartitionTextParsableToDatePercent));
        this.dailyPartitionTextParsableToDatePercent = dailyPartitionTextParsableToDatePercent;
        propagateHierarchyIdToField(dailyPartitionTextParsableToDatePercent, "daily_partition_text_parsable_to_date_percent");
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
