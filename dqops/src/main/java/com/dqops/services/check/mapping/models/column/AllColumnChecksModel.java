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

import com.dqops.checks.CheckTarget;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model containing selected information related to column checks on a connection level.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "AllColumnChecksModel", description = "Model containing selected information related to column checks on a connection level.")
public class AllColumnChecksModel {
    /**
     * Check target.
     */
    @JsonPropertyDescription("Check target.")
    private final CheckTarget checkTarget = CheckTarget.column;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to start the job.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to start the job.")
    private CheckSearchFilters runChecksJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored column checks results on this connection.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored column checks results on this connection.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * Flattened table list containing information related to columns in each table.
     */
    @JsonPropertyDescription("Flattened table list containing information related to columns in each table.")
    private List<TableColumnChecksModel> tableColumnChecksModels = new ArrayList<>();
}
