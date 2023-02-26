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
package ai.dqo.execution.sensors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class TimeWindowFilterParameters implements Cloneable {
    /**
     * Number of recent days to filter, using the database time zone.
     */
    private Integer dailyPartitioningRecentDays;

    /**
     * Boolean filter - to skip current date, using the database's time zone.
     */
    private Boolean dailyPartitioningIncludeToday;

    /**
     * Number of recent months to filter, using the database time zone.
     */
    private Integer monthlyPartitioningRecentMonths;

    /**
     * Boolean filter - to skip the current month, using the database's time zone.
     */
    private Boolean monthlyPartitioningIncludeCurrentMonth;

    /**
     * Start date to analyze (inclusive), without the time zone (uses the database default time zone or filters non-timezoned date and datetime fields).
     */
    private LocalDate fromDate;

    /**
     * Start date and time to analyze (inclusive), without the time zone (uses the database default time zone or filters non-timezoned date and datetime fields).
     */
    private LocalDateTime fromDateTime;

    /**
     * Start date and time to analyze (inclusive), using an absolute time with a time zone offset.
     */
    private OffsetDateTime fromDateTimeOffset;

    /**
     * End date to analyze (exclusive, less than), without the time zone (uses the database default time zone or filters non-timezoned date and datetime fields).
     */
    private LocalDate toDate;

    /**
     * End date and time to analyze (exclusive, less than), without the time zone (uses the database default time zone or filters non-timezoned date and datetime fields).
     */
    private LocalDateTime toDateTime;

    /**
     * End date and time to analyze (inclusive, less than), using an absolute time with a time zone offset.
     */
    private OffsetDateTime toDateTimeOffset;

    /**
     * Creates a copy of the configuration, copying (importing) also filters provided by a user.
     * @param userFilterParameters User filter parameters.
     * @return New object with user filters applied.
     */
    public TimeWindowFilterParameters withUserFilters(TimeWindowFilterParameters userFilterParameters) {
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

        if (userFilterParameters.dailyPartitioningRecentDays != null) {
            cloned.dailyPartitioningRecentDays = userFilterParameters.dailyPartitioningRecentDays;
        }
        if (userFilterParameters.dailyPartitioningIncludeToday != null) {
            cloned.dailyPartitioningIncludeToday = userFilterParameters.dailyPartitioningIncludeToday;
        }

        if (userFilterParameters.monthlyPartitioningRecentMonths != null) {
            cloned.monthlyPartitioningRecentMonths = userFilterParameters.monthlyPartitioningRecentMonths;
        }
        if (userFilterParameters.monthlyPartitioningIncludeCurrentMonth != null) {
            cloned.monthlyPartitioningIncludeCurrentMonth = userFilterParameters.monthlyPartitioningIncludeCurrentMonth;
        }

        return cloned;
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
