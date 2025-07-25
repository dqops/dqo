/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.databricks;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.BaseProviderParametersSpec;
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
 * Databricks connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DatabricksParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {

    private static final ChildHierarchyNodeFieldMapImpl<DatabricksParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--databricks-host"}, description = "Databricks host name")
    @JsonPropertyDescription("Databricks host name. Supports also a ${DATABRICKS_HOST} configuration with a custom environment variable.")
    private String host;

    @CommandLine.Option(names = {"--databricks-port"}, description = "Databricks port number")
    @JsonPropertyDescription("Databricks port number. The default port is 443. Supports also a ${DATABRICKS_PORT} configuration with a custom environment variable.")
    private String port;

    @CommandLine.Option(names = {"--databricks-catalog"}, description = "Databricks catalog name.")
    @JsonPropertyDescription("Databricks catalog name. Supports also a ${DATABRICKS_CATALOG} configuration with a custom environment variable.")
    private String catalog;

    @CommandLine.Option(names = {"--databricks-user"}, description = "(Obsolete) Databricks user name.")
    @JsonPropertyDescription("(Obsolete) Databricks user name. Supports also a ${DATABRICKS_USER} configuration with a custom environment variable.")
    private String user;

    @CommandLine.Option(names = {"--databricks-password"}, description = "(Obsolete) Databricks database password.")
    @JsonPropertyDescription("(Obsolete) Databricks database password. Supports also a ${DATABRICKS_PASSWORD} configuration with a custom environment variable.")
    private String password;

    @CommandLine.Option(names = {"--databricks-http-path"}, description = "Databricks http path to the warehouse. For example: /sql/1.0/warehouses/<warehouse instance id>")
    @JsonPropertyDescription("Databricks http path to the warehouse. For example: /sql/1.0/warehouses/<warehouse instance id>. Supports also a ${DATABRICKS_HTTP_PATH} configuration with a custom environment variable.")
    private String httpPath;

    @CommandLine.Option(names = {"--databricks-access-token"}, description = "Databricks access token for the warehouse.")
    @JsonPropertyDescription("Databricks access token the warehouse. Supports also a ${DATABRICKS_ACCESS_TOKEN} configuration with a custom environment variable.")
    private String accessToken;

    @CommandLine.Option(names = {"--databricks-initialization-sql"}, description = "Custom SQL that is executed after connecting to Databricks.")
    @JsonPropertyDescription("Custom SQL that is executed after connecting to Databricks.")
    private String initializationSql;

    @CommandLine.Option(names = {"-D"}, description = "Databricks additional properties that are added to the JDBC connection string")
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
     * Returns a catalog name.
     * @return Catalog name.
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * Sets a catalog name.
     * @param catalog Catalog name.
     */
    public void setCatalog(String catalog) {
        setDirtyIf(!Objects.equals(this.catalog, catalog));
        this.catalog = catalog;
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
     * Returns the httpPath for warehouse.
     * @return HttpPath for warehouse.
     */
    public String getHttpPath() {
        return httpPath;
    }

    /**
     * Sets the httpPath for warehouse.
     * @param httpPath HttpPath.
     */
    public void setHttpPath(String httpPath) {
        setDirtyIf(!Objects.equals(this.httpPath, httpPath));
        this.httpPath = httpPath;
    }

    /**
     * Returns the access token for warehouse.
     * @return Access token for warehouse.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the accessToken for warehouse.
     * @param accessToken accessToken for warehouse.
     */
    public void setAccessToken(String accessToken) {
        setDirtyIf(!Objects.equals(this.accessToken, accessToken));
        this.accessToken = accessToken;
    }

    /**
     * Returns an initialization SQL that is executed after opening the connection.
     * @return
     */
    public String getInitializationSql() {
        return initializationSql;
    }

    /**
     * Sets an SQL query that is executed after opening a connection.
     * @param initializationSql Initialization sql.
     */
    public void setInitializationSql(String initializationSql) {
        this.setDirtyIf(!Objects.equals(this.initializationSql, initializationSql));
        this.initializationSql = initializationSql;
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
    public DatabricksParametersSpec deepClone() {
        DatabricksParametersSpec cloned = (DatabricksParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public DatabricksParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        DatabricksParametersSpec cloned = this.deepClone();
        cloned.host = secretValueProvider.expandValue(cloned.host, lookupContext);
        cloned.port = secretValueProvider.expandValue(cloned.port, lookupContext);
        cloned.catalog = secretValueProvider.expandValue(cloned.catalog, lookupContext);
        cloned.user = secretValueProvider.expandValue(cloned.user, lookupContext);
        cloned.password = secretValueProvider.expandValue(cloned.password, lookupContext);
        cloned.httpPath = secretValueProvider.expandValue(cloned.httpPath, lookupContext);
        cloned.accessToken = secretValueProvider.expandValue(cloned.accessToken, lookupContext);
        cloned.initializationSql = secretValueProvider.expandValue(cloned.initializationSql, lookupContext);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties, lookupContext);

        return cloned;
    }

    /**
     * Returns a database name.
     * @return Database name.
     */
    @Override
    public String getDatabase(){
        return catalog;
    }

}
