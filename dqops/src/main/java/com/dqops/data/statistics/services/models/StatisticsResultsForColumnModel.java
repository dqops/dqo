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
package com.dqops.data.statistics.services.models;

import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Model object with the statistics results for a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Data
public class StatisticsResultsForColumnModel {
    public StatisticsResultsForColumnModel() {
    }

    public StatisticsResultsForColumnModel(String connectionName, PhysicalTableName table, String columnName) {
        this.connectionName = connectionName;
        this.table = table;
        this.columnName = columnName;
    }

    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name")
    private String connectionName;

    /**
     * Full table name (schema.table).
     */
    @JsonPropertyDescription("Physical table name including the schema and table names")
    private PhysicalTableName table;

    /**
     * Column name.
     */
    @JsonPropertyDescription("Column name")
    private String columnName;

    /**
     * Collection (list) of statistics that were collected for the column.
     */
    @JsonPropertyDescription("List of statistics metrics")
    private Collection<StatisticsMetricModel> metrics = new ArrayList<>();

    /**
     * Collect statistics job template that could be used to collect the statistics again for the column.
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collector within this column.")
    private StatisticsCollectorSearchFilters collectStatisticsJobTemplate;
}
