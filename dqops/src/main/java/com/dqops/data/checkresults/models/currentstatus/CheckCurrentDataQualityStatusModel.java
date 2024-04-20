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

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.data.checkresults.models.CheckResultStatus;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * The most recent data quality status for a single data quality check.
 * If data grouping is enabled, this model will return the highest data quality issue status from all
 * data quality results for all data groups.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CheckCurrentDataQualityStatusModel", description = "The most recent data quality status for a single data quality check. " +
          "If data grouping is enabled, the *current_severity* will be the highest data quality issue status from all data quality results for all data groups. " +
          "For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.")
@Data
public class CheckCurrentDataQualityStatusModel{
    /**
     * The data quality issue severity for this data quality check. An additional value *execution_error* is used to tell that the check,
     * sensor or rule failed to execute due to insufficient  permissions to the table or an error in the sensor's template or a Python rule.
     * For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.
     */
    @JsonPropertyDescription("The data quality issue severity for this data quality check. An additional value *execution_error* is used to tell that the check, " +
            "sensor or rule failed to execute due to insufficient  permissions to the table or an error in the sensor's template or a Python rule. " +
            "For partitioned checks, it is the highest severity of all results for all partitions (time periods) in the analyzed time range.")
    private CheckResultStatus currentSeverity;

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
     * The UTC timestamp when the check was recently executed.
     */
    @JsonPropertyDescription("The UTC timestamp when the check was recently executed.")
    private Instant lastExecutedAt;

    /**
     * Optional column name for column-level data quality checks.
     */
    @JsonPropertyDescription("Optional column name for column-level data quality checks.")
    private String columnName;

    /**
     * The check type: profiling, monitoring, partitioned.
     */
    @JsonPropertyDescription("The check type: profiling, monitoring, partitioned.")
    private CheckType checkType;

    /**
     * The check time scale for *monitoring* and *partitioned* check types. The time scales are *daily* and *monthly*. The profiling checks do not have a time scale.
     */
    @JsonPropertyDescription("The check time scale for *monitoring* and *partitioned* check types. The time scales are *daily* and *monthly*. The profiling checks do not have a time scale.")
    private CheckTimeScale timeScale;

    /**
     * Check category name.
     */
    @JsonPropertyDescription("Check category name, such as nulls, schema, strings, volume.")
    private String category;

    /**
     * Data quality dimension, such as Completeness, Uniqueness, Validity.
     */
    @JsonPropertyDescription("Data quality dimension, such as Completeness, Uniqueness, Validity.")
    private String qualityDimension;

    /**
     * The total number of most recent checks that were executed . Table comparison checks that are comparing groups of data are counted as the number of compared data groups.
     */
    @JsonPropertyDescription("The total number of most recent checks that were executed on the column. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.")
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
            "When an execution error is reported, the configuration of a data quality check on a column must be updated.")
    private int executionErrors;

    /**
     * Increments the count of issues with the given severity level for this check.
     * @param severity The severity level.
     */
    public void incrementTotalIssueCount(int severity) {
        this.setExecutedChecks(this.getExecutedChecks() + 1);
        switch (severity) {
            case 0:
                this.setValidResults(this.getValidResults() + 1);
                break;
            case 1:
                this.setWarnings(this.getWarnings() + 1);
                break;
            case 2:
                this.setErrors(this.getErrors() + 1);
                break;
            case 3:
                this.setFatals(this.getFatals() + 1);
                break;
            case 4:
                this.setExecutionErrors(this.getExecutionErrors() + 1);
                break;
        }
    }

    public static class CheckCurrentDataQualityStatusModelSampleFactory implements SampleValueFactory<CheckCurrentDataQualityStatusModel> {
        @Override
        public CheckCurrentDataQualityStatusModel createSample() {
            return new CheckCurrentDataQualityStatusModel() {{
                setCurrentSeverity(CheckResultStatus.warning);
                setHighestHistoricalSeverity(RuleSeverityLevel.error);
                setWarnings(1);
                setErrors(1);
                setLastExecutedAt(LocalDateTime.of(2007, 10, 14, 16,13, 42)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
                setCheckType(CheckType.profiling);
                setCategory(SampleStringsRegistry.getCategoryName());
                setQualityDimension(SampleStringsRegistry.getQualityDimension());
            }};
        }
    }
}
