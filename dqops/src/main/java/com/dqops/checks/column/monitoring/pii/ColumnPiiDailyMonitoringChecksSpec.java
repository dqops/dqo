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
 * Container of PII data quality monitoring checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiDailyMonitoringChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiDailyMonitoringChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_contains_usa_phone_percent", o -> o.dailyContainsUsaPhonePercent);
            put("daily_contains_email_percent", o -> o.dailyContainsEmailPercent);
            put("daily_contains_usa_zipcode_percent", o -> o.dailyContainsUsaZipcodePercent);
            put("daily_contains_ip4_percent", o -> o.dailyContainsIp4Percent);
            put("daily_contains_ip6_percent", o -> o.dailyContainsIp6Percent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec dailyContainsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPiiContainsEmailPercentCheckSpec dailyContainsEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec dailyContainsUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPiiContainsIp4PercentCheckSpec dailyContainsIp4Percent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.")
    private ColumnPiiContainsIp6PercentCheckSpec dailyContainsIp6Percent;

    /**
     * Returns contains USA phone number percent check specification.
     * @return Contains USA phone number percent check specification.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getDailyContainsUsaPhonePercent() {
        return dailyContainsUsaPhonePercent;
    }

    /**
     * Sets a new definition of contains USA phone number percent check.
     * @param dailyContainsUsaPhonePercent Contains USA phone number percent check specification.
     */
    public void setDailyContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec dailyContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyContainsUsaPhonePercent, dailyContainsUsaPhonePercent));
        this.dailyContainsUsaPhonePercent = dailyContainsUsaPhonePercent;
        propagateHierarchyIdToField(dailyContainsUsaPhonePercent, "daily_contains_usa_phone_percent");
    }

    /**
     * Returns a contains email percent check.
     * @return Contains email percent check.
     */
    public ColumnPiiContainsEmailPercentCheckSpec getDailyContainsEmailPercent() {
        return dailyContainsEmailPercent;
    }

    /**
     * Sets a new definition of a contains email percent check.
     * @param dailyContainsEmailPercent Contains email percent check.
     */
    public void setDailyContainsEmailPercent(ColumnPiiContainsEmailPercentCheckSpec dailyContainsEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyContainsEmailPercent, dailyContainsEmailPercent));
        this.dailyContainsEmailPercent = dailyContainsEmailPercent;
        propagateHierarchyIdToField(dailyContainsEmailPercent, "daily_contains_email_percent");
    }

    /**
     * Returns contains USA zip code percent check specification.
     * @return Contains USA zip code percent check specification.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getDailyContainsUsaZipcodePercent() {
        return dailyContainsUsaZipcodePercent;
    }

    /**
     * Sets a new definition of contains USA zip code percent check.
     * @param dailyContainsUsaZipcodePercent Contains USA zip code percent check specification.
     */
    public void setDailyContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec dailyContainsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyContainsUsaZipcodePercent, dailyContainsUsaZipcodePercent));
        this.dailyContainsUsaZipcodePercent = dailyContainsUsaZipcodePercent;
        propagateHierarchyIdToField(dailyContainsUsaZipcodePercent, "daily_contains_usa_zipcode_percent");
    }

    /**
     * Returns a contains IP4 percent check.
     * @return Contains IP4 percent check.
     */
    public ColumnPiiContainsIp4PercentCheckSpec getDailyContainsIp4Percent() {
        return dailyContainsIp4Percent;
    }

    /**
     * Sets a new definition of a contains IP4 percent check.
     * @param dailyContainsIp4Percent Contains IP4 percent check.
     */
    public void setDailyContainsIp4Percent(ColumnPiiContainsIp4PercentCheckSpec dailyContainsIp4Percent) {
        this.setDirtyIf(!Objects.equals(this.dailyContainsIp4Percent, dailyContainsIp4Percent));
        this.dailyContainsIp4Percent = dailyContainsIp4Percent;
        propagateHierarchyIdToField(dailyContainsIp4Percent, "daily_contains_ip4_percent");
    }

    /**
     * Returns a contains IP6 percent check.
     * @return Contains IP6 percent check.
     */
    public ColumnPiiContainsIp6PercentCheckSpec getDailyContainsIp6Percent() {
        return dailyContainsIp6Percent;
    }

    /**
     * Sets a new definition of a contains IP6 percent check.
     * @param dailyContainsIp6Percent Contains IP6 percent check.
     */
    public void setDailyContainsIp6Percent(ColumnPiiContainsIp6PercentCheckSpec dailyContainsIp6Percent) {
        this.setDirtyIf(!Objects.equals(this.dailyContainsIp6Percent, dailyContainsIp6Percent));
        this.dailyContainsIp6Percent = dailyContainsIp6Percent;
        propagateHierarchyIdToField(dailyContainsIp6Percent, "daily_contains_ip6_percent");
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
    public ColumnPiiDailyMonitoringChecksSpec deepClone() {
        return (ColumnPiiDailyMonitoringChecksSpec)super.deepClone();
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