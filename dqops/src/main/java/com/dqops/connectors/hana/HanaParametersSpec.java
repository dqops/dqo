/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.connectors.hana;

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
 * Sap Hana connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class HanaParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {

    private static final ChildHierarchyNodeFieldMapImpl<HanaParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--hana-host"}, description = "Hana host name")
    @JsonPropertyDescription("Hana host name. Supports also a ${HANA_HOST} configuration with a custom environment variable.")
    private String host;

    @CommandLine.Option(names = {"--hana-port"}, description = "Hana port number")
    @JsonPropertyDescription("Hana port number. The default port is 30015. Supports also a ${HANA_PORT} configuration with a custom environment variable.")
    private String port;

    @CommandLine.Option(names = {"--hana-instance-number"}, description = "Hana instance number")
    @JsonPropertyDescription("Hana instance number. Supports also a ${HANA_INSTANCE_NUMBER} configuration with a custom environment variable.")
    private String instanceNumber;

    @CommandLine.Option(names = {"--hana-user"}, description = "Hana user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Hana user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String user;

    @CommandLine.Option(names = {"--hana-password"}, description = "Hana database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Hana database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String password;

    @CommandLine.Option(names = {"-H"}, description = "Hana additional properties that are added to the JDBC connection string")
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
     * @param port Port number.
     */
    public void setPort(String port) {
        setDirtyIf(!Objects.equals(this.port, port));
        this.port = port;
    }

    /**
     * Returns the instance number. The value should store an environment variable expression or a numeric instance number.
     * @return Instance number or an expression to be extracted.
     */
    public String getInstanceNumber() {
        return instanceNumber;
    }

    /**
     * Sets the instance number.
     * @param instanceNumber instance number.
     */
    public void setInstanceNumber(String instanceNumber) {
        setDirtyIf(!Objects.equals(this.instanceNumber, instanceNumber));
        this.instanceNumber = instanceNumber;
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
    public HanaParametersSpec deepClone() {
        HanaParametersSpec cloned = (HanaParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public HanaParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        HanaParametersSpec cloned = this.deepClone();
        cloned.host = secretValueProvider.expandValue(cloned.host, lookupContext);
        cloned.port = secretValueProvider.expandValue(cloned.port, lookupContext);
        cloned.instanceNumber = secretValueProvider.expandValue(cloned.instanceNumber, lookupContext);
        cloned.user = secretValueProvider.expandValue(cloned.user, lookupContext);
        cloned.password = secretValueProvider.expandValue(cloned.password, lookupContext);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties, lookupContext);

        return cloned;
    }

    /**
     * Returns a database name.
     * @return Database name.
     */
    @Override
    public String getDatabase(){
        return null;
    }

}
