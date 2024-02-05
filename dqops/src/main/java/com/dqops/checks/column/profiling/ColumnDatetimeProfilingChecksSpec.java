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
package com.dqops.checks.column.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.datetime.ColumnDateValuesInFuturePercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDatetimeDateMatchFormatPercentCheckSpec;
import com.dqops.checks.column.checkspecs.datetime.ColumnDatetimeValueInRangeDatePercentCheckSpec;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for datetime.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnDatetimeProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnDatetimeProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_date_values_in_future_percent", o -> o.profileDateValuesInFuturePercent);
            put("profile_datetime_value_in_range_date_percent", o -> o.profileDatetimeValueInRangeDatePercent);
            put("profile_date_match_format_percent", o -> o.profileDateMatchFormatPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.")
    private ColumnDateValuesInFuturePercentCheckSpec profileDateValuesInFuturePercent;

    @JsonPropertyDescription("Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.")
    private ColumnDatetimeValueInRangeDatePercentCheckSpec profileDatetimeValueInRangeDatePercent;

    @JsonPropertyDescription("Verifies that the percentage of date values matching the given format in a text column does not exceed the maximum accepted percentage.")
    private ColumnDatetimeDateMatchFormatPercentCheckSpec profileDateMatchFormatPercent;


    /**
     * Returns a date values in future percent check specification.
     * @return Date values in future percent check specification.
     */
    public ColumnDateValuesInFuturePercentCheckSpec getProfileDateValuesInFuturePercent() {
        return profileDateValuesInFuturePercent;
    }

    /**
     * Sets a new date values in future percent check specification.
     * @param profileDateValuesInFuturePercent Date values in future percent check specification.
     */
    public void setProfileDateValuesInFuturePercent(ColumnDateValuesInFuturePercentCheckSpec profileDateValuesInFuturePercent) {
        this.setDirtyIf(!Objects.equals(this.profileDateValuesInFuturePercent, profileDateValuesInFuturePercent));
        this.profileDateValuesInFuturePercent = profileDateValuesInFuturePercent;
        propagateHierarchyIdToField(profileDateValuesInFuturePercent, "profile_date_values_in_future_percent");
    }

    /**
     * Returns a datetime value in range date percentage check.
     * @return Maximum datetime value in range date percentage check.
     */
    public ColumnDatetimeValueInRangeDatePercentCheckSpec getProfileDatetimeValueInRangeDatePercent() {
        return profileDatetimeValueInRangeDatePercent;
    }

    /**
     * Sets a new definition of a datetime value in range date percentage check.
     * @param profileDatetimeValueInRangeDatePercent Datetime value in range date percentage check.
     */
    public void setProfileDatetimeValueInRangeDatePercent(ColumnDatetimeValueInRangeDatePercentCheckSpec profileDatetimeValueInRangeDatePercent) {
        this.setDirtyIf(!Objects.equals(this.profileDatetimeValueInRangeDatePercent, profileDatetimeValueInRangeDatePercent));
        this.profileDatetimeValueInRangeDatePercent = profileDatetimeValueInRangeDatePercent;
        propagateHierarchyIdToField(profileDatetimeValueInRangeDatePercent, "profile_datetime_value_in_range_date_percent");
    }

    /**
     * Returns a date match format percentage check.
     * @return Maximum date match format percentage check.
     */
    public ColumnDatetimeDateMatchFormatPercentCheckSpec getProfileDateMatchFormatPercent() {
        return profileDateMatchFormatPercent;
    }

    /**
     * Sets a new definition of a date match format percentage check.
     * @param profileDateMatchFormatPercent Date match format percentage check.
     */
    public void setProfileDateMatchFormatPercent(ColumnDatetimeDateMatchFormatPercentCheckSpec profileDateMatchFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileDateMatchFormatPercent, profileDateMatchFormatPercent));
        this.profileDateMatchFormatPercent = profileDateMatchFormatPercent;
        propagateHierarchyIdToField(profileDateMatchFormatPercent, "profile_date_match_format_percent");
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
    public ColumnDatetimeProfilingChecksSpec deepClone() {
        return (ColumnDatetimeProfilingChecksSpec)super.deepClone();
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
        return CheckType.profiling;
    }

    /**
     * Gets the check timescale appropriate for all checks in this category.
     *
     * @return Corresponding check timescale.
     */
    @Override
    @JsonIgnore
    public CheckTimeScale getCheckTimeScale() {
        return null;
    }

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.CONTAINS_DATE;
    }
}