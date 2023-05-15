/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.checks.column.profiling;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.pii.*;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
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
public class ColumnProfilingPiiChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnProfilingPiiChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("valid_usa_phone_percent", o -> o.validUsaPhonePercent);
            put("contains_usa_phone_percent", o -> o.containsUsaPhonePercent);
            put("valid_usa_zipcode_percent", o -> o.validUsaZipcodePercent);
            put("contains_usa_zipcode_percent", o -> o.containsUsaZipcodePercent);
            put("valid_email_percent", o -> o.validEmailPercent);
            put("contains_email_percent", o -> o.containsEmailPercent);
            put("valid_ip4_address_percent", o -> o.validIp4AddressPercent);
            put("contains_ip4_percent", o -> o.containsIp4Percent);
            put("valid_ip6_address_percent", o -> o.validIp6AddressPercent);
            put("contains_ip6_percent", o -> o.containsIp6Percent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidUsaPhonePercentCheckSpec validUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec containsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidUsaZipcodePercentCheckSpec validUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec containsUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidEmailPercentCheckSpec validEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.")
    private ColumnPiiContainsEmailPercentCheckSpec containsEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidIp4AddressPercentCheckSpec validIp4AddressPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiContainsIp4PercentCheckSpec containsIp4Percent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiValidIp6AddressPercentCheckSpec validIp6AddressPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.")
    private ColumnPiiContainsIp6PercentCheckSpec containsIp6Percent;


    /**
     * Returns a minimum valid currency code percent check.
     * @return Minimum valid currency code percent check.
     */
    public ColumnPiiValidUsaPhonePercentCheckSpec getValidUsaPhonePercent() {
        return validUsaPhonePercent;
    }

    /**
     * Sets a new definition of a valid USA phone percent check.
     * @param validUsaPhonePercent valid USA phone percent check.
     */
    public void setValidUsaPhonePercent(ColumnPiiValidUsaPhonePercentCheckSpec validUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.validUsaPhonePercent, validUsaPhonePercent));
        this.validUsaPhonePercent = validUsaPhonePercent;
        propagateHierarchyIdToField(validUsaPhonePercent, "valid_usa_phone_percent");
    }

    /**
     * Returns contains USA phone number percent check specification.
     * @return Contains USA phone number percent check specification.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getContainsUsaPhonePercent() {
        return containsUsaPhonePercent;
    }

    /**
     * Sets a new contains USA phone number percent check specification.
     * @param containsUsaPhonePercent Contains USA phone number percent check specification.
     */
    public void setContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec containsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.containsUsaPhonePercent, containsUsaPhonePercent));
        this.containsUsaPhonePercent = containsUsaPhonePercent;
        propagateHierarchyIdToField(containsUsaPhonePercent, "contains_usa_phone_percent");
    }

    /**
     * Returns a minimum valid usa zip code percent check.
     * @return Minimum Valid usa zip code percent check.
     */
    public ColumnPiiValidUsaZipcodePercentCheckSpec getValidUsaZipcodePercent() {
        return validUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a valid usa zip code percent check.
     * @param validUsaZipcodePercent valid usa zip code percent check.
     */
    public void setValidUsaZipcodePercent(ColumnPiiValidUsaZipcodePercentCheckSpec validUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.validUsaZipcodePercent, validUsaZipcodePercent));
        this.validUsaZipcodePercent = validUsaZipcodePercent;
        propagateHierarchyIdToField(validUsaZipcodePercent, "valid_usa_zipcode_percent");
    }

    /**
     * Returns contains USA zip code percent check specification.
     * @return Contains USA zip code percent check specification.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getContainsUsaZipcodePercent() {
        return containsUsaZipcodePercent;
    }

    /**
     * Sets contains USA zip code percent check specification.
     * @param containsUsaZipcodePercent Contains USA zip code percent check specification.
     */
    public void setContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec containsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.containsUsaZipcodePercent, containsUsaZipcodePercent));
        this.containsUsaZipcodePercent = containsUsaZipcodePercent;
        propagateHierarchyIdToField(containsUsaZipcodePercent, "contains_usa_zipcode_percent");
    }

    /**
     * Returns a valid email percent check.
     * @return Valid email percent check.
     */
    public ColumnPiiValidEmailPercentCheckSpec getValidEmailPercent() {
        return validEmailPercent;
    }

    /**
     * Sets a new definition of a valid email percent check.
     * @param validEmailPercent Valid email percent check.
     */
    public void setValidEmailPercent(ColumnPiiValidEmailPercentCheckSpec validEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.validEmailPercent, validEmailPercent));
        this.validEmailPercent = validEmailPercent;
        propagateHierarchyIdToField(validEmailPercent, "valid_email_percent");
    }

    /**
     * Returns a contains email percent check.
     * @return Contains email percent check.
     */
    public ColumnPiiContainsEmailPercentCheckSpec getContainsEmailPercent() {
        return containsEmailPercent;
    }

    /**
     * Sets a new definition of a contains email percent check.
     * @param containsEmailPercent Contains email percent check.
     */
    public void setContainsEmailPercent(ColumnPiiContainsEmailPercentCheckSpec containsEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.containsEmailPercent, containsEmailPercent));
        this.containsEmailPercent = containsEmailPercent;
        propagateHierarchyIdToField(containsEmailPercent, "contains_email_percent");
    }

    /**
     * Returns a minimum valid IP4 address percent check.
     * @return Minimum valid IP4 address percent check.
     */
    public ColumnPiiValidIp4AddressPercentCheckSpec getValidIp4AddressPercent() {
        return validIp4AddressPercent;
    }

    /**
     * Sets a new definition of a valid IP4 address percent check.
     * @param validIp4AddressPercent valid IP4 address percent check.
     */
    public void setValidIp4AddressPercent(ColumnPiiValidIp4AddressPercentCheckSpec validIp4AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.validIp4AddressPercent, validIp4AddressPercent));
        this.validIp4AddressPercent = validIp4AddressPercent;
        propagateHierarchyIdToField(validIp4AddressPercent, "ip4_address_percent");
    }

    /**
     * Returns a contains IP4 percent check.
     * @return Contains IP4 percent check.
     */
    public ColumnPiiContainsIp4PercentCheckSpec getContainsIp4Percent() {
        return containsIp4Percent;
    }

    /**
     * Sets a new definition of a contains IP4 percent check.
     * @param containsIp4Percent Contains IP4 percent check.
     */
    public void setContainsIp4Percent(ColumnPiiContainsIp4PercentCheckSpec containsIp4Percent) {
        this.setDirtyIf(!Objects.equals(this.containsIp4Percent, containsIp4Percent));
        this.containsIp4Percent = containsIp4Percent;
        propagateHierarchyIdToField(containsIp4Percent, "contains_ip4_percent");
    }

    /**
     * Returns a minimum valid IP6 address percent check.
     * @return Minimum valid IP6 address percent check.
     */
    public ColumnPiiValidIp6AddressPercentCheckSpec getValidIp6AddressPercent() {
        return validIp6AddressPercent;
    }

    /**
     * Sets a new definition of a valid IP6 address percent check.
     * @param validIp6AddressPercent valid IP6 address percent check.
     */
    public void setValidIp6AddressPercent(ColumnPiiValidIp6AddressPercentCheckSpec validIp6AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.validIp6AddressPercent, validIp6AddressPercent));
        this.validIp6AddressPercent = validIp6AddressPercent;
        propagateHierarchyIdToField(validIp6AddressPercent, "valid_ip6_address_percent");
    }

    /**
     * Returns a contains IP6 percent check.
     * @return Contains IP6 percent check.
     */
    public ColumnPiiContainsIp6PercentCheckSpec getContainsIp6Percent() {
        return containsIp6Percent;
    }

    /**
     * Sets a new definition of a contains IP6 percent check.
     * @param containsIp6Percent Contains IP6 percent check.
     */
    public void setContainsIp6Percent(ColumnPiiContainsIp6PercentCheckSpec containsIp6Percent) {
        this.setDirtyIf(!Objects.equals(this.containsIp6Percent, containsIp6Percent));
        this.containsIp6Percent = containsIp6Percent;
        propagateHierarchyIdToField(containsIp6Percent, "contains_ip6_percent");
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
}