/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.rules.change;

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
 * Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds compared to last week.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BetweenPercentChange7DaysRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<BetweenPercentChange7DaysRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    /**
     * Default constructor.
     */
    public BetweenPercentChange7DaysRuleParametersSpec() {
    }

    @JsonPropertyDescription("Minimum accepted change relative to the previous readout (inclusive).")
    @SampleValues(values = "10")
    private Double fromPercent;

    @JsonPropertyDescription("Maximum accepted change relative to the previous readout (inclusive).")
    @SampleValues(values = "20")
    private Double toPercent;

    @JsonPropertyDescription("When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. " +
            "If the parameter is selected (exact_day: true), the rule compares only with the results from the past 7 days. If no results are found from that time, no results or errors will be generated.")
    private Boolean exactDay = false;

    /**
     * Gets the lower bound for the accepted change for a data quality check readout, relative to the previous readout.
     * @return Minimum accepted relative change for a data quality check readout.
     */
    public Double getFromPercent() {
        return fromPercent;
    }

    /**
     * Changes the relative lower bound for a data quality readout's change.
     * @param fromPercent New relative lower bound percent.
     */
    public void setFromPercent(Double fromPercent) {
        this.setDirtyIf(!Objects.equals(this.fromPercent, fromPercent));
        this.fromPercent = fromPercent;
    }

    /**
     * Gets the upper bound for the accepted change for a data quality check readout, relative to the previous readout.
     * @return Maximum accepted relative change for a data quality check readout.
     */
    public Double getToPercent() {
        return toPercent;
    }

    /**
     * Changes the relative upper bound for a data quality readout's change.
     * @param toPercent New relative upper bound percent.
     */
    public void setToPercent(Double toPercent) {
        this.setDirtyIf(!Objects.equals(this.toPercent, toPercent));
        this.toPercent = toPercent;
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
        return "change/between_percent_change_7_days";
    }

    /**
     * Decreases the rule severity by changing the parameters.
     * NOTE: this method is allowed to do nothing if changing the rule severity is not possible
     *
     * @param checkResultsSingleCheck Historical results for the check to decide how much to change.
     */
    @Override
    public void decreaseRuleSensitivity(CheckResultsNormalizedResult checkResultsSingleCheck) {
        if (this.fromPercent != null) {
            this.fromPercent = DoubleRounding.roundToKeepEffectiveDigits(this.fromPercent * 0.7);
        }

        if (this.toPercent != null) {
            this.toPercent = DoubleRounding.roundToKeepEffectiveDigits(this.toPercent * 1.3);
        }
    }
}
