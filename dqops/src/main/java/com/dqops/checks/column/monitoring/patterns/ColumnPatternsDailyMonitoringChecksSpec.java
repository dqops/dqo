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
            put("daily_texts_matching_regex_percent", o -> o.dailyTextsMatchingRegexPercent);
            put("daily_invalid_email_format_found", o -> o.dailyInvalidEmailFormatFound);
            put("daily_invalid_email_format_percent", o -> o.dailyInvalidEmailFormatPercent);
            put("daily_text_not_matching_date_pattern_found", o -> o.dailyTextNotMatchingDatePatternFound);
            put("daily_text_matching_date_pattern_percent", o -> o.dailyTextMatchingDatePatternPercent);
            put("daily_text_matching_name_pattern_percent", o -> o.dailyTextMatchingNamePatternPercent);

            put("daily_invalid_uuid_format_found", o -> o.dailyInvalidUuidFormatFound);
            put("daily_valid_uuid_format_percent", o -> o.dailyValidUuidFormatPercent);
            put("daily_invalid_ip4_address_format_found", o -> o.dailyInvalidIp4AddressFormatFound);
            put("daily_invalid_ip6_address_format_found", o -> o.dailyInvalidIp6AddressFormatFound);

            put("daily_invalid_usa_phone_format_found", o -> o.dailyInvalidUsaPhoneFormatFound);
            put("daily_invalid_usa_zipcode_format_found", o -> o.dailyInvalidUsaZipcodeFormatFound);
            put("daily_valid_usa_phone_format_percent", o -> o.dailyValidUsaPhoneFormatPercent);
            put("daily_valid_usa_zipcode_format_percent", o -> o.dailyValidUsaZipcodeFormatPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingRegexFoundCheckSpec dailyTextNotMatchingRegexFound;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.")
    private ColumnTextsMatchingRegexPercentCheckSpec dailyTextsMatchingRegexPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidEmailFormatFoundCheckSpec dailyInvalidEmailFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid emails in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidEmailFormatPercentCheckSpec dailyInvalidEmailFormatPercent;

    @JsonPropertyDescription("Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingDatePatternFoundCheckSpec dailyTextNotMatchingDatePatternFound;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingDatePatternPercentCheckSpec dailyTextMatchingDatePatternPercent;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingNamePatternPercentCheckSpec dailyTextMatchingNamePatternPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUuidFormatFoundCheckSpec dailyInvalidUuidFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.")
    private ColumnValidUuidFormatPercentCheckSpec dailyValidUuidFormatPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp4AddressFormatFoundCheckSpec dailyInvalidIp4AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp6AddressFormatFoundCheckSpec dailyInvalidIp6AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid USA phone numbers in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaPhoneFoundCheckSpec dailyInvalidUsaPhoneFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid zip codes in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaZipcodeFoundCheckSpec dailyInvalidUsaZipcodeFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phones number in a text column does not fall below the minimum accepted percentage.")
    private ColumnValidUsaPhonePercentCheckSpec dailyValidUsaPhoneFormatPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phones number in a text column does not fall below the minimum accepted percentage.")
    private ColumnValidUsaZipcodePercentCheckSpec dailyValidUsaZipcodeFormatPercent;

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
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnTextsMatchingRegexPercentCheckSpec getDailyTextsMatchingRegexPercent() {
        return dailyTextsMatchingRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param dailyTextsMatchingRegexPercent Minimum match regex percent check.
     */
    public void setDailyTextsMatchingRegexPercent(ColumnTextsMatchingRegexPercentCheckSpec dailyTextsMatchingRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextsMatchingRegexPercent, dailyTextsMatchingRegexPercent));
        this.dailyTextsMatchingRegexPercent = dailyTextsMatchingRegexPercent;
        propagateHierarchyIdToField(dailyTextsMatchingRegexPercent, "daily_texts_matching_regex_percent");
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
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnTextMatchingDatePatternPercentCheckSpec getDailyTextMatchingDatePatternPercent() {
        return dailyTextMatchingDatePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param dailyTextMatchingDatePatternPercent Maximum match date regex percent check.
     */
    public void setDailyTextMatchingDatePatternPercent(ColumnTextMatchingDatePatternPercentCheckSpec dailyTextMatchingDatePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextMatchingDatePatternPercent, dailyTextMatchingDatePatternPercent));
        this.dailyTextMatchingDatePatternPercent = dailyTextMatchingDatePatternPercent;
        propagateHierarchyIdToField(dailyTextMatchingDatePatternPercent, "daily_text_matching_date_pattern_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnTextMatchingNamePatternPercentCheckSpec getDailyTextMatchingNamePatternPercent() {
        return dailyTextMatchingNamePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param dailyTextMatchingNamePatternPercent Maximum match name regex percent check.
     */
    public void setDailyTextMatchingNamePatternPercent(ColumnTextMatchingNamePatternPercentCheckSpec dailyTextMatchingNamePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyTextMatchingNamePatternPercent, dailyTextMatchingNamePatternPercent));
        this.dailyTextMatchingNamePatternPercent = dailyTextMatchingNamePatternPercent;
        propagateHierarchyIdToField(dailyTextMatchingNamePatternPercent, "daily_text_matching_name_pattern_percent");
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
     * Returns a valid UUID percent check.
     * @return Valid UUID percent check.
     */
    public ColumnValidUuidFormatPercentCheckSpec getDailyValidUuidFormatPercent() {
        return dailyValidUuidFormatPercent;
    }

    /**
     * Sets a new definition of a valid UUID percent check.
     * @param dailyValidUuidFormatPercent Valid UUID percent check.
     */
    public void setDailyValidUuidFormatPercent(ColumnValidUuidFormatPercentCheckSpec dailyValidUuidFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyValidUuidFormatPercent, dailyValidUuidFormatPercent));
        this.dailyValidUuidFormatPercent = dailyValidUuidFormatPercent;
        propagateHierarchyIdToField(dailyValidUuidFormatPercent, "daily_valid_uuid_format_percent");
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
     * Returns a valid USA phones number percent check.
     * @return Valid USA phones number percent check.
     */
    public ColumnValidUsaPhonePercentCheckSpec getDailyValidUsaPhoneFormatPercent() {
        return dailyValidUsaPhoneFormatPercent;
    }

    /**
     * Sets a new definition of a valid USA phones number percent check.
     * @param dailyValidUsaPhoneFormatPercent Valid USA phones number percent check.
     */
    public void setDailyValidUsaPhoneFormatPercent(ColumnValidUsaPhonePercentCheckSpec dailyValidUsaPhoneFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyValidUsaPhoneFormatPercent, dailyValidUsaPhoneFormatPercent));
        this.dailyValidUsaPhoneFormatPercent = dailyValidUsaPhoneFormatPercent;
        propagateHierarchyIdToField(dailyValidUsaPhoneFormatPercent, "daily_valid_usa_phone_format_percent");
    }

    /**
     * Returns a valid USA zip code percent check.
     * @return Valid USA zip code percent check.
     */
    public ColumnValidUsaZipcodePercentCheckSpec getDailyValidUsaZipcodeFormatPercent() {
        return dailyValidUsaZipcodeFormatPercent;
    }

    /**
     * Sets a new definition of a valid USA zip code percent check.
     * @param dailyValidUsaZipcodeFormatPercent Valid USA zip code percent check.
     */
    public void setDailyValidUsaZipcodeFormatPercent(ColumnValidUsaZipcodePercentCheckSpec dailyValidUsaZipcodeFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyValidUsaPhoneFormatPercent, dailyValidUsaZipcodeFormatPercent));
        this.dailyValidUsaZipcodeFormatPercent = dailyValidUsaZipcodeFormatPercent;
        propagateHierarchyIdToField(dailyValidUsaZipcodeFormatPercent, "daily_valid_usa_zipcode_format_percent");
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
