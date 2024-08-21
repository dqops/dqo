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
package com.dqops.checks.column.partitioned.patterns;

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
 * Container of built-in preconfigured daily partition checks on a column level that are checking for values matching patterns (regular expressions) in text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPatternsDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPatternsDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_text_not_matching_regex_found", o -> o.dailyPartitionTextNotMatchingRegexFound);
            put("daily_partition_texts_not_matching_regex_percent", o -> o.dailyPartitionTextsNotMatchingRegexPercent);
            put("daily_partition_invalid_email_format_found", o -> o.dailyPartitionInvalidEmailFormatFound);
            put("daily_partition_invalid_email_format_percent", o -> o.dailyPartitionInvalidEmailFormatPercent);
            put("daily_partition_text_not_matching_date_pattern_found", o -> o.dailyPartitionTextNotMatchingDatePatternFound);
            put("daily_partition_text_not_matching_date_pattern_percent", o -> o.dailyPartitionTextNotMatchingDatePatternPercent);
            put("daily_partition_text_not_matching_name_pattern_percent", o -> o.dailyPartitionTextNotMatchingNamePatternPercent);

            put("daily_partition_invalid_uuid_format_found", o -> o.dailyPartitionInvalidUuidFormatFound);
            put("daily_partition_invalid_uuid_format_percent", o -> o.dailyPartitionInvalidUuidFormatPercent);
            put("daily_partition_invalid_ip4_address_format_found", o -> o.dailyPartitionInvalidIp4AddressFormatFound);
            put("daily_partition_invalid_ip6_address_format_found", o -> o.dailyPartitionInvalidIp6AddressFormatFound);

            put("daily_partition_invalid_usa_phone_format_found", o -> o.dailyPartitionInvalidUsaPhoneFormatFound);
            put("daily_partition_invalid_usa_zipcode_format_found", o -> o.dailyPartitionInvalidUsaZipcodeFormatFound);
            put("daily_partition_invalid_usa_phone_format_percent", o -> o.dailyPartitionInvalidUsaPhoneFormatPercent);
            put("daily_partition_invalid_usa_zipcode_format_percent", o -> o.dailyPartitionInvalidUsaZipcodeFormatPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingRegexFoundCheckSpec dailyPartitionTextNotMatchingRegexFound;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regular expression pattern does not exceed the maximum accepted percentage.")
    private ColumnTextsNotMatchingRegexPercentCheckSpec dailyPartitionTextsNotMatchingRegexPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidEmailFormatFoundCheckSpec dailyPartitionInvalidEmailFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid emails in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidEmailFormatPercentCheckSpec dailyPartitionInvalidEmailFormatPercent;

    @JsonPropertyDescription("Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingDatePatternFoundCheckSpec dailyPartitionTextNotMatchingDatePatternFound;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the date format regular expression in a column does not exceed the maximum accepted percentage.")
    private ColumnTextNotMatchingDatePatternPercentCheckSpec dailyPartitionTextNotMatchingDatePatternPercent;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the name regular expression does not exceed the maximum accepted percentage.")
    private ColumnTextNotMatchingNamePatternPercentCheckSpec dailyPartitionTextNotMatchingNamePatternPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUuidFormatFoundCheckSpec dailyPartitionInvalidUuidFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid UUID in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUuidFormatPercentCheckSpec dailyPartitionInvalidUuidFormatPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp4AddressFormatFoundCheckSpec dailyPartitionInvalidIp4AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp6AddressFormatFoundCheckSpec dailyPartitionInvalidIp6AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid USA phone numbers in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaPhoneFoundCheckSpec dailyPartitionInvalidUsaPhoneFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid zip codes in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaZipcodeFoundCheckSpec dailyPartitionInvalidUsaZipcodeFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid USA phones number in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUsaPhonePercentCheckSpec dailyPartitionInvalidUsaPhoneFormatPercent;

    @JsonPropertyDescription("Verifies that the percentage of invalid USA phones number in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUsaZipcodePercentCheckSpec dailyPartitionInvalidUsaZipcodeFormatPercent;

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnTextNotMatchingRegexFoundCheckSpec getDailyPartitionTextNotMatchingRegexFound() {
        return dailyPartitionTextNotMatchingRegexFound;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param dailyPartitionTextNotMatchingRegexFound Maximum not match regex count check.
     */
    public void setDailyPartitionTextNotMatchingRegexFound(ColumnTextNotMatchingRegexFoundCheckSpec dailyPartitionTextNotMatchingRegexFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextNotMatchingRegexFound, dailyPartitionTextNotMatchingRegexFound));
        this.dailyPartitionTextNotMatchingRegexFound = dailyPartitionTextNotMatchingRegexFound;
        propagateHierarchyIdToField(dailyPartitionTextNotMatchingRegexFound, "daily_partition_text_not_matching_regex_found");
    }

    /**
     * Returns a maximum not match regex percent check.
     * @return Maximum not match regex percent check.
     */
    public ColumnTextsNotMatchingRegexPercentCheckSpec getDailyPartitionTextsNotMatchingRegexPercent() {
        return dailyPartitionTextsNotMatchingRegexPercent;
    }

    /**
     * Sets a new definition of a maximum not match regex percent check.
     * @param dailyPartitionTextsNotMatchingRegexPercent Maximum not match regex percent check.
     */
    public void setDailyPartitionTextsNotMatchingRegexPercent(ColumnTextsNotMatchingRegexPercentCheckSpec dailyPartitionTextsNotMatchingRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextsNotMatchingRegexPercent, dailyPartitionTextsNotMatchingRegexPercent));
        this.dailyPartitionTextsNotMatchingRegexPercent = dailyPartitionTextsNotMatchingRegexPercent;
        propagateHierarchyIdToField(dailyPartitionTextsNotMatchingRegexPercent, "daily_partition_texts_not_matching_regex_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnInvalidEmailFormatFoundCheckSpec getDailyPartitionInvalidEmailFormatFound() {
        return dailyPartitionInvalidEmailFormatFound;
    }

    /**
     * Sets a new definition of an invalid email count check.
     * @param dailyPartitionInvalidEmailFormatFound Invalid email count check.
     */
    public void setDailyPartitionInvalidEmailFormatFound(ColumnInvalidEmailFormatFoundCheckSpec dailyPartitionInvalidEmailFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidEmailFormatFound, dailyPartitionInvalidEmailFormatFound));
        this.dailyPartitionInvalidEmailFormatFound = dailyPartitionInvalidEmailFormatFound;
        propagateHierarchyIdToField(dailyPartitionInvalidEmailFormatFound, "daily_partition_invalid_email_format_found");
    }

    /**
     * Returns a maximum invalid email percent check.
     * @return Maximum invalid email percent check.
     */
    public ColumnInvalidEmailFormatPercentCheckSpec getDailyPartitionInvalidEmailFormatPercent() {
        return dailyPartitionInvalidEmailFormatPercent;
    }

    /**
     * Sets a new definition of an invalid email percent check.
     * @param dailyPartitionInvalidEmailFormatPercent Invalid email percent check.
     */
    public void setDailyPartitionInvalidEmailFormatPercent(ColumnInvalidEmailFormatPercentCheckSpec dailyPartitionInvalidEmailFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidEmailFormatPercent, dailyPartitionInvalidEmailFormatPercent));
        this.dailyPartitionInvalidEmailFormatPercent = dailyPartitionInvalidEmailFormatPercent;
        propagateHierarchyIdToField(dailyPartitionInvalidEmailFormatPercent, "daily_partition_invalid_email_format_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnTextNotMatchingDatePatternFoundCheckSpec getDailyPartitionTextNotMatchingDatePatternFound() {
        return dailyPartitionTextNotMatchingDatePatternFound;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param dailyPartitionTextNotMatchingDatePatternFound Maximum not match date regex count check.
     */
    public void setDailyPartitionTextNotMatchingDatePatternFound(ColumnTextNotMatchingDatePatternFoundCheckSpec dailyPartitionTextNotMatchingDatePatternFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextNotMatchingDatePatternFound, dailyPartitionTextNotMatchingDatePatternFound));
        this.dailyPartitionTextNotMatchingDatePatternFound = dailyPartitionTextNotMatchingDatePatternFound;
        propagateHierarchyIdToField(dailyPartitionTextNotMatchingDatePatternFound, "daily_partition_text_not_matching_date_pattern_found");
    }

    /**
     * Returns a maximum not match date regex percent check.
     * @return Maximum not match date regex percent check.
     */
    public ColumnTextNotMatchingDatePatternPercentCheckSpec getDailyPartitionTextNotMatchingDatePatternPercent() {
        return dailyPartitionTextNotMatchingDatePatternPercent;
    }

    /**
     * Sets a new definition of a maximum not match date regex percent check.
     * @param dailyPartitionTextNotMatchingDatePatternPercent Maximum not match date regex percent check.
     */
    public void setDailyPartitionTextNotMatchingDatePatternPercent(ColumnTextNotMatchingDatePatternPercentCheckSpec dailyPartitionTextNotMatchingDatePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextNotMatchingDatePatternPercent, dailyPartitionTextNotMatchingDatePatternPercent));
        this.dailyPartitionTextNotMatchingDatePatternPercent = dailyPartitionTextNotMatchingDatePatternPercent;
        propagateHierarchyIdToField(dailyPartitionTextNotMatchingDatePatternPercent, "daily_partition_text_not_matching_date_pattern_percent");
    }

    /**
     * Returns a maximum not match name regex percent check.
     * @return Maximum not match name regex percent check.
     */
    public ColumnTextNotMatchingNamePatternPercentCheckSpec getDailyPartitionTextNotMatchingNamePatternPercent() {
        return dailyPartitionTextNotMatchingNamePatternPercent;
    }

    /**
     * Sets a new definition of a maximum not match name regex percent check.
     * @param dailyPartitionTextNotMatchingNamePatternPercent Maximum not match name regex percent check.
     */
    public void setDailyPartitionTextNotMatchingNamePatternPercent(ColumnTextNotMatchingNamePatternPercentCheckSpec dailyPartitionTextNotMatchingNamePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextNotMatchingNamePatternPercent, dailyPartitionTextNotMatchingNamePatternPercent));
        this.dailyPartitionTextNotMatchingNamePatternPercent = dailyPartitionTextNotMatchingNamePatternPercent;
        propagateHierarchyIdToField(dailyPartitionTextNotMatchingNamePatternPercent, "daily_partition_text_not_matching_name_pattern_percent");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnInvalidUuidFormatFoundCheckSpec getDailyPartitionInvalidUuidFormatFound() {
        return dailyPartitionInvalidUuidFormatFound;
    }

    /**
     * Sets a new definition of an invalid UUID count check.
     * @param dailyPartitionInvalidUuidFormatFound Invalid UUID count check.
     */
    public void setDailyPartitionInvalidUuidFormatFound(ColumnInvalidUuidFormatFoundCheckSpec dailyPartitionInvalidUuidFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidUuidFormatFound, dailyPartitionInvalidUuidFormatFound));
        this.dailyPartitionInvalidUuidFormatFound = dailyPartitionInvalidUuidFormatFound;
        propagateHierarchyIdToField(dailyPartitionInvalidUuidFormatFound, "daily_partition_invalid_uuid_format_found");
    }

    /**
     * Returns an invalid UUID percent check.
     * @return Invalid UUID percent check.
     */
    public ColumnInvalidUuidFormatPercentCheckSpec getDailyPartitionInvalidUuidFormatPercent() {
        return dailyPartitionInvalidUuidFormatPercent;
    }

    /**
     * Sets a new definition of an invalid UUID percent check.
     * @param dailyPartitionInvalidUuidFormatPercent Invalid UUID percent check.
     */
    public void setDailyPartitionInvalidUuidFormatPercent(ColumnInvalidUuidFormatPercentCheckSpec dailyPartitionInvalidUuidFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidUuidFormatPercent, dailyPartitionInvalidUuidFormatPercent));
        this.dailyPartitionInvalidUuidFormatPercent = dailyPartitionInvalidUuidFormatPercent;
        propagateHierarchyIdToField(dailyPartitionInvalidUuidFormatPercent, "daily_partition_invalid_uuid_format_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnInvalidIp4AddressFormatFoundCheckSpec getDailyPartitionInvalidIp4AddressFormatFound() {
        return dailyPartitionInvalidIp4AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP4 address count check.
     * @param dailyPartitionInvalidIp4AddressFormatFound Invalid IP4 address count check.
     */
    public void setDailyPartitionInvalidIp4AddressFormatFound(ColumnInvalidIp4AddressFormatFoundCheckSpec dailyPartitionInvalidIp4AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidIp4AddressFormatFound, dailyPartitionInvalidIp4AddressFormatFound));
        this.dailyPartitionInvalidIp4AddressFormatFound = dailyPartitionInvalidIp4AddressFormatFound;
        propagateHierarchyIdToField(dailyPartitionInvalidIp4AddressFormatFound, "daily_partition_invalid_ip4_address_format_found");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnInvalidIp6AddressFormatFoundCheckSpec getDailyPartitionInvalidIp6AddressFormatFound() {
        return dailyPartitionInvalidIp6AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP6 address count check.
     * @param dailyPartitionInvalidIp6AddressFormatFound Invalid IP6 address count check.
     */
    public void setDailyPartitionInvalidIp6AddressFormatFound(ColumnInvalidIp6AddressFormatFoundCheckSpec dailyPartitionInvalidIp6AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidIp6AddressFormatFound, dailyPartitionInvalidIp6AddressFormatFound));
        this.dailyPartitionInvalidIp6AddressFormatFound = dailyPartitionInvalidIp6AddressFormatFound;
        propagateHierarchyIdToField(dailyPartitionInvalidIp6AddressFormatFound, "daily_partition_invalid_ip6_address_format_found");
    }

    /**
     * Returns a maximum invalid USA phone numbers count check.
     * @return Maximum invalid USA phone numbers count check.
     */
    public ColumnInvalidUsaPhoneFoundCheckSpec getDailyPartitionInvalidUsaPhoneFormatFound() {
        return dailyPartitionInvalidUsaPhoneFormatFound;
    }

    /**
     * Sets a new definition of an invalid USA phone numbers count check.
     * @param dailyPartitionInvalidUsaPhoneFormatFound Invalid USA phone numbers count check.
     */
    public void setDailyPartitionInvalidUsaPhoneFormatFound(ColumnInvalidUsaPhoneFoundCheckSpec dailyPartitionInvalidUsaPhoneFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidUsaPhoneFormatFound, dailyPartitionInvalidUsaPhoneFormatFound));
        this.dailyPartitionInvalidUsaPhoneFormatFound = dailyPartitionInvalidUsaPhoneFormatFound;
        propagateHierarchyIdToField(dailyPartitionInvalidUsaPhoneFormatFound, "daily_partition_invalid_usa_phone_format_found");
    }

    /**
     * Returns a maximum invalid USA zip codes count check.
     * @return Maximum invalid USA zip codes count check.
     */
    public ColumnInvalidUsaZipcodeFoundCheckSpec getDailyPartitionInvalidUsaZipcodeFormatFound() {
        return dailyPartitionInvalidUsaZipcodeFormatFound;
    }

    /**
     * Sets a new definition of an invalid USA zip codes count check.
     * @param dailyPartitionInvalidUsaZipcodeFormatFound Invalid USA zip codes count check.
     */
    public void setDailyPartitionInvalidUsaZipcodeFormatFound(ColumnInvalidUsaZipcodeFoundCheckSpec dailyPartitionInvalidUsaZipcodeFormatFound) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidUsaZipcodeFormatFound, dailyPartitionInvalidUsaZipcodeFormatFound));
        this.dailyPartitionInvalidUsaZipcodeFormatFound = dailyPartitionInvalidUsaZipcodeFormatFound;
        propagateHierarchyIdToField(dailyPartitionInvalidUsaZipcodeFormatFound, "daily_partition_invalid_usa_zipcode_format_found");
    }

    /**
     * Returns an invalid USA phones number percent check.
     * @return Invalid USA phones number percent check.
     */
    public ColumnInvalidUsaPhonePercentCheckSpec getDailyPartitionInvalidUsaPhoneFormatPercent() {
        return dailyPartitionInvalidUsaPhoneFormatPercent;
    }

    /**
     * Sets a new definition of an invalid USA phones number percent check.
     * @param dailyPartitionInvalidUsaPhoneFormatPercent Invalid USA phones number percent check.
     */
    public void setDailyPartitionInvalidUsaPhoneFormatPercent(ColumnInvalidUsaPhonePercentCheckSpec dailyPartitionInvalidUsaPhoneFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidUsaPhoneFormatPercent, dailyPartitionInvalidUsaPhoneFormatPercent));
        this.dailyPartitionInvalidUsaPhoneFormatPercent = dailyPartitionInvalidUsaPhoneFormatPercent;
        propagateHierarchyIdToField(dailyPartitionInvalidUsaPhoneFormatPercent, "daily_partition_invalid_usa_phone_format_percent");
    }

    /**
     * Returns an invalid USA zip code percent check.
     * @return Invalid USA zip code percent check.
     */
    public ColumnInvalidUsaZipcodePercentCheckSpec getDailyPartitionInvalidUsaZipcodeFormatPercent() {
        return dailyPartitionInvalidUsaZipcodeFormatPercent;
    }

    /**
     * Sets a new definition of an invalid USA zip code percent check.
     * @param dailyPartitionInvalidUsaZipcodeFormatPercent Invalid USA zip code percent check.
     */
    public void setDailyPartitionInvalidUsaZipcodeFormatPercent(ColumnInvalidUsaZipcodePercentCheckSpec dailyPartitionInvalidUsaZipcodeFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionInvalidUsaPhoneFormatPercent, dailyPartitionInvalidUsaZipcodeFormatPercent));
        this.dailyPartitionInvalidUsaZipcodeFormatPercent = dailyPartitionInvalidUsaZipcodeFormatPercent;
        propagateHierarchyIdToField(dailyPartitionInvalidUsaZipcodeFormatPercent, "daily_partition_invalid_usa_zipcode_format_percent");
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
    public ColumnPatternsDailyPartitionedChecksSpec deepClone() {
        return (ColumnPatternsDailyPartitionedChecksSpec)super.deepClone();
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
        return CheckType.partitioned;
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
