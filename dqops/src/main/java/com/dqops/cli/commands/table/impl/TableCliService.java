/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.cli.commands.table.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.TabularOutputFormat;
import com.dqops.core.principal.DqoUserPrincipal;
import tech.tablesaw.api.Table;

/**
 * Service that performs table import operations.
 */
public interface TableCliService {
    /**
     * Loads a list of schemas from a given connection.
     * @param connectionName Connection name.
     * @param schemaFilter Schema filter name.
     * @return Table with the results.
     * @throws TableImportFailedException Listing the schemas failed, the message is returned in the exception.
     */
    Table loadSchemaList(String connectionName, String schemaFilter) throws TableImportFailedException;

    /**
     * Imports all tables to the connection from a given schema name.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Optional table name pattern.
     * @return CLI operation status.
     */
    CliOperationStatus importTables(String connectionName, String schemaName, String tableName) throws TableImportFailedException;

    /**
     * List all tables to the connection from a given schema name.
     * @param connectionName Connection name.
     * @param tableName Table name filter.
     * @param tabularOutputFormat tabular output format.
     * @param dimensions Dimensions filter.
     * @param labels Labels filter.
     * @return CLI operation status.
     */
	CliOperationStatus listTables(String connectionName, String tableName, TabularOutputFormat tabularOutputFormat, String[] dimensions, String[] labels);

    /**
     * Adds a table to the connection from a given schema and table name.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @return CLI operation status.
     */
    CliOperationStatus addTable(String connectionName, String schemaName, String tableName);

    /**
     * Removes a table to the connection from a given schema and table name.
     * @param connectionName Connection name.
     * @param fullTableName Full table name.
     * @return CLI operation status.
     */
    CliOperationStatus removeTable(String connectionName, String fullTableName);

    /**
     * Updates a table to the connection from a given schema and table name.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @return CLI operation status.
     */
    CliOperationStatus updateTable(String connectionName, String schemaName, String tableName, String newTableName) throws TableMetadataUpdateException;

}
