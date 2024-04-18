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

package com.dqops.data.checkresults.models;

import com.dqops.checks.CheckType;
import com.dqops.data.incidents.models.IncidentDailyIssuesCount;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Model that returns histograms of the data quality issue occurrences related to a data quality incident.
 * The dates in the daily histogram are using the default timezone of the DQOps server.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentIssueHistogramModel {
    /**
     * True when this data quality incident is based on data quality issues from profiling checks within the filters applied to search for linked data quality issues.
     */
    @JsonPropertyDescription("True when this data quality incident is based on data quality issues from profiling checks within the filters applied to search for linked data quality issues.")
    private boolean hasProfilingIssues;

    /**
     * True when this data quality incident is based on data quality issues from daily monitoring checks within the filters applied to search for linked data quality issues.
     */
    @JsonPropertyDescription("True when this data quality incident is based on data quality issues from daily monitoring checks within the filters applied to search for linked data quality issues.")
    private boolean hasDailyMonitoringIssues;

    /**
     * True when this data quality incident is based on data quality issues from monthly monitoring checks within the filters applied to search for linked data quality issues.
     */
    @JsonPropertyDescription("True when this data quality incident is based on data quality issues from monthly monitoring checks within the filters applied to search for linked data quality issues.")
    private boolean hasMonthlyMonitoringIssues;

    /**
     * True when this data quality incident is based on data quality issues from daily partitioned checks within the filters applied to search for linked data quality issues.
     */
    @JsonPropertyDescription("True when this data quality incident is based on data quality issues from daily partitioned checks within the filters applied to search for linked data quality issues.")
    private boolean hasDailyPartitionedIssues;

    /**
     * True when this data quality incident is based on data quality issues from monthly partitioned checks within the filters applied to search for linked data quality issues.
     */
    @JsonPropertyDescription("True when this data quality incident is based on data quality issues from monthly partitioned checks within the filters applied to search for linked data quality issues.")
    private boolean hasMonthlyPartitionedIssues;

    /**
     * A map of the numbers of data quality issues per day, the day uses the DQOps server timezone.
     */
    @JsonPropertyDescription("A map of the numbers of data quality issues per day, the day uses the DQOps server timezone.")
    private Map<LocalDate, IncidentDailyIssuesCount> days = new TreeMap<>();

    /**
     * A map of column names with the most data quality issues related to the incident. The map returns the count of issues as the value.
     */
    @JsonPropertyDescription("A map of column names with the most data quality issues related to the incident. The map returns the count of issues as the value.")
    private Map<String, Integer> columns = new LinkedHashMap<>();

    /**
     * A map of data quality check names with the most data quality issues related to the incident. The map returns the count of issues as the value.
     */
    @JsonPropertyDescription("A map of data quality check names with the most data quality issues related to the incident. The map returns the count of issues as the value.")
    private Map<String, Integer> checks = new LinkedHashMap<>();

    /**
     * Turns on a flag for profiling, monitoring or partitioned checks when an issue in that type was detected.
     * @param checkType Check type.
     * @param timeScale Check time scale.
     */
    public void markCheckType(CheckType checkType, TimePeriodGradient timeScale) {
        if (checkType == null) {
            return;
        }

        switch (checkType) {
            case profiling:
                this.hasProfilingIssues = true;
                break;

            case monitoring:
                switch (timeScale) {
                    case day:
                        this.hasDailyMonitoringIssues = true;
                        break;
                    case month:
                        this.hasMonthlyMonitoringIssues = true;
                        break;
                }
                break;

            case partitioned:
                switch (timeScale) {
                    case day:
                        this.hasDailyPartitionedIssues = true;
                        break;
                    case month:
                        this.hasMonthlyPartitionedIssues = true;
                        break;
                }
                break;
        }
    }

    /**
     * Increments a count of data quality issues for a date.
     * @param date Local date when the issue was detected.
     * @param severity Severity level of a failed data quality check.
     */
    public void incrementSeverityForDay(LocalDate date, int severity) {
        IncidentDailyIssuesCount incidentDailyIssuesCount = this.days.get(date);
        if (incidentDailyIssuesCount == null) {
            incidentDailyIssuesCount = new IncidentDailyIssuesCount();
            this.days.put(date, incidentDailyIssuesCount);
        }

        incidentDailyIssuesCount.incrementForIssueSeverity(severity);
    }

    /**
     * Increment the count of issues for a column.
     * @param columnName Column name.
     */
    public void incrementCountForColumn(String columnName) {
        Integer currentValue = this.columns.get(columnName);
        if (currentValue == null) {
            currentValue = 1;
        }
        else {
            currentValue++;
        }

        this.columns.put(columnName, currentValue);
    }

    /**
     * Increment the count of issues for a check name.
     * @param checkName Check name.
     */
    public void incrementCountForCheck(String checkName) {
        Integer currentValue = this.checks.get(checkName);
        if (currentValue == null) {
            currentValue = 1;
        }
        else {
            currentValue++;
        }

        this.checks.put(checkName, currentValue);
    }

    /**
     * Adds missing days between already present dates to the tree map, with 0 counts.
     */
    public void addMissingDaysInRange() {
        if (this.days.isEmpty()) {
            return;
        }

        LocalDate firstDate;
        LocalDate lastDate;

        if (this.days instanceof TreeMap) {
            firstDate = ((TreeMap<LocalDate, IncidentDailyIssuesCount>) this.days).firstKey();
            lastDate = ((TreeMap<LocalDate, IncidentDailyIssuesCount>) this.days).lastKey();
        } else {
            List<LocalDate> daysKeysSortedList = this.days.keySet().stream().sorted().collect(Collectors.toList());
            firstDate = daysKeysSortedList.get(0);
            lastDate = daysKeysSortedList.get(daysKeysSortedList.size() - 1);
        }

        for (LocalDate date = firstDate.plus(1L, ChronoUnit.DAYS); date.isBefore(lastDate);
             date = date.plus(1L, ChronoUnit.DAYS)) {
            if (this.days.containsKey(date)) {
                continue;
            }

            this.days.put(date, new IncidentDailyIssuesCount());
        }
    }

    /**
     * Sorts the column histogram by the number of issue occurrence and truncates it to the <code>histogramSize</code>.
     * @param histogramSize Histogram size to truncate to.
     */
    public void sortAndTruncateColumnHistogram(int histogramSize) {
        this.columns = findTop(this.columns, histogramSize);
    }

    /**
     * Sorts the check histogram by the number of issue occurrence and truncates it to the <code>histogramSize</code>.
     * @param histogramSize Histogram size to truncate to.
     */
    public void sortAndTruncateCheckHistogram(int histogramSize) {
        this.checks = findTop(this.checks, histogramSize);
    }

    /**
     * Sorts and finds top N (<code>histogramSize</code>) elements.
     * @param map Source map.
     * @param histogramSize Histogram size.
     * @return New hashmap that is sorted and truncated.
     */
    protected LinkedHashMap<String, Integer> findTop(Map<String, Integer> map, int histogramSize) {
        final LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(Comparator.comparingInt((Map.Entry<String, Integer> entry) -> entry.getValue()).reversed())
                .limit(histogramSize)
                .collect(Collectors.toList())
                .forEach(kv -> result.put(kv.getKey(), kv.getValue()));

        return result;
    }
}
