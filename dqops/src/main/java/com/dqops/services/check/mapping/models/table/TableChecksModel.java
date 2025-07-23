/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping.models.table;

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
 * Model containing information related to table-level checks on a table.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "TableChecksModel", description = "Model containing information related to table-level checks on a table.")
public class TableChecksModel {
    /**
     * Table name.
     */
    @JsonPropertyDescription("Table name.")
    private String tableName;

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
     * Configured parameters for the "data clean" job that after being supplied with a time range should be pushed to the job queue in order to remove stored table checks results connected with this table.
     */
    @JsonPropertyDescription("Configured parameters for the \"data clean\" job that after being supplied with a time range should be pushed to the job queue in order to remove stored table checks results connected with this table.")
    private DeleteStoredDataQueueJobParameters dataCleanJobTemplate;

    /**
     * Mapping of check type and timescale to check container on this table.
     */
    @JsonPropertyDescription("Mapping of check type and timescale to check container on this table.")
    private Map<CheckContainerTypeModel, CheckContainerModel> checkContainers = new LinkedHashMap<>();
}
