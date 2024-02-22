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
package com.dqops.core.jobqueue.jobs.table;

import com.dqops.checks.defaults.services.DefaultObservabilityConfigurationService;
import com.dqops.connectors.ConnectionProvider;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.ProviderType;
import com.dqops.connectors.SourceConnection;
import com.dqops.core.jobqueue.*;
import com.dqops.core.jobqueue.concurrency.ConcurrentJobType;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyTarget;
import com.dqops.core.jobqueue.jobs.schema.ImportSchemaQueueJobConcurrencyTarget;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.*;

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
    private ImportTablesQueueJobParameters importParameters;

    @Autowired
    public ImportTablesQueueJob(UserHomeContextFactory userHomeContextFactory,
                                ConnectionProviderRegistry connectionProviderRegistry,
                                SecretValueProvider secretValueProvider) {
        this.userHomeContextFactory = userHomeContextFactory;
        this.connectionProviderRegistry = connectionProviderRegistry;
        this.secretValueProvider = secretValueProvider;
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
        Table resultTable = Table.create().addColumns(
                TextColumn.create("Schema name"),
                TextColumn.create("Table name"),
                IntColumn.create("Column count"));

        for(TableSpec sourceTableSpec : sourceTableSpecs) {
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
    public ImportTablesResult onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.EDIT);

        UserDomainIdentity userIdentity = this.getPrincipal().getDataDomainIdentity();
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(userIdentity);
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
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true, secretValueLookupContext)) {
            List<TableSpec> sourceTableSpecs = sourceConnection.retrieveTableMetadata(
                    this.importParameters.getSchemaName(),
                    this.importParameters.getTableNames(),
                    connectionWrapper);

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
