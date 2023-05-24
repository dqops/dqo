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

package ai.dqo.data.incidents.services.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.TreeMap;

/**
 * Model that returns a daily histogram of the data quality issue occurrences related to a data quality incident.
 * The dates are using the default timezone of the DQO server.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentIssueHistogramModel {
    /**
     * A map of the numbers of data quality issues per day, the day uses the DQO server timezone.
     */
    @JsonPropertyDescription("A map of the numbers of data quality issues per day, the day uses the DQO server timezone.")
    private TreeMap<LocalDate, IncidentDailyIssuesCount> days = new TreeMap<>();

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
     * Adds missing days between already present dates to the tree map, with 0 counts.
     */
    public void addMissingDaysInRange() {
        if (this.days.isEmpty()) {
            return;
        }

        LocalDate firstDate = this.days.firstKey();
        LocalDate lastDate = this.days.lastKey();

        for (LocalDate date = firstDate.plus(1L, ChronoUnit.DAYS); date.isBefore(lastDate);
             date = date.plus(1L, ChronoUnit.DAYS)) {
            if (this.days.containsKey(date)) {
                continue;
            }

            this.days.put(date, new IncidentDailyIssuesCount());
        }
    }
}
