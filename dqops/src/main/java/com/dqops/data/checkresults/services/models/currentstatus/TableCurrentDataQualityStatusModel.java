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

package com.dqops.data.checkresults.services.models.currentstatus;

import com.dqops.data.checkresults.services.models.CheckResultStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The table validity status. It is a summary of the results of the most recently executed data quality checks on the table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableCurrentDataQualityStatusModel", description = "The table's most recent data quality status. " +
        "It is a summary of the results of the most recently executed data quality checks on the table. " +
        "Verify the value of the highest_severity_level to see if there are any data quality issues on the table. " +
        "The values of severity levels are: 0 - all data quality checks passed, 1 - a warning was detected, 2 - an error was detected, " +
        "3 - a fatal data quality issue was detected.")
@Data
public class TableCurrentDataQualityStatusModel implements CurrentDataQualityStatusHolder {
    /**
     * The connection name in DQOps.
     */
    @JsonPropertyDescription("The connection name in DQOps.")
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
     * The severity of the highest identified data quality issue. This field will be null if no data quality checks were executed on the table.
     */
    @JsonPropertyDescription("The severity of the highest identified data quality issue. This field will be null if no data quality checks were executed on the table.")
    private CheckResultStatus highestSeverityLevel;

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
     * The number of data quality check execution errors that were reported due to access issues to the data source, invalid mapping in DQOps,
     * invalid queries in data quality sensors or invalid python rules.
     * When an execution error is reported, the configuration of a data quality check on a table must be updated.
     */
    @JsonPropertyDescription("The number of data quality check execution errors that were reported due to access issues to the data source, " +
            "invalid mapping in DQOps, invalid queries in data quality sensors or invalid python rules. " +
            "When an execution error is reported, the configuration of a data quality check on a table must be updated.")
    private int executionErrors;

    /**
     * The dictionary of statuses for data quality checks. The keys are data quality check names, the values are the current data quality check statuses
     * that describe the most current status.
     */
    @JsonPropertyDescription("The dictionary of statuses for data quality checks. The keys are data quality check names, the values are the current data quality check statuses " +
            "that describe the most current status.")
    private Map<String, CheckCurrentDataQualityStatusModel> checks = new LinkedHashMap<>();

    /**
     * Dictionary of data statues for all columns that have any known data quality results. The keys in the dictionary are the column names.
     */
    @JsonPropertyDescription("Dictionary of data statues for all columns that have any known data quality results. The keys in the dictionary are the column names.")
    private Map<String, ColumnCurrentDataQualityStatusModel> columns = new LinkedHashMap<>();
}
