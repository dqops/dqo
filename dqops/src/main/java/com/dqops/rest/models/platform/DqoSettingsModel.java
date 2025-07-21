/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.models.platform;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.LinkedHashMap;

/**
 * REST API model that returns a key/value list of all DQOps configuration properties.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "DqoSettingsModel", description = "DQOps system settings")
public class DqoSettingsModel {
    /**
     * Dictionary of all effective DQOps system properties, retrieved from the default configuration files, user configuration files, environment variables and 'dqo' command arguments.
     */
    @JsonPropertyDescription("Dictionary of all effective DQOps system properties, retrieved from the default configuration files, user configuration files, environment variables and 'dqo' command arguments.")
    private LinkedHashMap<String, Object> properties = new LinkedHashMap<>();
}
