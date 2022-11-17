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
package ai.dqo.metadata.definitions.sensors;

import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.fields.ParameterDefinitionsListSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Data Quality sensor definition specification. Provides the configuration for a data quality sensor definition, sensor's parameters, etc.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class SensorDefinitionSpec extends AbstractSpec implements Cloneable {
    private static final ChildHierarchyNodeFieldMapImpl<SensorDefinitionSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
            put("fields", o -> o.fields);
        }
    };

    @JsonPropertyDescription("List of fields that are parameters of a custom sensor. Those fields are used by the DQO UI to display the data quality check editing screens with proper UI controls for all required fields.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ParameterDefinitionsListSpec fields;

    @JsonPropertyDescription("Additional sensor definition parameters")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

    @JsonIgnore
    private LinkedHashMap<String, String> originalParameters = new LinkedHashMap<>(); // used to perform comparison in the isDirty check

    /**
     * Returns a list of parameters (fields) used on this sensor. Those parameters are shown by the DQO UI.
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
     * Returns a key/value map of additional rule parameters.
     * @return Key/value dictionary of additional parameters passed to the rule.
     */
    public LinkedHashMap<String, String> getParameters() {
        return parameters;
    }

    /**
     * Sets a dictionary of parameters passed to the rule.
     * @param parameters Key/value dictionary with extra parameters.
     */
    public void setParameters(LinkedHashMap<String, String> parameters) {
		setDirtyIf(!Objects.equals(this.parameters, parameters));
        this.parameters = parameters;
		this.originalParameters = (LinkedHashMap<String, String>) parameters.clone();
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        return super.isDirty() || !Objects.equals(this.parameters, this.originalParameters);
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        super.clearDirty(propagateToChildren);
		this.originalParameters = (LinkedHashMap<String, String>) this.parameters.clone();
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
    public SensorDefinitionSpec clone() {
        try {
            SensorDefinitionSpec cloned = (SensorDefinitionSpec)super.clone();
            if (cloned.fields != null) {
                cloned.fields = cloned.fields.clone();
            }
            if (cloned.parameters != null) {
                cloned.parameters = (LinkedHashMap<String, String>) cloned.parameters.clone();
            }
            if (cloned.originalParameters != null) {
                cloned.originalParameters = (LinkedHashMap<String, String>) cloned.originalParameters.clone();
            }
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }

    /**
     * Creates a trimmed version of the object without unwanted properties.
     * A trimmed version is passed to a Jinja2 sql template as a context parameter.
     * @return Trimmed version of this object.
     */
    public SensorDefinitionSpec trim() {
        try {
            SensorDefinitionSpec cloned = (SensorDefinitionSpec)super.clone();
            cloned.fields = null;
            cloned.originalParameters = null;
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Object cannot be cloned.");
        }
    }
}
