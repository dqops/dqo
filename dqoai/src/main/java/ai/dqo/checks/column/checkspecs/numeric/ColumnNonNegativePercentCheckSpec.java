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
package ai.dqo.checks.column.checkspecs.numeric;

///**
// * Column non-negative percent (select sum(case when column_name < 0 then 1 else 0 end) from ...) check that calculates percent of non-negative values in a column.
// */
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
//@EqualsAndHashCode(callSuper = true)
//@Deprecated
//public class ColumnNonNegativePercentCheckSpec extends AbstractCheckDeprecatedSpec {
//    public static final ChildHierarchyNodeFieldMapImpl<ColumnNonNegativePercentCheckSpec> FIELDS = new ChildHierarchyNodeFieldMapImpl<>(AbstractCheckDeprecatedSpec.FIELDS) {
//        {
//			put("parameters", o -> o.parameters);
//			put("rules", o -> o.rules);
//        }
//    };
//
//    @JsonPropertyDescription("Non-negative percent sensor parameters")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    private ColumnNumericNonNegativePercentSensorParametersSpec parameters = new ColumnNumericNonNegativePercentSensorParametersSpec();
//
//    @JsonPropertyDescription("Non-negative percent validation rules at various alert severity levels (thresholds)")
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    @JsonSerialize(using = IgnoreEmptyYamlSerializer.class)
//    private ColumnValidityNonNegativePercentRulesSpec rules = new ColumnValidityNonNegativePercentRulesSpec();
//
//
//    /**
//     * Returns the parameters of the sensor.
//     * @return Sensor parameters.
//     */
//    public ColumnNumericNonNegativePercentSensorParametersSpec getParameters() {
//        return parameters;
//    }
//
//    /**
//     * Sets a new row count sensor parameter object.
//     * @param parameters Row count parameters.
//     */
//    public void setParameters(ColumnNumericNonNegativePercentSensorParametersSpec parameters) {
//		this.setDirtyIf(!Objects.equals(this.parameters, parameters));
//        this.parameters = parameters;
//		this.propagateHierarchyIdToField(parameters, "parameters");
//    }
//
//    /**
//     * Returns rules for the check.
//     * @return Rules set for the check.
//     */
//    public ColumnValidityNonNegativePercentRulesSpec getRules() {
//        return rules;
//    }
//
//    /**
//     * Sets a rules set for the check.
//     * @param rules Rules set.
//     */
//    public void setRules(ColumnValidityNonNegativePercentRulesSpec rules) {
//		this.setDirtyIf(!Objects.equals(this.rules, rules));
//        this.rules = rules;
//		this.propagateHierarchyIdToField(rules, "rules");
//    }
//
//    /**
//     * Returns the child map on the spec class with all fields.
//     *
//     * @return Return the field map.
//     */
//    @Override
//    protected ChildHierarchyNodeFieldMap getChildMap() {
//        return FIELDS;
//    }
//
//    /**
//     * Returns the sensor parameters spec object that identifies the sensor definition to use and contains parameters.
//     *
//     * @return Sensor parameters.
//     */
//    @Override
//    @JsonIgnore
//    public AbstractSensorParametersSpec getSensorParameters() {
//        return this.parameters;
//    }
//
//    /**
//     * Returns a rule set for this check.
//     *
//     * @return Rule set.
//     */
//    @JsonIgnore
//    @Override
//    public AbstractRuleSetSpec getRuleSet() {
//        return this.rules;
//    }
//}
