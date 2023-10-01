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
