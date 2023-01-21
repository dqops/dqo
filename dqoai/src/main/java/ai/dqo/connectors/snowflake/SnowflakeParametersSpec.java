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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import picocli.CommandLine;

import java.util.LinkedHashMap;
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

    @CommandLine.Option(names = {"--snowflake-account"}, description = "Snowflake account name, e.q. <account>, <account>-<locator>, <account>.<region> or <account>.<region>.<platform>.")
    @JsonPropertyDescription("Snowflake account name, e.q. <account>, <account>-<locator>, <account>.<region> or <account>.<region>.<platform>.. Supports also a ${SNOWFLAKE_ACCOUNT} configuration with a custom environment variable.")
    private String account;

    @CommandLine.Option(names = {"--snowflake-warehouse"}, description = "Snowflake warehouse name.")
    @JsonPropertyDescription("Snowflake warehouse name. Supports also a ${SNOWFLAKE_WAREHOUSE} configuration with a custom environment variable.")
    private String warehouse;

    @CommandLine.Option(names = {"--snowflake-database"}, description = "Snowflake database name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    @JsonPropertyDescription("Snowflake database name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    private String database;

    @CommandLine.Option(names = {"--snowflake-user"}, description = "Snowflake user name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    @JsonPropertyDescription("Snowflake user name. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    private String user;

    @CommandLine.Option(names = {"--snowflake-password"}, description = "Snowflake database password. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    @JsonPropertyDescription("Snowflake database password. The value could be in the format ${ENVIRONMENT_VARIABLE_NAME} to use dynamic substitution.")
    private String password;

    @CommandLine.Option(names = {"--snowflake-role"}, description = "Snowflake role name.")
    @JsonPropertyDescription("Snowflake role name. Supports also a ${SNOWFLAKE_ROLE} configuration with a custom environment variable.")
    private String role;

    @CommandLine.Option(names = {"--snowflake-properties"}, description = "Snowflake additional properties that are added to the JDBC connection string")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, String> properties = new LinkedHashMap<>();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private LinkedHashMap<String, String> originalProperties = new LinkedHashMap<>(); // used to perform comparison in the isDirty check

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
     * Returns a key/value map of additional properties that are included in the JDBC connection string.
     * @return Key/value dictionary of additional JDBC properties.
     */
    public LinkedHashMap<String, String> getProperties() {
        return properties;
    }

    /**
     * Sets a dictionary of additional connection parameters that are added to the JDBC connection string.
     * @param properties Key/value dictionary with extra parameters.
     */
    public void setProperties(LinkedHashMap<String, String> properties) {
        setDirtyIf(!Objects.equals(this.properties, properties));
        this.properties = properties;
        this.originalProperties = (LinkedHashMap<String, String>) properties.clone();
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
            if (cloned.properties != null) {
                cloned.properties = (LinkedHashMap<String, String>) cloned.properties.clone();
            }
            if (cloned.originalProperties != null) {
                cloned.originalProperties = (LinkedHashMap<String, String>) cloned.originalProperties.clone();
            }
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
            cloned.database = secretValueProvider.expandValue(cloned.database);
            cloned.user = secretValueProvider.expandValue(cloned.user);
            cloned.password = secretValueProvider.expandValue(cloned.password);
            cloned.role = secretValueProvider.expandValue(cloned.role);
            cloned.properties = secretValueProvider.expandProperties(cloned.properties);
            cloned.originalProperties = null;

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        return super.isDirty() || !Objects.equals(this.properties, this.originalProperties);
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        super.clearDirty(propagateToChildren);
        this.originalProperties = (LinkedHashMap<String, String>) this.properties.clone();
    }
}
