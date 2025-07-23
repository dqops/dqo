/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.bigquery;

import com.dqops.connectors.*;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.google.api.gax.paging.Page;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.Dataset;
import com.google.cloud.bigquery.DatasetId;
import com.google.cloud.bigquery.Table;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Row;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Big query connection.
 */
@Component("bigquery-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BigQuerySourceConnection extends AbstractSqlSourceConnection {
    private BigQueryInternalConnection bigQueryService;
    private final BigQuerySqlRunner bigQuerySqlRunner;
    private final BigQueryConnectionPool bigQueryConnectionPool;

    /**
     * Creates a big query connection.
     * @param bigQuerySqlRunner Big query specific query runner.
     * @param bigQueryConnectionProvider Connection provider to access provider specific settings (dialect).
     * @param secretValueProvider Secret value provider.
     * @param bigQueryConnectionPool Big query connection pool.
     */
    @Autowired
    public BigQuerySourceConnection(BigQuerySqlRunner bigQuerySqlRunner,
                                    SecretValueProvider secretValueProvider,
                                    BigQueryConnectionProvider bigQueryConnectionProvider,
                                    BigQueryConnectionPool bigQueryConnectionPool) {
        super(secretValueProvider, bigQueryConnectionProvider);
        this.bigQuerySqlRunner = bigQuerySqlRunner;
        this.bigQueryConnectionPool = bigQueryConnectionPool;
    }

    /**
     * Returns a big query internal connection (BigQuery client).
     * @return Big query internal connection.
     */
    public BigQueryInternalConnection getBigQueryInternalConnection() {
        return bigQueryService;
    }

    /**
     * Opens a connection before it can be used for executing any statements.
     * @param secretValueLookupContext Secret value lookup context used to access shared credentials.
     */
    @Override
    public void open(SecretValueLookupContext secretValueLookupContext) {
        this.bigQueryService = this.bigQueryConnectionPool.getBigQueryService(this.getConnectionSpec(), secretValueLookupContext);
   }

    /**
     * Closes a connection.
     */
    @Override
    public void close() {
        // do nothing
    }

    /**
     * Executes a provider specific SQL that returns a query. For example a SELECT statement or any other SQL text that also returns rows.
     *
     * @param sqlQueryStatement       SQL statement that returns a row set.
     * @param jobCancellationToken    Job cancellation token, enables cancelling a running query.
     * @param maxRows                 Maximum rows limit.
     * @param failWhenMaxRowsExceeded Throws an exception if the maximum number of rows is exceeded.
     * @return Tabular result captured from the query.
     */
    @Override
    public tech.tablesaw.api.Table executeQuery(String sqlQueryStatement,
                                                JobCancellationToken jobCancellationToken,
                                                Integer maxRows,
                                                boolean failWhenMaxRowsExceeded) {
        return this.bigQuerySqlRunner.executeQuery(this, sqlQueryStatement, maxRows, failWhenMaxRowsExceeded);
    }

    /**
     * Executes a provider specific SQL that runs a command DML/DDL command.
     *
     * @param sqlStatement SQL DDL or DML statement.
     * @param jobCancellationToken Job cancellation token, enables cancelling a running query.
     */
    @Override
    public long executeCommand(String sqlStatement, JobCancellationToken jobCancellationToken) {
        return this.bigQuerySqlRunner.executeStatement(this, sqlStatement);
    }

    /**
     * Returns a list of schemas from the source.
     *
     * @return List of schemas.
     */
    @Override
    public List<SourceSchemaModel> listSchemas() {
        try {
            List<SourceSchemaModel> schemas = new ArrayList<>();
            String projectId = this.getConnectionSpec().getBigquery().getSourceProjectId();
            Page<Dataset> datasetPage = null;
            BigQuery bigQueryClient = this.bigQueryService.getBigQueryClient();
            if (Strings.isNullOrEmpty(projectId)) {
                datasetPage = bigQueryClient.listDatasets(BigQuery.DatasetListOption.all());
            } else {
                // only datasets in the given GCP project
                datasetPage = bigQueryClient.listDatasets(projectId, BigQuery.DatasetListOption.all());
            }

            for (Dataset dataset : datasetPage.iterateAll() ) {
                DatasetId datasetId = dataset.getDatasetId();
                if (datasetId.getDataset().startsWith("_")) {
                    continue; // hidden datasets (https://cloud.google.com/bigquery/docs/datasets#create-dataset)
                }

                SourceSchemaModel sourceSchemaModel = new SourceSchemaModel();
                sourceSchemaModel.setSchemaName(datasetId.getDataset());
                sourceSchemaModel.getProperties().put("projectId", projectId);
                schemas.add(sourceSchemaModel);
            }

            return schemas;
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(ex.getMessage(), ex);
        }
    }

    /**
     * Lists tables inside a schema. Views are also returned.
     *
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter to return tables that contain a given text in the name.
     * @param limit Limit of rows to retur.
     * @param secretValueLookupContext Secret lookup context to find secrets.
     * @return List of tables in the given schema.
     */
    @Override
    public List<SourceTableModel> listTables(String schemaName, String tableNameContains, int limit, SecretValueLookupContext secretValueLookupContext) {
        try {
            List<SourceTableModel> tables = new ArrayList<>();
            String projectId = this.getConnectionSpec().getBigquery().getSourceProjectId();
            Page<Table> tablePages = null;
            BigQuery bigQueryClient = this.bigQueryService.getBigQueryClient();

            if (Strings.isNullOrEmpty(projectId)) {
                DatasetId dataSetId = DatasetId.of(schemaName);
                tablePages = bigQueryClient.listTables(dataSetId, BigQuery.TableListOption.pageSize(1000));
            } else {
                // only datasets in the given GCP project
                DatasetId dataSetId = DatasetId.of(projectId, schemaName);
                tablePages = bigQueryClient.listTables(dataSetId, BigQuery.TableListOption.pageSize(1000));
            }

            for (Table table : tablePages.iterateAll()) {
                SourceTableModel sourceTableModel = new SourceTableModel();
                String datasetName = table.getTableId().getDataset();
                PhysicalTableName physicalTableName = new PhysicalTableName(datasetName, table.getTableId().getTable());
                if (!Strings.isNullOrEmpty(tableNameContains)) {
                    if (!physicalTableName.getTableName().contains(tableNameContains)) {
                        continue;
                    }
                }

                sourceTableModel.setSchemaName(datasetName);
                sourceTableModel.setTableName(physicalTableName);
                sourceTableModel.getProperties().put("projectId", projectId);
                tables.add(sourceTableModel);

                if (tables.size() >= limit) {
                    break;
                }
            }

            return tables;
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(ex.getMessage(), ex);
        }
    }

    /**
     * Retrieves the metadata (column information) for a given list of tables from a given schema.
     *
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter to return only tables that match a condition.
     * @param limit The limit of the number of tables to return.
     * @param connectionWrapper Connection wrapper of the parent connection.
     * @param tableNames Table names.
     * @param secretValueLookupContext Secret lookup context.
     * @return List of table specifications with the column list.
     */
    @Override
    public List<TableSpec> retrieveTableMetadata(String schemaName,
                                                 String tableNameContains,
                                                 int limit,
                                                 List<String> tableNames,
                                                 ConnectionWrapper connectionWrapper,
                                                 SecretValueLookupContext secretValueLookupContext) {
        assert !Strings.isNullOrEmpty(schemaName);

        try {
            List<TableSpec> tableSpecs = new ArrayList<>();
            String sql = buildListColumnsSql(schemaName, tableNames);
            tech.tablesaw.api.Table tableResult = this.bigQuerySqlRunner.executeQuery(this, sql, null, false);
            HashMap<String, TableSpec> tablesByTableName = new LinkedHashMap<>();

            for (Row colRow : tableResult) {
                String physicalTableName = colRow.getString("table_name");
                String columnName = colRow.getString("column_name");
                long ordinalPosition = colRow.getLong("ordinal_position");
                boolean isNullable = Objects.equals(colRow.getString("is_nullable"),"YES");
                String dataType = colRow.getString("data_type");

                TableSpec tableSpec = tablesByTableName.get(physicalTableName);
                if (tableSpec == null) {
                    tableSpec = new TableSpec();
                    tableSpec.setPhysicalTableName(new PhysicalTableName(schemaName, physicalTableName));
                    tablesByTableName.put(physicalTableName, tableSpec);
                    tableSpecs.add(tableSpec);
                }

                ColumnSpec columnSpec = new ColumnSpec();
                ColumnTypeSnapshotSpec columnType = ColumnTypeSnapshotSpec.fromType(dataType);
                columnType.setNullable(isNullable);
                columnSpec.setTypeSnapshot(columnType);
                tableSpec.getColumns().put(columnName, columnSpec);
            }

            if (!org.apache.parquet.Strings.isNullOrEmpty(tableNameContains)) {
                tableSpecs.removeIf(tableSpec -> !tableSpec.getPhysicalTableName().getTableName().contains(tableNameContains));
            }

            if (tableSpecs.size() > limit) {
                tableSpecs = tableSpecs.stream()
                        .limit(limit)
                        .collect(Collectors.toList());
            }

            return tableSpecs;
        }
        catch (Exception ex) {
            throw new ConnectionQueryException(ex);
        }
    }

    /**
     * Creates an SQL for listing columns in the given tables.
     * @param schemaName Schema name (bigquery dataset name).
     * @param tableNames Table names to list.
     * @return SQL of the INFORMATION_SCHEMA query.
     */
    public String buildListColumnsSql(String schemaName, List<String> tableNames) {
        ProviderDialectSettings dialectSettings = this.getDialectSettings();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM ");
        sqlBuilder.append(dialectSettings.quoteIdentifier(this.getConnectionSpec().getBigquery().getSourceProjectId()));
        sqlBuilder.append(".");
        sqlBuilder.append(dialectSettings.quoteIdentifier(schemaName));
        sqlBuilder.append(".INFORMATION_SCHEMA.COLUMNS ");

        if (tableNames != null && tableNames.size() > 0) {
            sqlBuilder.append("WHERE table_name IN (");
            for (int ti = 0; ti < tableNames.size(); ti++) {
                String tableName = tableNames.get(ti);
                if (ti > 0) {
                    sqlBuilder.append(",");
                }
                sqlBuilder.append('\'');
                sqlBuilder.append(tableName.replace("'", "''"));
                sqlBuilder.append('\'');
            }
            sqlBuilder.append(") ");
        }
        sqlBuilder.append("ORDER BY table_name, ordinal_position");
        String sql = sqlBuilder.toString();
        return sql;
    }
}
