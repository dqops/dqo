/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.data.checkresults.models;

import com.dqops.checks.CheckNameUtility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.parquet.Strings;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The table comparison results model with the summary information about the most recent table comparison that was performed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableComparisonResultsModel", description = "The table comparison results model with the summary information about the most recent table comparison that was performed.")
@Data
public class TableComparisonResultsModel {
    /**
     * The dictionary of comparison results between the tables for table level comparisons (e.g. row count).
     * The keys for the dictionary are the check names. The value in the dictionary is a summary information about the most recent comparison.
     */
    @JsonPropertyDescription("The dictionary of comparison results between the tables for table level comparisons (e.g. row count). The keys for the dictionary are the check names. The value in the dictionary is a summary information about the most recent comparison.")
    private Map<String, ComparisonCheckResultModel> tableComparisonResults = new LinkedHashMap<>();

    /**
     * The dictionary of comparison results between the tables for each compared column.
     * The keys for the dictionary are the column names. The values are dictionaries of the data quality check names and their results.
     */
    @JsonPropertyDescription("The dictionary of comparison results between the tables for each compared column. The keys for the dictionary are the column names. The values are dictionaries of the data quality check names and their results.")
    private Map<String, TableComparisonColumnResultsModel> columnComparisonResults = new LinkedHashMap<>();

    /**
     * Appends a result for a check or a column+check.
     * @param executedAt Executed at timestamp.
     * @param checkName Check name.
     * @param columnName Optional column name for column level checks.
     * @param severity Check result severity. 0..4 where the extra severity 4 is for execution errors.
     * @param dataGroupingName The name of the data group.
     */
    public void appendResult(Instant executedAt, String checkName, String columnName, Integer severity, String dataGroupingName) {
        String unifiedCheckName = CheckNameUtility.getUnifiedCheckName(checkName);
        Map<String, ComparisonCheckResultModel> checkResultModelMap;
        if (Strings.isNullOrEmpty(columnName)) {
            checkResultModelMap = tableComparisonResults;
        } else {
            TableComparisonColumnResultsModel columnCheckResultModelMap = this.columnComparisonResults.get(columnName);
            if (columnCheckResultModelMap == null) {
                columnCheckResultModelMap = new TableComparisonColumnResultsModel(columnName);
                this.columnComparisonResults.put(columnName, columnCheckResultModelMap);
            }
            checkResultModelMap = columnCheckResultModelMap.getColumnComparisonResults();
        }

        ComparisonCheckResultModel comparisonCheckResultModel = checkResultModelMap.get(unifiedCheckName);
        if (comparisonCheckResultModel == null) {
            comparisonCheckResultModel = new ComparisonCheckResultModel(unifiedCheckName, executedAt);
            checkResultModelMap.put(unifiedCheckName, comparisonCheckResultModel);
        }

        comparisonCheckResultModel.appendResult(executedAt, severity, dataGroupingName);
    }
}
