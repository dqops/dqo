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
package com.dqops.checks.column.monitoring.conversions;

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
 * Container of conversion test checks that are monitoring if text values are convertible to a target data type at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConversionsMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConversionsMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_text_parsable_to_boolean_percent", o -> o.monthlyTextParsableToBooleanPercent);
            put("monthly_text_parsable_to_integer_percent", o -> o.monthlyTextParsableToIntegerPercent);
            put("monthly_text_parsable_to_float_percent", o -> o.monthlyTextParsableToFloatPercent);
            put("monthly_text_parsable_to_date_percent", o -> o.monthlyTextParsableToDatePercent);
        }
    };


    @JsonPropertyDescription("Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, " +
            "text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextParsableToBooleanPercentCheckSpec monthlyTextParsableToBooleanPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextParsableToIntegerPercentCheckSpec monthlyTextParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextParsableToFloatPercentCheckSpec monthlyTextParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each month when the data quality check was evaluated.")
    private ColumnTextParsableToDatePercentCheckSpec monthlyTextParsableToDatePercent;


    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getMonthlyTextParsableToBooleanPercent() {
        return monthlyTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param monthlyTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setMonthlyTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec monthlyTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextParsableToBooleanPercent, monthlyTextParsableToBooleanPercent));
        this.monthlyTextParsableToBooleanPercent = monthlyTextParsableToBooleanPercent;
        propagateHierarchyIdToField(monthlyTextParsableToBooleanPercent, "monthly_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getMonthlyTextParsableToIntegerPercent() {
        return monthlyTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param monthlyTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setMonthlyTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec monthlyTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextParsableToIntegerPercent, monthlyTextParsableToIntegerPercent));
        this.monthlyTextParsableToIntegerPercent = monthlyTextParsableToIntegerPercent;
        propagateHierarchyIdToField(monthlyTextParsableToIntegerPercent, "monthly_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getMonthlyTextParsableToFloatPercent() {
        return monthlyTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param monthlyTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setMonthlyTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec monthlyTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextParsableToFloatPercent, monthlyTextParsableToFloatPercent));
        this.monthlyTextParsableToFloatPercent = monthlyTextParsableToFloatPercent;
        propagateHierarchyIdToField(monthlyTextParsableToFloatPercent, "monthly_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getMonthlyTextParsableToDatePercent() {
        return monthlyTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param monthlyTextParsableToDatePercent String valid dates percent check.
     */
    public void setMonthlyTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec monthlyTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextParsableToDatePercent, monthlyTextParsableToDatePercent));
        this.monthlyTextParsableToDatePercent = monthlyTextParsableToDatePercent;
        propagateHierarchyIdToField(monthlyTextParsableToDatePercent, "monthly_text_parsable_to_date_percent");
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
    public ColumnConversionsMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnConversionsMonthlyMonitoringChecksSpec)super.deepClone();
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
