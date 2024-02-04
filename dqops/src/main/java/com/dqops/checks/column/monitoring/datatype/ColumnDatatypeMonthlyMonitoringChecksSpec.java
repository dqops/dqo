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
package com.dqops.checks.column.monitoring.datatype;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeStringDatatypeChangedCheckSpec;
import com.dqops.checks.column.checkspecs.datatype.ColumnDetectedDatatypeInTextCheckSpec;
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
 * Container of datatype data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatatypeMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatatypeMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_detected_datatype_in_text", o -> o.monthlyDetectedDatatypeInText);
            put("monthly_detected_datatype_in_text_changed", o -> o.monthlyDetectedDatatypeInTextChanged);
        }
    };

    @JsonPropertyDescription("Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnDetectedDatatypeInTextCheckSpec monthlyDetectedDatatypeInText;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnDatatypeStringDatatypeChangedCheckSpec monthlyDetectedDatatypeInTextChanged;

    /**
     * Returns a count of expected values in datatype detect check.
     * @return Datatype detect check.
     */
    public ColumnDetectedDatatypeInTextCheckSpec getMonthlyDetectedDatatypeInText() {
        return monthlyDetectedDatatypeInText;
    }

    /**
     * Sets a new definition of a datatype detect check.
     * @param monthlyDetectedDatatypeInText Datatype detect check.
     */
    public void setMonthlyDetectedDatatypeInText(ColumnDetectedDatatypeInTextCheckSpec monthlyDetectedDatatypeInText) {
        this.setDirtyIf(!Objects.equals(this.monthlyDetectedDatatypeInText, monthlyDetectedDatatypeInText));
        this.monthlyDetectedDatatypeInText = monthlyDetectedDatatypeInText;
        propagateHierarchyIdToField(monthlyDetectedDatatypeInText, "monthly_detected_datatype_in_text");
    }

    /**
     * Returns a count of expected values in datatype detect check.
     * @return Datatype detect check.
     */
    public ColumnDatatypeStringDatatypeChangedCheckSpec getMonthlyDetectedDatatypeInTextChanged() {
        return monthlyDetectedDatatypeInTextChanged;
    }

    /**
     * Sets a new definition of a datatype detect check.
     * @param monthlyDetectedDatatypeInTextChanged Datatype detect check.
     */
    public void setMonthlyDetectedDatatypeInTextChanged(ColumnDatatypeStringDatatypeChangedCheckSpec monthlyDetectedDatatypeInTextChanged) {
        this.setDirtyIf(!Objects.equals(this.monthlyDetectedDatatypeInTextChanged, monthlyDetectedDatatypeInTextChanged));
        this.monthlyDetectedDatatypeInTextChanged = monthlyDetectedDatatypeInTextChanged;
        propagateHierarchyIdToField(monthlyDetectedDatatypeInTextChanged, "monthly_detected_datatype_in_text_changed");
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
    public ColumnDatatypeMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnDatatypeMonthlyMonitoringChecksSpec)super.deepClone();
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
