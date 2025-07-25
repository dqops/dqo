/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
 * Container of PII data quality partitioned checks on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_contains_usa_phone_percent", o -> o.dailyPartitionContainsUsaPhonePercent);
            put("daily_partition_contains_email_percent", o -> o.dailyPartitionContainsEmailPercent);
            put("daily_partition_contains_usa_zipcode_percent", o -> o.dailyPartitionContainsUsaZipcodePercent);
            put("daily_partition_contains_ip4_percent", o -> o.dailyPartitionContainsIp4Percent);
            put("daily_partition_contains_ip6_percent", o -> o.dailyPartitionContainsIp6Percent);
        }
    };

    @JsonPropertyDescription("Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec dailyPartitionContainsUsaPhonePercent;

    @JsonPropertyDescription("Detects emails in text columns. Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnPiiContainsEmailPercentCheckSpec dailyPartitionContainsEmailPercent;

    @JsonPropertyDescription("Detects USA zip codes in text columns. Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec dailyPartitionContainsUsaZipcodePercent;

    @JsonPropertyDescription("Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnPiiContainsIp4PercentCheckSpec dailyPartitionContainsIp4Percent;

    @JsonPropertyDescription("Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.")
    private ColumnPiiContainsIp6PercentCheckSpec dailyPartitionContainsIp6Percent;

    /**
     * Returns contains USA phone number percentage check.
     * @return Contains USA phone number percentage check.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getDailyPartitionContainsUsaPhonePercent() {
        return dailyPartitionContainsUsaPhonePercent;
    }

    /**
     * Sets a new definition of a maximum rows that contains USA phone number percentage check.
     * @param dailyPartitionContainsUsaPhonePercent Maximum rows that contains USA phone number percentage check.
     */
    public void setDailyPartitionContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec dailyPartitionContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionContainsUsaPhonePercent, dailyPartitionContainsUsaPhonePercent));
        this.dailyPartitionContainsUsaPhonePercent = dailyPartitionContainsUsaPhonePercent;
        propagateHierarchyIdToField(dailyPartitionContainsUsaPhonePercent, "daily_partition_contains_usa_phone_percent");
    }

    /**
     * Returns a contains email percent check.
     * @return Contains email percent check.
     */
    public ColumnPiiContainsEmailPercentCheckSpec getDailyPartitionContainsEmailPercent() {
        return dailyPartitionContainsEmailPercent;
    }

    /**
     * Sets a new definition of a contains email percent check.
     * @param dailyPartitionContainsEmailPercent Contains valid email percent check.
     */
    public void setDailyPartitionContainsEmailPercent(ColumnPiiContainsEmailPercentCheckSpec dailyPartitionContainsEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionContainsEmailPercent, dailyPartitionContainsEmailPercent));
        this.dailyPartitionContainsEmailPercent = dailyPartitionContainsEmailPercent;
        propagateHierarchyIdToField(dailyPartitionContainsEmailPercent, "daily_partition_contains_email_percent");
    }

    /**
     * Returns a maximum rows that contains USA zip code percentage check.
     * @return Maximum rows that contains USA zip code percentage check.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getDailyPartitionContainsUsaZipcodePercent() {
        return dailyPartitionContainsUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a maximum rows that contains USA zip code percentage check.
     * @param dailyPartitionContainsUsaZipcodePercent Maximum rows that contains USA zip code percentage check.
     */
    public void setDailyPartitionContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec dailyPartitionContainsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionContainsUsaZipcodePercent, dailyPartitionContainsUsaZipcodePercent));
        this.dailyPartitionContainsUsaZipcodePercent = dailyPartitionContainsUsaZipcodePercent;
        propagateHierarchyIdToField(dailyPartitionContainsUsaZipcodePercent, "daily_partition_contains_usa_zipcode_percent");
    }

    /**
     * Returns a contains IP4 percent check.
     * @return Contains IP4 percent check.
     */
    public ColumnPiiContainsIp4PercentCheckSpec getDailyPartitionContainsIp4Percent() {
        return dailyPartitionContainsIp4Percent;
    }

    /**
     * Sets a new definition of a contains IP4 percent check.
     * @param dailyPartitionContainsIp4Percent Contains valid IP4 percent check.
     */
    public void setDailyPartitionContainsIp4Percent(ColumnPiiContainsIp4PercentCheckSpec dailyPartitionContainsIp4Percent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionContainsIp4Percent, dailyPartitionContainsIp4Percent));
        this.dailyPartitionContainsIp4Percent = dailyPartitionContainsIp4Percent;
        propagateHierarchyIdToField(dailyPartitionContainsIp4Percent, "daily_partition_contains_ip4_percent");
    }

    /**
     * Returns a contains IP6 percent check.
     * @return Contains IP6 percent check.
     */
    public ColumnPiiContainsIp6PercentCheckSpec getDailyPartitionContainsIp6Percent() {
        return dailyPartitionContainsIp6Percent;
    }

    /**
     * Sets a new definition of a contains IP6 percent check.
     * @param dailyPartitionContainsIp6Percent Contains valid IP6 percent check.
     */
    public void setDailyPartitionContainsIp6Percent(ColumnPiiContainsIp6PercentCheckSpec dailyPartitionContainsIp6Percent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionContainsIp6Percent, dailyPartitionContainsIp6Percent));
        this.dailyPartitionContainsIp6Percent = dailyPartitionContainsIp6Percent;
        propagateHierarchyIdToField(dailyPartitionContainsIp6Percent, "daily_partition_contains_ip6_percent");
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
