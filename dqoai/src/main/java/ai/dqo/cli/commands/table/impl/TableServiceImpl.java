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
package ai.dqo.cli.commands.table.impl;


import ai.dqo.cli.commands.TabularOutputFormat;
import ai.dqo.cli.commands.status.CliOperationStatus;
import ai.dqo.cli.output.OutputFormatService;
import ai.dqo.cli.terminal.TerminalReader;
import ai.dqo.cli.terminal.TerminalTableWritter;
import ai.dqo.cli.terminal.TerminalWriter;
import ai.dqo.connectors.*;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.search.StringPatternComparer;
import ai.dqo.metadata.search.TableSearchFilters;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalker;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Table metadata import service.
 */
@Service
@Scope("prototype")
public class TableServiceImpl implements TableService {
    private final UserHomeContextFactory userHomeContextFactory;
    private final TerminalReader terminalReader;
    private final TerminalWriter terminalWriter;
    private SecretValueProvider secretValueProvider;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final TerminalTableWritter terminalTableWritter;
    private final OutputFormatService outputFormatService;

    @Autowired
    public TableServiceImpl(UserHomeContextFactory userHomeContextFactory,
                            ConnectionProviderRegistry connectionProviderRegistry,
                            TerminalReader terminalReader,
                            TerminalWriter terminalWriter,
                            SecretValueProvider secretValueProvider,
                            TerminalTableWritter terminalTableWritter,
                            OutputFormatService outputFormatService) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.secretValueProvider = secretValueProvider;
        this.terminalTableWritter = terminalTableWritter;
        this.outputFormatService = outputFormatService;
    }

    /**
     * Loads a list of schemas from a given connection.
     *
     * @param connectionName Connection name.
     * @return Table with the results.
     * @throws TableImportFailedException Listing the schemas failed, the message is returned in the exception.
     */
    @Override
    public Table loadSchemaList(String connectionName, String schemaFilter) throws TableImportFailedException {
        ConnectionWrapper connectionWrapper = getConnection(connectionName);
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ProviderType providerType = connectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider);

        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true)) {
            List<SourceSchemaModel> schemas = sourceConnection.listSchemas().stream()
                    .filter(schema -> (schemaFilter == null || StringPatternComparer.matchSearchPattern(schema.getSchemaName(), schemaFilter)))
                    .collect(Collectors.toList());
            Table resultTable = Table.create().addColumns(StringColumn.create("Schema name"));
            for (SourceSchemaModel schemaModel : schemas) {
                Row row = resultTable.appendRow();
                row.setString(0, schemaModel.getSchemaName());
            }

            return resultTable;
        }
    }

    /**
     * Retrieves a connection by name.
     *
     * @param connectionName Connection name to return.
     * @return Connection specification object.
     * @throws TableImportFailedException Raised when the connection was not found.
     */
    public ConnectionWrapper getConnection(String connectionName) throws TableImportFailedException {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            throw new TableImportFailedException("Connection was not found");
        }

        return connectionWrapper;
    }

    /**
     * Returns boolean value if name fits tableFilterName.
     * @param name table name.
     * @param tableFilterName table filter name.
     * @return True value if name fits tableFilterName.
     */
    private boolean fitsTableFilterName(String name, String tableFilterName) {
        if (tableFilterName != null) {
            if (StringPatternComparer.isSearchPattern(tableFilterName)) {
                return StringPatternComparer.matchSearchPattern(name, tableFilterName);
            } else  {
                return tableFilterName.equals(name);
            }
        }
        return false;
    }

    /**
     * Filters table spec list by filter name.
     * @param baseSpecs list of table specs.
     * @param tableFilterName table filter name.
     * @return Table with a list of filtered tables.
     */
    private List<TableSpec> filterTableSpecs(List<TableSpec> baseSpecs, String tableFilterName) {
        List<TableSpec>resultList = new ArrayList<>();
        for (TableSpec spec: baseSpecs) {
            PhysicalTableName physicalTableName = spec.getTarget().toPhysicalTableName();
            String sourceTableName = physicalTableName.getTableName();
            if(fitsTableFilterName(sourceTableName, tableFilterName)) {
                resultList.add(spec);
            }
        }
        return resultList;
    }

    /**
     * Imports all tables to the connection from a given schema name.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @return Cli operation status.
     */
    public CliOperationStatus importTables(String connectionName, String schemaName, String tableName) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        if (tableName == null) {
            tableName = "*";
        }

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
            List<SourceTableModel> tableModels = sourceConnection.listTables(schemaName);
            if (tableModels.size() == 0) {
                cliOperationStatus.setFailedMessage("No tables found in the data source");
                return cliOperationStatus;
            }

            List<String> tableNames = tableModels.stream()
                    .map(tm -> tm.getTableName().getTableName())
                    .collect(Collectors.toList());

            List<TableSpec> sourceTableSpecs = sourceConnection.retrieveTableMetadata(schemaName, tableNames);
            sourceTableSpecs = filterTableSpecs(sourceTableSpecs, tableName);

            TableList currentTablesColl = connectionWrapper.getTables();
            currentTablesColl.importTables(sourceTableSpecs);
            userHomeContext.flush();

            Table resultTable = createDatasetTableFromTableSpecs(sourceTableSpecs);

            cliOperationStatus.setTable(resultTable);
            cliOperationStatus.setSuccess(true);
            return cliOperationStatus;
        }
    }

    /**
     * Creates a tablesaw table with a list of physical tables that will be imported.
     * @param sourceTableWrappers List of source tables to be imported.
     * @return Dataset with a summary of the import.
     */
    public Table createTablesTableFromTableSpecList(Collection<TableWrapper> sourceTableWrappers, UserHome userHome) {
        Table resultTable = Table.create().addColumns(
                LongColumn.create("Id"),
                StringColumn.create("Connection name"),
                StringColumn.create("Schema name"),
                StringColumn.create("Table name"),
                IntColumn.create("Column count"));

        for( TableWrapper sourceTableWrapper : sourceTableWrappers) {
            Row row = resultTable.appendRow();
            ConnectionWrapper connectionWrapper = userHome.findConnectionFor(sourceTableWrapper.getHierarchyId());
            TableSpec sourceTableSpec = sourceTableWrapper.getSpec();
            row.setLong(0, sourceTableSpec.getHierarchyId().hashCode64());
            row.setString(1, connectionWrapper.getName());
            row.setString(2, sourceTableSpec.getTarget().getSchemaName());
            row.setString(3, sourceTableSpec.getTarget().getTableName());
            row.setInt(4, sourceTableSpec.getColumns().size());
        }
        return resultTable;
    }

    /**
     * List all tables to the connection from a given schema name.
     * @param connectionName Connection name.
     * @param tableName Table name filter.
     * @param tabularOutputFormat tabular output format.
     * @return Cli operation status.
     */
    public CliOperationStatus listTables(String connectionName, String tableName, TabularOutputFormat tabularOutputFormat) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setConnectionName(connectionName);
        tableSearchFilters.setSchemaTableName(tableName);

        HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
        HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

        Collection<TableWrapper> tableWrappers = hierarchyNodeTreeSearcher.findTables(userHome, tableSearchFilters);

        if (tableWrappers.size() == 0) {
            cliOperationStatus.setFailedMessage("There are no tables with these requirements");
            return cliOperationStatus;
        }

        Table resultTable = createTablesTableFromTableSpecList(tableWrappers, userHome);

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
        return cliOperationStatus;
    }

    /**
     * Creates a tablesaw table with a list of physical tables that will be imported.
     * @param sourceTableSpecs List of source tables to be imported.
     * @return Dataset with a summary of the import.
     */
    public Table createDatasetTableFromTableSpecs(List<TableSpec> sourceTableSpecs) {
        Table resultTable = Table.create().addColumns(
                StringColumn.create("Schema name"),
                StringColumn.create("Table name"),
                IntColumn.create("Column count"));

        for( TableSpec sourceTableSpec : sourceTableSpecs) {
            Row row = resultTable.appendRow();
            row.setString(0, sourceTableSpec.getTarget().getSchemaName());
            row.setString(1, sourceTableSpec.getTarget().getTableName());
            row.setInt(2, sourceTableSpec.getColumns().size());
        }
        return resultTable;
    }

    /**
     * Adds a table to the connection from a given schema and table name.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @return Cli operation status.
     */
    public CliOperationStatus addTable(String connectionName, String schemaName, String tableName) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            cliOperationStatus.setFailedMessage("There are no connections with this name");
            return cliOperationStatus;
        }

        PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
        if(connectionWrapper.getTables().createAndAddNew(physicalTableName) == null) {
            cliOperationStatus.setFailedMessage("There are no tables with this name");
            return cliOperationStatus;
        }
        userHomeContext.flush();
        cliOperationStatus.setSuccesMessage(String.format("Table %s was successfully added.", physicalTableName.toBaseFileName()));
        return cliOperationStatus;
    }

    /**
     * Removes a table to the connection from a given schema and table name.
     * @param connectionName Connection name.
     * @param fullTableName Full table name.
     * @return Cli operation status.
     */
    public CliOperationStatus removeTable(String connectionName, String fullTableName) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setConnectionName(connectionName);
        tableSearchFilters.setSchemaTableName(fullTableName);

        HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
        HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

        Collection<TableWrapper> tableWrappers = hierarchyNodeTreeSearcher.findTables(userHome, tableSearchFilters);

        if (tableWrappers.size() == 0) {
            cliOperationStatus.setFailedMessage("There are no tables with these requirements");
            return cliOperationStatus;
        }

        CliOperationStatus listingStatus = listTables(connectionName, fullTableName, TabularOutputFormat.TABLE);
        this.terminalTableWritter.writeTable(listingStatus.getTable(), true);
        this.terminalWriter.writeLine("Do You want to remove these " + tableWrappers.size() + " tables?");
        boolean response = this.terminalReader.promptBoolean("Yes or No", false, false);
        if (!response) {
            cliOperationStatus.setFailedMessage("You deleted 0 tables");
            return cliOperationStatus;
        }

        tableWrappers.forEach(
                tableWrapper -> {
                    tableWrapper.markForDeletion();
                    userHomeContext.flush();
                }
        );

        cliOperationStatus.setSuccesMessage(String.format("Successfully removed %d tables", tableWrappers.size()));
        return cliOperationStatus;
    }

    /**
     * Updates a table to the connection from a given schema and table name.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Table name.
     * @return Cli operation status.
     */
    public CliOperationStatus updateTable(String connectionName, String schemaName, String tableName, String newTableName) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        CliOperationStatus deleteStatus = removeTable(connectionName, schemaName + "." + tableName);
        if (!deleteStatus.isSuccess()) {
            cliOperationStatus.setFailedMessage(deleteStatus.getMessage());
            return cliOperationStatus;
        }

        CliOperationStatus addStatus = addTable(connectionName, schemaName, newTableName);
        if (!addStatus.isSuccess()) {
            cliOperationStatus.setFailedMessage(addStatus.getMessage());
            return cliOperationStatus;
        }

        cliOperationStatus.setSuccesMessage(String.format("Table %s.%s was successfully updated.", schemaName, tableName));
        return cliOperationStatus;
    }

}
