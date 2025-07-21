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

import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Parameters for the {@link ImportTablesQueueJob} job that imports selected tables from the source database.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class ImportTablesQueueJobParameters {
    @JsonPropertyDescription("Connection name")
    private String connectionName;

    @JsonPropertyDescription("Schema name")
    private String schemaName;

    @JsonPropertyDescription("Optional filter for the table names to import. The table names that are imported must contain a substring matching this parameter. This filter is case sensitive.")
    private String tableNameContains;

    @JsonPropertyDescription("Optional list of table names inside the schema. When the list of tables is empty, all tables are imported.")
    private List<String> tableNames;

    @JsonPropertyDescription("Optional parameter to configure the limit of tables that are imported from a single schema. Leave this parameter blank to use the default limit (300 tables).")
    private Integer tablesImportLimit;

    public ImportTablesQueueJobParameters() {
    }

    /**
     * Creates a schema import job parameters.
     * @param connectionName Connection name to import.
     * @param schemaName Schema name in the source database to import.
     * @param tableNameContains Optional filter for the table names to import.
     * @param tableNames Names of tables to import.
     */
    public ImportTablesQueueJobParameters(String connectionName, String schemaName, String tableNameContains, List<String> tableNames) {
        this.connectionName = connectionName;
        this.schemaName = schemaName;
        this.tableNameContains = tableNameContains;
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
     * Returns an optional filter for the table names to return.
     * @return Optional filter for the table names.
     */
    public String getTableNameContains() {
        return tableNameContains;
    }

    /**
     * Sets an optional filter for the table names.
     * @param tableNameContains Table name filter.
     */
    public void setTableNameContains(String tableNameContains) {
        this.tableNameContains = tableNameContains;
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

    /**
     * Returns the limit of tables imported from a schema.
     * @return Limit of tables or null if using the default configuration.
     */
    public Integer getTablesImportLimit() {
        return tablesImportLimit;
    }

    /**
     * Sets the limit of tables imported from a schema.
     * @param tablesImportLimit New limit of tables.
     */
    public void setTablesImportLimit(Integer tablesImportLimit) {
        this.tablesImportLimit = tablesImportLimit;
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
