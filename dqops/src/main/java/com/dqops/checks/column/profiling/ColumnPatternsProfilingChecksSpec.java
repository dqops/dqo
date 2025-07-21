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
import com.dqops.checks.column.checkspecs.patterns.*;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for values matching patterns (regular expressions) in text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPatternsProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPatternsProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_text_not_matching_regex_found", o -> o.profileTextNotMatchingRegexFound);
            put("profile_texts_not_matching_regex_percent", o -> o.profileTextsNotMatchingRegexPercent);
            put("profile_invalid_email_format_found", o -> o.profileInvalidEmailFormatFound);
            put("profile_invalid_email_format_percent", o -> o.profileInvalidEmailFormatPercent);
            put("profile_text_not_matching_date_pattern_found", o -> o.profileTextNotMatchingDatePatternFound);
            put("profile_text_not_matching_date_pattern_percent", o -> o.profileTextNotMatchingDatePatternPercent);
            put("profile_text_not_matching_name_pattern_percent", o -> o.profileTextNotMatchingNamePatternPercent);

            put("profile_invalid_uuid_format_found", o -> o.profileInvalidUuidFormatFound);
            put("profile_invalid_uuid_format_percent", o -> o.profileInvalidUuidFormatPercent);
            put("profile_invalid_ip4_address_format_found", o -> o.profileInvalidIp4AddressFormatFound);
            put("profile_invalid_ip6_address_format_found", o -> o.profileInvalidIp6AddressFormatFound);

            put("profile_invalid_usa_phone_format_found", o -> o.profileInvalidUsaPhoneFormatFound);
            put("profile_invalid_usa_zipcode_format_found", o -> o.profileInvalidUsaZipcodeFormatFound);
            put("profile_invalid_usa_phone_format_percent", o -> o.profileInvalidUsaPhoneFormatPercent);
            put("profile_invalid_usa_zipcode_format_percent", o -> o.profileInvalidUsaZipcodeFormatPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingRegexFoundCheckSpec profileTextNotMatchingRegexFound;

    @JsonPropertyDescription("Verifies that the percentage of strings not matching the custom regular expression pattern does not exceed the maximum accepted percentage.")
    private ColumnTextsNotMatchingRegexPercentCheckSpec profileTextsNotMatchingRegexPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidEmailFormatFoundCheckSpec profileInvalidEmailFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid emails in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidEmailFormatPercentCheckSpec profileInvalidEmailFormatPercent;

    @JsonPropertyDescription("Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingDatePatternFoundCheckSpec profileTextNotMatchingDatePatternFound;

    @JsonPropertyDescription("Verifies that the percentage of texts not matching the date format regular expression in a column does not exceed the maximum accepted percentage.")
    private ColumnTextNotMatchingDatePatternPercentCheckSpec profileTextNotMatchingDatePatternPercent;

    @JsonPropertyDescription("Verifies that the percentage of texts not matching the name regular expression does not exceed the maximum accepted percentage.")
    private ColumnTextNotMatchingNamePatternPercentCheckSpec profileTextNotMatchingNamePatternPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUuidFormatFoundCheckSpec profileInvalidUuidFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid UUID in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUuidFormatPercentCheckSpec profileInvalidUuidFormatPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp4AddressFormatFoundCheckSpec profileInvalidIp4AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp6AddressFormatFoundCheckSpec profileInvalidIp6AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid USA phone numbers in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaPhoneFoundCheckSpec profileInvalidUsaPhoneFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid zip codes in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaZipcodeFoundCheckSpec profileInvalidUsaZipcodeFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid USA phones number in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUsaPhonePercentCheckSpec profileInvalidUsaPhoneFormatPercent;

    @JsonPropertyDescription("Verifies that the percentage of invalid USA phones number in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUsaZipcodePercentCheckSpec profileInvalidUsaZipcodeFormatPercent;

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnTextNotMatchingRegexFoundCheckSpec getProfileTextNotMatchingRegexFound() {
        return profileTextNotMatchingRegexFound;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param profileTextNotMatchingRegexFound Maximum not match regex count check.
     */
    public void setProfileTextNotMatchingRegexFound(ColumnTextNotMatchingRegexFoundCheckSpec profileTextNotMatchingRegexFound) {
        this.setDirtyIf(!Objects.equals(this.profileTextNotMatchingRegexFound, profileTextNotMatchingRegexFound));
        this.profileTextNotMatchingRegexFound = profileTextNotMatchingRegexFound;
        propagateHierarchyIdToField(profileTextNotMatchingRegexFound, "profile_text_not_matching_regex_found");
    }

    /**
     * Returns a maximum not match regex percent check.
     * @return Maximum not match regex percent check.
     */
    public ColumnTextsNotMatchingRegexPercentCheckSpec getProfileTextsNotMatchingRegexPercent() {
        return profileTextsNotMatchingRegexPercent;
    }

    /**
     * Sets a new definition of a maximum not match regex percent check.
     * @param profileTextsNotMatchingRegexPercent Maximum not match regex percent check.
     */
    public void setProfileTextsNotMatchingRegexPercent(ColumnTextsNotMatchingRegexPercentCheckSpec profileTextsNotMatchingRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextsNotMatchingRegexPercent, profileTextsNotMatchingRegexPercent));
        this.profileTextsNotMatchingRegexPercent = profileTextsNotMatchingRegexPercent;
        propagateHierarchyIdToField(profileTextsNotMatchingRegexPercent, "profile_texts_not_matching_regex_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnInvalidEmailFormatFoundCheckSpec getProfileInvalidEmailFormatFound() {
        return profileInvalidEmailFormatFound;
    }

    /**
     * Sets a new definition of an invalid email count check.
     * @param profileInvalidEmailFormatFound Invalid email count check.
     */
    public void setProfileInvalidEmailFormatFound(ColumnInvalidEmailFormatFoundCheckSpec profileInvalidEmailFormatFound) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidEmailFormatFound, profileInvalidEmailFormatFound));
        this.profileInvalidEmailFormatFound = profileInvalidEmailFormatFound;
        propagateHierarchyIdToField(profileInvalidEmailFormatFound, "profile_invalid_email_format_found");
    }

    /**
     * Returns a maximum invalid email percent check.
     * @return Maximum invalid email percent check.
     */
    public ColumnInvalidEmailFormatPercentCheckSpec getProfileInvalidEmailFormatPercent() {
        return profileInvalidEmailFormatPercent;
    }

    /**
     * Sets a new definition of an invalid email percent check.
     * @param profileInvalidEmailFormatPercent Invalid email percent check.
     */
    public void setProfileInvalidEmailFormatPercent(ColumnInvalidEmailFormatPercentCheckSpec profileInvalidEmailFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidEmailFormatPercent, profileInvalidEmailFormatPercent));
        this.profileInvalidEmailFormatPercent = profileInvalidEmailFormatPercent;
        propagateHierarchyIdToField(profileInvalidEmailFormatPercent, "profile_invalid_email_format_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnTextNotMatchingDatePatternFoundCheckSpec getProfileTextNotMatchingDatePatternFound() {
        return profileTextNotMatchingDatePatternFound;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param profileTextNotMatchingDatePatternFound Maximum not match date regex count check.
     */
    public void setProfileTextNotMatchingDatePatternFound(ColumnTextNotMatchingDatePatternFoundCheckSpec profileTextNotMatchingDatePatternFound) {
        this.setDirtyIf(!Objects.equals(this.profileTextNotMatchingDatePatternFound, profileTextNotMatchingDatePatternFound));
        this.profileTextNotMatchingDatePatternFound = profileTextNotMatchingDatePatternFound;
        propagateHierarchyIdToField(profileTextNotMatchingDatePatternFound, "profile_text_not_matching_date_pattern_found");
    }

    /**
     * Returns a maximum not match date regex percent check.
     * @return Maximum not match date regex percent check.
     */
    public ColumnTextNotMatchingDatePatternPercentCheckSpec getProfileTextNotMatchingDatePatternPercent() {
        return profileTextNotMatchingDatePatternPercent;
    }

    /**
     * Sets a new definition of a maximum not match date regex percent check.
     * @param profileTextNotMatchingDatePatternPercent Maximum not match date regex percent check.
     */
    public void setProfileTextNotMatchingDatePatternPercent(ColumnTextNotMatchingDatePatternPercentCheckSpec profileTextNotMatchingDatePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextNotMatchingDatePatternPercent, profileTextNotMatchingDatePatternPercent));
        this.profileTextNotMatchingDatePatternPercent = profileTextNotMatchingDatePatternPercent;
        propagateHierarchyIdToField(profileTextNotMatchingDatePatternPercent, "profile_text_not_matching_date_pattern_percent");
    }

    /**
     * Returns a maximum not match name regex percent check.
     * @return Maximum not match name regex percent check.
     */
    public ColumnTextNotMatchingNamePatternPercentCheckSpec getProfileTextNotMatchingNamePatternPercent() {
        return profileTextNotMatchingNamePatternPercent;
    }

    /**
     * Sets a new definition of a maximum not match name regex percent check.
     * @param profileTextNotMatchingNamePatternPercent Maximum not match name regex percent check.
     */
    public void setProfileTextNotMatchingNamePatternPercent(ColumnTextNotMatchingNamePatternPercentCheckSpec profileTextNotMatchingNamePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextNotMatchingNamePatternPercent, profileTextNotMatchingNamePatternPercent));
        this.profileTextNotMatchingNamePatternPercent = profileTextNotMatchingNamePatternPercent;
        propagateHierarchyIdToField(profileTextNotMatchingNamePatternPercent, "profile_text_not_matching_name_pattern_percent");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnInvalidUuidFormatFoundCheckSpec getProfileInvalidUuidFormatFound() {
        return profileInvalidUuidFormatFound;
    }

    /**
     * Sets a new definition of an invalid UUID count check.
     * @param profileInvalidUuidFormatFound Invalid UUID count check.
     */
    public void setProfileInvalidUuidFormatFound(ColumnInvalidUuidFormatFoundCheckSpec profileInvalidUuidFormatFound) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidUuidFormatFound, profileInvalidUuidFormatFound));
        this.profileInvalidUuidFormatFound = profileInvalidUuidFormatFound;
        propagateHierarchyIdToField(profileInvalidUuidFormatFound, "profile_invalid_uuid_format_found");
    }

    /**
     * Returns an invalid UUID percent check.
     * @return Invalid UUID percent check.
     */
    public ColumnInvalidUuidFormatPercentCheckSpec getProfileInvalidUuidFormatPercent() {
        return profileInvalidUuidFormatPercent;
    }

    /**
     * Sets a new definition of an invalid UUID percent check.
     * @param profileInvalidUuidFormatPercent Invalid UUID percent check.
     */
    public void setProfileInvalidUuidFormatPercent(ColumnInvalidUuidFormatPercentCheckSpec profileInvalidUuidFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidUuidFormatPercent, profileInvalidUuidFormatPercent));
        this.profileInvalidUuidFormatPercent = profileInvalidUuidFormatPercent;
        propagateHierarchyIdToField(profileInvalidUuidFormatPercent, "profile_invalid_uuid_format_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnInvalidIp4AddressFormatFoundCheckSpec getProfileInvalidIp4AddressFormatFound() {
        return profileInvalidIp4AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP4 address count check.
     * @param profileInvalidIp4AddressFormatFound Invalid IP4 address count check.
     */
    public void setProfileInvalidIp4AddressFormatFound(ColumnInvalidIp4AddressFormatFoundCheckSpec profileInvalidIp4AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidIp4AddressFormatFound, profileInvalidIp4AddressFormatFound));
        this.profileInvalidIp4AddressFormatFound = profileInvalidIp4AddressFormatFound;
        propagateHierarchyIdToField(profileInvalidIp4AddressFormatFound, "profile_invalid_ip4_address_format_found");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnInvalidIp6AddressFormatFoundCheckSpec getProfileInvalidIp6AddressFormatFound() {
        return profileInvalidIp6AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP6 address count check.
     * @param profileInvalidIp6AddressFormatFound Invalid IP6 address count check.
     */
    public void setProfileInvalidIp6AddressFormatFound(ColumnInvalidIp6AddressFormatFoundCheckSpec profileInvalidIp6AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidIp6AddressFormatFound, profileInvalidIp6AddressFormatFound));
        this.profileInvalidIp6AddressFormatFound = profileInvalidIp6AddressFormatFound;
        propagateHierarchyIdToField(profileInvalidIp6AddressFormatFound, "profile_invalid_ip6_address_format_found");
    }

    /**
     * Returns a maximum invalid USA phone numbers count check.
     * @return Maximum invalid USA phone numbers count check.
     */
    public ColumnInvalidUsaPhoneFoundCheckSpec getProfileInvalidUsaPhoneFormatFound() {
        return profileInvalidUsaPhoneFormatFound;
    }

    /**
     * Sets a new definition of an invalid USA phone numbers count check.
     * @param profileInvalidUsaPhoneFormatFound Invalid USA phone numbers count check.
     */
    public void setProfileInvalidUsaPhoneFormatFound(ColumnInvalidUsaPhoneFoundCheckSpec profileInvalidUsaPhoneFormatFound) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidUsaPhoneFormatFound, profileInvalidUsaPhoneFormatFound));
        this.profileInvalidUsaPhoneFormatFound = profileInvalidUsaPhoneFormatFound;
        propagateHierarchyIdToField(profileInvalidUsaPhoneFormatFound, "profile_invalid_usa_phone_format_found");
    }

    /**
     * Returns a maximum invalid USA zip codes count check.
     * @return Maximum invalid USA zip codes count check.
     */
    public ColumnInvalidUsaZipcodeFoundCheckSpec getProfileInvalidUsaZipcodeFormatFound() {
        return profileInvalidUsaZipcodeFormatFound;
    }

    /**
     * Sets a new definition of an invalid USA zip codes count check.
     * @param profileInvalidUsaZipcodeFormatFound Invalid USA zip codes count check.
     */
    public void setProfileInvalidUsaZipcodeFormatFound(ColumnInvalidUsaZipcodeFoundCheckSpec profileInvalidUsaZipcodeFormatFound) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidUsaZipcodeFormatFound, profileInvalidUsaZipcodeFormatFound));
        this.profileInvalidUsaZipcodeFormatFound = profileInvalidUsaZipcodeFormatFound;
        propagateHierarchyIdToField(profileInvalidUsaZipcodeFormatFound, "profile_invalid_usa_zipcode_format_found");
    }

    /**
     * Returns an invalid USA phones number percent check.
     * @return Invalid USA phones number percent check.
     */
    public ColumnInvalidUsaPhonePercentCheckSpec getProfileInvalidUsaPhoneFormatPercent() {
        return profileInvalidUsaPhoneFormatPercent;
    }

    /**
     * Sets a new definition of an invalid USA phones number percent check.
     * @param profileInvalidUsaPhoneFormatPercent Invalid USA phones number percent check.
     */
    public void setProfileInvalidUsaPhoneFormatPercent(ColumnInvalidUsaPhonePercentCheckSpec profileInvalidUsaPhoneFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidUsaPhoneFormatPercent, profileInvalidUsaPhoneFormatPercent));
        this.profileInvalidUsaPhoneFormatPercent = profileInvalidUsaPhoneFormatPercent;
        propagateHierarchyIdToField(profileInvalidUsaPhoneFormatPercent, "profile_invalid_usa_phone_format_percent");
    }

    /**
     * Returns an invalid USA zip code percent check.
     * @return Invalid USA zip code percent check.
     */
    public ColumnInvalidUsaZipcodePercentCheckSpec getProfileInvalidUsaZipcodeFormatPercent() {
        return profileInvalidUsaZipcodeFormatPercent;
    }

    /**
     * Sets a new definition of an invalid USA zip code percent check.
     * @param profileInvalidUsaZipcodeFormatPercent Invalid USA zip code percent check.
     */
    public void setProfileInvalidUsaZipcodeFormatPercent(ColumnInvalidUsaZipcodePercentCheckSpec profileInvalidUsaZipcodeFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileInvalidUsaPhoneFormatPercent, profileInvalidUsaZipcodeFormatPercent));
        this.profileInvalidUsaZipcodeFormatPercent = profileInvalidUsaZipcodeFormatPercent;
        propagateHierarchyIdToField(profileInvalidUsaZipcodeFormatPercent, "profile_invalid_usa_zipcode_format_percent");
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
    public ColumnPatternsProfilingChecksSpec deepClone() {
        return (ColumnPatternsProfilingChecksSpec)super.deepClone();
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
