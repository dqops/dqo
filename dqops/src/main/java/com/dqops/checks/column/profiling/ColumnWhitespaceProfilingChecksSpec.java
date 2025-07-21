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
import com.dqops.checks.column.checkspecs.whitespace.*;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for whitespace and blank values in text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnWhitespaceProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnWhitespaceProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_empty_text_found", o -> o.profileEmptyTextFound);
            put("profile_whitespace_text_found", o -> o.profileWhitespaceTextFound);
            put("profile_null_placeholder_text_found", o -> o.profileNullPlaceholderTextFound);
            put("profile_empty_text_percent", o -> o.profileEmptyTextPercent);
            put("profile_whitespace_text_percent", o -> o.profileWhitespaceTextPercent);
            put("profile_null_placeholder_text_percent", o -> o.profileNullPlaceholderTextPercent);

            put("profile_text_surrounded_by_whitespace_found", o -> o.profileTextSurroundedByWhitespaceFound);
            put("profile_text_surrounded_by_whitespace_percent", o -> o.profileTextSurroundedByWhitespacePercent);
        }
    };

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value.")
    private ColumnWhitespaceEmptyTextFoundCheckSpec profileEmptyTextFound;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value.")
    private ColumnWhitespaceWhitespaceTextFoundCheckSpec profileWhitespaceTextFound;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value.")
    private ColumnWhitespaceNullPlaceholderTextFoundCheckSpec profileNullPlaceholderTextFound;

    @JsonPropertyDescription("Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage.")
    private ColumnWhitespaceEmptyTextPercentCheckSpec profileEmptyTextPercent;

    @JsonPropertyDescription("Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value.")
    private ColumnWhitespaceWhitespaceTextPercentCheckSpec profileWhitespaceTextPercent;

    @JsonPropertyDescription("Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value.")
    private ColumnWhitespaceNullPlaceholderTextPercentCheckSpec profileNullPlaceholderTextPercent;

    @JsonPropertyDescription("Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value.")
    private ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec profileTextSurroundedByWhitespaceFound;

    @JsonPropertyDescription("This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value.")
    private ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec profileTextSurroundedByWhitespacePercent;


    /**
     * Returns a maximum string empty percent check.
     * @return Maximum string empty percent check.
     */
    public ColumnWhitespaceEmptyTextFoundCheckSpec getProfileEmptyTextFound() {
        return profileEmptyTextFound;
    }

    /**
     * Sets a new definition of a string empty count check.
     * @param profileEmptyTextFound String empty count check.
     */
    public void setProfileEmptyTextFound(ColumnWhitespaceEmptyTextFoundCheckSpec profileEmptyTextFound) {
        this.setDirtyIf(!Objects.equals(this.profileEmptyTextFound, profileEmptyTextFound));
        this.profileEmptyTextFound = profileEmptyTextFound;
        propagateHierarchyIdToField(profileEmptyTextFound, "profile_empty_text_found");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnWhitespaceWhitespaceTextFoundCheckSpec getProfileWhitespaceTextFound() {
        return profileWhitespaceTextFound;
    }

    /**
     * Sets a new definition of a string whitespace count check.
     * @param profileWhitespaceTextFound String whitespace count check.
     */
    public void setProfileWhitespaceTextFound(ColumnWhitespaceWhitespaceTextFoundCheckSpec profileWhitespaceTextFound) {
        this.setDirtyIf(!Objects.equals(this.profileWhitespaceTextFound, profileWhitespaceTextFound));
        this.profileWhitespaceTextFound = profileWhitespaceTextFound;
        propagateHierarchyIdToField(profileWhitespaceTextFound, "profile_whitespace_text_found");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnWhitespaceNullPlaceholderTextFoundCheckSpec getProfileNullPlaceholderTextFound() {
        return profileNullPlaceholderTextFound;
    }

    /**
     * Sets a new definition of a string null placeholder count check.
     * @param profileNullPlaceholderTextFound String null placeholder count check.
     */
    public void setProfileNullPlaceholderTextFound(ColumnWhitespaceNullPlaceholderTextFoundCheckSpec profileNullPlaceholderTextFound) {
        this.setDirtyIf(!Objects.equals(this.profileNullPlaceholderTextFound, profileNullPlaceholderTextFound));
        this.profileNullPlaceholderTextFound = profileNullPlaceholderTextFound;
        propagateHierarchyIdToField(profileNullPlaceholderTextFound, "profile_null_placeholder_text_found");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnWhitespaceEmptyTextPercentCheckSpec getProfileEmptyTextPercent() {
        return profileEmptyTextPercent;
    }

    /**
     * Sets a new definition of a string empty percent check.
     * @param profileEmptyTextPercent String empty percent check.
     */
    public void setProfileEmptyTextPercent(ColumnWhitespaceEmptyTextPercentCheckSpec profileEmptyTextPercent) {
        this.setDirtyIf(!Objects.equals(this.profileEmptyTextPercent, profileEmptyTextPercent));
        this.profileEmptyTextPercent = profileEmptyTextPercent;
        propagateHierarchyIdToField(profileEmptyTextPercent, "profile_empty_text_percent");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnWhitespaceWhitespaceTextPercentCheckSpec getProfileWhitespaceTextPercent() {
        return profileWhitespaceTextPercent;
    }

    /**
     * Sets a new definition of a string whitespace percent check.
     * @param profileWhitespaceTextPercent String whitespace percent check.
     */
    public void setProfileWhitespaceTextPercent(ColumnWhitespaceWhitespaceTextPercentCheckSpec profileWhitespaceTextPercent) {
        this.setDirtyIf(!Objects.equals(this.profileWhitespaceTextPercent, profileWhitespaceTextPercent));
        this.profileWhitespaceTextPercent = profileWhitespaceTextPercent;
        propagateHierarchyIdToField(profileWhitespaceTextPercent, "profile_whitespace_text_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnWhitespaceNullPlaceholderTextPercentCheckSpec getProfileNullPlaceholderTextPercent() {
        return profileNullPlaceholderTextPercent;
    }

    /**
     * Sets a new definition of a string null placeholder percent check.
     * @param profileNullPlaceholderTextPercent String null placeholder percent check.
     */
    public void setProfileNullPlaceholderTextPercent(ColumnWhitespaceNullPlaceholderTextPercentCheckSpec profileNullPlaceholderTextPercent) {
        this.setDirtyIf(!Objects.equals(this.profileNullPlaceholderTextPercent, profileNullPlaceholderTextPercent));
        this.profileNullPlaceholderTextPercent = profileNullPlaceholderTextPercent;
        propagateHierarchyIdToField(profileNullPlaceholderTextPercent, "profile_null_placeholder_text_percent");
    }

    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec getProfileTextSurroundedByWhitespaceFound() {
        return profileTextSurroundedByWhitespaceFound;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace count check.
     * @param profileTextSurroundedByWhitespaceFound String surrounded by whitespace count check.
     */
    public void setProfileTextSurroundedByWhitespaceFound(ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec profileTextSurroundedByWhitespaceFound) {
        this.setDirtyIf(!Objects.equals(this.profileTextSurroundedByWhitespaceFound, profileTextSurroundedByWhitespaceFound));
        this.profileTextSurroundedByWhitespaceFound = profileTextSurroundedByWhitespaceFound;
        propagateHierarchyIdToField(profileTextSurroundedByWhitespaceFound, "profile_text_surrounded_by_whitespace_found");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec getProfileTextSurroundedByWhitespacePercent() {
        return profileTextSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a string surrounded by whitespace percent check.
     * @param profileTextSurroundedByWhitespacePercent String surrounded by whitespace percent check.
     */
    public void setProfileTextSurroundedByWhitespacePercent(ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec profileTextSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextSurroundedByWhitespacePercent, profileTextSurroundedByWhitespacePercent));
        this.profileTextSurroundedByWhitespacePercent = profileTextSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(profileTextSurroundedByWhitespacePercent, "profile_text_surrounded_by_whitespace_percent");
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
    public ColumnWhitespaceProfilingChecksSpec deepClone() {
        return (ColumnWhitespaceProfilingChecksSpec)super.deepClone();
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
