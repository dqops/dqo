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
import org.apache.parquet.Strings;

import java.util.Objects;

/**
 * Base class for parameters for all sensors. This specification object is used to configure a sensor.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractSensorParametersSpec extends AbstractSpec implements Cloneable {
    public static final ChildHierarchyNodeFieldMapImpl<AbstractSensorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Disables the data quality sensor. Only enabled sensors are executed. The sensor should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.")
    @Deprecated  // we will disable the check, not the sensor
    private boolean disabled;

    @JsonPropertyDescription("SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String filter;

    /**
     * Checks if the sensor (and its parent check) is disabled.
     * @return True when the check is disabled.
     */
    @Deprecated
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the disabled flag on a check.
     * @param disabled Disabled flag.
     */
    @Deprecated
    public void setDisabled(boolean disabled) {
		this.setDirtyIf(this.disabled != disabled);
        this.disabled = disabled;
    }

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
    public AbstractSensorParametersSpec clone() {
        try {
            AbstractSensorParametersSpec cloned = (AbstractSensorParametersSpec)super.clone();
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object.");
        }
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
        try {
            AbstractSensorParametersSpec cloned = (AbstractSensorParametersSpec)super.clone();
            cloned.filter = secretValueProvider.expandValue(cloned.filter);
            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Cannot clone the object.");
        }
    }

    /**
     * This method should be overridden in derived classes and should check if there are any simple fields (String, integer, double, etc)
     * that are not HierarchyNodes (they are analyzed by the hierarchy tree engine).
     * This method should return true if there is at least one field that must be serialized to YAML.
     * It may return false only if:
     * - the parameter specification class has no custom fields (parameters are not configurable)
     * - there are some fields, but they are all nulls, so not a single field would be serialized.
     * The purpose of this method is to avoid serialization of the parameters as just "parameters: " yaml, without nested
     * fields because such a YAML is just invalid.
     * @return True when the parameters spec must be serialized to YAML because it has some non-null simple fields,
     *         false when serialization of the parameters may lead to writing an empty "parameters: " entry in YAML.
     */
    @JsonIgnore
    public abstract boolean hasNonNullSimpleFields();

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    @JsonIgnore
    public boolean isDefault() {
        if (!Strings.isNullOrEmpty(this.filter) || this.disabled) {
            return false;
        }

        boolean isDefault = super.isDefault();
        if (!isDefault) {
            return false;
        }

        if (hasNonNullSimpleFields()) {
            return false;
        }

        return true;  // the parameters does not need to be serialized
    }
}
