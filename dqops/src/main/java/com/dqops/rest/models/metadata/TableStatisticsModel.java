/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
