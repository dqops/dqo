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
package com.dqops.connectors.duckdb;

import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.jdbc.JdbcQueryFailedException;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpecProvider;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsConfigProfileSettingNames;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsCredentialProfileSettingNames;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsProfileProvider;
import com.dqops.utils.exceptions.RunSilently;
import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.profiles.Profile;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * DuckDB source connection.
 */
@Slf4j
@Component("duckdb-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DuckdbSourceConnection extends AbstractJdbcSourceConnection {

    private final HomeLocationFindService homeLocationFindService;
    private final static Object registerExtensionsLock = new Object();
    private static boolean extensionsRegistered = false;
    private final AwsProfileProvider awsProfileProvider;

    /**
     * Injection constructor for the duckdb connection.
     *
     * @param jdbcConnectionPool      Jdbc connection pool.
     * @param secretValueProvider     Secret value provider for the environment variable expansion.
     * @param homeLocationFindService
     * @param awsProfileProvider
     */
    @Autowired
    public DuckdbSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                  SecretValueProvider secretValueProvider,
                                  DuckdbConnectionProvider duckdbConnectionProvider,
                                  HomeLocationFindService homeLocationFindService,
                                  AwsProfileProvider awsProfileProvider) {
        super(jdbcConnectionPool, secretValueProvider, duckdbConnectionProvider);
        this.homeLocationFindService = homeLocationFindService;
        this.awsProfileProvider = awsProfileProvider;
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        DuckdbParametersSpec duckdbSpec = connectionSpec.getDuckdb();

        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:duckdb:");

        if(duckdbSpec.getReadMode().equals(DuckdbReadMode.in_memory)){
            jdbcConnectionBuilder.append(":memory:");
        }

        String database = duckdbSpec.getDatabase();
        if(database != null){
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();

        if (duckdbSpec.getProperties() != null) {
            dataSourceProperties.putAll(duckdbSpec.getProperties());
        }

        String options =  this.getSecretValueProvider().expandValue(duckdbSpec.getOptions(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    /**
     * Creates the tablesaw's Table from the ResultSet for the query execution
     * @param results               ResultSet object that contains the data produced by a query
     * @param sqlQueryStatement     SQL statement that returns a row set.
     * @return Tabular result captured from the query.
     * @throws SQLException
     */
    @Override
    protected Table rawTableResultFromResultSet(ResultSet results, String sqlQueryStatement) throws SQLException {
        try (DuckdbResultSet mysqlResultSet = new DuckdbResultSet(results)) {
            Table resultTable = Table.read().db(mysqlResultSet, sqlQueryStatement);
            return resultTable;
        }
    }

    /**
     * Executes a provider specific SQL that runs a command DML/DDL command.
     *
     * @param sqlStatement SQL DDL or DML statement.
     * @param jobCancellationToken Job cancellation token, enables cancelling a running query.
     */
    @Override
    public long executeCommand(String sqlStatement, JobCancellationToken jobCancellationToken) {
        try {
            try (Statement statement = this.getJdbcConnection().createStatement()) {
                try (JobCancellationListenerHandle cancellationListenerHandle =
                             jobCancellationToken.registerCancellationListener(
                                     cancellationToken -> RunSilently.run(statement::cancel))) {
                    statement.execute(sqlStatement);
                    return 0;
                }
                finally {
                    jobCancellationToken.throwIfCancelled();
                }
            }
        }
        catch (Exception ex) {
            String connectionName = this.getConnectionSpec().getConnectionName();
            throw new JdbcQueryFailedException(
                    String.format("SQL statement failed: %s, connection: %s, SQL: %s", ex.getMessage(), connectionName, sqlStatement),
                    ex, sqlStatement, connectionName);
        }
    }

    @Override
    public void open(SecretValueLookupContext secretValueLookupContext) {
        super.open(secretValueLookupContext);
        registerExtensions();
        ensureSecretsLoaded(secretValueLookupContext);
    }

    /**
     * Registers extensions for duckdb from the local extension repository.
     */
    private void registerExtensions(){
        if(extensionsRegistered){
            return;
        }
        try {
            synchronized (registerExtensionsLock) {
                String setExtensionsQuery = DuckdbQueriesProvider.provideSetExtensionsQuery(homeLocationFindService.getDqoHomePath());
                this.executeCommand(setExtensionsQuery, JobCancellationToken.createDummyJobCancellationToken());

                List<String> availableExtensionList = getAvailableExtensions();
                availableExtensionList.stream().forEach(extensionName -> {
                    try {
                        String installExtensionQuery = "INSTALL " + extensionName;
                        this.executeCommand(installExtensionQuery, JobCancellationToken.createDummyJobCancellationToken());
                        String loadExtensionQuery = "LOAD " + extensionName;
                        this.executeCommand(loadExtensionQuery, JobCancellationToken.createDummyJobCancellationToken());
                    } catch (Exception exception) {
                        log.error("Extension " + extensionName + " cannot be loaded.");
                        log.error(exception.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads secrets that are used during the connection to a cloud storage service.
     */
    private void ensureSecretsLoaded(SecretValueLookupContext secretValueLookupContext){
        ConnectionSpec connectionSpec = getConnectionSpec().expandAndTrim(getSecretValueProvider(), secretValueLookupContext);
        fillSpecWithDefaultCredentials(connectionSpec, secretValueLookupContext);

        try {
            DuckdbSecretManager.getInstance().ensureCreated(connectionSpec, this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void fillSpecWithDefaultCredentials(ConnectionSpec connectionSpec, SecretValueLookupContext secretValueLookupContext){
        DuckdbParametersSpec duckdb = connectionSpec.getDuckdb();
        DuckdbSecretsType secretsType = duckdb.getSecretsType();

        switch (secretsType){
            case s3:
                Optional<Profile> profile = awsProfileProvider.provideProfile(secretValueLookupContext);
                if(!profile.isPresent()){
                    return;
                }

                Optional<String> accessKeyId = profile.get().property(AwsCredentialProfileSettingNames.AWS_ACCESS_KEY_ID);
                if(!Strings.isNullOrEmpty(duckdb.getUser()) && accessKeyId.isPresent()){
                    String awsAccessKeyId = accessKeyId.get();
                    duckdb.setUser(awsAccessKeyId);
                }

                Optional<String> secretAccessKey = profile.get().property(AwsCredentialProfileSettingNames.AWS_SECRET_ACCESS_KEY);
                if(!Strings.isNullOrEmpty(duckdb.getPassword()) && secretAccessKey.isPresent()){
                    String awsSecretAccessKey = secretAccessKey.get();
                    duckdb.setPassword(awsSecretAccessKey);
                }

                Optional<String> region = profile.get().property(AwsConfigProfileSettingNames.REGION);
                if(!Strings.isNullOrEmpty(duckdb.getRegion()) && region.isPresent()){
                    String awsRegion = region.get();
                    duckdb.setRegion(awsRegion);
                }

                break;
            default:
                throw new RuntimeException("This type of DuckdbSecretsType is not supported: " + secretsType);
        }
    }

    /**
     * The list of available extensions for DuckDB. Extensions are gathered locally through the project installation process.
     * @return The list of extension names for DuckDB.
     */
    private List<String> getAvailableExtensions(){
        return Arrays.asList(
                "httpfs",
                "aws"
        );
    }

    /**
     * Lists tables inside a schema and userhome's dqotables for the connection. Views are also returned.
     *
     * @param schemaName Schema name.
     * @return List of tables in the given schema.
     */
    @Override
    public List<SourceTableModel> listTables(String schemaName, ConnectionWrapper connectionWrapper) {
        List<SourceTableModel> sourceTableModels = super.listTables(schemaName, connectionWrapper);

        if(connectionWrapper == null){
            return sourceTableModels;
        }

        TableList tableList = connectionWrapper.getTables();

        for (TableWrapper table : tableList) {
            if(table.getPhysicalTableName().getSchemaName().equals(schemaName)){
                PhysicalTableName physicalTableName = table.getPhysicalTableName();
                SourceTableModel schemaModel = new SourceTableModel(schemaName, physicalTableName);
                sourceTableModels.add(schemaModel);
            }
        }

        return sourceTableModels;
    }

    /**
     * Retrieves the metadata (column information) for a given list of tables from a given schema.
     *
     * @param schemaName Schema name.
     * @param tableNames Table names.
     * @return List of table specifications with the column list.
     */
    @Override
    public List<TableSpec> retrieveTableMetadata(String schemaName, List<String> tableNames, ConnectionWrapper connectionWrapper) {
        assert !Strings.isNullOrEmpty(schemaName);

        DuckdbParametersSpec duckdbParametersSpec = getConnectionSpec().getDuckdb();

        if(duckdbParametersSpec.getReadMode().equals(DuckdbReadMode.in_memory)){
            return super.retrieveTableMetadata(schemaName, tableNames, connectionWrapper);
        }

        List<TableSpec> tableSpecs = new ArrayList<>();

        try {
            List<TableWrapper> tableWrappers = connectionWrapper.getTables().toList();
            for (TableWrapper tableWrapper : tableWrappers) {

                FileFormatSpec fileFormatSpec = FileFormatSpecProvider.resolveFileFormat(duckdbParametersSpec, tableWrapper.getSpec());
                Table tableResult = queryForTableResult(fileFormatSpec, tableWrapper.getSpec());

                Column<?>[] columns = tableResult.columnArray();
                for (Column<?> column : columns) {
                    column.setName(column.name().toLowerCase(Locale.ROOT));
                }

                HashMap<String, TableSpec> tablesByTableName = new LinkedHashMap<>();

                for (Row colRow : tableResult) {
                    String physicalTableName = tableWrapper.getPhysicalTableName().getTableName();
                    String columnName = colRow.getString("column_name");
                    boolean isNullable = Objects.equals(colRow.getString("null"), "YES");
                    String dataType = colRow.getString("column_type");

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

            }
            return tableSpecs;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a table result depending on the source files type.
     * @param fileFormatSpec A file format specification with paths and format configuration.
     * @return A table result with metadata of the given data file.
     */
    private tech.tablesaw.api.Table queryForTableResult(FileFormatSpec fileFormatSpec, TableSpec tableSpec){
        DuckdbSourceFilesType duckdbSourceFilesType = super.getConnectionSpec().getDuckdb().getSourceFilesType();
        String tableString = fileFormatSpec.buildTableOptionsString(duckdbSourceFilesType, tableSpec);
        String query = String.format("DESCRIBE SELECT * FROM %s", tableString);
        return this.executeQuery(query, JobCancellationToken.createDummyJobCancellationToken(), null, false);
    }

}
