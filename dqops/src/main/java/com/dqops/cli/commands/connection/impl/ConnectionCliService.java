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
package com.dqops.cli.commands.connection.impl;

import com.dqops.cli.commands.CliOperationStatus;
import com.dqops.cli.commands.TabularOutputFormat;
import com.dqops.cli.commands.connection.impl.models.ConnectionListModel;
import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import com.dqops.cli.terminal.FormattedTableDto;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;

/**
 * Connection management service.
 */
public interface ConnectionCliService {

    /**
     * Returns cli operation status.
     * @param connectionName Connection name.
     * @param fullTableName Full table name.
     * @param tabularOutputFormat Tabular output format.
     * @return Cli operation status.
     */
    CliOperationStatus showTableForConnection(String connectionName, String fullTableName, TabularOutputFormat tabularOutputFormat);

    /**
     * Returns cli operation status.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableNameContains Table name.
     * @param tabularOutputFormat Tabular output format.
     * @param dimensions Dimensions filter.
     * @param labels Labels filter.
     * @return Cli operation status.
     */
    CliOperationStatus loadTableList(String connectionName, String schemaName, String tableNameContains, TabularOutputFormat tabularOutputFormat,
                                     String[] dimensions, String[] labels);

    /**
     * Returns a schemas of local connections.
     * @param connectionName Connection name.
     * @param tabularOutputFormat Tabular output format.
     * @param dimensions Dimensions filter.
     * @param labels Labels filter.
     * @return Schema list.
     */
    CliOperationStatus loadSchemaList(String connectionName, TabularOutputFormat tabularOutputFormat, String[] dimensions, String[] labels);

    /**
     * Returns a table of local connections.
     * @param dimensions Dimensions filter.
     * @param labels Labels filter.
     * @return Connection list.
     */
    FormattedTableDto<ConnectionListModel> loadConnectionTable(String connectionNameFilter, String[] dimensions, String[] labels);

    /**
     * Adds a new connection.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return Cli operation status.
     */
    CliOperationStatus addConnection(String connectionName, ConnectionSpec connectionSpec);

    /**
     * Remove a connection.
     * @param connectionName Connection name.
     * @return Cli operation status.
     */
    CliOperationStatus removeConnection(String connectionName);

    /**
     * Update connection.
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return Cli operation status.
     */
    CliOperationStatus updateConnection(String connectionName, ConnectionSpec connectionSpec);

    /**
     * Get connection.
     * @param connectionName Connection name.
     * @return connection wrapper.
     */
    ConnectionWrapper getConnection(String connectionName);

    /**
     * Delegates the connection configuration to the provider.
     * @param connectionSpec Connection specification to fill.
     * @param isHeadless When true and some required parameters are missing then throws an exception {@link CliRequiredParameterMissingException},
     *                   otherwise prompts the user to fill the answer.
     * @param terminalReader Terminal reader that may be used to prompt the user.
     * @param terminalWriter Terminal writer that should be used to write any messages.
     */
    void promptForConnectionParameters(ConnectionSpec connectionSpec, boolean isHeadless, TerminalReader terminalReader, TerminalWriter terminalWriter);

    /**
     * Finds a connection and opens the default text editor to edit the yaml file.
     * @param connectionName Connection name.
     * @return Error code: 0 when the table was found, -1 when the connection was not found.
     */
    int launchEditorForConnection(String connectionName);
}
