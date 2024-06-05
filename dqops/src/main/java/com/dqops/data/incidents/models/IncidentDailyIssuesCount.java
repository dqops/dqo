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
package com.dqops.data.incidents.models;

import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * A model that stores a daily number of incidents.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class IncidentDailyIssuesCount {
    /**
     * The number of failed data quality checks that generated a warning severity data quality issue.
     */
    @JsonPropertyDescription("The number of failed data quality checks that generated a warning severity data quality issue.")
    private int warnings = 0;

    /**
     * The number of failed data quality checks that generated an error severity data quality issue.
     */
    @JsonPropertyDescription("The number of failed data quality checks that generated an error severity data quality issue.")
    private int errors = 0;

    /**
     * The number of failed data quality checks that generated a fatal severity data quality issue.
     */
    @JsonPropertyDescription("The number of failed data quality checks that generated a fatal severity data quality issue.")
    private int fatals = 0;

    /**
     * The total count of failed data quality checks on this day.
     */
    @JsonPropertyDescription("The total count of failed data quality checks on this day.")
    private int totalCount = 0;

    /**
     * Increment the count of failed data quality checks for a given severity level.
     * @param severityLevel Severity level of a failed data quality check.
     */
    public void incrementForIssueSeverity(int severityLevel) {
        this.totalCount++;

        if (severityLevel == 1) {
            this.warnings++;
        }
        else if (severityLevel == 2) {
            this.errors++;
        }
        else if (severityLevel == 3) {
            this.fatals++;
        }
    }

    /**
     * Sample factory for an incident model.
     */
    public static class IncidentDailyIssuesCountSampleFactory implements SampleValueFactory<IncidentDailyIssuesCount> {
        @Override
        public IncidentDailyIssuesCount createSample() {
            return new IncidentDailyIssuesCount() {{
                setTotalCount(10);
                setWarnings(2);
                setErrors(7);
                setFatals(1);
            }};
        }
    }
}
