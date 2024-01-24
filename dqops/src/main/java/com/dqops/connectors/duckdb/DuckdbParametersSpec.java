/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.connectors.duckdb;

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
 * DuckDB connection parameters.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class DuckdbParametersSpec extends BaseProviderParametersSpec
        implements ConnectionProviderSpecificParameters {
    private static final ChildHierarchyNodeFieldMapImpl<DuckdbParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(BaseProviderParametersSpec.FIELDS) {
        {
        }
    };

    // todo: current driver sets it automatically when no folder is implicitly set to the connection stirng
    @CommandLine.Option(names = {"--duckdb-in-memory"}, description = "To use the special value :memory: to create an in-memory database where no data is persisted to disk (i.e., all data is lost when you exit the process). The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("To use the special value :memory: to create an in-memory database where no data is persisted to disk (i.e., all data is lost when you exit the process). The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private boolean inMemory;

    //todo: parameters
//    @CommandLine.Option(names = {"--postgresql-host"}, description = "DuckDB host name")
//    @JsonPropertyDescription("PostgreSQL host name. Supports also a ${POSTGRESQL_HOST} configuration with a custom environment variable.")
//    private String host;

    @CommandLine.Option(names = {"--duckdb-database"}, description = "DuckDB database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    @JsonPropertyDescription("DuckDB database name. The value can be in the ${ENVIRONMENT_VARIABLE_NAME} format to use dynamic substitution.")
    private String database;

    @CommandLine.Option(names = {"--duckdb-options"}, description = "DuckDB connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes.")
    @JsonPropertyDescription("DuckDB connection 'options' initialization parameter. For example setting this to -c statement_timeout=5min would set the statement timeout parameter for this session to 5 minutes. Supports also a ${DUCKDB_OPTIONS} configuration with a custom environment variable.")
    private String options;

    @CommandLine.Option(names = {"-D"}, description = "DuckDB additional properties that are added to the JDBC connection string")
    @JsonPropertyDescription("A dictionary of custom JDBC parameters that are added to the JDBC connection string, a key/value dictionary.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> properties;

    /**
     * Returns an inMemory value.
     * @return inMemory value.
     */
    public boolean isInMemory () {
        return inMemory;
    }

    /**
     * Sets an inMemory value.
     * @param inMemory inMemory value.
     */
    public void setInMemory(boolean inMemory) {
        setDirtyIf(!Objects.equals(this.inMemory, inMemory));
        this.inMemory = inMemory;
    }

//    /**
//     * Returns the host name.
//     * @return Host name.
//     */
//    public String getHost() {
//        return host;
//    }
//
//    /**
//     * Sets the host name.
//     * @param host New host name.
//     */
//    public void setHost(String host) {
//        setDirtyIf(!Objects.equals(this.host, host));
//        this.host = host;
//    }

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
    public DuckdbParametersSpec deepClone() {
        DuckdbParametersSpec cloned = (DuckdbParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed and expanded version of the object without unwanted properties, but with all variables like ${ENV_VAR} expanded.
     * @param secretValueProvider Secret provider.
     * @param lookupContext Secret lookup context.
     * @return Trimmed and expanded version of this object.
     */
    public DuckdbParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        DuckdbParametersSpec cloned = this.deepClone();
//        cloned.host = secretValueProvider.expandValue(cloned.host, lookupContext);
        cloned.database = secretValueProvider.expandValue(cloned.database, lookupContext);
        cloned.options = secretValueProvider.expandValue(cloned.options, lookupContext);
        cloned.properties = secretValueProvider.expandProperties(cloned.properties, lookupContext);

        return cloned;
    }

}
