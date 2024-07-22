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
import com.dqops.connectors.storage.aws.JdbcAwsProperties;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsCredentialProfileSettingNames;
import com.dqops.metadata.storage.localfiles.credentials.aws.AwsDefaultCredentialProfileProvider;
import com.zaxxer.hikari.HikariConfig;
import org.apache.parquet.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.profiles.Profile;
import tech.tablesaw.api.Table;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Redshift source connection.
 */
@Component("redshift-connection")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RedshiftSourceConnection extends AbstractJdbcSourceConnection {

    private final static Object driverRegisterLock = new Object();
    private static boolean driverRegistered = false;

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
     * Manually registers the JDBC Driver allowing the control of the registration time.
     */
    private static void registerDriver(){
        if(driverRegistered){
            return;
        }
        try {
            synchronized (driverRegisterLock){
                Class.forName("com.amazon.redshift.Driver");
                driverRegistered = true;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a hikari connection pool config for the connection specification.
     * @param secretValueLookupContext Secret value lookup context used to find shared credentials that can be used in the connection names.
     * @return Hikari config.
     */
    @Override
    public HikariConfig createHikariConfig(SecretValueLookupContext secretValueLookupContext) {
        registerDriver();

        HikariConfig hikariConfig = new HikariConfig();
        ConnectionSpec connectionSpec = this.getConnectionSpec();
        RedshiftParametersSpec redshiftSpec = connectionSpec.getRedshift();

        String host = this.getSecretValueProvider().expandValue(redshiftSpec.getHost(), secretValueLookupContext);
        StringBuilder jdbcConnectionBuilder = new StringBuilder();
        jdbcConnectionBuilder.append("jdbc:redshift:");

        RedshiftAuthenticationMode redshiftAuthenticationMode = redshiftSpec.getRedshiftAuthenticationMode() == null ?
                RedshiftAuthenticationMode.default_credentials : redshiftSpec.getRedshiftAuthenticationMode();

        if(redshiftAuthenticationMode.equals(RedshiftAuthenticationMode.iam)
            || redshiftAuthenticationMode.equals(RedshiftAuthenticationMode.default_credentials)
        ){
            jdbcConnectionBuilder.append("iam://");
        }

        jdbcConnectionBuilder.append("//");
        jdbcConnectionBuilder.append(host);

        String port = this.getSecretValueProvider().expandValue(redshiftSpec.getPort(), secretValueLookupContext);
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
        String database = this.getSecretValueProvider().expandValue(redshiftSpec.getDatabase(), secretValueLookupContext);
        if (!Strings.isNullOrEmpty(database)) {
            jdbcConnectionBuilder.append(database);
        }

        String jdbcUrl = jdbcConnectionBuilder.toString();
        hikariConfig.setJdbcUrl(jdbcUrl);

        Properties dataSourceProperties = new Properties();
        if (redshiftSpec.getProperties() != null) {
            dataSourceProperties.putAll(redshiftSpec.getProperties()
                    .entrySet().stream()
                    .filter(x -> !x.getKey().isEmpty())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            );
        }

        String user = this.getSecretValueProvider().expandValue(redshiftSpec.getUser(), secretValueLookupContext);
        String password = this.getSecretValueProvider().expandValue(redshiftSpec.getPassword(), secretValueLookupContext);

        switch(redshiftAuthenticationMode){
            case user_password:
                hikariConfig.setUsername(user);
                hikariConfig.setPassword(password);
                break;

            case iam:
                if (!Strings.isNullOrEmpty(user)){
                    dataSourceProperties.put(JdbcAwsProperties.ACCESS_KEY_ID, user);  // AccessKeyId alias for User
                }
                if (!Strings.isNullOrEmpty(password)){
                    dataSourceProperties.put(JdbcAwsProperties.SECRET_ACCESS_KEY, password);  // SecretAccessKey alias for Password
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
                break;

            default:
                throw new RuntimeException("Given enum is not supported : " + redshiftAuthenticationMode);
        }

        hikariConfig.setDataSourceProperties(dataSourceProperties);
        return hikariConfig;
    }
}
