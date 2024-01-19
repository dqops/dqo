package com.dqops.connectors.mysql.singlestore;

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

import java.util.List;
import java.util.Objects;

/**
 * Single Store connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SingleStoreParametersSpec extends BaseProviderParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<SingleStoreParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @CommandLine.Option(names = {"--single-store-load-balancing-mode"}, description = "Failover and Load-Balancing Mode for Single Store. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("Failover and Load-Balancing Modes for Single Store. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private SingleStoreLoadBalancingMode loadBalancingMode;

    @CommandLine.Option(names = {"--single-store-host-descriptions"}, description = "Host descriptions")
    @JsonPropertyDescription("Host descriptions. Supports also a ${SINGLE_STORE_HOST_DESCRIPTIONS} configuration with a custom environment variable.")
    private List<String> hostDescriptions;

    /**
     * Returns the failover and load-balancing Mode.
     * @return Single Store's failover and load-balancing Mode.
     */
    public SingleStoreLoadBalancingMode getLoadBalancingMode() {
        return loadBalancingMode;
    }

    /**
     * Sets the failover and load-balancing Mode.
     * @param loadBalancingMode Single Store's failover and load-balancing Mode.
     */
    public void setLoadBalancingMode(SingleStoreLoadBalancingMode loadBalancingMode) {
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
    public void setHostDescriptions(List<String> hostDescriptions) {
        setDirtyIf(!Objects.equals(this.hostDescriptions, hostDescriptions));
        this.hostDescriptions = hostDescriptions;
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
    public SingleStoreParametersSpec deepClone() {
        SingleStoreParametersSpec cloned = (SingleStoreParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret value provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public SingleStoreParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        SingleStoreParametersSpec cloned = this.deepClone();
        cloned.loadBalancingMode = SingleStoreLoadBalancingMode.valueOf(secretValueProvider.expandValue(cloned.loadBalancingMode.toString(), lookupContext));
        cloned.hostDescriptions = secretValueProvider.expandList(cloned.hostDescriptions, lookupContext);

        return cloned;
    }

}
