/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
package com.dqops.connectors.trino;

import com.dqops.connectors.ConnectorOperationFailedException;
import com.dqops.connectors.jdbc.AbstractJdbcSourceConnection;
import com.dqops.connectors.jdbc.JdbcConnectionPool;
import com.dqops.connectors.jdbc.JdbcQueryFailedException;
import com.dqops.connectors.storage.aws.AwsAuthenticationMode;
import com.dqops.connectors.storage.aws.JdbcAwsProperties;
import com.dqops.core.jobqueue.JobCancellationListenerHandle;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.sources.ColumnTypeSnapshotSpec;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsConfigProfileSettingNames;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsCredentialProfileSettingNames;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsDefaultConfigProfileProvider;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsDefaultCredentialProfileProvider;
import com.dqops.utils.exceptions.RunSilently;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.profiles.Profile;
import tech.tablesaw.api.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Trino source connection.
 */
@Component("trino-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TrinoSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean athenaDriverRegistered = false;
    private static boolean trinoDriverRegistered = false;

    /**
     * Injection constructor for the trino connection.
     * @param jdbcConnectionPool Jdbc connection pool.
     * @param secretValueProvider Secret value provider for the environment variable expansion.
     */
    @Autowired
    public TrinoSourceConnection(JdbcConnectionPool jdbcConnectionPool,
                                 SecretValueProvider secretValueProvider,
                                 TrinoConnectionProvider trinoConnectionProvider) {
        super(jdbcConnectionPool, secretValueProvider, trinoConnectionProvider);
    }

    /**
     * Manually registers the JDBC Driver allowing the control of the registration time.
     * Athena JDBC Driver has missing java.sql.Driver in META-INF/services jar path, which makes it not possible to automatically registered.
     */
    private static void registerDriver(TrinoEngineType trinoEngineType){
        if(trinoEngineType.equals(TrinoEngineType.trino)){
            if(trinoDriverRegistered){
                return;
            }
            try {
                synchronized (driverRegisterLock){
                    Class.forName("io.trino.jdbc.TrinoDriver");
                    TrinoSourceConnection.trinoDriverRegistered = true;
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if(trinoEngineType.equals(TrinoEngineType.athena)){
            if(athenaDriverRegistered){
                return;
            }
            try {
                synchronized (driverRegisterLock){
                    Class.forName("com.amazon.athena.jdbc.AthenaDriver");
                    TrinoSourceConnection.athenaDriverRegistered = true;
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that could be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        TrinoParametersSpec trinoSpec = this.getConnectionSpec().getTrino();
        TrinoEngineType trinoEngineType = trinoSpec.getTrinoEngineType();

        registerDriver(trinoEngineType);

        switch (trinoEngineType){
            case trino: return makeHikariConfigForTrino(trinoSpec, secretValueLookupContext);
            case athena: return makeHikariConfigForAthena(trinoSpec, secretValueLookupContext);
            default: throw new RuntimeException("Cannot create hikari config. Unsupported enum: " + trinoSpec.getTrinoEngineType());
        }
    }

    private HikariConfig makeHikariConfigForTrino(TrinoParametersSpec trinoSpec, SecretValueLookupContext secretValueLookupContext){
        HikariConfig hikariConfig = new HikariConfig();
        String host = this.getSecretValueProvider().expandValue(trinoSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:trino://");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(trinoSpec.getPort(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(port)) {
            try {
                int portNumber = Integer.parseInt(port);
                jdbcConnectionBuilder.append(':');
                jdbcConnectionBuilder.append(portNumber);
            }
            catch (NumberFormatException nfe) {
                throw new ConnectorOperationFailedException("Cannot create a connection to Trino, the port number is invalid: " + port, nfe);
            }
        }
        jdbcConnectionBuilder.append('/');
        String database = this.getSecretValueProvider().expandValue(trinoSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        String userName = this.getSecretValueProvider().expandValue(trinoSpec.getUser(), secretValueLookupContext);
        hikariConfig.setUsername(userName);

        String password = this.getSecretValueProvider().expandValue(trinoSpec.getPassword(), secretValueLookupContext);
        hikariConfig.setPassword(password);

        Properties dataSourceProperties = new Properties();
        if (trinoSpec.getProperties() != null) {
            dataSourceProperties.putAll(trinoSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }
        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }

    private HikariConfig makeHikariConfigForAthena(TrinoParametersSpec trinoSpec, SecretValueLookupContext secretValueLookupContext){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:athena://");

        Properties dataSourceProperties = new Properties();
        if (trinoSpec.getProperties() != null) {
            dataSourceProperties.putAll(trinoSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        AwsAuthenticationMode awsAuthenticationMode = trinoSpec.getAwsAuthenticationMode() == null ?
                AwsAuthenticationMode.default_credentials : trinoSpec.getAwsAuthenticationMode() ;
        switch(awsAuthenticationMode){
            case iam:
                String user = this.getSecretValueProvider().expandValue(trinoSpec.getUser(), secretValueLookupContext);
                if (!Strings.isNullOrEmpty(user)){
                    dataSourceProperties.put(JdbcAwsProperties.ACCESS_KEY_ID, user);  // AccessKeyId alias for User
                }

                String password = this.getSecretValueProvider().expandValue(trinoSpec.getPassword(), secretValueLookupContext);
                if (!Strings.isNullOrEmpty(password)){
                    dataSourceProperties.put(JdbcAwsProperties.SECRET_ACCESS_KEY, password);  // SecretAccessKey alias for Password
                }

                String region = this.getSecretValueProvider().expandValue(trinoSpec.getAthenaRegion(), secretValueLookupContext);
                if (!Strings.isNullOrEmpty(region)){
                    dataSourceProperties.put(JdbcAwsProperties.REGION, region);
                } else {
                    Optional<Profile> configProfile = AwsDefaultConfigProfileProvider.provideProfile(secretValueLookupContext);
                    if(configProfile.isPresent() && configProfile.get().property(AwsConfigProfileSettingNames.REGION).isPresent()){
                        dataSourceProperties.put(JdbcAwsProperties.REGION, configProfile.get().property(AwsConfigProfileSettingNames.REGION).get());
                    }
                }

                break;

            case default_credentials:
                Optional<Profile> credentialProfile = AwsDefaultCredentialProfileProvider.provideProfile(secretValueLookupContext);
                if(credentialProfile.isPresent()
                        && credentialProfile.get().property(AwsCredentialProfileSettingNames.AWS_ACCESS_KEY_ID).isPresent()
                        && credentialProfile.get().property(AwsCredentialProfileSettingNames.AWS_SECRET_ACCESS_KEY).isPresent()){
                    dataSourceProperties.put(JdbcAwsProperties.ACCESS_KEY_ID, credentialProfile.get().property(AwsCredentialProfileSettingNames.AWS_ACCESS_KEY_ID).get());  // AccessKeyId alias for User
                    dataSourceProperties.put(JdbcAwsProperties.SECRET_ACCESS_KEY, credentialProfile.get().property(AwsCredentialProfileSettingNames.AWS_SECRET_ACCESS_KEY).get());  // SecretAccessKey alias for Password
                } else {
                    dataSourceProperties.put("CredentialsProvider", "DefaultChain");    // The use of the local ~/.aws/credentials file with default profile
                }
                Optional<Profile> configProfile = AwsDefaultConfigProfileProvider.provideProfile(secretValueLookupContext);
                if(configProfile.isPresent() && configProfile.get().property(AwsConfigProfileSettingNames.REGION).isPresent()){
                    dataSourceProperties.put(JdbcAwsProperties.REGION, configProfile.get().property(AwsConfigProfileSettingNames.REGION).get());
                }
                break;

            default:
                throw new RuntimeException("Given enum is not supported : " + awsAuthenticationMode);
        }

        String catalog = this.getSecretValueProvider().expandValue(trinoSpec.getCatalog(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(catalog)){
            dataSourceProperties.put("Catalog", catalog);
        }

        String outputLocation = this.getSecretValueProvider().expandValue(trinoSpec.getAthenaOutputLocation(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(outputLocation)){
            dataSourceProperties.put("OutputLocation", outputLocation);
        }

        String workgroup = this.getSecretValueProvider().expandValue(trinoSpec.getAthenaWorkGroup(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(workgroup)){
            dataSourceProperties.put("Workgroup", workgroup);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);

        return hikariConfig;
    }

    /**
     * Creates a target table following the table specification.
     *
     * @param tableSpec Table specification with the physical table name, column names and physical column data types.
     */
    @Override
    public void createTable(TableSpec tableSpec) {
        TrinoEngineType trinoEngineType = this.getConnectionSpec().getTrino().getTrinoEngineType();
        if (trinoEngineType == null) {
            trinoEngineType = TrinoEngineType.trino;
        }

        switch (trinoEngineType){
            case trino:
                super.createTable(tableSpec);
                break;

            case athena:
                String createTableSql = generateCreateTableSqlStatementForAthena(tableSpec);
                this.executeCommand(createTableSql, JobCancellationToken.createDummyJobCancellationToken());
                break;
        }
    }

    /**
     * Creates a create table statement for athena that use an exernal table filled with data from a csv file located in AWS S3.
     * @param tableSpec Table specification with the physical table name, column names and physical column data types.
     * @return Ready to execute sql statement.
     */
    protected String generateCreateTableSqlStatementForAthena(TableSpec tableSpec){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE EXTERNAL TABLE IF NOT EXISTS ");
        sqlBuilder.append(tableSpec.getPhysicalTableName().getSchemaName());
        sqlBuilder.append(".");
        sqlBuilder.append(tableSpec.getPhysicalTableName().getTableName());
        sqlBuilder.append(" (\n");

        Map.Entry<String, ColumnSpec> [] columnKeyPairs = tableSpec.getColumns().entrySet().toArray(Map.Entry[]::new);
        for(int i = 0; i < columnKeyPairs.length ; i++) {
            Map.Entry<String, ColumnSpec> columnKeyPair = columnKeyPairs[i];

            if (i != 0) { // not the first iteration
                sqlBuilder.append(",\n");
            }

            sqlBuilder.append("    ");
            sqlBuilder.append(columnKeyPair.getKey());
            sqlBuilder.append(" ");
            ColumnTypeSnapshotSpec typeSnapshot = columnKeyPair.getValue().getTypeSnapshot();
            sqlBuilder.append(typeSnapshot.getColumnType());
            if (typeSnapshot.getLength() != null) {
                sqlBuilder.append("(");
                sqlBuilder.append(typeSnapshot.getLength());
                sqlBuilder.append(")");
            }
            else if (typeSnapshot.getPrecision() != null && typeSnapshot.getScale() != null) {
                sqlBuilder.append("(");
                sqlBuilder.append(typeSnapshot.getPrecision());
                sqlBuilder.append(",");
                sqlBuilder.append(typeSnapshot.getScale());
                sqlBuilder.append(")");
            }

        }
        sqlBuilder.append("\n)");
        sqlBuilder.append("\n");

        String tableName = tableSpec.getPhysicalTableName().getTableName();
        sqlBuilder.append("ROW FORMAT DELIMITED\n");
        sqlBuilder.append("FIELDS TERMINATED BY ','\n");
        sqlBuilder.append("STORED AS TEXTFILE\n");
//        sqlBuilder.append("LOCATION 's3://dqops-athena-test/" + tableName + "/" + tableName + ".csv'\n");
        sqlBuilder.append("LOCATION 's3://dqops-athena-test/" + tableName + "'\n");
        sqlBuilder.append("TBLPROPERTIES ('skip.header.line.count'='1');\n");

        String createTableSql = sqlBuilder.toString();
        return createTableSql;
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

    /**
     * Creates the tablesaw's Table from the ResultSet for the query execution
     * @param results               ResultSet object that contains the data produced by a query
     * @param sqlQueryStatement     SQL statement that returns a row set.
     * @return Tabular result captured from the query.
     * @throws SQLException
     */
    @Override
    protected Table rawTableResultFromResultSet(ResultSet results, String sqlQueryStatement) throws SQLException {
        TrinoEngineType trinoEngineType = this.getConnectionSpec().getTrino().getTrinoEngineType();
        if (trinoEngineType == null) {
            trinoEngineType = TrinoEngineType.trino;
        }

        switch (trinoEngineType){
            case trino:
                return super.rawTableResultFromResultSet(results, sqlQueryStatement);

            case athena:
                return this.rawTableResultFromResultSetForAthena(results, sqlQueryStatement);

            default:
                throw new RuntimeException(String.format("Trino engine type of %s is not supported.", trinoEngineType.toString()));
        }
    }

    /**
     * Creates the tablesaw's Table from the ResultSet for the query execution for the Athena engine type.
     * @param results               ResultSet object that contains the data produced by a query
     * @param sqlQueryStatement     SQL statement that returns a row set.
     * @return Tabular result captured from the query.
     * @throws SQLException
     */
    protected Table rawTableResultFromResultSetForAthena(ResultSet results, String sqlQueryStatement) throws SQLException {
        try (AthenaResultSetWrapper athenaResultSet = new AthenaResultSetWrapper(results)) {
            Table resultTable = Table.read().db(athenaResultSet, sqlQueryStatement);
            return resultTable;
        }
    }

}
