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
package ai.dqo.sensors;

import ai.dqo.core.secrets.SecretValueProvider;
import ai.dqo.metadata.basespecs.AbstractSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Base class for parameters for all sensors. This specification object is used to configure a sensor.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSensorParametersSpec extends AbstractSpec {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String filter;

    /**
     * Returns an optional WHERE clause filter used by this check for SQL sensors.
     * @return Filter.
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Sets an optional filter added to the WHERE clause.
     * @param filter Optional filter.
     */
    public void setFilter(String filter) {
		setDirtyIf(!Objects.equals(this.filter, filter));
        this.filter = filter;
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public AbstractSensorParametersSpec deepClone() {
        AbstractSensorParametersSpec cloned = (AbstractSensorParametersSpec)super.deepClone();
        return cloned;
    }

    /**
     * Returns the sensor definition name. This is the folder name that keeps the sensor definition files.
     * @return Sensor definition name.
     */
    @JsonIgnore
    public abstract String getSensorDefinitionName();

    /**
     * Creates a cloned and trimmed version of the object. A trimmed and cloned copy is passed to the sensor.
     * All configurable variables that may use a secret value or environment variable expansion in the form ${ENV_VAR} are also expanded.
     * @param secretValueProvider Secret value provider.
     * @return Cloned and expanded copy of the object.
     */
    public AbstractSensorParametersSpec expandAndTrim(SecretValueProvider secretValueProvider) {
        AbstractSensorParametersSpec cloned = (AbstractSensorParametersSpec)super.deepClone();
        cloned.filter = secretValueProvider.expandValue(cloned.filter);
        return cloned;
    }
}
