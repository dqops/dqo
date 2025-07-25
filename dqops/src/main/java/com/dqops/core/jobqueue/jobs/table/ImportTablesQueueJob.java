/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.jobqueue.jobs.table;

import com.dqops.connectors.*;
import com.dqops.core.configuration.DqoMetadataImportConfigurationProperties;
import com.dqops.core.jobqueue.DqoJobExecutionContext;
import com.dqops.core.jobqueue.DqoJobType;
import com.dqops.core.jobqueue.DqoQueueJob;
import com.dqops.core.jobqueue.concurrency.ConcurrentJobType;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyTarget;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJobConcurrencyTarget;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.search.pattern.SearchPattern;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Queue job that imports a single table from a source connection.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ImportTablesQueueJob extends DqoQueueJob<ImportTablesResult> {
    private final UserHomeContextFactory userHomeContextFactory;
    private final ConnectionProviderRegistry connectionProviderRegistry;
    private final SecretValueProvider secretValueProvider;
    private final DqoMetadataImportConfigurationProperties metadataImportConfigurationProperties;
    private ImportTablesQueueJobParameters importParameters;

    @Autowired
    public ImportTablesQueueJob(UserHomeContextFactory userHomeContextFactory,
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
    public ImportTablesQueueJobParameters getImportParameters() {
        return importParameters;
    }

    /**
     * Sets the parameters object for the job that identifies the connection, schema and table that should be imported.
     * @param importParameters Import parameters to store.
     */
    public void setImportParameters(ImportTablesQueueJobParameters importParameters) {
        this.importParameters = importParameters;
    }

    /**
     * Creates a tablesaw table with a list of imported tables.
     * @param sourceTableSpecs List of table that have been imported.
     * @return Dataset with a summary of the import.
     */
    public Table createDatasetTableFromTableSpecs(List<TableSpec> sourceTableSpecs) {
        // TODO: move method to tablesaw utils (repeated in ImportSchemaQueueJob).
        Table resultTable = createEmptyOutputResult();

        for(TableSpec sourceTableSpec : sourceTableSpecs) {
            Row row = resultTable.appendRow();
            row.setString(0, sourceTableSpec.getPhysicalTableName().getSchemaName());
            row.setString(1, sourceTableSpec.getPhysicalTableName().getTableName());
            row.setInt(2, sourceTableSpec.getColumns().size());
        }
        return resultTable;
    }

    /**
     * Creates an empty result table
     * @return Empty result table.
     */
    private Table createEmptyOutputResult() {
        return Table.create().addColumns(
                StringColumn.create("Schema name"),
                StringColumn.create("Table name"),
                IntColumn.create("Column count"));
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public ImportTablesResult onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        UserDomainIdentity userIdentity = this.getPrincipal().getDataDomainIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity, false);
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionWrapper connectionWrapper = connections.getByObjectName(this.importParameters.getConnectionName(), true);
        if (connectionWrapper == null) {
            throw new ImportTablesQueueJobException("Connection " + this.importParameters.getConnectionName() + "  was not found");
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        SecretValueLookupContext secretValueLookupContext = new SecretValueLookupContext(userHome);
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider, secretValueLookupContext);

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);

        String schemaNameFilter = Strings.isNullOrEmpty(this.importParameters.getSchemaName()) ? "*" : this.importParameters.getSchemaName();
        SearchPattern schemaSearchPattern = SearchPattern.create(false, schemaNameFilter);

        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext)) {
            if (schemaSearchPattern.isWildcardSearchPattern()) {
                // must iterate over schemas
                List<SourceSchemaModel> sourceSchemaModels = sourceConnection.listSchemas();
                jobExecutionContext.getCancellationToken().throwIfCancelled();
                List<TableSpec> fullTableList = new ArrayList<>();
                Table fullTableResult = createEmptyOutputResult();

                for (SourceSchemaModel sourceSchemaModel : sourceSchemaModels) {
                    jobExecutionContext.getCancellationToken().throwIfCancelled();
                    
                    if (!schemaSearchPattern.match(sourceSchemaModel.getSchemaName())) {
                        continue;
                    }

                    List<TableSpec> sourceTableSpecs = sourceConnection.retrieveTableMetadata(
                            sourceSchemaModel.getSchemaName(),
                            this.importParameters.getTableNameContains(),
                            this.importParameters.getTablesImportLimit() != null ? this.importParameters.getTablesImportLimit() :
                                    this.metadataImportConfigurationProperties.getTablesImportLimit(),
                            this.importParameters.getTableNames(),
                            connectionWrapper,
                            secretValueLookupContext);

                    List<TableSpec> importedTablesSpecs = sourceTableSpecs
                            .stream()
                            .map(tableSpec -> tableSpec.deepClone())
                            .collect(Collectors.toList());

                    TableList currentTablesColl = connectionWrapper.getTables();
                    currentTablesColl.importTables(importedTablesSpecs, connectionSpec.getDefaultGroupingConfiguration());
                    fullTableList.addAll(importedTablesSpecs);

                    Table resultTable = createDatasetTableFromTableSpecs(importedTablesSpecs);
                    fullTableResult.append(resultTable);
                }

                userHomeContext.flush();
                return new ImportTablesResult(fullTableResult, fullTableList);
            } else {
                // only one schema
                List<TableSpec> sourceTableSpecs = sourceConnection.retrieveTableMetadata(
                        this.importParameters.getSchemaName(),
                        this.importParameters.getTableNameContains(),
                        this.metadataImportConfigurationProperties.getTablesImportLimit(),
                        this.importParameters.getTableNames(),
                        connectionWrapper,
                        secretValueLookupContext);

                List<TableSpec> importedTablesSpecs = sourceTableSpecs
                        .stream()
                        .map(tableSpec -> tableSpec.deepClone())
                        .collect(Collectors.toList());

                TableList currentTablesColl = connectionWrapper.getTables();
                currentTablesColl.importTables(importedTablesSpecs, connectionSpec.getDefaultGroupingConfiguration());
                userHomeContext.flush();

                Table resultTable = createDatasetTableFromTableSpecs(importedTablesSpecs);

                return new ImportTablesResult(resultTable, sourceTableSpecs);
            }
        }
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.import_tables;
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
            setImportTableParameters(importParameters);
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
                this.importParameters.getConnectionName(),
                this.importParameters.getSchemaName());
        JobConcurrencyTarget concurrencyTarget = new JobConcurrencyTarget(ConcurrentJobType.IMPORT_SCHEMA, target);
        JobConcurrencyConstraint importSchemaLimit = new JobConcurrencyConstraint(concurrencyTarget, 1);
        return new JobConcurrencyConstraint[] { importSchemaLimit };
    }
}
