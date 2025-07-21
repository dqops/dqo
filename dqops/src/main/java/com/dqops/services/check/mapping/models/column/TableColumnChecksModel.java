/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping.models.column;

import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model containing information related to column checks on a table level.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableColumnChecksModel", description = "Model containing information related to column checks on a table level.")
public class TableColumnChecksModel {
    /**
     * Physical table name.
     */
    @JsonPropertyDescription("Physical table name.")
    private PhysicalTableName schemaTableName;

    /**
     * SQL WHERE clause added to the sensor query for every check on this table.
     */
    @JsonPropertyDescription("SQL WHERE clause added to the sensor query for every check on this table.")
    private String tableLevelFilter;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to start the job.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to start the job.")
    private CheckSearchFilters runChecksJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored column checks results on this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored column checks results on this table.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * List containing information related to checks on each column.
     */
    @JsonPropertyDescription("List containing information related to checks on each column.")
    private List<ColumnChecksModel> columnChecksModels = new ArrayList<>();
}
