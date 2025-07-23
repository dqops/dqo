/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.checks.column.monitoring.patterns;

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
 * Container of built-in preconfigured daily monitoring checks on a column level that are checking for values matching patterns (regular expressions) in text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPatternsDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPatternsDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_text_not_matching_regex_found", o -> o.dailyTextNotMatchingRegexFound);
            put("daily_texts_not_matching_regex_percent", o -> o.dailyTextsNotMatchingRegexPercent);
            put("daily_invalid_email_format_found", o -> o.dailyInvalidEmailFormatFound);
            put("daily_invalid_email_format_percent", o -> o.dailyInvalidEmailFormatPercent);
            put("daily_text_not_matching_date_pattern_found", o -> o.dailyTextNotMatchingDatePatternFound);
            put("daily_text_not_matching_date_pattern_percent", o -> o.dailyTextNotMatchingDatePatternPercent);
            put("daily_text_not_matching_name_pattern_percent", o -> o.dailyTextNotMatchingNamePatternPercent);

            put("daily_invalid_uuid_format_found", o -> o.dailyInvalidUuidFormatFound);
            put("daily_invalid_uuid_format_percent", o -> o.dailyInvalidUuidFormatPercent);
            put("daily_invalid_ip4_address_format_found", o -> o.dailyInvalidIp4AddressFormatFound);
            put("daily_invalid_ip6_address_format_found", o -> o.dailyInvalidIp6AddressFormatFound);

            put("daily_invalid_usa_phone_format_found", o -> o.dailyInvalidUsaPhoneFormatFound);
            put("daily_invalid_usa_zipcode_format_found", o -> o.dailyInvalidUsaZipcodeFormatFound);
            put("daily_invalid_usa_phone_format_percent", o -> o.dailyInvalidUsaPhoneFormatPercent);
            put("daily_invalid_usa_zipcode_format_percent", o -> o.dailyInvalidUsaZipcodeFormatPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingRegexFoundCheckSpec dailyTextNotMatchingRegexFound;

    @JsonPropertyDescription("Verifies that the percentage of strings not matching the custom regular expression pattern does not exceed the maximum accepted percentage.")
    private ColumnTextsNotMatchingRegexPercentCheckSpec dailyTextsNotMatchingRegexPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidEmailFormatFoundCheckSpec dailyInvalidEmailFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid emails in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidEmailFormatPercentCheckSpec dailyInvalidEmailFormatPercent;

    @JsonPropertyDescription("Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingDatePatternFoundCheckSpec dailyTextNotMatchingDatePatternFound;

    @JsonPropertyDescription("Verifies that the percentage of texts not matching the date format regular expression in a column does not exceed the maximum accepted percentage.")
    private ColumnTextNotMatchingDatePatternPercentCheckSpec dailyTextNotMatchingDatePatternPercent;

    @JsonPropertyDescription("Verifies that the percentage of texts not matching the name regular expression does not exceed the maximum accepted percentage.")
    private ColumnTextNotMatchingNamePatternPercentCheckSpec dailyTextNotMatchingNamePatternPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUuidFormatFoundCheckSpec dailyInvalidUuidFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid UUID in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUuidFormatPercentCheckSpec dailyInvalidUuidFormatPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp4AddressFormatFoundCheckSpec dailyInvalidIp4AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp6AddressFormatFoundCheckSpec dailyInvalidIp6AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid USA phone numbers in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaPhoneFoundCheckSpec dailyInvalidUsaPhoneFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid zip codes in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaZipcodeFoundCheckSpec dailyInvalidUsaZipcodeFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid USA phones number in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUsaPhonePercentCheckSpec dailyInvalidUsaPhoneFormatPercent;

    @JsonPropertyDescription("Verifies that the percentage of invalid USA zip code in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUsaZipcodePercentCheckSpec dailyInvalidUsaZipcodeFormatPercent;

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnTextNotMatchingRegexFoundCheckSpec getDailyTextNotMatchingRegexFound() {
        return dailyTextNotMatchingRegexFound;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param dailyTextNotMatchingRegexFound Maximum not match regex count check.
     */
    public void setDailyTextNotMatchingRegexFound(ColumnTextNotMatchingRegexFoundCheckSpec dailyTextNotMatchingRegexFound) {
        this.setDirtyIf(!Objects.equals(this.dailyTextNotMatchingRegexFound, dailyTextNotMatchingRegexFound));
        this.dailyTextNotMatchingRegexFound = dailyTextNotMatchingRegexFound;
        propagateHierarchyIdToField(dailyTextNotMatchingRegexFound, "daily_text_not_matching_regex_found");
    }

    /**
     * Returns a maximum not match regex percent check.
     * @return Maximum not match regex percent check.
     */
    public ColumnTextsNotMatchingRegexPercentCheckSpec getDailyTextsNotMatchingRegexPercent() {
        return dailyTextsNotMatchingRegexPercent;
    }

    /**
     * Sets a new definition of a maximum not match regex percent check.
     * @param dailyTextsNotMatchingRegexPercent Maximum not match regex percent check.
     */
    public void setDailyTextsNotMatchingRegexPercent(ColumnTextsNotMatchingRegexPercentCheckSpec dailyTextsNotMatchingRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextsNotMatchingRegexPercent, dailyTextsNotMatchingRegexPercent));
        this.dailyTextsNotMatchingRegexPercent = dailyTextsNotMatchingRegexPercent;
        propagateHierarchyIdToField(dailyTextsNotMatchingRegexPercent, "daily_texts_not_matching_regex_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnInvalidEmailFormatFoundCheckSpec getDailyInvalidEmailFormatFound() {
        return dailyInvalidEmailFormatFound;
    }

    /**
     * Sets a new definition of an invalid email count check.
     * @param dailyInvalidEmailFormatFound Invalid email count check.
     */
    public void setDailyInvalidEmailFormatFound(ColumnInvalidEmailFormatFoundCheckSpec dailyInvalidEmailFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidEmailFormatFound, dailyInvalidEmailFormatFound));
        this.dailyInvalidEmailFormatFound = dailyInvalidEmailFormatFound;
        propagateHierarchyIdToField(dailyInvalidEmailFormatFound, "daily_invalid_email_format_found");
    }

    /**
     * Returns a maximum invalid email percent check.
     * @return Maximum invalid email percent check.
     */
    public ColumnInvalidEmailFormatPercentCheckSpec getDailyInvalidEmailFormatPercent() {
        return dailyInvalidEmailFormatPercent;
    }

    /**
     * Sets a new definition of an invalid email percent check.
     * @param dailyInvalidEmailFormatPercent Invalid email percent check.
     */
    public void setDailyInvalidEmailFormatPercent(ColumnInvalidEmailFormatPercentCheckSpec dailyInvalidEmailFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidEmailFormatPercent, dailyInvalidEmailFormatPercent));
        this.dailyInvalidEmailFormatPercent = dailyInvalidEmailFormatPercent;
        propagateHierarchyIdToField(dailyInvalidEmailFormatPercent, "daily_invalid_email_format_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnTextNotMatchingDatePatternFoundCheckSpec getDailyTextNotMatchingDatePatternFound() {
        return dailyTextNotMatchingDatePatternFound;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param dailyTextNotMatchingDatePatternFound Maximum not match date regex count check.
     */
    public void setDailyTextNotMatchingDatePatternFound(ColumnTextNotMatchingDatePatternFoundCheckSpec dailyTextNotMatchingDatePatternFound) {
        this.setDirtyIf(!Objects.equals(this.dailyTextNotMatchingDatePatternFound, dailyTextNotMatchingDatePatternFound));
        this.dailyTextNotMatchingDatePatternFound = dailyTextNotMatchingDatePatternFound;
        propagateHierarchyIdToField(dailyTextNotMatchingDatePatternFound, "daily_text_not_matching_date_pattern_found");
    }

    /**
     * Returns a maximum not match date regex percent check.
     * @return Maximum not match date regex percent check.
     */
    public ColumnTextNotMatchingDatePatternPercentCheckSpec getDailyTextNotMatchingDatePatternPercent() {
        return dailyTextNotMatchingDatePatternPercent;
    }

    /**
     * Sets a new definition of a maximum not match date regex percent check.
     * @param dailyTextNotMatchingDatePatternPercent Maximum not match date regex percent check.
     */
    public void setDailyTextNotMatchingDatePatternPercent(ColumnTextNotMatchingDatePatternPercentCheckSpec dailyTextNotMatchingDatePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextNotMatchingDatePatternPercent, dailyTextNotMatchingDatePatternPercent));
        this.dailyTextNotMatchingDatePatternPercent = dailyTextNotMatchingDatePatternPercent;
        propagateHierarchyIdToField(dailyTextNotMatchingDatePatternPercent, "daily_text_not_matching_date_pattern_percent");
    }

    /**
     * Returns a maximum not match name regex percent check.
     * @return Maximum not match name regex percent check.
     */
    public ColumnTextNotMatchingNamePatternPercentCheckSpec getDailyTextNotMatchingNamePatternPercent() {
        return dailyTextNotMatchingNamePatternPercent;
    }

    /**
     * Sets a new definition of a maximum not match name regex percent check.
     * @param dailyTextNotMatchingNamePatternPercent Maximum not match name regex percent check.
     */
    public void setDailyTextNotMatchingNamePatternPercent(ColumnTextNotMatchingNamePatternPercentCheckSpec dailyTextNotMatchingNamePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextNotMatchingNamePatternPercent, dailyTextNotMatchingNamePatternPercent));
        this.dailyTextNotMatchingNamePatternPercent = dailyTextNotMatchingNamePatternPercent;
        propagateHierarchyIdToField(dailyTextNotMatchingNamePatternPercent, "daily_text_not_matching_name_pattern_percent");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnInvalidUuidFormatFoundCheckSpec getDailyInvalidUuidFormatFound() {
        return dailyInvalidUuidFormatFound;
    }

    /**
     * Sets a new definition of an invalid UUID count check.
     * @param dailyInvalidUuidFormatFound Invalid UUID count check.
     */
    public void setDailyInvalidUuidFormatFound(ColumnInvalidUuidFormatFoundCheckSpec dailyInvalidUuidFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidUuidFormatFound, dailyInvalidUuidFormatFound));
        this.dailyInvalidUuidFormatFound = dailyInvalidUuidFormatFound;
        propagateHierarchyIdToField(dailyInvalidUuidFormatFound, "daily_invalid_uuid_format_found");
    }

    /**
     * Returns an invalid UUID percent check.
     * @return Invalid UUID percent check.
     */
    public ColumnInvalidUuidFormatPercentCheckSpec getDailyInvalidUuidFormatPercent() {
        return dailyInvalidUuidFormatPercent;
    }

    /**
     * Sets a new definition of an invalid UUID percent check.
     * @param dailyInvalidUuidFormatPercent Invalid UUID percent check.
     */
    public void setDailyInvalidUuidFormatPercent(ColumnInvalidUuidFormatPercentCheckSpec dailyInvalidUuidFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidUuidFormatPercent, dailyInvalidUuidFormatPercent));
        this.dailyInvalidUuidFormatPercent = dailyInvalidUuidFormatPercent;
        propagateHierarchyIdToField(dailyInvalidUuidFormatPercent, "daily_invalid_uuid_format_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnInvalidIp4AddressFormatFoundCheckSpec getDailyInvalidIp4AddressFormatFound() {
        return dailyInvalidIp4AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP4 address count check.
     * @param dailyInvalidIp4AddressFormatFound Invalid IP4 address count check.
     */
    public void setDailyInvalidIp4AddressFormatFound(ColumnInvalidIp4AddressFormatFoundCheckSpec dailyInvalidIp4AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidIp4AddressFormatFound, dailyInvalidIp4AddressFormatFound));
        this.dailyInvalidIp4AddressFormatFound = dailyInvalidIp4AddressFormatFound;
        propagateHierarchyIdToField(dailyInvalidIp4AddressFormatFound, "daily_invalid_ip4_address_format_found");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnInvalidIp6AddressFormatFoundCheckSpec getDailyInvalidIp6AddressFormatFound() {
        return dailyInvalidIp6AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP6 address count check.
     * @param dailyInvalidIp6AddressFormatFound Invalid IP6 address count check.
     */
    public void setDailyInvalidIp6AddressFormatFound(ColumnInvalidIp6AddressFormatFoundCheckSpec dailyInvalidIp6AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidIp6AddressFormatFound, dailyInvalidIp6AddressFormatFound));
        this.dailyInvalidIp6AddressFormatFound = dailyInvalidIp6AddressFormatFound;
        propagateHierarchyIdToField(dailyInvalidIp6AddressFormatFound, "daily_invalid_ip6_address_format_found");
    }

    /**
     * Returns a maximum invalid USA phone numbers count check.
     * @return Maximum invalid USA phone numbers count check.
     */
    public ColumnInvalidUsaPhoneFoundCheckSpec getDailyInvalidUsaPhoneFormatFound() {
        return dailyInvalidUsaPhoneFormatFound;
    }

    /**
     * Sets a new definition of an invalid USA phone numbers count check.
     * @param dailyInvalidUsaPhoneFormatFound Invalid USA phone numbers count check.
     */
    public void setDailyInvalidUsaPhoneFormatFound(ColumnInvalidUsaPhoneFoundCheckSpec dailyInvalidUsaPhoneFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidUsaPhoneFormatFound, dailyInvalidUsaPhoneFormatFound));
        this.dailyInvalidUsaPhoneFormatFound = dailyInvalidUsaPhoneFormatFound;
        propagateHierarchyIdToField(dailyInvalidUsaPhoneFormatFound, "daily_invalid_usa_phone_format_found");
    }

    /**
     * Returns a maximum invalid USA zip codes count check.
     * @return Maximum invalid USA zip codes count check.
     */
    public ColumnInvalidUsaZipcodeFoundCheckSpec getDailyInvalidUsaZipcodeFormatFound() {
        return dailyInvalidUsaZipcodeFormatFound;
    }

    /**
     * Sets a new definition of an invalid USA zip codes count check.
     * @param dailyInvalidUsaZipcodeFormatFound Invalid USA zip codes count check.
     */
    public void setDailyInvalidUsaZipcodeFormatFound(ColumnInvalidUsaZipcodeFoundCheckSpec dailyInvalidUsaZipcodeFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidUsaZipcodeFormatFound, dailyInvalidUsaZipcodeFormatFound));
        this.dailyInvalidUsaZipcodeFormatFound = dailyInvalidUsaZipcodeFormatFound;
        propagateHierarchyIdToField(dailyInvalidUsaZipcodeFormatFound, "daily_invalid_usa_zipcode_format_found");
    }

    /**
     * Returns an invalid USA phones number percent check.
     * @return Invalid USA phones number percent check.
     */
    public ColumnInvalidUsaPhonePercentCheckSpec getDailyInvalidUsaPhoneFormatPercent() {
        return dailyInvalidUsaPhoneFormatPercent;
    }

    /**
     * Sets a new definition of an invalid USA phones number percent check.
     * @param dailyInvalidUsaPhoneFormatPercent Invalid USA phones number percent check.
     */
    public void setDailyInvalidUsaPhoneFormatPercent(ColumnInvalidUsaPhonePercentCheckSpec dailyInvalidUsaPhoneFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidUsaPhoneFormatPercent, dailyInvalidUsaPhoneFormatPercent));
        this.dailyInvalidUsaPhoneFormatPercent = dailyInvalidUsaPhoneFormatPercent;
        propagateHierarchyIdToField(dailyInvalidUsaPhoneFormatPercent, "daily_invalid_usa_phone_format_percent");
    }

    /**
     * Returns an invalid USA zip code percent check.
     * @return Invalid USA zip code percent check.
     */
    public ColumnInvalidUsaZipcodePercentCheckSpec getDailyInvalidUsaZipcodeFormatPercent() {
        return dailyInvalidUsaZipcodeFormatPercent;
    }

    /**
     * Sets a new definition of an invalid USA zip code percent check.
     * @param dailyInvalidUsaZipcodeFormatPercent Invalid USA zip code percent check.
     */
    public void setDailyInvalidUsaZipcodeFormatPercent(ColumnInvalidUsaZipcodePercentCheckSpec dailyInvalidUsaZipcodeFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyInvalidUsaPhoneFormatPercent, dailyInvalidUsaZipcodeFormatPercent));
        this.dailyInvalidUsaZipcodeFormatPercent = dailyInvalidUsaZipcodeFormatPercent;
        propagateHierarchyIdToField(dailyInvalidUsaZipcodeFormatPercent, "daily_invalid_usa_zipcode_format_percent");
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
    public ColumnPatternsDailyMonitoringChecksSpec deepClone() {
        return (ColumnPatternsDailyMonitoringChecksSpec)super.deepClone();
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
        return CheckType.monitoring;
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
