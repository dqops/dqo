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
package com.dqops.core.jobqueue.jobs.table;

import com.dqops.utils.docs.SampleStringsRegistry;
import com.dqops.utils.docs.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Parameters for the {@link ImportTablesQueueJob} job that imports selected tables from the source database.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class ImportTablesQueueJobParameters {
    private String connectionName;
    private String schemaName;
    private List<String> tableNames;

    public ImportTablesQueueJobParameters() {
    }

    /**
     * Creates a schema import job parameters.
     * @param connectionName Connection name to import.
     * @param schemaName Schema name in the source database to import.
     * @param tableNames Names of tables to import.
     */
    public ImportTablesQueueJobParameters(String connectionName, String schemaName, List<String> tableNames) {
        this.connectionName = connectionName;
        this.schemaName = schemaName;
        this.tableNames = tableNames;
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
     * Returns the names of tables to import.
     * @return Table names to import.
     */
    public List<String> getTableNames() {
        return tableNames;
    }

    /**
     * Sets the names of tables to import.
     * @param tableNames Table names to import.
     */
    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    public static class ImportTablesQueueJobParametersSampleFactory implements SampleValueFactory<ImportTablesQueueJobParameters> {
        @Override
        public ImportTablesQueueJobParameters createSample() {
            return new ImportTablesQueueJobParameters() {{
                setConnectionName(SampleStringsRegistry.getConnectionName());
                setSchemaName(SampleStringsRegistry.getSchemaName());
                setTableNames(List.of(SampleStringsRegistry.getTableName()));
            }};
        }
    }
}
