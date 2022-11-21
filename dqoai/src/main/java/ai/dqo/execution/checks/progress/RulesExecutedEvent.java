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
package ai.dqo.execution.checks.progress;

import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.sources.TableSpec;

/**
 * Progress event raised after data quality rules were executed for all rows of normalized sensor results.
 */
public class RulesExecutedEvent extends CheckExecutionProgressEvent {
    private final TableSpec tableSpec;
    private final SensorExecutionRunParameters sensorRunParameters;
    private final SensorReadoutsNormalizedResult normalizedSensorResults;
    private final RuleEvaluationResult ruleEvaluationResult;

    /**
     * Creates a progress event object.
     *
     * @param tableSpec               Target table specification.
     * @param sensorRunParameters     Sensor run parameters.
     * @param normalizedSensorResults Normalized sensor results that were passed to the rule evaluation.
     * @param ruleEvaluationResult    Rule evaluation results with one or more rows for each sensor value (a check may have multiple rules, one rule generates one result).
     */
    public RulesExecutedEvent(TableSpec tableSpec,
							  SensorExecutionRunParameters sensorRunParameters,
							  SensorReadoutsNormalizedResult normalizedSensorResults,
							  RuleEvaluationResult ruleEvaluationResult) {
        this.tableSpec = tableSpec;
        this.sensorRunParameters = sensorRunParameters;
        this.normalizedSensorResults = normalizedSensorResults;
        this.ruleEvaluationResult = ruleEvaluationResult;
    }

    /**
     * Target table specification.
     *
     * @return Target table specification.
     */
    public TableSpec getTableSpec() {
        return tableSpec;
    }

    /**
     * Sensor run parameters.
     *
     * @return Sensor run parameters.
     */
    public SensorExecutionRunParameters getSensorRunParameters() {
        return sensorRunParameters;
    }

    /**
     * Normalized sensor results that were passed to the rule evaluation.
     *
     * @return Normalized sensor results that were passed to the rule evaluation.
     */
    public SensorReadoutsNormalizedResult getNormalizedSensorResults() {
        return normalizedSensorResults;
    }

    /**
     * Rule evaluation results with one or more rows for each sensor value (a check may have multiple rules, one rule generates one result).
     *
     * @return Rule evaluation results with one or more rows for each sensor value (a check may have multiple rules, one rule generates one result).
     */
    public RuleEvaluationResult getRuleEvaluationResult() {
        return ruleEvaluationResult;
    }
}
