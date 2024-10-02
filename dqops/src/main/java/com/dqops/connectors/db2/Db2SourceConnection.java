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
package com.dqops.connectors.db2;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.ProviderDialectSettings;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * IBM DB2 source connection.
 */
@Component("db2-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Db2SourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean driverRegistered = false;
    private final Db2ConnectionProvider db2ConnectionProvider;

    /**
     * Injection constructor for the DB2 connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public Db2SourceConnection(JdbcConnectionPool jdbcConnectionPool,
                               SecretValueProvider secretValueProvider,
                               Db2ConnectionProvider db2ConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, db2ConnectionProvider);
        this.db2ConnectionProvider = db2ConnectionProvider;
    }

    /**
     * Manually registers the JDBC Driver allowing the control of the registration time.
     */
    private static void registerDriver() {
        if(driverRegistered){
            return;
        }
        try {
            synchronized (driverRegisterLock){
                Class.forName("com.ibm.db2.jcc.DB2Driver");
                driverRegistered = true;
            }
        } catch (ClassNotFoundException e) {
            throw new DqoRuntimeException("Please contact DQOps sales team to get access to a commercial version of DQOps. " +
                    "IBM DB2 drivers are not provided in an open-source version due to license limitations.");
        }
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        registerDriver();

        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        Db2ParametersSpec db2ParametersSpec = connectionSpec.getDb2();

        String host = this.getSecretValueProvider().expandValue(db2ParametersSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:db2://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(db2ParametersSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to DB2, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        jdbcConnectionBuilder.append(db2ParametersSpec.getDatabase());

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (db2ParametersSpec.getProperties() != null) {
            dataSourceProperties.putAll(db2ParametersSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String userName = this.getSecretValueProvider().expandValue(db2ParametersSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(db2ParametersSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    /**
     * Returns the schema name of the INFORMATION_SCHEMA equivalent.
     * @return Information schema name.
     */
    public String getInformationSchemaName() {
        return "SYSIBM";
    }

    /**
     * Generates an SQL statement that lists tables.
     * @param schemaName Schema name.
     * @param tableNameContains Optional filter with a text that must be present in the tables returned.
     * @param limit The limit of the number of tables to return.
     * @return SQL string for a query that lists tables.
     */
    @NotNull
    public String buildListTablesSql(String schemaName, String tableNameContains, int limit) {
        ConnectionProviderSpecificParameters providerSpecificConfiguration = this.getConnectionSpec().getProviderSpecificConfiguration();

//        where CREATOR = 'SCHEMA'
//        and name like '%CUR%'
//        and type = 'T';

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT CREATOR AS table_schema, NAME AS table_name FROM ");
        sqlBuilder.append(getInformationSchemaName());
        sqlBuilder.append(".systables\n");
        sqlBuilder.append("WHERE CREATOR='");
        sqlBuilder.append(schemaName.replace("'", "''"));
        sqlBuilder.append("'");
//        String databaseName = providerSpecificConfiguration.getDatabase();
//        if (!Strings.isNullOrEmpty(databaseName)) {
//            sqlBuilder.append(" AND table_catalog='");
//            sqlBuilder.append(databaseName.replace("'", "''"));
//            sqlBuilder.append("'");
//        }

        if (!Strings.isNullOrEmpty(tableNameContains)) {
            sqlBuilder.append(" AND NAME LIKE '%");
            sqlBuilder.append(tableNameContains.replace("'", "''"));
            sqlBuilder.append("%'");
        }

        ProviderDialectSettings dialectSettings = this.db2ConnectionProvider.getDialectSettings(this.getConnectionSpec());
        if (dialectSettings.isSupportsLimitClause()) {
            sqlBuilder.append(" LIMIT ");
            sqlBuilder.append(limit);
        }

        String listTablesSql = sqlBuilder.toString();
        return listTablesSql;
    }

}
