/*
 * Copyright © 2023 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.rules.percentile;

import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.fields.ControlDisplayHint;
import com.dqops.metadata.fields.DisplayHint;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.utils.conversion.DoubleRounding;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.Objects;

/**
 * Data quality rule that detects anomalies in time series of data quality measures that are stationary over time, such as a percentage of null values.
 * Stationary measures stay within a well-known range of values.
 * The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 * The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    @JsonPropertyDescription("The probability (in percent) that the current sensor readout (measure) is an anomaly, because the value is outside" +
            " the regular range of previous readouts." +
            " The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist" +
            " to run the calculation.")
    @RequiredField
    private Double anomalyPercent = 0.05;

    @JsonPropertyDescription("Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in an open-source distribution of DQOps. " +
            "Please contact DQOps support to upgrade your instance to a closed-source DQOps distribution.")
    @ControlDisplayHint(DisplayHint.requires_paid_version)
    private Boolean useAi;

    /**
     * Default constructor.
     */
    public AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec() {
    }

    /**
     * Probability that the current sensor readout will achieve values greater
     * than it would be expected from the estimated distribution based on
     * the previous values gathered within the time window.
     * @return The upper percentile of the estimated normal distribution.
     */
    public Double getAnomalyPercent() {
        return anomalyPercent;
    }

    /**
     * Sets the upper percentile threshold of the estimated normal distribution.
     * @param anomalyPercent Percentage of values in the right tail that should be considered erroneous.
     */
    public void setAnomalyPercent(Double anomalyPercent) {
        this.setDirtyIf(!Objects.equals(this.anomalyPercent, anomalyPercent));
        this.anomalyPercent = anomalyPercent;
    }

    /**
     * Returns a flag which says if anomaly detection should use an AI model.
     * @return True when anomaly detection should use AI.
     */
    public Boolean getUseAi() {
        return useAi;
    }

    /**
     * Sets a flag to enable anomaly detection using AI. AI rules are supported only in a closed-source (paid) version of DQOps.
     * @param useAi True when anomalies should be detected by AI.
     */
    public void setUseAi(Boolean useAi) {
        this.setDirtyIf(!Objects.equals(this.useAi, useAi));
        this.useAi = useAi;
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
     * Returns a rule definition name. It is a name of a python module (file) without the ".py" extension. Rule names are related to the "rules" folder in DQO_HOME.
     *
     * @return Rule definition name (python module name without .py extension).
     */
    @Override
    public String getRuleDefinitionName() {
        return "percentile/anomaly_stationary_percentile_moving_average";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.anomalyPercent == null) {
            return;
        }

        if (this.anomalyPercent == 0.0) {
            this.anomalyPercent = 0.5;
            return;
        }

        this.anomalyPercent = DoubleRounding.roundToKeepEffectiveDigits(this.anomalyPercent * 0.7);
    }

    /**
     * Returns the default configuration of rule parameters (additional parameters passed to the rule) that should be published in its .dqorule.yaml configuration file.
     *
     * @return Additional configuration to save.
     */
    @Override
    @JsonIgnore
    public Map<String, String> getRuleParametersTemplate() {
        return AnomalyDetectionRuleConfiguration.T_STUDENT_DISTRIBUTION_PARAMETERS;
    }
}
