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
 * Container of built-in preconfigured monthly partition checks on a column level that are checking for values matching patterns (regular expressions) in text columns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPatternsMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPatternsMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_text_not_matching_regex_found", o -> o.monthlyPartitionTextNotMatchingRegexFound);
            put("monthly_partition_texts_matching_regex_percent", o -> o.monthlyPartitionTextsMatchingRegexPercent);
            put("monthly_partition_invalid_email_format_found", o -> o.monthlyPartitionInvalidEmailFormatFound);
            put("monthly_partition_text_not_matching_date_pattern_found", o -> o.monthlyPartitionTextNotMatchingDatePatternFound);
            put("monthly_partition_text_matching_date_pattern_percent", o -> o.monthlyPartitionTextMatchingDatePatternPercent);
            put("monthly_partition_text_matching_name_pattern_percent", o -> o.monthlyPartitionTextMatchingNamePatternPercent);

            put("monthly_partition_invalid_uuid_format_found", o -> o.monthlyPartitionInvalidUuidFormatFound);
            put("monthly_partition_valid_uuid_format_percent", o -> o.monthlyPartitionValidUuidFormatPercent);
            put("monthly_partition_invalid_ip4_address_format_found", o -> o.monthlyPartitionInvalidIp4AddressFormatFound);
            put("monthly_partition_invalid_ip6_address_format_found", o -> o.monthlyPartitionInvalidIp6AddressFormatFound);
        }
    };

    @JsonPropertyDescription("Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingRegexFoundCheckSpec monthlyPartitionTextNotMatchingRegexFound;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.")
    private ColumnTextsMatchingRegexPercentCheckSpec monthlyPartitionTextsMatchingRegexPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidEmailFormatFoundCheckSpec monthlyPartitionInvalidEmailFormatFound;

    @JsonPropertyDescription("Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingDatePatternFoundCheckSpec monthlyPartitionTextNotMatchingDatePatternFound;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingDatePatternPercentCheckSpec monthlyPartitionTextMatchingDatePatternPercent;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingNamePatternPercentCheckSpec monthlyPartitionTextMatchingNamePatternPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUuidFormatFoundCheckSpec monthlyPartitionInvalidUuidFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.")
    private ColumnValidUuidFormatPercentCheckSpec monthlyPartitionValidUuidFormatPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp4AddressFormatFoundCheckSpec monthlyPartitionInvalidIp4AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp6AddressFormatFoundCheckSpec monthlyPartitionInvalidIp6AddressFormatFound;

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
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnTextsMatchingRegexPercentCheckSpec getMonthlyPartitionTextsMatchingRegexPercent() {
        return monthlyPartitionTextsMatchingRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param monthlyPartitionTextsMatchingRegexPercent Minimum match regex percent check.
     */
    public void setMonthlyPartitionTextsMatchingRegexPercent(ColumnTextsMatchingRegexPercentCheckSpec monthlyPartitionTextsMatchingRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextsMatchingRegexPercent, monthlyPartitionTextsMatchingRegexPercent));
        this.monthlyPartitionTextsMatchingRegexPercent = monthlyPartitionTextsMatchingRegexPercent;
        propagateHierarchyIdToField(monthlyPartitionTextsMatchingRegexPercent, "monthly_partition_texts_matching_regex_percent");
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
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnTextMatchingDatePatternPercentCheckSpec getMonthlyPartitionTextMatchingDatePatternPercent() {
        return monthlyPartitionTextMatchingDatePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param monthlyPartitionTextMatchingDatePatternPercent Maximum match date regex percent check.
     */
    public void setMonthlyPartitionTextMatchingDatePatternPercent(ColumnTextMatchingDatePatternPercentCheckSpec monthlyPartitionTextMatchingDatePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextMatchingDatePatternPercent, monthlyPartitionTextMatchingDatePatternPercent));
        this.monthlyPartitionTextMatchingDatePatternPercent = monthlyPartitionTextMatchingDatePatternPercent;
        propagateHierarchyIdToField(monthlyPartitionTextMatchingDatePatternPercent, "monthly_partition_text_matching_date_pattern_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnTextMatchingNamePatternPercentCheckSpec getMonthlyPartitionTextMatchingNamePatternPercent() {
        return monthlyPartitionTextMatchingNamePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param monthlyPartitionTextMatchingNamePatternPercent Maximum match name regex percent check.
     */
    public void setMonthlyPartitionTextMatchingNamePatternPercent(ColumnTextMatchingNamePatternPercentCheckSpec monthlyPartitionTextMatchingNamePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionTextMatchingNamePatternPercent, monthlyPartitionTextMatchingNamePatternPercent));
        this.monthlyPartitionTextMatchingNamePatternPercent = monthlyPartitionTextMatchingNamePatternPercent;
        propagateHierarchyIdToField(monthlyPartitionTextMatchingNamePatternPercent, "monthly_partition_text_matching_name_pattern_percent");
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
     * Returns a valid UUID percent check.
     * @return Valid UUID percent check.
     */
    public ColumnValidUuidFormatPercentCheckSpec getMonthlyPartitionValidUuidFormatPercent() {
        return monthlyPartitionValidUuidFormatPercent;
    }

    /**
     * Sets a new definition of a valid UUID percent check.
     * @param monthlyPartitionValidUuidFormatPercent Valid UUID percent check.
     */
    public void setMonthlyPartitionValidUuidFormatPercent(ColumnValidUuidFormatPercentCheckSpec monthlyPartitionValidUuidFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValidUuidFormatPercent, monthlyPartitionValidUuidFormatPercent));
        this.monthlyPartitionValidUuidFormatPercent = monthlyPartitionValidUuidFormatPercent;
        propagateHierarchyIdToField(monthlyPartitionValidUuidFormatPercent, "monthly_partition_valid_uuid_format_percent");
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
