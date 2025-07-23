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
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.models.CheckContainerTypeModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Model containing information related to checks on a column.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ColumnChecksModel", description = "Model containing information related to checks on a column.")
public class ColumnChecksModel {
    /**
     * Column name.
     */
    @JsonPropertyDescription("Column name.")
    private String columnName;

    /**
     * Configured parameters for the "check run" job that should be pushed to the job queue in order to start the job.
     */
    @JsonPropertyDescription("Configured parameters for the \"check run\" job that should be pushed to the job queue in order to start the job.")
    private CheckSearchFilters runChecksJobTemplate;

    /**
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored column checks results on this column.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored column checks results on this column.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * Mapping of check type and timescale to check container on this column.
     */
    @JsonPropertyDescription("Mapping of check type and timescale to check container on this column.")
    private Map<CheckContainerTypeModel, CheckContainerModel> checkContainers = new LinkedHashMap<>();
}
