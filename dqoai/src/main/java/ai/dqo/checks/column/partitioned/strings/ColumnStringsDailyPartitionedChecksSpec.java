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
package ai.dqo.checks.column.partitioned.strings;

import ai.dqo.checks.AbstractCheckCategorySpec;
import ai.dqo.checks.column.checkspecs.strings.*;
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
public class ColumnStringsDailyPartitionedChecksSpec extends AbstractCheckCategorySpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnStringsDailyPartitionedChecksSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckCategorySpec.FIELDS) {
        {
            put("daily_partition_string_max_length", o -> o.dailyPartitionStringMaxLength);
            put("daily_partition_string_min_length", o -> o.dailyPartitionStringMinLength);
            put("daily_partition_string_mean_length", o -> o.dailyPartitionStringMeanLength);

            put("daily_partition_string_empty_count", o -> o.dailyPartitionStringEmptyCount);
            put("daily_partition_string_empty_percent", o -> o.dailyPartitionStringEmptyPercent);
            put("daily_partition_string_whitespace_count", o -> o.dailyPartitionStringWhitespaceCount);
            put("daily_partition_string_whitespace_percent", o -> o.dailyPartitionStringWhitespacePercent);
            put("daily_partition_string_surrounded_by_whitespace_count", o -> o.dailyPartitionStringSurroundedByWhitespaceCount);
            put("daily_partition_string_surrounded_by_whitespace_percent", o -> o.dailyPartitionStringSurroundedByWhitespacePercent);
            
            put("daily_partition_string_null_placeholder_count", o -> o.dailyPartitionStringNullPlaceholderCount);
            put("daily_partition_string_null_placeholder_percent", o -> o.dailyPartitionStringNullPlaceholderPercent);
            put("daily_partition_string_boolean_placeholder_percent", o -> o.dailyPartitionStringBooleanPlaceholderPercent);
            put("daily_partition_string_parsable_to_integer_percent", o -> o.dailyPartitionStringParsableToIntegerPercent);
            put("daily_partition_string_parsable_to_float_percent", o -> o.dailyPartitionStringParsableToFloatPercent);

            put("daily_partition_string_in_set_count", o -> o.dailyPartitionStringInSetCount);
            put("daily_partition_string_in_set_percent", o -> o.dailyPartitionStringInSetPercent);
            
            put("daily_partition_string_valid_dates_percent", o -> o.dailyPartitionStringValidDatesPercent);
            put("daily_partition_string_valid_usa_zipcode_percent", o -> o.dailyPartitionStringValidUsaZipcodePercent);
            put("daily_partition_string_valid_usa_phone_percent", o -> o.dailyPartitionStringValidUsaPhonePercent);
            put("daily_partition_string_valid_country_code_percent", o -> o.dailyPartitionStringValidCountryCodePercent);
            put("daily_partition_string_valid_currency_code_percent", o -> o.dailyPartitionStringValidCurrencyCodePercent);
            put("daily_partition_string_invalid_email_count", o -> o.dailyPartitionStringInvalidEmailCount);
            put("daily_partition_valid_email_percent", o -> o.dailyPartitionValidEmailPercent);
            put("daily_partition_string_invalid_uuid_count", o -> o.dailyPartitionStringInvalidUuidCount);
            put("daily_partition_valid_uuid_percent", o -> o.dailyPartitionValidUuidPercent);
            put("daily_partition_string_invalid_ip4_address_count", o -> o.dailyPartitionStringInvalidIp4AddressCount);
            put("daily_partition_string_valid_ip4_address_percent", o -> o.dailyPartitionStringValidIp4AddressPercent);



