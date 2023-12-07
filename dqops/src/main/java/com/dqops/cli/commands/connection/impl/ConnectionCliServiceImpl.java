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
import com.dqops.cli.edit.EditorLaunchService;
import com.dqops.cli.exceptions.CliRequiredParameterMissingException;
import com.dqops.cli.output.OutputFormatService;
import com.dqops.cli.terminal.*;
import com.dqops.connectors.*;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.principal.DqoUserIdentity;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.scheduler.defaults.DefaultSchedulesProvider;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.metadata.search.ConnectionSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalker;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.services.metadata.ConnectionService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Connection management service.
 */
@Component
public class ConnectionCliServiceImpl implements ConnectionCliService {
    private final ConnectionService connectionService;
    private final UserHomeContextFactory userHomeContextFactory;
    private final TerminalFactory terminalFactory;
    private final TerminalTableWritter terminalTableWritter;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final SecretValueProvider secretValueProvider;
    private final OutputFormatService outputFormatService;
    private final EditorLaunchService editorLaunchService;
    private final DefaultSchedulesProvider defaultSchedulesProvider;
    private final DqoUserPrincipalProvider dqoUserPrincipalProvider;

    @Autowired
    public ConnectionCliServiceImpl(ConnectionService connectionService,
                                    UserHomeContextFactory userHomeContextFactory,
                                    ConnectionProviderRegistry connectionProviderRegistry,
                                    TerminalFactory terminalFactory,
                                    TerminalTableWritter terminalTableWritter,
                                    SecretValueProvider secretValueProvider,
                                    OutputFormatService outputFormatService,
                                    EditorLaunchService editorLaunchService,
                                    DefaultSchedulesProvider defaultSchedulesProvider,
                                    DqoUserPrincipalProvider dqoUserPrincipalProvider) {
        this.connectionService = connectionService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.terminalFactory = terminalFactory;
        this.terminalTableWritter = terminalTableWritter;
        this.secretValueProvider = secretValueProvider;
        this.outputFormatService = outputFormatService;
        this.editorLaunchService = editorLaunchService;
        this.defaultSchedulesProvider = defaultSchedulesProvider;
        this.dqoUserPrincipalProvider = dqoUserPrincipalProvider;
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
     * @return CLI operation status.
     */
    @Override
    public CliOperationStatus showTableForConnection(String connectionName, String fullTableName, TabularOutputFormat tabularOutputFormat) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity userIdentity = userPrincipal.createIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);

