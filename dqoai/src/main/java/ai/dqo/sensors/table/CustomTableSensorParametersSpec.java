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
package ai.dqo.sensors.table;

import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Custom table level sensor that accepts a name of a sensor to execute.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class CustomTableSensorParametersSpec extends AbstractTableSensorParametersSpec {
    public static final ChildHierarchyNodeFieldMapImpl<CustomTableSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractTableSensorParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Sensor definition as a folder path inside the user home sensor's folder to the folder with the right sensor.")
    private String sensorDefinitionPath;

    @JsonPropertyDescription("Dictionary of additional parameters (key / value pairs) that are passed to the sensor and may be used in the Jinja2 template.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private LinkedHashMap<String, Object> params = new LinkedHashMap<>();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private LinkedHashMap<String, Object> originalParams = new LinkedHashMap<>(); // used to perform comparison in the isDirty check

    /**
     * Sensor name as a path to the sensor.
     * @return Sensor name.
     */
    public String getSensorDefinitionPath() {
        return sensorDefinitionPath;
    }

    /**
     * Sets the sensor name as a path to the definition.
     * @param sensorDefinitionPath Sensor name.
     */
    public void setSensorDefinitionPath(String sensorDefinitionPath) {
		this.setDirtyIf(!Objects.equals(this.sensorDefinitionPath, sensorDefinitionPath));
        this.sensorDefinitionPath = sensorDefinitionPath;
    }

    /**
     * Returns a key/value map of additional sensor parameters.
     * @return Key/value dictionary of additional parameters.
     */
    public LinkedHashMap<String, Object> getParams() {
        return params;
    }

    /**
     * Sets a dictionary of additional sensor parameters.
     * @param params Key/value dictionary with extra parameters.
     */
    public void setParams(LinkedHashMap<String, Object> params) {
		setDirtyIf(!Objects.equals(this.params, params));
        this.params = params;
		this.originalParams = (LinkedHashMap<String, Object>) params.clone();
    }

    /**
     * Check if the object is dirty (has changes).
     *
     * @return True when the object is dirty and has modifications.
     */
    @Override
    public boolean isDirty() {
        return super.isDirty() || !Objects.equals(this.params, this.originalParams);
    }

    /**
     * Clears the dirty flag (sets the dirty to false). Called after flushing or when changes should be considered as unimportant.
     * @param propagateToChildren When true, clears also the dirty status of child objects.
     */
    @Override
    public void clearDirty(boolean propagateToChildren) {
        super.clearDirty(propagateToChildren);
		this.originalParams = (LinkedHashMap<String, Object>) this.params.clone();
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public AbstractSensorParametersSpec clone() {
        CustomTableSensorParametersSpec cloned = (CustomTableSensorParametersSpec)super.clone();
        if (cloned.params != null) {
            cloned.params = (LinkedHashMap<String, Object>)cloned.params.clone();
        }
        if (cloned.originalParams != null) {
            cloned.originalParams = (LinkedHashMap<String, Object>)cloned.originalParams.clone();
        }

        return cloned;
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
     * Returns the sensor definition name. This is the folder name that keeps the sensor definition files.
     *
     * @return Sensor definition name.
     */
    @Override
    public String getSensorDefinitionName() {
        return this.sensorDefinitionPath;
    }
}
