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
package com.dqops.metadata.definitions.sensors;

import com.dqops.execution.sqltemplates.rendering.JinjaSqlTemplateSensorRunner;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.utils.serialization.InvalidYamlStatusHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Specification (configuration) for a provider specific implementation of a data quality sensor or an SQL template.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ProviderSensorDefinitionSpec extends AbstractSpec implements InvalidYamlStatusHolder {
    private static final ChildHierarchyNodeFieldMapImpl<ProviderSensorDefinitionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Sensor implementation type")
    private ProviderSensorRunnerType type = ProviderSensorRunnerType.sql_template;

    @JsonPropertyDescription("Java class name for a sensor runner that will execute the sensor. The \"type\" must be \"java_class\".")
    private String javaClassName = JinjaSqlTemplateSensorRunner.CLASS_NAME;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyDescription("The sensor supports grouping, using the GROUP BY clause in SQL. Sensors that support a GROUP BY condition can capture separate data quality scores for each data group. The default value is true, because most of the data quality sensor support grouping.")
    private Boolean supportsGrouping;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyDescription("The sensor supports grouping by a partition date, using the GROUP BY clause in SQL. Sensors that support grouping by a partition_by_column could be used for partition checks, calculating separate data quality metrics for each daily/monthly partition. The default value is true, because most of the data quality sensor support partitioned checks.")
    private Boolean supportsPartitionedChecks;

    @JsonPropertyDescription("Additional provider specific sensor parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> parameters;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonPropertyDescription("Disables merging this sensor's SQL with other sensors. When this parameter is 'true', the sensor's SQL will be executed as an independent query.")
    private boolean disableMergingQueries;

    @JsonIgnore
    private String yamlParsingError;

    /**
     * Sets a value that indicates that the YAML file deserialized into this object has a parsing error.
     *
     * @param yamlParsingError YAML parsing error.
     */
    @Override
    public void setYamlParsingError(String yamlParsingError) {
        this.yamlParsingError = yamlParsingError;
    }

    /**
     * Returns the YAML parsing error that was captured.
     *
     * @return YAML parsing error.
     */
    @Override
    public String getYamlParsingError() {
        return this.yamlParsingError;
    }

    /**
     * Returns a key/value map of additional rule parameters.
     * @return Key/value dictionary of additional parameters passed to the rule.
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Sets a dictionary of parameters passed to the rule.
     * @param parameters Key/value dictionary with extra parameters.
     */
    public void setParameters(Map<String, String> parameters) {
		setDirtyIf(!Objects.equals(this.parameters, parameters));
        this.parameters = parameters != null ? Collections.unmodifiableMap(parameters) : null;
    }

    /**
     * Sensor implementation type.
     * @return Sensor implementation type.
     */
    public ProviderSensorRunnerType getType() {
        return type;
    }

    /**
     * Sets the sensor implementation type.
     * @param type Sensor type.
     */
    public void setType(ProviderSensorRunnerType type) {
		this.setDirtyIf(this.type != type);
        this.type = type;
    }

    /**
     * Returns a java class name for a sensor runner.
     * @return Java class for a sensor runner.
     */
    public String getJavaClassName() {
        return javaClassName;
    }

    /**
     * Sets a java class for a sensor runner.
     * @param javaClassName Sensor runner.
     */
    public void setJavaClassName(String javaClassName) {
		this.setDirtyIf(!Objects.equals(this.javaClassName, javaClassName));
        this.javaClassName = javaClassName;
    }

    /**
     * Returns true if the sensor supports grouping by the data stream.
     * @return True when the sensor supports grouping by the data stream.
     */
    public Boolean getSupportsGrouping() {
        return supportsGrouping;
    }

    /**
     * Sets the flag if the sensor supports grouping by the data stream.
     * @param supportsGrouping True when the sensor supports grouping, false otherwise.
     */
    public void setSupportsGrouping(Boolean supportsGrouping) {
        this.setDirtyIf(!Objects.equals(this.supportsGrouping, supportsGrouping));
        this.supportsGrouping = supportsGrouping;
    }

    /**
     * Checks if the sensor supports grouping by the date column identified by the 'partition_by_column' parameter, that means the sensor can analyze daily and monthly partitioned data.
     * @return True when the sensor supports partitioned checks.
     */
    public Boolean getSupportsPartitionedChecks() {
        return supportsPartitionedChecks;
    }

    /**
     * Sets the flag to enable/disable grouping by a partitioning column, enabling support for partitioned checks.
     * @param supportsPartitionedChecks Supports partitioned checks.
     */
    public void setSupportsPartitionedChecks(Boolean supportsPartitionedChecks) {
        this.setDirtyIf(!Objects.equals(this.supportsPartitionedChecks, supportsPartitionedChecks));
        this.supportsPartitionedChecks = supportsPartitionedChecks;
    }

    /**
     * Returns true if this sensor should not be merged with other queries.
     * @return True when this query should not be merged with other queries.
     */
    public boolean isDisableMergingQueries() {
        return disableMergingQueries;
    }

    /**
     * Sets a flag to disable merging queries with other similar queries.
     * @param disableMergingQueries Disable merging queries flag.
     */
    public void setDisableMergingQueries(boolean disableMergingQueries) {
        this.setDirtyIf(this.disableMergingQueries != disableMergingQueries);
        this.disableMergingQueries = disableMergingQueries;
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
     * Calls a visitor (using a visitor design pattern) that returns a result.
     *
     * @param visitor   Visitor instance.
     * @param parameter Additional parameter that will be passed back to the visitor.
     */
    @Override
    public <P, R> R visit(HierarchyNodeResultVisitor<P, R> visitor, P parameter) {
        return visitor.accept(this, parameter);
    }

    /**
     * Creates a trimmed version of the object without unwanted properties.
     * A trimmed version is passed to a Jinja2 sql template as a context parameter.
     * @return Trimmed version of this object.
     */
    public ProviderSensorDefinitionSpec trim() {
        return this; // returns self
    }

    /**
     * Creates and returns a deep clone (copy) of this object.
     */
    @Override
    public ProviderSensorDefinitionSpec deepClone() {
        return (ProviderSensorDefinitionSpec)super.deepClone();
    }
}