            put("daily_partition_string_not_match_regex_count", o -> o.dailyPartitionStringNotMatchRegexCount);
            put("daily_partition_string_match_regex_percent", o -> o.dailyPartitionStringMatchRegexPercent);
            put("daily_partition_string_not_match_date_regex_count", o -> o.dailyPartitionStringNotMatchDateRegexCount);
            put("daily_partition_string_match_date_regex_percent", o -> o.dailyPartitionStringMatchDateRegexPercent);
            put("daily_partition_string_match_name_regex_percent", o -> o.dailyPartitionStringMatchNameRegexPercent);
        }
    };

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringMaxLengthCheckSpec dailyPartitionStringMaxLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringMinLengthCheckSpec dailyPartitionStringMinLength;

    @JsonPropertyDescription("Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringMeanLengthCheckSpec dailyPartitionStringMeanLength;

    @JsonPropertyDescription("Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringEmptyCountCheckSpec dailyPartitionStringEmptyCount;

    @JsonPropertyDescription("Verifies that the percentage of string in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringEmptyPercentCheckSpec dailyPartitionStringEmptyPercent;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringWhitespaceCountCheckSpec dailyPartitionStringWhitespaceCount;

    @JsonPropertyDescription("Verifies that the number of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringWhitespacePercentCheckSpec dailyPartitionStringWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringSurroundedByWhitespaceCountCheckSpec dailyPartitionStringSurroundedByWhitespaceCount;

    @JsonPropertyDescription("Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringSurroundedByWhitespacePercentCheckSpec dailyPartitionStringSurroundedByWhitespacePercent;

    @JsonPropertyDescription("Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringNullPlaceholderCountCheckSpec dailyPartitionStringNullPlaceholderCount;

    @JsonPropertyDescription("Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringNullPlaceholderPercentCheckSpec dailyPartitionStringNullPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringBooleanPlaceholderPercentCheckSpec dailyPartitionStringBooleanPlaceholderPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringParsableToIntegerPercentCheckSpec dailyPartitionStringParsableToIntegerPercent;

    @JsonPropertyDescription("Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringParsableToFloatPercentCheckSpec dailyPartitionStringParsableToFloatPercent;

    @JsonPropertyDescription("Verifies that the number of strings from set in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringInSetCountCheckSpec dailyPartitionStringInSetCount;

    @JsonPropertyDescription("Verifies that the percentage of strings from set in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringInSetPercentCheckSpec dailyPartitionStringInSetPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidDatesPercentCheckSpec dailyPartitionStringValidDatesPercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA zip code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidUsaZipcodePercentCheckSpec dailyPartitionStringValidUsaZipcodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid USA phone in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidUsaPhonePercentCheckSpec dailyPartitionStringValidUsaPhonePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidCountryCodePercentCheckSpec dailyPartitionStringValidCountryCodePercent;

    @JsonPropertyDescription("Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidCurrencyCodePercentCheckSpec dailyPartitionStringValidCurrencyCodePercent;

    @JsonPropertyDescription("Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringInvalidEmailCountCheckSpec dailyPartitionStringInvalidEmailCount;

    @JsonPropertyDescription("Verifies that the percentage of valid emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidEmailPercentCheckSpec dailyPartitionValidEmailPercent;

    @JsonPropertyDescription("Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringInvalidUuidCountCheckSpec dailyPartitionStringInvalidUuidCount;

    @JsonPropertyDescription("Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidUuidPercentCheckSpec dailyPartitionValidUuidPercent;

    @JsonPropertyDescription("Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringInvalidIp4AddressCountCheckSpec dailyPartitionStringInvalidIp4AddressCount;

    @JsonPropertyDescription("Verifies that the percentage of valid IP4 address in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringValidIp4AddressPercentCheckSpec dailyPartitionStringValidIp4AddressPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringNotMatchRegexCountCheckSpec dailyPartitionStringNotMatchRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringRegexMatchPercentCheckSpec dailyPartitionStringMatchRegexPercent;

    @JsonPropertyDescription("Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringNotMatchDateRegexCountCheckSpec dailyPartitionStringNotMatchDateRegexCount;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringMatchDateRegexPercentCheckSpec dailyPartitionStringMatchDateRegexPercent;

    @JsonPropertyDescription("Verifies that the percentage of strings matching the name format regex in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.")
    private ColumnStringMatchNameRegexPercentCheckSpec dailyPartitionStringMatchNameRegexPercent;

    /**
     * Returns a maximum string length below  check.
     * @return Maximum string length below  check.
     */
    public ColumnStringMaxLengthCheckSpec getDailyPartitionStringMaxLength() {
        return dailyPartitionStringMaxLength;
    }

    /**
     * Sets a new definition of a maximum string length below  check.
     * @param dailyPartitionStringMaxLength Maximum string length below  check.
     */
    public void setDailyPartitionStringMaxLength(ColumnStringMaxLengthCheckSpec dailyPartitionStringMaxLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringMaxLength, dailyPartitionStringMaxLength));
        this.dailyPartitionStringMaxLength = dailyPartitionStringMaxLength;
        propagateHierarchyIdToField(dailyPartitionStringMaxLength, "daily_partition_string_max_length");
    }

    /**
     * Returns a minimum string length below  check.
     * @return Minimum string length below  check.
     */
    public ColumnStringMinLengthCheckSpec getDailyPartitionStringMinLength() {
        return dailyPartitionStringMinLength;
    }

    /**
     * Sets a new definition of a minimum string length below  check.
     * @param dailyPartitionStringMinLength Minimum string length above check.
     */
    public void setDailyPartitionStringMinLength(ColumnStringMinLengthCheckSpec dailyPartitionStringMinLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringMinLength, dailyPartitionStringMinLength));
        this.dailyPartitionStringMinLength = dailyPartitionStringMinLength;
        propagateHierarchyIdToField(dailyPartitionStringMaxLength, "daily_partition_string_min_length");
    }

    /**
     * Returns a mean string length between  check.
     * @return Mean string length between  check.
     */
    public ColumnStringMeanLengthCheckSpec getDailyPartitionStringMeanLength() {
        return dailyPartitionStringMeanLength;
    }

    /**
     * Sets a new definition of a mean string length between check.
     * @param dailyPartitionStringMeanLength Mean string length between check.
     */
    public void setDailyPartitionStringMeanLength(ColumnStringMeanLengthCheckSpec dailyPartitionStringMeanLength) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringMeanLength, dailyPartitionStringMeanLength));
        this.dailyPartitionStringMeanLength = dailyPartitionStringMeanLength;
        propagateHierarchyIdToField(dailyPartitionStringMeanLength, "daily_partition_string_mean_length");
    }

    /**
     * Returns a max string empty count check.
     * @return Max string empty count check.
     */
    public ColumnStringEmptyCountCheckSpec getDailyPartitionStringEmptyCount() {
        return dailyPartitionStringEmptyCount;
    }

    /**
     * Sets a new definition of a max string empty count check.
     * @param dailyPartitionStringEmptyCount Max string empty count check.
     */
    public void setDailyPartitionStringEmptyCount(ColumnStringEmptyCountCheckSpec dailyPartitionStringEmptyCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringEmptyCount, dailyPartitionStringEmptyCount));
        this.dailyPartitionStringEmptyCount = dailyPartitionStringEmptyCount;
        propagateHierarchyIdToField(dailyPartitionStringEmptyCount, "daily_partition_string_empty_count");
    }

    /**
     * Returns a maximum empty string percentage check.
     * @return Maximum empty string percentage check.
     */
    public ColumnStringEmptyPercentCheckSpec getDailyPartitionStringEmptyPercent() {
        return dailyPartitionStringEmptyPercent;
    }

    /**
     * Sets a new definition of a maximum empty string percentage check.
     * @param dailyPartitionStringEmptyPercent Maximum empty string percentage check.
     */
    public void setDailyPartitionStringEmptyPercent(ColumnStringEmptyPercentCheckSpec dailyPartitionStringEmptyPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringEmptyPercent, dailyPartitionStringEmptyPercent));
        this.dailyPartitionStringEmptyPercent = dailyPartitionStringEmptyPercent;
        propagateHierarchyIdToField(dailyPartitionStringEmptyPercent, "daily_partition_string_empty_percent");
    }

    /**
     * Returns a maximum string whitespace count check.
     * @return Maximum string whitespace count check.
     */
    public ColumnStringWhitespaceCountCheckSpec getDailyPartitionStringWhitespaceCount() {
        return dailyPartitionStringWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string whitespace count check.
     * @param dailyPartitionStringWhitespaceCount Maximum string whitespace count check.
     */
    public void setDailyPartitionStringWhitespaceCount(ColumnStringWhitespaceCountCheckSpec dailyPartitionStringWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringWhitespaceCount, dailyPartitionStringWhitespaceCount));
        this.dailyPartitionStringWhitespaceCount = dailyPartitionStringWhitespaceCount;
        propagateHierarchyIdToField(dailyPartitionStringWhitespaceCount, "daily_partition_string_whitespace_count");
    }

    /**
     * Returns a maximum string whitespace percent check.
     * @return Maximum string whitespace percent check.
     */
    public ColumnStringWhitespacePercentCheckSpec getDailyPartitionStringWhitespacePercent() {
        return dailyPartitionStringWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string whitespace percent check.
     * @param dailyPartitionStringWhitespacePercent Maximum string whitespace percent check.
     */
    public void setDailyPartitionStringWhitespacePercent(ColumnStringWhitespacePercentCheckSpec dailyPartitionStringWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringWhitespacePercent, dailyPartitionStringWhitespacePercent));
        this.dailyPartitionStringWhitespacePercent = dailyPartitionStringWhitespacePercent;
        propagateHierarchyIdToField(dailyPartitionStringWhitespacePercent, "daily_partition_string_whitespace_percent");
    }

    /**
     * Returns a maximum string surrounded by whitespace count check.
     * @return Maximum string surrounded by whitespace count check.
     */
    public ColumnStringSurroundedByWhitespaceCountCheckSpec getDailyPartitionStringSurroundedByWhitespaceCount() {
        return dailyPartitionStringSurroundedByWhitespaceCount;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace count check.
     * @param dailyPartitionStringSurroundedByWhitespaceCount Maximum string surrounded by whitespace count check.
     */
    public void setDailyPartitionStringSurroundedByWhitespaceCount(ColumnStringSurroundedByWhitespaceCountCheckSpec dailyPartitionStringSurroundedByWhitespaceCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringSurroundedByWhitespaceCount, dailyPartitionStringSurroundedByWhitespaceCount));
        this.dailyPartitionStringSurroundedByWhitespaceCount = dailyPartitionStringSurroundedByWhitespaceCount;
        propagateHierarchyIdToField(dailyPartitionStringSurroundedByWhitespaceCount, "daily_partition_string_surrounded_by_whitespace_count");
    }

    /**
     * Returns a maximum string surrounded by whitespace percent check.
     * @return Maximum string surrounded by whitespace percent check.
     */
    public ColumnStringSurroundedByWhitespacePercentCheckSpec getDailyPartitionStringSurroundedByWhitespacePercent() {
        return dailyPartitionStringSurroundedByWhitespacePercent;
    }

    /**
     * Sets a new definition of a maximum string surrounded by whitespace percent check.
     * @param dailyPartitionStringSurroundedByWhitespacePercent Maximum string surrounded by whitespace percent check.
     */
    public void setDailyPartitionStringSurroundedByWhitespacePercent(ColumnStringSurroundedByWhitespacePercentCheckSpec dailyPartitionStringSurroundedByWhitespacePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringSurroundedByWhitespacePercent, dailyPartitionStringSurroundedByWhitespacePercent));
        this.dailyPartitionStringSurroundedByWhitespacePercent = dailyPartitionStringSurroundedByWhitespacePercent;
        propagateHierarchyIdToField(dailyPartitionStringSurroundedByWhitespacePercent, "daily_partition_string_surrounded_by_whitespace_percent");
    }

    /**
     * Returns a maximum string null placeholder count check.
     * @return Maximum string null placeholder count check.
     */
    public ColumnStringNullPlaceholderCountCheckSpec getDailyPartitionStringNullPlaceholderCount() {
        return dailyPartitionStringNullPlaceholderCount;
    }

    /**
     * Sets a new definition of a maximum string null placeholder count check.
     * @param dailyPartitionStringNullPlaceholderCount Maximum string null placeholder count check.
     */
    public void setDailyPartitionStringNullPlaceholderCount(ColumnStringNullPlaceholderCountCheckSpec dailyPartitionStringNullPlaceholderCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringNullPlaceholderCount, dailyPartitionStringNullPlaceholderCount));
        this.dailyPartitionStringNullPlaceholderCount = dailyPartitionStringNullPlaceholderCount;
        propagateHierarchyIdToField(dailyPartitionStringNullPlaceholderCount, "daily_partition_string_null_placeholder_count");
    }

    /**
     * Returns a maximum string null placeholder percent check.
     * @return Maximum string null placeholder percent check.
     */
    public ColumnStringNullPlaceholderPercentCheckSpec getDailyPartitionStringNullPlaceholderPercent() {
        return dailyPartitionStringNullPlaceholderPercent;
    }

    /**
     * Sets a new definition of a maximum string null placeholder percent check.
     * @param dailyPartitionStringNullPlaceholderPercent Maximum string null placeholder percent check.
     */
    public void setDailyPartitionStringNullPlaceholderPercent(ColumnStringNullPlaceholderPercentCheckSpec dailyPartitionStringNullPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringNullPlaceholderPercent, dailyPartitionStringNullPlaceholderPercent));
        this.dailyPartitionStringNullPlaceholderPercent = dailyPartitionStringNullPlaceholderPercent;
        propagateHierarchyIdToField(dailyPartitionStringNullPlaceholderPercent, "daily_partition_string_null_placeholder_percent");
    }

    /**
     * Returns a minimum string boolean placeholder percent check.
     * @return Minimum string boolean placeholder percent check.
     */
    public ColumnStringBooleanPlaceholderPercentCheckSpec getDailyPartitionStringBooleanPlaceholderPercent() {
        return dailyPartitionStringBooleanPlaceholderPercent;
    }

    /**
     * Sets a new definition of a minimum string boolean placeholder percent check.
     * @param dailyPartitionStringBooleanPlaceholderPercent Minimum string boolean placeholder percent check.
     */
    public void setDailyPartitionStringBooleanPlaceholderPercent(ColumnStringBooleanPlaceholderPercentCheckSpec dailyPartitionStringBooleanPlaceholderPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringBooleanPlaceholderPercent, dailyPartitionStringBooleanPlaceholderPercent));
        this.dailyPartitionStringBooleanPlaceholderPercent = dailyPartitionStringBooleanPlaceholderPercent;
        propagateHierarchyIdToField(dailyPartitionStringBooleanPlaceholderPercent, "daily_partition_string_boolean_placeholder_percent");
    }

    /**
     * Returns a minimum string parsable to integer percent check.
     * @return Minimum string parsable to integer percent  check.
     */
    public ColumnStringParsableToIntegerPercentCheckSpec getDailyPartitionStringParsableToIntegerPercent() {
        return dailyPartitionStringParsableToIntegerPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to integer percent check.
     * @param dailyPartitionStringParsableToIntegerPercent Minimum string parsable to integer percent check.
     */
    public void setDailyPartitionStringParsableToIntegerPercent(ColumnStringParsableToIntegerPercentCheckSpec dailyPartitionStringParsableToIntegerPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringParsableToIntegerPercent, dailyPartitionStringParsableToIntegerPercent));
        this.dailyPartitionStringParsableToIntegerPercent = dailyPartitionStringParsableToIntegerPercent;
        propagateHierarchyIdToField(dailyPartitionStringParsableToIntegerPercent, "daily_partition_string_parsable_to_integer_percent");
    }

    /**
     * Returns a minimum string parsable to float percent check.
     * @return Minimum string parsable to float percent  check.
     */
    public ColumnStringParsableToFloatPercentCheckSpec getDailyPartitionStringParsableToFloatPercent() {
        return dailyPartitionStringParsableToFloatPercent;
    }

    /**
     * Sets a new definition of a minimum string parsable to float percent check.
     * @param dailyPartitionStringParsableToFloatPercent Minimum string parsable to float percent check.
     */
    public void setDailyPartitionStringParsableToFloatPercent(ColumnStringParsableToFloatPercentCheckSpec dailyPartitionStringParsableToFloatPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringParsableToFloatPercent, dailyPartitionStringParsableToFloatPercent));
        this.dailyPartitionStringParsableToFloatPercent = dailyPartitionStringParsableToFloatPercent;
        propagateHierarchyIdToField(dailyPartitionStringParsableToFloatPercent, "daily_partition_string_parsable_to_float_percent");
    }

    /**
     * Returns a minimum strings in set count check.
     * @return Minimum strings in set count check.
     */
    public ColumnStringInSetCountCheckSpec getDailyPartitionStringInSetCount() {
        return dailyPartitionStringInSetCount;
    }

    /**
     * Sets a new definition of a minimum strings in set count check.
     * @param dailyPartitionStringInSetCount Minimum strings in set count check.
     */
    public void setDailyPartitionStringInSetCount(ColumnStringInSetCountCheckSpec dailyPartitionStringInSetCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringInSetCount, dailyPartitionStringInSetCount));
        this.dailyPartitionStringInSetCount = dailyPartitionStringInSetCount;
        propagateHierarchyIdToField(dailyPartitionStringInSetCount, "daily_partition_string_in_set_count");
    }

    /**
     * Returns a minimum strings in set percent check.
     * @return Minimum strings in set percent check.
     */
    public ColumnStringInSetPercentCheckSpec getDailyPartitionStringInSetPercent() {
        return dailyPartitionStringInSetPercent;
    }

    /**
     * Sets a new definition of a minimum strings in set percent check.
     * @param dailyPartitionStringInSetPercent Minimum strings in set percent check.
     */
    public void setDailyPartitionStringInSetPercent(ColumnStringInSetPercentCheckSpec dailyPartitionStringInSetPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringInSetPercent, dailyPartitionStringInSetPercent));
        this.dailyPartitionStringInSetPercent = dailyPartitionStringInSetPercent;
        propagateHierarchyIdToField(dailyPartitionStringInSetPercent, "daily_partition_string_in_set_percent");
    }


    /**
     * Returns a minimum string valid dates percent check.
     * @return Minimum string valid dates percent check.
     */
    public ColumnStringValidDatesPercentCheckSpec getDailyPartitionStringValidDatesPercent() {
        return dailyPartitionStringValidDatesPercent;
    }

    /**
     * Sets a new definition of a minimum string valid dates percent check.
     * @param dailyPartitionStringValidDatesPercent Minimum string valid dates percent check.
     */
    public void setDailyPartitionStringValidDatesPercent(ColumnStringValidDatesPercentCheckSpec dailyPartitionStringValidDatesPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidDatesPercent, dailyPartitionStringValidDatesPercent));
        this.dailyPartitionStringValidDatesPercent = dailyPartitionStringValidDatesPercent;
        propagateHierarchyIdToField(dailyPartitionStringValidDatesPercent, "daily_partition_string_valid_dates_percent");
    }

    /**
     * Returns a minimum string valid usa zip code percent check.
     * @return Minimum string valid usa zip code percent  check.
     */
    public ColumnStringValidUsaZipcodePercentCheckSpec getDailyPartitionStringValidUsaZipcodePercent() {
        return dailyPartitionStringValidUsaZipcodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid usa zip code percent check.
     * @param dailyPartitionStringValidUsaZipcodePercent Minimum string valid usa zip code percent check.
     */
    public void setDailyPartitionStringValidUsaZipcodePercent(ColumnStringValidUsaZipcodePercentCheckSpec dailyPartitionStringValidUsaZipcodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidUsaZipcodePercent, dailyPartitionStringValidUsaZipcodePercent));
        this.dailyPartitionStringValidUsaZipcodePercent = dailyPartitionStringValidUsaZipcodePercent;
        propagateHierarchyIdToField(dailyPartitionStringValidUsaZipcodePercent, "daily_partition_string_valid_usa_zipcode_percent");
    }

    /**
     * Returns a minimum string valid USA phone percent check.
     * @return Minimum string valid USA phone percent  check.
     */
    public ColumnStringValidUsaPhonePercentCheckSpec getDailyPartitionStringValidUsaPhonePercent() {
        return dailyPartitionStringValidUsaPhonePercent;
    }

    /**
     * Sets a new definition of a minimum string valid USA phone percent check.
     * @param dailyPartitionStringValidUsaPhonePercent Minimum string valid USA phone percent check.
     */
    public void setDailyPartitionStringValidUsaPhonePercent(ColumnStringValidUsaPhonePercentCheckSpec dailyPartitionStringValidUsaPhonePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidUsaPhonePercent, dailyPartitionStringValidUsaPhonePercent));
        this.dailyPartitionStringValidUsaPhonePercent = dailyPartitionStringValidUsaPhonePercent;
        propagateHierarchyIdToField(dailyPartitionStringValidUsaPhonePercent, "daily_partition_string_valid_usa_phone_percent");
    }

    /**
     * Returns a minimum string valid country code percent check.
     * @return Minimum string valid country code percent  check.
     */
    public ColumnStringValidCountryCodePercentCheckSpec getDailyPartitionStringValidCountryCodePercent() {
        return dailyPartitionStringValidCountryCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid country code percent check.
     * @param dailyPartitionStringValidCountryCodePercent Minimum string valid country code percent check.
     */
    public void setDailyPartitionStringValidCountryCodePercent(ColumnStringValidCountryCodePercentCheckSpec dailyPartitionStringValidCountryCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidCountryCodePercent, dailyPartitionStringValidCountryCodePercent));
        this.dailyPartitionStringValidCountryCodePercent = dailyPartitionStringValidCountryCodePercent;
        propagateHierarchyIdToField(dailyPartitionStringValidCountryCodePercent, "daily_partition_string_valid_country_code_percent");
    }

    /**
     * Returns a minimum string valid currency code percent check.
     * @return Minimum string valid currency code percent  check.
     */
    public ColumnStringValidCurrencyCodePercentCheckSpec getDailyPartitionStringValidCurrencyCodePercent() {
        return dailyPartitionStringValidCurrencyCodePercent;
    }

    /**
     * Sets a new definition of a minimum string valid currency code percent check.
     * @param dailyPartitionStringValidCurrencyCodePercent Minimum string valid currency code percent check.
     */
    public void setDailyPartitionStringValidCurrencyCodePercent(ColumnStringValidCurrencyCodePercentCheckSpec dailyPartitionStringValidCurrencyCodePercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidCurrencyCodePercent, dailyPartitionStringValidCurrencyCodePercent));
        this.dailyPartitionStringValidCurrencyCodePercent = dailyPartitionStringValidCurrencyCodePercent;
        propagateHierarchyIdToField(dailyPartitionStringValidCurrencyCodePercent, "daily_partition_string_valid_currency_code_percent");
    }

    /**
     * Returns a maximum invalid email count check.
     * @return Maximum invalid email count check.
     */
    public ColumnStringInvalidEmailCountCheckSpec getDailyPartitionStringInvalidEmailCount() {
        return dailyPartitionStringInvalidEmailCount;
    }

    /**
     * Sets a new definition of a maximum invalid email count check.
     * @param dailyPartitionStringInvalidEmailCount Maximum invalid email count check.
     */
    public void setDailyPartitionStringInvalidEmailCount(ColumnStringInvalidEmailCountCheckSpec dailyPartitionStringInvalidEmailCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringInvalidEmailCount, dailyPartitionStringInvalidEmailCount));
        this.dailyPartitionStringInvalidEmailCount = dailyPartitionStringInvalidEmailCount;
        propagateHierarchyIdToField(dailyPartitionStringInvalidEmailCount, "daily_partition_string_invalid_email_count");
    }

    /**
     * Returns a minimum valid email percent check.
     * @return Minimum valid email percent check.
     */
    public ColumnStringValidEmailPercentCheckSpec getDailyPartitionValidEmailPercent() {
        return dailyPartitionValidEmailPercent;
    }

    /**
     * Sets a new definition of a minimum valid email percent check.
     * @param dailyPartitionValidEmailPercent Minimum valid email percent check.
     */
    public void setDailyPartitionValidEmailPercent(ColumnStringValidEmailPercentCheckSpec dailyPartitionValidEmailPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValidEmailPercent, dailyPartitionValidEmailPercent));
        this.dailyPartitionValidEmailPercent = dailyPartitionValidEmailPercent;
        propagateHierarchyIdToField(dailyPartitionValidEmailPercent, "daily_partition_valid_email_percent");
    }

    /**
     * Returns a maximum invalid UUID count check.
     * @return Maximum invalid UUID count check.
     */
    public ColumnStringInvalidUuidCountCheckSpec getDailyPartitionStringInvalidUuidCount() {
        return dailyPartitionStringInvalidUuidCount;
    }

    /**
     * Sets a new definition of a maximum invalid UUID count check.
     * @param dailyPartitionStringInvalidUuidCount Maximum invalid UUID count check.
     */
    public void setDailyPartitionStringInvalidUuidCount(ColumnStringInvalidUuidCountCheckSpec dailyPartitionStringInvalidUuidCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringInvalidUuidCount, dailyPartitionStringInvalidUuidCount));
        this.dailyPartitionStringInvalidUuidCount = dailyPartitionStringInvalidUuidCount;
        propagateHierarchyIdToField(dailyPartitionStringInvalidUuidCount, "daily_partition_string_invalid_uuid_count");
    }

    /**
     * Returns a minimum valid UUID percent check.
     * @return Minimum valid UUID percent check.
     */
    public ColumnStringValidUuidPercentCheckSpec getDailyPartitionValidUuidPercent() {
        return dailyPartitionValidUuidPercent;
    }

    /**
     * Sets a new definition of a minimum valid UUID percent check.
     * @param dailyPartitionValidUuidPercent Minimum valid UUID percent check.
     */
    public void setDailyPartitionValidUuidPercent(ColumnStringValidUuidPercentCheckSpec dailyPartitionValidUuidPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionValidUuidPercent, dailyPartitionValidUuidPercent));
        this.dailyPartitionValidUuidPercent = dailyPartitionValidUuidPercent;
        propagateHierarchyIdToField(dailyPartitionValidUuidPercent, "daily_partition_valid_uuid_percent");
    }

    /**
     * Returns a maximum invalid IP4 address count check.
     * @return Maximum invalid IP4 address count check.
     */
    public ColumnStringInvalidIp4AddressCountCheckSpec getDailyPartitionStringInvalidIp4AddressCount() {
        return dailyPartitionStringInvalidIp4AddressCount;
    }

    /**
     * Sets a new definition of a maximum invalid IP4 address count check.
     * @param dailyPartitionStringInvalidIp4AddressCount Maximum invalid IP4 address count check.
     */
    public void setDailyPartitionStringInvalidIp4AddressCount(ColumnStringInvalidIp4AddressCountCheckSpec dailyPartitionStringInvalidIp4AddressCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringInvalidIp4AddressCount, dailyPartitionStringInvalidIp4AddressCount));
        this.dailyPartitionStringInvalidIp4AddressCount = dailyPartitionStringInvalidIp4AddressCount;
        propagateHierarchyIdToField(dailyPartitionStringInvalidIp4AddressCount, "daily_partition_string_invalid_ip4_address_count");
    }

    /**
     * Returns a minimum valid IP4 address percent check.
     * @return Minimum valid IP4 address percent check.
     */
    public ColumnStringValidIp4AddressPercentCheckSpec getDailyPartitionStringValidIp4AddressPercent() {
        return dailyPartitionStringValidIp4AddressPercent;
    }

    /**
     * Sets a new definition of a minimum valid IP4 address percent check.
     * @param dailyPartitionStringValidIp4AddressPercent Minimum valid IP4 address percent check.
     */
    public void setDailyPartitionStringValidIp4AddressPercent(ColumnStringValidIp4AddressPercentCheckSpec dailyPartitionStringValidIp4AddressPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringValidIp4AddressPercent, dailyPartitionStringValidIp4AddressPercent));
        this.dailyPartitionStringValidIp4AddressPercent = dailyPartitionStringValidIp4AddressPercent;
        propagateHierarchyIdToField(dailyPartitionStringValidIp4AddressPercent, "daily_partition_string_valid_ip4_address_percent");
    }

    /**
     * Returns a maximum not match regex count check.
     * @return Maximum not match regex count check.
     */
    public ColumnStringNotMatchRegexCountCheckSpec getDailyPartitionStringNotMatchRegexCount() {
        return dailyPartitionStringNotMatchRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match regex count check.
     * @param dailyPartitionStringNotMatchRegexCount Maximum not match regex count check.
     */
    public void setDailyPartitionStringNotMatchRegexCount(ColumnStringNotMatchRegexCountCheckSpec dailyPartitionStringNotMatchRegexCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringNotMatchRegexCount, dailyPartitionStringNotMatchRegexCount));
        this.dailyPartitionStringNotMatchRegexCount = dailyPartitionStringNotMatchRegexCount;
        propagateHierarchyIdToField(dailyPartitionStringNotMatchRegexCount, "daily_partition_string_not_match_regex_count");
    }

    /**
     * Returns a minimum match regex percent check.
     * @return Minimum match regex percent check.
     */
    public ColumnStringRegexMatchPercentCheckSpec getDailyPartitionStringMatchRegexPercent() {
        return dailyPartitionStringMatchRegexPercent;
    }

    /**
     * Sets a new definition of a minimum match regex percent check.
     * @param dailyPartitionStringMatchRegexPercent Minimum match regex percent check.
     */
    public void setDailyPartitionStringMatchRegexPercent(ColumnStringRegexMatchPercentCheckSpec dailyPartitionStringMatchRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringMatchRegexPercent, dailyPartitionStringMatchRegexPercent));
        this.dailyPartitionStringMatchRegexPercent = dailyPartitionStringMatchRegexPercent;
        propagateHierarchyIdToField(dailyPartitionStringMatchRegexPercent, "daily_partition_string_match_regex_percent");
    }

    /**
     * Returns a maximum not match date regex count check.
     * @return Maximum not match date regex count check.
     */
    public ColumnStringNotMatchDateRegexCountCheckSpec getDailyPartitionStringNotMatchDateRegexCount() {
        return dailyPartitionStringNotMatchDateRegexCount;
    }

    /**
     * Sets a new definition of a maximum not match date regex count check.
     * @param dailyPartitionStringNotMatchDateRegexCount Maximum not match date regex count check.
     */
    public void setDailyPartitionStringNotMatchDateRegexCount(ColumnStringNotMatchDateRegexCountCheckSpec dailyPartitionStringNotMatchDateRegexCount) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringNotMatchDateRegexCount, dailyPartitionStringNotMatchDateRegexCount));
        this.dailyPartitionStringNotMatchDateRegexCount = dailyPartitionStringNotMatchDateRegexCount;
        propagateHierarchyIdToField(dailyPartitionStringNotMatchDateRegexCount, "daily_partition_string_not_match_date_regex_count");
    }

    /**
     * Returns a maximum match date regex percent check.
     * @return Maximum match date regex percent check.
     */
    public ColumnStringMatchDateRegexPercentCheckSpec getDailyPartitionStringMatchDateRegexPercent() {
        return dailyPartitionStringMatchDateRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match date regex percent check.
     * @param dailyPartitionStringMatchDateRegexPercent Maximum match date regex percent check.
     */
    public void setDailyPartitionStringMatchDateRegexPercent(ColumnStringMatchDateRegexPercentCheckSpec dailyPartitionStringMatchDateRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringMatchDateRegexPercent, dailyPartitionStringMatchDateRegexPercent));
        this.dailyPartitionStringMatchDateRegexPercent = dailyPartitionStringMatchDateRegexPercent;
        propagateHierarchyIdToField(dailyPartitionStringMatchDateRegexPercent, "daily_partition_string_match_date_regex_percent");
    }

    /**
     * Returns a maximum match name regex percent check.
     * @return Maximum match name regex percent check.
     */
    public ColumnStringMatchNameRegexPercentCheckSpec getDailyPartitionStringMatchNameRegexPercent() {
        return dailyPartitionStringMatchNameRegexPercent;
    }

    /**
     * Sets a new definition of a maximum match name regex percent check.
     * @param dailyPartitionStringMatchNameRegexPercent Maximum match name regex percent check.
     */
    public void setDailyPartitionStringMatchNameRegexPercent(ColumnStringMatchNameRegexPercentCheckSpec dailyPartitionStringMatchNameRegexPercent) {
        this.setDirtyIf(!Objects.equals(this.dailyPartitionStringMatchNameRegexPercent, dailyPartitionStringMatchNameRegexPercent));
        this.dailyPartitionStringMatchNameRegexPercent = dailyPartitionStringMatchNameRegexPercent;
        propagateHierarchyIdToField(dailyPartitionStringMatchNameRegexPercent, "daily_partition_string_match_name_regex_percent");
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
