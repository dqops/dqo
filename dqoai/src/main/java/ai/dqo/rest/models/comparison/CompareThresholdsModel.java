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
package ai.dqo.rest.models.comparison;

import ai.dqo.checks.comparison.ComparisonCheckRules;
import ai.dqo.rules.comparison.MaxDiffPercentRule0ParametersSpec;
import ai.dqo.rules.comparison.MaxDiffPercentRule1ParametersSpec;
import ai.dqo.rules.comparison.MaxDiffPercentRule5ParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Model with the custom compare threshold levels for raising data quality issues at different severity levels
 * when the difference between the compared (tested) table and the reference table (the source of truth) exceed given
 * thresholds as a percentage of difference between the actual value and the expected value from the reference table.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ApiModel(value = "CompareThresholdsModel", description = "Model with the compare threshold levels for raising data quality issues at different severity levels " +
        "when the difference between the compared (tested) table and the reference table (the source of truth) exceed given " +
        "thresholds as a percentage of difference between the actual value and the expected value from the reference table.")
@Data
public class CompareThresholdsModel {
    @JsonPropertyDescription("Enables raising a warning severity data quality issue when the difference between the measure on the compared table and the reference table is above the warning level threshold (the percentage difference).")
    private boolean enableWarning = true;

    @JsonPropertyDescription("The percentage difference between the measure value on the compared table and the reference table that raises a warning severity data quality issue when the difference is bigger.")
    private double warningDifferencePercent = 0.0;

    @JsonPropertyDescription("Enables raising an error severity data quality issue when the difference between the measure on the compared table and the reference table is above the error level threshold (the percentage difference).")
    private boolean enableError = true;

    @JsonPropertyDescription("The percentage difference between the measure value on the compared table and the reference table that raises an error severity data quality issue when the difference is bigger.")
    private double errorDifferencePercent = 1.0;

    @JsonPropertyDescription("Enables raising a fatal severity data quality issue when the difference between the measure on the compared table and the reference table is above the fatal level threshold (the percentage difference).")
    private boolean enableFatal = true;

    @JsonPropertyDescription("The percentage difference between the measure value on the compared table and the reference table that raises a fatal severity data quality issue when the difference is bigger.")
    private double fatalDifferencePercent = 5.0;

    /**
     * Creates a compare threshold object from the comparison check.
     * @param comparisonCheckRules Comparison check specification.
     * @return Compare threshold model or null when the check was also null.
     */
    public static CompareThresholdsModel fromComparisonCheckSpec(ComparisonCheckRules comparisonCheckRules) {
        if (comparisonCheckRules == null) {
            return null;
        }

        CompareThresholdsModel thresholdsModel = new CompareThresholdsModel();

        MaxDiffPercentRule0ParametersSpec warning = comparisonCheckRules.getWarning();
        thresholdsModel.enableWarning = warning != null;
        if (warning != null) {
            thresholdsModel.warningDifferencePercent = warning.getMaxDiffPercent();
        }

        MaxDiffPercentRule1ParametersSpec error = comparisonCheckRules.getError();
        thresholdsModel.enableError = error != null;
        if (error != null) {
            thresholdsModel.errorDifferencePercent = error.getMaxDiffPercent();
        }

        MaxDiffPercentRule5ParametersSpec fatal = comparisonCheckRules.getFatal();
        thresholdsModel.enableFatal = fatal != null;
        if (fatal != null) {
            thresholdsModel.fatalDifferencePercent = fatal.getMaxDiffPercent();
        }

        return thresholdsModel;
    }

    /**
     * Copies the configuration of comparison difference thresholds to the comparison rules, creating or removing rules that are enabled or disabled in the threshold model.
     * @param targetComparisonCheckSpec Target comparison check specification to alter the rules.
     */
    public void copyToComparisonCheckSpec(ComparisonCheckRules targetComparisonCheckSpec) {
        targetComparisonCheckSpec.setWarning(this.enableWarning ? new MaxDiffPercentRule0ParametersSpec(this.warningDifferencePercent) : null);
        targetComparisonCheckSpec.setError(this.enableError ? new MaxDiffPercentRule1ParametersSpec(this.errorDifferencePercent) : null);
        targetComparisonCheckSpec.setFatal(this.enableFatal ? new MaxDiffPercentRule5ParametersSpec(this.fatalDifferencePercent) : null);
    }

    /**
     * Checks if any rule is enabled, so the check should be set.
     * @return True when at least one threshold level (warning, error, fatal) is enabled. False when all rules are disabled, so the check should be disabled.
     */
    @JsonIgnore
    public boolean isAnyThresholdRuleEnabled() {
        return this.enableWarning || this.enableError || this.enableFatal;
    }
}
