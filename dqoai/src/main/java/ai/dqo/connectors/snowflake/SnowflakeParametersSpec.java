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
package ai.dqo.connectors.snowflake;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.sources.BaseProviderParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Snowflake connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SnowflakeParametersSpec extends BaseProviderParametersSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<SnowflakeParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Snowflake account name, e.q. <account>.<region> or <account>.<region>.<platform>. Supports also a ${SNOWFLAKE_ACCOUNT} configuration with a custom environment variable.")
    private String account;

    @JsonPropertyDescription("Snowflake warehouse name. Supports also a ${SNOWFLAKE_WAREHOUSE} configuration with a custom environment variable.")
    private String warehouse;

    @JsonPropertyDescription("Snowflake role name. Supports also a ${SNOWFLAKE_ROLE} configuration with a custom environment variable.")
    private String role;

    /**
     * Returns a snowflake account name.
     * @return Snowflake account name.
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets a snowflake account name.
     * @param account Snowflake account name.
     */
    public void setAccount(String account) {
		setDirtyIf(!Objects.equals(this.account, account));
        this.account = account;
    }

    /**
     * Returns the snowflake warehouse name.
     * @return Warehouse name.
     */
    public String getWarehouse() {
        return warehouse;
    }

    /**
     * Sets the snowflake warehouse name.
     * @param warehouse Warehouse name.
     */
    public void setWarehouse(String warehouse) {
		setDirtyIf(!Objects.equals(this.warehouse, warehouse));
        this.warehouse = warehouse;
    }

    /**
     * Gets the role.
     * @return Role name.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role name.
     * @param role Role name.
     */
    public void setRole(String role) {
		setDirtyIf(!Objects.equals(this.role, role));
        this.role = role;
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
    public SnowflakeParametersSpec clone() {
        try {
            SnowflakeParametersSpec cloned = (SnowflakeParametersSpec)super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @return Trimmed and expanded version of this object.
     */
    public SnowflakeParametersSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            SnowflakeParametersSpec cloned = (SnowflakeParametersSpec) super.clone();
            cloned.account = secretValueProvider.expandValue(cloned.account);
            cloned.warehouse = secretValueProvider.expandValue(cloned.warehouse);
            cloned.role = secretValueProvider.expandValue(cloned.role);

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }
}
