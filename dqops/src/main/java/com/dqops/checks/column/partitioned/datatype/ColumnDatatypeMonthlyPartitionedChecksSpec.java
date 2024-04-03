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
package com.dqops.checks.column.partitioned.datatype;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.datatype.ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec;
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
 * Container of datatype data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatatypeMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatatypeMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_detected_datatype_in_text", o -> o.monthlyPartitionDetectedDatatypeInText);
            put("monthly_partition_detected_datatype_in_text_changed", o -> o.monthlyPartitionDetectedDatatypeInTextChanged);
        }
    };

    @JsonPropertyDescription("Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each monthly partition.")
    private ColumnDetectedDatatypeInTextCheckSpec monthlyPartitionDetectedDatatypeInText;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. Stores a separate data quality check result for each monthly partition.")
    private ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec monthlyPartitionDetectedDatatypeInTextChanged;

    /**
     * Returns a count of expected values in datatype detected check.
     * @return Datatype detected check.
     */
    public ColumnDetectedDatatypeInTextCheckSpec getMonthlyPartitionDetectedDatatypeInText() {
        return monthlyPartitionDetectedDatatypeInText;
    }

    /**
     * Sets a new definition of a datatype detected check.
     * @param monthlyPartitionDetectedDatatypeInText Datatype detected check.
     */
    public void setMonthlyPartitionDetectedDatatypeInText(ColumnDetectedDatatypeInTextCheckSpec monthlyPartitionDetectedDatatypeInText) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDetectedDatatypeInText, monthlyPartitionDetectedDatatypeInText));
        this.monthlyPartitionDetectedDatatypeInText = monthlyPartitionDetectedDatatypeInText;
        propagateHierarchyIdToField(monthlyPartitionDetectedDatatypeInText, "monthly_partition_detected_datatype_in_text");
    }

    /**
     * Returns a count of expected values in datatype changed check.
     * @return Datatype changed check.
     */
    public ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec getMonthlyPartitionDetectedDatatypeInTextChanged() {
        return monthlyPartitionDetectedDatatypeInTextChanged;
    }

    /**
     * Sets a new definition of a datatype changed check.
     * @param monthlyPartitionDetectedDatatypeInTextChanged Datatype changed check.
     */
    public void setMonthlyPartitionDetectedDatatypeInTextChanged(ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec monthlyPartitionDetectedDatatypeInTextChanged) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionDetectedDatatypeInTextChanged, monthlyPartitionDetectedDatatypeInTextChanged));
        this.monthlyPartitionDetectedDatatypeInTextChanged = monthlyPartitionDetectedDatatypeInTextChanged;
        propagateHierarchyIdToField(monthlyPartitionDetectedDatatypeInTextChanged, "monthly_partition_detected_datatype_in_text_changed");
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
