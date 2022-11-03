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
package ai.dqo.checks.column.uniqueness;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMap;
import ai.dqo.metadata.id.ChildHierarchyNodeFieldMapImpl;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.sensors.column.uniqueness.ColumnUniquenessDistinctCountPercentSensorParametersSpec;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * Column distinct values percentage (select (count(distinct column_name) / count(column_name)) * 100 from ...) check that calculates unique values percentage in a column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = true)
@Deprecated
public class ColumnUniquenessDistinctCountPercentCheckSpec extends AbstractCheckSpec {
    public static final ChildHierarchyNodeFieldMapImpl<ColumnUniquenessDistinctCountPercentCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckSpec.FIELDS) {
        {
			put("parameters", o -> o.parameters);
			put("rules", o -> o.rules);
        }
    };

    @JsonPropertyDescription("Distinct count percent sensor parameters")
    private ColumnUniquenessDistinctCountPercentSensorParametersSpec parameters = new ColumnUniquenessDistinctCountPercentSensorParametersSpec();

    @JsonPropertyDescription("Distinct values count percent validation rules at various alert severity levels (thresholds)")
    private ColumnUniquenessDistinctCountPercentRulesSpec rules = new ColumnUniquenessDistinctCountPercentRulesSpec();

    /**
     * Returns the parameters of the sensor.
     * @return Sensor parameters.
     */
    public ColumnUniquenessDistinctCountPercentSensorParametersSpec getParameters() {
        return parameters;
    }

    /**
     * Sets a new distinct count percent sensor parameter object.
     * @param parameters Distinct count percent parameters.
     */
    public void setParameters(ColumnUniquenessDistinctCountPercentSensorParametersSpec parameters) {
		this.setDirtyIf(!Objects.equals(this.parameters, parameters));
        this.parameters = parameters;
		this.propagateHierarchyIdToField(parameters, "parameters");
    }

    /**
     * Returns rules for the check.
     * @return Rules set for the check.
     */
    public ColumnUniquenessDistinctCountPercentRulesSpec getRules() {
        return rules;
    }

    /**
     * Sets a rules set for the check.
     * @param rules Rules set.
     */
    public void setRules(ColumnUniquenessDistinctCountPercentRulesSpec rules) {
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
