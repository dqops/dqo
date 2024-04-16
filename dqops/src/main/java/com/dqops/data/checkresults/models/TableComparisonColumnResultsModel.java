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
