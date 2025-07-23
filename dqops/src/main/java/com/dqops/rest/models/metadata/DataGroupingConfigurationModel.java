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

import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Model of data grouping configuration on a table returned by the rest api, including all configuration information.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "DataGroupingConfigurationModel", description = "Data grouping configuration model containing nested objects and the configuration of grouping dimensions.")
public class DataGroupingConfigurationModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Schema name.")
    private String schemaName;

    @JsonPropertyDescription("Table name.")
    private String tableName;

    @JsonPropertyDescription("Data grouping configuration name.")
    private String dataGroupingConfigurationName;

    @JsonPropertyDescription("Data grouping specification with the definition of the list of data grouping dimensions, the column names to use in a **GROUP BY** clause or a value of a static tag to assign to every check result captured from the table.")
    private DataGroupingConfigurationSpec spec;

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

    public DataGroupingConfigurationModel() {
    }
}
