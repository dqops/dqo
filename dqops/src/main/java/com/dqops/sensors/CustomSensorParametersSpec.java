/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors;

import com.dqops.checks.custom.CustomParametersSpecObject;
import com.dqops.core.secrets.SecretValueLookupContext;
import com.dqops.core.secrets.SecretValueProvider;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.utils.schema.JsonAdditionalProperties;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.*;

/**
 * Custom sensor parameters for custom checks.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@JsonAdditionalProperties
public class CustomSensorParametersSpec extends AbstractSensorParametersSpec implements CustomParametersSpecObject {
    public static final ChildHierarchyNodeFieldMapImpl<CustomSensorParametersSpec> FIELDS =
            new ChildHierarchyNodeFieldMapImpl<>(AbstractSensorParametersSpec.FIELDS) {
        {
        }
    };

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
     * Returns a dictionary of invalid properties that were present in the YAML specification file, but were not declared in the class.
     * Returns null when all properties were valid.
     *
     * @return True when undefined properties were present in the YAML file that failed the deserialization. Null when all properties were valid (declared).
     */
    @Override
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return super.getAdditionalProperties();
    }

    /**
     * Retrieves a parameter value.
     * @param parameterName Parameter name.
     * @return Parameter value or null when the parameter was unknown.
     */
    public Object getParameter(String parameterName) {
        Map<String, Object> additionalProperties = this.getAdditionalProperties();
        if (additionalProperties == null) {
            return null;
        }

        return additionalProperties.get(parameterName);
    }

    /**
     * Sets a parameter value.
     * @param parameterName Parameter name.
     * @param value New parameter value.
     */
    public void setParameter(String parameterName, Object value) {
        Map<String, Object> additionalProperties = this.getAdditionalProperties();
        if (additionalProperties == null) {
            if (value == null) {
                return;
            }

            additionalProperties = new LinkedHashMap<>();
            this.setAdditionalProperties(additionalProperties);
        }

        boolean newValueIsDifferent = !Objects.equals(additionalProperties.get(parameterName), value);
        if (newValueIsDifferent) {
            this.setDirtyIf(true);
            if (value != null) {
                additionalProperties.put(parameterName, value);
            }
            else {
                additionalProperties.remove(parameterName);
            }
        }
    }

    /**
     * Creates a cloned and trimmed version of the object. A trimmed and cloned copy is passed to the sensor.
     * All configurable variables that may use a secret value or environment variable expansion in the form ${ENV_VAR} are also expanded.
     *
     * @param secretValueProvider Secret value provider.
     * @param lookupContext       Secret lookup context.
     * @return Cloned and expanded copy of the object.
     */
    @Override
    public AbstractSensorParametersSpec expandAndTrim(SecretValueProvider secretValueProvider, SecretValueLookupContext lookupContext) {
        CustomSensorParametersSpec cloned = (CustomSensorParametersSpec) super.expandAndTrim(secretValueProvider, lookupContext);
        Map<String, Object> additionalProperties = this.getAdditionalProperties();
        if (additionalProperties == null || additionalProperties.isEmpty()) {
            return cloned;
        }

        LinkedHashMap<String, Object> expandedAdditionalProperties = new LinkedHashMap<>();

        for (Map.Entry<String, Object> entry : additionalProperties.entrySet()) {
            if (entry.getValue() instanceof String) {
                String expandedString = secretValueProvider.expandValue(entry.getValue().toString(), lookupContext);
                expandedAdditionalProperties.put(entry.getKey(), expandedString);
            } else if (entry.getValue() instanceof List) {
                List<String> originalList = (List<String>)entry.getValue();
                List<String> expandedList = secretValueProvider.expandList(originalList, lookupContext);
                expandedAdditionalProperties.put(entry.getKey(), expandedList);
            } else {
                expandedAdditionalProperties.put(entry.getKey(), entry.getValue());
            }
        }
        cloned.setAdditionalProperties(expandedAdditionalProperties);

        return cloned;
    }

    /**
     * Returns the sensor definition name. This is the folder name that keeps the sensor definition files.
     *
     * @return Sensor definition name.
     */
    @Override
    public String getSensorDefinitionName() {
        throw new UnsupportedOperationException("A custom sensor parameters does not known its sensor name, it is defined on the custom check definition.");
    }

    /**
     * Checks if the object is a default value, so it would be rendered as an empty node. We want to skip it and not render it to YAML.
     * The implementation of this interface method should check all object's fields to find if at least one of them has a non-default value or is not null, so it should be rendered.
     *
     * @return true when the object has the default values only and should not be rendered to YAML, false when it should be rendered.
     */
    @Override
    @JsonIgnore
    public boolean isDefault() {
        return super.getAdditionalProperties() == null || super.getAdditionalProperties().isEmpty();
    }
}
