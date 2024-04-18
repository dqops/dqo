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
package com.dqops.rest.models.metadata;

import com.dqops.data.statistics.models.StatisticsMetricModel;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Collection;

/**
 * Model that returns a summary of the table level statistics (the basic profiling results).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableStatisticsModel", description = "Model that returns a summary of the table level statistics (the basic profiling results).")
public class TableStatisticsModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Physical table name including the schema and table names.")
    private PhysicalTableName table;

    @JsonPropertyDescription("List of collected table level statistics.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<StatisticsMetricModel> statistics;

    /**
     * Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table, limited only to the table level statistics (row count, etc).
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors within this table, limited only to the table level statistics (row count, etc).")
    private StatisticsCollectorSearchFilters collectTableStatisticsJobTemplate;

    /**
     * Configured parameters for the "collect statistics" job that should be pushed to the job queue in order to run all statistics collectors within this table, including statistics for all columns.
     */
    @JsonPropertyDescription("Configured parameters for the \"collect statistics\" job that should be pushed to the job queue in order to run all statistics collectors within this table, including statistics for all columns.")
    private StatisticsCollectorSearchFilters collectTableAndColumnStatisticsJobTemplate;

    /**
     * Boolean flag that decides if the current user can collect statistics.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can collect statistics.")
    private boolean canCollectStatistics;
}
