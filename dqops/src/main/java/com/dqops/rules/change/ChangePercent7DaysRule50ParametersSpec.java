/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.change;

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
 * Data quality rule that verifies if data quality sensor readout value changed by a percent within the provided bound compared to last week.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class ChangePercent7DaysRule50ParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<ChangePercent7DaysRule50ParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    /**
     * Default constructor.
     */
    public ChangePercent7DaysRule50ParametersSpec() {
    }

    @JsonPropertyDescription("Percentage of maximum accepted change compared to a readout 7 days ago (inclusive).")
    @RequiredField
    private Double maxPercent = 50.0;

    @JsonPropertyDescription("When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. " +
            "If the parameter is selected (exact_day: true), the rule compares only with the results from the past 7 days. If no results are found from that time, no results or errors will be generated.")
    private Boolean exactDay = false;

    /**
     * Gets the maximum accepted absolute value of the change of data quality check readout, relative to the previous readout.
     * @return Maximum accepted absolute value of data quality check readout's change, relative to the previous readout.
     */
    public Double getMaxPercent() {
        return maxPercent;
    }

    /**
     * Changes the relative accepted bound for a data quality readout's change.
     * @param maxPercent New relative accepted bound percent.
     */
    public void setMaxPercent(Double maxPercent) {
        this.setDirtyIf(!Objects.equals(this.maxPercent, maxPercent));
        this.maxPercent = maxPercent;
    }

    /**
     * Gets the flag that makes the rule abstain from searching for the latest readout if there was no readout on the same day last week.
     * @return Flag <code>exactDay</code>'s value.
     */
    public Boolean getExactDay() {
        return exactDay;
    }

    /**
     * Set the flag that makes the rule abstain from searching for the latest readout if there was no readout on the same day last week.
     * @param exactDay New flag <code>exactDay</code>'s value.
     */
    public void setExactDay(Boolean exactDay) {
        this.exactDay = exactDay;
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
        return "change/change_percent_7_days";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.maxPercent == null) {
            return;
        }

        if (this.maxPercent == 0.0) {
            this.maxPercent = checkResultsSingleCheck.getActualValueColumn().max();
            return;
        }

        this.maxPercent = DoubleRounding.roundToKeepEffectiveDigits(this.maxPercent * 1.3);
    }
}
