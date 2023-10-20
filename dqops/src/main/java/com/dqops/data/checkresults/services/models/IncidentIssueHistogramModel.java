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

package com.dqops.data.checkresults.services.models;

import com.dqops.data.incidents.services.models.IncidentDailyIssuesCount;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.opencensus.trace.Link;
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
public class IncidentIssueHistogramModel {
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

    public TreeMap<LocalDate, IncidentDailyIssuesCount> getDays() {
        return (TreeMap<LocalDate, IncidentDailyIssuesCount>) days;
    }

    public void setDays(TreeMap<LocalDate, IncidentDailyIssuesCount> days) {
        this.days = days;
    }

    public LinkedHashMap<String, Integer> getColumns() {
        return (LinkedHashMap<String, Integer>) columns;
    }

    public void setColumns(LinkedHashMap<String, Integer> columns) {
        this.columns = columns;
    }

    public LinkedHashMap<String, Integer> getChecks() {
        return (LinkedHashMap<String, Integer>) checks;
    }

    public void setChecks(LinkedHashMap<String, Integer> checks) {
        this.checks = checks;
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

        LocalDate firstDate = this.getDays().firstKey();
        LocalDate lastDate = this.getDays().lastKey();

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
