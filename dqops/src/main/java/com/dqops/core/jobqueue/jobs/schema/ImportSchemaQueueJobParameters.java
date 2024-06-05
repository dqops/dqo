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
package com.dqops.core.jobqueue.jobs.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.EqualsAndHashCode;

/**
 * Parameters for the {@link ImportSchemaQueueJob} job that imports tables from a database.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class ImportSchemaQueueJobParameters {
    @JsonPropertyDescription("Connection name where the tables are imported.")
    private String connectionName;

    @JsonPropertyDescription("Source schema name from which the tables are imported.")
    private String schemaName;

    @JsonPropertyDescription("Optional filter for the names of tables to import, it is a text (substring) that must be present inside table names. This filter is case sensitive.")
    private String tableNameContains;

    public ImportSchemaQueueJobParameters() {
    }

    /**
     * Creates a schema import job parameters.
     * @param connectionName Connection name to import.
     * @param schemaName Schema name in the source database to import.
     * @param tableNameContains Optional table name search filter.
     */
    public ImportSchemaQueueJobParameters(String connectionName, String schemaName, String tableNameContains) {
        this.connectionName = connectionName;
        this.schemaName = schemaName;
        this.tableNameContains = tableNameContains;
    }

    /**
     * Returns the connection name to be imported.
     * @return Connection name.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Sets the connection name to be imported.
     * @param connectionName Connection name.
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Returns the source schema name.
     * @return Source schema name.
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Sets the source schema name.
     * @param schemaName Source schema name.
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    /**
     * Sets an optional table filter that should be a text inside the table name, without any wildcard characters.
     * @return Table name substring.
     */
    public String getTableNameContains() {
        return tableNameContains;
    }

    /**
     * Sets the table name search filter.
     * @param tableNameContains Table name search filter.
     */
    public void setTableNameContains(String tableNameContains) {
        this.tableNameContains = tableNameContains;
    }
}
