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
package ai.dqo.connectors;

import ai.dqo.cli.exceptions.CliRequiredParameterMissingException;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.metadata.sources.ColumnTypeSnapshotSpec;
import ai.dqo.metadata.sources.ConnectionSpec;
import tech.tablesaw.columns.Column;

/**
 * Connection provider class.
 */
public interface ConnectionProvider {
    /**
     * Creates a connection to a target data source.
     * @param connectionSpec Connection specification.
     * @param openConnection Open the connection after creating.
     * @return Connection object.
     */
    SourceConnection createConnection(ConnectionSpec connectionSpec, boolean openConnection);

    /**
     * Returns the dialect settings for the provider. The dialect settings has information about supported SQL features, identifier quoting, etc.
     * @param connectionSpec Connection specification if the settings are database version specific.
     * @return Provider dialect settings.
     */
    ProviderDialectSettings getDialectSettings(ConnectionSpec connectionSpec);

    /**
     * Delegates the connection configuration to the provider.
     *
     * @param connectionSpec Connection specification to fill.
     * @param isHeadless     When true and some required parameters are missing then throws an exception {@link CliRequiredParameterMissingException},
     *                       otherwise prompts the user to fill the answer.
     * @param terminalReader Terminal reader that may be used to prompt the user.
     * @param terminalWriter Terminal writer that should be used to write any messages.
     */
    void promptForConnectionParameters(ConnectionSpec connectionSpec, boolean isHeadless, TerminalReader terminalReader, TerminalWriter terminalWriter);

    /**
     * Proposes a physical (provider specific) column type that is able to store the data of the given Tablesaw column.
     * @param dataColumn Tablesaw column with data that should be stored.
     * @return Column type snapshot.
     */
    ColumnTypeSnapshotSpec proposePhysicalColumnType(Column<?> dataColumn);

    /**
     * Formats a constant for the target database.
     * @param constant Constant to be formatted.
     * @param columnType Column type snapshot.
     * @return Formatted constant.
     */
    String formatConstant(Object constant, ColumnTypeSnapshotSpec columnType);

    /**
     * Returns the best matching column type for the type snapshot (real column type returned by the database).
     * @param columnTypeSnapshot Column type snapshot.
     * @return Data type category.
     */
    DataTypeCategory detectColumnType(ColumnTypeSnapshotSpec columnTypeSnapshot);
}
