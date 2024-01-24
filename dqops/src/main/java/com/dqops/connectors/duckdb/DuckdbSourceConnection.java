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
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * DuckDB source connection.
 */
@Component("duckdb-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DuckdbSourceConnection extends AbstractJdbcSourceConnection {
    /**
     * Injection constructor for the duckdb connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public DuckdbSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                  SecretValueProvider secretValueProvider,
                                  DuckdbConnectionProvider duckdbConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, duckdbConnectionProvider);
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

        // todo: connection

//        String host = this.getSecretValueProvider().expandValue(duckdbSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:duckdb:");
//        jdbcConnectionBuilder.append(host);



//        jdbcConnectionBuilder.append('/');
//        String database = this.getSecretValueProvider().expandValue(duckdbSpec.getDatabase(), secretValueLookupContext);
//        if (!Strings.isNullOrEmpty(database)) {
//            jdbcConnectionBuilder.append(database);
//        }
//
//        String jdbcUrl = jdbcConnectionBuilder.toString();
//        hikariConfig.setJdbcUrl(jdbcUrl);
//
        Properties dataSourceProperties = new Properties();

        if (duckdbSpec.getProperties() != null) {
            dataSourceProperties.putAll(duckdbSpec.getProperties());
        }

//
//        String options =  this.getSecretValueProvider().expandValue(duckdbSpec.getOptions(), secretValueLookupContext);
//        if (!Strings.isNullOrEmpty(options)) {
//            dataSourceProperties.put("options", options);
//        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }
}
