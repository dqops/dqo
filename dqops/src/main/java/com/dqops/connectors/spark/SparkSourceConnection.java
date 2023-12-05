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
package com.dqops.connectors.spark;

import com.dqops.connectors.*;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.jdbc.JdbcQueryFailedException;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.*;
import com.dqops.utils.exceptions.RunSilently;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

import java.sql.Statement;
import java.util.*;

/**
 * Spark source connection.
 */
@Component("spark-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SparkSourceConnection extends AbstractJdbcSourceConnection {
    /**
     * Injection constructor for the spark connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public SparkSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                 SecretValueProvider secretValueProvider,
                                 SparkConnectionProvider sparkConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, sparkConnectionProvider);
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
        SparkParametersSpec sparkParametersSpec = connectionSpec.getSpark();

        String host = this.getSecretValueProvider().expandValue(sparkParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:hive2://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(sparkParametersSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to Spark, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String schema = this.getSecretValueProvider().expandValue(sparkParametersSpec.getSchema(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(schema)) {
            jdbcConnectionBuilder.append(schema);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (sparkParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(sparkParametersSpec.getProperties());
        }

        String userName = this.getSecretValueProvider().expandValue(sparkParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(sparkParametersSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        String options =  this.getSecretValueProvider().expandValue(sparkParametersSpec.getOptions(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    @Override
    public List<SourceSchemaModel> listSchemas() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SHOW DATABASES");
        String listSchemataSql = sqlBuilder.toString();
        Table schemaRows = this.executeQuery(listSchemataSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceSchemaModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < schemaRows.rowCount(); rowIndex++) {
            String namespace = schemaRows.getString(rowIndex, "namespace");
            SourceSchemaModel schemaModel = new SourceSchemaModel(namespace);
            results.add(schemaModel);
        }

        return results;
    }

    @Override
    public List<SourceTableModel> listTables(String schemaName) {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SHOW tables FROM ");
        sqlBuilder.append(schemaName);

        String listTablesSql = sqlBuilder.toString();
        Table tablesRows = this.executeQuery(listTablesSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceTableModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < tablesRows.rowCount() ; rowIndex++) {
            String tableName = tablesRows.getString(rowIndex, "table_name");
            PhysicalTableName physicalTableName = new PhysicalTableName(schemaName, tableName);
            SourceTableModel schemaModel = new SourceTableModel(schemaName, physicalTableName);
            results.add(schemaModel);
        }

        return results;
    }

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


}
