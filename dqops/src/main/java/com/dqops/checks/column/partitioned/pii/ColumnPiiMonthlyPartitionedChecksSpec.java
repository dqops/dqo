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
package com.dqops.checks.column.partitioned.pii;

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
 * Container of PII data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiMonthlyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiMonthlyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_partition_contains_usa_phone_percent", o -> o.monthlyPartitionContainsUsaPhonePercent);
            put("monthly_partition_contains_email_percent", o -> o.monthlyPartitionContainsEmailPercent);
            put("monthly_partition_contains_usa_zipcode_percent", o -> o.monthlyPartitionContainsUsaZipcodePercent);
            put("monthly_partition_contains_ip4_percent", o -> o.monthlyPartitionContainsIp4Percent);
            put("monthly_partition_contains_ip6_percent", o -> o.monthlyPartitionContainsIp6Percent);
        }
    };

    @JsonPropertyDescription("Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec monthlyPartitionContainsUsaPhonePercent;

    @JsonPropertyDescription("Detects emails in text columns. Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnPiiContainsEmailPercentCheckSpec monthlyPartitionContainsEmailPercent;

    @JsonPropertyDescription("Detects USA zip codes in text columns. Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec monthlyPartitionContainsUsaZipcodePercent;

    @JsonPropertyDescription("Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnPiiContainsIp4PercentCheckSpec monthlyPartitionContainsIp4Percent;

    @JsonPropertyDescription("Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.")
    private ColumnPiiContainsIp6PercentCheckSpec monthlyPartitionContainsIp6Percent;

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
        propagateHierarchyIdToField(monthlyPartitionContainsUsaPhonePercent, "monthly_partition_contains_usa_phone_percent");
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
        propagateHierarchyIdToField(monthlyPartitionContainsUsaZipcodePercent, "monthly_partition_contains_usa_zipcode_percent");
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