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
package ai.dqo.services.check.mapping.models;

import ai.dqo.utils.exceptions.DqoRuntimeException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * UI model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "UIRuleThresholdsModel", description = "UI model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).")
public class UIRuleThresholdsModel implements Cloneable {
    @JsonPropertyDescription("Rule parameters for the error severity rule.")
    private UIRuleParametersModel error;

    @JsonPropertyDescription("Rule parameters for the warning severity rule.")
    private UIRuleParametersModel warning;

    @JsonPropertyDescription("Rule parameters for the fatal severity rule.")
    private UIRuleParametersModel fatal;

    /**
     * Finds the first not-null rule that is used for generating the documentation.
     * @return The first not null rule.
     */
    public UIRuleParametersModel findFirstNotNullRule() {
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
    public UIRuleThresholdsModel cloneForUpdate() {
        try {
            UIRuleThresholdsModel cloned = (UIRuleThresholdsModel) super.clone();
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
}
