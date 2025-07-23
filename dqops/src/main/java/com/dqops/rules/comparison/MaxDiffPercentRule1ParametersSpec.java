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
 * Data quality rule that verifies if a data quality check readout is less or equal a maximum value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class MaxDiffPercentRule1ParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<MaxDiffPercentRule1ParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };

    @JsonPropertyDescription("Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).")
    @RequiredField
    private Double maxDiffPercent = 1.0;

    /**
     * Default constructor, the minimum accepted value is 1.
     */
    public MaxDiffPercentRule1ParametersSpec() {
    }

    /**
     * Creates a rule with a given value.
     * @param maxDiffPercent Minimum accepted value.
     */
    public MaxDiffPercentRule1ParametersSpec(Double maxDiffPercent) {
        this.maxDiffPercent = maxDiffPercent;
    }

    /**
     * Returns a maximum value for a data quality check readout, for example a maximum row count.
     * @return Maximum value for a data quality check readout.
     */
    public Double getMaxDiffPercent() {
        return maxDiffPercent;
    }

    /**
     * Sets a maximum data quality check readout that is accepted, for example a maximum row count.
     * @param maxDiffPercent Maximum value that is accepted.
     */
    public void setMaxDiffPercent(Double maxDiffPercent) {
        this.setDirtyIf(!Objects.equals(this.maxDiffPercent, maxDiffPercent));
        this.maxDiffPercent = maxDiffPercent;
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
        return "comparison/diff_percent";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.maxDiffPercent != null) {
            this.maxDiffPercent = DoubleRounding.roundToKeepEffectiveDigits(this.maxDiffPercent * 1.3);
        }
    }
}