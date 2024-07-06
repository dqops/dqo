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
package com.dqops.services.check.mapping.models;

import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "RuleParametersModel", description = "Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).")
public class RuleParametersModel implements Cloneable {
    /**
     * Full rule name. This field is for information purposes and can be used to create additional custom checks that reuse the same data quality rule."
     */
    @JsonPropertyDescription("Full rule name. This field is for information purposes and can be used to create additional custom checks that reuse the same data quality rule.")
    private String ruleName;

    /**
     * Rule parameters specification, returned for reference and debugging. Used by the document generation utilities.
     */
    @JsonIgnore
    private AbstractRuleParametersSpec ruleParametersSpec;

    /**
     * List of fields for editing the rule parameters like thresholds.
     */
    @JsonPropertyDescription("List of fields for editing the rule parameters like thresholds.")
    private List<FieldModel> ruleParameters;

    /**
     * Disable the rule. The rule will not be evaluated. The sensor will also not be executed if it has no enabled rules.
     */
    @JsonPropertyDescription("Disable the rule. The rule will not be evaluated. The sensor will also not be executed if it has no enabled rules.")
    private boolean disabled;

    /**
     * Returns true when the rule is configured (is not null), so it should be shown in the UI as configured (having values).
     */
    @JsonPropertyDescription("Returns true when the rule is configured (is not null), so it should be shown in the UI as configured (having values).")
    private boolean configured;

    /**
     * Creates a selective deep/shallow clone of the object. Definition objects are not cloned, but all other editable objects are.
     * @return Cloned instance.
     */
    public RuleParametersModel cloneForUpdate() {
        try {
            RuleParametersModel cloned = (RuleParametersModel)super.clone();
            if (cloned.ruleParametersSpec != null) {
                cloned.ruleParametersSpec = (AbstractRuleParametersSpec) cloned.ruleParametersSpec.deepClone();
            }

            if (cloned.ruleParameters != null) {
                cloned.ruleParameters = cloned.ruleParameters
                        .stream()
                        .map(fieldModel -> fieldModel.cloneForUpdate())
                        .collect(Collectors.toList());
            }

            return cloned;
        }
        catch (CloneNotSupportedException ex) {
            throw new DqoRuntimeException("Clone not supported: " + ex.toString(), ex);
        }
    }

    /**
     * Applies sample values for fields that have a sample value. Overrides the current values.
     * The model filled with sample values is used for generating the documentation model.
     */
    public void applySampleValues() {
        if (this.ruleParameters != null) {
            for (FieldModel ruleParameterFieldModel : this.ruleParameters) {
                ruleParameterFieldModel.applySampleValues();
            }
        }
    }
}
