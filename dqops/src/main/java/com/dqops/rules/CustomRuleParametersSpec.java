/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules;

import com.dqops.checks.custom.CustomParametersSpecObject;
import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.utils.schema.JsonAdditionalProperties;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Custom data quality rule.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@JsonAdditionalProperties
public class CustomRuleParametersSpec extends AbstractRuleParametersSpec implements CustomParametersSpecObject {
    private static final ChildHierarchyNodeFieldMapImpl<CustomRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    /**
     * Default constructor, the minimum accepted value is 0.
     */
    public CustomRuleParametersSpec() {
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
     * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
     *
     * @return Rule definition name (python module name without .py extension).
     */
    @Override
    public String getRuleDefinitionName() {
        throw new UnsupportedOperationException("A custom rule parameters does not known its sensor name, it is defined on the custom check definition.");
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        // do nothing, we have no option right now to rescale custom rules, maybe in the future, we could support adding a special function inside the rule Python file for recalibration
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
