/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.remote;

import com.dqops.core.jobqueue.jobs.table.ImportTablesQueueJobParameters;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Schema model returned from REST API. Describes a schema on the source database with established connection.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "SchemaRemoteModel", description = "Schema remote model")
public class SchemaRemoteModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Schema name.")
    private String schemaName;

    @JsonPropertyDescription("Has the schema been imported.")
    private boolean alreadyImported;

    @JsonPropertyDescription("Job parameters for the import tables job that will import all tables from this schema.")
    private ImportTablesQueueJobParameters importTableJobParameters;
}
