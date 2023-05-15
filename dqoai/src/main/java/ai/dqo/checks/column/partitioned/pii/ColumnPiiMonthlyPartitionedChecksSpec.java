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
package ai.dqo.checks.column.partitioned.pii;

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
 * Container of PII data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_valid_usa_phone_percent", o -> o.monthlyPartitionValidUsaPhonePercent);
            put("monthly_partition_contains_usa_phone_percent", o -> o.monthlyPartitionContainsUsaPhonePercent);
            put("monthly_partition_valid_usa_zipcode_percent", o -> o.monthlyPartitionValidUsaZipcodePercent);
            put("monthly_partition_contains_usa_zipcode_percent", o -> o.monthlyPartitionContainsUsaZipcodePercent);
            put("monthly_partition_valid_email_percent", o -> o.monthlyPartitionValidEmailPercent);
            put("monthly_partition_contains_email_percent", o -> o.monthlyPartitionContainsEmailPercent);
            put("monthly_partition_valid_ip4_address_percent", o -> o.monthlyPartitionValidIp4AddressPercent);
            put("monthly_partition_contains_ip4_percent", o -> o.monthlyPartitionContainsIp4Percent);
            put("monthly_partition_valid_ip6_address_percent", o -> o.monthlyPartitionValidIp6AddressPercent);
            put("monthly_partition_contains_ip6_percent", o -> o.monthlyPartitionContainsIp6Percent);
            
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiValidUsaPhonePercentCheckSpec monthlyPartitionValidUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec monthlyPartitionContainsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiValidUsaZipcodePercentCheckSpec monthlyPartitionValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec monthlyPartitionContainsUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiValidEmailPercentCheckSpec monthlyPartitionValidEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiContainsEmailPercentCheckSpec monthlyPartitionContainsEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiValidIp4AddressPercentCheckSpec monthlyPartitionValidIp4AddressPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiContainsIp4PercentCheckSpec monthlyPartitionContainsIp4Percent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiValidIp6AddressPercentCheckSpec monthlyPartitionValidIp6AddressPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.")
    private ColumnPiiContainsIp6PercentCheckSpec monthlyPartitionContainsIp6Percent;

    /**
     * Returns a minimum valid USA phone percent check.
     * @return Minimum valid USA phone percent  check.
     */
    public ColumnPiiValidUsaPhonePercentCheckSpec getMonthlyPartitionValidUsaPhonePercent() {
        return monthlyPartitionValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum valid USA phone percent check.
     * @param monthlyPartitionValidUsaPhonePercent Minimum valid USA phone percent check.
     */
    public void setMonthlyPartitionValidUsaPhonePercent(ColumnPiiValidUsaPhonePercentCheckSpec monthlyPartitionValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValidUsaPhonePercent, monthlyPartitionValidUsaPhonePercent));
        this.monthlyPartitionValidUsaPhonePercent = monthlyPartitionValidUsaPhonePercent;
        propagateHierarchyIdToField(monthlyPartitionValidUsaPhonePercent, "monthly_partition_valid_usa_phone_percent");
    }

    /**
     * Returns a maximum rows that contains USA phone number percentage check.
     * @return Maximum rows that contains USA phone number percentage check.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getMonthlyPartitionContainsUsaPhonePercent() {
        return monthlyPartitionContainsUsaPhonePercent;
    }

    /**
     * Sets a new definition of a maximum rows that contains USA phone number percentage check.
     * @param monthlyPartitionContainsUsaPhonePercent Maximum rows that contains USA phone number percentage check.
     */
    public void setMonthlyPartitionContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec monthlyPartitionContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionContainsUsaPhonePercent, monthlyPartitionContainsUsaPhonePercent));
        this.monthlyPartitionContainsUsaPhonePercent = monthlyPartitionContainsUsaPhonePercent;
        propagateHierarchyIdToField(monthlyPartitionContainsUsaPhonePercent, "monthly_partition_max_contains_usa_phone_percent");
    }

    /**
     * Returns a minimum valid usa zip code percent check.
     * @return Minimum valid usa zip code percent check.
     */
    public ColumnPiiValidUsaZipcodePercentCheckSpec getMonthlyPartitionValidUsaZipcodePercent() {
        return monthlyPartitionValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum valid usa zip code percent check.
     * @param monthlyPartitionValidUsaZipcodePercent Minimum valid usa zip code percent check.
     */
    public void setMonthlyPartitionValidUsaZipcodePercent(ColumnPiiValidUsaZipcodePercentCheckSpec monthlyPartitionValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValidUsaZipcodePercent, monthlyPartitionValidUsaZipcodePercent));
        this.monthlyPartitionValidUsaZipcodePercent = monthlyPartitionValidUsaZipcodePercent;
        propagateHierarchyIdToField(monthlyPartitionValidUsaZipcodePercent, "monthly_partition_valid_usa_zipcode_percent");
    }

    /**
     * Returns a maximum rows that contains USA zip code percentage check.
     * @return Maximum rows that contains USA zip code percentage check.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getMonthlyPartitionContainsUsaZipcodePercent() {
        return monthlyPartitionContainsUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a maximum rows that contains USA zip code percentage check.
     * @param monthlyPartitionContainsUsaZipcodePercent Maximum rows that contains USA zip code percentage check.
     */
    public void setMonthlyPartitionContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec monthlyPartitionContainsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionContainsUsaZipcodePercent, monthlyPartitionContainsUsaZipcodePercent));
        this.monthlyPartitionContainsUsaZipcodePercent = monthlyPartitionContainsUsaZipcodePercent;
        propagateHierarchyIdToField(monthlyPartitionContainsUsaZipcodePercent, "monthly_partition_max_contains_usa_zipcode_percent");
    }

    /**
     * Returns a minimum valid email percent check.
     * @return Minimum valid email percent check.
     */
    public ColumnPiiValidEmailPercentCheckSpec getMonthlyPartitionValidEmailPercent() {
        return monthlyPartitionValidEmailPercent;
    }

    /**
     * Sets a new definition of a minimum valid email percent check.
     * @param monthlyPartitionValidEmailPercent Minimum valid email percent check.
     */
    public void setMonthlyPartitionValidEmailPercent(ColumnPiiValidEmailPercentCheckSpec monthlyPartitionValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValidEmailPercent, monthlyPartitionValidEmailPercent));
        this.monthlyPartitionValidEmailPercent = monthlyPartitionValidEmailPercent;
        propagateHierarchyIdToField(monthlyPartitionValidEmailPercent, "monthly_partition_valid_email_percent");
    }

    /**
     * Returns a contains email percent check.
     * @return Contains email percent check.
     */
    public ColumnPiiContainsEmailPercentCheckSpec getMonthlyPartitionContainsEmailPercent() {
        return monthlyPartitionContainsEmailPercent;
    }

    /**
     * Sets a new definition of a contains email percent check.
     * @param monthlyPartitionContainsEmailPercent Contains email percent check.
     */
    public void setMonthlyPartitionContainsEmailPercent(ColumnPiiContainsEmailPercentCheckSpec monthlyPartitionContainsEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionContainsEmailPercent, monthlyPartitionContainsEmailPercent));
        this.monthlyPartitionContainsEmailPercent = monthlyPartitionContainsEmailPercent;
        propagateHierarchyIdToField(monthlyPartitionContainsEmailPercent, "monthly_partition_contains_email_percent");
    }

    /**
     * Returns a minimum valid IP4 address percent check.
     * @return Minimum valid IP4 address percent check.
     */
    public ColumnPiiValidIp4AddressPercentCheckSpec getMonthlyPartitionValidIp4AddressPercent() {
        return monthlyPartitionValidIp4AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP4 address percent check.
     * @param monthlyPartitionValidIp4AddressPercent Minimum valid IP4 address percent check.
     */
    public void setMonthlyPartitionValidIp4AddressPercent(ColumnPiiValidIp4AddressPercentCheckSpec monthlyPartitionValidIp4AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValidIp4AddressPercent, monthlyPartitionValidIp4AddressPercent));
        this.monthlyPartitionValidIp4AddressPercent = monthlyPartitionValidIp4AddressPercent;
        propagateHierarchyIdToField(monthlyPartitionValidIp4AddressPercent, "monthly_partition_valid_ip4_address_percent");
    }

    /**
     * Returns a contains IP4 percent check.
     * @return Contains IP4 percent check.
     */
    public ColumnPiiContainsIp4PercentCheckSpec getMonthlyPartitionContainsIp4Percent() {
        return monthlyPartitionContainsIp4Percent;
    }

    /**
     * Sets a new definition of a contains IP4 percent check.
     * @param monthlyPartitionContainsIp4Percent Contains valid IP4 percent check.
     */
    public void setMonthlyPartitionContainsIp4Percent(ColumnPiiContainsIp4PercentCheckSpec monthlyPartitionContainsIp4Percent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionContainsIp4Percent, monthlyPartitionContainsIp4Percent));
        this.monthlyPartitionContainsIp4Percent = monthlyPartitionContainsIp4Percent;
        propagateHierarchyIdToField(monthlyPartitionContainsIp4Percent, "monthly_partition_contains_ip4_percent");
    }

    /**
     * Returns a minimum valid IP6 address percent check.
     * @return Minimum valid IP6 address percent check.
     */
    public ColumnPiiValidIp6AddressPercentCheckSpec getMonthlyPartitionValidIp6AddressPercent() {
        return monthlyPartitionValidIp6AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP6 address percent check.
     * @param monthlyPartitionValidIp6AddressPercent Minimum valid IP6 address percent check.
     */
    public void setMonthlyPartitionValidIp6AddressPercent(ColumnPiiValidIp6AddressPercentCheckSpec monthlyPartitionValidIp6AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionValidIp6AddressPercent, monthlyPartitionValidIp6AddressPercent));
        this.monthlyPartitionValidIp6AddressPercent = monthlyPartitionValidIp6AddressPercent;
        propagateHierarchyIdToField(monthlyPartitionValidIp6AddressPercent, "monthly_partition_valid_ip6_address_percent");
    }

    /**
     * Returns a contains IP6 percent check.
     * @return Contains IP6 percent check.
     */
    public ColumnPiiContainsIp6PercentCheckSpec getMonthlyPartitionContainsIp6Percent() {
        return monthlyPartitionContainsIp6Percent;
    }

    /**
     * Sets a new definition of a contains IP6 percent check.
     * @param monthlyPartitionContainsIp6Percent Contains valid IP6 percent check.
     */
    public void setMonthlyPartitionContainsIp6Percent(ColumnPiiContainsIp6PercentCheckSpec monthlyPartitionContainsIp6Percent) {
        this.setDirtyIf(!Objects.equals(this.monthlyPartitionContainsIp6Percent, monthlyPartitionContainsIp6Percent));
        this.monthlyPartitionContainsIp6Percent = monthlyPartitionContainsIp6Percent;
        propagateHierarchyIdToField(monthlyPartitionContainsIp6Percent, "monthly_partition_contains_ip6_percent");
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