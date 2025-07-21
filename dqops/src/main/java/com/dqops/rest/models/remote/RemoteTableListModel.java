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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Remote table list model that is returned when a data source is introspected to retrieve the list of tables available in a data source.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "RemoteTableListModel", description = "Table remote list model")
public class RemoteTableListModel {
    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    /**
     * Schema name.
     */
    @JsonPropertyDescription("Schema name.")
    private String schemaName;

    /**
     * Table name.
     */
    @JsonPropertyDescription("Table name.")
    private String tableName;

    /**
     * A flag that tells if the table been already imported.
     */
    @JsonPropertyDescription("A flag that tells if the table been already imported.")
    private boolean alreadyImported;
}
