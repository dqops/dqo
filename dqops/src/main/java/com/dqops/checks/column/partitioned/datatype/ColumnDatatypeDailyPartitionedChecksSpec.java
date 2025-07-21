/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
 * Container of datatype data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatatypeDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatatypeDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_detected_datatype_in_text", o -> o.dailyPartitionDetectedDatatypeInText);
            put("daily_partition_detected_datatype_in_text_changed", o -> o.dailyPartitionDetectedDatatypeInTextChanged);
        }
    };

    @JsonPropertyDescription("Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each daily partition.")
    private ColumnDetectedDatatypeInTextCheckSpec dailyPartitionDetectedDatatypeInText;

    @JsonPropertyDescription("Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Stores a separate data quality check result for each daily partition.")
    private ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec dailyPartitionDetectedDatatypeInTextChanged;

    /**
     * Returns a count of expected values in datatype changed check.
     * @return Datatype changed check.
     */
    public ColumnDetectedDatatypeInTextCheckSpec getDailyPartitionDetectedDatatypeInText() {
        return dailyPartitionDetectedDatatypeInText;
    }

    /**
     * Sets a new definition of a datatype changed check.
     * @param dailyPartitionDetectedDatatypeInText Datatype changed check.
     */
    public void setDailyPartitionDetectedDatatypeInText(ColumnDetectedDatatypeInTextCheckSpec dailyPartitionDetectedDatatypeInText) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDetectedDatatypeInText, dailyPartitionDetectedDatatypeInText));
        this.dailyPartitionDetectedDatatypeInText = dailyPartitionDetectedDatatypeInText;
        propagateHierarchyIdToField(dailyPartitionDetectedDatatypeInText, "daily_partition_detected_datatype_in_text");
    }

    /**
     * Returns a count of expected values in datatype changed check.
     * @return Datatype changed check.
     */
    public ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec getDailyPartitionDetectedDatatypeInTextChanged() {
        return dailyPartitionDetectedDatatypeInTextChanged;
    }

    /**
     * Sets a new definition of a datatype changed check.
     * @param dailyPartitionDetectedDatatypeInTextChanged Datatype changed check.
     */
    public void setDailyPartitionDetectedDatatypeInTextChanged(ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec dailyPartitionDetectedDatatypeInTextChanged) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionDetectedDatatypeInTextChanged, dailyPartitionDetectedDatatypeInTextChanged));
        this.dailyPartitionDetectedDatatypeInTextChanged = dailyPartitionDetectedDatatypeInTextChanged;
        propagateHierarchyIdToField(dailyPartitionDetectedDatatypeInTextChanged, "daily_partition_detected_datatype_in_text_changed");
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
