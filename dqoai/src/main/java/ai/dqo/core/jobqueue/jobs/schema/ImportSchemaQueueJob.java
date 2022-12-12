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
package ai.dqo.core.jobqueue.jobs.schema;

import ai.dqo.connectors.*;
import ai.dqo.core.jobqueue.*;
import ai.dqo.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.search.StringPatternComparer;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
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
    private ImportSchemaQueueJobParameters importParameters;

    @Autowired
    public ImportSchemaQueueJob(UserHomeContextFactory userHomeContextFactory,
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
            PhysicalTableName physicalTableName = spec.getTarget().toPhysicalTableName();
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
            row.setString(0, sourceTableSpec.getTarget().getSchemaName());
            row.setString(1, sourceTableSpec.getTarget().getTableName());
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
        String tableNamePattern = this.importParameters.getTableNamePattern();

        if (tableNamePattern == null) {
            tableNamePattern = "*";
        }

        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionList connections = userHome.getConnections();

        ConnectionWrapper connectionWrapper = connections.getByObjectName(this.importParameters.getConnectionName(), true);
        if (connectionWrapper == null) {
            throw new ImportSchemaQueueJobException("Connection " + this.importParameters.getConnectionName() + "  was not found");
        }

        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        ConnectionSpec expandedConnectionSpec = connectionSpec.expandAndTrim(this.secretValueProvider);

        ProviderType providerType = expandedConnectionSpec.getProviderType();
        ConnectionProvider connectionProvider = this.connectionProviderRegistry.getConnectionProvider(providerType);
        try (SourceConnection sourceConnection = connectionProvider.createConnection(expandedConnectionSpec, true)) {
            List<SourceTableModel> tableModels = sourceConnection.listTables(this.importParameters.getSchemaName());
            if (tableModels.size() == 0) {
                throw new ImportSchemaQueueJobException("No tables found in the data source when importing tables on the " +
                        this.importParameters.getConnectionName() + ", from the " + this.importParameters.getSchemaName() + " schema");
            }

            List<String> tableNames = tableModels.stream()
                    .map(tm -> tm.getTableName().getTableName())
                    .collect(Collectors.toList());

            List<TableSpec> sourceTableSpecs = sourceConnection.retrieveTableMetadata(this.importParameters.getSchemaName(), tableNames);
            sourceTableSpecs = filterTableSpecs(sourceTableSpecs, tableNamePattern);

            TableList currentTablesColl = connectionWrapper.getTables();
            currentTablesColl.importTables(sourceTableSpecs, connectionSpec.getDefaultDataStreamMapping());
            userHomeContext.flush();

            Table resultTable = createDatasetTableFromTableSpecs(sourceTableSpecs);

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
        return DqoJobType.IMPORT_SCHEMA;
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
    public JobConcurrencyConstraint getConcurrencyConstraint() {
        ImportSchemaQueueJobConcurrencyTarget target = new ImportSchemaQueueJobConcurrencyTarget(
                this.importParameters.getConnectionName(), this.importParameters.getSchemaName());
        JobConcurrencyTarget concurrencyTarget = new JobConcurrencyTarget(ConcurrentJobType.IMPORT_SCHEMA, target);
        return new JobConcurrencyConstraint(concurrencyTarget, 1);
    }
}
