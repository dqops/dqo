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
import com.dqops.cli.output.OutputFormatService;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalTableWritter;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.connectors.*;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.data.DeleteStoredDataQueueJobResult;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJob;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJobParameters;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJobResult;
import com.dqops.core.principal.DqoCloudApiKeyPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.data.models.DeleteStoredDataResult;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.search.StringPatternComparer;
import com.dqops.metadata.search.TableSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalker;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.services.metadata.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tech.tablesaw.api.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * Table metadata import service.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TableCliServiceImpl implements TableCliService {
    private final TableService tableService;
    private final UserHomeContextFactory userHomeContextFactory;
    private final TerminalReader terminalReader;
    private final TerminalWriter terminalWriter;
    private final SecretValueProvider secretValueProvider;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final TerminalTableWritter terminalTableWritter;
    private final OutputFormatService outputFormatService;
    private final DqoJobQueue dqoJobQueue;
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private DqoCloudApiKeyPrincipalProvider principalProvider;

    @Autowired
    public TableCliServiceImpl(TableService tableService,
                               UserHomeContextFactory userHomeContextFactory,
                               ConnectionProviderRegistry connectionProviderRegistry,
                               TerminalReader terminalReader,
                               TerminalWriter terminalWriter,
                               SecretValueProvider secretValueProvider,
                               TerminalTableWritter terminalTableWritter,
                               OutputFormatService outputFormatService,
                               DqoJobQueue dqoJobQueue,
                               DqoQueueJobFactory dqoQueueJobFactory,
                               DqoCloudApiKeyPrincipalProvider principalProvider) {
        this.tableService = tableService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.terminalReader = terminalReader;
        this.terminalWriter = terminalWriter;
        this.secretValueProvider = secretValueProvider;
        this.terminalTableWritter = terminalTableWritter;
        this.outputFormatService = outputFormatService;
        this.dqoJobQueue = dqoJobQueue;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.principalProvider = principalProvider;
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
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionWrapper connectionWrapper = connections.getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            throw new TableImportFailedException("Connection was not found");
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ProviderType providerType = connectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext)) {
            List<SourceSchemaModel> schemas = sourceConnection.listSchemas().stream()
                    .filter(schema -> (schemaFilter == null || StringPatternComparer.matchSearchPattern(schema.getSchemaName(), schemaFilter)))
                    .collect(Collectors.toList());
            Table resultTable = Table.create().addColumns(TextColumn.create("Schema name"));
            for (SourceSchemaModel schemaModel : schemas) {
                Row row = resultTable.appendRow();
                row.setString(0, schemaModel.getSchemaName());
            }

            return resultTable;
        }
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
     * Imports all tables to the connection from a given schema name.
     * @param connectionName Connection name.
     * @param schemaName Schema name.
     * @param tableName Optional table name pattern.
     * @return Cli operation status.
     */
    public CliOperationStatus importTables(String connectionName, String schemaName, String tableName) {
        ImportSchemaQueueJob importSchemaJob = this.dqoQueueJobFactory.createImportSchemaJob();
        ImportSchemaQueueJobParameters importParameters = new ImportSchemaQueueJobParameters(
                connectionName, schemaName, tableName);
        importSchemaJob.setImportParameters(importParameters);

        DqoUserPrincipal principal = this.principalProvider.createUserPrincipal();
        PushJobResult<ImportSchemaQueueJobResult> pushJobResult = this.dqoJobQueue.pushJob(importSchemaJob, principal);

        try {
            ImportSchemaQueueJobResult importSchemaQueueJobResult = pushJobResult.getFinishedFuture().get(); // TODO: add import timeout to stop blocking the CLI and run the import in the background after a while

            CliOperationStatus importSuccessStatus = new CliOperationStatus();
            importSuccessStatus.setTable(importSchemaQueueJobResult.getImportedTables());
            importSuccessStatus.setSuccess(true);
            return importSuccessStatus;
        } catch (ExecutionException | InterruptedException e) {
            CliOperationStatus importFailedStatus = new CliOperationStatus();
            importFailedStatus.setFailedMessage(e.getMessage()); // TODO: probably the actual exception from the job is in an inner exception
            return importFailedStatus;
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
                TextColumn.create("Connection name"),
                TextColumn.create("Schema name"),
                TextColumn.create("Table name"),
                IntColumn.create("Column count"));

        for( TableWrapper sourceTableWrapper : sourceTableWrappers) {
            Row row = resultTable.appendRow();
            ConnectionWrapper connectionWrapper = userHome.findConnectionFor(sourceTableWrapper.getHierarchyId());
            TableSpec sourceTableSpec = sourceTableWrapper.getSpec();
            row.setLong(0, sourceTableSpec.getHierarchyId().hashCode64());
            row.setString(1, connectionWrapper.getName());
            row.setString(2, sourceTableSpec.getPhysicalTableName().getSchemaName());
            row.setString(3, sourceTableSpec.getPhysicalTableName().getTableName());
            row.setInt(4, sourceTableSpec.getColumns().size());
        }
        return resultTable;
    }

    /**
     * List all tables to the connection from a given schema name.
     * @param connectionName Connection name.
     * @param tableName Table name filter.
     * @param tabularOutputFormat tabular output format.
     * @param dimensions Dimensions filter.
     * @param labels Labels filter.
     * @return Cli operation status.
     */
    public CliOperationStatus listTables(String connectionName, String tableName, TabularOutputFormat tabularOutputFormat, String[] dimensions, String[] labels) {
        CliOperationStatus cliOperationStatus = new CliOperationStatus();

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setConnectionName(connectionName);
        tableSearchFilters.setSchemaTableName(tableName);
        tableSearchFilters.setTags(dimensions);
        tableSearchFilters.setLabels(labels);

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
        cliOperationStatus.setSuccessMessage(String.format("Table %s was successfully added.", physicalTableName.toBaseFileName()));
        return cliOperationStatus;
    }

    /**
     * Removes a table to the connection from a given schema and table name.
     * @param connectionName Connection name.
     * @param fullTableName Full table name.
     * @return Cli operation status.
     */
    @Override
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

        CliOperationStatus listingStatus = listTables(connectionName, fullTableName, TabularOutputFormat.TABLE, null, null);
        this.terminalTableWritter.writeTable(listingStatus.getTable(), true);
        this.terminalWriter.writeLine("Do you want to remove these " + tableWrappers.size() + " tables?");
        boolean response = this.terminalReader.promptBoolean("Yes or No", false);
        if (!response) {
            cliOperationStatus.setFailedMessage("Delete operation cancelled.");
            return cliOperationStatus;
        }

        Map<String, Iterable<PhysicalTableName>> connToTablesMap = new HashMap<>();
        List<PhysicalTableName> tableNames = tableWrappers.stream()
                .map(TableWrapper::getPhysicalTableName)
                .collect(Collectors.toList());
        connToTablesMap.put(connectionName, tableNames);

        DqoUserPrincipal principal = this.principalProvider.createUserPrincipal();
        List<PushJobResult<DeleteStoredDataResult>> backgroundJobs =this.tableService.deleteTables(connToTablesMap, principal);

        try {
            for (PushJobResult<DeleteStoredDataResult> job: backgroundJobs) {
                job.getFinishedFuture().get();
            }
        } catch (InterruptedException e) {
            cliOperationStatus.setSuccessMessage(String.format("Removed %d tables.", tableWrappers.size())
                    + " Deleting results for these tables has been cancelled."
            );
            return cliOperationStatus;
        } catch (ExecutionException e) {
            cliOperationStatus.setSuccessMessage(String.format("Removed %d tables.", tableWrappers.size())
                    + " An exception occurred while deleting results for these tables."
            );
            return cliOperationStatus;
        }

        cliOperationStatus.setSuccessMessage(String.format("Successfully removed %d tables", tableWrappers.size()));
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

        DqoUserPrincipal principal = this.principalProvider.createUserPrincipal();
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

        cliOperationStatus.setSuccessMessage(String.format("Table %s.%s was successfully updated.", schemaName, tableName));
        return cliOperationStatus;
    }

}
