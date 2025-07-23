/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.incidents.models;

import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * A model that stores a daily number of incidents.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class HistogramDailyIssuesCount {
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
    public static class IncidentDailyIssuesCountSampleFactory implements SampleValueFactory<HistogramDailyIssuesCount> {
        @Override
        public HistogramDailyIssuesCount createSample() {
            return new HistogramDailyIssuesCount() {{
                setTotalCount(10);
                setWarnings(2);
                setErrors(7);
                setFatals(1);
            }};
        }
    }
}
