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
