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

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.SourceSchemaModel;
import com.dqops.connectors.SourceTableModel;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.PhysicalTableName;
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
        String database = this.getSecretValueProvider().expandValue(sparkParametersSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (sparkParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(sparkParametersSpec.getProperties());
        }

        // todo: not sure if commented code is applicable
//        String userName = this.getSecretValueProvider().expandValue(sparkParametersSpec.getUser(), secretValueLookupContext);
//        hikariConfig.setUsername(userName);
        hikariConfig.setUsername("");
//
//        String password = this.getSecretValueProvider().expandValue(sparkParametersSpec.getPassword(), secretValueLookupContext);
//        hikariConfig.setPassword(password);
        hikariConfig.setPassword("");
//
//        String options =  this.getSecretValueProvider().expandValue(sparkParametersSpec.getOptions(), secretValueLookupContext);
//        if (!Strings.isNullOrEmpty(options)) {
//            dataSourceProperties.put("options", options);
//        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

}
