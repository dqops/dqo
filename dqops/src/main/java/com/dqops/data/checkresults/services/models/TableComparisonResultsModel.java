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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The table comparison result model with the summary information about the most recent table comparison that was performed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableComparisonResultsModel", description = "The table comparison result model with the summary information about the most recent table comparison that was performed.")
@Data
public class TableComparisonResultsModel {
    /**
     * The dictionary of comparison results between the tables for table level comparisons (the row count).
     * The keys for the dictionary are the check names. The value in the dictionary is a summary information about the most recent comparison.
     */
    @JsonPropertyDescription("The dictionary of comparison results between the tables for table level comparisons (the row count). The keys for the dictionary are the check names. The value in the dictionary is a summary information about the most recent comparison.")
    private Map<String, ComparisonCheckResultModel> tableComparisonResults = new LinkedHashMap<>();

    /**
     * The dictionary of comparison results between the tables for each compared column.
     * The keys for the dictionary are the column names. The values are dictionaries of the data quality check names and their results.
     */
    @JsonPropertyDescription("The dictionary of comparison results between the tables for each compared column. The keys for the dictionary are the column names. The values are dictionaries of the data quality check names and their results.")
    private Map<String, Map<String, ComparisonCheckResultModel>> columnComparisonResults = new LinkedHashMap<>();

    /**
     * Appends a result for a check or a column+check.
     * @param executedAt Executed at timestamp.
     * @param checkName Check name.
     * @param columnName Optional column name for column level checks.
     * @param severity Check result severity. 0..4 where the extra severity 4 is for execution errors.
     * @param dataGroupingName The name of the data group.
     */
    public void appendResult(Instant executedAt, String checkName, String columnName, Integer severity, String dataGroupingName) {
        Map<String, ComparisonCheckResultModel> checkResultModelMap;
        if (columnName == null) {
            checkResultModelMap = tableComparisonResults;
        } else {
            checkResultModelMap = this.columnComparisonResults.get(columnName);
            if (checkResultModelMap == null) {
                checkResultModelMap = new LinkedHashMap<>();
                this.columnComparisonResults.put(columnName, checkResultModelMap);
            }
        }

        ComparisonCheckResultModel comparisonCheckResultModel = checkResultModelMap.get(checkName);
        if (comparisonCheckResultModel == null) {
            comparisonCheckResultModel = new ComparisonCheckResultModel(checkName, executedAt);
            checkResultModelMap.put(checkName, comparisonCheckResultModel);
        }

        comparisonCheckResultModel.appendResult(executedAt, severity, dataGroupingName);
    }
}
