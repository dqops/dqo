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
 * Container of built-in preconfigured data quality check points on a column level that are checking at a daily level.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ColumnPiiDailyCheckpointsSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnPiiDailyCheckpointsSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_checkpoint_valid_usa_phone_percent", o -> o.dailyCheckpointValidUsaPhonePercent);
            put("daily_checkpoint_contains_usa_phone_percent", o -> o.dailyCheckpointContainsUsaPhonePercent);
            put("daily_checkpoint_valid_usa_zipcode_percent", o -> o.dailyCheckpointValidUsaZipcodePercent);
            put("daily_checkpoint_contains_usa_zipcode_percent", o -> o.dailyCheckpointContainsUsaZipcodePercent);
            put("daily_checkpoint_valid_email_percent", o -> o.dailyCheckpointValidEmailPercent);
            put("daily_checkpoint_contains_email_percent", o -> o.dailyCheckpointContainsEmailPercent);
            put("daily_checkpoint_valid_ip4_address_percent", o -> o.dailyCheckpointValidIp4AddressPercent);
            put("daily_checkpoint_valid_ip6_address_percent", o -> o.dailyCheckpointValidIp6AddressPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPiiValidUsaPhonePercentCheckSpec dailyCheckpointValidUsaPhonePercent;
    
    @JsonPropertyDescription("Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPiiContainsUsaPhonePercentCheckSpec dailyCheckpointContainsUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPiiValidUsaZipcodePercentCheckSpec dailyCheckpointValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPiiContainsUsaZipcodePercentCheckSpec dailyCheckpointContainsUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPiiValidEmailPercentCheckSpec dailyCheckpointValidEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPiiContainsEmailPercentCheckSpec dailyCheckpointContainsEmailPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP4 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPiiValidIp4AddressPercentCheckSpec dailyCheckpointValidIp4AddressPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid IP6 address in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each day when the data quality check was evaluated.")
    private ColumnPiiValidIp6AddressPercentCheckSpec dailyCheckpointValidIp6AddressPercent;

    /**
     * Returns a minimum valid USA phone percent check.
     * @return Minimum valid USA phone percent check.
     */
    public ColumnPiiValidUsaPhonePercentCheckSpec getDailyCheckpointValidUsaPhonePercent() {
        return dailyCheckpointValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum valid USA phone percent check.
     * @param dailyCheckpointValidUsaPhonePercent Minimum valid USA phone percent check.
     */
    public void setDailyCheckpointValidUsaPhonePercent(ColumnPiiValidUsaPhonePercentCheckSpec dailyCheckpointValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValidUsaPhonePercent, dailyCheckpointValidUsaPhonePercent));
        this.dailyCheckpointValidUsaPhonePercent = dailyCheckpointValidUsaPhonePercent;
        propagateHierarchyIdToField(dailyCheckpointValidUsaPhonePercent, "daily_checkpoint_valid_usa_phone_percent");
    }

    /**
     * Returns contains USA phone number percent check specification.
     * @return Contains USA phone number percent check specification.
     */
    public ColumnPiiContainsUsaPhonePercentCheckSpec getDailyCheckpointContainsUsaPhonePercent() {
        return dailyCheckpointContainsUsaPhonePercent;
    }

    /**
     * Sets a new definition of contains USA phone number percent check.
     * @param dailyCheckpointContainsUsaPhonePercent Contains USA phone number percent check specification.
     */
    public void setDailyCheckpointContainsUsaPhonePercent(ColumnPiiContainsUsaPhonePercentCheckSpec dailyCheckpointContainsUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointContainsUsaPhonePercent, dailyCheckpointContainsUsaPhonePercent));
        this.dailyCheckpointContainsUsaPhonePercent = dailyCheckpointContainsUsaPhonePercent;
        propagateHierarchyIdToField(dailyCheckpointContainsUsaPhonePercent, "daily_checkpoint_contains_usa_phone_percent");
    }

    /**
     * Returns a minimum valid usa zip code percent check.
     * @return Minimum valid usa zip code percent check.
     */
    public ColumnPiiValidUsaZipcodePercentCheckSpec getDailyCheckpointValidUsaZipcodePercent() {
        return dailyCheckpointValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum valid usa zip code percent check.
     * @param dailyCheckpointValidUsaZipcodePercent Minimum valid usa zip code percent check.
     */
    public void setDailyCheckpointValidUsaZipcodePercent(ColumnPiiValidUsaZipcodePercentCheckSpec dailyCheckpointValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValidUsaZipcodePercent, dailyCheckpointValidUsaZipcodePercent));
        this.dailyCheckpointValidUsaZipcodePercent = dailyCheckpointValidUsaZipcodePercent;
        propagateHierarchyIdToField(dailyCheckpointValidUsaZipcodePercent, "daily_checkpoint_valid_usa_zipcode_percent");
    }

    /**
     * Returns contains USA zip code percent check specification.
     * @return Contains USA zip code percent check specification.
     */
    public ColumnPiiContainsUsaZipcodePercentCheckSpec getDailyCheckpointContainsUsaZipcodePercent() {
        return dailyCheckpointContainsUsaZipcodePercent;
    }

    /**
     * Sets a new definition of contains USA zip code percent check.
     * @param dailyCheckpointContainsUsaZipcodePercent Contains USA zip code percent check specification.
     */
    public void setDailyCheckpointContainsUsaZipcodePercent(ColumnPiiContainsUsaZipcodePercentCheckSpec dailyCheckpointContainsUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointContainsUsaZipcodePercent, dailyCheckpointContainsUsaZipcodePercent));
        this.dailyCheckpointContainsUsaZipcodePercent = dailyCheckpointContainsUsaZipcodePercent;
        propagateHierarchyIdToField(dailyCheckpointContainsUsaZipcodePercent, "daily_checkpoint_contains_usa_zipcode_percent");
    }

    /**
     * Returns a minimum valid email percent check.
     * @return Minimum valid email percent check.
     */
    public ColumnPiiValidEmailPercentCheckSpec getDailyCheckpointValidEmailPercent() {
        return dailyCheckpointValidEmailPercent;
    }

    /**
     * Sets a new definition of a minimum valid email percent check.
     * @param dailyCheckpointValidEmailPercent Minimum valid email percent check.
     */
    public void setDailyCheckpointValidEmailPercent(ColumnPiiValidEmailPercentCheckSpec dailyCheckpointValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValidEmailPercent, dailyCheckpointValidEmailPercent));
        this.dailyCheckpointValidEmailPercent = dailyCheckpointValidEmailPercent;
        propagateHierarchyIdToField(dailyCheckpointValidEmailPercent, "daily_checkpoint_valid_email_percent");
    }

    /**
     * Returns a contains email percent check.
     * @return Contains email percent check.
     */
    public ColumnPiiContainsEmailPercentCheckSpec getDailyCheckpointContainsEmailPercent() {
        return dailyCheckpointContainsEmailPercent;
    }

    /**
     * Sets a new definition of a contains email percent check.
     * @param dailyCheckpointContainsEmailPercent Contains email percent check.
     */
    public void setDailyCheckpointContainsEmailPercent(ColumnPiiContainsEmailPercentCheckSpec dailyCheckpointContainsEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointContainsEmailPercent, dailyCheckpointContainsEmailPercent));
        this.dailyCheckpointContainsEmailPercent = dailyCheckpointContainsEmailPercent;
        propagateHierarchyIdToField(dailyCheckpointContainsEmailPercent, "daily_checkpoint_contains_email_percent");
    }

    /**
     * Returns a minimum valid IP4 address percent check.
     * @return Minimum valid IP4 address percent check.
     */
    public ColumnPiiValidIp4AddressPercentCheckSpec getDailyCheckpointValidIp4AddressPercent() {
        return dailyCheckpointValidIp4AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP4 address percent check.
     * @param dailyCheckpointValidIp4AddressPercent Minimum valid IP4 address percent check.
     */
    public void setDailyCheckpointValidIp4AddressPercent(ColumnPiiValidIp4AddressPercentCheckSpec dailyCheckpointValidIp4AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValidIp4AddressPercent, dailyCheckpointValidIp4AddressPercent));
        this.dailyCheckpointValidIp4AddressPercent = dailyCheckpointValidIp4AddressPercent;
        propagateHierarchyIdToField(dailyCheckpointValidIp4AddressPercent, "daily_checkpoint_valid_ip4_address_percent");
    }

    /**
     * Returns a minimum valid IP6 address percent check.
     * @return Minimum valid IP6 address percent check.
     */
    public ColumnPiiValidIp6AddressPercentCheckSpec getDailyCheckpointValidIp6AddressPercent() {
        return dailyCheckpointValidIp6AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP6 address percent check.
     * @param dailyCheckpointValidIp6AddressPercent Minimum valid IP6 address percent check.
     */
    public void setDailyCheckpointValidIp6AddressPercent(ColumnPiiValidIp6AddressPercentCheckSpec dailyCheckpointValidIp6AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyCheckpointValidIp6AddressPercent, dailyCheckpointValidIp6AddressPercent));
        this.dailyCheckpointValidIp6AddressPercent = dailyCheckpointValidIp6AddressPercent;
        propagateHierarchyIdToField(dailyCheckpointValidIp6AddressPercent, "daily_checkpoint_valid_ip6_address_percent");
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