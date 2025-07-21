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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The table comparison column results model with the information about the most recent table comparison relating to a single compared column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableComparisonColumnResultsModel", description = "The table comparison column results model with the information about the most recent table comparison relating to a single compared column.")
@Data
public class TableComparisonColumnResultsModel {
    /**
     * Column name.
     */
    @JsonPropertyDescription("Column name")
    private final String columnName;
    
    /**
     * The dictionary of comparison results between the tables for the specific column.
     * The keys for the dictionary are check names. The values are summaries of the most recent comparison on this column.
     */
    @JsonPropertyDescription("The dictionary of comparison results between the tables for the specific column. " +
            "The keys for the dictionary are check names. The values are summaries of the most recent comparison on this column.")
    private Map<String, ComparisonCheckResultModel> columnComparisonResults = new LinkedHashMap<>();

    public TableComparisonColumnResultsModel(String columnName) {
        this.columnName = columnName;
    }
}
