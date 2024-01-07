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
import com.dqops.checks.column.checkspecs.blanks.*;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for blank values in text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBlanksProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBlanksProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_string_empty_count", o -> o.profileStringEmptyCount);
            put("profile_string_whitespace_count", o -> o.profileStringWhitespaceCount);
            put("profile_string_null_placeholder_count", o -> o.profileStringNullPlaceholderCount);
            put("profile_string_empty_percent", o -> o.profileStringEmptyPercent);
            put("profile_string_whitespace_percent", o -> o.profileStringWhitespacePercent);
            put("profile_string_null_placeholder_percent", o -> o.profileStringNullPlaceholderPercent);
        }
    };

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted count.")
    private ColumnBlanksEmptyTextFoundCheckSpec profileStringEmptyCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.")
    private ColumnBlanksWhitespaceTextFoundCheckSpec profileStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.")
    private ColumnBlanksNullPlaceholderTextFoundCheckSpec profileStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.")
    private ColumnBlanksEmptyTextPercentCheckSpec profileStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.")
    private ColumnBlanksWhitespaceTextPercentCheckSpec profileStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.")
    private ColumnBlanksNullPlaceholderTextPercentCheckSpec profileStringNullPlaceholderPercent;


    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnBlanksEmptyTextFoundCheckSpec getProfileStringEmptyCount() {
        return profileStringEmptyCount;
    }

    /**
     * Sets a new definition of a string empty count check.
     * @param profileStringEmptyCount String empty count check.
     */
    public void setProfileStringEmptyCount(ColumnBlanksEmptyTextFoundCheckSpec profileStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringEmptyCount, profileStringEmptyCount));
        this.profileStringEmptyCount = profileStringEmptyCount;
        propagateHierarchyIdToField(profileStringEmptyCount, "profile_string_empty_count");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnBlanksWhitespaceTextFoundCheckSpec getProfileStringWhitespaceCount() {
        return profileStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a string whitespace count check.
     * @param profileStringWhitespaceCount String whitespace count check.
     */
    public void setProfileStringWhitespaceCount(ColumnBlanksWhitespaceTextFoundCheckSpec profileStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringWhitespaceCount, profileStringWhitespaceCount));
        this.profileStringWhitespaceCount = profileStringWhitespaceCount;
        propagateHierarchyIdToField(profileStringWhitespaceCount, "profile_string_whitespace_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextFoundCheckSpec getProfileStringNullPlaceholderCount() {
        return profileStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a string null placeholder count check.
     * @param profileStringNullPlaceholderCount String null placeholder count check.
     */
    public void setProfileStringNullPlaceholderCount(ColumnBlanksNullPlaceholderTextFoundCheckSpec profileStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.profileStringNullPlaceholderCount, profileStringNullPlaceholderCount));
        this.profileStringNullPlaceholderCount = profileStringNullPlaceholderCount;
        propagateHierarchyIdToField(profileStringNullPlaceholderCount, "profile_string_null_placeholder_count");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnBlanksEmptyTextPercentCheckSpec getProfileStringEmptyPercent() {
        return profileStringEmptyPercent;
    }

    /**
     * Sets a new definition of a string empty percent check.
     * @param profileStringEmptyPercent String empty percent check.
     */
    public void setProfileStringEmptyPercent(ColumnBlanksEmptyTextPercentCheckSpec profileStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringEmptyPercent, profileStringEmptyPercent));
        this.profileStringEmptyPercent = profileStringEmptyPercent;
        propagateHierarchyIdToField(profileStringEmptyPercent, "profile_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnBlanksWhitespaceTextPercentCheckSpec getProfileStringWhitespacePercent() {
        return profileStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a string whitespace percent check.
     * @param profileStringWhitespacePercent String whitespace percent check.
     */
    public void setProfileStringWhitespacePercent(ColumnBlanksWhitespaceTextPercentCheckSpec profileStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringWhitespacePercent, profileStringWhitespacePercent));
        this.profileStringWhitespacePercent = profileStringWhitespacePercent;
        propagateHierarchyIdToField(profileStringWhitespacePercent, "profile_string_whitespace_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextPercentCheckSpec getProfileStringNullPlaceholderPercent() {
        return profileStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a string null placeholder percent check.
     * @param profileStringNullPlaceholderPercent String null placeholder percent check.
     */
    public void setProfileStringNullPlaceholderPercent(ColumnBlanksNullPlaceholderTextPercentCheckSpec profileStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.profileStringNullPlaceholderPercent, profileStringNullPlaceholderPercent));
        this.profileStringNullPlaceholderPercent = profileStringNullPlaceholderPercent;
        propagateHierarchyIdToField(profileStringNullPlaceholderPercent, "profile_string_null_placeholder_percent");
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
    public ColumnBlanksProfilingChecksSpec deepClone() {
        return (ColumnBlanksProfilingChecksSpec)super.deepClone();
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
