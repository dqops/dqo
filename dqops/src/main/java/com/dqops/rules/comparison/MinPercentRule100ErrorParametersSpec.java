/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.comparison;

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
 * Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class MinPercentRule100ErrorParametersSpec extends AbstractRuleParametersSpec implements MinPercentRule {
    private static final ChildHierarchyNodeFieldMapImpl<MinPercentRule100ErrorParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Minimum accepted value for the actual_value returned by the sensor (inclusive).")
    @SampleValues(values = "99.0")
    @RequiredField
    private Double minPercent = 100.0;

    /**
     * Default constructor, the minimum accepted value is 0.
     */
    public MinPercentRule100ErrorParametersSpec() {
    }

    /**
     * Creates a rule with a given value.
     * @param minPercent Minimum accepted value.
     */
    public MinPercentRule100ErrorParametersSpec(Double minPercent) {
        this.minPercent = minPercent;
    }

    /**
     * Minimum value for a data quality check readout, for example a minimum row count.
     * @return Minimum value for a data quality check readout.
     */
    public Double getMinPercent() {
        return minPercent;
    }

    /**
     * Changes the minimum value (threshold) for a data quality readout.
     * @param minPercent Minimum value.
     */
    public void setMinPercent(Double minPercent) {
        this.setDirtyIf(!Objects.equals(this.minPercent, minPercent));
        this.minPercent = minPercent;
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
        return "comparison/min_percent";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.minPercent == null) {
            return;
        }

        double to100Pct = 100.0 - this.minPercent;

        this.minPercent = DoubleRounding.roundToKeepEffectiveDigits(this.minPercent + 0.3 * to100Pct);
    }
}