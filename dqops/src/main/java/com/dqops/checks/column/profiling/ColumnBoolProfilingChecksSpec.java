/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.profiling;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.bool.ColumnFalsePercentCheckSpec;
import com.dqops.checks.column.checkspecs.bool.ColumnTruePercentCheckSpec;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for booleans.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBoolProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBoolProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_true_percent", o -> o.profileTruePercent);
            put("profile_false_percent", o -> o.profileFalsePercent);
        }
    };

    @JsonPropertyDescription("Measures the percentage of **true** values in a boolean column and verifies that it is within the accepted range.")
    private ColumnTruePercentCheckSpec profileTruePercent;

    @JsonPropertyDescription("Measures the percentage of **false** values in a boolean column and verifies that it is within the accepted range.")
    private ColumnFalsePercentCheckSpec profileFalsePercent;

    /**
     * Returns a true percent check specification.
     * @return True percent check specification.
     */
    public ColumnTruePercentCheckSpec getProfileTruePercent() {
        return profileTruePercent;
    }

    /**
     * Sets true percent check specification.
     * @param profileTruePercent True percent check specification.
     */
    public void setProfileTruePercent(ColumnTruePercentCheckSpec profileTruePercent) {
        this.setDirtyIf(!Objects.equals(this.profileTruePercent, profileTruePercent));
        this.profileTruePercent = profileTruePercent;
        propagateHierarchyIdToField(profileTruePercent, "profile_true_percent");
    }

    /**
     * Returns a false percent check specification.
     * @return False percent check specification.
     */
    public ColumnFalsePercentCheckSpec getProfileFalsePercent() {
        return profileFalsePercent;
    }

    /**
     * Sets a new false percent check specification.
     * @param profileFalsePercent False percent check specification.
     */
    public void setProfileFalsePercent(ColumnFalsePercentCheckSpec profileFalsePercent) {
        this.setDirtyIf(!Objects.equals(this.profileFalsePercent, profileFalsePercent));
        this.profileFalsePercent = profileFalsePercent;
        propagateHierarchyIdToField(profileFalsePercent, "profile_false_percent");
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
    public ColumnBoolProfilingChecksSpec deepClone() {
        return (ColumnBoolProfilingChecksSpec)super.deepClone();
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
        return DataTypeCategory.BOOLEAN;
    }
}