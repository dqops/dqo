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
import com.dqops.checks.column.checkspecs.acceptedvalues.*;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for accepted values.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnAcceptedValuesProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnAcceptedValuesProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_text_found_in_set_percent", o -> o.profileTextFoundInSetPercent);
            put("profile_number_found_in_set_percent", o -> o.profileNumberFoundInSetPercent);
            put("profile_expected_text_values_in_use_count", o -> o.profileExpectedTextValuesInUseCount);
            put("profile_expected_texts_in_top_values_count", o -> o.profileExpectedTextsInTopValuesCount);
            put("profile_expected_numbers_in_use_count", o -> o.profileExpectedNumbersInUseCount);

            put("profile_text_valid_country_code_percent", o -> o.profileTextValidCountryCodePercent);
            put("profile_text_valid_currency_code_percent", o -> o.profileTextValidCurrencyCodePercent);
        }
    };

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.")
    private ColumnTextFoundInSetPercentCheckSpec profileTextFoundInSetPercent;

    @JsonPropertyDescription("The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.")
    private ColumnNumberFoundInSetPercentCheckSpec profileNumberFoundInSetPercent;

    @JsonPropertyDescription("Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).")
    private ColumnExpectedTextValuesInUseCountCheckSpec profileExpectedTextValuesInUseCount;

    @JsonPropertyDescription("Verifies that the top X most popular column values contain all values from a list of expected values.")
    private ColumnExpectedTextsInTopValuesCountCheckSpec profileExpectedTextsInTopValuesCount;

    @JsonPropertyDescription("Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).")
    private ColumnExpectedNumbersInUseCountCheckSpec profileExpectedNumbersInUseCount;

    @JsonPropertyDescription("Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage")
    private ColumnTextValidCountryCodePercentCheckSpec profileTextValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage")
    private ColumnTextValidCurrencyCodePercentCheckSpec profileTextValidCurrencyCodePercent;

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent check.
     */
    public ColumnTextFoundInSetPercentCheckSpec getProfileTextFoundInSetPercent() {
        return profileTextFoundInSetPercent;
    }

    /**
     * Sets a new definition of a strings in set percent check.
     * @param profileTextFoundInSetPercent Strings in set percent check.
     */
    public void setProfileTextFoundInSetPercent(ColumnTextFoundInSetPercentCheckSpec profileTextFoundInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextFoundInSetPercent, profileTextFoundInSetPercent));
        this.profileTextFoundInSetPercent = profileTextFoundInSetPercent;
        propagateHierarchyIdToField(profileTextFoundInSetPercent, "profile_text_found_in_set_percent");
    }

    /**
     * Returns a numbers valid percent check specification.
     * @return Numbers valid percent check specification.
     */
    public ColumnNumberFoundInSetPercentCheckSpec getProfileNumberFoundInSetPercent() {
        return profileNumberFoundInSetPercent;
    }

    /**
     * Sets a new specification of a numbers valid percent check specification.
     * @param profileNumberFoundInSetPercent Numbers valid percent check specification.
     */
    public void setProfileNumberFoundInSetPercent(ColumnNumberFoundInSetPercentCheckSpec profileNumberFoundInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.profileNumberFoundInSetPercent, profileNumberFoundInSetPercent));
        this.profileNumberFoundInSetPercent = profileNumberFoundInSetPercent;
        propagateHierarchyIdToField(profileNumberFoundInSetPercent, "profile_number_found_in_set_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent check.
     */
    public ColumnExpectedTextValuesInUseCountCheckSpec getProfileExpectedTextValuesInUseCount() {
        return profileExpectedTextValuesInUseCount;
    }

    /**
     * Sets a new definition of a string in set count check.
     * @param profileExpectedTextValuesInUseCount String in set count check.
     */
    public void setProfileExpectedTextValuesInUseCount(ColumnExpectedTextValuesInUseCountCheckSpec profileExpectedTextValuesInUseCount) {
        this.setDirtyIf(!Objects.equals(this.profileExpectedTextValuesInUseCount, profileExpectedTextValuesInUseCount));
        this.profileExpectedTextValuesInUseCount = profileExpectedTextValuesInUseCount;
        propagateHierarchyIdToField(profileExpectedTextValuesInUseCount, "profile_expected_text_values_in_use_count");
    }

    /**
     * Returns a count of expected values in most popular values set count check.
     * @return Most popular values count check.
     */
    public ColumnExpectedTextsInTopValuesCountCheckSpec getProfileExpectedTextsInTopValuesCount() {
        return profileExpectedTextsInTopValuesCount;
    }

    /**
     * Sets a new definition of a most popular values count check.
     * @param profileExpectedTextsInTopValuesCount Most popular values count check.
     */
    public void setProfileExpectedTextsInTopValuesCount(ColumnExpectedTextsInTopValuesCountCheckSpec profileExpectedTextsInTopValuesCount) {
        this.setDirtyIf(!Objects.equals(this.profileExpectedTextsInTopValuesCount, profileExpectedTextsInTopValuesCount));
        this.profileExpectedTextsInTopValuesCount = profileExpectedTextsInTopValuesCount;
        propagateHierarchyIdToField(profileExpectedTextsInTopValuesCount, "profile_expected_texts_in_top_values_count");
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
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnTextValidCountryCodePercentCheckSpec getProfileTextValidCountryCodePercent() {
        return profileTextValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a string valid country code percent check.
     * @param profileTextValidCountryCodePercent String valid country code percent check.
     */
    public void setProfileTextValidCountryCodePercent(ColumnTextValidCountryCodePercentCheckSpec profileTextValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextValidCountryCodePercent, profileTextValidCountryCodePercent));
        this.profileTextValidCountryCodePercent = profileTextValidCountryCodePercent;
        propagateHierarchyIdToField(profileTextValidCountryCodePercent, "profile_text_valid_country_code_percent");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnTextValidCurrencyCodePercentCheckSpec getProfileTextValidCurrencyCodePercent() {
        return profileTextValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a string valid currency code percent check.
     * @param profileTextValidCurrencyCodePercent String valid currency code percent check.
     */
    public void setProfileTextValidCurrencyCodePercent(ColumnTextValidCurrencyCodePercentCheckSpec profileTextValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextValidCurrencyCodePercent, profileTextValidCurrencyCodePercent));
        this.profileTextValidCurrencyCodePercent = profileTextValidCurrencyCodePercent;
        propagateHierarchyIdToField(profileTextValidCurrencyCodePercent, "profile_text_valid_currency_code_percent");
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

    /**
     * Returns an array of supported data type categories. DQOps uses this list when activating default data quality checks.
     *
     * @return Array of supported data type categories.
     */
    @Override
    @JsonIgnore
    public DataTypeCategory[] getSupportedDataTypeCategories() {
        return DataTypeCategory.COMPARABLE;
    }
}
