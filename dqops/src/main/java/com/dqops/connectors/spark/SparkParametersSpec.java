package com.dqops.connectors.spark;

import com.dqops.connectors.ConnectionProviderSpecificParameters;
import com.dqops.connectors.oracle.OracleParametersSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.BaseProviderParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import picocli.CommandLine;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class SparkParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {

    private static final ChildHierarchyNodeFieldMapImpl<OracleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--spark-host"}, description = "Spark host name")
    @JsonPropertyDescription("Spark host name. Supports also a ${SPARK_HOST} configuration with a custom environment variable.")
    private String host;

    @CommandLine.Option(names = {"--spark-port"}, description = "Spark port number")
    @JsonPropertyDescription("Spark port number. The default port is 10000. Supports also a ${SPARK_PORT} configuration with a custom environment variable.")
    private String port;

    @CommandLine.Option(names = {"--spark-database"}, description = "Spark database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Spark database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String database;

    @CommandLine.Option(names = {"-P"}, description = "Spark additional properties that are added to the JDBC connection string")
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

    @Override
    protected ChildHierarchyNodeFieldMap getChildMap() {
        return FIELDS;
    }

}


