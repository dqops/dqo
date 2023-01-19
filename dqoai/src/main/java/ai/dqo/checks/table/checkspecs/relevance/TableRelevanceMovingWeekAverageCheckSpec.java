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
package ai.dqo.checks.table.checkspecs.relevance;

import ai.dqo.checks.AbstractCheckDeprecatedSpec;
import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.sensors.table.relevance.TableRelevanceMovingWeekAverageSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Moving week average (SELECT AVG(<column>) OVER(ORDER BY <date> DESC ROWS BETWEEN CURRENT ROW AND 6 FOLLOWING ...) test that runs a moving_week_average check, obtains an average and verifies the number by calling the validation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated
public class TableRelevanceMovingWeekAverageCheckSpec extends AbstractCheckDeprecatedSpec {
    public static final ChildHierarchyNodeFieldMapImpl<TableRelevanceMovingWeekAverageCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckDeprecatedSpec.FIELDS) {
        {
            put("parameters", o -> o.parameters);
            put("rules", o -> o.rules);
        }
    };

    @JsonPropertyDescription("Moving week average sensor parameters")
    private TableRelevanceMovingWeekAverageSensorParametersSpec parameters = new TableRelevanceMovingWeekAverageSensorParametersSpec();

    @JsonPropertyDescription("Moving week averages validation rules at various alert severity levels (thresholds)")
    private TableRelevanceMovingWeekAverageRulesSpec rules = new TableRelevanceMovingWeekAverageRulesSpec();

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    public TableRelevanceMovingWeekAverageSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new moving week average sensor parameter object.
     * @param parameters Moving week average parameters.
     */
    public void setParameters(TableRelevanceMovingWeekAverageSensorParametersSpec parameters) {
        this.setDirtyIf(!Objects.equals(this.parameters, parameters));
        this.parameters = parameters;
        this.propagateHierarchyIdToField(parameters, "parameters");
    }

    /**
     * Returns rules for the check.
     * @return Rules set for the check.
     */
    public TableRelevanceMovingWeekAverageRulesSpec getRules() {
        return rules;
    }

    /**
     * Sets a rules set for the check.
     * @param rules Rules set.
     */
    public void setRules(TableRelevanceMovingWeekAverageRulesSpec rules) {
        this.setDirtyIf(!Objects.equals(this.rules, rules));
        this.rules = rules;
        this.propagateHierarchyIdToField(rules, "rules");
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
     * Returns the sensor parameters spec object that identifies the sensor definition to use and contains parameters.
     *
     * @return Sensor parameters.
     */
    @Override
    @JsonIgnore
    public AbstractSensorParametersSpec getSensorParameters() {
        return this.parameters;
    }

    /**
     * Returns a rule set for this check.
     *
     * @return Rule set.
     */
    @JsonIgnore
    @Override
    public AbstractRuleSetSpec getRuleSet() {
        return this.rules;
    }
}
