/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.cli.commands.connection.impl;

import ai.dqo.cli.commands.TabularOutputFormat;
import ai.dqo.cli.commands.connection.impl.models.ConnectionListModel;
import ai.dqo.cli.commands.CliOperationStatus;
import ai.dqo.cli.exceptions.CliRequiredParameterMissingException;
import ai.dqo.cli.terminal.FormattedTableDto;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.metadata.sources.ConnectionSpec;
import ai.dqo.metadata.sources.ConnectionWrapper;

/**
 * Connection management service.
 */
public interface ConnectionService {

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
     * @param tableName Table name.
     * @param tabularOutputFormat Tabular output format.
     * @return Cli operation status.
     */
    CliOperationStatus loadTableList(String connectionName, String schemaName, String tableName, TabularOutputFormat tabularOutputFormat);

    /**
     * Returns a schemas of local connections.
     * @param connectionName Connection name.
     * @param tabularOutputFormat Tabular output format.
     * @return Schema list.
     */
    CliOperationStatus loadSchemaList(String connectionName, TabularOutputFormat tabularOutputFormat);

    /**
     * Returns a table of local connections.
     * @return Connection list.
     */
    FormattedTableDto<ConnectionListModel> loadConnectionTable(String connectionNameFilter);

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
