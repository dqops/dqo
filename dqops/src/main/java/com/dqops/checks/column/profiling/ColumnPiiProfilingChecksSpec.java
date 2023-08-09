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
import com.dqops.checks.column.checkspecs.pii.*;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
            put("profile_valid_usa_phone_percent", o -> o.profileValidUsaPhonePercent);
            put("profile_contains_usa_phone_percent", o -> o.profileContainsUsaPhonePercent);
            put("profile_valid_usa_zipcode_percent", o -> o.profileValidUsaZipcodePercent);
            put("profile_contains_usa_zipcode_percent", o -> o.profileContainsUsaZipcodePercent);
            put("profile_valid_email_percent", o -> o.profileValidEmailPercent);
            put("profile_contains_email_percent", o -> o.profileContainsEmailPercent);
            put("profile_valid_ip4_address_percent", o -> o.profileValidIp4AddressPercent);
            put("profile_contains_ip4_percent", o -> o.profileContainsIp4Percent);
            put("profile_valid_ip6_address_percent", o -> o.profileValidIp6AddressPercent);
            put("profile_contains_ip6_percent", o -> o.profileContainsIp6Percent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidUsaPhonePercentCheckSpec profileValidUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec profileContainsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidUsaZipcodePercentCheckSpec profileValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec profileContainsUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidEmailPercentCheckSpec profileValidEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.")
    private ColumnPiiContainsEmailPercentCheckSpec profileContainsEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidIp4AddressPercentCheckSpec profileValidIp4AddressPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiContainsIp4PercentCheckSpec profileContainsIp4Percent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidIp6AddressPercentCheckSpec profileValidIp6AddressPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiContainsIp6PercentCheckSpec profileContainsIp6Percent;


    /**
     * Returns a minimum valid currency code percent check.
     * @return Minimum valid currency code percent check.
     */
    public ColumnPiiValidUsaPhonePercentCheckSpec getProfileValidUsaPhonePercent() {
        return profileValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a valid USA phone percent check.
     * @param profileValidUsaPhonePercent valid USA phone percent check.
     */
    public void setProfileValidUsaPhonePercent(ColumnPiiValidUsaPhonePercentCheckSpec profileValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.profileValidUsaPhonePercent, profileValidUsaPhonePercent));
        this.profileValidUsaPhonePercent = profileValidUsaPhonePercent;
        propagateHierarchyIdToField(profileValidUsaPhonePercent, "profile_valid_usa_phone_percent");
    }

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
     * Returns a minimum valid usa zip code percent check.
     * @return Minimum Valid usa zip code percent check.
     */
    public ColumnPiiValidUsaZipcodePercentCheckSpec getProfileValidUsaZipcodePercent() {
        return profileValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a valid usa zip code percent check.
     * @param profileValidUsaZipcodePercent valid usa zip code percent check.
     */
    public void setProfileValidUsaZipcodePercent(ColumnPiiValidUsaZipcodePercentCheckSpec profileValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.profileValidUsaZipcodePercent, profileValidUsaZipcodePercent));
        this.profileValidUsaZipcodePercent = profileValidUsaZipcodePercent;
        propagateHierarchyIdToField(profileValidUsaZipcodePercent, "profile_valid_usa_zipcode_percent");
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
     * Returns a valid email percent check.
     * @return Valid email percent check.
     */
    public ColumnPiiValidEmailPercentCheckSpec getProfileValidEmailPercent() {
        return profileValidEmailPercent;
    }

    /**
     * Sets a new definition of a valid email percent check.
     * @param profileValidEmailPercent Valid email percent check.
     */
    public void setProfileValidEmailPercent(ColumnPiiValidEmailPercentCheckSpec profileValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.profileValidEmailPercent, profileValidEmailPercent));
        this.profileValidEmailPercent = profileValidEmailPercent;
        propagateHierarchyIdToField(profileValidEmailPercent, "profile_valid_email_percent");
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
     * Returns a minimum valid IP4 address percent check.
     * @return Minimum valid IP4 address percent check.
     */
    public ColumnPiiValidIp4AddressPercentCheckSpec getProfileValidIp4AddressPercent() {
        return profileValidIp4AddressPercent;
    }

    /**
     * Sets a new definition of a valid IP4 address percent check.
     * @param profileValidIp4AddressPercent valid IP4 address percent check.
     */
    public void setProfileValidIp4AddressPercent(ColumnPiiValidIp4AddressPercentCheckSpec profileValidIp4AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.profileValidIp4AddressPercent, profileValidIp4AddressPercent));
        this.profileValidIp4AddressPercent = profileValidIp4AddressPercent;
        propagateHierarchyIdToField(profileValidIp4AddressPercent, "ip4_address_percent");
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
     * Returns a minimum valid IP6 address percent check.
     * @return Minimum valid IP6 address percent check.
     */
    public ColumnPiiValidIp6AddressPercentCheckSpec getProfileValidIp6AddressPercent() {
        return profileValidIp6AddressPercent;
    }

    /**
     * Sets a new definition of a valid IP6 address percent check.
     * @param profileValidIp6AddressPercent valid IP6 address percent check.
     */
    public void setProfileValidIp6AddressPercent(ColumnPiiValidIp6AddressPercentCheckSpec profileValidIp6AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.profileValidIp6AddressPercent, profileValidIp6AddressPercent));
        this.profileValidIp6AddressPercent = profileValidIp6AddressPercent;
        propagateHierarchyIdToField(profileValidIp6AddressPercent, "profile_valid_ip6_address_percent");
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
}