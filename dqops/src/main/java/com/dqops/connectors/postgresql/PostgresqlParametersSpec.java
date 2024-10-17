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
package com.dqops.connectors.postgresql;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.BaseProviderParametersSpec;
import com.dqops.utils.docs.generators.SampleStringsRegistry;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import picocli.CommandLine;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Postgresql connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class PostgresqlParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {
    private static final ChildHierarchyNodeFieldMapImpl<PostgresqlParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--postgresql-host"}, description = "PostgreSQL host name")
    @JsonPropertyDescription("PostgreSQL host name. Supports also a ${POSTGRESQL_HOST} configuration with a custom environment variable.")
    private String host;

    @CommandLine.Option(names = {"--postgresql-port"}, description = "PostgreSQL port number")
    @JsonPropertyDescription("PostgreSQL port number. The default port is 5432. Supports also a ${POSTGRESQL_PORT} configuration with a custom environment variable.")
    private String port;

    @CommandLine.Option(names = {"--postgresql-database"}, description = "PostgreSQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("PostgreSQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String database;

    @CommandLine.Option(names = {"--postgresql-user"}, description = "PostgreSQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("PostgreSQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String user;

    @CommandLine.Option(names = {"--postgresql-password"}, description = "PostgreSQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("PostgreSQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String password;

    @CommandLine.Option(names = {"--postgresql-options"}, description = "PostgreSQL connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes.")
    @JsonPropertyDescription("PostgreSQL connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${POSTGRESQL_OPTIONS} configuration with a custom environment variable.")
    private String options;

    @CommandLine.Option(names = {"--postgresql-sslmode"}, description = "Connect to PostgreSQL using sslmode connection parameter")
    @JsonPropertyDescription("Sslmode PostgreSQL connection parameter. The default value is disabled.")
    private PostgresqlSslMode sslmode = PostgresqlSslMode.disable;

    @CommandLine.Option(names = {"--postgresql-engine"}, description = "Postgresql engine type. Supports also a ${POSTGRESQL_ENGINE} configuration with a custom environment variable.")
    @JsonPropertyDescription("Postgresql engine type. Supports also a ${POSTGRESQL_ENGINE} configuration with a custom environment variable.")
    private PostgresqlEngineType postgresqlEngineType = PostgresqlEngineType.postgresql;

    @CommandLine.Option(names = {"-P"}, description = "PostgreSQL additional properties that are added to the JDBC connection string")
    @JsonPropertyDescription("A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> properties;

    /**
     * Returns the host name.
     * @return Host name.
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the host name.
     * @param host New host name.
     */
    public void setHost(String host) {
        setDirtyIf(!Objects.equals(this.host, host));
        this.host = host;
    }

    /**
     * Returns the port number. The value should store an environment variable expression or a numeric postgresql port number.
     * @return Port name or an expression to be extracted.
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the port number.
     * @param port Port name.
     */
    public void setPort(String port) {
        setDirtyIf(!Objects.equals(this.port, port));
        this.port = port;
    }

    /**
     * Returns a physical database name.
     * @return Physical database name.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Sets a physical database name.
     * @param database Physical database name.
     */
    public void setDatabase(String database) {
        setDirtyIf(!Objects.equals(this.database, database));
        this.database = database;
    }

    /**
     * Returns the user that is used to log in to the data source (JDBC user or similar).
     * @return User name.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets a user name.
     * @param user User name.
     */
    public void setUser(String user) {
        setDirtyIf(!Objects.equals(this.user, user));
        this.user = user;
    }

    /**
     * Returns a password used to authenticate to the server.
     * @return Password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets a password that is used to connect to the database.
     * @param password Password.
     */
    public void setPassword(String password) {
        setDirtyIf(!Objects.equals(this.password, password));
        this.password = password;
    }

    /**
     * Returns the custom connection initialization options.
     * @return Connection initialization options.
     */
    public String getOptions() {
        return options;
    }

    /**
     * Sets the connection initialization options.
     * @param options Connection initialization options.
     */
    public void setOptions(String options) {
        setDirtyIf(!Objects.equals(this.options, options));
        this.options = options;
    }

    /**
     * Returns the flag to require SSL connection.
     * @return True - require an SSL connection.
     */
    public PostgresqlSslMode getSslmode() {
        return sslmode;
    }

    /**
     * Sets a flag to require an SSL connection.
     * @param sslmode True - ssl connection is required.
     */
    public void setSslmode(PostgresqlSslMode sslmode) {
        setDirtyIf(!Objects.equals(this.sslmode, sslmode));
        this.sslmode = sslmode;
    }

    /**
     * Returns the Postgresql engine type.
     * @return Postgresql engine type.
     */
    public PostgresqlEngineType getPostgresqlEngineType() {
        return postgresqlEngineType;
    }

    /**
     * Sets a Postgresql engine type.
     * @param postgresqlEngineType Postgresql engine type.
     */
    public void setPostgresqlEngineType(PostgresqlEngineType postgresqlEngineType) {
        setDirtyIf(!Objects.equals(this.postgresqlEngineType, postgresqlEngineType));
        this.postgresqlEngineType = postgresqlEngineType;
    }

    /**
     * Returns a key/value map of additional properties that are included in the JDBC connection string.
     * @return Key/value dictionary of additional JDBC properties.
     */
    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * Sets a dictionary of additional connection parameters that are added to the JDBC connection string.
     * @param properties Key/value dictionary with extra parameters.
     */
    public void setProperties(Map<String, String> properties) {
        setDirtyIf(!Objects.equals(this.properties, properties));
        this.properties = properties != null ? Collections.unmodifiableMap(properties) : null;
    }

    /**
     * Returns the child map on the spec class with all fields.
     *
     * @return Return the field map.
     */
    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

    /**
     * Creates and returns a deep copy of this object.
     */
    @Override
    public PostgresqlParametersSpec deepClone() {
        PostgresqlParametersSpec cloned = (PostgresqlParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public PostgresqlParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        PostgresqlParametersSpec cloned = this.deepClone();
        cloned.host = secretValueProvider.expandValue(cloned.host, lookupContext);
        cloned.port = secretValueProvider.expandValue(cloned.port, lookupContext);
        cloned.database = secretValueProvider.expandValue(cloned.database, lookupContext);
        cloned.user = secretValueProvider.expandValue(cloned.user, lookupContext);
        cloned.password = secretValueProvider.expandValue(cloned.password, lookupContext);
        cloned.options = secretValueProvider.expandValue(cloned.options, lookupContext);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties, lookupContext);

        return cloned;
    }

    public static class PostgresqlParametersSpecSampleFactory implements SampleValueFactory<PostgresqlParametersSpec> {
        @Override
        public PostgresqlParametersSpec createSample() {
            return new PostgresqlParametersSpec() {{
                setHost("localhost");
                setPort("5432");
                setDatabase("db");
                setUser(SampleStringsRegistry.getUserName());
                setUser("PASSWD");
            }};
        }
    }
}
