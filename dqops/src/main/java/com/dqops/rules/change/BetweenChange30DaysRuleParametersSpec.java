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

import com.dqops.metadata.fields.SampleValues;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMap;
import com.dqops.metadata.id.ChildHierarchyNodeFieldMapImpl;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies if data quality sensor readout value changed by a value between the provided bounds compared to last month.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BetweenChange30DaysRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<BetweenChange30DaysRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    /**
     * Default constructor.
     */
    public BetweenChange30DaysRuleParametersSpec() {
    }

    @JsonPropertyDescription("Minimal accepted change with regards to the previous readout (inclusive).")
    @SampleValues(values = "10")
    private Double from;

    @JsonPropertyDescription("Maximal accepted change with regards to the previous readout (inclusive).")
    @SampleValues(values = "20")
    private Double to;

    @JsonPropertyDescription("When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. " +
            "If the parameter is selected (exact_day: true), the rule compares only with the results from the past 30 days. If no results are found from that time, no results or errors will be generated.")
    @SampleValues(values = "false")
    private Boolean exactDay = false;

    /**
     * Gets the lower bound for the accepted change for a data quality check readout.
     * @return Minimal accepted change for a data quality check readout.
     */
    public Double getFrom() {
        return from;
    }

    /**
     * Changes the lower bound for a data quality readout's change.
     * @param from New lower bound.
     */
    public void setFrom(Double from) {
        this.setDirtyIf(!Objects.equals(this.from, from));
        this.from = from;
    }

    /**
     * Gets the upper bound for the accepted change for a data quality check readout.
     * @return Maximal accepted change for a data quality check readout.
     */
    public Double getTo() {
        return to;
    }

    /**
     * Changes the upper bound for a data quality readout's change.
     * @param to New upper bound.
     */
    public void setTo(Double to) {
        this.setDirtyIf(!Objects.equals(this.to, to));
        this.to = to;
    }

    /**
     * Gets the flag that makes the rule abstain from searching for the latest readout if there was no readout exactly 30 days in the past.
     * @return Flag <code>exactDay</code>'s value.
     */
    public Boolean getExactDay() {
        return exactDay;
    }

    /**
     * Set the flag that makes the rule abstain from searching for the latest readout if there was no readout exactly 30 days in the past.
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
        return "change/between_change_30_days";
    }
}