        if (connectionWrapper == null) {
            cliOperationStatus.setFailedMessage("Connection was not found");
            return cliOperationStatus;
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        PhysicalTableName physicalTableName = PhysicalTableName.fromSchemaTableFilter(fullTableName);

        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext)) {
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
                    TextColumn.create("Source column name"),
                    TextColumn.create("Source column type"),
                    TextColumn.create("Imported column name"),
                    TextColumn.create("Imported column type"));

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
     * @return CLI operation status.
     */
    @Override
    public CliOperationStatus loadTableList(String connectionName, String schemaName, String tableName, TabularOutputFormat tabularOutputFormat,
                                            String[] dimensions, String[] labels) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity userIdentity = userPrincipal.createIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionList connections = userHome.getConnections();
        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);

        if (connectionWrapper == null) {
            cliOperationStatus.setFailedMessage("Connection was not found");
            return cliOperationStatus;
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext)) {
            List<SourceTableModel> schemaModels = sourceConnection.listTables(schemaName);
            if (schemaModels.size() == 0) {
                cliOperationStatus.setFailedMessage("No schemas found in the data source");
                return cliOperationStatus;
            }
            TableSearchFilters tableSearchFilters = new TableSearchFilters();
            tableSearchFilters.setConnection(connectionName);
            tableSearchFilters.setFullTableName(schemaName + ".*");
            tableSearchFilters.setTags(dimensions);
            tableSearchFilters.setLabels(labels);

            HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
            HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);
            Collection<TableWrapper> tableWrappers = hierarchyNodeTreeSearcher.findTables(userHome, tableSearchFilters);

            Table resultTable = Table.create().addColumns(
                    TextColumn.create("Source table name"),
                    TextColumn.create("Is imported"),
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

        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity userIdentity = userPrincipal.createIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            cliOperationStatus.setFailedMessage("Connection was not found");
            return cliOperationStatus;
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext)) {
            List<SourceSchemaModel> schemaModels = sourceConnection.listSchemas();

            if (schemaModels.size() == 0) {
                cliOperationStatus.setFailedMessage("No schemas found in the data source");
                return cliOperationStatus;
            }

            TableSearchFilters tableSearchFilters = new TableSearchFilters();
            tableSearchFilters.setConnection(connectionName);
            tableSearchFilters.setTags(dimensions);
            tableSearchFilters.setLabels(labels);

            HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
            HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

            Collection<TableWrapper> tableWrappers = hierarchyNodeTreeSearcher.findTables(userHome, tableSearchFilters);

            Table resultTable = Table.create().addColumns(
                    TextColumn.create("Source schema name"),
                    TextColumn.create("Is imported"),
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
        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity userIdentity = userPrincipal.createIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
        UserHome userHome = userHomeContext.getUserHome();

        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName(connectionNameFilter);
        connectionSearchFilters.setTags(dimensions);
        connectionSearchFilters.setLabels(labels);

        HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
        HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

        Collection<ConnectionSpec> connectionSpecs = hierarchyNodeTreeSearcher.findConnections(userHome, connectionSearchFilters);

        List<ConnectionListModel> connectionModels = connectionSpecs.stream().map(
                spec -> {
                    ConnectionListModel model = new ConnectionListModel();
                    model.setId(spec.getHierarchyId().hashCode64());
                    model.setName(spec.getConnectionName());
                    model.setDialect(spec.getProviderType());
                    model.setDatabaseName(spec.getProviderSpecificConfiguration().getDatabase());

                    return model;
                }
        ).collect(Collectors.toList());

        FormattedTableDto<ConnectionListModel> formattedTable = new FormattedTableDto<>();
        formattedTable.addRows(connectionModels);
        formattedTable.addColumnHeader("id", "Hash Id");
        formattedTable.addColumnHeader("name", "Connection Name");
        formattedTable.addColumnHeader("dialect", "Connection Type");
        formattedTable.addColumnHeader("databaseName", "Physical Database Name");

        return formattedTable;
    }

    /**
     * Adds a new connection.
     *
     * @param connectionName Connection name.
     * @param connectionSpec Connection specification.
     * @return CLI operation status.
     */
    @Override
    public CliOperationStatus addConnection(String connectionName, ConnectionSpec connectionSpec) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity userIdentity = userPrincipal.createIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        if (connections.getByObjectName(connectionName, true) != null) {
            cliOperationStatus.setFailedMessage("There is connection with this name");
            return cliOperationStatus;
        }

        ConnectionWrapper connectionWrapper = connections.createAndAddNew(connectionName);
        connectionWrapper.setSpec(connectionSpec);
        if (connectionSpec.getSchedules() == null) {
            // no configuration, apply the defaults
            connectionSpec.setSchedules(this.defaultSchedulesProvider.createMonitoringSchedulesSpecForNewConnection(userHome));
        }
        userHomeContext.flush();
        cliOperationStatus.setSuccessMessage(String.format(
                "Connection %s was successfully added.\nRun 'table import -c=%s' to import tables.", connectionName, connectionName));
        return cliOperationStatus;
    }

    /**
     * Remove a connection.
     * @param connectionName Connection name.
     * @return CLI operation status.
     */
    @Override
    public CliOperationStatus removeConnection(String connectionName) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity userIdentity = userPrincipal.createIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
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

        FormattedTableDto<ConnectionListModel> connectionTables = loadConnectionTable(connectionName, null, null);
        this.terminalTableWritter.writeTable(connectionTables, true);
        this.terminalFactory.getWriter().writeLine("Do you want to remove these " + connectionTables.getRows().size() + " connections?");
        boolean response = this.terminalFactory.getReader().promptBoolean("Yes or No", false);
        if (!response) {
            cliOperationStatus.setFailedMessage("Delete operation cancelled.");
            return cliOperationStatus;
        }

        List<String> connectionNames = connectionSpecs.stream()
                .map(ConnectionSpec::getConnectionName)
                .collect(Collectors.toList());

        List<PushJobResult<DeleteStoredDataResult>> backgroundJobs = this.connectionService.deleteConnections(
                connectionNames, userPrincipal);

        try {
            for (PushJobResult<DeleteStoredDataResult> job: backgroundJobs) {
                job.getFinishedFuture().get();
            }
        } catch (InterruptedException e) {
            cliOperationStatus.setSuccessMessage(String.format("Removed %d connections.", connectionNames.size())
                    + " Deleting results for these connections has been cancelled."
            );
            return cliOperationStatus;
        } catch (ExecutionException e) {
            cliOperationStatus.setSuccessMessage(String.format("Removed %d connections.", connectionNames.size())
                    + " An exception occurred while deleting results for these connections."
            );
            return cliOperationStatus;
        }

        cliOperationStatus.setSuccessMessage(String.format("Successfully removed %d connections", connectionNames.size()));
        return cliOperationStatus;
    }

    /**
     * Update a connection.
     * @param connectionName Connection name.
     * @return CLI operation status.
     */
    @Override
    public CliOperationStatus updateConnection(String connectionName, ConnectionSpec connectionSpec) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity userIdentity = userPrincipal.createIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
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
                    wrapperSpec.copyNotNullPropertiesFrom(connectionSpec.deepClone());
                }
        );
        userHomeContext.flush();

        cliOperationStatus.setSuccessMessage(String.format("Successfully updated %d connection(s)", connectionSpecs.size()));
        return cliOperationStatus;
    }

    /**
     * Get a connection.
     * @param connectionName Connection name.
     * @return ConnectionWrapper.
     */
    @Override
    public ConnectionWrapper getConnection(String connectionName) {
        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity userIdentity = userPrincipal.createIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
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
        DqoUserPrincipal userPrincipal = this.dqoUserPrincipalProvider.createUserPrincipalForAdministrator();
        DqoUserIdentity userIdentity = userPrincipal.createIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            this.terminalFactory.getWriter().writeLine(String.format("Connection '%s' not found", connectionName));
            return -1;
        }
        this.editorLaunchService.openEditorForConnection(connectionWrapper);

        return 0;
    }
}
