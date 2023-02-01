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
package ai.dqo.checks.column.checkpoints.pii;

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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiMonthlyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiMonthlyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_checkpoint_valid_usa_phone_percent", o -> o.monthlyCheckpointValidUsaPhonePercent);
            put("monthly_checkpoint_contains_usa_phone_percent", o -> o.monthlyCheckpointContainsUsaPhonePercent);
            put("monthly_checkpoint_valid_usa_zipcode_percent", o -> o.monthlyCheckpointValidUsaZipcodePercent);
            put("monthly_checkpoint_contains_usa_zipcode_percent", o -> o.monthlyCheckpointContainsUsaZipcodePercent);
            put("monthly_checkpoint_valid_email_percent", o -> o.monthlyCheckpointValidEmailPercent);
            put("monthly_checkpoint_valid_ip4_address_percent", o -> o.monthlyCheckpointValidIp4AddressPercent);
            put("monthly_checkpoint_valid_ip6_address_percent", o -> o.monthlyCheckpointValidIp6AddressPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPiiValidUsaPhonePercentCheckSpec monthlyCheckpointValidUsaPhonePercent;
    
    @JsonPropertyDescription("Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec monthlyCheckpointContainsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPiiValidUsaZipcodePercentCheckSpec monthlyCheckpointValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec monthlyCheckpointContainsUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPiiValidEmailPercentCheckSpec monthlyCheckpointValidEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP4 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPiiValidIp4AddressPercentCheckSpec monthlyCheckpointValidIp4AddressPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP6 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.")
    private ColumnPiiValidIp6AddressPercentCheckSpec monthlyCheckpointValidIp6AddressPercent;

    /**
     * Returns contains USA phone number percent check specification.
     * @return Contains USA phone number percent check specification.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getMonthlyCheckpointContainsUsaPhonePercent() {
        return monthlyCheckpointContainsUsaPhonePercent;
    }

    /**
     * Returns a minimum valid USA phone percent check.
     * @return Minimum valid USA phone percent check.
     */
    public ColumnPiiValidUsaPhonePercentCheckSpec getMonthlyCheckpointValidUsaPhonePercent() {
        return monthlyCheckpointValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum valid USA phone percent check.
     * @param monthlyCheckpointValidUsaPhonePercent Minimum valid USA phone percent check.
     */
    public void setMonthlyCheckpointValidUsaPhonePercent(ColumnPiiValidUsaPhonePercentCheckSpec monthlyCheckpointValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointValidUsaPhonePercent, monthlyCheckpointValidUsaPhonePercent));
        this.monthlyCheckpointValidUsaPhonePercent = monthlyCheckpointValidUsaPhonePercent;
        propagateHierarchyIdToField(monthlyCheckpointValidUsaPhonePercent, "monthly_checkpoint_valid_usa_phone_percent");
    }
    
    /**
     * Sets a new definition of contains USA phone number percent check.
     * @param monthlyCheckpointContainsUsaPhonePercent Contains USA phone number percent check specification.
     */
    public void setMonthlyCheckpointContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec monthlyCheckpointContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointContainsUsaPhonePercent, monthlyCheckpointContainsUsaPhonePercent));
        this.monthlyCheckpointContainsUsaPhonePercent = monthlyCheckpointContainsUsaPhonePercent;
        propagateHierarchyIdToField(monthlyCheckpointContainsUsaPhonePercent, "monthly_checkpoint_contains_usa_phone_percent");
    }

    /**
     * Returns a minimum valid usa zip code percent check.
     * @return Minimum valid usa zip code percent check.
     */
    public ColumnPiiValidUsaZipcodePercentCheckSpec getMonthlyCheckpointValidUsaZipcodePercent() {
        return monthlyCheckpointValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum valid usa zip code percent check.
     * @param monthlyCheckpointValidUsaZipcodePercent Minimum valid usa zip code percent check.
     */
    public void setMonthlyCheckpointValidUsaZipcodePercent(ColumnPiiValidUsaZipcodePercentCheckSpec monthlyCheckpointValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointValidUsaZipcodePercent, monthlyCheckpointValidUsaZipcodePercent));
        this.monthlyCheckpointValidUsaZipcodePercent = monthlyCheckpointValidUsaZipcodePercent;
        propagateHierarchyIdToField(monthlyCheckpointValidUsaZipcodePercent, "monthly_checkpoint_valid_usa_zipcode_percent");
    }

    /**
     * Returns contains USA zip code percent check specification.
     * @return Contains USA zip code percent check specification.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getMonthlyCheckpointContainsUsaZipcodePercent() {
        return monthlyCheckpointContainsUsaZipcodePercent;
    }

    /**
     * Sets a new definition of contains USA zip code percent check.
     * @param monthlyCheckpointContainsUsaZipcodePercent Contains USA zip code percent check specification.
     */
    public void setMonthlyCheckpointContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec monthlyCheckpointContainsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointContainsUsaZipcodePercent, monthlyCheckpointContainsUsaZipcodePercent));
        this.monthlyCheckpointContainsUsaZipcodePercent = monthlyCheckpointContainsUsaZipcodePercent;
        propagateHierarchyIdToField(monthlyCheckpointContainsUsaZipcodePercent, "monthly_checkpoint_contains_usa_zipcode_percent");
    }

    /**
     * Returns a minimum valid email percent check.
     * @return Minimum valid email percent check.
     */
    public ColumnPiiValidEmailPercentCheckSpec getMonthlyCheckpointValidEmailPercent() {
        return monthlyCheckpointValidEmailPercent;
    }

    /**
     * Sets a new definition of a minimum valid email percent check.
     * @param monthlyCheckpointValidEmailPercent Minimum valid email percent check.
     */
    public void setMonthlyCheckpointValidEmailPercent(ColumnPiiValidEmailPercentCheckSpec monthlyCheckpointValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointValidEmailPercent, monthlyCheckpointValidEmailPercent));
        this.monthlyCheckpointValidEmailPercent = monthlyCheckpointValidEmailPercent;
        propagateHierarchyIdToField(monthlyCheckpointValidEmailPercent, "monthly_checkpoint_valid_email_percent");
    }

    /**
     * Returns a minimum valid IP4 address percent check.
     * @return Minimum valid IP4 address percent check.
     */
    public ColumnPiiValidIp4AddressPercentCheckSpec getMonthlyCheckpointValidIp4AddressPercent() {
        return monthlyCheckpointValidIp4AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP4 address percent check.
     * @param monthlyCheckpointValidIp4AddressPercent Minimum valid IP4 address percent check.
     */
    public void setMonthlyCheckpointValidIp4AddressPercent(ColumnPiiValidIp4AddressPercentCheckSpec monthlyCheckpointValidIp4AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointValidIp4AddressPercent, monthlyCheckpointValidIp4AddressPercent));
        this.monthlyCheckpointValidIp4AddressPercent = monthlyCheckpointValidIp4AddressPercent;
        propagateHierarchyIdToField(monthlyCheckpointValidIp4AddressPercent, "monthly_checkpoint_valid_ip4_address_percent");
    }

    /**
     * Returns a minimum valid IP6 address percent check.
     * @return Minimum valid IP6 address percent check.
     */
    public ColumnPiiValidIp6AddressPercentCheckSpec getMonthlyCheckpointValidIp6AddressPercent() {
        return monthlyCheckpointValidIp6AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP6 address percent check.
     * @param monthlyCheckpointValidIp6AddressPercent Minimum valid IP6 address percent check.
     */
    public void setMonthlyCheckpointValidIp6AddressPercent(ColumnPiiValidIp6AddressPercentCheckSpec monthlyCheckpointValidIp6AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyCheckpointValidIp6AddressPercent, monthlyCheckpointValidIp6AddressPercent));
        this.monthlyCheckpointValidIp6AddressPercent = monthlyCheckpointValidIp6AddressPercent;
        propagateHierarchyIdToField(monthlyCheckpointValidIp6AddressPercent, "monthly_checkpoint_valid_ip6_address_percent");
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