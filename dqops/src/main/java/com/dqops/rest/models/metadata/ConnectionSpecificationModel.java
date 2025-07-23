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

import com.dqops.metadata.sources.ConnectionSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Connection model returned by the rest api.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "ConnectionSpecificationModel", description = "Full connection model")
public class ConnectionSpecificationModel {
    /**
     * Connection name.
     */
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    /**
     * Connection hash that identifies the connection using a unique hash code.
     */
    @JsonPropertyDescription("Connection hash that identifies the connection using a unique hash code.")
    private Long connectionHash;

    /**
     * Full connection specification, including all nested objects (but not a list of tables).
     */
    @JsonPropertyDescription("Full connection specification, including all nested objects (but not a list of tables).")
    private ConnectionSpec spec;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    /**
     * Optional parsing error that was captured when parsing the YAML file.
     * This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.
     */
    @JsonPropertyDescription("Optional parsing error that was captured when parsing the YAML file. " +
            "This field is null when the YAML file is valid. If an error was captured, this field returns the file parsing error message and the file location.")
    private String yamlParsingError;

    public ConnectionSpecificationModel() {
    }
}
