/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.jobs.schema;

import com.dqops.connectors.*;
import com.dqops.core.configuration.DqoMetadataImportConfigurationProperties;
import com.dqops.core.jobqueue.DqoJobExecutionContext;
import com.dqops.core.jobqueue.DqoJobType;
import com.dqops.core.jobqueue.DqoQueueJob;
import com.dqops.core.jobqueue.concurrency.ConcurrentJobType;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyTarget;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.search.StringPatternComparer;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Queue job that imports tables from a source connection.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ImportSchemaQueueJob extends DqoQueueJob<ImportSchemaQueueJobResult> {
    private final UserHomeContextFactory userHomeContextFactory;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final SecretValueProvider secretValueProvider;
    private final DqoMetadataImportConfigurationProperties metadataImportConfigurationProperties;
    private ImportSchemaQueueJobParameters importParameters;

    @Autowired
    public ImportSchemaQueueJob(UserHomeContextFactory userHomeContextFactory,
                                ConnectionProviderRegistry connectionProviderRegistry,
                                SecretValueProvider secretValueProvider,
                                DqoMetadataImportConfigurationProperties metadataImportConfigurationProperties) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.secretValueProvider = secretValueProvider;
        this.metadataImportConfigurationProperties = metadataImportConfigurationProperties;
    }

    /**
     * Returns the import parameters object.
     * @return Import parameters object.
     */
    public ImportSchemaQueueJobParameters getImportParameters() {
        return importParameters;
    }

    /**
     * Sets the parameters object for the job that identifies the connection and schema that should be imported.
     * @param importParameters Import parameters to store.
     */
    public void setImportParameters(ImportSchemaQueueJobParameters importParameters) {
        this.importParameters = importParameters;
    }

    /**
     * Returns boolean value if name fits tableFilterName.
     * @param name table name.
     * @param tableFilterName table filter name.
     * @return True value if name fits tableFilterName.
     */
    public boolean fitsTableFilterName(String name, String tableFilterName) {
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
    public List<TableSpec> filterTableSpecs(List<TableSpec> baseSpecs, String tableFilterName) {
        List<TableSpec>resultList = new ArrayList<>();
        for (TableSpec spec: baseSpecs) {
            PhysicalTableName physicalTableName = spec.getPhysicalTableName();
            String sourceTableName = physicalTableName.getTableName();
            if(fitsTableFilterName(sourceTableName, tableFilterName)) {
                resultList.add(spec);
            }
        }
        return resultList;
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
            row.setString(0, sourceTableSpec.getPhysicalTableName().getSchemaName());
            row.setString(1, sourceTableSpec.getPhysicalTableName().getTableName());
            row.setInt(2, sourceTableSpec.getColumns().size());
        }
        return resultTable;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public ImportSchemaQueueJobResult onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        String tableNameContains = this.importParameters.getTableNameContains();

        UserDomainIdentity userIdentity = this.getPrincipal().getDataDomainIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity, false);
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionWrapper connectionWrapper = connections.getByObjectName(this.importParameters.getConnectionName(), true);
        if (connectionWrapper == null) {
            throw new ImportSchemaQueueJobException("Connection " + this.importParameters.getConnectionName() + "  was not found");
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext)) {
            List<SourceTableModel> tableModels = sourceConnection.listTables(this.importParameters.getSchemaName(), tableNameContains,
                    this.metadataImportConfigurationProperties.getTablesImportLimit(), secretValueLookupContext);
            if (tableModels.size() == 0) {
                throw new ImportSchemaQueueJobException("No tables found in the data source when importing tables on the " +
                        this.importParameters.getConnectionName() + ", from the " + this.importParameters.getSchemaName() + " schema");
            }

            List<String> tableNames = tableModels.stream()
                    .map(tm -> tm.getTableName().getTableName())
                    .collect(Collectors.toList());

            List<TableSpec> sourceTableSpecs = sourceConnection.retrieveTableMetadata(this.importParameters.getSchemaName(),
                    tableNameContains, this.metadataImportConfigurationProperties.getTablesImportLimit(),
                    tableNames, connectionWrapper, secretValueLookupContext);
            List<TableSpec> filteredSourceTableSpecs = filterTableSpecs(sourceTableSpecs, tableNameContains);
//
            TableList currentTablesColl = connectionWrapper.getTables();
            currentTablesColl.importTables(filteredSourceTableSpecs, connectionSpec.getDefaultGroupingConfiguration());
            userHomeContext.flush();

            Table resultTable = createDatasetTableFromTableSpecs(filteredSourceTableSpecs);

            return new ImportSchemaQueueJobResult(resultTable);
        }
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.import_schema;
    }

    /**
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel()
        {{
            setImportSchemaParameters(importParameters);
        }};
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        ImportSchemaQueueJobConcurrencyTarget target = new ImportSchemaQueueJobConcurrencyTarget(
                this.importParameters.getConnectionName(), this.importParameters.getSchemaName());
        JobConcurrencyTarget concurrencyTarget = new JobConcurrencyTarget(ConcurrentJobType.IMPORT_SCHEMA, target);
        JobConcurrencyConstraint schemaImportLimit = new JobConcurrencyConstraint(concurrencyTarget, 1);
        return new JobConcurrencyConstraint[] { schemaImportLimit };
    }
}
