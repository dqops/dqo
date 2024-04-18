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
            put("daily_partition_texts_matching_regex_percent", o -> o.dailyPartitionTextsMatchingRegexPercent);
            put("daily_partition_invalid_email_format_found", o -> o.dailyPartitionInvalidEmailFormatFound);
            put("daily_partition_invalid_email_format_percent", o -> o.dailyPartitionInvalidEmailFormatPercent);
            put("daily_partition_text_not_matching_date_pattern_found", o -> o.dailyPartitionTextNotMatchingDatePatternFound);
            put("daily_partition_text_matching_date_pattern_percent", o -> o.dailyPartitionTextMatchingDatePatternPercent);
            put("daily_partition_text_matching_name_pattern_percent", o -> o.dailyPartitionTextMatchingNamePatternPercent);

            put("daily_partition_invalid_uuid_format_found", o -> o.dailyPartitionInvalidUuidFormatFound);
            put("daily_partition_valid_uuid_format_percent", o -> o.dailyPartitionValidUuidFormatPercent);
            put("daily_partition_invalid_ip4_address_format_found", o -> o.dailyPartitionInvalidIp4AddressFormatFound);
            put("daily_partition_invalid_ip6_address_format_found", o -> o.dailyPartitionInvalidIp6AddressFormatFound);
        }
    };

    @JsonPropertyDescription("Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingRegexFoundCheckSpec dailyPartitionTextNotMatchingRegexFound;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.")
    private ColumnTextsMatchingRegexPercentCheckSpec dailyPartitionTextsMatchingRegexPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidEmailFormatFoundCheckSpec dailyPartitionInvalidEmailFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid emails in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidEmailFormatPercentCheckSpec dailyPartitionInvalidEmailFormatPercent;

    @JsonPropertyDescription("Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingDatePatternFoundCheckSpec dailyPartitionTextNotMatchingDatePatternFound;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingDatePatternPercentCheckSpec dailyPartitionTextMatchingDatePatternPercent;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingNamePatternPercentCheckSpec dailyPartitionTextMatchingNamePatternPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUuidFormatFoundCheckSpec dailyPartitionInvalidUuidFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.")
    private ColumnValidUuidFormatPercentCheckSpec dailyPartitionValidUuidFormatPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp4AddressFormatFoundCheckSpec dailyPartitionInvalidIp4AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp6AddressFormatFoundCheckSpec dailyPartitionInvalidIp6AddressFormatFound;

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
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnTextsMatchingRegexPercentCheckSpec getDailyPartitionTextsMatchingRegexPercent() {
        return dailyPartitionTextsMatchingRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param dailyPartitionTextsMatchingRegexPercent Minimum match regex percent check.
     */
    public void setDailyPartitionTextsMatchingRegexPercent(ColumnTextsMatchingRegexPercentCheckSpec dailyPartitionTextsMatchingRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextsMatchingRegexPercent, dailyPartitionTextsMatchingRegexPercent));
        this.dailyPartitionTextsMatchingRegexPercent = dailyPartitionTextsMatchingRegexPercent;
        propagateHierarchyIdToField(dailyPartitionTextsMatchingRegexPercent, "daily_partition_texts_matching_regex_percent");
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
    public void setDailyPartitionInvalidEmailFormatFound(ColumnInvalidEmailFormatPercentCheckSpec dailyPartitionInvalidEmailFormatPercent) {
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
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnTextMatchingDatePatternPercentCheckSpec getDailyPartitionTextMatchingDatePatternPercent() {
        return dailyPartitionTextMatchingDatePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param dailyPartitionTextMatchingDatePatternPercent Maximum match date regex percent check.
     */
    public void setDailyPartitionTextMatchingDatePatternPercent(ColumnTextMatchingDatePatternPercentCheckSpec dailyPartitionTextMatchingDatePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMatchingDatePatternPercent, dailyPartitionTextMatchingDatePatternPercent));
        this.dailyPartitionTextMatchingDatePatternPercent = dailyPartitionTextMatchingDatePatternPercent;
        propagateHierarchyIdToField(dailyPartitionTextMatchingDatePatternPercent, "daily_partition_text_matching_date_pattern_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnTextMatchingNamePatternPercentCheckSpec getDailyPartitionTextMatchingNamePatternPercent() {
        return dailyPartitionTextMatchingNamePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param dailyPartitionTextMatchingNamePatternPercent Maximum match name regex percent check.
     */
    public void setDailyPartitionTextMatchingNamePatternPercent(ColumnTextMatchingNamePatternPercentCheckSpec dailyPartitionTextMatchingNamePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionTextMatchingNamePatternPercent, dailyPartitionTextMatchingNamePatternPercent));
        this.dailyPartitionTextMatchingNamePatternPercent = dailyPartitionTextMatchingNamePatternPercent;
        propagateHierarchyIdToField(dailyPartitionTextMatchingNamePatternPercent, "daily_partition_text_matching_name_pattern_percent");
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
     * Returns a valid UUID percent check.
     * @return Valid UUID percent check.
     */
    public ColumnValidUuidFormatPercentCheckSpec getDailyPartitionValidUuidFormatPercent() {
        return dailyPartitionValidUuidFormatPercent;
    }

    /**
     * Sets a new definition of a valid UUID percent check.
     * @param dailyPartitionValidUuidFormatPercent Valid UUID percent check.
     */
    public void setDailyPartitionValidUuidFormatPercent(ColumnValidUuidFormatPercentCheckSpec dailyPartitionValidUuidFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValidUuidFormatPercent, dailyPartitionValidUuidFormatPercent));
        this.dailyPartitionValidUuidFormatPercent = dailyPartitionValidUuidFormatPercent;
        propagateHierarchyIdToField(dailyPartitionValidUuidFormatPercent, "daily_partition_valid_uuid_format_percent");
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
