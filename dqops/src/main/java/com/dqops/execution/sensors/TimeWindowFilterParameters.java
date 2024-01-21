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
package com.dqops.execution.sensors;

import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import picocli.CommandLine;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * Incremental partitioned checks time window filter that should be applied by the query.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel(value = "TimeWindowFilterParameters", description = "Time window configuration for partitioned checks (the number of recent days or months to analyze in an incremental mode) or an absolute time range to analyze.")
public class TimeWindowFilterParameters implements Cloneable {
    /**
     * Number of recent days to analyze incrementally by daily partitioned checks, using the database time zone.
     */
    @CommandLine.Option(names = {"--daily-partitioning-recent-days"}, description = "The number of recent days to analyze incrementally by daily partitioned data quality checks.")
    @JsonPropertyDescription("The number of recent days to analyze incrementally by daily partitioned data quality checks.")
    private Integer dailyPartitioningRecentDays;

    /**
     * Boolean filter - to skip current date, using the database's time zone.
     */
    @CommandLine.Option(names = {"--daily-partitioning-include-today"}, description = "Analyze also today and later days when running daily partitioned checks. " +
            "By default, daily partitioned checks will not analyze today and future dates. Setting true will disable filtering the end dates.", defaultValue = "false")
    @JsonPropertyDescription("Analyze also today and later days when running daily partitioned checks. " +
            "By default, daily partitioned checks will not analyze today and future dates. Setting true will disable filtering the end dates.")
    private Boolean dailyPartitioningIncludeToday;

    /**
     * Number of recent months to filter, using the database time zone.
     */
    @CommandLine.Option(names = {"--monthly-partitioning-recent-months"}, description = "The number of recent months to analyze incrementally by monthly partitioned data quality checks.")
    @JsonPropertyDescription("The number of recent months to analyze incrementally by monthly partitioned data quality checks.")
    private Integer monthlyPartitioningRecentMonths;

    /**
     * Boolean filter - to skip the current month, using the database's time zone.
     */
    @CommandLine.Option(names = {"--monthly-partitioning-include-current-month"}, description = "Analyze also the current month and later months when running monthly partitioned checks. " +
            "By default, monthly partitioned checks will not analyze the current month and future months. Setting true will disable filtering the end dates.", defaultValue = "false")
    @JsonPropertyDescription("Analyze also the current month and later months when running monthly partitioned checks. " +
            "By default, monthly partitioned checks will not analyze the current month and future months. Setting true will disable filtering the end dates.")
    private Boolean monthlyPartitioningIncludeCurrentMonth;

