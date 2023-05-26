/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.rules.change;

import ai.dqo.metadata.fields.SampleValues;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.rules.AbstractRuleParametersSpec;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Data quality rule that verifies if data quality sensor readout value changed by a percent between the provided bounds.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
public class BetweenPercentChangeRuleParametersSpec extends AbstractRuleParametersSpec {
    private static final ChildHierarchyNodeFieldMapImpl<BetweenPercentChangeRuleParametersSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractRuleParametersSpec.FIELDS) {
        {
        }
    };


    /**
     * Default constructor.
     */
    public BetweenPercentChangeRuleParametersSpec() {
    }

    @JsonPropertyDescription("Minimal accepted change relative to the previous readout (inclusive).")
    @SampleValues(values = "10")
    private Double fromPercent;

    @JsonPropertyDescription("Maximal accepted change with relative to the previous readout (inclusive).")
    @SampleValues(values = "20")
    private Double toPercent;

    /**
     * Gets the lower bound for the accepted change for a data quality check readout, relative to the previous readout.
     * @return Minimal accepted relative change for a data quality check readout.
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
     * @return Maximal accepted relative change for a data quality check readout.
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
        return "change/between_percent_change";
    }
}
