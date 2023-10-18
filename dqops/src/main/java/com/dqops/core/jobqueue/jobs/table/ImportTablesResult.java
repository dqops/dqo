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

import com.dqops.metadata.sources.TableSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import tech.tablesaw.api.Table;

import java.util.List;

/**
 * Result object from the {@link ImportTablesQueueJob} table import job that returns list of tables that have been imported.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ImportTablesResult", description = "Result object returned from the \"import tables\" job. " +
        "Contains the original table schemas and column schemas of imported tables.")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ImportTablesResult {
    @JsonIgnore
    private Table importedTables;

    /**
     * Table schemas (including column schemas) of imported tables.
     */
    @JsonPropertyDescription("Table schemas (including column schemas) of imported tables.")
    private List<TableSpec> sourceTableSpecs;

    /**
     * Creates a job result object.
     * @param importedTable List of imported tables in a tabular format.
     */
    public ImportTablesResult(Table importedTable, List<TableSpec> sourceTableSpecs) {
        this.importedTables = importedTable;
        this.sourceTableSpecs = sourceTableSpecs;
    }

    /**
     * Returns a list of imported tables in a tabular object.
     * @return Imported tables.
     */
    public Table getImportedTables() {
        return importedTables;
    }

    /**
     * Stores a tablesaw table with the list of imported tables.
     * @param importedTables Imported table.
     */
    public void setImportedTables(Table importedTables) {
        this.importedTables = importedTables;
    }

    /**
     * Returns the table schemas of imported tables.
     * @return Table schemas of imported tables.
     */
    public List<TableSpec> getSourceTableSpecs() {
        return sourceTableSpecs;
    }

    /**
     * Sets a list of table schemas of imported tables.
     * @param sourceTableSpecs Table schemas of imported tables.
     */
    public void setSourceTableSpecs(List<TableSpec> sourceTableSpecs) {
        this.sourceTableSpecs = sourceTableSpecs;
    }
}
