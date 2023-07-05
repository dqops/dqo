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
 * Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to last week.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class WithinChange7DaysRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<WithinChange7DaysRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    /**
     * Default constructor.
     */
    public WithinChange7DaysRuleParametersSpec() {
    }

    @JsonPropertyDescription("Maximal accepted absolute change with regards to the previous readout (inclusive).")
    @SampleValues(values = "10")
    private Double maxWithin;

    @JsonPropertyDescription("When the exact_day parameter is unchecked (exact_day: false), rule searches for the most recent sensor readouts from the past 60 days and compares them. " +
            "If the parameter is selected (exact_day: true), the rule compares only with the results from the past 7 days. If no results are found from that time, no results or errors will be generated.")
    @SampleValues(values = "false")
    private Boolean exactDay = false;

    /**
     * Gets the maximal accepted absolute change for a data quality check readout.
     * @return Maximal accepted absolute change for a data quality check readout.
     */
    public Double getMaxWithin() {
        return maxWithin;
    }

    /**
     * Changes the maximal accepted absolute value for a data quality readout's change.
     * @param maxWithin New accepted absolute change.
     */
    public void setMaxWithin(Double maxWithin) {
        this.setDirtyIf(!Objects.equals(this.maxWithin, maxWithin));
        this.maxWithin = maxWithin;
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
        return "change/within_change_7_days";
    }
}
