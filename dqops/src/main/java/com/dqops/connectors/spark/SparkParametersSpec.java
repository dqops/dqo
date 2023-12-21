package com.dqops.connectors.spark;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.snowflake.SnowflakeParametersSpec;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.BaseProviderParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * Apache Spark connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SparkParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {

    private static final ChildHierarchyNodeFieldMapImpl<SparkParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--spark-host"}, description = "Spark host name")
    @JsonPropertyDescription("Spark host name. Supports also a ${SPARK_HOST} configuration with a custom environment variable.")
    private String host;

    @CommandLine.Option(names = {"--spark-port"}, description = "Spark port number")
    @JsonPropertyDescription("Spark port number. The default port is 10000. Supports also a ${SPARK_PORT} configuration with a custom environment variable.")
    private String port;

    @CommandLine.Option(names = {"--spark-user"}, description = "Spark user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Spark user name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String user;

    @CommandLine.Option(names = {"--spark-password"}, description = "Spark database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Spark database password. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String password;

    @CommandLine.Option(names = {"--spark-options"}, description = "Spark connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes.")
    @JsonPropertyDescription("Spark connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${REDSHIFT_OPTIONS} configuration with a custom environment variable.")
    private String options;

    @CommandLine.Option(names = {"-K"}, description = "Spark additional properties that are added to the JDBC connection string")
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
    public SparkParametersSpec deepClone() {
        SparkParametersSpec cloned = (SparkParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public SparkParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        SparkParametersSpec cloned = this.deepClone();
        cloned.host = secretValueProvider.expandValue(cloned.host, lookupContext);
        cloned.port = secretValueProvider.expandValue(cloned.port, lookupContext);
        cloned.user = secretValueProvider.expandValue(cloned.user, lookupContext);
        cloned.password = secretValueProvider.expandValue(cloned.password, lookupContext);
        cloned.options = secretValueProvider.expandValue(cloned.options, lookupContext);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties, lookupContext);

        return cloned;
    }

    /**
     * Returns a database name.
     * @return Database name.
     */
    @Override
    public String getDatabase(){
        return "spark_catalog";
    }

}
