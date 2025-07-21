/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.stdev;

import com.dqops.data.checkresults.normalization.CheckResultsNormalizedResult;
import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.utils.conversion.DoubleRounding;
import com.dqops.utils.reflection.RequiredField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies if a data quality sensor readout value
 * doesn't excessively deviate from the moving average of increments on a time window.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ChangeMultiplyMovingStdevWithin60DaysRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<ChangeMultiplyMovingStdevWithin60DaysRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    @JsonPropertyDescription("How many multiples of the estimated standard deviation within the moving average" +
            " can the current sensor readout be with regard to the time window. Set" +
            " the time window at the threshold level for all severity levels (warning, error," +
            " fatal) at once. The default is a time window of 60 periods (days, etc.)," +
            " but there must be at least 20 readouts to run the calculation.")
    @SampleValues(values = "1.5")
    @RequiredField
    private Double multiplyStdev;

    /**
     * Multiplied factor used to calculate a multiplied stdev.
     * @return Multiple factor to calculate multiplied stdev.
     */
    public Double getMultiplyStdev() {
        return multiplyStdev;
    }

    /**
     *  Sets a multiple factor to calculate multiplied stdev.
     * @param multiplyStdev Multiple factor.
     */
    public void setMultiplyStdev(Double multiplyStdev) {
        this.setDirtyIf(!Objects.equals(this.multiplyStdev, multiplyStdev));
        this.multiplyStdev = multiplyStdev;
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
        return "stdev/change_multiply_moving_stdev_within_60_days";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.multiplyStdev == null) {
            return;
        }

        if (this.multiplyStdev <= 0) {
            this.multiplyStdev = 1.0;
            return;
        }

        this.multiplyStdev = DoubleRounding.roundToKeepEffectiveDigits(this.multiplyStdev * 1.3);
    }
}
