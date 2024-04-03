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
package com.dqops.checks.column.monitoring.pii;

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
 * Container of PII data quality monitoring checks on a column level that are checking at a monthly level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiMonthlyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiMonthlyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("monthly_contains_usa_phone_percent", o -> o.monthlyContainsUsaPhonePercent);
            put("monthly_contains_email_percent", o -> o.monthlyContainsEmailPercent);
            put("monthly_contains_usa_zipcode_percent", o -> o.monthlyContainsUsaZipcodePercent);
            put("monthly_contains_ip4_percent", o -> o.monthlyContainsIp4Percent);
            put("monthly_contains_ip6_percent", o -> o.monthlyContainsIp6Percent);
        }
    };

    @JsonPropertyDescription("Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec monthlyContainsUsaPhonePercent;

    @JsonPropertyDescription("Detects emails in text columns. Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnPiiContainsEmailPercentCheckSpec monthlyContainsEmailPercent;

    @JsonPropertyDescription("Detects USA zip codes in text columns. Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec monthlyContainsUsaZipcodePercent;

    @JsonPropertyDescription("Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnPiiContainsIp4PercentCheckSpec monthlyContainsIp4Percent;

    @JsonPropertyDescription("Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.")
    private ColumnPiiContainsIp6PercentCheckSpec monthlyContainsIp6Percent;    

    /**
     * Returns contains USA phone number percent check specification.
     * @return Contains USA phone number percent check specification.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getMonthlyContainsUsaPhonePercent() {
        return monthlyContainsUsaPhonePercent;
    }

    /**
     * Sets a new definition of contains USA phone number percent check.
     * @param monthlyContainsUsaPhonePercent Contains USA phone number percent check specification.
     */
    public void setMonthlyContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec monthlyContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyContainsUsaPhonePercent, monthlyContainsUsaPhonePercent));
        this.monthlyContainsUsaPhonePercent = monthlyContainsUsaPhonePercent;
        propagateHierarchyIdToField(monthlyContainsUsaPhonePercent, "monthly_contains_usa_phone_percent");
    }

    /**
     * Returns a contains email percent check.
     * @return Contains email percent check.
     */
    public ColumnPiiContainsEmailPercentCheckSpec getMonthlyContainsEmailPercent() {
        return monthlyContainsEmailPercent;
    }

    /**
     * Sets a new definition of a contains email percent check.
     * @param monthlyContainsEmailPercent Contains valid email percent check.
     */
    public void setMonthlyContainsEmailPercent(ColumnPiiContainsEmailPercentCheckSpec monthlyContainsEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyContainsEmailPercent, monthlyContainsEmailPercent));
        this.monthlyContainsEmailPercent = monthlyContainsEmailPercent;
        propagateHierarchyIdToField(monthlyContainsEmailPercent, "monthly_contains_email_percent");
    }

    /**
     * Returns contains USA zip code percent check specification.
     * @return Contains USA zip code percent check specification.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getMonthlyContainsUsaZipcodePercent() {
        return monthlyContainsUsaZipcodePercent;
    }

    /**
     * Sets a new definition of contains USA zip code percent check.
     * @param monthlyContainsUsaZipcodePercent Contains USA zip code percent check specification.
     */
    public void setMonthlyContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec monthlyContainsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.monthlyContainsUsaZipcodePercent, monthlyContainsUsaZipcodePercent));
        this.monthlyContainsUsaZipcodePercent = monthlyContainsUsaZipcodePercent;
        propagateHierarchyIdToField(monthlyContainsUsaZipcodePercent, "monthly_contains_usa_zipcode_percent");
    }

    /**
     * Returns a contains IP4 percent check.
     * @return Contains IP4 percent check.
     */
    public ColumnPiiContainsIp4PercentCheckSpec getMonthlyContainsIp4Percent() {
        return monthlyContainsIp4Percent;
    }

    /**
     * Sets a new definition of a contains IP4 percent check.
     * @param monthlyContainsIp4Percent Contains IP4 percent check.
     */
    public void setMonthlyContainsIp4Percent(ColumnPiiContainsIp4PercentCheckSpec monthlyContainsIp4Percent) {
        this.setDirtyIf(!Objects.equals(this.monthlyContainsIp4Percent, monthlyContainsIp4Percent));
        this.monthlyContainsIp4Percent = monthlyContainsIp4Percent;
        propagateHierarchyIdToField(monthlyContainsIp4Percent, "monthly_contains_ip4_percent");
    }

    /**
     * Returns a contains IP6 percent check.
     * @return Contains IP6 percent check.
     */
    public ColumnPiiContainsIp6PercentCheckSpec getMonthlyContainsIp6Percent() {
        return monthlyContainsIp6Percent;
    }

    /**
     * Sets a new definition of a contains IP6 percent check.
     * @param monthlyContainsIp6Percent Contains IP6 percent check.
     */
    public void setMonthlyContainsIp6Percent(ColumnPiiContainsIp6PercentCheckSpec monthlyContainsIp6Percent) {
        this.setDirtyIf(!Objects.equals(this.monthlyContainsIp6Percent, monthlyContainsIp6Percent));
        this.monthlyContainsIp6Percent = monthlyContainsIp6Percent;
        propagateHierarchyIdToField(monthlyContainsIp6Percent, "monthly_contains_ip6_percent");
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
    public ColumnPiiMonthlyMonitoringChecksSpec deepClone() {
        return (ColumnPiiMonthlyMonitoringChecksSpec)super.deepClone();
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