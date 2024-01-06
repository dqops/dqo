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
import com.dqops.checks.column.checkspecs.acceptedvalues.*;
import com.dqops.checks.column.checkspecs.strings.*;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for accepted values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_string_value_in_set_percent", o -> o.profileStringValueInSetPercent);
            put("profile_number_value_in_set_percent", o -> o.profileNumberValueInSetPercent);
            put("profile_expected_strings_in_use_count", o -> o.profileExpectedStringsInUseCount);
            put("profile_expected_strings_in_top_values_count", o -> o.profileExpectedStringsInTopValuesCount);
            put("profile_expected_numbers_in_use_count", o -> o.profileExpectedNumbersInUseCount);
        }
    };

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.")
    private ColumnStringValueInSetPercentCheckSpec profileStringValueInSetPercent;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.")
    private ColumnNumberValueInSetPercentCheckSpec profileNumberValueInSetPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).")
    private ColumnExpectedStringsInUseCountCheckSpec profileExpectedStringsInUseCount;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values.")
    private ColumnExpectedStringsInTopValuesCountCheckSpec profileExpectedStringsInTopValuesCount;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).")
    private ColumnExpectedNumbersInUseCountCheckSpec profileExpectedNumbersInUseCount;


    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent check.
     */
    public ColumnStringValueInSetPercentCheckSpec getProfileStringValueInSetPercent() {
        return profileStringValueInSetPercent;
    }

    /**
     * Sets a new definition of a strings in set percent check.
     * @param profileStringValueInSetPercent Strings in set percent check.
     */
    public void setProfileStringValueInSetPercent(ColumnStringValueInSetPercentCheckSpec profileStringValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringValueInSetPercent, profileStringValueInSetPercent));
        this.profileStringValueInSetPercent = profileStringValueInSetPercent;
        propagateHierarchyIdToField(profileStringValueInSetPercent, "profile_string_value_in_set_percent");
    }

    /**
     * Returns a numbers valid percent check specification.
     * @return Numbers valid percent check specification.
     */
    public ColumnNumberValueInSetPercentCheckSpec getProfileNumberValueInSetPercent() {
        return profileNumberValueInSetPercent;
    }

    /**
     * Sets a new specification of a numbers valid percent check specification.
     * @param profileNumberValueInSetPercent Numbers valid percent check specification.
     */
    public void setProfileNumberValueInSetPercent(ColumnNumberValueInSetPercentCheckSpec profileNumberValueInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.profileNumberValueInSetPercent, profileNumberValueInSetPercent));
        this.profileNumberValueInSetPercent = profileNumberValueInSetPercent;
        propagateHierarchyIdToField(profileNumberValueInSetPercent, "profile_number_value_in_set_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnExpectedStringsInUseCountCheckSpec getProfileExpectedStringsInUseCount() {
        return profileExpectedStringsInUseCount;
    }

    /**
     * Sets a new definition of a string in set count check.
     * @param profileExpectedStringsInUseCount String in set count check.
     */
    public void setProfileExpectedStringsInUseCount(ColumnExpectedStringsInUseCountCheckSpec profileExpectedStringsInUseCount) {
        this.setDirtyIf(!Objects.equals(this.profileExpectedStringsInUseCount, profileExpectedStringsInUseCount));
        this.profileExpectedStringsInUseCount = profileExpectedStringsInUseCount;
        propagateHierarchyIdToField(profileExpectedStringsInUseCount, "profile_expected_strings_in_use_count");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedStringsInTopValuesCountCheckSpec getProfileExpectedStringsInTopValuesCount() {
        return profileExpectedStringsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param profileExpectedStringsInTopValuesCount Most popular values count check.
     */
    public void setProfileExpectedStringsInTopValuesCount(ColumnExpectedStringsInTopValuesCountCheckSpec profileExpectedStringsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.profileExpectedStringsInTopValuesCount, profileExpectedStringsInTopValuesCount));
        this.profileExpectedStringsInTopValuesCount = profileExpectedStringsInTopValuesCount;
        propagateHierarchyIdToField(profileExpectedStringsInTopValuesCount, "profile_expected_strings_in_top_values_count");
    }

    /**
     * Returns a numbers in set count check specification.
     * @return Numbers in set count check specification.
     */
    public ColumnExpectedNumbersInUseCountCheckSpec getProfileExpectedNumbersInUseCount() {
        return profileExpectedNumbersInUseCount;
    }

    /**
     * Sets a new specification of a numbers in set count check specification.
     * @param profileExpectedNumbersInUseCount Numbers in set count check specification.
     */
    public void setProfileExpectedNumbersInUseCount(ColumnExpectedNumbersInUseCountCheckSpec profileExpectedNumbersInUseCount) {
        this.setDirtyIf(!Objects.equals(this.profileExpectedNumbersInUseCount, profileExpectedNumbersInUseCount));
        this.profileExpectedNumbersInUseCount = profileExpectedNumbersInUseCount;
        propagateHierarchyIdToField(profileExpectedNumbersInUseCount, "profile_expected_numbers_in_use_count");
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
    public ColumnAcceptedValuesProfilingChecksSpec deepClone() {
        return (ColumnAcceptedValuesProfilingChecksSpec)super.deepClone();
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
}
