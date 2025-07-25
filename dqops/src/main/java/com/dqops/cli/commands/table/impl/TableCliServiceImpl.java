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
import com.dqops.cli.output.OutputFormatService;
import com.dqops.cli.terminal.TerminalFactory;
import com.dqops.cli.terminal.TerminalReader;
import com.dqops.cli.terminal.TerminalTableWritter;
import com.dqops.cli.terminal.TerminalWriter;
import com.dqops.connectors.*;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJob;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJobParameters;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJobResult;
import com.dqops.core.principal.DqoUserPrincipalProvider;
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
    private final SecretValueProvider secretValueProvider;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final TerminalFactory terminalFactory;
    private final TerminalTableWritter terminalTableWritter;
    private final OutputFormatService outputFormatService;
    private final DqoJobQueue dqoJobQueue;
    private final DqoQueueJobFactory dqoQueueJobFactory;
    private DqoUserPrincipalProvider principalProvider;

    @Autowired
    public TableCliServiceImpl(TableService tableService,
                               UserHomeContextFactory userHomeContextFactory,
                               ConnectionProviderRegistry connectionProviderRegistry,
                               TerminalFactory terminalFactory,
                               SecretValueProvider secretValueProvider,
                               TerminalTableWritter terminalTableWritter,
                               OutputFormatService outputFormatService,
                               DqoJobQueue dqoJobQueue,
                               DqoQueueJobFactory dqoQueueJobFactory,
                               DqoUserPrincipalProvider principalProvider) {
        this.tableService = tableService;
        this.userHomeContextFactory = userHomeContextFactory;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.terminalFactory = terminalFactory;
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
        DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), true);
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
            Table resultTable = Table.create().addColumns(StringColumn.create("Schema name"));
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

        DqoUserPrincipal principal = this.principalProvider.getLocalUserPrincipal();
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

        DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), true);
        UserHome userHome = userHomeContext.getUserHome();

        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setConnection(connectionName);
        tableSearchFilters.setFullTableName(tableName);
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

        DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
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

        DqoUserPrincipal userPrincipal = this.principalProvider.getLocalUserPrincipal();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userPrincipal.getDataDomainIdentity(), false);
        UserHome userHome = userHomeContext.getUserHome();

        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setConnection(connectionName);
        tableSearchFilters.setFullTableName(fullTableName);

        HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
        HierarchyNodeTreeSearcherImpl hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

        Collection<TableWrapper> tableWrappers = hierarchyNodeTreeSearcher.findTables(userHome, tableSearchFilters);

        if (tableWrappers.size() == 0) {
            cliOperationStatus.setFailedMessage("There are no tables with these requirements");
            return cliOperationStatus;
        }

        CliOperationStatus listingStatus = listTables(connectionName, fullTableName, TabularOutputFormat.TABLE, null, null);
        this.terminalTableWritter.writeTable(listingStatus.getTable(), true);
        this.terminalFactory.getWriter().writeLine("Do you want to remove these " + tableWrappers.size() + " tables?");
        boolean response = this.terminalFactory.getReader().promptBoolean("Yes or No", false);
        if (!response) {
            cliOperationStatus.setFailedMessage("Delete operation cancelled.");
            return cliOperationStatus;
        }

        Map<String, Iterable<PhysicalTableName>> connToTablesMap = new LinkedHashMap<>();
        List<PhysicalTableName> tableNames = tableWrappers.stream()
                .map(TableWrapper::getPhysicalTableName)
                .collect(Collectors.toList());
        connToTablesMap.put(connectionName, tableNames);

        List<PushJobResult<DeleteStoredDataResult>> backgroundJobs = this.tableService.deleteTables(connToTablesMap, userPrincipal);

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

        DqoUserPrincipal principal = this.principalProvider.getLocalUserPrincipal();
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
