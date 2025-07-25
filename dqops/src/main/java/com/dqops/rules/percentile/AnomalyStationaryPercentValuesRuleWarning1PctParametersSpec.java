/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
 * Data quality rule that detects anomalies in a stationary time series of percentage values (in the range 0..100).
 * The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 * The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class AnomalyStationaryPercentValuesRuleWarning1PctParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<AnomalyStationaryPercentValuesRuleWarning1PctParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    @JsonPropertyDescription("The probability (in percent) that the current percentage value is an anomaly because the value is outside" +
            " the regular range of captured percentage measures." +
            " The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist" +
            " to run the calculation.")
    @RequiredField
    private Double anomalyPercent = 0.1;

    @JsonPropertyDescription("Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. " +
            "Please contact DQOps support to upgrade your instance to a full DQOps instance.")
    @ControlDisplayHint(DisplayHint.requires_paid_version)
    private Boolean useAi;

    /**
     * Default constructor.
     */
    public AnomalyStationaryPercentValuesRuleWarning1PctParametersSpec() {
    }

    /**
     * Configures the anomaly detection rule with a parameter.
     * @param anomalyPercent Anomaly percent.
     */
    public AnomalyStationaryPercentValuesRuleWarning1PctParametersSpec(Double anomalyPercent) {
        this.anomalyPercent = anomalyPercent;
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
        return "percentile/anomaly_stationary_percent_values";
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
            this.anomalyPercent = 1.0;
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
