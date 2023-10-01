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
package com.dqops.rest.models.metadata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Basic model for data grouping configuration on a table, returned by the rest api.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "DataGroupingConfigurationListModel", description = "Data grouping configurations list model not containing nested objects, but only the name of the grouping configuration.")
public class DataGroupingConfigurationListModel {
    @JsonPropertyDescription("Connection name.")
    private String connectionName;

    @JsonPropertyDescription("Schema name.")
    private String schemaName;

    @JsonPropertyDescription("Table name.")
    private String tableName;

    @JsonPropertyDescription("Data grouping configuration name.")
    private String dataGroupingConfigurationName;

    @JsonPropertyDescription("True when this is the default data grouping configuration for the table.")
    private boolean defaultDataGroupingConfiguration;

    /**
     * Boolean flag that decides if the current user can update or delete this object.
     */
    @JsonPropertyDescription("Boolean flag that decides if the current user can update or delete this object.")
    private boolean canEdit;

    public DataGroupingConfigurationListModel() {
    }
}
