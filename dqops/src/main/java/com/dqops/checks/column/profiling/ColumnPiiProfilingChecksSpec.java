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
import com.dqops.checks.column.checkspecs.pii.*;
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
 * Container of built-in preconfigured data quality checks on a column level that are checking for Personal Identifiable Information (PII).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiProfilingChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiProfilingChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("profile_contains_usa_phone_percent", o -> o.profileContainsUsaPhonePercent);
            put("profile_contains_email_percent", o -> o.profileContainsEmailPercent);
            put("profile_contains_usa_zipcode_percent", o -> o.profileContainsUsaZipcodePercent);
            put("profile_contains_ip4_percent", o -> o.profileContainsIp4Percent);
            put("profile_contains_ip6_percent", o -> o.profileContainsIp6Percent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec profileContainsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.")
    private ColumnPiiContainsEmailPercentCheckSpec profileContainsEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec profileContainsUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiContainsIp4PercentCheckSpec profileContainsIp4Percent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiContainsIp6PercentCheckSpec profileContainsIp6Percent;


    /**
     * Returns contains USA phone number percent check specification.
     * @return Contains USA phone number percent check specification.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getProfileContainsUsaPhonePercent() {
        return profileContainsUsaPhonePercent;
    }

    /**
     * Sets a new contains USA phone number percent check specification.
     * @param profileContainsUsaPhonePercent Contains USA phone number percent check specification.
     */
    public void setProfileContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec profileContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.profileContainsUsaPhonePercent, profileContainsUsaPhonePercent));
        this.profileContainsUsaPhonePercent = profileContainsUsaPhonePercent;
        propagateHierarchyIdToField(profileContainsUsaPhonePercent, "profile_contains_usa_phone_percent");
    }

    /**
     * Returns a contains email percent check.
     * @return Contains email percent check.
     */
    public ColumnPiiContainsEmailPercentCheckSpec getProfileContainsEmailPercent() {
        return profileContainsEmailPercent;
    }

    /**
     * Sets a new definition of a contains email percent check.
     * @param profileContainsEmailPercent Contains email percent check.
     */
    public void setProfileContainsEmailPercent(ColumnPiiContainsEmailPercentCheckSpec profileContainsEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.profileContainsEmailPercent, profileContainsEmailPercent));
        this.profileContainsEmailPercent = profileContainsEmailPercent;
        propagateHierarchyIdToField(profileContainsEmailPercent, "profile_contains_email_percent");
    }

    /**
     * Returns contains USA zip code percent check specification.
     * @return Contains USA zip code percent check specification.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getProfileContainsUsaZipcodePercent() {
        return profileContainsUsaZipcodePercent;
    }

    /**
     * Sets contains USA zip code percent check specification.
     * @param profileContainsUsaZipcodePercent Contains USA zip code percent check specification.
     */
    public void setProfileContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec profileContainsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileContainsUsaZipcodePercent, profileContainsUsaZipcodePercent));
        this.profileContainsUsaZipcodePercent = profileContainsUsaZipcodePercent;
        propagateHierarchyIdToField(profileContainsUsaZipcodePercent, "profile_contains_usa_zipcode_percent");
    }

    /**
     * Returns a contains IP4 percent check.
     * @return Contains IP4 percent check.
     */
    public ColumnPiiContainsIp4PercentCheckSpec getProfileContainsIp4Percent() {
        return profileContainsIp4Percent;
    }

    /**
     * Sets a new definition of a contains IP4 percent check.
     * @param profileContainsIp4Percent Contains IP4 percent check.
     */
    public void setProfileContainsIp4Percent(ColumnPiiContainsIp4PercentCheckSpec profileContainsIp4Percent) {
        this.setDirtyIf(!Objects.equals(this.profileContainsIp4Percent, profileContainsIp4Percent));
        this.profileContainsIp4Percent = profileContainsIp4Percent;
        propagateHierarchyIdToField(profileContainsIp4Percent, "profile_contains_ip4_percent");
    }

    /**
     * Returns a contains IP6 percent check.
     * @return Contains IP6 percent check.
     */
    public ColumnPiiContainsIp6PercentCheckSpec getProfileContainsIp6Percent() {
        return profileContainsIp6Percent;
    }

    /**
     * Sets a new definition of a contains IP6 percent check.
     * @param profileContainsIp6Percent Contains IP6 percent check.
     */
    public void setProfileContainsIp6Percent(ColumnPiiContainsIp6PercentCheckSpec profileContainsIp6Percent) {
        this.setDirtyIf(!Objects.equals(this.profileContainsIp6Percent, profileContainsIp6Percent));
        this.profileContainsIp6Percent = profileContainsIp6Percent;
        propagateHierarchyIdToField(profileContainsIp6Percent, "profile_contains_ip6_percent");
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
    public ColumnPiiProfilingChecksSpec deepClone() {
        return (ColumnPiiProfilingChecksSpec)super.deepClone();
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