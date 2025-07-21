/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.services.check.mapping.models;

import com.dqops.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "RuleThresholdsModel", description = "Model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).")
public class RuleThresholdsModel implements Cloneable {
    /**
     * Rule parameters for the error severity rule.
     */
    @JsonPropertyDescription("Rule parameters for the error severity rule.")
    private RuleParametersModel error;

    /**
     * Rule parameters for the warning severity rule.
     */
    @JsonPropertyDescription("Rule parameters for the warning severity rule.")
    private RuleParametersModel warning;

    /**
     * Rule parameters for the fatal severity rule.
     */
    @JsonPropertyDescription("Rule parameters for the fatal severity rule.")
    private RuleParametersModel fatal;

    /**
     * Finds the first not-null rule that is used for generating the documentation.
     * @return The first not null rule.
     */
    public RuleParametersModel findFirstNotNullRule() {
        if (this.error != null) {
            return this.error;
        }

        if (this.warning != null) {
            return this.warning;
        }

        return this.fatal;
    }

    /**
     * Creates a selective deep/shallow clone of the object. Definition objects are not cloned, but all other editable objects are.
     * @return Cloned instance.
     */
    public RuleThresholdsModel cloneForUpdate() {
        try {
            RuleThresholdsModel cloned = (RuleThresholdsModel) super.clone();
            if (cloned.error != null) {
                cloned.error = cloned.error.cloneForUpdate();
            }
            if (cloned.warning != null) {
                cloned.warning = cloned.warning.cloneForUpdate();
            }
            if (cloned.fatal != null) {
                cloned.fatal = cloned.fatal.cloneForUpdate();
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
        if (this.error != null) {
            this.error.applySampleValues();
        }
        if (this.warning != null) {
            this.warning.applySampleValues();
        }
        if (this.fatal != null) {
            this.fatal.applySampleValues();
        }
    }

    /**
     * Checks if any rule at any severity level is configured (enabled).
     * @return One of the rules is enabled.
     */
    public boolean hasAnyRulesConfigured() {
        return this.warning.isConfigured() || this.error.isConfigured() || this.fatal.isConfigured();
    }
}
