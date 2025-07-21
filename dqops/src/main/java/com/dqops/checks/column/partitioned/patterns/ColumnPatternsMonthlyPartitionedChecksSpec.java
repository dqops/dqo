/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
 * Container of built-in preconfigured monthly partition checks on a column level that are checking for values matching patterns (regular expressions) in text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPatternsMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPatternsMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_text_not_matching_regex_found", o -> o.monthlyPartitionTextNotMatchingRegexFound);
            put("monthly_partition_texts_not_matching_regex_percent", o -> o.monthlyPartitionTextsNotMatchingRegexPercent);
            put("monthly_partition_invalid_email_format_found", o -> o.monthlyPartitionInvalidEmailFormatFound);
            put("monthly_partition_invalid_email_format_percent", o -> o.monthlyPartitionInvalidEmailFormatPercent);
            put("monthly_partition_text_not_matching_date_pattern_found", o -> o.monthlyPartitionTextNotMatchingDatePatternFound);
            put("monthly_partition_text_not_matching_date_pattern_percent", o -> o.monthlyPartitionTextNotMatchingDatePatternPercent);
            put("monthly_partition_text_not_matching_name_pattern_percent", o -> o.monthlyPartitionTextNotMatchingNamePatternPercent);

            put("monthly_partition_invalid_uuid_format_found", o -> o.monthlyPartitionInvalidUuidFormatFound);
            put("monthly_partition_invalid_uuid_format_percent", o -> o.monthlyPartitionInvalidUuidFormatPercent);
            put("monthly_partition_invalid_ip4_address_format_found", o -> o.monthlyPartitionInvalidIp4AddressFormatFound);
            put("monthly_partition_invalid_ip6_address_format_found", o -> o.monthlyPartitionInvalidIp6AddressFormatFound);

            put("monthly_partition_invalid_usa_phone_format_found", o -> o.monthlyPartitionInvalidUsaPhoneFormatFound);
            put("monthly_partition_invalid_usa_zipcode_format_found", o -> o.monthlyPartitionInvalidUsaZipcodeFormatFound);
            put("monthly_partition_invalid_usa_phone_format_percent", o -> o.monthlyPartitionInvalidUsaPhoneFormatPercent);
            put("monthly_partition_invalid_usa_zipcode_format_percent", o -> o.monthlyPartitionInvalidUsaZipcodeFormatPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingRegexFoundCheckSpec monthlyPartitionTextNotMatchingRegexFound;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regular expression pattern does not exceed the maximum accepted percentage.")
    private ColumnTextsNotMatchingRegexPercentCheckSpec monthlyPartitionTextsNotMatchingRegexPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidEmailFormatFoundCheckSpec monthlyPartitionInvalidEmailFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid emails in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidEmailFormatPercentCheckSpec monthlyPartitionInvalidEmailFormatPercent;

    @JsonPropertyDescription("Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingDatePatternFoundCheckSpec monthlyPartitionTextNotMatchingDatePatternFound;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the date format regular expression in a column does not exceed the maximum accepted percentage.")
    private ColumnTextNotMatchingDatePatternPercentCheckSpec monthlyPartitionTextNotMatchingDatePatternPercent;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the name regular expression does not exceed the maximum accepted percentage.")
    private ColumnTextNotMatchingNamePatternPercentCheckSpec monthlyPartitionTextNotMatchingNamePatternPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUuidFormatFoundCheckSpec monthlyPartitionInvalidUuidFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid UUID in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUuidFormatPercentCheckSpec monthlyPartitionInvalidUuidFormatPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp4AddressFormatFoundCheckSpec monthlyPartitionInvalidIp4AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp6AddressFormatFoundCheckSpec monthlyPartitionInvalidIp6AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid USA phone numbers in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaPhoneFoundCheckSpec monthlyPartitionInvalidUsaPhoneFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid zip codes in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUsaZipcodeFoundCheckSpec monthlyPartitionInvalidUsaZipcodeFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid USA phones number in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUsaPhonePercentCheckSpec monthlyPartitionInvalidUsaPhoneFormatPercent;

    @JsonPropertyDescription("Verifies that the percentage of invalid USA phones number in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidUsaZipcodePercentCheckSpec monthlyPartitionInvalidUsaZipcodeFormatPercent;

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnTextNotMatchingRegexFoundCheckSpec getMonthlyPartitionTextNotMatchingRegexFound() {
        return monthlyPartitionTextNotMatchingRegexFound;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param monthlyPartitionTextNotMatchingRegexFound Maximum not match regex count check.
     */
    public void setMonthlyPartitionTextNotMatchingRegexFound(ColumnTextNotMatchingRegexFoundCheckSpec monthlyPartitionTextNotMatchingRegexFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextNotMatchingRegexFound, monthlyPartitionTextNotMatchingRegexFound));
        this.monthlyPartitionTextNotMatchingRegexFound = monthlyPartitionTextNotMatchingRegexFound;
        propagateHierarchyIdToField(monthlyPartitionTextNotMatchingRegexFound, "monthly_partition_text_not_matching_regex_found");
    }

    /**
     * Returns a maximum not match regex percent check.
     * @return Maximum not match regex percent check.
     */
    public ColumnTextsNotMatchingRegexPercentCheckSpec getMonthlyPartitionTextsNotMatchingRegexPercent() {
        return monthlyPartitionTextsNotMatchingRegexPercent;
    }

    /**
     * Sets a new definition of a maximum not match regex percent check.
     * @param monthlyPartitionTextsNotMatchingRegexPercent Maximum not match regex percent check.
     */
    public void setMonthlyPartitionTextsNotMatchingRegexPercent(ColumnTextsNotMatchingRegexPercentCheckSpec monthlyPartitionTextsNotMatchingRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextsNotMatchingRegexPercent, monthlyPartitionTextsNotMatchingRegexPercent));
        this.monthlyPartitionTextsNotMatchingRegexPercent = monthlyPartitionTextsNotMatchingRegexPercent;
        propagateHierarchyIdToField(monthlyPartitionTextsNotMatchingRegexPercent, "monthly_partition_texts_not_matching_regex_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnInvalidEmailFormatFoundCheckSpec getMonthlyPartitionInvalidEmailFormatFound() {
        return monthlyPartitionInvalidEmailFormatFound;
    }

    /**
     * Sets a new definition of an invalid email count check.
     * @param monthlyPartitionInvalidEmailFormatFound Invalid email count check.
     */
    public void setMonthlyPartitionInvalidEmailFormatFound(ColumnInvalidEmailFormatFoundCheckSpec monthlyPartitionInvalidEmailFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidEmailFormatFound, monthlyPartitionInvalidEmailFormatFound));
        this.monthlyPartitionInvalidEmailFormatFound = monthlyPartitionInvalidEmailFormatFound;
        propagateHierarchyIdToField(monthlyPartitionInvalidEmailFormatFound, "monthly_partition_invalid_email_format_found");
    }

    /**
     * Returns a maximum invalid email percent check.
     * @return Maximum invalid email percent check.
     */
    public ColumnInvalidEmailFormatPercentCheckSpec getMonthlyPartitionInvalidEmailFormatPercent() {
        return monthlyPartitionInvalidEmailFormatPercent;
    }

    /**
     * Sets a new definition of an invalid email percent check.
     * @param monthlyPartitionInvalidEmailFormatPercent Invalid email percent check.
     */
    public void setMonthlyPartitionInvalidEmailFormatPercent(ColumnInvalidEmailFormatPercentCheckSpec monthlyPartitionInvalidEmailFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidEmailFormatPercent, monthlyPartitionInvalidEmailFormatPercent));
        this.monthlyPartitionInvalidEmailFormatPercent = monthlyPartitionInvalidEmailFormatPercent;
        propagateHierarchyIdToField(monthlyPartitionInvalidEmailFormatPercent, "monthly_partition_invalid_email_format_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnTextNotMatchingDatePatternFoundCheckSpec getMonthlyPartitionTextNotMatchingDatePatternFound() {
        return monthlyPartitionTextNotMatchingDatePatternFound;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param monthlyPartitionTextNotMatchingDatePatternFound Maximum not match date regex count check.
     */
    public void setMonthlyPartitionTextNotMatchingDatePatternFound(ColumnTextNotMatchingDatePatternFoundCheckSpec monthlyPartitionTextNotMatchingDatePatternFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextNotMatchingDatePatternFound, monthlyPartitionTextNotMatchingDatePatternFound));
        this.monthlyPartitionTextNotMatchingDatePatternFound = monthlyPartitionTextNotMatchingDatePatternFound;
        propagateHierarchyIdToField(monthlyPartitionTextNotMatchingDatePatternFound, "monthly_partition_text_not_matching_date_pattern_found");
    }

    /**
     * Returns a maximum not match date regex percent check.
     * @return Maximum not match date regex percent check.
     */
    public ColumnTextNotMatchingDatePatternPercentCheckSpec getMonthlyPartitionTextNotMatchingDatePatternPercent() {
        return monthlyPartitionTextNotMatchingDatePatternPercent;
    }

    /**
     * Sets a new definition of a maximum not match date regex percent check.
     * @param monthlyPartitionTextNotMatchingDatePatternPercent Maximum not match date regex percent check.
     */
    public void setMonthlyPartitionTextNotMatchingDatePatternPercent(ColumnTextNotMatchingDatePatternPercentCheckSpec monthlyPartitionTextNotMatchingDatePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextNotMatchingDatePatternPercent, monthlyPartitionTextNotMatchingDatePatternPercent));
        this.monthlyPartitionTextNotMatchingDatePatternPercent = monthlyPartitionTextNotMatchingDatePatternPercent;
        propagateHierarchyIdToField(monthlyPartitionTextNotMatchingDatePatternPercent, "monthly_partition_text_not_matching_date_pattern_percent");
    }

    /**
     * Returns a maximum not match name regex percent check.
     * @return Maximum not match name regex percent check.
     */
    public ColumnTextNotMatchingNamePatternPercentCheckSpec getMonthlyPartitionTextNotMatchingNamePatternPercent() {
        return monthlyPartitionTextNotMatchingNamePatternPercent;
    }

    /**
     * Sets a new definition of a maximum not match name regex percent check.
     * @param monthlyPartitionTextNotMatchingNamePatternPercent Maximum not match name regex percent check.
     */
    public void setMonthlyPartitionTextNotMatchingNamePatternPercent(ColumnTextNotMatchingNamePatternPercentCheckSpec monthlyPartitionTextNotMatchingNamePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextNotMatchingNamePatternPercent, monthlyPartitionTextNotMatchingNamePatternPercent));
        this.monthlyPartitionTextNotMatchingNamePatternPercent = monthlyPartitionTextNotMatchingNamePatternPercent;
        propagateHierarchyIdToField(monthlyPartitionTextNotMatchingNamePatternPercent, "monthly_partition_text_not_matching_name_pattern_percent");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnInvalidUuidFormatFoundCheckSpec getMonthlyPartitionInvalidUuidFormatFound() {
        return monthlyPartitionInvalidUuidFormatFound;
    }

    /**
     * Sets a new definition of an invalid UUID count check.
     * @param monthlyPartitionInvalidUuidFormatFound Invalid UUID count check.
     */
    public void setMonthlyPartitionInvalidUuidFormatFound(ColumnInvalidUuidFormatFoundCheckSpec monthlyPartitionInvalidUuidFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidUuidFormatFound, monthlyPartitionInvalidUuidFormatFound));
        this.monthlyPartitionInvalidUuidFormatFound = monthlyPartitionInvalidUuidFormatFound;
        propagateHierarchyIdToField(monthlyPartitionInvalidUuidFormatFound, "monthly_partition_invalid_uuid_format_found");
    }

    /**
     * Returns an invalid UUID percent check.
     * @return Invalid UUID percent check.
     */
    public ColumnInvalidUuidFormatPercentCheckSpec getMonthlyPartitionInvalidUuidFormatPercent() {
        return monthlyPartitionInvalidUuidFormatPercent;
    }

    /**
     * Sets a new definition of an invalid UUID percent check.
     * @param monthlyPartitionInvalidUuidFormatPercent Invalid UUID percent check.
     */
    public void setMonthlyPartitionInvalidUuidFormatPercent(ColumnInvalidUuidFormatPercentCheckSpec monthlyPartitionInvalidUuidFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidUuidFormatPercent, monthlyPartitionInvalidUuidFormatPercent));
        this.monthlyPartitionInvalidUuidFormatPercent = monthlyPartitionInvalidUuidFormatPercent;
        propagateHierarchyIdToField(monthlyPartitionInvalidUuidFormatPercent, "monthly_partition_invalid_uuid_format_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnInvalidIp4AddressFormatFoundCheckSpec getMonthlyPartitionInvalidIp4AddressFormatFound() {
        return monthlyPartitionInvalidIp4AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP4 address count check.
     * @param monthlyPartitionInvalidIp4AddressFormatFound Invalid IP4 address count check.
     */
    public void setMonthlyPartitionInvalidIp4AddressFormatFound(ColumnInvalidIp4AddressFormatFoundCheckSpec monthlyPartitionInvalidIp4AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidIp4AddressFormatFound, monthlyPartitionInvalidIp4AddressFormatFound));
        this.monthlyPartitionInvalidIp4AddressFormatFound = monthlyPartitionInvalidIp4AddressFormatFound;
        propagateHierarchyIdToField(monthlyPartitionInvalidIp4AddressFormatFound, "monthly_partition_invalid_ip4_address_format_found");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnInvalidIp6AddressFormatFoundCheckSpec getMonthlyPartitionInvalidIp6AddressFormatFound() {
        return monthlyPartitionInvalidIp6AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP6 address count check.
     * @param monthlyPartitionInvalidIp6AddressFormatFound Invalid IP6 address count check.
     */
    public void setMonthlyPartitionInvalidIp6AddressFormatFound(ColumnInvalidIp6AddressFormatFoundCheckSpec monthlyPartitionInvalidIp6AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidIp6AddressFormatFound, monthlyPartitionInvalidIp6AddressFormatFound));
        this.monthlyPartitionInvalidIp6AddressFormatFound = monthlyPartitionInvalidIp6AddressFormatFound;
        propagateHierarchyIdToField(monthlyPartitionInvalidIp6AddressFormatFound, "monthly_partition_invalid_ip6_address_format_found");
    }


    /**
     * Returns a maximum invalid USA phone numbers count check.
     * @return Maximum invalid USA phone numbers count check.
     */
    public ColumnInvalidUsaPhoneFoundCheckSpec getMonthlyPartitionInvalidUsaPhoneFormatFound() {
        return monthlyPartitionInvalidUsaPhoneFormatFound;
    }

    /**
     * Sets a new definition of an invalid USA phone numbers count check.
     * @param monthlyPartitionInvalidUsaPhoneFormatFound Invalid USA phone numbers count check.
     */
    public void setMonthlyPartitionInvalidUsaPhoneFormatFound(ColumnInvalidUsaPhoneFoundCheckSpec monthlyPartitionInvalidUsaPhoneFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidUsaPhoneFormatFound, monthlyPartitionInvalidUsaPhoneFormatFound));
        this.monthlyPartitionInvalidUsaPhoneFormatFound = monthlyPartitionInvalidUsaPhoneFormatFound;
        propagateHierarchyIdToField(monthlyPartitionInvalidUsaPhoneFormatFound, "monthly_partition_invalid_usa_phone_format_found");
    }

    /**
     * Returns a maximum invalid USA zip codes count check.
     * @return Maximum invalid USA zip codes count check.
     */
    public ColumnInvalidUsaZipcodeFoundCheckSpec getMonthlyPartitionInvalidUsaZipcodeFormatFound() {
        return monthlyPartitionInvalidUsaZipcodeFormatFound;
    }

    /**
     * Sets a new definition of an invalid USA zip codes count check.
     * @param monthlyPartitionInvalidUsaZipcodeFormatFound Invalid USA zip codes count check.
     */
    public void setMonthlyPartitionInvalidUsaZipcodeFormatFound(ColumnInvalidUsaZipcodeFoundCheckSpec monthlyPartitionInvalidUsaZipcodeFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidUsaZipcodeFormatFound, monthlyPartitionInvalidUsaZipcodeFormatFound));
        this.monthlyPartitionInvalidUsaZipcodeFormatFound = monthlyPartitionInvalidUsaZipcodeFormatFound;
        propagateHierarchyIdToField(monthlyPartitionInvalidUsaZipcodeFormatFound, "monthly_partition_invalid_usa_zipcode_format_found");
    }

    /**
     * Returns an invalid USA phones number percent check.
     * @return Invalid USA phones number percent check.
     */
    public ColumnInvalidUsaPhonePercentCheckSpec getMonthlyPartitionInvalidUsaPhoneFormatPercent() {
        return monthlyPartitionInvalidUsaPhoneFormatPercent;
    }

    /**
     * Sets a new definition of an invalid USA phones number percent check.
     * @param monthlyPartitionInvalidUsaPhoneFormatPercent Invalid USA phones number percent check.
     */
    public void setMonthlyPartitionInvalidUsaPhoneFormatPercent(ColumnInvalidUsaPhonePercentCheckSpec monthlyPartitionInvalidUsaPhoneFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidUsaPhoneFormatPercent, monthlyPartitionInvalidUsaPhoneFormatPercent));
        this.monthlyPartitionInvalidUsaPhoneFormatPercent = monthlyPartitionInvalidUsaPhoneFormatPercent;
        propagateHierarchyIdToField(monthlyPartitionInvalidUsaPhoneFormatPercent, "monthly_partition_invalid_usa_phone_format_percent");
    }

    /**
     * Returns an invalid USA zip code percent check.
     * @return Invalid USA zip code percent check.
     */
    public ColumnInvalidUsaZipcodePercentCheckSpec getMonthlyPartitionInvalidUsaZipcodeFormatPercent() {
        return monthlyPartitionInvalidUsaZipcodeFormatPercent;
    }

    /**
     * Sets a new definition of an invalid USA zip code percent check.
     * @param monthlyPartitionInvalidUsaZipcodeFormatPercent Invalid USA zip code percent check.
     */
    public void setMonthlyPartitionInvalidUsaZipcodeFormatPercent(ColumnInvalidUsaZipcodePercentCheckSpec monthlyPartitionInvalidUsaZipcodeFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionInvalidUsaPhoneFormatPercent, monthlyPartitionInvalidUsaZipcodeFormatPercent));
        this.monthlyPartitionInvalidUsaZipcodeFormatPercent = monthlyPartitionInvalidUsaZipcodeFormatPercent;
        propagateHierarchyIdToField(monthlyPartitionInvalidUsaZipcodeFormatPercent, "monthly_partition_invalid_usa_zipcode_format_percent");
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
    public ColumnPatternsMonthlyPartitionedChecksSpec deepClone() {
        return (ColumnPatternsMonthlyPartitionedChecksSpec)super.deepClone();
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
        return CheckTimeScale.monthly;
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
