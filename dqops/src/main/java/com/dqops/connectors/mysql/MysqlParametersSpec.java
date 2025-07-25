/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.connectors.mysql;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.mysql.singlestore.SingleStoreDbParametersSpec;
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
 * MySql connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class MysqlParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {
    private static final ChildHierarchyNodeFieldMapImpl<MysqlParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--mysql-host"}, description = "MySQL host name")
    @JsonPropertyDescription("MySQL host name. Supports also a ${MYSQL_HOST} configuration with a custom environment variable.")
    private String host;

    @CommandLine.Option(names = {"--mysql-port"}, description = "MySQL port number")
    @JsonPropertyDescription("MySQL port number. The default port is 3306. Supports also a ${MYSQL_PORT} configuration with a custom environment variable.")
    private String port;

    @CommandLine.Option(names = {"--mysql-database"}, description = "MySQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("MySQL database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String database;

    @CommandLine.Option(names = {"--mysql-user"}, description = "MySQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("MySQL user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String user;

    @CommandLine.Option(names = {"--mysql-password"}, description = "MySQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("MySQL database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String password;

    @CommandLine.Option(names = {"--mysql-sslmode"}, description = "SslMode MySQL connection parameter")
    @JsonPropertyDescription("SslMode MySQL connection parameter.")
    private MySqlSslMode sslmode;

    @CommandLine.Option(names = {"--single-store-parameters-spec"}, description = "Single Store DB parameters spec.")
    @JsonPropertyDescription("Single Store DB parameters spec.")
    private SingleStoreDbParametersSpec singleStoreDbParametersSpec;

    @CommandLine.Option(names = {"--mysql-engine"}, description = "MySQL engine type. Supports also a ${MYSQL_ENGINE} configuration with a custom environment variable.")
    @JsonPropertyDescription("MySQL engine type. Supports also a ${MYSQL_ENGINE} configuration with a custom environment variable.")
    private MysqlEngineType mysqlEngineType;

    @CommandLine.Option(names = {"-M"}, description = "MySQL additional properties that are added to the JDBC connection string")
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
     * Returns the port number. The value should store an environment variable expression or a numeric mysql port number.
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
        if (mysqlEngineType == MysqlEngineType.singlestoredb) {
            return SingleStoreDbParametersSpec.DEFAULT_CATALOG_NAME; // database and schema is the same thing for the single store db
        } else {
            return database;
        }
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
     * Returns the flag to require SSL connection.
     * @return True - require an SSL connection.
     */
    public MySqlSslMode getSslmode() {
        return sslmode;
    }

    /**
     * Sets a flag to require an SSL connection.
     * @param sslmode True - ssl connection is required.
     */
    public void setSslmode(MySqlSslMode sslmode) {
        setDirtyIf(!Objects.equals(this.sslmode, sslmode));
        this.sslmode = sslmode;
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
     * Returns the SingleStoreDbParametersSpec
     * @return SingleStoreDbParametersSpec
     */
    public SingleStoreDbParametersSpec getSingleStoreDbParametersSpec() {
        return singleStoreDbParametersSpec;
    }

    /**
     * Sets a SingleStoreDbParametersSpec
     * @param singleStoreDbParametersSpec SingleStoreDbParametersSpec
     */
    public void setSingleStoreDbParametersSpec(SingleStoreDbParametersSpec singleStoreDbParametersSpec) {
        setDirtyIf(!Objects.equals(this.singleStoreDbParametersSpec, singleStoreDbParametersSpec));
        this.singleStoreDbParametersSpec = singleStoreDbParametersSpec;
    }


    /**
     * Returns the MySQL engine type.
     * @return MySQL engine type.
     */
    public MysqlEngineType getMysqlEngineType() {
        return mysqlEngineType;
    }

    /**
     * Sets a MySQL engine type.
     * @param mysqlEngineType MySQL engine type.
     */
    public void setMysqlEngineType(MysqlEngineType mysqlEngineType) {
        setDirtyIf(!Objects.equals(this.mysqlEngineType, mysqlEngineType));
        this.mysqlEngineType = mysqlEngineType;
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
    public MysqlParametersSpec deepClone() {
        MysqlParametersSpec cloned = (MysqlParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public MysqlParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        MysqlParametersSpec cloned = this.deepClone();
        cloned.host = secretValueProvider.expandValue(cloned.host, lookupContext);
        cloned.port = secretValueProvider.expandValue(cloned.port, lookupContext);
        cloned.database = secretValueProvider.expandValue(cloned.database, lookupContext);
        cloned.user = secretValueProvider.expandValue(cloned.user, lookupContext);
        cloned.password = secretValueProvider.expandValue(cloned.password, lookupContext);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties, lookupContext);

        if (cloned.singleStoreDbParametersSpec != null) {
            cloned.singleStoreDbParametersSpec = cloned.singleStoreDbParametersSpec.expandAndTrim(secretValueProvider, lookupContext);
        }

        return cloned;
    }
}
