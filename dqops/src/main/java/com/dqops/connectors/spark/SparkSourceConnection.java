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

import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.core.secrets.SecretValueProvider;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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


    // todo
    /**
     * Creates a hikari connection pool config for the connection specification.
     *
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
//        ConnectionSpec connectionSpec = this.getConnectionSpec();
//        SparkParametersSpec sparkParametersSpec = connectionSpec.getSpark();
//
//        String host = this.getSecretValueProvider().expandValue(sparkParametersSpec.getHost());
//        StringBuilder jdbcConnectionBuilder = new StringBuilder();
//        jdbcConnectionBuilder.append("jdbc:oracle:thin:@");
//        jdbcConnectionBuilder.append(host);
//
//        String port = this.getSecretValueProvider().expandValue(sparkParametersSpec.getPort());
//        if (!Strings.isNullOrEmpty(port)) {
//            try {
//                int portNumber = Integer.parseInt(port);
//                jdbcConnectionBuilder.append(':');
//                jdbcConnectionBuilder.append(portNumber);
//            }
//            catch (NumberFormatException nfe) {
//                throw new ConnectorOperationFailedException("Cannot create a connection to Spark, the port number is invalid: " + port, nfe);
//            }
//        }
//        jdbcConnectionBuilder.append('/');
//        String database = this.getSecretValueProvider().expandValue(sparkParametersSpec.getDatabase());
//        if (!Strings.isNullOrEmpty(database)) {
//            jdbcConnectionBuilder.append(database);
//        }
//
//        String jdbcUrl = jdbcConnectionBuilder.toString();
//        hikariConfig.setJdbcUrl(jdbcUrl);
//
//        Properties dataSourceProperties = new Properties();
//        if (sparkParametersSpec.getProperties() != null) {
//            dataSourceProperties.putAll(sparkParametersSpec.getProperties());
//        }
//
//        String userName = this.getSecretValueProvider().expandValue(sparkParametersSpec.getUser());
//        hikariConfig.setUsername(userName);
//
//        String password = this.getSecretValueProvider().expandValue(sparkParametersSpec.getPassword());
//        hikariConfig.setPassword(password);
//
//        String options =  this.getSecretValueProvider().expandValue(sparkParametersSpec.getOptions());
//        if (!Strings.isNullOrEmpty(options)) {
//            dataSourceProperties.put("options", options);
//        }
//
//        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

}
