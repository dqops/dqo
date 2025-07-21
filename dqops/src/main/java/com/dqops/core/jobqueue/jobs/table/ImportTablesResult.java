/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.jobs.table;

import com.dqops.metadata.sources.TableSpec;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
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

    public static class ImportTablesResultSampleFactory implements SampleValueFactory<ImportTablesResult> {
        @Override
        public ImportTablesResult createSample() {
            Table importedTables = Table.create();
            List<TableSpec> sourceTablesSpecs = List.of(new TableSpec.TableSpecSampleFactory().createSample());
            return new ImportTablesResult(importedTables, sourceTablesSpecs);
        }
    }
}
