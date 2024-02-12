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
 * Container of conversion test checks that are monitoring if text values are convertible to a target data type at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnConversionsDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnConversionsDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_text_parsable_to_boolean_percent", o -> o.dailyTextParsableToBooleanPercent);
            put("daily_text_parsable_to_integer_percent", o -> o.dailyTextParsableToIntegerPercent);
            put("daily_text_parsable_to_float_percent", o -> o.dailyTextParsableToFloatPercent);
            put("daily_text_parsable_to_date_percent", o -> o.dailyTextParsableToDatePercent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, " +
            "text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextParsableToBooleanPercentCheckSpec dailyTextParsableToBooleanPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextParsableToIntegerPercentCheckSpec dailyTextParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextParsableToFloatPercentCheckSpec dailyTextParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnTextParsableToDatePercentCheckSpec dailyTextParsableToDatePercent;


    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent check.
     */
    public ColumnTextParsableToBooleanPercentCheckSpec getDailyTextParsableToBooleanPercent() {
        return dailyTextParsableToBooleanPercent;
    }

    /**
     * Sets a new definition of a string boolean placeholder percent check.
     * @param dailyTextParsableToBooleanPercent String boolean placeholder percent check.
     */
    public void setDailyTextParsableToBooleanPercent(ColumnTextParsableToBooleanPercentCheckSpec dailyTextParsableToBooleanPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextParsableToBooleanPercent, dailyTextParsableToBooleanPercent));
        this.dailyTextParsableToBooleanPercent = dailyTextParsableToBooleanPercent;
        propagateHierarchyIdToField(dailyTextParsableToBooleanPercent, "daily_text_parsable_to_boolean_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnTextParsableToIntegerPercentCheckSpec getDailyTextParsableToIntegerPercent() {
        return dailyTextParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a string parsable to integer percent check.
     * @param dailyTextParsableToIntegerPercent String parsable to integer percent check.
     */
    public void setDailyTextParsableToIntegerPercent(ColumnTextParsableToIntegerPercentCheckSpec dailyTextParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextParsableToIntegerPercent, dailyTextParsableToIntegerPercent));
        this.dailyTextParsableToIntegerPercent = dailyTextParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyTextParsableToIntegerPercent, "daily_text_parsable_to_integer_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnTextParsableToFloatPercentCheckSpec getDailyTextParsableToFloatPercent() {
        return dailyTextParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a string parsable to float percent check.
     * @param dailyTextParsableToFloatPercent String parsable to float percent check.
     */
    public void setDailyTextParsableToFloatPercent(ColumnTextParsableToFloatPercentCheckSpec dailyTextParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextParsableToFloatPercent, dailyTextParsableToFloatPercent));
        this.dailyTextParsableToFloatPercent = dailyTextParsableToFloatPercent;
        propagateHierarchyIdToField(dailyTextParsableToFloatPercent, "daily_text_parsable_to_float_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent check.
     */
    public ColumnTextParsableToDatePercentCheckSpec getDailyTextParsableToDatePercent() {
        return dailyTextParsableToDatePercent;
    }

    /**
     * Sets a new definition of a string valid dates percent check.
     * @param dailyTextParsableToDatePercent String valid dates percent check.
     */
    public void setDailyTextParsableToDatePercent(ColumnTextParsableToDatePercentCheckSpec dailyTextParsableToDatePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextParsableToDatePercent, dailyTextParsableToDatePercent));
        this.dailyTextParsableToDatePercent = dailyTextParsableToDatePercent;
        propagateHierarchyIdToField(dailyTextParsableToDatePercent, "daily_text_parsable_to_date_percent");
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
    public ColumnConversionsDailyMonitoringChecksSpec deepClone() {
        return (ColumnConversionsDailyMonitoringChecksSpec)super.deepClone();
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
