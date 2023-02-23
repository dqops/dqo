/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.connectors.sqlserver;

import ai.dqo.connectors.ConnectionProviderSpecificParameters;
import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.sources.BaseProviderParametersSpec;
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
 * Microsoft SQL Server connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SqlServerParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {
    private static final ChildHierarchyNodeFieldMapImpl<SqlServerParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--sqlserver-host"}, description = "SQL Server host name")
    @JsonPropertyDescription("SQL Server host name. Supports also a ${SQLSERVER_HOST} configuration with a custom environment variable.")
    private String host;

    @CommandLine.Option(names = {"--sqlserver-port"}, description = "SQL Server port number", defaultValue = "1433")
    @JsonPropertyDescription("SQL Server port name. The default port is 1433. Supports also a ${SQLSERVER_PORT} configuration with a custom environment variable.")
    private String port;

    @CommandLine.Option(names = {"--sqlserver-database"}, description = "SQL Server database name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    @JsonPropertyDescription("SQL Server database name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    private String database;

    @CommandLine.Option(names = {"--sqlserver-user"}, description = "SQL Server user name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    @JsonPropertyDescription("SQL Server user name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    private String user;

    @CommandLine.Option(names = {"--sqlserver-password"}, description = "SQL Server database password. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    @JsonPropertyDescription("SQL Server database password. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    private String password;

    @CommandLine.Option(names = {"--sqlserver-options"}, description = "SQL Server connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes.")
    @JsonPropertyDescription("SQL Server connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${SQLSERVER_OPTIONS} configuration with a custom environment variable.")
    private String options;

    @CommandLine.Option(names = {"--sqlserver-ssl"}, description = "Connect to SQL Server using SSL", defaultValue = "false")
    @JsonPropertyDescription("Connect to SQL Server using SSL. The default value is false.")
    private Boolean ssl;

    @CommandLine.Option(names = {"--sqlserver-properties"}, description = "SQL Server additional properties that are added to the JDBC connection string")
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
     * Returns the port name. The value should store an environment variable expression or a numeric MS SQL Server port name.
     * @return Port name or an expression to be extracted.
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the port name.
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
    public Boolean getSsl() {
        return ssl;
    }

    /**
     * Sets a flag to require an SSL connection.
     * @param ssl True - ssl connection is required.
     */
    public void setSsl(Boolean ssl) {
        setDirtyIf(!Objects.equals(this.ssl, ssl));
        this.ssl = ssl;
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
    public SqlServerParametersSpec deepClone() {
        SqlServerParametersSpec cloned = (SqlServerParametersSpec) super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @return Trimmed and expanded version of this object.
     */
    public SqlServerParametersSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        SqlServerParametersSpec cloned = this.deepClone();
        cloned.host = secretValueProvider.expandValue(cloned.host);
        cloned.port = secretValueProvider.expandValue(cloned.port);
        cloned.database = secretValueProvider.expandValue(cloned.database);
        cloned.user = secretValueProvider.expandValue(cloned.user);
        cloned.password = secretValueProvider.expandValue(cloned.password);
        cloned.options = secretValueProvider.expandValue(cloned.options);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties);

        return cloned;
    }
}
