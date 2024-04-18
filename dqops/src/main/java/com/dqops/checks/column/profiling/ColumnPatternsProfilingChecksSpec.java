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
            put("profile_texts_matching_regex_percent", o -> o.profileTextsMatchingRegexPercent);
            put("profile_invalid_email_format_found", o -> o.profileInvalidEmailFormatFound);
            put("profile_invalid_email_format_percent", o -> o.profileInvalidEmailFormatPercent);
            put("profile_text_not_matching_date_pattern_found", o -> o.profileTextNotMatchingDatePatternFound);
            put("profile_text_matching_date_pattern_percent", o -> o.profileTextMatchingDatePatternPercent);
            put("profile_text_matching_name_pattern_percent", o -> o.profileTextMatchingNamePatternPercent);

            put("profile_invalid_uuid_format_found", o -> o.profileInvalidUuidFormatFound);
            put("profile_valid_uuid_format_percent", o -> o.profileValidUuidFormatPercent);
            put("profile_invalid_ip4_address_format_found", o -> o.profileInvalidIp4AddressFormatFound);
            put("profile_invalid_ip6_address_format_found", o -> o.profileInvalidIp6AddressFormatFound);
        }
    };

    @JsonPropertyDescription("Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingRegexFoundCheckSpec profileTextNotMatchingRegexFound;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.")
    private ColumnTextsMatchingRegexPercentCheckSpec profileTextsMatchingRegexPercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidEmailFormatFoundCheckSpec profileInvalidEmailFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of invalid emails in a text column does not exceed the maximum accepted percentage.")
    private ColumnInvalidEmailFormatPercentCheckSpec profileInvalidEmailFormatPercent;

    @JsonPropertyDescription("Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.")
    private ColumnTextNotMatchingDatePatternFoundCheckSpec profileTextNotMatchingDatePatternFound;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingDatePatternPercentCheckSpec profileTextMatchingDatePatternPercent;

    @JsonPropertyDescription("Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.")
    private ColumnTextMatchingNamePatternPercentCheckSpec profileTextMatchingNamePatternPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidUuidFormatFoundCheckSpec profileInvalidUuidFormatFound;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.")
    private ColumnValidUuidFormatPercentCheckSpec profileValidUuidFormatPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp4AddressFormatFoundCheckSpec profileInvalidIp4AddressFormatFound;

    @JsonPropertyDescription("Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.")
    private ColumnInvalidIp6AddressFormatFoundCheckSpec profileInvalidIp6AddressFormatFound;

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
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnTextsMatchingRegexPercentCheckSpec getProfileTextsMatchingRegexPercent() {
        return profileTextsMatchingRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param profileTextsMatchingRegexPercent Minimum match regex percent check.
     */
    public void setProfileTextsMatchingRegexPercent(ColumnTextsMatchingRegexPercentCheckSpec profileTextsMatchingRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextsMatchingRegexPercent, profileTextsMatchingRegexPercent));
        this.profileTextsMatchingRegexPercent = profileTextsMatchingRegexPercent;
        propagateHierarchyIdToField(profileTextsMatchingRegexPercent, "profile_texts_matching_regex_percent");
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
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnTextMatchingDatePatternPercentCheckSpec getProfileTextMatchingDatePatternPercent() {
        return profileTextMatchingDatePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param profileTextMatchingDatePatternPercent Maximum match date regex percent check.
     */
    public void setProfileTextMatchingDatePatternPercent(ColumnTextMatchingDatePatternPercentCheckSpec profileTextMatchingDatePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextMatchingDatePatternPercent, profileTextMatchingDatePatternPercent));
        this.profileTextMatchingDatePatternPercent = profileTextMatchingDatePatternPercent;
        propagateHierarchyIdToField(profileTextMatchingDatePatternPercent, "profile_text_matching_date_pattern_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnTextMatchingNamePatternPercentCheckSpec getProfileTextMatchingNamePatternPercent() {
        return profileTextMatchingNamePatternPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param profileTextMatchingNamePatternPercent Maximum match name regex percent check.
     */
    public void setProfileTextMatchingNamePatternPercent(ColumnTextMatchingNamePatternPercentCheckSpec profileTextMatchingNamePatternPercent) {
        this.setDirtyIf(!Objects.equals(this.profileTextMatchingNamePatternPercent, profileTextMatchingNamePatternPercent));
        this.profileTextMatchingNamePatternPercent = profileTextMatchingNamePatternPercent;
        propagateHierarchyIdToField(profileTextMatchingNamePatternPercent, "profile_text_matching_name_pattern_percent");
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
     * Returns a valid UUID percent check.
     * @return Valid UUID percent check.
     */
    public ColumnValidUuidFormatPercentCheckSpec getProfileValidUuidFormatPercent() {
        return profileValidUuidFormatPercent;
    }

    /**
     * Sets a new definition of a valid UUID percent check.
     * @param profileValidUuidFormatPercent Valid UUID percent check.
     */
    public void setProfileValidUuidFormatPercent(ColumnValidUuidFormatPercentCheckSpec profileValidUuidFormatPercent) {
        this.setDirtyIf(!Objects.equals(this.profileValidUuidFormatPercent, profileValidUuidFormatPercent));
        this.profileValidUuidFormatPercent = profileValidUuidFormatPercent;
        propagateHierarchyIdToField(profileValidUuidFormatPercent, "profile_valid_uuid_format_percent");
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
