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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.parquet.Strings;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The table comparison check result model for the most recent data comparison run. Identifies the check name and the number of data groupings that passed or failed the comparison.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ComparisonCheckResultModel", description = "The table comparison check result model for the most recent data comparison run. Identifies the check name and the number of data groupings that passed or failed the comparison.")
@Data
public class ComparisonCheckResultModel {
    /**
     * The limit of data group names that are added.
     */
    public static final int NOT_MATCHING_DATA_GROUPS_LIMIT = 10;

    /**
     * DQOps data quality check name.
     */
    @JsonPropertyDescription("DQOps data quality check name.")
    private String checkName;

    /**
     * The timestamp when the check was executed.
     */
    @JsonPropertyDescription("The timestamp when the check was executed.")
    private Instant executedAt;

    /**
     * The number of data groups that were compared and the values matched within the accepted error margin for all check severity levels.
     */
    @JsonPropertyDescription("The number of data groups that were compared and the values matched within the accepted error margin for all check severity levels.")
    private int validResults;

    /**
     * The number of data groups that were compared and the values did not match, raising a warning severity level data quality issue.
     */
    @JsonPropertyDescription("The number of data groups that were compared and the values did not match, raising a warning severity level data quality issue.")
    private int warnings;

    /**
     * The number of data groups that were compared and the values did not match, raising an error severity level data quality issue.
     */
    @JsonPropertyDescription("The number of data groups that were compared and the values did not match, raising an error severity level data quality issue.")
    private int errors;

    /**
     * The number of data groups that were compared and the values did not match, raising a fatal severity level data quality issue.
     */
    @JsonPropertyDescription("The number of data groups that were compared and the values did not match, raising a fatal severity level data quality issue.")
    private int fatals;

    /**
     * The number of execution errors in the check or rule that prevented comparing the tables.
     */
    @JsonPropertyDescription("The number of execution errors in the check or rule that prevented comparing the tables.")
    private int executionErrors;

    /**
     * A list of not matching data grouping names.
     */
    @JsonPropertyDescription("A list of not matching data grouping names.")
    private List<String> notMatchingDataGroups;

    /**
     * Default constructor for deserialization.
     */
    public ComparisonCheckResultModel() {
    }

    /**
     * Creates a check result for a check name and an execution time.
     * @param checkName Check name.
     * @param executedAt The most recent execution time of the table comparison. Only results with the same execution time will be added.
     */
    public ComparisonCheckResultModel(String checkName, Instant executedAt) {
        this.checkName = checkName;
        this.executedAt = executedAt;
    }

    /**
     * Appends a new severity result (a comparison of one group) to the list of results, but only when the executed at timestamp match, so the result
     * is for the same check comparison execution.
     * @param executedAt Executed at timestamp.
     * @param severity Severity level. An additional severity level 4 (outside the range 0..3) is for execution errors.
     * @param dataGroupingName Data grouping name.
     */
    public void appendResult(Instant executedAt, Integer severity, String dataGroupingName) {
        if (!Objects.equals(this.executedAt, executedAt)) {
            return; // this is a result of an earlier comparison
        }

        if (severity == null) {
            return;
        }

        if (severity == 0) {
            this.validResults++;
        } else if (severity == 1) {
            this.warnings++;
        } else if (severity == 2) {
            this.errors++;
        } else if (severity == 3) {
            this.fatals++;
        } else if (severity == 4) {
            this.executionErrors++;
        }

        if (severity > 0 && severity < 4 && !Strings.isNullOrEmpty(dataGroupingName)) {
            if (this.notMatchingDataGroups == null) {
                this.notMatchingDataGroups = new ArrayList<>();
            }

            if (this.notMatchingDataGroups.size() >= NOT_MATCHING_DATA_GROUPS_LIMIT) {
                return;
            }

            this.notMatchingDataGroups.add(dataGroupingName);
        }
    }
}
