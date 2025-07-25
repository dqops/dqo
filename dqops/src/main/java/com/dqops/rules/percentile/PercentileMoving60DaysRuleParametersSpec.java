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
import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.utils.conversion.DoubleRounding;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies if a data quality sensor readout value is probable under
 * the estimated normal distribution based on the previous values gathered within a time window.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class PercentileMoving60DaysRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<PercentileMoving60DaysRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    @JsonPropertyDescription("Probability that the current sensor readout will achieve values greater" +
            " than it would be expected from the estimated distribution based on the previous values" +
            " gathered within the time window. In other words, the upper quantile of the estimated" +
            " normal distribution. Set the time window at the threshold level for all severity" +
            " levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.)" +
            " time window, but at least 20 readouts must exist to run the calculation.")
    @SampleValues(values = "5")
    private Double percentileAbove;

    @JsonPropertyDescription("Probability that the current sensor readout will achieve values lesser" +
            " than it would be expected from the estimated distribution based on the previous values" +
            " gathered within the time window. In other words, the lower quantile of the estimated" +
            " normal distribution. Set the time window at the threshold level for all severity" +
            " levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.)" +
            " time window, but at least 20 readouts must exist to run the calculation.")
    @SampleValues(values = "5")
    private Double percentileBelow;

    /**
     * Default constructor.
     */
    public PercentileMoving60DaysRuleParametersSpec() {
    }

    /**
     * Probability that the current sensor readout will achieve values greater
     * than it would be expected from the estimated distribution based on
     * the previous values gathered within the time window.
     * @return The upper percentile of the estimated normal distribution.
     */
    public Double getPercentileAbove() {
        return percentileAbove;
    }

    /**
     * Sets the upper percentile threshold of the estimated normal distribution.
     * @param percentileAbove Percentage of values in the right tail that should be considered erroneous.
     */
    public void setPercentileAbove(Double percentileAbove) {
        this.setDirtyIf(!Objects.equals(this.percentileAbove, percentileAbove));
        this.percentileAbove = percentileAbove;
    }

    /**
     * Probability that the current sensor readout will achieve values lesser
     * than it would be expected from the estimated distribution based on
     * the previous values gathered within the time window.
     * @return The lower percentile of the estimated normal distribution.
     */
    public Double getPercentileBelow() {
        return percentileBelow;
    }

    /**
     * Sets the upper percentile threshold of the estimated normal distribution.
     * @param percentileBelow Percentage of values in the left tail that should be considered erroneous.
     */
    public void setPercentileBelow(Double percentileBelow) {
        this.setDirtyIf(!Objects.equals(this.percentileBelow, percentileBelow));
        this.percentileBelow = percentileBelow;
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
        return "percentile/percentile_moving_60_days";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.percentileAbove != null) {
            this.percentileAbove = DoubleRounding.roundToKeepEffectiveDigits(this.percentileAbove * 0.7);
        }

        if (this.percentileBelow != null) {
            this.percentileBelow = DoubleRounding.roundToKeepEffectiveDigits(this.percentileBelow * 0.7);
        }
    }
}
