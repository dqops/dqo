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
package com.dqops.connectors.redshift;

import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Redshift source connection.
 */
@Component("redshift-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RedshiftSourceConnection extends AbstractJdbcSourceConnection {
    /**
     * Injection constructor for the snowflake connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public RedshiftSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                    SecretValueProvider secretValueProvider,
                                    RedshiftConnectionProvider redshiftConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, redshiftConnectionProvider);
    }

    /**
     * Returns a list of schemas from the source.
     *
     * @return List of schemas.
     */
    @Override
    public List<SourceSchemaModel> listSchemas() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT database_name, schema_name FROM ");
        sqlBuilder.append("SVV_ALL_SCHEMAS\n");
        sqlBuilder.append("WHERE SCHEMA_NAME <> 'information_schema' AND SCHEMA_NAME <> 'pg_catalog'");
        String listSchemataSql = sqlBuilder.toString();
        Table schemaRows = this.executeQuery(listSchemataSql, JobCancellationToken.createDummyJobCancellationToken(), null, false);

        List<SourceSchemaModel> results = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < schemaRows.rowCount() ; rowIndex++) {
            String schemaName = schemaRows.getString(rowIndex, "schema_name");
            SourceSchemaModel schemaModel = new SourceSchemaModel(schemaName);
            results.add(schemaModel);
        }
        return results;
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     *
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        RedshiftParametersSpec redshiftSpec = connectionSpec.getRedshift();

        String host = this.getSecretValueProvider().expandValue(redshiftSpec.getHost());
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:redshift://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(redshiftSpec.getPort());
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to Redshift, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(redshiftSpec.getDatabase());
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (redshiftSpec.getProperties() != null) {
            dataSourceProperties.putAll(redshiftSpec.getProperties());
        }

        String userName = this.getSecretValueProvider().expandValue(redshiftSpec.getUser());
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(redshiftSpec.getPassword());
        hikariConfig.setPassword(password);

        String options =  this.getSecretValueProvider().expandValue(redshiftSpec.getOptions());
        if (!Strings.isNullOrEmpty(options)) {
            dataSourceProperties.put("options", options);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }
}
