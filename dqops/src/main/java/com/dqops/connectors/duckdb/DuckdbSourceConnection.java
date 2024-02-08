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

import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.jdbc.JdbcQueryFailedException;
import com.dqops.core.filesystem.localfiles.HomeLocationFindService;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.exceptions.RunSilently;
import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * DuckDB source connection.
 */
@Slf4j
@Component("duckdb-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DuckdbSourceConnection extends AbstractJdbcSourceConnection {

    private final HomeLocationFindService homeLocationFindService;
    private final static Object registerLock = new Object();
    private static boolean extensionsRegistered = false;

    /**
     * Injection constructor for the duckdb connection.
     *
     * @param jdbcConnectionPool      Jdbc connection pool.
     * @param secretValueProvider     Secret value provider for the environment variable expansion.
     * @param homeLocationFindService
     */
    @Autowired
    public DuckdbSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                  SecretValueProvider secretValueProvider,
                                  DuckdbConnectionProvider duckdbConnectionProvider,
                                  HomeLocationFindService homeLocationFindService) {
        super(jdbcConnectionPool, secretValueProvider, duckdbConnectionProvider);
        this.homeLocationFindService = homeLocationFindService;
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

        if(duckdbSpec.isInMemory()){
            jdbcConnectionBuilder.append(":memory:");
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();

        if (duckdbSpec.getProperties() != null) {
            dataSourceProperties.putAll(duckdbSpec.getProperties());
        }

        // todo: connection

//        String options =  this.getSecretValueProvider().expandValue(duckdbSpec.getOptions(), secretValueLookupContext);
//        if (!Strings.isNullOrEmpty(options)) {
//            dataSourceProperties.put("options", options);
//        }

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
    }

    private void registerExtensions(){
        if(extensionsRegistered){
            return;
        }
        try {
            synchronized (registerLock) {
                StringBuilder setCustomRepository = new StringBuilder();
                setCustomRepository.append("SET extension_directory = ");
                setCustomRepository.append("'");
                setCustomRepository.append(homeLocationFindService.getDqoHomePath());
                setCustomRepository.append("/bin/duckdb");
                setCustomRepository.append("'");

                this.executeCommand(setCustomRepository.toString(), JobCancellationToken.createDummyJobCancellationToken());

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

    private List<String> getAvailableExtensions(){
        return Arrays.asList("httpfs");
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
        TableList tableList = connectionWrapper.getTables();

        for (TableWrapper table : tableList) {
            PhysicalTableName physicalTableName = table.getPhysicalTableName();
            SourceTableModel schemaModel = new SourceTableModel(schemaName, physicalTableName);
            sourceTableModels.add(schemaModel);
        }

        return sourceTableModels;
    }

}
