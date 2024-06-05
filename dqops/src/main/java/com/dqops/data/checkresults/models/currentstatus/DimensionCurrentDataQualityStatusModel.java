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

package com.dqops.data.checkresults.models.currentstatus;

import com.dqops.rules.RuleSeverityLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;
import java.util.Objects;

/**
 * A model that describes the current data quality status for a single data quality dimension.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "DimensionCurrentDataQualityStatusModel", description = "The summary of the current data quality status for one data quality dimension")
@Data
public class DimensionCurrentDataQualityStatusModel {
    /**
     * Data quality dimension name. The most popular dimensions are: Completeness, Uniqueness, Timeliness, Validity, Consistency, Accuracy, Availability.
     */
    @JsonPropertyDescription("Data quality dimension name. The most popular dimensions are: Completeness, Uniqueness, Timeliness, Validity, Consistency, Accuracy, Availability.")
    private String dimension;

    /**
     * The most recent data quality issue severity for this data quality dimension. When the table is monitored using data grouping, it is the highest issue severity of all recently analyzed data groups.
     * For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.
     */
    @JsonPropertyDescription("The most recent data quality issue severity for this table. When the table is monitored using data grouping, it is the highest issue severity of all recently analyzed data groups. " +
            "For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.")
    private RuleSeverityLevel currentSeverity;

    /**
     * The highest severity of previous executions of this data quality issue in the analyzed time range.
     * It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore.
     * For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.
     */
    @JsonPropertyDescription("The highest severity of previous executions of this data quality issue in the analyzed time range. " +
            "It can be different from the *current_severity* if the data quality issue was solved and the most recently data quality issue did not detect it anymore. " +
            "For partitioned checks, this field returns the same value as the *current_severity*, because data quality issues in older partitions are still valid.")
    private RuleSeverityLevel highestHistoricalSeverity;

    /**
     * The UTC timestamp when the most recent data quality check was executed on the table for one data quality dimension.
     */
    @JsonPropertyDescription("The UTC timestamp when the most recent data quality check was executed on the table.")
    private Instant lastCheckExecutedAt;

    /**
     * The total number of most recent checks that were executed on the table for one data quality dimension. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.
     */
    @JsonPropertyDescription("The total number of most recent checks that were executed on the table for one data quality dimension. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.")
    private int executedChecks;

    /**
     * The number of most recent valid data quality checks that passed without raising any issues.
     */
    @JsonPropertyDescription("The number of most recent valid data quality checks that passed without raising any issues.")
    private int validResults;

    /**
     * The number of most recent data quality checks that failed by raising a warning severity data quality issue.
     */
    @JsonPropertyDescription("The number of most recent data quality checks that failed by raising a warning severity data quality issue.")
    private int warnings;

    /**
     * The number of most recent data quality checks that failed by raising an error severity data quality issue.
     */
    @JsonPropertyDescription("The number of most recent data quality checks that failed by raising an error severity data quality issue.")
    private int errors;

    /**
     * The number of most recent data quality checks that failed by raising a fatal severity data quality issue.
     */
    @JsonPropertyDescription("The number of most recent data quality checks that failed by raising a fatal severity data quality issue.")
    private int fatals;

    /**
     * The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps,
     * invalid queries in data quality sensors or invalid python rules.
     * When an execution error is reported, the configuration of a data quality check on a table must be updated.
     */
    @JsonPropertyDescription("The number of data quality check execution errors that were reported due to access issues to the data source, " +
            "invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. " +
            "When an execution error is reported, the configuration of a data quality check on a table must be updated.")
    private int executionErrors;

    /**
     * Data quality KPI score for the table, measured as a percentage of passed data quality checks.
     * DQOps counts data quality issues at a warning severity level as passed checks. The data quality KPI score is a value in the range 0..100.
     */
    @JsonPropertyDescription("Data quality KPI score for the data quality dimension, measured as a percentage of passed data quality checks. " +
            "DQOps counts data quality issues at a warning severity level as passed checks. The data quality KPI score is a value in the range 0..100.")
    private Double dataQualityKpi;

    /**
     * Calculates a data quality KPI score for a data quality dimension.
     */
    public void calculateDataQualityKpiScore() {
        int totalExecutedChecksWithNoExecutionErrors = this.getValidResults() + this.getWarnings() + this.getErrors() + this.getFatals();
        Double dataQualityKpi = totalExecutedChecksWithNoExecutionErrors > 0 ?
                (this.getValidResults() + this.getWarnings()) * 100.0 / totalExecutedChecksWithNoExecutionErrors : null;
        this.setDataQualityKpi(dataQualityKpi);
    }

    /**
     * Appends the results from one data quality check.
     * @param checkStatusModel Data quality check result model.
     */
    public void appendCheckResult(CheckCurrentDataQualityStatusModel checkStatusModel) {
        if (this.currentSeverity == null ||
                (checkStatusModel.getCurrentSeverity() != null && this.currentSeverity.getSeverity() < checkStatusModel.getCurrentSeverity().getSeverity() &&
                        checkStatusModel.getCurrentSeverity().getSeverity() != 4)) {
            this.currentSeverity = RuleSeverityLevel.fromCheckSeverity(checkStatusModel.getCurrentSeverity());
        }

        if (this.highestHistoricalSeverity == null ||
                (checkStatusModel.getHighestHistoricalSeverity() != null &&
                        this.highestHistoricalSeverity.getSeverity() < checkStatusModel.getHighestHistoricalSeverity().getSeverity())) {
            this.highestHistoricalSeverity = checkStatusModel.getHighestHistoricalSeverity();
        }

        if (this.lastCheckExecutedAt == null ||
                (checkStatusModel.getLastExecutedAt() != null && checkStatusModel.getLastExecutedAt().isAfter(this.lastCheckExecutedAt))) {
            this.lastCheckExecutedAt = checkStatusModel.getLastExecutedAt();
        }

        this.executedChecks++; // we count only the current status (the last executed check), maybe we should count executed checks also for partitioned checks
        if (checkStatusModel.getCurrentSeverity() != null) {
            switch (checkStatusModel.getCurrentSeverity()) {
                case valid:
                    this.validResults++;
                    break;

                case warning:
                    this.warnings++;
                    break;

                case error:
                    this.errors++;
                    break;

                case fatal:
                    this.fatals++;
                    break;

                case execution_error:
                    this.executionErrors++;
                    break;
            }
        }
    }

    /**
     * Appends results from the dimension on another column.
     * @param columnDimensionModel The results for the same dimension, but on a different column.
     */
    public void appendResults(DimensionCurrentDataQualityStatusModel columnDimensionModel) {
        assert Objects.equals(this.dimension, columnDimensionModel.dimension);

        if (this.currentSeverity == null ||
                (columnDimensionModel.getCurrentSeverity() != null && this.currentSeverity.getSeverity() < columnDimensionModel.getCurrentSeverity().getSeverity() &&
                        columnDimensionModel.getCurrentSeverity().getSeverity() != 4)) {
            this.currentSeverity = columnDimensionModel.getCurrentSeverity();
        }

        if (this.highestHistoricalSeverity == null ||
                (columnDimensionModel.getHighestHistoricalSeverity() != null &&
                        this.highestHistoricalSeverity.getSeverity() < columnDimensionModel.getHighestHistoricalSeverity().getSeverity() &&
                        columnDimensionModel.getHighestHistoricalSeverity().getSeverity() != 4)) {
            this.highestHistoricalSeverity = columnDimensionModel.getHighestHistoricalSeverity();
        }

        if (this.lastCheckExecutedAt == null ||
                (columnDimensionModel.getLastCheckExecutedAt() != null && columnDimensionModel.getLastCheckExecutedAt().isAfter(this.lastCheckExecutedAt))) {
            this.lastCheckExecutedAt = columnDimensionModel.getLastCheckExecutedAt();
        }

        this.executedChecks += columnDimensionModel.executedChecks;
        this.validResults += columnDimensionModel.validResults;
        this.warnings += columnDimensionModel.warnings;
        this.errors += columnDimensionModel.errors;
        this.fatals += columnDimensionModel.fatals;
        this.executionErrors += columnDimensionModel.executionErrors;
    }
}
