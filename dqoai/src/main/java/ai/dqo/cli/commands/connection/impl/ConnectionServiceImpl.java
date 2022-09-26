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
import ai.dqo.cli.edit.EditorLaunchService;
import ai.dqo.cli.exceptions.CliRequiredParameterMissingException;
import ai.dqo.cli.output.OutputFormatService;
import ai.dqo.cli.terminal.FormattedTableDto;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.connectors.*;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.search.ConnectionSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.search.TableSearchFilters;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalker;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Connection management service.
 */
@Component
public class ConnectionServiceImpl implements ConnectionService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final TerminalReader terminalReader;
    private final TerminalWriter terminalWriter;
    private final TerminalTableWritter terminalTableWritter;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private SecretValueProvider secretValueProvider;
    private final OutputFormatService outputFormatService;
    private final EditorLaunchService editorLaunchService;

    @Autowired
    public ConnectionServiceImpl(UserHomeContextFactory userHomeContextFactory,
								 ConnectionProviderRegistry connectionProviderRegistry,
                                 TerminalReader terminalReader,
                                 TerminalWriter terminalWriter,
                                 TerminalTableWritter terminalTableWritter,
                                 SecretValueProvider secretValueProvider,
                                 OutputFormatService outputFormatService,
                                 EditorLaunchService editorLaunchService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.terminalTableWritter = terminalTableWritter;
        this.secretValueProvider = secretValueProvider;
        this.outputFormatService = outputFormatService;
        this.editorLaunchService = editorLaunchService;
    }

    private TableWrapper findTableFromNameAndSchema(String tableName, Collection<TableWrapper> tableWrappers) {
        return tableWrappers.stream().filter(tableWrapper -> tableWrapper.getPhysicalTableName().getTableName().equals(tableName))
                .findAny().orElse(null);
    }

    /**
     * Returns cli operation status.
     * @param connectionName Connection name.
     * @param fullTableName Full table name.
     * @param tabularOutputFormat Tabular output format.
     * @return Cli operation status.
     */
    @Override
    public CliOperationStatus showTableForConnection(String connectionName, String fullTableName, TabularOutputFormat tabularOutputFormat) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);

        if (connectionWrapper == null) {
            cliOperationStatus.setFailedMessage("Connection was not found");
            return cliOperationStatus;
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider);

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(fullTableName);

        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true)) {
            ArrayList<String> tableNames = new ArrayList<>();
            tableNames.add(physicalTableName.getTableName());
            List<TableSpec> sourceTableSpecs = sourceConnection.retrieveTableMetadata(physicalTableName.getSchemaName(), tableNames);

            if (sourceTableSpecs.size() == 0) {
                cliOperationStatus.setFailedMessage("No tables found in the data source");
                return cliOperationStatus;
            }

            TableWrapper currentTable = connectionWrapper.getTables().getByObjectName(physicalTableName, true);
            ColumnSpecMap currentColumns = currentTable.getSpec().getColumns();

            TableSpec tableSpec = sourceTableSpecs.get(0);
            ColumnSpecMap columnSpecMap = tableSpec.getColumns();

            Table resultTable = Table.create().addColumns(
                    StringColumn.create("Source column name"),
                    StringColumn.create("Source column type"),
                    StringColumn.create("Imported column name"),
                    StringColumn.create("Imported column type"));

            for (Map.Entry<String, ColumnSpec> entry : columnSpecMap.entrySet()) {
                String columnName = entry.getKey();
                ColumnSpec columnSpec = columnSpecMap.get(columnName);
                Row row = resultTable.appendRow();
                ColumnSpec currentColumn = currentColumns.get(columnName);
                row.setString(0, columnName);
                row.setString(1, columnSpec.getTypeSnapshot().getColumnType());
                row.setString(2, currentColumn == null ? "" : columnName);
                row.setString(3, currentColumn == null ? "" : currentColumn.getTypeSnapshot().getColumnType());
            }

            cliOperationStatus.setSuccess(true);
            switch(tabularOutputFormat) {
                case CSV: {
                    cliOperationStatus.setMessage(this.outputFormatService.tableToCsv(resultTable));
                    break;
                }
                case JSON: {
                    cliOperationStatus.setMessage(this.outputFormatService.tableToJson(resultTable));
                    break;
                }
                default: {
                    cliOperationStatus.setTable(resultTable);
                    break;
                }
            }
        } catch (Exception e) {
            cliOperationStatus.setFailedMessage(e.getMessage());
        }
        return cliOperationStatus;
    }

    /**
     * Returns cli operation status.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @param tabularOutputFormat Tabular output format.
     * @param dimensions Dimensions filter.
     * @param labels Labels filter.
     * @return Cli operation status.
     */
    @Override
    public CliOperationStatus loadTableList(String connectionName, String schemaName, String tableName, TabularOutputFormat tabularOutputFormat,
                                            String[] dimensions, String[] labels) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);

        if (connectionWrapper == null) {
            cliOperationStatus.setFailedMessage("Connection was not found");
            return cliOperationStatus;
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider);

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true)) {
            List<SourceTableModel> schemaModels = sourceConnection.listTables(schemaName);
            if (schemaModels.size() == 0) {
                cliOperationStatus.setFailedMessage("No schemas found in the data source");
                return cliOperationStatus;
            }
            TableSearchFilters tableSearchFilters = new TableSearchFilters();
            tableSearchFilters.setConnectionName(connectionName);
            tableSearchFilters.setSchemaTableName(schemaName + ".*");
            tableSearchFilters.setDimensions(dimensions);
            tableSearchFilters.setLabels(labels);

            HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
            HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);
            Collection<TableWrapper> tableWrappers = hierarchyNodeTreeSearcher.findTables(userHome, tableSearchFilters);

            Table resultTable = Table.create().addColumns(
                    StringColumn.create("Source table name"),
                    StringColumn.create("Is imported"),
                    IntColumn.create("Imported columns count"),
                    IntColumn.create("Table hash"));

            for( SourceTableModel sourceTableModel : schemaModels) {
                Row row = resultTable.appendRow();
                TableWrapper tableWrapper = findTableFromNameAndSchema(sourceTableModel.getTableName().getTableName(), tableWrappers);
                row.setString(0, sourceTableModel.getTableName().toString());
                row.setString(1, tableWrapper != null ? "yes" : "");
                row.setInt(2, tableWrapper != null ? tableWrapper.getSpec().getColumns().size() : 0);
                row.setInt(3, tableWrapper.getSpec().hashCode());
            }

            cliOperationStatus.setSuccess(true);
            switch(tabularOutputFormat) {
                case CSV: {
                    cliOperationStatus.setMessage(this.outputFormatService.tableToCsv(resultTable));
                    break;
                }
                case JSON: {
                    cliOperationStatus.setMessage(this.outputFormatService.tableToJson(resultTable));
                    break;
                }
                default: {
                    cliOperationStatus.setTable(resultTable);
                    break;
                }
            }
        } catch (Exception e) {
            cliOperationStatus.setFailedMessage(e.getMessage());
        }
        return cliOperationStatus;
    }

    private int countImportedTablesFromSchema(String schemaName, Collection<TableWrapper> tableWrappers) {
        return tableWrappers.stream().filter(tableWrapper -> tableWrapper.getPhysicalTableName().getSchemaName().equals(schemaName))
                .collect(Collectors.toList()).size();
    }

    /**
     * Returns a schemas of local connections.
     * @param connectionName Connection name.
     * @param tabularOutputFormat Tabular output format.
     * @param dimensions Dimensions filter.
     * @param labels Labels filter.
     * @return Schema list.
     */
    public CliOperationStatus loadSchemaList(String connectionName, TabularOutputFormat tabularOutputFormat, String[] dimensions, String[] labels) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            cliOperationStatus.setFailedMessage("Connection was not found");
            return cliOperationStatus;
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider);

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true)) {
            List<SourceSchemaModel> schemaModels = sourceConnection.listSchemas();

            if (schemaModels.size() == 0) {
                cliOperationStatus.setFailedMessage("No schemas found in the data source");
                return cliOperationStatus;
            }

            TableSearchFilters tableSearchFilters = new TableSearchFilters();
            tableSearchFilters.setConnectionName(connectionName);
            tableSearchFilters.setDimensions(dimensions);
            tableSearchFilters.setLabels(labels);

            HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
            HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

            Collection<TableWrapper> tableWrappers = hierarchyNodeTreeSearcher.findTables(userHome, tableSearchFilters);

            Table resultTable = Table.create().addColumns(
                    StringColumn.create("Source schema name name"),
                    StringColumn.create("Is imported"),
                    IntColumn.create("Imported tables count"));

            for( SourceSchemaModel sourceSchemaModel : schemaModels) {
                Row row = resultTable.appendRow();
                int tablesCounter = countImportedTablesFromSchema(sourceSchemaModel.getSchemaName(), tableWrappers);
                row.setString(0, sourceSchemaModel.getSchemaName());
                row.setString(1, tablesCounter > 0 ? "yes" : "no");
                row.setInt(2, tablesCounter);
            }

            cliOperationStatus.setSuccess(true);
            switch(tabularOutputFormat) {
                case CSV: {
                    cliOperationStatus.setMessage(this.outputFormatService.tableToCsv(resultTable));
                    break;
                }
                case JSON: {
                    cliOperationStatus.setMessage(this.outputFormatService.tableToJson(resultTable));
                    break;
                }
                default: {
                    cliOperationStatus.setTable(resultTable);
                    break;
                }
            }
        } catch (Exception e) {
            cliOperationStatus.setFailedMessage(e.getMessage());
        }

        return cliOperationStatus;
    }

    /**
     * Returns a table of local connections.
     * @param dimensions Dimensions filter.
     * @param labels Labels filter.
     * @return Connection list.
     */
    public FormattedTableDto<ConnectionListModel> loadConnectionTable(String connectionNameFilter, String[] dimensions, String[] labels) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName(connectionNameFilter);
        connectionSearchFilters.setDimensions(dimensions);
        connectionSearchFilters.setLabels(labels);

        HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
        HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

        Collection<ConnectionSpec> connectionSpecs = hierarchyNodeTreeSearcher.findConnections(userHomeContextFactory.
                openLocalUserHome().getUserHome(), connectionSearchFilters);

        List<ConnectionListModel> connectionModels = connectionSpecs.stream().map(
                spec -> {
                    ConnectionListModel model = new ConnectionListModel();
                    model.setId(spec.getHierarchyId().hashCode64());
                    model.setName(spec.getConnectionName());
                    model.setDialect(spec.getProviderType());
                    model.setUrl(spec.getUrl());
                    model.setDatabaseName(spec.getDatabaseName());

                    return model;
                }
        ).collect(Collectors.toList());

        FormattedTableDto<ConnectionListModel> formattedTable = new FormattedTableDto<>();
        formattedTable.addRows(connectionModels);
        formattedTable.addColumnHeader("id", "Hash Id");
        formattedTable.addColumnHeader("name", "Connection Name");
        formattedTable.addColumnHeader("dialect", "Connection Type");
        formattedTable.addColumnHeader("url", "JDBC Url");
        formattedTable.addColumnHeader("databaseName", "Physical Database Name");

        return formattedTable;
    }

    /**
     * Adds a new connection.
     *
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return Cli operation status.
     */
    @Override
    public CliOperationStatus addConnection(String connectionName, ConnectionSpec connectionSpec) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        if (connections.getByObjectName(connectionName, true) != null) {
            cliOperationStatus.setFailedMessage("There is connection with this name");
            return cliOperationStatus;
        }

        ConnectionWrapper connectionWrapper = connections.createAndAddNew(connectionName);
        connectionWrapper.setSpec(connectionSpec);
        userHomeContext.flush();
        cliOperationStatus.setSuccesMessage(String.format(
                "Connection %s was successfully added.\nRun 'table import -c=%s' to import tables.", connectionName, connectionName));
        return cliOperationStatus;
    }

    /**
     * Remove a connection.
     * @param connectionName Connection name.
     * @return Cli operation status.
     */
    @Override
    public CliOperationStatus removeConnection(String connectionName) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName(connectionName);

        HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
        HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

        Collection<ConnectionSpec> connectionSpecs = hierarchyNodeTreeSearcher.findConnections(userHomeContextFactory.
                openLocalUserHome().getUserHome(), connectionSearchFilters);

        if (connectionSpecs.size() == 0) {
            cliOperationStatus.setFailedMessage("There are no connections with this name");
            return cliOperationStatus;
        }

        FormattedTableDto<ConnectionListModel> connectionTables = loadConnectionTable(connectionName, null, null);
        this.terminalTableWritter.writeTable(connectionTables, true);
        this.terminalWriter.writeLine("Do You want to remove these " + connectionTables.getRows().size() + " connections?");
        boolean response = this.terminalReader.promptBoolean("Yes or No", false, false);
        if (!response) {
            cliOperationStatus.setFailedMessage("You deleted 0 connections");
            return cliOperationStatus;
        }

        connectionSpecs.forEach(
                spec -> {
                    ConnectionWrapper connectionWrapper = connections.getByObjectName(spec.getConnectionName(), true);
                    connectionWrapper.markForDeletion();
                }
        );

        userHomeContext.flush();
        cliOperationStatus.setSuccesMessage(String.format("Successfully removed %d connections", connectionSpecs.size()));
        return cliOperationStatus;
    }

    /**
     * Update a connection.
     * @param connectionName Connection name.
     * @return Cli operation status.
     */
    @Override
    public CliOperationStatus updateConnection(String connectionName, ConnectionSpec connectionSpec) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName(connectionName);

        HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
        HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

        Collection<ConnectionSpec> connectionSpecs = hierarchyNodeTreeSearcher.findConnections(userHome, connectionSearchFilters);

        if (connectionSpecs.size() == 0) {
            cliOperationStatus.setFailedMessage("There are no connections with this name");
            return cliOperationStatus;
        }
        connectionSpecs.forEach(
                wrapperSpec -> {
                    if (connectionSpec.getUser() != null) {
                        wrapperSpec.setUser(connectionSpec.getUser());
                    }

                    if (connectionSpec.getDatabaseName() != null) {
                        wrapperSpec.setDatabaseName(connectionSpec.getDatabaseName());
                    }

                    if (connectionSpec.getUrl() != null) {
                        wrapperSpec.setUrl(connectionSpec.getUrl());
                    }

                    if (connectionSpec.getPassword() != null) {
                        wrapperSpec.setPassword(connectionSpec.getPassword());
                    }

                    if (connectionSpec.getProviderType() != null) {
                        wrapperSpec.setProviderType(connectionSpec.getProviderType());
                    }
                    userHomeContext.flush();
                }
        );

        cliOperationStatus.setSuccesMessage(String.format("Successfully updated %d connections", connectionSpecs.size()));
        return cliOperationStatus;
    }

    /**
     * Get a connection.
     * @param connectionName Connection name.
     * @return ConnectionWrapper.
     */
    @Override
    public ConnectionWrapper getConnection(String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        if (Strings.isNullOrEmpty(connectionName)) {
            return null;
        }

        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        return connectionWrapper;
    }

    /**
     * Delegates the connection configuration to the provider.
     *
     * @param connectionSpec Connection specification to fill.
     * @param isHeadless     When true and some required parameters are missing then throws an exception {@link CliRequiredParameterMissingException},
     *                       otherwise prompts the user to fill the answer.
     * @param terminalReader Terminal reader that may be used to prompt the user.
     * @param terminalWriter Terminal writer that should be used to write any messages.
     */
    @Override
    public void promptForConnectionParameters(ConnectionSpec connectionSpec, boolean isHeadless, TerminalReader terminalReader, TerminalWriter terminalWriter) {
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(connectionSpec.getProviderType());
        connectionProvider.promptForConnectionParameters(connectionSpec, isHeadless, terminalReader, terminalWriter);
    }

    /**
     * Finds a connection and opens the default text editor to edit the yaml file.
     * @param connectionName Connection name.
     * @return Error code: 0 when the table was found, -1 when the connection was not found.
     */
    @Override
    public int launchEditorForConnection(String connectionName) {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            this.terminalWriter.writeLine(String.format("Connection '%s' not found", connectionName));
            return -1;
        }
        this.editorLaunchService.openEditorForConnection(connectionWrapper);

        return 0;
    }
}
