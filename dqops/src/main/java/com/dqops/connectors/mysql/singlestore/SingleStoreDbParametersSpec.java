package com.dqops.connectors.mysql.singlestore;

import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.sources.BaseProviderParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Single Store DB connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SingleStoreDbParametersSpec extends BaseProviderParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<SingleStoreDbParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--single-store-load-balancing-mode"}, description = "Failover and Load-Balancing Mode for Single Store DB. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Failover and Load-Balancing Modes for Single Store DB. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private SingleStoreDbLoadBalancingMode loadBalancingMode;

    @CommandLine.Option(names = {"--single-store-host-descriptions"}, description = "Host descriptions")
    @JsonPropertyDescription("Host descriptions. Supports also a ${SINGLE_STORE_HOST_DESCRIPTIONS} configuration with a custom environment variable.")
    private List<String> hostDescriptions;

    @CommandLine.Option(names = {"--single-store-schema"}, description = "MySQL database/schema name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Single Store DB database/schema name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String schema;

    @CommandLine.Option(names = {"--single-store-host-descriptions"}, description = "Force enables SSL/TLS on the connection.")
    @JsonPropertyDescription("Force enables SSL/TLS on the connection. Supports also a ${SINGLE_STORE_USE_SSL} configuration with a custom environment variable.")
    private boolean useSsl;

    public SingleStoreDbParametersSpec() {
        this.hostDescriptions = new ArrayList<>();
    }

    /**
     * The default catalog name of single store db.
     */
    public static final String DEFAULT_CATALOG_NAME = "def";

    /**
     * Returns the failover and load-balancing Mode.
     * @return Single Store DB's failover and load-balancing Mode.
     */
    public SingleStoreDbLoadBalancingMode getLoadBalancingMode() {
        return loadBalancingMode;
    }

    /**
     * Sets the failover and load-balancing Mode.
     * @param loadBalancingMode Single Store DB's failover and load-balancing Mode.
     */
    public void setLoadBalancingMode(SingleStoreDbLoadBalancingMode loadBalancingMode) {
        setDirtyIf(!Objects.equals(this.loadBalancingMode, loadBalancingMode));
        this.loadBalancingMode = loadBalancingMode;
    }

    /**
     * Returns the host descriptions.
     * @return Host descriptions.
     */
    public List<String> getHostDescriptions() {
        return hostDescriptions;
    }

    /**
     * Sets the host descriptions.
     * @param hostDescriptions Host descriptions.
     */
    @JsonSetter
    public void setHostDescriptions(List<String> hostDescriptions) {
        setDirtyIf(!Objects.equals(this.hostDescriptions, hostDescriptions));
        this.hostDescriptions = hostDescriptions;
    }

    /**
     * Sets the host descriptions.
     * @param commaSeparatedHostDescriptions Host descriptions separated by comma character.
     */
    @JsonIgnore
    public void setHostDescriptions(String commaSeparatedHostDescriptions) {
        setDirtyIf(!Objects.equals(this.hostDescriptions, hostDescriptions));
        this.hostDescriptions = Arrays.stream(commaSeparatedHostDescriptions.split(",")).collect(Collectors.toList());
    }

    /**
     * Returns the flag to require SSL connection.
     * @return True - require an SSL connection.
     */
    public boolean isUseSsl() {
        return useSsl;
    }

    /**
     * Sets a flag to require an SSL connection.
     * @param useSsl True - ssl connection is required.
     */
    public void setUseSsl(boolean useSsl) {
        setDirtyIf(!Objects.equals(this.useSsl, useSsl));
        this.useSsl = useSsl;
    }

    /**
     * Returns a schema name.
     * @return Schema name.
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets a schema name.
     * @param schema Schema name.
     */
    public void setSchema(String schema) {
        setDirtyIf(!Objects.equals(this.schema, schema));
        this.schema = schema;
    }

    /**
     * Returns the child map on the spec class with all fields.
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
    public SingleStoreDbParametersSpec deepClone() {
        SingleStoreDbParametersSpec cloned = (SingleStoreDbParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public SingleStoreDbParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        SingleStoreDbParametersSpec cloned = this.deepClone();
        cloned.loadBalancingMode = SingleStoreDbLoadBalancingMode.valueOf(secretValueProvider.expandValue(cloned.loadBalancingMode.toString(), lookupContext));
        cloned.hostDescriptions = secretValueProvider.expandList(cloned.hostDescriptions, lookupContext);
        cloned.schema = secretValueProvider.expandValue(cloned.schema, lookupContext);

        return cloned;
    }

}
