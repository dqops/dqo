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
 * Container of built-in preconfigured monthly monitoring checks on a column level that are checking for values matching patterns (regular expressions) in text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPatternsMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPatternsMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_text_not_matching_regex_found", o -> o.monthlyTextNotMatchingRegexFound);
            put("monthly_texts_matching_regex_percent", o -> o.monthlyTextsMatchingRegexPercent);
            put("monthly_invalid_email_format_found", o -> o.monthlyInvalidEmailFormatFound);
            put("monthly_invalid_email_format_percent", o -> o.monthlyInvalidEmailFormatPercent);
            put("monthly_text_not_matching_date_pattern_found", o -> o.monthlyTextNotMatchingDatePatternFound);
            put("monthly_text_matching_date_pattern_percent", o -> o.monthlyTextMatchingDatePatternPercent);
            put("monthly_text_matching_name_pattern_percent", o -> o.monthlyTextMatchingNamePatternPercent);

            put("monthly_invalid_uuid_format_found", o -> o.monthlyInvalidUuidFormatFound);
            put("monthly_valid_uuid_format_percent", o -> o.monthlyValidUuidFormatPercent);
            put("monthly_invalid_ip4_address_format_found", o -> o.monthlyInvalidIp4AddressFormatFound);
            put("monthly_invalid_ip6_address_format_found", o -> o.monthlyInvalidIp6AddressFormatFound);
        }
    };

    @JsonPropertyDescription("Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingRegexFoundCheckSpec monthlyTextNotMatchingRegexFound;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.")
    private ColumnTextsMatchingRegexPercentCheckSpec monthlyTextsMatchingRegexPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidEmailFormatFoundCheckSpec monthlyInvalidEmailFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid emails in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidEmailFormatPercentCheckSpec monthlyInvalidEmailFormatPercent;

    @JsonPropertyDescription("Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingDatePatternFoundCheckSpec monthlyTextNotMatchingDatePatternFound;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingDatePatternPercentCheckSpec monthlyTextMatchingDatePatternPercent;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingNamePatternPercentCheckSpec monthlyTextMatchingNamePatternPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUuidFormatFoundCheckSpec monthlyInvalidUuidFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.")
    private ColumnValidUuidFormatPercentCheckSpec monthlyValidUuidFormatPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp4AddressFormatFoundCheckSpec monthlyInvalidIp4AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp6AddressFormatFoundCheckSpec monthlyInvalidIp6AddressFormatFound;

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnTextNotMatchingRegexFoundCheckSpec getMonthlyTextNotMatchingRegexFound() {
        return monthlyTextNotMatchingRegexFound;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param monthlyTextNotMatchingRegexFound Maximum not match regex count check.
     */
    public void setMonthlyTextNotMatchingRegexFound(ColumnTextNotMatchingRegexFoundCheckSpec monthlyTextNotMatchingRegexFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextNotMatchingRegexFound, monthlyTextNotMatchingRegexFound));
        this.monthlyTextNotMatchingRegexFound = monthlyTextNotMatchingRegexFound;
        propagateHierarchyIdToField(monthlyTextNotMatchingRegexFound, "monthly_text_not_matching_regex_found");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnTextsMatchingRegexPercentCheckSpec getMonthlyTextsMatchingRegexPercent() {
        return monthlyTextsMatchingRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param monthlyTextsMatchingRegexPercent Minimum match regex percent check.
     */
    public void setMonthlyTextsMatchingRegexPercent(ColumnTextsMatchingRegexPercentCheckSpec monthlyTextsMatchingRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextsMatchingRegexPercent, monthlyTextsMatchingRegexPercent));
        this.monthlyTextsMatchingRegexPercent = monthlyTextsMatchingRegexPercent;
        propagateHierarchyIdToField(monthlyTextsMatchingRegexPercent, "monthly_texts_matching_regex_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnInvalidEmailFormatFoundCheckSpec getMonthlyInvalidEmailFormatFound() {
        return monthlyInvalidEmailFormatFound;
    }

    /**
     * Sets a new definition of an invalid email count check.
     * @param monthlyInvalidEmailFormatFound Invalid email count check.
     */
    public void setMonthlyInvalidEmailFormatFound(ColumnInvalidEmailFormatFoundCheckSpec monthlyInvalidEmailFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyInvalidEmailFormatFound, monthlyInvalidEmailFormatFound));
        this.monthlyInvalidEmailFormatFound = monthlyInvalidEmailFormatFound;
        propagateHierarchyIdToField(monthlyInvalidEmailFormatFound, "monthly_invalid_email_format_found");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnInvalidEmailFormatPercentCheckSpec getMonthlyInvalidEmailFormatPercent() {
        return monthlyInvalidEmailFormatPercent;
    }

    /**
     * Sets a new definition of an invalid email count check.
     * @param monthlyInvalidEmailFormatPercent Invalid email count check.
     */
    public void setMonthlyInvalidEmailFormatPercent(ColumnInvalidEmailFormatPercentCheckSpec monthlyInvalidEmailFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyInvalidEmailFormatPercent, monthlyInvalidEmailFormatPercent));
        this.monthlyInvalidEmailFormatPercent = monthlyInvalidEmailFormatPercent;
        propagateHierarchyIdToField(monthlyInvalidEmailFormatPercent, "monthly_invalid_email_format_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnTextNotMatchingDatePatternFoundCheckSpec getMonthlyTextNotMatchingDatePatternFound() {
        return monthlyTextNotMatchingDatePatternFound;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param monthlyTextNotMatchingDatePatternFound Maximum not match date regex count check.
     */
    public void setMonthlyTextNotMatchingDatePatternFound(ColumnTextNotMatchingDatePatternFoundCheckSpec monthlyTextNotMatchingDatePatternFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextNotMatchingDatePatternFound, monthlyTextNotMatchingDatePatternFound));
        this.monthlyTextNotMatchingDatePatternFound = monthlyTextNotMatchingDatePatternFound;
        propagateHierarchyIdToField(monthlyTextNotMatchingDatePatternFound, "monthly_text_not_matching_date_pattern_found");
    }

    /**
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnTextMatchingDatePatternPercentCheckSpec getMonthlyTextMatchingDatePatternPercent() {
        return monthlyTextMatchingDatePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param monthlyTextMatchingDatePatternPercent Maximum match date regex percent check.
     */
    public void setMonthlyTextMatchingDatePatternPercent(ColumnTextMatchingDatePatternPercentCheckSpec monthlyTextMatchingDatePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextMatchingDatePatternPercent, monthlyTextMatchingDatePatternPercent));
        this.monthlyTextMatchingDatePatternPercent = monthlyTextMatchingDatePatternPercent;
        propagateHierarchyIdToField(monthlyTextMatchingDatePatternPercent, "monthly_text_matching_date_pattern_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnTextMatchingNamePatternPercentCheckSpec getMonthlyTextMatchingNamePatternPercent() {
        return monthlyTextMatchingNamePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param monthlyTextMatchingNamePatternPercent Maximum match name regex percent check.
     */
    public void setMonthlyTextMatchingNamePatternPercent(ColumnTextMatchingNamePatternPercentCheckSpec monthlyTextMatchingNamePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyTextMatchingNamePatternPercent, monthlyTextMatchingNamePatternPercent));
        this.monthlyTextMatchingNamePatternPercent = monthlyTextMatchingNamePatternPercent;
        propagateHierarchyIdToField(monthlyTextMatchingNamePatternPercent, "monthly_text_matching_name_pattern_percent");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnInvalidUuidFormatFoundCheckSpec getMonthlyInvalidUuidFormatFound() {
        return monthlyInvalidUuidFormatFound;
    }

    /**
     * Sets a new definition of an invalid UUID count check.
     * @param monthlyInvalidUuidFormatFound Invalid UUID count check.
     */
    public void setMonthlyInvalidUuidFormatFound(ColumnInvalidUuidFormatFoundCheckSpec monthlyInvalidUuidFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyInvalidUuidFormatFound, monthlyInvalidUuidFormatFound));
        this.monthlyInvalidUuidFormatFound = monthlyInvalidUuidFormatFound;
        propagateHierarchyIdToField(monthlyInvalidUuidFormatFound, "monthly_invalid_uuid_format_found");
    }

    /**
     * Returns a valid UUID percent check.
     * @return Valid UUID percent check.
     */
    public ColumnValidUuidFormatPercentCheckSpec getMonthlyValidUuidFormatPercent() {
        return monthlyValidUuidFormatPercent;
    }

    /**
     * Sets a new definition of a valid UUID percent check.
     * @param monthlyValidUuidFormatPercent Valid UUID percent check.
     */
    public void setMonthlyValidUuidFormatPercent(ColumnValidUuidFormatPercentCheckSpec monthlyValidUuidFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyValidUuidFormatPercent, monthlyValidUuidFormatPercent));
        this.monthlyValidUuidFormatPercent = monthlyValidUuidFormatPercent;
        propagateHierarchyIdToField(monthlyValidUuidFormatPercent, "monthly_valid_uuid_format_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnInvalidIp4AddressFormatFoundCheckSpec getMonthlyInvalidIp4AddressFormatFound() {
        return monthlyInvalidIp4AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP4 address count check.
     * @param monthlyInvalidIp4AddressFormatFound Invalid IP4 address count check.
     */
    public void setMonthlyInvalidIp4AddressFormatFound(ColumnInvalidIp4AddressFormatFoundCheckSpec monthlyInvalidIp4AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyInvalidIp4AddressFormatFound, monthlyInvalidIp4AddressFormatFound));
        this.monthlyInvalidIp4AddressFormatFound = monthlyInvalidIp4AddressFormatFound;
        propagateHierarchyIdToField(monthlyInvalidIp4AddressFormatFound, "monthly_invalid_ip4_address_format_found");
    }

    /**
     * Returns a maximum invalid IP6 address count check.
     * @return Maximum invalid IP6 address count check.
     */
    public ColumnInvalidIp6AddressFormatFoundCheckSpec getMonthlyInvalidIp6AddressFormatFound() {
        return monthlyInvalidIp6AddressFormatFound;
    }

    /**
     * Sets a new definition of an invalid IP6 address count check.
     * @param monthlyInvalidIp6AddressFormatFound Invalid IP6 address count check.
     */
    public void setMonthlyInvalidIp6AddressFormatFound(ColumnInvalidIp6AddressFormatFoundCheckSpec monthlyInvalidIp6AddressFormatFound) {
        this.setDirtyIf(!Objects.equals(this.monthlyInvalidIp6AddressFormatFound, monthlyInvalidIp6AddressFormatFound));
        this.monthlyInvalidIp6AddressFormatFound = monthlyInvalidIp6AddressFormatFound;
        propagateHierarchyIdToField(monthlyInvalidIp6AddressFormatFound, "monthly_invalid_ip6_address_format_found");
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
    public ColumnPatternsMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnPatternsMonthlyMonitoringChecksSpec)super.deepClone();
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