    /**
     * Start date to analyze (inclusive), without the time zone (uses the database default time zone or filters non-timezoned date and datetime fields).
     */
    @CommandLine.Option(names = {"--from-date"}, description = "Analyze the data since the given date (inclusive). The date should be an ISO 8601 date (yyyy-MM-dd). " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the beginning date overrides recent days and recent months.")
    @JsonPropertyDescription("Analyze the data since the given date (inclusive). The date should be an ISO 8601 date (yyyy-MM-dd). " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the beginning date overrides recent days and recent months.")
    private LocalDate fromDate;

    /**
     * Start date and time to analyze (inclusive), without the time zone (uses the database default time zone or filters non-timezoned date and datetime fields).
     */
    @CommandLine.Option(names = {"--from-date-time"}, description = "Analyze the data since the given date and time (inclusive). The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH:mm:ss). " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the beginning date and time overrides recent days and recent months.")
    @JsonPropertyDescription("Analyze the data since the given date and time (inclusive). The date and time should be an ISO 8601 local date and time without the time zone (yyyy-MM-dd HH:mm:ss). " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the beginning date and time overrides recent days and recent months.")
    private LocalDateTime fromDateTime;

    /**
     * Start date and time to analyze (inclusive), using an absolute time with a time zone offset.
     */
    @CommandLine.Option(names = {"--from-date-time-offset"}, description = "Analyze the data since the given date and time with a time zone offset (inclusive). " +
            "The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH:mm:ss). For example: 2023-02-20 14:10:00+02. " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the beginning date and time overrides recent days and recent months.")
    @JsonPropertyDescription("Analyze the data since the given date and time with a time zone offset (inclusive). " +
            "The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH:mm:ss). For example: 2023-02-20 14:10:00+02. " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the beginning date and time overrides recent days and recent months.")
    private OffsetDateTime fromDateTimeOffset;

    /**
     * End date to analyze (exclusive, less than), without the time zone (uses the database default time zone or filters non-timezoned date and datetime fields).
     */
    @CommandLine.Option(names = {"--to-date"}, description = "Analyze the data until the given date (exclusive, the given date and the following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the end date overrides the parameters to disable analyzing today or the current month.")
    @JsonPropertyDescription("Analyze the data until the given date (exclusive, the given date and the following dates are not analyzed). The date should be an ISO 8601 date (YYYY-MM-DD). " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the end date overrides the parameters to disable analyzing today or the current month.")
    private LocalDate toDate;

    /**
     * End date and time to analyze (exclusive, less than), without the time zone (uses the database default time zone or filters non-timezoned date and datetime fields).
     */
    @CommandLine.Option(names = {"--to-date-time"}, description = "Analyze the data until the given date and time (exclusive). The date should be an ISO 8601 date (yyyy-MM-dd). " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the end date and time overrides the parameters to disable analyzing today or the current month.")
    @JsonPropertyDescription("Analyze the data until the given date and time (exclusive). The date should be an ISO 8601 date (yyyy-MM-dd). " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the end date and time overrides the parameters to disable analyzing today or the current month.")
    private LocalDateTime toDateTime;

    /**
     * End date and time to analyze (inclusive, less than), using an absolute time with a time zone offset.
     */
    @CommandLine.Option(names = {"--to-date-time-offset"}, description = "Analyze the data until the given date and time with a time zone offset (exclusive). " +
            "The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH:mm:ss). For example: 2023-02-20 14:10:00+02. " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the end date and time overrides the parameters to disable analyzing today or the current month.")
    @JsonPropertyDescription("Analyze the data until the given date and time with a time zone offset (exclusive). " +
            "The date and time should be an ISO 8601 date and time followed by a time zone offset (yyyy-MM-dd HH:mm:ss). For example: 2023-02-20 14:10:00+02. " +
            "The analyzed table must have the timestamp column properly configured, it is the column that is used for filtering the date and time ranges. " +
            "Setting the end date and time overrides the parameters to disable analyzing today or the current month.")
    private OffsetDateTime toDateTimeOffset;

    /**
     * Creates a copy of the configuration, copying (importing) also filters provided by a user.
     * @param userFilterParameters User filter parameters.
     * @param partitionedChecksTimeScale Time series gradient for daily/monthly partitioned data. Should be null for non partitioned checks.
     * @return New object with user filters applied.
     */
    public TimeWindowFilterParameters withUserFilters(TimeWindowFilterParameters userFilterParameters,
                                                      TimePeriodGradient partitionedChecksTimeScale) {
        if (userFilterParameters == null) {
            return this;
        }

        TimeWindowFilterParameters cloned = this.clone();

        if (userFilterParameters.fromDate != null || userFilterParameters.fromDateTime != null || userFilterParameters.fromDateTimeOffset != null) {
            cloned.fromDate = userFilterParameters.fromDate;
            cloned.fromDateTime = userFilterParameters.fromDateTime;
            cloned.fromDateTimeOffset = userFilterParameters.fromDateTimeOffset;
            cloned.dailyPartitioningRecentDays = null;
            cloned.monthlyPartitioningRecentMonths = null;
        }

        if (userFilterParameters.toDate != null || userFilterParameters.toDateTime != null || userFilterParameters.toDateTimeOffset != null) {
            cloned.toDate = userFilterParameters.toDate;
            cloned.toDateTime = userFilterParameters.toDateTime;
            cloned.toDateTimeOffset = userFilterParameters.toDateTimeOffset;
            cloned.dailyPartitioningIncludeToday = null;
            cloned.monthlyPartitioningIncludeCurrentMonth = null;
        }

        if (partitionedChecksTimeScale == TimePeriodGradient.day) {
            if (userFilterParameters.dailyPartitioningRecentDays != null) {
                cloned.dailyPartitioningRecentDays = userFilterParameters.dailyPartitioningRecentDays;
            }
            if (userFilterParameters.dailyPartitioningIncludeToday != null) {
                cloned.dailyPartitioningIncludeToday = userFilterParameters.dailyPartitioningIncludeToday;
            }
        }

        if (partitionedChecksTimeScale == TimePeriodGradient.month) {
            if (userFilterParameters.monthlyPartitioningRecentMonths != null) {
                cloned.monthlyPartitioningRecentMonths = userFilterParameters.monthlyPartitioningRecentMonths;
            }
            if (userFilterParameters.monthlyPartitioningIncludeCurrentMonth != null) {
                cloned.monthlyPartitioningIncludeCurrentMonth = userFilterParameters.monthlyPartitioningIncludeCurrentMonth;
            }
        }

        return cloned;
    }

    /**
     * Detects if any filter parameter is set, so the user did not send an empty filter object.
     * If DQOps receives an empty time window, the default time window configured on the table is used.
     * @return True when any filters are applied, false when it is an empty (dummy) filter, to be ignored.
     */
    public boolean hasAnyParametersApplied() {
        return dailyPartitioningRecentDays != null ||
                dailyPartitioningIncludeToday != null ||
                monthlyPartitioningRecentMonths != null ||
                monthlyPartitioningIncludeCurrentMonth != null ||
                fromDate != null ||
                fromDateTime != null ||
                fromDateTimeOffset != null ||
                toDate != null ||
                toDateTime != null ||
                toDateTimeOffset != null;
    }

    /**
     * Creates and returns a copy of this object (deep clone).
     */
    @Override
    public TimeWindowFilterParameters clone()  {
        try {
            TimeWindowFilterParameters cloned = (TimeWindowFilterParameters) super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
