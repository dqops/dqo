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

import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.fields.ParameterDefinitionsListSpec;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Data Quality sensor definition specification. Provides the configuration for a data quality sensor definition, sensor's parameters, etc.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SensorDefinitionSpec extends AbstractSpec {
    private static final ChildHierarchyNodeFieldMapImpl<SensorDefinitionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("fields", o -> o.fields);
        }
    };

    @JsonPropertyDescription("List of fields that are parameters of a custom sensor. " +
            "Those fields are used by the DQOps UI to display the data quality check editing screens with proper UI controls for all required fields.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ParameterDefinitionsListSpec fields;

    @JsonPropertyDescription("The data quality sensor depends on the configuration of the event timestamp column name on the analyzed table. " +
            "When true, the name of the column that stores the event (transaction, etc.) timestamp must be specified in the timestamp_columns.event_timestamp_column field on the table.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean requiresEventTimestamp;

    @JsonPropertyDescription("The data quality sensor depends on the configuration of the ingestion timestamp column name on the analyzed table. " +
            "When true, the name of the column that stores the ingestion (created_at, loaded_at, etc.) timestamp must be specified " +
            "in the timestamp_columns.ingestion_timestamp_column field on the table.")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean requiresIngestionTimestamp;

    @JsonPropertyDescription("Default value that is used when the sensor returns no rows. " +
            "A row count sensor may return no rows when a GROUP BY condition is added to capture the database server's local time zone. In order to always return a value, a sensor may have a default value configured.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double defaultValue;

    @JsonPropertyDescription("Additional sensor definition parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> parameters;

    /**
     * Returns a list of parameters (fields) used on this sensor. Those parameters are shown by the DQOps UI.
     * @return List of parameters.
     */
    public ParameterDefinitionsListSpec getFields() {
        return fields;
    }

    /**
     * Sets the new list of fields.
     * @param fields List of fields.
     */
    public void setFields(ParameterDefinitionsListSpec fields) {
        setDirtyIf(!Objects.equals(this.fields, fields));
        this.fields = fields;
        propagateHierarchyIdToField(fields, "fields");
    }

    /**
     * Returns the flag if the event timestamp column must be configured on a table.
     * @return True when the event timestamp column name is required.
     */
    public boolean isRequiresEventTimestamp() {
        return requiresEventTimestamp;
    }

    /**
     * Sets the flag that the sensor requires an event timestamp column.
     * @param requiresEventTimestamp True when the event timestamp column is required.
     */
    public void setRequiresEventTimestamp(boolean requiresEventTimestamp) {
        setDirtyIf(this.requiresEventTimestamp != requiresEventTimestamp);
        this.requiresEventTimestamp = requiresEventTimestamp;
    }

    /**
     * Returns the flag if the ingestion timestamp column must be configured on a table.
     * @return True when the ingestion timestamp column name is required.
     */
    public boolean isRequiresIngestionTimestamp() {
        return requiresIngestionTimestamp;
    }

    /**
     * Sets the flag that the sensor requires an ingestion timestamp column.
     * @param requiresIngestionTimestamp True when the ingestion timestamp column is required.
     */
    public void setRequiresIngestionTimestamp(boolean requiresIngestionTimestamp) {
        setDirtyIf(this.requiresIngestionTimestamp != requiresIngestionTimestamp);
        this.requiresIngestionTimestamp = requiresIngestionTimestamp;
    }

    /**
     * Returns the default value that is used when the sensor does not return any result (no rows returned due to a GROUP BY clause on a whole table).
     * @return Default value to return.
     */
    public Double getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default value to return when the sensor does not return a result.
     * @param defaultValue Default value to return.
     */
    public void setDefaultValue(Double defaultValue) {
        setDirtyIf(!Objects.equals(this.defaultValue, defaultValue));
        this.defaultValue = defaultValue;
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
     * Creates and returns a copy of this object.
     */
    @Override
    public SensorDefinitionSpec deepClone() {
        SensorDefinitionSpec cloned = (SensorDefinitionSpec)super.deepClone();
        return cloned;
    }

    /**
     * Creates a trimmed version of the object without unwanted properties.
     * A trimmed version is passed to a Jinja2 sql template as a context parameter.
     * @return Trimmed version of this object.
     */
    public SensorDefinitionSpec trim() {
        SensorDefinitionSpec cloned = (SensorDefinitionSpec)super.deepClone();
        cloned.fields = null;
        return cloned;
    }
}
