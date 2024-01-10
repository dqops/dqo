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
 * Trino connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class TrinoParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {
    private static final ChildHierarchyNodeFieldMapImpl<TrinoParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--trino-engine"}, description = "Trino engine type.")
    @JsonPropertyDescription("Trino engine type. Supports also a ${TRINO_ENGINE} configuration with a custom environment variable.")
    private TrinoEngineType trinoEngineType;

    @CommandLine.Option(names = {"--trino-host"}, description = "Trino host name.")
    @JsonPropertyDescription("Trino host name. Supports also a ${TRINO_HOST} configuration with a custom environment variable.")
    private String host;

    @CommandLine.Option(names = {"--trino-port"}, description = "Trino port number.")
    @JsonPropertyDescription("Trino port number. The default port is 8080. Supports also a ${TRINO_PORT} configuration with a custom environment variable.")
    private String port;

    @CommandLine.Option(names = {"--trino-user"}, description = "Trino user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Trino user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String user;

    // TODO: Handling authentication methods to different Trino instances: https://trino.io/docs/current/security/authentication-types.html
//    @CommandLine.Option(names = {"--trino-password"}, description = "Trino database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
//    @JsonPropertyDescription("Trino database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
//    private String password;

    @CommandLine.Option(names = {"-T"}, description = "Trino additional properties that are added to the JDBC connection string")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> properties;

    @CommandLine.Option(names = {"--athena-region"}, description = "The AWS Athena Region where queries will be run. Supports also a ${ATHENA_REGION} configuration with a custom environment variable.")
    @JsonPropertyDescription("The AWS Region where queries will be run. Supports also a ${ATHENA_REGION} configuration with a custom environment variable.")
    private String athenaRegion;

    @CommandLine.Option(names = {"--trino-catalog"}, description = "The Trino catalog that contains the databases and the tables that will be accessed with the driver. Supports also a ${TRINO_CATALOG} configuration with a custom environment variable.")
    @JsonPropertyDescription("The catalog that contains the databases and the tables that will be accessed with the driver. Supports also a ${TRINO_CATALOG} configuration with a custom environment variable.")
    private String catalog;

    @CommandLine.Option(names = {"--athena-work-group"}, description = "The Athena WorkGroup in which queries will run. Supports also a ${ATHENA_WORK_GROUP} configuration with a custom environment variable.")
    @JsonPropertyDescription("The workgroup in which queries will run. Supports also a ${ATHENA_WORK_GROUP} configuration with a custom environment variable.")
    private String athenaWorkGroup;

    @CommandLine.Option(names = {"--athena-output-location"}, description = "The location in Amazon S3 where query results will be stored. Supports also a ${ATHENA_OUTPUT_LOCATION} configuration with a custom environment variable.")
    @JsonPropertyDescription("The location in Amazon S3 where query results will be stored. Supports also a ${ATHENA_OUTPUT_LOCATION} configuration with a custom environment variable.")
    private String athenaOutputLocation;

    /**
     * Returns the trino engine type.
     * @return Trino engine type.
     */
    public TrinoEngineType getTrinoEngineType() {
        return trinoEngineType;
    }

    /**
     * Sets the trino engine type.
     * @param trinoEngineType New trino engine type.
     */
    public void setTrinoEngineType(TrinoEngineType trinoEngineType) {
        setDirtyIf(!Objects.equals(this.trinoEngineType, trinoEngineType));
        this.trinoEngineType = trinoEngineType;
    }

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
     * Returns the port number. The value should store an environment variable expression or a numeric trino port number.
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

//    /**
//     * Returns a password used to authenticate to the server.
//     * @return Password.
//     */
//    public String getPassword() {
//        return password;
//    }
//
//    /**
//     * Sets a password that is used to connect to the database.
//     * @param password Password.
//     */
//    public void setPassword(String password) {
//        setDirtyIf(!Objects.equals(this.password, password));
//        this.password = password;
//    }

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
     * Returns the Athena's AWS Region.
     * @return Athena's AWS Region.
     */
    public String getAthenaRegion() {
        return athenaRegion;
    }

    /**
     * Sets Athena's AWS Region.
     * @param athenaRegion Athena's AWS Region.
     */
    public void setAthenaRegion(String athenaRegion) {
        setDirtyIf(!Objects.equals(this.athenaRegion, athenaRegion));
        this.athenaRegion = athenaRegion;
    }

    /**
     * Returns the catalog
     * @return The catalog name.
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * Sets the catalog name.
     * @param catalog The catalog name.
     */
    public void setCatalog(String catalog) {
        setDirtyIf(!Objects.equals(this.catalog, catalog));
        this.catalog = catalog;
    }

    /**
     * Returns a high level container name that contian schemas.
     * @return High level container name that contian schemas.
     */
    @Override
    public String getDatabase(){
        return getCatalog();
    }

    /**
     * Returns the Athena's WorkGroup
     * @return Athena's WorkGroup.
     */
    public String getAthenaWorkGroup() {
        return athenaWorkGroup;
    }

    /**
     * Sets Athena's WorkGroup.
     * @param athenaWorkGroup Athena's WorkGroup.
     */
    public void setAthenaWorkGroup(String athenaWorkGroup) {
        setDirtyIf(!Objects.equals(this.athenaWorkGroup, athenaWorkGroup));
        this.athenaWorkGroup = athenaWorkGroup;
    }

    /**
     * Returns the Athena's OutputLocation
     * @return Athena's OutputLocation.
     */
    public String getAthenaOutputLocation() {
        return athenaOutputLocation;
    }

    /**
     * Sets Athena's OutputLocation.
     * @param athenaOutputLocation Athena's OutputLocation.
     */
    public void setAthenaOutputLocation(String athenaOutputLocation) {
        setDirtyIf(!Objects.equals(this.athenaOutputLocation, athenaOutputLocation));
        this.athenaOutputLocation = athenaOutputLocation;
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
    public TrinoParametersSpec deepClone() {
        TrinoParametersSpec cloned = (TrinoParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public TrinoParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        TrinoParametersSpec cloned = this.deepClone();

        cloned.trinoEngineType = TrinoEngineType.valueOf(secretValueProvider.expandValue(cloned.trinoEngineType.toString(), lookupContext));

        cloned.host = secretValueProvider.expandValue(cloned.host, lookupContext);
        cloned.port = secretValueProvider.expandValue(cloned.port, lookupContext);
        cloned.user = secretValueProvider.expandValue(cloned.user, lookupContext);
        // cloned.password = secretValueProvider.expandValue(cloned.password, lookupContext);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties, lookupContext);

        cloned.athenaRegion = secretValueProvider.expandValue(cloned.athenaRegion, lookupContext);
        cloned.catalog = secretValueProvider.expandValue(cloned.catalog, lookupContext);
        cloned.athenaWorkGroup = secretValueProvider.expandValue(cloned.athenaWorkGroup, lookupContext);
        cloned.athenaOutputLocation = secretValueProvider.expandValue(cloned.athenaOutputLocation, lookupContext);

        return cloned;
    }
}
