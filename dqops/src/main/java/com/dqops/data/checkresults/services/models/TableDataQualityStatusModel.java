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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * The table validity status. It is a summary of the results of the most recently executed data quality checks on the table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableDataQualityStatusModel", description = "The table's most recent data quality status. " +
        "It is a summary of the results of the most recently executed data quality checks on the table. " +
        "Verify the value of the highest_severity_issue (0 - all data quality checks passed, 1 - a warning was detected, 2 - an error was detected, " +
        "3 - a fatal data quality issue was detected.")
@Data
public class TableDataQualityStatusModel {
    /**
     * The connection name in DQO.
     */
    @JsonPropertyDescription("The connection name in DQO.")
    private String connectionName;

    /**
     * The schema name.
     */
    @JsonPropertyDescription("The schema name.")
    private String schemaName;

    /**
     * The table name.
     */
    @JsonPropertyDescription("The table name.")
    private String tableName;

    /**
     * The severity of the highest identified data quality issue (1 = warning, 2 = error, 3 = fatal) or 0 when no data quality issues were identified. This field will be null if no data quality checks were executed on the table.
     */
    @JsonPropertyDescription("The severity of the highest identified data quality issue (1 = warning, 2 = error, 3 = fatal) or 0 when no data quality issues were identified. This field will be null if no data quality checks were executed on the table.")
    private int highestSeverityIssue;

    /**
     * The UTC timestamp when the most recent data quality check was executed on the table.
     */
    @JsonPropertyDescription("The UTC timestamp when the most recent data quality check was executed on the table.")
    private Instant lastCheckExecutedAt;

    /**
     * The total number of most recent checks that were executed on the table. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.
     */
    @JsonPropertyDescription("The total number of most recent checks that were executed on the table. Table comparison checks that are comparing groups of data are counted as the number of compared data groups.")
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
     * The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQO, invalid queries in data quality sensors or invalid python rules.
     * When an execution error is reported, the configuration of a data quality check on a table must be updated.
     */
    @JsonPropertyDescription("The number of data quality check execution errors that were reported due to access issues to the data source, " +
            "invalid mapping in DQO, invalid queries in data quality sensors or invalid python rules. " +
            "When an execution error is reported, the configuration of a data quality check on a table must be updated.")
    private int executionErrors;

    /**
     * The names of failed data quality checks (keys) and severity of the highest data quality issue that was detected.
     * Table-level checks are identified by the check name. Column-level checks are identified as a check_name[column_name].
     */
    @JsonPropertyDescription("The paths to all failed data quality checks (keys) and severity of the highest data quality issue that was detected. " +
            "Table-level checks are identified by the check name. Column-level checks are identified as a check_name[column_name].")
    private Map<String, CheckResultStatus> failedChecksStatuses = new HashMap<>();
}
