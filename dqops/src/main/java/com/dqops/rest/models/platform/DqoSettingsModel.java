/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
