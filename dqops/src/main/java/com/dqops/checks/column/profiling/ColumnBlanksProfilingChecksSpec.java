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
 * Container of built-in preconfigured data quality checks on a column level that are checking for blank values in text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnBlanksProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnBlanksProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_empty_text_found", o -> o.profileEmptyTextFound);
            put("profile_whitespace_text_found", o -> o.profileWhitespaceTextFound);
            put("profile_null_placeholder_text_found", o -> o.profileNullPlaceholderTextFound);
            put("profile_empty_text_percent", o -> o.profileEmptyTextPercent);
            put("profile_whitespace_text_percent", o -> o.profileWhitespaceTextPercent);
            put("profile_null_placeholder_text_percent", o -> o.profileNullPlaceholderTextPercent);
        }
    };

    @JsonPropertyDescription("Verifies that empty strings in a column does not exceed the maximum accepted count.")
    private ColumnBlanksEmptyTextFoundCheckSpec profileEmptyTextFound;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.")
    private ColumnBlanksWhitespaceTextFoundCheckSpec profileWhitespaceTextFound;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.")
    private ColumnBlanksNullPlaceholderTextFoundCheckSpec profileNullPlaceholderTextFound;

    @JsonPropertyDescription("Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.")
    private ColumnBlanksEmptyTextPercentCheckSpec profileEmptyTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.")
    private ColumnBlanksWhitespaceTextPercentCheckSpec profileWhitespaceTextPercent;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.")
    private ColumnBlanksNullPlaceholderTextPercentCheckSpec profileNullPlaceholderTextPercent;


    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnBlanksEmptyTextFoundCheckSpec getProfileEmptyTextFound() {
        return profileEmptyTextFound;
    }

    /**
     * Sets a new definition of a string empty count check.
     * @param profileEmptyTextFound String empty count check.
     */
    public void setProfileEmptyTextFound(ColumnBlanksEmptyTextFoundCheckSpec profileEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.profileEmptyTextFound, profileEmptyTextFound));
        this.profileEmptyTextFound = profileEmptyTextFound;
        propagateHierarchyIdToField(profileEmptyTextFound, "profile_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnBlanksWhitespaceTextFoundCheckSpec getProfileWhitespaceTextFound() {
        return profileWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a string whitespace count check.
     * @param profileWhitespaceTextFound String whitespace count check.
     */
    public void setProfileWhitespaceTextFound(ColumnBlanksWhitespaceTextFoundCheckSpec profileWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.profileWhitespaceTextFound, profileWhitespaceTextFound));
        this.profileWhitespaceTextFound = profileWhitespaceTextFound;
        propagateHierarchyIdToField(profileWhitespaceTextFound, "profile_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextFoundCheckSpec getProfileNullPlaceholderTextFound() {
        return profileNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a string null placeholder count check.
     * @param profileNullPlaceholderTextFound String null placeholder count check.
     */
    public void setProfileNullPlaceholderTextFound(ColumnBlanksNullPlaceholderTextFoundCheckSpec profileNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.profileNullPlaceholderTextFound, profileNullPlaceholderTextFound));
        this.profileNullPlaceholderTextFound = profileNullPlaceholderTextFound;
        propagateHierarchyIdToField(profileNullPlaceholderTextFound, "profile_null_placeholder_text_found");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnBlanksEmptyTextPercentCheckSpec getProfileEmptyTextPercent() {
        return profileEmptyTextPercent;
    }

    /**
     * Sets a new definition of a string empty percent check.
     * @param profileEmptyTextPercent String empty percent check.
     */
    public void setProfileEmptyTextPercent(ColumnBlanksEmptyTextPercentCheckSpec profileEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.profileEmptyTextPercent, profileEmptyTextPercent));
        this.profileEmptyTextPercent = profileEmptyTextPercent;
        propagateHierarchyIdToField(profileEmptyTextPercent, "profile_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnBlanksWhitespaceTextPercentCheckSpec getProfileWhitespaceTextPercent() {
        return profileWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a string whitespace percent check.
     * @param profileWhitespaceTextPercent String whitespace percent check.
     */
    public void setProfileWhitespaceTextPercent(ColumnBlanksWhitespaceTextPercentCheckSpec profileWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.profileWhitespaceTextPercent, profileWhitespaceTextPercent));
        this.profileWhitespaceTextPercent = profileWhitespaceTextPercent;
        propagateHierarchyIdToField(profileWhitespaceTextPercent, "profile_whitespace_text_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnBlanksNullPlaceholderTextPercentCheckSpec getProfileNullPlaceholderTextPercent() {
        return profileNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a string null placeholder percent check.
     * @param profileNullPlaceholderTextPercent String null placeholder percent check.
     */
    public void setProfileNullPlaceholderTextPercent(ColumnBlanksNullPlaceholderTextPercentCheckSpec profileNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.profileNullPlaceholderTextPercent, profileNullPlaceholderTextPercent));
        this.profileNullPlaceholderTextPercent = profileNullPlaceholderTextPercent;
        propagateHierarchyIdToField(profileNullPlaceholderTextPercent, "profile_null_placeholder_text_percent");
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
