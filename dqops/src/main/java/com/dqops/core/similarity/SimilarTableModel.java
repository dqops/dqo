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

package com.dqops.core.similarity;

import com.dqops.metadata.sources.PhysicalTableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Model that describes a table that is similar to a reference table. Similar tables are used to build the data lineage graph.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode
@ApiModel(value = "SimilarTableModel", description = "Model that describes a table that is similar to a reference table. Similar tables are used to build the data lineage graph.")
public class SimilarTableModel {
    /**
     * Table similarity score. Lower numbers indicate higher similarity.
     */
    @JsonPropertyDescription("Table similarity score. Lower numbers indicate higher similarity.")
    private int difference;

    /**
     * Connection name
     */
    @JsonPropertyDescription("Connection name")
    private String connectionName;

    /**
     * Schema name
     */
    @JsonPropertyDescription("Schema name")
    private String schemaName;

    /**
     * Table name
     */
    @JsonPropertyDescription("Table name")
    private String tableName;

    public SimilarTableModel() {
    }

    public SimilarTableModel(int difference, String connectionName, PhysicalTableName physicalTableName) {
        this.difference = difference;
        this.connectionName = connectionName;
        this.schemaName = physicalTableName.getSchemaName();
        this.tableName = physicalTableName.getTableName();
    }
}