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
package ai.dqo.connectors.postgresql;

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
 * Postgresql connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class PostgresqlParametersSpec extends BaseProviderParametersSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<PostgresqlParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("PostgreSQL host name. Supports also a ${POSTGRESQL_HOST} configuration with a custom environment variable.")
    private String host;

    @JsonPropertyDescription("PostgreSQL port name. The default port is 5432. Supports also a ${POSTGRESQL_PORT} configuration with a custom environment variable.")
    private String port = "5432";

    @JsonPropertyDescription("PostgreSQL connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${POSTGRESQL_OPTIONS} configuration with a custom environment variable.")
    private String options;

    @JsonPropertyDescription("Connect to PostgreSQL using SSL. The default value is false.")
    private Boolean ssl;

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
     * Returns the port name. The value should store an environment variable expression or a numeric postgresql port name.
     * @return Port name or an expression to be extracted.
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the port name.
     * @param port Port name.
     */
    public void setPort(String port) {
        setDirtyIf(!Objects.equals(this.port, port));
        this.port = port;
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
     * Returns the flag to require SSL connection.
     * @return True - require an SSL connection.
     */
    public Boolean getSsl() {
        return ssl;
    }

    /**
     * Sets a flag to require an SSL connection.
     * @param ssl True - ssl connection is required.
     */
    public void setSsl(Boolean ssl) {
        setDirtyIf(!Objects.equals(this.ssl, ssl));
        this.ssl = ssl;
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
    public PostgresqlParametersSpec clone() {
        try {
            PostgresqlParametersSpec cloned = (PostgresqlParametersSpec)super.clone();
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
    public PostgresqlParametersSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        try {
            PostgresqlParametersSpec cloned = (PostgresqlParametersSpec) super.clone();
            cloned.host = secretValueProvider.expandValue(cloned.host);
            cloned.port = secretValueProvider.expandValue(cloned.port);
            cloned.options = secretValueProvider.expandValue(cloned.options);

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned", ex);
        }
    }
}
